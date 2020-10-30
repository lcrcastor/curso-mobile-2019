package org.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class SignedData extends ASN1Object implements PKCSObjectIdentifiers {
    private ASN1Integer a;
    private ASN1Set b;
    private ContentInfo c;
    private ASN1Set d;
    private ASN1Set e;
    private ASN1Set f;

    public SignedData(ASN1Integer aSN1Integer, ASN1Set aSN1Set, ContentInfo contentInfo, ASN1Set aSN1Set2, ASN1Set aSN1Set3, ASN1Set aSN1Set4) {
        this.a = aSN1Integer;
        this.b = aSN1Set;
        this.c = contentInfo;
        this.d = aSN1Set2;
        this.e = aSN1Set3;
        this.f = aSN1Set4;
    }

    public SignedData(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = (ASN1Integer) objects.nextElement();
        this.b = (ASN1Set) objects.nextElement();
        this.c = ContentInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1Primitive aSN1Primitive = (ASN1Primitive) objects.nextElement();
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
                switch (aSN1TaggedObject.getTagNo()) {
                    case 0:
                        this.d = ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    case 1:
                        this.e = ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown tag value ");
                        sb.append(aSN1TaggedObject.getTagNo());
                        throw new IllegalArgumentException(sb.toString());
                }
            } else {
                this.f = (ASN1Set) aSN1Primitive;
            }
        }
    }

    public static SignedData getInstance(Object obj) {
        if (obj instanceof SignedData) {
            return (SignedData) obj;
        }
        if (obj != null) {
            return new SignedData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Set getCRLs() {
        return this.e;
    }

    public ASN1Set getCertificates() {
        return this.d;
    }

    public ContentInfo getContentInfo() {
        return this.c;
    }

    public ASN1Set getDigestAlgorithms() {
        return this.b;
    }

    public ASN1Set getSignerInfos() {
        return this.f;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.d));
        }
        if (this.e != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.e));
        }
        aSN1EncodableVector.add(this.f);
        return new BERSequence(aSN1EncodableVector);
    }
}
