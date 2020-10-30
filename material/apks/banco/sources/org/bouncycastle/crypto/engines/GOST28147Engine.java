package org.bouncycastle.crypto.engines;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.util.Hashtable;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class GOST28147Engine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private static byte[] d = {4, 10, 9, 2, Ascii.CR, 8, 0, Ascii.SO, 6, Ascii.VT, 1, Ascii.FF, 7, Ascii.SI, 5, 3, Ascii.SO, Ascii.VT, 4, Ascii.FF, 6, Ascii.CR, Ascii.SI, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, Ascii.CR, 10, 3, 4, 2, Ascii.SO, Ascii.SI, Ascii.FF, 7, 6, 0, 9, Ascii.VT, 7, Ascii.CR, 10, 1, 0, 8, 9, Ascii.SI, Ascii.SO, 4, 6, Ascii.FF, Ascii.VT, 2, 5, 3, 6, Ascii.FF, 7, 1, 5, Ascii.SI, Ascii.CR, 8, 4, 10, 9, Ascii.SO, 0, 3, Ascii.VT, 2, 4, Ascii.VT, 10, 0, 7, 2, 1, Ascii.CR, 3, 6, 8, 5, 9, Ascii.FF, Ascii.SI, Ascii.SO, Ascii.CR, Ascii.VT, 4, 1, 3, Ascii.SI, 5, 9, 0, 10, Ascii.SO, 7, 6, 8, 2, Ascii.FF, 1, Ascii.SI, Ascii.CR, 0, 5, 7, 10, 4, 9, 2, 3, Ascii.SO, 6, Ascii.VT, 8, Ascii.FF};
    private static byte[] e = {4, 2, Ascii.SI, 5, 9, 1, 0, 8, Ascii.SO, 3, Ascii.VT, Ascii.FF, Ascii.CR, 7, 10, 6, Ascii.FF, 9, Ascii.SI, Ascii.SO, 8, 1, 3, 10, 2, 7, 4, Ascii.CR, 6, 0, Ascii.VT, 5, Ascii.CR, 8, Ascii.SO, Ascii.FF, 7, 3, 9, 10, 1, 5, 2, 4, 6, Ascii.SI, 0, Ascii.VT, Ascii.SO, 9, Ascii.VT, 2, 5, Ascii.SI, 7, 1, 0, Ascii.CR, Ascii.FF, 6, 10, 4, 3, 8, 3, Ascii.SO, 5, 9, 6, 8, 0, Ascii.CR, 10, Ascii.VT, 7, Ascii.FF, 2, 1, Ascii.SI, 4, 8, Ascii.SI, 6, Ascii.VT, 1, 9, Ascii.FF, 5, Ascii.CR, 3, 7, 10, 0, Ascii.SO, 2, 4, 9, Ascii.VT, Ascii.FF, 0, 3, 6, 7, 5, 4, 8, Ascii.SO, Ascii.SI, 1, 10, 2, Ascii.CR, Ascii.FF, 6, 5, 2, Ascii.VT, 0, 9, Ascii.CR, 3, Ascii.SO, 7, 10, Ascii.SI, 4, 1, 8};
    private static byte[] f = {9, 6, 3, 2, 8, Ascii.VT, 1, 7, 10, 4, Ascii.SO, Ascii.SI, Ascii.FF, 0, Ascii.CR, 5, 3, 7, Ascii.SO, 9, 8, 10, Ascii.SI, 0, 5, 2, 6, Ascii.FF, Ascii.VT, 4, Ascii.CR, 1, Ascii.SO, 4, 6, 2, Ascii.VT, 3, Ascii.CR, 8, Ascii.FF, Ascii.SI, 5, 10, 0, 7, 1, 9, Ascii.SO, 7, 10, Ascii.FF, Ascii.CR, 1, 3, 9, 0, 2, Ascii.VT, 4, Ascii.SI, 8, 5, 6, Ascii.VT, 5, 1, 9, 8, Ascii.CR, Ascii.SI, 0, Ascii.SO, 4, 2, 3, Ascii.FF, 7, 10, 6, 3, 10, Ascii.CR, Ascii.FF, 1, 2, 0, Ascii.VT, 7, 5, 9, 4, 8, Ascii.SI, Ascii.SO, 6, 1, Ascii.CR, 2, 9, 7, 10, 6, 0, 8, Ascii.FF, 4, 5, Ascii.SI, 3, Ascii.VT, Ascii.SO, Ascii.VT, 10, Ascii.SI, 5, 0, Ascii.FF, Ascii.SO, 8, 6, 2, 3, 9, 1, 7, Ascii.CR, 4};
    private static byte[] g = {8, 4, Ascii.VT, 1, 3, 5, 0, 9, 2, Ascii.SO, 10, Ascii.FF, Ascii.CR, 6, 7, Ascii.SI, 0, 1, 2, 10, 4, Ascii.CR, 5, Ascii.FF, 9, 7, 3, Ascii.SI, Ascii.VT, 8, 6, Ascii.SO, Ascii.SO, Ascii.FF, 0, 10, 9, 2, Ascii.CR, Ascii.VT, 7, 5, 8, Ascii.SI, 3, 6, 1, 4, 7, 5, 0, Ascii.CR, Ascii.VT, 6, 1, 2, 3, 10, Ascii.FF, Ascii.SI, 4, Ascii.SO, 9, 8, 2, 7, Ascii.FF, Ascii.SI, 9, 5, 10, Ascii.VT, 1, 4, 0, Ascii.CR, 6, 8, Ascii.SO, 3, 8, 3, 2, 6, 4, Ascii.CR, Ascii.SO, Ascii.VT, Ascii.FF, 1, 7, Ascii.SI, 10, 0, 9, 5, 5, 2, 10, Ascii.VT, 9, 1, Ascii.FF, 3, 7, 4, Ascii.CR, 0, 6, Ascii.SI, 8, Ascii.SO, 0, 4, Ascii.VT, Ascii.SO, 8, 3, 7, 1, 10, 2, 9, 6, Ascii.SI, Ascii.CR, 5, Ascii.FF};
    private static byte[] h = {1, Ascii.VT, Ascii.FF, 2, 9, Ascii.CR, 0, Ascii.SI, 4, 5, 8, Ascii.SO, 10, 7, 6, 3, 0, 1, 7, Ascii.CR, Ascii.VT, 4, 5, 2, 8, Ascii.SO, Ascii.SI, Ascii.FF, 9, 10, 6, 3, 8, 2, 5, 0, 4, 9, Ascii.SI, 10, 3, 7, Ascii.FF, Ascii.CR, 6, Ascii.SO, 1, Ascii.VT, 3, 6, 0, 1, 5, Ascii.CR, 10, 8, Ascii.VT, 2, 9, 7, Ascii.SO, Ascii.SI, Ascii.FF, 4, 8, Ascii.CR, Ascii.VT, 0, 4, 5, 1, 2, 9, 3, Ascii.FF, Ascii.SO, 6, Ascii.SI, 10, 7, Ascii.FF, 9, Ascii.VT, 1, 8, Ascii.SO, 2, 4, 7, 3, 6, 5, 10, 0, Ascii.SI, Ascii.CR, 10, 9, 6, 8, Ascii.CR, Ascii.SO, 2, 0, Ascii.SI, 3, 5, Ascii.VT, 4, 1, Ascii.FF, 7, 7, 4, 0, 5, 10, 2, Ascii.SI, Ascii.SO, Ascii.FF, 6, 1, Ascii.VT, Ascii.CR, 9, 3, 8};
    private static byte[] i = {Ascii.SI, Ascii.FF, 2, 10, 6, 4, 5, 0, 7, 9, Ascii.SO, Ascii.CR, 1, Ascii.VT, 8, 3, Ascii.VT, 6, 3, 4, Ascii.FF, Ascii.SI, Ascii.SO, 2, 7, Ascii.CR, 8, 0, 5, 10, 9, 1, 1, Ascii.FF, Ascii.VT, 0, Ascii.SI, Ascii.SO, 6, 5, 10, Ascii.CR, 4, 8, 9, 3, 7, 2, 1, 5, Ascii.SO, Ascii.FF, 10, 7, 0, Ascii.CR, 6, 2, Ascii.VT, 4, 9, 3, Ascii.SI, 8, 0, Ascii.FF, 8, 9, Ascii.CR, 2, 10, Ascii.VT, 7, 3, 6, 5, 4, Ascii.SO, Ascii.SI, 1, 8, 0, Ascii.SI, 3, 2, 5, Ascii.SO, Ascii.VT, 1, 10, 4, 7, Ascii.FF, 9, Ascii.CR, 6, 3, 0, 6, Ascii.SI, 1, Ascii.SO, 9, 2, Ascii.CR, 8, Ascii.FF, 4, Ascii.VT, 10, 5, 7, 1, 10, 6, 8, Ascii.SI, Ascii.VT, 0, 4, Ascii.FF, 3, 5, 9, 7, Ascii.CR, 2, Ascii.SO};
    private static byte[] j = {4, 10, 9, 2, Ascii.CR, 8, 0, Ascii.SO, 6, Ascii.VT, 1, Ascii.FF, 7, Ascii.SI, 5, 3, Ascii.SO, Ascii.VT, 4, Ascii.FF, 6, Ascii.CR, Ascii.SI, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, Ascii.CR, 10, 3, 4, 2, Ascii.SO, Ascii.SI, Ascii.FF, 7, 6, 0, 9, Ascii.VT, 7, Ascii.CR, 10, 1, 0, 8, 9, Ascii.SI, Ascii.SO, 4, 6, Ascii.FF, Ascii.VT, 2, 5, 3, 6, Ascii.FF, 7, 1, 5, Ascii.SI, Ascii.CR, 8, 4, 10, 9, Ascii.SO, 0, 3, Ascii.VT, 2, 4, Ascii.VT, 10, 0, 7, 2, 1, Ascii.CR, 3, 6, 8, 5, 9, Ascii.FF, Ascii.SI, Ascii.SO, Ascii.CR, Ascii.VT, 4, 1, 3, Ascii.SI, 5, 9, 0, 10, Ascii.SO, 7, 6, 8, 2, Ascii.FF, 1, Ascii.SI, Ascii.CR, 0, 5, 7, 10, 4, 9, 2, 3, Ascii.SO, 6, Ascii.VT, 8, Ascii.FF};
    private static byte[] k = {10, 4, 5, 6, 8, 1, 3, 7, Ascii.CR, Ascii.FF, Ascii.SO, 0, 9, 2, Ascii.VT, Ascii.SI, 5, Ascii.SI, 4, 0, 2, Ascii.CR, Ascii.VT, 9, 1, 7, 6, 3, Ascii.FF, Ascii.SO, 10, 8, 7, Ascii.SI, Ascii.FF, Ascii.SO, 9, 4, 1, 0, 3, Ascii.VT, 5, 2, 6, 10, 8, Ascii.CR, 4, 10, 7, Ascii.FF, 0, Ascii.SI, 2, 8, Ascii.SO, 1, 6, 5, Ascii.CR, Ascii.VT, 9, 3, 7, 6, 4, Ascii.VT, 9, Ascii.FF, 2, 10, 1, 8, 0, Ascii.SO, Ascii.SI, Ascii.CR, 3, 5, 7, 6, 2, 4, Ascii.CR, 9, Ascii.SI, 0, 10, 1, 5, Ascii.VT, 8, Ascii.SO, Ascii.FF, 3, Ascii.CR, Ascii.SO, 4, 1, 7, 0, 5, 10, 3, Ascii.FF, 8, Ascii.SI, 6, 2, 9, Ascii.VT, 1, 3, 10, 9, 5, Ascii.VT, 4, Ascii.SI, 8, 6, 7, Ascii.SO, Ascii.CR, 0, 2, Ascii.FF};
    private static Hashtable l = new Hashtable();
    private int[] a = null;
    private boolean b;
    private byte[] c = d;

    static {
        a("Default", d);
        a("E-TEST", e);
        a("E-A", f);
        a("E-B", g);
        a("E-C", h);
        a("E-D", i);
        a("D-TEST", j);
        a("D-A", k);
    }

    private int a(int i2, int i3) {
        int i4 = i3 + i2;
        int i5 = (this.c[((i4 >> 0) & 15) + 0] << 0) + (this.c[((i4 >> 4) & 15) + 16] << 4) + (this.c[((i4 >> 8) & 15) + 32] << 8) + (this.c[((i4 >> 12) & 15) + 48] << Ascii.FF) + (this.c[((i4 >> 16) & 15) + 64] << Ascii.DLE) + (this.c[((i4 >> 20) & 15) + 80] << Ascii.DC4) + (this.c[((i4 >> 24) & 15) + 96] << Ascii.CAN) + (this.c[((i4 >> 28) & 15) + 112] << Ascii.FS);
        return (i5 >>> 21) | (i5 << 11);
    }

    private int a(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) + ((bArr[i2 + 2] << Ascii.DLE) & 16711680) + ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i2] & UnsignedBytes.MAX_VALUE);
    }

    private void a(int i2, byte[] bArr, int i3) {
        bArr[i3 + 3] = (byte) (i2 >>> 24);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3] = (byte) i2;
    }

    private static void a(String str, byte[] bArr) {
        l.put(Strings.toUpperCase(str), bArr);
    }

    private void a(int[] iArr, byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4;
        int i5;
        int a2 = a(bArr, i2);
        int a3 = a(bArr, i2 + 4);
        int i6 = 7;
        if (this.b) {
            int i7 = a3;
            int i8 = 0;
            while (i8 < 3) {
                int i9 = i7;
                int i10 = 0;
                while (i10 < 8) {
                    i10++;
                    int a4 = i9 ^ a(a2, iArr[i10]);
                    i9 = a2;
                    a2 = a4;
                }
                i8++;
                i7 = i9;
            }
            i5 = i7;
            i4 = a2;
            while (i6 > 0) {
                i6--;
                int i11 = i4;
                i4 = i5 ^ a(i4, iArr[i6]);
                i5 = i11;
            }
        } else {
            int i12 = a3;
            int i13 = 0;
            while (i13 < 8) {
                i13++;
                int i14 = a2;
                a2 = i12 ^ a(a2, iArr[i13]);
                i12 = i14;
            }
            int i15 = 0;
            while (i15 < 3) {
                int i16 = i12;
                int i17 = 7;
                while (i17 >= 0 && (i15 != 2 || i17 != 0)) {
                    i17--;
                    int a5 = i16 ^ a(a2, iArr[i17]);
                    i16 = a2;
                    a2 = a5;
                }
                i15++;
                i12 = i16;
            }
            i5 = i12;
            i4 = a2;
        }
        int a6 = a(i4, iArr[0]) ^ i5;
        a(i4, bArr2, i3);
        a(a6, bArr2, i3 + 4);
    }

    private int[] a(boolean z, byte[] bArr) {
        this.b = z;
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
        }
        int[] iArr = new int[8];
        for (int i2 = 0; i2 != 8; i2++) {
            iArr[i2] = a(bArr, i2 * 4);
        }
        return iArr;
    }

    public static byte[] getSBox(String str) {
        byte[] bArr = (byte[]) l.get(Strings.toUpperCase(str));
        if (bArr != null) {
            return Arrays.clone(bArr);
        }
        throw new IllegalArgumentException("Unknown S-Box - possible types: \"Default\", \"E-Test\", \"E-A\", \"E-B\", \"E-C\", \"E-D\", \"D-Test\", \"D-A\".");
    }

    public String getAlgorithmName() {
        return "GOST28147";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithSBox) {
            ParametersWithSBox parametersWithSBox = (ParametersWithSBox) cipherParameters;
            byte[] sBox = parametersWithSBox.getSBox();
            if (sBox.length != d.length) {
                throw new IllegalArgumentException("invalid S-box passed to GOST28147 init");
            }
            this.c = Arrays.clone(sBox);
            if (parametersWithSBox.getParameters() != null) {
                cipherParameters = parametersWithSBox.getParameters();
            }
            return;
        } else if (!(cipherParameters instanceof KeyParameter)) {
            if (cipherParameters != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("invalid parameter passed to GOST28147 init - ");
                sb.append(cipherParameters.getClass().getName());
                throw new IllegalArgumentException(sb.toString());
            }
            return;
        }
        this.a = a(z, ((KeyParameter) cipherParameters).getKey());
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.a == null) {
            throw new IllegalStateException("GOST28147 engine not initialised");
        } else if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            a(this.a, bArr, i2, bArr2, i3);
            return 8;
        }
    }

    public void reset() {
    }
}
