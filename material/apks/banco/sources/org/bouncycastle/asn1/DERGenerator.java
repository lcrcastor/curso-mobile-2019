package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public abstract class DERGenerator extends ASN1Generator {
    private boolean a;
    private boolean b;
    private int c;

    protected DERGenerator(OutputStream outputStream) {
        super(outputStream);
        this.a = false;
    }

    public DERGenerator(OutputStream outputStream, int i, boolean z) {
        super(outputStream);
        this.a = false;
        this.a = true;
        this.b = z;
        this.c = i;
    }

    private void a(OutputStream outputStream, int i) {
        if (i > 127) {
            int i2 = i;
            int i3 = 1;
            while (true) {
                i2 >>>= 8;
                if (i2 == 0) {
                    break;
                }
                i3++;
            }
            outputStream.write((byte) (i3 | 128));
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                outputStream.write((byte) (i >> i4));
            }
            return;
        }
        outputStream.write((byte) i);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, byte[] bArr) {
        OutputStream outputStream;
        if (this.a) {
            int i2 = this.c | 128;
            if (this.b) {
                i2 = this.c | 32 | 128;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                a(byteArrayOutputStream, i, bArr);
                outputStream = this._out;
                bArr = byteArrayOutputStream.toByteArray();
            } else if ((i & 32) != 0) {
                outputStream = this._out;
                i2 |= 32;
            } else {
                outputStream = this._out;
            }
            a(outputStream, i2, bArr);
            return;
        }
        a(this._out, i, bArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(OutputStream outputStream, int i, byte[] bArr) {
        outputStream.write(i);
        a(outputStream, bArr.length);
        outputStream.write(bArr);
    }
}
