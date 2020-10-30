package org.bouncycastle.jcajce.spec;

import javax.crypto.SecretKey;

public class RepeatedSecretKeySpec implements SecretKey {
    private String a;

    public RepeatedSecretKeySpec(String str) {
        this.a = str;
    }

    public String getAlgorithm() {
        return this.a;
    }

    public byte[] getEncoded() {
        return null;
    }

    public String getFormat() {
        return null;
    }
}
