package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;

public class DERExternal extends ASN1Primitive {
    private ASN1ObjectIdentifier a;
    private ASN1Integer b;
    private ASN1Primitive c;
    private int d;
    private ASN1Primitive e;

    public DERExternal(ASN1EncodableVector aSN1EncodableVector) {
        int i = 0;
        ASN1Primitive a2 = a(aSN1EncodableVector, 0);
        if (a2 instanceof ASN1ObjectIdentifier) {
            this.a = (ASN1ObjectIdentifier) a2;
            a2 = a(aSN1EncodableVector, 1);
            i = 1;
        }
        if (a2 instanceof ASN1Integer) {
            this.b = (ASN1Integer) a2;
            i++;
            a2 = a(aSN1EncodableVector, i);
        }
        if (!(a2 instanceof DERTaggedObject)) {
            this.c = a2;
            i++;
            a2 = a(aSN1EncodableVector, i);
        }
        if (aSN1EncodableVector.size() != i + 1) {
            throw new IllegalArgumentException("input vector too large");
        } else if (!(a2 instanceof DERTaggedObject)) {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        } else {
            DERTaggedObject dERTaggedObject = (DERTaggedObject) a2;
            a(dERTaggedObject.getTagNo());
            this.e = dERTaggedObject.getObject();
        }
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, int i, ASN1Primitive aSN1Primitive2) {
        a(aSN1ObjectIdentifier);
        a(aSN1Integer);
        a(aSN1Primitive);
        a(i);
        b(aSN1Primitive2.toASN1Primitive());
    }

    public DERExternal(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, DERTaggedObject dERTaggedObject) {
        this(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject.getTagNo(), dERTaggedObject.toASN1Primitive());
    }

    private ASN1Primitive a(ASN1EncodableVector aSN1EncodableVector, int i) {
        if (aSN1EncodableVector.size() > i) {
            return aSN1EncodableVector.get(i).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    private void a(int i) {
        if (i < 0 || i > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid encoding value: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.d = i;
    }

    private void a(ASN1Integer aSN1Integer) {
        this.b = aSN1Integer;
    }

    private void a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
    }

    private void a(ASN1Primitive aSN1Primitive) {
        this.c = aSN1Primitive;
    }

    private void b(ASN1Primitive aSN1Primitive) {
        this.e = aSN1Primitive;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return getEncoded().length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERExternal)) {
            return false;
        }
        if (this == aSN1Primitive) {
            return true;
        }
        DERExternal dERExternal = (DERExternal) aSN1Primitive;
        if (this.a != null && (dERExternal.a == null || !dERExternal.a.equals(this.a))) {
            return false;
        }
        if (this.b != null && (dERExternal.b == null || !dERExternal.b.equals(this.b))) {
            return false;
        }
        if (this.c == null || (dERExternal.c != null && dERExternal.c.equals(this.c))) {
            return this.e.equals(dERExternal.e);
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (this.a != null) {
            byteArrayOutputStream.write(this.a.getEncoded(ASN1Encoding.DER));
        }
        if (this.b != null) {
            byteArrayOutputStream.write(this.b.getEncoded(ASN1Encoding.DER));
        }
        if (this.c != null) {
            byteArrayOutputStream.write(this.c.getEncoded(ASN1Encoding.DER));
        }
        byteArrayOutputStream.write(new DERTaggedObject(true, this.d, this.e).getEncoded(ASN1Encoding.DER));
        aSN1OutputStream.a(32, 8, byteArrayOutputStream.toByteArray());
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.c;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.a;
    }

    public int getEncoding() {
        return this.d;
    }

    public ASN1Primitive getExternalContent() {
        return this.e;
    }

    public ASN1Integer getIndirectReference() {
        return this.b;
    }

    public int hashCode() {
        int hashCode = this.a != null ? this.a.hashCode() : 0;
        if (this.b != null) {
            hashCode ^= this.b.hashCode();
        }
        if (this.c != null) {
            hashCode ^= this.c.hashCode();
        }
        return hashCode ^ this.e.hashCode();
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return true;
    }
}
