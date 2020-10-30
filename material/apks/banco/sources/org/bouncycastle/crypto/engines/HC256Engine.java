package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class HC256Engine implements StreamCipher {
    private int[] a = new int[1024];
    private int[] b = new int[1024];
    private int c = 0;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private byte[] g = new byte[4];
    private int h = 0;

    private int a() {
        int i;
        int i2;
        int i3 = this.c & 1023;
        if (this.c < 1024) {
            int i4 = this.a[(i3 - 3) & 1023];
            int i5 = this.a[(i3 - 1023) & 1023];
            int[] iArr = this.a;
            iArr[i3] = iArr[i3] + this.a[(i3 - 10) & 1023] + (a(i5, 23) ^ a(i4, 10)) + this.b[(i4 ^ i5) & 1023];
            int i6 = this.a[(i3 - 12) & 1023];
            i = this.b[i6 & 255] + this.b[((i6 >> 8) & 255) + 256] + this.b[((i6 >> 16) & 255) + 512] + this.b[((i6 >> 24) & 255) + 768];
            i2 = this.a[i3];
        } else {
            int i7 = this.b[(i3 - 3) & 1023];
            int i8 = this.b[(i3 - 1023) & 1023];
            int[] iArr2 = this.b;
            iArr2[i3] = iArr2[i3] + this.b[(i3 - 10) & 1023] + (a(i8, 23) ^ a(i7, 10)) + this.a[(i7 ^ i8) & 1023];
            int i9 = this.b[(i3 - 12) & 1023];
            i = this.a[i9 & 255] + this.a[((i9 >> 8) & 255) + 256] + this.a[((i9 >> 16) & 255) + 512] + this.a[((i9 >> 24) & 255) + 768];
            i2 = this.b[i3];
        }
        int i10 = i2 ^ i;
        this.c = (this.c + 1) & 2047;
        return i10;
    }

    private static int a(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private void b() {
        if (this.d.length != 32 && this.d.length != 16) {
            throw new IllegalArgumentException("The key must be 128/256 bits long");
        } else if (this.e.length < 16) {
            throw new IllegalArgumentException("The IV must be at least 128 bits long");
        } else {
            if (this.d.length != 32) {
                byte[] bArr = new byte[32];
                System.arraycopy(this.d, 0, bArr, 0, this.d.length);
                System.arraycopy(this.d, 0, bArr, 16, this.d.length);
                this.d = bArr;
            }
            if (this.e.length < 32) {
                byte[] bArr2 = new byte[32];
                System.arraycopy(this.e, 0, bArr2, 0, this.e.length);
                System.arraycopy(this.e, 0, bArr2, this.e.length, bArr2.length - this.e.length);
                this.e = bArr2;
            }
            this.h = 0;
            this.c = 0;
            int[] iArr = new int[2560];
            for (int i = 0; i < 32; i++) {
                int i2 = i >> 2;
                iArr[i2] = iArr[i2] | ((this.d[i] & UnsignedBytes.MAX_VALUE) << ((i & 3) * 8));
            }
            for (int i3 = 0; i3 < 32; i3++) {
                int i4 = (i3 >> 2) + 8;
                iArr[i4] = iArr[i4] | ((this.e[i3] & UnsignedBytes.MAX_VALUE) << ((i3 & 3) * 8));
            }
            for (int i5 = 16; i5 < 2560; i5++) {
                int i6 = iArr[i5 - 2];
                int i7 = iArr[i5 - 15];
                iArr[i5] = ((i6 >>> 10) ^ (a(i6, 17) ^ a(i6, 19))) + iArr[i5 - 7] + ((i7 >>> 3) ^ (a(i7, 7) ^ a(i7, 18))) + iArr[i5 - 16] + i5;
            }
            System.arraycopy(iArr, 512, this.a, 0, 1024);
            System.arraycopy(iArr, 1536, this.b, 0, 1024);
            for (int i8 = 0; i8 < 4096; i8++) {
                a();
            }
            this.c = 0;
        }
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

    public String getAlgorithmName() {
        return "HC-256";
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
        sb.append("Invalid parameter passed to HC256 init - ");
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
