package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class OFBBlockCipher extends StreamBlockCipher {
    private int a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private final int e;
    private final BlockCipher f;

    public OFBBlockCipher(BlockCipher blockCipher, int i) {
        super(blockCipher);
        this.f = blockCipher;
        this.e = i / 8;
        this.b = new byte[blockCipher.getBlockSize()];
        this.c = new byte[blockCipher.getBlockSize()];
        this.d = new byte[blockCipher.getBlockSize()];
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b2) {
        if (this.a == 0) {
            this.f.processBlock(this.c, 0, this.d, 0);
        }
        byte[] bArr = this.d;
        int i = this.a;
        this.a = i + 1;
        byte b3 = (byte) (b2 ^ bArr[i]);
        if (this.a == this.e) {
            this.a = 0;
            System.arraycopy(this.c, this.e, this.c, 0, this.c.length - this.e);
            System.arraycopy(this.d, 0, this.c, this.c.length - this.e, this.e);
        }
        return b3;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f.getAlgorithmName());
        sb.append("/OFB");
        sb.append(this.e * 8);
        return sb.toString();
    }

    public int getBlockSize() {
        return this.e;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length < this.b.length) {
                System.arraycopy(iv, 0, this.b, this.b.length - iv.length, iv.length);
                for (int i = 0; i < this.b.length - iv.length; i++) {
                    this.b[i] = 0;
                }
            } else {
                System.arraycopy(iv, 0, this.b, 0, this.b.length);
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
        System.arraycopy(this.b, 0, this.c, 0, this.b.length);
        this.a = 0;
        this.f.reset();
    }
}
