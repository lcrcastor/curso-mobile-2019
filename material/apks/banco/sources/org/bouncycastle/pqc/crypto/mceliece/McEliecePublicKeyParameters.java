package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McEliecePublicKeyParameters extends McElieceKeyParameters {
    private String b;
    private int c;
    private int d;
    private GF2Matrix e;

    public McEliecePublicKeyParameters(String str, int i, int i2, GF2Matrix gF2Matrix, McElieceParameters mcElieceParameters) {
        super(false, mcElieceParameters);
        this.b = str;
        this.c = i;
        this.d = i2;
        this.e = new GF2Matrix(gF2Matrix);
    }

    public McEliecePublicKeyParameters(String str, int i, int i2, byte[] bArr, McElieceParameters mcElieceParameters) {
        super(false, mcElieceParameters);
        this.b = str;
        this.c = i2;
        this.d = i;
        this.e = new GF2Matrix(bArr);
    }

    public GF2Matrix getG() {
        return this.e;
    }

    public int getK() {
        return this.e.getNumRows();
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
