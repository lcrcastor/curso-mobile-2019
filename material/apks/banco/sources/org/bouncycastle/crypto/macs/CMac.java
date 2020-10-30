package org.bouncycastle.crypto.macs;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.params.KeyParameter;

public class CMac implements Mac {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e;
    private int f;
    private byte[] g;
    private byte[] h;
    private byte[] i;

    public CMac(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8);
    }

    public CMac(BlockCipher blockCipher, int i2) {
        if (i2 % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        } else if (i2 > blockCipher.getBlockSize() * 8) {
            StringBuilder sb = new StringBuilder();
            sb.append("MAC size must be less or equal to ");
            sb.append(blockCipher.getBlockSize() * 8);
            throw new IllegalArgumentException(sb.toString());
        } else if (blockCipher.getBlockSize() == 8 || blockCipher.getBlockSize() == 16) {
            this.e = new CBCBlockCipher(blockCipher);
            this.f = i2 / 8;
            this.b = new byte[blockCipher.getBlockSize()];
            this.c = new byte[blockCipher.getBlockSize()];
            this.a = new byte[blockCipher.getBlockSize()];
            this.d = 0;
        } else {
            throw new IllegalArgumentException("Block size must be either 64 or 128 bits");
        }
    }

    private static int a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            byte b2 = bArr[length] & UnsignedBytes.MAX_VALUE;
            bArr2[length] = (byte) (i2 | (b2 << 1));
            i2 = (b2 >>> 7) & 1;
        }
    }

    private static byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int a2 = a(bArr, bArr2);
        int length = bArr.length - 1;
        bArr2[length] = (byte) ((((bArr.length == 16 ? -121 : 27) & 255) >>> ((1 - a2) << 3)) ^ bArr2[length]);
        return bArr2;
    }

    /* access modifiers changed from: 0000 */
    public void a(CipherParameters cipherParameters) {
        if (cipherParameters != null && !(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("CMac mode only permits key to be set.");
        }
    }

    public int doFinal(byte[] bArr, int i2) {
        byte[] bArr2;
        if (this.d == this.e.getBlockSize()) {
            bArr2 = this.h;
        } else {
            new ISO7816d4Padding().addPadding(this.c, this.d);
            bArr2 = this.i;
        }
        for (int i3 = 0; i3 < this.b.length; i3++) {
            byte[] bArr3 = this.c;
            bArr3[i3] = (byte) (bArr3[i3] ^ bArr2[i3]);
        }
        this.e.processBlock(this.c, 0, this.b, 0);
        System.arraycopy(this.b, 0, bArr, i2, this.f);
        reset();
        return this.f;
    }

    public String getAlgorithmName() {
        return this.e.getAlgorithmName();
    }

    public int getMacSize() {
        return this.f;
    }

    public void init(CipherParameters cipherParameters) {
        a(cipherParameters);
        this.e.init(true, cipherParameters);
        this.g = new byte[this.a.length];
        this.e.processBlock(this.a, 0, this.g, 0);
        this.h = a(this.g);
        this.i = a(this.h);
        reset();
    }

    public void reset() {
        for (int i2 = 0; i2 < this.c.length; i2++) {
            this.c[i2] = 0;
        }
        this.d = 0;
        this.e.reset();
    }

    public void update(byte b2) {
        if (this.d == this.c.length) {
            this.e.processBlock(this.c, 0, this.b, 0);
            this.d = 0;
        }
        byte[] bArr = this.c;
        int i2 = this.d;
        this.d = i2 + 1;
        bArr[i2] = b2;
    }

    public void update(byte[] bArr, int i2, int i3) {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = this.e.getBlockSize();
        int i4 = blockSize - this.d;
        if (i3 > i4) {
            System.arraycopy(bArr, i2, this.c, this.d, i4);
            this.e.processBlock(this.c, 0, this.b, 0);
            this.d = 0;
            i3 -= i4;
            i2 += i4;
            while (i3 > blockSize) {
                this.e.processBlock(bArr, i2, this.b, 0);
                i3 -= blockSize;
                i2 += blockSize;
            }
        }
        System.arraycopy(bArr, i2, this.c, this.d, i3);
        this.d += i3;
    }
}
