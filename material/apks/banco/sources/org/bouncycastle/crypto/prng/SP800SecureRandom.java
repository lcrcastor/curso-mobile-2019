package org.bouncycastle.crypto.prng;

import java.security.SecureRandom;
import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;

public class SP800SecureRandom extends SecureRandom {
    private final DRBGProvider a;
    private final boolean b;
    private final SecureRandom c;
    private final EntropySource d;
    private SP80090DRBG e;

    SP800SecureRandom(SecureRandom secureRandom, EntropySource entropySource, DRBGProvider dRBGProvider, boolean z) {
        this.c = secureRandom;
        this.d = entropySource;
        this.a = dRBGProvider;
        this.b = z;
    }

    public byte[] generateSeed(int i) {
        byte[] bArr = new byte[i];
        nextBytes(bArr);
        return bArr;
    }

    public void nextBytes(byte[] bArr) {
        synchronized (this) {
            if (this.e == null) {
                this.e = this.a.a(this.d);
            }
            if (this.e.generate(bArr, null, this.b) < 0) {
                this.e.reseed(this.d.getEntropy());
                this.e.generate(bArr, null, this.b);
            }
        }
    }

    public void setSeed(long j) {
        synchronized (this) {
            if (this.c != null) {
                this.c.setSeed(j);
            }
        }
    }

    public void setSeed(byte[] bArr) {
        synchronized (this) {
            if (this.c != null) {
                this.c.setSeed(bArr);
            }
        }
    }
}
