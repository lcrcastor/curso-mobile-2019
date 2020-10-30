package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class CBCBlockCipher implements BlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e = null;
    private boolean f;

    public CBCBlockCipher(BlockCipher blockCipher) {
        this.e = blockCipher;
        this.d = blockCipher.getBlockSize();
        this.a = new byte[this.d];
        this.b = new byte[this.d];
        this.c = new byte[this.d];
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.d + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i3 = 0; i3 < this.d; i3++) {
            byte[] bArr3 = this.b;
            bArr3[i3] = (byte) (bArr3[i3] ^ bArr[i + i3]);
        }
        int processBlock = this.e.processBlock(this.b, 0, bArr2, i2);
        System.arraycopy(bArr2, i2, this.b, 0, this.b.length);
        return processBlock;
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.d + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        System.arraycopy(bArr, i, this.c, 0, this.d);
        int processBlock = this.e.processBlock(bArr, i, bArr2, i2);
        for (int i3 = 0; i3 < this.d; i3++) {
            int i4 = i2 + i3;
            bArr2[i4] = (byte) (bArr2[i4] ^ this.b[i3]);
        }
        byte[] bArr3 = this.b;
        this.b = this.c;
        this.c = bArr3;
        return processBlock;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.e.getAlgorithmName());
        sb.append("/CBC");
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
        boolean z2 = this.f;
        this.f = z;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length != this.d) {
                throw new IllegalArgumentException("initialisation vector must be the same length as block size");
            }
            System.arraycopy(iv, 0, this.a, 0, iv.length);
            reset();
            if (parametersWithIV.getParameters() != null) {
                blockCipher = this.e;
                cipherParameters = parametersWithIV.getParameters();
            } else {
                if (z2 != z) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
                return;
            }
        } else {
            reset();
            if (cipherParameters != null) {
                blockCipher = this.e;
            } else {
                if (z2 != z) {
                    throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                }
                return;
            }
        }
        blockCipher.init(z, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.f ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
    }

    public void reset() {
        System.arraycopy(this.a, 0, this.b, 0, this.a.length);
        Arrays.fill(this.c, 0);
        this.e.reset();
    }
}
