package org.bouncycastle.pqc.math.linearalgebra;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.message.TokenParser;

public class GF2mVector extends Vector {
    private GF2mField a;
    private int[] b;

    public GF2mVector(GF2mField gF2mField, byte[] bArr) {
        this.a = new GF2mField(gF2mField);
        int i = 8;
        int i2 = 1;
        while (gF2mField.getDegree() > i) {
            i2++;
            i += 8;
        }
        if (bArr.length % i2 != 0) {
            throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
        }
        this.length = bArr.length / i2;
        this.b = new int[this.length];
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.b.length) {
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                int[] iArr = this.b;
                int i7 = i5 + 1;
                iArr[i3] = ((bArr[i5] & UnsignedBytes.MAX_VALUE) << i6) | iArr[i3];
                i6 += 8;
                i5 = i7;
            }
            if (!gF2mField.isElementOfThisField(this.b[i3])) {
                throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
            }
            i3++;
            i4 = i5;
        }
    }

    public GF2mVector(GF2mField gF2mField, int[] iArr) {
        this.a = gF2mField;
        this.length = iArr.length;
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (!gF2mField.isElementOfThisField(iArr[length])) {
                throw new ArithmeticException("Element array is not specified over the given finite field.");
            }
        }
        this.b = IntUtils.clone(iArr);
    }

    public GF2mVector(GF2mVector gF2mVector) {
        this.a = new GF2mField(gF2mVector.a);
        this.length = gF2mVector.length;
        this.b = IntUtils.clone(gF2mVector.b);
    }

    public Vector add(Vector vector) {
        throw new RuntimeException("not implemented");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GF2mVector)) {
            return false;
        }
        GF2mVector gF2mVector = (GF2mVector) obj;
        if (!this.a.equals(gF2mVector.a)) {
            return false;
        }
        return IntUtils.equals(this.b, gF2mVector.b);
    }

    public byte[] getEncoded() {
        int i = 8;
        int i2 = 1;
        while (this.a.getDegree() > i) {
            i2++;
            i += 8;
        }
        byte[] bArr = new byte[(this.b.length * i2)];
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.b.length) {
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                int i7 = i5 + 1;
                bArr[i5] = (byte) (this.b[i3] >>> i6);
                i6 += 8;
                i5 = i7;
            }
            i3++;
            i4 = i5;
        }
        return bArr;
    }

    public GF2mField getField() {
        return this.a;
    }

    public int[] getIntArrayForm() {
        return IntUtils.clone(this.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    public boolean isZero() {
        for (int length = this.b.length - 1; length >= 0; length--) {
            if (this.b[length] != 0) {
                return false;
            }
        }
        return true;
    }

    public Vector multiply(Permutation permutation) {
        int[] vector = permutation.getVector();
        if (this.length != vector.length) {
            throw new ArithmeticException("permutation size and vector size mismatch");
        }
        int[] iArr = new int[this.length];
        for (int i = 0; i < vector.length; i++) {
            iArr[i] = this.b[vector[i]];
        }
        return new GF2mVector(this.a, iArr);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.b.length; i++) {
            for (int i2 = 0; i2 < this.a.getDegree(); i2++) {
                stringBuffer.append(((1 << (i2 & 31)) & this.b[i]) != 0 ? '1' : TarjetasConstants.ULT_NUM_AMEX);
            }
            stringBuffer.append(TokenParser.SP);
        }
        return stringBuffer.toString();
    }
}
