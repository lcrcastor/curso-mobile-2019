package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPrivateKeyParameters extends CramerShoupKeyParameters {
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;
    private BigInteger e;
    private BigInteger f;
    private CramerShoupPublicKeyParameters g;

    public CramerShoupPrivateKeyParameters(CramerShoupParameters cramerShoupParameters, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5) {
        super(true, cramerShoupParameters);
        this.b = bigInteger;
        this.c = bigInteger2;
        this.d = bigInteger3;
        this.e = bigInteger4;
        this.f = bigInteger5;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof CramerShoupPrivateKeyParameters)) {
            return false;
        }
        CramerShoupPrivateKeyParameters cramerShoupPrivateKeyParameters = (CramerShoupPrivateKeyParameters) obj;
        if (cramerShoupPrivateKeyParameters.getX1().equals(this.b) && cramerShoupPrivateKeyParameters.getX2().equals(this.c) && cramerShoupPrivateKeyParameters.getY1().equals(this.d) && cramerShoupPrivateKeyParameters.getY2().equals(this.e) && cramerShoupPrivateKeyParameters.getZ().equals(this.f) && super.equals(obj)) {
            z = true;
        }
        return z;
    }

    public CramerShoupPublicKeyParameters getPk() {
        return this.g;
    }

    public BigInteger getX1() {
        return this.b;
    }

    public BigInteger getX2() {
        return this.c;
    }

    public BigInteger getY1() {
        return this.d;
    }

    public BigInteger getY2() {
        return this.e;
    }

    public BigInteger getZ() {
        return this.f;
    }

    public int hashCode() {
        return ((((this.b.hashCode() ^ this.c.hashCode()) ^ this.d.hashCode()) ^ this.e.hashCode()) ^ this.f.hashCode()) ^ super.hashCode();
    }

    public void setPk(CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters) {
        this.g = cramerShoupPublicKeyParameters;
    }
}
