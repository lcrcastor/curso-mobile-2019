package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

public class SSL3Mac implements Mac {
    static final byte[] a = a(54, 48);
    static final byte[] b = a(92, 48);
    private Digest c;
    private byte[] d;
    private int e;

    public SSL3Mac(Digest digest) {
        this.c = digest;
        this.e = digest.getDigestSize() == 20 ? 40 : 48;
    }

    private static byte[] a(byte b2, int i) {
        byte[] bArr = new byte[i];
        Arrays.fill(bArr, b2);
        return bArr;
    }

    public int doFinal(byte[] bArr, int i) {
        byte[] bArr2 = new byte[this.c.getDigestSize()];
        this.c.doFinal(bArr2, 0);
        this.c.update(this.d, 0, this.d.length);
        this.c.update(b, 0, this.e);
        this.c.update(bArr2, 0, bArr2.length);
        int doFinal = this.c.doFinal(bArr, i);
        reset();
        return doFinal;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c.getAlgorithmName());
        sb.append("/SSL3MAC");
        return sb.toString();
    }

    public int getMacSize() {
        return this.c.getDigestSize();
    }

    public Digest getUnderlyingDigest() {
        return this.c;
    }

    public void init(CipherParameters cipherParameters) {
        this.d = Arrays.clone(((KeyParameter) cipherParameters).getKey());
        reset();
    }

    public void reset() {
        this.c.reset();
        this.c.update(this.d, 0, this.d.length);
        this.c.update(a, 0, this.e);
    }

    public void update(byte b2) {
        this.c.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.c.update(bArr, i, i2);
    }
}
