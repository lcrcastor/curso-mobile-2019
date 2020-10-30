package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;

public class MGFParameters implements DerivationParameters {
    byte[] a;

    public MGFParameters(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public MGFParameters(byte[] bArr, int i, int i2) {
        this.a = new byte[i2];
        System.arraycopy(bArr, i, this.a, 0, i2);
    }

    public byte[] getSeed() {
        return this.a;
    }
}
