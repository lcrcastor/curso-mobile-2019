package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;

public class CamelliaLightEngine implements BlockCipher {
    private static final int[] g = {-1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691};
    private static final byte[] h = {112, -126, 44, -20, -77, 39, -64, -27, -28, -123, 87, 53, -22, Ascii.FF, -82, 65, 35, -17, 107, -109, 69, Ascii.EM, -91, 33, -19, Ascii.SO, 79, 78, Ascii.GS, 101, -110, -67, -122, -72, -81, -113, 124, -21, Ascii.US, -50, 62, 48, -36, 95, 94, -59, Ascii.VT, Ascii.SUB, -90, -31, 57, -54, -43, 71, 93, 61, -39, 1, 90, -42, 81, 86, 108, 77, -117, Ascii.CR, -102, 102, -5, -52, -80, 45, 116, Ascii.DC2, 43, 32, -16, -79, -124, -103, -33, 76, -53, -62, 52, 126, 118, 5, 109, -73, -87, 49, -47, Ascii.ETB, 4, -41, Ascii.DC4, 88, 58, 97, -34, Ascii.ESC, 17, Ascii.FS, 50, Ascii.SI, -100, Ascii.SYN, 83, Ascii.CAN, -14, 34, -2, 68, -49, -78, -61, -75, 122, -111, 36, 8, -24, -88, 96, -4, 105, 80, -86, -48, -96, 125, -95, -119, 98, -105, 84, 91, Ascii.RS, -107, -32, -1, 100, -46, Ascii.DLE, -60, 0, 72, -93, -9, 117, -37, -118, 3, -26, -38, 9, 63, -35, -108, -121, 92, -125, 2, -51, 74, -112, 51, 115, 103, -10, -13, -99, Ascii.DEL, -65, -30, 82, -101, -40, 38, -56, 55, -58, 59, -127, -106, 111, 75, 19, -66, 99, 46, -23, 121, -89, -116, -97, 110, PSSSigner.TRAILER_IMPLICIT, -114, 41, -11, -7, -74, 47, -3, -76, 89, 120, -104, 6, 106, -25, 70, 113, -70, -44, 37, -85, 66, -120, -94, -115, -6, 114, 7, -71, 85, -8, -18, -84, 10, 54, 73, 42, 104, 60, 56, -15, -92, SignedBytes.MAX_POWER_OF_TWO, 40, -45, 123, -69, -55, 67, -63, Ascii.NAK, -29, -83, -12, 119, -57, UnsignedBytes.MAX_POWER_OF_TWO, -98};
    private boolean a;
    private boolean b;
    private int[] c = new int[96];
    private int[] d = new int[8];
    private int[] e = new int[12];
    private int[] f = new int[4];

    private byte a(byte b2, int i) {
        return (byte) (((b2 & UnsignedBytes.MAX_VALUE) >>> (8 - i)) | (b2 << i));
    }

    private int a(int i) {
        return a(h[i], 1) & UnsignedBytes.MAX_VALUE;
    }

    private static int a(int i, int i2) {
        return (i >>> i2) + (i << (32 - i2));
    }

