package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;

public class NonMemoableDigest implements ExtendedDigest {
    private ExtendedDigest a;

    public NonMemoableDigest(ExtendedDigest extendedDigest) {
        if (extendedDigest == null) {
            throw new IllegalArgumentException("baseDigest must not be null");
        }
        this.a = extendedDigest;
    }

    public int doFinal(byte[] bArr, int i) {
        return this.a.doFinal(bArr, i);
    }

    public String getAlgorithmName() {
        return this.a.getAlgorithmName();
    }

    public int getByteLength() {
        return this.a.getByteLength();
    }

    public int getDigestSize() {
        return this.a.getDigestSize();
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b) {
        this.a.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
