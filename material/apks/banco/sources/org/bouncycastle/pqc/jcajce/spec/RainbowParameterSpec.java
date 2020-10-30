package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.util.Arrays;

public class RainbowParameterSpec implements AlgorithmParameterSpec {
    private static final int[] a = {6, 12, 17, 22, 33};
    private int[] b;

    public RainbowParameterSpec() {
        this.b = a;
    }

    public RainbowParameterSpec(int[] iArr) {
        this.b = iArr;
        try {
            a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a() {
        if (this.b == null) {
            throw new IllegalArgumentException("no layers defined.");
        } else if (this.b.length > 1) {
            int i = 0;
            while (i < this.b.length - 1) {
                int i2 = this.b[i];
                i++;
                if (i2 >= this.b[i]) {
                    throw new IllegalArgumentException("v[i] has to be smaller than v[i+1]");
                }
            }
        } else {
            throw new IllegalArgumentException("Rainbow needs at least 1 layer, such that v1 < v2.");
        }
    }

    public int getDocumentLength() {
        return this.b[this.b.length - 1] - this.b[0];
    }

    public int getNumOfLayers() {
        return this.b.length - 1;
    }

    public int[] getVi() {
        return Arrays.clone(this.b);
    }
}
