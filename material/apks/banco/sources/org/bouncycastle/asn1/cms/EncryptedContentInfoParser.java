package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfoParser {
    private ASN1ObjectIdentifier a;
    private AlgorithmIdentifier b;
    private ASN1TaggedObjectParser c;

    public EncryptedContentInfoParser(ASN1SequenceParser aSN1SequenceParser) {
        this.a = (ASN1ObjectIdentifier) aSN1SequenceParser.readObject();
        this.b = AlgorithmIdentifier.getInstance(aSN1SequenceParser.readObject().toASN1Primitive());
        this.c = (ASN1TaggedObjectParser) aSN1SequenceParser.readObject();
    }

    public AlgorithmIdentifier getContentEncryptionAlgorithm() {
        return this.b;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.a;
    }

    public ASN1Encodable getEncryptedContent(int i) {
        return this.c.getObjectParser(i, false);
    }
}
