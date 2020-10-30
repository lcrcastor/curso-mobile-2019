package org.bouncycastle.asn1.x509.qualified;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class MonetaryValue extends ASN1Object {
    private Iso4217CurrencyCode a;
    private ASN1Integer b;
    private ASN1Integer c;

    private MonetaryValue(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = Iso4217CurrencyCode.getInstance(objects.nextElement());
        this.b = ASN1Integer.getInstance(objects.nextElement());
        this.c = ASN1Integer.getInstance(objects.nextElement());
    }

    public MonetaryValue(Iso4217CurrencyCode iso4217CurrencyCode, int i, int i2) {
        this.a = iso4217CurrencyCode;
        this.b = new ASN1Integer((long) i);
        this.c = new ASN1Integer((long) i2);
    }

    public static MonetaryValue getInstance(Object obj) {
        if (obj instanceof MonetaryValue) {
            return (MonetaryValue) obj;
        }
        if (obj != null) {
            return new MonetaryValue(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getAmount() {
        return this.b.getValue();
    }

    public Iso4217CurrencyCode getCurrency() {
        return this.a;
    }

    public BigInteger getExponent() {
        return this.c.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
