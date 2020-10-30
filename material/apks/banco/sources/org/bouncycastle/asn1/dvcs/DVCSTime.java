package org.bouncycastle.asn1.dvcs;

import java.util.Date;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.cms.ContentInfo;

public class DVCSTime extends ASN1Object implements ASN1Choice {
    private ASN1GeneralizedTime a;
    private ContentInfo b;

    public DVCSTime(Date date) {
        this(new ASN1GeneralizedTime(date));
    }

    public DVCSTime(ASN1GeneralizedTime aSN1GeneralizedTime) {
        this.a = aSN1GeneralizedTime;
    }

    public DVCSTime(ContentInfo contentInfo) {
        this.b = contentInfo;
    }

    public static DVCSTime getInstance(Object obj) {
        if (obj instanceof DVCSTime) {
            return (DVCSTime) obj;
        }
        if (obj instanceof ASN1GeneralizedTime) {
            return new DVCSTime(ASN1GeneralizedTime.getInstance(obj));
        }
        if (obj != null) {
            return new DVCSTime(ContentInfo.getInstance(obj));
        }
        return null;
    }

    public static DVCSTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public ASN1GeneralizedTime getGenTime() {
        return this.a;
    }

    public ContentInfo getTimeStampToken() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.a != null) {
            return this.a;
        }
        if (this.b != null) {
            return this.b.toASN1Primitive();
        }
        return null;
    }

    public String toString() {
        Object obj;
        if (this.a != null) {
            obj = this.a;
        } else if (this.b == null) {
            return null;
        } else {
            obj = this.b;
        }
        return obj.toString();
    }
}
