package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class RC2Parameters implements CipherParameters {
    private byte[] a;
    private int b;

    public RC2Parameters(byte[] bArr) {
        this(bArr, bArr.length > 128 ? 1024 : bArr.length * 8);
    }

    public RC2Parameters(byte[] bArr, int i) {
        this.a = new byte[bArr.length];
        this.b = i;
        System.arraycopy(bArr, 0, this.a, 0, bArr.length);
    }

    public int getEffectiveKeyBits() {
        return this.b;
    }

    public byte[] getKey() {
        return this.a;
    }
}
