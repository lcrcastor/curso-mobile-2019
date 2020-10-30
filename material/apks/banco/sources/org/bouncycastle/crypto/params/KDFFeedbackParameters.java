package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

public final class KDFFeedbackParameters implements DerivationParameters {
    private final byte[] a;
    private final byte[] b;
    private final boolean c;
    private final int d;
    private final byte[] e;

    private KDFFeedbackParameters(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, boolean z) {
        if (bArr == null) {
            throw new IllegalArgumentException("A KDF requires Ki (a seed) as input");
        }
        this.a = Arrays.clone(bArr);
        this.e = bArr3 == null ? new byte[0] : Arrays.clone(bArr3);
        this.d = i;
        this.b = bArr2 == null ? new byte[0] : Arrays.clone(bArr2);
        this.c = z;
    }

    public static KDFFeedbackParameters createWithCounter(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        if (i == 8 || i == 16 || i == 24 || i == 32) {
            KDFFeedbackParameters kDFFeedbackParameters = new KDFFeedbackParameters(bArr, bArr2, bArr3, i, true);
            return kDFFeedbackParameters;
        }
        throw new IllegalArgumentException("Length of counter should be 8, 16, 24 or 32");
    }

    public static KDFFeedbackParameters createWithoutCounter(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        KDFFeedbackParameters kDFFeedbackParameters = new KDFFeedbackParameters(bArr, bArr2, bArr3, -1, false);
        return kDFFeedbackParameters;
    }

    public byte[] getFixedInputData() {
        return Arrays.clone(this.e);
    }

    public byte[] getIV() {
        return this.b;
    }

    public byte[] getKI() {
        return this.a;
    }

    public int getR() {
        return this.d;
    }

    public boolean useCounter() {
        return this.c;
    }
}
