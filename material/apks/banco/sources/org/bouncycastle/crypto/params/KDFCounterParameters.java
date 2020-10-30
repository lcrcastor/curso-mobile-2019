package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

public final class KDFCounterParameters implements DerivationParameters {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;

    public KDFCounterParameters(byte[] bArr, byte[] bArr2, int i) {
        this(bArr, null, bArr2, i);
    }

    public KDFCounterParameters(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        if (bArr == null) {
            throw new IllegalArgumentException("A KDF requires Ki (a seed) as input");
        }
        this.a = Arrays.clone(bArr);
        this.b = bArr2 == null ? new byte[0] : Arrays.clone(bArr2);
        this.c = bArr3 == null ? new byte[0] : Arrays.clone(bArr3);
        if (i == 8 || i == 16 || i == 24 || i == 32) {
            this.d = i;
            return;
        }
        throw new IllegalArgumentException("Length of counter should be 8, 16, 24 or 32");
    }

    public byte[] getFixedInputData() {
        return Arrays.clone(this.c);
    }

    public byte[] getFixedInputDataCounterPrefix() {
        return Arrays.clone(this.b);
    }

    public byte[] getFixedInputDataCounterSuffix() {
        return Arrays.clone(this.c);
    }

    public byte[] getKI() {
        return this.a;
    }

    public int getR() {
        return this.d;
    }
}
