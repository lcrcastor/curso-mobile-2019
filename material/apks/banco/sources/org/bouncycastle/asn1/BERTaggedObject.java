package org.bouncycastle.asn1;

import java.util.Enumeration;
import org.bouncycastle.crypto.tls.CipherSuite;

public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int i) {
        super(false, i, new BERSequence());
    }

    public BERTaggedObject(int i, ASN1Encodable aSN1Encodable) {
        super(true, i, aSN1Encodable);
    }

    public BERTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        int b;
        if (this.b) {
            return StreamUtil.b(this.a) + 1;
        }
        int a = this.d.toASN1Primitive().a();
        if (this.c) {
            b = StreamUtil.b(this.a) + StreamUtil.a(a);
        } else {
            a--;
            b = StreamUtil.b(this.a);
        }
        return b + a;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        Enumeration enumeration;
        aSN1OutputStream.a((int) CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, this.a);
        aSN1OutputStream.b(128);
        if (!this.b) {
            if (!this.c) {
                if (this.d instanceof ASN1OctetString) {
                    enumeration = this.d instanceof BEROctetString ? ((BEROctetString) this.d).getObjects() : new BEROctetString(((ASN1OctetString) this.d).getOctets()).getObjects();
                } else if (this.d instanceof ASN1Sequence) {
                    enumeration = ((ASN1Sequence) this.d).getObjects();
                } else if (this.d instanceof ASN1Set) {
                    enumeration = ((ASN1Set) this.d).getObjects();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("not implemented: ");
                    sb.append(this.d.getClass().getName());
                    throw new RuntimeException(sb.toString());
                }
                while (enumeration.hasMoreElements()) {
                    aSN1OutputStream.writeObject((ASN1Encodable) enumeration.nextElement());
                }
            } else {
                aSN1OutputStream.writeObject(this.d);
            }
        }
        aSN1OutputStream.b(0);
        aSN1OutputStream.b(0);
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        if (this.b || this.c) {
            return true;
        }
        return this.d.toASN1Primitive().b().isConstructed();
    }
}
