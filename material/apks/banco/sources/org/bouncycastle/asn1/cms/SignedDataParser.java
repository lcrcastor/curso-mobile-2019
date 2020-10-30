package org.bouncycastle.asn1.cms;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;

public class SignedDataParser {
    private ASN1SequenceParser a;
    private ASN1Integer b;
    private Object c;
    private boolean d;
    private boolean e;

    private SignedDataParser(ASN1SequenceParser aSN1SequenceParser) {
        this.a = aSN1SequenceParser;
        this.b = (ASN1Integer) aSN1SequenceParser.readObject();
    }

    public static SignedDataParser getInstance(Object obj) {
        if (obj instanceof ASN1Sequence) {
            return new SignedDataParser(((ASN1Sequence) obj).parser());
        }
        if (obj instanceof ASN1SequenceParser) {
            return new SignedDataParser((ASN1SequenceParser) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object encountered: ");
        sb.append(obj.getClass().getName());
        throw new IOException(sb.toString());
    }

    public ASN1SetParser getCertificates() {
        this.d = true;
        this.c = this.a.readObject();
        if (!(this.c instanceof ASN1TaggedObjectParser) || ((ASN1TaggedObjectParser) this.c).getTagNo() != 0) {
            return null;
        }
        ASN1SetParser aSN1SetParser = (ASN1SetParser) ((ASN1TaggedObjectParser) this.c).getObjectParser(17, false);
        this.c = null;
        return aSN1SetParser;
    }

    public ASN1SetParser getCrls() {
        if (!this.d) {
            throw new IOException("getCerts() has not been called.");
        }
        this.e = true;
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        if (!(this.c instanceof ASN1TaggedObjectParser) || ((ASN1TaggedObjectParser) this.c).getTagNo() != 1) {
            return null;
        }
        ASN1SetParser aSN1SetParser = (ASN1SetParser) ((ASN1TaggedObjectParser) this.c).getObjectParser(17, false);
        this.c = null;
        return aSN1SetParser;
    }

    public ASN1SetParser getDigestAlgorithms() {
        ASN1Encodable readObject = this.a.readObject();
        return readObject instanceof ASN1Set ? ((ASN1Set) readObject).parser() : (ASN1SetParser) readObject;
    }

    public ContentInfoParser getEncapContentInfo() {
        return new ContentInfoParser((ASN1SequenceParser) this.a.readObject());
    }

    public ASN1SetParser getSignerInfos() {
        if (!this.d || !this.e) {
            throw new IOException("getCerts() and/or getCrls() has not been called.");
        }
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        return (ASN1SetParser) this.c;
    }

    public ASN1Integer getVersion() {
        return this.b;
    }
}
