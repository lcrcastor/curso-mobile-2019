package org.bouncycastle.asn1.pkcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;

public class CertificationRequestInfo extends ASN1Object {
    ASN1Integer a = new ASN1Integer(0);
    X500Name b;
    SubjectPublicKeyInfo c;
    ASN1Set d = null;

    public CertificationRequestInfo(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1Integer) aSN1Sequence.getObjectAt(0);
        this.b = X500Name.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 3) {
            this.d = ASN1Set.getInstance((DERTaggedObject) aSN1Sequence.getObjectAt(3), false);
        }
        if (this.b == null || this.a == null || this.c == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public CertificationRequestInfo(X500Name x500Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        this.b = x500Name;
        this.c = subjectPublicKeyInfo;
        this.d = aSN1Set;
        if (x500Name == null || this.a == null || this.c == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public CertificationRequestInfo(X509Name x509Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        this.b = X500Name.getInstance(x509Name.toASN1Primitive());
        this.c = subjectPublicKeyInfo;
        this.d = aSN1Set;
        if (x509Name == null || this.a == null || this.c == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public static CertificationRequestInfo getInstance(Object obj) {
        if (obj instanceof CertificationRequestInfo) {
            return (CertificationRequestInfo) obj;
        }
        if (obj != null) {
            return new CertificationRequestInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Set getAttributes() {
        return this.d;
    }

    public X500Name getSubject() {
        return this.b;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.c;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.d));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
