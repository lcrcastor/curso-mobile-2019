package org.bouncycastle.crypto.ec;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

public class ECElGamalDecryptor implements ECDecryptor {
    private ECPrivateKeyParameters a;

    public ECPoint decrypt(ECPair eCPair) {
        if (this.a == null) {
            throw new IllegalStateException("ECElGamalDecryptor not initialised");
        }
        return eCPair.getY().subtract(eCPair.getX().multiply(this.a.getD())).normalize();
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ECPrivateKeyParameters)) {
            throw new IllegalArgumentException("ECPrivateKeyParameters are required for decryption.");
        }
        this.a = (ECPrivateKeyParameters) cipherParameters;
    }
}
