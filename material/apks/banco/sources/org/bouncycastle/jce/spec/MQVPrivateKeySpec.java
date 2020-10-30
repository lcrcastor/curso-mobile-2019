package org.bouncycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.bouncycastle.jce.interfaces.MQVPrivateKey;

public class MQVPrivateKeySpec implements KeySpec, MQVPrivateKey {
    private PrivateKey a;
    private PrivateKey b;
    private PublicKey c;

    public MQVPrivateKeySpec(PrivateKey privateKey, PrivateKey privateKey2) {
        this(privateKey, privateKey2, null);
    }

    public MQVPrivateKeySpec(PrivateKey privateKey, PrivateKey privateKey2, PublicKey publicKey) {
        this.a = privateKey;
        this.b = privateKey2;
        this.c = publicKey;
    }

    public String getAlgorithm() {
        return "ECMQV";
    }

    public byte[] getEncoded() {
        return null;
    }

    public PrivateKey getEphemeralPrivateKey() {
        return this.b;
    }

    public PublicKey getEphemeralPublicKey() {
        return this.c;
    }

    public String getFormat() {
        return null;
    }

    public PrivateKey getStaticPrivateKey() {
        return this.a;
    }
}
