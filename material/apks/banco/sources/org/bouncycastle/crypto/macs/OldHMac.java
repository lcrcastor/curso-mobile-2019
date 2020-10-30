package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;

public class OldHMac implements Mac {
    private Digest a;
    private int b;
    private byte[] c = new byte[64];
    private byte[] d = new byte[64];

    public OldHMac(Digest digest) {
        this.a = digest;
        this.b = digest.getDigestSize();
    }

    public int doFinal(byte[] bArr, int i) {
        byte[] bArr2 = new byte[this.b];
        this.a.doFinal(bArr2, 0);
        this.a.update(this.d, 0, this.d.length);
        this.a.update(bArr2, 0, bArr2.length);
        int doFinal = this.a.doFinal(bArr, i);
        reset();
        return doFinal;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("/HMAC");
        return sb.toString();
    }

    public int getMacSize() {
        return this.b;
    }

    public Digest getUnderlyingDigest() {
        return this.a;
    }

    public void init(CipherParameters cipherParameters) {
        this.a.reset();
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length > 64) {
            this.a.update(key, 0, key.length);
            this.a.doFinal(this.c, 0);
            for (int i = this.b; i < this.c.length; i++) {
                this.c[i] = 0;
            }
        } else {
            System.arraycopy(key, 0, this.c, 0, key.length);
            for (int length = key.length; length < this.c.length; length++) {
                this.c[length] = 0;
            }
        }
        this.d = new byte[this.c.length];
        System.arraycopy(this.c, 0, this.d, 0, this.c.length);
        for (int i2 = 0; i2 < this.c.length; i2++) {
            byte[] bArr = this.c;
            bArr[i2] = (byte) (bArr[i2] ^ 54);
        }
        for (int i3 = 0; i3 < this.d.length; i3++) {
            byte[] bArr2 = this.d;
            bArr2[i3] = (byte) (bArr2[i3] ^ 92);
        }
        this.a.update(this.c, 0, this.c.length);
    }

    public void reset() {
        this.a.reset();
        this.a.update(this.c, 0, this.c.length);
    }

    public void update(byte b2) {
        this.a.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
