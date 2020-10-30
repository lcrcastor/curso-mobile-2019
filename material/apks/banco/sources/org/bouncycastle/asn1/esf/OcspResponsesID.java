package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class OcspResponsesID extends ASN1Object {
    private OcspIdentifier a;
    private OtherHash b;

    private OcspResponsesID(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = OcspIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.b = OtherHash.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public OcspResponsesID(OcspIdentifier ocspIdentifier) {
        this(ocspIdentifier, null);
    }

    public OcspResponsesID(OcspIdentifier ocspIdentifier, OtherHash otherHash) {
        this.a = ocspIdentifier;
        this.b = otherHash;
    }

    public static OcspResponsesID getInstance(Object obj) {
        if (obj instanceof OcspResponsesID) {
            return (OcspResponsesID) obj;
        }
        if (obj != null) {
            return new OcspResponsesID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public OcspIdentifier getOcspIdentifier() {
        return this.a;
    }

    public OtherHash getOcspRepHash() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
