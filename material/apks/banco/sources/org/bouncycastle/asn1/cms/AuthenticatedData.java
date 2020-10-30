package org.bouncycastle.asn1.cms;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
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
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedData extends ASN1Object {
    private ASN1Integer a;
    private OriginatorInfo b;
    private ASN1Set c;
    private AlgorithmIdentifier d;
    private AlgorithmIdentifier e;
    private ContentInfo f;
    private ASN1Set g;
    private ASN1OctetString h;
    private ASN1Set i;

    public AuthenticatedData(ASN1Sequence aSN1Sequence) {
        int i2;
        ASN1Encodable aSN1Encodable;
        this.a = (ASN1Integer) aSN1Sequence.getObjectAt(0);
        ASN1Encodable objectAt = aSN1Sequence.getObjectAt(1);
        int i3 = 2;
        if (objectAt instanceof ASN1TaggedObject) {
            this.b = OriginatorInfo.getInstance((ASN1TaggedObject) objectAt, false);
            objectAt = aSN1Sequence.getObjectAt(2);
            i3 = 3;
        }
        this.c = ASN1Set.getInstance(objectAt);
        int i4 = i3 + 1;
        this.d = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i3));
        int i5 = i4 + 1;
        ASN1Encodable objectAt2 = aSN1Sequence.getObjectAt(i4);
        if (objectAt2 instanceof ASN1TaggedObject) {
            this.e = AlgorithmIdentifier.getInstance((ASN1TaggedObject) objectAt2, false);
            int i6 = i5 + 1;
            ASN1Encodable objectAt3 = aSN1Sequence.getObjectAt(i5);
            i5 = i6;
            objectAt2 = objectAt3;
        }
        this.f = ContentInfo.getInstance(objectAt2);
        int i7 = i5 + 1;
        ASN1Encodable objectAt4 = aSN1Sequence.getObjectAt(i5);
        if (objectAt4 instanceof ASN1TaggedObject) {
            this.g = ASN1Set.getInstance((ASN1TaggedObject) objectAt4, false);
            i2 = i7 + 1;
            aSN1Encodable = aSN1Sequence.getObjectAt(i7);
        } else {
            ASN1Encodable aSN1Encodable2 = objectAt4;
            i2 = i7;
            aSN1Encodable = aSN1Encodable2;
        }
        this.h = ASN1OctetString.getInstance(aSN1Encodable);
        if (aSN1Sequence.size() > i2) {
            this.i = ASN1Set.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i2), false);
        }
    }

    public AuthenticatedData(OriginatorInfo originatorInfo, ASN1Set aSN1Set, AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, ContentInfo contentInfo, ASN1Set aSN1Set2, ASN1OctetString aSN1OctetString, ASN1Set aSN1Set3) {
        if (!(algorithmIdentifier2 == null && aSN1Set2 == null) && (algorithmIdentifier2 == null || aSN1Set2 == null)) {
            throw new IllegalArgumentException("digestAlgorithm and authAttrs must be set together");
        }
        this.a = new ASN1Integer((long) calculateVersion(originatorInfo));
        this.b = originatorInfo;
        this.d = algorithmIdentifier;
        this.e = algorithmIdentifier2;
        this.c = aSN1Set;
        this.f = contentInfo;
        this.g = aSN1Set2;
        this.h = aSN1OctetString;
        this.i = aSN1Set3;
    }

    public static int calculateVersion(OriginatorInfo originatorInfo) {
        int i2 = 0;
        if (originatorInfo == null) {
            return 0;
        }
        Enumeration objects = originatorInfo.getCertificates().getObjects();
        while (true) {
            if (!objects.hasMoreElements()) {
                break;
            }
            Object nextElement = objects.nextElement();
            if (nextElement instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) nextElement;
                if (aSN1TaggedObject.getTagNo() == 2) {
                    i2 = 1;
                } else if (aSN1TaggedObject.getTagNo() == 3) {
                    i2 = 3;
                    break;
                }
            }
        }
        if (originatorInfo.getCRLs() != null) {
            Enumeration objects2 = originatorInfo.getCRLs().getObjects();
            while (true) {
                if (!objects2.hasMoreElements()) {
                    break;
                }
                Object nextElement2 = objects2.nextElement();
                if ((nextElement2 instanceof ASN1TaggedObject) && ((ASN1TaggedObject) nextElement2).getTagNo() == 1) {
                    i2 = 3;
                    break;
                }
            }
        }
        return i2;
    }

    public static AuthenticatedData getInstance(Object obj) {
        if (obj == null || (obj instanceof AuthenticatedData)) {
            return (AuthenticatedData) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AuthenticatedData((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid AuthenticatedData: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static AuthenticatedData getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1Set getAuthAttrs() {
        return this.g;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.e;
    }

    public ContentInfo getEncapsulatedContentInfo() {
        return this.f;
    }

    public ASN1OctetString getMac() {
        return this.h;
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.d;
    }

    public OriginatorInfo getOriginatorInfo() {
        return this.b;
    }

    public ASN1Set getRecipientInfos() {
        return this.c;
    }

    public ASN1Set getUnauthAttrs() {
        return this.i;
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
        aSN1EncodableVector.add(this.h);
        if (this.i != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, this.i));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}
