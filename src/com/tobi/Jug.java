package com.tobi;

/**
 * @author Tobi Akinyemi
 * @since 29/11/2018
 */

/**
 * Jug class, representing 1 of 3 jugs
 */
public class Jug {
    private String name;
    private int filled;
    private final int capacity;


    public Jug(String name, int filled, int capacity) {
        this.name = name;
        this.filled = filled;
        this.capacity = capacity;
    }

    public int getFilled() {
        return filled;
    }

    public void to(Jug destination) {
        int spaceLeft = destination.capacity - destination.filled;
        int moving = spaceLeft > filled ? filled : spaceLeft;
        filled -= moving;
        int result = destination.filled + moving;
        destination.filled = result < destination.capacity ? result : destination.capacity;
    }

    public void fill() {
        filled = capacity;
    }

    public void empty() {
        filled = 0;
    }

    @Override
    public String toString() {
        return String.format("Jug %s { %d / %d }", name, filled, capacity);
    }

    public Jug clone() {
        return new Jug(name, filled, capacity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Jug) {
            Jug jug = (Jug) obj;
            return jug.filled == filled &&
                    jug.capacity == capacity;
        } else return false;
    }

    @Override
    public int hashCode() {
        return Main.pair(filled, capacity);
    }

    public boolean level(Jug jug) {
        return jug.filled == filled;
    }

    public int getCapacity() {
        return capacity;
    }
}
