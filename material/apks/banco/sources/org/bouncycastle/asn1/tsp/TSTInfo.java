package org.bouncycastle.asn1.tsp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;

public class TSTInfo extends ASN1Object {
    private ASN1Integer a;
    private ASN1ObjectIdentifier b;
    private MessageImprint c;
    private ASN1Integer d;
    private ASN1GeneralizedTime e;
    private Accuracy f;
    private ASN1Boolean g;
    private ASN1Integer h;
    private GeneralName i;
    private Extensions j;

    public TSTInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, MessageImprint messageImprint, ASN1Integer aSN1Integer, ASN1GeneralizedTime aSN1GeneralizedTime, Accuracy accuracy, ASN1Boolean aSN1Boolean, ASN1Integer aSN1Integer2, GeneralName generalName, Extensions extensions) {
        this.a = new ASN1Integer(1);
        this.b = aSN1ObjectIdentifier;
        this.c = messageImprint;
        this.d = aSN1Integer;
        this.e = aSN1GeneralizedTime;
        this.f = accuracy;
        this.g = aSN1Boolean;
        this.h = aSN1Integer2;
        this.i = generalName;
        this.j = extensions;
    }

    private TSTInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Integer.getInstance(objects.nextElement());
        this.b = ASN1ObjectIdentifier.getInstance(objects.nextElement());
        this.c = MessageImprint.getInstance(objects.nextElement());
        this.d = ASN1Integer.getInstance(objects.nextElement());
        this.e = ASN1GeneralizedTime.getInstance(objects.nextElement());
        ASN1Boolean instance = ASN1Boolean.getInstance(false);
        while (true) {
            this.g = instance;
            while (objects.hasMoreElements()) {
                ASN1Object aSN1Object = (ASN1Object) objects.nextElement();
                if (aSN1Object instanceof ASN1TaggedObject) {
                    DERTaggedObject dERTaggedObject = (DERTaggedObject) aSN1Object;
                    switch (dERTaggedObject.getTagNo()) {
                        case 0:
                            this.i = GeneralName.getInstance(dERTaggedObject, true);
                            break;
                        case 1:
                            this.j = Extensions.getInstance(dERTaggedObject, false);
                            break;
                        default:
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown tag value ");
                            sb.append(dERTaggedObject.getTagNo());
                            throw new IllegalArgumentException(sb.toString());
                    }
                } else if ((aSN1Object instanceof ASN1Sequence) || (aSN1Object instanceof Accuracy)) {
                    this.f = Accuracy.getInstance(aSN1Object);
                } else if (aSN1Object instanceof ASN1Boolean) {
                    instance = ASN1Boolean.getInstance((Object) aSN1Object);
                } else if (aSN1Object instanceof ASN1Integer) {
                    this.h = ASN1Integer.getInstance(aSN1Object);
                }
            }
            return;
        }
    }

    public static TSTInfo getInstance(Object obj) {
        if (obj instanceof TSTInfo) {
            return (TSTInfo) obj;
        }
        if (obj != null) {
            return new TSTInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Accuracy getAccuracy() {
        return this.f;
    }

    public Extensions getExtensions() {
        return this.j;
    }

    public ASN1GeneralizedTime getGenTime() {
        return this.e;
    }

    public MessageImprint getMessageImprint() {
        return this.c;
    }

    public ASN1Integer getNonce() {
        return this.h;
    }

    public ASN1Boolean getOrdering() {
        return this.g;
    }

    public ASN1ObjectIdentifier getPolicy() {
        return this.b;
    }

    public ASN1Integer getSerialNumber() {
        return this.d;
    }

    public GeneralName getTsa() {
        return this.i;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        if (this.f != null) {
            aSN1EncodableVector.add(this.f);
        }
        if (this.g != null && this.g.isTrue()) {
            aSN1EncodableVector.add(this.g);
        }
        if (this.h != null) {
            aSN1EncodableVector.add(this.h);
        }
        if (this.i != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.i));
        }
        if (this.j != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.j));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
