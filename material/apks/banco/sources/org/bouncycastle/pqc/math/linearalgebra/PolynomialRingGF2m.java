package org.bouncycastle.pqc.math.linearalgebra;

public class PolynomialRingGF2m {
    private GF2mField a;
    private PolynomialGF2mSmallM b;
    protected PolynomialGF2mSmallM[] sqMatrix;
    protected PolynomialGF2mSmallM[] sqRootMatrix;

    public PolynomialRingGF2m(GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM) {
        this.a = gF2mField;
        this.b = polynomialGF2mSmallM;
        a();
        b();
    }

    private void a() {
        int i;
        int degree = this.b.getDegree();
        this.sqMatrix = new PolynomialGF2mSmallM[degree];
        int i2 = 0;
        while (true) {
            i = degree >> 1;
            if (i2 >= i) {
                break;
            }
            int i3 = i2 << 1;
            int[] iArr = new int[(i3 + 1)];
            iArr[i3] = 1;
            this.sqMatrix[i2] = new PolynomialGF2mSmallM(this.a, iArr);
            i2++;
        }
        while (i < degree) {
            int i4 = i << 1;
            int[] iArr2 = new int[(i4 + 1)];
            iArr2[i4] = 1;
            this.sqMatrix[i] = new PolynomialGF2mSmallM(this.a, iArr2).mod(this.b);
            i++;
        }
    }

    private static void a(PolynomialGF2mSmallM[] polynomialGF2mSmallMArr, int i, int i2) {
        PolynomialGF2mSmallM polynomialGF2mSmallM = polynomialGF2mSmallMArr[i];
        polynomialGF2mSmallMArr[i] = polynomialGF2mSmallMArr[i2];
        polynomialGF2mSmallMArr[i2] = polynomialGF2mSmallM;
    }

    private void b() {
        int degree = this.b.getDegree();
        PolynomialGF2mSmallM[] polynomialGF2mSmallMArr = new PolynomialGF2mSmallM[degree];
        int i = degree - 1;
        for (int i2 = i; i2 >= 0; i2--) {
            polynomialGF2mSmallMArr[i2] = new PolynomialGF2mSmallM(this.sqMatrix[i2]);
        }
        this.sqRootMatrix = new PolynomialGF2mSmallM[degree];
        while (i >= 0) {
            this.sqRootMatrix[i] = new PolynomialGF2mSmallM(this.a, i);
            i--;
        }
        for (int i3 = 0; i3 < degree; i3++) {
            if (polynomialGF2mSmallMArr[i3].getCoefficient(i3) == 0) {
                int i4 = i3 + 1;
                boolean z = false;
                while (i4 < degree) {
                    if (polynomialGF2mSmallMArr[i4].getCoefficient(i3) != 0) {
                        a(polynomialGF2mSmallMArr, i3, i4);
                        a(this.sqRootMatrix, i3, i4);
                        i4 = degree;
                        z = true;
                    }
                    i4++;
                }
                if (!z) {
                    throw new ArithmeticException("Squaring matrix is not invertible.");
                }
            }
            int inverse = this.a.inverse(polynomialGF2mSmallMArr[i3].getCoefficient(i3));
            polynomialGF2mSmallMArr[i3].multThisWithElement(inverse);
            this.sqRootMatrix[i3].multThisWithElement(inverse);
            for (int i5 = 0; i5 < degree; i5++) {
                if (i5 != i3) {
                    int coefficient = polynomialGF2mSmallMArr[i5].getCoefficient(i3);
                    if (coefficient != 0) {
                        PolynomialGF2mSmallM multWithElement = polynomialGF2mSmallMArr[i3].multWithElement(coefficient);
                        PolynomialGF2mSmallM multWithElement2 = this.sqRootMatrix[i3].multWithElement(coefficient);
                        polynomialGF2mSmallMArr[i5].addToThis(multWithElement);
                        this.sqRootMatrix[i5].addToThis(multWithElement2);
                    }
                }
            }
        }
    }

    public PolynomialGF2mSmallM[] getSquareRootMatrix() {
        return this.sqRootMatrix;
    }

    public PolynomialGF2mSmallM[] getSquaringMatrix() {
        return this.sqMatrix;
    }
}
