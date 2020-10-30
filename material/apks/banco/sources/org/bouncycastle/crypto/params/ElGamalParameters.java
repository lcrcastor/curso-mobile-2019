package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class ElGamalParameters implements CipherParameters {
    private BigInteger a;
    private BigInteger b;
    private int c;

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, 0);
    }

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.a = bigInteger2;
        this.b = bigInteger;
        this.c = i;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ElGamalParameters)) {
            return false;
        }
        ElGamalParameters elGamalParameters = (ElGamalParameters) obj;
        if (elGamalParameters.getP().equals(this.b) && elGamalParameters.getG().equals(this.a) && elGamalParameters.getL() == this.c) {
            z = true;
        }
        return z;
    }

    public BigInteger getG() {
        return this.a;
    }

    public int getL() {
        return this.c;
    }

    public BigInteger getP() {
        return this.b;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) + this.c;
    }
}
