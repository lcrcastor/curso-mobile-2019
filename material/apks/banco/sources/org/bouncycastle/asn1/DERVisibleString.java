package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class DERVisibleString extends ASN1Primitive implements ASN1String {
    private byte[] a;

    public DERVisibleString(String str) {
        this.a = Strings.toByteArray(str);
    }

    DERVisibleString(byte[] bArr) {
        this.a = bArr;
    }

    public static DERVisibleString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERVisibleString)) {
            return (DERVisibleString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERVisibleString) fromByteArray((byte[]) obj);
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

    public static DERVisibleString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERVisibleString)) ? getInstance(object) : new DERVisibleString(ASN1OctetString.getInstance(object).getOctets());
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.a.length) + 1 + this.a.length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERVisibleString)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERVisibleString) aSN1Primitive).a);
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(26, this.a);
    }

    public byte[] getOctets() {
        return Arrays.clone(this.a);
    }

    public String getString() {
        return Strings.fromByteArray(this.a);
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
