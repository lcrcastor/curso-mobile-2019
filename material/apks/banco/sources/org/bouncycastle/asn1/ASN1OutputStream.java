package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.eac.CertificateBody;

public class ASN1OutputStream {
    private OutputStream a;

    class ImplicitOutputStream extends ASN1OutputStream {
        private boolean b = true;

        public ImplicitOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        public void b(int i) {
            if (this.b) {
                this.b = false;
            } else {
                ASN1OutputStream.super.b(i);
            }
        }
    }

    public ASN1OutputStream(OutputStream outputStream) {
        this.a = outputStream;
    }

    /* access modifiers changed from: 0000 */
    public ASN1OutputStream a() {
        return new DEROutputStream(this.a);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
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
            b((byte) (i3 | 128));
            for (int i4 = (i3 - 1) * 8; i4 >= 0; i4 -= 8) {
                b((byte) (i >> i4));
            }
            return;
        }
        b((byte) i);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, int i2) {
        if (i2 < 31) {
            b(i | i2);
            return;
        }
        b(i | 31);
        if (i2 < 128) {
            b(i2);
            return;
        }
        byte[] bArr = new byte[5];
        int length = bArr.length - 1;
        bArr[length] = (byte) (i2 & CertificateBody.profileType);
        do {
            i2 >>= 7;
            length--;
            bArr[length] = (byte) ((i2 & CertificateBody.profileType) | 128);
        } while (i2 > 127);
        a(bArr, length, bArr.length - length);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, int i2, byte[] bArr) {
        a(i, i2);
        a(bArr.length);
        a(bArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, byte[] bArr) {
        b(i);
        a(bArr.length);
        a(bArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive != null) {
            aSN1Primitive.encode(new ImplicitOutputStream(this.a));
            return;
        }
        throw new IOException("null object detected");
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr) {
        this.a.write(bArr);
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr, int i, int i2) {
        this.a.write(bArr, i, i2);
    }

    /* access modifiers changed from: 0000 */
    public ASN1OutputStream b() {
        return new DLOutputStream(this.a);
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        this.a.write(i);
    }

    public void close() {
        this.a.close();
    }

    public void flush() {
        this.a.flush();
    }

    /* access modifiers changed from: protected */
    public void writeNull() {
        this.a.write(5);
        this.a.write(0);
    }

    public void writeObject(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}
