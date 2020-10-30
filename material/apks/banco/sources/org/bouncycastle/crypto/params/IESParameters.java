package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class IESParameters implements CipherParameters {
    private byte[] a;
    private byte[] b;
    private int c;

    public IESParameters(byte[] bArr, byte[] bArr2, int i) {
        this.a = bArr;
        this.b = bArr2;
        this.c = i;
    }

    public byte[] getDerivationV() {
        return this.a;
    }

    public byte[] getEncodingV() {
        return this.b;
    }

    public int getMacKeySize() {
        return this.c;
    }
}
