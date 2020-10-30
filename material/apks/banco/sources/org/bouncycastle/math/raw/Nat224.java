package org.bouncycastle.math.raw;

import java.math.BigInteger;
import org.bouncycastle.util.Pack;

public abstract class Nat224 {
    public static int add(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = (((long) iArr[i + 0]) & 4294967295L) + (((long) iArr2[i2 + 0]) & 4294967295L) + 0;
        iArr3[i3 + 0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[i + 1]) & 4294967295L) + (((long) iArr2[i2 + 1]) & 4294967295L);
        iArr3[i3 + 1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + (((long) iArr2[i2 + 2]) & 4294967295L);
        iArr3[i3 + 2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L) + (((long) iArr2[i2 + 3]) & 4294967295L);
        iArr3[i3 + 3] = (int) j4;
        long j5 = (j4 >>> 32) + (((long) iArr[i + 4]) & 4294967295L) + (((long) iArr2[i2 + 4]) & 4294967295L);
        iArr3[i3 + 4] = (int) j5;
        long j6 = (j5 >>> 32) + (((long) iArr[i + 5]) & 4294967295L) + (((long) iArr2[i2 + 5]) & 4294967295L);
        iArr3[i3 + 5] = (int) j6;
        long j7 = (j6 >>> 32) + (((long) iArr[i + 6]) & 4294967295L) + (((long) iArr2[i2 + 6]) & 4294967295L);
        iArr3[i3 + 6] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L) + 0;
        iArr3[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L);
        iArr3[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L);
        iArr3[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L);
        iArr3[3] = (int) j4;
        long j5 = (j4 >>> 32) + (((long) iArr[4]) & 4294967295L) + (((long) iArr2[4]) & 4294967295L);
        iArr3[4] = (int) j5;
        long j6 = (j5 >>> 32) + (((long) iArr[5]) & 4294967295L) + (((long) iArr2[5]) & 4294967295L);
        iArr3[5] = (int) j6;
        long j7 = (j6 >>> 32) + (((long) iArr[6]) & 4294967295L) + (((long) iArr2[6]) & 4294967295L);
        iArr3[6] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addBothTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        int i4 = i3 + 0;
        long j = (((long) iArr[i + 0]) & 4294967295L) + (((long) iArr2[i2 + 0]) & 4294967295L) + (((long) iArr3[i4]) & 4294967295L) + 0;
        iArr3[i4] = (int) j;
        int i5 = i3 + 1;
        long j2 = (j >>> 32) + (((long) iArr[i + 1]) & 4294967295L) + (((long) iArr2[i2 + 1]) & 4294967295L) + (((long) iArr3[i5]) & 4294967295L);
        iArr3[i5] = (int) j2;
        int i6 = i3 + 2;
        long j3 = (j2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + (((long) iArr2[i2 + 2]) & 4294967295L) + (((long) iArr3[i6]) & 4294967295L);
        iArr3[i6] = (int) j3;
        int i7 = i3 + 3;
        long j4 = (j3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L) + (((long) iArr2[i2 + 3]) & 4294967295L) + (((long) iArr3[i7]) & 4294967295L);
        iArr3[i7] = (int) j4;
        int i8 = i3 + 4;
        long j5 = (j4 >>> 32) + (((long) iArr[i + 4]) & 4294967295L) + (((long) iArr2[i2 + 4]) & 4294967295L) + (((long) iArr3[i8]) & 4294967295L);
        iArr3[i8] = (int) j5;
        int i9 = i3 + 5;
        long j6 = (j5 >>> 32) + (((long) iArr[i + 5]) & 4294967295L) + (((long) iArr2[i2 + 5]) & 4294967295L) + (((long) iArr3[i9]) & 4294967295L);
        iArr3[i9] = (int) j6;
        int i10 = i3 + 6;
        long j7 = (j6 >>> 32) + (((long) iArr[i + 6]) & 4294967295L) + (((long) iArr2[i2 + 6]) & 4294967295L) + (((long) iArr3[i10]) & 4294967295L);
        iArr3[i10] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L) + (((long) iArr3[0]) & 4294967295L) + 0;
        iArr3[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L) + (((long) iArr3[1]) & 4294967295L);
        iArr3[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L) + (((long) iArr3[2]) & 4294967295L);
        iArr3[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L) + (((long) iArr3[3]) & 4294967295L);
        iArr3[3] = (int) j4;
        long j5 = (j4 >>> 32) + (((long) iArr[4]) & 4294967295L) + (((long) iArr2[4]) & 4294967295L) + (((long) iArr3[4]) & 4294967295L);
        iArr3[4] = (int) j5;
        long j6 = (j5 >>> 32) + (((long) iArr[5]) & 4294967295L) + (((long) iArr2[5]) & 4294967295L) + (((long) iArr3[5]) & 4294967295L);
        iArr3[5] = (int) j6;
        long j7 = (j6 >>> 32) + (((long) iArr[6]) & 4294967295L) + (((long) iArr2[6]) & 4294967295L) + (((long) iArr3[6]) & 4294967295L);
        iArr3[6] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addTo(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        int i4 = i2 + 0;
        long j = (((long) i3) & 4294967295L) + (((long) iArr[i + 0]) & 4294967295L) + (((long) iArr2[i4]) & 4294967295L);
        iArr2[i4] = (int) j;
        int i5 = i2 + 1;
        long j2 = (j >>> 32) + (((long) iArr[i + 1]) & 4294967295L) + (((long) iArr2[i5]) & 4294967295L);
        iArr2[i5] = (int) j2;
        int i6 = i2 + 2;
        long j3 = (j2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + (((long) iArr2[i6]) & 4294967295L);
        iArr2[i6] = (int) j3;
        int i7 = i2 + 3;
        long j4 = (j3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L) + (((long) iArr2[i7]) & 4294967295L);
        iArr2[i7] = (int) j4;
        int i8 = i2 + 4;
        long j5 = (j4 >>> 32) + (((long) iArr[i + 4]) & 4294967295L) + (((long) iArr2[i8]) & 4294967295L);
        iArr2[i8] = (int) j5;
        int i9 = i2 + 5;
        long j6 = (j5 >>> 32) + (((long) iArr[i + 5]) & 4294967295L) + (((long) iArr2[i9]) & 4294967295L);
        iArr2[i9] = (int) j6;
        int i10 = i2 + 6;
        long j7 = (j6 >>> 32) + (((long) iArr[i + 6]) & 4294967295L) + (((long) iArr2[i10]) & 4294967295L);
        iArr2[i10] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j = (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L) + 0;
        iArr2[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L);
        iArr2[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L);
        iArr2[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L);
        iArr2[3] = (int) j4;
        long j5 = (j4 >>> 32) + (((long) iArr[4]) & 4294967295L) + (((long) iArr2[4]) & 4294967295L);
        iArr2[4] = (int) j5;
        long j6 = (j5 >>> 32) + (((long) iArr[5]) & 4294967295L) + (((long) iArr2[5]) & 4294967295L);
        iArr2[5] = (int) j6;
        long j7 = (j6 >>> 32) + (((long) iArr[6]) & 4294967295L) + (((long) iArr2[6]) & 4294967295L);
        iArr2[6] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i, int[] iArr2, int i2) {
        int i3 = i + 0;
        int i4 = i2 + 0;
        long j = (((long) iArr[i3]) & 4294967295L) + (((long) iArr2[i4]) & 4294967295L) + 0;
        int i5 = (int) j;
        iArr[i3] = i5;
        iArr2[i4] = i5;
        int i6 = i + 1;
        int i7 = i2 + 1;
        long j2 = (j >>> 32) + (((long) iArr[i6]) & 4294967295L) + (((long) iArr2[i7]) & 4294967295L);
        int i8 = (int) j2;
        iArr[i6] = i8;
        iArr2[i7] = i8;
        int i9 = i + 2;
        int i10 = i2 + 2;
        long j3 = (j2 >>> 32) + (((long) iArr[i9]) & 4294967295L) + (((long) iArr2[i10]) & 4294967295L);
        int i11 = (int) j3;
        iArr[i9] = i11;
        iArr2[i10] = i11;
        int i12 = i + 3;
        int i13 = i2 + 3;
        long j4 = (j3 >>> 32) + (((long) iArr[i12]) & 4294967295L) + (((long) iArr2[i13]) & 4294967295L);
        int i14 = (int) j4;
        iArr[i12] = i14;
        iArr2[i13] = i14;
        int i15 = i + 4;
        int i16 = i2 + 4;
        long j5 = (j4 >>> 32) + (((long) iArr[i15]) & 4294967295L) + (((long) iArr2[i16]) & 4294967295L);
        int i17 = (int) j5;
        iArr[i15] = i17;
        iArr2[i16] = i17;
        int i18 = i + 5;
        int i19 = i2 + 5;
        long j6 = (j5 >>> 32) + (((long) iArr[i18]) & 4294967295L) + (((long) iArr2[i19]) & 4294967295L);
        int i20 = (int) j6;
        iArr[i18] = i20;
        iArr2[i19] = i20;
        int i21 = i + 6;
        int i22 = i2 + 6;
        long j7 = (j6 >>> 32) + (((long) iArr[i21]) & 4294967295L) + (((long) iArr2[i22]) & 4294967295L);
        int i23 = (int) j7;
        iArr[i21] = i23;
        iArr2[i22] = i23;
        return (int) (j7 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
        iArr2[5] = iArr[5];
        iArr2[6] = iArr[6];
    }

    public static int[] create() {
        return new int[7];
    }

    public static int[] createExt() {
        return new int[14];
    }

    public static boolean diff(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        boolean gte = gte(iArr, i, iArr2, i2);
        if (gte) {
            sub(iArr, i, iArr2, i2, iArr3, i3);
            return gte;
        }
        sub(iArr2, i2, iArr, i, iArr3, i3);
        return gte;
    }

    public static boolean eq(int[] iArr, int[] iArr2) {
        for (int i = 6; i >= 0; i--) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 224) {
            throw new IllegalArgumentException();
        }
        int[] create = create();
        int i = 0;
        while (bigInteger.signum() != 0) {
            int i2 = i + 1;
            create[i] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
            i = i2;
        }
        return create;
    }

    public static int getBit(int[] iArr, int i) {
        int i2;
        if (i == 0) {
            i2 = iArr[0];
        } else {
            int i3 = i >> 5;
            if (i3 < 0 || i3 >= 7) {
                return 0;
            }
            i2 = iArr[i3] >>> (i & 31);
        }
        return i2 & 1;
    }

    public static boolean gte(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 6; i3 >= 0; i3--) {
            int i4 = iArr[i + i3] ^ Integer.MIN_VALUE;
            int i5 = Integer.MIN_VALUE ^ iArr2[i2 + i3];
            if (i4 < i5) {
                return false;
            }
            if (i4 > i5) {
                return true;
            }
        }
        return true;
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i = 6; i >= 0; i--) {
            int i2 = iArr[i] ^ Integer.MIN_VALUE;
            int i3 = Integer.MIN_VALUE ^ iArr2[i];
            if (i2 < i3) {
                return false;
            }
            if (i2 > i3) {
                return true;
            }
        }
        return true;
    }

    public static boolean isOne(int[] iArr) {
        if (iArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 7; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i = 0; i < 7; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = ((long) iArr2[i2 + 0]) & 4294967295L;
        long j2 = ((long) iArr2[i2 + 1]) & 4294967295L;
        long j3 = ((long) iArr2[i2 + 2]) & 4294967295L;
        long j4 = ((long) iArr2[i2 + 3]) & 4294967295L;
        long j5 = ((long) iArr2[i2 + 4]) & 4294967295L;
        long j6 = ((long) iArr2[i2 + 5]) & 4294967295L;
        long j7 = ((long) iArr2[i2 + 6]) & 4294967295L;
        long j8 = ((long) iArr[i + 0]) & 4294967295L;
        long j9 = (j8 * j) + 0;
        iArr3[i3 + 0] = (int) j9;
        long j10 = (j9 >>> 32) + (j8 * j2);
        iArr3[i3 + 1] = (int) j10;
        long j11 = (j10 >>> 32) + (j8 * j3);
        iArr3[i3 + 2] = (int) j11;
        long j12 = (j11 >>> 32) + (j8 * j4);
        iArr3[i3 + 3] = (int) j12;
        long j13 = (j12 >>> 32) + (j8 * j5);
        iArr3[i3 + 4] = (int) j13;
        long j14 = (j13 >>> 32) + (j8 * j6);
        iArr3[i3 + 5] = (int) j14;
        long j15 = (j14 >>> 32) + (j8 * j7);
        iArr3[i3 + 6] = (int) j15;
        iArr3[i3 + 7] = (int) (j15 >>> 32);
        int i4 = 1;
        int i5 = i3;
        int i6 = 1;
        while (i6 < 7) {
            i5 += i4;
            long j16 = ((long) iArr[i + i6]) & 4294967295L;
            int i7 = i5 + 0;
            long j17 = (j16 * j) + (((long) iArr3[i7]) & 4294967295L) + 0;
            iArr3[i7] = (int) j17;
            int i8 = i5 + 1;
            long j18 = j;
            long j19 = (j17 >>> 32) + (j16 * j2) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j19;
            int i9 = i5 + 2;
            long j20 = (j19 >>> 32) + (j16 * j3) + (((long) iArr3[i9]) & 4294967295L);
            iArr3[i9] = (int) j20;
            int i10 = i5 + 3;
            long j21 = (j20 >>> 32) + (j16 * j4) + (((long) iArr3[i10]) & 4294967295L);
            iArr3[i10] = (int) j21;
            int i11 = i5 + 4;
            long j22 = (j21 >>> 32) + (j16 * j5) + (((long) iArr3[i11]) & 4294967295L);
            iArr3[i11] = (int) j22;
            int i12 = i5 + 5;
            long j23 = (j22 >>> 32) + (j16 * j6) + (((long) iArr3[i12]) & 4294967295L);
            iArr3[i12] = (int) j23;
            long j24 = j23 >>> 32;
            int i13 = i5 + 6;
            long j25 = j7;
            long j26 = j24 + (j16 * j7) + (((long) iArr3[i13]) & 4294967295L);
            iArr3[i13] = (int) j26;
            iArr3[i5 + 7] = (int) (j26 >>> 32);
            i6++;
            j = j18;
            j7 = j25;
            i4 = 1;
        }
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = ((long) iArr2[0]) & 4294967295L;
        int i = 1;
        long j2 = ((long) iArr2[1]) & 4294967295L;
        long j3 = ((long) iArr2[2]) & 4294967295L;
        long j4 = ((long) iArr2[3]) & 4294967295L;
        long j5 = ((long) iArr2[4]) & 4294967295L;
        long j6 = ((long) iArr2[5]) & 4294967295L;
        long j7 = ((long) iArr2[6]) & 4294967295L;
        long j8 = ((long) iArr[0]) & 4294967295L;
        long j9 = (j8 * j) + 0;
        iArr3[0] = (int) j9;
        long j10 = j2;
        long j11 = (j9 >>> 32) + (j8 * j2);
        iArr3[1] = (int) j11;
        long j12 = (j11 >>> 32) + (j8 * j3);
        iArr3[2] = (int) j12;
        long j13 = (j12 >>> 32) + (j8 * j4);
        iArr3[3] = (int) j13;
        long j14 = (j13 >>> 32) + (j8 * j5);
        iArr3[4] = (int) j14;
        long j15 = (j14 >>> 32) + (j8 * j6);
        iArr3[5] = (int) j15;
        long j16 = (j15 >>> 32) + (j8 * j7);
        iArr3[6] = (int) j16;
        iArr3[7] = (int) (j16 >>> 32);
        for (int i2 = 7; i < i2; i2 = 7) {
            long j17 = ((long) iArr[i]) & 4294967295L;
            int i3 = i + 0;
            long j18 = (j17 * j) + (((long) iArr3[i3]) & 4294967295L) + 0;
            iArr3[i3] = (int) j18;
            int i4 = i + 1;
            long j19 = (j18 >>> 32) + (j17 * j10) + (((long) iArr3[i4]) & 4294967295L);
            iArr3[i4] = (int) j19;
            int i5 = i + 2;
            long j20 = (j19 >>> 32) + (j17 * j3) + (((long) iArr3[i5]) & 4294967295L);
            iArr3[i5] = (int) j20;
            int i6 = i + 3;
            long j21 = (j20 >>> 32) + (j17 * j4) + (((long) iArr3[i6]) & 4294967295L);
            iArr3[i6] = (int) j21;
            int i7 = i + 4;
            long j22 = (j21 >>> 32) + (j17 * j5) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j22;
            int i8 = i + 5;
            long j23 = (j22 >>> 32) + (j17 * j6) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j23;
            int i9 = i + 6;
            long j24 = (j23 >>> 32) + (j17 * j7) + (((long) iArr3[i9]) & 4294967295L);
            iArr3[i9] = (int) j24;
            iArr3[i + 7] = (int) (j24 >>> 32);
            i = i4;
        }
    }

    public static long mul33Add(int i, int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j = ((long) i) & 4294967295L;
        long j2 = ((long) iArr[i2 + 0]) & 4294967295L;
        long j3 = (j * j2) + (((long) iArr2[i3 + 0]) & 4294967295L) + 0;
        iArr3[i4 + 0] = (int) j3;
        long j4 = ((long) iArr[i2 + 1]) & 4294967295L;
        long j5 = (j3 >>> 32) + (j * j4) + j2 + (((long) iArr2[i3 + 1]) & 4294967295L);
        iArr3[i4 + 1] = (int) j5;
        long j6 = ((long) iArr[i2 + 2]) & 4294967295L;
        long j7 = (j5 >>> 32) + (j * j6) + j4 + (((long) iArr2[i3 + 2]) & 4294967295L);
        iArr3[i4 + 2] = (int) j7;
        long j8 = ((long) iArr[i2 + 3]) & 4294967295L;
        long j9 = (j7 >>> 32) + (j * j8) + j6 + (((long) iArr2[i3 + 3]) & 4294967295L);
        iArr3[i4 + 3] = (int) j9;
        long j10 = ((long) iArr[i2 + 4]) & 4294967295L;
        long j11 = (j9 >>> 32) + (j * j10) + j8 + (((long) iArr2[i3 + 4]) & 4294967295L);
        iArr3[i4 + 4] = (int) j11;
        long j12 = ((long) iArr[i2 + 5]) & 4294967295L;
        long j13 = (j11 >>> 32) + (j * j12) + j10 + (((long) iArr2[i3 + 5]) & 4294967295L);
        iArr3[i4 + 5] = (int) j13;
        long j14 = j13 >>> 32;
        long j15 = ((long) iArr[i2 + 6]) & 4294967295L;
        long j16 = j14 + (j * j15) + j12 + (((long) iArr2[i3 + 6]) & 4294967295L);
        iArr3[i4 + 6] = (int) j16;
        return (j16 >>> 32) + j15;
    }

    public static int mul33DWordAdd(int i, long j, int[] iArr, int i2) {
        int[] iArr2 = iArr;
        int i3 = i2;
        long j2 = ((long) i) & 4294967295L;
        long j3 = j & 4294967295L;
        int i4 = i3 + 0;
        long j4 = (j2 * j3) + (((long) iArr2[i4]) & 4294967295L) + 0;
        iArr2[i4] = (int) j4;
        long j5 = j >>> 32;
        long j6 = (j2 * j5) + j3;
        int i5 = i3 + 1;
        long j7 = (j4 >>> 32) + j6 + (((long) iArr2[i5]) & 4294967295L);
        iArr2[i5] = (int) j7;
        int i6 = i3 + 2;
        long j8 = (j7 >>> 32) + j5 + (((long) iArr2[i6]) & 4294967295L);
        iArr2[i6] = (int) j8;
        int i7 = i3 + 3;
        long j9 = (j8 >>> 32) + (((long) iArr2[i7]) & 4294967295L);
        iArr2[i7] = (int) j9;
        if ((j9 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(7, iArr2, i3, 4);
    }

    public static int mul33WordAdd(int i, int i2, int[] iArr, int i3) {
        long j = ((long) i) & 4294967295L;
        long j2 = ((long) i2) & 4294967295L;
        int i4 = i3 + 0;
        long j3 = (j * j2) + (((long) iArr[i4]) & 4294967295L) + 0;
        iArr[i4] = (int) j3;
        int i5 = i3 + 1;
        long j4 = (j3 >>> 32) + j2 + (((long) iArr[i5]) & 4294967295L);
        iArr[i5] = (int) j4;
        int i6 = i3 + 2;
        long j5 = (j4 >>> 32) + (((long) iArr[i6]) & 4294967295L);
        iArr[i6] = (int) j5;
        if ((j5 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(7, iArr, i3, 3);
    }

    public static int mulAddTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = ((long) iArr2[i2 + 0]) & 4294967295L;
        long j2 = ((long) iArr2[i2 + 1]) & 4294967295L;
        long j3 = ((long) iArr2[i2 + 2]) & 4294967295L;
        long j4 = ((long) iArr2[i2 + 3]) & 4294967295L;
        long j5 = ((long) iArr2[i2 + 4]) & 4294967295L;
        long j6 = ((long) iArr2[i2 + 5]) & 4294967295L;
        long j7 = ((long) iArr2[i2 + 6]) & 4294967295L;
        int i4 = i3;
        int i5 = 0;
        long j8 = 0;
        while (i5 < 7) {
            long j9 = ((long) iArr[i + i5]) & 4294967295L;
            int i6 = i4 + 0;
            long j10 = j;
            long j11 = j7;
            long j12 = (j9 * j) + (((long) iArr3[i6]) & 4294967295L) + 0;
            iArr3[i6] = (int) j12;
            int i7 = i4 + 1;
            long j13 = j2;
            long j14 = (j12 >>> 32) + (j9 * j2) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j14;
            long j15 = j14 >>> 32;
            int i8 = i4 + 2;
            int i9 = i7;
            long j16 = j15 + (j9 * j3) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j16;
            int i10 = i4 + 3;
            long j17 = (j16 >>> 32) + (j9 * j4) + (((long) iArr3[i10]) & 4294967295L);
            iArr3[i10] = (int) j17;
            int i11 = i4 + 4;
            long j18 = (j17 >>> 32) + (j9 * j5) + (((long) iArr3[i11]) & 4294967295L);
            iArr3[i11] = (int) j18;
            int i12 = i4 + 5;
            long j19 = (j18 >>> 32) + (j9 * j6) + (((long) iArr3[i12]) & 4294967295L);
            iArr3[i12] = (int) j19;
            long j20 = j19 >>> 32;
            int i13 = i4 + 6;
            long j21 = j20 + (j9 * j11) + (((long) iArr3[i13]) & 4294967295L);
            iArr3[i13] = (int) j21;
            int i14 = i4 + 7;
            long j22 = (j21 >>> 32) + j8 + (((long) iArr3[i14]) & 4294967295L);
            iArr3[i14] = (int) j22;
            j8 = j22 >>> 32;
            i5++;
            j = j10;
            j7 = j11;
            j2 = j13;
            i4 = i9;
        }
        return (int) j8;
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        int i = 0;
        long j = 4294967295L;
        long j2 = ((long) iArr2[0]) & 4294967295L;
        long j3 = ((long) iArr2[1]) & 4294967295L;
        long j4 = ((long) iArr2[2]) & 4294967295L;
        long j5 = ((long) iArr2[3]) & 4294967295L;
        long j6 = ((long) iArr2[4]) & 4294967295L;
        long j7 = ((long) iArr2[5]) & 4294967295L;
        long j8 = ((long) iArr2[6]) & 4294967295L;
        long j9 = 0;
        while (i < 7) {
            long j10 = ((long) iArr[i]) & j;
            int i2 = i + 0;
            long j11 = j2;
            long j12 = (j10 * j2) + (((long) iArr3[i2]) & j) + 0;
            iArr3[i2] = (int) j12;
            int i3 = i + 1;
            long j13 = (j12 >>> 32) + (j10 * j3) + (((long) iArr3[i3]) & 4294967295L);
            iArr3[i3] = (int) j13;
            long j14 = j13 >>> 32;
            int i4 = i + 2;
            long j15 = j14 + (j10 * j4) + (((long) iArr3[i4]) & 4294967295L);
            iArr3[i4] = (int) j15;
            int i5 = i + 3;
            long j16 = (j15 >>> 32) + (j10 * j5) + (((long) iArr3[i5]) & 4294967295L);
            iArr3[i5] = (int) j16;
            int i6 = i + 4;
            long j17 = (j16 >>> 32) + (j10 * j6) + (((long) iArr3[i6]) & 4294967295L);
            iArr3[i6] = (int) j17;
            int i7 = i + 5;
            long j18 = (j17 >>> 32) + (j10 * j7) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j18;
            long j19 = j18 >>> 32;
            int i8 = i + 6;
            long j20 = j3;
            long j21 = j19 + (j10 * j8) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j21;
            int i9 = i + 7;
            long j22 = (j21 >>> 32) + j9 + (((long) iArr3[i9]) & 4294967295L);
            iArr3[i9] = (int) j22;
            j9 = j22 >>> 32;
            i = i3;
            j2 = j11;
            j = 4294967295L;
            j3 = j20;
        }
        return (int) j9;
    }

    public static int mulByWord(int i, int[] iArr) {
        long j = ((long) i) & 4294967295L;
        long j2 = ((((long) iArr[0]) & 4294967295L) * j) + 0;
        iArr[0] = (int) j2;
        long j3 = (j2 >>> 32) + ((((long) iArr[1]) & 4294967295L) * j);
        iArr[1] = (int) j3;
        long j4 = (j3 >>> 32) + ((((long) iArr[2]) & 4294967295L) * j);
        iArr[2] = (int) j4;
        long j5 = (j4 >>> 32) + ((((long) iArr[3]) & 4294967295L) * j);
        iArr[3] = (int) j5;
        long j6 = (j5 >>> 32) + ((((long) iArr[4]) & 4294967295L) * j);
        iArr[4] = (int) j6;
        long j7 = (j6 >>> 32) + ((((long) iArr[5]) & 4294967295L) * j);
        iArr[5] = (int) j7;
        long j8 = (j7 >>> 32) + (j * (((long) iArr[6]) & 4294967295L));
        iArr[6] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int mulByWordAddTo(int i, int[] iArr, int[] iArr2) {
        long j = ((long) i) & 4294967295L;
        long j2 = ((((long) iArr2[0]) & 4294967295L) * j) + (((long) iArr[0]) & 4294967295L) + 0;
        iArr2[0] = (int) j2;
        long j3 = (j2 >>> 32) + ((((long) iArr2[1]) & 4294967295L) * j) + (((long) iArr[1]) & 4294967295L);
        iArr2[1] = (int) j3;
        long j4 = (j3 >>> 32) + ((((long) iArr2[2]) & 4294967295L) * j) + (((long) iArr[2]) & 4294967295L);
        iArr2[2] = (int) j4;
        long j5 = (j4 >>> 32) + ((((long) iArr2[3]) & 4294967295L) * j) + (((long) iArr[3]) & 4294967295L);
        iArr2[3] = (int) j5;
        long j6 = (j5 >>> 32) + ((((long) iArr2[4]) & 4294967295L) * j) + (((long) iArr[4]) & 4294967295L);
        iArr2[4] = (int) j6;
        long j7 = (j6 >>> 32) + ((((long) iArr2[5]) & 4294967295L) * j) + (((long) iArr[5]) & 4294967295L);
        iArr2[5] = (int) j7;
        long j8 = (j7 >>> 32) + (j * (((long) iArr2[6]) & 4294967295L)) + (((long) iArr[6]) & 4294967295L);
        iArr2[6] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int mulWord(int i, int[] iArr, int[] iArr2, int i2) {
        long j = ((long) i) & 4294967295L;
        long j2 = 0;
        int i3 = 0;
        do {
            long j3 = j2 + ((((long) iArr[i3]) & 4294967295L) * j);
            iArr2[i2 + i3] = (int) j3;
            j2 = j3 >>> 32;
            i3++;
        } while (i3 < 7);
        return (int) j2;
    }

    public static int mulWordAddTo(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        long j = ((long) i) & 4294967295L;
        int i4 = i3 + 0;
        long j2 = ((((long) iArr[i2 + 0]) & 4294967295L) * j) + (((long) iArr2[i4]) & 4294967295L) + 0;
        iArr2[i4] = (int) j2;
        int i5 = i3 + 1;
        long j3 = (j2 >>> 32) + ((((long) iArr[i2 + 1]) & 4294967295L) * j) + (((long) iArr2[i5]) & 4294967295L);
        iArr2[i5] = (int) j3;
        int i6 = i3 + 2;
        long j4 = (j3 >>> 32) + ((((long) iArr[i2 + 2]) & 4294967295L) * j) + (((long) iArr2[i6]) & 4294967295L);
        iArr2[i6] = (int) j4;
        int i7 = i3 + 3;
        long j5 = (j4 >>> 32) + ((((long) iArr[i2 + 3]) & 4294967295L) * j) + (((long) iArr2[i7]) & 4294967295L);
        iArr2[i7] = (int) j5;
        int i8 = i3 + 4;
        long j6 = (j5 >>> 32) + ((((long) iArr[i2 + 4]) & 4294967295L) * j) + (((long) iArr2[i8]) & 4294967295L);
        iArr2[i8] = (int) j6;
        int i9 = i3 + 5;
        long j7 = (j6 >>> 32) + ((((long) iArr[i2 + 5]) & 4294967295L) * j) + (((long) iArr2[i9]) & 4294967295L);
        iArr2[i9] = (int) j7;
        int i10 = i3 + 6;
        long j8 = (j7 >>> 32) + (j * (((long) iArr[i2 + 6]) & 4294967295L)) + (((long) iArr2[i10]) & 4294967295L);
        iArr2[i10] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int mulWordDwordAdd(int i, long j, int[] iArr, int i2) {
        int[] iArr2 = iArr;
        int i3 = i2;
        long j2 = ((long) i) & 4294967295L;
        int i4 = i3 + 0;
        long j3 = ((j & 4294967295L) * j2) + (((long) iArr2[i4]) & 4294967295L) + 0;
        iArr2[i4] = (int) j3;
        int i5 = i3 + 1;
        long j4 = (j3 >>> 32) + (j2 * (j >>> 32)) + (((long) iArr2[i5]) & 4294967295L);
        iArr2[i5] = (int) j4;
        long j5 = j4 >>> 32;
        int i6 = i3 + 2;
        long j6 = j5 + (((long) iArr2[i6]) & 4294967295L);
        iArr2[i6] = (int) j6;
        if ((j6 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(7, iArr2, i3, 3);
    }

    public static void square(int[] iArr, int i, int[] iArr2, int i2) {
        long j = 4294967295L;
        long j2 = ((long) iArr[i + 0]) & 4294967295L;
        int i3 = 14;
        int i4 = 6;
        int i5 = 0;
        while (true) {
            int i6 = i4 - 1;
            long j3 = ((long) iArr[i + i4]) & j;
            long j4 = j3 * j3;
            int i7 = i3 - 1;
            iArr2[i2 + i7] = ((int) (j4 >>> 33)) | (i5 << 31);
            int i8 = i7 - 1;
            int i9 = i8;
            iArr2[i2 + i8] = (int) (j4 >>> 1);
            i5 = (int) j4;
            if (i6 <= 0) {
                long j5 = j2 * j2;
                long j6 = (((long) (i5 << 31)) & 4294967295L) | (j5 >>> 33);
                iArr2[i2 + 0] = (int) j5;
                long j7 = ((long) iArr[i + 1]) & 4294967295L;
                int i10 = i2 + 2;
                long j8 = ((long) iArr2[i10]) & 4294967295L;
                long j9 = j6 + (j7 * j2);
                int i11 = (int) j9;
                iArr2[i2 + 1] = (((int) (j5 >>> 32)) & 1) | (i11 << 1);
                long j10 = j8 + (j9 >>> 32);
                long j11 = ((long) iArr[i + 2]) & 4294967295L;
                int i12 = i2 + 3;
                long j12 = ((long) iArr2[i12]) & 4294967295L;
                int i13 = i2 + 4;
                long j13 = ((long) iArr2[i13]) & 4294967295L;
                int i14 = i13;
                long j14 = j10 + (j11 * j2);
                int i15 = (int) j14;
                iArr2[i10] = (i11 >>> 31) | (i15 << 1);
                long j15 = j12 + (j14 >>> 32) + (j11 * j7);
                long j16 = j13 + (j15 >>> 32);
                long j17 = j15 & 4294967295L;
                long j18 = ((long) iArr[i + 3]) & 4294967295L;
                int i16 = i2 + 5;
                int i17 = i16;
                long j19 = ((long) iArr2[i16]) & 4294967295L;
                int i18 = i2 + 6;
                int i19 = i18;
                long j20 = ((long) iArr2[i18]) & 4294967295L;
                long j21 = j17 + (j18 * j2);
                int i20 = (int) j21;
                iArr2[i12] = (i15 >>> 31) | (i20 << 1);
                int i21 = i20 >>> 31;
                long j22 = j16 + (j21 >>> 32) + (j18 * j7);
                long j23 = j19 + (j22 >>> 32) + (j18 * j11);
                long j24 = j20 + (j23 >>> 32);
                long j25 = j23 & 4294967295L;
                long j26 = ((long) iArr[i + 4]) & 4294967295L;
                int i22 = i2 + 7;
                long j27 = ((long) iArr2[i22]) & 4294967295L;
                int i23 = i2 + 8;
                int i24 = i23;
                long j28 = ((long) iArr2[i23]) & 4294967295L;
                long j29 = (j22 & 4294967295L) + (j26 * j2);
                int i25 = (int) j29;
                iArr2[i14] = i21 | (i25 << 1);
                int i26 = i25 >>> 31;
                long j30 = j25 + (j29 >>> 32) + (j26 * j7);
                long j31 = j24 + (j30 >>> 32) + (j26 * j11);
                long j32 = j30 & 4294967295L;
                long j33 = j27 + (j31 >>> 32) + (j26 * j18);
                long j34 = j31 & 4294967295L;
                long j35 = j28 + (j33 >>> 32);
                long j36 = j33 & 4294967295L;
                long j37 = ((long) iArr[i + 5]) & 4294967295L;
                int i27 = i2 + 9;
                long j38 = ((long) iArr2[i27]) & 4294967295L;
                int i28 = i2 + 10;
                int i29 = i28;
                long j39 = ((long) iArr2[i28]) & 4294967295L;
                long j40 = j32 + (j37 * j2);
                int i30 = (int) j40;
                iArr2[i17] = i26 | (i30 << 1);
                int i31 = i30 >>> 31;
                long j41 = j34 + (j40 >>> 32) + (j37 * j7);
                long j42 = j36 + (j41 >>> 32) + (j37 * j11);
                long j43 = j41 & 4294967295L;
                long j44 = j35 + (j42 >>> 32) + (j37 * j18);
                long j45 = j42 & 4294967295L;
                long j46 = j38 + (j44 >>> 32) + (j37 * j26);
                long j47 = j44 & 4294967295L;
                long j48 = j39 + (j46 >>> 32);
                long j49 = j46 & 4294967295L;
                long j50 = ((long) iArr[i + 6]) & 4294967295L;
                int i32 = i2 + 11;
                int i33 = i32;
                long j51 = ((long) iArr2[i32]) & 4294967295L;
                int i34 = i2 + 12;
                int i35 = i34;
                long j52 = ((long) iArr2[i34]) & 4294967295L;
                long j53 = j43 + (j2 * j50);
                int i36 = (int) j53;
                iArr2[i19] = i31 | (i36 << 1);
                int i37 = i36 >>> 31;
                long j54 = j45 + (j53 >>> 32) + (j7 * j50);
                long j55 = j47 + (j54 >>> 32) + (j11 * j50);
                long j56 = j55;
                long j57 = j49 + (j55 >>> 32) + (j18 * j50);
                long j58 = j48 + (j57 >>> 32) + (j26 * j50);
                long j59 = j51 + (j58 >>> 32) + (j50 * j37);
                long j60 = j52 + (j59 >>> 32);
                int i38 = (int) j54;
                iArr2[i22] = (i38 << 1) | i37;
                int i39 = (int) j56;
                iArr2[i24] = (i38 >>> 31) | (i39 << 1);
                int i40 = i39 >>> 31;
                int i41 = (int) j57;
                iArr2[i27] = i40 | (i41 << 1);
                int i42 = i41 >>> 31;
                int i43 = (int) j58;
                iArr2[i29] = i42 | (i43 << 1);
                int i44 = i43 >>> 31;
                int i45 = (int) j59;
                iArr2[i33] = i44 | (i45 << 1);
                int i46 = i45 >>> 31;
                int i47 = (int) j60;
                iArr2[i35] = i46 | (i47 << 1);
                int i48 = i47 >>> 31;
                int i49 = i2 + 13;
                iArr2[i49] = i48 | ((iArr2[i49] + ((int) (j60 >> 32))) << 1);
                return;
            }
            i4 = i6;
            i3 = i9;
            j = 4294967295L;
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j = 4294967295L;
        long j2 = ((long) iArr[0]) & 4294967295L;
        int i = 6;
        int i2 = 14;
        int i3 = 0;
        while (true) {
            int i4 = i - 1;
            long j3 = ((long) iArr[i]) & j;
            long j4 = j3 * j3;
            int i5 = i2 - 1;
            iArr2[i5] = (i3 << 31) | ((int) (j4 >>> 33));
            i2 = i5 - 1;
            iArr2[i2] = (int) (j4 >>> 1);
            int i6 = (int) j4;
            if (i4 <= 0) {
                long j5 = j2 * j2;
                long j6 = (((long) (i6 << 31)) & j) | (j5 >>> 33);
                iArr2[0] = (int) j5;
                long j7 = ((long) iArr[1]) & j;
                long j8 = ((long) iArr2[2]) & j;
                long j9 = j6 + (j7 * j2);
                int i7 = (int) j9;
                iArr2[1] = (((int) (j5 >>> 32)) & 1) | (i7 << 1);
                int i8 = i7 >>> 31;
                long j10 = j8 + (j9 >>> 32);
                long j11 = ((long) iArr[2]) & 4294967295L;
                long j12 = ((long) iArr2[3]) & 4294967295L;
                long j13 = ((long) iArr2[4]) & 4294967295L;
                long j14 = j10 + (j11 * j2);
                int i9 = (int) j14;
                iArr2[2] = (i9 << 1) | i8;
                long j15 = j12 + (j14 >>> 32) + (j11 * j7);
                long j16 = j13 + (j15 >>> 32);
                long j17 = ((long) iArr[3]) & 4294967295L;
                long j18 = ((long) iArr2[5]) & 4294967295L;
                long j19 = ((long) iArr2[6]) & 4294967295L;
                long j20 = (j15 & 4294967295L) + (j17 * j2);
                int i10 = (int) j20;
                iArr2[3] = (i9 >>> 31) | (i10 << 1);
                long j21 = j16 + (j20 >>> 32) + (j17 * j7);
                long j22 = j18 + (j21 >>> 32) + (j17 * j11);
                long j23 = j21 & 4294967295L;
                long j24 = j19 + (j22 >>> 32);
                long j25 = j22 & 4294967295L;
                long j26 = ((long) iArr[4]) & 4294967295L;
                long j27 = ((long) iArr2[7]) & 4294967295L;
                long j28 = ((long) iArr2[8]) & 4294967295L;
                long j29 = j23 + (j26 * j2);
                int i11 = (int) j29;
                iArr2[4] = (i10 >>> 31) | (i11 << 1);
                int i12 = i11 >>> 31;
                long j30 = j25 + (j29 >>> 32) + (j26 * j7);
                long j31 = j24 + (j30 >>> 32) + (j26 * j11);
                long j32 = j30 & 4294967295L;
                long j33 = j27 + (j31 >>> 32) + (j26 * j17);
                long j34 = j31 & 4294967295L;
                long j35 = j28 + (j33 >>> 32);
                long j36 = j33 & 4294967295L;
                long j37 = ((long) iArr[5]) & 4294967295L;
                long j38 = ((long) iArr2[9]) & 4294967295L;
                long j39 = ((long) iArr2[10]) & 4294967295L;
                long j40 = j32 + (j37 * j2);
                int i13 = (int) j40;
                iArr2[5] = i12 | (i13 << 1);
                int i14 = i13 >>> 31;
                long j41 = j34 + (j40 >>> 32) + (j37 * j7);
                long j42 = j36 + (j41 >>> 32) + (j37 * j11);
                long j43 = j41 & 4294967295L;
                long j44 = j35 + (j42 >>> 32) + (j37 * j17);
                long j45 = j42 & 4294967295L;
                long j46 = j38 + (j44 >>> 32) + (j37 * j26);
                long j47 = j44 & 4294967295L;
                long j48 = j39 + (j46 >>> 32);
                long j49 = j46 & 4294967295L;
                long j50 = ((long) iArr[6]) & 4294967295L;
                long j51 = ((long) iArr2[11]) & 4294967295L;
                long j52 = ((long) iArr2[12]) & 4294967295L;
                long j53 = j43 + (j2 * j50);
                int i15 = (int) j53;
                iArr2[6] = i14 | (i15 << 1);
                long j54 = j45 + (j53 >>> 32) + (j7 * j50);
                long j55 = j47 + (j54 >>> 32) + (j11 * j50);
                long j56 = j49 + (j55 >>> 32) + (j17 * j50);
                long j57 = j48 + (j56 >>> 32) + (j26 * j50);
                long j58 = j51 + (j57 >>> 32) + (j50 * j37);
                long j59 = j58;
                long j60 = j52 + (j58 >>> 32);
                int i16 = (int) j54;
                iArr2[7] = (i15 >>> 31) | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) j55;
                iArr2[8] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) j56;
                iArr2[9] = i19 | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) j57;
                iArr2[10] = i21 | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) j59;
                iArr2[11] = i23 | (i24 << 1);
                int i25 = i24 >>> 31;
                int i26 = (int) j60;
                iArr2[12] = i25 | (i26 << 1);
                iArr2[13] = (i26 >>> 31) | ((iArr2[13] + ((int) (j60 >> 32))) << 1);
                return;
            }
            j = j;
            i = i4;
            i3 = i6;
        }
    }

    public static int sub(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = ((((long) iArr[i + 0]) & 4294967295L) - (((long) iArr2[i2 + 0]) & 4294967295L)) + 0;
        iArr3[i3 + 0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr[i + 1]) & 4294967295L) - (((long) iArr2[i2 + 1]) & 4294967295L));
        iArr3[i3 + 1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr[i + 2]) & 4294967295L) - (((long) iArr2[i2 + 2]) & 4294967295L));
        iArr3[i3 + 2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr[i + 3]) & 4294967295L) - (((long) iArr2[i2 + 3]) & 4294967295L));
        iArr3[i3 + 3] = (int) j4;
        long j5 = (j4 >> 32) + ((((long) iArr[i + 4]) & 4294967295L) - (((long) iArr2[i2 + 4]) & 4294967295L));
        iArr3[i3 + 4] = (int) j5;
        long j6 = (j5 >> 32) + ((((long) iArr[i + 5]) & 4294967295L) - (((long) iArr2[i2 + 5]) & 4294967295L));
        iArr3[i3 + 5] = (int) j6;
        long j7 = (j6 >> 32) + ((((long) iArr[i + 6]) & 4294967295L) - (((long) iArr2[i2 + 6]) & 4294967295L));
        iArr3[i3 + 6] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = ((((long) iArr[0]) & 4294967295L) - (((long) iArr2[0]) & 4294967295L)) + 0;
        iArr3[0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr[1]) & 4294967295L) - (((long) iArr2[1]) & 4294967295L));
        iArr3[1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr[2]) & 4294967295L) - (((long) iArr2[2]) & 4294967295L));
        iArr3[2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr[3]) & 4294967295L) - (((long) iArr2[3]) & 4294967295L));
        iArr3[3] = (int) j4;
        long j5 = (j4 >> 32) + ((((long) iArr[4]) & 4294967295L) - (((long) iArr2[4]) & 4294967295L));
        iArr3[4] = (int) j5;
        long j6 = (j5 >> 32) + ((((long) iArr[5]) & 4294967295L) - (((long) iArr2[5]) & 4294967295L));
        iArr3[5] = (int) j6;
        long j7 = (j6 >> 32) + ((((long) iArr[6]) & 4294967295L) - (((long) iArr2[6]) & 4294967295L));
        iArr3[6] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (((((long) iArr3[0]) & 4294967295L) - (((long) iArr[0]) & 4294967295L)) - (((long) iArr2[0]) & 4294967295L)) + 0;
        iArr3[0] = (int) j;
        long j2 = (j >> 32) + (((((long) iArr3[1]) & 4294967295L) - (((long) iArr[1]) & 4294967295L)) - (((long) iArr2[1]) & 4294967295L));
        iArr3[1] = (int) j2;
        long j3 = (j2 >> 32) + (((((long) iArr3[2]) & 4294967295L) - (((long) iArr[2]) & 4294967295L)) - (((long) iArr2[2]) & 4294967295L));
        iArr3[2] = (int) j3;
        long j4 = (j3 >> 32) + (((((long) iArr3[3]) & 4294967295L) - (((long) iArr[3]) & 4294967295L)) - (((long) iArr2[3]) & 4294967295L));
        iArr3[3] = (int) j4;
        long j5 = (j4 >> 32) + (((((long) iArr3[4]) & 4294967295L) - (((long) iArr[4]) & 4294967295L)) - (((long) iArr2[4]) & 4294967295L));
        iArr3[4] = (int) j5;
        long j6 = (j5 >> 32) + (((((long) iArr3[5]) & 4294967295L) - (((long) iArr[5]) & 4294967295L)) - (((long) iArr2[5]) & 4294967295L));
        iArr3[5] = (int) j6;
        long j7 = (j6 >> 32) + (((((long) iArr3[6]) & 4294967295L) - (((long) iArr[6]) & 4294967295L)) - (((long) iArr2[6]) & 4294967295L));
        iArr3[6] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subFrom(int[] iArr, int i, int[] iArr2, int i2) {
        int i3 = i2 + 0;
        long j = ((((long) iArr2[i3]) & 4294967295L) - (((long) iArr[i + 0]) & 4294967295L)) + 0;
        iArr2[i3] = (int) j;
        int i4 = i2 + 1;
        long j2 = (j >> 32) + ((((long) iArr2[i4]) & 4294967295L) - (((long) iArr[i + 1]) & 4294967295L));
        iArr2[i4] = (int) j2;
        int i5 = i2 + 2;
        long j3 = (j2 >> 32) + ((((long) iArr2[i5]) & 4294967295L) - (((long) iArr[i + 2]) & 4294967295L));
        iArr2[i5] = (int) j3;
        int i6 = i2 + 3;
        long j4 = (j3 >> 32) + ((((long) iArr2[i6]) & 4294967295L) - (((long) iArr[i + 3]) & 4294967295L));
        iArr2[i6] = (int) j4;
        int i7 = i2 + 4;
        long j5 = (j4 >> 32) + ((((long) iArr2[i7]) & 4294967295L) - (((long) iArr[i + 4]) & 4294967295L));
        iArr2[i7] = (int) j5;
        int i8 = i2 + 5;
        long j6 = (j5 >> 32) + ((((long) iArr2[i8]) & 4294967295L) - (((long) iArr[i + 5]) & 4294967295L));
        iArr2[i8] = (int) j6;
        int i9 = i2 + 6;
        long j7 = (j6 >> 32) + ((((long) iArr2[i9]) & 4294967295L) - (((long) iArr[i + 6]) & 4294967295L));
        iArr2[i9] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j = ((((long) iArr2[0]) & 4294967295L) - (((long) iArr[0]) & 4294967295L)) + 0;
        iArr2[0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr2[1]) & 4294967295L) - (((long) iArr[1]) & 4294967295L));
        iArr2[1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr2[2]) & 4294967295L) - (((long) iArr[2]) & 4294967295L));
        iArr2[2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr2[3]) & 4294967295L) - (((long) iArr[3]) & 4294967295L));
        iArr2[3] = (int) j4;
        long j5 = (j4 >> 32) + ((((long) iArr2[4]) & 4294967295L) - (((long) iArr[4]) & 4294967295L));
        iArr2[4] = (int) j5;
        long j6 = (j5 >> 32) + ((((long) iArr2[5]) & 4294967295L) - (((long) iArr[5]) & 4294967295L));
        iArr2[5] = (int) j6;
        long j7 = (j6 >> 32) + ((((long) iArr2[6]) & 4294967295L) - (((long) iArr[6]) & 4294967295L));
        iArr2[6] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[28];
        for (int i = 0; i < 7; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                Pack.intToBigEndian(i2, bArr, (6 - i) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static void zero(int[] iArr) {
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        iArr[4] = 0;
        iArr[5] = 0;
        iArr[6] = 0;
    }
}
