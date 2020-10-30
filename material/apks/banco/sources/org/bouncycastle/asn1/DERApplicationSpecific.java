package org.bouncycastle.asn1;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.Arrays;

public class DERApplicationSpecific extends ASN1Primitive {
    private final boolean a;
    private final int b;
    private final byte[] c;

    public DERApplicationSpecific(int i, ASN1Encodable aSN1Encodable) {
        this(true, i, aSN1Encodable);
    }

    public DERApplicationSpecific(int i, ASN1EncodableVector aSN1EncodableVector) {
        this.b = i;
        this.a = true;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (i2 != aSN1EncodableVector.size()) {
            try {
                byteArrayOutputStream.write(((ASN1Object) aSN1EncodableVector.get(i2)).getEncoded(ASN1Encoding.DER));
                i2++;
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("malformed object: ");
                sb.append(e);
                throw new ASN1ParsingException(sb.toString(), e);
            }
        }
        this.c = byteArrayOutputStream.toByteArray();
    }

    public DERApplicationSpecific(int i, byte[] bArr) {
        this(false, i, bArr);
    }

    public DERApplicationSpecific(boolean z, int i, ASN1Encodable aSN1Encodable) {
        ASN1Primitive aSN1Primitive = aSN1Encodable.toASN1Primitive();
        byte[] encoded = aSN1Primitive.getEncoded(ASN1Encoding.DER);
        this.a = z || (aSN1Primitive instanceof ASN1Set) || (aSN1Primitive instanceof ASN1Sequence);
        this.b = i;
        if (z) {
            this.c = encoded;
            return;
        }
        int a2 = a(encoded);
        byte[] bArr = new byte[(encoded.length - a2)];
        System.arraycopy(encoded, a2, bArr, 0, bArr.length);
        this.c = bArr;
    }

    DERApplicationSpecific(boolean z, int i, byte[] bArr) {
        this.a = z;
        this.b = i;
        this.c = bArr;
    }

    private int a(byte[] bArr) {
        byte b2 = bArr[1] & UnsignedBytes.MAX_VALUE;
        if (b2 == 128 || b2 <= Byte.MAX_VALUE) {
            return 2;
        }
        byte b3 = b2 & Ascii.DEL;
        if (b3 <= 4) {
            return b3 + 2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DER length more than 4 bytes: ");
        sb.append(b3);
        throw new IllegalStateException(sb.toString());
    }

    private byte[] a(int i, byte[] bArr) {
        int i2;
        if ((bArr[0] & Ascii.US) == 31) {
            i2 = 2;
            byte b2 = bArr[1] & UnsignedBytes.MAX_VALUE;
            if ((b2 & Ascii.DEL) == 0) {
                throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
            }
            while (b2 >= 0 && (b2 & UnsignedBytes.MAX_POWER_OF_TWO) != 0) {
                int i3 = i2 + 1;
                b2 = bArr[i2] & UnsignedBytes.MAX_VALUE;
                i2 = i3;
            }
        } else {
            i2 = 1;
        }
        byte[] bArr2 = new byte[((bArr.length - i2) + 1)];
        System.arraycopy(bArr, i2, bArr2, 1, bArr2.length - 1);
        bArr2[0] = (byte) i;
        return bArr2;
    }

    public static DERApplicationSpecific getInstance(Object obj) {
        if (obj == null || (obj instanceof DERApplicationSpecific)) {
            return (DERApplicationSpecific) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("failed to construct object from byte[]: ");
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

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.b(this.b) + StreamUtil.a(this.c.length) + this.c.length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        boolean z = false;
        if (!(aSN1Primitive instanceof DERApplicationSpecific)) {
            return false;
        }
        DERApplicationSpecific dERApplicationSpecific = (DERApplicationSpecific) aSN1Primitive;
        if (this.a == dERApplicationSpecific.a && this.b == dERApplicationSpecific.b && Arrays.areEqual(this.c, dERApplicationSpecific.c)) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(this.a ? 96 : 64, this.b, this.c);
    }

    public int getApplicationTag() {
        return this.b;
    }

    public byte[] getContents() {
        return this.c;
    }

    public ASN1Primitive getObject() {
        return new ASN1InputStream(getContents()).readObject();
    }

    public ASN1Primitive getObject(int i) {
        if (i >= 31) {
            throw new IOException("unsupported tag number");
        }
        byte[] encoded = getEncoded();
        byte[] a2 = a(i, encoded);
        if ((encoded[0] & 32) != 0) {
            a2[0] = (byte) (a2[0] | 32);
        }
        return new ASN1InputStream(a2).readObject();
    }

    public int hashCode() {
        return (this.a ^ this.b) ^ Arrays.hashCode(this.c) ? 1 : 0;
    }

    public boolean isConstructed() {
        return this.a;
    }
}
