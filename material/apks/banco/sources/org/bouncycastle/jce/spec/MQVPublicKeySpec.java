package org.bouncycastle.jce.spec;

import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.bouncycastle.jce.interfaces.MQVPublicKey;

public class MQVPublicKeySpec implements KeySpec, MQVPublicKey {
    private PublicKey a;
    private PublicKey b;

    public MQVPublicKeySpec(PublicKey publicKey, PublicKey publicKey2) {
        this.a = publicKey;
        this.b = publicKey2;
    }

    public String getAlgorithm() {
        return "ECMQV";
    }

    public byte[] getEncoded() {
        return null;
    }

    public PublicKey getEphemeralKey() {
        return this.b;
    }

    public String getFormat() {
        return null;
    }

    public PublicKey getStaticKey() {
        return this.a;
    }
}
