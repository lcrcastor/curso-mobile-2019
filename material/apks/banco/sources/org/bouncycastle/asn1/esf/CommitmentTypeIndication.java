package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CommitmentTypeIndication extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1Sequence b;

    public CommitmentTypeIndication(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
    }

    public CommitmentTypeIndication(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Sequence aSN1Sequence) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Sequence;
    }

    private CommitmentTypeIndication(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            this.b = (ASN1Sequence) aSN1Sequence.getObjectAt(1);
        }
    }

    public static CommitmentTypeIndication getInstance(Object obj) {
        return (obj == null || (obj instanceof CommitmentTypeIndication)) ? (CommitmentTypeIndication) obj : new CommitmentTypeIndication(ASN1Sequence.getInstance(obj));
    }

    public ASN1ObjectIdentifier getCommitmentTypeId() {
        return this.a;
    }

    public ASN1Sequence getCommitmentTypeQualifier() {
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
