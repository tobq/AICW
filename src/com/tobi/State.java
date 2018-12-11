package com.tobi;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * State class representing a state of n water jugs
 *
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

public class State {

    /**
     * jugCount field represents the number of Jugs used in state
     */
    private final int jugCount;

    /**
     * number of different transfer operations between each jug,
     * calculated by {@link State#jugCount} * ({@link State#jugCount} - 1)
     */
    private final int transferOperations;

    /**
     * branches field represents the number of different operations
     * (branches from one state to another) that are available.
     * <p>
     * {@link State#jugCount} Fill operations +
     * {@link State#transferOperations} Transfer operations +
     * {@link State#jugCount} Empty operations
     */

    private final int branches;

    /**
     * array, of size {@link State#jugCount}, containing capacities for each jug
     */

    private final int[] capacities;

    /**
     * array, of size {@link State#jugCount}, containing fill levels for each jug
     */

    private final int[] fills;

    private State(int[] fills, int[] capacities) {
        this.capacities = capacities;
        int jugCount = capacities.length;
        this.fills = fills;
        this.jugCount = jugCount;
        transferOperations = jugCount * (jugCount - 1);
        branches = jugCount + transferOperations + jugCount;
    }

    /**
     * @param capacities for each jug in this state
     * @return a state with empty jugs of the given capacities
     */

    public static State setup(int... capacities) {
        int[] fills = new int[capacities.length];
        Arrays.fill(fills, 0);
        return new State(fills, capacities);
    }


    /**
     * This method goes down 1 of "branches" branches,
     * which each represent an operation such as "Fill Jug 1."
     * This method clones the current state, decodes the branch number
     * into an operation and then runs the operation.
     *
     * @param branch to be traversed
     * @return the child state (node) at the end of the branch
     * @see State#branches
     */

    private State traverse(int branch) {
        State childState = clone();
        if (branch < transferOperations) {
            int destinations = jugCount - 1,
                    source = branch / destinations,
                    destination = branch % destinations;
            if (destination == source) destination = destinations;
            childState.transfer(source, destination);
        } else {
            branch -= transferOperations;
            if (branch < jugCount) childState.fill(branch);
            else childState.empty(branch - jugCount);
        }

        return childState;
    }

    /**
     * transfers water between the source and destination jug
     * without overflowing
     *
     * @param source      jug
     * @param destination jug
     */

    private void transfer(int source, int destination) {
        int destinationCapacity = capacities[destination];
        int destinationFill = fills[destination];
        int spaceLeft = destinationCapacity - destinationFill,
                moving = spaceLeft > fills[source] ? fills[source] : spaceLeft,
                result = destinationFill + moving;

        fills[source] -= moving;
        fills[destination] =
                result < destinationCapacity ?
                        result : destinationCapacity;
    }

    /**
     * Fills jug to max capacity
     *
     * @param jug index
     */

    private void fill(int jug) {
        fills[jug] = capacities[jug];
    }

    /**
     * Empties jug
     *
     * @param jug index
     */

    private void empty(int jug) {
        fills[jug] = 0;
    }

    /**
     * Used to log the order of operations within {@link State#search()}
     *
     * @param branch to be decoded
     * @return description of decoded branch operation
     */

    public String branchDescription(int branch) {
        if (branch < transferOperations) {
            int destinations = jugCount - 1,
                    source = branch / destinations,
                    destination = branch % destinations;
            if (destination == source) destination = destinations;

            return String.format("Transfer from Jug %d to Jug %d", source, destination);
        } else {
            branch -= transferOperations;
            if (branch < jugCount) return String.format("Fill Jug %d", branch);
            else return String.format("Empty Jug %d", branch - jugCount);
        }
    }

    /**
     * This method is an implementation for Depth First Search, searching this
     * state and logging all the states reachable from this state.
     * The idea of dynamic programming is used within this search to make
     * the search much more efficient. If a branch from a state has already
     * been traversed, it is never done again. This is done by storing Traversals
     * in a HashSet, and checking whether it already exists, before traversal.
     * <p>
     * This method was initially recursively implemented; however, with large
     * jug capacities, stack overflow errors were -- expectedly -- reached.
     *
     * @see HashSet
     * @see State#traverse(int)
     */

    public void search() {
        HashSet<State> states = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.push(this);

        // Search stopwatch began
        long startTime = System.nanoTime();
        long ITERATIONS = 0;
        for (; !stack.isEmpty(); ITERATIONS++) {
            State state = stack.pop();
            states.add(state);
//            System.out.println(state);
            for (int i = 0; i < branches; i++) {
                State childState = state.traverse(i);
//                    System.out.println(branchDescription(i) + ": " + state + " > " + childState);
                if (!states.contains(childState))
                    stack.push(childState);
            }
        }

        // Search stopwatch stopped
        long endTime = System.nanoTime();

        System.out.printf(
                "\nFound %d unique states from jug capacities %s\n" +
                        "Search iterated %d times in %.3f milliseconds",
                states.size(),
                Arrays.toString(capacities),
                ITERATIONS,
                (endTime - startTime) / 1E6
        );
    }

    /**
     * @return String representation for this state, used when logging in console
     * @see State#search()
     */

    @Override
    public String toString() {
        return String.format("State %s", Arrays.toString(fills));
    }

    /**
     * @param obj State to be compared to
     * @return this and obj are in the same state
     */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state = (State) obj;
            return Arrays.equals(fills, state.fills);
        } else return false;
    }

    /**
     * used by HashSet class, to assign a location
     * for object in the HashTable
     *
     * @return Hash representation of this state
     * @see HashSet
     * @see StateSearch#pair(int...)
     */


    @Override
    public int hashCode() {
        return StateSearch.pair(fills);
    }

    /**
     * @return clone of this state
     * @see State#traverse(int)
     */

    @Override
    protected State clone() {
        return new State(fills.clone(), capacities.clone());
    }
}

