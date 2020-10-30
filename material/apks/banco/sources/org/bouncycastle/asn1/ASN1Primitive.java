package org.bouncycastle.asn1;

import java.io.IOException;

public abstract class ASN1Primitive extends ASN1Object {
    ASN1Primitive() {
    }

    public static ASN1Primitive fromByteArray(byte[] bArr) {
        try {
            return new ASN1InputStream(bArr).readObject();
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract int a();

    /* access modifiers changed from: 0000 */
    public abstract boolean asn1Equals(ASN1Primitive aSN1Primitive);

    /* access modifiers changed from: 0000 */
    public ASN1Primitive b() {
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive c() {
        return this;
    }

    /* access modifiers changed from: 0000 */
    public abstract void encode(ASN1OutputStream aSN1OutputStream);

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ASN1Encodable) && asn1Equals(((ASN1Encodable) obj).toASN1Primitive());
    }

    public abstract int hashCode();

    /* access modifiers changed from: 0000 */
    public abstract boolean isConstructed();

    public ASN1Primitive toASN1Primitive() {
        return this;
    }
}
