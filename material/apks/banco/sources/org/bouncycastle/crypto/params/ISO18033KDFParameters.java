package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;

public class ISO18033KDFParameters implements DerivationParameters {
    byte[] a;

    public ISO18033KDFParameters(byte[] bArr) {
        this.a = bArr;
    }

    public byte[] getSeed() {
        return this.a;
    }
}
