package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Memoable;

public class MD4Digest extends GeneralDigest {
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] e = new int[16];
    private int f;

    public MD4Digest() {
        reset();
    }

    public MD4Digest(MD4Digest mD4Digest) {
        super((GeneralDigest) mD4Digest);
        a(mD4Digest);
    }

    private int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private int a(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 3] = (byte) (i >>> 24);
    }

    private void a(MD4Digest mD4Digest) {
        super.copyIn(mD4Digest);
        this.a = mD4Digest.a;
        this.b = mD4Digest.b;
        this.c = mD4Digest.c;
        this.d = mD4Digest.d;
        System.arraycopy(mD4Digest.e, 0, this.e, 0, mD4Digest.e.length);
        this.f = mD4Digest.f;
    }

    private int b(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    private int c(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    public Memoable copy() {
        return new MD4Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        a(this.a, bArr, i);
        a(this.b, bArr, i + 4);
        a(this.c, bArr, i + 8);
        a(this.d, bArr, i + 12);
        reset();
        return 16;
    }

    public String getAlgorithmName() {
        return "MD4";
    }

    public int getDigestSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        int i4 = this.d;
        int a2 = a(i + a(i2, i3, i4) + this.e[0], 3);
        int a3 = a(i4 + a(a2, i2, i3) + this.e[1], 7);
        int a4 = a(i3 + a(a3, a2, i2) + this.e[2], 11);
        int a5 = a(i2 + a(a4, a3, a2) + this.e[3], 19);
        int a6 = a(a2 + a(a5, a4, a3) + this.e[4], 3);
        int a7 = a(a3 + a(a6, a5, a4) + this.e[5], 7);
        int a8 = a(a4 + a(a7, a6, a5) + this.e[6], 11);
        int a9 = a(a5 + a(a8, a7, a6) + this.e[7], 19);
        int a10 = a(a6 + a(a9, a8, a7) + this.e[8], 3);
        int a11 = a(a7 + a(a10, a9, a8) + this.e[9], 7);
        int a12 = a(a8 + a(a11, a10, a9) + this.e[10], 11);
        int a13 = a(a9 + a(a12, a11, a10) + this.e[11], 19);
        int a14 = a(a10 + a(a13, a12, a11) + this.e[12], 3);
        int a15 = a(a11 + a(a14, a13, a12) + this.e[13], 7);
        int a16 = a(a12 + a(a15, a14, a13) + this.e[14], 11);
        int a17 = a(a13 + a(a16, a15, a14) + this.e[15], 19);
        int a18 = a(a14 + b(a17, a16, a15) + this.e[0] + 1518500249, 3);
        int a19 = a(a15 + b(a18, a17, a16) + this.e[4] + 1518500249, 5);
        int a20 = a(a16 + b(a19, a18, a17) + this.e[8] + 1518500249, 9);
        int a21 = a(a17 + b(a20, a19, a18) + this.e[12] + 1518500249, 13);
        int a22 = a(a18 + b(a21, a20, a19) + this.e[1] + 1518500249, 3);
        int a23 = a(a19 + b(a22, a21, a20) + this.e[5] + 1518500249, 5);
        int a24 = a(a20 + b(a23, a22, a21) + this.e[9] + 1518500249, 9);
        int a25 = a(a21 + b(a24, a23, a22) + this.e[13] + 1518500249, 13);
        int a26 = a(a22 + b(a25, a24, a23) + this.e[2] + 1518500249, 3);
        int a27 = a(a23 + b(a26, a25, a24) + this.e[6] + 1518500249, 5);
        int a28 = a(a24 + b(a27, a26, a25) + this.e[10] + 1518500249, 9);
        int a29 = a(a25 + b(a28, a27, a26) + this.e[14] + 1518500249, 13);
        int a30 = a(a26 + b(a29, a28, a27) + this.e[3] + 1518500249, 3);
        int a31 = a(a27 + b(a30, a29, a28) + this.e[7] + 1518500249, 5);
        int a32 = a(a28 + b(a31, a30, a29) + this.e[11] + 1518500249, 9);
        int a33 = a(a29 + b(a32, a31, a30) + this.e[15] + 1518500249, 13);
        int a34 = a(a30 + c(a33, a32, a31) + this.e[0] + 1859775393, 3);
        int a35 = a(a31 + c(a34, a33, a32) + this.e[8] + 1859775393, 9);
        int a36 = a(a32 + c(a35, a34, a33) + this.e[4] + 1859775393, 11);
        int a37 = a(a33 + c(a36, a35, a34) + this.e[12] + 1859775393, 15);
        int a38 = a(a34 + c(a37, a36, a35) + this.e[2] + 1859775393, 3);
        int a39 = a(a35 + c(a38, a37, a36) + this.e[10] + 1859775393, 9);
        int a40 = a(a36 + c(a39, a38, a37) + this.e[6] + 1859775393, 11);
        int a41 = a(a37 + c(a40, a39, a38) + this.e[14] + 1859775393, 15);
        int a42 = a(a38 + c(a41, a40, a39) + this.e[1] + 1859775393, 3);
        int a43 = a(a39 + c(a42, a41, a40) + this.e[9] + 1859775393, 9);
        int a44 = a(a40 + c(a43, a42, a41) + this.e[5] + 1859775393, 11);
        int a45 = a(a41 + c(a44, a43, a42) + this.e[13] + 1859775393, 15);
        int a46 = a(a42 + c(a45, a44, a43) + this.e[3] + 1859775393, 3);
        int a47 = a(a43 + c(a46, a45, a44) + this.e[11] + 1859775393, 9);
        int a48 = a(a44 + c(a47, a46, a45) + this.e[7] + 1859775393, 11);
        int a49 = a(a45 + c(a48, a47, a46) + this.e[15] + 1859775393, 15);
        this.a += a46;
        this.b += a49;
        this.c += a48;
        this.d += a47;
        this.f = 0;
        for (int i5 = 0; i5 != this.e.length; i5++) {
            this.e[i5] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j) {
        if (this.f > 14) {
            processBlock();
        }
        this.e[14] = (int) (j & -1);
        this.e[15] = (int) (j >>> 32);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        int[] iArr = this.e;
        int i2 = this.f;
        this.f = i2 + 1;
        iArr[i2] = ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        if (this.f == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.f = 0;
        for (int i = 0; i != this.e.length; i++) {
            this.e[i] = 0;
        }
    }

    public void reset(Memoable memoable) {
        a((MD4Digest) memoable);
    }
}
