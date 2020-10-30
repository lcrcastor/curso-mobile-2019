package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class RFC5649WrapEngine implements Wrapper {
    private BlockCipher a;
    private KeyParameter b;
    private boolean c;
    private byte[] d = {-90, 89, 89, -90};
    private byte[] e = this.d;
    private byte[] f = null;

    public RFC5649WrapEngine(BlockCipher blockCipher) {
        this.a = blockCipher;
    }

    private byte[] a(byte[] bArr) {
        int length = bArr.length;
        int i = (8 - (length % 8)) % 8;
        byte[] bArr2 = new byte[(length + i)];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        if (i != 0) {
            System.arraycopy(new byte[i], 0, bArr2, length, i);
        }
        return bArr2;
    }

    private byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        int i3 = i;
        byte[] bArr3 = new byte[8];
        byte[] bArr4 = new byte[(i2 - bArr3.length)];
        byte[] bArr5 = new byte[bArr3.length];
        byte[] bArr6 = new byte[(bArr3.length + 8)];
        System.arraycopy(bArr2, i3, bArr5, 0, bArr3.length);
        System.arraycopy(bArr2, i3 + bArr3.length, bArr4, 0, i2 - bArr3.length);
        this.a.init(false, this.b);
        int i4 = (i2 / 8) - 1;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = i4; i6 >= 1; i6--) {
                System.arraycopy(bArr5, 0, bArr6, 0, bArr3.length);
                int i7 = (i6 - 1) * 8;
                System.arraycopy(bArr4, i7, bArr6, bArr3.length, 8);
                int i8 = (i4 * i5) + i6;
                int i9 = 1;
                while (i8 != 0) {
                    int length = bArr3.length - i9;
                    bArr6[length] = (byte) (bArr6[length] ^ ((byte) i8));
                    i8 >>>= 8;
                    i9++;
                }
                this.a.processBlock(bArr6, 0, bArr6, 0);
                System.arraycopy(bArr6, 0, bArr5, 0, 8);
                System.arraycopy(bArr6, 8, bArr4, i7, 8);
            }
        }
        this.f = bArr5;
        return bArr4;
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
            this.e = parametersWithIV.getIV();
            this.b = (KeyParameter) parametersWithIV.getParameters();
            if (this.e.length != 4) {
                throw new IllegalArgumentException("IV length not equal to 4");
            }
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) {
        byte[] bArr2;
        if (this.c) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        } else if (i3 == 1) {
            throw new InvalidCipherTextException("unwrap data must be at least 16 bytes");
        } else {
            byte[] bArr3 = new byte[i2];
            System.arraycopy(bArr, i, bArr3, 0, i2);
            byte[] bArr4 = new byte[i2];
            if (i3 == 2) {
                this.a.init(false, this.b);
                int i4 = 0;
                while (i4 < bArr3.length) {
                    this.a.processBlock(bArr3, i4, bArr4, i4);
                    i4 += this.a.getBlockSize();
                }
                this.f = new byte[8];
                System.arraycopy(bArr4, 0, this.f, 0, this.f.length);
                bArr2 = new byte[(bArr4.length - this.f.length)];
                System.arraycopy(bArr4, this.f.length, bArr2, 0, bArr2.length);
            } else {
                bArr2 = a(bArr, i, i2);
            }
            byte[] bArr5 = new byte[4];
            byte[] bArr6 = new byte[4];
            System.arraycopy(this.f, 0, bArr5, 0, bArr5.length);
            System.arraycopy(this.f, bArr5.length, bArr6, 0, bArr6.length);
            int bigEndianToInt = Pack.bigEndianToInt(bArr6, 0);
            boolean constantTimeAreEqual = Arrays.constantTimeAreEqual(bArr5, this.e);
            int length = bArr2.length;
            if (bigEndianToInt <= length - 8) {
                constantTimeAreEqual = false;
            }
            if (bigEndianToInt > length) {
                constantTimeAreEqual = false;
            }
            int i5 = length - bigEndianToInt;
            byte[] bArr7 = new byte[i5];
            byte[] bArr8 = new byte[i5];
            System.arraycopy(bArr2, bArr2.length - i5, bArr8, 0, i5);
            if (!Arrays.constantTimeAreEqual(bArr8, bArr7)) {
                constantTimeAreEqual = false;
            }
            byte[] bArr9 = new byte[bigEndianToInt];
            System.arraycopy(bArr2, 0, bArr9, 0, bArr9.length);
            if (constantTimeAreEqual) {
                return bArr9;
            }
            throw new InvalidCipherTextException("checksum failed");
        }
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.c) {
            throw new IllegalStateException("not set for wrapping");
        }
        byte[] bArr2 = new byte[8];
        byte[] intToBigEndian = Pack.intToBigEndian(i2);
        int i3 = 0;
        System.arraycopy(this.e, 0, bArr2, 0, this.e.length);
        System.arraycopy(intToBigEndian, 0, bArr2, this.e.length, intToBigEndian.length);
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, i, bArr3, 0, i2);
        byte[] a2 = a(bArr3);
        if (a2.length == 8) {
            byte[] bArr4 = new byte[(a2.length + bArr2.length)];
            System.arraycopy(bArr2, 0, bArr4, 0, bArr2.length);
            System.arraycopy(a2, 0, bArr4, bArr2.length, a2.length);
            this.a.init(true, this.b);
            while (i3 < bArr4.length) {
                this.a.processBlock(bArr4, i3, bArr4, i3);
                i3 += this.a.getBlockSize();
            }
            return bArr4;
        }
        RFC3394WrapEngine rFC3394WrapEngine = new RFC3394WrapEngine(this.a);
        rFC3394WrapEngine.init(true, new ParametersWithIV(this.b, bArr2));
        return rFC3394WrapEngine.wrap(a2, i, a2.length);
    }
}
