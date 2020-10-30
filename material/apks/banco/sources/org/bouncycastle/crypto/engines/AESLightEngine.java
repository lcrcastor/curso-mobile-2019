package org.bouncycastle.crypto.engines;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.crypto.tls.CipherSuite;

public class AESLightEngine implements BlockCipher {
    private static final byte[] a = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, Ascii.NAK, 4, -57, 35, -61, Ascii.CAN, -106, 5, -102, 7, Ascii.DC2, UnsignedBytes.MAX_POWER_OF_TWO, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, 59, -42, -77, 41, -29, 47, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, -123, 69, -7, 2, Ascii.DEL, 80, 60, -97, -88, 81, -93, SignedBytes.MAX_POWER_OF_TWO, -113, -110, -99, 56, -11, PSSSigner.TRAILER_IMPLICIT, -74, -38, 33, Ascii.DLE, -1, -13, -46, -51, Ascii.FF, 19, -20, 95, -105, 68, Ascii.ETB, -60, -89, 126, 61, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, 34, 42, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, Ascii.VT, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, Ascii.SO, 97, 53, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, Ascii.CR, -65, -26, 66, 104, 65, -103, 45, Ascii.SI, -80, 84, -69, Ascii.SYN};
    private static final byte[] b = {82, 9, 106, -43, 48, 54, -91, 56, -65, SignedBytes.MAX_POWER_OF_TWO, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, Ascii.VT, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, Ascii.SYN, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, Ascii.NAK, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, PSSSigner.TRAILER_IMPLICIT, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, Ascii.RS, -113, -54, 63, Ascii.SI, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, -123, -30, -7, 55, -24, Ascii.FS, 117, -33, 110, 71, -15, Ascii.SUB, 113, Ascii.GS, 41, -59, -119, 111, -73, 98, Ascii.SO, -86, Ascii.CAN, -66, Ascii.ESC, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, Ascii.US, -35, -88, 51, -120, 7, -57, 49, -79, Ascii.DC2, Ascii.DLE, 89, 39, UnsignedBytes.MAX_POWER_OF_TWO, -20, 95, 96, 81, Ascii.DEL, -87, Ascii.EM, -75, 74, Ascii.CR, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, 42, -11, -80, -56, -21, -69, 60, -125, 83, -103, 97, Ascii.ETB, 43, 4, 126, -70, 119, -42, 38, -31, 105, Ascii.DC4, 99, 85, 33, Ascii.FF, 125};
    private static final int[] c = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, 77, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 47, 94, 188, 99, 198, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 53, 106, 212, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, EACTags.SECURE_MESSAGING_TEMPLATE, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 239, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA};
    private int d;
    private int[][] e = null;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;

    private static int a(int i2) {
        return (((i2 & -2139062144) >>> 7) * 27) ^ ((2139062143 & i2) << 1);
    }

    private static int a(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    private void a(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        this.f = bArr[i2] & UnsignedBytes.MAX_VALUE;
        int i4 = i3 + 1;
        this.f |= (bArr[i3] & UnsignedBytes.MAX_VALUE) << 8;
        int i5 = i4 + 1;
        this.f |= (bArr[i4] & UnsignedBytes.MAX_VALUE) << Ascii.DLE;
        int i6 = i5 + 1;
        this.f |= bArr[i5] << Ascii.CAN;
        int i7 = i6 + 1;
        this.g = bArr[i6] & UnsignedBytes.MAX_VALUE;
        int i8 = i7 + 1;
        this.g = ((bArr[i7] & UnsignedBytes.MAX_VALUE) << 8) | this.g;
        int i9 = i8 + 1;
        this.g |= (bArr[i8] & UnsignedBytes.MAX_VALUE) << Ascii.DLE;
        int i10 = i9 + 1;
        this.g |= bArr[i9] << Ascii.CAN;
        int i11 = i10 + 1;
        this.h = bArr[i10] & UnsignedBytes.MAX_VALUE;
        int i12 = i11 + 1;
        this.h = ((bArr[i11] & UnsignedBytes.MAX_VALUE) << 8) | this.h;
        int i13 = i12 + 1;
        this.h |= (bArr[i12] & UnsignedBytes.MAX_VALUE) << Ascii.DLE;
        int i14 = i13 + 1;
        this.h |= bArr[i13] << Ascii.CAN;
        int i15 = i14 + 1;
        this.i = bArr[i14] & UnsignedBytes.MAX_VALUE;
        int i16 = i15 + 1;
        this.i = ((bArr[i15] & UnsignedBytes.MAX_VALUE) << 8) | this.i;
        int i17 = i16 + 1;
        this.i |= (bArr[i16] & UnsignedBytes.MAX_VALUE) << Ascii.DLE;
        this.i = (bArr[i17] << Ascii.CAN) | this.i;
    }

    private void a(int[][] iArr) {
        int i2 = this.i ^ iArr[0][3];
        int i3 = this.h ^ iArr[0][2];
        int i4 = this.g ^ iArr[0][1];
        int i5 = this.f ^ iArr[0][0];
        int i6 = 1;
        while (i6 < this.d - 1) {
            int b2 = b((int) (((a[i5 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][0];
            int b3 = b((int) (((a[i4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][1];
            int b4 = b((int) (((a[i3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i5 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][2];
            int b5 = b((int) ((((a[(i5 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8) ^ (a[i2 & 255] & UnsignedBytes.MAX_VALUE)) ^ ((a[(i4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i3 >> 24) & 255] << Ascii.CAN));
            int i7 = i6 + 1;
            int i8 = iArr[i6][3] ^ b5;
            i5 = b((int) (((a[b2 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i8 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
            int b6 = b((int) (((a[b3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i8 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
            int b7 = b((int) (((a[b4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i8 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
            int i9 = i7 + 1;
            int b8 = b((int) (((a[i8 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][3];
            i4 = b6;
            i3 = b7;
            i2 = b8;
            i6 = i9;
        }
        int b9 = b((int) (((a[i5 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][0];
        int b10 = b((int) (((a[i4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][1];
        int b11 = b((int) (((a[i3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i5 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][2];
        int b12 = b((int) ((((a[(i5 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8) ^ (a[i2 & 255] & UnsignedBytes.MAX_VALUE)) ^ ((a[(i4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i3 >> 24) & 255] << Ascii.CAN));
        int i10 = i6 + 1;
        int i11 = iArr[i6][3] ^ b12;
        this.f = iArr[i10][0] ^ ((((a[b9 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b10 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b11 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(i11 >> 24) & 255] << Ascii.CAN));
        this.g = ((((a[b10 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b11 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(i11 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b9 >> 24) & 255] << Ascii.CAN)) ^ iArr[i10][1];
        this.h = ((((a[b11 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(i11 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b9 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b10 >> 24) & 255] << Ascii.CAN)) ^ iArr[i10][2];
        this.i = iArr[i10][3] ^ ((((a[i11 & 255] & UnsignedBytes.MAX_VALUE) ^ ((a[(b9 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((a[(b10 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (a[(b11 >> 24) & 255] << Ascii.CAN));
    }

    private int[][] a(byte[] bArr, boolean z) {
        int length = bArr.length / 4;
        if ((length == 4 || length == 6 || length == 8) && length * 4 == bArr.length) {
            this.d = length + 6;
            int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{this.d + 1, 4});
            int i2 = 0;
            int i3 = 0;
            while (i2 < bArr.length) {
                iArr[i3 >> 2][i3 & 3] = (bArr[i2] & UnsignedBytes.MAX_VALUE) | ((bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i2 + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | (bArr[i2 + 3] << Ascii.CAN);
                i2 += 4;
                i3++;
            }
            int i4 = (this.d + 1) << 2;
            for (int i5 = length; i5 < i4; i5++) {
                int i6 = i5 - 1;
                int i7 = iArr[i6 >> 2][i6 & 3];
                int i8 = i5 % length;
                if (i8 == 0) {
                    i7 = d(a(i7, 8)) ^ c[(i5 / length) - 1];
                } else if (length > 6 && i8 == 4) {
                    i7 = d(i7);
                }
                int i9 = i5 - length;
                iArr[i5 >> 2][i5 & 3] = i7 ^ iArr[i9 >> 2][i9 & 3];
            }
            if (!z) {
                for (int i10 = 1; i10 < this.d; i10++) {
                    for (int i11 = 0; i11 < 4; i11++) {
                        iArr[i10][i11] = c(iArr[i10][i11]);
                    }
                }
            }
            return iArr;
        }
        throw new IllegalArgumentException("Key length not 128/192/256 bits.");
    }

    private static int b(int i2) {
        int a2 = a(i2);
        return a(i2, 24) ^ ((a2 ^ a(i2 ^ a2, 8)) ^ a(i2, 16));
    }

    private void b(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) this.f;
        int i4 = i3 + 1;
        bArr[i3] = (byte) (this.f >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (this.f >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (this.f >> 24);
        int i7 = i6 + 1;
        bArr[i6] = (byte) this.g;
        int i8 = i7 + 1;
        bArr[i7] = (byte) (this.g >> 8);
        int i9 = i8 + 1;
        bArr[i8] = (byte) (this.g >> 16);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (this.g >> 24);
        int i11 = i10 + 1;
        bArr[i10] = (byte) this.h;
        int i12 = i11 + 1;
        bArr[i11] = (byte) (this.h >> 8);
        int i13 = i12 + 1;
        bArr[i12] = (byte) (this.h >> 16);
        int i14 = i13 + 1;
        bArr[i13] = (byte) (this.h >> 24);
        int i15 = i14 + 1;
        bArr[i14] = (byte) this.i;
        int i16 = i15 + 1;
        bArr[i15] = (byte) (this.i >> 8);
        int i17 = i16 + 1;
        bArr[i16] = (byte) (this.i >> 16);
        bArr[i17] = (byte) (this.i >> 24);
    }

    private void b(int[][] iArr) {
        int i2 = this.f ^ iArr[this.d][0];
        int i3 = this.g ^ iArr[this.d][1];
        int i4 = this.h ^ iArr[this.d][2];
        int i5 = this.d - 1;
        int i6 = this.i ^ iArr[this.d][3];
        while (i5 > 1) {
            int c2 = c((((b[i2 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i6 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][0];
            int c3 = c((((b[i3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i6 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][1];
            int c4 = c((((b[i4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i6 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][2];
            byte b2 = ((b[(i3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) ^ (((b[(i4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8) ^ (b[i6 & 255] & UnsignedBytes.MAX_VALUE));
            int i7 = i5 - 1;
            int c5 = c((b[(i2 >> 24) & 255] << Ascii.CAN) ^ b2) ^ iArr[i5][3];
            int c6 = c((((b[c2 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c5 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
            int c7 = c((((b[c3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c5 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
            int c8 = c((((b[c4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
            int i8 = i7 - 1;
            int c9 = c((((b[c5 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][3];
            i3 = c7;
            i5 = i8;
            int i9 = c8;
            i6 = c9;
            i2 = c6;
            i4 = i9;
        }
        char c10 = c((((b[i2 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i6 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i4 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][0];
        int c11 = c((((b[i3 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i6 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][1];
        int c12 = c((((b[i4 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(i3 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(i2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(i6 >> 24) & 255] << Ascii.CAN)) ^ iArr[i5][2];
        int c13 = c((b[(i2 >> 24) & 255] << Ascii.CAN) ^ (((b[(i3 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) ^ (((b[(i4 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8) ^ (b[i6 & 255] & UnsignedBytes.MAX_VALUE)))) ^ iArr[i5][3];
        this.f = ((((b[c10 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c13 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c12 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c11 >> 24) & 255] << Ascii.CAN)) ^ iArr[0][0];
        this.g = ((((b[c11 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c10 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c13 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c12 >> 24) & 255] << Ascii.CAN)) ^ iArr[0][1];
        this.h = ((((b[c12 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c11 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c10 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c13 >> 24) & 255] << Ascii.CAN)) ^ iArr[0][2];
        this.i = iArr[0][3] ^ ((((b[c13 & 255] & UnsignedBytes.MAX_VALUE) ^ ((b[(c12 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8)) ^ ((b[(c11 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) ^ (b[(c10 >> 24) & 255] << Ascii.CAN));
    }

    private static int c(int i2) {
        int a2 = a(i2);
        int a3 = a(a2);
        int a4 = a(a3);
        int i3 = i2 ^ a4;
        return a(i3, 24) ^ ((a(a2 ^ i3, 8) ^ (a4 ^ (a2 ^ a3))) ^ a(a3 ^ i3, 16));
    }

    private static int d(int i2) {
        return (a[(i2 >> 24) & 255] << Ascii.CAN) | (a[i2 & 255] & UnsignedBytes.MAX_VALUE) | ((a[(i2 >> 8) & 255] & UnsignedBytes.MAX_VALUE) << 8) | ((a[(i2 >> 16) & 255] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    public String getAlgorithmName() {
        return "AES";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.e = a(((KeyParameter) cipherParameters).getKey(), z);
            this.j = z;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to AES init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.e == null) {
            throw new IllegalStateException("AES engine not initialised");
        } else if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            if (this.j) {
                a(bArr, i2);
                a(this.e);
            } else {
                a(bArr, i2);
                b(this.e);
            }
            b(bArr2, i3);
            return 16;
        }
    }

    public void reset() {
    }
}
