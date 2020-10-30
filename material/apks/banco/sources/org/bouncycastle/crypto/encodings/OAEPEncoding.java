package org.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class OAEPEncoding implements AsymmetricBlockCipher {
    private byte[] a;
    private Digest b;
    private AsymmetricBlockCipher c;
    private SecureRandom d;
    private boolean e;

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, new SHA1Digest(), null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] bArr) {
        this.c = asymmetricBlockCipher;
        this.b = digest2;
        this.a = new byte[digest.getDigestSize()];
        digest.reset();
        if (bArr != null) {
            digest.update(bArr, 0, bArr.length);
        }
        digest.doFinal(this.a, 0);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest, bArr);
    }

    private void a(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    private byte[] a(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[this.b.getDigestSize()];
        byte[] bArr4 = new byte[4];
        this.b.reset();
        int i4 = 0;
        while (i4 < i3 / bArr3.length) {
            a(i4, bArr4);
            this.b.update(bArr, i, i2);
            this.b.update(bArr4, 0, bArr4.length);
            this.b.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, bArr3.length * i4, bArr3.length);
            i4++;
        }
        if (bArr3.length * i4 < i3) {
            a(i4, bArr4);
            this.b.update(bArr, i, i2);
            this.b.update(bArr4, 0, bArr4.length);
            this.b.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, bArr3.length * i4, bArr2.length - (i4 * bArr3.length));
        }
        return bArr2;
    }

    public byte[] decodeBlock(byte[] bArr, int i, int i2) {
        byte[] processBlock = this.c.processBlock(bArr, i, i2);
        if (processBlock.length < this.c.getOutputBlockSize()) {
            byte[] bArr2 = new byte[this.c.getOutputBlockSize()];
            System.arraycopy(processBlock, 0, bArr2, bArr2.length - processBlock.length, processBlock.length);
            processBlock = bArr2;
        }
        if (processBlock.length < (this.a.length * 2) + 1) {
            throw new InvalidCipherTextException("data too short");
        }
        byte[] a2 = a(processBlock, this.a.length, processBlock.length - this.a.length, this.a.length);
        for (int i3 = 0; i3 != this.a.length; i3++) {
            processBlock[i3] = (byte) (processBlock[i3] ^ a2[i3]);
        }
        byte[] a3 = a(processBlock, 0, this.a.length, processBlock.length - this.a.length);
        for (int length = this.a.length; length != processBlock.length; length++) {
            processBlock[length] = (byte) (processBlock[length] ^ a3[length - this.a.length]);
        }
        boolean z = false;
        for (int i4 = 0; i4 != this.a.length; i4++) {
            if (this.a[i4] != processBlock[this.a.length + i4]) {
                z = true;
            }
        }
        if (z) {
            throw new InvalidCipherTextException("data hash wrong");
        }
        int length2 = this.a.length * 2;
        while (length2 != processBlock.length && processBlock[length2] == 0) {
            length2++;
        }
        if (length2 >= processBlock.length - 1 || processBlock[length2] != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("data start wrong ");
            sb.append(length2);
            throw new InvalidCipherTextException(sb.toString());
        }
        int i5 = length2 + 1;
        byte[] bArr3 = new byte[(processBlock.length - i5)];
        System.arraycopy(processBlock, i5, bArr3, 0, bArr3.length);
        return bArr3;
    }

    public byte[] encodeBlock(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(getInputBlockSize() + 1 + (this.a.length * 2))];
        System.arraycopy(bArr, i, bArr2, bArr2.length - i2, i2);
        bArr2[(bArr2.length - i2) - 1] = 1;
        System.arraycopy(this.a, 0, bArr2, this.a.length, this.a.length);
        byte[] bArr3 = new byte[this.a.length];
        this.d.nextBytes(bArr3);
        byte[] a2 = a(bArr3, 0, bArr3.length, bArr2.length - this.a.length);
        for (int length = this.a.length; length != bArr2.length; length++) {
            bArr2[length] = (byte) (bArr2[length] ^ a2[length - this.a.length]);
        }
        System.arraycopy(bArr3, 0, bArr2, 0, this.a.length);
        byte[] a3 = a(bArr2, this.a.length, bArr2.length - this.a.length, this.a.length);
        for (int i3 = 0; i3 != this.a.length; i3++) {
            bArr2[i3] = (byte) (bArr2[i3] ^ a3[i3]);
        }
        return this.c.processBlock(bArr2, 0, bArr2.length);
    }

    public int getInputBlockSize() {
        int inputBlockSize = this.c.getInputBlockSize();
        return this.e ? (inputBlockSize - 1) - (this.a.length * 2) : inputBlockSize;
    }

    public int getOutputBlockSize() {
        int outputBlockSize = this.c.getOutputBlockSize();
        return this.e ? outputBlockSize : (outputBlockSize - 1) - (this.a.length * 2);
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.c;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.d = cipherParameters instanceof ParametersWithRandom ? ((ParametersWithRandom) cipherParameters).getRandom() : new SecureRandom();
        this.c.init(z, cipherParameters);
        this.e = z;
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        return this.e ? encodeBlock(bArr, i, i2) : decodeBlock(bArr, i, i2);
    }
}
