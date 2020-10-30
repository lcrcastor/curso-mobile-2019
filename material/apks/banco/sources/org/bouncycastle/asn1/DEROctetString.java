package org.bouncycastle.asn1;

public class DEROctetString extends ASN1OctetString {
    public DEROctetString(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
    }

    public DEROctetString(byte[] bArr) {
        super(bArr);
    }

    static void a(DEROutputStream dEROutputStream, byte[] bArr) {
        dEROutputStream.a(4, bArr);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.a.length) + 1 + this.a.length;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(4, this.a);
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }
}
