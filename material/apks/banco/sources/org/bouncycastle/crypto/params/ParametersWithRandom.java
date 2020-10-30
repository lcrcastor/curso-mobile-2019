package org.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithRandom implements CipherParameters {
    private SecureRandom a;
    private CipherParameters b;

    public ParametersWithRandom(CipherParameters cipherParameters) {
        this(cipherParameters, new SecureRandom());
    }

    public ParametersWithRandom(CipherParameters cipherParameters, SecureRandom secureRandom) {
        this.a = secureRandom;
        this.b = cipherParameters;
    }

    public CipherParameters getParameters() {
        return this.b;
    }

    public SecureRandom getRandom() {
        return this.a;
    }
}
