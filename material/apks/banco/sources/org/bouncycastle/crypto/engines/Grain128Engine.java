package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class Grain128Engine implements StreamCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int[] d;
    private int[] e;
    private int f;
    private int g = 4;
    private boolean h = false;

    private void a() {
        for (int i = 0; i < 8; i++) {
            this.f = d();
            this.e = a(this.e, (b() ^ this.d[0]) ^ this.f);
            this.d = a(this.d, c() ^ this.f);
        }
        this.h = true;
    }

    private void a(byte[] bArr, byte[] bArr2) {
        bArr2[12] = -1;
        bArr2[13] = -1;
        bArr2[14] = -1;
        bArr2[15] = -1;
        this.a = bArr;
        this.b = bArr2;
        int i = 0;
        for (int i2 = 0; i2 < this.e.length; i2++) {
            int i3 = i + 3;
            int i4 = i + 2;
            int i5 = i + 1;
            this.e[i2] = (this.a[i3] << Ascii.CAN) | ((this.a[i4] << Ascii.DLE) & Ascii.NUL) | ((this.a[i5] << 8) & Ascii.NUL) | (this.a[i] & UnsignedBytes.MAX_VALUE);
            this.d[i2] = (this.b[i3] << Ascii.CAN) | ((this.b[i4] << Ascii.DLE) & Ascii.NUL) | ((this.b[i5] << 8) & Ascii.NUL) | (this.b[i] & UnsignedBytes.MAX_VALUE);
            i += 4;
        }
    }

    private int[] a(int[] iArr, int i) {
        iArr[0] = iArr[1];
        iArr[1] = iArr[2];
        iArr[2] = iArr[3];
        iArr[3] = i;
        return iArr;
    }

    private int b() {
        int i = (this.e[1] >>> 24) | (this.e[2] << 8);
        int i2 = (this.e[2] << 3) | (this.e[1] >>> 29);
        int i3 = (this.e[3] << 31) | (this.e[2] >>> 1);
        int i4 = i2;
        int i5 = i3;
        int i6 = (this.e[3] << 28) | (this.e[2] >>> 4);
        int i7 = i6;
        int i8 = (this.e[3] << 12) | (this.e[2] >>> 20);
        int i9 = i8;
        int i10 = (this.e[3] << 5) | (this.e[2] >>> 27);
        return ((((((((((this.e[0] ^ ((this.e[0] >>> 26) | (this.e[1] << 6))) ^ i) ^ i10) ^ this.e[3]) ^ (((this.e[0] >>> 3) | (this.e[1] << 29)) & ((this.e[3] << 29) | (this.e[2] >>> 3)))) ^ (((this.e[0] >>> 11) | (this.e[1] << 21)) & ((this.e[0] >>> 13) | (this.e[1] << 19)))) ^ (((this.e[0] >>> 17) | (this.e[1] << 15)) & ((this.e[0] >>> 18) | (this.e[1] << 14)))) ^ (((this.e[0] >>> 27) | (this.e[1] << 5)) & ((this.e[2] << 5) | (this.e[1] >>> 27)))) ^ (((this.e[1] >>> 8) | (this.e[2] << 24)) & ((this.e[1] >>> 16) | (this.e[2] << 16)))) ^ (i4 & i5)) ^ (i7 & i9);
    }

    private int c() {
        int i = (this.d[1] >>> 6) | (this.d[2] << 26);
        int i2 = (this.d[2] >>> 6) | (this.d[3] << 26);
        int i3 = (this.d[2] >>> 17) | (this.d[3] << 15);
        return ((((this.d[0] ^ ((this.d[0] >>> 7) | (this.d[1] << 25))) ^ i) ^ i2) ^ i3) ^ this.d[3];
    }

    private int d() {
        int i = (this.e[0] >>> 2) | (this.e[1] << 30);
        int i2 = (this.e[0] >>> 12) | (this.e[1] << 20);
        int i3 = (this.e[0] >>> 15) | (this.e[1] << 17);
        int i4 = (this.e[1] >>> 4) | (this.e[2] << 28);
        int i5 = (this.e[1] >>> 13) | (this.e[2] << 19);
        int i6 = (this.e[2] >>> 31) | (this.e[3] << 1);
        int i7 = (this.d[1] << 19) | (this.d[0] >>> 13);
        int i8 = (this.d[2] << 22) | (this.d[1] >>> 10);
        int i9 = (this.d[2] << 4) | (this.d[1] >>> 28);
        int i10 = (this.d[3] << 17) | (this.d[2] >>> 15);
        int i11 = (this.d[3] << 3) | (this.d[2] >>> 29);
        int i12 = this.d[2] >>> 31;
        int i13 = ((this.d[0] >>> 20) | (this.d[1] << 12)) & i7;
        return ((((((i ^ (((((i13 ^ (((this.d[0] >>> 8) | (this.d[1] << 24)) & i2)) ^ (i8 & i6)) ^ (i10 & i9)) ^ ((i2 & i6) & ((this.d[3] << 1) | i12))) ^ i11)) ^ i3) ^ i4) ^ i5) ^ this.e[2]) ^ ((this.e[2] >>> 9) | (this.e[3] << 23))) ^ ((this.e[2] >>> 25) | (this.e[3] << 7));
    }

    private void e() {
        this.f = d();
        this.c[0] = (byte) this.f;
        this.c[1] = (byte) (this.f >> 8);
        this.c[2] = (byte) (this.f >> 16);
        this.c[3] = (byte) (this.f >> 24);
        this.e = a(this.e, b() ^ this.d[0]);
        this.d = a(this.d, c());
    }

    private byte f() {
        if (this.g > 3) {
            e();
            this.g = 0;
        }
        byte[] bArr = this.c;
        int i = this.g;
        this.g = i + 1;
        return bArr[i];
    }

    public String getAlgorithmName() {
        return "Grain-128";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Grain-128 Init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        if (iv == null || iv.length != 12) {
            throw new IllegalArgumentException("Grain-128  requires exactly 12 bytes of IV");
        } else if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("Grain-128 Init parameters must include a key");
        } else {
            KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
            this.b = new byte[keyParameter.getKey().length];
            this.a = new byte[keyParameter.getKey().length];
            this.d = new int[4];
            this.e = new int[4];
            this.c = new byte[4];
            System.arraycopy(iv, 0, this.b, 0, iv.length);
            System.arraycopy(keyParameter.getKey(), 0, this.a, 0, keyParameter.getKey().length);
            reset();
        }
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (!this.h) {
            StringBuilder sb = new StringBuilder();
            sb.append(getAlgorithmName());
            sb.append(" not initialised");
            throw new IllegalStateException(sb.toString());
        } else if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            for (int i4 = 0; i4 < i2; i4++) {
                bArr2[i3 + i4] = (byte) (bArr[i + i4] ^ f());
            }
            return i2;
        }
    }

    public void reset() {
        this.g = 4;
        a(this.a, this.b);
        a();
    }

    public byte returnByte(byte b2) {
        if (this.h) {
            return (byte) (b2 ^ f());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getAlgorithmName());
        sb.append(" not initialised");
        throw new IllegalStateException(sb.toString());
    }
}
