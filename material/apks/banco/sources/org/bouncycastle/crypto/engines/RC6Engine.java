package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class RC6Engine implements BlockCipher {
    private int[] a = null;
    private boolean b;

    private int a(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int a(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 3; i3 >= 0; i3--) {
            i2 = (i2 << 8) + (bArr[i3 + i] & UnsignedBytes.MAX_VALUE);
        }
        return i2;
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        int a4 = a(bArr, i + 8);
        int i3 = a2;
        int i4 = a3 + this.a[0];
        int i5 = a4;
        int a5 = a(bArr, i + 12) + this.a[1];
        int i6 = 1;
        while (i6 <= 20) {
            int a6 = a(((i4 * 2) + 1) * i4, 5);
            int a7 = a(((a5 * 2) + 1) * a5, 5);
            int i7 = i6 * 2;
            i6++;
            int i8 = a5;
            a5 = a(i3 ^ a6, a7) + this.a[i7];
            i3 = i4;
            i4 = a(i5 ^ a7, a6) + this.a[i7 + 1];
            i5 = i8;
        }
        int i9 = i5 + this.a[43];
        a(i3 + this.a[42], bArr2, i2);
        a(i4, bArr2, i2 + 4);
        a(i9, bArr2, i2 + 8);
        a(a5, bArr2, i2 + 12);
        return 16;
    }

    private void a(int i, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            bArr[i3 + i2] = (byte) i;
            i >>>= 8;
        }
    }

    private void a(byte[] bArr) {
        int length = (bArr.length + 3) / 4;
        int[] iArr = new int[(((bArr.length + 4) - 1) / 4)];
        for (int length2 = bArr.length - 1; length2 >= 0; length2--) {
            int i = length2 / 4;
            iArr[i] = (iArr[i] << 8) + (bArr[length2] & UnsignedBytes.MAX_VALUE);
        }
        this.a = new int[44];
        this.a[0] = -1209970333;
        for (int i2 = 1; i2 < this.a.length; i2++) {
            this.a[i2] = this.a[i2 - 1] - 1640531527;
        }
        int length3 = (iArr.length > this.a.length ? iArr.length : this.a.length) * 3;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < length3; i7++) {
            int[] iArr2 = this.a;
            i4 = a(this.a[i3] + i4 + i5, 3);
            iArr2[i3] = i4;
            i5 = a(iArr[i6] + i4 + i5, i5 + i4);
            iArr[i6] = i5;
            i3 = (i3 + 1) % this.a.length;
            i6 = (i6 + 1) % iArr.length;
        }
    }

    private int b(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        int a4 = a(bArr, i + 8);
        int a5 = a(bArr, i + 12);
        int i3 = a4 - this.a[43];
        int i4 = a2 - this.a[42];
        int i5 = 20;
        while (i5 >= 1) {
            int a6 = a(((i4 * 2) + 1) * i4, 5);
            int a7 = a(((i3 * 2) + 1) * i3, 5);
            int i6 = i5 * 2;
            i5--;
            int i7 = i4;
            i4 = b(a5 - this.a[i6], a7) ^ a6;
            a5 = i3;
            i3 = b(a3 - this.a[i6 + 1], a6) ^ a7;
            a3 = i7;
        }
        int i8 = a5 - this.a[1];
        int i9 = a3 - this.a[0];
        a(i4, bArr2, i2);
        a(i9, bArr2, i2 + 4);
        a(i3, bArr2, i2 + 8);
        a(i8, bArr2, i2 + 12);
        return 16;
    }

    public String getAlgorithmName() {
        return "RC6";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to RC6 init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        KeyParameter keyParameter = (KeyParameter) cipherParameters;
        this.b = z;
        a(keyParameter.getKey());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int blockSize = getBlockSize();
        if (this.a == null) {
            throw new IllegalStateException("RC6 engine not initialised");
        } else if (i + blockSize > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (blockSize + i2 <= bArr2.length) {
            return this.b ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
