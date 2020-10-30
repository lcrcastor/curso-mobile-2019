package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class OtherKeyAttribute extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1Encodable b;

    public OtherKeyAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Encodable;
    }

    public OtherKeyAttribute(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.b = aSN1Sequence.getObjectAt(1);
    }

    public static OtherKeyAttribute getInstance(Object obj) {
        if (obj instanceof OtherKeyAttribute) {
            return (OtherKeyAttribute) obj;
        }
        if (obj != null) {
            return new OtherKeyAttribute(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Encodable getKeyAttr() {
        return this.b;
    }

    public ASN1ObjectIdentifier getKeyAttrId() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
