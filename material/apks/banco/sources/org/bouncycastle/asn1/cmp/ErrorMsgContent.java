package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class ErrorMsgContent extends ASN1Object {
    private PKIStatusInfo a;
    private ASN1Integer b;
    private PKIFreeText c;

    private ErrorMsgContent(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = PKIStatusInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            Object nextElement = objects.nextElement();
            if (nextElement instanceof ASN1Integer) {
                this.b = ASN1Integer.getInstance(nextElement);
            } else {
                this.c = PKIFreeText.getInstance(nextElement);
            }
        }
    }

    public ErrorMsgContent(PKIStatusInfo pKIStatusInfo) {
        this(pKIStatusInfo, null, null);
    }

    public ErrorMsgContent(PKIStatusInfo pKIStatusInfo, ASN1Integer aSN1Integer, PKIFreeText pKIFreeText) {
        if (pKIStatusInfo == null) {
            throw new IllegalArgumentException("'pkiStatusInfo' cannot be null");
        }
        this.a = pKIStatusInfo;
        this.b = aSN1Integer;
        this.c = pKIFreeText;
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(aSN1Encodable);
        }
    }

    public static ErrorMsgContent getInstance(Object obj) {
        if (obj instanceof ErrorMsgContent) {
            return (ErrorMsgContent) obj;
        }
        if (obj != null) {
            return new ErrorMsgContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getErrorCode() {
        return this.b;
    }

    public PKIFreeText getErrorDetails() {
        return this.c;
    }

    public PKIStatusInfo getPKIStatusInfo() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        a(aSN1EncodableVector, this.b);
        a(aSN1EncodableVector, this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
