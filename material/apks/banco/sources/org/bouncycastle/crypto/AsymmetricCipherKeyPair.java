package org.bouncycastle.crypto;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class AsymmetricCipherKeyPair {
    private AsymmetricKeyParameter a;
    private AsymmetricKeyParameter b;

    public AsymmetricCipherKeyPair(CipherParameters cipherParameters, CipherParameters cipherParameters2) {
        this.a = (AsymmetricKeyParameter) cipherParameters;
        this.b = (AsymmetricKeyParameter) cipherParameters2;
    }

    public AsymmetricCipherKeyPair(AsymmetricKeyParameter asymmetricKeyParameter, AsymmetricKeyParameter asymmetricKeyParameter2) {
        this.a = asymmetricKeyParameter;
        this.b = asymmetricKeyParameter2;
    }

    public AsymmetricKeyParameter getPrivate() {
        return this.b;
    }

    public AsymmetricKeyParameter getPublic() {
        return this.a;
    }
}
