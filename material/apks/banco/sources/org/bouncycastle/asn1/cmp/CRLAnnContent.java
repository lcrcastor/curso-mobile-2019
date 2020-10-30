package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.CertificateList;

public class CRLAnnContent extends ASN1Object {
    private ASN1Sequence a;

    private CRLAnnContent(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public CRLAnnContent(CertificateList certificateList) {
        this.a = new DERSequence((ASN1Encodable) certificateList);
    }

    public static CRLAnnContent getInstance(Object obj) {
        if (obj instanceof CRLAnnContent) {
            return (CRLAnnContent) obj;
        }
        if (obj != null) {
            return new CRLAnnContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificateList[] getCertificateLists() {
        CertificateList[] certificateListArr = new CertificateList[this.a.size()];
        for (int i = 0; i != certificateListArr.length; i++) {
            certificateListArr[i] = CertificateList.getInstance(this.a.getObjectAt(i));
        }
        return certificateListArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
