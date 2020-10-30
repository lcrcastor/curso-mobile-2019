package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher a;
    private ParametersWithIV b;
    private boolean c;
    private SecureRandom d;

    public RFC3211WrapEngine(BlockCipher blockCipher) {
        this.a = new CBCBlockCipher(blockCipher);
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getUnderlyingCipher().getAlgorithmName());
        sb.append("/RFC3211Wrap");
        return sb.toString();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.c = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.d = parametersWithRandom.getRandom();
            this.b = (ParametersWithIV) parametersWithRandom.getParameters();
            return;
        }
        if (z) {
            this.d = new SecureRandom();
        }
        this.b = (ParametersWithIV) cipherParameters;
    }

    public byte[] unwrap(byte[] bArr, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int blockSize = this.a.getBlockSize();
        if (i2 < blockSize * 2) {
            throw new InvalidCipherTextException("input too short");
        }
        byte[] bArr2 = new byte[i2];
        byte[] bArr3 = new byte[blockSize];
        int i3 = 0;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        System.arraycopy(bArr, i, bArr3, 0, bArr3.length);
        this.a.init(false, new ParametersWithIV(this.b.getParameters(), bArr3));
        for (int i4 = blockSize; i4 < bArr2.length; i4 += blockSize) {
            this.a.processBlock(bArr2, i4, bArr2, i4);
        }
        System.arraycopy(bArr2, bArr2.length - bArr3.length, bArr3, 0, bArr3.length);
        this.a.init(false, new ParametersWithIV(this.b.getParameters(), bArr3));
        this.a.processBlock(bArr2, 0, bArr2, 0);
        this.a.init(false, this.b);
        for (int i5 = 0; i5 < bArr2.length; i5 += blockSize) {
            this.a.processBlock(bArr2, i5, bArr2, i5);
        }
        if ((bArr2[0] & UnsignedBytes.MAX_VALUE) > bArr2.length - 4) {
            throw new InvalidCipherTextException("wrapped key corrupted");
        }
        byte[] bArr4 = new byte[(bArr2[0] & UnsignedBytes.MAX_VALUE)];
        System.arraycopy(bArr2, 4, bArr4, 0, bArr2[0]);
        byte b2 = 0;
        while (i3 != 3) {
            int i6 = i3 + 1;
            b2 |= ((byte) (bArr2[i6] ^ -1)) ^ bArr4[i3];
            i3 = i6;
        }
        if (b2 == 0) {
            return bArr4;
        }
        throw new InvalidCipherTextException("wrapped key fails checksum");
    }

    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.c) {
            throw new IllegalStateException("not set for wrapping");
        }
        this.a.init(true, this.b);
        int blockSize = this.a.getBlockSize();
        int i3 = i2 + 4;
        int i4 = blockSize * 2;
        if (i3 >= i4) {
            i4 = i3 % blockSize == 0 ? i3 : ((i3 / blockSize) + 1) * blockSize;
        }
        byte[] bArr2 = new byte[i4];
        bArr2[0] = (byte) i2;
        bArr2[1] = (byte) (bArr[i] ^ -1);
        bArr2[2] = (byte) (bArr[i + 1] ^ -1);
        bArr2[3] = (byte) (bArr[i + 2] ^ -1);
        System.arraycopy(bArr, i, bArr2, 4, i2);
        while (i3 < bArr2.length) {
            bArr2[i3] = (byte) this.d.nextInt();
            i3++;
        }
        for (int i5 = 0; i5 < bArr2.length; i5 += blockSize) {
            this.a.processBlock(bArr2, i5, bArr2, i5);
        }
        for (int i6 = 0; i6 < bArr2.length; i6 += blockSize) {
            this.a.processBlock(bArr2, i6, bArr2, i6);
        }
        return bArr2;
    }
}
