package org.bouncycastle.crypto.macs;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCMac implements Mac {
    private byte a;
    private byte b = 0;
    private byte[] c = null;
    private byte d = 0;
    private byte[] e;
    private byte[] f;
    private byte[] g;
    private byte h;
    private byte i;
    private byte j;
    private byte k;

    private void a(byte[] bArr, byte[] bArr2) {
        this.d = 0;
        this.c = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.c[i2] = (byte) i2;
        }
        for (int i3 = 0; i3 < 768; i3++) {
            int i4 = i3 & 255;
            this.d = this.c[(this.d + this.c[i4] + bArr[i3 % bArr.length]) & 255];
            byte b2 = this.c[i4];
            this.c[i4] = this.c[this.d & UnsignedBytes.MAX_VALUE];
            this.c[this.d & UnsignedBytes.MAX_VALUE] = b2;
        }
        for (int i5 = 0; i5 < 768; i5++) {
            int i6 = i5 & 255;
            this.d = this.c[(this.d + this.c[i6] + bArr2[i5 % bArr2.length]) & 255];
            byte b3 = this.c[i6];
            this.c[i6] = this.c[this.d & UnsignedBytes.MAX_VALUE];
            this.c[this.d & UnsignedBytes.MAX_VALUE] = b3;
        }
        this.b = 0;
    }

    public int doFinal(byte[] bArr, int i2) {
        for (int i3 = 1; i3 < 25; i3++) {
            this.d = this.c[(this.d + this.c[this.b & UnsignedBytes.MAX_VALUE]) & 255];
            this.k = this.c[(this.k + this.j + i3) & 255];
            this.j = this.c[(this.j + this.i + i3) & 255];
            this.i = this.c[(this.i + this.h + i3) & 255];
            this.h = this.c[(this.h + this.d + i3) & 255];
            this.e[this.a & Ascii.US] = (byte) (this.e[this.a & Ascii.US] ^ this.h);
            this.e[(this.a + 1) & 31] = (byte) (this.e[(this.a + 1) & 31] ^ this.i);
            this.e[(this.a + 2) & 31] = (byte) (this.e[(this.a + 2) & 31] ^ this.j);
            this.e[(this.a + 3) & 31] = (byte) (this.e[(this.a + 3) & 31] ^ this.k);
            this.a = (byte) ((this.a + 4) & 31);
            byte b2 = this.c[this.b & UnsignedBytes.MAX_VALUE];
            this.c[this.b & UnsignedBytes.MAX_VALUE] = this.c[this.d & UnsignedBytes.MAX_VALUE];
            this.c[this.d & UnsignedBytes.MAX_VALUE] = b2;
            this.b = (byte) ((this.b + 1) & 255);
        }
        for (int i4 = 0; i4 < 768; i4++) {
            int i5 = i4 & 255;
            this.d = this.c[(this.d + this.c[i5] + this.e[i4 & 31]) & 255];
            byte b3 = this.c[i5];
            this.c[i5] = this.c[this.d & UnsignedBytes.MAX_VALUE];
            this.c[this.d & UnsignedBytes.MAX_VALUE] = b3;
        }
        byte[] bArr2 = new byte[20];
        for (int i6 = 0; i6 < 20; i6++) {
            int i7 = i6 & 255;
            this.d = this.c[(this.d + this.c[i7]) & 255];
            bArr2[i6] = this.c[(this.c[this.c[this.d & UnsignedBytes.MAX_VALUE] & UnsignedBytes.MAX_VALUE] + 1) & 255];
            byte b4 = this.c[i7];
            this.c[i7] = this.c[this.d & UnsignedBytes.MAX_VALUE];
            this.c[this.d & UnsignedBytes.MAX_VALUE] = b4;
        }
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        reset();
        return bArr2.length;
    }

    public String getAlgorithmName() {
        return "VMPC-MAC";
    }

    public int getMacSize() {
        return 20;
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
        }
        this.f = parametersWithIV.getIV();
        if (this.f == null || this.f.length < 1 || this.f.length > 768) {
            throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
        }
        this.g = keyParameter.getKey();
        reset();
    }

    public void reset() {
        a(this.g, this.f);
        this.b = 0;
        this.k = 0;
        this.j = 0;
        this.i = 0;
        this.h = 0;
        this.a = 0;
        this.e = new byte[32];
        for (int i2 = 0; i2 < 32; i2++) {
            this.e[i2] = 0;
        }
    }

    public void update(byte b2) {
        this.d = this.c[(this.d + this.c[this.b & UnsignedBytes.MAX_VALUE]) & 255];
        byte b3 = (byte) (b2 ^ this.c[(this.c[this.c[this.d & UnsignedBytes.MAX_VALUE] & UnsignedBytes.MAX_VALUE] + 1) & 255]);
        this.k = this.c[(this.k + this.j) & 255];
        this.j = this.c[(this.j + this.i) & 255];
        this.i = this.c[(this.i + this.h) & 255];
        this.h = this.c[(this.h + this.d + b3) & 255];
        this.e[this.a & Ascii.US] = (byte) (this.e[this.a & Ascii.US] ^ this.h);
        this.e[(this.a + 1) & 31] = (byte) (this.e[(this.a + 1) & 31] ^ this.i);
        this.e[(this.a + 2) & 31] = (byte) (this.e[(this.a + 2) & 31] ^ this.j);
        this.e[(this.a + 3) & 31] = (byte) (this.e[(this.a + 3) & 31] ^ this.k);
        this.a = (byte) ((this.a + 4) & 31);
        byte b4 = this.c[this.b & UnsignedBytes.MAX_VALUE];
        this.c[this.b & UnsignedBytes.MAX_VALUE] = this.c[this.d & UnsignedBytes.MAX_VALUE];
        this.c[this.d & UnsignedBytes.MAX_VALUE] = b4;
        this.b = (byte) ((this.b + 1) & 255);
    }

    public void update(byte[] bArr, int i2, int i3) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i4 = 0; i4 < i3; i4++) {
            update(bArr[i4]);
        }
    }
}
