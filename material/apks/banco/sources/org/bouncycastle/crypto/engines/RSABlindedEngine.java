package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(1);
    private RSACoreEngine b = new RSACoreEngine();
    private RSAKeyParameters c;
    private SecureRandom d;

    public int getInputBlockSize() {
        return this.b.a();
    }

    public int getOutputBlockSize() {
        return this.b.b();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.b.a(z, cipherParameters);
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.c = (RSAKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.c = (RSAKeyParameters) cipherParameters;
            secureRandom = new SecureRandom();
        }
        this.d = secureRandom;
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        BigInteger bigInteger;
        if (this.c == null) {
            throw new IllegalStateException("RSA engine not initialised");
        }
        BigInteger a2 = this.b.a(bArr, i, i2);
        if (this.c instanceof RSAPrivateCrtKeyParameters) {
            RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters) this.c;
            BigInteger publicExponent = rSAPrivateCrtKeyParameters.getPublicExponent();
            if (publicExponent != null) {
                BigInteger modulus = rSAPrivateCrtKeyParameters.getModulus();
                BigInteger createRandomInRange = BigIntegers.createRandomInRange(a, modulus.subtract(a), this.d);
                bigInteger = this.b.b(createRandomInRange.modPow(publicExponent, modulus).multiply(a2).mod(modulus)).multiply(createRandomInRange.modInverse(modulus)).mod(modulus);
                return this.b.a(bigInteger);
            }
        }
        bigInteger = this.b.b(a2);
        return this.b.a(bigInteger);
    }
}
