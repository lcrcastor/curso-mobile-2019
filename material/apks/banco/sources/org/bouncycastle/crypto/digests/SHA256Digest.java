package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public class SHA256Digest extends GeneralDigest implements EncodableDigest {
    static final int[] a = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int[] j = new int[64];
    private int k;

    public SHA256Digest() {
        reset();
    }

    public SHA256Digest(SHA256Digest sHA256Digest) {
        super((GeneralDigest) sHA256Digest);
        a(sHA256Digest);
    }

    public SHA256Digest(byte[] bArr) {
        super(bArr);
        this.b = Pack.bigEndianToInt(bArr, 16);
        this.c = Pack.bigEndianToInt(bArr, 20);
        this.d = Pack.bigEndianToInt(bArr, 24);
        this.e = Pack.bigEndianToInt(bArr, 28);
        this.f = Pack.bigEndianToInt(bArr, 32);
        this.g = Pack.bigEndianToInt(bArr, 36);
        this.h = Pack.bigEndianToInt(bArr, 40);
        this.i = Pack.bigEndianToInt(bArr, 44);
        this.k = Pack.bigEndianToInt(bArr, 48);
        for (int i2 = 0; i2 != this.k; i2++) {
            this.j[i2] = Pack.bigEndianToInt(bArr, (i2 * 4) + 52);
        }
    }

    private int a(int i2) {
        return ((i2 << 10) | (i2 >>> 22)) ^ (((i2 >>> 2) | (i2 << 30)) ^ ((i2 >>> 13) | (i2 << 19)));
    }

    private int a(int i2, int i3, int i4) {
        return ((i2 ^ -1) & i4) ^ (i3 & i2);
    }

    private void a(SHA256Digest sHA256Digest) {
        super.copyIn(sHA256Digest);
        this.b = sHA256Digest.b;
        this.c = sHA256Digest.c;
        this.d = sHA256Digest.d;
        this.e = sHA256Digest.e;
        this.f = sHA256Digest.f;
        this.g = sHA256Digest.g;
        this.h = sHA256Digest.h;
        this.i = sHA256Digest.i;
        System.arraycopy(sHA256Digest.j, 0, this.j, 0, sHA256Digest.j.length);
        this.k = sHA256Digest.k;
    }

    private int b(int i2) {
        return ((i2 << 7) | (i2 >>> 25)) ^ (((i2 >>> 6) | (i2 << 26)) ^ ((i2 >>> 11) | (i2 << 21)));
    }

    private int b(int i2, int i3, int i4) {
        return ((i2 & i4) ^ (i2 & i3)) ^ (i3 & i4);
    }

    private int c(int i2) {
        return (i2 >>> 3) ^ (((i2 >>> 7) | (i2 << 25)) ^ ((i2 >>> 18) | (i2 << 14)));
    }

    private int d(int i2) {
        return (i2 >>> 10) ^ (((i2 >>> 17) | (i2 << 15)) ^ ((i2 >>> 19) | (i2 << 13)));
    }

    public Memoable copy() {
        return new SHA256Digest(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        finish();
        Pack.intToBigEndian(this.b, bArr, i2);
        Pack.intToBigEndian(this.c, bArr, i2 + 4);
        Pack.intToBigEndian(this.d, bArr, i2 + 8);
        Pack.intToBigEndian(this.e, bArr, i2 + 12);
        Pack.intToBigEndian(this.f, bArr, i2 + 16);
        Pack.intToBigEndian(this.g, bArr, i2 + 20);
        Pack.intToBigEndian(this.h, bArr, i2 + 24);
        Pack.intToBigEndian(this.i, bArr, i2 + 28);
        reset();
        return 32;
    }

    public String getAlgorithmName() {
        return "SHA-256";
    }

    public int getDigestSize() {
        return 32;
    }

    public byte[] getEncodedState() {
        byte[] bArr = new byte[((this.k * 4) + 52)];
        super.populateState(bArr);
        Pack.intToBigEndian(this.b, bArr, 16);
        Pack.intToBigEndian(this.c, bArr, 20);
        Pack.intToBigEndian(this.d, bArr, 24);
        Pack.intToBigEndian(this.e, bArr, 28);
        Pack.intToBigEndian(this.f, bArr, 32);
        Pack.intToBigEndian(this.g, bArr, 36);
        Pack.intToBigEndian(this.h, bArr, 40);
        Pack.intToBigEndian(this.i, bArr, 44);
        Pack.intToBigEndian(this.k, bArr, 48);
        for (int i2 = 0; i2 != this.k; i2++) {
            Pack.intToBigEndian(this.j[i2], bArr, (i2 * 4) + 52);
        }
        return bArr;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        for (int i2 = 16; i2 <= 63; i2++) {
            this.j[i2] = d(this.j[i2 - 2]) + this.j[i2 - 7] + c(this.j[i2 - 15]) + this.j[i2 - 16];
        }
        int i3 = this.b;
        int i4 = this.c;
        int i5 = this.d;
        int i6 = this.e;
        int i7 = this.f;
        int i8 = this.g;
        int i9 = this.h;
        int i10 = this.i;
        int i11 = i4;
        int i12 = i5;
        int i13 = 0;
        int i14 = i3;
        for (int i15 = 0; i15 < 8; i15++) {
            int b2 = i10 + b(i7) + a(i7, i8, i9) + a[i13] + this.j[i13];
            int i16 = i6 + b2;
            int a2 = b2 + a(i14) + b(i14, i11, i12);
            int i17 = i13 + 1;
            int b3 = i9 + b(i16) + a(i16, i7, i8) + a[i17] + this.j[i17];
            int i18 = i12 + b3;
            int a3 = b3 + a(a2) + b(a2, i14, i11);
            int i19 = i17 + 1;
            int b4 = i8 + b(i18) + a(i18, i16, i7) + a[i19] + this.j[i19];
            int i20 = i11 + b4;
            int a4 = b4 + a(a3) + b(a3, a2, i14);
            int i21 = i19 + 1;
            int b5 = i7 + b(i20) + a(i20, i18, i16) + a[i21] + this.j[i21];
            int i22 = i14 + b5;
            int a5 = b5 + a(a4) + b(a4, a3, a2);
            int i23 = i21 + 1;
            int b6 = i16 + b(i22) + a(i22, i20, i18) + a[i23] + this.j[i23];
            i10 = a2 + b6;
            i6 = b6 + a(a5) + b(a5, a4, a3);
            int i24 = i23 + 1;
            int b7 = i18 + b(i10) + a(i10, i22, i20) + a[i24] + this.j[i24];
            i9 = a3 + b7;
            i12 = b7 + a(i6) + b(i6, a5, a4);
            int i25 = i24 + 1;
            int b8 = i20 + b(i9) + a(i9, i10, i22) + a[i25] + this.j[i25];
            i8 = a4 + b8;
            i11 = b8 + a(i12) + b(i12, i6, a5);
            int i26 = i25 + 1;
            int b9 = i22 + b(i8) + a(i8, i9, i10) + a[i26] + this.j[i26];
            i7 = a5 + b9;
            i14 = b9 + a(i11) + b(i11, i12, i6);
            i13 = i26 + 1;
        }
        this.b += i14;
        this.c += i11;
        this.d += i12;
        this.e += i6;
        this.f += i7;
        this.g += i8;
        this.h += i9;
        this.i += i10;
        this.k = 0;
        for (int i27 = 0; i27 < 16; i27++) {
            this.j[i27] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j2) {
        if (this.k > 14) {
            processBlock();
        }
        this.j[14] = (int) (j2 >>> 32);
        this.j[15] = (int) (j2 & -1);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        this.j[this.k] = (bArr[i4 + 1] & UnsignedBytes.MAX_VALUE) | (bArr[i2] << Ascii.CAN) | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i4] & UnsignedBytes.MAX_VALUE) << 8);
        int i5 = this.k + 1;
        this.k = i5;
        if (i5 == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.b = 1779033703;
        this.c = -1150833019;
        this.d = 1013904242;
        this.e = -1521486534;
        this.f = 1359893119;
        this.g = -1694144372;
        this.h = 528734635;
        this.i = 1541459225;
        this.k = 0;
        for (int i2 = 0; i2 != this.j.length; i2++) {
            this.j[i2] = 0;
        }
    }

    public void reset(Memoable memoable) {
        a((SHA256Digest) memoable);
    }
}
