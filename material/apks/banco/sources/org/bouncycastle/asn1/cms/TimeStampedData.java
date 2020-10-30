package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERIA5String;

public class TimeStampedData extends ASN1Object {
    private ASN1Integer a;
    private DERIA5String b;
    private MetaData c;
    private ASN1OctetString d;
    private Evidence e;

    private TimeStampedData(ASN1Sequence aSN1Sequence) {
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        int i = 1;
        if (aSN1Sequence.getObjectAt(1) instanceof DERIA5String) {
            this.b = DERIA5String.getInstance(aSN1Sequence.getObjectAt(1));
            i = 2;
        }
        if ((aSN1Sequence.getObjectAt(i) instanceof MetaData) || (aSN1Sequence.getObjectAt(i) instanceof ASN1Sequence)) {
            int i2 = i + 1;
            this.c = MetaData.getInstance(aSN1Sequence.getObjectAt(i));
            i = i2;
        }
        if (aSN1Sequence.getObjectAt(i) instanceof ASN1OctetString) {
            int i3 = i + 1;
            this.d = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i));
            i = i3;
        }
        this.e = Evidence.getInstance(aSN1Sequence.getObjectAt(i));
    }

    public TimeStampedData(DERIA5String dERIA5String, MetaData metaData, ASN1OctetString aSN1OctetString, Evidence evidence) {
        this.a = new ASN1Integer(1);
        this.b = dERIA5String;
        this.c = metaData;
        this.d = aSN1OctetString;
        this.e = evidence;
    }

    public static TimeStampedData getInstance(Object obj) {
        return (obj == null || (obj instanceof TimeStampedData)) ? (TimeStampedData) obj : new TimeStampedData(ASN1Sequence.getInstance(obj));
    }

    public ASN1OctetString getContent() {
        return this.d;
    }

    public DERIA5String getDataUri() {
        return this.b;
    }

    public MetaData getMetaData() {
        return this.c;
    }

    public Evidence getTemporalEvidence() {
        return this.e;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        aSN1EncodableVector.add(this.e);
        return new BERSequence(aSN1EncodableVector);
    }
}
