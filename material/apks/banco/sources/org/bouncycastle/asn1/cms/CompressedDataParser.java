package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CompressedDataParser {
    private ASN1Integer a;
    private AlgorithmIdentifier b;
    private ContentInfoParser c;

    public CompressedDataParser(ASN1SequenceParser aSN1SequenceParser) {
        this.a = (ASN1Integer) aSN1SequenceParser.readObject();
        this.b = AlgorithmIdentifier.getInstance(aSN1SequenceParser.readObject().toASN1Primitive());
        this.c = new ContentInfoParser((ASN1SequenceParser) aSN1SequenceParser.readObject());
    }

    public AlgorithmIdentifier getCompressionAlgorithmIdentifier() {
        return this.b;
    }

    public ContentInfoParser getEncapContentInfo() {
        return this.c;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }
}
