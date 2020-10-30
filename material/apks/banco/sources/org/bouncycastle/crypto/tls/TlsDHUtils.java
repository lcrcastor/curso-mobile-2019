package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class TlsDHUtils {
    static final BigInteger a = BigInteger.valueOf(1);
    static final BigInteger b = BigInteger.valueOf(2);

    public static boolean areCompatibleParameters(DHParameters dHParameters, DHParameters dHParameters2) {
        return dHParameters.getP().equals(dHParameters2.getP()) && dHParameters.getG().equals(dHParameters2.getG());
    }

    public static byte[] calculateDHBasicAgreement(DHPublicKeyParameters dHPublicKeyParameters, DHPrivateKeyParameters dHPrivateKeyParameters) {
        DHBasicAgreement dHBasicAgreement = new DHBasicAgreement();
        dHBasicAgreement.init(dHPrivateKeyParameters);
        return BigIntegers.asUnsignedByteArray(dHBasicAgreement.calculateAgreement(dHPublicKeyParameters));
    }

    public static AsymmetricCipherKeyPair generateDHKeyPair(SecureRandom secureRandom, DHParameters dHParameters) {
        DHBasicKeyPairGenerator dHBasicKeyPairGenerator = new DHBasicKeyPairGenerator();
        dHBasicKeyPairGenerator.init(new DHKeyGenerationParameters(secureRandom, dHParameters));
        return dHBasicKeyPairGenerator.generateKeyPair();
    }

    public static DHPrivateKeyParameters generateEphemeralClientKeyExchange(SecureRandom secureRandom, DHParameters dHParameters, OutputStream outputStream) {
        AsymmetricCipherKeyPair generateDHKeyPair = generateDHKeyPair(secureRandom, dHParameters);
        writeDHParameter(((DHPublicKeyParameters) generateDHKeyPair.getPublic()).getY(), outputStream);
        return (DHPrivateKeyParameters) generateDHKeyPair.getPrivate();
    }

    public static DHPrivateKeyParameters generateEphemeralServerKeyExchange(SecureRandom secureRandom, DHParameters dHParameters, OutputStream outputStream) {
        AsymmetricCipherKeyPair generateDHKeyPair = generateDHKeyPair(secureRandom, dHParameters);
        new ServerDHParams((DHPublicKeyParameters) generateDHKeyPair.getPublic()).encode(outputStream);
        return (DHPrivateKeyParameters) generateDHKeyPair.getPrivate();
    }

    public static BigInteger readDHParameter(InputStream inputStream) {
        return new BigInteger(1, TlsUtils.readOpaque16(inputStream));
    }

    public static DHPublicKeyParameters validateDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        BigInteger y = dHPublicKeyParameters.getY();
        DHParameters parameters = dHPublicKeyParameters.getParameters();
        BigInteger p = parameters.getP();
        BigInteger g = parameters.getG();
        if (!p.isProbablePrime(2)) {
            throw new TlsFatalAlert(47);
        } else if (g.compareTo(b) < 0 || g.compareTo(p.subtract(b)) > 0) {
            throw new TlsFatalAlert(47);
        } else if (y.compareTo(b) >= 0 && y.compareTo(p.subtract(a)) <= 0) {
            return dHPublicKeyParameters;
        } else {
            throw new TlsFatalAlert(47);
        }
    }

    public static void writeDHParameter(BigInteger bigInteger, OutputStream outputStream) {
        TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(bigInteger), outputStream);
    }
}
