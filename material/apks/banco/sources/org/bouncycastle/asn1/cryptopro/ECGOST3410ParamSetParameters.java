package org.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class ECGOST3410ParamSetParameters extends ASN1Object {
    ASN1Integer a;
    ASN1Integer b;
    ASN1Integer c;
    ASN1Integer d;
    ASN1Integer e;
    ASN1Integer f;

    public ECGOST3410ParamSetParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i, BigInteger bigInteger5) {
        this.c = new ASN1Integer(bigInteger);
        this.d = new ASN1Integer(bigInteger2);
        this.a = new ASN1Integer(bigInteger3);
        this.b = new ASN1Integer(bigInteger4);
        this.e = new ASN1Integer((long) i);
        this.f = new ASN1Integer(bigInteger5);
    }

    public ECGOST3410ParamSetParameters(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.c = (ASN1Integer) objects.nextElement();
        this.d = (ASN1Integer) objects.nextElement();
        this.a = (ASN1Integer) objects.nextElement();
        this.b = (ASN1Integer) objects.nextElement();
        this.e = (ASN1Integer) objects.nextElement();
        this.f = (ASN1Integer) objects.nextElement();
    }

    public static ECGOST3410ParamSetParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof ECGOST3410ParamSetParameters)) {
            return (ECGOST3410ParamSetParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new ECGOST3410ParamSetParameters((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid GOST3410Parameter: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static ECGOST3410ParamSetParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getA() {
        return this.c.getPositiveValue();
    }

    public BigInteger getP() {
        return this.a.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.b.getPositiveValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        return new DERSequence(aSN1EncodableVector);
    }
}
