package org.bouncycastle.crypto.digests;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Arrays;

public class SHA3Digest implements ExtendedDigest {
    private static long[] d = a();
    private static int[] e = b();
    long[] a = new long[5];
    long[] b = new long[25];
    long[] c = new long[5];
    private byte[] f = new byte[200];
    private byte[] g = new byte[192];
    private int h;
    private int i;
    private int j;
    private boolean k;
    private int l;
    private byte[] m;
    private byte[] n;

    public SHA3Digest() {
        a(0);
    }

    public SHA3Digest(int i2) {
        a(i2);
    }

    public SHA3Digest(SHA3Digest sHA3Digest) {
        System.arraycopy(sHA3Digest.f, 0, this.f, 0, sHA3Digest.f.length);
        System.arraycopy(sHA3Digest.g, 0, this.g, 0, sHA3Digest.g.length);
        this.h = sHA3Digest.h;
        this.i = sHA3Digest.i;
        this.j = sHA3Digest.j;
        this.k = sHA3Digest.k;
        this.l = sHA3Digest.l;
        this.m = Arrays.clone(sHA3Digest.m);
        this.n = Arrays.clone(sHA3Digest.n);
    }

    private void a(int i2) {
        int i3;
        int i4;
        if (i2 != 0) {
            if (i2 == 224) {
                i3 = 1152;
                i4 = 448;
            } else if (i2 == 256) {
                b(1088, 512);
                return;
            } else if (i2 != 288) {
                if (i2 == 384) {
                    i3 = 832;
                    i4 = 768;
                } else if (i2 != 512) {
                    throw new IllegalArgumentException("bitLength must be one of 224, 256, 384, or 512.");
                } else {
                    b(576, 1024);
                    return;
                }
            }
            b(i3, i4);
            return;
        }
        b(1024, 576);
    }

    private void a(int i2, int i3) {
        for (int i4 = i2; i4 != i2 + i3; i4++) {
            this.g[i4] = 0;
        }
    }

    private void a(byte[] bArr, int i2, long j2) {
        long j3 = j2 % 8;
        if (j3 == 0) {
            b(bArr, i2, j2);
            return;
        }
        b(bArr, i2, j2 - j3);
        b(new byte[]{(byte) (bArr[((int) (j2 / 8)) + i2] >> ((int) (8 - j3)))}, i2, j3);
    }

    private void a(byte[] bArr, byte[] bArr2) {
        System.arraycopy(bArr, 0, bArr2, 0, 128);
    }

