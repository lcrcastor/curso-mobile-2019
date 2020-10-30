package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;

public class CFBBlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private MacCFBBlockCipher d;
    private BlockCipherPadding e;
    private int f;

    public CFBBlockCipherMac(BlockCipher blockCipher) {
        this(blockCipher, 8, (blockCipher.getBlockSize() * 8) / 2, null);
    }

    public CFBBlockCipherMac(BlockCipher blockCipher, int i, int i2) {
        this(blockCipher, i, i2, null);
    }

    public CFBBlockCipherMac(BlockCipher blockCipher, int i, int i2, BlockCipherPadding blockCipherPadding) {
        this.e = null;
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        }
        this.a = new byte[blockCipher.getBlockSize()];
        this.d = new MacCFBBlockCipher(blockCipher, i);
        this.e = blockCipherPadding;
        this.f = i2 / 8;
        this.b = new byte[this.d.b()];
        this.c = 0;
    }

    public CFBBlockCipherMac(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this(blockCipher, 8, (blockCipher.getBlockSize() * 8) / 2, blockCipherPadding);
    }

    public int doFinal(byte[] bArr, int i) {
        int b2 = this.d.b();
        if (this.e == null) {
            while (this.c < b2) {
                this.b[this.c] = 0;
                this.c++;
            }
        } else {
            this.e.addPadding(this.b, this.c);
        }
        this.d.a(this.b, 0, this.a, 0);
        this.d.a(this.a);
        System.arraycopy(this.a, 0, bArr, i, this.f);
        reset();
        return this.f;
    }

    public String getAlgorithmName() {
        return this.d.a();
    }

    public int getMacSize() {
        return this.f;
    }

    public void init(CipherParameters cipherParameters) {
        reset();
        this.d.a(cipherParameters);
    }

    public void reset() {
        for (int i = 0; i < this.b.length; i++) {
            this.b[i] = 0;
        }
        this.c = 0;
        this.d.c();
    }

    public void update(byte b2) {
        if (this.c == this.b.length) {
            this.d.a(this.b, 0, this.a, 0);
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
        int b2 = this.d.b();
        int i3 = b2 - this.c;
        if (i2 > i3) {
            System.arraycopy(bArr, i, this.b, this.c, i3);
            this.d.a(this.b, 0, this.a, 0);
            this.c = 0;
            i2 -= i3;
            i += i3;
            while (i2 > b2) {
                this.d.a(bArr, i, this.a, 0);
                i2 -= b2;
                i += b2;
            }
        }
        System.arraycopy(bArr, i, this.b, this.c, i2);
        this.c += i2;
    }
}
