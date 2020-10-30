package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class SignerIdentifier extends ASN1Object implements ASN1Choice {
    private ASN1Encodable a;

    public SignerIdentifier(ASN1OctetString aSN1OctetString) {
        this.a = new DERTaggedObject(false, 0, aSN1OctetString);
    }

    public SignerIdentifier(ASN1Primitive aSN1Primitive) {
        this.a = aSN1Primitive;
    }

    public SignerIdentifier(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.a = issuerAndSerialNumber;
    }

    public static SignerIdentifier getInstance(Object obj) {
        if (obj == null || (obj instanceof SignerIdentifier)) {
            return (SignerIdentifier) obj;
        }
        if (obj instanceof IssuerAndSerialNumber) {
            return new SignerIdentifier((IssuerAndSerialNumber) obj);
        }
        if (obj instanceof ASN1OctetString) {
            return new SignerIdentifier((ASN1OctetString) obj);
        }
        if (obj instanceof ASN1Primitive) {
            return new SignerIdentifier((ASN1Primitive) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Illegal object in SignerIdentifier: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public ASN1Encodable getId() {
        return this.a instanceof ASN1TaggedObject ? ASN1OctetString.getInstance((ASN1TaggedObject) this.a, false) : this.a;
    }

    public boolean isTagged() {
        return this.a instanceof ASN1TaggedObject;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