    private void a(byte[] bArr, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) (bArr[i3] ^ bArr2[i3]);
        }
        b(bArr);
    }

    private void a(byte[] bArr, long[] jArr) {
        for (int i2 = 0; i2 < 25; i2++) {
            int i3 = i2 * 8;
            for (int i4 = 0; i4 < 8; i4++) {
                bArr[i3 + i4] = (byte) ((int) ((jArr[i2] >>> (i4 * 8)) & 255));
            }
        }
    }

    private void a(long[] jArr) {
        for (int i2 = 0; i2 < 24; i2++) {
            b(jArr);
            c(jArr);
            d(jArr);
            e(jArr);
            a(jArr, i2);
        }
    }

    private void a(long[] jArr, int i2) {
        jArr[0] = jArr[0] ^ d[i2];
    }

    private void a(long[] jArr, byte[] bArr) {
        for (int i2 = 0; i2 < 25; i2++) {
            jArr[i2] = 0;
            int i3 = i2 * 8;
            for (int i4 = 0; i4 < 8; i4++) {
                jArr[i2] = jArr[i2] | ((((long) bArr[i3 + i4]) & 255) << (i4 * 8));
            }
        }
    }

    private static boolean a(byte[] bArr) {
        boolean z = (bArr[0] & 1) != 0;
        if ((bArr[0] & UnsignedBytes.MAX_POWER_OF_TWO) != 0) {
            bArr[0] = (byte) ((bArr[0] << 1) ^ 113);
            return z;
        }
        bArr[0] = (byte) (bArr[0] << 1);
        return z;
    }

    private static long[] a() {
        long[] jArr = new long[24];
        byte[] bArr = {1};
        for (int i2 = 0; i2 < 24; i2++) {
            jArr[i2] = 0;
            for (int i3 = 0; i3 < 7; i3++) {
                int i4 = (1 << i3) - 1;
                if (a(bArr)) {
                    jArr[i2] = jArr[i2] ^ (1 << i4);
                }
            }
        }
        return jArr;
    }

    private void b(int i2, int i3) {
        if (i2 + i3 != 1600) {
            throw new IllegalStateException("rate + capacity != 1600");
        } else if (i2 <= 0 || i2 >= 1600 || i2 % 64 != 0) {
            throw new IllegalStateException("invalid rate value");
        } else {
            this.h = i2;
            this.j = 0;
            Arrays.fill(this.f, 0);
            Arrays.fill(this.g, 0);
            this.i = 0;
            this.k = false;
            this.l = 0;
            this.j = i3 / 2;
            this.m = new byte[(i2 / 8)];
            this.n = new byte[1];
        }
    }

    private void b(byte[] bArr) {
        long[] jArr = new long[(bArr.length / 8)];
        a(jArr, bArr);
        a(jArr);
        a(bArr, jArr);
    }

    private void b(byte[] bArr, int i2, long j2) {
        byte[] bArr2 = bArr;
        int i3 = i2;
        if (this.i % 8 != 0) {
            throw new IllegalStateException("attempt to absorb with odd length queue.");
        } else if (this.k) {
            throw new IllegalStateException("attempt to absorb while squeezing.");
        } else {
            long j3 = 0;
            while (j3 < j2) {
                long j4 = 8;
                if (this.i != 0 || j2 < ((long) this.h) || j3 > j2 - ((long) this.h)) {
                    int i4 = (int) (j2 - j3);
                    if (this.i + i4 > this.h) {
                        i4 = this.h - this.i;
                    }
                    int i5 = i4 % 8;
                    int i6 = i4 - i5;
                    System.arraycopy(bArr2, ((int) (j3 / 8)) + i3, this.g, this.i / 8, i6 / 8);
                    this.i += i6;
                    long j5 = j3 + ((long) i6);
                    if (this.i == this.h) {
                        c();
                    }
                    if (i5 > 0) {
                        this.g[this.i / 8] = (byte) (((1 << i5) - 1) & bArr2[((int) (j5 / 8)) + i3]);
                        this.i += i5;
                        j3 = j5 + ((long) i5);
                    } else {
                        j3 = j5;
                    }
                } else {
                    long j6 = (j2 - j3) / ((long) this.h);
                    long j7 = 0;
                    while (j7 < j6) {
                        System.arraycopy(bArr2, (int) (((long) i3) + (j3 / j4) + (((long) this.m.length) * j7)), this.m, 0, this.m.length);
                        b(this.f, this.m, this.m.length);
                        j7++;
                        j4 = 8;
                    }
                    j3 += j6 * ((long) this.h);
                }
            }
        }
    }

    private void b(byte[] bArr, byte[] bArr2, int i2) {
        a(bArr, bArr2, i2);
    }

    private void b(long[] jArr) {
        for (int i2 = 0; i2 < 5; i2++) {
            this.a[i2] = 0;
            for (int i3 = 0; i3 < 5; i3++) {
                long[] jArr2 = this.a;
                jArr2[i2] = jArr2[i2] ^ jArr[(i3 * 5) + i2];
            }
        }
        int i4 = 0;
        while (i4 < 5) {
            int i5 = i4 + 1;
            int i6 = i5 % 5;
            long j2 = ((this.a[i6] << 1) ^ (this.a[i6] >>> 63)) ^ this.a[(i4 + 4) % 5];
            for (int i7 = 0; i7 < 5; i7++) {
                int i8 = (i7 * 5) + i4;
                jArr[i8] = jArr[i8] ^ j2;
            }
            i4 = i5;
        }
    }

    private static int[] b() {
        int[] iArr = new int[25];
        int i2 = 0;
        iArr[0] = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < 24) {
            int i5 = i2 + 1;
            iArr[(i3 % 5) + ((i4 % 5) * 5)] = (((i2 + 2) * i5) / 2) % 64;
            i4 = ((i3 * 2) + (i4 * 3)) % 5;
            i3 = ((i3 * 0) + (i4 * 1)) % 5;
            i2 = i5;
        }
        return iArr;
    }

    private void c() {
        b(this.f, this.g, this.h / 8);
        this.i = 0;
    }

    private void c(byte[] bArr, int i2, long j2) {
        if (!this.k) {
            d();
        }
        long j3 = 0;
        if (j2 % 8 != 0) {
            throw new IllegalStateException("outputLength not a multiple of 8");
        }
        while (j3 < j2) {
            if (this.l == 0) {
                b(this.f);
                if (this.h == 1024) {
                    a(this.f, this.g);
                    this.l = 1024;
                } else {
                    c(this.f, this.g, this.h / 64);
                    this.l = this.h;
                }
            }
            int i3 = this.l;
            long j4 = j2 - j3;
            if (((long) i3) > j4) {
                i3 = (int) j4;
            }
            System.arraycopy(this.g, (this.h - this.l) / 8, bArr, ((int) (j3 / 8)) + i2, i3 / 8);
            this.l -= i3;
            j3 += (long) i3;
        }
    }

    private void c(byte[] bArr, byte[] bArr2, int i2) {
        System.arraycopy(bArr, 0, bArr2, 0, i2 * 8);
    }

    private void c(long[] jArr) {
        for (int i2 = 0; i2 < 5; i2++) {
            for (int i3 = 0; i3 < 5; i3++) {
                int i4 = (i3 * 5) + i2;
                jArr[i4] = e[i4] != 0 ? (jArr[i4] << e[i4]) ^ (jArr[i4] >>> (64 - e[i4])) : jArr[i4];
            }
        }
    }

    private void d() {
        if (this.i + 1 == this.h) {
            byte[] bArr = this.g;
            int i2 = this.i / 8;
            bArr[i2] = (byte) (bArr[i2] | (1 << (this.i % 8)));
            c();
            a(0, this.h / 8);
        } else {
            a((this.i + 7) / 8, (this.h / 8) - ((this.i + 7) / 8));
            byte[] bArr2 = this.g;
            int i3 = this.i / 8;
            bArr2[i3] = (byte) (bArr2[i3] | (1 << (this.i % 8)));
        }
        byte[] bArr3 = this.g;
        int i4 = (this.h - 1) / 8;
        bArr3[i4] = (byte) (bArr3[i4] | (1 << ((this.h - 1) % 8)));
        c();
        if (this.h == 1024) {
            a(this.f, this.g);
            this.l = 1024;
        } else {
            c(this.f, this.g, this.h / 64);
            this.l = this.h;
        }
        this.k = true;
    }

    private void d(long[] jArr) {
        System.arraycopy(jArr, 0, this.b, 0, this.b.length);
        for (int i2 = 0; i2 < 5; i2++) {
            for (int i3 = 0; i3 < 5; i3++) {
                jArr[((((i2 * 2) + (i3 * 3)) % 5) * 5) + i3] = this.b[(i3 * 5) + i2];
            }
        }
    }

    private void e(long[] jArr) {
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = 0;
            while (i3 < 5) {
                int i4 = i2 * 5;
                int i5 = i3 + 1;
                this.c[i3] = jArr[i3 + i4] ^ ((jArr[(i5 % 5) + i4] ^ -1) & jArr[((i3 + 2) % 5) + i4]);
                i3 = i5;
            }
            for (int i6 = 0; i6 < 5; i6++) {
                jArr[(i2 * 5) + i6] = this.c[i6];
            }
        }
    }

    public int doFinal(byte[] bArr, int i2) {
        c(bArr, i2, (long) this.j);
        reset();
        return getDigestSize();
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("SHA3-");
        sb.append(this.j);
        return sb.toString();
    }

    public int getByteLength() {
        return this.h / 8;
    }

    public int getDigestSize() {
        return this.j / 8;
    }

    public void reset() {
        a(this.j);
    }

    public void update(byte b2) {
        this.n[0] = b2;
        a(this.n, 0, 8);
    }

    public void update(byte[] bArr, int i2, int i3) {
        a(bArr, i2, ((long) i3) * 8);
    }
}
