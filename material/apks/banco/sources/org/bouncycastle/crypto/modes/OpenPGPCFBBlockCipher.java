package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;

public class OpenPGPCFBBlockCipher implements BlockCipher {
    private byte[] a = new byte[this.f];
    private byte[] b = new byte[this.f];
    private byte[] c = new byte[this.f];
    private BlockCipher d;
    private int e;
    private int f;
    private boolean g;

    public OpenPGPCFBBlockCipher(BlockCipher blockCipher) {
        this.d = blockCipher;
        this.f = blockCipher.getBlockSize();
    }

    private byte a(byte b2, int i) {
        return (byte) (b2 ^ this.c[i]);
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.f + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.f + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            int i3 = 2;
            if (this.e > this.f) {
                byte[] bArr3 = this.b;
                int i4 = this.f - 2;
                byte a2 = a(bArr[i], this.f - 2);
                bArr2[i2] = a2;
                bArr3[i4] = a2;
                byte[] bArr4 = this.b;
                int i5 = this.f - 1;
                int i6 = i2 + 1;
                byte a3 = a(bArr[i + 1], this.f - 1);
                bArr2[i6] = a3;
                bArr4[i5] = a3;
                this.d.processBlock(this.b, 0, this.c, 0);
                while (i3 < this.f) {
                    byte[] bArr5 = this.b;
                    int i7 = i3 - 2;
                    int i8 = i2 + i3;
                    byte a4 = a(bArr[i + i3], i7);
                    bArr2[i8] = a4;
                    bArr5[i7] = a4;
                    i3++;
                }
            } else {
                if (this.e == 0) {
                    this.d.processBlock(this.b, 0, this.c, 0);
                    for (int i9 = 0; i9 < this.f; i9++) {
                        byte[] bArr6 = this.b;
                        int i10 = i2 + i9;
                        byte a5 = a(bArr[i + i9], i9);
                        bArr2[i10] = a5;
                        bArr6[i9] = a5;
                    }
                } else if (this.e == this.f) {
                    this.d.processBlock(this.b, 0, this.c, 0);
                    bArr2[i2] = a(bArr[i], 0);
                    bArr2[i2 + 1] = a(bArr[i + 1], 1);
                    System.arraycopy(this.b, 2, this.b, 0, this.f - 2);
                    System.arraycopy(bArr2, i2, this.b, this.f - 2, 2);
                    this.d.processBlock(this.b, 0, this.c, 0);
                    while (i3 < this.f) {
                        byte[] bArr7 = this.b;
                        int i11 = i3 - 2;
                        int i12 = i2 + i3;
                        byte a6 = a(bArr[i + i3], i11);
                        bArr2[i12] = a6;
                        bArr7[i11] = a6;
                        i3++;
                    }
                }
                this.e += this.f;
            }
            return this.f;
        }
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.f + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.f + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            int i3 = 2;
            if (this.e > this.f) {
                byte b2 = bArr[i];
                this.b[this.f - 2] = b2;
                bArr2[i2] = a(b2, this.f - 2);
                byte b3 = bArr[i + 1];
                this.b[this.f - 1] = b3;
                bArr2[i2 + 1] = a(b3, this.f - 1);
                this.d.processBlock(this.b, 0, this.c, 0);
                while (i3 < this.f) {
                    byte b4 = bArr[i + i3];
                    int i4 = i3 - 2;
                    this.b[i4] = b4;
                    bArr2[i2 + i3] = a(b4, i4);
                    i3++;
                }
            } else {
                if (this.e == 0) {
                    this.d.processBlock(this.b, 0, this.c, 0);
                    for (int i5 = 0; i5 < this.f; i5++) {
                        int i6 = i + i5;
                        this.b[i5] = bArr[i6];
                        bArr2[i5] = a(bArr[i6], i5);
                    }
                } else if (this.e == this.f) {
                    this.d.processBlock(this.b, 0, this.c, 0);
                    byte b5 = bArr[i];
                    byte b6 = bArr[i + 1];
                    bArr2[i2] = a(b5, 0);
                    bArr2[i2 + 1] = a(b6, 1);
                    System.arraycopy(this.b, 2, this.b, 0, this.f - 2);
                    this.b[this.f - 2] = b5;
                    this.b[this.f - 1] = b6;
                    this.d.processBlock(this.b, 0, this.c, 0);
                    while (i3 < this.f) {
                        byte b7 = bArr[i + i3];
                        int i7 = i3 - 2;
                        this.b[i7] = b7;
                        bArr2[i2 + i3] = a(b7, i7);
                        i3++;
                    }
                }
                this.e += this.f;
            }
            return this.f;
        }
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.d.getAlgorithmName());
        sb.append("/OpenPGPCFB");
        return sb.toString();
    }

    public int getBlockSize() {
        return this.d.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.d;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.g = z;
        reset();
        this.d.init(true, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.g ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
    }

    public void reset() {
        this.e = 0;
        System.arraycopy(this.a, 0, this.b, 0, this.b.length);
        this.d.reset();
    }
}
