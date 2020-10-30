package org.bouncycastle.crypto.signers;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class GenericSigner implements Signer {
    private final AsymmetricBlockCipher a;
    private final Digest b;
    private boolean c;

    public GenericSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this.a = asymmetricBlockCipher;
        this.b = digest;
    }

    public byte[] generateSignature() {
        if (!this.c) {
            throw new IllegalStateException("GenericSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr, 0);
        return this.a.processBlock(bArr, 0, bArr.length);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.c = z;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        } else if (z || !asymmetricKeyParameter.isPrivate()) {
            reset();
            this.a.init(z, cipherParameters);
        } else {
            throw new IllegalArgumentException("verification requires public key");
        }
    }

    public void reset() {
        this.b.reset();
    }

    public void update(byte b2) {
        this.b.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.b.update(bArr, i, i2);
    }

    public boolean verifySignature(byte[] bArr) {
        if (this.c) {
            throw new IllegalStateException("GenericSigner not initialised for verification");
        }
        byte[] bArr2 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr2, 0);
        try {
            return Arrays.constantTimeAreEqual(this.a.processBlock(bArr, 0, bArr.length), bArr2);
        } catch (Exception unused) {
            return false;
        }
    }
}
