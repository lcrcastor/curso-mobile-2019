package org.bouncycastle.jcajce.io;

import com.google.common.primitives.UnsignedBytes;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.bouncycastle.crypto.io.InvalidCipherTextIOException;

public class CipherInputStream extends FilterInputStream {
    private final Cipher a;
    private final byte[] b = new byte[512];
    private boolean c = false;
    private byte[] d;
    private int e;
    private int f;

    public CipherInputStream(InputStream inputStream, Cipher cipher) {
        super(inputStream);
        this.a = cipher;
    }

    private int a() {
        if (this.c) {
            return -1;
        }
        this.f = 0;
        this.e = 0;
        while (this.e == 0) {
            int read = this.in.read(this.b);
            if (read == -1) {
                this.d = b();
                if (this.d == null || this.d.length == 0) {
                    return -1;
                }
                this.e = this.d.length;
                return this.e;
            }
            this.d = this.a.update(this.b, 0, read);
            if (this.d != null) {
                this.e = this.d.length;
            }
        }
        return this.e;
    }

    private byte[] b() {
        try {
            this.c = true;
            return this.a.doFinal();
        } catch (GeneralSecurityException e2) {
            throw new InvalidCipherTextIOException("Error finalising cipher", e2);
        }
    }

    public int available() {
        return this.e - this.f;
    }

    public void close() {
        try {
            this.in.close();
            this.f = 0;
            this.e = 0;
        } finally {
            if (!this.c) {
                b();
            }
        }
    }

    public void mark(int i) {
    }

    public boolean markSupported() {
        return false;
    }

    public int read() {
        if (this.f >= this.e && a() < 0) {
            return -1;
        }
        byte[] bArr = this.d;
        int i = this.f;
        this.f = i + 1;
        return bArr[i] & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) {
        if (this.f >= this.e && a() < 0) {
            return -1;
        }
        int min = Math.min(i2, available());
        System.arraycopy(this.d, this.f, bArr, i, min);
        this.f += min;
        return min;
    }

    public void reset() {
    }

    public long skip(long j) {
        if (j <= 0) {
            return 0;
        }
        int min = (int) Math.min(j, (long) available());
        this.f += min;
        return (long) min;
    }
}
