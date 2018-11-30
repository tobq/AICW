package com.tobi;

/**
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

import java.util.HashSet;
import java.util.Stack;

public class State {

    /**
     * N field represents the number of Jugs used in state
     */
    private static final int N = 3;

    /**
     * BRANCHES field represents the number of different operations
     * (branches from one state to another) are available.
     * <p>
     * N Fill operations +
     * N * (N - 1) Transfer operations +
     * N Transfer operations
     */

    private static final int BRANCHES = N + N * (N - 1) + N;

    private final Jug A;
    private final Jug B;
    private final Jug C;

    State(Jug A, Jug B, Jug C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    /**
     * This method goes down 1 of {@value BRANCHES} branches,
     * which each represent an operation, such as: A.fill()
     *
     * @param branch to be traversed
     * @return the child state (node) at the end of that branch
     */

    public State traverse(int branch) {
        Jug A = this.A.clone();
        Jug B = this.B.clone();
        Jug C = this.C.clone();

        switch (branch) {
            case (0):
                A.to(B);
                break;
            case (1):
                A.to(C);
                break;
            case (2):
                B.to(A);
                break;
            case (3):
                B.to(C);
                break;
            case (4):
                C.to(A);
                break;
            case (5):
                C.to(B);
                break;
            case (6):
                A.fill();
                break;
            case (7):
                B.fill();
                break;
            case (8):
                C.fill();
                break;
            case (9):
                A.empty();
                break;
            case (10):
                B.empty();
                break;
            case (11):
                C.empty();
                break;
        }
        return new State(A, B, C);
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
            for (int i = 0; i < BRANCHES; i++)
                if (visitedTraversals.add(new Traversal(state, i)))
                    stack.push(state.traverse(i));
        }
        long endTime = System.nanoTime();

        System.out.printf(
                "\nFound %d possible states from jug capacities {A: %d, B: %d, C: %d}\n" +
                        "Search traversed %d branches in %.3f milliseconds",
                states.size(),
                A.getCapacity(),
                B.getCapacity(),
                C.getCapacity(),
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
        return A + ", " + B + ", " + C;
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