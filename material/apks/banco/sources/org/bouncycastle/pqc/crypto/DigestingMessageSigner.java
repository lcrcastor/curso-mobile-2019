package org.bouncycastle.pqc.crypto;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class DigestingMessageSigner implements Signer {
    private final Digest a;
    private final MessageSigner b;
    private boolean c;

    public DigestingMessageSigner(MessageSigner messageSigner, Digest digest) {
        this.b = messageSigner;
        this.a = digest;
    }

    public byte[] generateSignature() {
        if (!this.c) {
            throw new IllegalStateException("RainbowDigestSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr, 0);
        return this.b.generateSignature(bArr);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.c = z;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("Signing Requires Private Key.");
        } else if (z || !asymmetricKeyParameter.isPrivate()) {
            reset();
            this.b.init(z, cipherParameters);
        } else {
            throw new IllegalArgumentException("Verification Requires Public Key.");
        }
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b2) {
        this.a.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }

    public boolean verify(byte[] bArr) {
        if (this.c) {
            throw new IllegalStateException("RainbowDigestSigner not initialised for verification");
        }
        byte[] bArr2 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr2, 0);
        return this.b.verifySignature(bArr2, bArr);
    }

    public boolean verifySignature(byte[] bArr) {
        return verify(bArr);
    }
}
