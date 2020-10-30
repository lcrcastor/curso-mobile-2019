package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class DERSequenceGenerator extends DERGenerator {
    private final ByteArrayOutputStream a = new ByteArrayOutputStream();

    public DERSequenceGenerator(OutputStream outputStream) {
        super(outputStream);
    }

    public DERSequenceGenerator(OutputStream outputStream, int i, boolean z) {
        super(outputStream, i, z);
    }

    public void addObject(ASN1Encodable aSN1Encodable) {
        aSN1Encodable.toASN1Primitive().encode(new DEROutputStream(this.a));
    }

    public void close() {
        a(48, this.a.toByteArray());
    }

    public OutputStream getRawOutputStream() {
        return this.a;
    }
}
