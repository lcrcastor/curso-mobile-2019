package org.bouncycastle.crypto.engines;

import java.math.BigInteger;

public class CramerShoupCiphertext {
    BigInteger a;
    BigInteger b;
    BigInteger c;
    BigInteger d;

    public CramerShoupCiphertext() {
    }

    public CramerShoupCiphertext(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.a = bigInteger;
        this.b = bigInteger2;
        this.c = bigInteger3;
        this.d = bigInteger4;
    }

    public CramerShoupCiphertext(byte[] bArr) {
        byte[] bArr2 = new byte[4];
        System.arraycopy(bArr, 0, bArr2, 0, 4);
        int a2 = a(bArr2);
        byte[] bArr3 = new byte[a2];
        System.arraycopy(bArr, 4, bArr3, 0, a2);
        int i = a2 + 4;
        this.a = new BigInteger(bArr3);
        System.arraycopy(bArr, i, bArr2, 0, 4);
        int a3 = a(bArr2);
        byte[] bArr4 = new byte[a3];
        int i2 = i + 4;
        System.arraycopy(bArr, i2, bArr4, 0, a3);
        int i3 = i2 + a3;
        this.b = new BigInteger(bArr4);
        System.arraycopy(bArr, i3, bArr2, 0, 4);
        int a4 = a(bArr2);
        byte[] bArr5 = new byte[a4];
        int i4 = i3 + 4;
        System.arraycopy(bArr, i4, bArr5, 0, a4);
        int i5 = i4 + a4;
        this.c = new BigInteger(bArr5);
        System.arraycopy(bArr, i5, bArr2, 0, 4);
        int a5 = a(bArr2);
        byte[] bArr6 = new byte[a5];
        System.arraycopy(bArr, i5 + 4, bArr6, 0, a5);
        this.d = new BigInteger(bArr6);
    }

    private int a(byte[] bArr) {
        if (bArr.length != 4) {
            return -1;
        }
        int i = 0;
        for (int i2 = 3; i2 >= 0; i2--) {
            i += bArr[i2] << ((3 - i2) * 8);
        }
        return i;
    }

    private byte[] a(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[3 - i2] = (byte) (i >>> (i2 * 8));
        }
        return bArr;
    }

    public BigInteger getE() {
        return this.c;
    }

    public BigInteger getU1() {
        return this.a;
    }

    public BigInteger getU2() {
        return this.b;
    }

    public BigInteger getV() {
        return this.d;
    }

    public void setE(BigInteger bigInteger) {
        this.c = bigInteger;
    }

    public void setU1(BigInteger bigInteger) {
        this.a = bigInteger;
    }

    public void setU2(BigInteger bigInteger) {
        this.b = bigInteger;
    }

    public void setV(BigInteger bigInteger) {
        this.d = bigInteger;
    }

    public byte[] toByteArray() {
        byte[] byteArray = this.a.toByteArray();
        int length = byteArray.length;
        byte[] byteArray2 = this.b.toByteArray();
        int length2 = byteArray2.length;
        byte[] byteArray3 = this.c.toByteArray();
        int length3 = byteArray3.length;
        byte[] byteArray4 = this.d.toByteArray();
        int length4 = byteArray4.length;
        byte[] bArr = new byte[(length + length2 + length3 + length4 + 16)];
        System.arraycopy(a(length), 0, bArr, 0, 4);
        System.arraycopy(byteArray, 0, bArr, 4, length);
        int i = length + 4;
        System.arraycopy(a(length2), 0, bArr, i, 4);
        int i2 = i + 4;
        System.arraycopy(byteArray2, 0, bArr, i2, length2);
        int i3 = i2 + length2;
        System.arraycopy(a(length3), 0, bArr, i3, 4);
        int i4 = i3 + 4;
        System.arraycopy(byteArray3, 0, bArr, i4, length3);
        int i5 = i4 + length3;
        System.arraycopy(a(length4), 0, bArr, i5, 4);
        System.arraycopy(byteArray4, 0, bArr, i5 + 4, length4);
        return bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append("u1: ");
        sb.append(this.a.toString());
        stringBuffer.append(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\nu2: ");
        sb2.append(this.b.toString());
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("\ne: ");
        sb3.append(this.c.toString());
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("\nv: ");
        sb4.append(this.d.toString());
        stringBuffer.append(sb4.toString());
        return stringBuffer.toString();
    }
}
