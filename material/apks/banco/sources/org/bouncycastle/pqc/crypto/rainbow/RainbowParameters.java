package org.bouncycastle.pqc.crypto.rainbow;

import org.bouncycastle.crypto.CipherParameters;

public class RainbowParameters implements CipherParameters {
    private final int[] a;
    private int[] b;

    public RainbowParameters() {
        this.a = new int[]{6, 12, 17, 22, 33};
        this.b = this.a;
    }

    public RainbowParameters(int[] iArr) {
        this.a = new int[]{6, 12, 17, 22, 33};
        this.b = iArr;
        try {
            a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a() {
        if (this.b == null) {
            throw new Exception("no layers defined.");
        } else if (this.b.length > 1) {
            int i = 0;
            while (i < this.b.length - 1) {
                int i2 = this.b[i];
                i++;
                if (i2 >= this.b[i]) {
                    throw new Exception("v[i] has to be smaller than v[i+1]");
                }
            }
        } else {
            throw new Exception("Rainbow needs at least 1 layer, such that v1 < v2.");
        }
    }

    public int getDocLength() {
        return this.b[this.b.length - 1] - this.b[0];
    }

    public int getNumOfLayers() {
        return this.b.length - 1;
    }

    public int[] getVi() {
        return this.b;
    }
}
