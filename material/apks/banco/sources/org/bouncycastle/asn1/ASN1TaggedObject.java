package org.bouncycastle.asn1;

import java.io.IOException;

public abstract class ASN1TaggedObject extends ASN1Primitive implements ASN1TaggedObjectParser {
    int a;
    boolean b = false;
    boolean c = true;
    ASN1Encodable d = null;

    public ASN1TaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable instanceof ASN1Choice) {
            this.c = true;
        } else {
            this.c = z;
        }
        this.a = i;
        if (!this.c) {
            boolean z2 = aSN1Encodable.toASN1Primitive() instanceof ASN1Set;
        }
        this.d = aSN1Encodable;
    }

    public static ASN1TaggedObject getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1TaggedObject)) {
            return (ASN1TaggedObject) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("failed to construct tagged object from byte[]: ");
                sb.append(e.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unknown object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static ASN1TaggedObject getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return (ASN1TaggedObject) aSN1TaggedObject.getObject();
        }
        throw new IllegalArgumentException("implicitly tagged tagged object");
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
            return false;
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
        if (this.a != aSN1TaggedObject.a || this.b != aSN1TaggedObject.b || this.c != aSN1TaggedObject.c) {
            return false;
        }
        if (this.d == null) {
            if (aSN1TaggedObject.d != null) {
                return false;
            }
        } else if (!this.d.toASN1Primitive().equals(aSN1TaggedObject.d.toASN1Primitive())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive b() {
        return new DERTaggedObject(this.c, this.a, this.d);
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive c() {
        return new DLTaggedObject(this.c, this.a, this.d);
    }

    /* access modifiers changed from: 0000 */
    public abstract void encode(ASN1OutputStream aSN1OutputStream);

    public ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    public ASN1Primitive getObject() {
        if (this.d != null) {
            return this.d.toASN1Primitive();
        }
        return null;
    }

    public ASN1Encodable getObjectParser(int i, boolean z) {
        if (i == 4) {
            return ASN1OctetString.getInstance(this, z).parser();
        }
        switch (i) {
            case 16:
                return ASN1Sequence.getInstance(this, z).parser();
            case 17:
                return ASN1Set.getInstance(this, z).parser();
            default:
                if (z) {
                    return getObject();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("implicit tagging not implemented for tag: ");
                sb.append(i);
                throw new RuntimeException(sb.toString());
        }
    }

    public int getTagNo() {
        return this.a;
    }

    public int hashCode() {
        int i = this.a;
        return this.d != null ? i ^ this.d.hashCode() : i;
    }

    public boolean isEmpty() {
        return this.b;
    }

    public boolean isExplicit() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.a);
        sb.append("]");
        sb.append(this.d);
        return sb.toString();
    }
}
