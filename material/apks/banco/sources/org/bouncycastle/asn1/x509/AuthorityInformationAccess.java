package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class AuthorityInformationAccess extends ASN1Object {
    private AccessDescription[] a;

    public AuthorityInformationAccess(ASN1ObjectIdentifier aSN1ObjectIdentifier, GeneralName generalName) {
        this(new AccessDescription(aSN1ObjectIdentifier, generalName));
    }

    private AuthorityInformationAccess(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1) {
            throw new IllegalArgumentException("sequence may not be empty");
        }
        this.a = new AccessDescription[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.a[i] = AccessDescription.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public AuthorityInformationAccess(AccessDescription accessDescription) {
        this.a = new AccessDescription[]{accessDescription};
    }

    public static AuthorityInformationAccess getInstance(Object obj) {
        if (obj instanceof AuthorityInformationAccess) {
            return (AuthorityInformationAccess) obj;
        }
        if (obj != null) {
            return new AuthorityInformationAccess(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AccessDescription[] getAccessDescriptions() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.a.length; i++) {
            aSN1EncodableVector.add(this.a[i]);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthorityInformationAccess: Oid(");
        sb.append(this.a[0].getAccessMethod().getId());
        sb.append(")");
        return sb.toString();
    }
}
