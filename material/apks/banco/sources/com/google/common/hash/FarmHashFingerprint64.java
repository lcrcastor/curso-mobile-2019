package com.google.common.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;

final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    private static long a(long j) {
        return j ^ (j >>> 47);
    }

    private static long a(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        long j5 = (j2 ^ (j4 ^ (j4 >>> 47))) * j3;
        return (j5 ^ (j5 >>> 47)) * j3;
    }

    public int bits() {
        return 64;
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    FarmHashFingerprint64() {
    }

    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        return HashCode.fromLong(a(bArr, i, i2));
    }

    @VisibleForTesting
    static long a(byte[] bArr, int i, int i2) {
        if (i2 <= 32) {
            if (i2 <= 16) {
                return b(bArr, i, i2);
            }
            return c(bArr, i, i2);
        } else if (i2 <= 64) {
            return d(bArr, i, i2);
        } else {
            return e(bArr, i, i2);
        }
    }

    private static void a(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long a = LittleEndianByteArray.a(bArr, i);
        long a2 = LittleEndianByteArray.a(bArr, i + 8);
        long a3 = LittleEndianByteArray.a(bArr, i + 16);
        long a4 = LittleEndianByteArray.a(bArr, i + 24);
        long j3 = j + a;
        long j4 = j3 + a2 + a3;
        long rotateRight = Long.rotateRight(j2 + j3 + a4, 21) + Long.rotateRight(j4, 44);
        jArr[0] = j4 + a4;
        jArr[1] = rotateRight + j3;
    }

    private static long b(byte[] bArr, int i, int i2) {
        if (i2 >= 8) {
            long j = ((long) (i2 * 2)) - 7286425919675154353L;
            long a = LittleEndianByteArray.a(bArr, i) - 7286425919675154353L;
            long a2 = LittleEndianByteArray.a(bArr, (i + i2) - 8);
            return a((Long.rotateRight(a2, 37) * j) + a, (Long.rotateRight(a, 25) + a2) * j, j);
        } else if (i2 >= 4) {
            return a(((long) i2) + ((((long) LittleEndianByteArray.b(bArr, i)) & 4294967295L) << 3), ((long) LittleEndianByteArray.b(bArr, (i + i2) - 4)) & 4294967295L, ((long) (i2 * 2)) - 7286425919675154353L);
        } else if (i2 <= 0) {
            return -7286425919675154353L;
        } else {
            return a((((long) ((bArr[i] & UnsignedBytes.MAX_VALUE) + ((bArr[(i2 >> 1) + i] & UnsignedBytes.MAX_VALUE) << 8))) * -7286425919675154353L) ^ (((long) (i2 + ((bArr[i + (i2 - 1)] & UnsignedBytes.MAX_VALUE) << 2))) * -4348849565147123417L)) * -7286425919675154353L;
        }
    }

    private static long c(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        long j = ((long) (i2 * 2)) - 7286425919675154353L;
        long a = LittleEndianByteArray.a(bArr, i) * -5435081209227447693L;
        long a2 = LittleEndianByteArray.a(bArr2, i + 8);
        int i3 = i + i2;
        long a3 = LittleEndianByteArray.a(bArr2, i3 - 8) * j;
        return a(Long.rotateRight(a + a2, 43) + Long.rotateRight(a3, 30) + (LittleEndianByteArray.a(bArr2, i3 - 16) * -7286425919675154353L), a + Long.rotateRight(a2 - 7286425919675154353L, 18) + a3, j);
    }

    private static long d(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        long j = ((long) (i2 * 2)) - 7286425919675154353L;
        long a = LittleEndianByteArray.a(bArr, i) * -7286425919675154353L;
        long a2 = LittleEndianByteArray.a(bArr2, i + 8);
        int i3 = i + i2;
        long a3 = LittleEndianByteArray.a(bArr2, i3 - 8) * j;
        long rotateRight = Long.rotateRight(a + a2, 43) + Long.rotateRight(a3, 30) + (LittleEndianByteArray.a(bArr2, i3 - 16) * -7286425919675154353L);
        long a4 = a(rotateRight, a + Long.rotateRight(a2 - 7286425919675154353L, 18) + a3, j);
        byte[] bArr3 = bArr;
        long a5 = LittleEndianByteArray.a(bArr3, i + 16) * j;
        long a6 = LittleEndianByteArray.a(bArr3, i + 24);
        long a7 = (rotateRight + LittleEndianByteArray.a(bArr3, i3 - 32)) * j;
        return a(Long.rotateRight(a5 + a6, 43) + Long.rotateRight(a7, 30) + ((a4 + LittleEndianByteArray.a(bArr3, i3 - 24)) * j), a5 + Long.rotateRight(a6 + a, 18) + a7, j);
    }

    private static long e(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        long a = a(-7956866745689871395L) * -7286425919675154353L;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long a2 = 95310865018149119L + LittleEndianByteArray.a(bArr, i);
        char c = 1;
        int i3 = i2 - 1;
        int i4 = i + ((i3 / 64) * 64);
        int i5 = i3 & 63;
        int i6 = (i4 + i5) - 63;
        long j = 2480279821605975764L;
        int i7 = i;
        while (true) {
            long rotateRight = Long.rotateRight(j + jArr[c] + LittleEndianByteArray.a(bArr2, i7 + 48), 42) * -5435081209227447693L;
            long rotateRight2 = (Long.rotateRight(((a2 + j) + jArr[0]) + LittleEndianByteArray.a(bArr2, i7 + 8), 37) * -5435081209227447693L) ^ jArr2[c];
            byte[] bArr3 = bArr;
            long a3 = rotateRight + jArr[0] + LittleEndianByteArray.a(bArr3, i7 + 40);
            long rotateRight3 = Long.rotateRight(a + jArr2[0], 33) * -5435081209227447693L;
            a(bArr3, i7, jArr[1] * -5435081209227447693L, rotateRight2 + jArr2[0], jArr);
            a(bArr3, i7 + 32, rotateRight3 + jArr2[1], a3 + LittleEndianByteArray.a(bArr3, i7 + 16), jArr2);
            int i8 = i7 + 64;
            if (i8 == i4) {
                long j2 = ((rotateRight2 & 255) << 1) - 5435081209227447693L;
                jArr2[0] = jArr2[0] + ((long) i5);
                jArr[0] = jArr[0] + jArr2[0];
                jArr2[0] = jArr2[0] + jArr[0];
                long rotateRight4 = (Long.rotateRight(((rotateRight3 + a3) + jArr[0]) + LittleEndianByteArray.a(bArr3, i6 + 8), 37) * j2) ^ (jArr2[1] * 9);
                long rotateRight5 = Long.rotateRight(rotateRight2 + jArr2[0], 33) * j2;
                long j3 = rotateRight4;
                long rotateRight6 = (Long.rotateRight(a3 + jArr[1] + LittleEndianByteArray.a(bArr3, i6 + 48), 42) * j2) + (jArr[0] * 9) + LittleEndianByteArray.a(bArr3, i6 + 40);
                a(bArr3, i6, jArr[1] * j2, rotateRight4 + jArr2[0], jArr);
                a(bArr3, i6 + 32, rotateRight5 + jArr2[1], rotateRight6 + LittleEndianByteArray.a(bArr3, i6 + 16), jArr2);
                long j4 = j2;
                return a(a(jArr[0], jArr2[0], j4) + (a(rotateRight6) * -4348849565147123417L) + j3, a(jArr[1], jArr2[1], j4) + rotateRight5, j4);
            }
            i7 = i8;
            bArr2 = bArr3;
            j = a3;
            a = rotateRight2;
            a2 = rotateRight3;
            c = 1;
        }
    }
}
