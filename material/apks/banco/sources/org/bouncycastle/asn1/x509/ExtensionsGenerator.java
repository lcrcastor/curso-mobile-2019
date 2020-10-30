package org.bouncycastle.asn1.x509;

import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DEROctetString;

public class ExtensionsGenerator {
    private Hashtable a = new Hashtable();
    private Vector b = new Vector();

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, ASN1Encodable aSN1Encodable) {
        addExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
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
        this.a.put(aSN1ObjectIdentifier, new Extension(aSN1ObjectIdentifier, z, (ASN1OctetString) new DEROctetString(bArr)));
    }

    public Extensions generate() {
        Extension[] extensionArr = new Extension[this.b.size()];
        for (int i = 0; i != this.b.size(); i++) {
            extensionArr[i] = (Extension) this.a.get(this.b.elementAt(i));
        }
        return new Extensions(extensionArr);
    }

    public boolean isEmpty() {
        return this.b.isEmpty();
    }

    public void reset() {
        this.a = new Hashtable();
        this.b = new Vector();
    }
}
