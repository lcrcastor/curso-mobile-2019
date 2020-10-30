package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSequence;

public class Attribute extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1Set b;

    public Attribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Set aSN1Set) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Set;
    }

    private Attribute(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.b = (ASN1Set) aSN1Sequence.getObjectAt(1);
    }

    public static Attribute getInstance(Object obj) {
        if (obj instanceof Attribute) {
            return (Attribute) obj;
        }
        if (obj != null) {
            return new Attribute(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1ObjectIdentifier getAttrType() {
        return this.a;
    }

    public ASN1Set getAttrValues() {
        return this.b;
    }

    public ASN1Encodable[] getAttributeValues() {
        return this.b.toArray();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
