package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.util.Memoable;

public class MD2Digest implements ExtendedDigest, Memoable {
    private static final byte[] g = {41, 46, 67, -55, -94, -40, 124, 1, 61, 54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116, -104, -109, 43, -39, PSSSigner.TRAILER_IMPLICIT, 76, -126, -54, Ascii.RS, -101, 87, 60, -3, -44, -32, Ascii.SYN, 103, 66, 111, Ascii.CAN, -118, Ascii.ETB, -27, Ascii.DC2, -66, 78, -60, -42, -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104, 121, -111, Ascii.NAK, -78, 7, 63, -108, -62, Ascii.DLE, -119, Ascii.VT, 34, 95, 33, UnsignedBytes.MAX_POWER_OF_TWO, Ascii.DEL, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9, -105, 3, -1, Ascii.EM, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42, -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4, 107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32, -122, 91, -49, 101, -26, 45, -88, 2, Ascii.ESC, 96, 37, -83, -82, -80, -71, -10, Ascii.FS, 70, 97, 105, 52, SignedBytes.MAX_POWER_OF_TWO, 126, Ascii.SI, 85, 71, -93, 35, -35, 81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, Ascii.CR, 110, -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36, 55, -56, 108, -63, -85, -6, 36, -31, 123, 8, Ascii.FF, -67, -79, 74, 120, -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, Ascii.GS, 57, -14, -17, -73, Ascii.SO, 102, 88, -48, -28, -90, 119, 114, -8, -21, 117, 75, 10, 49, 68, 80, -76, -113, -19, Ascii.US, Ascii.SUB, -37, -103, -115, 51, -97, 17, -125, Ascii.DC4};
    private byte[] a = new byte[48];
    private int b;
    private byte[] c = new byte[16];
    private int d;
    private byte[] e = new byte[16];
    private int f;

    public MD2Digest() {
        reset();
    }

    public MD2Digest(MD2Digest mD2Digest) {
        a(mD2Digest);
    }

    private void a(MD2Digest mD2Digest) {
        System.arraycopy(mD2Digest.a, 0, this.a, 0, mD2Digest.a.length);
        this.b = mD2Digest.b;
        System.arraycopy(mD2Digest.c, 0, this.c, 0, mD2Digest.c.length);
        this.d = mD2Digest.d;
        System.arraycopy(mD2Digest.e, 0, this.e, 0, mD2Digest.e.length);
        this.f = mD2Digest.f;
    }

    public Memoable copy() {
        return new MD2Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        byte length = (byte) (this.c.length - this.d);
        for (int i2 = this.d; i2 < this.c.length; i2++) {
            this.c[i2] = length;
        }
        processCheckSum(this.c);
        processBlock(this.c);
        processBlock(this.e);
        System.arraycopy(this.a, this.b, bArr, i, 16);
        reset();
        return 16;
    }

    public String getAlgorithmName() {
        return "MD2";
    }

    public int getByteLength() {
        return 16;
    }

    public int getDigestSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void processBlock(byte[] bArr) {
        for (int i = 0; i < 16; i++) {
            this.a[i + 16] = bArr[i];
            this.a[i + 32] = (byte) (bArr[i] ^ this.a[i]);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 18; i3++) {
            int i4 = i2;
            for (int i5 = 0; i5 < 48; i5++) {
                byte[] bArr2 = this.a;
                byte b2 = (byte) (g[i4] ^ bArr2[i5]);
                bArr2[i5] = b2;
                i4 = b2 & UnsignedBytes.MAX_VALUE;
            }
            i2 = (i4 + i3) % 256;
        }
    }

    /* access modifiers changed from: protected */
    public void processCheckSum(byte[] bArr) {
        byte b2 = this.e[15];
        for (int i = 0; i < 16; i++) {
            byte[] bArr2 = this.e;
            bArr2[i] = (byte) (g[(b2 ^ bArr[i]) & UnsignedBytes.MAX_VALUE] ^ bArr2[i]);
            b2 = this.e[i];
        }
    }

    public void reset() {
        this.b = 0;
        for (int i = 0; i != this.a.length; i++) {
            this.a[i] = 0;
        }
        this.d = 0;
        for (int i2 = 0; i2 != this.c.length; i2++) {
            this.c[i2] = 0;
        }
        this.f = 0;
        for (int i3 = 0; i3 != this.e.length; i3++) {
            this.e[i3] = 0;
        }
    }

    public void reset(Memoable memoable) {
        a((MD2Digest) memoable);
    }

    public void update(byte b2) {
        byte[] bArr = this.c;
        int i = this.d;
        this.d = i + 1;
        bArr[i] = b2;
        if (this.d == 16) {
            processCheckSum(this.c);
            processBlock(this.c);
            this.d = 0;
        }
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.d != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > 16) {
            System.arraycopy(bArr, i, this.c, 0, 16);
            processCheckSum(this.c);
            processBlock(this.c);
            i2 -= 16;
            i += 16;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
