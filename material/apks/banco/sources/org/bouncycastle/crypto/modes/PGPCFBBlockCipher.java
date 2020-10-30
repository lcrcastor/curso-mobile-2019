package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class PGPCFBBlockCipher implements BlockCipher {
    private byte[] a = new byte[this.g];
    private byte[] b = new byte[this.g];
    private byte[] c = new byte[this.g];
    private byte[] d = new byte[this.g];
    private BlockCipher e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;

    public PGPCFBBlockCipher(BlockCipher blockCipher, boolean z) {
        this.e = blockCipher;
        this.i = z;
        this.g = blockCipher.getBlockSize();
    }

    private byte a(byte b2, int i2) {
        return (byte) (b2 ^ this.c[i2]);
    }

    private int a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.g + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.f != 0) {
            if (this.f >= this.g + 2) {
                if (this.g + i3 > bArr2.length) {
                    throw new DataLengthException("output buffer too short");
                }
                this.e.processBlock(this.b, 0, this.c, 0);
                for (int i4 = 0; i4 < this.g; i4++) {
                    bArr2[i3 + i4] = a(bArr[i2 + i4], i4);
                }
                System.arraycopy(bArr2, i3, this.b, 0, this.g);
            }
            return this.g;
        } else if ((this.g * 2) + i3 + 2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i5 = 0; i5 < this.g; i5++) {
                bArr2[i3 + i5] = a(this.a[i5], i5);
            }
            System.arraycopy(bArr2, i3, this.b, 0, this.g);
            this.e.processBlock(this.b, 0, this.c, 0);
            bArr2[this.g + i3] = a(this.a[this.g - 2], 0);
            bArr2[this.g + i3 + 1] = a(this.a[this.g - 1], 1);
            System.arraycopy(bArr2, i3 + 2, this.b, 0, this.g);
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i6 = 0; i6 < this.g; i6++) {
                bArr2[this.g + i3 + 2 + i6] = a(bArr[i2 + i6], i6);
            }
            System.arraycopy(bArr2, i3 + this.g + 2, this.b, 0, this.g);
            this.f += (this.g * 2) + 2;
            return (this.g * 2) + 2;
        }
    }

    private int b(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.g + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.g + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else if (this.f == 0) {
            for (int i4 = 0; i4 < this.g; i4++) {
                this.b[i4] = bArr[i2 + i4];
            }
            this.e.processBlock(this.b, 0, this.c, 0);
            this.f += this.g;
            return 0;
        } else if (this.f == this.g) {
            System.arraycopy(bArr, i2, this.d, 0, this.g);
            System.arraycopy(this.b, 2, this.b, 0, this.g - 2);
            this.b[this.g - 2] = this.d[0];
            this.b[this.g - 1] = this.d[1];
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i5 = 0; i5 < this.g - 2; i5++) {
                bArr2[i3 + i5] = a(this.d[i5 + 2], i5);
            }
            System.arraycopy(this.d, 2, this.b, 0, this.g - 2);
            this.f += 2;
            return this.g - 2;
        } else {
            if (this.f >= this.g + 2) {
                System.arraycopy(bArr, i2, this.d, 0, this.g);
                bArr2[i3 + 0] = a(this.d[0], this.g - 2);
                bArr2[i3 + 1] = a(this.d[1], this.g - 1);
                System.arraycopy(this.d, 0, this.b, this.g - 2, 2);
                this.e.processBlock(this.b, 0, this.c, 0);
                for (int i6 = 0; i6 < this.g - 2; i6++) {
                    bArr2[i3 + i6 + 2] = a(this.d[i6 + 2], i6);
                }
                System.arraycopy(this.d, 2, this.b, 0, this.g - 2);
            }
            return this.g;
        }
    }

    private int c(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.g + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.g + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i4 = 0; i4 < this.g; i4++) {
                bArr2[i3 + i4] = a(bArr[i2 + i4], i4);
            }
            for (int i5 = 0; i5 < this.g; i5++) {
                this.b[i5] = bArr2[i3 + i5];
            }
            return this.g;
        }
    }

    private int d(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.g + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.g + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i4 = 0; i4 < this.g; i4++) {
                bArr2[i3 + i4] = a(bArr[i2 + i4], i4);
            }
            for (int i5 = 0; i5 < this.g; i5++) {
                this.b[i5] = bArr[i2 + i5];
            }
            return this.g;
        }
    }

    public String getAlgorithmName() {
        StringBuilder sb;
        String str;
        if (this.i) {
            sb = new StringBuilder();
            sb.append(this.e.getAlgorithmName());
            str = "/PGPCFBwithIV";
        } else {
            sb = new StringBuilder();
            sb.append(this.e.getAlgorithmName());
            str = "/PGPCFB";
        }
        sb.append(str);
        return sb.toString();
    }

    public int getBlockSize() {
        return this.e.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.e;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.h = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length < this.a.length) {
                System.arraycopy(iv, 0, this.a, this.a.length - iv.length, iv.length);
                for (int i2 = 0; i2 < this.a.length - iv.length; i2++) {
                    this.a[i2] = 0;
                }
            } else {
                System.arraycopy(iv, 0, this.a, 0, this.a.length);
            }
            reset();
            blockCipher = this.e;
            cipherParameters = parametersWithIV.getParameters();
        } else {
            reset();
            blockCipher = this.e;
        }
        blockCipher.init(true, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        return this.i ? this.h ? a(bArr, i2, bArr2, i3) : b(bArr, i2, bArr2, i3) : this.h ? c(bArr, i2, bArr2, i3) : d(bArr, i2, bArr2, i3);
    }

    public void reset() {
        this.f = 0;
        for (int i2 = 0; i2 != this.b.length; i2++) {
            if (this.i) {
                this.b[i2] = 0;
            } else {
                this.b[i2] = this.a[i2];
            }
        }
        this.e.reset();
    }
}
