package org.bouncycastle.asn1.tsp;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;

public class TimeStampReq extends ASN1Object {
    ASN1Integer a;
    MessageImprint b;
    ASN1ObjectIdentifier c;
    ASN1Integer d;
    ASN1Boolean e;
    Extensions f;

    private TimeStampReq(ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = MessageImprint.getInstance(aSN1Sequence.getObjectAt(1));
        for (int i = 2; i < size; i++) {
            if (aSN1Sequence.getObjectAt(i) instanceof ASN1ObjectIdentifier) {
                this.c = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
            } else if (aSN1Sequence.getObjectAt(i) instanceof ASN1Integer) {
                this.d = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i));
            } else if (aSN1Sequence.getObjectAt(i) instanceof ASN1Boolean) {
                this.e = ASN1Boolean.getInstance((Object) aSN1Sequence.getObjectAt(i));
            } else if (aSN1Sequence.getObjectAt(i) instanceof ASN1TaggedObject) {
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i);
                if (aSN1TaggedObject.getTagNo() == 0) {
                    this.f = Extensions.getInstance(aSN1TaggedObject, false);
                }
            }
        }
    }

    public TimeStampReq(MessageImprint messageImprint, ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Boolean aSN1Boolean, Extensions extensions) {
        this.a = new ASN1Integer(1);
        this.b = messageImprint;
        this.c = aSN1ObjectIdentifier;
        this.d = aSN1Integer;
        this.e = aSN1Boolean;
        this.f = extensions;
    }

    public static TimeStampReq getInstance(Object obj) {
        if (obj instanceof TimeStampReq) {
            return (TimeStampReq) obj;
        }
        if (obj != null) {
            return new TimeStampReq(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Boolean getCertReq() {
        return this.e;
    }

    public Extensions getExtensions() {
        return this.f;
    }

    public MessageImprint getMessageImprint() {
        return this.b;
    }

    public ASN1Integer getNonce() {
        return this.d;
    }

    public ASN1ObjectIdentifier getReqPolicy() {
        return this.c;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        if (this.e != null && this.e.isTrue()) {
            aSN1EncodableVector.add(this.e);
        }
        if (this.f != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.f));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
