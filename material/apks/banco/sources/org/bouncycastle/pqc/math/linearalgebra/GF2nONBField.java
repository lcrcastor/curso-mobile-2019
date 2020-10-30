package org.bouncycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Vector;

public class GF2nONBField extends GF2nField {
    int[][] a;
    private int b;
    private int c;
    private int d;

    public GF2nONBField(int i) {
        if (i < 3) {
            throw new IllegalArgumentException("k must be at least 3");
        }
        this.mDegree = i;
        this.b = this.mDegree / 64;
        this.c = this.mDegree & 63;
        if (this.c == 0) {
            this.c = 64;
        } else {
            this.b++;
        }
        c();
        if (this.d < 3) {
            this.a = (int[][]) Array.newInstance(int.class, new int[]{this.mDegree, 2});
            for (int i2 = 0; i2 < this.mDegree; i2++) {
                this.a[i2][0] = -1;
                this.a[i2][1] = -1;
            }
            d();
            computeFieldPolynomial();
            this.fields = new Vector();
            this.matrices = new Vector();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nThe type of this field is ");
        sb.append(this.d);
        throw new RuntimeException(sb.toString());
    }

    private int a(int i, int i2) {
        int order;
        Random random = new Random();
        int i3 = 0;
        while (i3 == 0) {
            int i4 = i2 - 1;
            i3 = random.nextInt() % i4;
            if (i3 < 0) {
                i3 += i4;
            }
        }
        while (true) {
            order = IntegerFunctions.order(i3, i2);
            if (order % i == 0 && order != 0) {
                break;
            }
            while (i3 == 0) {
                int i5 = i2 - 1;
                int nextInt = random.nextInt() % i5;
                if (nextInt < 0) {
                    nextInt += i5;
                }
            }
        }
        int i6 = i3;
        for (int i7 = 2; i7 <= i / order; i7++) {
            i6 *= i3;
        }
        return i6;
    }

    private void c() {
        if ((this.mDegree & 7) == 0) {
            throw new RuntimeException("The extension degree is divisible by 8!");
        }
        this.d = 1;
        int i = 0;
        while (i != 1) {
            int i2 = (this.d * this.mDegree) + 1;
            if (IntegerFunctions.isPrime(i2)) {
                i = IntegerFunctions.gcd((this.d * this.mDegree) / IntegerFunctions.order(2, i2), this.mDegree);
            }
            this.d++;
        }
        this.d--;
        if (this.d == 1) {
            int i3 = (this.mDegree << 1) + 1;
            if (IntegerFunctions.isPrime(i3)) {
                if (IntegerFunctions.gcd((this.mDegree << 1) / IntegerFunctions.order(2, i3), this.mDegree) == 1) {
                    this.d++;
                }
            }
        }
    }

    private void d() {
        if ((this.d & 7) != 0) {
            int i = (this.d * this.mDegree) + 1;
            int[] iArr = new int[i];
            int i2 = this.d == 1 ? 1 : this.d == 2 ? i - 1 : a(this.d, i);
            int i3 = 1;
            for (int i4 = 0; i4 < this.d; i4++) {
                int i5 = i3;
                for (int i6 = 0; i6 < this.mDegree; i6++) {
                    iArr[i5] = i6;
                    i5 = (i5 << 1) % i;
                    if (i5 < 0) {
                        i5 += i;
                    }
                }
                i3 = (i3 * i2) % i;
                if (i3 < 0) {
                    i3 += i;
                }
            }
            if (this.d == 1) {
                int i7 = 1;
                while (i7 < i - 1) {
                    int i8 = i7 + 1;
                    if (this.a[iArr[i8]][0] == -1) {
                        this.a[iArr[i8]][0] = iArr[i - i7];
                    } else {
                        this.a[iArr[i8]][1] = iArr[i - i7];
                    }
                    i7 = i8;
                }
                int i9 = this.mDegree >> 1;
                for (int i10 = 1; i10 <= i9; i10++) {
                    int i11 = i10 - 1;
                    if (this.a[i11][0] == -1) {
                        this.a[i11][0] = (i9 + i10) - 1;
                    } else {
                        this.a[i11][1] = (i9 + i10) - 1;
                    }
                    int i12 = (i9 + i10) - 1;
                    if (this.a[i12][0] == -1) {
                        this.a[i12][0] = i11;
                    } else {
                        this.a[i12][1] = i11;
                    }
                }
            } else if (this.d == 2) {
                int i13 = 1;
                while (i13 < i - 1) {
                    int i14 = i13 + 1;
                    if (this.a[iArr[i14]][0] == -1) {
                        this.a[iArr[i14]][0] = iArr[i - i13];
                    } else {
                        this.a[iArr[i14]][1] = iArr[i - i13];
                    }
                    i13 = i14;
                }
            } else {
                throw new RuntimeException("only type 1 or type 2 implemented");
            }
        } else {
            throw new RuntimeException("bisher nur fuer Gausssche Normalbasen implementiert");
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void computeCOBMatrix(GF2nField gF2nField) {
        GF2nElement randomRoot;
        if (this.mDegree != gF2nField.mDegree) {
            throw new IllegalArgumentException("GF2nField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
        }
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[this.mDegree];
        for (int i = 0; i < this.mDegree; i++) {
            gF2PolynomialArr[i] = new GF2Polynomial(this.mDegree);
        }
        do {
            randomRoot = gF2nField.getRandomRoot(this.fieldPolynomial);
        } while (randomRoot.isZero());
        GF2nElement[] gF2nElementArr = new GF2nPolynomialElement[this.mDegree];
        gF2nElementArr[0] = (GF2nElement) randomRoot.clone();
        for (int i2 = 1; i2 < this.mDegree; i2++) {
            gF2nElementArr[i2] = gF2nElementArr[i2 - 1].square();
        }
        for (int i3 = 0; i3 < this.mDegree; i3++) {
            for (int i4 = 0; i4 < this.mDegree; i4++) {
                if (gF2nElementArr[i3].a(i4)) {
                    gF2PolynomialArr[(this.mDegree - i4) - 1].setBit((this.mDegree - i3) - 1);
                }
            }
        }
        this.fields.addElement(gF2nField);
        this.matrices.addElement(gF2PolynomialArr);
        gF2nField.fields.addElement(this);
        gF2nField.matrices.addElement(invertMatrix(gF2PolynomialArr));
    }

    /* access modifiers changed from: protected */
    public void computeFieldPolynomial() {
        int i = 1;
        if (this.d == 1) {
            this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1, "ALL");
            return;
        }
        if (this.d == 2) {
            GF2Polynomial gF2Polynomial = new GF2Polynomial(this.mDegree + 1, "ONE");
            GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.mDegree + 1, "X");
            gF2Polynomial2.addToThis(gF2Polynomial);
            while (i < this.mDegree) {
                GF2Polynomial shiftLeft = gF2Polynomial2.shiftLeft();
                shiftLeft.addToThis(gF2Polynomial);
                i++;
                gF2Polynomial = gF2Polynomial2;
                gF2Polynomial2 = shiftLeft;
            }
            this.fieldPolynomial = gF2Polynomial2;
        }
    }

    /* access modifiers changed from: protected */
    public GF2nElement getRandomRoot(GF2Polynomial gF2Polynomial) {
        GF2nPolynomial gcd;
        int degree;
        int degree2;
        GF2nPolynomial gF2nPolynomial = new GF2nPolynomial(gF2Polynomial, (GF2nField) this);
        while (gF2nPolynomial.getDegree() > 1) {
            while (true) {
                GF2nONBElement gF2nONBElement = new GF2nONBElement(this, new Random());
                GF2nPolynomial gF2nPolynomial2 = new GF2nPolynomial(2, (GF2nElement) GF2nONBElement.ZERO(this));
                gF2nPolynomial2.set(1, gF2nONBElement);
                GF2nPolynomial gF2nPolynomial3 = new GF2nPolynomial(gF2nPolynomial2);
                for (int i = 1; i <= this.mDegree - 1; i++) {
                    gF2nPolynomial3 = gF2nPolynomial3.multiplyAndReduce(gF2nPolynomial3, gF2nPolynomial).add(gF2nPolynomial2);
                }
                gcd = gF2nPolynomial3.gcd(gF2nPolynomial);
                degree = gcd.getDegree();
                degree2 = gF2nPolynomial.getDegree();
                if (degree != 0 && degree != degree2) {
                    break;
                }
            }
            gF2nPolynomial = (degree << 1) > degree2 ? gF2nPolynomial.quotient(gcd) : new GF2nPolynomial(gcd);
        }
        return gF2nPolynomial.at(0);
    }
}
