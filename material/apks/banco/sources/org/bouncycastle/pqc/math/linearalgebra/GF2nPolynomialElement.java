package org.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;

public class GF2nPolynomialElement extends GF2nElement {
    private static final int[] a = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, PKIFailureInfo.duplicateCertReq, 1073741824, Integer.MIN_VALUE, 0};
    private GF2Polynomial b;

    public GF2nPolynomialElement(GF2nPolynomialElement gF2nPolynomialElement) {
        this.mField = gF2nPolynomialElement.mField;
        this.mDegree = gF2nPolynomialElement.mDegree;
        this.b = new GF2Polynomial(gF2nPolynomialElement.b);
    }

    public GF2nPolynomialElement(GF2nPolynomialField gF2nPolynomialField, Random random) {
        this.mField = gF2nPolynomialField;
        this.mDegree = this.mField.getDegree();
        this.b = new GF2Polynomial(this.mDegree);
        a(random);
    }

    public GF2nPolynomialElement(GF2nPolynomialField gF2nPolynomialField, GF2Polynomial gF2Polynomial) {
        this.mField = gF2nPolynomialField;
        this.mDegree = this.mField.getDegree();
        this.b = new GF2Polynomial(gF2Polynomial);
        this.b.expandN(this.mDegree);
    }

    public GF2nPolynomialElement(GF2nPolynomialField gF2nPolynomialField, byte[] bArr) {
        this.mField = gF2nPolynomialField;
        this.mDegree = this.mField.getDegree();
        this.b = new GF2Polynomial(this.mDegree, bArr);
        this.b.expandN(this.mDegree);
    }

    public GF2nPolynomialElement(GF2nPolynomialField gF2nPolynomialField, int[] iArr) {
        this.mField = gF2nPolynomialField;
        this.mDegree = this.mField.getDegree();
        this.b = new GF2Polynomial(this.mDegree, iArr);
        this.b.expandN(gF2nPolynomialField.mDegree);
    }

    public static GF2nPolynomialElement ONE(GF2nPolynomialField gF2nPolynomialField) {
        return new GF2nPolynomialElement(gF2nPolynomialField, new GF2Polynomial(gF2nPolynomialField.getDegree(), new int[]{1}));
    }

    public static GF2nPolynomialElement ZERO(GF2nPolynomialField gF2nPolynomialField) {
        return new GF2nPolynomialElement(gF2nPolynomialField, new GF2Polynomial(gF2nPolynomialField.getDegree()));
    }

    private void a(Random random) {
        this.b.expandN(this.mDegree);
        this.b.randomize(random);
    }

    private void a(int[] iArr) {
        int i = this.mDegree - iArr[2];
        int i2 = this.mDegree - iArr[1];
        int i3 = this.mDegree - iArr[0];
        for (int length = this.b.getLength() - 1; length >= this.mDegree; length--) {
            if (this.b.testBit(length)) {
                this.b.xorBit(length);
                this.b.xorBit(length - i);
                this.b.xorBit(length - i2);
                this.b.xorBit(length - i3);
                this.b.xorBit(length - this.mDegree);
            }
        }
        this.b.reduceN();
        this.b.expandN(this.mDegree);
    }

    private GF2Polynomial b() {
        return new GF2Polynomial(this.b);
    }

    private void b(int i) {
        int i2 = this.mDegree - i;
        int length = this.b.getLength();
        while (true) {
            length--;
            if (length < this.mDegree) {
                this.b.reduceN();
                this.b.expandN(this.mDegree);
                return;
            } else if (this.b.testBit(length)) {
                this.b.xorBit(length);
                this.b.xorBit(length - i2);
                this.b.xorBit(length - this.mDegree);
            }
        }
    }

    private GF2nPolynomialElement c() {
        if ((this.mDegree & 1) == 0) {
            throw new RuntimeException();
        }
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        for (int i = 1; i <= ((this.mDegree - 1) >> 1); i++) {
            gF2nPolynomialElement.squareThis();
            gF2nPolynomialElement.squareThis();
            gF2nPolynomialElement.addToThis(this);
        }
        return gF2nPolynomialElement;
    }

    private void d() {
        if (this.b.getLength() <= this.mDegree) {
            if (this.b.getLength() < this.mDegree) {
                this.b.expandN(this.mDegree);
            }
        } else if (((GF2nPolynomialField) this.mField).isTrinomial()) {
            try {
                int tc = ((GF2nPolynomialField) this.mField).getTc();
                if (this.mDegree - tc <= 32 || this.b.getLength() > (this.mDegree << 1)) {
                    b(tc);
                } else {
                    this.b.a(this.mDegree, tc);
                }
            } catch (RuntimeException unused) {
                throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a trinomial");
            }
        } else if (((GF2nPolynomialField) this.mField).isPentanomial()) {
            try {
                int[] pc = ((GF2nPolynomialField) this.mField).getPc();
                if (this.mDegree - pc[2] <= 32 || this.b.getLength() > (this.mDegree << 1)) {
                    a(pc);
                } else {
                    this.b.a(this.mDegree, pc);
                }
            } catch (RuntimeException unused2) {
                throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a pentanomial");
            }
        } else {
            this.b = this.b.remainder(this.mField.getFieldPolynomial());
            this.b.expandN(this.mDegree);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.b.assignZero();
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        return this.b.testBit(i);
    }

    public GFElement add(GFElement gFElement) {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.addToThis(gFElement);
        return gF2nPolynomialElement;
    }

    public void addToThis(GFElement gFElement) {
        if (!(gFElement instanceof GF2nPolynomialElement)) {
            throw new RuntimeException();
        }
        GF2nPolynomialElement gF2nPolynomialElement = (GF2nPolynomialElement) gFElement;
        if (!this.mField.equals(gF2nPolynomialElement.mField)) {
            throw new RuntimeException();
        }
        this.b.addToThis(gF2nPolynomialElement.b);
    }

    public Object clone() {
        return new GF2nPolynomialElement(this);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GF2nPolynomialElement)) {
            return false;
        }
        GF2nPolynomialElement gF2nPolynomialElement = (GF2nPolynomialElement) obj;
        if (this.mField == gF2nPolynomialElement.mField || this.mField.getFieldPolynomial().equals(gF2nPolynomialElement.mField.getFieldPolynomial())) {
            return this.b.equals(gF2nPolynomialElement.b);
        }
        return false;
    }

    public int hashCode() {
        return this.mField.hashCode() + this.b.hashCode();
    }

    public GF2nElement increase() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.increaseThis();
        return gF2nPolynomialElement;
    }

    public void increaseThis() {
        this.b.increaseThis();
    }

    public GFElement invert() {
        return invertMAIA();
    }

    public GF2nPolynomialElement invertEEA() {
        if (isZero()) {
            throw new ArithmeticException();
        }
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.mDegree + 32, "ONE");
        gF2Polynomial.reduceN();
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.mDegree + 32);
        gF2Polynomial2.reduceN();
        GF2Polynomial b2 = b();
        GF2Polynomial fieldPolynomial = this.mField.getFieldPolynomial();
        b2.reduceN();
        while (!b2.isOne()) {
            b2.reduceN();
            fieldPolynomial.reduceN();
            int length = b2.getLength() - fieldPolynomial.getLength();
            if (length < 0) {
                length = -length;
                gF2Polynomial.reduceN();
                GF2Polynomial gF2Polynomial3 = gF2Polynomial2;
                gF2Polynomial2 = gF2Polynomial;
                gF2Polynomial = gF2Polynomial3;
                GF2Polynomial gF2Polynomial4 = fieldPolynomial;
                fieldPolynomial = b2;
                b2 = gF2Polynomial4;
            }
            b2.shiftLeftAddThis(fieldPolynomial, length);
            gF2Polynomial.shiftLeftAddThis(gF2Polynomial2, length);
        }
        gF2Polynomial.reduceN();
        return new GF2nPolynomialElement((GF2nPolynomialField) this.mField, gF2Polynomial);
    }

    public GF2nPolynomialElement invertMAIA() {
        if (isZero()) {
            throw new ArithmeticException();
        }
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.mDegree, "ONE");
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.mDegree);
        GF2Polynomial b2 = b();
        GF2Polynomial fieldPolynomial = this.mField.getFieldPolynomial();
        while (true) {
            if (!b2.testBit(0)) {
                b2.shiftRightThis();
                if (gF2Polynomial.testBit(0)) {
                    gF2Polynomial.addToThis(this.mField.getFieldPolynomial());
                }
                gF2Polynomial.shiftRightThis();
            } else if (b2.isOne()) {
                return new GF2nPolynomialElement((GF2nPolynomialField) this.mField, gF2Polynomial);
            } else {
                b2.reduceN();
                fieldPolynomial.reduceN();
                if (b2.getLength() < fieldPolynomial.getLength()) {
                    GF2Polynomial gF2Polynomial3 = gF2Polynomial2;
                    gF2Polynomial2 = gF2Polynomial;
                    gF2Polynomial = gF2Polynomial3;
                    GF2Polynomial gF2Polynomial4 = fieldPolynomial;
                    fieldPolynomial = b2;
                    b2 = gF2Polynomial4;
                }
                b2.addToThis(fieldPolynomial);
                gF2Polynomial.addToThis(gF2Polynomial2);
            }
        }
    }

    public GF2nPolynomialElement invertSquare() {
        if (isZero()) {
            throw new ArithmeticException();
        }
        int degree = this.mField.getDegree() - 1;
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.b.expandN((this.mDegree << 1) + 32);
        gF2nPolynomialElement.b.reduceN();
        int i = 1;
        for (int floorLog = IntegerFunctions.floorLog(degree) - 1; floorLog >= 0; floorLog--) {
            GF2nPolynomialElement gF2nPolynomialElement2 = new GF2nPolynomialElement(gF2nPolynomialElement);
            for (int i2 = 1; i2 <= i; i2++) {
                gF2nPolynomialElement2.squareThisPreCalc();
            }
            gF2nPolynomialElement.multiplyThisBy(gF2nPolynomialElement2);
            i <<= 1;
            if ((a[floorLog] & degree) != 0) {
                gF2nPolynomialElement.squareThisPreCalc();
                gF2nPolynomialElement.multiplyThisBy(this);
                i++;
            }
        }
        gF2nPolynomialElement.squareThisPreCalc();
        return gF2nPolynomialElement;
    }

    public boolean isOne() {
        return this.b.isOne();
    }

    public boolean isZero() {
        return this.b.isZero();
    }

    public GFElement multiply(GFElement gFElement) {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.multiplyThisBy(gFElement);
        return gF2nPolynomialElement;
    }

    public void multiplyThisBy(GFElement gFElement) {
        if (!(gFElement instanceof GF2nPolynomialElement)) {
            throw new RuntimeException();
        }
        GF2nPolynomialElement gF2nPolynomialElement = (GF2nPolynomialElement) gFElement;
        if (!this.mField.equals(gF2nPolynomialElement.mField)) {
            throw new RuntimeException();
        } else if (equals(gFElement)) {
            squareThis();
        } else {
            this.b = this.b.multiply(gF2nPolynomialElement.b);
            d();
        }
    }

    public GF2nPolynomialElement power(int i) {
        if (i == 1) {
            return new GF2nPolynomialElement(this);
        }
        GF2nPolynomialElement ONE = ONE((GF2nPolynomialField) this.mField);
        if (i == 0) {
            return ONE;
        }
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.b.expandN((gF2nPolynomialElement.mDegree << 1) + 32);
        gF2nPolynomialElement.b.reduceN();
        for (int i2 = 0; i2 < this.mDegree; i2++) {
            if (((1 << i2) & i) != 0) {
                ONE.multiplyThisBy(gF2nPolynomialElement);
            }
            gF2nPolynomialElement.square();
        }
        return ONE;
    }

    public GF2nElement solveQuadraticEquation() {
        GF2nPolynomialElement ZERO;
        GF2nPolynomialElement gF2nPolynomialElement;
        if (isZero()) {
            return ZERO((GF2nPolynomialField) this.mField);
        }
        if ((this.mDegree & 1) == 1) {
            return c();
        }
        do {
            GF2nPolynomialElement gF2nPolynomialElement2 = new GF2nPolynomialElement((GF2nPolynomialField) this.mField, new Random());
            ZERO = ZERO((GF2nPolynomialField) this.mField);
            gF2nPolynomialElement = (GF2nPolynomialElement) gF2nPolynomialElement2.clone();
            for (int i = 1; i < this.mDegree; i++) {
                ZERO.squareThis();
                gF2nPolynomialElement.squareThis();
                ZERO.addToThis(gF2nPolynomialElement.multiply(this));
                gF2nPolynomialElement.addToThis(gF2nPolynomialElement2);
            }
        } while (gF2nPolynomialElement.isZero());
        if (equals(ZERO.square().add(ZERO))) {
            return ZERO;
        }
        throw new RuntimeException();
    }

    public GF2nElement square() {
        return squarePreCalc();
    }

    public GF2nPolynomialElement squareBitwise() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.squareThisBitwise();
        gF2nPolynomialElement.d();
        return gF2nPolynomialElement;
    }

    public GF2nPolynomialElement squareMatrix() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.squareThisMatrix();
        gF2nPolynomialElement.d();
        return gF2nPolynomialElement;
    }

    public GF2nPolynomialElement squarePreCalc() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.squareThisPreCalc();
        gF2nPolynomialElement.d();
        return gF2nPolynomialElement;
    }

    public GF2nElement squareRoot() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        gF2nPolynomialElement.squareRootThis();
        return gF2nPolynomialElement;
    }

    public void squareRootThis() {
        this.b.expandN((this.mDegree << 1) + 32);
        this.b.reduceN();
        for (int i = 0; i < this.mField.getDegree() - 1; i++) {
            squareThis();
        }
    }

    public void squareThis() {
        squareThisPreCalc();
    }

    public void squareThisBitwise() {
        this.b.squareThisBitwise();
        d();
    }

    public void squareThisMatrix() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.mDegree);
        for (int i = 0; i < this.mDegree; i++) {
            if (this.b.vectorMult(((GF2nPolynomialField) this.mField).a[(this.mDegree - i) - 1])) {
                gF2Polynomial.setBit(i);
            }
        }
        this.b = gF2Polynomial;
    }

    public void squareThisPreCalc() {
        this.b.squareThisPreCalc();
        d();
    }

    public boolean testRightmostBit() {
        return this.b.testBit(0);
    }

    public byte[] toByteArray() {
        return this.b.toByteArray();
    }

    public BigInteger toFlexiBigInt() {
        return this.b.toFlexiBigInt();
    }

    public String toString() {
        return this.b.toString(16);
    }

    public String toString(int i) {
        return this.b.toString(i);
    }

    public int trace() {
        GF2nPolynomialElement gF2nPolynomialElement = new GF2nPolynomialElement(this);
        for (int i = 1; i < this.mDegree; i++) {
            gF2nPolynomialElement.squareThis();
            gF2nPolynomialElement.addToThis(this);
        }
        return gF2nPolynomialElement.isOne() ? 1 : 0;
    }
}
