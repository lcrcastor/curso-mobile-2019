package org.bouncycastle.pqc.math.linearalgebra;

import java.util.Random;
import java.util.Vector;

public class GF2nPolynomialField extends GF2nField {
    GF2Polynomial[] a;
    private boolean b = false;
    private boolean c = false;
    private int d;
    private int[] e = new int[3];

    public GF2nPolynomialField(int i) {
        if (i < 3) {
            throw new IllegalArgumentException("k must be at least 3");
        }
        this.mDegree = i;
        computeFieldPolynomial();
        a();
        this.fields = new Vector();
        this.matrices = new Vector();
    }

    public GF2nPolynomialField(int i, GF2Polynomial gF2Polynomial) {
        if (i < 3) {
            throw new IllegalArgumentException("degree must be at least 3");
        } else if (gF2Polynomial.getLength() != i + 1) {
            throw new RuntimeException();
        } else if (!gF2Polynomial.isIrreducible()) {
            throw new RuntimeException();
        } else {
            this.mDegree = i;
            this.fieldPolynomial = gF2Polynomial;
            a();
            int i2 = 2;
            for (int i3 = 1; i3 < this.fieldPolynomial.getLength() - 1; i3++) {
                if (this.fieldPolynomial.testBit(i3)) {
                    i2++;
                    if (i2 == 3) {
                        this.d = i3;
                    }
                    if (i2 <= 5) {
                        this.e[i2 - 3] = i3;
                    }
                }
            }
            if (i2 == 3) {
                this.b = true;
            }
            if (i2 == 5) {
                this.c = true;
            }
            this.fields = new Vector();
            this.matrices = new Vector();
        }
    }

    public GF2nPolynomialField(int i, boolean z) {
        if (i < 3) {
            throw new IllegalArgumentException("k must be at least 3");
        }
        this.mDegree = i;
        if (z) {
            computeFieldPolynomial();
        } else {
            computeFieldPolynomial2();
        }
        a();
        this.fields = new Vector();
        this.matrices = new Vector();
    }

