package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DEROctetString;

public class X509ExtensionsGenerator {
    private Hashtable a = new Hashtable();
    private Vector b = new Vector();

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, ASN1Encodable aSN1Encodable) {
        try {
            addExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("error encoding value: ");
            sb.append(e);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        if (this.a.containsKey(aSN1ObjectIdentifier)) {
            StringBuilder sb = new StringBuilder();
            sb.append("extension ");
            sb.append(aSN1ObjectIdentifier);
            sb.append(" already added");
            throw new IllegalArgumentException(sb.toString());
        }
        this.b.addElement(aSN1ObjectIdentifier);
        this.a.put(aSN1ObjectIdentifier, new X509Extension(z, (ASN1OctetString) new DEROctetString(bArr)));
    }

    public X509Extensions generate() {
        return new X509Extensions(this.b, this.a);
    }

    public boolean isEmpty() {
        return this.b.isEmpty();
    }

    public void reset() {
        this.a = new Hashtable();
        this.b = new Vector();
    }
}
