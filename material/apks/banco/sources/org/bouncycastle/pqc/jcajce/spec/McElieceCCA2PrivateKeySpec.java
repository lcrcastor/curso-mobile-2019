package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCCA2PrivateKeySpec implements KeySpec {
    private String a;
    private int b;
    private int c;
    private GF2mField d;
    private PolynomialGF2mSmallM e;
    private Permutation f;
    private GF2Matrix g;
    private PolynomialGF2mSmallM[] h;

    public McElieceCCA2PrivateKeySpec(String str, int i, int i2, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, GF2Matrix gF2Matrix, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = gF2mField;
        this.e = polynomialGF2mSmallM;
        this.f = permutation;
        this.g = gF2Matrix;
        this.h = polynomialGF2mSmallMArr;
    }

    public McElieceCCA2PrivateKeySpec(String str, int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[][] bArr5) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = new GF2mField(bArr);
        this.e = new PolynomialGF2mSmallM(this.d, bArr2);
        this.f = new Permutation(bArr3);
        this.g = new GF2Matrix(bArr4);
        this.h = new PolynomialGF2mSmallM[bArr5.length];
        for (int i3 = 0; i3 < bArr5.length; i3++) {
            this.h[i3] = new PolynomialGF2mSmallM(this.d, bArr5[i3]);
        }
    }

    public GF2mField getField() {
        return this.d;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.e;
    }

    public GF2Matrix getH() {
        return this.g;
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

    public Permutation getP() {
        return this.f;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.h;
    }
}
