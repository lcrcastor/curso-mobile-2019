package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat192;

public class SecP192R1Field {
    static final int[] a = {-1, -1, -2, -1, -1, -1};
    static final int[] b = {1, 0, 2, 0, 1, 0, -2, -1, -3, -1, -1, -1};
    private static final int[] c = {-1, -1, -3, -1, -2, -1, 1, 0, 2};

    private static void a(int[] iArr) {
        long j = (((long) iArr[0]) & 4294967295L) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (((long) iArr[1]) & 4294967295L);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = j2 + (((long) iArr[2]) & 4294967295L) + 1;
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            Nat.incAt(6, iArr, 3);
        }
    }

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat192.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && Nat192.gte(iArr3, a))) {
            a(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((Nat.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && Nat.gte(12, iArr3, b))) && Nat.addTo(c.length, c, iArr3) != 0) {
            Nat.incAt(12, iArr3, c.length);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && Nat192.gte(iArr2, a))) {
            a(iArr2);
        }
    }

    private static void b(int[] iArr) {
        long j = (((long) iArr[0]) & 4294967295L) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (((long) iArr[1]) & 4294967295L);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = j2 + ((((long) iArr[2]) & 4294967295L) - 1);
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            Nat.decAt(6, iArr, 3);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat192.fromBigInteger(bigInteger);
        if (fromBigInteger[5] == -1 && Nat192.gte(fromBigInteger, a)) {
            Nat192.subFrom(a, fromBigInteger);
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(6, iArr, 0, iArr2);
        } else {
            Nat.shiftDownBit(6, iArr2, Nat192.add(iArr, a, iArr2));
        }
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = Nat192.createExt();
        Nat192.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if ((Nat192.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && Nat.gte(12, iArr3, b))) && Nat.addTo(c.length, c, iArr3) != 0) {
            Nat.incAt(12, iArr3, c.length);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (Nat192.isZero(iArr)) {
            Nat192.zero(iArr2);
        } else {
            Nat192.sub(a, iArr, iArr2);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j;
        int[] iArr3 = iArr2;
        long j2 = ((long) iArr[6]) & 4294967295L;
        long j3 = ((long) iArr[7]) & 4294967295L;
        long j4 = ((long) iArr[9]) & 4294967295L;
        long j5 = j2 + (((long) iArr[10]) & 4294967295L);
        long j6 = j3 + (((long) iArr[11]) & 4294967295L);
        long j7 = j3;
        long j8 = (((long) iArr[0]) & 4294967295L) + j5 + 0;
        int i = (int) j8;
        long j9 = ((long) iArr[8]) & 4294967295L;
        long j10 = (j8 >> 32) + (((long) iArr[1]) & 4294967295L) + j6;
        iArr3[1] = (int) j10;
        long j11 = j10 >> 32;
        long j12 = j5 + j9;
        long j13 = j6 + j4;
        long j14 = j11 + (((long) iArr[2]) & 4294967295L) + j12;
        long j15 = j14 & 4294967295L;
        long j16 = (j14 >> 32) + (((long) iArr[3]) & 4294967295L) + j13;
        iArr3[3] = (int) j16;
        long j17 = j13 - j7;
        long j18 = (j16 >> 32) + (((long) iArr[4]) & 4294967295L) + (j12 - j2);
        iArr3[4] = (int) j18;
        long j19 = (j18 >> 32) + (((long) iArr[5]) & 4294967295L) + j17;
        iArr3[5] = (int) j19;
        long j20 = j19 >> 32;
        long j21 = j15 + j20;
        long j22 = j20 + (((long) i) & 4294967295L);
        iArr3[0] = (int) j22;
        long j23 = j22 >> 32;
        if (j23 != 0) {
            long j24 = j23 + (((long) iArr3[1]) & 4294967295L);
            iArr3[1] = (int) j24;
            j = j21 + (j24 >> 32);
        } else {
            j = j21;
        }
        iArr3[2] = (int) j;
        if (((j >> 32) != 0 && Nat.incAt(6, iArr3, 3) != 0) || (iArr3[5] == -1 && Nat192.gte(iArr3, a))) {
            a(iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = ((long) i) & 4294967295L;
            long j3 = (((long) iArr[0]) & 4294967295L) + j2 + 0;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (((long) iArr[1]) & 4294967295L);
                iArr[1] = (int) j5;
                j4 = j5 >> 32;
            }
            long j6 = j4 + (((long) iArr[2]) & 4294967295L) + j2;
            iArr[2] = (int) j6;
            j = j6 >> 32;
        } else {
            j = 0;
        }
        if ((j != 0 && Nat.incAt(6, iArr, 3) != 0) || (iArr[5] == -1 && Nat192.gte(iArr, a))) {
            a(iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = Nat192.createExt();
        Nat192.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] createExt = Nat192.createExt();
        Nat192.square(iArr, createExt);
        while (true) {
            reduce(createExt, iArr2);
            i--;
            if (i > 0) {
                Nat192.square(iArr2, createExt);
            } else {
                return;
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat192.sub(iArr, iArr2, iArr3) != 0) {
            b(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(12, iArr, iArr2, iArr3) != 0 && Nat.subFrom(c.length, c, iArr3) != 0) {
            Nat.decAt(12, iArr3, c.length);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (Nat.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && Nat192.gte(iArr2, a))) {
            a(iArr2);
        }
    }
}
