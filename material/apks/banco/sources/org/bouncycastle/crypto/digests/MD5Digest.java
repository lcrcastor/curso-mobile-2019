package org.bouncycastle.crypto.digests;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import org.bouncycastle.util.Memoable;

public class MD5Digest extends GeneralDigest {
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] e = new int[16];
    private int f;

    public MD5Digest() {
        reset();
    }

    public MD5Digest(MD5Digest mD5Digest) {
        super((GeneralDigest) mD5Digest);
        a(mD5Digest);
    }

    private int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private int a(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 3] = (byte) (i >>> 24);
    }

    private void a(MD5Digest mD5Digest) {
        super.copyIn(mD5Digest);
        this.a = mD5Digest.a;
        this.b = mD5Digest.b;
        this.c = mD5Digest.c;
        this.d = mD5Digest.d;
        System.arraycopy(mD5Digest.e, 0, this.e, 0, mD5Digest.e.length);
        this.f = mD5Digest.f;
    }

    private int b(int i, int i2, int i3) {
        return (i & i3) | (i2 & (i3 ^ -1));
    }

    private int c(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private int d(int i, int i2, int i3) {
        return (i | (i3 ^ -1)) ^ i2;
    }

    public Memoable copy() {
        return new MD5Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        a(this.a, bArr, i);
        a(this.b, bArr, i + 4);
        a(this.c, bArr, i + 8);
        a(this.d, bArr, i + 12);
        reset();
        return 16;
    }

    public String getAlgorithmName() {
        return CommonUtils.MD5_INSTANCE;
    }

    public int getDigestSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        int i4 = this.d;
        int a2 = a(((i + a(i2, i3, i4)) + this.e[0]) - 680876936, 7) + i2;
        int a3 = a(((i4 + a(a2, i2, i3)) + this.e[1]) - 389564586, 12) + a2;
        int a4 = a(i3 + a(a3, a2, i2) + this.e[2] + 606105819, 17) + a3;
        int a5 = a(((i2 + a(a4, a3, a2)) + this.e[3]) - 1044525330, 22) + a4;
        int a6 = a(((a2 + a(a5, a4, a3)) + this.e[4]) - 176418897, 7) + a5;
        int a7 = a(a3 + a(a6, a5, a4) + this.e[5] + 1200080426, 12) + a6;
        int a8 = a(((a4 + a(a7, a6, a5)) + this.e[6]) - 1473231341, 17) + a7;
        int a9 = a(((a5 + a(a8, a7, a6)) + this.e[7]) - 45705983, 22) + a8;
        int a10 = a(a6 + a(a9, a8, a7) + this.e[8] + 1770035416, 7) + a9;
        int a11 = a(((a7 + a(a10, a9, a8)) + this.e[9]) - 1958414417, 12) + a10;
        int a12 = a(((a8 + a(a11, a10, a9)) + this.e[10]) - 42063, 17) + a11;
        int a13 = a(((a9 + a(a12, a11, a10)) + this.e[11]) - 1990404162, 22) + a12;
        int a14 = a(a10 + a(a13, a12, a11) + this.e[12] + 1804603682, 7) + a13;
        int a15 = a(((a11 + a(a14, a13, a12)) + this.e[13]) - 40341101, 12) + a14;
        int a16 = a(((a12 + a(a15, a14, a13)) + this.e[14]) - 1502002290, 17) + a15;
        int a17 = a(a13 + a(a16, a15, a14) + this.e[15] + 1236535329, 22) + a16;
        int a18 = a(((a14 + b(a17, a16, a15)) + this.e[1]) - 165796510, 5) + a17;
        int a19 = a(((a15 + b(a18, a17, a16)) + this.e[6]) - 1069501632, 9) + a18;
        int a20 = a(a16 + b(a19, a18, a17) + this.e[11] + 643717713, 14) + a19;
        int a21 = a(((a17 + b(a20, a19, a18)) + this.e[0]) - 373897302, 20) + a20;
        int a22 = a(((a18 + b(a21, a20, a19)) + this.e[5]) - 701558691, 5) + a21;
        int a23 = a(a19 + b(a22, a21, a20) + this.e[10] + 38016083, 9) + a22;
        int a24 = a(((a20 + b(a23, a22, a21)) + this.e[15]) - 660478335, 14) + a23;
        int a25 = a(((a21 + b(a24, a23, a22)) + this.e[4]) - 405537848, 20) + a24;
        int a26 = a(a22 + b(a25, a24, a23) + this.e[9] + 568446438, 5) + a25;
        int a27 = a(((a23 + b(a26, a25, a24)) + this.e[14]) - 1019803690, 9) + a26;
        int a28 = a(((a24 + b(a27, a26, a25)) + this.e[3]) - 187363961, 14) + a27;
        int a29 = a(a25 + b(a28, a27, a26) + this.e[8] + 1163531501, 20) + a28;
        int a30 = a(((a26 + b(a29, a28, a27)) + this.e[13]) - 1444681467, 5) + a29;
        int a31 = a(((a27 + b(a30, a29, a28)) + this.e[2]) - 51403784, 9) + a30;
        int a32 = a(a28 + b(a31, a30, a29) + this.e[7] + 1735328473, 14) + a31;
        int a33 = a(((a29 + b(a32, a31, a30)) + this.e[12]) - 1926607734, 20) + a32;
        int a34 = a(((a30 + c(a33, a32, a31)) + this.e[5]) - 378558, 4) + a33;
        int a35 = a(((a31 + c(a34, a33, a32)) + this.e[8]) - 2022574463, 11) + a34;
        int a36 = a(a32 + c(a35, a34, a33) + this.e[11] + 1839030562, 16) + a35;
        int a37 = a(((a33 + c(a36, a35, a34)) + this.e[14]) - 35309556, 23) + a36;
        int a38 = a(((a34 + c(a37, a36, a35)) + this.e[1]) - 1530992060, 4) + a37;
        int a39 = a(a35 + c(a38, a37, a36) + this.e[4] + 1272893353, 11) + a38;
        int a40 = a(((a36 + c(a39, a38, a37)) + this.e[7]) - 155497632, 16) + a39;
        int a41 = a(((a37 + c(a40, a39, a38)) + this.e[10]) - 1094730640, 23) + a40;
        int a42 = a(a38 + c(a41, a40, a39) + this.e[13] + 681279174, 4) + a41;
        int a43 = a(((a39 + c(a42, a41, a40)) + this.e[0]) - 358537222, 11) + a42;
        int a44 = a(((a40 + c(a43, a42, a41)) + this.e[3]) - 722521979, 16) + a43;
        int a45 = a(a41 + c(a44, a43, a42) + this.e[6] + 76029189, 23) + a44;
        int a46 = a(((a42 + c(a45, a44, a43)) + this.e[9]) - 640364487, 4) + a45;
        int a47 = a(((a43 + c(a46, a45, a44)) + this.e[12]) - 421815835, 11) + a46;
        int a48 = a(a44 + c(a47, a46, a45) + this.e[15] + 530742520, 16) + a47;
        int a49 = a(((a45 + c(a48, a47, a46)) + this.e[2]) - 995338651, 23) + a48;
        int a50 = a(((a46 + d(a49, a48, a47)) + this.e[0]) - 198630844, 6) + a49;
        int a51 = a(a47 + d(a50, a49, a48) + this.e[7] + 1126891415, 10) + a50;
        int a52 = a(((a48 + d(a51, a50, a49)) + this.e[14]) - 1416354905, 15) + a51;
        int a53 = a(((a49 + d(a52, a51, a50)) + this.e[5]) - 57434055, 21) + a52;
        int a54 = a(a50 + d(a53, a52, a51) + this.e[12] + 1700485571, 6) + a53;
        int a55 = a(((a51 + d(a54, a53, a52)) + this.e[3]) - 1894986606, 10) + a54;
        int a56 = a(((a52 + d(a55, a54, a53)) + this.e[10]) - 1051523, 15) + a55;
        int a57 = a(((a53 + d(a56, a55, a54)) + this.e[1]) - 2054922799, 21) + a56;
        int a58 = a(a54 + d(a57, a56, a55) + this.e[8] + 1873313359, 6) + a57;
        int a59 = a(((a55 + d(a58, a57, a56)) + this.e[15]) - 30611744, 10) + a58;
        int a60 = a(((a56 + d(a59, a58, a57)) + this.e[6]) - 1560198380, 15) + a59;
        int a61 = a(a57 + d(a60, a59, a58) + this.e[13] + 1309151649, 21) + a60;
        int a62 = a(((a58 + d(a61, a60, a59)) + this.e[4]) - 145523070, 6) + a61;
        int a63 = a(((a59 + d(a62, a61, a60)) + this.e[11]) - 1120210379, 10) + a62;
        int a64 = a(a60 + d(a63, a62, a61) + this.e[2] + 718787259, 15) + a63;
        int a65 = a(((a61 + d(a64, a63, a62)) + this.e[9]) - 343485551, 21) + a64;
        this.a += a62;
        this.b += a65;
        this.c += a64;
        this.d += a63;
        this.f = 0;
        for (int i5 = 0; i5 != this.e.length; i5++) {
            this.e[i5] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j) {
        if (this.f > 14) {
            processBlock();
        }
        this.e[14] = (int) (j & -1);
        this.e[15] = (int) (j >>> 32);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        int[] iArr = this.e;
        int i2 = this.f;
        this.f = i2 + 1;
        iArr[i2] = ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        if (this.f == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.f = 0;
        for (int i = 0; i != this.e.length; i++) {
            this.e[i] = 0;
        }
    }

    public void reset(Memoable memoable) {
        a((MD5Digest) memoable);
    }
}
