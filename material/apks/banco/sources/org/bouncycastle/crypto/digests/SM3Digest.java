package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public class SM3Digest extends GeneralDigest {
    private static final int[] f = new int[64];
    private int[] a = new int[8];
    private int[] b = new int[16];
    private int c;
    private int[] d = new int[68];
    private int[] e = new int[64];

    static {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= 16) {
                break;
            }
            f[i2] = (2043430169 >>> (32 - i2)) | (2043430169 << i2);
            i2++;
        }
        for (i = 16; i < 64; i++) {
            int i3 = i % 32;
            f[i] = (2055708042 >>> (32 - i3)) | (2055708042 << i3);
        }
    }

    public SM3Digest() {
        reset();
    }

    public SM3Digest(SM3Digest sM3Digest) {
        super((GeneralDigest) sM3Digest);
        a(sM3Digest);
    }

    private int a(int i) {
        return (i ^ ((i << 9) | (i >>> 23))) ^ ((i << 17) | (i >>> 15));
    }

    private int a(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private void a(SM3Digest sM3Digest) {
        System.arraycopy(sM3Digest.a, 0, this.a, 0, this.a.length);
        System.arraycopy(sM3Digest.b, 0, this.b, 0, this.b.length);
        this.c = sM3Digest.c;
    }

    private int b(int i) {
        return (i ^ ((i << 15) | (i >>> 17))) ^ ((i << 23) | (i >>> 9));
    }

    private int b(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    private int c(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private int d(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    public Memoable copy() {
        return new SM3Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        Pack.intToBigEndian(this.a[0], bArr, i + 0);
        Pack.intToBigEndian(this.a[1], bArr, i + 4);
        Pack.intToBigEndian(this.a[2], bArr, i + 8);
        Pack.intToBigEndian(this.a[3], bArr, i + 12);
        Pack.intToBigEndian(this.a[4], bArr, i + 16);
        Pack.intToBigEndian(this.a[5], bArr, i + 20);
        Pack.intToBigEndian(this.a[6], bArr, i + 24);
        Pack.intToBigEndian(this.a[7], bArr, i + 28);
        reset();
        return 32;
    }

    public String getAlgorithmName() {
        return "SM3";
    }

    public int getDigestSize() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        int i;
        int i2;
        int i3 = 0;
        while (true) {
            i = 16;
            if (i3 >= 16) {
                break;
            }
            this.d[i3] = this.b[i3];
            i3++;
        }
        for (int i4 = 16; i4 < 68; i4++) {
            int i5 = this.d[i4 - 3];
            int i6 = (i5 >>> 17) | (i5 << 15);
            int i7 = this.d[i4 - 13];
            this.d[i4] = (b(i6 ^ (this.d[i4 - 16] ^ this.d[i4 - 9])) ^ ((i7 >>> 25) | (i7 << 7))) ^ this.d[i4 - 6];
        }
        int i8 = 0;
        while (true) {
            i2 = 64;
            if (i8 >= 64) {
                break;
            }
            this.e[i8] = this.d[i8] ^ this.d[i8 + 4];
            i8++;
        }
        int i9 = this.a[0];
        int i10 = this.a[1];
        int i11 = this.a[2];
        int i12 = this.a[3];
        int i13 = this.a[4];
        int i14 = this.a[5];
        int i15 = this.a[6];
        int i16 = this.a[7];
        int i17 = i14;
        int i18 = i13;
        int i19 = i12;
        int i20 = i11;
        int i21 = i10;
        int i22 = i9;
        int i23 = 0;
        while (i23 < i) {
            int i24 = (i22 << 12) | (i22 >>> 20);
            int i25 = i24 + i18 + f[i23];
            int i26 = (i25 << 7) | (i25 >>> 25);
            int a2 = a(i22, i21, i20) + i19 + (i26 ^ i24) + this.e[i23];
            int i27 = (i21 << 9) | (i21 >>> 23);
            i23++;
            i16 = i15;
            i15 = (i17 << 19) | (i17 >>> 13);
            i17 = i18;
            i18 = a(c(i18, i17, i15) + i16 + i26 + this.d[i23]);
            i = 16;
            int i28 = i27;
            i21 = i22;
            i22 = a2;
            i19 = i20;
            i20 = i28;
        }
        int i29 = i21;
        int i30 = 16;
        int i31 = i22;
        int i32 = i19;
        int i33 = i20;
        int i34 = i17;
        int i35 = i18;
        while (i30 < i2) {
            int i36 = (i31 << 12) | (i31 >>> 20);
            int i37 = i36 + i35 + f[i30];
            int i38 = (i37 << 7) | (i37 >>> 25);
            int b2 = b(i31, i29, i33) + i32 + (i38 ^ i36) + this.e[i30];
            int d2 = d(i35, i34, i15) + i16 + i38 + this.d[i30];
            int i39 = (i29 >>> 23) | (i29 << 9);
            i30++;
            i16 = i15;
            i15 = (i34 << 19) | (i34 >>> 13);
            i2 = 64;
            int i40 = i33;
            i33 = i39;
            i29 = i31;
            i31 = b2;
            i32 = i40;
            int i41 = i35;
            i35 = a(d2);
            i34 = i41;
        }
        int[] iArr = this.a;
        iArr[0] = iArr[0] ^ i31;
        int[] iArr2 = this.a;
        iArr2[1] = i29 ^ iArr2[1];
        int[] iArr3 = this.a;
        iArr3[2] = iArr3[2] ^ i33;
        int[] iArr4 = this.a;
        iArr4[3] = iArr4[3] ^ i32;
        int[] iArr5 = this.a;
        iArr5[4] = iArr5[4] ^ i35;
        int[] iArr6 = this.a;
        iArr6[5] = iArr6[5] ^ i34;
        int[] iArr7 = this.a;
        iArr7[6] = iArr7[6] ^ i15;
        int[] iArr8 = this.a;
        iArr8[7] = iArr8[7] ^ i16;
        this.c = 0;
    }

    /* access modifiers changed from: protected */
    public void processLength(long j) {
        if (this.c > 14) {
            this.b[this.c] = 0;
            this.c++;
            processBlock();
        }
        while (this.c < 14) {
            this.b[this.c] = 0;
            this.c++;
        }
        int[] iArr = this.b;
        int i = this.c;
        this.c = i + 1;
        iArr[i] = (int) (j >>> 32);
        int[] iArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        iArr2[i2] = (int) j;
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        this.b[this.c] = (bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
        this.c++;
        if (this.c >= 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.a[0] = 1937774191;
        this.a[1] = 1226093241;
        this.a[2] = 388252375;
        this.a[3] = -628488704;
        this.a[4] = -1452330820;
        this.a[5] = 372324522;
        this.a[6] = -477237683;
        this.a[7] = -1325724082;
        this.c = 0;
    }

    public void reset(Memoable memoable) {
        SM3Digest sM3Digest = (SM3Digest) memoable;
        super.copyIn(sM3Digest);
        a(sM3Digest);
    }
}
