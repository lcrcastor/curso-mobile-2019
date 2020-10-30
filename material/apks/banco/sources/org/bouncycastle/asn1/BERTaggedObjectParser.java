package org.bouncycastle.asn1;

import java.io.IOException;

public class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    private boolean a;
    private int b;
    private ASN1StreamParser c;

    BERTaggedObjectParser(boolean z, int i, ASN1StreamParser aSN1StreamParser) {
        this.a = z;
        this.b = i;
        this.c = aSN1StreamParser;
    }

    public ASN1Primitive getLoadedObject() {
        return this.c.b(this.a, this.b);
    }

    public ASN1Encodable getObjectParser(int i, boolean z) {
        if (!z) {
            return this.c.a(this.a, i);
        }
        if (this.a) {
            return this.c.readObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    public int getTagNo() {
        return this.b;
    }

    public boolean isConstructed() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage());
        }
    }
}
