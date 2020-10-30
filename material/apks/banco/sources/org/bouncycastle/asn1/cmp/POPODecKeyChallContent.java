package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

public class POPODecKeyChallContent extends ASN1Object {
    private ASN1Sequence a;

    private POPODecKeyChallContent(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public static POPODecKeyChallContent getInstance(Object obj) {
        if (obj instanceof POPODecKeyChallContent) {
            return (POPODecKeyChallContent) obj;
        }
        if (obj != null) {
            return new POPODecKeyChallContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public Challenge[] toChallengeArray() {
        Challenge[] challengeArr = new Challenge[this.a.size()];
        for (int i = 0; i != challengeArr.length; i++) {
            challengeArr[i] = Challenge.getInstance(this.a.getObjectAt(i));
        }
        return challengeArr;
    }
}
