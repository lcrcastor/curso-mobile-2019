package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.math.ec.WNafUtil;

public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger a = BigInteger.valueOf(1);
    private RSAKeyGenerationParameters b;

    /* access modifiers changed from: protected */
    public BigInteger chooseRandomPrime(int i, BigInteger bigInteger) {
        while (true) {
            BigInteger bigInteger2 = new BigInteger(i, 1, this.b.getRandom());
            if (!bigInteger2.mod(bigInteger).equals(a) && bigInteger2.isProbablePrime(this.b.getCertainty()) && bigInteger.gcd(bigInteger2.subtract(a)).equals(a)) {
                return bigInteger2;
            }
        }
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger chooseRandomPrime;
        BigInteger chooseRandomPrime2;
        BigInteger multiply;
        BigInteger bigInteger;
        BigInteger bigInteger2;
        int strength = this.b.getStrength();
        int i = strength >>> 1;
        int i2 = strength - i;
        int i3 = strength / 3;
        int i4 = strength >>> 2;
        BigInteger publicExponent = this.b.getPublicExponent();
        do {
            chooseRandomPrime = chooseRandomPrime(i2, publicExponent);
            while (true) {
                chooseRandomPrime2 = chooseRandomPrime(i, publicExponent);
                if (chooseRandomPrime2.subtract(chooseRandomPrime).abs().bitLength() >= i3) {
                    multiply = chooseRandomPrime.multiply(chooseRandomPrime2);
                    if (multiply.bitLength() == strength) {
                        break;
                    }
                    chooseRandomPrime = chooseRandomPrime.max(chooseRandomPrime2);
                }
            }
        } while (WNafUtil.getNafWeight(multiply) < i4);
        if (chooseRandomPrime.compareTo(chooseRandomPrime2) < 0) {
            bigInteger = chooseRandomPrime;
            bigInteger2 = chooseRandomPrime2;
        } else {
            bigInteger2 = chooseRandomPrime;
            bigInteger = chooseRandomPrime2;
        }
        BigInteger subtract = bigInteger2.subtract(a);
        BigInteger subtract2 = bigInteger.subtract(a);
        BigInteger modInverse = publicExponent.modInverse(subtract.multiply(subtract2));
        BigInteger remainder = modInverse.remainder(subtract);
        BigInteger remainder2 = modInverse.remainder(subtract2);
        BigInteger modInverse2 = bigInteger.modInverse(bigInteger2);
        RSAKeyParameters rSAKeyParameters = new RSAKeyParameters(false, multiply, publicExponent);
        RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters = new RSAPrivateCrtKeyParameters(multiply, publicExponent, modInverse, bigInteger2, bigInteger, remainder, remainder2, modInverse2);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) rSAKeyParameters, (AsymmetricKeyParameter) rSAPrivateCrtKeyParameters);
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.b = (RSAKeyGenerationParameters) keyGenerationParameters;
    }
}
