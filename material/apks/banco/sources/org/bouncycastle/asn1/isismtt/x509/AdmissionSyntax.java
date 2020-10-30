package org.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;

public class AdmissionSyntax extends ASN1Object {
    private GeneralName a;
    private ASN1Sequence b;

    private AdmissionSyntax(ASN1Sequence aSN1Sequence) {
        ASN1Encodable aSN1Encodable;
        switch (aSN1Sequence.size()) {
            case 1:
                aSN1Encodable = aSN1Sequence.getObjectAt(0);
                break;
            case 2:
                this.a = GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
                aSN1Encodable = aSN1Sequence.getObjectAt(1);
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Bad sequence size: ");
                sb.append(aSN1Sequence.size());
                throw new IllegalArgumentException(sb.toString());
        }
        this.b = DERSequence.getInstance(aSN1Encodable);
    }

    public AdmissionSyntax(GeneralName generalName, ASN1Sequence aSN1Sequence) {
        this.a = generalName;
        this.b = aSN1Sequence;
    }

    public static AdmissionSyntax getInstance(Object obj) {
        if (obj == null || (obj instanceof AdmissionSyntax)) {
            return (AdmissionSyntax) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AdmissionSyntax((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public GeneralName getAdmissionAuthority() {
        return this.a;
    }

    public Admissions[] getContentsOfAdmissions() {
        Admissions[] admissionsArr = new Admissions[this.b.size()];
        Enumeration objects = this.b.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            admissionsArr[i] = Admissions.getInstance(objects.nextElement());
            i = i2;
        }
        return admissionsArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