    private void a() {
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[(this.mDegree - 1)];
        this.a = new GF2Polynomial[this.mDegree];
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = new GF2Polynomial(this.mDegree, "ZERO");
        }
        for (int i2 = 0; i2 < this.mDegree - 1; i2++) {
            gF2PolynomialArr[i2] = new GF2Polynomial(1, "ONE").shiftLeft(this.mDegree + i2).remainder(this.fieldPolynomial);
        }
        for (int i3 = 1; i3 <= Math.abs(this.mDegree >> 1); i3++) {
            for (int i4 = 1; i4 <= this.mDegree; i4++) {
                if (gF2PolynomialArr[this.mDegree - (i3 << 1)].testBit(this.mDegree - i4)) {
                    this.a[i4 - 1].setBit(this.mDegree - i3);
                }
            }
        }
        for (int abs = Math.abs(this.mDegree >> 1) + 1; abs <= this.mDegree; abs++) {
            this.a[((abs << 1) - this.mDegree) - 1].setBit(this.mDegree - abs);
        }
    }

    private boolean b() {
        this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
        boolean z = false;
        this.fieldPolynomial.setBit(0);
        this.fieldPolynomial.setBit(this.mDegree);
        for (int i = 1; i < this.mDegree && !z; i++) {
            this.fieldPolynomial.setBit(i);
            boolean isIrreducible = this.fieldPolynomial.isIrreducible();
            if (isIrreducible) {
                this.b = true;
                this.d = i;
                return isIrreducible;
            }
            this.fieldPolynomial.resetBit(i);
            z = this.fieldPolynomial.isIrreducible();
        }
        return z;
    }

    private boolean c() {
        this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
        this.fieldPolynomial.setBit(0);
        this.fieldPolynomial.setBit(this.mDegree);
        int i = 1;
        boolean z = false;
        while (i <= this.mDegree - 3 && !z) {
            this.fieldPolynomial.setBit(i);
            int i2 = i + 1;
            boolean z2 = z;
            int i3 = i2;
            while (i3 <= this.mDegree - 2 && !z2) {
                this.fieldPolynomial.setBit(i3);
                int i4 = i3 + 1;
                boolean z3 = z2;
                for (int i5 = i4; i5 <= this.mDegree - 1 && !z3; i5++) {
                    this.fieldPolynomial.setBit(i5);
                    if ((((this.mDegree & 1) != 0) | ((i & 1) != 0) | ((i3 & 1) != 0)) || ((i5 & 1) != 0)) {
                        z3 = this.fieldPolynomial.isIrreducible();
                        if (z3) {
                            this.c = true;
                            this.e[0] = i;
                            this.e[1] = i3;
                            this.e[2] = i5;
                            return z3;
                        }
                    }
                    this.fieldPolynomial.resetBit(i5);
                }
                this.fieldPolynomial.resetBit(i3);
                i3 = i4;
                z2 = z3;
            }
            this.fieldPolynomial.resetBit(i);
            i = i2;
            z = z2;
        }
        return z;
    }

    private boolean d() {
        this.fieldPolynomial = new GF2Polynomial(this.mDegree + 1);
        do {
            this.fieldPolynomial.randomize();
            this.fieldPolynomial.setBit(this.mDegree);
            this.fieldPolynomial.setBit(0);
        } while (!this.fieldPolynomial.isIrreducible());
        return true;
    }

    /* access modifiers changed from: protected */
    public void computeCOBMatrix(GF2nField gF2nField) {
        GF2nElement randomRoot;
        GF2nElement[] gF2nElementArr;
        if (this.mDegree != gF2nField.mDegree) {
            throw new IllegalArgumentException("GF2nPolynomialField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
        }
        boolean z = gF2nField instanceof GF2nONBField;
        if (z) {
            gF2nField.computeCOBMatrix(this);
            return;
        }
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[this.mDegree];
        for (int i = 0; i < this.mDegree; i++) {
            gF2PolynomialArr[i] = new GF2Polynomial(this.mDegree);
        }
        do {
            randomRoot = gF2nField.getRandomRoot(this.fieldPolynomial);
        } while (randomRoot.isZero());
        if (randomRoot instanceof GF2nONBElement) {
            gF2nElementArr = new GF2nONBElement[this.mDegree];
            gF2nElementArr[this.mDegree - 1] = GF2nONBElement.ONE((GF2nONBField) gF2nField);
        } else {
            gF2nElementArr = new GF2nPolynomialElement[this.mDegree];
            gF2nElementArr[this.mDegree - 1] = GF2nPolynomialElement.ONE((GF2nPolynomialField) gF2nField);
        }
        gF2nElementArr[this.mDegree - 2] = randomRoot;
        for (int i2 = this.mDegree - 3; i2 >= 0; i2--) {
            gF2nElementArr[i2] = (GF2nElement) gF2nElementArr[i2 + 1].multiply(randomRoot);
        }
        if (z) {
            for (int i3 = 0; i3 < this.mDegree; i3++) {
                for (int i4 = 0; i4 < this.mDegree; i4++) {
                    if (gF2nElementArr[i3].a((this.mDegree - i4) - 1)) {
                        gF2PolynomialArr[(this.mDegree - i4) - 1].setBit((this.mDegree - i3) - 1);
                    }
                }
            }
        } else {
            for (int i5 = 0; i5 < this.mDegree; i5++) {
                for (int i6 = 0; i6 < this.mDegree; i6++) {
                    if (gF2nElementArr[i5].a(i6)) {
                        gF2PolynomialArr[(this.mDegree - i6) - 1].setBit((this.mDegree - i5) - 1);
                    }
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
        if (!b() && !c()) {
            d();
        }
    }

    /* access modifiers changed from: protected */
    public void computeFieldPolynomial2() {
        if (!b() && !c()) {
            d();
        }
    }

    public int[] getPc() {
        if (!this.c) {
            throw new RuntimeException();
        }
        int[] iArr = new int[3];
        System.arraycopy(this.e, 0, iArr, 0, 3);
        return iArr;
    }

    /* access modifiers changed from: protected */
    public GF2nElement getRandomRoot(GF2Polynomial gF2Polynomial) {
        GF2nPolynomial gcd;
        int degree;
        int degree2;
        GF2nPolynomial gF2nPolynomial = new GF2nPolynomial(gF2Polynomial, (GF2nField) this);
        while (gF2nPolynomial.getDegree() > 1) {
            while (true) {
                GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this, new Random());
                GF2nPolynomial gF2nPolynomial2 = new GF2nPolynomial(2, (GF2nElement) GF2nPolynomialElement.ZERO(this));
                gF2nPolynomial2.set(1, gF2nPolynomialElement);
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

    public GF2Polynomial getSquaringVector(int i) {
        return new GF2Polynomial(this.a[i]);
    }

    public int getTc() {
        if (this.b) {
            return this.d;
        }
        throw new RuntimeException();
    }

    public boolean isPentanomial() {
        return this.c;
    }

    public boolean isTrinomial() {
        return this.b;
    }
}
