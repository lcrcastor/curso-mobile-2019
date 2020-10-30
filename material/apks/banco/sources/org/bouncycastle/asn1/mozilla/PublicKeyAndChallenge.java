package org.bouncycastle.asn1.mozilla;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class PublicKeyAndChallenge extends ASN1Object {
    private ASN1Sequence a;
    private SubjectPublicKeyInfo b;
    private DERIA5String c;

    private PublicKeyAndChallenge(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
        this.b = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.c = DERIA5String.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static PublicKeyAndChallenge getInstance(Object obj) {
        if (obj instanceof PublicKeyAndChallenge) {
            return (PublicKeyAndChallenge) obj;
        }
        if (obj != null) {
            return new PublicKeyAndChallenge(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERIA5String getChallenge() {
        return this.c;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
