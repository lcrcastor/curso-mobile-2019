package org.bouncycastle.asn1;

import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import org.bouncycastle.util.Arrays;

public class ASN1Boolean extends ASN1Primitive {
    public static final ASN1Boolean FALSE = new ASN1Boolean(false);
    public static final ASN1Boolean TRUE = new ASN1Boolean(true);
    private static final byte[] a = {-1};
    private static final byte[] b = {0};
    private byte[] c;

    public ASN1Boolean(boolean z) {
        this.c = z ? a : b;
    }

    ASN1Boolean(byte[] bArr) {
        if (bArr.length != 1) {
            throw new IllegalArgumentException("byte value should have 1 byte in it");
        }
        byte[] clone = bArr[0] == 0 ? b : (bArr[0] & UnsignedBytes.MAX_VALUE) == 255 ? a : Arrays.clone(bArr);
        this.c = clone;
    }

    static ASN1Boolean a(byte[] bArr) {
        if (bArr.length == 1) {
            return bArr[0] == 0 ? FALSE : (bArr[0] & UnsignedBytes.MAX_VALUE) == 255 ? TRUE : new ASN1Boolean(bArr);
        }
        throw new IllegalArgumentException("BOOLEAN value should have 1 byte in it");
    }

    public static ASN1Boolean getInstance(int i) {
        return i != 0 ? TRUE : FALSE;
    }

    public static ASN1Boolean getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Boolean)) {
            return (ASN1Boolean) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Boolean) fromByteArray((byte[]) obj);
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("failed to construct boolean from byte[]: ");
                sb.append(e.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("illegal object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static ASN1Boolean getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1Boolean)) ? getInstance((Object) object) : a(((ASN1OctetString) object).getOctets());
    }

    public static ASN1Boolean getInstance(boolean z) {
        return z ? TRUE : FALSE;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return 3;
    }

    /* access modifiers changed from: protected */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        return (aSN1Primitive instanceof ASN1Boolean) && this.c[0] == ((ASN1Boolean) aSN1Primitive).c[0];
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(1, this.c);
    }

    public int hashCode() {
        return this.c[0];
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }

    public boolean isTrue() {
        return this.c[0] != 0;
    }

    public String toString() {
        return this.c[0] != 0 ? "TRUE" : "FALSE";
    }
}
