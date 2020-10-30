package org.bouncycastle.asn1.ocsp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class CrlID extends ASN1Object {
    private DERIA5String a;
    private ASN1Integer b;
    private ASN1GeneralizedTime c;

    private CrlID(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.a = DERIA5String.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.b = ASN1Integer.getInstance(aSN1TaggedObject, true);
                    break;
                case 2:
                    this.c = ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag number: ");
                    sb.append(aSN1TaggedObject.getTagNo());
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    public static CrlID getInstance(Object obj) {
        if (obj instanceof CrlID) {
            return (CrlID) obj;
        }
        if (obj != null) {
            return new CrlID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getCrlNum() {
        return this.b;
    }

    public ASN1GeneralizedTime getCrlTime() {
        return this.c;
    }

    public DERIA5String getCrlUrl() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.b));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
