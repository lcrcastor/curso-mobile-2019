package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class AuthEnvelopedData extends ASN1Object {
    private ASN1Integer a;
    private OriginatorInfo b;
    private ASN1Set c;
    private EncryptedContentInfo d;
    private ASN1Set e;
    private ASN1OctetString f;
    private ASN1Set g;

    public AuthEnvelopedData(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1Integer) aSN1Sequence.getObjectAt(0).toASN1Primitive();
        ASN1Primitive aSN1Primitive = aSN1Sequence.getObjectAt(1).toASN1Primitive();
        int i = 2;
        if (aSN1Primitive instanceof ASN1TaggedObject) {
            this.b = OriginatorInfo.getInstance((ASN1TaggedObject) aSN1Primitive, false);
            aSN1Primitive = aSN1Sequence.getObjectAt(2).toASN1Primitive();
            i = 3;
        }
        this.c = ASN1Set.getInstance(aSN1Primitive);
        int i2 = i + 1;
        this.d = EncryptedContentInfo.getInstance(aSN1Sequence.getObjectAt(i).toASN1Primitive());
        int i3 = i2 + 1;
        ASN1Primitive aSN1Primitive2 = aSN1Sequence.getObjectAt(i2).toASN1Primitive();
        if (aSN1Primitive2 instanceof ASN1TaggedObject) {
            this.e = ASN1Set.getInstance((ASN1TaggedObject) aSN1Primitive2, false);
            int i4 = i3 + 1;
            ASN1Primitive aSN1Primitive3 = aSN1Sequence.getObjectAt(i3).toASN1Primitive();
            i3 = i4;
            aSN1Primitive2 = aSN1Primitive3;
        }
        this.f = ASN1OctetString.getInstance(aSN1Primitive2);
        if (aSN1Sequence.size() > i3) {
            this.g = ASN1Set.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i3).toASN1Primitive(), false);
        }
    }

    public AuthEnvelopedData(OriginatorInfo originatorInfo, ASN1Set aSN1Set, EncryptedContentInfo encryptedContentInfo, ASN1Set aSN1Set2, ASN1OctetString aSN1OctetString, ASN1Set aSN1Set3) {
        this.a = new ASN1Integer(0);
        this.b = originatorInfo;
        this.c = aSN1Set;
        this.d = encryptedContentInfo;
        this.e = aSN1Set2;
        this.f = aSN1OctetString;
        this.g = aSN1Set3;
    }

    public static AuthEnvelopedData getInstance(Object obj) {
        if (obj == null || (obj instanceof AuthEnvelopedData)) {
            return (AuthEnvelopedData) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AuthEnvelopedData((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid AuthEnvelopedData: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static AuthEnvelopedData getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1Set getAuthAttrs() {
        return this.e;
    }

    public EncryptedContentInfo getAuthEncryptedContentInfo() {
        return this.d;
    }

    public ASN1OctetString getMac() {
        return this.f;
    }

    public OriginatorInfo getOriginatorInfo() {
        return this.b;
    }

    public ASN1Set getRecipientInfos() {
        return this.c;
    }

    public ASN1Set getUnauthAttrs() {
        return this.g;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.b));
        }
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        if (this.e != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.e));
        }
        aSN1EncodableVector.add(this.f);
        if (this.g != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, this.g));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}
