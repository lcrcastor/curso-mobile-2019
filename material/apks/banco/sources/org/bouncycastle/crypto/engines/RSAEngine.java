package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;

public class RSAEngine implements AsymmetricBlockCipher {
    private RSACoreEngine a;

    public int getInputBlockSize() {
        return this.a.a();
    }

    public int getOutputBlockSize() {
        return this.a.b();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (this.a == null) {
            this.a = new RSACoreEngine();
        }
        this.a.a(z, cipherParameters);
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        if (this.a != null) {
            return this.a.a(this.a.b(this.a.a(bArr, i, i2)));
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}
