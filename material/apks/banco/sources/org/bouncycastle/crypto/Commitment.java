package org.bouncycastle.crypto;

public class Commitment {
    private final byte[] a;
    private final byte[] b;

    public Commitment(byte[] bArr, byte[] bArr2) {
        this.a = bArr;
        this.b = bArr2;
    }

    public byte[] getCommitment() {
        return this.b;
    }

    public byte[] getSecret() {
        return this.a;
    }
}
