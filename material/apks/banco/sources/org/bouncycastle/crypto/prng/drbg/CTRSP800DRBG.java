package org.bouncycastle.crypto.prng.drbg;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

public class CTRSP800DRBG implements SP80090DRBG {
    private static final byte[] i = Hex.decode("000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F");
    private EntropySource a;
    private BlockCipher b;
    private int c;
    private int d;
    private byte[] e;
    private byte[] f;
    private long g = 0;
    private boolean h = false;

    public CTRSP800DRBG(BlockCipher blockCipher, int i2, int i3, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        this.a = entropySource;
        this.b = blockCipher;
        this.c = i2;
        this.d = (blockCipher.getBlockSize() * 8) + i2;
        this.h = a(blockCipher);
        if (i3 > 256) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (a(blockCipher, i2) < i3) {
            throw new IllegalArgumentException("Requested security strength is not supported by block cipher and key size");
        } else if (entropySource.entropySize() < i3) {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        } else {
            a(entropySource.getEntropy(), bArr2, bArr);
        }
    }

    private int a(BlockCipher blockCipher, int i2) {
        if (a(blockCipher) && i2 == 168) {
            return 112;
        }
        if (blockCipher.getAlgorithmName().equals("AES")) {
            return i2;
        }
        return -1;
    }

    private void a(EntropySource entropySource, byte[] bArr) {
        b(a(Arrays.concatenate(entropySource.getEntropy(), bArr), this.d), this.e, this.f);
        this.g = 1;
    }

    private void a(byte[] bArr, int i2, int i3) {
        bArr[i3 + 0] = (byte) (i2 >> 24);
        bArr[i3 + 1] = (byte) (i2 >> 16);
        bArr[i3 + 2] = (byte) (i2 >> 8);
        bArr[i3 + 3] = (byte) i2;
    }

