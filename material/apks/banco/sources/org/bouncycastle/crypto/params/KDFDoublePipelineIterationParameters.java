package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

public final class KDFDoublePipelineIterationParameters implements DerivationParameters {
    private final byte[] a;
    private final boolean b;
    private final int c;
    private final byte[] d;

    private KDFDoublePipelineIterationParameters(byte[] bArr, byte[] bArr2, int i, boolean z) {
        if (bArr == null) {
            throw new IllegalArgumentException("A KDF requires Ki (a seed) as input");
        }
        this.a = Arrays.clone(bArr);
        this.d = bArr2 == null ? new byte[0] : Arrays.clone(bArr2);
        if (i == 8 || i == 16 || i == 24 || i == 32) {
            this.c = i;
            this.b = z;
            return;
        }
        throw new IllegalArgumentException("Length of counter should be 8, 16, 24 or 32");
    }

    public static KDFDoublePipelineIterationParameters createWithCounter(byte[] bArr, byte[] bArr2, int i) {
        return new KDFDoublePipelineIterationParameters(bArr, bArr2, i, true);
    }

    public static KDFDoublePipelineIterationParameters createWithoutCounter(byte[] bArr, byte[] bArr2) {
        return new KDFDoublePipelineIterationParameters(bArr, bArr2, 32, false);
    }

    public byte[] getFixedInputData() {
        return Arrays.clone(this.d);
    }

    public byte[] getKI() {
        return this.a;
    }

    public int getR() {
        return this.c;
    }

    public boolean useCounter() {
        return this.b;
    }
}
