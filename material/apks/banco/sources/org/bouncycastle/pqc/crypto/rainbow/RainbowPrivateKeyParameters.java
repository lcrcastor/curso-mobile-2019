package org.bouncycastle.pqc.crypto.rainbow;

public class RainbowPrivateKeyParameters extends RainbowKeyParameters {
    private short[][] b;
    private short[] c;
    private short[][] d;
    private short[] e;
    private int[] f;
    private Layer[] g;

    public RainbowPrivateKeyParameters(short[][] sArr, short[] sArr2, short[][] sArr3, short[] sArr4, int[] iArr, Layer[] layerArr) {
        super(true, iArr[iArr.length - 1] - iArr[0]);
        this.b = sArr;
        this.c = sArr2;
        this.d = sArr3;
        this.e = sArr4;
        this.f = iArr;
        this.g = layerArr;
    }

    public short[] getB1() {
        return this.c;
    }

    public short[] getB2() {
        return this.e;
    }

    public short[][] getInvA1() {
        return this.b;
    }

    public short[][] getInvA2() {
        return this.d;
    }

    public Layer[] getLayers() {
        return this.g;
    }

    public int[] getVi() {
        return this.f;
    }
}
