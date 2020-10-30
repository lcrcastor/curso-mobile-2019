package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class XTEAEngine implements BlockCipher {
    private int[] a = new int[4];
    private int[] b = new int[32];
    private int[] c = new int[32];
    private boolean d = false;
    private boolean e;

    private int a(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b2 = (bArr[i] << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        return (bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | b2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        for (int i3 = 0; i3 < 32; i3++) {
            a2 += (((a3 << 4) ^ (a3 >>> 5)) + a3) ^ this.b[i3];
            a3 += (((a2 << 4) ^ (a2 >>> 5)) + a2) ^ this.c[i3];
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 4);
        return 8;
    }

    private void a(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i5] = (byte) i;
    }

    private void a(byte[] bArr) {
        if (bArr.length != 16) {
            throw new IllegalArgumentException("Key size must be 128 bits.");
        }
        int i = 0;
        int i2 = 0;
        while (i < 4) {
            this.a[i] = a(bArr, i2);
            i++;
            i2 += 4;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 32; i4++) {
            this.b[i4] = this.a[i3 & 3] + i3;
            i3 -= 1640531527;
            this.c[i4] = this.a[(i3 >>> 11) & 3] + i3;
        }
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        for (int i3 = 31; i3 >= 0; i3--) {
            a3 -= (((a2 << 4) ^ (a2 >>> 5)) + a2) ^ this.c[i3];
            a2 -= (((a3 << 4) ^ (a3 >>> 5)) + a3) ^ this.b[i3];
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 4);
        return 8;
    }

    public String getAlgorithmName() {
        return "XTEA";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to TEA init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        this.e = z;
        this.d = true;
        a(((KeyParameter) cipherParameters).getKey());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (!this.d) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 <= bArr2.length) {
            return this.e ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
