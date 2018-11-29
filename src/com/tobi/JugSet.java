package com.tobi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class JugSet {
    private Jug A;
    private Jug B;
    private Jug C;
    private long DFSiterations = 0;
    private HashSet<Long> states = new HashSet<>();
    private HashSet<Long> visitedIterations = new HashSet<>();

    JugSet(long capacityA, long capacityB, long capacityC) {
        A = new Jug("A", capacityA);
        B = new Jug("B", capacityB);
        C = new Jug("C", capacityC);

        iterate(0, pair(0, 0, 0));

        System.out.printf("Found %d states after %d iterations", states.size(), DFSiterations);
    }


    private void iterate(int branch, long state) {
        DFSiterations++;
        visitedIterations.add(pair(branch, state));
        states.add(state);
        importState(state); //TODO: check redunancy of this
        System.out.println(branch);
        printJugs();

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
        long nextState = hashState();
        printJugs();
        int i = branch;
        if (states.add(nextState)) printJugs();
        else if (visitedIterations.contains(pair(branch, nextState))) i++;
        for (; i < 12; i++) iterate(i, nextState);
    }

    private void importState(long state) {
        long[] capacities = unpair(state, 3);
        A.setFilled(capacities[0]);
        B.setFilled(capacities[1]);
        C.setFilled(capacities[2]);
    }

    public static long[] unpair(long z) {
        double floorRootZ = Math.floor(Math.sqrt(z));
        double squareFloorRootZ = floorRootZ * floorRootZ;
        double x, y;

        if (z - squareFloorRootZ < floorRootZ) {
            x = z - squareFloorRootZ;
            y = floorRootZ;
        } else {
            x = floorRootZ;
            y = z - squareFloorRootZ - floorRootZ;
        }
        return new long[]{(long) x, (long) y};
    }

    public static long[] unpair(long z, int dimensions) {
        long[] result = new long[dimensions];
        while (dimensions-- > 2) {
            long[] pair = unpair(z);
            result[dimensions] = pair[1];
            z = pair[0];
        }
        long[] finalPair = unpair(z);
        result[0] = finalPair[0];
        result[1] = finalPair[1];
        return result;
    }

    private void printJugs() {
        System.out.println(A + ", " + B + ", " + C);
    }

    public static long pair(long a, long b) {
        return a >= b ? a * a + a + b : a + b * b;
    }

    public static long pair(long a, long b, long... rest) {
        long result = pair(a, b);
        for (long c : rest) result = pair(result, c);
        return result;
    }

    private long hashState() {
        return pair(A.getFilled(), B.getFilled(), C.getFilled());
    }
}

