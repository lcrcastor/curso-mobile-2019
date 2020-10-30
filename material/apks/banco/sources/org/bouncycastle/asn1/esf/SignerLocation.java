package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.x500.DirectoryString;

public class SignerLocation extends ASN1Object {
    private DERUTF8String a;
    private DERUTF8String b;
    private ASN1Sequence c;

    private SignerLocation(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            DERTaggedObject dERTaggedObject = (DERTaggedObject) objects.nextElement();
            switch (dERTaggedObject.getTagNo()) {
                case 0:
                    this.a = new DERUTF8String(DirectoryString.getInstance(dERTaggedObject, true).getString());
                    break;
                case 1:
                    this.b = new DERUTF8String(DirectoryString.getInstance(dERTaggedObject, true).getString());
                    break;
                case 2:
                    this.c = dERTaggedObject.isExplicit() ? ASN1Sequence.getInstance(dERTaggedObject, true) : ASN1Sequence.getInstance(dERTaggedObject, false);
                    if (this.c != null && this.c.size() > 6) {
                        throw new IllegalArgumentException("postal address must contain less than 6 strings");
                    }
                default:
                    throw new IllegalArgumentException("illegal tag");
            }
        }
    }

    public SignerLocation(DERUTF8String dERUTF8String, DERUTF8String dERUTF8String2, ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence == null || aSN1Sequence.size() <= 6) {
            if (dERUTF8String != null) {
                this.a = DERUTF8String.getInstance(dERUTF8String.toASN1Primitive());
            }
            if (dERUTF8String2 != null) {
                this.b = DERUTF8String.getInstance(dERUTF8String2.toASN1Primitive());
            }
            if (aSN1Sequence != null) {
                this.c = ASN1Sequence.getInstance(aSN1Sequence.toASN1Primitive());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("postal address must contain less than 6 strings");
    }

    public static SignerLocation getInstance(Object obj) {
        return (obj == null || (obj instanceof SignerLocation)) ? (SignerLocation) obj : new SignerLocation(ASN1Sequence.getInstance(obj));
    }

    public DERUTF8String getCountryName() {
        return this.a;
    }

    public DERUTF8String getLocalityName() {
        return this.b;
    }

    public ASN1Sequence getPostalAddress() {
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
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
