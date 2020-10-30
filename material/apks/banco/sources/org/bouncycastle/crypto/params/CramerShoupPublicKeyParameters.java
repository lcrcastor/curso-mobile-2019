package org.bouncycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPublicKeyParameters extends CramerShoupKeyParameters {
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;

    public CramerShoupPublicKeyParameters(CramerShoupParameters cramerShoupParameters, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(false, cramerShoupParameters);
        this.b = bigInteger;
        this.c = bigInteger2;
        this.d = bigInteger3;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof CramerShoupPublicKeyParameters)) {
            return false;
        }
        CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters = (CramerShoupPublicKeyParameters) obj;
        if (cramerShoupPublicKeyParameters.getC().equals(this.b) && cramerShoupPublicKeyParameters.getD().equals(this.c) && cramerShoupPublicKeyParameters.getH().equals(this.d) && super.equals(obj)) {
            z = true;
        }
        return z;
    }

    public BigInteger getC() {
        return this.b;
    }

    public BigInteger getD() {
        return this.c;
    }

    public BigInteger getH() {
        return this.d;
    }

    public int hashCode() {
        return ((this.b.hashCode() ^ this.c.hashCode()) ^ this.d.hashCode()) ^ super.hashCode();
    }
}
