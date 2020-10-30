package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class ISO9797Alg3Mac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private BlockCipherPadding e;
    private int f;
    private KeyParameter g;
    private KeyParameter h;

    public ISO9797Alg3Mac(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8, null);
    }

    public ISO9797Alg3Mac(BlockCipher blockCipher, int i) {
        this(blockCipher, i, null);
    }

    public ISO9797Alg3Mac(BlockCipher blockCipher, int i, BlockCipherPadding blockCipherPadding) {
        if (i % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        } else if (!(blockCipher instanceof DESEngine)) {
            throw new IllegalArgumentException("cipher must be instance of DESEngine");
        } else {
            this.d = new CBCBlockCipher(blockCipher);
            this.e = blockCipherPadding;
            this.f = i / 8;
            this.a = new byte[blockCipher.getBlockSize()];
            this.b = new byte[blockCipher.getBlockSize()];
            this.c = 0;
        }
    }

    public ISO9797Alg3Mac(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this(blockCipher, blockCipher.getBlockSize() * 8, blockCipherPadding);
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
        DESEngine dESEngine = new DESEngine();
        dESEngine.init(false, this.g);
        dESEngine.processBlock(this.a, 0, this.a, 0);
        dESEngine.init(true, this.h);
        dESEngine.processBlock(this.a, 0, this.a, 0);
        System.arraycopy(this.a, 0, bArr, i, this.f);
        reset();
        return this.f;
    }

    public String getAlgorithmName() {
        return "ISO9797Alg3";
    }

    public int getMacSize() {
        return this.f;
    }

    public void init(CipherParameters cipherParameters) {
        KeyParameter keyParameter;
        reset();
        boolean z = cipherParameters instanceof KeyParameter;
        if (z || (cipherParameters instanceof ParametersWithIV)) {
            byte[] key = (z ? (KeyParameter) cipherParameters : (KeyParameter) ((ParametersWithIV) cipherParameters).getParameters()).getKey();
            if (key.length == 16) {
                keyParameter = new KeyParameter(key, 0, 8);
                this.g = new KeyParameter(key, 8, 8);
                this.h = keyParameter;
            } else if (key.length == 24) {
                keyParameter = new KeyParameter(key, 0, 8);
                this.g = new KeyParameter(key, 8, 8);
                this.h = new KeyParameter(key, 16, 8);
            } else {
                throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
            }
            if (cipherParameters instanceof ParametersWithIV) {
                this.d.init(true, new ParametersWithIV(keyParameter, ((ParametersWithIV) cipherParameters).getIV()));
            } else {
                this.d.init(true, keyParameter);
            }
        } else {
            throw new IllegalArgumentException("params must be an instance of KeyParameter or ParametersWithIV");
        }
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
