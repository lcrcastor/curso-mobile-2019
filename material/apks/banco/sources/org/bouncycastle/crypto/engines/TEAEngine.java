package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class TEAEngine implements BlockCipher {
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean e = false;
    private boolean f;

    private int a(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b2 = (bArr[i] << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        return (bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | b2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        int i3 = a3;
        int i4 = 0;
        for (int i5 = 0; i5 != 32; i5++) {
            i4 -= 1640531527;
            a2 += (((i3 << 4) + this.a) ^ (i3 + i4)) ^ ((i3 >>> 5) + this.b);
            i3 += (((a2 << 4) + this.c) ^ (a2 + i4)) ^ ((a2 >>> 5) + this.d);
        }
        a(a2, bArr2, i2);
        a(i3, bArr2, i2 + 4);
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
        this.a = a(bArr, 0);
        this.b = a(bArr, 4);
        this.c = a(bArr, 8);
        this.d = a(bArr, 12);
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        int i3 = -957401312;
        for (int i4 = 0; i4 != 32; i4++) {
            a3 -= (((a2 << 4) + this.c) ^ (a2 + i3)) ^ ((a2 >>> 5) + this.d);
            a2 -= (((a3 << 4) + this.a) ^ (a3 + i3)) ^ ((a3 >>> 5) + this.b);
            i3 += 1640531527;
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 4);
        return 8;
    }

    public String getAlgorithmName() {
        return "TEA";
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
        this.f = z;
        this.e = true;
        a(((KeyParameter) cipherParameters).getKey());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (!this.e) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 <= bArr2.length) {
            return this.f ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
