package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCCA2PrivateKeyParameters extends McElieceCCA2KeyParameters {
    private String b;
    private int c;
    private int d;
    private GF2mField e;
    private PolynomialGF2mSmallM f;
    private Permutation g;
    private GF2Matrix h;
    private PolynomialGF2mSmallM[] i;

    public McElieceCCA2PrivateKeyParameters(String str, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, GF2Matrix gF2Matrix, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr, McElieceCCA2Parameters mcElieceCCA2Parameters) {
        super(true, mcElieceCCA2Parameters);
        this.b = str;
        this.c = i2;
        this.d = i3;
        this.e = gF2mField;
        this.f = polynomialGF2mSmallM;
        this.g = permutation;
        this.h = gF2Matrix;
        this.i = polynomialGF2mSmallMArr;
    }

    public McElieceCCA2PrivateKeyParameters(String str, int i2, int i3, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[][] bArr5, McElieceCCA2Parameters mcElieceCCA2Parameters) {
        super(true, mcElieceCCA2Parameters);
        this.b = str;
        this.c = i2;
        this.d = i3;
        this.e = new GF2mField(bArr);
        this.f = new PolynomialGF2mSmallM(this.e, bArr2);
        this.g = new Permutation(bArr3);
        this.h = new GF2Matrix(bArr4);
        this.i = new PolynomialGF2mSmallM[bArr5.length];
        for (int i4 = 0; i4 < bArr5.length; i4++) {
            this.i[i4] = new PolynomialGF2mSmallM(this.e, bArr5[i4]);
        }
    }

    public GF2mField getField() {
        return this.e;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.f;
    }

    public GF2Matrix getH() {
        return this.h;
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

    public Permutation getP() {
        return this.g;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.i;
    }

    public int getT() {
        return this.f.getDegree();
    }
}
