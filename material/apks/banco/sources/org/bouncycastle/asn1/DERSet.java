package org.bouncycastle.asn1;

import java.util.Enumeration;

public class DERSet extends ASN1Set {
    private int a = -1;

    public DERSet() {
    }

    public DERSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public DERSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, true);
    }

    DERSet(ASN1EncodableVector aSN1EncodableVector, boolean z) {
        super(aSN1EncodableVector, z);
    }

    public DERSet(ASN1Encodable[] aSN1EncodableArr) {
        super(aSN1EncodableArr, true);
    }

    private int d() {
        if (this.a < 0) {
            int i = 0;
            Enumeration objects = getObjects();
            while (objects.hasMoreElements()) {
                i += ((ASN1Encodable) objects.nextElement()).toASN1Primitive().b().a();
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
        ASN1OutputStream a2 = aSN1OutputStream.a();
        int d = d();
        aSN1OutputStream.b(49);
        aSN1OutputStream.a(d);
        Enumeration objects = getObjects();
        while (objects.hasMoreElements()) {
            a2.writeObject((ASN1Encodable) objects.nextElement());
        }
    }
}
