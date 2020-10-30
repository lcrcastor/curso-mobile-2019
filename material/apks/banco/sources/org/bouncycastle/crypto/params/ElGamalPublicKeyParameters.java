package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {
    private BigInteger b;

    public ElGamalPublicKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(false, elGamalParameters);
        this.b = bigInteger;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ElGamalPublicKeyParameters)) {
            return false;
        }
        if (((ElGamalPublicKeyParameters) obj).getY().equals(this.b) && super.equals(obj)) {
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
