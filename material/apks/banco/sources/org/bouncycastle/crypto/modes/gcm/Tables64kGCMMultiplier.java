package org.bouncycastle.crypto.modes.gcm;

import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {
    private byte[] a;
    private int[][][] b;

    public void init(byte[] bArr) {
        if (this.b == null) {
            this.b = (int[][][]) Array.newInstance(int.class, new int[]{16, 256, 4});
        } else if (Arrays.areEqual(this.a, bArr)) {
            return;
        }
        this.a = Arrays.clone(bArr);
        int i = 0;
        GCMUtil.a(bArr, this.b[0][128]);
        for (int i2 = 64; i2 >= 1; i2 >>= 1) {
            GCMUtil.b(this.b[0][i2 + i2], this.b[0][i2]);
        }
        while (true) {
            for (int i3 = 2; i3 < 256; i3 += i3) {
                for (int i4 = 1; i4 < i3; i4++) {
                    GCMUtil.a(this.b[i][i3], this.b[i][i4], this.b[i][i3 + i4]);
                }
            }
            i++;
            if (i != 16) {
                for (int i5 = 128; i5 > 0; i5 >>= 1) {
                    GCMUtil.c(this.b[i - 1][i5], this.b[i][i5]);
                }
            } else {
                return;
            }
        }
    }

    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i = 15; i >= 0; i--) {
            int[] iArr2 = this.b[i][bArr[i] & UnsignedBytes.MAX_VALUE];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr2[3] ^ iArr[3];
        }
        Pack.intToBigEndian(iArr, bArr, 0);
    }
}
