package org.bouncycastle.pqc.crypto.gmss;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.encoders.Hex;

public class GMSSRootSig {
    private Digest a;
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    private GMSSRandom h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private long o;
    private long p;
    private int q;
    private int r;
    private int s;
    private byte[] t;

    public GMSSRootSig(Digest digest, int i2, int i3) {
        this.a = digest;
        this.h = new GMSSRandom(this.a);
        this.b = this.a.getDigestSize();
        this.g = i2;
        this.s = i3;
        this.j = (1 << i2) - 1;
        this.i = (int) Math.ceil(((double) (this.b << 3)) / ((double) i2));
    }

    public GMSSRootSig(Digest digest, byte[][] bArr, int[] iArr) {
        this.a = digest;
        this.h = new GMSSRandom(this.a);
        this.m = iArr[0];
        this.l = iArr[1];
        this.n = iArr[2];
        this.k = iArr[3];
        this.q = iArr[4];
        this.c = iArr[5];
        this.s = iArr[6];
        this.g = iArr[7];
        this.r = iArr[8];
        this.b = this.a.getDigestSize();
        this.j = (1 << this.g) - 1;
        this.i = (int) Math.ceil(((double) (this.b << 3)) / ((double) this.g));
        this.d = bArr[0];
        this.t = bArr[1];
        this.e = bArr[2];
        this.f = bArr[3];
        this.o = ((long) (bArr[4][0] & UnsignedBytes.MAX_VALUE)) | (((long) (bArr[4][1] & UnsignedBytes.MAX_VALUE)) << 8) | (((long) (bArr[4][2] & UnsignedBytes.MAX_VALUE)) << 16) | (((long) (bArr[4][3] & UnsignedBytes.MAX_VALUE)) << 24) | (((long) (bArr[4][4] & UnsignedBytes.MAX_VALUE)) << 32) | (((long) (bArr[4][5] & UnsignedBytes.MAX_VALUE)) << 40) | (((long) (bArr[4][6] & UnsignedBytes.MAX_VALUE)) << 48) | (((long) (bArr[4][7] & UnsignedBytes.MAX_VALUE)) << 56);
        this.p = ((long) (bArr[4][8] & UnsignedBytes.MAX_VALUE)) | (((long) (bArr[4][9] & UnsignedBytes.MAX_VALUE)) << 8) | (((long) (bArr[4][10] & UnsignedBytes.MAX_VALUE)) << 16) | (((long) (bArr[4][11] & UnsignedBytes.MAX_VALUE)) << 24) | (((long) (bArr[4][12] & UnsignedBytes.MAX_VALUE)) << 32) | (((long) (bArr[4][13] & UnsignedBytes.MAX_VALUE)) << 40) | (((long) (bArr[4][14] & UnsignedBytes.MAX_VALUE)) << 48) | (((long) (bArr[4][15] & UnsignedBytes.MAX_VALUE)) << 56);
    }

