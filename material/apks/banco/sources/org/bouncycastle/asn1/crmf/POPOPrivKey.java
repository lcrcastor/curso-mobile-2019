package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.EnvelopedData;

public class POPOPrivKey extends ASN1Object implements ASN1Choice {
    public static final int agreeMAC = 3;
    public static final int dhMAC = 2;
    public static final int encryptedKey = 4;
    public static final int subsequentMessage = 1;
    public static final int thisMessage = 0;
    private int a;
    private ASN1Encodable b;

    private POPOPrivKey(ASN1TaggedObject aSN1TaggedObject) {
        ASN1Encodable aSN1Encodable;
        this.a = aSN1TaggedObject.getTagNo();
        switch (this.a) {
            case 0:
            case 2:
                aSN1Encodable = DERBitString.getInstance(aSN1TaggedObject, false);
                break;
            case 1:
                aSN1Encodable = SubsequentMessage.valueOf(ASN1Integer.getInstance(aSN1TaggedObject, false).getValue().intValue());
                break;
            case 3:
                aSN1Encodable = PKMACValue.getInstance(aSN1TaggedObject, false);
                break;
            case 4:
                aSN1Encodable = EnvelopedData.getInstance(aSN1TaggedObject, false);
                break;
            default:
                throw new IllegalArgumentException("unknown tag in POPOPrivKey");
        }
        this.b = aSN1Encodable;
    }

    public POPOPrivKey(SubsequentMessage subsequentMessage2) {
        this.a = 1;
        this.b = subsequentMessage2;
    }

    public static POPOPrivKey getInstance(Object obj) {
        if (obj instanceof POPOPrivKey) {
            return (POPOPrivKey) obj;
        }
        if (obj != null) {
            return new POPOPrivKey(ASN1TaggedObject.getInstance(obj));
        }
        return null;
    }

    public static POPOPrivKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1TaggedObject.getInstance(aSN1TaggedObject, z));
    }

    public int getType() {
        return this.a;
    }

    public ASN1Encodable getValue() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.a, this.b);
    }
}
