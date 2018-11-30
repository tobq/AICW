package com.tobi;

/**
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class State {

    /**
     * jugCount field represents the number of Jugs used in state
     */
    private final int jugCount;

    /**
     * branches field represents the number of different operations
     * (branches from one state to another) are available.
     * <p>
     * jugCount Fill operations +
     * jugCount * (jugCount - 1) Transfer operations +
     * jugCount Empty operations
     */

    private final int branches;

    private final int[] capacities;
    private final int[] fills;
    private final int transferOperations;

    State(int[] fills, int[] capacities) {
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
     * which each represent an operation, such as: A.fill()
     * TODO:
     *
     * @param branch to be traversed
     * @return the child state (node) at the end of that branch
     * @see State#branches
     */

    public State traverse(int branch) {
//        if (branch < 0) return this; // todo: check performance hit
        State state = new State(fills, capacities);
        if (branch < transferOperations) {
            for (int i = 0; i < jugCount; i++) {
                if (branch < i *)
            }
        } else {
            branch -= transferOperations;
            if (branch < jugCount) state.fills[branch] = capacities[branch];
            else state.fills[branch - jugCount] = 0;
        }

        return state;
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
     * @see Traversal
     * @see HashSet
     * @see State#traverse(int)
     */

    public void search() {
        HashSet<State> states = new HashSet<>();
        HashSet<Traversal> visitedTraversals = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.push(this);

        long startTime = System.nanoTime();
        while (!stack.isEmpty()) {
            State state = stack.pop();
            if (states.add(state)) System.out.println(state);
            for (int i = 0; i < branches; i++)
                if (visitedTraversals.add(new Traversal(state, i)))
                    stack.push(state.traverse(i));
        }
        long endTime = System.nanoTime();

        System.out.printf(
                "\nFound %d possible states from jug capacities %s\n" +
                        "Search traversed %d branches in %.3f milliseconds",
                states.size(),
                Arrays.toString(capacities),
                visitedTraversals.size(),
                (endTime - startTime) / 1E6
        );
    }

    /**
     * @return String representation for this state, used when logging in console
     * @see State#search()
     */

    @Override
    public String toString() {
        return String.format("State {%s, %s, %s}", A, B, C);
    }

    /**
     * @param obj State to be compared to
     * @return this and obj are in the same state
     */


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state = (State) obj;
            return state.A.level(A) &&
                    state.B.level(B) &&
                    state.C.level(C);
        } else return false;
    }

    /**
     * used by HashSet class, to assign a location
     * for object in the HashTable
     *
     * @return object hash
     * @see HashSet
     * @see Main#pair(int, int, int...)
     */


    @Override
    public int hashCode() {
        return Main.pair(A.getFilled(), B.getFilled(), C.getFilled());
    }
}

class Traversal {
    private final int branch;
    private final State state;

    Traversal(State state, int branch) {
        this.branch = branch;
        this.state = state;
    }

    @Override
    public String toString() {
        return branch + ": " + state.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Traversal) {
            Traversal traversal = (Traversal) obj;
            return traversal.branch == branch &&
                    traversal.state.equals(state);
        } else return false;
    }

    @Override
    public int hashCode() {
        return Main.pair(branch, state.hashCode());
    }
}