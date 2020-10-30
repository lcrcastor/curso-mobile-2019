package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CompleteRevocationRefs extends ASN1Object {
    private ASN1Sequence a;

    private CompleteRevocationRefs(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            CrlOcspRef.getInstance(objects.nextElement());
        }
        this.a = aSN1Sequence;
    }

    public CompleteRevocationRefs(CrlOcspRef[] crlOcspRefArr) {
        this.a = new DERSequence((ASN1Encodable[]) crlOcspRefArr);
    }

    public static CompleteRevocationRefs getInstance(Object obj) {
        if (obj instanceof CompleteRevocationRefs) {
            return (CompleteRevocationRefs) obj;
        }
        if (obj != null) {
            return new CompleteRevocationRefs(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CrlOcspRef[] getCrlOcspRefs() {
        CrlOcspRef[] crlOcspRefArr = new CrlOcspRef[this.a.size()];
        for (int i = 0; i < crlOcspRefArr.length; i++) {
            crlOcspRefArr[i] = CrlOcspRef.getInstance(this.a.getObjectAt(i));
        }
        return crlOcspRefArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
