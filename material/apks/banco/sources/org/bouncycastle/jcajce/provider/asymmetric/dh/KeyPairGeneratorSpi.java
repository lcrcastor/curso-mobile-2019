package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.bouncycastle.crypto.generators.DHParametersGenerator;
import org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Integers;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    private static Hashtable g = new Hashtable();
    private static Object h = new Object();
    DHKeyGenerationParameters a;
    DHBasicKeyPairGenerator b = new DHBasicKeyPairGenerator();
    int c = 1024;
    int d = 20;
    SecureRandom e = new SecureRandom();
    boolean f = false;

    public KeyPairGeneratorSpi() {
        super("DH");
    }

    public KeyPair generateKeyPair() {
        DHKeyGenerationParameters dHKeyGenerationParameters;
        if (!this.f) {
            Integer valueOf = Integers.valueOf(this.c);
            if (g.containsKey(valueOf)) {
                dHKeyGenerationParameters = (DHKeyGenerationParameters) g.get(valueOf);
            } else {
                DHParameterSpec dHDefaultParameters = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.c);
                if (dHDefaultParameters != null) {
                    dHKeyGenerationParameters = new DHKeyGenerationParameters(this.e, new DHParameters(dHDefaultParameters.getP(), dHDefaultParameters.getG(), null, dHDefaultParameters.getL()));
                } else {
                    synchronized (h) {
                        if (g.containsKey(valueOf)) {
                            this.a = (DHKeyGenerationParameters) g.get(valueOf);
                        } else {
                            DHParametersGenerator dHParametersGenerator = new DHParametersGenerator();
                            dHParametersGenerator.init(this.c, this.d, this.e);
                            this.a = new DHKeyGenerationParameters(this.e, dHParametersGenerator.generateParameters());
                            g.put(valueOf, this.a);
                        }
                    }
                    this.b.init(this.a);
                    this.f = true;
                }
            }
            this.a = dHKeyGenerationParameters;
            this.b.init(this.a);
            this.f = true;
        }
        AsymmetricCipherKeyPair generateKeyPair = this.b.generateKeyPair();
        return new KeyPair(new BCDHPublicKey((DHPublicKeyParameters) generateKeyPair.getPublic()), new BCDHPrivateKey((DHPrivateKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        this.c = i;
        this.e = secureRandom;
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
        }
        DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
        this.a = new DHKeyGenerationParameters(secureRandom, new DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG(), null, dHParameterSpec.getL()));
        this.b.init(this.a);
        this.f = true;
    }
}
