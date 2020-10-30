package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class BEROctetString extends ASN1OctetString {
    /* access modifiers changed from: private */
    public ASN1OctetString[] b;

    public BEROctetString(byte[] bArr) {
        super(bArr);
    }

    public BEROctetString(ASN1OctetString[] aSN1OctetStringArr) {
        super(a(aSN1OctetStringArr));
        this.b = aSN1OctetStringArr;
    }

    static BEROctetString a(ASN1Sequence aSN1Sequence) {
        ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            aSN1OctetStringArr[i] = (ASN1OctetString) objects.nextElement();
            i = i2;
        }
        return new BEROctetString(aSN1OctetStringArr);
    }

    private static byte[] a(ASN1OctetString[] aSN1OctetStringArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i != aSN1OctetStringArr.length) {
            try {
                byteArrayOutputStream.write(aSN1OctetStringArr[i].getOctets());
                i++;
            } catch (ClassCastException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append(aSN1OctetStringArr[i].getClass().getName());
                sb.append(" found in input should only contain DEROctetString");
                throw new IllegalArgumentException(sb.toString());
            } catch (IOException e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("exception converting octets ");
                sb2.append(e.toString());
                throw new IllegalArgumentException(sb2.toString());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private Vector d() {
        Vector vector = new Vector();
        int i = 0;
        while (i < this.a.length) {
            int i2 = i + 1000;
            byte[] bArr = new byte[((i2 > this.a.length ? this.a.length : i2) - i)];
            System.arraycopy(this.a, i, bArr, 0, bArr.length);
            vector.addElement(new DEROctetString(bArr));
            i = i2;
        }
        return vector;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        Enumeration objects = getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().a();
        }
        return i + 2 + 2;
    }

    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.b(36);
        aSN1OutputStream.b(128);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            aSN1OutputStream.writeObject((ASN1Encodable) objects.nextElement());
        }
        aSN1OutputStream.b(0);
        aSN1OutputStream.b(0);
    }

    public Enumeration getObjects() {
        return this.b == null ? d().elements() : new Enumeration() {
            int a = 0;

            public boolean hasMoreElements() {
                return this.a < BEROctetString.this.b.length;
            }

            public Object nextElement() {
                ASN1OctetString[] a2 = BEROctetString.this.b;
                int i = this.a;
                this.a = i + 1;
                return a2[i];
            }
        };
    }

    public byte[] getOctets() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return true;
    }
}
