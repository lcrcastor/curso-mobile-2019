package org.bouncycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.pqc.crypto.rainbow.RainbowKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.rainbow.RainbowKeyPairGenerator;
import org.bouncycastle.pqc.crypto.rainbow.RainbowParameters;
import org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.RainbowParameterSpec;

public class RainbowKeyPairGeneratorSpi extends KeyPairGenerator {
    RainbowKeyGenerationParameters a;
    RainbowKeyPairGenerator b = new RainbowKeyPairGenerator();
    int c = 1024;
    SecureRandom d = new SecureRandom();
    boolean e = false;

    public RainbowKeyPairGeneratorSpi() {
        super("Rainbow");
    }

    public KeyPair generateKeyPair() {
        if (!this.e) {
            this.a = new RainbowKeyGenerationParameters(this.d, new RainbowParameters(new RainbowParameterSpec().getVi()));
            this.b.init(this.a);
            this.e = true;
        }
        AsymmetricCipherKeyPair generateKeyPair = this.b.generateKeyPair();
        return new KeyPair(new BCRainbowPublicKey((RainbowPublicKeyParameters) generateKeyPair.getPublic()), new BCRainbowPrivateKey((RainbowPrivateKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        this.c = i;
        this.d = secureRandom;
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (!(algorithmParameterSpec instanceof RainbowParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a RainbowParameterSpec");
        }
        this.a = new RainbowKeyGenerationParameters(secureRandom, new RainbowParameters(((RainbowParameterSpec) algorithmParameterSpec).getVi()));
        this.b.init(this.a);
        this.e = true;
    }
}
