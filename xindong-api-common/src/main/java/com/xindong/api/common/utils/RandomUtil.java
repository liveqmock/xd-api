package com.xindong.api.common.utils;


import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    private static final Random random = new Random();

    private static final long hi = 1L << 50;

    public static int randomRangeInt(int min, int max) {

        return random.nextInt(max) % (max - min + 1) + min;

    }


    public static String randomStr() {
        return Long.toHexString(hi | (random.nextLong() & (hi - 1)));
    }

    public static UUID randomUUID() {

        return UUID.randomUUID();
    }

    public static int randomInt(int max) {
        return random.nextInt(max);
    }

    public static int randomInt_1000() {
        return random.nextInt(1000);
    }
}
