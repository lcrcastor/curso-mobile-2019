package org.bouncycastle.crypto.params;

public class GOST3410KeyParameters extends AsymmetricKeyParameter {
    private GOST3410Parameters b;

    public GOST3410KeyParameters(boolean z, GOST3410Parameters gOST3410Parameters) {
        super(z);
        this.b = gOST3410Parameters;
    }

    public GOST3410Parameters getParameters() {
        return this.b;
    }
}
