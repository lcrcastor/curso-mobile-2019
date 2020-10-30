package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Memoable;

public class RIPEMD256Digest extends GeneralDigest {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int[] i = new int[16];
    private int j;

    public RIPEMD256Digest() {
        reset();
    }

    public RIPEMD256Digest(RIPEMD256Digest rIPEMD256Digest) {
        super((GeneralDigest) rIPEMD256Digest);
        a(rIPEMD256Digest);
    }

    private int a(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private int a(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    private int a(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + a(i3, i4, i5) + i6, i7);
    }

    private void a(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 3] = (byte) (i2 >>> 24);
    }

    private void a(RIPEMD256Digest rIPEMD256Digest) {
        super.copyIn(rIPEMD256Digest);
        this.a = rIPEMD256Digest.a;
        this.b = rIPEMD256Digest.b;
        this.c = rIPEMD256Digest.c;
        this.d = rIPEMD256Digest.d;
        this.e = rIPEMD256Digest.e;
        this.f = rIPEMD256Digest.f;
        this.g = rIPEMD256Digest.g;
        this.h = rIPEMD256Digest.h;
        System.arraycopy(rIPEMD256Digest.i, 0, this.i, 0, rIPEMD256Digest.i.length);
        this.j = rIPEMD256Digest.j;
    }

    private int b(int i2, int i3, int i4) {
        return ((i2 ^ -1) & i4) | (i3 & i2);
    }

