package com.tobi;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

public class Main {

    public static void main(String[] args) {
        int capacityA, capacityB, capacityC;

        try (Scanner scanner = new Scanner(System.in)) {
            capacityA = readCapacity(scanner, "A");
            capacityB = readCapacity(scanner, "B");
            capacityC = readCapacity(scanner, "C");
        }

        State startState = State.setup(capacityA, capacityB, capacityC);
        startState.search();
    }

    /**
     * This method is used to read jug capacities from a console
     *
     * @param scanner used to read from console
     * @param jugName used to log request for jug capacity
     * @return the Integer passed from the console
     */

    private static int readCapacity(Scanner scanner, String jugName) {
        try {
            System.out.printf("Enter capacity for %s: ", jugName);
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.print("Invalid input capacity. ");
            scanner.next();
            return readCapacity(scanner, jugName);
        }
    }

    /**
     * This is an implementation of Szudzik's efficient
     * "Elegant Pair" function
     *
     * @param x first value in pairing
     * @param y second value in pairing
     * @return unique integer mapping to both x and y
     * @see <a href="http://szudzik.com/ElegantPairing.pdf">Szudzik's function</a>
     */

    public static int pair(int x, int y) {
        return x > y ? x * x + x + y : x + y * y;
    }

    /**
     * This is and overload for the "Elegant Pair" implementation
     * where N numbers can be paired
     * ! N >= 2
     *
     * @param x    first value in pairing
     * @param y    second value in pairing
     * @param rest N-2 numbers to be added to pairing
     * @return unique integer mapping to both x and y
     * @see Main#pair(int, int)
     */

    public static int pair(int x, int y, int... rest) {
        int result = pair(x, x);
        for (int c : rest) result = pair(result, c);
        return result;
    }
}
