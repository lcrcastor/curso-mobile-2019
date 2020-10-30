package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class RecipientInfo extends ASN1Object implements ASN1Choice {
    ASN1Encodable a;

    public RecipientInfo(ASN1Primitive aSN1Primitive) {
        this.a = aSN1Primitive;
    }

    public RecipientInfo(KEKRecipientInfo kEKRecipientInfo) {
        this.a = new DERTaggedObject(false, 2, kEKRecipientInfo);
    }

    public RecipientInfo(KeyAgreeRecipientInfo keyAgreeRecipientInfo) {
        this.a = new DERTaggedObject(false, 1, keyAgreeRecipientInfo);
    }

    public RecipientInfo(KeyTransRecipientInfo keyTransRecipientInfo) {
        this.a = keyTransRecipientInfo;
    }

    public RecipientInfo(OtherRecipientInfo otherRecipientInfo) {
        this.a = new DERTaggedObject(false, 4, otherRecipientInfo);
    }

    public RecipientInfo(PasswordRecipientInfo passwordRecipientInfo) {
        this.a = new DERTaggedObject(false, 3, passwordRecipientInfo);
    }

    private KEKRecipientInfo a(ASN1TaggedObject aSN1TaggedObject) {
        return KEKRecipientInfo.getInstance(aSN1TaggedObject, aSN1TaggedObject.isExplicit());
    }

    public static RecipientInfo getInstance(Object obj) {
        if (obj == null || (obj instanceof RecipientInfo)) {
            return (RecipientInfo) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new RecipientInfo((ASN1Primitive) (ASN1Sequence) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            return new RecipientInfo((ASN1Primitive) (ASN1TaggedObject) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object in factory: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public ASN1Encodable getInfo() {
        if (!(this.a instanceof ASN1TaggedObject)) {
            return KeyTransRecipientInfo.getInstance(this.a);
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) this.a;
        switch (aSN1TaggedObject.getTagNo()) {
            case 1:
                return KeyAgreeRecipientInfo.getInstance(aSN1TaggedObject, false);
            case 2:
                return a(aSN1TaggedObject);
            case 3:
                return PasswordRecipientInfo.getInstance(aSN1TaggedObject, false);
            case 4:
                return OtherRecipientInfo.getInstance(aSN1TaggedObject, false);
            default:
                throw new IllegalStateException("unknown tag");
        }
    }

    public ASN1Integer getVersion() {
        if (!(this.a instanceof ASN1TaggedObject)) {
            return KeyTransRecipientInfo.getInstance(this.a).getVersion();
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) this.a;
        switch (aSN1TaggedObject.getTagNo()) {
            case 1:
                return KeyAgreeRecipientInfo.getInstance(aSN1TaggedObject, false).getVersion();
            case 2:
                return a(aSN1TaggedObject).getVersion();
            case 3:
                return PasswordRecipientInfo.getInstance(aSN1TaggedObject, false).getVersion();
            case 4:
                return new ASN1Integer(0);
            default:
                throw new IllegalStateException("unknown tag");
        }
    }

    public boolean isTagged() {
        return this.a instanceof ASN1TaggedObject;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
