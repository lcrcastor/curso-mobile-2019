package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McElieceCCA2PublicKeyParameters extends McElieceCCA2KeyParameters {
    private String b;
    private int c;
    private int d;
    private GF2Matrix e;

    public McElieceCCA2PublicKeyParameters(String str, int i, int i2, GF2Matrix gF2Matrix, McElieceCCA2Parameters mcElieceCCA2Parameters) {
        super(false, mcElieceCCA2Parameters);
        this.b = str;
        this.c = i;
        this.d = i2;
        this.e = new GF2Matrix(gF2Matrix);
    }

    public McElieceCCA2PublicKeyParameters(String str, int i, int i2, byte[] bArr, McElieceCCA2Parameters mcElieceCCA2Parameters) {
        super(false, mcElieceCCA2Parameters);
        this.b = str;
        this.c = i;
        this.d = i2;
        this.e = new GF2Matrix(bArr);
    }

    public int getK() {
        return this.e.getNumRows();
    }

    public GF2Matrix getMatrixG() {
        return this.e;
    }

    public int getN() {
        return this.c;
    }

    public String getOIDString() {
        return this.b;
    }

    public int getT() {
        return this.d;
    }
}
