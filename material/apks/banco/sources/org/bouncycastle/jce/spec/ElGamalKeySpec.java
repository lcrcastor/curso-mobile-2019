package org.bouncycastle.jce.spec;

import java.security.spec.KeySpec;

public class ElGamalKeySpec implements KeySpec {
    private ElGamalParameterSpec a;

    public ElGamalKeySpec(ElGamalParameterSpec elGamalParameterSpec) {
        this.a = elGamalParameterSpec;
    }

    public ElGamalParameterSpec getParams() {
        return this.a;
    }
}
