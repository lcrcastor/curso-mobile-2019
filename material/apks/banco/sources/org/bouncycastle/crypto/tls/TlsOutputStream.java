package org.bouncycastle.crypto.tls;

import java.io.OutputStream;

class TlsOutputStream extends OutputStream {
    private byte[] a = new byte[1];
    private TlsProtocol b;

    TlsOutputStream(TlsProtocol tlsProtocol) {
        this.b = tlsProtocol;
    }

    public void close() {
        this.b.close();
    }

    public void flush() {
        this.b.flush();
    }

    public void write(int i) {
        this.a[0] = (byte) i;
        write(this.a, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.b.writeData(bArr, i, i2);
    }
}
