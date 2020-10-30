package org.bouncycastle.pqc.crypto.gmss.util;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.Digest;

public class WinternitzOTSVerify {
    private Digest a;
    private int b;

    public WinternitzOTSVerify(Digest digest, int i) {
        this.b = i;
        this.a = digest;
    }

    public byte[] Verify(byte[] bArr, byte[] bArr2) {
        int i;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        int digestSize = this.a.getDigestSize();
        byte[] bArr5 = new byte[digestSize];
        this.a.update(bArr3, 0, bArr3.length);
        byte[] bArr6 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr6, 0);
        int i2 = digestSize << 3;
        int i3 = ((this.b - 1) + i2) / this.b;
        int log = getLog((i3 << this.b) + 1);
        int i4 = ((((this.b + log) - 1) / this.b) + i3) * digestSize;
        if (i4 != bArr4.length) {
            return null;
        }
        byte[] bArr7 = new byte[i4];
        int i5 = 8;
        if (8 % this.b == 0) {
            int i6 = 8 / this.b;
            int i7 = (1 << this.b) - 1;
            byte[] bArr8 = new byte[digestSize];
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            while (i8 < bArr6.length) {
                byte[] bArr9 = bArr8;
                int i11 = i10;
                int i12 = i9;
                int i13 = 0;
                while (i13 < i6) {
                    int i14 = bArr6[i8] & i7;
                    i12 += i14;
                    int i15 = i6;
                    int i16 = i11 * digestSize;
                    System.arraycopy(bArr4, i16, bArr9, 0, digestSize);
                    while (i14 < i7) {
                        int i17 = i12;
                        this.a.update(bArr9, 0, bArr9.length);
                        bArr9 = new byte[this.a.getDigestSize()];
                        this.a.doFinal(bArr9, 0);
                        i14++;
                        i12 = i17;
                        byte[] bArr10 = bArr2;
                    }
                    int i18 = i12;
                    System.arraycopy(bArr9, 0, bArr7, i16, digestSize);
                    bArr6[i8] = (byte) (bArr6[i8] >>> this.b);
                    i11++;
                    i13++;
                    i6 = i15;
                    bArr4 = bArr2;
                }
                int i19 = i6;
                i8++;
                i9 = i12;
                i10 = i11;
                bArr8 = bArr9;
                bArr4 = bArr2;
            }
            int i20 = (i3 << this.b) - i9;
            for (int i21 = 0; i21 < log; i21 += this.b) {
                int i22 = i10 * digestSize;
                System.arraycopy(bArr2, i22, bArr8, 0, digestSize);
                for (int i23 = i20 & i7; i23 < i7; i23++) {
                    this.a.update(bArr8, 0, bArr8.length);
                    bArr8 = new byte[this.a.getDigestSize()];
                    this.a.doFinal(bArr8, 0);
                }
                System.arraycopy(bArr8, 0, bArr7, i22, digestSize);
                i20 >>>= this.b;
                i10++;
            }
        } else {
            byte[] bArr11 = bArr4;
            if (this.b < 8) {
                int i24 = digestSize / this.b;
                int i25 = (1 << this.b) - 1;
                byte[] bArr12 = new byte[digestSize];
                int i26 = 0;
                int i27 = 0;
                int i28 = 0;
                int i29 = 0;
                while (i26 < i24) {
                    int i30 = i27;
                    int i31 = 0;
                    long j = 0;
                    while (i31 < this.b) {
                        i30++;
                        i31++;
                        j ^= (long) ((bArr6[i30] & UnsignedBytes.MAX_VALUE) << (i31 << 3));
                    }
                    byte[] bArr13 = bArr12;
                    int i32 = 0;
                    while (i32 < i5) {
                        int i33 = log;
                        int i34 = i32;
                        int i35 = (int) (j & ((long) i25));
                        i28 += i35;
                        int i36 = i29 * digestSize;
                        System.arraycopy(bArr11, i36, bArr13, 0, digestSize);
                        while (i35 < i25) {
                            this.a.update(bArr13, 0, bArr13.length);
                            bArr13 = new byte[this.a.getDigestSize()];
                            this.a.doFinal(bArr13, 0);
                            i35++;
                        }
                        System.arraycopy(bArr13, 0, bArr7, i36, digestSize);
                        j >>>= this.b;
                        i29++;
                        i32 = i34 + 1;
                        log = i33;
                        i5 = 8;
                    }
                    int i37 = log;
                    i26++;
                    bArr12 = bArr13;
                    i27 = i30;
                    i5 = 8;
                }
                int i38 = log;
                int i39 = digestSize % this.b;
                int i40 = 0;
                long j2 = 0;
                while (i40 < i39) {
                    i27++;
                    i40++;
                    j2 ^= (long) ((bArr6[i27] & UnsignedBytes.MAX_VALUE) << (i40 << 3));
                }
                int i41 = i39 << 3;
                byte[] bArr14 = bArr12;
                for (int i42 = 0; i42 < i41; i42 += this.b) {
                    int i43 = (int) (j2 & ((long) i25));
                    i28 += i43;
                    int i44 = i29 * digestSize;
                    System.arraycopy(bArr11, i44, bArr14, 0, digestSize);
                    while (i43 < i25) {
                        this.a.update(bArr14, 0, bArr14.length);
                        bArr14 = new byte[this.a.getDigestSize()];
                        this.a.doFinal(bArr14, 0);
                        i43++;
                    }
                    System.arraycopy(bArr14, 0, bArr7, i44, digestSize);
                    j2 >>>= this.b;
                    i29++;
                }
                int i45 = (i3 << this.b) - i28;
                int i46 = i38;
                for (int i47 = 0; i47 < i46; i47 += this.b) {
                    int i48 = i29 * digestSize;
                    System.arraycopy(bArr11, i48, bArr14, 0, digestSize);
                    for (int i49 = i45 & i25; i49 < i25; i49++) {
                        this.a.update(bArr14, 0, bArr14.length);
                        bArr14 = new byte[this.a.getDigestSize()];
                        this.a.doFinal(bArr14, 0);
                    }
                    System.arraycopy(bArr14, 0, bArr7, i48, digestSize);
                    i45 >>>= this.b;
                    i29++;
                }
            } else {
                int i50 = log;
                if (this.b < 57) {
                    int i51 = i2 - this.b;
                    int i52 = (1 << this.b) - 1;
                    byte[] bArr15 = new byte[digestSize];
                    int i53 = 0;
                    int i54 = 0;
                    int i55 = 0;
                    while (i53 <= i51) {
                        int i56 = i53 >>> 3;
                        int i57 = i53 % 8;
                        i53 += this.b;
                        int i58 = i56;
                        int i59 = 0;
                        long j3 = 0;
                        while (i58 < ((i53 + 7) >>> 3)) {
                            i59++;
                            i58++;
                            j3 ^= (long) ((bArr6[i58] & UnsignedBytes.MAX_VALUE) << (i59 << 3));
                            i3 = i3;
                            i51 = i51;
                        }
                        int i60 = i51;
                        int i61 = i3;
                        long j4 = (long) i52;
                        long j5 = (j3 >>> i57) & j4;
                        int i62 = i52;
                        i54 = (int) (((long) i54) + j5);
                        int i63 = i55 * digestSize;
                        System.arraycopy(bArr11, i63, bArr15, 0, digestSize);
                        while (j5 < j4) {
                            this.a.update(bArr15, 0, bArr15.length);
                            bArr15 = new byte[this.a.getDigestSize()];
                            this.a.doFinal(bArr15, 0);
                            j5++;
                        }
                        System.arraycopy(bArr15, 0, bArr7, i63, digestSize);
                        i55++;
                        i3 = i61;
                        i51 = i60;
                        i52 = i62;
                    }
                    int i64 = i3;
                    int i65 = i52;
                    int i66 = i53 >>> 3;
                    if (i66 < digestSize) {
                        int i67 = i53 % 8;
                        int i68 = 0;
                        long j6 = 0;
                        while (i66 < digestSize) {
                            i68++;
                            i66++;
                            j6 ^= (long) ((bArr6[i66] & UnsignedBytes.MAX_VALUE) << (i68 << 3));
                        }
                        int i69 = i65;
                        long j7 = (long) i69;
                        long j8 = (j6 >>> i67) & j7;
                        i = i69;
                        i54 = (int) (((long) i54) + j8);
                        int i70 = i55 * digestSize;
                        System.arraycopy(bArr11, i70, bArr15, 0, digestSize);
                        while (j8 < j7) {
                            this.a.update(bArr15, 0, bArr15.length);
                            bArr15 = new byte[this.a.getDigestSize()];
                            this.a.doFinal(bArr15, 0);
                            j8++;
                        }
                        System.arraycopy(bArr15, 0, bArr7, i70, digestSize);
                        i55++;
                    } else {
                        i = i65;
                    }
                    int i71 = (i64 << this.b) - i54;
                    int i72 = 0;
                    while (i72 < i50) {
                        long j9 = (long) (i71 & i);
                        int i73 = i55 * digestSize;
                        System.arraycopy(bArr11, i73, bArr15, 0, digestSize);
                        byte[] bArr16 = bArr7;
                        byte[] bArr17 = bArr15;
                        int i74 = i;
                        for (long j10 = j9; j10 < ((long) i74); j10++) {
                            this.a.update(bArr17, 0, bArr17.length);
                            bArr17 = new byte[this.a.getDigestSize()];
                            this.a.doFinal(bArr17, 0);
                        }
                        byte[] bArr18 = bArr16;
                        System.arraycopy(bArr17, 0, bArr18, i73, digestSize);
                        i71 >>>= this.b;
                        i55++;
                        i72 += this.b;
                        i = i74;
                        bArr7 = bArr18;
                        bArr15 = bArr17;
                    }
                }
            }
        }
        byte[] bArr19 = bArr7;
        byte[] bArr20 = new byte[digestSize];
        this.a.update(bArr19, 0, bArr19.length);
        byte[] bArr21 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr21, 0);
        return bArr21;
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

    public int getSignatureLength() {
        int digestSize = this.a.getDigestSize();
        int i = ((digestSize << 3) + (this.b - 1)) / this.b;
        return digestSize * (i + (((getLog((i << this.b) + 1) + this.b) - 1) / this.b));
    }
}
