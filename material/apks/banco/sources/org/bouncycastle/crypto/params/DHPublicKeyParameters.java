package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DHPublicKeyParameters extends DHKeyParameters {
    private BigInteger b;

    public DHPublicKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(false, dHParameters);
        this.b = bigInteger;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DHPublicKeyParameters)) {
            return false;
        }
        if (((DHPublicKeyParameters) obj).getY().equals(this.b) && super.equals(obj)) {
            z = true;
        }
        return z;
    }

    public BigInteger getY() {
        return this.b;
    }

    public int hashCode() {
        return this.b.hashCode() ^ super.hashCode();
    }
}
