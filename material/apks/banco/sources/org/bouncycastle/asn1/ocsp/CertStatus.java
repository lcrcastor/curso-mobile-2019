package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERTaggedObject;

public class CertStatus extends ASN1Object implements ASN1Choice {
    private int a;
    private ASN1Encodable b;

    public CertStatus() {
        this.a = 0;
        this.b = DERNull.INSTANCE;
    }

    public CertStatus(int i, ASN1Encodable aSN1Encodable) {
        this.a = i;
        this.b = aSN1Encodable;
    }

    public CertStatus(ASN1TaggedObject aSN1TaggedObject) {
        ASN1Encodable aSN1Encodable;
        this.a = aSN1TaggedObject.getTagNo();
        switch (aSN1TaggedObject.getTagNo()) {
            case 0:
            case 2:
                aSN1Encodable = DERNull.INSTANCE;
                break;
            case 1:
                aSN1Encodable = RevokedInfo.getInstance(aSN1TaggedObject, false);
                break;
            default:
                return;
        }
        this.b = aSN1Encodable;
    }

    public CertStatus(RevokedInfo revokedInfo) {
        this.a = 1;
        this.b = revokedInfo;
    }

    public static CertStatus getInstance(Object obj) {
        if (obj == null || (obj instanceof CertStatus)) {
            return (CertStatus) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new CertStatus((ASN1TaggedObject) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object in factory: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static CertStatus getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public ASN1Encodable getStatus() {
        return this.b;
    }

    public int getTagNo() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.a, this.b);
    }
}
