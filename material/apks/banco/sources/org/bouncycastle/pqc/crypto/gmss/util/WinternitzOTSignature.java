package org.bouncycastle.pqc.crypto.gmss.util;

import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import org.bouncycastle.crypto.Digest;

public class WinternitzOTSignature {
    private Digest a;
    private int b = this.a.getDigestSize();
    private int c;
    private byte[][] d;
    private int e;
    private GMSSRandom f = new GMSSRandom(this.a);
    private int g;
    private int h;

    public WinternitzOTSignature(byte[] bArr, Digest digest, int i) {
        this.e = i;
        this.a = digest;
        double d2 = (double) i;
        this.g = (int) Math.ceil(((double) (this.b << 3)) / d2);
        this.h = getLog((this.g << i) + 1);
        this.c = this.g + ((int) Math.ceil(((double) this.h) / d2));
        this.d = (byte[][]) Array.newInstance(byte.class, new int[]{this.c, this.b});
        byte[] bArr2 = new byte[this.b];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        for (int i2 = 0; i2 < this.c; i2++) {
            this.d[i2] = this.f.nextSeed(bArr2);
        }
    }

    public int getLog(int i) {
        int i2 = 1;
        int i3 = 2;
        while (i3 < i) {
            i3 <<= 1;
            i2++;
        }
        return i2;
    }

    public byte[][] getPrivateKey() {
        return this.d;
    }

