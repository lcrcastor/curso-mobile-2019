package org.bouncycastle.math.raw;

import java.math.BigInteger;
import org.bouncycastle.util.Pack;

public abstract class Nat192 {
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
        return (int) (j6 >>> 32);
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
        return (int) (j6 >>> 32);
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
        return (int) (j6 >>> 32);
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
        return (int) (j6 >>> 32);
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
        return (int) (j6 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
        iArr2[5] = iArr[5];
    }

    public static int[] create() {
        return new int[6];
    }

    public static int[] createExt() {
        return new int[12];
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
        for (int i = 5; i >= 0; i--) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 192) {
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
            if (i3 < 0 || i3 >= 6) {
                return 0;
            }
            i2 = iArr[i3] >>> (i & 31);
        }
        return i2 & 1;
    }

    public static boolean gte(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 5; i3 >= 0; i3--) {
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
        for (int i = 5; i >= 0; i--) {
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
        for (int i = 1; i < 6; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i = 0; i < 6; i++) {
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
        long j7 = ((long) iArr[i + 0]) & 4294967295L;
        long j8 = (j7 * j) + 0;
        iArr3[i3 + 0] = (int) j8;
        long j9 = (j8 >>> 32) + (j7 * j2);
        iArr3[i3 + 1] = (int) j9;
        long j10 = (j9 >>> 32) + (j7 * j3);
        iArr3[i3 + 2] = (int) j10;
        long j11 = (j10 >>> 32) + (j7 * j4);
        iArr3[i3 + 3] = (int) j11;
        long j12 = (j11 >>> 32) + (j7 * j5);
        iArr3[i3 + 4] = (int) j12;
        long j13 = (j12 >>> 32) + (j7 * j6);
        iArr3[i3 + 5] = (int) j13;
        iArr3[i3 + 6] = (int) (j13 >>> 32);
        int i4 = 1;
        int i5 = i3;
        int i6 = 1;
        while (i6 < 6) {
            i5 += i4;
            long j14 = ((long) iArr[i + i6]) & 4294967295L;
            int i7 = i5 + 0;
            long j15 = (j14 * j) + (((long) iArr3[i7]) & 4294967295L) + 0;
            iArr3[i7] = (int) j15;
            int i8 = i5 + 1;
            long j16 = j;
            long j17 = (j15 >>> 32) + (j14 * j2) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j17;
            int i9 = i5 + 2;
            long j18 = (j17 >>> 32) + (j14 * j3) + (((long) iArr3[i9]) & 4294967295L);
            iArr3[i9] = (int) j18;
            int i10 = i5 + 3;
            long j19 = (j18 >>> 32) + (j14 * j4) + (((long) iArr3[i10]) & 4294967295L);
            iArr3[i10] = (int) j19;
            int i11 = i5 + 4;
            long j20 = (j19 >>> 32) + (j14 * j5) + (((long) iArr3[i11]) & 4294967295L);
            iArr3[i11] = (int) j20;
            long j21 = j20 >>> 32;
            int i12 = i5 + 5;
            long j22 = j6;
            long j23 = j21 + (j14 * j6) + (((long) iArr3[i12]) & 4294967295L);
            iArr3[i12] = (int) j23;
            iArr3[i5 + 6] = (int) (j23 >>> 32);
            i6++;
            j = j16;
            j6 = j22;
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
        long j7 = ((long) iArr[0]) & 4294967295L;
        long j8 = (j7 * j) + 0;
        iArr3[0] = (int) j8;
        long j9 = j2;
        long j10 = (j8 >>> 32) + (j7 * j2);
        iArr3[1] = (int) j10;
        long j11 = (j10 >>> 32) + (j7 * j3);
        iArr3[2] = (int) j11;
        long j12 = (j11 >>> 32) + (j7 * j4);
        iArr3[3] = (int) j12;
        long j13 = (j12 >>> 32) + (j7 * j5);
        iArr3[4] = (int) j13;
        long j14 = (j13 >>> 32) + (j7 * j6);
        iArr3[5] = (int) j14;
        iArr3[6] = (int) (j14 >>> 32);
        for (int i2 = 6; i < i2; i2 = 6) {
            long j15 = ((long) iArr[i]) & 4294967295L;
            int i3 = i + 0;
            long j16 = (j15 * j) + (((long) iArr3[i3]) & 4294967295L) + 0;
            iArr3[i3] = (int) j16;
            int i4 = i + 1;
            long j17 = (j16 >>> 32) + (j15 * j9) + (((long) iArr3[i4]) & 4294967295L);
            iArr3[i4] = (int) j17;
            int i5 = i + 2;
            long j18 = (j17 >>> 32) + (j15 * j3) + (((long) iArr3[i5]) & 4294967295L);
            iArr3[i5] = (int) j18;
            int i6 = i + 3;
            long j19 = (j18 >>> 32) + (j15 * j4) + (((long) iArr3[i6]) & 4294967295L);
            iArr3[i6] = (int) j19;
            int i7 = i + 4;
            long j20 = (j19 >>> 32) + (j15 * j5) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j20;
            int i8 = i + 5;
            long j21 = (j20 >>> 32) + (j15 * j6) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j21;
            iArr3[i + 6] = (int) (j21 >>> 32);
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
        long j12 = j11 >>> 32;
        long j13 = ((long) iArr[i2 + 5]) & 4294967295L;
        long j14 = j12 + (j * j13) + j10 + (((long) iArr2[i3 + 5]) & 4294967295L);
        iArr3[i4 + 5] = (int) j14;
        return (j14 >>> 32) + j13;
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
        return Nat.incAt(6, iArr2, i3, 4);
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
        return Nat.incAt(6, iArr, i3, 3);
    }

    public static int mulAddTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = ((long) iArr2[i2 + 0]) & 4294967295L;
        long j2 = ((long) iArr2[i2 + 1]) & 4294967295L;
        long j3 = ((long) iArr2[i2 + 2]) & 4294967295L;
        long j4 = ((long) iArr2[i2 + 3]) & 4294967295L;
        long j5 = ((long) iArr2[i2 + 4]) & 4294967295L;
        long j6 = ((long) iArr2[i2 + 5]) & 4294967295L;
        int i4 = i3;
        int i5 = 0;
        long j7 = 0;
        while (i5 < 6) {
            long j8 = ((long) iArr[i + i5]) & 4294967295L;
            int i6 = i4 + 0;
            long j9 = j;
            long j10 = j6;
            long j11 = (j8 * j) + (((long) iArr3[i6]) & 4294967295L) + 0;
            iArr3[i6] = (int) j11;
            int i7 = i4 + 1;
            long j12 = j2;
            long j13 = (j11 >>> 32) + (j8 * j2) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j13;
            long j14 = j13 >>> 32;
            int i8 = i4 + 2;
            int i9 = i7;
            long j15 = j14 + (j8 * j3) + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j15;
            int i10 = i4 + 3;
            long j16 = (j15 >>> 32) + (j8 * j4) + (((long) iArr3[i10]) & 4294967295L);
            iArr3[i10] = (int) j16;
            int i11 = i4 + 4;
            long j17 = (j16 >>> 32) + (j8 * j5) + (((long) iArr3[i11]) & 4294967295L);
            iArr3[i11] = (int) j17;
            long j18 = j17 >>> 32;
            int i12 = i4 + 5;
            long j19 = j18 + (j8 * j10) + (((long) iArr3[i12]) & 4294967295L);
            iArr3[i12] = (int) j19;
            int i13 = i4 + 6;
            long j20 = (j19 >>> 32) + j7 + (((long) iArr3[i13]) & 4294967295L);
            iArr3[i13] = (int) j20;
            j7 = j20 >>> 32;
            i5++;
            j = j9;
            j6 = j10;
            j2 = j12;
            i4 = i9;
        }
        return (int) j7;
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
        long j8 = 0;
        while (i < 6) {
            long j9 = ((long) iArr[i]) & j;
            int i2 = i + 0;
            long j10 = j2;
            long j11 = (j9 * j2) + (((long) iArr3[i2]) & j) + 0;
            iArr3[i2] = (int) j11;
            int i3 = i + 1;
            long j12 = (j11 >>> 32) + (j9 * j3) + (((long) iArr3[i3]) & 4294967295L);
            iArr3[i3] = (int) j12;
            long j13 = j12 >>> 32;
            int i4 = i + 2;
            long j14 = j13 + (j9 * j4) + (((long) iArr3[i4]) & 4294967295L);
            iArr3[i4] = (int) j14;
            int i5 = i + 3;
            long j15 = (j14 >>> 32) + (j9 * j5) + (((long) iArr3[i5]) & 4294967295L);
            iArr3[i5] = (int) j15;
            int i6 = i + 4;
            long j16 = (j15 >>> 32) + (j9 * j6) + (((long) iArr3[i6]) & 4294967295L);
            iArr3[i6] = (int) j16;
            long j17 = j16 >>> 32;
            int i7 = i + 5;
            long j18 = j3;
            long j19 = j17 + (j9 * j7) + (((long) iArr3[i7]) & 4294967295L);
            iArr3[i7] = (int) j19;
            int i8 = i + 6;
            long j20 = (j19 >>> 32) + j8 + (((long) iArr3[i8]) & 4294967295L);
            iArr3[i8] = (int) j20;
            j8 = j20 >>> 32;
            i = i3;
            j2 = j10;
            j = 4294967295L;
            j3 = j18;
        }
        return (int) j8;
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
        } while (i3 < 6);
        return (int) j2;
    }

    public static int mulWordAddExt(int i, int[] iArr, int i2, int[] iArr2, int i3) {
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
        long j7 = (j6 >>> 32) + (j * (((long) iArr[i2 + 5]) & 4294967295L)) + (((long) iArr2[i9]) & 4294967295L);
        iArr2[i9] = (int) j7;
        return (int) (j7 >>> 32);
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
        return Nat.incAt(6, iArr2, i3, 3);
    }

    public static void square(int[] iArr, int i, int[] iArr2, int i2) {
        long j = 4294967295L;
        long j2 = ((long) iArr[i + 0]) & 4294967295L;
        int i3 = 12;
        int i4 = 5;
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
                int i28 = i27;
                long j38 = ((long) iArr2[i27]) & 4294967295L;
                int i29 = i2 + 10;
                int i30 = i29;
                long j39 = ((long) iArr2[i29]) & 4294967295L;
                long j40 = j32 + (j2 * j37);
                int i31 = (int) j40;
                iArr2[i17] = i26 | (i31 << 1);
                int i32 = i31 >>> 31;
                long j41 = j34 + (j40 >>> 32) + (j7 * j37);
                long j42 = j36 + (j41 >>> 32) + (j11 * j37);
                long j43 = j35 + (j42 >>> 32) + (j18 * j37);
                long j44 = j38 + (j43 >>> 32) + (j37 * j26);
                long j45 = j44;
                long j46 = j39 + (j44 >>> 32);
                int i33 = (int) j41;
                iArr2[i19] = (i33 << 1) | i32;
                int i34 = (int) j42;
                iArr2[i22] = (i33 >>> 31) | (i34 << 1);
                int i35 = i34 >>> 31;
                int i36 = (int) j43;
                iArr2[i24] = i35 | (i36 << 1);
                int i37 = i36 >>> 31;
                int i38 = (int) j45;
                iArr2[i28] = i37 | (i38 << 1);
                int i39 = i38 >>> 31;
                int i40 = (int) j46;
                iArr2[i30] = i39 | (i40 << 1);
                int i41 = i40 >>> 31;
                int i42 = i2 + 11;
                iArr2[i42] = i41 | ((iArr2[i42] + ((int) (j46 >> 32))) << 1);
                return;
            }
            i4 = i6;
            i3 = i9;
            j = 4294967295L;
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j = ((long) iArr[0]) & 4294967295L;
        int i = 5;
        int i2 = 12;
        int i3 = 0;
        while (true) {
            int i4 = i - 1;
            long j2 = ((long) iArr[i]) & 4294967295L;
            long j3 = j2 * j2;
            int i5 = i2 - 1;
            iArr2[i5] = (i3 << 31) | ((int) (j3 >>> 33));
            i2 = i5 - 1;
            iArr2[i2] = (int) (j3 >>> 1);
            int i6 = (int) j3;
            if (i4 <= 0) {
                long j4 = j * j;
                long j5 = (((long) (i6 << 31)) & 4294967295L) | (j4 >>> 33);
                iArr2[0] = (int) j4;
                long j6 = ((long) iArr[1]) & 4294967295L;
                long j7 = ((long) iArr2[2]) & 4294967295L;
                long j8 = j5 + (j6 * j);
                int i7 = (int) j8;
                iArr2[1] = (((int) (j4 >>> 32)) & 1) | (i7 << 1);
                int i8 = i7 >>> 31;
                long j9 = j7 + (j8 >>> 32);
                long j10 = ((long) iArr[2]) & 4294967295L;
                long j11 = ((long) iArr2[3]) & 4294967295L;
                long j12 = ((long) iArr2[4]) & 4294967295L;
                long j13 = j9 + (j10 * j);
                int i9 = (int) j13;
                iArr2[2] = (i9 << 1) | i8;
                long j14 = j11 + (j13 >>> 32) + (j10 * j6);
                long j15 = j12 + (j14 >>> 32);
                long j16 = ((long) iArr[3]) & 4294967295L;
                long j17 = ((long) iArr2[5]) & 4294967295L;
                long j18 = ((long) iArr2[6]) & 4294967295L;
                long j19 = (j14 & 4294967295L) + (j16 * j);
                int i10 = (int) j19;
                iArr2[3] = (i9 >>> 31) | (i10 << 1);
                long j20 = j15 + (j19 >>> 32) + (j16 * j6);
                long j21 = j17 + (j20 >>> 32) + (j16 * j10);
                long j22 = j20 & 4294967295L;
                long j23 = j18 + (j21 >>> 32);
                long j24 = j21 & 4294967295L;
                long j25 = ((long) iArr[4]) & 4294967295L;
                long j26 = ((long) iArr2[7]) & 4294967295L;
                long j27 = ((long) iArr2[8]) & 4294967295L;
                long j28 = j22 + (j25 * j);
                int i11 = (int) j28;
                iArr2[4] = (i10 >>> 31) | (i11 << 1);
                int i12 = i11 >>> 31;
                long j29 = j24 + (j28 >>> 32) + (j25 * j6);
                long j30 = j23 + (j29 >>> 32) + (j25 * j10);
                long j31 = j29 & 4294967295L;
                long j32 = j26 + (j30 >>> 32) + (j25 * j16);
                long j33 = j30 & 4294967295L;
                long j34 = j27 + (j32 >>> 32);
                long j35 = j32 & 4294967295L;
                long j36 = ((long) iArr[5]) & 4294967295L;
                long j37 = ((long) iArr2[9]) & 4294967295L;
                long j38 = ((long) iArr2[10]) & 4294967295L;
                long j39 = j31 + (j * j36);
                int i13 = (int) j39;
                iArr2[5] = i12 | (i13 << 1);
                int i14 = i13 >>> 31;
                long j40 = j33 + (j39 >>> 32) + (j6 * j36);
                long j41 = j35 + (j40 >>> 32) + (j10 * j36);
                long j42 = j34 + (j41 >>> 32) + (j16 * j36);
                long j43 = j37 + (j42 >>> 32) + (j36 * j25);
                int i15 = (int) j40;
                long j44 = j38 + (j43 >>> 32);
                iArr2[6] = i14 | (i15 << 1);
                int i16 = (int) j41;
                iArr2[7] = (i15 >>> 31) | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) j42;
                iArr2[8] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) j43;
                iArr2[9] = i19 | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) j44;
                iArr2[10] = i21 | (i22 << 1);
                iArr2[11] = (i22 >>> 31) | ((iArr2[11] + ((int) (j44 >> 32))) << 1);
                return;
            }
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
        return (int) (j6 >> 32);
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
        return (int) (j6 >> 32);
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
        return (int) (j6 >> 32);
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
        return (int) (j6 >> 32);
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
        return (int) (j6 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[24];
        for (int i = 0; i < 6; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                Pack.intToBigEndian(i2, bArr, (5 - i) << 2);
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
    }
}
