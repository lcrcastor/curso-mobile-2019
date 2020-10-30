package org.bouncycastle.crypto.prng;

import java.security.SecureRandom;

public class BasicEntropySourceProvider implements EntropySourceProvider {
    /* access modifiers changed from: private */
    public final SecureRandom a;
    /* access modifiers changed from: private */
    public final boolean b;

    public BasicEntropySourceProvider(SecureRandom secureRandom, boolean z) {
        this.a = secureRandom;
        this.b = z;
    }

    public EntropySource get(final int i) {
        return new EntropySource() {
            public int entropySize() {
                return i;
            }

            public byte[] getEntropy() {
                return BasicEntropySourceProvider.this.a.generateSeed((i + 7) / 8);
            }

            public boolean isPredictionResistant() {
                return BasicEntropySourceProvider.this.b;
            }
        };
    }
}
