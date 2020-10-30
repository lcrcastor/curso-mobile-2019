package org.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.bouncycastle.crypto.generators.ElGamalParametersGenerator;
import org.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    ElGamalKeyGenerationParameters a;
    ElGamalKeyPairGenerator b = new ElGamalKeyPairGenerator();
    int c = 1024;
    int d = 20;
    SecureRandom e = new SecureRandom();
    boolean f = false;

    public KeyPairGeneratorSpi() {
        super("ElGamal");
    }

    public KeyPair generateKeyPair() {
        ElGamalKeyGenerationParameters elGamalKeyGenerationParameters;
        if (!this.f) {
            DHParameterSpec dHDefaultParameters = BouncyCastleProvider.CONFIGURATION.getDHDefaultParameters(this.c);
            if (dHDefaultParameters != null) {
                elGamalKeyGenerationParameters = new ElGamalKeyGenerationParameters(this.e, new ElGamalParameters(dHDefaultParameters.getP(), dHDefaultParameters.getG(), dHDefaultParameters.getL()));
            } else {
                ElGamalParametersGenerator elGamalParametersGenerator = new ElGamalParametersGenerator();
                elGamalParametersGenerator.init(this.c, this.d, this.e);
                elGamalKeyGenerationParameters = new ElGamalKeyGenerationParameters(this.e, elGamalParametersGenerator.generateParameters());
            }
            this.a = elGamalKeyGenerationParameters;
            this.b.init(this.a);
            this.f = true;
        }
        AsymmetricCipherKeyPair generateKeyPair = this.b.generateKeyPair();
        return new KeyPair(new BCElGamalPublicKey((ElGamalPublicKeyParameters) generateKeyPair.getPublic()), new BCElGamalPrivateKey((ElGamalPrivateKeyParameters) generateKeyPair.getPrivate()));
    }

    public void initialize(int i, SecureRandom secureRandom) {
        this.c = i;
        this.e = secureRandom;
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        ElGamalKeyGenerationParameters elGamalKeyGenerationParameters;
        boolean z = algorithmParameterSpec instanceof ElGamalParameterSpec;
        if (z || (algorithmParameterSpec instanceof DHParameterSpec)) {
            if (z) {
                ElGamalParameterSpec elGamalParameterSpec = (ElGamalParameterSpec) algorithmParameterSpec;
                elGamalKeyGenerationParameters = new ElGamalKeyGenerationParameters(secureRandom, new ElGamalParameters(elGamalParameterSpec.getP(), elGamalParameterSpec.getG()));
            } else {
                DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
                elGamalKeyGenerationParameters = new ElGamalKeyGenerationParameters(secureRandom, new ElGamalParameters(dHParameterSpec.getP(), dHParameterSpec.getG(), dHParameterSpec.getL()));
            }
            this.a = elGamalKeyGenerationParameters;
            this.b.init(this.a);
            this.f = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec or an ElGamalParameterSpec");
    }
}
