package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.CramerShoupKeyGenerationParameters;
import org.bouncycastle.crypto.params.CramerShoupParameters;
import org.bouncycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.bouncycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class CramerShoupKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger a = BigInteger.valueOf(1);
    private CramerShoupKeyGenerationParameters b;

    private BigInteger a(BigInteger bigInteger, SecureRandom secureRandom) {
        return BigIntegers.createRandomInRange(a, bigInteger.subtract(a), secureRandom);
    }

    private CramerShoupPrivateKeyParameters a(SecureRandom secureRandom, CramerShoupParameters cramerShoupParameters) {
        BigInteger p = cramerShoupParameters.getP();
        CramerShoupPrivateKeyParameters cramerShoupPrivateKeyParameters = new CramerShoupPrivateKeyParameters(cramerShoupParameters, a(p, secureRandom), a(p, secureRandom), a(p, secureRandom), a(p, secureRandom), a(p, secureRandom));
        return cramerShoupPrivateKeyParameters;
    }

    private CramerShoupPublicKeyParameters a(CramerShoupParameters cramerShoupParameters, CramerShoupPrivateKeyParameters cramerShoupPrivateKeyParameters) {
        BigInteger g1 = cramerShoupParameters.getG1();
        BigInteger g2 = cramerShoupParameters.getG2();
        BigInteger p = cramerShoupParameters.getP();
        return new CramerShoupPublicKeyParameters(cramerShoupParameters, g1.modPow(cramerShoupPrivateKeyParameters.getX1(), p).multiply(g2.modPow(cramerShoupPrivateKeyParameters.getX2(), p)), g1.modPow(cramerShoupPrivateKeyParameters.getY1(), p).multiply(g2.modPow(cramerShoupPrivateKeyParameters.getY2(), p)), g1.modPow(cramerShoupPrivateKeyParameters.getZ(), p));
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        CramerShoupParameters parameters = this.b.getParameters();
        CramerShoupPrivateKeyParameters a2 = a(this.b.getRandom(), parameters);
        CramerShoupPublicKeyParameters a3 = a(parameters, a2);
        a2.setPk(a3);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) a3, (AsymmetricKeyParameter) a2);
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.b = (CramerShoupKeyGenerationParameters) keyGenerationParameters;
    }
}
