package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.X509Extensions;

public class ResponseData extends ASN1Object {
    private static final ASN1Integer a = new ASN1Integer(0);
    private boolean b;
    private ASN1Integer c;
    private ResponderID d;
    private ASN1GeneralizedTime e;
    private ASN1Sequence f;
    private Extensions g;

    public ResponseData(ASN1Integer aSN1Integer, ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, Extensions extensions) {
        this.c = aSN1Integer;
        this.d = responderID;
        this.e = aSN1GeneralizedTime;
        this.f = aSN1Sequence;
        this.g = extensions;
    }

    private ResponseData(ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (!(aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) || ((ASN1TaggedObject) aSN1Sequence.getObjectAt(0)).getTagNo() != 0) {
            this.c = a;
        } else {
            this.b = true;
            this.c = ASN1Integer.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(0), true);
            i = 1;
        }
        int i2 = i + 1;
        this.d = ResponderID.getInstance(aSN1Sequence.getObjectAt(i));
        int i3 = i2 + 1;
        this.e = ASN1GeneralizedTime.getInstance(aSN1Sequence.getObjectAt(i2));
        int i4 = i3 + 1;
        this.f = (ASN1Sequence) aSN1Sequence.getObjectAt(i3);
        if (aSN1Sequence.size() > i4) {
            this.g = Extensions.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i4), true);
        }
    }

    public ResponseData(ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, Extensions extensions) {
        this(a, responderID, aSN1GeneralizedTime, aSN1Sequence, extensions);
    }

    public ResponseData(ResponderID responderID, ASN1GeneralizedTime aSN1GeneralizedTime, ASN1Sequence aSN1Sequence, X509Extensions x509Extensions) {
        this(a, responderID, ASN1GeneralizedTime.getInstance(aSN1GeneralizedTime), aSN1Sequence, Extensions.getInstance(x509Extensions));
    }

    public static ResponseData getInstance(Object obj) {
        if (obj instanceof ResponseData) {
            return (ResponseData) obj;
        }
        if (obj != null) {
            return new ResponseData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static ResponseData getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1GeneralizedTime getProducedAt() {
        return this.e;
    }

    public ResponderID getResponderID() {
        return this.d;
    }

    public Extensions getResponseExtensions() {
        return this.g;
    }

    public ASN1Sequence getResponses() {
        return this.f;
    }

    public ASN1Integer getVersion() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.b || !this.c.equals(a)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.c));
        }
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        if (this.g != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.g));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
