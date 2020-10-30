package org.bouncycastle.math.field;

import java.math.BigInteger;

class PrimeField implements FiniteField {
    protected final BigInteger a;

    PrimeField(BigInteger bigInteger) {
        this.a = bigInteger;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrimeField)) {
            return false;
        }
        return this.a.equals(((PrimeField) obj).a);
    }

    public BigInteger getCharacteristic() {
        return this.a;
    }

    public int getDimension() {
        return 1;
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
