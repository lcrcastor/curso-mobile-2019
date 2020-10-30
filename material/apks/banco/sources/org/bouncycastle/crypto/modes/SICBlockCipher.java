package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.SkippingStreamCipher;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Pack;

public class SICBlockCipher extends StreamBlockCipher implements SkippingStreamCipher {
    private final BlockCipher a;
    private final int b = this.a.getBlockSize();
    private byte[] c = new byte[this.b];
    private byte[] d = new byte[this.b];
    private byte[] e = new byte[this.b];
    private int f = 0;

    public SICBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.a = blockCipher;
    }

    private void a() {
        int length = this.d.length - 1;
        while (length >= 0) {
            byte[] bArr = this.d;
            byte b2 = (byte) (bArr[length] + 1);
            bArr[length] = b2;
            if (b2 == 0) {
                length--;
            } else {
                return;
            }
        }
    }

    private void a(long j) {
        int i;
        long j2 = 0;
        if (j >= 0) {
            long j3 = (j + ((long) this.f)) / ((long) this.b);
            while (j2 != j3) {
                a();
                j2++;
            }
            i = (int) ((j + ((long) this.f)) - (((long) this.b) * j3));
        } else {
            long j4 = ((-j) - ((long) this.f)) / ((long) this.b);
            while (j2 != j4) {
                b();
                j2++;
            }
            int i2 = (int) (((long) this.f) + j + (((long) this.b) * j4));
            if (i2 >= 0) {
                i = 0;
            } else {
                b();
                this.f = this.b + i2;
                return;
            }
        }
        this.f = i;
    }

    private void b() {
        boolean z = false;
        if (this.d[0] == 0) {
            for (int length = this.d.length - 1; length > 0; length--) {
                if (this.d[length] != 0) {
                    z = true;
                }
            }
            if (!z) {
                throw new IllegalStateException("attempt to reduce counter past zero.");
            }
        }
        int length2 = this.d.length - 1;
        while (length2 >= 0) {
            byte[] bArr = this.d;
            byte b2 = (byte) (bArr[length2] - 1);
            bArr[length2] = b2;
            if (b2 == -1) {
                length2--;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b2) {
        if (this.f == 0) {
            this.a.processBlock(this.d, 0, this.e, 0);
            byte[] bArr = this.e;
            int i = this.f;
            this.f = i + 1;
            return (byte) (b2 ^ bArr[i]);
        }
        byte[] bArr2 = this.e;
        int i2 = this.f;
        this.f = i2 + 1;
        byte b3 = (byte) (b2 ^ bArr2[i2]);
        if (this.f == this.d.length) {
            this.f = 0;
            a();
        }
        return b3;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("/SIC");
        return sb.toString();
    }

    public int getBlockSize() {
        return this.a.getBlockSize();
    }

    public long getPosition() {
        byte[] bArr = new byte[this.c.length];
        System.arraycopy(this.d, 0, bArr, 0, bArr.length);
        for (int length = bArr.length - 1; length >= 1; length--) {
            int i = bArr[length] - this.c[length];
            if (i < 0) {
                int i2 = length - 1;
                bArr[i2] = (byte) (bArr[i2] - 1);
                i += 256;
            }
            bArr[length] = (byte) i;
        }
        return (Pack.bigEndianToLong(bArr, bArr.length - 8) * ((long) this.b)) + ((long) this.f);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            System.arraycopy(parametersWithIV.getIV(), 0, this.c, 0, this.c.length);
            if (parametersWithIV.getParameters() != null) {
                this.a.init(true, parametersWithIV.getParameters());
            }
            reset();
            return;
        }
        throw new IllegalArgumentException("SIC mode requires ParametersWithIV");
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        processBytes(bArr, i, this.b, bArr2, i2);
        return this.b;
    }

    public void reset() {
        System.arraycopy(this.c, 0, this.d, 0, this.d.length);
        this.a.reset();
        this.f = 0;
    }

    public long seekTo(long j) {
        reset();
        return skip(j);
    }

    public long skip(long j) {
        a(j);
        this.a.processBlock(this.d, 0, this.e, 0);
        return j;
    }
}
