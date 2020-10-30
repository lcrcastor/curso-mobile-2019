package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class CRLDistPoint extends ASN1Object {
    ASN1Sequence a = null;

    private CRLDistPoint(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public CRLDistPoint(DistributionPoint[] distributionPointArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != distributionPointArr.length; i++) {
            aSN1EncodableVector.add(distributionPointArr[i]);
        }
        this.a = new DERSequence(aSN1EncodableVector);
    }

    public static CRLDistPoint getInstance(Object obj) {
        if (obj instanceof CRLDistPoint) {
            return (CRLDistPoint) obj;
        }
        if (obj != null) {
            return new CRLDistPoint(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static CRLDistPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public DistributionPoint[] getDistributionPoints() {
        DistributionPoint[] distributionPointArr = new DistributionPoint[this.a.size()];
        for (int i = 0; i != this.a.size(); i++) {
            distributionPointArr[i] = DistributionPoint.getInstance(this.a.getObjectAt(i));
        }
        return distributionPointArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("CRLDistPoint:");
        stringBuffer.append(property);
        DistributionPoint[] distributionPoints = getDistributionPoints();
        for (int i = 0; i != distributionPoints.length; i++) {
            stringBuffer.append("    ");
            stringBuffer.append(distributionPoints[i]);
            stringBuffer.append(property);
        }
        return stringBuffer.toString();
    }
}
