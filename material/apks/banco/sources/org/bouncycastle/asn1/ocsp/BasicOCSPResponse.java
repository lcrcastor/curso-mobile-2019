package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class BasicOCSPResponse extends ASN1Object {
    private ResponseData a;
    private AlgorithmIdentifier b;
    private DERBitString c;
    private ASN1Sequence d;

    private BasicOCSPResponse(ASN1Sequence aSN1Sequence) {
        this.a = ResponseData.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = (DERBitString) aSN1Sequence.getObjectAt(2);
        if (aSN1Sequence.size() > 3) {
            this.d = ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(3), true);
        }
    }

    public BasicOCSPResponse(ResponseData responseData, AlgorithmIdentifier algorithmIdentifier, DERBitString dERBitString, ASN1Sequence aSN1Sequence) {
        this.a = responseData;
        this.b = algorithmIdentifier;
        this.c = dERBitString;
        this.d = aSN1Sequence;
    }

    public static BasicOCSPResponse getInstance(Object obj) {
        if (obj instanceof BasicOCSPResponse) {
            return (BasicOCSPResponse) obj;
        }
        if (obj != null) {
            return new BasicOCSPResponse(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static BasicOCSPResponse getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1Sequence getCerts() {
        return this.d;
    }

    public DERBitString getSignature() {
        return this.c;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.b;
    }

    public ResponseData getTbsResponseData() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.d));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
