package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import org.bouncycastle.asn1.x509.CertificateList;

public class RevocationValues extends ASN1Object {
    private ASN1Sequence a;
    private ASN1Sequence b;
    private OtherRevVals c;

    private RevocationValues(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            DERTaggedObject dERTaggedObject = (DERTaggedObject) objects.nextElement();
            switch (dERTaggedObject.getTagNo()) {
                case 0:
                    ASN1Sequence aSN1Sequence2 = (ASN1Sequence) dERTaggedObject.getObject();
                    Enumeration objects2 = aSN1Sequence2.getObjects();
                    while (objects2.hasMoreElements()) {
                        CertificateList.getInstance(objects2.nextElement());
                    }
                    this.a = aSN1Sequence2;
                    break;
                case 1:
                    ASN1Sequence aSN1Sequence3 = (ASN1Sequence) dERTaggedObject.getObject();
                    Enumeration objects3 = aSN1Sequence3.getObjects();
                    while (objects3.hasMoreElements()) {
                        BasicOCSPResponse.getInstance(objects3.nextElement());
                    }
                    this.b = aSN1Sequence3;
                    break;
                case 2:
                    this.c = OtherRevVals.getInstance(dERTaggedObject.getObject());
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("invalid tag: ");
                    sb2.append(dERTaggedObject.getTagNo());
                    throw new IllegalArgumentException(sb2.toString());
            }
        }
    }

    public RevocationValues(CertificateList[] certificateListArr, BasicOCSPResponse[] basicOCSPResponseArr, OtherRevVals otherRevVals) {
        if (certificateListArr != null) {
            this.a = new DERSequence((ASN1Encodable[]) certificateListArr);
        }
        if (basicOCSPResponseArr != null) {
            this.b = new DERSequence((ASN1Encodable[]) basicOCSPResponseArr);
        }
        this.c = otherRevVals;
    }

    public static RevocationValues getInstance(Object obj) {
        if (obj instanceof RevocationValues) {
            return (RevocationValues) obj;
        }
        if (obj != null) {
            return new RevocationValues(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificateList[] getCrlVals() {
        if (this.a == null) {
            return new CertificateList[0];
        }
        CertificateList[] certificateListArr = new CertificateList[this.a.size()];
        for (int i = 0; i < certificateListArr.length; i++) {
            certificateListArr[i] = CertificateList.getInstance(this.a.getObjectAt(i));
        }
        return certificateListArr;
    }

    public BasicOCSPResponse[] getOcspVals() {
        if (this.b == null) {
            return new BasicOCSPResponse[0];
        }
        BasicOCSPResponse[] basicOCSPResponseArr = new BasicOCSPResponse[this.b.size()];
        for (int i = 0; i < basicOCSPResponseArr.length; i++) {
            basicOCSPResponseArr[i] = BasicOCSPResponse.getInstance(this.b.getObjectAt(i));
        }
        return basicOCSPResponseArr;
    }

    public OtherRevVals getOtherRevVals() {
        return this.c;
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
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c.toASN1Primitive()));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
