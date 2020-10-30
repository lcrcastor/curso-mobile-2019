package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKeyParameters extends McElieceKeyParameters {
    private String b;
    private int c;
    private int d;
    private GF2mField e;
    private PolynomialGF2mSmallM f;
    private GF2Matrix g;
    private Permutation h;
    private Permutation i;
    private GF2Matrix j;
    private PolynomialGF2mSmallM[] k;

    public McEliecePrivateKeyParameters(String str, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix2, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr, McElieceParameters mcElieceParameters) {
        super(true, mcElieceParameters);
        this.b = str;
        this.d = i3;
        this.c = i2;
        this.e = gF2mField;
        this.f = polynomialGF2mSmallM;
        this.g = gF2Matrix;
        this.h = permutation;
        this.i = permutation2;
        this.j = gF2Matrix2;
        this.k = polynomialGF2mSmallMArr;
    }

    public McEliecePrivateKeyParameters(String str, int i2, int i3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, byte[][] bArr7, McElieceParameters mcElieceParameters) {
        super(true, mcElieceParameters);
        this.b = str;
        this.c = i2;
        this.d = i3;
        this.e = new GF2mField(bArr);
        this.f = new PolynomialGF2mSmallM(this.e, bArr2);
        this.g = new GF2Matrix(bArr3);
        this.h = new Permutation(bArr4);
        this.i = new Permutation(bArr5);
        this.j = new GF2Matrix(bArr6);
        this.k = new PolynomialGF2mSmallM[bArr7.length];
        for (int i4 = 0; i4 < bArr7.length; i4++) {
            this.k[i4] = new PolynomialGF2mSmallM(this.e, bArr7[i4]);
        }
    }

    public GF2mField getField() {
        return this.e;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.f;
    }

    public GF2Matrix getH() {
        return this.j;
    }

    public int getK() {
        return this.d;
    }

    public int getN() {
        return this.c;
    }

    public String getOIDString() {
        return this.b;
    }

    public Permutation getP1() {
        return this.h;
    }

    public Permutation getP2() {
        return this.i;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.k;
    }

    public GF2Matrix getSInv() {
        return this.g;
    }
}
