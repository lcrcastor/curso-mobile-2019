package org.bouncycastle.asn1;

import java.io.IOException;

public class DERExternalParser implements ASN1Encodable, InMemoryRepresentable {
    private ASN1StreamParser a;

    public DERExternalParser(ASN1StreamParser aSN1StreamParser) {
        this.a = aSN1StreamParser;
    }

    public ASN1Primitive getLoadedObject() {
        try {
            return new DERExternal(this.a.a());
        } catch (IllegalArgumentException e) {
            throw new ASN1Exception(e.getMessage(), e);
        }
    }

    public ASN1Encodable readObject() {
        return this.a.readObject();
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("unable to get DER object", e);
        } catch (IllegalArgumentException e2) {
            throw new ASN1ParsingException("unable to get DER object", e2);
        }
    }
}
