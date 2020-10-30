package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;

public class ISAACEngine implements StreamCipher {
    private final int a = 8;
    private final int b = 256;
    private int[] c = null;
    private int[] d = null;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private byte[] i = new byte[1024];
    private byte[] j = null;
    private boolean k = false;

    private void a() {
        int i2;
        int i3;
        int i4 = this.f;
        int i5 = this.g + 1;
        this.g = i5;
        this.f = i4 + i5;
        for (int i6 = 0; i6 < 256; i6++) {
            int i7 = this.c[i6];
            switch (i6 & 3) {
                case 0:
                    i3 = this.e;
                    i2 = this.e << 13;
                    break;
                case 1:
                    i3 = this.e;
                    i2 = this.e >>> 6;
                    break;
                case 2:
                    i3 = this.e;
                    i2 = this.e << 2;
                    break;
                case 3:
                    i3 = this.e;
                    i2 = this.e >>> 16;
                    break;
            }
            this.e = i3 ^ i2;
            this.e += this.c[(i6 + 128) & 255];
            int i8 = this.c[(i7 >>> 2) & 255] + this.e + this.f;
            this.c[i6] = i8;
            int[] iArr = this.d;
            int i9 = this.c[(i8 >>> 10) & 255] + i7;
            this.f = i9;
            iArr[i6] = i9;
        }
    }

    private void a(byte[] bArr) {
        this.j = bArr;
        if (this.c == null) {
            this.c = new int[256];
        }
        if (this.d == null) {
            this.d = new int[256];
        }
        for (int i2 = 0; i2 < 256; i2++) {
            int[] iArr = this.c;
            this.d[i2] = 0;
            iArr[i2] = 0;
        }
        this.g = 0;
        this.f = 0;
        this.e = 0;
        this.h = 0;
        byte[] bArr2 = new byte[(bArr.length + (bArr.length & 3))];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int i3 = 0; i3 < bArr2.length; i3 += 4) {
            this.d[i3 >>> 2] = Pack.littleEndianToInt(bArr2, i3);
        }
        int[] iArr2 = new int[8];
        for (int i4 = 0; i4 < 8; i4++) {
            iArr2[i4] = -1640531527;
        }
        for (int i5 = 0; i5 < 4; i5++) {
            a(iArr2);
        }
        int i6 = 0;
        while (i6 < 2) {
            for (int i7 = 0; i7 < 256; i7 += 8) {
                for (int i8 = 0; i8 < 8; i8++) {
                    iArr2[i8] = iArr2[i8] + (i6 < 1 ? this.d[i7 + i8] : this.c[i7 + i8]);
                }
                a(iArr2);
                for (int i9 = 0; i9 < 8; i9++) {
                    this.c[i7 + i9] = iArr2[i9];
                }
            }
            i6++;
        }
        a();
        this.k = true;
    }

    private void a(int[] iArr) {
        iArr[0] = iArr[0] ^ (iArr[1] << 11);
        iArr[3] = iArr[3] + iArr[0];
        iArr[1] = iArr[1] + iArr[2];
        iArr[1] = iArr[1] ^ (iArr[2] >>> 2);
        iArr[4] = iArr[4] + iArr[1];
        iArr[2] = iArr[2] + iArr[3];
        iArr[2] = iArr[2] ^ (iArr[3] << 8);
        iArr[5] = iArr[5] + iArr[2];
        iArr[3] = iArr[3] + iArr[4];
        iArr[3] = iArr[3] ^ (iArr[4] >>> 16);
        iArr[6] = iArr[6] + iArr[3];
        iArr[4] = iArr[4] + iArr[5];
        iArr[4] = iArr[4] ^ (iArr[5] << 10);
        iArr[7] = iArr[7] + iArr[4];
        iArr[5] = iArr[5] + iArr[6];
        iArr[5] = (iArr[6] >>> 4) ^ iArr[5];
        iArr[0] = iArr[0] + iArr[5];
        iArr[6] = iArr[6] + iArr[7];
        iArr[6] = iArr[6] ^ (iArr[7] << 8);
        iArr[1] = iArr[1] + iArr[6];
        iArr[7] = iArr[7] + iArr[0];
        iArr[7] = iArr[7] ^ (iArr[0] >>> 9);
        iArr[2] = iArr[2] + iArr[7];
        iArr[0] = iArr[0] + iArr[1];
    }

    public String getAlgorithmName() {
        return "ISAAC";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to ISAAC init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        a(((KeyParameter) cipherParameters).getKey());
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (!this.k) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i4 + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            for (int i5 = 0; i5 < i3; i5++) {
                if (this.h == 0) {
                    a();
                    this.i = Pack.intToBigEndian(this.d);
                }
                bArr2[i5 + i4] = (byte) (this.i[this.h] ^ bArr[i5 + i2]);
                this.h = (this.h + 1) & 1023;
            }
            return i3;
        }
    }

    public void reset() {
        a(this.j);
    }

    public byte returnByte(byte b2) {
        if (this.h == 0) {
            a();
            this.i = Pack.intToBigEndian(this.d);
        }
        byte b3 = (byte) (b2 ^ this.i[this.h]);
        this.h = (this.h + 1) & 1023;
        return b3;
    }
}
