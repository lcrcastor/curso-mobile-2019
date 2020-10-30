package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class RC4Engine implements StreamCipher {
    private byte[] a = null;
    private int b = 0;
    private int c = 0;
    private byte[] d = null;

    private void a(byte[] bArr) {
        this.d = bArr;
        this.b = 0;
        this.c = 0;
        if (this.a == null) {
            this.a = new byte[256];
        }
        for (int i = 0; i < 256; i++) {
            this.a[i] = (byte) i;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            i3 = ((bArr[i2] & UnsignedBytes.MAX_VALUE) + this.a[i4] + i3) & 255;
            byte b2 = this.a[i4];
            this.a[i4] = this.a[i3];
            this.a[i3] = b2;
            i2 = (i2 + 1) % bArr.length;
        }
    }

    public String getAlgorithmName() {
        return "RC4";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.d = ((KeyParameter) cipherParameters).getKey();
            a(this.d);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to RC4 init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                this.b = (this.b + 1) & 255;
                this.c = (this.a[this.b] + this.c) & 255;
                byte b2 = this.a[this.b];
                this.a[this.b] = this.a[this.c];
                this.a[this.c] = b2;
                bArr2[i4 + i3] = (byte) (bArr[i4 + i] ^ this.a[(this.a[this.b] + this.a[this.c]) & 255]);
            }
            return i2;
        }
    }

    public void reset() {
        a(this.d);
    }

    public byte returnByte(byte b2) {
        this.b = (this.b + 1) & 255;
        this.c = (this.a[this.b] + this.c) & 255;
        byte b3 = this.a[this.b];
        this.a[this.b] = this.a[this.c];
        this.a[this.c] = b3;
        return (byte) (b2 ^ this.a[(this.a[this.b] + this.a[this.c]) & 255]);
    }
}
