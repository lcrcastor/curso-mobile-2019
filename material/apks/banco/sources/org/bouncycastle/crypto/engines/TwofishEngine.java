package org.bouncycastle.crypto.engines;

import android.support.v4.view.InputDeviceCompat;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.crypto.tls.CipherSuite;

public final class TwofishEngine implements BlockCipher {
    private static final byte[][] a = {new byte[]{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, UnsignedBytes.MAX_POWER_OF_TWO, 120, -28, -35, -47, 56, Ascii.CR, -58, 53, -104, Ascii.CAN, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, -14, -48, -117, 48, -124, 84, -33, 35, Ascii.EM, 91, 61, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, Ascii.SYN, Ascii.FF, -29, 97, -64, -116, 58, -11, 115, 44, 37, Ascii.VT, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, Ascii.FS, Ascii.RS, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, Ascii.GS, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, Ascii.ESC, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Ascii.DEL, -43, Ascii.SUB, 75, Ascii.SO, -89, 90, 40, Ascii.DC4, 63, 41, -120, 60, 76, 2, -72, -38, -80, Ascii.ETB, 85, Ascii.US, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, Ascii.NAK, 34, Ascii.DC2, 88, 7, -103, 52, 110, 80, -34, 104, 101, PSSSigner.TRAILER_IMPLICIT, -37, -8, -56, -88, 43, SignedBytes.MAX_POWER_OF_TWO, -36, -2, 50, -92, -54, Ascii.DLE, 33, -16, -45, 93, Ascii.SI, 0, 111, -99, 54, 66, 74, 94, -63, -32}, new byte[]{117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, Ascii.SI, -8, Ascii.ESC, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, PSSSigner.TRAILER_IMPLICIT, -99, 109, -63, -79, Ascii.SO, UnsignedBytes.MAX_POWER_OF_TWO, 93, -46, -43, -96, -124, 7, Ascii.DC4, -75, -112, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, Ascii.DLE, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, SignedBytes.MAX_POWER_OF_TWO, -25, 43, -30, 121, Ascii.FF, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, Ascii.ETB, 102, -108, -95, Ascii.GS, 61, -16, -34, -77, Ascii.VT, 114, -89, Ascii.FS, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, Ascii.SUB, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, Ascii.CAN, 35, -35, Ascii.US, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, Ascii.EM, -115, -27, -104, 87, 103, Ascii.DEL, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, Ascii.NAK, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, Ascii.DC2, -94, Ascii.CR, 82, -69, 2, 47, -87, -41, 97, Ascii.RS, -76, 80, 4, -10, -62, Ascii.SYN, 37, -122, 86, 85, 9, -66, -111}};
    private boolean b = false;
    private int[] c = new int[256];
    private int[] d = new int[256];
    private int[] e = new int[256];
    private int[] f = new int[256];
    private int[] g;
    private int[] h;
    private int i = 0;
    private byte[] j = null;

    public TwofishEngine() {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        int[] iArr3 = new int[2];
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = a[0][i2] & UnsignedBytes.MAX_VALUE;
            iArr[0] = i3;
            iArr2[0] = d(i3) & 255;
            iArr3[0] = e(i3) & 255;
            int i4 = a[1][i2] & UnsignedBytes.MAX_VALUE;
            iArr[1] = i4;
            iArr2[1] = d(i4) & 255;
            iArr3[1] = e(i4) & 255;
            this.c[i2] = iArr[1] | (iArr2[1] << 8) | (iArr3[1] << 16) | (iArr3[1] << 24);
            this.d[i2] = iArr3[0] | (iArr3[0] << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            this.e[i2] = (iArr3[1] << 24) | iArr2[1] | (iArr3[1] << 8) | (iArr[1] << 16);
            this.f[i2] = iArr2[0] | (iArr[0] << 8) | (iArr3[0] << 16) | (iArr2[0] << 24);
        }
    }

    private int a(int i2) {
        int i3 = (i2 >>> 24) & 255;
        int i4 = 0;
        int i5 = ((i3 << 1) ^ ((i3 & 128) != 0 ? 333 : 0)) & 255;
        int i6 = i3 >>> 1;
        if ((i3 & 1) != 0) {
            i4 = CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256;
        }
        int i7 = (i6 ^ i4) ^ i5;
        return ((((i2 << 8) ^ (i7 << 24)) ^ (i5 << 16)) ^ (i7 << 8)) ^ i3;
    }

