package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERIA5String;

public class TimeStampedDataParser {
    private ASN1Integer a;
    private DERIA5String b;
    private MetaData c;
    private ASN1OctetStringParser d;
    private Evidence e;
    private ASN1SequenceParser f;

    private TimeStampedDataParser(ASN1SequenceParser aSN1SequenceParser) {
        this.f = aSN1SequenceParser;
        this.a = ASN1Integer.getInstance(aSN1SequenceParser.readObject());
        ASN1Encodable readObject = aSN1SequenceParser.readObject();
        if (readObject instanceof DERIA5String) {
            this.b = DERIA5String.getInstance(readObject);
            readObject = aSN1SequenceParser.readObject();
        }
        if ((readObject instanceof MetaData) || (readObject instanceof ASN1SequenceParser)) {
            this.c = MetaData.getInstance(readObject.toASN1Primitive());
            readObject = aSN1SequenceParser.readObject();
        }
        if (readObject instanceof ASN1OctetStringParser) {
            this.d = (ASN1OctetStringParser) readObject;
        }
    }

    public static TimeStampedDataParser getInstance(Object obj) {
        if (obj instanceof ASN1Sequence) {
            return new TimeStampedDataParser(((ASN1Sequence) obj).parser());
        }
        if (obj instanceof ASN1SequenceParser) {
            return new TimeStampedDataParser((ASN1SequenceParser) obj);
        }
        return null;
    }

    public ASN1OctetStringParser getContent() {
        return this.d;
    }

    public DERIA5String getDataUri() {
        return this.b;
    }

    public MetaData getMetaData() {
        return this.c;
    }

    public Evidence getTemporalEvidence() {
        if (this.e == null) {
            this.e = Evidence.getInstance(this.f.readObject().toASN1Primitive());
        }
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
