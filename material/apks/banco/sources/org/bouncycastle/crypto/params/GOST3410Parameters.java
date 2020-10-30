package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class GOST3410Parameters implements CipherParameters {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private GOST3410ValidationParameters d;

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.a = bigInteger;
        this.b = bigInteger2;
        this.c = bigInteger3;
    }

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, GOST3410ValidationParameters gOST3410ValidationParameters) {
        this.c = bigInteger3;
        this.a = bigInteger;
        this.b = bigInteger2;
        this.d = gOST3410ValidationParameters;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof GOST3410Parameters)) {
            return false;
        }
        GOST3410Parameters gOST3410Parameters = (GOST3410Parameters) obj;
        if (gOST3410Parameters.getP().equals(this.a) && gOST3410Parameters.getQ().equals(this.b) && gOST3410Parameters.getA().equals(this.c)) {
            z = true;
        }
        return z;
    }

    public BigInteger getA() {
        return this.c;
    }

    public BigInteger getP() {
        return this.a;
    }

    public BigInteger getQ() {
        return this.b;
    }

    public GOST3410ValidationParameters getValidationParameters() {
        return this.d;
    }

    public int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ this.c.hashCode();
    }
}
