package org.bouncycastle.jce.provider;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.KDFParameters;

public class BrokenKDF2BytesGenerator implements DerivationFunction {
    private Digest a;
    private byte[] b;
    private byte[] c;

    public BrokenKDF2BytesGenerator(Digest digest) {
        this.a = digest;
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        long j = (long) (i2 * 8);
        if (j > ((long) (this.a.getDigestSize() * 8)) * 29) {
            new IllegalArgumentException("Output length to large");
        }
        int digestSize = (int) (j / ((long) this.a.getDigestSize()));
        byte[] bArr2 = new byte[this.a.getDigestSize()];
        for (int i3 = 1; i3 <= digestSize; i3++) {
            this.a.update(this.b, 0, this.b.length);
            this.a.update((byte) (i3 & 255));
            this.a.update((byte) ((i3 >> 8) & 255));
            this.a.update((byte) ((i3 >> 16) & 255));
            this.a.update((byte) ((i3 >> 24) & 255));
            this.a.update(this.c, 0, this.c.length);
            this.a.doFinal(bArr2, 0);
            int i4 = i2 - i;
            if (i4 > bArr2.length) {
                System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
                i += bArr2.length;
            } else {
                System.arraycopy(bArr2, 0, bArr, i, i4);
            }
        }
        this.a.reset();
        return i2;
    }

    public Digest getDigest() {
        return this.a;
    }

    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof KDFParameters)) {
            throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
        }
        KDFParameters kDFParameters = (KDFParameters) derivationParameters;
        this.b = kDFParameters.getSharedSecret();
        this.c = kDFParameters.getIV();
    }
}
