package org.bouncycastle.asn1;

import com.google.common.primitives.UnsignedBytes;
import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

public class ASN1Integer extends ASN1Primitive {
    byte[] a;

    public ASN1Integer(long j) {
        this.a = BigInteger.valueOf(j).toByteArray();
    }

    public ASN1Integer(BigInteger bigInteger) {
        this.a = bigInteger.toByteArray();
    }

    public ASN1Integer(byte[] bArr) {
        this(bArr, true);
    }

    ASN1Integer(byte[] bArr, boolean z) {
        if (z) {
            bArr = Arrays.clone(bArr);
        }
        this.a = bArr;
    }

    public static ASN1Integer getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Integer)) {
            return (ASN1Integer) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1Integer) fromByteArray((byte[]) obj);
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

    public static ASN1Integer getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1Integer)) ? getInstance(object) : new ASN1Integer(ASN1OctetString.getInstance(aSN1TaggedObject.getObject()).getOctets());
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.a.length) + 1 + this.a.length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Integer)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((ASN1Integer) aSN1Primitive).a);
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(2, this.a);
    }

    public BigInteger getPositiveValue() {
        return new BigInteger(1, this.a);
    }

    public BigInteger getValue() {
        return new BigInteger(this.a);
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 != this.a.length; i2++) {
            i ^= (this.a[i2] & UnsignedBytes.MAX_VALUE) << (i2 % 4);
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getValue().toString();
    }
}
