package org.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class DHBasicAgreement implements BasicAgreement {
    private DHPrivateKeyParameters a;
    private DHParameters b;

    public BigInteger calculateAgreement(CipherParameters cipherParameters) {
        DHPublicKeyParameters dHPublicKeyParameters = (DHPublicKeyParameters) cipherParameters;
        if (dHPublicKeyParameters.getParameters().equals(this.b)) {
            return dHPublicKeyParameters.getY().modPow(this.a.getX(), this.b.getP());
        }
        throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }

    public int getFieldSize() {
        return (this.a.getParameters().getP().bitLength() + 7) / 8;
    }

    public void init(CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        AsymmetricKeyParameter asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
        if (!(asymmetricKeyParameter instanceof DHPrivateKeyParameters)) {
            throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
        }
        this.a = (DHPrivateKeyParameters) asymmetricKeyParameter;
        this.b = this.a.getParameters();
    }
}
