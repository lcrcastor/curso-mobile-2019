package org.bouncycastle.crypto.engines;

import android.support.v4.app.FrameMetricsAggregator;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class HC128Engine implements StreamCipher {
    private int[] a = new int[512];
    private int[] b = new int[512];
    private int c = 0;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private byte[] g = new byte[4];
    private int h = 0;

    private int a() {
        int d2;
        int i;
        int f2 = f(this.c);
        if (this.c < 512) {
            int[] iArr = this.a;
            iArr[f2] = iArr[f2] + a(this.a[c(f2, 3)], this.a[c(f2, 10)], this.a[c(f2, FrameMetricsAggregator.EVERY_DURATION)]);
            d2 = c(this.a[c(f2, 12)]);
            i = this.a[f2];
        } else {
            int[] iArr2 = this.b;
            iArr2[f2] = iArr2[f2] + b(this.b[c(f2, 3)], this.b[c(f2, 10)], this.b[c(f2, FrameMetricsAggregator.EVERY_DURATION)]);
            d2 = d(this.b[c(f2, 12)]);
            i = this.b[f2];
        }
        int i2 = i ^ d2;
        this.c = e(this.c + 1);
        return i2;
    }

    private static int a(int i) {
        return (i >>> 3) ^ (b(i, 7) ^ b(i, 18));
    }

    private static int a(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int a(int i, int i2, int i3) {
        return (b(i, 10) ^ b(i3, 23)) + b(i2, 8);
    }

    private static int b(int i) {
        return (i >>> 10) ^ (b(i, 17) ^ b(i, 19));
    }

    private static int b(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private int b(int i, int i2, int i3) {
        return (a(i, 10) ^ a(i3, 23)) + a(i2, 8);
    }

    private void b() {
        if (this.d.length != 16) {
            throw new IllegalArgumentException("The key must be 128 bits long");
        }
        this.h = 0;
        this.c = 0;
        int[] iArr = new int[1280];
        for (int i = 0; i < 16; i++) {
            int i2 = i >> 2;
            iArr[i2] = ((this.d[i] & UnsignedBytes.MAX_VALUE) << ((i & 3) * 8)) | iArr[i2];
        }
        System.arraycopy(iArr, 0, iArr, 4, 4);
        int i3 = 0;
        while (i3 < this.e.length && i3 < 16) {
            int i4 = (i3 >> 2) + 8;
            iArr[i4] = iArr[i4] | ((this.e[i3] & UnsignedBytes.MAX_VALUE) << ((i3 & 3) * 8));
            i3++;
        }
        System.arraycopy(iArr, 8, iArr, 12, 4);
        for (int i5 = 16; i5 < 1280; i5++) {
            iArr[i5] = b(iArr[i5 - 2]) + iArr[i5 - 7] + a(iArr[i5 - 15]) + iArr[i5 - 16] + i5;
        }
        System.arraycopy(iArr, 256, this.a, 0, 512);
        System.arraycopy(iArr, 768, this.b, 0, 512);
        for (int i6 = 0; i6 < 512; i6++) {
            this.a[i6] = a();
        }
        for (int i7 = 0; i7 < 512; i7++) {
            this.b[i7] = a();
        }
        this.c = 0;
    }

    private byte c() {
        if (this.h == 0) {
            int a2 = a();
            this.g[0] = (byte) (a2 & 255);
            int i = a2 >> 8;
            this.g[1] = (byte) (i & 255);
            int i2 = i >> 8;
            this.g[2] = (byte) (i2 & 255);
            this.g[3] = (byte) ((i2 >> 8) & 255);
        }
        byte b2 = this.g[this.h];
        this.h = 3 & (this.h + 1);
        return b2;
    }

    private int c(int i) {
        return this.b[i & 255] + this.b[((i >> 16) & 255) + 256];
    }

    private static int c(int i, int i2) {
        return f(i - i2);
    }

    private int d(int i) {
        return this.a[i & 255] + this.a[((i >> 16) & 255) + 256];
    }

    private static int e(int i) {
        return i & 1023;
    }

    private static int f(int i) {
        return i & FrameMetricsAggregator.EVERY_DURATION;
    }

    public String getAlgorithmName() {
        return "HC-128";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.e = parametersWithIV.getIV();
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            this.e = new byte[0];
            cipherParameters2 = cipherParameters;
        }
        if (cipherParameters2 instanceof KeyParameter) {
            this.d = ((KeyParameter) cipherParameters2).getKey();
            b();
            this.f = true;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid parameter passed to HC128 init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (!this.f) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr2[i3 + i4] = (byte) (bArr[i + i4] ^ c());
            }
            return i2;
        }
    }

    public void reset() {
        b();
    }

    public byte returnByte(byte b2) {
        return (byte) (b2 ^ c());
    }
}
