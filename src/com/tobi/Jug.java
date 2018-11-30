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

    /**
     * Transfers water from this jug, to another
     * without overfilling the destination
     *
     * @param destination jug for water to be poured into
     */

    public void to(Jug destination) {
        int spaceLeft = destination.capacity - destination.filled;
        int moving = spaceLeft > filled ? filled : spaceLeft;
        filled -= moving;
        int result = destination.filled + moving;
        destination.filled = result < destination.capacity ? result : destination.capacity;
    }

    /**
     * Fills jub
     */

    public void fill() {
        filled = capacity;
    }

    /**
     * Empties jub
     */

    public void empty() {
        filled = 0;
    }

    /**
     * @return String representation for this jug, used when logging in console
     * @see State#toString()
     */

    @Override
    public String toString() {
        return String.format("%s: %d", name, filled);
    }

    /**
     * Used when running traversing a state's branch, so this jug's
     * properties -- thus the parent state's properties -- go unnaffected
     *
     * @return cloned jug
     */

    public Jug clone() {
        return new Jug(name, filled, capacity);
    }

    /**
     * @param obj Jug to be compared to
     * @return this and obj are in the same state
     */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Jug) {
            Jug jug = (Jug) obj;
            return jug.filled == filled &&
                    jug.capacity == capacity;
        } else return false;
    }

    /**
     * used by HashSet class, to assign a location
     * for object in the HashTable
     *
     * @return object hash
     * @see java.util.HashSet
     * @see Main#pair(int, int)
     */

    @Override
    public int hashCode() {
        return Main.pair(filled, capacity);
    }

    /**
     * @param jug to be compared to
     * @return whether this and jug are filled to the same level
     * @see State#equals(Object)
     */

    public boolean level(Jug jug) {
        return jug.filled == filled;
    }

    public int getCapacity() {
        return capacity;
    }
}
