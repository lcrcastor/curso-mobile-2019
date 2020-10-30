package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.util.Arrays;

public class TweakableBlockCipherParameters implements CipherParameters {
    private final byte[] a;
    private final KeyParameter b;

    public TweakableBlockCipherParameters(KeyParameter keyParameter, byte[] bArr) {
        this.b = keyParameter;
        this.a = Arrays.clone(bArr);
    }

    public KeyParameter getKey() {
        return this.b;
    }

    public byte[] getTweak() {
        return this.a;
    }
}
