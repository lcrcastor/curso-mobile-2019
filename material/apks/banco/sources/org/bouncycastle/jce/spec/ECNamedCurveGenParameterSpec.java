package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ECNamedCurveGenParameterSpec implements AlgorithmParameterSpec {
    private String a;

    public ECNamedCurveGenParameterSpec(String str) {
        this.a = str;
    }

    public String getName() {
        return this.a;
    }
}
