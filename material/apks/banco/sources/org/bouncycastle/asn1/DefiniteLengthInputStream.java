package org.bouncycastle.asn1;

import java.io.EOFException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

class DefiniteLengthInputStream extends LimitedInputStream {
    private static final byte[] b = new byte[0];
    private final int c;
    private int d;

    DefiniteLengthInputStream(InputStream inputStream, int i) {
        super(inputStream, i);
        if (i < 0) {
            throw new IllegalArgumentException("negative lengths not allowed");
        }
        this.c = i;
        this.d = i;
        if (i == 0) {
            b(true);
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public byte[] b() {
        if (this.d == 0) {
            return b;
        }
        byte[] bArr = new byte[this.d];
        int readFully = this.d - Streams.readFully(this.a, bArr);
        this.d = readFully;
        if (readFully != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("DEF length ");
            sb.append(this.c);
            sb.append(" object truncated by ");
            sb.append(this.d);
            throw new EOFException(sb.toString());
        }
        b(true);
        return bArr;
    }

    public int read() {
        if (this.d == 0) {
            return -1;
        }
        int read = this.a.read();
        if (read < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("DEF length ");
            sb.append(this.c);
            sb.append(" object truncated by ");
            sb.append(this.d);
            throw new EOFException(sb.toString());
        }
        int i = this.d - 1;
        this.d = i;
        if (i == 0) {
            b(true);
        }
        return read;
    }

    public int read(byte[] bArr, int i, int i2) {
        if (this.d == 0) {
            return -1;
        }
        int read = this.a.read(bArr, i, Math.min(i2, this.d));
        if (read < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("DEF length ");
            sb.append(this.c);
            sb.append(" object truncated by ");
            sb.append(this.d);
            throw new EOFException(sb.toString());
        }
        int i3 = this.d - read;
        this.d = i3;
        if (i3 == 0) {
            b(true);
        }
        return read;
    }
}
