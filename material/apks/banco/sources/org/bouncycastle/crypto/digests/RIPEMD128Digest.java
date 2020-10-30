package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Memoable;

public class RIPEMD128Digest extends GeneralDigest {
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] e = new int[16];
    private int f;

    public RIPEMD128Digest() {
        reset();
    }

    public RIPEMD128Digest(RIPEMD128Digest rIPEMD128Digest) {
        super((GeneralDigest) rIPEMD128Digest);
        a(rIPEMD128Digest);
    }

    private int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private int a(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private int a(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + a(i2, i3, i4) + i5, i6);
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 3] = (byte) (i >>> 24);
    }

    private void a(RIPEMD128Digest rIPEMD128Digest) {
        super.copyIn(rIPEMD128Digest);
        this.a = rIPEMD128Digest.a;
        this.b = rIPEMD128Digest.b;
        this.c = rIPEMD128Digest.c;
        this.d = rIPEMD128Digest.d;
        System.arraycopy(rIPEMD128Digest.e, 0, this.e, 0, rIPEMD128Digest.e.length);
        this.f = rIPEMD128Digest.f;
    }

    private int b(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    private int b(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + b(i2, i3, i4) + i5 + 1518500249, i6);
    }

    private int c(int i, int i2, int i3) {
        return (i | (i2 ^ -1)) ^ i3;
    }

    private int c(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + c(i2, i3, i4) + i5 + 1859775393, i6);
    }

    private int d(int i, int i2, int i3) {
        return (i & i3) | (i2 & (i3 ^ -1));
    }

    private int d(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(((i + d(i2, i3, i4)) + i5) - 1894007588, i6);
    }

    private int e(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + a(i2, i3, i4) + i5, i6);
    }

    private int f(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + b(i2, i3, i4) + i5 + 1836072691, i6);
    }

    private int g(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + c(i2, i3, i4) + i5 + 1548603684, i6);
    }

    private int h(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + d(i2, i3, i4) + i5 + 1352829926, i6);
    }

    public Memoable copy() {
        return new RIPEMD128Digest(this);
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
        return "RIPEMD128";
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
        int a2 = a(i, i2, i3, i4, this.e[0], 11);
        int a3 = a(i4, a2, i2, i3, this.e[1], 14);
        int a4 = a(i3, a3, a2, i2, this.e[2], 15);
        int a5 = a(i2, a4, a3, a2, this.e[3], 12);
        int a6 = a(a2, a5, a4, a3, this.e[4], 5);
        int a7 = a(a3, a6, a5, a4, this.e[5], 8);
        int a8 = a(a4, a7, a6, a5, this.e[6], 7);
        int a9 = a(a5, a8, a7, a6, this.e[7], 9);
        int a10 = a(a6, a9, a8, a7, this.e[8], 11);
        int a11 = a(a7, a10, a9, a8, this.e[9], 13);
        int a12 = a(a8, a11, a10, a9, this.e[10], 14);
        int a13 = a(a9, a12, a11, a10, this.e[11], 15);
        int a14 = a(a10, a13, a12, a11, this.e[12], 6);
        int a15 = a(a11, a14, a13, a12, this.e[13], 7);
        int a16 = a(a12, a15, a14, a13, this.e[14], 9);
        int a17 = a(a13, a16, a15, a14, this.e[15], 8);
        int b2 = b(a14, a17, a16, a15, this.e[7], 7);
        int b3 = b(a15, b2, a17, a16, this.e[4], 6);
        int b4 = b(a16, b3, b2, a17, this.e[13], 8);
        int b5 = b(a17, b4, b3, b2, this.e[1], 13);
        int b6 = b(b2, b5, b4, b3, this.e[10], 11);
        int b7 = b(b3, b6, b5, b4, this.e[6], 9);
        int b8 = b(b4, b7, b6, b5, this.e[15], 7);
        int b9 = b(b5, b8, b7, b6, this.e[3], 15);
        int b10 = b(b6, b9, b8, b7, this.e[12], 7);
        int b11 = b(b7, b10, b9, b8, this.e[0], 12);
        int b12 = b(b8, b11, b10, b9, this.e[9], 15);
        int b13 = b(b9, b12, b11, b10, this.e[5], 9);
        int b14 = b(b10, b13, b12, b11, this.e[2], 11);
        int b15 = b(b11, b14, b13, b12, this.e[14], 7);
        int b16 = b(b12, b15, b14, b13, this.e[11], 13);
        int b17 = b(b13, b16, b15, b14, this.e[8], 12);
        int c2 = c(b14, b17, b16, b15, this.e[3], 11);
        int c3 = c(b15, c2, b17, b16, this.e[10], 13);
        int c4 = c(b16, c3, c2, b17, this.e[14], 6);
        int c5 = c(b17, c4, c3, c2, this.e[4], 7);
        int c6 = c(c2, c5, c4, c3, this.e[9], 14);
        int c7 = c(c3, c6, c5, c4, this.e[15], 9);
        int c8 = c(c4, c7, c6, c5, this.e[8], 13);
        int c9 = c(c5, c8, c7, c6, this.e[1], 15);
        int c10 = c(c6, c9, c8, c7, this.e[2], 14);
        int c11 = c(c7, c10, c9, c8, this.e[7], 8);
        int c12 = c(c8, c11, c10, c9, this.e[0], 13);
        int c13 = c(c9, c12, c11, c10, this.e[6], 6);
        int c14 = c(c10, c13, c12, c11, this.e[13], 5);
        int c15 = c(c11, c14, c13, c12, this.e[11], 12);
        int c16 = c(c12, c15, c14, c13, this.e[5], 7);
        int c17 = c(c13, c16, c15, c14, this.e[12], 5);
        int d2 = d(c14, c17, c16, c15, this.e[1], 11);
        int d3 = d(c15, d2, c17, c16, this.e[9], 12);
        int d4 = d(c16, d3, d2, c17, this.e[11], 14);
        int d5 = d(c17, d4, d3, d2, this.e[10], 15);
        int d6 = d(d2, d5, d4, d3, this.e[0], 14);
        int d7 = d(d3, d6, d5, d4, this.e[8], 15);
        int d8 = d(d4, d7, d6, d5, this.e[12], 9);
        int d9 = d(d5, d8, d7, d6, this.e[4], 8);
        int d10 = d(d6, d9, d8, d7, this.e[13], 9);
        int d11 = d(d7, d10, d9, d8, this.e[3], 14);
        int d12 = d(d8, d11, d10, d9, this.e[7], 5);
        int d13 = d(d9, d12, d11, d10, this.e[15], 6);
        int d14 = d(d10, d13, d12, d11, this.e[14], 8);
        int d15 = d(d11, d14, d13, d12, this.e[5], 6);
        int d16 = d(d12, d15, d14, d13, this.e[6], 5);
        int d17 = d(d13, d16, d15, d14, this.e[2], 12);
        int h = h(i, i2, i3, i4, this.e[5], 8);
        int h2 = h(i4, h, i2, i3, this.e[14], 9);
        int h3 = h(i3, h2, h, i2, this.e[7], 9);
        int h4 = h(i2, h3, h2, h, this.e[0], 11);
        int h5 = h(h, h4, h3, h2, this.e[9], 13);
        int h6 = h(h2, h5, h4, h3, this.e[2], 15);
        int h7 = h(h3, h6, h5, h4, this.e[11], 15);
        int h8 = h(h4, h7, h6, h5, this.e[4], 5);
        int h9 = h(h5, h8, h7, h6, this.e[13], 7);
        int h10 = h(h6, h9, h8, h7, this.e[6], 7);
        int h11 = h(h7, h10, h9, h8, this.e[15], 8);
        int h12 = h(h8, h11, h10, h9, this.e[8], 11);
        int h13 = h(h9, h12, h11, h10, this.e[1], 14);
        int h14 = h(h10, h13, h12, h11, this.e[10], 14);
        int h15 = h(h11, h14, h13, h12, this.e[3], 12);
        int h16 = h(h12, h15, h14, h13, this.e[12], 6);
        int g = g(h13, h16, h15, h14, this.e[6], 9);
        int g2 = g(h14, g, h16, h15, this.e[11], 13);
        int g3 = g(h15, g2, g, h16, this.e[3], 15);
        int g4 = g(h16, g3, g2, g, this.e[7], 7);
        int g5 = g(g, g4, g3, g2, this.e[0], 12);
        int g6 = g(g2, g5, g4, g3, this.e[13], 8);
        int g7 = g(g3, g6, g5, g4, this.e[5], 9);
        int g8 = g(g4, g7, g6, g5, this.e[10], 11);
        int g9 = g(g5, g8, g7, g6, this.e[14], 7);
        int g10 = g(g6, g9, g8, g7, this.e[15], 7);
        int g11 = g(g7, g10, g9, g8, this.e[8], 12);
        int g12 = g(g8, g11, g10, g9, this.e[12], 7);
        int g13 = g(g9, g12, g11, g10, this.e[4], 6);
        int g14 = g(g10, g13, g12, g11, this.e[9], 15);
        int g15 = g(g11, g14, g13, g12, this.e[1], 13);
        int g16 = g(g12, g15, g14, g13, this.e[2], 11);
        int f2 = f(g13, g16, g15, g14, this.e[15], 9);
        int f3 = f(g14, f2, g16, g15, this.e[5], 7);
        int f4 = f(g15, f3, f2, g16, this.e[1], 15);
        int f5 = f(g16, f4, f3, f2, this.e[3], 11);
        int f6 = f(f2, f5, f4, f3, this.e[7], 8);
        int f7 = f(f3, f6, f5, f4, this.e[14], 6);
        int f8 = f(f4, f7, f6, f5, this.e[6], 6);
        int f9 = f(f5, f8, f7, f6, this.e[9], 14);
        int f10 = f(f6, f9, f8, f7, this.e[11], 12);
        int f11 = f(f7, f10, f9, f8, this.e[8], 13);
        int f12 = f(f8, f11, f10, f9, this.e[12], 5);
        int f13 = f(f9, f12, f11, f10, this.e[2], 14);
        int f14 = f(f10, f13, f12, f11, this.e[10], 13);
        int f15 = f(f11, f14, f13, f12, this.e[0], 13);
        int f16 = f(f12, f15, f14, f13, this.e[4], 7);
        int f17 = f(f13, f16, f15, f14, this.e[13], 5);
        int e2 = e(f14, f17, f16, f15, this.e[8], 15);
        int e3 = e(f15, e2, f17, f16, this.e[6], 5);
        int e4 = e(f16, e3, e2, f17, this.e[4], 8);
        int e5 = e(f17, e4, e3, e2, this.e[1], 11);
        int e6 = e(e2, e5, e4, e3, this.e[3], 14);
        int e7 = e(e3, e6, e5, e4, this.e[11], 14);
        int e8 = e(e4, e7, e6, e5, this.e[15], 6);
        int e9 = e(e5, e8, e7, e6, this.e[0], 14);
        int e10 = e(e6, e9, e8, e7, this.e[5], 6);
        int e11 = e(e7, e10, e9, e8, this.e[12], 9);
        int e12 = e(e8, e11, e10, e9, this.e[2], 12);
        int e13 = e(e9, e12, e11, e10, this.e[13], 9);
        int e14 = e(e10, e13, e12, e11, this.e[9], 12);
        int e15 = e(e11, e14, e13, e12, this.e[7], 5);
        int e16 = e(e12, e15, e14, e13, this.e[10], 15);
        int e17 = e(e13, e16, e15, e14, this.e[14], 8);
        int i5 = e15 + d16 + this.b;
        this.b = this.c + d15 + e14;
        this.c = this.d + d14 + e17;
        this.d = this.a + d17 + e16;
        this.a = i5;
        this.f = 0;
        for (int i6 = 0; i6 != this.e.length; i6++) {
            this.e[i6] = 0;
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
        a((RIPEMD128Digest) memoable);
    }
}
