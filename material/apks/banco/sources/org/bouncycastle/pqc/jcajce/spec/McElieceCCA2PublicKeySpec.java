package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McElieceCCA2PublicKeySpec implements KeySpec {
    private String a;
    private int b;
    private int c;
    private GF2Matrix d;

    public McElieceCCA2PublicKeySpec(String str, int i, int i2, GF2Matrix gF2Matrix) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = new GF2Matrix(gF2Matrix);
    }

    public McElieceCCA2PublicKeySpec(String str, int i, int i2, byte[] bArr) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = new GF2Matrix(bArr);
    }

    public GF2Matrix getMatrixG() {
        return this.d;
    }

    public int getN() {
        return this.b;
    }

    public String getOIDString() {
        return this.a;
    }

    public int getT() {
        return this.c;
    }
}
