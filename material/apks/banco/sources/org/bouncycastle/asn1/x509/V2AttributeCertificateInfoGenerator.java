package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;

public class V2AttributeCertificateInfoGenerator {
    private ASN1Integer a = new ASN1Integer(1);
    private Holder b;
    private AttCertIssuer c;
    private AlgorithmIdentifier d;
    private ASN1Integer e;
    private ASN1EncodableVector f = new ASN1EncodableVector();
    private DERBitString g;
    private Extensions h;
    private ASN1GeneralizedTime i;
    private ASN1GeneralizedTime j;

    public void addAttribute(String str, ASN1Encodable aSN1Encodable) {
        this.f.add(new Attribute(new ASN1ObjectIdentifier(str), new DERSet(aSN1Encodable)));
    }

    public void addAttribute(Attribute attribute) {
        this.f.add(attribute);
    }

    public AttributeCertificateInfo generateAttributeCertificateInfo() {
        if (this.e == null || this.d == null || this.c == null || this.i == null || this.j == null || this.b == null || this.f == null) {
            throw new IllegalStateException("not all mandatory fields set in V2 AttributeCertificateInfo generator");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(new AttCertValidityPeriod(this.i, this.j));
        aSN1EncodableVector.add(new DERSequence(this.f));
        if (this.g != null) {
            aSN1EncodableVector.add(this.g);
        }
        if (this.h != null) {
            aSN1EncodableVector.add(this.h);
        }
        return AttributeCertificateInfo.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public void setEndDate(ASN1GeneralizedTime aSN1GeneralizedTime) {
        this.j = aSN1GeneralizedTime;
    }

    public void setExtensions(Extensions extensions) {
        this.h = extensions;
    }

    public void setExtensions(X509Extensions x509Extensions) {
        this.h = Extensions.getInstance(x509Extensions.toASN1Primitive());
    }

    public void setHolder(Holder holder) {
        this.b = holder;
    }

    public void setIssuer(AttCertIssuer attCertIssuer) {
        this.c = attCertIssuer;
    }

    public void setIssuerUniqueID(DERBitString dERBitString) {
        this.g = dERBitString;
    }

    public void setSerialNumber(ASN1Integer aSN1Integer) {
        this.e = aSN1Integer;
    }

    public void setSignature(AlgorithmIdentifier algorithmIdentifier) {
        this.d = algorithmIdentifier;
    }

    public void setStartDate(ASN1GeneralizedTime aSN1GeneralizedTime) {
        this.i = aSN1GeneralizedTime;
    }
}
