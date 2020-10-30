package org.bouncycastle.crypto.generators;

import android.support.v4.view.InputDeviceCompat;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.DigestDerivationFunction;
import org.bouncycastle.crypto.params.ISO18033KDFParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.util.Pack;

public class BaseKDFBytesGenerator implements DigestDerivationFunction {
    private int a;
    private Digest b;
    private byte[] c;
    private byte[] d;

    protected BaseKDFBytesGenerator(int i, Digest digest) {
        this.a = i;
        this.b = digest;
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        long j = (long) i2;
        int digestSize = this.b.getDigestSize();
        if (j > 8589934591L) {
            throw new IllegalArgumentException("Output length too large");
        }
        long j2 = (long) digestSize;
        int i3 = (int) (((j + j2) - 1) / j2);
        byte[] bArr2 = new byte[this.b.getDigestSize()];
        byte[] bArr3 = new byte[4];
        Pack.intToBigEndian(this.a, bArr3, 0);
        int i4 = this.a & InputDeviceCompat.SOURCE_ANY;
        int i5 = i;
        for (int i6 = 0; i6 < i3; i6++) {
            this.b.update(this.c, 0, this.c.length);
            this.b.update(bArr3, 0, bArr3.length);
            if (this.d != null) {
                this.b.update(this.d, 0, this.d.length);
            }
            this.b.doFinal(bArr2, 0);
            if (i2 > digestSize) {
                System.arraycopy(bArr2, 0, bArr, i5, digestSize);
                i5 += digestSize;
                i2 -= digestSize;
            } else {
                System.arraycopy(bArr2, 0, bArr, i5, i2);
            }
            byte b2 = (byte) (bArr3[3] + 1);
            bArr3[3] = b2;
            if (b2 == 0) {
                i4 += 256;
                Pack.intToBigEndian(i4, bArr3, 0);
            }
        }
        this.b.reset();
        return (int) j;
    }

    public Digest getDigest() {
        return this.b;
    }

    public void init(DerivationParameters derivationParameters) {
        byte[] bArr;
        if (derivationParameters instanceof KDFParameters) {
            KDFParameters kDFParameters = (KDFParameters) derivationParameters;
            this.c = kDFParameters.getSharedSecret();
            bArr = kDFParameters.getIV();
        } else if (derivationParameters instanceof ISO18033KDFParameters) {
            this.c = ((ISO18033KDFParameters) derivationParameters).getSeed();
            bArr = null;
        } else {
            throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
        }
        this.d = bArr;
    }
}