    private void a() {
        int i2;
        if (8 % this.g == 0) {
            if (this.l == 0) {
                this.d = this.h.nextSeed(this.t);
                if (this.n < this.b) {
                    this.l = this.e[this.n] & this.j;
                    this.e[this.n] = (byte) (this.e[this.n] >>> this.g);
                } else {
                    this.l = this.r & this.j;
                    this.r >>>= this.g;
                }
            } else if (this.l > 0) {
                this.a.update(this.d, 0, this.d.length);
                this.d = new byte[this.a.getDigestSize()];
                this.a.doFinal(this.d, 0);
                this.l--;
            }
            if (this.l == 0) {
                System.arraycopy(this.d, 0, this.f, this.m * this.b, this.b);
                this.m++;
                if (this.m % (8 / this.g) == 0) {
                    this.n++;
                }
            }
        } else {
            if (this.g < 8) {
                if (this.l == 0) {
                    if (this.m % 8 == 0 && this.n < this.b) {
                        this.p = 0;
                        if (this.m < ((this.b / this.g) << 3)) {
                            for (int i3 = 0; i3 < this.g; i3++) {
                                this.p ^= (long) ((this.e[this.n] & UnsignedBytes.MAX_VALUE) << (i3 << 3));
                                this.n++;
                            }
                        } else {
                            for (int i4 = 0; i4 < this.b % this.g; i4++) {
                                this.p ^= (long) ((this.e[this.n] & UnsignedBytes.MAX_VALUE) << (i4 << 3));
                                this.n++;
                            }
                        }
                    }
                    if (this.m == this.i) {
                        this.p = (long) this.r;
                    }
                    this.l = (int) (this.p & ((long) this.j));
                    this.d = this.h.nextSeed(this.t);
                } else if (this.l > 0) {
                    this.a.update(this.d, 0, this.d.length);
                    this.d = new byte[this.a.getDigestSize()];
                    this.a.doFinal(this.d, 0);
                    this.l--;
                }
                if (this.l == 0) {
                    System.arraycopy(this.d, 0, this.f, this.m * this.b, this.b);
                    this.p >>>= this.g;
                }
            } else if (this.g < 57) {
                if (this.o == 0) {
                    this.p = 0;
                    this.n = 0;
                    int i5 = this.k % 8;
                    int i6 = this.k >>> 3;
                    if (i6 < this.b) {
                        if (this.k <= (this.b << 3) - this.g) {
                            this.k += this.g;
                            i2 = (this.k + 7) >>> 3;
                        } else {
                            i2 = this.b;
                            this.k += this.g;
                        }
                        while (i6 < i2) {
                            this.p ^= (long) ((this.e[i6] & UnsignedBytes.MAX_VALUE) << (this.n << 3));
                            this.n++;
                            i6++;
                        }
                        this.p >>>= i5;
                        this.o = this.p & ((long) this.j);
                    } else {
                        this.o = (long) (this.r & this.j);
                        this.r >>>= this.g;
                    }
                    this.d = this.h.nextSeed(this.t);
                } else if (this.o > 0) {
                    this.a.update(this.d, 0, this.d.length);
                    this.d = new byte[this.a.getDigestSize()];
                    this.a.doFinal(this.d, 0);
                    this.o--;
                }
                if (this.o == 0) {
                    System.arraycopy(this.d, 0, this.f, this.m * this.b, this.b);
                }
            }
            this.m++;
        }
    }

    public int getLog(int i2) {
        int i3 = 1;
        int i4 = 2;
        while (i4 < i2) {
            i4 <<= 1;
            i3++;
        }
        return i3;
    }

    public byte[] getSig() {
        return this.f;
    }

