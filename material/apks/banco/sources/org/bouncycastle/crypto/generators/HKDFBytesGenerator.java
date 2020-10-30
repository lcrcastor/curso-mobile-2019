package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class HKDFBytesGenerator implements DerivationFunction {
    private HMac a;
    private int b;
    private byte[] c;
    private byte[] d;
    private int e;

    public HKDFBytesGenerator(Digest digest) {
        this.a = new HMac(digest);
        this.b = digest.getDigestSize();
    }

    private KeyParameter a(byte[] bArr, byte[] bArr2) {
        this.a.init(new KeyParameter(bArr2));
        if (bArr == null) {
            this.a.init(new KeyParameter(new byte[this.b]));
        } else {
            this.a.init(new KeyParameter(bArr));
        }
        this.a.update(bArr2, 0, bArr2.length);
        byte[] bArr3 = new byte[this.b];
        this.a.doFinal(bArr3, 0);
        return new KeyParameter(bArr3);
    }

    private void a() {
        int i = (this.e / this.b) + 1;
        if (i >= 256) {
            throw new DataLengthException("HKDF cannot generate more than 255 blocks of HashLen size");
        }
        if (this.e != 0) {
            this.a.update(this.d, 0, this.b);
        }
        this.a.update(this.c, 0, this.c.length);
        this.a.update((byte) i);
        this.a.doFinal(this.d, 0);
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        if (this.e + i2 > this.b * 255) {
            throw new DataLengthException("HKDF may only be used for 255 * HashLen bytes of output");
        }
        if (this.e % this.b == 0) {
            a();
        }
        int i3 = this.e % this.b;
        int min = Math.min(this.b - (this.e % this.b), i2);
        System.arraycopy(this.d, i3, bArr, i, min);
        this.e += min;
        int i4 = i2 - min;
        while (true) {
            i += min;
            if (i4 <= 0) {
                return i2;
            }
            a();
            min = Math.min(this.b, i4);
            System.arraycopy(this.d, 0, bArr, i, min);
            this.e += min;
            i4 -= min;
        }
    }

    public Digest getDigest() {
        return this.a.getUnderlyingDigest();
    }

    public void init(DerivationParameters derivationParameters) {
        HMac hMac;
        KeyParameter a2;
        if (!(derivationParameters instanceof HKDFParameters)) {
            throw new IllegalArgumentException("HKDF parameters required for HKDFBytesGenerator");
        }
        HKDFParameters hKDFParameters = (HKDFParameters) derivationParameters;
        if (hKDFParameters.skipExtract()) {
            hMac = this.a;
            a2 = new KeyParameter(hKDFParameters.getIKM());
        } else {
            hMac = this.a;
            a2 = a(hKDFParameters.getSalt(), hKDFParameters.getIKM());
        }
        hMac.init(a2);
        this.c = hKDFParameters.getInfo();
        this.e = 0;
        this.d = new byte[this.b];
    }
}
