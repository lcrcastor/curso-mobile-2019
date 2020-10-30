package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;

public class ContentInfoParser {
    private ASN1ObjectIdentifier a;
    private ASN1TaggedObjectParser b;

    public ContentInfoParser(ASN1SequenceParser aSN1SequenceParser) {
        this.a = (ASN1ObjectIdentifier) aSN1SequenceParser.readObject();
        this.b = (ASN1TaggedObjectParser) aSN1SequenceParser.readObject();
    }

    public ASN1Encodable getContent(int i) {
        if (this.b != null) {
            return this.b.getObjectParser(i, true);
        }
        return null;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.a;
    }
}
