package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC5Parameters;

public class RC532Engine implements BlockCipher {
    private int a = 12;
    private int[] b = null;
    private boolean c;

    private int a(int i, int i2) {
        int i3 = i2 & 31;
        return (i >>> (32 - i3)) | (i << i3);
    }

    private int a(byte[] bArr, int i) {
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i) + this.b[0];
        int a3 = a(bArr, i + 4) + this.b[1];
        for (int i3 = 1; i3 <= this.a; i3++) {
            int i4 = i3 * 2;
            a2 = a(a2 ^ a3, a3) + this.b[i4];
            a3 = a(a3 ^ a2, a2) + this.b[i4 + 1];
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 4);
        return 8;
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
    }

    private void a(byte[] bArr) {
        int[] iArr = new int[((bArr.length + 3) / 4)];
        for (int i = 0; i != bArr.length; i++) {
            int i2 = i / 4;
            iArr[i2] = iArr[i2] + ((bArr[i] & UnsignedBytes.MAX_VALUE) << ((i % 4) * 8));
        }
        this.b = new int[((this.a + 1) * 2)];
        this.b[0] = -1209970333;
        for (int i3 = 1; i3 < this.b.length; i3++) {
            this.b[i3] = this.b[i3 - 1] - 1640531527;
        }
        int length = (iArr.length > this.b.length ? iArr.length : this.b.length) * 3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < length; i8++) {
            int[] iArr2 = this.b;
            i5 = a(this.b[i4] + i5 + i6, 3);
            iArr2[i4] = i5;
            i6 = a(iArr[i7] + i5 + i6, i6 + i5);
            iArr[i7] = i6;
            i4 = (i4 + 1) % this.b.length;
            i7 = (i7 + 1) % iArr.length;
        }
    }

    private int b(int i, int i2) {
        int i3 = i2 & 31;
        return (i << (32 - i3)) | (i >>> i3);
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        for (int i3 = this.a; i3 >= 1; i3--) {
            int i4 = i3 * 2;
            a3 = b(a3 - this.b[i4 + 1], a2) ^ a2;
            a2 = b(a2 - this.b[i4], a3) ^ a3;
        }
        a(a2 - this.b[0], bArr2, i2);
        a(a3 - this.b[1], bArr2, i2 + 4);
        return 8;
    }

    public String getAlgorithmName() {
        return "RC5-32";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        byte[] key;
        if (cipherParameters instanceof RC5Parameters) {
            RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
            this.a = rC5Parameters.getRounds();
            key = rC5Parameters.getKey();
        } else if (cipherParameters instanceof KeyParameter) {
            key = ((KeyParameter) cipherParameters).getKey();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to RC532 init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        a(key);
        this.c = z;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.c ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
    }

    public void reset() {
    }
}
