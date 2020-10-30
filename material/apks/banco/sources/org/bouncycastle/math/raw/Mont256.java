package org.bouncycastle.math.raw;

public abstract class Mont256 {
    public static int inverse32(int i) {
        int i2 = (2 - (i * i)) * i;
        int i3 = i2 * (2 - (i * i2));
        int i4 = i3 * (2 - (i * i3));
        return i4 * (2 - (i * i4));
    }

    public static void multAdd(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) {
        int[] iArr5 = iArr3;
        int[] iArr6 = iArr4;
        char c = 0;
        long j = ((long) iArr2[0]) & 4294967295L;
        int i2 = 0;
        int i3 = 0;
        while (i2 < 8) {
            long j2 = ((long) iArr[i2]) & 4294967295L;
            long j3 = j2 * j;
            int i4 = i2;
            long j4 = (j3 & 4294967295L) + (((long) iArr5[c]) & 4294967295L);
            long j5 = j;
            long j6 = ((long) (((int) j4) * i)) & 4294967295L;
            long j7 = (((long) iArr6[0]) & 4294967295L) * j6;
            long j8 = ((j4 + (j7 & 4294967295L)) >>> 32) + (j3 >>> 32) + (j7 >>> 32);
            int i5 = 1;
            while (i5 < 8) {
                long j9 = (((long) iArr2[i5]) & 4294967295L) * j2;
                long j10 = (((long) iArr6[i5]) & 4294967295L) * j6;
                long j11 = j8 + (j9 & 4294967295L) + (j10 & 4294967295L) + (((long) iArr5[i5]) & 4294967295L);
                iArr5[i5 - 1] = (int) j11;
                i5++;
                j8 = (j11 >>> 32) + (j9 >>> 32) + (j10 >>> 32);
            }
            long j12 = j8 + (((long) i3) & 4294967295L);
            iArr5[7] = (int) j12;
            i3 = (int) (j12 >>> 32);
            i2 = i4 + 1;
            j = j5;
            c = 0;
        }
        if (i3 != 0 || Nat256.gte(iArr3, iArr4)) {
            Nat256.sub(iArr5, iArr6, iArr5);
        }
    }

    public static void multAddXF(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        int[] iArr5 = iArr3;
        int[] iArr6 = iArr4;
        char c = 0;
        long j = 4294967295L;
        long j2 = ((long) iArr2[0]) & 4294967295L;
        int i = 0;
        int i2 = 0;
        while (i < 8) {
            long j3 = ((long) iArr[i]) & j;
            int i3 = i;
            long j4 = (j3 * j2) + (((long) iArr5[c]) & j);
            long j5 = j4 & j;
            long j6 = (j4 >>> 32) + j5;
            int i4 = 1;
            int i5 = 8;
            while (i4 < i5) {
                long j7 = (((long) iArr2[i4]) & j) * j3;
                long j8 = (((long) iArr6[i4]) & j) * j5;
                long j9 = j6 + (j7 & j) + (j8 & j) + (((long) iArr5[i4]) & j);
                iArr5[i4 - 1] = (int) j9;
                i4++;
                j6 = (j9 >>> 32) + (j7 >>> 32) + (j8 >>> 32);
                i5 = 8;
                j = 4294967295L;
            }
            long j10 = j6 + (((long) i2) & 4294967295L);
            iArr5[7] = (int) j10;
            i2 = (int) (j10 >>> 32);
            i = i3 + 1;
            j = 4294967295L;
            c = 0;
        }
        if (i2 != 0 || Nat256.gte(iArr3, iArr4)) {
            Nat256.sub(iArr5, iArr6, iArr5);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2, int i) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = iArr3[0];
            long j = 4294967295L;
            long j2 = ((long) (i3 * i)) & 4294967295L;
            long j3 = (((((long) iArr4[0]) & 4294967295L) * j2) + (((long) i3) & 4294967295L)) >>> 32;
            int i4 = 1;
            while (i4 < 8) {
                long j4 = j3 + ((((long) iArr4[i4]) & j) * j2) + (((long) iArr3[i4]) & j);
                iArr3[i4 - 1] = (int) j4;
                j3 = j4 >>> 32;
                i4++;
                j = 4294967295L;
            }
            iArr3[7] = (int) j3;
        }
        if (Nat256.gte(iArr, iArr2)) {
            Nat256.sub(iArr3, iArr4, iArr3);
        }
    }

    public static void reduceXF(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        for (int i = 0; i < 8; i++) {
            long j = 4294967295L;
            long j2 = ((long) iArr3[0]) & 4294967295L;
            int i2 = 1;
            long j3 = j2;
            while (i2 < 8) {
                long j4 = j3 + ((((long) iArr4[i2]) & j) * j2) + (((long) iArr3[i2]) & j);
                iArr3[i2 - 1] = (int) j4;
                j3 = j4 >>> 32;
                i2++;
                j = 4294967295L;
            }
            iArr3[7] = (int) j3;
        }
        if (Nat256.gte(iArr, iArr2)) {
            Nat256.sub(iArr3, iArr4, iArr3);
        }
    }
}
