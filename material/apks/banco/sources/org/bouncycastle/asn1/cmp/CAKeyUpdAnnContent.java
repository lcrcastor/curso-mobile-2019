package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CAKeyUpdAnnContent extends ASN1Object {
    private CMPCertificate a;
    private CMPCertificate b;
    private CMPCertificate c;

    private CAKeyUpdAnnContent(ASN1Sequence aSN1Sequence) {
        this.a = CMPCertificate.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = CMPCertificate.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = CMPCertificate.getInstance(aSN1Sequence.getObjectAt(2));
    }

    public CAKeyUpdAnnContent(CMPCertificate cMPCertificate, CMPCertificate cMPCertificate2, CMPCertificate cMPCertificate3) {
        this.a = cMPCertificate;
        this.b = cMPCertificate2;
        this.c = cMPCertificate3;
    }

    public static CAKeyUpdAnnContent getInstance(Object obj) {
        if (obj instanceof CAKeyUpdAnnContent) {
            return (CAKeyUpdAnnContent) obj;
        }
        if (obj != null) {
            return new CAKeyUpdAnnContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CMPCertificate getNewWithNew() {
        return this.c;
    }

    public CMPCertificate getNewWithOld() {
        return this.b;
    }

    public CMPCertificate getOldWithNew() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
