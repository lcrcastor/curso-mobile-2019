package org.bouncycastle.crypto.params;

public class DSAKeyParameters extends AsymmetricKeyParameter {
    private DSAParameters b;

    public DSAKeyParameters(boolean z, DSAParameters dSAParameters) {
        super(z);
        this.b = dSAParameters;
    }

    public DSAParameters getParameters() {
        return this.b;
    }
}
