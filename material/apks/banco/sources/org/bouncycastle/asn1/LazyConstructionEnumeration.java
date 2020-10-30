package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

class LazyConstructionEnumeration implements Enumeration {
    private ASN1InputStream a;
    private Object b = a();

    public LazyConstructionEnumeration(byte[] bArr) {
        this.a = new ASN1InputStream(bArr, true);
    }

    private Object a() {
        try {
            return this.a.readObject();
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("malformed DER construction: ");
            sb.append(e);
            throw new ASN1ParsingException(sb.toString(), e);
        }
    }

    public boolean hasMoreElements() {
        return this.b != null;
    }

    public Object nextElement() {
        Object obj = this.b;
        this.b = a();
        return obj;
    }
}
