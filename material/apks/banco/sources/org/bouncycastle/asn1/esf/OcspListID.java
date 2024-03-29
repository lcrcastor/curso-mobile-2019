package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class OcspListID extends ASN1Object {
    private ASN1Sequence a;

    private OcspListID(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = (ASN1Sequence) aSN1Sequence.getObjectAt(0);
        Enumeration objects = this.a.getObjects();
        while (objects.hasMoreElements()) {
            OcspResponsesID.getInstance(objects.nextElement());
        }
    }

    public OcspListID(OcspResponsesID[] ocspResponsesIDArr) {
        this.a = new DERSequence((ASN1Encodable[]) ocspResponsesIDArr);
    }

    public static OcspListID getInstance(Object obj) {
        if (obj instanceof OcspListID) {
            return (OcspListID) obj;
        }
        if (obj != null) {
            return new OcspListID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public OcspResponsesID[] getOcspResponses() {
        OcspResponsesID[] ocspResponsesIDArr = new OcspResponsesID[this.a.size()];
        for (int i = 0; i < ocspResponsesIDArr.length; i++) {
            ocspResponsesIDArr[i] = OcspResponsesID.getInstance(this.a.getObjectAt(i));
        }
        return ocspResponsesIDArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable) this.a);
    }
}
