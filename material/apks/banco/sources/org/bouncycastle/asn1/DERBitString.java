package org.bouncycastle.asn1;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.Streams;

public class DERBitString extends ASN1Primitive implements ASN1String {
    private static final char[] a = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected byte[] data;
    protected int padBits;

    protected DERBitString(byte b, int i) {
        this.data = new byte[1];
        this.data[0] = b;
        this.padBits = i;
    }

    public DERBitString(int i) {
        this.data = getBytes(i);
        this.padBits = getPadBits(i);
    }

    public DERBitString(ASN1Encodable aSN1Encodable) {
        this.data = aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        this.padBits = 0;
    }

    public DERBitString(byte[] bArr) {
        this(bArr, 0);
    }

    public DERBitString(byte[] bArr, int i) {
        this.data = bArr;
        this.padBits = i;
    }

    static DERBitString a(int i, InputStream inputStream) {
        if (i < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        int read = inputStream.read();
        byte[] bArr = new byte[(i - 1)];
        if (bArr.length == 0 || Streams.readFully(inputStream, bArr) == bArr.length) {
            return new DERBitString(bArr, read);
        }
        throw new EOFException("EOF encountered in middle of BIT STRING");
    }

    static DERBitString a(byte[] bArr) {
        if (bArr.length < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        }
        byte b = bArr[0];
        byte[] bArr2 = new byte[(bArr.length - 1)];
        if (bArr2.length != 0) {
            System.arraycopy(bArr, 1, bArr2, 0, bArr.length - 1);
        }
        return new DERBitString(bArr2, (int) b);
    }

    protected static byte[] getBytes(int i) {
        int i2 = 4;
        int i3 = 3;
        while (i3 >= 1 && ((255 << (i3 * 8)) & i) == 0) {
            i2--;
            i3--;
        }
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr[i4] = (byte) ((i >> (i4 * 8)) & 255);
        }
        return bArr;
    }

    public static DERBitString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERBitString)) {
            return (DERBitString) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static DERBitString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof DERBitString)) ? getInstance(object) : a(((ASN1OctetString) object).getOctets());
    }

    protected static int getPadBits(int i) {
        int i2;
        int i3 = 3;
        while (true) {
            if (i3 < 0) {
                i2 = 0;
                break;
            }
            if (i3 != 0) {
                int i4 = i >> (i3 * 8);
                if (i4 != 0) {
                    i2 = i4 & 255;
                    break;
                }
            } else if (i != 0) {
                i2 = i & 255;
                break;
            }
            i3--;
        }
        if (i2 == 0) {
            return 7;
        }
        int i5 = 1;
        while (true) {
            i2 <<= 1;
            if ((i2 & 255) == 0) {
                return 8 - i5;
            }
            i5++;
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return StreamUtil.a(this.data.length + 1) + 1 + this.data.length + 1;
    }

    /* access modifiers changed from: protected */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        boolean z = false;
        if (!(aSN1Primitive instanceof DERBitString)) {
            return false;
        }
        DERBitString dERBitString = (DERBitString) aSN1Primitive;
        if (this.padBits == dERBitString.padBits && Arrays.areEqual(this.data, dERBitString.data)) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        byte[] bArr = new byte[(getBytes().length + 1)];
        bArr[0] = (byte) getPadBits();
        System.arraycopy(getBytes(), 0, bArr, 1, bArr.length - 1);
        aSN1OutputStream.a(3, bArr);
    }

    public byte[] getBytes() {
        return this.data;
    }

    public int getPadBits() {
        return this.padBits;
    }

    public String getString() {
        StringBuffer stringBuffer = new StringBuffer("#");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            for (int i = 0; i != byteArray.length; i++) {
                stringBuffer.append(a[(byteArray[i] >>> 4) & 15]);
                stringBuffer.append(a[byteArray[i] & Ascii.SI]);
            }
            return stringBuffer.toString();
        } catch (IOException unused) {
            throw new RuntimeException("internal error encoding BitString");
        }
    }

    public int hashCode() {
        return this.padBits ^ Arrays.hashCode(this.data);
    }

    public int intValue() {
        int i = 0;
        int i2 = 0;
        while (i != this.data.length && i != 4) {
            i2 |= (this.data[i] & UnsignedBytes.MAX_VALUE) << (i * 8);
            i++;
        }
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }

    public String toString() {
        return getString();
    }
}
