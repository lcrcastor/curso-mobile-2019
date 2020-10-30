package org.bouncycastle.crypto.io;

import com.google.common.primitives.UnsignedBytes;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.SkippingCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.util.Arrays;

public class CipherInputStream extends FilterInputStream {
    private SkippingCipher a;
    private byte[] b;
    private BufferedBlockCipher c;
    private StreamCipher d;
    private AEADBlockCipher e;
    private byte[] f;
    private byte[] g;
    private int h;
    private int i;
    private boolean j;
    private long k;
    private int l;

    public CipherInputStream(InputStream inputStream, BufferedBlockCipher bufferedBlockCipher) {
        this(inputStream, bufferedBlockCipher, 2048);
    }

    public CipherInputStream(InputStream inputStream, BufferedBlockCipher bufferedBlockCipher, int i2) {
        super(inputStream);
        this.c = bufferedBlockCipher;
        this.b = new byte[i2];
        this.a = bufferedBlockCipher instanceof SkippingCipher ? (SkippingCipher) bufferedBlockCipher : null;
    }

    public CipherInputStream(InputStream inputStream, StreamCipher streamCipher) {
        this(inputStream, streamCipher, 2048);
    }

    public CipherInputStream(InputStream inputStream, StreamCipher streamCipher, int i2) {
        super(inputStream);
        this.d = streamCipher;
        this.b = new byte[i2];
        this.a = streamCipher instanceof SkippingCipher ? (SkippingCipher) streamCipher : null;
    }

    public CipherInputStream(InputStream inputStream, AEADBlockCipher aEADBlockCipher) {
        this(inputStream, aEADBlockCipher, 2048);
    }

    public CipherInputStream(InputStream inputStream, AEADBlockCipher aEADBlockCipher, int i2) {
        super(inputStream);
        this.e = aEADBlockCipher;
        this.b = new byte[i2];
        this.a = aEADBlockCipher instanceof SkippingCipher ? (SkippingCipher) aEADBlockCipher : null;
    }

    private int a() {
        if (this.j) {
            return -1;
        }
        this.h = 0;
        this.i = 0;
        while (this.i == 0) {
            int read = this.in.read(this.b);
            if (read == -1) {
                b();
                if (this.i == 0) {
                    return -1;
                }
                return this.i;
            }
            try {
                a(read, false);
                if (this.c != null) {
                    read = this.c.processBytes(this.b, 0, read, this.f, 0);
                } else if (this.e != null) {
                    read = this.e.processBytes(this.b, 0, read, this.f, 0);
                } else {
                    this.d.processBytes(this.b, 0, read, this.f, 0);
                }
                this.i = read;
            } catch (Exception e2) {
                throw new CipherIOException("Error processing stream ", e2);
            }
        }
        return this.i;
    }

    private void a(int i2, boolean z) {
        if (z) {
            if (this.c != null) {
                i2 = this.c.getOutputSize(i2);
            } else if (this.e != null) {
                i2 = this.e.getOutputSize(i2);
            }
        } else if (this.c != null) {
            i2 = this.c.getUpdateOutputSize(i2);
        } else if (this.e != null) {
            i2 = this.e.getUpdateOutputSize(i2);
        }
        if (this.f == null || this.f.length < i2) {
            this.f = new byte[i2];
        }
    }

    private void b() {
        int doFinal;
        try {
            this.j = true;
            a(0, true);
            if (this.c != null) {
                doFinal = this.c.doFinal(this.f, 0);
            } else if (this.e != null) {
                doFinal = this.e.doFinal(this.f, 0);
            } else {
                this.i = 0;
                return;
            }
            this.i = doFinal;
        } catch (InvalidCipherTextException e2) {
            throw new InvalidCipherTextIOException("Error finalising cipher", e2);
        } catch (Exception e3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error finalising cipher ");
            sb.append(e3);
            throw new IOException(sb.toString());
        }
    }

    public int available() {
        return this.i - this.h;
    }

    public void close() {
        try {
            this.in.close();
            this.h = 0;
            this.i = 0;
            this.l = 0;
            this.k = 0;
            if (this.g != null) {
                Arrays.fill(this.g, 0);
                this.g = null;
            }
            if (this.f != null) {
                Arrays.fill(this.f, 0);
                this.f = null;
            }
            Arrays.fill(this.b, 0);
        } finally {
            if (!this.j) {
                b();
            }
        }
    }

    public void mark(int i2) {
        this.in.mark(i2);
        if (this.a != null) {
            this.k = this.a.getPosition();
        }
        if (this.f != null) {
            this.g = new byte[this.f.length];
            System.arraycopy(this.f, 0, this.g, 0, this.f.length);
        }
        this.l = this.h;
    }

    public boolean markSupported() {
        if (this.a != null) {
            return this.in.markSupported();
        }
        return false;
    }

    public int read() {
        if (this.h >= this.i && a() < 0) {
            return -1;
        }
        byte[] bArr = this.f;
        int i2 = this.h;
        this.h = i2 + 1;
        return bArr[i2] & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i2, int i3) {
        if (this.h >= this.i && a() < 0) {
            return -1;
        }
        int min = Math.min(i3, available());
        System.arraycopy(this.f, this.h, bArr, i2, min);
        this.h += min;
        return min;
    }

    public void reset() {
        if (this.a == null) {
            throw new IOException("cipher must implement SkippingCipher to be used with reset()");
        }
        this.in.reset();
        this.a.seekTo(this.k);
        if (this.g != null) {
            this.f = this.g;
        }
        this.h = this.l;
    }

    public long skip(long j2) {
        if (j2 <= 0) {
            return 0;
        }
        if (this.a != null) {
            long available = (long) available();
            if (j2 <= available) {
                this.h = (int) (((long) this.h) + j2);
                return j2;
            }
            this.h = this.i;
            long skip = this.in.skip(j2 - available);
            if (skip == this.a.skip(skip)) {
                return skip + available;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to skip cipher ");
            sb.append(skip);
            sb.append(" bytes.");
            throw new IOException(sb.toString());
        }
        int min = (int) Math.min(j2, (long) available());
        this.h += min;
        return (long) min;
    }
}