    public byte[] getPublicKey() {
        byte[] bArr = new byte[(this.c * this.b)];
        byte[] bArr2 = new byte[this.b];
        int i = 1 << this.e;
        for (int i2 = 0; i2 < this.c; i2++) {
            this.a.update(this.d[i2], 0, this.d[i2].length);
            byte[] bArr3 = new byte[this.a.getDigestSize()];
            this.a.doFinal(bArr3, 0);
            for (int i3 = 2; i3 < i; i3++) {
                this.a.update(bArr3, 0, bArr3.length);
                bArr3 = new byte[this.a.getDigestSize()];
                this.a.doFinal(bArr3, 0);
            }
            System.arraycopy(bArr3, 0, bArr, this.b * i2, this.b);
        }
        this.a.update(bArr, 0, bArr.length);
        byte[] bArr4 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr4, 0);
        return bArr4;
    }

    public byte[] getSignature(byte[] bArr) {
        byte[] bArr2 = bArr;
        byte[] bArr3 = new byte[(this.c * this.b)];
        byte[] bArr4 = new byte[this.b];
        this.a.update(bArr2, 0, bArr2.length);
        byte[] bArr5 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr5, 0);
        int i = 8;
        if (8 % this.e == 0) {
            int i2 = 8 / this.e;
            int i3 = (1 << this.e) - 1;
            byte[] bArr6 = new byte[this.b];
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i4 < bArr5.length) {
                byte[] bArr7 = bArr6;
                int i7 = i6;
                int i8 = i5;
                for (int i9 = 0; i9 < i2; i9++) {
                    int i10 = bArr5[i4] & i3;
                    i8 += i10;
                    System.arraycopy(this.d[i7], 0, bArr7, 0, this.b);
                    while (i10 > 0) {
                        this.a.update(bArr7, 0, bArr7.length);
                        bArr7 = new byte[this.a.getDigestSize()];
                        this.a.doFinal(bArr7, 0);
                        i10--;
                    }
                    System.arraycopy(bArr7, 0, bArr3, this.b * i7, this.b);
                    bArr5[i4] = (byte) (bArr5[i4] >>> this.e);
                    i7++;
                }
                i4++;
                i5 = i8;
                i6 = i7;
                bArr6 = bArr7;
            }
            int i11 = (this.g << this.e) - i5;
            for (int i12 = 0; i12 < this.h; i12 += this.e) {
                System.arraycopy(this.d[i6], 0, bArr6, 0, this.b);
                for (int i13 = i11 & i3; i13 > 0; i13--) {
                    this.a.update(bArr6, 0, bArr6.length);
                    bArr6 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr6, 0);
                }
                System.arraycopy(bArr6, 0, bArr3, this.b * i6, this.b);
                i11 >>>= this.e;
                i6++;
            }
        } else if (this.e < 8) {
            int i14 = this.b / this.e;
            int i15 = (1 << this.e) - 1;
            byte[] bArr8 = new byte[this.b];
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            while (i16 < i14) {
                int i20 = i17;
                int i21 = 0;
                long j = 0;
                while (i21 < this.e) {
                    i20++;
                    i21++;
                    j ^= (long) ((bArr5[i20] & UnsignedBytes.MAX_VALUE) << (i21 << 3));
                }
                int i22 = 0;
                while (i22 < i) {
                    int i23 = i22;
                    int i24 = (int) (j & ((long) i15));
                    i19 += i24;
                    System.arraycopy(this.d[i18], 0, bArr8, 0, this.b);
                    while (i24 > 0) {
                        this.a.update(bArr8, 0, bArr8.length);
                        bArr8 = new byte[this.a.getDigestSize()];
                        this.a.doFinal(bArr8, 0);
                        i24--;
                    }
                    System.arraycopy(bArr8, 0, bArr3, this.b * i18, this.b);
                    j >>>= this.e;
                    i18++;
                    i22 = i23 + 1;
                    i = 8;
                }
                i16++;
                i17 = i20;
                i = 8;
            }
            int i25 = this.b % this.e;
            int i26 = 0;
            long j2 = 0;
            while (i26 < i25) {
                i17++;
                i26++;
                j2 ^= (long) ((bArr5[i17] & UnsignedBytes.MAX_VALUE) << (i26 << 3));
            }
            int i27 = i25 << 3;
            for (int i28 = 0; i28 < i27; i28 += this.e) {
                int i29 = (int) (j2 & ((long) i15));
                i19 += i29;
                System.arraycopy(this.d[i18], 0, bArr8, 0, this.b);
                while (i29 > 0) {
                    this.a.update(bArr8, 0, bArr8.length);
                    bArr8 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr8, 0);
                    i29--;
                }
                System.arraycopy(bArr8, 0, bArr3, this.b * i18, this.b);
                j2 >>>= this.e;
                i18++;
            }
            int i30 = (this.g << this.e) - i19;
            for (int i31 = 0; i31 < this.h; i31 += this.e) {
                System.arraycopy(this.d[i18], 0, bArr8, 0, this.b);
                for (int i32 = i30 & i15; i32 > 0; i32--) {
                    this.a.update(bArr8, 0, bArr8.length);
                    bArr8 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr8, 0);
                }
                System.arraycopy(bArr8, 0, bArr3, this.b * i18, this.b);
                i30 >>>= this.e;
                i18++;
            }
        } else if (this.e < 57) {
            int i33 = (this.b << 3) - this.e;
            int i34 = (1 << this.e) - 1;
            byte[] bArr9 = new byte[this.b];
            int i35 = 0;
            int i36 = 0;
            int i37 = 0;
            while (i35 <= i33) {
                int i38 = i35 >>> 3;
                int i39 = i35 % 8;
                i35 += this.e;
                long j3 = 0;
                int i40 = 0;
                while (i38 < ((i35 + 7) >>> 3)) {
                    i40++;
                    i38++;
                    j3 ^= (long) ((bArr5[i38] & UnsignedBytes.MAX_VALUE) << (i40 << 3));
                }
                long j4 = (j3 >>> i39) & ((long) i34);
                i36 = (int) (((long) i36) + j4);
                System.arraycopy(this.d[i37], 0, bArr9, 0, this.b);
                while (j4 > 0) {
                    this.a.update(bArr9, 0, bArr9.length);
                    bArr9 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr9, 0);
                    j4--;
                }
                System.arraycopy(bArr9, 0, bArr3, this.b * i37, this.b);
                i37++;
            }
            int i41 = i35 >>> 3;
            if (i41 < this.b) {
                int i42 = i35 % 8;
                long j5 = 0;
                int i43 = 0;
                while (i41 < this.b) {
                    i43++;
                    i41++;
                    j5 ^= (long) ((bArr5[i41] & UnsignedBytes.MAX_VALUE) << (i43 << 3));
                }
                long j6 = (j5 >>> i42) & ((long) i34);
                i36 = (int) (((long) i36) + j6);
                System.arraycopy(this.d[i37], 0, bArr9, 0, this.b);
                while (j6 > 0) {
                    this.a.update(bArr9, 0, bArr9.length);
                    bArr9 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr9, 0);
                    j6--;
                }
                System.arraycopy(bArr9, 0, bArr3, this.b * i37, this.b);
                i37++;
            }
            int i44 = (this.g << this.e) - i36;
            for (int i45 = 0; i45 < this.h; i45 += this.e) {
                System.arraycopy(this.d[i37], 0, bArr9, 0, this.b);
                for (long j7 = (long) (i44 & i34); j7 > 0; j7--) {
                    this.a.update(bArr9, 0, bArr9.length);
                    bArr9 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr9, 0);
                }
                System.arraycopy(bArr9, 0, bArr3, this.b * i37, this.b);
                i44 >>>= this.e;
                i37++;
            }
        }
        return bArr3;
    }
}
