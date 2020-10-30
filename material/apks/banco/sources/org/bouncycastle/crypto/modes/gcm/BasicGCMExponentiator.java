package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.util.Arrays;

public class BasicGCMExponentiator implements GCMExponentiator {
    private int[] a;

    public void exponentiateX(long j, byte[] bArr) {
        int[] a2 = GCMUtil.a();
        if (j > 0) {
            int[] clone = Arrays.clone(this.a);
            do {
                if ((j & 1) != 0) {
                    GCMUtil.a(a2, clone);
                }
                GCMUtil.a(clone, clone);
                j >>>= 1;
            } while (j > 0);
        }
        GCMUtil.a(a2, bArr);
    }

    public void init(byte[] bArr) {
        this.a = GCMUtil.a(bArr);
    }
}
