package org.bouncycastle.asn1.x509.sigi;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.DirectoryString;

public class PersonalData extends ASN1Object {
    private NameOrPseudonym a;
    private BigInteger b;
    private ASN1GeneralizedTime c;
    private DirectoryString d;
    private String e;
    private DirectoryString f;

    private PersonalData(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = NameOrPseudonym.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 0:
                    this.b = ASN1Integer.getInstance(instance, false).getValue();
                    break;
                case 1:
                    this.c = ASN1GeneralizedTime.getInstance(instance, false);
                    break;
                case 2:
                    this.d = DirectoryString.getInstance(instance, true);
                    break;
                case 3:
                    this.e = DERPrintableString.getInstance(instance, false).getString();
                    break;
                case 4:
                    this.f = DirectoryString.getInstance(instance, true);
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Bad tag number: ");
                    sb2.append(instance.getTagNo());
                    throw new IllegalArgumentException(sb2.toString());
            }
        }
    }

    public PersonalData(NameOrPseudonym nameOrPseudonym, BigInteger bigInteger, ASN1GeneralizedTime aSN1GeneralizedTime, DirectoryString directoryString, String str, DirectoryString directoryString2) {
        this.a = nameOrPseudonym;
        this.c = aSN1GeneralizedTime;
        this.e = str;
        this.b = bigInteger;
        this.f = directoryString2;
        this.d = directoryString;
    }

    public static PersonalData getInstance(Object obj) {
        if (obj == null || (obj instanceof PersonalData)) {
            return (PersonalData) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new PersonalData((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public ASN1GeneralizedTime getDateOfBirth() {
        return this.c;
    }

    public String getGender() {
        return this.e;
    }

    public BigInteger getNameDistinguisher() {
        return this.b;
    }

    public NameOrPseudonym getNameOrPseudonym() {
        return this.a;
    }

    public DirectoryString getPlaceOfBirth() {
        return this.d;
    }

    public DirectoryString getPostalAddress() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, new ASN1Integer(this.b)));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.c));
        }
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.d));
        }
        if (this.e != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, new DERPrintableString(this.e, true)));
        }
        if (this.f != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 4, this.f));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
