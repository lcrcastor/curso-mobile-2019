package org.bouncycastle.asn1;

import java.io.IOException;

public class BERApplicationSpecificParser implements ASN1ApplicationSpecificParser {
    private final int a;
    private final ASN1StreamParser b;

    BERApplicationSpecificParser(int i, ASN1StreamParser aSN1StreamParser) {
        this.a = i;
        this.b = aSN1StreamParser;
    }

    public ASN1Primitive getLoadedObject() {
        return new BERApplicationSpecific(this.a, this.b.a());
    }

    public ASN1Encodable readObject() {
        return this.b.readObject();
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
