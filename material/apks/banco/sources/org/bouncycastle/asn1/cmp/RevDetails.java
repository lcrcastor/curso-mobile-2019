package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.crmf.CertTemplate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.X509Extensions;

public class RevDetails extends ASN1Object {
    private CertTemplate a;
    private Extensions b;

    private RevDetails(ASN1Sequence aSN1Sequence) {
        this.a = CertTemplate.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.b = Extensions.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public RevDetails(CertTemplate certTemplate) {
        this.a = certTemplate;
    }

    public RevDetails(CertTemplate certTemplate, Extensions extensions) {
        this.a = certTemplate;
        this.b = extensions;
    }

    public RevDetails(CertTemplate certTemplate, X509Extensions x509Extensions) {
        this.a = certTemplate;
        this.b = Extensions.getInstance(x509Extensions.toASN1Primitive());
    }

    public static RevDetails getInstance(Object obj) {
        if (obj instanceof RevDetails) {
            return (RevDetails) obj;
        }
        if (obj != null) {
            return new RevDetails(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertTemplate getCertDetails() {
        return this.a;
    }

    public Extensions getCrlEntryDetails() {
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
