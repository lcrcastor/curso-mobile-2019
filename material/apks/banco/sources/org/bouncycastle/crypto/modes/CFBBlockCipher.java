package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class CFBBlockCipher extends StreamBlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private int e;
    private BlockCipher f = null;
    private boolean g;
    private int h;

    public CFBBlockCipher(BlockCipher blockCipher, int i) {
        super(blockCipher);
        this.f = blockCipher;
        this.e = i / 8;
        this.a = new byte[blockCipher.getBlockSize()];
        this.b = new byte[blockCipher.getBlockSize()];
        this.c = new byte[blockCipher.getBlockSize()];
        this.d = new byte[this.e];
    }

    private byte a(byte b2) {
        if (this.h == 0) {
            this.f.processBlock(this.b, 0, this.c, 0);
        }
        byte b3 = (byte) (b2 ^ this.c[this.h]);
        byte[] bArr = this.d;
        int i = this.h;
        this.h = i + 1;
        bArr[i] = b3;
        if (this.h == this.e) {
            this.h = 0;
            System.arraycopy(this.b, this.e, this.b, 0, this.b.length - this.e);
            System.arraycopy(this.d, 0, this.b, this.b.length - this.e, this.e);
        }
        return b3;
    }

    private byte b(byte b2) {
        if (this.h == 0) {
            this.f.processBlock(this.b, 0, this.c, 0);
        }
        this.d[this.h] = b2;
        byte[] bArr = this.c;
        int i = this.h;
        this.h = i + 1;
        byte b3 = (byte) (b2 ^ bArr[i]);
        if (this.h == this.e) {
            this.h = 0;
            System.arraycopy(this.b, this.e, this.b, 0, this.b.length - this.e);
            System.arraycopy(this.d, 0, this.b, this.b.length - this.e, this.e);
        }
        return b3;
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b2) {
        return this.g ? a(b2) : b(b2);
    }

    public int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        processBytes(bArr, i, this.e, bArr2, i2);
        return this.e;
    }

    public int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        processBytes(bArr, i, this.e, bArr2, i2);
        return this.e;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f.getAlgorithmName());
        sb.append("/CFB");
        sb.append(this.e * 8);
        return sb.toString();
    }

    public int getBlockSize() {
        return this.e;
    }

    public byte[] getCurrentIV() {
        return Arrays.clone(this.b);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.g = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length < this.a.length) {
                System.arraycopy(iv, 0, this.a, this.a.length - iv.length, iv.length);
                for (int i = 0; i < this.a.length - iv.length; i++) {
                    this.a[i] = 0;
                }
            } else {
                System.arraycopy(iv, 0, this.a, 0, this.a.length);
            }
            reset();
            if (parametersWithIV.getParameters() != null) {
                blockCipher = this.f;
                cipherParameters = parametersWithIV.getParameters();
            } else {
                return;
            }
        } else {
            reset();
            if (cipherParameters != null) {
                blockCipher = this.f;
            } else {
                return;
            }
        }
        blockCipher.init(true, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        processBytes(bArr, i, this.e, bArr2, i2);
        return this.e;
    }

    public void reset() {
        System.arraycopy(this.a, 0, this.b, 0, this.a.length);
        Arrays.fill(this.d, 0);
        this.h = 0;
        this.f.reset();
    }
}
