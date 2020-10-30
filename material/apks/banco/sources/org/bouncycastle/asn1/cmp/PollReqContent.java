package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class PollReqContent extends ASN1Object {
    private ASN1Sequence a;

    public PollReqContent(ASN1Integer aSN1Integer) {
        this((ASN1Sequence) new DERSequence((ASN1Encodable) new DERSequence((ASN1Encodable) aSN1Integer)));
    }

    private PollReqContent(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    private static ASN1Integer[] a(ASN1Sequence aSN1Sequence) {
        ASN1Integer[] aSN1IntegerArr = new ASN1Integer[aSN1Sequence.size()];
        for (int i = 0; i != aSN1IntegerArr.length; i++) {
            aSN1IntegerArr[i] = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return aSN1IntegerArr;
    }

    public static PollReqContent getInstance(Object obj) {
        if (obj instanceof PollReqContent) {
            return (PollReqContent) obj;
        }
        if (obj != null) {
            return new PollReqContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer[][] getCertReqIds() {
        ASN1Integer[][] aSN1IntegerArr = new ASN1Integer[this.a.size()][];
        for (int i = 0; i != aSN1IntegerArr.length; i++) {
            aSN1IntegerArr[i] = a((ASN1Sequence) this.a.getObjectAt(i));
        }
        return aSN1IntegerArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
