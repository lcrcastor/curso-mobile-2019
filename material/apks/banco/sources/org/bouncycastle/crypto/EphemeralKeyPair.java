package org.bouncycastle.crypto;

public class EphemeralKeyPair {
    private AsymmetricCipherKeyPair a;
    private KeyEncoder b;

    public EphemeralKeyPair(AsymmetricCipherKeyPair asymmetricCipherKeyPair, KeyEncoder keyEncoder) {
        this.a = asymmetricCipherKeyPair;
        this.b = keyEncoder;
    }

    public byte[] getEncodedPublicKey() {
        return this.b.getEncoded(this.a.getPublic());
    }

    public AsymmetricCipherKeyPair getKeyPair() {
        return this.a;
    }
}
