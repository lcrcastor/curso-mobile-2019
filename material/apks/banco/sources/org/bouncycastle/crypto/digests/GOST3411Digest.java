package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public class GOST3411Digest implements ExtendedDigest, Memoable {
    private static final byte[] s = {0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1};
    byte[] a;
    short[] b;
    short[] c;
    byte[] d;
    byte[] e;
    byte[] f;
    byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte[][] l;
    private byte[] m;
    private int n;
    private long o;
    private BlockCipher p;
    private byte[] q;
    private byte[] r;

    public GOST3411Digest() {
        this.h = new byte[32];
        this.i = new byte[32];
        this.j = new byte[32];
        this.k = new byte[32];
        this.l = (byte[][]) Array.newInstance(byte.class, new int[]{4, 32});
        this.m = new byte[32];
        this.p = new GOST28147Engine();
        this.r = new byte[32];
        this.a = new byte[8];
        this.b = new short[16];
        this.c = new short[16];
        this.d = new byte[32];
        this.e = new byte[32];
        this.f = new byte[32];
        this.g = new byte[32];
        this.q = GOST28147Engine.getSBox("D-A");
        this.p.init(true, new ParametersWithSBox(null, this.q));
        reset();
    }

    public GOST3411Digest(GOST3411Digest gOST3411Digest) {
        this.h = new byte[32];
        this.i = new byte[32];
        this.j = new byte[32];
        this.k = new byte[32];
        this.l = (byte[][]) Array.newInstance(byte.class, new int[]{4, 32});
        this.m = new byte[32];
        this.p = new GOST28147Engine();
        this.r = new byte[32];
        this.a = new byte[8];
        this.b = new short[16];
        this.c = new short[16];
        this.d = new byte[32];
        this.e = new byte[32];
        this.f = new byte[32];
        this.g = new byte[32];
        reset(gOST3411Digest);
    }

    public GOST3411Digest(byte[] bArr) {
        this.h = new byte[32];
        this.i = new byte[32];
        this.j = new byte[32];
        this.k = new byte[32];
        this.l = (byte[][]) Array.newInstance(byte.class, new int[]{4, 32});
        this.m = new byte[32];
        this.p = new GOST28147Engine();
        this.r = new byte[32];
        this.a = new byte[8];
        this.b = new short[16];
        this.c = new short[16];
        this.d = new byte[32];
        this.e = new byte[32];
        this.f = new byte[32];
        this.g = new byte[32];
        this.q = Arrays.clone(bArr);
        this.p.init(true, new ParametersWithSBox(null, this.q));
        reset();
    }

    private void a() {
        Pack.longToLittleEndian(this.o * 8, this.i, 0);
        while (this.n != 0) {
            update(0);
        }
        processBlock(this.i, 0);
        processBlock(this.k, 0);
    }

    private void a(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, int i3) {
        this.p.init(true, new KeyParameter(bArr));
        this.p.processBlock(bArr3, i3, bArr2, i2);
    }

    private void a(byte[] bArr, short[] sArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            sArr[i2] = (short) ((bArr[i3] & UnsignedBytes.MAX_VALUE) | ((bArr[i3 + 1] << 8) & Ascii.NUL));
        }
    }

    private void a(short[] sArr, byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            int i3 = i2 * 2;
            bArr[i3 + 1] = (byte) (sArr[i2] >> 8);
            bArr[i3] = (byte) sArr[i2];
        }
    }

    private byte[] a(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = i2 * 4;
            this.r[i3] = bArr[i2];
            this.r[i3 + 1] = bArr[i2 + 8];
            this.r[i3 + 2] = bArr[i2 + 16];
            this.r[i3 + 3] = bArr[i2 + 24];
        }
        return this.r;
    }

    private byte[] b(byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            this.a[i2] = (byte) (bArr[i2] ^ bArr[i2 + 8]);
        }
        System.arraycopy(bArr, 8, bArr, 0, 24);
        System.arraycopy(this.a, 0, bArr, 24, 8);
        return bArr;
    }

    private void c(byte[] bArr) {
        a(bArr, this.b);
        this.c[15] = (short) (((((this.b[0] ^ this.b[1]) ^ this.b[2]) ^ this.b[3]) ^ this.b[12]) ^ this.b[15]);
        System.arraycopy(this.b, 1, this.c, 0, 15);
        a(this.c, bArr);
    }

    private void d(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 0; i3 != this.k.length; i3++) {
            int i4 = (this.k[i3] & UnsignedBytes.MAX_VALUE) + (bArr[i3] & UnsignedBytes.MAX_VALUE) + i2;
            this.k[i3] = (byte) i4;
            i2 = i4 >>> 8;
        }
    }

    public Memoable copy() {
        return new GOST3411Digest(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        a();
        System.arraycopy(this.h, 0, bArr, i2, this.h.length);
        reset();
        return 32;
    }

    public String getAlgorithmName() {
        return "GOST3411";
    }

    public int getByteLength() {
        return 32;
    }

    public int getDigestSize() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void processBlock(byte[] bArr, int i2) {
        System.arraycopy(bArr, i2, this.j, 0, 32);
        System.arraycopy(this.h, 0, this.e, 0, 32);
        System.arraycopy(this.j, 0, this.f, 0, 32);
        for (int i3 = 0; i3 < 32; i3++) {
            this.g[i3] = (byte) (this.e[i3] ^ this.f[i3]);
        }
        a(a(this.g), this.d, 0, this.h, 0);
        for (int i4 = 1; i4 < 4; i4++) {
            byte[] b2 = b(this.e);
            for (int i5 = 0; i5 < 32; i5++) {
                this.e[i5] = (byte) (b2[i5] ^ this.l[i4][i5]);
            }
            this.f = b(b(this.f));
            for (int i6 = 0; i6 < 32; i6++) {
                this.g[i6] = (byte) (this.e[i6] ^ this.f[i6]);
            }
            int i7 = i4 * 8;
            a(a(this.g), this.d, i7, this.h, i7);
        }
        for (int i8 = 0; i8 < 12; i8++) {
            c(this.d);
        }
        for (int i9 = 0; i9 < 32; i9++) {
            this.d[i9] = (byte) (this.d[i9] ^ this.j[i9]);
        }
        c(this.d);
        for (int i10 = 0; i10 < 32; i10++) {
            this.d[i10] = (byte) (this.h[i10] ^ this.d[i10]);
        }
        for (int i11 = 0; i11 < 61; i11++) {
            c(this.d);
        }
        System.arraycopy(this.d, 0, this.h, 0, this.h.length);
    }

    public void reset() {
        this.o = 0;
        this.n = 0;
        for (int i2 = 0; i2 < this.h.length; i2++) {
            this.h[i2] = 0;
        }
        for (int i3 = 0; i3 < this.i.length; i3++) {
            this.i[i3] = 0;
        }
        for (int i4 = 0; i4 < this.j.length; i4++) {
            this.j[i4] = 0;
        }
        for (int i5 = 0; i5 < this.l[1].length; i5++) {
            this.l[1][i5] = 0;
        }
        for (int i6 = 0; i6 < this.l[3].length; i6++) {
            this.l[3][i6] = 0;
        }
        for (int i7 = 0; i7 < this.k.length; i7++) {
            this.k[i7] = 0;
        }
        for (int i8 = 0; i8 < this.m.length; i8++) {
            this.m[i8] = 0;
        }
        System.arraycopy(s, 0, this.l[2], 0, s.length);
    }

    public void reset(Memoable memoable) {
        GOST3411Digest gOST3411Digest = (GOST3411Digest) memoable;
        this.q = gOST3411Digest.q;
        this.p.init(true, new ParametersWithSBox(null, this.q));
        reset();
        System.arraycopy(gOST3411Digest.h, 0, this.h, 0, gOST3411Digest.h.length);
        System.arraycopy(gOST3411Digest.i, 0, this.i, 0, gOST3411Digest.i.length);
        System.arraycopy(gOST3411Digest.j, 0, this.j, 0, gOST3411Digest.j.length);
        System.arraycopy(gOST3411Digest.k, 0, this.k, 0, gOST3411Digest.k.length);
        System.arraycopy(gOST3411Digest.l[1], 0, this.l[1], 0, gOST3411Digest.l[1].length);
        System.arraycopy(gOST3411Digest.l[2], 0, this.l[2], 0, gOST3411Digest.l[2].length);
        System.arraycopy(gOST3411Digest.l[3], 0, this.l[3], 0, gOST3411Digest.l[3].length);
        System.arraycopy(gOST3411Digest.m, 0, this.m, 0, gOST3411Digest.m.length);
        this.n = gOST3411Digest.n;
        this.o = gOST3411Digest.o;
    }

    public void update(byte b2) {
        byte[] bArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        bArr[i2] = b2;
        if (this.n == this.m.length) {
            d(this.m);
            processBlock(this.m, 0);
            this.n = 0;
        }
        this.o++;
    }

    public void update(byte[] bArr, int i2, int i3) {
        while (this.n != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > this.m.length) {
            System.arraycopy(bArr, i2, this.m, 0, this.m.length);
            d(this.m);
            processBlock(this.m, 0);
            i2 += this.m.length;
            i3 -= this.m.length;
            this.o += (long) this.m.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
