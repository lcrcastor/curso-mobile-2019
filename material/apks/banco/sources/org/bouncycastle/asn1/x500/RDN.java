package org.bouncycastle.asn1.x500;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;

public class RDN extends ASN1Object {
    private ASN1Set a;

    public RDN(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(aSN1ObjectIdentifier);
        aSN1EncodableVector.add(aSN1Encodable);
        this.a = new DERSet((ASN1Encodable) new DERSequence(aSN1EncodableVector));
    }

    private RDN(ASN1Set aSN1Set) {
        this.a = aSN1Set;
    }

    public RDN(AttributeTypeAndValue attributeTypeAndValue) {
        this.a = new DERSet((ASN1Encodable) attributeTypeAndValue);
    }

    public RDN(AttributeTypeAndValue[] attributeTypeAndValueArr) {
        this.a = new DERSet((ASN1Encodable[]) attributeTypeAndValueArr);
    }

    public static RDN getInstance(Object obj) {
        if (obj instanceof RDN) {
            return (RDN) obj;
        }
        if (obj != null) {
            return new RDN(ASN1Set.getInstance(obj));
        }
        return null;
    }

    public AttributeTypeAndValue getFirst() {
        if (this.a.size() == 0) {
            return null;
        }
        return AttributeTypeAndValue.getInstance(this.a.getObjectAt(0));
    }

    public AttributeTypeAndValue[] getTypesAndValues() {
        AttributeTypeAndValue[] attributeTypeAndValueArr = new AttributeTypeAndValue[this.a.size()];
        for (int i = 0; i != attributeTypeAndValueArr.length; i++) {
            attributeTypeAndValueArr[i] = AttributeTypeAndValue.getInstance(this.a.getObjectAt(i));
        }
        return attributeTypeAndValueArr;
    }

    public boolean isMultiValued() {
        return this.a.size() > 1;
    }

    public int size() {
        return this.a.size();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
