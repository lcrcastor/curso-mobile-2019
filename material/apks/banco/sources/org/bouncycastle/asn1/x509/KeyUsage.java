package org.bouncycastle.asn1.x509;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;

public class KeyUsage extends ASN1Object {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;
    private DERBitString a;

    public KeyUsage(int i) {
        this.a = new DERBitString(i);
    }

    private KeyUsage(DERBitString dERBitString) {
        this.a = dERBitString;
    }

    public static KeyUsage fromExtensions(Extensions extensions) {
        return getInstance(extensions.getExtensionParsedValue(Extension.keyUsage));
    }

    public static KeyUsage getInstance(Object obj) {
        if (obj instanceof KeyUsage) {
            return (KeyUsage) obj;
        }
        if (obj != null) {
            return new KeyUsage(DERBitString.getInstance(obj));
        }
        return null;
    }

    public byte[] getBytes() {
        return this.a.getBytes();
    }

    public int getPadBits() {
        return this.a.getPadBits();
    }

    public boolean hasUsages(int i) {
        return (this.a.intValue() & i) == i;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb;
        byte b;
        byte[] bytes = this.a.getBytes();
        if (bytes.length == 1) {
            sb = new StringBuilder();
            sb.append("KeyUsage: 0x");
            b = bytes[0] & UnsignedBytes.MAX_VALUE;
        } else {
            sb = new StringBuilder();
            sb.append("KeyUsage: 0x");
            b = (bytes[0] & UnsignedBytes.MAX_VALUE) | ((bytes[1] & UnsignedBytes.MAX_VALUE) << 8);
        }
        sb.append(Integer.toHexString(b));
        return sb.toString();
    }
}
