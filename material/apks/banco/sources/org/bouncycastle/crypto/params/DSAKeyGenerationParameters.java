package org.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class DSAKeyGenerationParameters extends KeyGenerationParameters {
    private DSAParameters a;

    public DSAKeyGenerationParameters(SecureRandom secureRandom, DSAParameters dSAParameters) {
        super(secureRandom, dSAParameters.getP().bitLength() - 1);
        this.a = dSAParameters;
    }

    public DSAParameters getParameters() {
        return this.a;
    }
}
