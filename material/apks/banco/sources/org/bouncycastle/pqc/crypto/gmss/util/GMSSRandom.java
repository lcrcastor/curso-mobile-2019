package org.bouncycastle.pqc.crypto.gmss.util;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.Digest;

public class GMSSRandom {
    private Digest a;

    public GMSSRandom(Digest digest) {
        this.a = digest;
    }

    private void a(byte[] bArr) {
        byte b = 1;
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] & UnsignedBytes.MAX_VALUE) + b;
            bArr[i] = (byte) i2;
            b = (byte) (i2 >> 8);
        }
    }

    private void a(byte[] bArr, byte[] bArr2) {
        byte b = 0;
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] & UnsignedBytes.MAX_VALUE) + (bArr2[i] & UnsignedBytes.MAX_VALUE) + b;
            bArr[i] = (byte) i2;
            b = (byte) (i2 >> 8);
        }
    }

    public byte[] nextSeed(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        this.a.update(bArr, 0, bArr.length);
        byte[] bArr3 = new byte[this.a.getDigestSize()];
        this.a.doFinal(bArr3, 0);
        a(bArr, bArr3);
        a(bArr);
        return bArr3;
    }
}
