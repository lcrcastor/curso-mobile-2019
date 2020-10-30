package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

public class DEROctetStringParser implements ASN1OctetStringParser {
    private DefiniteLengthInputStream a;

    DEROctetStringParser(DefiniteLengthInputStream definiteLengthInputStream) {
        this.a = definiteLengthInputStream;
    }

    public ASN1Primitive getLoadedObject() {
        return new DEROctetString(this.a.b());
    }

    public InputStream getOctetStream() {
        return this.a;
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
