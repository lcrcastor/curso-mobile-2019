package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;

public class POPODecKeyRespContent extends ASN1Object {
    private ASN1Sequence a;

    private POPODecKeyRespContent(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public static POPODecKeyRespContent getInstance(Object obj) {
        if (obj instanceof POPODecKeyRespContent) {
            return (POPODecKeyRespContent) obj;
        }
        if (obj != null) {
            return new POPODecKeyRespContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer[] toASN1IntegerArray() {
        ASN1Integer[] aSN1IntegerArr = new ASN1Integer[this.a.size()];
        for (int i = 0; i != aSN1IntegerArr.length; i++) {
            aSN1IntegerArr[i] = ASN1Integer.getInstance(this.a.getObjectAt(i));
        }
        return aSN1IntegerArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
