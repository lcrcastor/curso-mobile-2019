package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;

public class KDFParameters implements DerivationParameters {
    byte[] a;
    byte[] b;

    public KDFParameters(byte[] bArr, byte[] bArr2) {
        this.b = bArr;
        this.a = bArr2;
    }

    public byte[] getIV() {
        return this.a;
    }

    public byte[] getSharedSecret() {
        return this.b;
    }
}
