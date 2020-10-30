package org.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class CertReqMsg extends ASN1Object {
    private CertRequest a;
    private ProofOfPossession b;
    private ASN1Sequence c;

    private CertReqMsg(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = CertRequest.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            Object nextElement = objects.nextElement();
            if ((nextElement instanceof ASN1TaggedObject) || (nextElement instanceof ProofOfPossession)) {
                this.b = ProofOfPossession.getInstance(nextElement);
            } else {
                this.c = ASN1Sequence.getInstance(nextElement);
            }
        }
    }

    public CertReqMsg(CertRequest certRequest, ProofOfPossession proofOfPossession, AttributeTypeAndValue[] attributeTypeAndValueArr) {
        if (certRequest == null) {
            throw new IllegalArgumentException("'certReq' cannot be null");
        }
        this.a = certRequest;
        this.b = proofOfPossession;
        if (attributeTypeAndValueArr != null) {
            this.c = new DERSequence((ASN1Encodable[]) attributeTypeAndValueArr);
        }
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(aSN1Encodable);
        }
    }

    public static CertReqMsg getInstance(Object obj) {
        if (obj instanceof CertReqMsg) {
            return (CertReqMsg) obj;
        }
        if (obj != null) {
            return new CertReqMsg(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertRequest getCertReq() {
        return this.a;
    }

    public ProofOfPossession getPop() {
        return this.b;
    }

    public ProofOfPossession getPopo() {
        return this.b;
    }

    public AttributeTypeAndValue[] getRegInfo() {
        if (this.c == null) {
            return null;
        }
        AttributeTypeAndValue[] attributeTypeAndValueArr = new AttributeTypeAndValue[this.c.size()];
        for (int i = 0; i != attributeTypeAndValueArr.length; i++) {
            attributeTypeAndValueArr[i] = AttributeTypeAndValue.getInstance(this.c.getObjectAt(i));
        }
        return attributeTypeAndValueArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        a(aSN1EncodableVector, this.b);
        a(aSN1EncodableVector, this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
