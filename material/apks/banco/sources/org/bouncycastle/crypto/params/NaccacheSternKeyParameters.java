package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {
    int b;
    private BigInteger c;
    private BigInteger d;

    public NaccacheSternKeyParameters(boolean z, BigInteger bigInteger, BigInteger bigInteger2, int i) {
        super(z);
        this.c = bigInteger;
        this.d = bigInteger2;
        this.b = i;
    }

    public BigInteger getG() {
        return this.c;
    }

    public int getLowerSigmaBound() {
        return this.b;
    }

    public BigInteger getModulus() {
        return this.d;
    }
}
