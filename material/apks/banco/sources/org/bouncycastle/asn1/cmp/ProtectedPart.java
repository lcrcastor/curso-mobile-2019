package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class ProtectedPart extends ASN1Object {
    private PKIHeader a;
    private PKIBody b;

    private ProtectedPart(ASN1Sequence aSN1Sequence) {
        this.a = PKIHeader.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = PKIBody.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public ProtectedPart(PKIHeader pKIHeader, PKIBody pKIBody) {
        this.a = pKIHeader;
        this.b = pKIBody;
    }

    public static ProtectedPart getInstance(Object obj) {
        if (obj instanceof ProtectedPart) {
            return (ProtectedPart) obj;
        }
        if (obj != null) {
            return new ProtectedPart(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public PKIBody getBody() {
        return this.b;
    }

    public PKIHeader getHeader() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
