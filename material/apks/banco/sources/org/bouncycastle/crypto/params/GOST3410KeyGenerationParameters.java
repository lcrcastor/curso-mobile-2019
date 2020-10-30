package org.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class GOST3410KeyGenerationParameters extends KeyGenerationParameters {
    private GOST3410Parameters a;

    public GOST3410KeyGenerationParameters(SecureRandom secureRandom, GOST3410Parameters gOST3410Parameters) {
        super(secureRandom, gOST3410Parameters.getP().bitLength() - 1);
        this.a = gOST3410Parameters;
    }

    public GOST3410Parameters getParameters() {
        return this.a;
    }
}
