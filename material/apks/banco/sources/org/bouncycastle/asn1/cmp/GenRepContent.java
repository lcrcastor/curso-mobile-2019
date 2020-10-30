package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class GenRepContent extends ASN1Object {
    private ASN1Sequence a;

    private GenRepContent(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public GenRepContent(InfoTypeAndValue infoTypeAndValue) {
        this.a = new DERSequence((ASN1Encodable) infoTypeAndValue);
    }

    public GenRepContent(InfoTypeAndValue[] infoTypeAndValueArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (InfoTypeAndValue add : infoTypeAndValueArr) {
            aSN1EncodableVector.add(add);
        }
        this.a = new DERSequence(aSN1EncodableVector);
    }

    public static GenRepContent getInstance(Object obj) {
        if (obj instanceof GenRepContent) {
            return (GenRepContent) obj;
        }
        if (obj != null) {
            return new GenRepContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public InfoTypeAndValue[] toInfoTypeAndValueArray() {
        InfoTypeAndValue[] infoTypeAndValueArr = new InfoTypeAndValue[this.a.size()];
        for (int i = 0; i != infoTypeAndValueArr.length; i++) {
            infoTypeAndValueArr[i] = InfoTypeAndValue.getInstance(this.a.getObjectAt(i));
        }
        return infoTypeAndValueArr;
    }
}
