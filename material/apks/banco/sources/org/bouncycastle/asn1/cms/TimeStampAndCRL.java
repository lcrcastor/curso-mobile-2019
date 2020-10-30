package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.CertificateList;

public class TimeStampAndCRL extends ASN1Object {
    private ContentInfo a;
    private CertificateList b;

    private TimeStampAndCRL(ASN1Sequence aSN1Sequence) {
        this.a = ContentInfo.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.b = CertificateList.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public TimeStampAndCRL(ContentInfo contentInfo) {
        this.a = contentInfo;
    }

    public static TimeStampAndCRL getInstance(Object obj) {
        if (obj instanceof TimeStampAndCRL) {
            return (TimeStampAndCRL) obj;
        }
        if (obj != null) {
            return new TimeStampAndCRL(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificateList getCRL() {
        return this.b;
    }

    public CertificateList getCertificateList() {
        return this.b;
    }

    public ContentInfo getTimeStampToken() {
        return this.a;
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
