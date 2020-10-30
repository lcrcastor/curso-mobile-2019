package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.tls.CipherSuite;

public class DHParameters implements CipherParameters {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;
    private int e;
    private int f;
    private DHValidationParameters g;

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i) {
        this(bigInteger, bigInteger2, bigInteger3, a(i), i, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, i, i2, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i, int i2, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        if (i2 != 0) {
            if (BigInteger.valueOf(((long) (i2 - 1)) ^ 2).compareTo(bigInteger) == 1) {
                throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            } else if (i2 < i) {
                throw new IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        this.a = bigInteger2;
        this.b = bigInteger;
        this.c = bigInteger3;
        this.e = i;
        this.f = i2;
        this.d = bigInteger4;
        this.g = dHValidationParameters;
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        this(bigInteger, bigInteger2, bigInteger3, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 0, bigInteger4, dHValidationParameters);
    }

    private static int a(int i) {
        return i == 0 ? CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 : i < 160 ? i : CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DHParameters)) {
            return false;
        }
        DHParameters dHParameters = (DHParameters) obj;
        if (getQ() != null) {
            if (!getQ().equals(dHParameters.getQ())) {
                return false;
            }
        } else if (dHParameters.getQ() != null) {
            return false;
        }
        if (dHParameters.getP().equals(this.b) && dHParameters.getG().equals(this.a)) {
            z = true;
        }
        return z;
    }

    public BigInteger getG() {
        return this.a;
    }

    public BigInteger getJ() {
        return this.d;
    }

    public int getL() {
        return this.f;
    }

    public int getM() {
        return this.e;
    }

    public BigInteger getP() {
        return this.b;
    }

    public BigInteger getQ() {
        return this.c;
    }

    public DHValidationParameters getValidationParameters() {
        return this.g;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) ^ (getQ() != null ? getQ().hashCode() : 0);
    }
}
