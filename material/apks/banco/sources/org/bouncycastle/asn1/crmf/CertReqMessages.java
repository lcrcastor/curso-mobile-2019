package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CertReqMessages extends ASN1Object {
    private ASN1Sequence a;

    private CertReqMessages(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public CertReqMessages(CertReqMsg certReqMsg) {
        this.a = new DERSequence((ASN1Encodable) certReqMsg);
    }

    public CertReqMessages(CertReqMsg[] certReqMsgArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (CertReqMsg add : certReqMsgArr) {
            aSN1EncodableVector.add(add);
        }
        this.a = new DERSequence(aSN1EncodableVector);
    }

    public static CertReqMessages getInstance(Object obj) {
        if (obj instanceof CertReqMessages) {
            return (CertReqMessages) obj;
        }
        if (obj != null) {
            return new CertReqMessages(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public CertReqMsg[] toCertReqMsgArray() {
        CertReqMsg[] certReqMsgArr = new CertReqMsg[this.a.size()];
        for (int i = 0; i != certReqMsgArr.length; i++) {
            certReqMsgArr[i] = CertReqMsg.getInstance(this.a.getObjectAt(i));
        }
        return certReqMsgArr;
    }
}
