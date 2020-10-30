package org.bouncycastle.asn1.tsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class Accuracy extends ASN1Object {
    protected static final int MAX_MICROS = 999;
    protected static final int MAX_MILLIS = 999;
    protected static final int MIN_MICROS = 1;
    protected static final int MIN_MILLIS = 1;
    ASN1Integer a;
    ASN1Integer b;
    ASN1Integer c;

    protected Accuracy() {
    }

    public Accuracy(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, ASN1Integer aSN1Integer3) {
        this.a = aSN1Integer;
        if (aSN1Integer2 == null || (aSN1Integer2.getValue().intValue() >= 1 && aSN1Integer2.getValue().intValue() <= 999)) {
            this.b = aSN1Integer2;
            if (aSN1Integer3 == null || (aSN1Integer3.getValue().intValue() >= 1 && aSN1Integer3.getValue().intValue() <= 999)) {
                this.c = aSN1Integer3;
                return;
            }
            throw new IllegalArgumentException("Invalid micros field : not in (1..999)");
        }
        throw new IllegalArgumentException("Invalid millis field : not in (1..999)");
    }

    private Accuracy(ASN1Sequence aSN1Sequence) {
        this.a = null;
        this.b = null;
        this.c = null;
        for (int i = 0; i < aSN1Sequence.size(); i++) {
            if (aSN1Sequence.getObjectAt(i) instanceof ASN1Integer) {
                this.a = (ASN1Integer) aSN1Sequence.getObjectAt(i);
            } else if (aSN1Sequence.getObjectAt(i) instanceof DERTaggedObject) {
                DERTaggedObject dERTaggedObject = (DERTaggedObject) aSN1Sequence.getObjectAt(i);
                switch (dERTaggedObject.getTagNo()) {
                    case 0:
                        this.b = ASN1Integer.getInstance(dERTaggedObject, false);
                        if (this.b.getValue().intValue() >= 1 && this.b.getValue().intValue() <= 999) {
                            break;
                        } else {
                            throw new IllegalArgumentException("Invalid millis field : not in (1..999).");
                        }
                        break;
                    case 1:
                        this.c = ASN1Integer.getInstance(dERTaggedObject, false);
                        if (this.c.getValue().intValue() >= 1 && this.c.getValue().intValue() <= 999) {
                            break;
                        } else {
                            throw new IllegalArgumentException("Invalid micros field : not in (1..999).");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalig tag number");
                }
            } else {
                continue;
            }
        }
    }

    public static Accuracy getInstance(Object obj) {
        if (obj instanceof Accuracy) {
            return (Accuracy) obj;
        }
        if (obj != null) {
            return new Accuracy(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getMicros() {
        return this.c;
    }

    public ASN1Integer getMillis() {
        return this.b;
    }

    public ASN1Integer getSeconds() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.b));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
