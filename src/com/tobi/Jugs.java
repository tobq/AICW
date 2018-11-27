package com.tobi;

public class Jugs {
    public static void search(int capacityA, int capacityB, int capacityC) {
        Jug A = new Jug("A", capacityA);
        Jug B = new Jug("B", capacityB);
        Jug C = new Jug("C", capacityC);

        printJugs(A, B, C);
    }

    private static void AtoB(Jug A, Jug B, Jug C) {
        long initialState = hashJugs(A, B, C);
        A.to(B);
        if (hashJugs(A, B, C) != initialState) AtoB(A, B, C);
        
    }

    private static void AtoC(Jug A, Jug B, Jug C) {
        A.to(C);
    }

    private static void BtoA(Jug A, Jug B, Jug C) {
        B.to(A);
    }

    private static void BtoC(Jug A, Jug B, Jug C) {
        B.to(C);
    }

    private static void CtoA(Jug A, Jug B, Jug C) {
        C.to(A);
    }

    private static void CtoB(Jug A, Jug B, Jug C) {
        C.to(B);
    }

    private static void fillA(Jug A, Jug B, Jug C) {
        A.fill();
    }

    private static void fillB(Jug A, Jug B, Jug C) {
        B.fill();
    }

    private static void fillC(Jug A, Jug B, Jug C) {
        C.fill();
    }

    private static void emptyA(Jug A, Jug B, Jug C) {
        A.empty();
    }

    private static void emptyB(Jug A, Jug B, Jug C) {
        B.empty();
    }

    private static void emptyC(Jug A, Jug B, Jug C) {
        C.empty();
    }

    private static void printJugs(Jug A, Jug B, Jug C) {
        System.out.println(A + ", " + B + ", " + C);
    }

    public static long hashPair(long a, long b) {
        return a >= b ? a * a + a + b : a + b * b;
    }

    public static long hashJugs(Jug A, Jug B, Jug C) {
        return hashPair(hashPair(A.getFilled(), B.getFilled()), C.getFilled());
    }
}

class Jug {
    private final int capacity;
    private int filled;
    private String name;

    Jug(String name, int capacity, int filled) {
        this.name = name;
        this.capacity = capacity;
        this.filled = filled;
    }

    Jug(String name, int capacity) {
        this(name, capacity, 0);
    }

    public int getFilled() {
        return filled;
    }

    public void to(Jug jug) {
        int sum = jug.filled + filled;
        filled = 0;
        jug.filled = sum < jug.capacity ? sum : jug.capacity;
    }

    public void fill() {
        filled = capacity;
    }

    public void empty() {
        filled = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Jug %s { %d / %d }",
                name, filled, capacity
        );
    }
}
