package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.GOST3410KeyParameters;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class GOST3410Signer implements DSA {
    GOST3410KeyParameters a;
    SecureRandom b;

    public BigInteger[] generateSignature(byte[] bArr) {
        BigInteger bigInteger;
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i != bArr2.length; i++) {
            bArr2[i] = bArr[(bArr2.length - 1) - i];
        }
        BigInteger bigInteger2 = new BigInteger(1, bArr2);
        GOST3410Parameters parameters = this.a.getParameters();
        do {
            bigInteger = new BigInteger(parameters.getQ().bitLength(), this.b);
        } while (bigInteger.compareTo(parameters.getQ()) >= 0);
        BigInteger mod = parameters.getA().modPow(bigInteger, parameters.getP()).mod(parameters.getQ());
        return new BigInteger[]{mod, bigInteger.multiply(bigInteger2).add(((GOST3410PrivateKeyParameters) this.a).getX().multiply(mod)).mod(parameters.getQ())};
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        GOST3410KeyParameters gOST3410KeyParameters;
        if (!z) {
            gOST3410KeyParameters = (GOST3410PublicKeyParameters) cipherParameters;
        } else if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.b = parametersWithRandom.getRandom();
            this.a = (GOST3410PrivateKeyParameters) parametersWithRandom.getParameters();
            return;
        } else {
            this.b = new SecureRandom();
            gOST3410KeyParameters = (GOST3410PrivateKeyParameters) cipherParameters;
        }
        this.a = gOST3410KeyParameters;
    }

    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i != bArr2.length; i++) {
            bArr2[i] = bArr[(bArr2.length - 1) - i];
        }
        BigInteger bigInteger3 = new BigInteger(1, bArr2);
        GOST3410Parameters parameters = this.a.getParameters();
        BigInteger valueOf = BigInteger.valueOf(0);
        if (valueOf.compareTo(bigInteger) >= 0 || parameters.getQ().compareTo(bigInteger) <= 0 || valueOf.compareTo(bigInteger2) >= 0 || parameters.getQ().compareTo(bigInteger2) <= 0) {
            return false;
        }
        BigInteger modPow = bigInteger3.modPow(parameters.getQ().subtract(new BigInteger("2")), parameters.getQ());
        return parameters.getA().modPow(bigInteger2.multiply(modPow).mod(parameters.getQ()), parameters.getP()).multiply(((GOST3410PublicKeyParameters) this.a).getY().modPow(parameters.getQ().subtract(bigInteger).multiply(modPow).mod(parameters.getQ()), parameters.getP())).mod(parameters.getP()).mod(parameters.getQ()).equals(bigInteger);
    }
}
