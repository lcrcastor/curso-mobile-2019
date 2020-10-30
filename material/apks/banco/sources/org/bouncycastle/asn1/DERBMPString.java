package org.bouncycastle.asn1;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.util.Arrays;

public class DERBMPString extends ASN1Primitive implements ASN1String {
    private char[] a;

    public DERBMPString(String str) {
        this.a = str.toCharArray();
    }

    DERBMPString(byte[] bArr) {
        char[] cArr = new char[(bArr.length / 2)];
        for (int i = 0; i != cArr.length; i++) {
            int i2 = i * 2;
            cArr[i] = (char) ((bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) | (bArr[i2] << 8));
        }
        this.a = cArr;
    }

    DERBMPString(char[] cArr) {
        this.a = cArr;
    }

    public static DERBMPString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBMPString)) {
            return (DERBMPString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (DERBMPString) fromByteArray((byte[]) obj);
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

    public static DERBMPString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERBMPString)) ? getInstance(object) : new DERBMPString(ASN1OctetString.getInstance(object).getOctets());
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.a.length * 2) + 1 + (this.a.length * 2);
    }

    /* access modifiers changed from: protected */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERBMPString)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((DERBMPString) aSN1Primitive).a);
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.b(30);
        aSN1OutputStream.a(this.a.length * 2);
        for (int i = 0; i != this.a.length; i++) {
            char c = this.a[i];
            aSN1OutputStream.b((byte) (c >> 8));
            aSN1OutputStream.b((byte) c);
        }
    }

    public String getString() {
        return new String(this.a);
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
