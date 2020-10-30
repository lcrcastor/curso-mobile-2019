package org.bouncycastle.asn1.x500;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class X500Name extends ASN1Object implements ASN1Choice {
    private static X500NameStyle a = BCStyle.INSTANCE;
    private boolean b;
    private int c;
    private X500NameStyle d;
    private RDN[] e;

    public X500Name(String str) {
        this(a, str);
    }

    private X500Name(ASN1Sequence aSN1Sequence) {
        this(a, aSN1Sequence);
    }

    public X500Name(X500NameStyle x500NameStyle, String str) {
        this(x500NameStyle.fromString(str));
        this.d = x500NameStyle;
    }

    private X500Name(X500NameStyle x500NameStyle, ASN1Sequence aSN1Sequence) {
        this.d = x500NameStyle;
        this.e = new RDN[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            this.e[i] = RDN.getInstance(objects.nextElement());
            i = i2;
        }
    }

    public X500Name(X500NameStyle x500NameStyle, X500Name x500Name) {
        this.e = x500Name.e;
        this.d = x500NameStyle;
    }

    public X500Name(X500NameStyle x500NameStyle, RDN[] rdnArr) {
        this.e = rdnArr;
        this.d = x500NameStyle;
    }

    public X500Name(RDN[] rdnArr) {
        this(a, rdnArr);
    }

    public static X500NameStyle getDefaultStyle() {
        return a;
    }

    public static X500Name getInstance(Object obj) {
        if (obj instanceof X500Name) {
            return (X500Name) obj;
        }
        if (obj != null) {
            return new X500Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X500Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static X500Name getInstance(X500NameStyle x500NameStyle, Object obj) {
        if (obj instanceof X500Name) {
            return getInstance(x500NameStyle, (Object) ((X500Name) obj).toASN1Primitive());
        }
        if (obj != null) {
            return new X500Name(x500NameStyle, ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static void setDefaultStyle(X500NameStyle x500NameStyle) {
        if (x500NameStyle == null) {
            throw new NullPointerException("cannot set style to null");
        }
        a = x500NameStyle;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X500Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.d.areEqual(this, new X500Name(ASN1Sequence.getInstance(((ASN1Encodable) obj).toASN1Primitive())));
        } catch (Exception unused) {
            return false;
        }
    }

    public ASN1ObjectIdentifier[] getAttributeTypes() {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 != this.e.length; i3++) {
            i2 += this.e[i3].size();
        }
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[i2];
        int i4 = 0;
        for (int i5 = 0; i5 != this.e.length; i5++) {
            RDN rdn = this.e[i5];
            if (rdn.isMultiValued()) {
                AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
                i = i4;
                int i6 = 0;
                while (i6 != typesAndValues.length) {
                    int i7 = i + 1;
                    aSN1ObjectIdentifierArr[i] = typesAndValues[i6].getType();
                    i6++;
                    i = i7;
                }
            } else if (rdn.size() != 0) {
                i = i4 + 1;
                aSN1ObjectIdentifierArr[i4] = rdn.getFirst().getType();
            }
            i4 = i;
        }
        return aSN1ObjectIdentifierArr;
    }

    public RDN[] getRDNs() {
        RDN[] rdnArr = new RDN[this.e.length];
        System.arraycopy(this.e, 0, rdnArr, 0, rdnArr.length);
        return rdnArr;
    }

    public RDN[] getRDNs(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int i;
        RDN[] rdnArr = new RDN[this.e.length];
        int i2 = 0;
        for (int i3 = 0; i3 != this.e.length; i3++) {
            RDN rdn = this.e[i3];
            if (rdn.isMultiValued()) {
                AttributeTypeAndValue[] typesAndValues = rdn.getTypesAndValues();
                int i4 = 0;
                while (true) {
                    if (i4 == typesAndValues.length) {
                        break;
                    } else if (typesAndValues[i4].getType().equals(aSN1ObjectIdentifier)) {
                        i = i2 + 1;
                        rdnArr[i2] = rdn;
                        break;
                    } else {
                        i4++;
                    }
                }
            } else if (rdn.getFirst().getType().equals(aSN1ObjectIdentifier)) {
                i = i2 + 1;
                rdnArr[i2] = rdn;
            }
            i2 = i;
        }
        RDN[] rdnArr2 = new RDN[i2];
        System.arraycopy(rdnArr, 0, rdnArr2, 0, rdnArr2.length);
        return rdnArr2;
    }

    public int hashCode() {
        if (this.b) {
            return this.c;
        }
        this.b = true;
        this.c = this.d.calculateHashCode(this);
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.e);
    }

    public String toString() {
        return this.d.toString(this);
    }
}
