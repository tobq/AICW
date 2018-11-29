package com.tobi;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        new JugSet(2, 3, 7);
//        new JugSet(1, 1, 1);

        long pair = JugSet.pair(12, 10, 10, 10);
        System.out.println(pair);
        System.out.println(Arrays.toString(JugSet.unpair(pair, 5)));
    }
}
