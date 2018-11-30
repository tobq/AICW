package com.tobi;

/**
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

import java.util.HashSet;
import java.util.Stack;

public class State {
    private static final int BRANCHES = 12;

    private final Jug A;
    private final Jug B;
    private final Jug C;

    State(Jug A, Jug B, Jug C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

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

    @Override
    public String toString() {
        return A + ", " + B + ", " + C;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State state = (State) obj;
            return state.A.level(A) &&
                    state.B.level(B) &&
                    state.C.level(C);
        } else return false;
    }

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