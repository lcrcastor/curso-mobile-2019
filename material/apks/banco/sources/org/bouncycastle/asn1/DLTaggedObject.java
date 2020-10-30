package org.bouncycastle.asn1;

import org.bouncycastle.crypto.tls.CipherSuite;

public class DLTaggedObject extends ASN1TaggedObject {
    private static final byte[] e = new byte[0];

    public DLTaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        int b;
        if (this.b) {
            return StreamUtil.b(this.a) + 1;
        }
        int a = this.d.toASN1Primitive().c().a();
        if (this.c) {
            b = StreamUtil.b(this.a) + StreamUtil.a(a);
        } else {
            a--;
            b = StreamUtil.b(this.a);
        }
        return b + a;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        boolean z = this.b;
        int i = CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        if (!z) {
            ASN1Primitive c = this.d.toASN1Primitive().c();
            if (this.c) {
                aSN1OutputStream.a((int) CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, this.a);
                aSN1OutputStream.a(c.a());
                aSN1OutputStream.writeObject(c);
                return;
            }
            if (!c.isConstructed()) {
                i = 128;
            }
            aSN1OutputStream.a(i, this.a);
            aSN1OutputStream.a(c);
            return;
        }
        aSN1OutputStream.a((int) CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, this.a, e);
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        if (this.b || this.c) {
            return true;
        }
        return this.d.toASN1Primitive().c().isConstructed();
    }
}
