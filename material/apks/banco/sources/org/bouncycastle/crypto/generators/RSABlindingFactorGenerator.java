package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindingFactorGenerator {
    private static BigInteger a = BigInteger.valueOf(0);
    private static BigInteger b = BigInteger.valueOf(1);
    private RSAKeyParameters c;
    private SecureRandom d;

    public BigInteger generateBlindingFactor() {
        if (this.c == null) {
            throw new IllegalStateException("generator not initialised");
        }
        BigInteger modulus = this.c.getModulus();
        int bitLength = modulus.bitLength() - 1;
        while (true) {
            BigInteger bigInteger = new BigInteger(bitLength, this.d);
            BigInteger gcd = bigInteger.gcd(modulus);
            if (!bigInteger.equals(a) && !bigInteger.equals(b) && gcd.equals(b)) {
                return bigInteger;
            }
        }
    }

    public void init(CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.c = (RSAKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.c = (RSAKeyParameters) cipherParameters;
            secureRandom = new SecureRandom();
        }
        this.d = secureRandom;
        if (this.c instanceof RSAPrivateCrtKeyParameters) {
            throw new IllegalArgumentException("generator requires RSA public key");
        }
    }
}
