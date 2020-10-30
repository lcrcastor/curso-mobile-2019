package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

public class RSABlindingParameters implements CipherParameters {
    private RSAKeyParameters a;
    private BigInteger b;

    public RSABlindingParameters(RSAKeyParameters rSAKeyParameters, BigInteger bigInteger) {
        if (rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) {
            throw new IllegalArgumentException("RSA parameters should be for a public key");
        }
        this.a = rSAKeyParameters;
        this.b = bigInteger;
    }

    public BigInteger getBlindingFactor() {
        return this.b;
    }

    public RSAKeyParameters getPublicKey() {
        return this.a;
    }
}