    private void a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = i2 + 0;
        bArr2[i3 + 0] = (byte) (bArr[i4] & 254);
        int i5 = i2 + 1;
        bArr2[i3 + 1] = (byte) ((bArr[i4] << 7) | ((bArr[i5] & 252) >>> 1));
        int i6 = i2 + 2;
        bArr2[i3 + 2] = (byte) ((bArr[i5] << 6) | ((bArr[i6] & 248) >>> 2));
        int i7 = i2 + 3;
        bArr2[i3 + 3] = (byte) ((bArr[i6] << 5) | ((bArr[i7] & 240) >>> 3));
        int i8 = i2 + 4;
        bArr2[i3 + 4] = (byte) ((bArr[i7] << 4) | ((bArr[i8] & 224) >>> 4));
        int i9 = i2 + 5;
        bArr2[i3 + 5] = (byte) ((bArr[i8] << 3) | ((bArr[i9] & 192) >>> 5));
        int i10 = i2 + 6;
        bArr2[i3 + 6] = (byte) ((bArr[i9] << 2) | ((bArr[i10] & UnsignedBytes.MAX_POWER_OF_TWO) >>> 6));
        int i11 = i3 + 7;
        bArr2[i11] = (byte) (bArr[i10] << 1);
        while (i3 <= i11) {
            byte b2 = bArr2[i3];
            bArr2[i3] = (byte) (((((b2 >> 7) ^ ((((((b2 >> 1) ^ (b2 >> 2)) ^ (b2 >> 3)) ^ (b2 >> 4)) ^ (b2 >> 5)) ^ (b2 >> 6))) ^ 1) & 1) | (b2 & 254));
            i3++;
        }
    }

    private void a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] a2 = a(Arrays.concatenate(bArr, bArr2, bArr3), this.d);
        int blockSize = this.b.getBlockSize();
        this.e = new byte[((this.c + 7) / 8)];
        this.f = new byte[blockSize];
        b(a2, this.e, this.f);
        this.g = 1;
    }

    private void a(byte[] bArr, byte[] bArr2, byte[] bArr3, int i2) {
        for (int i3 = 0; i3 < bArr.length; i3++) {
            bArr[i3] = (byte) (bArr2[i3] ^ bArr3[i3 + i2]);
        }
    }

    private void a(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        int blockSize = this.b.getBlockSize();
        byte[] bArr5 = new byte[blockSize];
        int length = bArr4.length / blockSize;
        byte[] bArr6 = new byte[blockSize];
        this.b.init(true, new KeyParameter(a(bArr2)));
        this.b.processBlock(bArr3, 0, bArr5, 0);
        for (int i2 = 0; i2 < length; i2++) {
            a(bArr6, bArr5, bArr4, i2 * blockSize);
            this.b.processBlock(bArr6, 0, bArr5, 0);
        }
        System.arraycopy(bArr5, 0, bArr, 0, bArr.length);
    }

    private boolean a(BlockCipher blockCipher) {
        return blockCipher.getAlgorithmName().equals("DESede") || blockCipher.getAlgorithmName().equals("TDEA");
    }

    private byte[] a(byte[] bArr, int i2) {
        int blockSize = this.b.getBlockSize();
        int length = bArr.length;
        int i3 = i2 / 8;
        int i4 = length + 8;
        byte[] bArr2 = new byte[(((((i4 + 1) + blockSize) - 1) / blockSize) * blockSize)];
        a(bArr2, length, 0);
        a(bArr2, i3, 4);
        System.arraycopy(bArr, 0, bArr2, 8, length);
        bArr2[i4] = UnsignedBytes.MAX_POWER_OF_TWO;
        byte[] bArr3 = new byte[((this.c / 8) + blockSize)];
        byte[] bArr4 = new byte[blockSize];
        byte[] bArr5 = new byte[blockSize];
        byte[] bArr6 = new byte[(this.c / 8)];
        System.arraycopy(i, 0, bArr6, 0, bArr6.length);
        int i5 = 0;
        while (true) {
            int i6 = i5 * blockSize;
            if (i6 * 8 >= this.c + (blockSize * 8)) {
                break;
            }
            a(bArr5, i5, 0);
            a(bArr4, bArr6, bArr5, bArr2);
            System.arraycopy(bArr4, 0, bArr3, i6, bArr3.length - i6 > blockSize ? blockSize : bArr3.length - i6);
            i5++;
        }
        byte[] bArr7 = new byte[blockSize];
        System.arraycopy(bArr3, 0, bArr6, 0, bArr6.length);
        System.arraycopy(bArr3, bArr6.length, bArr7, 0, bArr7.length);
        byte[] bArr8 = new byte[(i2 / 2)];
        this.b.init(true, new KeyParameter(a(bArr6)));
        int i7 = 0;
        while (true) {
            int i8 = i7 * blockSize;
            if (i8 >= bArr8.length) {
                return bArr8;
            }
            this.b.processBlock(bArr7, 0, bArr7, 0);
            System.arraycopy(bArr7, 0, bArr8, i8, bArr8.length - i8 > blockSize ? blockSize : bArr8.length - i8);
            i7++;
        }
    }

    private void b(byte[] bArr) {
        byte b2 = 1;
        for (int i2 = 1; i2 <= bArr.length; i2++) {
            int i3 = (bArr[bArr.length - i2] & UnsignedBytes.MAX_VALUE) + b2;
            b2 = i3 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - i2] = (byte) i3;
        }
    }

    private void b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr.length];
        byte[] bArr5 = new byte[this.b.getBlockSize()];
        int blockSize = this.b.getBlockSize();
        this.b.init(true, new KeyParameter(a(bArr2)));
        int i2 = 0;
        while (true) {
            int i3 = i2 * blockSize;
            if (i3 < bArr.length) {
                b(bArr3);
                this.b.processBlock(bArr3, 0, bArr5, 0);
                System.arraycopy(bArr5, 0, bArr4, i3, bArr4.length - i3 > blockSize ? blockSize : bArr4.length - i3);
                i2++;
            } else {
                a(bArr4, bArr, bArr4, 0);
                System.arraycopy(bArr4, 0, bArr2, 0, bArr2.length);
                System.arraycopy(bArr4, bArr2.length, bArr3, 0, bArr3.length);
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public byte[] a(byte[] bArr) {
        if (!this.h) {
            return bArr;
        }
        byte[] bArr2 = new byte[24];
        a(bArr, 0, bArr2, 0);
        a(bArr, 7, bArr2, 8);
        a(bArr, 14, bArr2, 16);
        return bArr2;
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        byte[] bArr3;
        if (this.h) {
            if (this.g > 2147483648L) {
                return -1;
            }
            if (Utils.a(bArr, 512)) {
                throw new IllegalArgumentException("Number of bits per request limited to 4096");
            }
        } else if (this.g > 140737488355328L) {
            return -1;
        } else {
            if (Utils.a(bArr, 32768)) {
                throw new IllegalArgumentException("Number of bits per request limited to 262144");
            }
        }
        if (z) {
            a(this.a, bArr2);
            bArr2 = null;
        }
        if (bArr2 != null) {
            bArr3 = a(bArr2, this.d);
            b(bArr3, this.e, this.f);
        } else {
            bArr3 = new byte[this.d];
        }
        byte[] bArr4 = new byte[this.f.length];
        this.b.init(true, new KeyParameter(a(this.e)));
        for (int i2 = 0; i2 < bArr.length / bArr4.length; i2++) {
            b(this.f);
            this.b.processBlock(this.f, 0, bArr4, 0);
            System.arraycopy(bArr4, 0, bArr, bArr4.length * i2, bArr.length - (bArr4.length * i2) > bArr4.length ? bArr4.length : bArr.length - (this.f.length * i2));
        }
        b(bArr3, this.e, this.f);
        this.g++;
        return bArr.length * 8;
    }

    public int getBlockSize() {
        return this.f.length * 8;
    }

    public void reseed(byte[] bArr) {
        a(this.a, bArr);
    }
}
