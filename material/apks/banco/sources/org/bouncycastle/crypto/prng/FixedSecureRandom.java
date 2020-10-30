package org.bouncycastle.crypto.prng;

import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class FixedSecureRandom extends SecureRandom {
    private byte[] a;
    private int b;
    private int c;

    public FixedSecureRandom(boolean z, byte[] bArr) {
        this(z, new byte[][]{bArr});
    }

    public FixedSecureRandom(boolean z, byte[][] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i != bArr.length) {
            try {
                byteArrayOutputStream.write(bArr[i]);
                i++;
            } catch (IOException unused) {
                throw new IllegalArgumentException("can't save value array.");
            }
        }
        this.a = byteArrayOutputStream.toByteArray();
        if (z) {
            this.c = this.a.length % 4;
        }
    }

    public FixedSecureRandom(byte[] bArr) {
        this(false, new byte[][]{bArr});
    }

    public FixedSecureRandom(byte[][] bArr) {
        this(false, bArr);
    }

    private int a() {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        return bArr[i] & UnsignedBytes.MAX_VALUE;
    }

    public byte[] generateSeed(int i) {
        byte[] bArr = new byte[i];
        nextBytes(bArr);
        return bArr;
    }

    public boolean isExhausted() {
        return this.b == this.a.length;
    }

    public void nextBytes(byte[] bArr) {
        System.arraycopy(this.a, this.b, bArr, 0, bArr.length);
        this.b += bArr.length;
    }

    public int nextInt() {
        int a2 = (a() << 24) | 0 | (a() << 16);
        if (this.c == 2) {
            this.c--;
        } else {
            a2 |= a() << 8;
        }
        if (this.c != 1) {
            return a2 | a();
        }
        this.c--;
        return a2;
    }

    public long nextLong() {
        return (((long) a()) << 56) | 0 | (((long) a()) << 48) | (((long) a()) << 40) | (((long) a()) << 32) | (((long) a()) << 24) | (((long) a()) << 16) | (((long) a()) << 8) | ((long) a());
    }
}
