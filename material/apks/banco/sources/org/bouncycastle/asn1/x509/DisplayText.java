package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERVisibleString;

public class DisplayText extends ASN1Object implements ASN1Choice {
    public static final int CONTENT_TYPE_BMPSTRING = 1;
    public static final int CONTENT_TYPE_IA5STRING = 0;
    public static final int CONTENT_TYPE_UTF8STRING = 2;
    public static final int CONTENT_TYPE_VISIBLESTRING = 3;
    public static final int DISPLAY_TEXT_MAXIMUM_SIZE = 200;
    int a;
    ASN1String b;

    public DisplayText(int i, String str) {
        ASN1String dERIA5String;
        if (str.length() > 200) {
            str = str.substring(0, 200);
        }
        this.a = i;
        switch (i) {
            case 0:
                dERIA5String = new DERIA5String(str);
                break;
            case 1:
                dERIA5String = new DERBMPString(str);
                break;
            case 2:
                dERIA5String = new DERUTF8String(str);
                break;
            case 3:
                dERIA5String = new DERVisibleString(str);
                break;
            default:
                dERIA5String = new DERUTF8String(str);
                break;
        }
        this.b = dERIA5String;
    }

    public DisplayText(String str) {
        if (str.length() > 200) {
            str = str.substring(0, 200);
        }
        this.a = 2;
        this.b = new DERUTF8String(str);
    }

    private DisplayText(ASN1String aSN1String) {
        this.b = aSN1String;
    }

    public static DisplayText getInstance(Object obj) {
        if (obj instanceof ASN1String) {
            return new DisplayText((ASN1String) obj);
        }
        if (obj == null || (obj instanceof DisplayText)) {
            return (DisplayText) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static DisplayText getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public String getString() {
        return this.b.getString();
    }

    public ASN1Primitive toASN1Primitive() {
        return (ASN1Primitive) this.b;
    }
}
