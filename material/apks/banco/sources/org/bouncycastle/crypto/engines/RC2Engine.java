package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.crypto.signers.PSSSigner;

public class RC2Engine implements BlockCipher {
    private static byte[] a = {-39, 120, -7, -60, Ascii.EM, -35, -75, -19, 40, -23, -3, 121, 74, -96, -40, -99, -58, 126, 55, -125, 43, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, Ascii.ETB, -102, 89, -11, -121, -77, 79, 19, 97, 69, 109, -115, 9, -127, 125, 50, -67, -113, SignedBytes.MAX_POWER_OF_TWO, -21, -122, -73, 123, Ascii.VT, -16, -107, 33, 34, 92, 107, 78, -126, 84, -42, 101, -109, -50, 96, -78, Ascii.FS, 115, 86, -64, Ascii.DC4, -89, -116, -15, -36, Ascii.DC2, 117, -54, Ascii.US, 59, -66, -28, -47, 66, 61, -44, 48, -93, 60, -74, 38, 111, -65, Ascii.SO, -38, 70, 105, 7, 87, 39, -14, Ascii.GS, -101, PSSSigner.TRAILER_IMPLICIT, -108, 67, 3, -8, 17, -57, -10, -112, -17, 62, -25, 6, -61, -43, 47, -56, 102, Ascii.RS, -41, 8, -24, -22, -34, UnsignedBytes.MAX_POWER_OF_TWO, 82, -18, -9, -124, -86, 114, -84, 53, 77, 106, 42, -106, Ascii.SUB, -46, 113, 90, Ascii.NAK, 73, 116, 75, -97, -48, 94, 4, Ascii.CAN, -92, -20, -62, -32, 65, 110, Ascii.SI, 81, -53, -52, 36, -111, -81, 80, -95, -12, 112, 57, -103, 124, 58, -123, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, 49, 45, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, Ascii.DLE, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, 44, 99, Ascii.SYN, 1, 63, 88, -30, -119, -87, Ascii.CR, 56, 52, Ascii.ESC, -85, 51, -1, -80, -69, 72, Ascii.FF, 95, -71, -79, -51, 46, -59, -13, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, Ascii.DEL, -63, -83};
    private int[] b;
    private boolean c;

    private int a(int i, int i2) {
        int i3 = i & 65535;
        return (i3 >> (16 - i2)) | (i3 << i2);
    }

