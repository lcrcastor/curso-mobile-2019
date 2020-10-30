package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extensions;

public class CertTemplateBuilder {
    private ASN1Integer a;
    private ASN1Integer b;
    private AlgorithmIdentifier c;
    private X500Name d;
    private OptionalValidity e;
    private X500Name f;
    private SubjectPublicKeyInfo g;
    private DERBitString h;
    private DERBitString i;
    private Extensions j;

    private void a(ASN1EncodableVector aSN1EncodableVector, int i2, boolean z, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(z, i2, aSN1Encodable));
        }
    }

    public CertTemplate build() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        a(aSN1EncodableVector, 0, false, this.a);
        a(aSN1EncodableVector, 1, false, this.b);
        a(aSN1EncodableVector, 2, false, this.c);
        a(aSN1EncodableVector, 3, true, this.d);
        a(aSN1EncodableVector, 4, false, this.e);
        a(aSN1EncodableVector, 5, true, this.f);
        a(aSN1EncodableVector, 6, false, this.g);
        a(aSN1EncodableVector, 7, false, this.h);
        a(aSN1EncodableVector, 8, false, this.i);
        a(aSN1EncodableVector, 9, false, this.j);
        return CertTemplate.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public CertTemplateBuilder setExtensions(Extensions extensions) {
        this.j = extensions;
        return this;
    }

    public CertTemplateBuilder setExtensions(X509Extensions x509Extensions) {
        return setExtensions(Extensions.getInstance(x509Extensions));
    }

    public CertTemplateBuilder setIssuer(X500Name x500Name) {
        this.d = x500Name;
        return this;
    }

    public CertTemplateBuilder setIssuerUID(DERBitString dERBitString) {
        this.h = dERBitString;
        return this;
    }

    public CertTemplateBuilder setPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.g = subjectPublicKeyInfo;
        return this;
    }

    public CertTemplateBuilder setSerialNumber(ASN1Integer aSN1Integer) {
        this.b = aSN1Integer;
        return this;
    }

    public CertTemplateBuilder setSigningAlg(AlgorithmIdentifier algorithmIdentifier) {
        this.c = algorithmIdentifier;
        return this;
    }

    public CertTemplateBuilder setSubject(X500Name x500Name) {
        this.f = x500Name;
        return this;
    }

    public CertTemplateBuilder setSubjectUID(DERBitString dERBitString) {
        this.i = dERBitString;
        return this;
    }

    public CertTemplateBuilder setValidity(OptionalValidity optionalValidity) {
        this.e = optionalValidity;
        return this;
    }

    public CertTemplateBuilder setVersion(int i2) {
        this.a = new ASN1Integer((long) i2);
        return this;
    }
}
