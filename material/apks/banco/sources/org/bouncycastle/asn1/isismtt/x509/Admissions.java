package org.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.GeneralName;

public class Admissions extends ASN1Object {
    private GeneralName a;
    private NamingAuthority b;
    private ASN1Sequence c;

    private Admissions(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        ASN1Encodable aSN1Encodable = (ASN1Encodable) objects.nextElement();
        if (aSN1Encodable instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Encodable;
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.a = GeneralName.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.b = NamingAuthority.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Bad tag number: ");
                    sb2.append(aSN1TaggedObject.getTagNo());
                    throw new IllegalArgumentException(sb2.toString());
            }
            aSN1Encodable = (ASN1Encodable) objects.nextElement();
        }
        if (aSN1Encodable instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject2 = (ASN1TaggedObject) aSN1Encodable;
            if (aSN1TaggedObject2.getTagNo() != 1) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Bad tag number: ");
                sb3.append(aSN1TaggedObject2.getTagNo());
                throw new IllegalArgumentException(sb3.toString());
            }
            this.b = NamingAuthority.getInstance(aSN1TaggedObject2, true);
            aSN1Encodable = (ASN1Encodable) objects.nextElement();
        }
        this.c = ASN1Sequence.getInstance(aSN1Encodable);
        if (objects.hasMoreElements()) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Bad object encountered: ");
            sb4.append(objects.nextElement().getClass());
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    public Admissions(GeneralName generalName, NamingAuthority namingAuthority, ProfessionInfo[] professionInfoArr) {
        this.a = generalName;
        this.b = namingAuthority;
        this.c = new DERSequence((ASN1Encodable[]) professionInfoArr);
    }

    public static Admissions getInstance(Object obj) {
        if (obj == null || (obj instanceof Admissions)) {
            return (Admissions) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new Admissions((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public GeneralName getAdmissionAuthority() {
        return this.a;
    }

    public NamingAuthority getNamingAuthority() {
        return this.b;
    }

    public ProfessionInfo[] getProfessionInfos() {
        ProfessionInfo[] professionInfoArr = new ProfessionInfo[this.c.size()];
        Enumeration objects = this.c.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            professionInfoArr[i] = ProfessionInfo.getInstance(objects.nextElement());
            i = i2;
        }
        return professionInfoArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.b));
        }
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
