package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class AEADParameters implements CipherParameters {
    private byte[] a;
    private byte[] b;
    private KeyParameter c;
    private int d;

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr) {
        this(keyParameter, i, bArr, null);
    }

    public AEADParameters(KeyParameter keyParameter, int i, byte[] bArr, byte[] bArr2) {
        this.c = keyParameter;
        this.b = bArr;
        this.d = i;
        this.a = bArr2;
    }

    public byte[] getAssociatedText() {
        return this.a;
    }

    public KeyParameter getKey() {
        return this.c;
    }

    public int getMacSize() {
        return this.d;
    }

    public byte[] getNonce() {
        return this.b;
    }
}
