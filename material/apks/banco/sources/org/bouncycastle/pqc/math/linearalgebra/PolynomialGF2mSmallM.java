package org.bouncycastle.pqc.math.linearalgebra;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;

public class PolynomialGF2mSmallM {
    public static final char RANDOM_IRREDUCIBLE_POLYNOMIAL = 'I';
    private GF2mField a;
    private int b;
    private int[] c;

    public PolynomialGF2mSmallM(GF2mField gF2mField) {
        this.a = gF2mField;
        this.b = -1;
        this.c = new int[1];
    }

    public PolynomialGF2mSmallM(GF2mField gF2mField, int i) {
        this.a = gF2mField;
        this.b = i;
        this.c = new int[(i + 1)];
        this.c[i] = 1;
    }

    public PolynomialGF2mSmallM(GF2mField gF2mField, int i, char c2, SecureRandom secureRandom) {
        this.a = gF2mField;
        if (c2 != 'I') {
            StringBuilder sb = new StringBuilder();
            sb.append(" Error: type ");
            sb.append(c2);
            sb.append(" is not defined for GF2smallmPolynomial");
            throw new IllegalArgumentException(sb.toString());
        }
        this.c = a(i, secureRandom);
        a();
    }

    public PolynomialGF2mSmallM(GF2mField gF2mField, byte[] bArr) {
        this.a = gF2mField;
        int i = 8;
        int i2 = 1;
        while (gF2mField.getDegree() > i) {
            i2++;
            i += 8;
        }
        if (bArr.length % i2 != 0) {
            throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
        }
        this.c = new int[(bArr.length / i2)];
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.c.length) {
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                int[] iArr = this.c;
                int i7 = i5 + 1;
                iArr[i3] = ((bArr[i5] & UnsignedBytes.MAX_VALUE) << i6) ^ iArr[i3];
                i6 += 8;
                i5 = i7;
            }
            if (!this.a.isElementOfThisField(this.c[i3])) {
                throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
            }
            i3++;
            i4 = i5;
        }
        if (this.c.length == 1 || this.c[this.c.length - 1] != 0) {
            a();
            return;
        }
        throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
    }

    public PolynomialGF2mSmallM(GF2mField gF2mField, int[] iArr) {
        this.a = gF2mField;
        this.c = d(iArr);
        a();
    }

    public PolynomialGF2mSmallM(GF2mVector gF2mVector) {
        this(gF2mVector.getField(), gF2mVector.getIntArrayForm());
    }

    public PolynomialGF2mSmallM(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        this.a = polynomialGF2mSmallM.a;
        this.b = polynomialGF2mSmallM.b;
        this.c = IntUtils.clone(polynomialGF2mSmallM.c);
    }

    private static int a(int[] iArr) {
        int c2 = c(iArr);
        if (c2 == -1) {
            return 0;
        }
        return iArr[c2];
    }

    private void a() {
        int length = this.c.length;
        while (true) {
            this.b = length - 1;
            if (this.b >= 0 && this.c[this.b] == 0) {
                length = this.b;
            } else {
                return;
            }
        }
    }

    private int[] a(int i, SecureRandom secureRandom) {
        int[] iArr = new int[(i + 1)];
        iArr[i] = 1;
        iArr[0] = this.a.getRandomNonZeroElement(secureRandom);
        for (int i2 = 1; i2 < i; i2++) {
            iArr[i2] = this.a.getRandomElement(secureRandom);
        }
        while (!b(iArr)) {
            int a2 = RandUtils.a(secureRandom, i);
            if (a2 == 0) {
                iArr[0] = this.a.getRandomNonZeroElement(secureRandom);
            } else {
                iArr[a2] = this.a.getRandomElement(secureRandom);
            }
        }
        return iArr;
    }

    private int[] a(int[] iArr, int i) {
        int c2 = c(iArr);
        if (c2 == -1 || i == 0) {
            return new int[1];
        }
        if (i == 1) {
            return IntUtils.clone(iArr);
        }
        int[] iArr2 = new int[(c2 + 1)];
        while (c2 >= 0) {
            iArr2[c2] = this.a.mult(iArr[c2], i);
            c2--;
        }
        return iArr2;
    }

    private int[] a(int[] iArr, int[] iArr2) {
        int[] iArr3;
        if (iArr.length < iArr2.length) {
            iArr3 = new int[iArr2.length];
            System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
        } else {
            iArr3 = new int[iArr.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            iArr = iArr2;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            iArr3[length] = this.a.add(iArr3[length], iArr[length]);
        }
        return iArr3;
    }

    private int[] a(int[] iArr, int[] iArr2, int[] iArr3) {
        return e(d(iArr, iArr2), iArr3);
    }

    private boolean b(int[] iArr) {
        if (iArr[0] == 0) {
            return false;
        }
        int c2 = c(iArr) >> 1;
        int[] iArr2 = {0, 1};
        int[] iArr3 = {0, 1};
        int degree = this.a.getDegree();
        int[] iArr4 = iArr2;
        for (int i = 0; i < c2; i++) {
            for (int i2 = degree - 1; i2 >= 0; i2--) {
                iArr4 = a(iArr4, iArr4, iArr);
            }
            iArr4 = d(iArr4);
            if (c(c(a(iArr4, iArr3), iArr)) != 0) {
                return false;
            }
        }
        return true;
    }

    private static int[] b(int[] iArr, int i) {
        int c2 = c(iArr);
        if (c2 == -1) {
            return new int[1];
        }
        int[] iArr2 = new int[(c2 + i + 1)];
        System.arraycopy(iArr, 0, iArr2, i, c2 + 1);
        return iArr2;
    }

    private int[] b(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] d = d(iArr3);
        int[] e = e(iArr2, iArr3);
        int[] iArr4 = {0};
        int[] e2 = e(iArr, iArr3);
        while (c(e) != -1) {
            int[][] b2 = b(d, e);
            int[] d2 = d(e);
            int[] d3 = d(b2[1]);
            int[] a2 = a(iArr4, a(b2[0], e2, iArr3));
            iArr4 = d(e2);
            e2 = d(a2);
            d = d2;
            e = d3;
        }
        return a(iArr4, this.a.inverse(a(d)));
    }

    private int[][] b(int[] iArr, int[] iArr2) {
        int c2 = c(iArr2);
        int c3 = c(iArr) + 1;
        if (c2 == -1) {
            throw new ArithmeticException("Division by zero.");
        }
        int[][] iArr3 = {new int[1], new int[c3]};
        int inverse = this.a.inverse(a(iArr2));
        iArr3[0][0] = 0;
        System.arraycopy(iArr, 0, iArr3[1], 0, iArr3[1].length);
        while (c2 <= c(iArr3[1])) {
            int[] iArr4 = {this.a.mult(a(iArr3[1]), inverse)};
            int c4 = c(iArr3[1]) - c2;
            int[] b2 = b(a(iArr2, iArr4[0]), c4);
            iArr3[0] = a(b(iArr4, c4), iArr3[0]);
            iArr3[1] = a(b2, iArr3[1]);
        }
        return iArr3;
    }

    private static int c(int[] iArr) {
        int length = iArr.length - 1;
        while (length >= 0 && iArr[length] == 0) {
            length--;
        }
        return length;
    }

    private int[] c(int[] iArr, int[] iArr2) {
        if (c(iArr) == -1) {
            return iArr2;
        }
        while (c(iArr2) != -1) {
            int[] e = e(iArr, iArr2);
            int[] iArr3 = new int[iArr2.length];
            System.arraycopy(iArr2, 0, iArr3, 0, iArr3.length);
            iArr2 = new int[e.length];
            System.arraycopy(e, 0, iArr2, 0, iArr2.length);
            iArr = iArr3;
        }
        return a(iArr, this.a.inverse(a(iArr)));
    }

    private static int[] d(int[] iArr) {
        int c2 = c(iArr);
        if (c2 == -1) {
            return new int[1];
        }
        int i = c2 + 1;
        if (iArr.length == i) {
            return IntUtils.clone(iArr);
        }
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    private int[] d(int[] iArr, int[] iArr2) {
        if (c(iArr) < c(iArr2)) {
            int[] iArr3 = iArr2;
            iArr2 = iArr;
            iArr = iArr3;
        }
        int[] d = d(iArr);
        int[] d2 = d(iArr2);
        if (d2.length == 1) {
            return a(d, d2[0]);
        }
        int length = d.length;
        int length2 = d2.length;
        int[] iArr4 = new int[((length + length2) - 1)];
        if (length2 != length) {
            int[] iArr5 = new int[length2];
            int[] iArr6 = new int[(length - length2)];
            System.arraycopy(d, 0, iArr5, 0, iArr5.length);
            System.arraycopy(d, length2, iArr6, 0, iArr6.length);
            return a(d(iArr5, d2), b(d(iArr6, d2), length2));
        }
        int i = (length + 1) >>> 1;
        int i2 = length - i;
        int[] iArr7 = new int[i];
        int[] iArr8 = new int[i];
        int[] iArr9 = new int[i2];
        int[] iArr10 = new int[i2];
        System.arraycopy(d, 0, iArr7, 0, iArr7.length);
        System.arraycopy(d, i, iArr9, 0, iArr9.length);
        System.arraycopy(d2, 0, iArr8, 0, iArr8.length);
        System.arraycopy(d2, i, iArr10, 0, iArr10.length);
        int[] a2 = a(iArr7, iArr9);
        int[] a3 = a(iArr8, iArr10);
        int[] d3 = d(iArr7, iArr8);
        int[] d4 = d(a2, a3);
        int[] d5 = d(iArr9, iArr10);
        return a(b(a(a(a(d4, d3), d5), b(d5, i)), i), d3);
    }

    private int[] e(int[] iArr, int[] iArr2) {
        int c2 = c(iArr2);
        if (c2 == -1) {
            throw new ArithmeticException("Division by zero");
        }
        int[] iArr3 = new int[iArr.length];
        int inverse = this.a.inverse(a(iArr2));
        System.arraycopy(iArr, 0, iArr3, 0, iArr3.length);
        while (c2 <= c(iArr3)) {
            iArr3 = a(a(b(iArr2, c(iArr3) - c2), this.a.mult(a(iArr3), inverse)), iArr3);
        }
        return iArr3;
    }

    private static boolean f(int[] iArr, int[] iArr2) {
        int c2 = c(iArr);
        if (c2 != c(iArr2)) {
            return false;
        }
        for (int i = 0; i <= c2; i++) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public PolynomialGF2mSmallM add(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        return new PolynomialGF2mSmallM(this.a, a(this.c, polynomialGF2mSmallM.c));
    }

    public PolynomialGF2mSmallM addMonomial(int i) {
        int[] iArr = new int[(i + 1)];
        iArr[i] = 1;
        return new PolynomialGF2mSmallM(this.a, a(this.c, iArr));
    }

    public void addToThis(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        this.c = a(this.c, polynomialGF2mSmallM.c);
        a();
    }

    public PolynomialGF2mSmallM[] div(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        int[][] b2 = b(this.c, polynomialGF2mSmallM.c);
        return new PolynomialGF2mSmallM[]{new PolynomialGF2mSmallM(this.a, b2[0]), new PolynomialGF2mSmallM(this.a, b2[1])};
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PolynomialGF2mSmallM)) {
            return false;
        }
        PolynomialGF2mSmallM polynomialGF2mSmallM = (PolynomialGF2mSmallM) obj;
        if (this.a.equals(polynomialGF2mSmallM.a) && this.b == polynomialGF2mSmallM.b && f(this.c, polynomialGF2mSmallM.c)) {
            return true;
        }
        return false;
    }

    public int evaluateAt(int i) {
        int i2 = this.c[this.b];
        for (int i3 = this.b - 1; i3 >= 0; i3--) {
            i2 = this.a.mult(i2, i) ^ this.c[i3];
        }
        return i2;
    }

    public PolynomialGF2mSmallM gcd(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        return new PolynomialGF2mSmallM(this.a, c(this.c, polynomialGF2mSmallM.c));
    }

    public int getCoefficient(int i) {
        if (i < 0 || i > this.b) {
            return 0;
        }
        return this.c[i];
    }

    public int getDegree() {
        int length = this.c.length - 1;
        if (this.c[length] == 0) {
            return -1;
        }
        return length;
    }

    public byte[] getEncoded() {
        int i = 8;
        int i2 = 1;
        while (this.a.getDegree() > i) {
            i2++;
            i += 8;
        }
        byte[] bArr = new byte[(this.c.length * i2)];
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.c.length) {
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                int i7 = i5 + 1;
                bArr[i5] = (byte) (this.c[i3] >>> i6);
                i6 += 8;
                i5 = i7;
            }
            i3++;
            i4 = i5;
        }
        return bArr;
    }

    public int getHeadCoefficient() {
        if (this.b == -1) {
            return 0;
        }
        return this.c[this.b];
    }

    public int hashCode() {
        int hashCode = this.a.hashCode();
        for (int i : this.c) {
            hashCode = (hashCode * 31) + i;
        }
        return hashCode;
    }

    public PolynomialGF2mSmallM mod(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        return new PolynomialGF2mSmallM(this.a, e(this.c, polynomialGF2mSmallM.c));
    }

    public PolynomialGF2mSmallM modDiv(PolynomialGF2mSmallM polynomialGF2mSmallM, PolynomialGF2mSmallM polynomialGF2mSmallM2) {
        return new PolynomialGF2mSmallM(this.a, b(this.c, polynomialGF2mSmallM.c, polynomialGF2mSmallM2.c));
    }

    public PolynomialGF2mSmallM modInverse(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        return new PolynomialGF2mSmallM(this.a, b(new int[]{1}, this.c, polynomialGF2mSmallM.c));
    }

    public PolynomialGF2mSmallM modMultiply(PolynomialGF2mSmallM polynomialGF2mSmallM, PolynomialGF2mSmallM polynomialGF2mSmallM2) {
        return new PolynomialGF2mSmallM(this.a, a(this.c, polynomialGF2mSmallM.c, polynomialGF2mSmallM2.c));
    }

    public PolynomialGF2mSmallM[] modPolynomialToFracton(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        int i = polynomialGF2mSmallM.b >> 1;
        int[] d = d(polynomialGF2mSmallM.c);
        int[] iArr = {0};
        int[] iArr2 = {1};
        int[] e = e(this.c, polynomialGF2mSmallM.c);
        int[] iArr3 = d;
        int[] iArr4 = e;
        while (c(iArr4) > i) {
            int[][] b2 = b(iArr3, iArr4);
            int[] iArr5 = b2[1];
            int[] a2 = a(iArr, a(b2[0], iArr2, polynomialGF2mSmallM.c));
            iArr = iArr2;
            iArr2 = a2;
            iArr3 = iArr4;
            iArr4 = iArr5;
        }
        return new PolynomialGF2mSmallM[]{new PolynomialGF2mSmallM(this.a, iArr4), new PolynomialGF2mSmallM(this.a, iArr2)};
    }

    public PolynomialGF2mSmallM modSquareMatrix(PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        int length = polynomialGF2mSmallMArr.length;
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        for (int i = 0; i < this.c.length; i++) {
            iArr2[i] = this.a.mult(this.c[i], this.c[i]);
        }
        for (int i2 = 0; i2 < length; i2++) {
            for (int i3 = 0; i3 < length; i3++) {
                if (i2 < polynomialGF2mSmallMArr[i3].c.length) {
                    iArr[i2] = this.a.add(iArr[i2], this.a.mult(polynomialGF2mSmallMArr[i3].c[i2], iArr2[i3]));
                }
            }
        }
        return new PolynomialGF2mSmallM(this.a, iArr);
    }

    public PolynomialGF2mSmallM modSquareRoot(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        int[] clone = IntUtils.clone(this.c);
        while (true) {
            int[] a2 = a(clone, clone, polynomialGF2mSmallM.c);
            if (f(a2, this.c)) {
                return new PolynomialGF2mSmallM(this.a, clone);
            }
            clone = d(a2);
        }
    }

    public PolynomialGF2mSmallM modSquareRootMatrix(PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        int length = polynomialGF2mSmallMArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                if (i < polynomialGF2mSmallMArr[i2].c.length && i2 < this.c.length) {
                    iArr[i] = this.a.add(iArr[i], this.a.mult(polynomialGF2mSmallMArr[i2].c[i], this.c[i2]));
                }
            }
        }
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.a.sqRoot(iArr[i3]);
        }
        return new PolynomialGF2mSmallM(this.a, iArr);
    }

    public void multThisWithElement(int i) {
        if (!this.a.isElementOfThisField(i)) {
            throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
        }
        this.c = a(this.c, i);
        a();
    }

    public PolynomialGF2mSmallM multWithElement(int i) {
        if (!this.a.isElementOfThisField(i)) {
            throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
        }
        return new PolynomialGF2mSmallM(this.a, a(this.c, i));
    }

    public PolynomialGF2mSmallM multWithMonomial(int i) {
        return new PolynomialGF2mSmallM(this.a, b(this.c, i));
    }

    public PolynomialGF2mSmallM multiply(PolynomialGF2mSmallM polynomialGF2mSmallM) {
        return new PolynomialGF2mSmallM(this.a, d(this.c, polynomialGF2mSmallM.c));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Polynomial over ");
        sb.append(this.a.toString());
        sb.append(": \n");
        String sb2 = sb.toString();
        for (int i = 0; i < this.c.length; i++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(this.a.elementToStr(this.c[i]));
            sb3.append("Y^");
            sb3.append(i);
            sb3.append(Constants.SYMBOL_POSITIVE);
            sb2 = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append(";");
        return sb4.toString();
    }
}
