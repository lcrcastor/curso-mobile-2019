package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

class DHKeyGeneratorHelper {
    static final DHKeyGeneratorHelper a = new DHKeyGeneratorHelper();
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = BigInteger.valueOf(2);

    private DHKeyGeneratorHelper() {
    }

    /* access modifiers changed from: 0000 */
    public BigInteger a(DHParameters dHParameters, BigInteger bigInteger) {
        return dHParameters.getG().modPow(bigInteger, dHParameters.getP());
    }

    /* access modifiers changed from: 0000 */
    public BigInteger a(DHParameters dHParameters, SecureRandom secureRandom) {
        BigInteger createRandomInRange;
        BigInteger bit;
        int l = dHParameters.getL();
        if (l != 0) {
            int i = l >>> 2;
            do {
                bit = new BigInteger(l, secureRandom).setBit(l - 1);
            } while (WNafUtil.getNafWeight(bit) < i);
            return bit;
        }
        BigInteger bigInteger = c;
        int m = dHParameters.getM();
        if (m != 0) {
            bigInteger = b.shiftLeft(m - 1);
        }
        BigInteger q = dHParameters.getQ();
        if (q == null) {
            q = dHParameters.getP();
        }
        BigInteger subtract = q.subtract(c);
        int bitLength = subtract.bitLength() >>> 2;
        do {
            createRandomInRange = BigIntegers.createRandomInRange(bigInteger, subtract, secureRandom);
        } while (WNafUtil.getNafWeight(createRandomInRange) < bitLength);
        return createRandomInRange;
    }
}
