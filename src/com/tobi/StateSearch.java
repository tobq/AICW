package com.tobi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class used to set up inputs and start
 * state search on user-specified capacities
 *
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

class StateSearch {
    /**
     * List of letters for jugs
     */
    static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * Starts state search using capacities from CLI arguments,
     * or capacities read from console
     *
     * @param args from console
     */

    public static void main(String[] args) {
        int capacityArgCount = args.length;
        State startState;
        if (capacityArgCount > 0) {
            int[] capacities = new int[capacityArgCount];
            for (int i = 0; i < args.length; i++) {
                try {
                    capacities[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.out.printf("ERROR: '%s' is not a valid integer jug capacity\n", args[i]);
                    System.exit(1);
                }
            }
            startState = State.setup(capacities);
        } else {
            startState = State.setup(readCapacities());
        }
        startState.search();
    }

    /**
     * This used to read capacities from the console (when no CLI arguments are given)
     *
     * @return the capacities read
     */

    private static int[] readCapacities() {
        ArrayList<Integer> capacityList = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            capacityList.add(readCapacity(scanner, 0));

            System.out.println("\nWhen you're done adding additional jugs, press ENTER.");
            for (int i = 1; i < alphabet.length; i++) capacityList.add(readOptionalCapacity(scanner, i));
        } catch (NoSuchElementException e) {

        }
        int[] capacities = new int[capacityList.size()];
        int size = capacityList.size();
        for (int i = 0; i < size; i++) capacities[i] = capacityList.get(i);
        return capacities;
    }

    /**
     * This method is used to read jug capacities from a console
     *
     * @param scanner   used to read from console
     * @param jugNumber used to log request for jug capacity
     * @return the Integer passed from the console
     */

    private static int readCapacity(Scanner scanner, int jugNumber) {
        try {
            return readOptionalCapacity(scanner, jugNumber);
        } catch (NoSuchElementException e) {
            System.out.print("At least 1 jug required. ");
            return readCapacity(scanner, jugNumber);
        }
    }

    /**
     * This method is used to read following (optional) jug capacities from a console
     * after the first capacity has been read
     *
     * @param scanner   used to read from console
     * @param jugNumber used to log request for jug capacity
     * @return the Integer passed from the console
     */

    private static int readOptionalCapacity(Scanner scanner, int jugNumber)
            throws NoSuchElementException {
        try {
            System.out.printf("Enter capacity for jug %s: ", alphabet[jugNumber]);
            String capacity = scanner.nextLine();
            if (capacity.isEmpty()) throw new NoSuchElementException("Enter key pressed");
            return Integer.parseInt(capacity);
        } catch (NumberFormatException e) {
            System.out.print("Invalid input capacity. ");
            scanner.next();
            return readOptionalCapacity(scanner, jugNumber);
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

    private static int pair(int x, int y) {
        return x > y ? x * x + x + y : x + y * y;
    }

    /**
     * This is and overload for the "Elegant Pair" implementation
     * where N numbers can be paired
     *
     * @param numbers N numbers to be added to pairing, where N {@literal >}= 2
     * @return unique integer mapping to both x and y
     * @see StateSearch#pair(int, int)
     */

    public static int pair(int... numbers) {
        switch (numbers.length) {
            case 0:
                return 0;
            case 1:
                return numbers[0];
            default:
                int result = pair(numbers[0], numbers[1]);
                for (int i = 2; i < numbers.length; i++) result = pair(result, numbers[i]);
                return result;
        }
    }
}
