package org.bouncycastle.crypto;

public class BufferedAsymmetricBlockCipher {
    private final AsymmetricBlockCipher a;
    protected byte[] buf;
    protected int bufOff;

    public BufferedAsymmetricBlockCipher(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.a = asymmetricBlockCipher;
    }

    public byte[] doFinal() {
        byte[] processBlock = this.a.processBlock(this.buf, 0, this.bufOff);
        reset();
        return processBlock;
    }

    public int getBufferPosition() {
        return this.bufOff;
    }

    public int getInputBlockSize() {
        return this.a.getInputBlockSize();
    }

    public int getOutputBlockSize() {
        return this.a.getOutputBlockSize();
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.a;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        reset();
        this.a.init(z, cipherParameters);
        this.buf = new byte[(this.a.getInputBlockSize() + (z ? 1 : 0))];
        this.bufOff = 0;
    }

    public void processByte(byte b) {
        if (this.bufOff >= this.buf.length) {
            throw new DataLengthException("attempt to process message too long for cipher");
        }
        byte[] bArr = this.buf;
        int i = this.bufOff;
        this.bufOff = i + 1;
        bArr[i] = b;
    }

    public void processBytes(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            if (i2 < 0) {
                throw new IllegalArgumentException("Can't have a negative input length!");
            } else if (this.bufOff + i2 > this.buf.length) {
                throw new DataLengthException("attempt to process message too long for cipher");
            } else {
                System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
                this.bufOff += i2;
            }
        }
    }

    public void reset() {
        if (this.buf != null) {
            for (int i = 0; i < this.buf.length; i++) {
                this.buf[i] = 0;
            }
        }
        this.bufOff = 0;
    }
}
