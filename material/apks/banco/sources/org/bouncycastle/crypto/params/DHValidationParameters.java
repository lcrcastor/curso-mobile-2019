package org.bouncycastle.crypto.params;

import org.bouncycastle.util.Arrays;

public class DHValidationParameters {
    private byte[] a;
    private int b;

    public DHValidationParameters(byte[] bArr, int i) {
        this.a = bArr;
        this.b = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHValidationParameters)) {
            return false;
        }
        DHValidationParameters dHValidationParameters = (DHValidationParameters) obj;
        if (dHValidationParameters.b != this.b) {
            return false;
        }
        return Arrays.areEqual(this.a, dHValidationParameters.a);
    }

    public int getCounter() {
        return this.b;
    }

    public byte[] getSeed() {
        return this.a;
    }

    public int hashCode() {
        return this.b ^ Arrays.hashCode(this.a);
    }
}
