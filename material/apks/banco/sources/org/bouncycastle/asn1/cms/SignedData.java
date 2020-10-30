package org.bouncycastle.asn1.cms;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class SignedData extends ASN1Object {
    private static final ASN1Integer a = new ASN1Integer(1);
    private static final ASN1Integer b = new ASN1Integer(3);
    private static final ASN1Integer c = new ASN1Integer(4);
    private static final ASN1Integer d = new ASN1Integer(5);
    private ASN1Integer e;
    private ASN1Set f;
    private ContentInfo g;
    private ASN1Set h;
    private ASN1Set i;
    private ASN1Set j;
    private boolean k;
    private boolean l;

    private SignedData(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.e = ASN1Integer.getInstance(objects.nextElement());
        this.f = (ASN1Set) objects.nextElement();
        this.g = ContentInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1Primitive aSN1Primitive = (ASN1Primitive) objects.nextElement();
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
                switch (aSN1TaggedObject.getTagNo()) {
                    case 0:
                        this.k = aSN1TaggedObject instanceof BERTaggedObject;
                        this.h = ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    case 1:
                        this.l = aSN1TaggedObject instanceof BERTaggedObject;
                        this.i = ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown tag value ");
                        sb.append(aSN1TaggedObject.getTagNo());
                        throw new IllegalArgumentException(sb.toString());
                }
            } else {
                this.j = (ASN1Set) aSN1Primitive;
            }
        }
    }

    public SignedData(ASN1Set aSN1Set, ContentInfo contentInfo, ASN1Set aSN1Set2, ASN1Set aSN1Set3, ASN1Set aSN1Set4) {
        this.e = a(contentInfo.getContentType(), aSN1Set2, aSN1Set3, aSN1Set4);
        this.f = aSN1Set;
        this.g = contentInfo;
        this.h = aSN1Set2;
        this.i = aSN1Set3;
        this.j = aSN1Set4;
        this.l = aSN1Set3 instanceof BERSet;
        this.k = aSN1Set2 instanceof BERSet;
    }

    private ASN1Integer a(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Set aSN1Set, ASN1Set aSN1Set2, ASN1Set aSN1Set3) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (aSN1Set != null) {
            Enumeration objects = aSN1Set.getObjects();
            z3 = false;
            z2 = false;
            z = false;
            while (objects.hasMoreElements()) {
                Object nextElement = objects.nextElement();
                if (nextElement instanceof ASN1TaggedObject) {
                    ASN1TaggedObject instance = ASN1TaggedObject.getInstance(nextElement);
                    if (instance.getTagNo() == 1) {
                        z = true;
                    } else if (instance.getTagNo() == 2) {
                        z2 = true;
                    } else if (instance.getTagNo() == 3) {
                        z3 = true;
                    }
                }
            }
        } else {
            z3 = false;
            z2 = false;
            z = false;
        }
        if (z3) {
            return new ASN1Integer(5);
        }
        if (aSN1Set2 != null) {
            Enumeration objects2 = aSN1Set2.getObjects();
            while (objects2.hasMoreElements()) {
                if (objects2.nextElement() instanceof ASN1TaggedObject) {
                    z4 = true;
                }
            }
        }
        return z4 ? d : z2 ? c : z ? b : a(aSN1Set3) ? b : !CMSObjectIdentifiers.data.equals(aSN1ObjectIdentifier) ? b : a;
    }

    private boolean a(ASN1Set aSN1Set) {
        Enumeration objects = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            if (SignerInfo.getInstance(objects.nextElement()).getVersion().getValue().intValue() == 3) {
                return true;
            }
        }
        return false;
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
        return this.i;
    }

    public ASN1Set getCertificates() {
        return this.h;
    }

    public ASN1Set getDigestAlgorithms() {
        return this.f;
    }

    public ContentInfo getEncapContentInfo() {
        return this.g;
    }

    public ASN1Set getSignerInfos() {
        return this.j;
    }

    public ASN1Integer getVersion() {
        return this.e;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        aSN1EncodableVector.add(this.g);
        if (this.h != null) {
            aSN1EncodableVector.add(this.k ? new BERTaggedObject(false, 0, this.h) : new DERTaggedObject(false, 0, this.h));
        }
        if (this.i != null) {
            aSN1EncodableVector.add(this.l ? new BERTaggedObject(false, 1, this.i) : new DERTaggedObject(false, 1, this.i));
        }
        aSN1EncodableVector.add(this.j);
        return new BERSequence(aSN1EncodableVector);
    }
}