    public byte[][] getStatByte() {
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, new int[]{5, this.b});
        bArr[0] = this.d;
        bArr[1] = this.t;
        bArr[2] = this.e;
        bArr[3] = this.f;
        bArr[4] = getStatLong();
        return bArr;
    }

    public int[] getStatInt() {
        return new int[]{this.m, this.l, this.n, this.k, this.q, this.c, this.s, this.g, this.r};
    }

    public byte[] getStatLong() {
        return new byte[]{(byte) ((int) (this.o & 255)), (byte) ((int) ((this.o >> 8) & 255)), (byte) ((int) ((this.o >> 16) & 255)), (byte) ((int) ((this.o >> 24) & 255)), (byte) ((int) ((this.o >> 32) & 255)), (byte) ((int) ((this.o >> 40) & 255)), (byte) ((int) ((this.o >> 48) & 255)), (byte) ((int) ((this.o >> 56) & 255)), (byte) ((int) (this.p & 255)), (byte) ((int) ((this.p >> 8) & 255)), (byte) ((int) ((this.p >> 16) & 255)), (byte) ((int) ((this.p >> 24) & 255)), (byte) ((int) ((this.p >> 32) & 255)), (byte) ((int) ((this.p >> 40) & 255)), (byte) ((int) ((this.p >> 48) & 255)), (byte) ((int) ((this.p >> 56) & 255))};
    }

    public void initSign(byte[] bArr, byte[] bArr2) {
        int i2;
        byte[] bArr3 = bArr2;
        this.e = new byte[this.b];
        this.a.update(bArr3, 0, bArr3.length);
        this.e = new byte[this.a.getDigestSize()];
        this.a.doFinal(this.e, 0);
        byte[] bArr4 = new byte[this.b];
        System.arraycopy(this.e, 0, bArr4, 0, this.b);
        int log = getLog((this.i << this.g) + 1);
        int i3 = 8;
        if (8 % this.g == 0) {
            int i4 = 8 / this.g;
            int i5 = 0;
            int i6 = 0;
            while (i5 < this.b) {
                int i7 = i6;
                for (int i8 = 0; i8 < i4; i8++) {
                    i7 += bArr4[i5] & this.j;
                    bArr4[i5] = (byte) (bArr4[i5] >>> this.g);
                }
                i5++;
                i6 = i7;
            }
            this.r = (this.i << this.g) - i6;
            int i9 = this.r;
            for (int i10 = 0; i10 < log; i10 += this.g) {
                i6 += this.j & i9;
                i9 >>>= this.g;
            }
            i2 = i6;
        } else if (this.g < 8) {
            int i11 = this.b / this.g;
            int i12 = 0;
            int i13 = 0;
            int i14 = 0;
            while (i12 < i11) {
                int i15 = i13;
                int i16 = 0;
                long j2 = 0;
                while (i16 < this.g) {
                    i15++;
                    i16++;
                    j2 ^= (long) ((bArr4[i15] & UnsignedBytes.MAX_VALUE) << (i16 << 3));
                }
                int i17 = 0;
                while (i17 < i3) {
                    i14 += (int) (j2 & ((long) this.j));
                    j2 >>>= this.g;
                    i17++;
                    i11 = i11;
                    i3 = 8;
                }
                int i18 = i11;
                i12++;
                i13 = i15;
                i3 = 8;
            }
            int i19 = this.b % this.g;
            int i20 = 0;
            long j3 = 0;
            while (i20 < i19) {
                i13++;
                i20++;
                j3 ^= (long) ((bArr4[i13] & UnsignedBytes.MAX_VALUE) << (i20 << 3));
            }
            int i21 = i19 << 3;
            for (int i22 = 0; i22 < i21; i22 += this.g) {
                i14 += (int) (j3 & ((long) this.j));
                j3 >>>= this.g;
            }
            this.r = (this.i << this.g) - i14;
            int i23 = this.r;
            i2 = i14;
            for (int i24 = 0; i24 < log; i24 += this.g) {
                i2 += this.j & i23;
                i23 >>>= this.g;
            }
        } else if (this.g < 57) {
            int i25 = 0;
            int i26 = 0;
            while (i25 <= (this.b << 3) - this.g) {
                int i27 = i25 >>> 3;
                int i28 = i25 % 8;
                i25 += this.g;
                long j4 = 0;
                int i29 = 0;
                while (i27 < ((i25 + 7) >>> 3)) {
                    i29++;
                    i27++;
                    j4 ^= (long) ((bArr4[i27] & UnsignedBytes.MAX_VALUE) << (i29 << 3));
                }
                i26 = (int) (((long) i26) + ((j4 >>> i28) & ((long) this.j)));
            }
            int i30 = i25 >>> 3;
            if (i30 < this.b) {
                int i31 = i25 % 8;
                long j5 = 0;
                int i32 = 0;
                while (i30 < this.b) {
                    i32++;
                    i30++;
                    j5 ^= (long) ((bArr4[i30] & UnsignedBytes.MAX_VALUE) << (i32 << 3));
                }
                i26 = (int) (((long) i26) + ((j5 >>> i31) & ((long) this.j)));
            }
            this.r = (this.i << this.g) - i26;
            int i33 = i26;
            int i34 = this.r;
            for (int i35 = 0; i35 < log; i35 += this.g) {
                i33 = i2 + (this.j & i34);
                i34 >>>= this.g;
            }
        } else {
            i2 = 0;
        }
        this.c = this.i + ((int) Math.ceil(((double) log) / ((double) this.g)));
        this.q = (int) Math.ceil(((double) (this.c + i2)) / ((double) (1 << this.s)));
        this.f = new byte[(this.c * this.b)];
        this.m = 0;
        this.l = 0;
        this.n = 0;
        this.o = 0;
        this.k = 0;
        this.d = new byte[this.b];
        this.t = new byte[this.b];
        System.arraycopy(bArr, 0, this.t, 0, this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.p);
        sb.append("  ");
        String sb2 = sb.toString();
        int[] iArr = new int[9];
        int[] statInt = getStatInt();
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, new int[]{5, this.b});
        byte[][] statByte = getStatByte();
        String str = sb2;
        for (int i2 = 0; i2 < 9; i2++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(statInt[i2]);
            sb3.append(UtilsCuentas.SEPARAOR2);
            str = sb3.toString();
        }
        for (int i3 = 0; i3 < 5; i3++) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(new String(Hex.encode(statByte[i3])));
            sb4.append(UtilsCuentas.SEPARAOR2);
            str = sb4.toString();
        }
        return str;
    }

    public boolean updateSign() {
        for (int i2 = 0; i2 < this.q; i2++) {
            if (this.m < this.c) {
                a();
            }
            if (this.m == this.c) {
                return true;
            }
        }
        return false;
    }
}
