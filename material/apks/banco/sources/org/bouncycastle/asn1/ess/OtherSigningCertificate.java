package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class OtherSigningCertificate extends ASN1Object {
    ASN1Sequence a;
    ASN1Sequence b;

    private OtherSigningCertificate(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.b = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public OtherSigningCertificate(OtherCertID otherCertID) {
        this.a = new DERSequence((ASN1Encodable) otherCertID);
    }

    public static OtherSigningCertificate getInstance(Object obj) {
        if (obj instanceof OtherSigningCertificate) {
            return (OtherSigningCertificate) obj;
        }
        if (obj != null) {
            return new OtherSigningCertificate(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public OtherCertID[] getCerts() {
        OtherCertID[] otherCertIDArr = new OtherCertID[this.a.size()];
        for (int i = 0; i != this.a.size(); i++) {
            otherCertIDArr[i] = OtherCertID.getInstance(this.a.getObjectAt(i));
        }
        return otherCertIDArr;
    }

    public PolicyInformation[] getPolicies() {
        if (this.b == null) {
            return null;
        }
        PolicyInformation[] policyInformationArr = new PolicyInformation[this.b.size()];
        for (int i = 0; i != this.b.size(); i++) {
            policyInformationArr[i] = PolicyInformation.getInstance(this.b.getObjectAt(i));
        }
        return policyInformationArr;
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
