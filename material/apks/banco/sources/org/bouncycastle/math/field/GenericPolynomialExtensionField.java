package org.bouncycastle.math.field;

import java.math.BigInteger;
import org.bouncycastle.util.Integers;

class GenericPolynomialExtensionField implements PolynomialExtensionField {
    protected final FiniteField a;
    protected final Polynomial b;

    GenericPolynomialExtensionField(FiniteField finiteField, Polynomial polynomial) {
        this.a = finiteField;
        this.b = polynomial;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenericPolynomialExtensionField)) {
            return false;
        }
        GenericPolynomialExtensionField genericPolynomialExtensionField = (GenericPolynomialExtensionField) obj;
        return this.a.equals(genericPolynomialExtensionField.a) && this.b.equals(genericPolynomialExtensionField.b);
    }

    public BigInteger getCharacteristic() {
        return this.a.getCharacteristic();
    }

    public int getDegree() {
        return this.b.getDegree();
    }

    public int getDimension() {
        return this.a.getDimension() * this.b.getDegree();
    }

    public Polynomial getMinimalPolynomial() {
        return this.b;
    }

    public FiniteField getSubfield() {
        return this.a;
    }

    public int hashCode() {
        return this.a.hashCode() ^ Integers.rotateLeft(this.b.hashCode(), 16);
    }
}
