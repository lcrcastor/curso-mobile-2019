package com.zurich.lockview.utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomUtils {
    private static final Random a = new Random();

    private RandomUtils() {
        throw new AssertionError("You can not instantiate this class. Use its static utility methods instead");
    }

    public static int randInt() {
        return a.nextInt((int) (System.nanoTime() % 2147483647L));
    }

    public static int randInt(int i) {
        if (i > 0) {
            return randInt() % i;
        }
        return 0;
    }

    public static int[] randIntArray(int i, int i2) {
        if (i2 <= i) {
            return new int[0];
        }
        ArrayList arrayList = new ArrayList();
        while (i < i2) {
            arrayList.add(Integer.valueOf(i));
            i++;
        }
        int[] iArr = new int[arrayList.size()];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int randInt = randInt(arrayList.size());
            iArr[i3] = ((Integer) arrayList.get(randInt)).intValue();
            arrayList.remove(randInt);
        }
        return iArr;
    }

    public static int[] randIntArray(int i) {
        return randIntArray(0, i);
    }
}
