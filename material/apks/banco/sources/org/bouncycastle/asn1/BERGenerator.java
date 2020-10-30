package org.bouncycastle.asn1;

import java.io.InputStream;
import java.io.OutputStream;

public class BERGenerator extends ASN1Generator {
    private boolean a;
    private boolean b;
    private int c;

    protected BERGenerator(OutputStream outputStream) {
        super(outputStream);
        this.a = false;
    }

    public BERGenerator(OutputStream outputStream, int i, boolean z) {
        super(outputStream);
        this.a = false;
        this.a = true;
        this.b = z;
        this.c = i;
    }

    private void a(int i) {
        this._out.write(i);
        this._out.write(128);
    }

    public OutputStream getRawOutputStream() {
        return this._out;
    }

    /* access modifiers changed from: protected */
    public void writeBERBody(InputStream inputStream) {
        while (true) {
            int read = inputStream.read();
            if (read >= 0) {
                this._out.write(read);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeBEREnd() {
        this._out.write(0);
        this._out.write(0);
        if (this.a && this.b) {
            this._out.write(0);
            this._out.write(0);
        }
    }

    /* access modifiers changed from: protected */
    public void writeBERHeader(int i) {
        if (this.a) {
            int i2 = this.c | 128;
            if (this.b) {
                a(i2 | 32);
            } else if ((i & 32) != 0) {
                i = i2 | 32;
            } else {
                a(i2);
                return;
            }
        }
        a(i);
    }
}
