package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ElGamalGenParameterSpec implements AlgorithmParameterSpec {
    private int a;

    public ElGamalGenParameterSpec(int i) {
        this.a = i;
    }

    public int getPrimeSize() {
        return this.a;
    }
}
