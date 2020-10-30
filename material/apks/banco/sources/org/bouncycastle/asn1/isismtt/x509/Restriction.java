package org.bouncycastle.asn1.isismtt.x509;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.DirectoryString;

public class Restriction extends ASN1Object {
    private DirectoryString a;

    public Restriction(String str) {
        this.a = new DirectoryString(str);
    }

    private Restriction(DirectoryString directoryString) {
        this.a = directoryString;
    }

    public static Restriction getInstance(Object obj) {
        if (obj instanceof Restriction) {
            return (Restriction) obj;
        }
        if (obj != null) {
            return new Restriction(DirectoryString.getInstance(obj));
        }
        return null;
    }

    public DirectoryString getRestriction() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
