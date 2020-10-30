package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CommitmentTypeQualifier extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1Encodable b;

    public CommitmentTypeQualifier(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, null);
    }

    public CommitmentTypeQualifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Encodable;
    }

    private CommitmentTypeQualifier(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            this.b = aSN1Sequence.getObjectAt(1);
        }
    }

    public static CommitmentTypeQualifier getInstance(Object obj) {
        if (obj instanceof CommitmentTypeQualifier) {
            return (CommitmentTypeQualifier) obj;
        }
        if (obj != null) {
            return new CommitmentTypeQualifier(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1ObjectIdentifier getCommitmentTypeIdentifier() {
        return this.a;
    }

    public ASN1Encodable getQualifier() {
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
