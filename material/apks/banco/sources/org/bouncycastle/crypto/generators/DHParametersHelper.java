package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

class DHParametersHelper {
    private static final BigInteger a = BigInteger.valueOf(1);
    private static final BigInteger b = BigInteger.valueOf(2);

    DHParametersHelper() {
    }

    static BigInteger a(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger subtract = bigInteger.subtract(b);
        do {
            modPow = BigIntegers.createRandomInRange(b, subtract, secureRandom).modPow(b, bigInteger);
        } while (modPow.equals(a));
        return modPow;
    }

    static BigInteger[] a(int i, int i2, SecureRandom secureRandom) {
        int i3 = i - 1;
        int i4 = i >>> 2;
        while (true) {
            BigInteger bigInteger = new BigInteger(i3, 2, secureRandom);
            BigInteger add = bigInteger.shiftLeft(1).add(a);
            if (add.isProbablePrime(i2) && ((i2 <= 2 || bigInteger.isProbablePrime(i2 - 2)) && WNafUtil.getNafWeight(add) >= i4)) {
                return new BigInteger[]{add, bigInteger};
            }
        }
    }
}
