package org.bouncycastle.asn1;

import java.io.OutputStream;

public class BEROctetStringGenerator extends BERGenerator {

    class BufferedBEROctetStream extends OutputStream {
        private byte[] b;
        private int c = 0;
        private DEROutputStream d;

        BufferedBEROctetStream(byte[] bArr) {
            this.b = bArr;
            this.d = new DEROutputStream(BEROctetStringGenerator.this._out);
        }

        public void close() {
            if (this.c != 0) {
                byte[] bArr = new byte[this.c];
                System.arraycopy(this.b, 0, bArr, 0, this.c);
                DEROctetString.a(this.d, bArr);
            }
            BEROctetStringGenerator.this.writeBEREnd();
        }

        public void write(int i) {
            byte[] bArr = this.b;
            int i2 = this.c;
            this.c = i2 + 1;
            bArr[i2] = (byte) i;
            if (this.c == this.b.length) {
                DEROctetString.a(this.d, this.b);
                this.c = 0;
            }
        }

        public void write(byte[] bArr, int i, int i2) {
            while (i2 > 0) {
                int min = Math.min(i2, this.b.length - this.c);
                System.arraycopy(bArr, i, this.b, this.c, min);
                this.c += min;
                if (this.c >= this.b.length) {
                    DEROctetString.a(this.d, this.b);
                    this.c = 0;
                    i += min;
                    i2 -= min;
                } else {
                    return;
                }
            }
        }
    }

    public BEROctetStringGenerator(OutputStream outputStream) {
        super(outputStream);
        writeBERHeader(36);
    }

    public BEROctetStringGenerator(OutputStream outputStream, int i, boolean z) {
        super(outputStream, i, z);
        writeBERHeader(36);
    }

    public OutputStream getOctetOutputStream() {
        return getOctetOutputStream(new byte[1000]);
    }

    public OutputStream getOctetOutputStream(byte[] bArr) {
        return new BufferedBEROctetStream(bArr);
    }
}
