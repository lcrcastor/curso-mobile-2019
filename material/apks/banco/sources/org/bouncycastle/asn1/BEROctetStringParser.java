package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

public class BEROctetStringParser implements ASN1OctetStringParser {
    private ASN1StreamParser a;

    BEROctetStringParser(ASN1StreamParser aSN1StreamParser) {
        this.a = aSN1StreamParser;
    }

    public ASN1Primitive getLoadedObject() {
        return new BEROctetString(Streams.readAll(getOctetStream()));
    }

    public InputStream getOctetStream() {
        return new ConstructedOctetStream(this.a);
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("IOException converting stream to byte array: ");
            sb.append(e.getMessage());
            throw new ASN1ParsingException(sb.toString(), e);
        }
    }
}
