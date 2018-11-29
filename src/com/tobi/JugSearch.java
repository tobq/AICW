package com.tobi;

import java.util.HashSet;

public class JugSearch {
    private long DFSiterations = 0;
    private HashSet<State> states = new HashSet<>();
    private HashSet<Iteration> visitedIterations = new HashSet<>();

    JugSearch(long capacityA, long capacityB, long capacityC) {
        iterate(new Iteration(0, capacityA, capacityB, capacityC));

        System.out.printf("Found %d states after %d iterations", states.size(), DFSiterations);
    }


    private void iterate(Iteration iteration) {
        DFSiterations++;
        visitedIterations.add(iteration);
        states.add(iteration.state);
//        importState(iteration.state); //TODO: check redunancy of this
        System.out.println(iteration);
//        printJugs();

//        long nextState = hashState();
//        printJugs();
//        int i = branch;
//        if (states.add(nextState)) printJugs();
//        else if (visitedIterations.contains(pair(branch, nextState))) i++;
//        for (; i < 12; i++) iterate(i, nextState);
    }

//    public static long[] unpair(long z) {
//        double floorRootZ = Math.floor(Math.sqrt(z));
//        double squareFloorRootZ = floorRootZ * floorRootZ;
//        double x, y;
//
//        if (z - squareFloorRootZ < floorRootZ) {
//            x = z - squareFloorRootZ;
//            y = floorRootZ;
//        } else {
//            x = floorRootZ;
//            y = z - squareFloorRootZ - floorRootZ;
//        }
//        return new long[]{(long) x, (long) y};
//    }
//
//    public static long[] unpair(long z, int dimensions) {
//        long[] result = new long[dimensions];
//        while (dimensions-- > 2) {
//            long[] pair = unpair(z);
//            result[dimensions] = pair[1];
//            z = pair[0];
//        }
//        long[] finalPair = unpair(z);
//        result[0] = finalPair[0];
//        result[1] = finalPair[1];
//        return result;
//    }
//
//    public static long pair(long a, long b) {
//        return a >= b ? a * a + a + b : a + b * b;
//    }
//
//    public static long pair(long a, long b, long... rest) {
//        long result = pair(a, b);
//        for (long c : rest) result = pair(result, c);
//        return result;
//    }

//    private long hashState() {
//        return pair(A.getFilled(), B.getFilled(), C.getFilled());
//    }
}

class State {
    final Jug A;
    final Jug B;
    final Jug C;

    State(long a, long b, long c) {
        A = new Jug("A", a);
        B = new Jug("B", b);
        C = new Jug("C", c);
    }

    State(Jug A, Jug B, Jug C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    public String toString() {
        return A + ", " + B + ", " + C;
    }

    public void iterate(int branch){
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
    }
}

class Iteration {
    final int branch;
    final State state;

    Iteration(int branch, State state) {
        this.branch = branch;
        this.state = state;
    }

    Iteration(int branch, long a, long b, long c) {
        this(branch, new State(a, b, c));
    }

//    State execute(){
//        Jug A =
//        switch (branch) {
//            case (0):
//                A.to(B);
//                break;
//            case (1):
//                A.to(C);
//                break;
//            case (2):
//                B.to(A);
//                break;
//            case (3):
//                B.to(C);
//                break;
//            case (4):
//                C.to(A);
//                break;
//            case (5):
//                C.to(B);
//                break;
//            case (6):
//                A.fill();
//                break;
//            case (7):
//                B.fill();
//                break;
//            case (8):
//                C.fill();
//                break;
//            case (9):
//                A.empty();
//                break;
//            case (10):
//                B.empty();
//                break;
//            case (11):
//                C.empty();
//                break;
//        }
//    }

    @Override
    public String toString() {
        return branch + ": " + state.toString();
    }
}