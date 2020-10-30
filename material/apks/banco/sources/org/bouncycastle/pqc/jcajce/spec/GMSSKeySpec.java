package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import org.bouncycastle.pqc.crypto.gmss.GMSSParameters;

public class GMSSKeySpec implements KeySpec {
    private GMSSParameters a;

    protected GMSSKeySpec(GMSSParameters gMSSParameters) {
        this.a = gMSSParameters;
    }

    public GMSSParameters getParameters() {
        return this.a;
    }
}
