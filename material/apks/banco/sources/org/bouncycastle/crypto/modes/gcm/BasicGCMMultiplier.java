package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

public class BasicGCMMultiplier implements GCMMultiplier {
    private byte[] a;

    public void init(byte[] bArr) {
        this.a = Arrays.clone(bArr);
    }

    public void multiplyH(byte[] bArr) {
        GCMUtil.a(bArr, this.a);
    }
}
