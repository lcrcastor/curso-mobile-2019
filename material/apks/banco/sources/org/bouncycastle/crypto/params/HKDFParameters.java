package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.util.Arrays;

public class HKDFParameters implements DerivationParameters {
    private final byte[] a;
    private final boolean b;
    private final byte[] c;
    private final byte[] d;

    private HKDFParameters(byte[] bArr, boolean z, byte[] bArr2, byte[] bArr3) {
        if (bArr == null) {
            throw new IllegalArgumentException("IKM (input keying material) should not be null");
        }
        this.a = Arrays.clone(bArr);
        this.b = z;
        this.c = (bArr2 == null || bArr2.length == 0) ? null : Arrays.clone(bArr2);
        this.d = bArr3 == null ? new byte[0] : Arrays.clone(bArr3);
    }

    public HKDFParameters(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this(bArr, false, bArr2, bArr3);
    }

    public static HKDFParameters defaultParameters(byte[] bArr) {
        return new HKDFParameters(bArr, false, null, null);
    }

    public static HKDFParameters skipExtractParameters(byte[] bArr, byte[] bArr2) {
        return new HKDFParameters(bArr, true, null, bArr2);
    }

    public byte[] getIKM() {
        return Arrays.clone(this.a);
    }

    public byte[] getInfo() {
        return Arrays.clone(this.d);
    }

    public byte[] getSalt() {
        return Arrays.clone(this.c);
    }

    public boolean skipExtract() {
        return this.b;
    }
}
