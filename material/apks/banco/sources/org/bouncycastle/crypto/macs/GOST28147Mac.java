package org.bouncycastle.crypto.macs;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;

public class GOST28147Mac implements Mac {
    private int a = 8;
    private int b = 4;
    private int c = 0;
    private byte[] d = new byte[this.a];
    private byte[] e = new byte[this.a];
    private boolean f = true;
    private int[] g = null;
    private byte[] h = {9, 6, 3, 2, 8, Ascii.VT, 1, 7, 10, 4, Ascii.SO, Ascii.SI, Ascii.FF, 0, Ascii.CR, 5, 3, 7, Ascii.SO, 9, 8, 10, Ascii.SI, 0, 5, 2, 6, Ascii.FF, Ascii.VT, 4, Ascii.CR, 1, Ascii.SO, 4, 6, 2, Ascii.VT, 3, Ascii.CR, 8, Ascii.FF, Ascii.SI, 5, 10, 0, 7, 1, 9, Ascii.SO, 7, 10, Ascii.FF, Ascii.CR, 1, 3, 9, 0, 2, Ascii.VT, 4, Ascii.SI, 8, 5, 6, Ascii.VT, 5, 1, 9, 8, Ascii.CR, Ascii.SI, 0, Ascii.SO, 4, 2, 3, Ascii.FF, 7, 10, 6, 3, 10, Ascii.CR, Ascii.FF, 1, 2, 0, Ascii.VT, 7, 5, 9, 4, 8, Ascii.SI, Ascii.SO, 6, 1, Ascii.CR, 2, 9, 7, 10, 6, 0, 8, Ascii.FF, 4, 5, Ascii.SI, 3, Ascii.VT, Ascii.SO, Ascii.VT, 10, Ascii.SI, 5, 0, Ascii.FF, Ascii.SO, 8, 6, 2, 3, 9, 1, 7, Ascii.CR, 4};

    private int a(int i, int i2) {
        int i3 = i2 + i;
        int i4 = (this.h[((i3 >> 0) & 15) + 0] << 0) + (this.h[((i3 >> 4) & 15) + 16] << 4) + (this.h[((i3 >> 8) & 15) + 32] << 8) + (this.h[((i3 >> 12) & 15) + 48] << Ascii.FF) + (this.h[((i3 >> 16) & 15) + 64] << Ascii.DLE) + (this.h[((i3 >> 20) & 15) + 80] << Ascii.DC4) + (this.h[((i3 >> 24) & 15) + 96] << Ascii.CAN) + (this.h[((i3 >> 28) & 15) + 112] << Ascii.FS);
        return (i4 >>> 21) | (i4 << 11);
    }

    private int a(byte[] bArr, int i) {
        return ((bArr[i + 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) + ((bArr[i + 2] << Ascii.DLE) & 16711680) + ((bArr[i + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i] & UnsignedBytes.MAX_VALUE);
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) (i >>> 24);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2] = (byte) i;
    }

    private void a(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 4);
        int i3 = 0;
        while (i3 < 2) {
            int i4 = a3;
            int i5 = a2;
            int i6 = 0;
            while (i6 < 8) {
                i6++;
                int a4 = i4 ^ a(i5, iArr[i6]);
                i4 = i5;
                i5 = a4;
            }
            i3++;
            a2 = i5;
            a3 = i4;
        }
        a(a2, bArr2, i2);
        a(a3, bArr2, i2 + 4);
    }

    private byte[] a(byte[] bArr, int i, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length - i)];
        System.arraycopy(bArr, i, bArr3, 0, bArr2.length);
        for (int i2 = 0; i2 != bArr2.length; i2++) {
            bArr3[i2] = (byte) (bArr3[i2] ^ bArr2[i2]);
        }
        return bArr3;
    }

    private int[] a(byte[] bArr) {
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
        }
        int[] iArr = new int[8];
        for (int i = 0; i != 8; i++) {
            iArr[i] = a(bArr, i * 4);
        }
        return iArr;
    }

    public int doFinal(byte[] bArr, int i) {
        while (this.c < this.a) {
            this.d[this.c] = 0;
            this.c++;
        }
        byte[] bArr2 = new byte[this.d.length];
        System.arraycopy(this.d, 0, bArr2, 0, this.e.length);
        if (this.f) {
            this.f = false;
        } else {
            bArr2 = a(this.d, 0, this.e);
        }
        a(this.g, bArr2, 0, this.e, 0);
        System.arraycopy(this.e, (this.e.length / 2) - this.b, bArr, i, this.b);
        reset();
        return this.b;
    }

    public String getAlgorithmName() {
        return "GOST28147Mac";
    }

    public int getMacSize() {
        return this.b;
    }

    public void init(CipherParameters cipherParameters) {
        reset();
        this.d = new byte[this.a];
        if (cipherParameters instanceof ParametersWithSBox) {
            ParametersWithSBox parametersWithSBox = (ParametersWithSBox) cipherParameters;
            System.arraycopy(parametersWithSBox.getSBox(), 0, this.h, 0, parametersWithSBox.getSBox().length);
            if (parametersWithSBox.getParameters() != null) {
                cipherParameters = parametersWithSBox.getParameters();
            } else {
                return;
            }
        } else if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to GOST28147 init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        this.g = a(((KeyParameter) cipherParameters).getKey());
    }

    public void reset() {
        for (int i = 0; i < this.d.length; i++) {
            this.d[i] = 0;
        }
        this.c = 0;
        this.f = true;
    }

    public void update(byte b2) {
        if (this.c == this.d.length) {
            byte[] bArr = new byte[this.d.length];
            System.arraycopy(this.d, 0, bArr, 0, this.e.length);
            if (this.f) {
                this.f = false;
            } else {
                bArr = a(this.d, 0, this.e);
            }
            a(this.g, bArr, 0, this.e, 0);
            this.c = 0;
        }
        byte[] bArr2 = this.d;
        int i = this.c;
        this.c = i + 1;
        bArr2[i] = b2;
    }

    public void update(byte[] bArr, int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int i3 = this.a - this.c;
        if (i2 > i3) {
            System.arraycopy(bArr, i, this.d, this.c, i3);
            byte[] bArr2 = new byte[this.d.length];
            System.arraycopy(this.d, 0, bArr2, 0, this.e.length);
            if (this.f) {
                this.f = false;
            } else {
                bArr2 = a(this.d, 0, this.e);
            }
            a(this.g, bArr2, 0, this.e, 0);
            this.c = 0;
            i2 -= i3;
            while (true) {
                i += i3;
                if (i2 <= this.a) {
                    break;
                }
                a(this.g, a(bArr, i, this.e), 0, this.e, 0);
                i2 -= this.a;
                i3 = this.a;
            }
        }
        System.arraycopy(bArr, i, this.d, this.c, i2);
        this.c += i2;
    }
}
