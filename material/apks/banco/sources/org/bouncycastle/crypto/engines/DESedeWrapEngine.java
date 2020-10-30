package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class DESedeWrapEngine implements Wrapper {
    private static final byte[] h = {74, -35, -94, 44, 121, -24, 33, 5};
    Digest a = new SHA1Digest();
    byte[] b = new byte[20];
    private CBCBlockCipher c;
    private KeyParameter d;
    private ParametersWithIV e;
    private byte[] f;
    private boolean g;

    private boolean a(byte[] bArr, byte[] bArr2) {
        return Arrays.constantTimeAreEqual(a(bArr), bArr2);
    }

    private byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[8];
        this.a.update(bArr, 0, bArr.length);
        this.a.doFinal(this.b, 0);
        System.arraycopy(this.b, 0, bArr2, 0, 8);
        return bArr2;
    }

    private static byte[] b(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            bArr2[i] = bArr[bArr.length - i2];
            i = i2;
        }
        return bArr2;
    }

    public String getAlgorithmName() {
        return "DESede";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        CipherParameters cipherParameters2;
        this.g = z;
        this.c = new CBCBlockCipher(new DESedeEngine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            cipherParameters2 = parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            CipherParameters cipherParameters3 = cipherParameters;
            secureRandom = new SecureRandom();
            cipherParameters2 = cipherParameters3;
        }
        if (cipherParameters2 instanceof KeyParameter) {
            this.d = (KeyParameter) cipherParameters2;
            if (this.g) {
                this.f = new byte[8];
                secureRandom.nextBytes(this.f);
                this.e = new ParametersWithIV(this.d, this.f);
            }
        } else if (cipherParameters2 instanceof ParametersWithIV) {
            this.e = (ParametersWithIV) cipherParameters2;
            this.f = this.e.getIV();
            this.d = (KeyParameter) this.e.getParameters();
            if (!this.g) {
                throw new IllegalArgumentException("You should not supply an IV for unwrapping");
            } else if (this.f == null || this.f.length != 8) {
                throw new IllegalArgumentException("IV is not 8 octets");
            }
        }
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) {
        if (this.g) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (bArr == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        } else {
            int blockSize = this.c.getBlockSize();
            if (i2 % blockSize != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Ciphertext not multiple of ");
                sb.append(blockSize);
                throw new InvalidCipherTextException(sb.toString());
            }
            this.c.init(false, new ParametersWithIV(this.d, h));
            byte[] bArr2 = new byte[i2];
            for (int i3 = 0; i3 != i2; i3 += blockSize) {
                this.c.processBlock(bArr, i + i3, bArr2, i3);
            }
            byte[] b2 = b(bArr2);
            this.f = new byte[8];
            byte[] bArr3 = new byte[(b2.length - 8)];
            System.arraycopy(b2, 0, this.f, 0, 8);
            System.arraycopy(b2, 8, bArr3, 0, b2.length - 8);
            this.e = new ParametersWithIV(this.d, this.f);
            this.c.init(false, this.e);
            byte[] bArr4 = new byte[bArr3.length];
            for (int i4 = 0; i4 != bArr4.length; i4 += blockSize) {
                this.c.processBlock(bArr3, i4, bArr4, i4);
            }
            byte[] bArr5 = new byte[(bArr4.length - 8)];
            byte[] bArr6 = new byte[8];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length - 8);
            System.arraycopy(bArr4, bArr4.length - 8, bArr6, 0, 8);
            if (a(bArr5, bArr6)) {
                return bArr5;
            }
            throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
        }
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.g) {
            throw new IllegalStateException("Not initialized for wrapping");
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        byte[] a2 = a(bArr2);
        byte[] bArr3 = new byte[(bArr2.length + a2.length)];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(a2, 0, bArr3, bArr2.length, a2.length);
        int blockSize = this.c.getBlockSize();
        if (bArr3.length % blockSize != 0) {
            throw new IllegalStateException("Not multiple of block length");
        }
        this.c.init(true, this.e);
        byte[] bArr4 = new byte[bArr3.length];
        for (int i3 = 0; i3 != bArr3.length; i3 += blockSize) {
            this.c.processBlock(bArr3, i3, bArr4, i3);
        }
        byte[] bArr5 = new byte[(this.f.length + bArr4.length)];
        System.arraycopy(this.f, 0, bArr5, 0, this.f.length);
        System.arraycopy(bArr4, 0, bArr5, this.f.length, bArr4.length);
        byte[] b2 = b(bArr5);
        this.c.init(true, new ParametersWithIV(this.d, h));
        for (int i4 = 0; i4 != b2.length; i4 += blockSize) {
            this.c.processBlock(b2, i4, b2, i4);
        }
        return b2;
    }
}
