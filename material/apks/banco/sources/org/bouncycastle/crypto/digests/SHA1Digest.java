package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public class SHA1Digest extends GeneralDigest implements EncodableDigest {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int[] f = new int[80];
    private int g;

    public SHA1Digest() {
        reset();
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super((GeneralDigest) sHA1Digest);
        a(sHA1Digest);
    }

    public SHA1Digest(byte[] bArr) {
        super(bArr);
        this.a = Pack.bigEndianToInt(bArr, 16);
        this.b = Pack.bigEndianToInt(bArr, 20);
        this.c = Pack.bigEndianToInt(bArr, 24);
        this.d = Pack.bigEndianToInt(bArr, 28);
        this.e = Pack.bigEndianToInt(bArr, 32);
        this.g = Pack.bigEndianToInt(bArr, 36);
        for (int i = 0; i != this.g; i++) {
            this.f[i] = Pack.bigEndianToInt(bArr, (i * 4) + 40);
        }
    }

    private int a(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    private void a(SHA1Digest sHA1Digest) {
        this.a = sHA1Digest.a;
        this.b = sHA1Digest.b;
        this.c = sHA1Digest.c;
        this.d = sHA1Digest.d;
        this.e = sHA1Digest.e;
        System.arraycopy(sHA1Digest.f, 0, this.f, 0, sHA1Digest.f.length);
        this.g = sHA1Digest.g;
    }

    private int b(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private int c(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    public Memoable copy() {
        return new SHA1Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        Pack.intToBigEndian(this.a, bArr, i);
        Pack.intToBigEndian(this.b, bArr, i + 4);
        Pack.intToBigEndian(this.c, bArr, i + 8);
        Pack.intToBigEndian(this.d, bArr, i + 12);
        Pack.intToBigEndian(this.e, bArr, i + 16);
        reset();
        return 20;
    }

    public String getAlgorithmName() {
        return CommonUtils.SHA1_INSTANCE;
    }

    public int getDigestSize() {
        return 20;
    }

    public byte[] getEncodedState() {
        byte[] bArr = new byte[((this.g * 4) + 40)];
        super.populateState(bArr);
        Pack.intToBigEndian(this.a, bArr, 16);
        Pack.intToBigEndian(this.b, bArr, 20);
        Pack.intToBigEndian(this.c, bArr, 24);
        Pack.intToBigEndian(this.d, bArr, 28);
        Pack.intToBigEndian(this.e, bArr, 32);
        Pack.intToBigEndian(this.g, bArr, 36);
        for (int i = 0; i != this.g; i++) {
            Pack.intToBigEndian(this.f[i], bArr, (i * 4) + 40);
        }
        return bArr;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        for (int i = 16; i < 80; i++) {
            int i2 = ((this.f[i - 3] ^ this.f[i - 8]) ^ this.f[i - 14]) ^ this.f[i - 16];
            this.f[i] = (i2 >>> 31) | (i2 << 1);
        }
        int i3 = this.a;
        int i4 = this.b;
        int i5 = this.c;
        int i6 = this.d;
        int i7 = this.e;
        int i8 = i6;
        int i9 = 0;
        int i10 = i5;
        int i11 = i4;
        int i12 = i3;
        int i13 = 0;
        while (i13 < 4) {
            int i14 = i9 + 1;
            int a2 = i7 + ((i12 << 5) | (i12 >>> 27)) + a(i11, i10, i8) + this.f[i9] + 1518500249;
            int i15 = (i11 >>> 2) | (i11 << 30);
            int i16 = i14 + 1;
            int a3 = i8 + ((a2 << 5) | (a2 >>> 27)) + a(i12, i15, i10) + this.f[i14] + 1518500249;
            int i17 = (i12 >>> 2) | (i12 << 30);
            int i18 = i16 + 1;
            int a4 = i10 + ((a3 << 5) | (a3 >>> 27)) + a(a2, i17, i15) + this.f[i16] + 1518500249;
            i7 = (a2 >>> 2) | (a2 << 30);
            int i19 = i18 + 1;
            i11 = i15 + ((a4 << 5) | (a4 >>> 27)) + a(a3, i7, i17) + this.f[i18] + 1518500249;
            i8 = (a3 >>> 2) | (a3 << 30);
            i12 = i17 + ((i11 << 5) | (i11 >>> 27)) + a(a4, i8, i7) + this.f[i19] + 1518500249;
            i10 = (a4 >>> 2) | (a4 << 30);
            i13++;
            i9 = i19 + 1;
        }
        int i20 = 0;
        while (i20 < 4) {
            int i21 = i9 + 1;
            int b2 = i7 + ((i12 << 5) | (i12 >>> 27)) + b(i11, i10, i8) + this.f[i9] + 1859775393;
            int i22 = (i11 >>> 2) | (i11 << 30);
            int i23 = i21 + 1;
            int b3 = i8 + ((b2 << 5) | (b2 >>> 27)) + b(i12, i22, i10) + this.f[i21] + 1859775393;
            int i24 = (i12 >>> 2) | (i12 << 30);
            int i25 = i23 + 1;
            int b4 = i10 + ((b3 << 5) | (b3 >>> 27)) + b(b2, i24, i22) + this.f[i23] + 1859775393;
            i7 = (b2 >>> 2) | (b2 << 30);
            int i26 = i25 + 1;
            i11 = i22 + ((b4 << 5) | (b4 >>> 27)) + b(b3, i7, i24) + this.f[i25] + 1859775393;
            i8 = (b3 >>> 2) | (b3 << 30);
            i12 = i24 + ((i11 << 5) | (i11 >>> 27)) + b(b4, i8, i7) + this.f[i26] + 1859775393;
            i10 = (b4 >>> 2) | (b4 << 30);
            i20++;
            i9 = i26 + 1;
        }
        int i27 = 0;
        while (i27 < 4) {
            int i28 = i9 + 1;
            int c2 = i7 + (((((i12 << 5) | (i12 >>> 27)) + c(i11, i10, i8)) + this.f[i9]) - 1894007588);
            int i29 = (i11 >>> 2) | (i11 << 30);
            int i30 = i28 + 1;
            int c3 = i8 + (((((c2 << 5) | (c2 >>> 27)) + c(i12, i29, i10)) + this.f[i28]) - 1894007588);
            int i31 = (i12 >>> 2) | (i12 << 30);
            int i32 = i30 + 1;
            int c4 = i10 + (((((c3 << 5) | (c3 >>> 27)) + c(c2, i31, i29)) + this.f[i30]) - 1894007588);
            i7 = (c2 >>> 2) | (c2 << 30);
            int i33 = i32 + 1;
            i11 = i29 + (((((c4 << 5) | (c4 >>> 27)) + c(c3, i7, i31)) + this.f[i32]) - 1894007588);
            i8 = (c3 >>> 2) | (c3 << 30);
            i12 = i31 + (((((i11 << 5) | (i11 >>> 27)) + c(c4, i8, i7)) + this.f[i33]) - 1894007588);
            i10 = (c4 >>> 2) | (c4 << 30);
            i27++;
            i9 = i33 + 1;
        }
        int i34 = 0;
        while (i34 <= 3) {
            int i35 = i9 + 1;
            int b5 = i7 + (((((i12 << 5) | (i12 >>> 27)) + b(i11, i10, i8)) + this.f[i9]) - 899497514);
            int i36 = (i11 >>> 2) | (i11 << 30);
            int i37 = i35 + 1;
            int b6 = i8 + (((((b5 << 5) | (b5 >>> 27)) + b(i12, i36, i10)) + this.f[i35]) - 899497514);
            int i38 = (i12 >>> 2) | (i12 << 30);
            int i39 = i37 + 1;
            int b7 = i10 + (((((b6 << 5) | (b6 >>> 27)) + b(b5, i38, i36)) + this.f[i37]) - 899497514);
            i7 = (b5 >>> 2) | (b5 << 30);
            int i40 = i39 + 1;
            i11 = i36 + (((((b7 << 5) | (b7 >>> 27)) + b(b6, i7, i38)) + this.f[i39]) - 899497514);
            i8 = (b6 >>> 2) | (b6 << 30);
            i12 = i38 + (((((i11 << 5) | (i11 >>> 27)) + b(b7, i8, i7)) + this.f[i40]) - 899497514);
            i10 = (b7 >>> 2) | (b7 << 30);
            i34++;
            i9 = i40 + 1;
        }
        this.a += i12;
        this.b += i11;
        this.c += i10;
        this.d += i8;
        this.e += i7;
        this.g = 0;
        for (int i41 = 0; i41 < 16; i41++) {
            this.f[i41] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j) {
        if (this.g > 14) {
            processBlock();
        }
        this.f[14] = (int) (j >>> 32);
        this.f[15] = (int) (j & -1);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        this.f[this.g] = (bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | (bArr[i] << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
        int i4 = this.g + 1;
        this.g = i4;
        if (i4 == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
        this.g = 0;
        for (int i = 0; i != this.f.length; i++) {
            this.f[i] = 0;
        }
    }

    public void reset(Memoable memoable) {
        SHA1Digest sHA1Digest = (SHA1Digest) memoable;
        super.copyIn(sHA1Digest);
        a(sHA1Digest);
    }
}
