package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {
    private static final byte[] i = {74, -35, -94, 44, 121, -24, 33, 5};
    Digest a = new SHA1Digest();
    byte[] b = new byte[20];
    private CBCBlockCipher c;
    private CipherParameters d;
    private ParametersWithIV e;
    private byte[] f;
    private boolean g;
    private SecureRandom h;

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

    public String getAlgorithmName() {
        return "RC2";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.g = z;
        this.c = new CBCBlockCipher(new RC2Engine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.h = parametersWithRandom.getRandom();
            cipherParameters = parametersWithRandom.getParameters();
        } else {
            this.h = new SecureRandom();
        }
        if (cipherParameters instanceof ParametersWithIV) {
            this.e = (ParametersWithIV) cipherParameters;
            this.f = this.e.getIV();
            this.d = this.e.getParameters();
            if (!this.g) {
                throw new IllegalArgumentException("You should not supply an IV for unwrapping");
            } else if (this.f == null || this.f.length != 8) {
                throw new IllegalArgumentException("IV is not 8 octets");
            }
        } else {
            this.d = cipherParameters;
            if (this.g) {
                this.f = new byte[8];
                this.h.nextBytes(this.f);
                this.e = new ParametersWithIV(this.d, this.f);
            }
        }
    }

    public byte[] unwrap(byte[] bArr, int i2, int i3) {
        if (this.g) {
            throw new IllegalStateException("Not set for unwrapping");
        } else if (bArr == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        } else if (i3 % this.c.getBlockSize() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ciphertext not multiple of ");
            sb.append(this.c.getBlockSize());
            throw new InvalidCipherTextException(sb.toString());
        } else {
            this.c.init(false, new ParametersWithIV(this.d, i));
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            for (int i4 = 0; i4 < bArr2.length / this.c.getBlockSize(); i4++) {
                int blockSize = this.c.getBlockSize() * i4;
                this.c.processBlock(bArr2, blockSize, bArr2, blockSize);
            }
            byte[] bArr3 = new byte[bArr2.length];
            int i5 = 0;
            while (i5 < bArr2.length) {
                int i6 = i5 + 1;
                bArr3[i5] = bArr2[bArr2.length - i6];
                i5 = i6;
            }
            this.f = new byte[8];
            byte[] bArr4 = new byte[(bArr3.length - 8)];
            System.arraycopy(bArr3, 0, this.f, 0, 8);
            System.arraycopy(bArr3, 8, bArr4, 0, bArr3.length - 8);
            this.e = new ParametersWithIV(this.d, this.f);
            this.c.init(false, this.e);
            byte[] bArr5 = new byte[bArr4.length];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            for (int i7 = 0; i7 < bArr5.length / this.c.getBlockSize(); i7++) {
                int blockSize2 = this.c.getBlockSize() * i7;
                this.c.processBlock(bArr5, blockSize2, bArr5, blockSize2);
            }
            byte[] bArr6 = new byte[(bArr5.length - 8)];
            byte[] bArr7 = new byte[8];
            System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length - 8);
            System.arraycopy(bArr5, bArr5.length - 8, bArr7, 0, 8);
            if (!a(bArr6, bArr7)) {
                throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
            } else if (bArr6.length - ((bArr6[0] & UnsignedBytes.MAX_VALUE) + 1) > 7) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("too many pad bytes (");
                sb2.append(bArr6.length - ((bArr6[0] & UnsignedBytes.MAX_VALUE) + 1));
                sb2.append(")");
                throw new InvalidCipherTextException(sb2.toString());
            } else {
                byte[] bArr8 = new byte[bArr6[0]];
                System.arraycopy(bArr6, 1, bArr8, 0, bArr8.length);
                return bArr8;
            }
        }
    }

    public byte[] wrap(byte[] bArr, int i2, int i3) {
        if (!this.g) {
            throw new IllegalStateException("Not initialized for wrapping");
        }
        int i4 = i3 + 1;
        int i5 = i4 % 8;
        byte[] bArr2 = new byte[(i5 != 0 ? (8 - i5) + i4 : i4)];
        bArr2[0] = (byte) i3;
        System.arraycopy(bArr, i2, bArr2, 1, i3);
        byte[] bArr3 = new byte[((bArr2.length - i3) - 1)];
        if (bArr3.length > 0) {
            this.h.nextBytes(bArr3);
            System.arraycopy(bArr3, 0, bArr2, i4, bArr3.length);
        }
        byte[] a2 = a(bArr2);
        byte[] bArr4 = new byte[(bArr2.length + a2.length)];
        System.arraycopy(bArr2, 0, bArr4, 0, bArr2.length);
        System.arraycopy(a2, 0, bArr4, bArr2.length, a2.length);
        byte[] bArr5 = new byte[bArr4.length];
        System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
        int length = bArr4.length / this.c.getBlockSize();
        if (bArr4.length % this.c.getBlockSize() != 0) {
            throw new IllegalStateException("Not multiple of block length");
        }
        this.c.init(true, this.e);
        for (int i6 = 0; i6 < length; i6++) {
            int blockSize = this.c.getBlockSize() * i6;
            this.c.processBlock(bArr5, blockSize, bArr5, blockSize);
        }
        byte[] bArr6 = new byte[(this.f.length + bArr5.length)];
        System.arraycopy(this.f, 0, bArr6, 0, this.f.length);
        System.arraycopy(bArr5, 0, bArr6, this.f.length, bArr5.length);
        byte[] bArr7 = new byte[bArr6.length];
        int i7 = 0;
        while (i7 < bArr6.length) {
            int i8 = i7 + 1;
            bArr7[i7] = bArr6[bArr6.length - i8];
            i7 = i8;
        }
        this.c.init(true, new ParametersWithIV(this.d, i));
        for (int i9 = 0; i9 < length + 1; i9++) {
            int blockSize2 = this.c.getBlockSize() * i9;
            this.c.processBlock(bArr7, blockSize2, bArr7, blockSize2);
        }
        return bArr7;
    }
}
