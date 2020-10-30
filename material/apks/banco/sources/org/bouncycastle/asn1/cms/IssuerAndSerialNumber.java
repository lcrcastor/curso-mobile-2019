package org.bouncycastle.asn1.cms;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Name;

public class IssuerAndSerialNumber extends ASN1Object {
    private X500Name a;
    private ASN1Integer b;

    public IssuerAndSerialNumber(ASN1Sequence aSN1Sequence) {
        this.a = X500Name.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = (ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public IssuerAndSerialNumber(X500Name x500Name, BigInteger bigInteger) {
        this.a = x500Name;
        this.b = new ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(Certificate certificate) {
        this.a = certificate.getIssuer();
        this.b = certificate.getSerialNumber();
    }

    public IssuerAndSerialNumber(X509CertificateStructure x509CertificateStructure) {
        this.a = x509CertificateStructure.getIssuer();
        this.b = x509CertificateStructure.getSerialNumber();
    }

    public IssuerAndSerialNumber(X509Name x509Name, BigInteger bigInteger) {
        this.a = X500Name.getInstance(x509Name);
        this.b = new ASN1Integer(bigInteger);
    }

    public IssuerAndSerialNumber(X509Name x509Name, ASN1Integer aSN1Integer) {
        this.a = X500Name.getInstance(x509Name);
        this.b = aSN1Integer;
    }

    public static IssuerAndSerialNumber getInstance(Object obj) {
        if (obj instanceof IssuerAndSerialNumber) {
            return (IssuerAndSerialNumber) obj;
        }
        if (obj != null) {
            return new IssuerAndSerialNumber(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public X500Name getName() {
        return this.a;
    }

    public ASN1Integer getSerialNumber() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
