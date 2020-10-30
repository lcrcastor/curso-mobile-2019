package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class Evidence extends ASN1Object implements ASN1Choice {
    private TimeStampTokenEvidence a;

    private Evidence(ASN1TaggedObject aSN1TaggedObject) {
        if (aSN1TaggedObject.getTagNo() == 0) {
            this.a = TimeStampTokenEvidence.getInstance(aSN1TaggedObject, false);
        }
    }

    public Evidence(TimeStampTokenEvidence timeStampTokenEvidence) {
        this.a = timeStampTokenEvidence;
    }

    public static Evidence getInstance(Object obj) {
        if (obj == null || (obj instanceof Evidence)) {
            return (Evidence) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Evidence(ASN1TaggedObject.getInstance(obj));
        }
        throw new IllegalArgumentException("unknown object in getInstance");
    }

    public TimeStampTokenEvidence getTstEvidence() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.a != null) {
            return new DERTaggedObject(false, 0, this.a);
        }
        return null;
    }
}
