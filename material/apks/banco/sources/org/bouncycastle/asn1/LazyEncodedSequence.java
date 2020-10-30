package org.bouncycastle.asn1;

import java.util.Enumeration;

class LazyEncodedSequence extends ASN1Sequence {
    private byte[] a;

    LazyEncodedSequence(byte[] bArr) {
        this.a = bArr;
    }

    private void d() {
        LazyConstructionEnumeration lazyConstructionEnumeration = new LazyConstructionEnumeration(this.a);
        while (lazyConstructionEnumeration.hasMoreElements()) {
            this.seq.addElement(lazyConstructionEnumeration.nextElement());
        }
        this.a = null;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a != null ? StreamUtil.a(this.a.length) + 1 + this.a.length : super.c().a();
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive b() {
        if (this.a != null) {
            d();
        }
        return super.b();
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive c() {
        if (this.a != null) {
            d();
        }
        return super.c();
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        if (this.a != null) {
            aSN1OutputStream.a(48, this.a);
        } else {
            super.c().encode(aSN1OutputStream);
        }
    }

    public synchronized ASN1Encodable getObjectAt(int i) {
        if (this.a != null) {
            d();
        }
        return super.getObjectAt(i);
    }

    public synchronized Enumeration getObjects() {
        if (this.a == null) {
            return super.getObjects();
        }
        return new LazyConstructionEnumeration(this.a);
    }

    public synchronized int size() {
        if (this.a != null) {
            d();
        }
        return super.size();
    }
}
