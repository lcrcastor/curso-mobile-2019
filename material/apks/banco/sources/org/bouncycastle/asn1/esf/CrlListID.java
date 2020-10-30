package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CrlListID extends ASN1Object {
    private ASN1Sequence a;

    private CrlListID(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1Sequence) aSN1Sequence.getObjectAt(0);
        Enumeration objects = this.a.getObjects();
        while (objects.hasMoreElements()) {
            CrlValidatedID.getInstance(objects.nextElement());
        }
    }

    public CrlListID(CrlValidatedID[] crlValidatedIDArr) {
        this.a = new DERSequence((ASN1Encodable[]) crlValidatedIDArr);
    }

    public static CrlListID getInstance(Object obj) {
        if (obj instanceof CrlListID) {
            return (CrlListID) obj;
        }
        if (obj != null) {
            return new CrlListID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CrlValidatedID[] getCrls() {
        CrlValidatedID[] crlValidatedIDArr = new CrlValidatedID[this.a.size()];
        for (int i = 0; i < crlValidatedIDArr.length; i++) {
            crlValidatedIDArr[i] = CrlValidatedID.getInstance(this.a.getObjectAt(i));
        }
        return crlValidatedIDArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable) this.a);
    }
}
