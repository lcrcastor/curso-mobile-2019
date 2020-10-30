package org.bouncycastle.crypto.params;

public class ECKeyParameters extends AsymmetricKeyParameter {
    ECDomainParameters b;

    protected ECKeyParameters(boolean z, ECDomainParameters eCDomainParameters) {
        super(z);
        this.b = eCDomainParameters;
    }

    public ECDomainParameters getParameters() {
        return this.b;
    }
}
