package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;

public class AuthEnvelopedDataParser {
    private ASN1SequenceParser a;
    private ASN1Integer b;
    private ASN1Encodable c;
    private boolean d;

    public AuthEnvelopedDataParser(ASN1SequenceParser aSN1SequenceParser) {
        this.a = aSN1SequenceParser;
        this.b = ASN1Integer.getInstance(aSN1SequenceParser.readObject());
    }

    public ASN1SetParser getAuthAttrs() {
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        if (!(this.c instanceof ASN1TaggedObjectParser)) {
            return null;
        }
        ASN1Encodable aSN1Encodable = this.c;
        this.c = null;
        return (ASN1SetParser) ((ASN1TaggedObjectParser) aSN1Encodable).getObjectParser(17, false);
    }

    public EncryptedContentInfoParser getAuthEncryptedContentInfo() {
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        if (this.c == null) {
            return null;
        }
        ASN1SequenceParser aSN1SequenceParser = (ASN1SequenceParser) this.c;
        this.c = null;
        return new EncryptedContentInfoParser(aSN1SequenceParser);
    }

    public ASN1OctetString getMac() {
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        ASN1Encodable aSN1Encodable = this.c;
        this.c = null;
        return ASN1OctetString.getInstance(aSN1Encodable.toASN1Primitive());
    }

    public OriginatorInfo getOriginatorInfo() {
        this.d = true;
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        if (!(this.c instanceof ASN1TaggedObjectParser) || ((ASN1TaggedObjectParser) this.c).getTagNo() != 0) {
            return null;
        }
        ASN1SequenceParser aSN1SequenceParser = (ASN1SequenceParser) ((ASN1TaggedObjectParser) this.c).getObjectParser(16, false);
        this.c = null;
        return OriginatorInfo.getInstance(aSN1SequenceParser.toASN1Primitive());
    }

    public ASN1SetParser getRecipientInfos() {
        if (!this.d) {
            getOriginatorInfo();
        }
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        ASN1SetParser aSN1SetParser = (ASN1SetParser) this.c;
        this.c = null;
        return aSN1SetParser;
    }

    public ASN1SetParser getUnauthAttrs() {
        if (this.c == null) {
            this.c = this.a.readObject();
        }
        if (this.c == null) {
            return null;
        }
        ASN1Encodable aSN1Encodable = this.c;
        this.c = null;
        return (ASN1SetParser) ((ASN1TaggedObjectParser) aSN1Encodable).getObjectParser(17, false);
    }

    public ASN1Integer getVersion() {
        return this.b;
    }
}
