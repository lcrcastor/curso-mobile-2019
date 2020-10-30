package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.X509Extensions;

public class SingleResponse extends ASN1Object {
    private CertID a;
    private CertStatus b;
    private ASN1GeneralizedTime c;
    private ASN1GeneralizedTime d;
    private Extensions e;

    private SingleResponse(ASN1Sequence aSN1Sequence) {
        ASN1TaggedObject aSN1TaggedObject;
        this.a = CertID.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = CertStatus.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 4) {
            this.d = ASN1GeneralizedTime.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(3), true);
            aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(4);
        } else {
            if (aSN1Sequence.size() > 3) {
                aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(3);
                if (aSN1TaggedObject.getTagNo() == 0) {
                    this.d = ASN1GeneralizedTime.getInstance(aSN1TaggedObject, true);
                }
            }
            return;
        }
        this.e = Extensions.getInstance(aSN1TaggedObject, true);
    }

    public SingleResponse(CertID certID, CertStatus certStatus, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1GeneralizedTime aSN1GeneralizedTime2, Extensions extensions) {
        this.a = certID;
        this.b = certStatus;
        this.c = aSN1GeneralizedTime;
        this.d = aSN1GeneralizedTime2;
        this.e = extensions;
    }

    public SingleResponse(CertID certID, CertStatus certStatus, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1GeneralizedTime aSN1GeneralizedTime2, X509Extensions x509Extensions) {
        this(certID, certStatus, aSN1GeneralizedTime, aSN1GeneralizedTime2, Extensions.getInstance(x509Extensions));
    }

    public static SingleResponse getInstance(Object obj) {
        if (obj instanceof SingleResponse) {
            return (SingleResponse) obj;
        }
        if (obj != null) {
            return new SingleResponse(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static SingleResponse getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public CertID getCertID() {
        return this.a;
    }

    public CertStatus getCertStatus() {
        return this.b;
    }

    public ASN1GeneralizedTime getNextUpdate() {
        return this.d;
    }

    public Extensions getSingleExtensions() {
        return this.e;
    }

    public ASN1GeneralizedTime getThisUpdate() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.d));
        }
        if (this.e != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.e));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
