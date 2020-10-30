package org.bouncycastle.crypto.params;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class NaccacheSternKeyGenerationParameters extends KeyGenerationParameters {
    private int a;
    private int b;
    private boolean c;

    public NaccacheSternKeyGenerationParameters(SecureRandom secureRandom, int i, int i2, int i3) {
        this(secureRandom, i, i2, i3, false);
    }

    public NaccacheSternKeyGenerationParameters(SecureRandom secureRandom, int i, int i2, int i3, boolean z) {
        super(secureRandom, i);
        this.c = false;
        this.a = i2;
        if (i3 % 2 == 1) {
            throw new IllegalArgumentException("cntSmallPrimes must be a multiple of 2");
        } else if (i3 < 30) {
            throw new IllegalArgumentException("cntSmallPrimes must be >= 30 for security reasons");
        } else {
            this.b = i3;
            this.c = z;
        }
    }

    public int getCertainty() {
        return this.a;
    }

    public int getCntSmallPrimes() {
        return this.b;
    }

    public boolean isDebug() {
        return this.c;
    }
}
