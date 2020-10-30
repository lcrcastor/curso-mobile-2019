package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.StringTokenizer;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.IPAddress;

public class GeneralName extends ASN1Object implements ASN1Choice {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    private ASN1Encodable a;
    private int b;

    public GeneralName(int i, String str) {
        ASN1Encodable aSN1Encodable;
        this.b = i;
        if (i == 1 || i == 2 || i == 6) {
            aSN1Encodable = new DERIA5String(str);
        } else if (i == 8) {
            aSN1Encodable = new ASN1ObjectIdentifier(str);
        } else if (i == 4) {
            aSN1Encodable = new X500Name(str);
        } else if (i == 7) {
            byte[] a2 = a(str);
            if (a2 != null) {
                this.a = new DEROctetString(a2);
                return;
            }
            throw new IllegalArgumentException("IP Address is invalid");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("can't process String for tag: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = aSN1Encodable;
    }

    public GeneralName(int i, ASN1Encodable aSN1Encodable) {
        this.a = aSN1Encodable;
        this.b = i;
    }

    public GeneralName(X500Name x500Name) {
        this.a = x500Name;
        this.b = 4;
    }

    public GeneralName(X509Name x509Name) {
        this.a = X500Name.getInstance(x509Name);
        this.b = 4;
    }

    private void a(String str, byte[] bArr, int i) {
        int parseInt = Integer.parseInt(str);
        for (int i2 = 0; i2 != parseInt; i2++) {
            int i3 = (i2 / 8) + i;
            bArr[i3] = (byte) (bArr[i3] | (1 << (7 - (i2 % 8))));
        }
    }

    private void a(int[] iArr, byte[] bArr, int i) {
        for (int i2 = 0; i2 != iArr.length; i2++) {
            int i3 = i2 * 2;
            bArr[i3 + i] = (byte) (iArr[i2] >> 8);
            bArr[i3 + 1 + i] = (byte) iArr[i2];
        }
    }

    private byte[] a(String str) {
        if (IPAddress.isValidIPv6WithNetmask(str) || IPAddress.isValidIPv6(str)) {
            int indexOf = str.indexOf(47);
            if (indexOf < 0) {
                byte[] bArr = new byte[16];
                a(c(str), bArr, 0);
                return bArr;
            }
            byte[] bArr2 = new byte[32];
            a(c(str.substring(0, indexOf)), bArr2, 0);
            String substring = str.substring(indexOf + 1);
            a(substring.indexOf(58) > 0 ? c(substring) : b(substring), bArr2, 16);
            return bArr2;
        } else if (!IPAddress.isValidIPv4WithNetmask(str) && !IPAddress.isValidIPv4(str)) {
            return null;
        } else {
            int indexOf2 = str.indexOf(47);
            if (indexOf2 < 0) {
                byte[] bArr3 = new byte[4];
                b(str, bArr3, 0);
                return bArr3;
            }
            byte[] bArr4 = new byte[8];
            b(str.substring(0, indexOf2), bArr4, 0);
            String substring2 = str.substring(indexOf2 + 1);
            if (substring2.indexOf(46) > 0) {
                b(substring2, bArr4, 4);
                return bArr4;
            }
            a(substring2, bArr4, 4);
            return bArr4;
        }
    }

    private void b(String str, byte[] bArr, int i) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "./");
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int i3 = i2 + 1;
            bArr[i2 + i] = (byte) Integer.parseInt(stringTokenizer.nextToken());
            i2 = i3;
        }
    }

    private int[] b(String str) {
        int[] iArr = new int[8];
        int parseInt = Integer.parseInt(str);
        for (int i = 0; i != parseInt; i++) {
            int i2 = i / 16;
            iArr[i2] = iArr[i2] | (1 << (15 - (i % 16)));
        }
        return iArr;
    }

    private int[] c(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":", true);
        int[] iArr = new int[8];
        if (str.charAt(0) == ':' && str.charAt(1) == ':') {
            stringTokenizer.nextToken();
        }
        int i = 0;
        int i2 = -1;
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals(":")) {
                int i3 = i + 1;
                iArr[i] = 0;
                int i4 = i3;
                i2 = i;
                i = i4;
            } else if (nextToken.indexOf(46) < 0) {
                int i5 = i + 1;
                iArr[i] = Integer.parseInt(nextToken, 16);
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken();
                }
                i = i5;
            } else {
                StringTokenizer stringTokenizer2 = new StringTokenizer(nextToken, ".");
                int i6 = i + 1;
                iArr[i] = (Integer.parseInt(stringTokenizer2.nextToken()) << 8) | Integer.parseInt(stringTokenizer2.nextToken());
                i = i6 + 1;
                iArr[i6] = Integer.parseInt(stringTokenizer2.nextToken()) | (Integer.parseInt(stringTokenizer2.nextToken()) << 8);
            }
        }
        if (i != iArr.length) {
            int i7 = i - i2;
            System.arraycopy(iArr, i2, iArr, iArr.length - i7, i7);
            while (i2 != iArr.length - i7) {
                iArr[i2] = 0;
                i2++;
            }
        }
        return iArr;
    }

    public static GeneralName getInstance(Object obj) {
        if (obj == null || (obj instanceof GeneralName)) {
            return (GeneralName) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) obj;
            int tagNo = aSN1TaggedObject.getTagNo();
            switch (tagNo) {
                case 0:
                    return new GeneralName(tagNo, (ASN1Encodable) ASN1Sequence.getInstance(aSN1TaggedObject, false));
                case 1:
                    return new GeneralName(tagNo, (ASN1Encodable) DERIA5String.getInstance(aSN1TaggedObject, false));
                case 2:
                    return new GeneralName(tagNo, (ASN1Encodable) DERIA5String.getInstance(aSN1TaggedObject, false));
                case 3:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag: ");
                    sb.append(tagNo);
                    throw new IllegalArgumentException(sb.toString());
                case 4:
                    return new GeneralName(tagNo, (ASN1Encodable) X500Name.getInstance(aSN1TaggedObject, true));
                case 5:
                    return new GeneralName(tagNo, (ASN1Encodable) ASN1Sequence.getInstance(aSN1TaggedObject, false));
                case 6:
                    return new GeneralName(tagNo, (ASN1Encodable) DERIA5String.getInstance(aSN1TaggedObject, false));
                case 7:
                    return new GeneralName(tagNo, (ASN1Encodable) ASN1OctetString.getInstance(aSN1TaggedObject, false));
                case 8:
                    return new GeneralName(tagNo, (ASN1Encodable) ASN1ObjectIdentifier.getInstance(aSN1TaggedObject, false));
            }
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException unused) {
                throw new IllegalArgumentException("unable to parse encoded general name");
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unknown object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static GeneralName getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1TaggedObject.getInstance(aSN1TaggedObject, true));
    }

    public ASN1Encodable getName() {
        return this.a;
    }

    public int getTagNo() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b == 4 ? new DERTaggedObject(true, this.b, this.a) : new DERTaggedObject(false, this.b, this.a);
    }

    public String toString() {
        String x500Name;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.b);
        stringBuffer.append(": ");
        int i = this.b;
        if (i != 4) {
            if (i != 6) {
                switch (i) {
                    case 1:
                    case 2:
                        break;
                    default:
                        x500Name = this.a.toString();
                        break;
                }
            }
            x500Name = DERIA5String.getInstance(this.a).getString();
        } else {
            x500Name = X500Name.getInstance(this.a).toString();
        }
        stringBuffer.append(x500Name);
        return stringBuffer.toString();
    }
}
