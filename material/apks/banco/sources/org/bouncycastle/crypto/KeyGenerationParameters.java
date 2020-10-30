package org.bouncycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters {
    private SecureRandom a;
    private int b;

    public KeyGenerationParameters(SecureRandom secureRandom, int i) {
        this.a = secureRandom;
        this.b = i;
    }

    public SecureRandom getRandom() {
        return this.a;
    }

    public int getStrength() {
        return this.b;
    }
}
