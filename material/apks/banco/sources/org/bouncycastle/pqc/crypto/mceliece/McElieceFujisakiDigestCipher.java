package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageEncryptor;

public class McElieceFujisakiDigestCipher {
    private final Digest a;
    private final MessageEncryptor b;
    private boolean c;

    public McElieceFujisakiDigestCipher(MessageEncryptor messageEncryptor, Digest digest) {
        this.b = messageEncryptor;
        this.a = digest;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.c = z;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z && asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("Encrypting Requires Public Key.");
        } else if (z || asymmetricKeyParameter.isPrivate()) {
            reset();
            this.b.init(z, cipherParameters);
        } else {
            throw new IllegalArgumentException("Decrypting Requires Private Key.");
        }
    }

    public byte[] messageDecrypt(byte[] bArr) {
        if (this.c) {
            throw new IllegalStateException("McElieceFujisakiDigestCipher not initialised for decrypting.");
        }
        try {
            return this.b.messageDecrypt(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] messageEncrypt() {
        if (!this.c) {
            throw new IllegalStateException("McElieceFujisakiDigestCipher not initialised for encrypting.");
        }
        byte[] bArr = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr, 0);
        try {
            return this.b.messageEncrypt(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
}
