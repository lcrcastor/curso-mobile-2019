package org.bouncycastle.crypto.modes.gcm;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

abstract class GCMUtil {
    private static final int[] a = b();

    GCMUtil() {
    }

    static int a(int[] iArr) {
        int i = iArr[0];
        iArr[0] = i >>> 1;
        int i2 = i << 31;
        int i3 = iArr[1];
        iArr[1] = i2 | (i3 >>> 1);
        int i4 = i3 << 31;
        int i5 = iArr[2];
        iArr[2] = i4 | (i5 >>> 1);
        int i6 = i5 << 31;
        int i7 = iArr[3];
        iArr[3] = i6 | (i7 >>> 1);
        return i7 << 31;
    }

    static int a(int[] iArr, int i, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = 32 - i;
        iArr2[0] = i2 >>> i;
        int i4 = i2 << i3;
        int i5 = iArr[1];
        iArr2[1] = i4 | (i5 >>> i);
        int i6 = i5 << i3;
        int i7 = iArr[2];
        iArr2[2] = i6 | (i7 >>> i);
        int i8 = i7 << i3;
        int i9 = iArr[3];
        iArr2[3] = (i9 >>> i) | i8;
        return i9 << i3;
    }

    static void a(byte[] bArr, byte[] bArr2) {
        byte[] clone = Arrays.clone(bArr);
        byte[] bArr3 = new byte[16];
        for (int i = 0; i < 16; i++) {
            byte b = bArr2[i];
            for (int i2 = 7; i2 >= 0; i2--) {
                if (((1 << i2) & b) != 0) {
                    b(bArr3, clone);
                }
                if (b(clone) != 0) {
                    clone[0] = (byte) (clone[0] ^ -31);
                }
            }
        }
        System.arraycopy(bArr3, 0, bArr, 0, 16);
    }

    static void a(byte[] bArr, int[] iArr) {
        Pack.bigEndianToInt(bArr, 0, iArr);
    }

    static void a(int[] iArr, byte[] bArr) {
        Pack.intToBigEndian(iArr, bArr, 0);
    }

    static void a(int[] iArr, int[] iArr2) {
        int[] clone = Arrays.clone(iArr);
        int[] iArr3 = new int[4];
        for (int i = 0; i < 4; i++) {
            int i2 = iArr2[i];
            for (int i3 = 31; i3 >= 0; i3--) {
                if (((1 << i3) & i2) != 0) {
                    e(iArr3, clone);
                }
                if (a(clone) != 0) {
                    clone[0] = clone[0] ^ -520093696;
                }
            }
        }
        System.arraycopy(iArr3, 0, iArr, 0, 4);
    }

    static void a(int[] iArr, int[] iArr2, int[] iArr3) {
        iArr3[0] = iArr[0] ^ iArr2[0];
        iArr3[1] = iArr[1] ^ iArr2[1];
        iArr3[2] = iArr[2] ^ iArr2[2];
        iArr3[3] = iArr[3] ^ iArr2[3];
    }

    static int[] a() {
        int[] iArr = new int[4];
        iArr[0] = Integer.MIN_VALUE;
        return iArr;
    }

    static int[] a(byte[] bArr) {
        int[] iArr = new int[4];
        Pack.bigEndianToInt(bArr, 0, iArr);
        return iArr;
    }

    static byte b(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte b = bArr[i] & UnsignedBytes.MAX_VALUE;
            int i3 = i + 1;
            bArr[i] = (byte) (i2 | (b >>> 1));
            int i4 = (b & 1) << 7;
            byte b2 = bArr[i3] & UnsignedBytes.MAX_VALUE;
            int i5 = i3 + 1;
            bArr[i3] = (byte) (i4 | (b2 >>> 1));
            int i6 = (b2 & 1) << 7;
            byte b3 = bArr[i5] & UnsignedBytes.MAX_VALUE;
            int i7 = i5 + 1;
            bArr[i5] = (byte) (i6 | (b3 >>> 1));
            int i8 = (b3 & 1) << 7;
            byte b4 = bArr[i7] & UnsignedBytes.MAX_VALUE;
            int i9 = i7 + 1;
            bArr[i7] = (byte) (i8 | (b4 >>> 1));
            i2 = (b4 & 1) << 7;
            if (i9 >= 16) {
                return (byte) i2;
            }
            i = i9;
        }
    }

    static void b(byte[] bArr, byte[] bArr2) {
        int i = 0;
        do {
            bArr[i] = (byte) (bArr[i] ^ bArr2[i]);
            int i2 = i + 1;
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
            int i3 = i2 + 1;
            bArr[i3] = (byte) (bArr[i3] ^ bArr2[i3]);
            int i4 = i3 + 1;
            bArr[i4] = (byte) (bArr[i4] ^ bArr2[i4]);
            i = i4 + 1;
        } while (i < 16);
    }

    static void b(int[] iArr, int[] iArr2) {
        if (d(iArr, iArr2) != 0) {
            iArr2[0] = iArr2[0] ^ -520093696;
        }
    }

    private static int[] b() {
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            int i2 = 0;
            for (int i3 = 7; i3 >= 0; i3--) {
                if (((1 << i3) & i) != 0) {
                    i2 ^= -520093696 >>> (7 - i3);
                }
            }
            iArr[i] = i2;
        }
        return iArr;
    }

    static void c(int[] iArr, int[] iArr2) {
        int a2 = a(iArr, 8, iArr2);
        iArr2[0] = a[a2 >>> 24] ^ iArr2[0];
    }

    static int d(int[] iArr, int[] iArr2) {
        int i = iArr[0];
        iArr2[0] = i >>> 1;
        int i2 = i << 31;
        int i3 = iArr[1];
        iArr2[1] = i2 | (i3 >>> 1);
        int i4 = i3 << 31;
        int i5 = iArr[2];
        iArr2[2] = i4 | (i5 >>> 1);
        int i6 = i5 << 31;
        int i7 = iArr[3];
        iArr2[3] = i6 | (i7 >>> 1);
        return i7 << 31;
    }

    static void e(int[] iArr, int[] iArr2) {
        iArr[0] = iArr[0] ^ iArr2[0];
        iArr[1] = iArr[1] ^ iArr2[1];
        iArr[2] = iArr[2] ^ iArr2[2];
        iArr[3] = iArr2[3] ^ iArr[3];
    }
}
