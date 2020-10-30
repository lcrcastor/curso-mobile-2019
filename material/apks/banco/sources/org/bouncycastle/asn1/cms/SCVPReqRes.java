package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class SCVPReqRes extends ASN1Object {
    private final ContentInfo a;
    private final ContentInfo b;

    private SCVPReqRes(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            this.a = ContentInfo.getInstance(ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(0)), true);
            objectAt = aSN1Sequence.getObjectAt(1);
        } else {
            this.a = null;
            objectAt = aSN1Sequence.getObjectAt(0);
        }
        this.b = ContentInfo.getInstance(objectAt);
    }

    public SCVPReqRes(ContentInfo contentInfo) {
        this.a = null;
        this.b = contentInfo;
    }

    public SCVPReqRes(ContentInfo contentInfo, ContentInfo contentInfo2) {
        this.a = contentInfo;
        this.b = contentInfo2;
    }

    public static SCVPReqRes getInstance(Object obj) {
        if (obj instanceof SCVPReqRes) {
            return (SCVPReqRes) obj;
        }
        if (obj != null) {
            return new SCVPReqRes(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ContentInfo getRequest() {
        return this.a;
    }

    public ContentInfo getResponse() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a));
        }
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
