package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Pack;

public class Poly1305 implements Mac {
    private final BlockCipher a;
    private final byte[] b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private final byte[] p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;

    public Poly1305() {
        this.b = new byte[1];
        this.p = new byte[16];
        this.q = 0;
        this.a = null;
    }

    public Poly1305(BlockCipher blockCipher) {
        this.b = new byte[1];
        this.p = new byte[16];
        this.q = 0;
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("Poly1305 requires a 128 bit block cipher.");
        }
        this.a = blockCipher;
    }

    private static final long a(int i2, int i3) {
        return ((long) i2) * ((long) i3);
    }

    private void a() {
        if (this.q < 16) {
            this.p[this.q] = 1;
            for (int i2 = this.q + 1; i2 < 16; i2++) {
                this.p[i2] = 0;
            }
        }
        long littleEndianToInt = ((long) Pack.littleEndianToInt(this.p, 0)) & 4294967295L;
        long littleEndianToInt2 = ((long) Pack.littleEndianToInt(this.p, 4)) & 4294967295L;
        long littleEndianToInt3 = ((long) Pack.littleEndianToInt(this.p, 8)) & 4294967295L;
        long littleEndianToInt4 = ((long) Pack.littleEndianToInt(this.p, 12)) & 4294967295L;
        this.r = (int) (((long) this.r) + (littleEndianToInt & 67108863));
        this.s = (int) (((long) this.s) + ((((littleEndianToInt2 << 32) | littleEndianToInt) >>> 26) & 67108863));
        this.t = (int) (((long) this.t) + ((((littleEndianToInt3 << 32) | littleEndianToInt2) >>> 20) & 67108863));
        this.u = (int) (((long) this.u) + ((((littleEndianToInt4 << 32) | littleEndianToInt3) >>> 14) & 67108863));
        this.v = (int) (((long) this.v) + (littleEndianToInt4 >>> 8));
        if (this.q == 16) {
            this.v += 16777216;
        }
        long a2 = a(this.r, this.c) + a(this.s, this.k) + a(this.t, this.j) + a(this.u, this.i) + a(this.v, this.h);
        long a3 = a(this.r, this.d) + a(this.s, this.c) + a(this.t, this.k) + a(this.u, this.j) + a(this.v, this.i);
        long a4 = a(this.r, this.e) + a(this.s, this.d) + a(this.t, this.c) + a(this.u, this.k) + a(this.v, this.j);
        long a5 = a(this.r, this.f) + a(this.s, this.e) + a(this.t, this.d) + a(this.u, this.c) + a(this.v, this.k);
        long a6 = a(this.r, this.g) + a(this.s, this.f) + a(this.t, this.e) + a(this.u, this.d) + a(this.v, this.c);
        this.r = ((int) a2) & 67108863;
        long j2 = a6;
        long j3 = a3 + (a2 >>> 26);
        this.s = ((int) j3) & 67108863;
        long j4 = a4 + ((j3 >>> 26) & -1);
        this.t = ((int) j4) & 67108863;
        long j5 = a5 + ((j4 >>> 26) & -1);
        this.u = ((int) j5) & 67108863;
        long j6 = j2 + (j5 >>> 26);
        this.v = ((int) j6) & 67108863;
        this.r = (int) (((long) this.r) + ((j6 >>> 26) * 5));
    }

    private void a(byte[] bArr, byte[] bArr2) {
        if (this.a == null || (bArr2 != null && bArr2.length == 16)) {
            Poly1305KeyGenerator.checkKey(bArr);
            int littleEndianToInt = Pack.littleEndianToInt(bArr, 16);
            int littleEndianToInt2 = Pack.littleEndianToInt(bArr, 20);
            int littleEndianToInt3 = Pack.littleEndianToInt(bArr, 24);
            int littleEndianToInt4 = Pack.littleEndianToInt(bArr, 28);
            this.c = 67108863 & littleEndianToInt;
            this.d = ((littleEndianToInt >>> 26) | (littleEndianToInt2 << 6)) & 67108611;
            this.e = ((littleEndianToInt2 >>> 20) | (littleEndianToInt3 << 12)) & 67092735;
            this.f = ((littleEndianToInt3 >>> 14) | (littleEndianToInt4 << 18)) & 66076671;
            this.g = (littleEndianToInt4 >>> 8) & 1048575;
            this.h = this.d * 5;
            this.i = this.e * 5;
            this.j = this.f * 5;
            this.k = this.g * 5;
            if (this.a != null) {
                byte[] bArr3 = new byte[16];
                this.a.init(true, new KeyParameter(bArr, 0, 16));
                this.a.processBlock(bArr2, 0, bArr3, 0);
                bArr = bArr3;
            }
            this.l = Pack.littleEndianToInt(bArr, 0);
            this.m = Pack.littleEndianToInt(bArr, 4);
            this.n = Pack.littleEndianToInt(bArr, 8);
            this.o = Pack.littleEndianToInt(bArr, 12);
            return;
        }
        throw new IllegalArgumentException("Poly1305 requires a 128 bit IV.");
    }

    public int doFinal(byte[] bArr, int i2) {
        byte[] bArr2 = bArr;
        int i3 = i2;
        if (i3 + 16 > bArr2.length) {
            throw new DataLengthException("Output buffer is too short.");
        }
        if (this.q > 0) {
            a();
        }
        int i4 = this.r >>> 26;
        this.r &= 67108863;
        this.s += i4;
        int i5 = this.s >>> 26;
        this.s &= 67108863;
        this.t += i5;
        int i6 = this.t >>> 26;
        this.t &= 67108863;
        this.u += i6;
        int i7 = this.u >>> 26;
        this.u &= 67108863;
        this.v += i7;
        int i8 = this.v >>> 26;
        this.v &= 67108863;
        this.r += i8 * 5;
        int i9 = this.r + 5;
        int i10 = i9 >>> 26;
        int i11 = this.s + i10;
        int i12 = i11 >>> 26;
        int i13 = i11 & 67108863;
        int i14 = this.t + i12;
        int i15 = i14 >>> 26;
        int i16 = i14 & 67108863;
        int i17 = this.u + i15;
        int i18 = 67108863 & i17;
        int i19 = (this.v + (i17 >>> 26)) - 67108864;
        int i20 = (i19 >>> 31) - 1;
        int i21 = i20 ^ -1;
        this.r = (i9 & 67108863 & i20) | (this.r & i21);
        this.s = (this.s & i21) | (i13 & i20);
        this.t = (this.t & i21) | (i16 & i20);
        this.u = (this.u & i21) | (i18 & i20);
        this.v = (this.v & i21) | (i20 & i19);
        long j2 = (((long) (this.r | (this.s << 26))) & 4294967295L) + (((long) this.l) & 4294967295L);
        long j3 = (((long) ((this.s >>> 6) | (this.t << 20))) & 4294967295L) + (((long) this.m) & 4294967295L);
        long j4 = (((long) ((this.t >>> 12) | (this.u << 14))) & 4294967295L) + (((long) this.n) & 4294967295L);
        long j5 = (((long) ((this.u >>> 18) | (this.v << 8))) & 4294967295L) + (((long) this.o) & 4294967295L);
        Pack.intToLittleEndian((int) j2, bArr2, i3);
        long j6 = j3 + (j2 >>> 32);
        Pack.intToLittleEndian((int) j6, bArr2, i3 + 4);
        long j7 = j4 + (j6 >>> 32);
        Pack.intToLittleEndian((int) j7, bArr2, i3 + 8);
        Pack.intToLittleEndian((int) (j5 + (j7 >>> 32)), bArr2, i3 + 12);
        reset();
        return 16;
    }

    public String getAlgorithmName() {
        if (this.a == null) {
            return "Poly1305";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Poly1305-");
        sb.append(this.a.getAlgorithmName());
        return sb.toString();
    }

    public int getMacSize() {
        return 16;
    }

    public void init(CipherParameters cipherParameters) {
        byte[] bArr;
        if (this.a == null) {
            bArr = null;
        } else if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Poly1305 requires an IV when used with a block cipher.");
        } else {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            cipherParameters = parametersWithIV.getParameters();
        }
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("Poly1305 requires a key.");
        }
        a(((KeyParameter) cipherParameters).getKey(), bArr);
        reset();
    }

    public void reset() {
        this.q = 0;
        this.v = 0;
        this.u = 0;
        this.t = 0;
        this.s = 0;
        this.r = 0;
    }

    public void update(byte b2) {
        this.b[0] = b2;
        update(this.b, 0, 1);
    }

    public void update(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        while (i3 > i4) {
            if (this.q == 16) {
                a();
                this.q = 0;
            }
            int min = Math.min(i3 - i4, 16 - this.q);
            System.arraycopy(bArr, i4 + i2, this.p, this.q, min);
            i4 += min;
            this.q += min;
        }
    }
}
