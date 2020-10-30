package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class DERT61UTF8String extends ASN1Primitive implements ASN1String {
    private byte[] a;

    public DERT61UTF8String(String str) {
        this(Strings.toUTF8ByteArray(str));
    }

    public DERT61UTF8String(byte[] bArr) {
        this.a = bArr;
    }

    public static DERT61UTF8String getInstance(Object obj) {
        if (obj instanceof DERT61String) {
            return new DERT61UTF8String(((DERT61String) obj).getOctets());
        }
        if (obj == null || (obj instanceof DERT61UTF8String)) {
            return (DERT61UTF8String) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return new DERT61UTF8String(((DERT61String) fromByteArray((byte[]) obj)).getOctets());
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("encoding error in getInstance: ");
                sb.append(e.toString());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("illegal object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static DERT61UTF8String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERT61String) || (object instanceof DERT61UTF8String)) ? getInstance(object) : new DERT61UTF8String(ASN1OctetString.getInstance(object).getOctets());
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.a.length) + 1 + this.a.length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERT61UTF8String)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERT61UTF8String) aSN1Primitive).a);
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(20, this.a);
    }

    public byte[] getOctets() {
        return Arrays.clone(this.a);
    }

    public String getString() {
        return Strings.fromUTF8ByteArray(this.a);
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getString();
    }
}
