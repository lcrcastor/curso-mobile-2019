package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.util.Arrays;

public class IESParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private int c;
    private int d;
    private byte[] e;

    public IESParameterSpec(byte[] bArr, byte[] bArr2, int i) {
        this(bArr, bArr2, i, -1);
    }

    public IESParameterSpec(byte[] bArr, byte[] bArr2, int i, int i2) {
        this(bArr, bArr2, i, i2, null);
    }

    public IESParameterSpec(byte[] bArr, byte[] bArr2, int i, int i2, byte[] bArr3) {
        if (bArr != null) {
            this.a = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.a, 0, bArr.length);
        } else {
            this.a = null;
        }
        if (bArr2 != null) {
            this.b = new byte[bArr2.length];
            System.arraycopy(bArr2, 0, this.b, 0, bArr2.length);
        } else {
            this.b = null;
        }
        this.c = i;
        this.d = i2;
        this.e = Arrays.clone(bArr3);
    }

    public int getCipherKeySize() {
        return this.d;
    }

    public byte[] getDerivationV() {
        return Arrays.clone(this.a);
    }

    public byte[] getEncodingV() {
        return Arrays.clone(this.b);
    }

    public int getMacKeySize() {
        return this.c;
    }

    public byte[] getNonce() {
        return Arrays.clone(this.e);
    }
}
