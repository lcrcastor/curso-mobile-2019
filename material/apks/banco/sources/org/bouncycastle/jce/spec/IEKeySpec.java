package org.bouncycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.bouncycastle.jce.interfaces.IESKey;

public class IEKeySpec implements KeySpec, IESKey {
    private PublicKey a;
    private PrivateKey b;

    public IEKeySpec(PrivateKey privateKey, PublicKey publicKey) {
        this.b = privateKey;
        this.a = publicKey;
    }

    public String getAlgorithm() {
        return "IES";
    }

    public byte[] getEncoded() {
        return null;
    }

    public String getFormat() {
        return null;
    }

    public PrivateKey getPrivate() {
        return this.b;
    }

    public PublicKey getPublic() {
        return this.a;
    }
}
