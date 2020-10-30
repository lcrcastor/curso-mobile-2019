package org.bouncycastle.jce.provider;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

class OldPKCS12ParametersGenerator extends PBEParametersGenerator {
    private Digest a;
    private int b;
    private int c;

    public OldPKCS12ParametersGenerator(Digest digest) {
        this.a = digest;
        if (digest instanceof MD5Digest) {
            this.b = 16;
        } else if (!(digest instanceof SHA1Digest) && !(digest instanceof RIPEMD160Digest)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Digest ");
            sb.append(digest.getAlgorithmName());
            sb.append(" unsupported");
            throw new IllegalArgumentException(sb.toString());
        } else {
            this.b = 20;
        }
        this.c = 64;
    }

    private void a(byte[] bArr, int i, byte[] bArr2) {
        int i2 = (bArr2[bArr2.length - 1] & UnsignedBytes.MAX_VALUE) + (bArr[(bArr2.length + i) - 1] & UnsignedBytes.MAX_VALUE) + 1;
        bArr[(bArr2.length + i) - 1] = (byte) i2;
        int i3 = i2 >>> 8;
        for (int length = bArr2.length - 2; length >= 0; length--) {
            int i4 = i + length;
            int i5 = i3 + (bArr2[length] & UnsignedBytes.MAX_VALUE) + (bArr[i4] & UnsignedBytes.MAX_VALUE);
            bArr[i4] = (byte) i5;
            i3 = i5 >>> 8;
        }
    }

    private byte[] a(int i, int i2) {
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3 = new byte[this.c];
        byte[] bArr4 = new byte[i2];
        for (int i3 = 0; i3 != bArr3.length; i3++) {
            bArr3[i3] = (byte) i;
        }
        if (this.salt == null || this.salt.length == 0) {
            bArr = new byte[0];
        } else {
            bArr = new byte[(this.c * (((this.salt.length + this.c) - 1) / this.c))];
            for (int i4 = 0; i4 != bArr.length; i4++) {
                bArr[i4] = this.salt[i4 % this.salt.length];
            }
        }
        if (this.password == null || this.password.length == 0) {
            bArr2 = new byte[0];
        } else {
            bArr2 = new byte[(this.c * (((this.password.length + this.c) - 1) / this.c))];
            for (int i5 = 0; i5 != bArr2.length; i5++) {
                bArr2[i5] = this.password[i5 % this.password.length];
            }
        }
        byte[] bArr5 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
        byte[] bArr6 = new byte[this.c];
        int i6 = ((i2 + this.b) - 1) / this.b;
        for (int i7 = 1; i7 <= i6; i7++) {
            byte[] bArr7 = new byte[this.b];
            this.a.update(bArr3, 0, bArr3.length);
            this.a.update(bArr5, 0, bArr5.length);
            this.a.doFinal(bArr7, 0);
            for (int i8 = 1; i8 != this.iterationCount; i8++) {
                this.a.update(bArr7, 0, bArr7.length);
                this.a.doFinal(bArr7, 0);
            }
            for (int i9 = 0; i9 != bArr6.length; i9++) {
                bArr6[i7] = bArr7[i9 % bArr7.length];
            }
            for (int i10 = 0; i10 != bArr5.length / this.c; i10++) {
                a(bArr5, this.c * i10, bArr6);
            }
            if (i7 == i6) {
                int i11 = i7 - 1;
                System.arraycopy(bArr7, 0, bArr4, this.b * i11, bArr4.length - (i11 * this.b));
            } else {
                System.arraycopy(bArr7, 0, bArr4, (i7 - 1) * this.b, bArr7.length);
            }
        }
        return bArr4;
    }

    public CipherParameters generateDerivedMacParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(a(3, i2), 0, i2);
    }

    public CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(a(1, i2), 0, i2);
    }

    public CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] a2 = a(1, i3);
        return new ParametersWithIV(new KeyParameter(a2, 0, i3), a(2, i4), 0, i4);
    }
}
