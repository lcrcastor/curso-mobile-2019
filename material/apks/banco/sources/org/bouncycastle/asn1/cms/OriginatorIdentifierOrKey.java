package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;

public class OriginatorIdentifierOrKey extends ASN1Object implements ASN1Choice {
    private ASN1Encodable a;

    public OriginatorIdentifierOrKey(ASN1OctetString aSN1OctetString) {
        this(new SubjectKeyIdentifier(aSN1OctetString.getOctets()));
    }

    public OriginatorIdentifierOrKey(ASN1Primitive aSN1Primitive) {
        this.a = aSN1Primitive;
    }

    public OriginatorIdentifierOrKey(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.a = issuerAndSerialNumber;
    }

    public OriginatorIdentifierOrKey(OriginatorPublicKey originatorPublicKey) {
        this.a = new DERTaggedObject(false, 1, originatorPublicKey);
    }

    public OriginatorIdentifierOrKey(SubjectKeyIdentifier subjectKeyIdentifier) {
        this.a = new DERTaggedObject(false, 0, subjectKeyIdentifier);
    }

    public static OriginatorIdentifierOrKey getInstance(Object obj) {
        if (obj == null || (obj instanceof OriginatorIdentifierOrKey)) {
            return (OriginatorIdentifierOrKey) obj;
        }
        if (obj instanceof IssuerAndSerialNumber) {
            return new OriginatorIdentifierOrKey((IssuerAndSerialNumber) obj);
        }
        if (obj instanceof SubjectKeyIdentifier) {
            return new OriginatorIdentifierOrKey((SubjectKeyIdentifier) obj);
        }
        if (obj instanceof OriginatorPublicKey) {
            return new OriginatorIdentifierOrKey((OriginatorPublicKey) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            return new OriginatorIdentifierOrKey((ASN1Primitive) (ASN1TaggedObject) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid OriginatorIdentifierOrKey: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static OriginatorIdentifierOrKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return getInstance(aSN1TaggedObject.getObject());
        }
        throw new IllegalArgumentException("Can't implicitly tag OriginatorIdentifierOrKey");
    }

    public ASN1Encodable getId() {
        return this.a;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber() {
        if (this.a instanceof IssuerAndSerialNumber) {
            return (IssuerAndSerialNumber) this.a;
        }
        return null;
    }

    public OriginatorPublicKey getOriginatorKey() {
        if (!(this.a instanceof ASN1TaggedObject) || ((ASN1TaggedObject) this.a).getTagNo() != 1) {
            return null;
        }
        return OriginatorPublicKey.getInstance((ASN1TaggedObject) this.a, false);
    }

    public SubjectKeyIdentifier getSubjectKeyIdentifier() {
        if (!(this.a instanceof ASN1TaggedObject) || ((ASN1TaggedObject) this.a).getTagNo() != 0) {
            return null;
        }
        return SubjectKeyIdentifier.getInstance((ASN1TaggedObject) this.a, false);
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
