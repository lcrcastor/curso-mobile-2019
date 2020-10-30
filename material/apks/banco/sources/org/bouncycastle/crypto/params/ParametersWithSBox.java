package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithSBox implements CipherParameters {
    private CipherParameters a;
    private byte[] b;

    public ParametersWithSBox(CipherParameters cipherParameters, byte[] bArr) {
        this.a = cipherParameters;
        this.b = bArr;
    }

    public CipherParameters getParameters() {
        return this.a;
    }

    public byte[] getSBox() {
        return this.b;
    }
}
