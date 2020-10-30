package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

class MacCFBBlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e = null;

    public MacCFBBlockCipher(BlockCipher blockCipher, int i) {
        this.e = blockCipher;
        this.d = i / 8;
        this.a = new byte[blockCipher.getBlockSize()];
        this.b = new byte[blockCipher.getBlockSize()];
        this.c = new byte[blockCipher.getBlockSize()];
    }

    public int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.d + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.d + i2 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            this.e.processBlock(this.b, 0, this.c, 0);
            for (int i3 = 0; i3 < this.d; i3++) {
                bArr2[i2 + i3] = (byte) (this.c[i3] ^ bArr[i + i3]);
            }
            System.arraycopy(this.b, this.d, this.b, 0, this.b.length - this.d);
            System.arraycopy(bArr2, i2, this.b, this.b.length - this.d, this.d);
            return this.d;
        }
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.e.getAlgorithmName());
        sb.append("/CFB");
        sb.append(this.d * 8);
        return sb.toString();
    }

    public void a(CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length < this.a.length) {
                System.arraycopy(iv, 0, this.a, this.a.length - iv.length, iv.length);
            } else {
                System.arraycopy(iv, 0, this.a, 0, this.a.length);
            }
            c();
            blockCipher = this.e;
            cipherParameters = parametersWithIV.getParameters();
        } else {
            c();
            blockCipher = this.e;
        }
        blockCipher.init(true, cipherParameters);
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr) {
        this.e.processBlock(this.b, 0, bArr, 0);
    }

    public int b() {
        return this.d;
    }

    public void c() {
        System.arraycopy(this.a, 0, this.b, 0, this.a.length);
        this.e.reset();
    }
}
