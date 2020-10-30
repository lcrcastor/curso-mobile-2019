package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.RC5Parameters;

public class RC564Engine implements BlockCipher {
    private int a = 12;
    private long[] b = null;
    private boolean c;

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        long a2 = a(bArr, i) + this.b[0];
        long a3 = a(bArr, i + 8) + this.b[1];
        int i3 = 1;
        while (i3 <= this.a) {
            int i4 = i3 * 2;
            long a4 = a(a2 ^ a3, a3) + this.b[i4];
            a3 = a(a3 ^ a4, a4) + this.b[i4 + 1];
            i3++;
            a2 = a4;
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 8);
        return 16;
    }

    private long a(long j, long j2) {
        long j3 = j2 & 63;
        return (j << ((int) j3)) | (j >>> ((int) (64 - j3)));
    }

    private long a(byte[] bArr, int i) {
        long j = 0;
        int i2 = 7;
        while (i2 >= 0) {
            i2--;
            j = (j << 8) + ((long) (bArr[i2 + i] & UnsignedBytes.MAX_VALUE));
        }
        return j;
    }

    private void a(long j, byte[] bArr, int i) {
        for (int i2 = 0; i2 < 8; i2++) {
            bArr[i2 + i] = (byte) ((int) j);
            j >>>= 8;
        }
    }

    private void a(byte[] bArr) {
        byte[] bArr2 = bArr;
        long[] jArr = new long[((bArr2.length + 7) / 8)];
        for (int i = 0; i != bArr2.length; i++) {
            int i2 = i / 8;
            jArr[i2] = jArr[i2] + (((long) (bArr2[i] & UnsignedBytes.MAX_VALUE)) << ((i % 8) * 8));
        }
        this.b = new long[((this.a + 1) * 2)];
        this.b[0] = -5196783011329398165L;
        for (int i3 = 1; i3 < this.b.length; i3++) {
            this.b[i3] = this.b[i3 - 1] - 7046029254386353131L;
        }
        int length = (jArr.length > this.b.length ? jArr.length : this.b.length) * 3;
        long j = 0;
        int i4 = 0;
        long j2 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            long[] jArr2 = this.b;
            j2 = a(this.b[i5] + j2 + j, 3);
            jArr2[i5] = j2;
            j = a(jArr[i4] + j2 + j, j2 + j);
            jArr[i4] = j;
            i5 = (i5 + 1) % this.b.length;
            i4 = (i4 + 1) % jArr.length;
        }
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        long a2 = a(bArr, i);
        long a3 = a(bArr, i + 8);
        int i3 = this.a;
        while (i3 >= 1) {
            int i4 = i3 * 2;
            long b2 = b(a3 - this.b[i4 + 1], a2) ^ a2;
            a2 = b(a2 - this.b[i4], b2) ^ b2;
            i3--;
            a3 = b2;
        }
        a(a2 - this.b[0], bArr2, i2);
        a(a3 - this.b[1], bArr2, i2 + 8);
        return 16;
    }

    private long b(long j, long j2) {
        long j3 = j2 & 63;
        return (j >>> ((int) j3)) | (j << ((int) (64 - j3)));
    }

    public String getAlgorithmName() {
        return "RC5-64";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof RC5Parameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to RC564 init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
        this.c = z;
        this.a = rC5Parameters.getRounds();
        a(rC5Parameters.getKey());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.c ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
    }

    public void reset() {
    }
}
