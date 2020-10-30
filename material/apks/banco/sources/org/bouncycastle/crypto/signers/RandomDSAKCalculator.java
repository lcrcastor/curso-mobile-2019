package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

class RandomDSAKCalculator implements DSAKCalculator {
    private static final BigInteger a = BigInteger.valueOf(0);
    private BigInteger b;
    private SecureRandom c;

    RandomDSAKCalculator() {
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        throw new IllegalStateException("Operation not supported");
    }

    public void init(BigInteger bigInteger, SecureRandom secureRandom) {
        this.b = bigInteger;
        this.c = secureRandom;
    }

    public boolean isDeterministic() {
        return false;
    }

    public BigInteger nextK() {
        int bitLength = this.b.bitLength();
        while (true) {
            BigInteger bigInteger = new BigInteger(bitLength, this.c);
            if (!bigInteger.equals(a) && bigInteger.compareTo(this.b) < 0) {
                return bigInteger;
            }
        }
    }
}
