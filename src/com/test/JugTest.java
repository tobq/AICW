package com.test;

import com.tobi.Jug;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JugTest {
    @Test
    public static void to() {
        Jug a = new Jug("A", 2),
                b = new Jug("B", 3),
                c = new Jug("C", 5);

        b.fill();
        System.out.println(a + " " + b + " " + c);
        b.to(c);
        System.out.println(a + " " + b + " " + c);
        c.to(a);
        System.out.println(a + " " + b + " " + c);

        assertEquals(a.getFilled(), 2);
        assertEquals(b.getFilled(), 0);
        assertEquals(c.getFilled(), 1);
    }

    @Test
    public static void fill() {
        Jug a = new Jug("A", 2);

        System.out.println(a);
        a.fill();
        System.out.println(a);

        assertEquals(a.getFilled(), 2);
    }

    @Test
    public static void Clone() throws CloneNotSupportedException {
        Jug a = new Jug("A", 2);

        System.out.println(a);
        Jug clone = (Jug) a.clone();
        a.fill();
        System.out.println(clone);
    }
}
