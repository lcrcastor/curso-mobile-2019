package org.bouncycastle.asn1.x9;

public abstract class X9ECParametersHolder {
    private X9ECParameters a;

    public abstract X9ECParameters createParameters();

    public X9ECParameters getParameters() {
        if (this.a == null) {
            this.a = createParameters();
        }
        return this.a;
    }
}
