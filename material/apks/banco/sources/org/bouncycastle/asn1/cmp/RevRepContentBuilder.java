package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.crmf.CertId;
import org.bouncycastle.asn1.x509.CertificateList;

public class RevRepContentBuilder {
    private ASN1EncodableVector a = new ASN1EncodableVector();
    private ASN1EncodableVector b = new ASN1EncodableVector();
    private ASN1EncodableVector c = new ASN1EncodableVector();

    public RevRepContentBuilder add(PKIStatusInfo pKIStatusInfo) {
        this.a.add(pKIStatusInfo);
        return this;
    }

    public RevRepContentBuilder add(PKIStatusInfo pKIStatusInfo, CertId certId) {
        if (this.a.size() != this.b.size()) {
            throw new IllegalStateException("status and revCerts sequence must be in common order");
        }
        this.a.add(pKIStatusInfo);
        this.b.add(certId);
        return this;
    }

    public RevRepContentBuilder addCrl(CertificateList certificateList) {
        this.c.add(certificateList);
        return this;
    }

    public RevRepContent build() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new DERSequence(this.a));
        if (this.b.size() != 0) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, new DERSequence(this.b)));
        }
        if (this.c.size() != 0) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, new DERSequence(this.c)));
        }
        return RevRepContent.getInstance(new DERSequence(aSN1EncodableVector));
    }
}