    private int a(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            i2 = (i2 << 8) + (bArr[i3 + i] & UnsignedBytes.MAX_VALUE);
        }
        return i2;
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            this.f[i3] = a(bArr, (i3 * 4) + i);
            int[] iArr = this.f;
            iArr[i3] = iArr[i3] ^ this.d[i3];
        }
        a(this.f, this.c, 0);
        a(this.f, this.c, 4);
        a(this.f, this.c, 8);
        b(this.f, this.e, 0);
        a(this.f, this.c, 12);
        a(this.f, this.c, 16);
        a(this.f, this.c, 20);
        b(this.f, this.e, 4);
        a(this.f, this.c, 24);
        a(this.f, this.c, 28);
        a(this.f, this.c, 32);
        int[] iArr2 = this.f;
        iArr2[2] = this.d[4] ^ iArr2[2];
        int[] iArr3 = this.f;
        iArr3[3] = iArr3[3] ^ this.d[5];
        int[] iArr4 = this.f;
        iArr4[0] = iArr4[0] ^ this.d[6];
        int[] iArr5 = this.f;
        iArr5[1] = iArr5[1] ^ this.d[7];
        a(this.f[2], bArr2, i2);
        a(this.f[3], bArr2, i2 + 4);
        a(this.f[0], bArr2, i2 + 8);
        a(this.f[1], bArr2, i2 + 12);
        return 16;
    }

    private void a(int i, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            bArr[(3 - i3) + i2] = (byte) i;
            i >>>= 8;
        }
    }

    private static void a(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = i3 + 0;
        int i5 = i2 + 0;
        int i6 = i2 + 1;
        int i7 = 32 - i;
        iArr2[i4] = (iArr[i5] << i) | (iArr[i6] >>> i7);
        int i8 = i3 + 1;
        int i9 = i2 + 2;
        iArr2[i8] = (iArr[i6] << i) | (iArr[i9] >>> i7);
        int i10 = i3 + 2;
        int i11 = i2 + 3;
        iArr2[i10] = (iArr[i9] << i) | (iArr[i11] >>> i7);
        int i12 = i3 + 3;
        iArr2[i12] = (iArr[i11] << i) | (iArr[i5] >>> i7);
        iArr[i5] = iArr2[i4];
        iArr[i6] = iArr2[i8];
        iArr[i9] = iArr2[i10];
        iArr[i11] = iArr2[i12];
    }

    private void a(boolean z, byte[] bArr) {
        byte[] bArr2 = bArr;
        int[] iArr = new int[8];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        int[] iArr4 = new int[4];
        int length = bArr2.length;
        if (length != 16) {
            if (length == 24) {
                iArr[0] = a(bArr2, 0);
                iArr[1] = a(bArr2, 4);
                iArr[2] = a(bArr2, 8);
                iArr[3] = a(bArr2, 12);
                iArr[4] = a(bArr2, 16);
                iArr[5] = a(bArr2, 20);
                iArr[6] = iArr[4] ^ -1;
                iArr[7] = iArr[5] ^ -1;
            } else if (length != 32) {
                throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
            } else {
                iArr[0] = a(bArr2, 0);
                iArr[1] = a(bArr2, 4);
                iArr[2] = a(bArr2, 8);
                iArr[3] = a(bArr2, 12);
                iArr[4] = a(bArr2, 16);
                iArr[5] = a(bArr2, 20);
                iArr[6] = a(bArr2, 24);
                iArr[7] = a(bArr2, 28);
            }
            this.b = false;
        } else {
            this.b = true;
            iArr[0] = a(bArr2, 0);
            iArr[1] = a(bArr2, 4);
            iArr[2] = a(bArr2, 8);
            iArr[3] = a(bArr2, 12);
            iArr[7] = 0;
            iArr[6] = 0;
            iArr[5] = 0;
            iArr[4] = 0;
        }
        for (int i = 0; i < 4; i++) {
            iArr2[i] = iArr[i] ^ iArr[i + 4];
        }
        a(iArr2, g, 0);
        for (int i2 = 0; i2 < 4; i2++) {
            iArr2[i2] = iArr2[i2] ^ iArr[i2];
        }
        a(iArr2, g, 4);
        if (!this.b) {
            for (int i3 = 0; i3 < 4; i3++) {
                iArr3[i3] = iArr2[i3] ^ iArr[i3 + 4];
            }
            a(iArr3, g, 8);
            if (z) {
                this.d[0] = iArr[0];
                this.d[1] = iArr[1];
                this.d[2] = iArr[2];
                this.d[3] = iArr[3];
                c(45, iArr, 0, this.c, 16);
                a(15, iArr, 0, this.e, 4);
                a(17, iArr, 0, this.c, 32);
                c(34, iArr, 0, this.c, 44);
                a(15, iArr, 4, this.c, 4);
                a(15, iArr, 4, this.e, 0);
                a(30, iArr, 4, this.c, 24);
                c(34, iArr, 4, this.c, 36);
                a(15, iArr2, 0, this.c, 8);
                a(30, iArr2, 0, this.c, 20);
                this.e[8] = iArr2[1];
                this.e[9] = iArr2[2];
                this.e[10] = iArr2[3];
                this.e[11] = iArr2[0];
                c(49, iArr2, 0, this.c, 40);
                this.c[0] = iArr3[0];
                this.c[1] = iArr3[1];
                this.c[2] = iArr3[2];
                this.c[3] = iArr3[3];
                a(30, iArr3, 0, this.c, 12);
                a(30, iArr3, 0, this.c, 28);
                c(51, iArr3, 0, this.d, 4);
                return;
            }
            this.d[4] = iArr[0];
            this.d[5] = iArr[1];
            this.d[6] = iArr[2];
            this.d[7] = iArr[3];
            d(45, iArr, 0, this.c, 28);
            b(15, iArr, 0, this.e, 4);
            b(17, iArr, 0, this.c, 12);
            d(34, iArr, 0, this.c, 0);
            b(15, iArr, 4, this.c, 40);
            b(15, iArr, 4, this.e, 8);
            b(30, iArr, 4, this.c, 20);
            d(34, iArr, 4, this.c, 8);
            b(15, iArr2, 0, this.c, 36);
            b(30, iArr2, 0, this.c, 24);
            this.e[2] = iArr2[1];
            this.e[3] = iArr2[2];
            this.e[0] = iArr2[3];
            this.e[1] = iArr2[0];
            d(49, iArr2, 0, this.c, 4);
            this.c[46] = iArr3[0];
            this.c[47] = iArr3[1];
            this.c[44] = iArr3[2];
            this.c[45] = iArr3[3];
            b(30, iArr3, 0, this.c, 32);
            b(30, iArr3, 0, this.c, 16);
            c(51, iArr3, 0, this.d, 0);
        } else if (z) {
            this.d[0] = iArr[0];
            this.d[1] = iArr[1];
            this.d[2] = iArr[2];
            this.d[3] = iArr[3];
            a(15, iArr, 0, this.c, 4);
            a(30, iArr, 0, this.c, 12);
            a(15, iArr, 0, iArr4, 0);
            this.c[18] = iArr4[2];
            this.c[19] = iArr4[3];
            a(17, iArr, 0, this.e, 4);
            a(17, iArr, 0, this.c, 24);
            a(17, iArr, 0, this.c, 32);
            this.c[0] = iArr2[0];
            this.c[1] = iArr2[1];
            this.c[2] = iArr2[2];
            this.c[3] = iArr2[3];
            a(15, iArr2, 0, this.c, 8);
            a(15, iArr2, 0, this.e, 0);
            a(15, iArr2, 0, iArr4, 0);
            this.c[16] = iArr4[0];
            this.c[17] = iArr4[1];
            a(15, iArr2, 0, this.c, 20);
            c(34, iArr2, 0, this.c, 28);
            a(17, iArr2, 0, this.d, 4);
        } else {
            this.d[4] = iArr[0];
            this.d[5] = iArr[1];
            this.d[6] = iArr[2];
            this.d[7] = iArr[3];
            b(15, iArr, 0, this.c, 28);
            b(30, iArr, 0, this.c, 20);
            b(15, iArr, 0, iArr4, 0);
            this.c[16] = iArr4[0];
            this.c[17] = iArr4[1];
            b(17, iArr, 0, this.e, 0);
            b(17, iArr, 0, this.c, 8);
            b(17, iArr, 0, this.c, 0);
            this.c[34] = iArr2[0];
            this.c[35] = iArr2[1];
            this.c[32] = iArr2[2];
            this.c[33] = iArr2[3];
            b(15, iArr2, 0, this.c, 24);
            b(15, iArr2, 0, this.e, 4);
            b(15, iArr2, 0, iArr4, 0);
            this.c[18] = iArr4[2];
            this.c[19] = iArr4[3];
            b(15, iArr2, 0, this.c, 12);
            d(34, iArr2, 0, this.c, 4);
            a(17, iArr2, 0, this.d, 0);
        }
    }

    private void a(int[] iArr, int[] iArr2, int i) {
        int i2 = iArr[0] ^ iArr2[i + 0];
        int c2 = ((h[(i2 >>> 24) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | c(i2 & 255) | (b((i2 >>> 8) & 255) << 8) | (a((i2 >>> 16) & 255) << 16);
        int i3 = iArr[1] ^ iArr2[i + 1];
        int b2 = b((a((i3 >>> 24) & 255) << 24) | (h[i3 & 255] & UnsignedBytes.MAX_VALUE) | (c((i3 >>> 8) & 255) << 8) | (b((i3 >>> 16) & 255) << 16), 8);
        int i4 = c2 ^ b2;
        int b3 = b(b2, 8) ^ i4;
        int a2 = a(i4, 8) ^ b3;
        iArr[2] = (b(b3, 16) ^ a2) ^ iArr[2];
        iArr[3] = b(a2, 8) ^ iArr[3];
        int i5 = iArr[2] ^ iArr2[i + 2];
        int c3 = ((h[(i5 >>> 24) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | c(i5 & 255) | (b((i5 >>> 8) & 255) << 8) | (a((i5 >>> 16) & 255) << 16);
        int i6 = iArr2[i + 3] ^ iArr[3];
        int b4 = b((a((i6 >>> 24) & 255) << 24) | (h[i6 & 255] & UnsignedBytes.MAX_VALUE) | (c((i6 >>> 8) & 255) << 8) | (b((i6 >>> 16) & 255) << 16), 8);
        int i7 = c3 ^ b4;
        int b5 = b(b4, 8) ^ i7;
        int a3 = a(i7, 8) ^ b5;
        iArr[0] = (b(b5, 16) ^ a3) ^ iArr[0];
        iArr[1] = iArr[1] ^ b(a3, 8);
    }

    private int b(int i) {
        return a(h[i], 7) & UnsignedBytes.MAX_VALUE;
    }

    private static int b(int i, int i2) {
        return (i << i2) + (i >>> (32 - i2));
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            this.f[i3] = a(bArr, (i3 * 4) + i);
            int[] iArr = this.f;
            iArr[i3] = iArr[i3] ^ this.d[i3];
        }
        a(this.f, this.c, 0);
        a(this.f, this.c, 4);
        a(this.f, this.c, 8);
        b(this.f, this.e, 0);
        a(this.f, this.c, 12);
        a(this.f, this.c, 16);
        a(this.f, this.c, 20);
        b(this.f, this.e, 4);
        a(this.f, this.c, 24);
        a(this.f, this.c, 28);
        a(this.f, this.c, 32);
        b(this.f, this.e, 8);
        a(this.f, this.c, 36);
        a(this.f, this.c, 40);
        a(this.f, this.c, 44);
        int[] iArr2 = this.f;
        iArr2[2] = iArr2[2] ^ this.d[4];
        int[] iArr3 = this.f;
        iArr3[3] = iArr3[3] ^ this.d[5];
        int[] iArr4 = this.f;
        iArr4[0] = iArr4[0] ^ this.d[6];
        int[] iArr5 = this.f;
        iArr5[1] = iArr5[1] ^ this.d[7];
        a(this.f[2], bArr2, i2);
        a(this.f[3], bArr2, i2 + 4);
        a(this.f[0], bArr2, i2 + 8);
        a(this.f[1], bArr2, i2 + 12);
        return 16;
    }

    private static void b(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = i3 + 2;
        int i5 = i2 + 0;
        int i6 = i2 + 1;
        int i7 = 32 - i;
        iArr2[i4] = (iArr[i5] << i) | (iArr[i6] >>> i7);
        int i8 = i3 + 3;
        int i9 = i2 + 2;
        iArr2[i8] = (iArr[i6] << i) | (iArr[i9] >>> i7);
        int i10 = i3 + 0;
        int i11 = i2 + 3;
        iArr2[i10] = (iArr[i9] << i) | (iArr[i11] >>> i7);
        int i12 = i3 + 1;
        iArr2[i12] = (iArr[i11] << i) | (iArr[i5] >>> i7);
        iArr[i5] = iArr2[i4];
        iArr[i6] = iArr2[i8];
        iArr[i9] = iArr2[i10];
        iArr[i11] = iArr2[i12];
    }

    private void b(int[] iArr, int[] iArr2, int i) {
        iArr[1] = iArr[1] ^ b(iArr[0] & iArr2[i + 0], 1);
        iArr[0] = iArr[0] ^ (iArr2[i + 1] | iArr[1]);
        iArr[2] = iArr[2] ^ (iArr2[i + 3] | iArr[3]);
        iArr[3] = b(iArr2[i + 2] & iArr[2], 1) ^ iArr[3];
    }

    private int c(int i) {
        return h[a((byte) i, 1) & UnsignedBytes.MAX_VALUE] & UnsignedBytes.MAX_VALUE;
    }

    private static void c(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = i3 + 0;
        int i5 = i2 + 1;
        int i6 = i - 32;
        int i7 = i2 + 2;
        int i8 = 64 - i;
        iArr2[i4] = (iArr[i5] << i6) | (iArr[i7] >>> i8);
        int i9 = i3 + 1;
        int i10 = i2 + 3;
        iArr2[i9] = (iArr[i7] << i6) | (iArr[i10] >>> i8);
        int i11 = i3 + 2;
        int i12 = i2 + 0;
        iArr2[i11] = (iArr[i10] << i6) | (iArr[i12] >>> i8);
        int i13 = i3 + 3;
        iArr2[i13] = (iArr[i5] >>> i8) | (iArr[i12] << i6);
        iArr[i12] = iArr2[i4];
        iArr[i5] = iArr2[i9];
        iArr[i7] = iArr2[i11];
        iArr[i10] = iArr2[i13];
    }

    private static void d(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        int i4 = i3 + 2;
        int i5 = i2 + 1;
        int i6 = i - 32;
        int i7 = i2 + 2;
        int i8 = 64 - i;
        iArr2[i4] = (iArr[i5] << i6) | (iArr[i7] >>> i8);
        int i9 = i3 + 3;
        int i10 = i2 + 3;
        iArr2[i9] = (iArr[i7] << i6) | (iArr[i10] >>> i8);
        int i11 = i3 + 0;
        int i12 = i2 + 0;
        iArr2[i11] = (iArr[i10] << i6) | (iArr[i12] >>> i8);
        int i13 = i3 + 1;
        iArr2[i13] = (iArr[i5] >>> i8) | (iArr[i12] << i6);
        iArr[i12] = iArr2[i4];
        iArr[i5] = iArr2[i9];
        iArr[i7] = iArr2[i11];
        iArr[i10] = iArr2[i13];
    }

    public String getAlgorithmName() {
        return "Camellia";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("only simple KeyParameter expected.");
        }
        a(z, ((KeyParameter) cipherParameters).getKey());
        this.a = true;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (!this.a) {
            throw new IllegalStateException("Camellia is not initialized");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 <= bArr2.length) {
            return this.b ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
