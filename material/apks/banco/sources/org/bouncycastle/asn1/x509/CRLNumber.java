package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

public class CRLNumber extends ASN1Object {
    private BigInteger a;

    public CRLNumber(BigInteger bigInteger) {
        this.a = bigInteger;
    }

    public static CRLNumber getInstance(Object obj) {
        if (obj instanceof CRLNumber) {
            return (CRLNumber) obj;
        }
        if (obj != null) {
            return new CRLNumber(ASN1Integer.getInstance(obj).getValue());
        }
        return null;
    }

    public BigInteger getCRLNumber() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CRLNumber: ");
        sb.append(getCRLNumber());
        return sb.toString();
    }
}
