package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class RFC3394WrapEngine implements Wrapper {
    private BlockCipher a;
    private KeyParameter b;
    private boolean c;
    private byte[] d = {-90, -90, -90, -90, -90, -90, -90, -90};

    public RFC3394WrapEngine(BlockCipher blockCipher) {
        this.a = blockCipher;
    }

    public String getAlgorithmName() {
        return this.a.getAlgorithmName();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.c = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        if (cipherParameters instanceof KeyParameter) {
            this.b = (KeyParameter) cipherParameters;
            return;
        }
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.d = parametersWithIV.getIV();
            this.b = (KeyParameter) parametersWithIV.getParameters();
            if (this.d.length != 8) {
                throw new IllegalArgumentException("IV not equal to 8");
            }
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        byte[] bArr2 = new byte[(i2 - this.d.length)];
        byte[] bArr3 = new byte[this.d.length];
        byte[] bArr4 = new byte[(this.d.length + 8)];
        System.arraycopy(bArr, i, bArr3, 0, this.d.length);
        System.arraycopy(bArr, i + this.d.length, bArr2, 0, i2 - this.d.length);
        this.a.init(false, this.b);
        int i4 = i3 - 1;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = i4; i6 >= 1; i6--) {
                System.arraycopy(bArr3, 0, bArr4, 0, this.d.length);
                int i7 = (i6 - 1) * 8;
                System.arraycopy(bArr2, i7, bArr4, this.d.length, 8);
                int i8 = (i4 * i5) + i6;
                int i9 = 1;
                while (i8 != 0) {
                    int length = this.d.length - i9;
                    bArr4[length] = (byte) (((byte) i8) ^ bArr4[length]);
                    i8 >>>= 8;
                    i9++;
                }
                this.a.processBlock(bArr4, 0, bArr4, 0);
                System.arraycopy(bArr4, 0, bArr3, 0, 8);
                System.arraycopy(bArr4, 8, bArr2, i7, 8);
            }
        }
        if (Arrays.constantTimeAreEqual(bArr3, this.d)) {
            return bArr2;
        }
        throw new InvalidCipherTextException("checksum failed");
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.c) {
            throw new IllegalStateException("not set for wrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
        }
        byte[] bArr2 = new byte[(this.d.length + i2)];
        byte[] bArr3 = new byte[(this.d.length + 8)];
        System.arraycopy(this.d, 0, bArr2, 0, this.d.length);
        System.arraycopy(bArr, i, bArr2, this.d.length, i2);
        this.a.init(true, this.b);
        for (int i4 = 0; i4 != 6; i4++) {
            for (int i5 = 1; i5 <= i3; i5++) {
                System.arraycopy(bArr2, 0, bArr3, 0, this.d.length);
                int i6 = i5 * 8;
                System.arraycopy(bArr2, i6, bArr3, this.d.length, 8);
                this.a.processBlock(bArr3, 0, bArr3, 0);
                int i7 = (i3 * i4) + i5;
                int i8 = 1;
                while (i7 != 0) {
                    int length = this.d.length - i8;
                    bArr3[length] = (byte) (((byte) i7) ^ bArr3[length]);
                    i7 >>>= 8;
                    i8++;
                }
                System.arraycopy(bArr3, 0, bArr2, 0, 8);
                System.arraycopy(bArr3, 8, bArr2, i6, 8);
            }
        }
        return bArr2;
    }
}
