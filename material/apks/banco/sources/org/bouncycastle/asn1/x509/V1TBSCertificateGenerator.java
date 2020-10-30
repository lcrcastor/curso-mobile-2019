package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class V1TBSCertificateGenerator {
    DERTaggedObject a = new DERTaggedObject(true, 0, new ASN1Integer(0));
    ASN1Integer b;
    AlgorithmIdentifier c;
    X500Name d;
    Time e;
    Time f;
    X500Name g;
    SubjectPublicKeyInfo h;

    public TBSCertificate generateTBSCertificate() {
        if (this.b == null || this.c == null || this.d == null || this.e == null || this.f == null || this.g == null || this.h == null) {
            throw new IllegalStateException("not all mandatory fields set in V1 TBScertificate generator");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        aSN1EncodableVector2.add(this.e);
        aSN1EncodableVector2.add(this.f);
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.g);
        aSN1EncodableVector.add(this.h);
        return TBSCertificate.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public void setEndDate(ASN1UTCTime aSN1UTCTime) {
        this.f = new Time((ASN1Primitive) aSN1UTCTime);
    }

    public void setEndDate(Time time) {
        this.f = time;
    }

    public void setIssuer(X500Name x500Name) {
        this.d = x500Name;
    }

    public void setIssuer(X509Name x509Name) {
        this.d = X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setSerialNumber(ASN1Integer aSN1Integer) {
        this.b = aSN1Integer;
    }

    public void setSignature(AlgorithmIdentifier algorithmIdentifier) {
        this.c = algorithmIdentifier;
    }

    public void setStartDate(ASN1UTCTime aSN1UTCTime) {
        this.e = new Time((ASN1Primitive) aSN1UTCTime);
    }

    public void setStartDate(Time time) {
        this.e = time;
    }

    public void setSubject(X500Name x500Name) {
        this.g = x500Name;
    }

    public void setSubject(X509Name x509Name) {
        this.g = X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.h = subjectPublicKeyInfo;
    }
}
