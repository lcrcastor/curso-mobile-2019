package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    static final BigInteger a = BigInteger.valueOf(65537);
    RSAKeyGenerationParameters b;
    RSAKeyPairGenerator c;

    public KeyPairGeneratorSpi() {
        super("RSA");
        this.c = new RSAKeyPairGenerator();
        this.b = new RSAKeyGenerationParameters(a, new SecureRandom(), 2048, 12);
        this.c.init(this.b);
    }

    public KeyPairGeneratorSpi(String str) {
        super(str);
    }

    public KeyPair generateKeyPair() {
        AsymmetricCipherKeyPair generateKeyPair = this.c.generateKeyPair();
        return new KeyPair(new BCRSAPublicKey((RSAKeyParameters) generateKeyPair.getPublic()), new BCRSAPrivateCrtKey((RSAPrivateCrtKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        this.b = new RSAKeyGenerationParameters(a, secureRandom, i, 12);
        this.c.init(this.b);
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (!(algorithmParameterSpec instanceof RSAKeyGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
        }
        RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec) algorithmParameterSpec;
        this.b = new RSAKeyGenerationParameters(rSAKeyGenParameterSpec.getPublicExponent(), secureRandom, rSAKeyGenParameterSpec.getKeysize(), 12);
        this.c.init(this.b);
    }
}
