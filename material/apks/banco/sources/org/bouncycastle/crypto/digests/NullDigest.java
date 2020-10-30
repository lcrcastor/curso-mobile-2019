package org.bouncycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.Digest;

public class NullDigest implements Digest {
    private ByteArrayOutputStream a = new ByteArrayOutputStream();

    public int doFinal(byte[] bArr, int i) {
        byte[] byteArray = this.a.toByteArray();
        System.arraycopy(byteArray, 0, bArr, i, byteArray.length);
        reset();
        return byteArray.length;
    }

    public String getAlgorithmName() {
        return "NULL";
    }

    public int getDigestSize() {
        return this.a.size();
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b) {
        this.a.write(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.write(bArr, i, i2);
    }
}
