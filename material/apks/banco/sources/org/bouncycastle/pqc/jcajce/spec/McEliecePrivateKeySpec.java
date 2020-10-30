package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKeySpec implements KeySpec {
    private String a;
    private int b;
    private int c;
    private GF2mField d;
    private PolynomialGF2mSmallM e;
    private GF2Matrix f;
    private Permutation g;
    private Permutation h;
    private GF2Matrix i;
    private PolynomialGF2mSmallM[] j;

    public McEliecePrivateKeySpec(String str, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix2, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = str;
        this.c = i3;
        this.b = i2;
        this.d = gF2mField;
        this.e = polynomialGF2mSmallM;
        this.f = gF2Matrix;
        this.g = permutation;
        this.h = permutation2;
        this.i = gF2Matrix2;
        this.j = polynomialGF2mSmallMArr;
    }

    public McEliecePrivateKeySpec(String str, int i2, int i3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, byte[][] bArr7) {
        this.a = str;
        this.b = i2;
        this.c = i3;
        this.d = new GF2mField(bArr);
        this.e = new PolynomialGF2mSmallM(this.d, bArr2);
        this.f = new GF2Matrix(bArr3);
        this.g = new Permutation(bArr4);
        this.h = new Permutation(bArr5);
        this.i = new GF2Matrix(bArr6);
        this.j = new PolynomialGF2mSmallM[bArr7.length];
        for (int i4 = 0; i4 < bArr7.length; i4++) {
            this.j[i4] = new PolynomialGF2mSmallM(this.d, bArr7[i4]);
        }
    }

    public GF2mField getField() {
        return this.d;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.e;
    }

    public GF2Matrix getH() {
        return this.i;
    }

    public int getK() {
        return this.c;
    }

    public int getN() {
        return this.b;
    }

    public String getOIDString() {
        return this.a;
    }

    public Permutation getP1() {
        return this.g;
    }

    public Permutation getP2() {
        return this.h;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.j;
    }

    public GF2Matrix getSInv() {
        return this.f;
    }
}
