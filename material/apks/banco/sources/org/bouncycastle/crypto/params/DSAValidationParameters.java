package org.bouncycastle.crypto.params;

import org.bouncycastle.util.Arrays;

public class DSAValidationParameters {
    private int a;
    private byte[] b;
    private int c;

    public DSAValidationParameters(byte[] bArr, int i) {
        this(bArr, i, -1);
    }

    public DSAValidationParameters(byte[] bArr, int i, int i2) {
        this.b = bArr;
        this.c = i;
        this.a = i2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAValidationParameters)) {
            return false;
        }
        DSAValidationParameters dSAValidationParameters = (DSAValidationParameters) obj;
        if (dSAValidationParameters.c != this.c) {
            return false;
        }
        return Arrays.areEqual(this.b, dSAValidationParameters.b);
    }

    public int getCounter() {
        return this.c;
    }

    public byte[] getSeed() {
        return this.b;
    }

    public int getUsageIndex() {
        return this.a;
    }

    public int hashCode() {
        return this.c ^ Arrays.hashCode(this.b);
    }
}