    private void a(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = ((bArr[i + 7] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 6] & UnsignedBytes.MAX_VALUE);
        int i4 = ((bArr[i + 5] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 4] & UnsignedBytes.MAX_VALUE);
        int i5 = ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 2] & UnsignedBytes.MAX_VALUE);
        int i6 = ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 0] & UnsignedBytes.MAX_VALUE);
        for (int i7 = 0; i7 <= 16; i7 += 4) {
            i6 = a(i6 + ((i3 ^ -1) & i5) + (i4 & i3) + this.b[i7], 1);
            i5 = a(i5 + ((i6 ^ -1) & i4) + (i3 & i6) + this.b[i7 + 1], 2);
            i4 = a(i4 + ((i5 ^ -1) & i3) + (i6 & i5) + this.b[i7 + 2], 3);
            i3 = a(i3 + ((i4 ^ -1) & i6) + (i5 & i4) + this.b[i7 + 3], 5);
        }
        int i8 = i6 + this.b[i3 & 63];
        int i9 = i5 + this.b[i8 & 63];
        int i10 = i4 + this.b[i9 & 63];
        int i11 = i3 + this.b[i10 & 63];
        for (int i12 = 20; i12 <= 40; i12 += 4) {
            i8 = a(i8 + ((i11 ^ -1) & i9) + (i10 & i11) + this.b[i12], 1);
            i9 = a(i9 + ((i8 ^ -1) & i10) + (i11 & i8) + this.b[i12 + 1], 2);
            i10 = a(i10 + ((i9 ^ -1) & i11) + (i8 & i9) + this.b[i12 + 2], 3);
            i11 = a(i11 + ((i10 ^ -1) & i8) + (i9 & i10) + this.b[i12 + 3], 5);
        }
        int i13 = i8 + this.b[i11 & 63];
        int i14 = i9 + this.b[i13 & 63];
        int i15 = i10 + this.b[i14 & 63];
        int i16 = i11 + this.b[i15 & 63];
        for (int i17 = 44; i17 < 64; i17 += 4) {
            i13 = a(i13 + ((i16 ^ -1) & i14) + (i15 & i16) + this.b[i17], 1);
            i14 = a(i14 + ((i13 ^ -1) & i15) + (i16 & i13) + this.b[i17 + 1], 2);
            i15 = a(i15 + ((i14 ^ -1) & i16) + (i13 & i14) + this.b[i17 + 2], 3);
            i16 = a(i16 + ((i15 ^ -1) & i13) + (i14 & i15) + this.b[i17 + 3], 5);
        }
        bArr2[i2 + 0] = (byte) i13;
        bArr2[i2 + 1] = (byte) (i13 >> 8);
        bArr2[i2 + 2] = (byte) i14;
        bArr2[i2 + 3] = (byte) (i14 >> 8);
        bArr2[i2 + 4] = (byte) i15;
        bArr2[i2 + 5] = (byte) (i15 >> 8);
        bArr2[i2 + 6] = (byte) i16;
        bArr2[i2 + 7] = (byte) (i16 >> 8);
    }

    private int[] a(byte[] bArr, int i) {
        int[] iArr = new int[128];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            iArr[i2] = bArr[i2] & UnsignedBytes.MAX_VALUE;
        }
        int length = bArr.length;
        if (length < 128) {
            int i3 = iArr[length - 1];
            int i4 = length;
            int i5 = 0;
            while (true) {
                int i6 = i5 + 1;
                i3 = a[(i3 + iArr[i5]) & 255] & UnsignedBytes.MAX_VALUE;
                int i7 = i4 + 1;
                iArr[i4] = i3;
                if (i7 >= 128) {
                    break;
                }
                i4 = i7;
                i5 = i6;
            }
        }
        int i8 = (i + 7) >> 3;
        int i9 = 128 - i8;
        int i10 = a[(255 >> ((-i) & 7)) & iArr[i9]] & UnsignedBytes.MAX_VALUE;
        iArr[i9] = i10;
        for (int i11 = i9 - 1; i11 >= 0; i11--) {
            i10 = a[i10 ^ iArr[i11 + i8]] & UnsignedBytes.MAX_VALUE;
            iArr[i11] = i10;
        }
        int[] iArr2 = new int[64];
        for (int i12 = 0; i12 != iArr2.length; i12++) {
            int i13 = i12 * 2;
            iArr2[i12] = iArr[i13] + (iArr[i13 + 1] << 8);
        }
        return iArr2;
    }

    private void b(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = ((bArr[i + 7] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 6] & UnsignedBytes.MAX_VALUE);
        int i4 = ((bArr[i + 5] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 4] & UnsignedBytes.MAX_VALUE);
        int i5 = ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 2] & UnsignedBytes.MAX_VALUE);
        int i6 = ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) + (bArr[i + 0] & UnsignedBytes.MAX_VALUE);
        for (int i7 = 60; i7 >= 44; i7 -= 4) {
            i3 = a(i3, 11) - ((((i4 ^ -1) & i6) + (i5 & i4)) + this.b[i7 + 3]);
            i4 = a(i4, 13) - ((((i5 ^ -1) & i3) + (i6 & i5)) + this.b[i7 + 2]);
            i5 = a(i5, 14) - ((((i6 ^ -1) & i4) + (i3 & i6)) + this.b[i7 + 1]);
            i6 = a(i6, 15) - ((((i3 ^ -1) & i5) + (i4 & i3)) + this.b[i7]);
        }
        int i8 = i3 - this.b[i4 & 63];
        int i9 = i4 - this.b[i5 & 63];
        int i10 = i5 - this.b[i6 & 63];
        int i11 = i6 - this.b[i8 & 63];
        for (int i12 = 40; i12 >= 20; i12 -= 4) {
            i8 = a(i8, 11) - ((((i9 ^ -1) & i11) + (i10 & i9)) + this.b[i12 + 3]);
            i9 = a(i9, 13) - ((((i10 ^ -1) & i8) + (i11 & i10)) + this.b[i12 + 2]);
            i10 = a(i10, 14) - ((((i11 ^ -1) & i9) + (i8 & i11)) + this.b[i12 + 1]);
            i11 = a(i11, 15) - ((((i8 ^ -1) & i10) + (i9 & i8)) + this.b[i12]);
        }
        int i13 = i8 - this.b[i9 & 63];
        int i14 = i9 - this.b[i10 & 63];
        int i15 = i10 - this.b[i11 & 63];
        int i16 = i11 - this.b[i13 & 63];
        for (int i17 = 16; i17 >= 0; i17 -= 4) {
            i13 = a(i13, 11) - ((((i14 ^ -1) & i16) + (i15 & i14)) + this.b[i17 + 3]);
            i14 = a(i14, 13) - ((((i15 ^ -1) & i13) + (i16 & i15)) + this.b[i17 + 2]);
            i15 = a(i15, 14) - ((((i16 ^ -1) & i14) + (i13 & i16)) + this.b[i17 + 1]);
            i16 = a(i16, 15) - ((((i13 ^ -1) & i15) + (i14 & i13)) + this.b[i17]);
        }
        bArr2[i2 + 0] = (byte) i16;
        bArr2[i2 + 1] = (byte) (i16 >> 8);
        bArr2[i2 + 2] = (byte) i15;
        bArr2[i2 + 3] = (byte) (i15 >> 8);
        bArr2[i2 + 4] = (byte) i14;
        bArr2[i2 + 5] = (byte) (i14 >> 8);
        bArr2[i2 + 6] = (byte) i13;
        bArr2[i2 + 7] = (byte) (i13 >> 8);
    }

    public String getAlgorithmName() {
        return "RC2";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        byte[] key;
        int length;
        this.c = z;
        if (cipherParameters instanceof RC2Parameters) {
            RC2Parameters rC2Parameters = (RC2Parameters) cipherParameters;
            key = rC2Parameters.getKey();
            length = rC2Parameters.getEffectiveKeyBits();
        } else if (cipherParameters instanceof KeyParameter) {
            key = ((KeyParameter) cipherParameters).getKey();
            length = key.length * 8;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to RC2 init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        this.b = a(key, length);
    }

    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.b == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            if (this.c) {
                a(bArr, i, bArr2, i2);
            } else {
                b(bArr, i, bArr2, i2);
            }
            return 8;
        }
    }

    public void reset() {
    }
}
