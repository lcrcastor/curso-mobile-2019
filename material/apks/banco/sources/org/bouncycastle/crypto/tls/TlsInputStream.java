package org.bouncycastle.crypto.tls;

import com.google.common.primitives.UnsignedBytes;
import java.io.InputStream;

class TlsInputStream extends InputStream {
    private byte[] a = new byte[1];
    private TlsProtocol b = null;

    TlsInputStream(TlsProtocol tlsProtocol) {
        this.b = tlsProtocol;
    }

    public int available() {
        return this.b.applicationDataAvailable();
    }

    public void close() {
        this.b.close();
    }

    public int read() {
        if (read(this.a) < 0) {
            return -1;
        }
        return this.a[0] & UnsignedBytes.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) {
        return this.b.readApplicationData(bArr, i, i2);
    }
}
