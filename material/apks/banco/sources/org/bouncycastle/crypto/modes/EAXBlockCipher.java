package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class EAXBlockCipher implements AEADBlockCipher {
    private SICBlockCipher a;
    private boolean b;
    private int c;
    private Mac d;
    private byte[] e = new byte[this.d.getMacSize()];
    private byte[] f = new byte[this.d.getMacSize()];
    private byte[] g = new byte[this.c];
    private int h;
    private byte[] i;
    private int j;
    private boolean k;
    private byte[] l;

    public EAXBlockCipher(BlockCipher blockCipher) {
        this.c = blockCipher.getBlockSize();
        this.d = new CMac(blockCipher);
        this.a = new SICBlockCipher(blockCipher);
    }

    private int a(byte b2, byte[] bArr, int i2) {
        int i3;
        byte[] bArr2 = this.i;
        int i4 = this.j;
        this.j = i4 + 1;
        bArr2[i4] = b2;
        if (this.j != this.i.length) {
            return 0;
        }
        if (bArr.length < this.c + i2) {
            throw new OutputLengthException("Output buffer is too short");
        }
        if (this.b) {
            i3 = this.a.processBlock(this.i, 0, bArr, i2);
            this.d.update(bArr, i2, this.c);
        } else {
            this.d.update(this.i, 0, this.c);
            i3 = this.a.processBlock(this.i, 0, bArr, i2);
        }
        this.j = 0;
        if (!this.b) {
            System.arraycopy(this.i, this.c, this.i, 0, this.h);
            this.j = this.h;
        }
        return i3;
    }

    private void a() {
        if (!this.k) {
            this.k = true;
            this.d.doFinal(this.f, 0);
            byte[] bArr = new byte[this.c];
            bArr[this.c - 1] = 2;
            this.d.update(bArr, 0, this.c);
        }
    }

    private void a(boolean z) {
        this.a.reset();
        this.d.reset();
        this.j = 0;
        Arrays.fill(this.i, 0);
        if (z) {
            Arrays.fill(this.g, 0);
        }
        byte[] bArr = new byte[this.c];
        bArr[this.c - 1] = 1;
        this.d.update(bArr, 0, this.c);
        this.k = false;
        if (this.l != null) {
            processAADBytes(this.l, 0, this.l.length);
        }
    }

    private boolean a(byte[] bArr, int i2) {
        byte b2 = 0;
        for (int i3 = 0; i3 < this.h; i3++) {
            b2 |= this.g[i3] ^ bArr[i2 + i3];
        }
        return b2 == 0;
    }

    private void b() {
        byte[] bArr = new byte[this.c];
        this.d.doFinal(bArr, 0);
        for (int i2 = 0; i2 < this.g.length; i2++) {
            this.g[i2] = (byte) ((this.e[i2] ^ this.f[i2]) ^ bArr[i2]);
        }
    }

    public int doFinal(byte[] bArr, int i2) {
        a();
        int i3 = this.j;
        byte[] bArr2 = new byte[this.i.length];
        this.j = 0;
        if (this.b) {
            int i4 = i2 + i3;
            if (bArr.length < this.h + i4) {
                throw new OutputLengthException("Output buffer too short");
            }
            this.a.processBlock(this.i, 0, bArr2, 0);
            System.arraycopy(bArr2, 0, bArr, i2, i3);
            this.d.update(bArr2, 0, i3);
            b();
            System.arraycopy(this.g, 0, bArr, i4, this.h);
            a(false);
            return i3 + this.h;
        } else if (bArr.length < (i2 + i3) - this.h) {
            throw new OutputLengthException("Output buffer too short");
        } else if (i3 < this.h) {
            throw new InvalidCipherTextException("data too short");
        } else {
            if (i3 > this.h) {
                this.d.update(this.i, 0, i3 - this.h);
                this.a.processBlock(this.i, 0, bArr2, 0);
                System.arraycopy(bArr2, 0, bArr, i2, i3 - this.h);
            }
            b();
            if (!a(this.i, i3 - this.h)) {
                throw new InvalidCipherTextException("mac check in EAX failed");
            }
            a(false);
            return i3 - this.h;
        }
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getUnderlyingCipher().getAlgorithmName());
        sb.append("/EAX");
        return sb.toString();
    }

    public int getBlockSize() {
        return this.a.getBlockSize();
    }

    public byte[] getMac() {
        byte[] bArr = new byte[this.h];
        System.arraycopy(this.g, 0, bArr, 0, this.h);
        return bArr;
    }

    public int getOutputSize(int i2) {
        int i3 = i2 + this.j;
        if (this.b) {
            return i3 + this.h;
        }
        if (i3 < this.h) {
            return 0;
        }
        return i3 - this.h;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.a.getUnderlyingCipher();
    }

    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.j;
        if (!this.b) {
            if (i3 < this.h) {
                return 0;
            }
            i3 -= this.h;
        }
        return i3 - (i3 % this.c);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        byte[] bArr;
        this.b = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            bArr = aEADParameters.getNonce();
            this.l = aEADParameters.getAssociatedText();
            this.h = aEADParameters.getMacSize() / 8;
            cipherParameters2 = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            this.l = null;
            this.h = this.d.getMacSize() / 2;
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to EAX");
        }
        this.i = new byte[(z ? this.c : this.c + this.h)];
        byte[] bArr2 = new byte[this.c];
        this.d.init(cipherParameters2);
        bArr2[this.c - 1] = 0;
        this.d.update(bArr2, 0, this.c);
        this.d.update(bArr, 0, bArr.length);
        this.d.doFinal(this.e, 0);
        this.a.init(true, new ParametersWithIV(null, this.e));
        reset();
    }

    public void processAADByte(byte b2) {
        if (this.k) {
            throw new IllegalStateException("AAD data cannot be added after encryption/decription processing has begun.");
        }
        this.d.update(b2);
    }

    public void processAADBytes(byte[] bArr, int i2, int i3) {
        if (this.k) {
            throw new IllegalStateException("AAD data cannot be added after encryption/decryption processing has begun.");
        }
        this.d.update(bArr, i2, i3);
    }

    public int processByte(byte b2, byte[] bArr, int i2) {
        a();
        return a(b2, bArr, i2);
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        a();
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("Input buffer too short");
        }
        int i5 = 0;
        for (int i6 = 0; i6 != i3; i6++) {
            i5 += a(bArr[i2 + i6], bArr2, i4 + i5);
        }
        return i5;
    }

    public void reset() {
        a(true);
    }
}
