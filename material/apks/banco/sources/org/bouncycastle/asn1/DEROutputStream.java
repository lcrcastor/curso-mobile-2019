package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class DEROutputStream extends ASN1OutputStream {
    public DEROutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    /* access modifiers changed from: 0000 */
    public ASN1OutputStream a() {
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ASN1OutputStream b() {
        return this;
    }

    public void writeObject(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().b().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}
