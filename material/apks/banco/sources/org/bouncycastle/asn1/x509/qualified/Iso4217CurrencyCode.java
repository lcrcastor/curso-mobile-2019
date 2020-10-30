package org.bouncycastle.asn1.x509.qualified;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERPrintableString;

public class Iso4217CurrencyCode extends ASN1Object implements ASN1Choice {
    final int a = 3;
    final int b = 1;
    final int c = 999;
    ASN1Encodable d;

    public Iso4217CurrencyCode(int i) {
        if (i > 999 || i < 1) {
            throw new IllegalArgumentException("wrong size in numeric code : not in (1..999)");
        }
        this.d = new ASN1Integer((long) i);
    }

    public Iso4217CurrencyCode(String str) {
        if (str.length() > 3) {
            throw new IllegalArgumentException("wrong size in alphabetic code : max size is 3");
        }
        this.d = new DERPrintableString(str);
    }

    public static Iso4217CurrencyCode getInstance(Object obj) {
        if (obj == null || (obj instanceof Iso4217CurrencyCode)) {
            return (Iso4217CurrencyCode) obj;
        }
        if (obj instanceof ASN1Integer) {
            return new Iso4217CurrencyCode(ASN1Integer.getInstance(obj).getValue().intValue());
        }
        if (obj instanceof DERPrintableString) {
            return new Iso4217CurrencyCode(DERPrintableString.getInstance(obj).getString());
        }
        throw new IllegalArgumentException("unknown object in getInstance");
    }

    public String getAlphabetic() {
        return ((DERPrintableString) this.d).getString();
    }

    public int getNumeric() {
        return ((ASN1Integer) this.d).getValue().intValue();
    }

    public boolean isAlphabetic() {
        return this.d instanceof DERPrintableString;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.d.toASN1Primitive();
    }
}
