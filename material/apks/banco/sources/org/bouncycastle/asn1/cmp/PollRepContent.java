package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class PollRepContent extends ASN1Object {
    private ASN1Integer[] a;
    private ASN1Integer[] b;
    private PKIFreeText[] c;

    public PollRepContent(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2) {
        this(aSN1Integer, aSN1Integer2, null);
    }

    public PollRepContent(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, PKIFreeText pKIFreeText) {
        this.a = new ASN1Integer[1];
        this.b = new ASN1Integer[1];
        this.c = new PKIFreeText[1];
        this.a[0] = aSN1Integer;
        this.b[0] = aSN1Integer2;
        this.c[0] = pKIFreeText;
    }

    private PollRepContent(ASN1Sequence aSN1Sequence) {
        this.a = new ASN1Integer[aSN1Sequence.size()];
        this.b = new ASN1Integer[aSN1Sequence.size()];
        this.c = new PKIFreeText[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1Sequence instance = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i));
            this.a[i] = ASN1Integer.getInstance(instance.getObjectAt(0));
            this.b[i] = ASN1Integer.getInstance(instance.getObjectAt(1));
            if (instance.size() > 2) {
                this.c[i] = PKIFreeText.getInstance(instance.getObjectAt(2));
            }
        }
    }

    public static PollRepContent getInstance(Object obj) {
        if (obj instanceof PollRepContent) {
            return (PollRepContent) obj;
        }
        if (obj != null) {
            return new PollRepContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getCertReqId(int i) {
        return this.a[i];
    }

    public ASN1Integer getCheckAfter(int i) {
        return this.b[i];
    }

    public PKIFreeText getReason(int i) {
        return this.c[i];
    }

    public int size() {
        return this.a.length;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.a.length; i++) {
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(this.a[i]);
            aSN1EncodableVector2.add(this.b[i]);
            if (this.c[i] != null) {
                aSN1EncodableVector2.add(this.c[i]);
            }
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
