package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.MemoableResetException;
import org.bouncycastle.util.Pack;

public class SHA512tDigest extends LongDigest {
    private int b;
    private long c;
    private long d;
    private long e;
    private long f;
    private long g;
    private long h;
    private long i;
    private long j;

    public SHA512tDigest(int i2) {
        if (i2 >= 512) {
            throw new IllegalArgumentException("bitLength cannot be >= 512");
        } else if (i2 % 8 != 0) {
            throw new IllegalArgumentException("bitLength needs to be a multiple of 8");
        } else if (i2 == 384) {
            throw new IllegalArgumentException("bitLength cannot be 384 use SHA384 instead");
        } else {
            this.b = i2 / 8;
            a(this.b * 8);
            reset();
        }
    }

    public SHA512tDigest(SHA512tDigest sHA512tDigest) {
        super(sHA512tDigest);
        this.b = sHA512tDigest.b;
        reset(sHA512tDigest);
    }

    public SHA512tDigest(byte[] bArr) {
        this(a(bArr));
        restoreState(bArr);
    }

    private static int a(byte[] bArr) {
        return Pack.bigEndianToInt(bArr, bArr.length - 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x007f, code lost:
        if (r4 > 10) goto L_0x006f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r4) {
        /*
            r3 = this;
            r0 = -3482333909917012819(0xcfac43c256196cad, double:-6.392239886847908E75)
            r3.H1 = r0
            r0 = 2216346199247487646(0x1ec20b20216f029e, double:1.604250256667292E-160)
            r3.H2 = r0
            r0 = -7364697282686394994(0x99cb56d75b315d8e, double:-2.0106609494103695E-184)
            r3.H3 = r0
            r0 = 65953792586715988(0xea509ffab89354, double:2.9978976005667514E-304)
            r3.H4 = r0
            r0 = -816286391624063116(0xf4abf7da08432774, double:-1.0252515228978657E254)
            r3.H5 = r0
            r0 = 4512832404995164602(0x3ea0cd298e9bc9ba, double:5.007211971427005E-7)
            r3.H6 = r0
            r0 = -5033199132376557362(0xba267c0e5ee418ce, double:-1.418977391716189E-28)
            r3.H7 = r0
            r0 = -124578254951840548(0xfe4568bcb6db84dc, double:-1.7921927020935902E300)
            r3.H8 = r0
            r0 = 83
            r3.update(r0)
            r0 = 72
            r3.update(r0)
            r0 = 65
            r3.update(r0)
            r0 = 45
            r3.update(r0)
            r0 = 53
            r3.update(r0)
            r0 = 49
            r3.update(r0)
            r0 = 50
            r3.update(r0)
            r0 = 47
            r3.update(r0)
            r0 = 100
            r1 = 10
            if (r4 <= r0) goto L_0x007f
            int r2 = r4 / 100
            int r2 = r2 + 48
            byte r2 = (byte) r2
            r3.update(r2)
            int r4 = r4 % r0
        L_0x006f:
            int r0 = r4 / 10
            int r0 = r0 + 48
            byte r0 = (byte) r0
            r3.update(r0)
            int r4 = r4 % r1
        L_0x0078:
            int r4 = r4 + 48
            byte r4 = (byte) r4
            r3.update(r4)
            goto L_0x0082
        L_0x007f:
            if (r4 <= r1) goto L_0x0078
            goto L_0x006f
        L_0x0082:
            r3.finish()
            long r0 = r3.H1
            r3.c = r0
            long r0 = r3.H2
            r3.d = r0
            long r0 = r3.H3
            r3.e = r0
            long r0 = r3.H4
            r3.f = r0
            long r0 = r3.H5
            r3.g = r0
            long r0 = r3.H6
            r3.h = r0
            long r0 = r3.H7
            r3.i = r0
            long r0 = r3.H8
            r3.j = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.digests.SHA512tDigest.a(int):void");
    }

    private static void a(int i2, byte[] bArr, int i3, int i4) {
        int min = Math.min(4, i4);
        while (true) {
            min--;
            if (min >= 0) {
                bArr[i3 + min] = (byte) (i2 >>> ((3 - min) * 8));
            } else {
                return;
            }
        }
    }

    private static void a(long j2, byte[] bArr, int i2, int i3) {
        if (i3 > 0) {
            a((int) (j2 >>> 32), bArr, i2, i3);
            if (i3 > 4) {
                a((int) (j2 & 4294967295L), bArr, i2 + 4, i3 - 4);
            }
        }
    }

    public Memoable copy() {
        return new SHA512tDigest(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        finish();
        a(this.H1, bArr, i2, this.b);
        a(this.H2, bArr, i2 + 8, this.b - 8);
        a(this.H3, bArr, i2 + 16, this.b - 16);
        a(this.H4, bArr, i2 + 24, this.b - 24);
        a(this.H5, bArr, i2 + 32, this.b - 32);
        a(this.H6, bArr, i2 + 40, this.b - 40);
        a(this.H7, bArr, i2 + 48, this.b - 48);
        a(this.H8, bArr, i2 + 56, this.b - 56);
        reset();
        return this.b;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("SHA-512/");
        sb.append(Integer.toString(this.b * 8));
        return sb.toString();
    }

    public int getDigestSize() {
        return this.b;
    }

    public byte[] getEncodedState() {
        int encodedStateSize = getEncodedStateSize();
        byte[] bArr = new byte[(encodedStateSize + 4)];
        populateState(bArr);
        Pack.intToBigEndian(this.b * 8, bArr, encodedStateSize);
        return bArr;
    }

    public void reset() {
        super.reset();
        this.H1 = this.c;
        this.H2 = this.d;
        this.H3 = this.e;
        this.H4 = this.f;
        this.H5 = this.g;
        this.H6 = this.h;
        this.H7 = this.i;
        this.H8 = this.j;
    }

    public void reset(Memoable memoable) {
        SHA512tDigest sHA512tDigest = (SHA512tDigest) memoable;
        if (this.b != sHA512tDigest.b) {
            throw new MemoableResetException("digestLength inappropriate in other");
        }
        super.copyIn(sHA512tDigest);
        this.c = sHA512tDigest.c;
        this.d = sHA512tDigest.d;
        this.e = sHA512tDigest.e;
        this.f = sHA512tDigest.f;
        this.g = sHA512tDigest.g;
        this.h = sHA512tDigest.h;
        this.i = sHA512tDigest.i;
        this.j = sHA512tDigest.j;
    }
}
