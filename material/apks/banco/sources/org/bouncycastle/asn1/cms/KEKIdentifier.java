package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class KEKIdentifier extends ASN1Object {
    private ASN1OctetString a;
    private ASN1GeneralizedTime b;
    private OtherKeyAttribute c;

    private KEKIdentifier(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt;
        this.a = (ASN1OctetString) aSN1Sequence.getObjectAt(0);
        switch (aSN1Sequence.size()) {
            case 1:
                return;
            case 2:
                if (!(aSN1Sequence.getObjectAt(1) instanceof ASN1GeneralizedTime)) {
                    objectAt = aSN1Sequence.getObjectAt(1);
                    break;
                } else {
                    this.b = (ASN1GeneralizedTime) aSN1Sequence.getObjectAt(1);
                    return;
                }
            case 3:
                this.b = (ASN1GeneralizedTime) aSN1Sequence.getObjectAt(1);
                objectAt = aSN1Sequence.getObjectAt(2);
                break;
            default:
                throw new IllegalArgumentException("Invalid KEKIdentifier");
        }
        this.c = OtherKeyAttribute.getInstance(objectAt);
    }

    public KEKIdentifier(byte[] bArr, ASN1GeneralizedTime aSN1GeneralizedTime, OtherKeyAttribute otherKeyAttribute) {
        this.a = new DEROctetString(bArr);
        this.b = aSN1GeneralizedTime;
        this.c = otherKeyAttribute;
    }

    public static KEKIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof KEKIdentifier)) {
            return (KEKIdentifier) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new KEKIdentifier((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid KEKIdentifier: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static KEKIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1GeneralizedTime getDate() {
        return this.b;
    }

    public ASN1OctetString getKeyIdentifier() {
        return this.a;
    }

    public OtherKeyAttribute getOther() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
