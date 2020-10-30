package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;

public class ShortenedDigest implements ExtendedDigest {
    private ExtendedDigest a;
    private int b;

    public ShortenedDigest(ExtendedDigest extendedDigest, int i) {
        if (extendedDigest == null) {
            throw new IllegalArgumentException("baseDigest must not be null");
        } else if (i > extendedDigest.getDigestSize()) {
            throw new IllegalArgumentException("baseDigest output not large enough to support length");
        } else {
            this.a = extendedDigest;
            this.b = i;
        }
    }

    public int doFinal(byte[] bArr, int i) {
        byte[] bArr2 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr2, 0);
        System.arraycopy(bArr2, 0, bArr, i, this.b);
        return this.b;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("(");
        sb.append(this.b * 8);
        sb.append(")");
        return sb.toString();
    }

    public int getByteLength() {
        return this.a.getByteLength();
    }

    public int getDigestSize() {
        return this.b;
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b2) {
        this.a.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
