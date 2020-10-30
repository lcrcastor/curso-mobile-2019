package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.CramerShoupParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.util.BigIntegers;

public class CramerShoupParametersGenerator {
    /* access modifiers changed from: private */
    public static final BigInteger a = BigInteger.valueOf(1);
    private int b;
    private int c;
    private SecureRandom d;

    static class ParametersHelper {
        private static final BigInteger a = BigInteger.valueOf(2);

        private ParametersHelper() {
        }

        static BigInteger a(BigInteger bigInteger, SecureRandom secureRandom) {
            BigInteger modPow;
            BigInteger subtract = bigInteger.subtract(a);
            do {
                modPow = BigIntegers.createRandomInRange(a, subtract, secureRandom).modPow(a, bigInteger);
            } while (modPow.equals(CramerShoupParametersGenerator.a));
            return modPow;
        }

        static BigInteger[] a(int i, int i2, SecureRandom secureRandom) {
            BigInteger bigInteger;
            BigInteger add;
            int i3 = i - 1;
            while (true) {
                bigInteger = new BigInteger(i3, 2, secureRandom);
                add = bigInteger.shiftLeft(1).add(CramerShoupParametersGenerator.a);
                if (!add.isProbablePrime(i2) || (i2 > 2 && !bigInteger.isProbablePrime(i2))) {
                }
            }
            return new BigInteger[]{add, bigInteger};
        }
    }

    public CramerShoupParameters generateParameters() {
        BigInteger a2;
        BigInteger bigInteger = ParametersHelper.a(this.b, this.c, this.d)[1];
        BigInteger a3 = ParametersHelper.a(bigInteger, this.d);
        do {
            a2 = ParametersHelper.a(bigInteger, this.d);
        } while (a3.equals(a2));
        return new CramerShoupParameters(bigInteger, a3, a2, new SHA256Digest());
    }

    public CramerShoupParameters generateParameters(DHParameters dHParameters) {
        BigInteger a2;
        BigInteger p = dHParameters.getP();
        BigInteger g = dHParameters.getG();
        do {
            a2 = ParametersHelper.a(p, this.d);
        } while (g.equals(a2));
        return new CramerShoupParameters(p, g, a2, new SHA256Digest());
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.b = i;
        this.c = i2;
        this.d = secureRandom;
    }
}