    private int b(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + b(i3, i4, i5) + i6 + 1518500249, i7);
    }

    private int c(int i2, int i3, int i4) {
        return (i2 | (i3 ^ -1)) ^ i4;
    }

    private int c(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + c(i3, i4, i5) + i6 + 1859775393, i7);
    }

    private int d(int i2, int i3, int i4) {
        return (i2 & i4) | (i3 & (i4 ^ -1));
    }

    private int d(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(((i2 + d(i3, i4, i5)) + i6) - 1894007588, i7);
    }

    private int e(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + a(i3, i4, i5) + i6, i7);
    }

    private int f(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + b(i3, i4, i5) + i6 + 1836072691, i7);
    }

    private int g(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + c(i3, i4, i5) + i6 + 1548603684, i7);
    }

    private int h(int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i2 + d(i3, i4, i5) + i6 + 1352829926, i7);
    }

    public Memoable copy() {
        return new RIPEMD256Digest(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        finish();
        a(this.a, bArr, i2);
        a(this.b, bArr, i2 + 4);
        a(this.c, bArr, i2 + 8);
        a(this.d, bArr, i2 + 12);
        a(this.e, bArr, i2 + 16);
        a(this.f, bArr, i2 + 20);
        a(this.g, bArr, i2 + 24);
        a(this.h, bArr, i2 + 28);
        reset();
        return 32;
    }

    public String getAlgorithmName() {
        return "RIPEMD256";
    }

    public int getDigestSize() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        int i2 = this.a;
        int i3 = this.b;
        int i4 = this.c;
        int i5 = this.d;
        int i6 = this.e;
        int i7 = this.f;
        int i8 = this.g;
        int i9 = this.h;
        int a2 = a(i2, i3, i4, i5, this.i[0], 11);
        int a3 = a(i5, a2, i3, i4, this.i[1], 14);
        int a4 = a(i4, a3, a2, i3, this.i[2], 15);
        int a5 = a(i3, a4, a3, a2, this.i[3], 12);
        int a6 = a(a2, a5, a4, a3, this.i[4], 5);
        int a7 = a(a3, a6, a5, a4, this.i[5], 8);
        int a8 = a(a4, a7, a6, a5, this.i[6], 7);
        int a9 = a(a5, a8, a7, a6, this.i[7], 9);
        int a10 = a(a6, a9, a8, a7, this.i[8], 11);
        int a11 = a(a7, a10, a9, a8, this.i[9], 13);
        int a12 = a(a8, a11, a10, a9, this.i[10], 14);
        int a13 = a(a9, a12, a11, a10, this.i[11], 15);
        int a14 = a(a10, a13, a12, a11, this.i[12], 6);
        int a15 = a(a11, a14, a13, a12, this.i[13], 7);
        int a16 = a(a12, a15, a14, a13, this.i[14], 9);
        int a17 = a(a13, a16, a15, a14, this.i[15], 8);
        int h2 = h(i6, i7, i8, i9, this.i[5], 8);
        int h3 = h(i9, h2, i7, i8, this.i[14], 9);
        int h4 = h(i8, h3, h2, i7, this.i[7], 9);
        int h5 = h(i7, h4, h3, h2, this.i[0], 11);
        int h6 = h(h2, h5, h4, h3, this.i[9], 13);
        int h7 = h(h3, h6, h5, h4, this.i[2], 15);
        int h8 = h(h4, h7, h6, h5, this.i[11], 15);
        int h9 = h(h5, h8, h7, h6, this.i[4], 5);
        int h10 = h(h6, h9, h8, h7, this.i[13], 7);
        int h11 = h(h7, h10, h9, h8, this.i[6], 7);
        int h12 = h(h8, h11, h10, h9, this.i[15], 8);
        int h13 = h(h9, h12, h11, h10, this.i[8], 11);
        int h14 = h(h10, h13, h12, h11, this.i[1], 14);
        int h15 = h(h11, h14, h13, h12, this.i[10], 14);
        int h16 = h(h12, h15, h14, h13, this.i[3], 12);
        int h17 = h(h13, h16, h15, h14, this.i[12], 6);
        int b2 = b(h14, a17, a16, a15, this.i[7], 7);
        int b3 = b(a15, b2, a17, a16, this.i[4], 6);
        int b4 = b(a16, b3, b2, a17, this.i[13], 8);
        int b5 = b(a17, b4, b3, b2, this.i[1], 13);
        int b6 = b(b2, b5, b4, b3, this.i[10], 11);
        int b7 = b(b3, b6, b5, b4, this.i[6], 9);
        int b8 = b(b4, b7, b6, b5, this.i[15], 7);
        int b9 = b(b5, b8, b7, b6, this.i[3], 15);
        int b10 = b(b6, b9, b8, b7, this.i[12], 7);
        int b11 = b(b7, b10, b9, b8, this.i[0], 12);
        int b12 = b(b8, b11, b10, b9, this.i[9], 15);
        int b13 = b(b9, b12, b11, b10, this.i[5], 9);
        int b14 = b(b10, b13, b12, b11, this.i[2], 11);
        int b15 = b(b11, b14, b13, b12, this.i[14], 7);
        int b16 = b(b12, b15, b14, b13, this.i[11], 13);
        int b17 = b(b13, b16, b15, b14, this.i[8], 12);
        int g2 = g(a14, h17, h16, h15, this.i[6], 9);
        int g3 = g(h15, g2, h17, h16, this.i[11], 13);
        int g4 = g(h16, g3, g2, h17, this.i[3], 15);
        int g5 = g(h17, g4, g3, g2, this.i[7], 7);
        int g6 = g(g2, g5, g4, g3, this.i[0], 12);
        int g7 = g(g3, g6, g5, g4, this.i[13], 8);
        int g8 = g(g4, g7, g6, g5, this.i[5], 9);
        int g9 = g(g5, g8, g7, g6, this.i[10], 11);
        int g10 = g(g6, g9, g8, g7, this.i[14], 7);
        int g11 = g(g7, g10, g9, g8, this.i[15], 7);
        int g12 = g(g8, g11, g10, g9, this.i[8], 12);
        int g13 = g(g9, g12, g11, g10, this.i[12], 7);
        int g14 = g(g10, g13, g12, g11, this.i[4], 6);
        int g15 = g(g11, g14, g13, g12, this.i[9], 15);
        int g16 = g(g12, g15, g14, g13, this.i[1], 13);
        int g17 = g(g13, g16, g15, g14, this.i[2], 11);
        int c2 = c(b14, g17, b16, b15, this.i[3], 11);
        int c3 = c(b15, c2, g17, b16, this.i[10], 13);
        int c4 = c(b16, c3, c2, g17, this.i[14], 6);
        int c5 = c(g17, c4, c3, c2, this.i[4], 7);
        int c6 = c(c2, c5, c4, c3, this.i[9], 14);
        int c7 = c(c3, c6, c5, c4, this.i[15], 9);
        int c8 = c(c4, c7, c6, c5, this.i[8], 13);
        int c9 = c(c5, c8, c7, c6, this.i[1], 15);
        int c10 = c(c6, c9, c8, c7, this.i[2], 14);
        int c11 = c(c7, c10, c9, c8, this.i[7], 8);
        int c12 = c(c8, c11, c10, c9, this.i[0], 13);
        int c13 = c(c9, c12, c11, c10, this.i[6], 6);
        int c14 = c(c10, c13, c12, c11, this.i[13], 5);
        int c15 = c(c11, c14, c13, c12, this.i[11], 12);
        int c16 = c(c12, c15, c14, c13, this.i[5], 7);
        int c17 = c(c13, c16, c15, c14, this.i[12], 5);
        int f2 = f(g14, b17, g16, g15, this.i[15], 9);
        int f3 = f(g15, f2, b17, g16, this.i[5], 7);
        int f4 = f(g16, f3, f2, b17, this.i[1], 15);
        int f5 = f(b17, f4, f3, f2, this.i[3], 11);
        int f6 = f(f2, f5, f4, f3, this.i[7], 8);
        int f7 = f(f3, f6, f5, f4, this.i[14], 6);
        int f8 = f(f4, f7, f6, f5, this.i[6], 6);
        int f9 = f(f5, f8, f7, f6, this.i[9], 14);
        int f10 = f(f6, f9, f8, f7, this.i[11], 12);
        int f11 = f(f7, f10, f9, f8, this.i[8], 13);
        int f12 = f(f8, f11, f10, f9, this.i[12], 5);
        int f13 = f(f9, f12, f11, f10, this.i[2], 14);
        int f14 = f(f10, f13, f12, f11, this.i[10], 13);
        int f15 = f(f11, f14, f13, f12, this.i[0], 13);
        int f16 = f(f12, f15, f14, f13, this.i[4], 7);
        int f17 = f(f13, f16, f15, f14, this.i[13], 5);
        int d2 = d(c14, c17, f16, c15, this.i[1], 11);
        int d3 = d(c15, d2, c17, f16, this.i[9], 12);
        int d4 = d(f16, d3, d2, c17, this.i[11], 14);
        int d5 = d(c17, d4, d3, d2, this.i[10], 15);
        int d6 = d(d2, d5, d4, d3, this.i[0], 14);
        int d7 = d(d3, d6, d5, d4, this.i[8], 15);
        int d8 = d(d4, d7, d6, d5, this.i[12], 9);
        int d9 = d(d5, d8, d7, d6, this.i[4], 8);
        int d10 = d(d6, d9, d8, d7, this.i[13], 9);
        int d11 = d(d7, d10, d9, d8, this.i[3], 14);
        int d12 = d(d8, d11, d10, d9, this.i[7], 5);
        int d13 = d(d9, d12, d11, d10, this.i[15], 6);
        int d14 = d(d10, d13, d12, d11, this.i[14], 8);
        int d15 = d(d11, d14, d13, d12, this.i[5], 6);
        int d16 = d(d12, d15, d14, d13, this.i[6], 5);
        int d17 = d(d13, d16, d15, d14, this.i[2], 12);
        int e2 = e(f14, f17, c16, f15, this.i[8], 15);
        int e3 = e(f15, e2, f17, c16, this.i[6], 5);
        int e4 = e(c16, e3, e2, f17, this.i[4], 8);
        int e5 = e(f17, e4, e3, e2, this.i[1], 11);
        int e6 = e(e2, e5, e4, e3, this.i[3], 14);
        int e7 = e(e3, e6, e5, e4, this.i[11], 14);
        int e8 = e(e4, e7, e6, e5, this.i[15], 6);
        int e9 = e(e5, e8, e7, e6, this.i[0], 14);
        int e10 = e(e6, e9, e8, e7, this.i[5], 6);
        int e11 = e(e7, e10, e9, e8, this.i[12], 9);
        int e12 = e(e8, e11, e10, e9, this.i[2], 12);
        int e13 = e(e9, e12, e11, e10, this.i[13], 9);
        int e14 = e(e10, e13, e12, e11, this.i[9], 12);
        int e15 = e(e11, e14, e13, e12, this.i[7], 5);
        int e16 = e(e12, e15, e14, e13, this.i[10], 15);
        int e17 = e(e13, e16, e15, e14, this.i[14], 8);
        this.a += d14;
        this.b += d17;
        this.c += d16;
        this.d += e15;
        this.e += e14;
        this.f += e17;
        this.g += e16;
        this.h += d15;
        this.j = 0;
        for (int i10 = 0; i10 != this.i.length; i10++) {
            this.i[i10] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j2) {
        if (this.j > 14) {
            processBlock();
        }
        this.i[14] = (int) (j2 & -1);
        this.i[15] = (int) (j2 >>> 32);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i2) {
        int[] iArr = this.i;
        int i3 = this.j;
        this.j = i3 + 1;
        iArr[i3] = ((bArr[i2 + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i2] & UnsignedBytes.MAX_VALUE) | ((bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i2 + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        if (this.j == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = 1985229328;
        this.f = -19088744;
        this.g = -1985229329;
        this.h = 19088743;
        this.j = 0;
        for (int i2 = 0; i2 != this.i.length; i2++) {
            this.i[i2] = 0;
        }
    }

    public void reset(Memoable memoable) {
        a((RIPEMD256Digest) memoable);
    }
}
