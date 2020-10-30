package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.MGFParameters;

public class MGF1BytesGenerator implements DerivationFunction {
    private Digest a;
    private byte[] b;
    private int c;

    public MGF1BytesGenerator(Digest digest) {
        this.a = digest;
        this.c = digest.getDigestSize();
    }

    private void a(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    public int generateBytes(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        byte[] bArr2 = new byte[this.c];
        byte[] bArr3 = new byte[4];
        this.a.reset();
        if (i2 > this.c) {
            i3 = 0;
            do {
                a(i3, bArr3);
                this.a.update(this.b, 0, this.b.length);
                this.a.update(bArr3, 0, bArr3.length);
                this.a.doFinal(bArr2, 0);
                System.arraycopy(bArr2, 0, bArr, (this.c * i3) + i, this.c);
                i3++;
            } while (i3 < i2 / this.c);
        } else {
            i3 = 0;
        }
        if (this.c * i3 < i2) {
            a(i3, bArr3);
            this.a.update(this.b, 0, this.b.length);
            this.a.update(bArr3, 0, bArr3.length);
            this.a.doFinal(bArr2, 0);
            System.arraycopy(bArr2, 0, bArr, i + (this.c * i3), i2 - (i3 * this.c));
        }
        return i2;
    }

    public Digest getDigest() {
        return this.a;
    }

    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof MGFParameters)) {
            throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
        }
        this.b = ((MGFParameters) derivationParameters).getSeed();
    }
}
