package org.bouncycastle.asn1;

import java.util.Enumeration;

public class DLSequence extends ASN1Sequence {
    private int a = -1;

    public DLSequence() {
    }

    public DLSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public DLSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
    }

    public DLSequence(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr);
    }

    private int d() {
        if (this.a < 0) {
            int i = 0;
            Enumeration objects = getObjects();
            while (objects.hasMoreElements()) {
                i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().c().a();
            }
            this.a = i;
        }
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        int d = d();
        return StreamUtil.a(d) + 1 + d;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        ASN1OutputStream b = aSN1OutputStream.b();
        int d = d();
        aSN1OutputStream.b(48);
        aSN1OutputStream.a(d);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            b.writeObject((ASN1Encodable) objects.nextElement());
        }
    }
}