    private int a(int i2, int i3) {
        int i4 = i3;
        for (int i5 = 0; i5 < 4; i5++) {
            i4 = a(i4);
        }
        int i6 = i2 ^ i4;
        for (int i7 = 0; i7 < 4; i7++) {
            i6 = a(i6);
        }
        return i6;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x006b, code lost:
        return r12 ^ r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x00a0, code lost:
        r0 = f(r7) ^ (a[1][r0] & com.google.common.primitives.UnsignedBytes.MAX_VALUE);
        r1 = g(r7) ^ (a[1][r1] & com.google.common.primitives.UnsignedBytes.MAX_VALUE);
        r2 = h(r7) ^ (a[0][r2] & com.google.common.primitives.UnsignedBytes.MAX_VALUE);
        r11 = (a[0][r11] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ i(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x00d4, code lost:
        r12 = (r10.c[(a[0][(a[0][r0] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ f(r6)] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ f(r4)] ^ r10.d[(a[0][(a[1][r1] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ g(r6)] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ g(r4)]) ^ r10.e[(a[1][(a[0][r2] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ h(r6)] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ h(r4)];
        r11 = r10.f[(a[1][(a[1][r11] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ i(r6)] & com.google.common.primitives.UnsignedBytes.MAX_VALUE) ^ i(r4)];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(int r11, int[] r12) {
        /*
            r10 = this;
            int r0 = r10.f(r11)
            int r1 = r10.g(r11)
            int r2 = r10.h(r11)
            int r11 = r10.i(r11)
            r3 = 0
            r4 = r12[r3]
            r5 = 1
            r6 = r12[r5]
            r7 = 2
            r7 = r12[r7]
            r8 = 3
            r12 = r12[r8]
            int r9 = r10.i
            r8 = r8 & r9
            switch(r8) {
                case 0: goto L_0x006c;
                case 1: goto L_0x0023;
                case 2: goto L_0x00d4;
                case 3: goto L_0x00a0;
                default: goto L_0x0022;
            }
        L_0x0022:
            return r3
        L_0x0023:
            int[] r12 = r10.c
            byte[][] r6 = a
            r6 = r6[r3]
            byte r0 = r6[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r6 = r10.f(r4)
            r0 = r0 ^ r6
            r12 = r12[r0]
            int[] r0 = r10.d
            byte[][] r6 = a
            r3 = r6[r3]
            byte r1 = r3[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r3 = r10.g(r4)
            r1 = r1 ^ r3
            r0 = r0[r1]
            r12 = r12 ^ r0
            int[] r0 = r10.e
            byte[][] r1 = a
            r1 = r1[r5]
            byte r1 = r1[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r2 = r10.h(r4)
            r1 = r1 ^ r2
            r0 = r0[r1]
            r12 = r12 ^ r0
            int[] r0 = r10.f
            byte[][] r1 = a
            r1 = r1[r5]
            byte r11 = r1[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r1 = r10.i(r4)
            r11 = r11 ^ r1
            r11 = r0[r11]
        L_0x0069:
            r3 = r12 ^ r11
            return r3
        L_0x006c:
            byte[][] r8 = a
            r8 = r8[r5]
            byte r0 = r8[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r8 = r10.f(r12)
            r0 = r0 ^ r8
            byte[][] r8 = a
            r8 = r8[r3]
            byte r1 = r8[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r8 = r10.g(r12)
            r1 = r1 ^ r8
            byte[][] r8 = a
            r8 = r8[r3]
            byte r2 = r8[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r8 = r10.h(r12)
            r2 = r2 ^ r8
            byte[][] r8 = a
            r8 = r8[r5]
            byte r11 = r8[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r12 = r10.i(r12)
            r11 = r11 ^ r12
        L_0x00a0:
            byte[][] r12 = a
            r12 = r12[r5]
            byte r12 = r12[r0]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r0 = r10.f(r7)
            r0 = r0 ^ r12
            byte[][] r12 = a
            r12 = r12[r5]
            byte r12 = r12[r1]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r1 = r10.g(r7)
            r1 = r1 ^ r12
            byte[][] r12 = a
            r12 = r12[r3]
            byte r12 = r12[r2]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r2 = r10.h(r7)
            r2 = r2 ^ r12
            byte[][] r12 = a
            r12 = r12[r3]
            byte r11 = r12[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r12 = r10.i(r7)
            r11 = r11 ^ r12
        L_0x00d4:
            int[] r12 = r10.c
            byte[][] r7 = a
            r7 = r7[r3]
            byte[][] r8 = a
            r8 = r8[r3]
            byte r0 = r8[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r8 = r10.f(r6)
            r0 = r0 ^ r8
            byte r0 = r7[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r7 = r10.f(r4)
            r0 = r0 ^ r7
            r12 = r12[r0]
            int[] r0 = r10.d
            byte[][] r7 = a
            r7 = r7[r3]
            byte[][] r8 = a
            r8 = r8[r5]
            byte r1 = r8[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r8 = r10.g(r6)
            r1 = r1 ^ r8
            byte r1 = r7[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r7 = r10.g(r4)
            r1 = r1 ^ r7
            r0 = r0[r1]
            r12 = r12 ^ r0
            int[] r0 = r10.e
            byte[][] r1 = a
            r1 = r1[r5]
            byte[][] r7 = a
            r3 = r7[r3]
            byte r2 = r3[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r3 = r10.h(r6)
            r2 = r2 ^ r3
            byte r1 = r1[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r2 = r10.h(r4)
            r1 = r1 ^ r2
            r0 = r0[r1]
            r12 = r12 ^ r0
            int[] r0 = r10.f
            byte[][] r1 = a
            r1 = r1[r5]
            byte[][] r2 = a
            r2 = r2[r5]
            byte r11 = r2[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r2 = r10.i(r6)
            r11 = r11 ^ r2
            byte r11 = r1[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r1 = r10.i(r4)
            r11 = r11 ^ r1
            r11 = r0[r11]
            goto L_0x0069
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.TwofishEngine.a(int, int[]):int");
    }

    private int a(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i2] & UnsignedBytes.MAX_VALUE) | ((bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i2 + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    private void a(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >> 8);
        bArr[i3 + 2] = (byte) (i2 >> 16);
        bArr[i3 + 3] = (byte) (i2 >> 24);
    }

    /* JADX WARNING: type inference failed for: r12v0 */
    /* JADX WARNING: type inference failed for: r11v0 */
    /* JADX WARNING: type inference failed for: r10v0 */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r12v5 */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r9v15 */
    /* JADX WARNING: type inference failed for: r9v28 */
    /* JADX WARNING: type inference failed for: r10v23 */
    /* JADX WARNING: type inference failed for: r11v29 */
    /* JADX WARNING: type inference failed for: r12v29 */
    /* JADX WARNING: type inference failed for: r10v24 */
    /* JADX WARNING: type inference failed for: r11v30 */
    /* JADX WARNING: type inference failed for: r12v30 */
    /* JADX WARNING: type inference failed for: r9v40 */
    /* JADX WARNING: type inference failed for: r10v30 */
    /* JADX WARNING: type inference failed for: r11v32 */
    /* JADX WARNING: type inference failed for: r10v31 */
    /* JADX WARNING: type inference failed for: r11v33 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(byte[] r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = 4
            int[] r3 = new int[r2]
            int[] r4 = new int[r2]
            int[] r5 = new int[r2]
            r6 = 40
            int[] r6 = new int[r6]
            r0.g = r6
            int r6 = r0.i
            r7 = 1
            if (r6 >= r7) goto L_0x001e
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Key size less than 64 bits"
            r1.<init>(r2)
            throw r1
        L_0x001e:
            int r6 = r0.i
            if (r6 <= r2) goto L_0x002a
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Key size larger than 256 bits"
            r1.<init>(r2)
            throw r1
        L_0x002a:
            r6 = 0
            r8 = 0
        L_0x002c:
            int r9 = r0.i
            if (r8 >= r9) goto L_0x0050
            int r9 = r8 * 8
            int r10 = r0.a(r1, r9)
            r3[r8] = r10
            int r9 = r9 + r2
            int r9 = r0.a(r1, r9)
            r4[r8] = r9
            int r9 = r0.i
            int r9 = r9 - r7
            int r9 = r9 - r8
            r10 = r3[r8]
            r11 = r4[r8]
            int r10 = r0.a(r10, r11)
            r5[r9] = r10
            int r8 = r8 + 1
            goto L_0x002c
        L_0x0050:
            r1 = 0
        L_0x0051:
            r2 = 20
            if (r1 >= r2) goto L_0x0080
            r2 = 33686018(0x2020202, float:9.551468E-38)
            int r2 = r2 * r1
            int r8 = r0.a(r2, r3)
            r9 = 16843009(0x1010101, float:2.3694278E-38)
            int r2 = r2 + r9
            int r2 = r0.a(r2, r4)
            int r9 = r2 << 8
            int r2 = r2 >>> 24
            r2 = r2 | r9
            int r8 = r8 + r2
            int[] r9 = r0.g
            int r10 = r1 * 2
            r9[r10] = r8
            int r8 = r8 + r2
            int[] r2 = r0.g
            int r10 = r10 + r7
            int r9 = r8 << 9
            int r8 = r8 >>> 23
            r8 = r8 | r9
            r2[r10] = r8
            int r1 = r1 + 1
            goto L_0x0051
        L_0x0080:
            r1 = r5[r6]
            r2 = r5[r7]
            r3 = 2
            r3 = r5[r3]
            r4 = 3
            r5 = r5[r4]
            r8 = 1024(0x400, float:1.435E-42)
            int[] r8 = new int[r8]
            r0.h = r8
            r8 = 0
        L_0x0091:
            r9 = 256(0x100, float:3.59E-43)
            if (r8 >= r9) goto L_0x0208
            int r9 = r0.i
            r9 = r9 & r4
            switch(r9) {
                case 0: goto L_0x0107;
                case 1: goto L_0x00a9;
                case 2: goto L_0x00a3;
                case 3: goto L_0x009d;
                default: goto L_0x009b;
            }
        L_0x009b:
            goto L_0x0204
        L_0x009d:
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            goto L_0x013b
        L_0x00a3:
            r9 = r8
            r10 = r9
            r11 = r10
            r12 = r11
            goto L_0x016f
        L_0x00a9:
            int[] r9 = r0.h
            int r10 = r8 * 2
            int[] r11 = r0.c
            byte[][] r12 = a
            r12 = r12[r6]
            byte r12 = r12[r8]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r13 = r0.f(r1)
            r12 = r12 ^ r13
            r11 = r11[r12]
            r9[r10] = r11
            int[] r9 = r0.h
            int r11 = r10 + 1
            int[] r12 = r0.d
            byte[][] r13 = a
            r13 = r13[r6]
            byte r13 = r13[r8]
            r13 = r13 & 255(0xff, float:3.57E-43)
            int r14 = r0.g(r1)
            r13 = r13 ^ r14
            r12 = r12[r13]
            r9[r11] = r12
            int[] r9 = r0.h
            int r11 = r10 + 512
            int[] r12 = r0.e
            byte[][] r13 = a
            r13 = r13[r7]
            byte r13 = r13[r8]
            r13 = r13 & 255(0xff, float:3.57E-43)
            int r14 = r0.h(r1)
            r13 = r13 ^ r14
            r12 = r12[r13]
            r9[r11] = r12
            int[] r9 = r0.h
            int r10 = r10 + 513
            int[] r11 = r0.f
            byte[][] r12 = a
            r12 = r12[r7]
            byte r12 = r12[r8]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r13 = r0.i(r1)
            r12 = r12 ^ r13
            r11 = r11[r12]
            r9[r10] = r11
            goto L_0x0204
        L_0x0107:
            byte[][] r9 = a
            r9 = r9[r7]
            byte r9 = r9[r8]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r10 = r0.f(r5)
            r9 = r9 ^ r10
            byte[][] r10 = a
            r10 = r10[r6]
            byte r10 = r10[r8]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r11 = r0.g(r5)
            r10 = r10 ^ r11
            byte[][] r11 = a
            r11 = r11[r6]
            byte r11 = r11[r8]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r12 = r0.h(r5)
            r11 = r11 ^ r12
            byte[][] r12 = a
            r12 = r12[r7]
            byte r12 = r12[r8]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r13 = r0.i(r5)
            r12 = r12 ^ r13
        L_0x013b:
            byte[][] r13 = a
            r13 = r13[r7]
            byte r9 = r13[r9]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r13 = r0.f(r3)
            r9 = r9 ^ r13
            byte[][] r13 = a
            r13 = r13[r7]
            byte r10 = r13[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r13 = r0.g(r3)
            r10 = r10 ^ r13
            byte[][] r13 = a
            r13 = r13[r6]
            byte r11 = r13[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r13 = r0.h(r3)
            r11 = r11 ^ r13
            byte[][] r13 = a
            r13 = r13[r6]
            byte r12 = r13[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r13 = r0.i(r3)
            r12 = r12 ^ r13
        L_0x016f:
            int[] r13 = r0.h
            int r14 = r8 * 2
            int[] r15 = r0.c
            byte[][] r16 = a
            r16 = r16[r6]
            byte[][] r17 = a
            r17 = r17[r6]
            byte r9 = r17[r9]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r17 = r0.f(r2)
            r9 = r9 ^ r17
            byte r9 = r16[r9]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r16 = r0.f(r1)
            r9 = r9 ^ r16
            r9 = r15[r9]
            r13[r14] = r9
            int[] r9 = r0.h
            int r13 = r14 + 1
            int[] r15 = r0.d
            byte[][] r16 = a
            r16 = r16[r6]
            byte[][] r17 = a
            r17 = r17[r7]
            byte r10 = r17[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r17 = r0.g(r2)
            r10 = r10 ^ r17
            byte r10 = r16[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r16 = r0.g(r1)
            r10 = r10 ^ r16
            r10 = r15[r10]
            r9[r13] = r10
            int[] r9 = r0.h
            int r10 = r14 + 512
            int[] r13 = r0.e
            byte[][] r15 = a
            r15 = r15[r7]
            byte[][] r16 = a
            r16 = r16[r6]
            byte r11 = r16[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r16 = r0.h(r2)
            r11 = r11 ^ r16
            byte r11 = r15[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r15 = r0.h(r1)
            r11 = r11 ^ r15
            r11 = r13[r11]
            r9[r10] = r11
            int[] r9 = r0.h
            int r14 = r14 + 513
            int[] r10 = r0.f
            byte[][] r11 = a
            r11 = r11[r7]
            byte[][] r13 = a
            r13 = r13[r7]
            byte r12 = r13[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r13 = r0.i(r2)
            r12 = r12 ^ r13
            byte r11 = r11[r12]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r12 = r0.i(r1)
            r11 = r11 ^ r12
            r10 = r10[r11]
            r9[r14] = r10
        L_0x0204:
            int r8 = r8 + 1
            goto L_0x0091
        L_0x0208:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.TwofishEngine.a(byte[]):void");
    }

    private void a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        int a2 = a(bArr, i2) ^ this.g[0];
        int a3 = a(bArr, i2 + 4) ^ this.g[1];
        int a4 = a(bArr, i2 + 8) ^ this.g[2];
        int a5 = a(bArr, i2 + 12) ^ this.g[3];
        int i5 = 8;
        while (i4 < 16) {
            int j2 = j(a2);
            int k = k(a3);
            int i6 = i5 + 1;
            int i7 = a4 ^ ((j2 + k) + this.g[i5]);
            a4 = (i7 >>> 1) | (i7 << 31);
            int i8 = j2 + (k * 2);
            int i9 = i6 + 1;
            a5 = ((a5 >>> 31) | (a5 << 1)) ^ (i8 + this.g[i6]);
            int j3 = j(a4);
            int k2 = k(a5);
            int i10 = i9 + 1;
            int i11 = a2 ^ ((j3 + k2) + this.g[i9]);
            a2 = (i11 << 31) | (i11 >>> 1);
            int i12 = (a3 >>> 31) | (a3 << 1);
            a3 = i12 ^ ((j3 + (k2 * 2)) + this.g[i10]);
            i4 += 2;
            i5 = i10 + 1;
        }
        a(this.g[4] ^ a4, bArr2, i3);
        a(a5 ^ this.g[5], bArr2, i3 + 4);
        a(this.g[6] ^ a2, bArr2, i3 + 8);
        a(this.g[7] ^ a3, bArr2, i3 + 12);
    }

    private int b(int i2) {
        return ((i2 & 1) != 0 ? 180 : 0) ^ (i2 >> 1);
    }

    private void b(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int a2 = a(bArr, i2) ^ this.g[4];
        int a3 = a(bArr, i2 + 4) ^ this.g[5];
        int i4 = 39;
        int a4 = a(bArr, i2 + 8) ^ this.g[6];
        int a5 = a(bArr, i2 + 12) ^ this.g[7];
        int i5 = 0;
        while (i5 < 16) {
            int j2 = j(a2);
            int k = k(a3);
            int i6 = i4 - 1;
            int i7 = a5 ^ (((k * 2) + j2) + this.g[i4]);
            int i8 = j2 + k;
            int i9 = i6 - 1;
            a4 = ((a4 << 1) | (a4 >>> 31)) ^ (i8 + this.g[i6]);
            a5 = (i7 << 31) | (i7 >>> 1);
            int j3 = j(a4);
            int k2 = k(a5);
            int i10 = i9 - 1;
            int i11 = a3 ^ (((k2 * 2) + j3) + this.g[i9]);
            int i12 = (a2 >>> 31) | (a2 << 1);
            a2 = i12 ^ ((j3 + k2) + this.g[i10]);
            a3 = (i11 << 31) | (i11 >>> 1);
            i5 += 2;
            i4 = i10 - 1;
        }
        a(this.g[0] ^ a4, bArr2, i3);
        a(this.g[1] ^ a5, bArr2, i3 + 4);
        a(this.g[2] ^ a2, bArr2, i3 + 8);
        a(this.g[3] ^ a3, bArr2, i3 + 12);
    }

    private int c(int i2) {
        int i3 = 0;
        int i4 = (i2 >> 2) ^ ((i2 & 2) != 0 ? 180 : 0);
        if ((i2 & 1) != 0) {
            i3 = 90;
        }
        return i4 ^ i3;
    }

    private int d(int i2) {
        return i2 ^ c(i2);
    }

    private int e(int i2) {
        return c(i2) ^ (b(i2) ^ i2);
    }

    private int f(int i2) {
        return i2 & 255;
    }

    private int g(int i2) {
        return (i2 >>> 8) & 255;
    }

    private int h(int i2) {
        return (i2 >>> 16) & 255;
    }

    private int i(int i2) {
        return (i2 >>> 24) & 255;
    }

    private int j(int i2) {
        return this.h[(((i2 >>> 24) & 255) * 2) + InputDeviceCompat.SOURCE_DPAD] ^ ((this.h[((i2 & 255) * 2) + 0] ^ this.h[(((i2 >>> 8) & 255) * 2) + 1]) ^ this.h[(((i2 >>> 16) & 255) * 2) + 512]);
    }

    private int k(int i2) {
        return this.h[(((i2 >>> 16) & 255) * 2) + InputDeviceCompat.SOURCE_DPAD] ^ ((this.h[(((i2 >>> 24) & 255) * 2) + 0] ^ this.h[((i2 & 255) * 2) + 1]) ^ this.h[(((i2 >>> 8) & 255) * 2) + 512]);
    }

    public String getAlgorithmName() {
        return "Twofish";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.b = z;
            this.j = ((KeyParameter) cipherParameters).getKey();
            this.i = this.j.length / 8;
            a(this.j);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to Twofish init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.j == null) {
            throw new IllegalStateException("Twofish not initialised");
        } else if (i2 + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            if (this.b) {
                a(bArr, i2, bArr2, i3);
            } else {
                b(bArr, i2, bArr2, i3);
            }
            return 16;
        }
    }

    public void reset() {
        if (this.j != null) {
            a(this.j);
        }
    }
}
