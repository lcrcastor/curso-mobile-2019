package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.DSAKeyParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class DSASigner implements DSA {
    private final DSAKCalculator a;
    private DSAKeyParameters b;
    private SecureRandom c;

    public DSASigner() {
        this.a = new RandomDSAKCalculator();
    }

    public DSASigner(DSAKCalculator dSAKCalculator) {
        this.a = dSAKCalculator;
    }

    private BigInteger a(BigInteger bigInteger, byte[] bArr) {
        if (bigInteger.bitLength() >= bArr.length * 8) {
            return new BigInteger(1, bArr);
        }
        byte[] bArr2 = new byte[(bigInteger.bitLength() / 8)];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        return new BigInteger(1, bArr2);
    }

    public BigInteger[] generateSignature(byte[] bArr) {
        DSAParameters parameters = this.b.getParameters();
        BigInteger a2 = a(parameters.getQ(), bArr);
        if (this.a.isDeterministic()) {
            this.a.init(parameters.getQ(), ((DSAPrivateKeyParameters) this.b).getX(), bArr);
        } else {
            this.a.init(parameters.getQ(), this.c);
        }
        BigInteger nextK = this.a.nextK();
        BigInteger mod = parameters.getG().modPow(nextK, parameters.getP()).mod(parameters.getQ());
        return new BigInteger[]{mod, nextK.modInverse(parameters.getQ()).multiply(a2.add(((DSAPrivateKeyParameters) this.b).getX().multiply(mod))).mod(parameters.getQ())};
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        DSAKeyParameters dSAKeyParameters;
        if (!z) {
            dSAKeyParameters = (DSAPublicKeyParameters) cipherParameters;
        } else if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.b = (DSAPrivateKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
            this.c = initSecureRandom(!z && !this.a.isDeterministic(), secureRandom);
        } else {
            dSAKeyParameters = (DSAPrivateKeyParameters) cipherParameters;
        }
        this.b = dSAKeyParameters;
        secureRandom = null;
        this.c = initSecureRandom(!z && !this.a.isDeterministic(), secureRandom);
    }

    /* access modifiers changed from: protected */
    public SecureRandom initSecureRandom(boolean z, SecureRandom secureRandom) {
        if (!z) {
            return null;
        }
        return secureRandom != null ? secureRandom : new SecureRandom();
    }

    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        DSAParameters parameters = this.b.getParameters();
        BigInteger a2 = a(parameters.getQ(), bArr);
        BigInteger valueOf = BigInteger.valueOf(0);
        if (valueOf.compareTo(bigInteger) >= 0 || parameters.getQ().compareTo(bigInteger) <= 0 || valueOf.compareTo(bigInteger2) >= 0 || parameters.getQ().compareTo(bigInteger2) <= 0) {
            return false;
        }
        BigInteger modInverse = bigInteger2.modInverse(parameters.getQ());
        return parameters.getG().modPow(a2.multiply(modInverse).mod(parameters.getQ()), parameters.getP()).multiply(((DSAPublicKeyParameters) this.b).getY().modPow(bigInteger.multiply(modInverse).mod(parameters.getQ()), parameters.getP())).mod(parameters.getP()).mod(parameters.getQ()).equals(bigInteger);
    }
}
