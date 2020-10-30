package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.tls.CipherSuite;

public class NoekeonEngine implements BlockCipher {
    private static final int[] a = {0, 0, 0, 0};
    private static final int[] b = {128, 27, 54, 108, 216, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, 77, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 47, 94, 188, 99, 198, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 53, 106, 212};
    private int[] c = new int[4];
    private int[] d = new int[4];
    private int[] e = new int[4];
    private boolean f = false;
    private boolean g;

    private int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private int a(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        byte b2 = (bArr[i] << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        return (bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | b2 | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8);
    }

    private int a(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.c[0] = a(bArr, i);
        this.c[1] = a(bArr, i + 4);
        this.c[2] = a(bArr, i + 8);
        this.c[3] = a(bArr, i + 12);
        int i3 = 0;
        while (i3 < 16) {
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ b[i3];
            a(this.c, this.d);
            b(this.c);
            a(this.c);
            c(this.c);
            i3++;
        }
        int[] iArr2 = this.c;
        iArr2[0] = b[i3] ^ iArr2[0];
        a(this.c, this.d);
        a(this.c[0], bArr2, i2);
        a(this.c[1], bArr2, i2 + 4);
        a(this.c[2], bArr2, i2 + 8);
        a(this.c[3], bArr2, i2 + 12);
        return 16;
    }

    private void a(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i5] = (byte) i;
    }

    private void a(byte[] bArr) {
        this.d[0] = a(bArr, 0);
        this.d[1] = a(bArr, 4);
        this.d[2] = a(bArr, 8);
        this.d[3] = a(bArr, 12);
    }

    private void a(int[] iArr) {
        iArr[1] = iArr[1] ^ ((iArr[3] ^ -1) & (iArr[2] ^ -1));
        iArr[0] = iArr[0] ^ (iArr[2] & iArr[1]);
        int i = iArr[3];
        iArr[3] = iArr[0];
        iArr[0] = i;
        iArr[2] = iArr[2] ^ ((iArr[0] ^ iArr[1]) ^ iArr[3]);
        iArr[1] = ((iArr[3] ^ -1) & (iArr[2] ^ -1)) ^ iArr[1];
        iArr[0] = (iArr[1] & iArr[2]) ^ iArr[0];
    }

    private void a(int[] iArr, int[] iArr2) {
        int i = iArr[0] ^ iArr[2];
        int a2 = i ^ (a(i, 8) ^ a(i, 24));
        iArr[1] = iArr[1] ^ a2;
        iArr[3] = a2 ^ iArr[3];
        for (int i2 = 0; i2 < 4; i2++) {
            iArr[i2] = iArr[i2] ^ iArr2[i2];
        }
        int i3 = iArr[1] ^ iArr[3];
        int a3 = i3 ^ (a(i3, 8) ^ a(i3, 24));
        iArr[0] = iArr[0] ^ a3;
        iArr[2] = a3 ^ iArr[2];
    }

    private int b(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.c[0] = a(bArr, i);
        this.c[1] = a(bArr, i + 4);
        this.c[2] = a(bArr, i + 8);
        this.c[3] = a(bArr, i + 12);
        System.arraycopy(this.d, 0, this.e, 0, this.d.length);
        a(this.e, a);
        int i3 = 16;
        while (i3 > 0) {
            a(this.c, this.e);
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ b[i3];
            b(this.c);
            a(this.c);
            c(this.c);
            i3--;
        }
        a(this.c, this.e);
        int[] iArr2 = this.c;
        iArr2[0] = b[i3] ^ iArr2[0];
        a(this.c[0], bArr2, i2);
        a(this.c[1], bArr2, i2 + 4);
        a(this.c[2], bArr2, i2 + 8);
        a(this.c[3], bArr2, i2 + 12);
        return 16;
    }

    private void b(int[] iArr) {
        iArr[1] = a(iArr[1], 1);
        iArr[2] = a(iArr[2], 5);
        iArr[3] = a(iArr[3], 2);
    }

    private void c(int[] iArr) {
        iArr[1] = a(iArr[1], 31);
        iArr[2] = a(iArr[2], 27);
        iArr[3] = a(iArr[3], 30);
    }

    public String getAlgorithmName() {
        return "Noekeon";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to Noekeon init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        this.g = z;
        this.f = true;
        a(((KeyParameter) cipherParameters).getKey());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (!this.f) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 <= bArr2.length) {
            return this.g ? a(bArr, i, bArr2, i2) : b(bArr, i, bArr2, i2);
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
