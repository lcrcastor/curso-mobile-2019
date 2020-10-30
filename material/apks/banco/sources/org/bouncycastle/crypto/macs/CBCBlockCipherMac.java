package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;

public class CBCBlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private BlockCipherPadding e;
    private int f;

    public CBCBlockCipherMac(BlockCipher blockCipher) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int i) {
        this(blockCipher, i, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int i, BlockCipherPadding blockCipherPadding) {
        if (i % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        }
        this.d = new CBCBlockCipher(blockCipher);
        this.e = blockCipherPadding;
        this.f = i / 8;
        this.a = new byte[blockCipher.getBlockSize()];
        this.b = new byte[blockCipher.getBlockSize()];
        this.c = 0;
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this(blockCipher, (blockCipher.getBlockSize() * 8) / 2, blockCipherPadding);
    }

    public int doFinal(byte[] bArr, int i) {
        int blockSize = this.d.getBlockSize();
        if (this.e == null) {
            while (this.c < blockSize) {
                this.b[this.c] = 0;
                this.c++;
            }
        } else {
            if (this.c == blockSize) {
                this.d.processBlock(this.b, 0, this.a, 0);
                this.c = 0;
            }
            this.e.addPadding(this.b, this.c);
        }
        this.d.processBlock(this.b, 0, this.a, 0);
        System.arraycopy(this.a, 0, bArr, i, this.f);
        reset();
        return this.f;
    }

    public String getAlgorithmName() {
        return this.d.getAlgorithmName();
    }

    public int getMacSize() {
        return this.f;
    }

    public void init(CipherParameters cipherParameters) {
        reset();
        this.d.init(true, cipherParameters);
    }

    public void reset() {
        for (int i = 0; i < this.b.length; i++) {
            this.b[i] = 0;
        }
        this.c = 0;
        this.d.reset();
    }

    public void update(byte b2) {
        if (this.c == this.b.length) {
            this.d.processBlock(this.b, 0, this.a, 0);
            this.c = 0;
        }
        byte[] bArr = this.b;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = b2;
    }

    public void update(byte[] bArr, int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = this.d.getBlockSize();
        int i3 = blockSize - this.c;
        if (i2 > i3) {
            System.arraycopy(bArr, i, this.b, this.c, i3);
            this.d.processBlock(this.b, 0, this.a, 0);
            this.c = 0;
            i2 -= i3;
            i += i3;
            while (i2 > blockSize) {
                this.d.processBlock(bArr, i, this.a, 0);
                i2 -= blockSize;
                i += blockSize;
            }
        }
        System.arraycopy(bArr, i, this.b, this.c, i2);
        this.c += i2;
    }
}
