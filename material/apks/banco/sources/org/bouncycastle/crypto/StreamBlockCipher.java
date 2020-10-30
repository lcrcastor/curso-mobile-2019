package org.bouncycastle.crypto;

public abstract class StreamBlockCipher implements BlockCipher, StreamCipher {
    private final BlockCipher a;

    protected StreamBlockCipher(BlockCipher blockCipher) {
        this.a = blockCipher;
    }

    public abstract byte calculateByte(byte b);

    public BlockCipher getUnderlyingCipher() {
        return this.a;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i3 + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int i4 = i + i2;
        if (i4 > bArr.length) {
            throw new DataLengthException("input buffer too small");
        }
        while (i < i4) {
            int i5 = i3 + 1;
            int i6 = i + 1;
            bArr2[i3] = calculateByte(bArr[i]);
            i3 = i5;
            i = i6;
        }
        return i2;
    }

    public final byte returnByte(byte b) {
        return calculateByte(b);
    }
}
