package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class AsymmetricKeyParameter implements CipherParameters {
    boolean a;

    public AsymmetricKeyParameter(boolean z) {
        this.a = z;
    }

    public boolean isPrivate() {
        return this.a;
    }
}
