package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class RSAKeyGenerationParameters extends KeyGenerationParameters {
    private BigInteger a;
    private int b;

    public RSAKeyGenerationParameters(BigInteger bigInteger, SecureRandom secureRandom, int i, int i2) {
        super(secureRandom, i);
        if (i < 12) {
            throw new IllegalArgumentException("key strength too small");
        } else if (!bigInteger.testBit(0)) {
            throw new IllegalArgumentException("public exponent cannot be even");
        } else {
            this.a = bigInteger;
            this.b = i2;
        }
    }

    public int getCertainty() {
        return this.b;
    }

    public BigInteger getPublicExponent() {
        return this.a;
    }
}
