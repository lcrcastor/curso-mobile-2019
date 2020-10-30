package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceParameters implements CipherParameters {
    public static final int DEFAULT_M = 11;
    public static final int DEFAULT_T = 50;
    private int a;
    private int b;
    private int c;
    private int d;

    public McElieceParameters() {
        this(11, 50);
    }

    public McElieceParameters(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("key size must be positive");
        }
        this.a = 0;
        this.c = 1;
        while (this.c < i) {
            this.c <<= 1;
            this.a++;
        }
        this.b = this.c >>> 1;
        this.b /= this.a;
        this.d = PolynomialRingGF2.getIrreduciblePolynomial(this.a);
    }

    public McElieceParameters(int i, int i2) {
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (i > 32) {
            throw new IllegalArgumentException("m is too large");
        } else {
            this.a = i;
            this.c = 1 << i;
            if (i2 < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (i2 > this.c) {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            } else {
                this.b = i2;
                this.d = PolynomialRingGF2.getIrreduciblePolynomial(i);
            }
        }
    }

    public McElieceParameters(int i, int i2, int i3) {
        this.a = i;
        if (i < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (i > 32) {
            throw new IllegalArgumentException(" m is too large");
        } else {
            this.c = 1 << i;
            this.b = i2;
            if (i2 < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (i2 > this.c) {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            } else if (PolynomialRingGF2.degree(i3) != i || !PolynomialRingGF2.isIrreducible(i3)) {
                throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
            } else {
                this.d = i3;
            }
        }
    }

    public int getFieldPoly() {
        return this.d;
    }

    public int getM() {
        return this.a;
    }

    public int getN() {
        return this.c;
    }

    public int getT() {
        return this.b;
    }
}
