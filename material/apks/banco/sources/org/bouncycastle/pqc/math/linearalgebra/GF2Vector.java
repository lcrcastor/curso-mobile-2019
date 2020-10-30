package org.bouncycastle.pqc.math.linearalgebra;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import cz.msebera.android.httpclient.message.TokenParser;
import java.security.SecureRandom;

public class GF2Vector extends Vector {
    private int[] a;

    public GF2Vector(int i) {
        if (i < 0) {
            throw new ArithmeticException("Negative length.");
        }
        this.length = i;
        this.a = new int[((i + 31) >> 5)];
    }

    public GF2Vector(int i, int i2, SecureRandom secureRandom) {
        if (i2 > i) {
            throw new ArithmeticException("The hamming weight is greater than the length of vector.");
        }
        this.length = i;
        this.a = new int[((i + 31) >> 5)];
        int[] iArr = new int[i];
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3] = i3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            int a2 = RandUtils.a(secureRandom, i);
            setBit(iArr[a2]);
            i--;
            iArr[a2] = iArr[i];
        }
    }

    public GF2Vector(int i, SecureRandom secureRandom) {
        this.length = i;
        int i2 = (i + 31) >> 5;
        this.a = new int[i2];
        int i3 = i2 - 1;
        for (int i4 = i3; i4 >= 0; i4--) {
            this.a[i4] = secureRandom.nextInt();
        }
        int i5 = i & 31;
        if (i5 != 0) {
            int[] iArr = this.a;
            iArr[i3] = ((1 << i5) - 1) & iArr[i3];
        }
    }

    public GF2Vector(int i, int[] iArr) {
        if (i < 0) {
            throw new ArithmeticException("negative length");
        }
        this.length = i;
        int i2 = (i + 31) >> 5;
        if (iArr.length != i2) {
            throw new ArithmeticException("length mismatch");
        }
        this.a = IntUtils.clone(iArr);
        int i3 = i & 31;
        if (i3 != 0) {
            int[] iArr2 = this.a;
            int i4 = i2 - 1;
            iArr2[i4] = ((1 << i3) - 1) & iArr2[i4];
        }
    }

    public GF2Vector(GF2Vector gF2Vector) {
        this.length = gF2Vector.length;
        this.a = IntUtils.clone(gF2Vector.a);
    }

    protected GF2Vector(int[] iArr, int i) {
        this.a = iArr;
        this.length = i;
    }

    public static GF2Vector OS2VP(int i, byte[] bArr) {
        if (i < 0) {
            throw new ArithmeticException("negative length");
        }
        if (bArr.length <= ((i + 7) >> 3)) {
            return new GF2Vector(i, LittleEndianConversions.toIntArray(bArr));
        }
        throw new ArithmeticException("length mismatch");
    }

    public Vector add(Vector vector) {
        if (!(vector instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        }
        GF2Vector gF2Vector = (GF2Vector) vector;
        if (this.length != gF2Vector.length) {
            throw new ArithmeticException("length mismatch");
        }
        int[] clone = IntUtils.clone(gF2Vector.a);
        for (int length = clone.length - 1; length >= 0; length--) {
            clone[length] = clone[length] ^ this.a[length];
        }
        return new GF2Vector(this.length, clone);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof GF2Vector)) {
            return false;
        }
        GF2Vector gF2Vector = (GF2Vector) obj;
        if (this.length == gF2Vector.length && IntUtils.equals(this.a, gF2Vector.a)) {
            z = true;
        }
        return z;
    }

    public GF2Vector extractLeftVector(int i) {
        if (i > this.length) {
            throw new ArithmeticException("invalid length");
        } else if (i == this.length) {
            return new GF2Vector(this);
        } else {
            GF2Vector gF2Vector = new GF2Vector(i);
            int i2 = i >> 5;
            int i3 = i & 31;
            System.arraycopy(this.a, 0, gF2Vector.a, 0, i2);
            if (i3 != 0) {
                gF2Vector.a[i2] = ((1 << i3) - 1) & this.a[i2];
            }
            return gF2Vector;
        }
    }

    public GF2Vector extractRightVector(int i) {
        int i2;
        if (i > this.length) {
            throw new ArithmeticException("invalid length");
        } else if (i == this.length) {
            return new GF2Vector(this);
        } else {
            GF2Vector gF2Vector = new GF2Vector(i);
            int i3 = (this.length - i) >> 5;
            int i4 = (this.length - i) & 31;
            int i5 = (i + 31) >> 5;
            int i6 = 0;
            if (i4 != 0) {
                while (true) {
                    i2 = i5 - 1;
                    if (i6 >= i2) {
                        break;
                    }
                    int i7 = i3 + 1;
                    gF2Vector.a[i6] = (this.a[i3] >>> i4) | (this.a[i7] << (32 - i4));
                    i6++;
                    i3 = i7;
                }
                int i8 = i3 + 1;
                gF2Vector.a[i2] = this.a[i3] >>> i4;
                if (i8 < this.a.length) {
                    int[] iArr = gF2Vector.a;
                    iArr[i2] = iArr[i2] | (this.a[i8] << (32 - i4));
                    return gF2Vector;
                }
            } else {
                System.arraycopy(this.a, i3, gF2Vector.a, 0, i5);
            }
            return gF2Vector;
        }
    }

    public GF2Vector extractVector(int[] iArr) {
        int length = iArr.length;
        if (iArr[length - 1] > this.length) {
            throw new ArithmeticException("invalid index set");
        }
        GF2Vector gF2Vector = new GF2Vector(length);
        for (int i = 0; i < length; i++) {
            if ((this.a[iArr[i] >> 5] & (1 << (iArr[i] & 31))) != 0) {
                int[] iArr2 = gF2Vector.a;
                int i2 = i >> 5;
                iArr2[i2] = (1 << (i & 31)) | iArr2[i2];
            }
        }
        return gF2Vector;
    }

    public int getBit(int i) {
        if (i >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int i2 = i >> 5;
        int i3 = i & 31;
        return (this.a[i2] & (1 << i3)) >>> i3;
    }

    public byte[] getEncoded() {
        return LittleEndianConversions.toByteArray(this.a, (this.length + 7) >> 3);
    }

    public int getHammingWeight() {
        int i = 0;
        int i2 = 0;
        while (i < this.a.length) {
            int i3 = this.a[i];
            int i4 = i2;
            for (int i5 = 0; i5 < 32; i5++) {
                if ((i3 & 1) != 0) {
                    i4++;
                }
                i3 >>>= 1;
            }
            i++;
            i2 = i4;
        }
        return i2;
    }

    public int[] getVecArray() {
        return this.a;
    }

    public int hashCode() {
        return (this.length * 31) + this.a.hashCode();
    }

    public boolean isZero() {
        for (int length = this.a.length - 1; length >= 0; length--) {
            if (this.a[length] != 0) {
                return false;
            }
        }
        return true;
    }

    public Vector multiply(Permutation permutation) {
        int[] vector = permutation.getVector();
        if (this.length != vector.length) {
            throw new ArithmeticException("length mismatch");
        }
        GF2Vector gF2Vector = new GF2Vector(this.length);
        for (int i = 0; i < vector.length; i++) {
            if ((this.a[vector[i] >> 5] & (1 << (vector[i] & 31))) != 0) {
                int[] iArr = gF2Vector.a;
                int i2 = i >> 5;
                iArr[i2] = (1 << (i & 31)) | iArr[i2];
            }
        }
        return gF2Vector;
    }

    public void setBit(int i) {
        if (i >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int[] iArr = this.a;
        int i2 = i >> 5;
        iArr[i2] = (1 << (i & 31)) | iArr[i2];
    }

    public GF2mVector toExtensionFieldVector(GF2mField gF2mField) {
        int degree = gF2mField.getDegree();
        if (this.length % degree != 0) {
            throw new ArithmeticException("conversion is impossible");
        }
        int i = this.length / degree;
        int[] iArr = new int[i];
        int i2 = 0;
        for (int i3 = i - 1; i3 >= 0; i3--) {
            for (int degree2 = gF2mField.getDegree() - 1; degree2 >= 0; degree2--) {
                if (((this.a[i2 >>> 5] >>> (i2 & 31)) & 1) == 1) {
                    iArr[i3] = iArr[i3] ^ (1 << degree2);
                }
                i2++;
            }
        }
        return new GF2mVector(gF2mField, iArr);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.length; i++) {
            if (i != 0 && (i & 31) == 0) {
                stringBuffer.append(TokenParser.SP);
            }
            stringBuffer.append((this.a[i >> 5] & (1 << (i & 31))) == 0 ? TarjetasConstants.ULT_NUM_AMEX : '1');
        }
        return stringBuffer.toString();
    }
}
