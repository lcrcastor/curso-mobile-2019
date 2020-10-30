package org.bouncycastle.crypto.engines;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class Grainv1Engine implements StreamCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int[] d;
    private int[] e;
    private int f;
    private int g = 2;
    private boolean h = false;

    private void a() {
        for (int i = 0; i < 10; i++) {
            this.f = d();
            this.e = a(this.e, (b() ^ this.d[0]) ^ this.f);
            this.d = a(this.d, c() ^ this.f);
        }
        this.h = true;
    }

    private void a(byte[] bArr, byte[] bArr2) {
        bArr2[8] = -1;
        bArr2[9] = -1;
        this.a = bArr;
        this.b = bArr2;
        int i = 0;
        for (int i2 = 0; i2 < this.e.length; i2++) {
            int i3 = i + 1;
            this.e[i2] = ((this.a[i3] << 8) | (this.a[i] & UnsignedBytes.MAX_VALUE)) & UnsignedBytes.MAX_VALUE;
            this.d[i2] = ((this.b[i3] << 8) | (this.b[i] & UnsignedBytes.MAX_VALUE)) & UnsignedBytes.MAX_VALUE;
            i += 2;
        }
    }

    private int[] a(int[] iArr, int i) {
        iArr[0] = iArr[1];
        iArr[1] = iArr[2];
        iArr[2] = iArr[3];
        iArr[3] = iArr[4];
        iArr[4] = i;
        return iArr;
    }

    private int b() {
        int i = (this.e[0] >>> 9) | (this.e[1] << 7);
        int i2 = (this.e[0] >>> 15) | (this.e[1] << 1);
        int i3 = (this.e[1] >>> 5) | (this.e[2] << 11);
        int i4 = (this.e[1] >>> 12) | (this.e[2] << 4);
        int i5 = (this.e[2] >>> 1) | (this.e[3] << 15);
        int i6 = (this.e[2] >>> 5) | (this.e[3] << 11);
        int i7 = (this.e[2] >>> 13) | (this.e[3] << 3);
        int i8 = (this.e[3] >>> 4) | (this.e[4] << 12);
        int i9 = (this.e[4] << 4) | (this.e[3] >>> 12);
        int i10 = (this.e[4] << 1) | (this.e[3] >>> 15);
        int i11 = this.e[0] ^ ((((this.e[0] >>> 14) | (this.e[1] << 2)) ^ (((((((((this.e[4] << 2) | (this.e[3] >>> 14)) ^ i9) ^ i8) ^ i7) ^ i6) ^ i5) ^ i4) ^ i3)) ^ i);
        int i12 = i10 & i9;
        int i13 = i9 & i8;
        int i14 = i5 & i4 & i3;
        return (((((((((((i11 ^ i12) ^ (i6 & i5)) ^ (i2 & i)) ^ (i13 & i7)) ^ i14) ^ (((i10 & i7) & i4) & i)) ^ ((i13 & i6) & i5)) ^ ((i12 & i3) & i2)) ^ (((i12 & i8) & i7) & i6)) ^ ((i2 & i14) & i)) ^ (((((i8 & i7) & i6) & i5) & i4) & i3)) & 65535;
    }

    private int c() {
        int i = (this.d[1] >>> 7) | (this.d[2] << 9);
        int i2 = (this.d[2] >>> 6) | (this.d[3] << 10);
        int i3 = (this.d[3] >>> 3) | (this.d[4] << 13);
        return (((((this.d[0] ^ ((this.d[0] >>> 13) | (this.d[1] << 3))) ^ i) ^ i2) ^ i3) ^ ((this.d[3] >>> 14) | (this.d[4] << 2))) & 65535;
    }

    private int d() {
        int i = (this.e[0] >>> 1) | (this.e[1] << 15);
        int i2 = (this.e[0] >>> 2) | (this.e[1] << 14);
        int i3 = (this.e[0] >>> 4) | (this.e[1] << 12);
        int i4 = (this.e[0] >>> 10) | (this.e[1] << 6);
        int i5 = (this.e[1] >>> 15) | (this.e[2] << 1);
        int i6 = (this.e[2] >>> 11) | (this.e[3] << 5);
        int i7 = (this.e[3] >>> 8) | (this.e[4] << 8);
        int i8 = (this.e[3] >>> 15) | (this.e[4] << 1);
        int i9 = (this.d[0] >>> 3) | (this.d[1] << 13);
        int i10 = (this.d[1] >>> 9) | (this.d[2] << 7);
        int i11 = (this.d[3] << 2) | (this.d[2] >>> 14);
        int i12 = this.d[4];
        int i13 = (i10 ^ i8) ^ (i9 & i12);
        int i14 = i11 & i12;
        int i15 = i9 & i11;
        int i16 = i12 & i15;
        return (((((((i ^ ((((i15 & i8) ^ (i16 ^ (((i13 ^ i14) ^ (i12 & i8)) ^ ((i9 & i10) & i11)))) ^ ((i10 & i11) & i8)) ^ (i14 & i8))) ^ i2) ^ i3) ^ i4) ^ i5) ^ i6) ^ i7) & 65535;
    }

    private void e() {
        this.f = d();
        this.c[0] = (byte) this.f;
        this.c[1] = (byte) (this.f >> 8);
        this.e = a(this.e, b() ^ this.d[0]);
        this.d = a(this.d, c());
    }

    private byte f() {
        if (this.g > 1) {
            e();
            this.g = 0;
        }
        byte[] bArr = this.c;
        int i = this.g;
        this.g = i + 1;
        return bArr[i];
    }

    public String getAlgorithmName() {
        return "Grain v1";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("Grain v1 Init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] iv = parametersWithIV.getIV();
        if (iv == null || iv.length != 8) {
            throw new IllegalArgumentException("Grain v1 requires exactly 8 bytes of IV");
        } else if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("Grain v1 Init parameters must include a key");
        } else {
            KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
            this.b = new byte[keyParameter.getKey().length];
            this.a = new byte[keyParameter.getKey().length];
            this.d = new int[5];
            this.e = new int[5];
            this.c = new byte[2];
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
        this.g = 2;
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
