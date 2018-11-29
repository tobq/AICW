package com.tobi;

public class Jug {
    private final long capacity;
    private long filled;
    private String name;

    public Jug(String name, long capacity, long filled) {
        this.name = name;
        this.capacity = capacity;
        this.filled = filled;
    }

    public Jug(String name, long capacity) {
        this(name, capacity, 0);
    }

    public long getFilled() {
        return filled;
    }

    public void to(Jug jug) {
        long sum = jug.filled + filled;
        filled = jug.capacity < filled ? filled - jug.capacity : 0;
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

    public void setFilled(long filled) {
        this.filled = filled;
    }
}
