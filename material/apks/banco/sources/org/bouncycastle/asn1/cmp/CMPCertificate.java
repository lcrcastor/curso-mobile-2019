package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import org.bouncycastle.asn1.x509.Certificate;

public class CMPCertificate extends ASN1Object implements ASN1Choice {
    private Certificate a;
    private AttributeCertificate b;

    public CMPCertificate(AttributeCertificate attributeCertificate) {
        this.b = attributeCertificate;
    }

    public CMPCertificate(Certificate certificate) {
        if (certificate.getVersionNumber() != 3) {
            throw new IllegalArgumentException("only version 3 certificates allowed");
        }
        this.a = certificate;
    }

    public static CMPCertificate getInstance(Object obj) {
        if (obj == null || (obj instanceof CMPCertificate)) {
            return (CMPCertificate) obj;
        }
        if ((obj instanceof ASN1Sequence) || (obj instanceof byte[])) {
            return new CMPCertificate(Certificate.getInstance(obj));
        }
        if (obj instanceof ASN1TaggedObject) {
            return new CMPCertificate(AttributeCertificate.getInstance(((ASN1TaggedObject) obj).getObject()));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid object: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public AttributeCertificate getX509v2AttrCert() {
        return this.b;
    }

    public Certificate getX509v3PKCert() {
        return this.a;
    }

    public boolean isX509v3PKCert() {
        return this.a != null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b != null ? new DERTaggedObject(true, 1, this.b) : this.a.toASN1Primitive();
    }
}
