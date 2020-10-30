package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class V3TBSCertificateGenerator {
    DERTaggedObject a = new DERTaggedObject(true, 0, new ASN1Integer(2));
    ASN1Integer b;
    AlgorithmIdentifier c;
    X500Name d;
    Time e;
    Time f;
    X500Name g;
    SubjectPublicKeyInfo h;
    Extensions i;
    private boolean j;
    private DERBitString k;
    private DERBitString l;

    public TBSCertificate generateTBSCertificate() {
        if (this.b == null || this.c == null || this.d == null || this.e == null || this.f == null || ((this.g == null && !this.j) || this.h == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        aSN1EncodableVector2.add(this.e);
        aSN1EncodableVector2.add(this.f);
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.g != null ? this.g : new DERSequence());
        aSN1EncodableVector.add(this.h);
        if (this.k != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.k));
        }
        if (this.l != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, this.l));
        }
        if (this.i != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 3, this.i));
        }
        return TBSCertificate.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public void setEndDate(ASN1UTCTime aSN1UTCTime) {
        this.f = new Time((ASN1Primitive) aSN1UTCTime);
    }

    public void setEndDate(Time time) {
        this.f = time;
    }

    public void setExtensions(Extensions extensions) {
        this.i = extensions;
        if (extensions != null) {
            Extension extension = extensions.getExtension(Extension.subjectAlternativeName);
            if (extension != null && extension.isCritical()) {
                this.j = true;
            }
        }
    }

    public void setExtensions(X509Extensions x509Extensions) {
        setExtensions(Extensions.getInstance(x509Extensions));
    }

    public void setIssuer(X500Name x500Name) {
        this.d = x500Name;
    }

    public void setIssuer(X509Name x509Name) {
        this.d = X500Name.getInstance(x509Name);
    }

    public void setIssuerUniqueID(DERBitString dERBitString) {
        this.k = dERBitString;
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

    public void setSubjectUniqueID(DERBitString dERBitString) {
        this.l = dERBitString;
    }
}
