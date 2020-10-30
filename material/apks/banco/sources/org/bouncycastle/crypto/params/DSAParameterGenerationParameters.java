package org.bouncycastle.crypto.params;

import java.security.SecureRandom;

public class DSAParameterGenerationParameters {
    public static final int DIGITAL_SIGNATURE_USAGE = 1;
    public static final int KEY_ESTABLISHMENT_USAGE = 2;
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final SecureRandom e;

    public DSAParameterGenerationParameters(int i, int i2, int i3, SecureRandom secureRandom) {
        this(i, i2, i3, secureRandom, -1);
    }

    public DSAParameterGenerationParameters(int i, int i2, int i3, SecureRandom secureRandom, int i4) {
        this.a = i;
        this.b = i2;
        this.d = i3;
        this.c = i4;
        this.e = secureRandom;
    }

    public int getCertainty() {
        return this.d;
    }

    public int getL() {
        return this.a;
    }

    public int getN() {
        return this.b;
    }

    public SecureRandom getRandom() {
        return this.e;
    }

    public int getUsageIndex() {
        return this.c;
    }
}
