package org.bouncycastle.pqc.crypto.rainbow;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class RainbowKeyParameters extends AsymmetricKeyParameter {
    private int b;

    public RainbowKeyParameters(boolean z, int i) {
        super(z);
        this.b = i;
    }

    public int getDocLength() {
        return this.b;
    }
}
