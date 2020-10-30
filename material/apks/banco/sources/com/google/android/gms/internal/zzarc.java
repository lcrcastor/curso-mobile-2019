package com.google.android.gms.internal;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

public final class zzarc {
    private final byte[] a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g = SubsamplingScaleImageView.TILE_SIZE_AUTO;
    private int h;
    private int i = 64;
    private int j = 67108864;

    private zzarc(byte[] bArr, int i2, int i3) {
        this.a = bArr;
        this.b = i2;
        this.c = i3 + i2;
        this.e = i2;
    }

    private void a() {
        this.c += this.d;
        int i2 = this.c;
        if (i2 > this.g) {
            this.d = i2 - this.g;
            this.c -= this.d;
            return;
        }
        this.d = 0;
    }

    public static int zzahb(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    public static zzarc zzb(byte[] bArr, int i2, int i3) {
        return new zzarc(bArr, i2, i3);
    }

    public static zzarc zzbd(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static long zzcv(long j2) {
        return (j2 >>> 1) ^ (-(j2 & 1));
    }

    public int cA() {
        return cF();
    }

    public long cB() {
        return cI();
    }

    public boolean cC() {
        return cF() != 0;
    }

    public int cD() {
        return zzahb(cF());
    }

    public long cE() {
        return zzcv(cG());
    }

    public int cF() {
        int i2;
        byte cM = cM();
        if (cM >= 0) {
            return cM;
        }
        byte b2 = cM & Ascii.DEL;
        byte cM2 = cM();
        if (cM2 >= 0) {
            i2 = cM2 << 7;
        } else {
            b2 |= (cM2 & Ascii.DEL) << 7;
            byte cM3 = cM();
            if (cM3 >= 0) {
                i2 = cM3 << Ascii.SO;
            } else {
                b2 |= (cM3 & Ascii.DEL) << Ascii.SO;
                byte cM4 = cM();
                if (cM4 >= 0) {
                    i2 = cM4 << Ascii.NAK;
                } else {
                    byte b3 = b2 | ((cM4 & Ascii.DEL) << Ascii.NAK);
                    byte cM5 = cM();
                    byte b4 = b3 | (cM5 << Ascii.FS);
                    if (cM5 >= 0) {
                        return b4;
                    }
                    for (int i3 = 0; i3 < 5; i3++) {
                        if (cM() >= 0) {
                            return b4;
                        }
                    }
                    throw zzarj.c();
                }
            }
        }
        return b2 | i2;
    }

    public long cG() {
        int i2 = 0;
        long j2 = 0;
        while (i2 < 64) {
            byte cM = cM();
            long j3 = j2 | (((long) (cM & Ascii.DEL)) << i2);
            if ((cM & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return j3;
            }
            i2 += 7;
            j2 = j3;
        }
        throw zzarj.c();
    }

    public int cH() {
        return (cM() & UnsignedBytes.MAX_VALUE) | ((cM() & UnsignedBytes.MAX_VALUE) << 8) | ((cM() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((cM() & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
    }

    public long cI() {
        return (((long) cM()) & 255) | ((((long) cM()) & 255) << 8) | ((((long) cM()) & 255) << 16) | ((((long) cM()) & 255) << 24) | ((((long) cM()) & 255) << 32) | ((((long) cM()) & 255) << 40) | ((((long) cM()) & 255) << 48) | ((((long) cM()) & 255) << 56);
    }

    public int cK() {
        if (this.g == Integer.MAX_VALUE) {
            return -1;
        }
        return this.g - this.e;
    }

    public boolean cL() {
        return this.e == this.c;
    }

    public byte cM() {
        if (this.e == this.c) {
            throw zzarj.a();
        }
        byte[] bArr = this.a;
        int i2 = this.e;
        this.e = i2 + 1;
        return bArr[i2];
    }

    public int cw() {
        if (cL()) {
            this.f = 0;
            return 0;
        }
        this.f = cF();
        if (this.f != 0) {
            return this.f;
        }
        throw zzarj.d();
    }

    public void cx() {
        int cw;
        do {
            cw = cw();
            if (cw == 0) {
                return;
            }
        } while (zzaha(cw));
    }

    public long cy() {
        return cG();
    }

    public long cz() {
        return cG();
    }

    public int getPosition() {
        return this.e - this.b;
    }

    public byte[] readBytes() {
        int cF = cF();
        if (cF < 0) {
            throw zzarj.b();
        } else if (cF == 0) {
            return zzarn.bqM;
        } else {
            if (cF > this.c - this.e) {
                throw zzarj.a();
            }
            byte[] bArr = new byte[cF];
            System.arraycopy(this.a, this.e, bArr, 0, cF);
            this.e += cF;
            return bArr;
        }
    }

    public double readDouble() {
        return Double.longBitsToDouble(cI());
    }

    public float readFloat() {
        return Float.intBitsToFloat(cH());
    }

    public String readString() {
        int cF = cF();
        if (cF < 0) {
            throw zzarj.b();
        } else if (cF > this.c - this.e) {
            throw zzarj.a();
        } else {
            String str = new String(this.a, this.e, cF, zzari.UTF_8);
            this.e += cF;
            return str;
        }
    }

    public void zza(zzark zzark) {
        int cF = cF();
        if (this.h >= this.i) {
            throw zzarj.g();
        }
        int zzahc = zzahc(cF);
        this.h++;
        zzark.zzb(this);
        zzagz(0);
        this.h--;
        zzahd(zzahc);
    }

    public void zza(zzark zzark, int i2) {
        if (this.h >= this.i) {
            throw zzarj.g();
        }
        this.h++;
        zzark.zzb(this);
        zzagz(zzarn.zzaj(i2, 4));
        this.h--;
    }

    public byte[] zzad(int i2, int i3) {
        if (i3 == 0) {
            return zzarn.bqM;
        }
        byte[] bArr = new byte[i3];
        System.arraycopy(this.a, this.b + i2, bArr, 0, i3);
        return bArr;
    }

    public void zzagz(int i2) {
        if (this.f != i2) {
            throw zzarj.e();
        }
    }

    public boolean zzaha(int i2) {
        switch (zzarn.a(i2)) {
            case 0:
                cA();
                return true;
            case 1:
                cI();
                return true;
            case 2:
                zzahf(cF());
                return true;
            case 3:
                cx();
                zzagz(zzarn.zzaj(zzarn.zzahu(i2), 4));
                return true;
            case 4:
                return false;
            case 5:
                cH();
                return true;
            default:
                throw zzarj.f();
        }
    }

    public int zzahc(int i2) {
        if (i2 < 0) {
            throw zzarj.b();
        }
        int i3 = i2 + this.e;
        int i4 = this.g;
        if (i3 > i4) {
            throw zzarj.a();
        }
        this.g = i3;
        a();
        return i4;
    }

    public void zzahd(int i2) {
        this.g = i2;
        a();
    }

    public void zzahe(int i2) {
        if (i2 > this.e - this.b) {
            int i3 = this.e - this.b;
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i2);
            sb.append(" is beyond current ");
            sb.append(i3);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < 0) {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        } else {
            this.e = this.b + i2;
        }
    }

    public void zzahf(int i2) {
        if (i2 < 0) {
            throw zzarj.b();
        } else if (this.e + i2 > this.g) {
            zzahf(this.g - this.e);
            throw zzarj.a();
        } else if (i2 <= this.c - this.e) {
            this.e += i2;
        } else {
            throw zzarj.a();
        }
    }
}
