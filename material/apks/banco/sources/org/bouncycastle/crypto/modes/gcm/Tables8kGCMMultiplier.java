package org.bouncycastle.crypto.modes.gcm;

import com.google.common.base.Ascii;
import java.lang.reflect.Array;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class Tables8kGCMMultiplier implements GCMMultiplier {
    private byte[] a;
    private int[][][] b;

    public void init(byte[] bArr) {
        if (this.b == null) {
            this.b = (int[][][]) Array.newInstance(int.class, new int[]{32, 16, 4});
        } else if (Arrays.areEqual(this.a, bArr)) {
            return;
        }
        this.a = Arrays.clone(bArr);
        GCMUtil.a(bArr, this.b[1][8]);
        for (int i = 4; i >= 1; i >>= 1) {
            GCMUtil.b(this.b[1][i + i], this.b[1][i]);
        }
        int i2 = 0;
        GCMUtil.b(this.b[1][1], this.b[0][8]);
        for (int i3 = 4; i3 >= 1; i3 >>= 1) {
            GCMUtil.b(this.b[0][i3 + i3], this.b[0][i3]);
        }
        while (true) {
            for (int i4 = 2; i4 < 16; i4 += i4) {
                for (int i5 = 1; i5 < i4; i5++) {
                    GCMUtil.a(this.b[i2][i4], this.b[i2][i5], this.b[i2][i4 + i5]);
                }
            }
            i2++;
            if (i2 != 32) {
                if (i2 > 1) {
                    for (int i6 = 8; i6 > 0; i6 >>= 1) {
                        GCMUtil.c(this.b[i2 - 2][i6], this.b[i2][i6]);
                    }
                }
            } else {
                return;
            }
        }
    }

    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i = 15; i >= 0; i--) {
            int i2 = i + i;
            int[] iArr2 = this.b[i2][bArr[i] & Ascii.SI];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr2[3] ^ iArr[3];
            int[] iArr3 = this.b[i2 + 1][(bArr[i] & 240) >>> 4];
            iArr[0] = iArr[0] ^ iArr3[0];
            iArr[1] = iArr[1] ^ iArr3[1];
            iArr[2] = iArr[2] ^ iArr3[2];
            iArr[3] = iArr[3] ^ iArr3[3];
        }
        Pack.intToBigEndian(iArr, bArr, 0);
    }
}
