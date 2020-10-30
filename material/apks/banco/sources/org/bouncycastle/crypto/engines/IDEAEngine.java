package org.bouncycastle.crypto.engines;

import com.facebook.internal.NativeProtocol;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class IDEAEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private int[] a = null;

    private int a(int i, int i2) {
        int i3;
        if (i == 0) {
            i3 = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY - i2;
        } else if (i2 == 0) {
            i3 = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY - i;
        } else {
            int i4 = i * i2;
            int i5 = i4 & 65535;
            int i6 = i4 >>> 16;
            i3 = (i5 - i6) + (i5 < i6 ? 1 : 0);
        }
        return i3 & 65535;
    }

    private int a(byte[] bArr, int i) {
        return ((bArr[i] << 8) & Ascii.NUL) + (bArr[i + 1] & UnsignedBytes.MAX_VALUE);
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
    }

    private void a(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int a2 = a(bArr, i);
        int a3 = a(bArr, i + 2);
        int a4 = a(bArr, i + 4);
        int a5 = a(bArr, i + 6);
        int i3 = 0;
        int i4 = a5;
        int i5 = 0;
        while (i3 < 8) {
            int i6 = i5 + 1;
            int a6 = a(a2, iArr[i5]);
            int i7 = i6 + 1;
            int i8 = (a3 + iArr[i6]) & 65535;
            int i9 = i7 + 1;
            int i10 = (a4 + iArr[i7]) & 65535;
            int i11 = i9 + 1;
            int a7 = a(i4, iArr[i9]);
            int i12 = i8 ^ a7;
            int i13 = i11 + 1;
            int a8 = a(i10 ^ a6, iArr[i11]);
            int i14 = (i12 + a8) & 65535;
            int i15 = i13 + 1;
            int a9 = a(i14, iArr[i13]);
            int i16 = (a8 + a9) & 65535;
            i4 = a7 ^ i16;
            a4 = i16 ^ i8;
            i3++;
            a3 = i10 ^ a9;
            a2 = a6 ^ a9;
            i5 = i15;
        }
        int i17 = i5 + 1;
        a(a(a2, iArr[i5]), bArr2, i2);
        int i18 = i17 + 1;
        a(a4 + iArr[i17], bArr2, i2 + 2);
        int i19 = i18 + 1;
        a(a3 + iArr[i18], bArr2, i2 + 4);
        a(a(i4, iArr[i19]), bArr2, i2 + 6);
    }

    private int[] a(boolean z, byte[] bArr) {
        return z ? a(bArr) : a(a(bArr));
    }

    private int[] a(byte[] bArr) {
        int i;
        int[] iArr = new int[52];
        int i2 = 0;
        if (bArr.length < 16) {
            byte[] bArr2 = new byte[16];
            System.arraycopy(bArr, 0, bArr2, bArr2.length - bArr.length, bArr.length);
            bArr = bArr2;
        }
        while (true) {
            if (i2 >= 8) {
                break;
            }
            iArr[i2] = a(bArr, i2 * 2);
            i2++;
        }
        for (i = 8; i < 52; i++) {
            int i3 = i & 7;
            if (i3 < 6) {
                iArr[i] = (((iArr[i - 7] & CertificateBody.profileType) << 9) | (iArr[i - 6] >> 7)) & 65535;
            } else if (i3 == 6) {
                iArr[i] = (((iArr[i - 7] & CertificateBody.profileType) << 9) | (iArr[i - 14] >> 7)) & 65535;
            } else {
                iArr[i] = (((iArr[i - 15] & CertificateBody.profileType) << 9) | (iArr[i - 14] >> 7)) & 65535;
            }
        }
        return iArr;
    }

    private int[] a(int[] iArr) {
        int[] iArr2 = new int[52];
        int b = b(iArr[0]);
        int i = 1;
        int a2 = a(iArr[1]);
        int a3 = a(iArr[2]);
        iArr2[51] = b(iArr[3]);
        iArr2[50] = a3;
        iArr2[49] = a2;
        int i2 = 48;
        iArr2[48] = b;
        int i3 = 4;
        while (i < 8) {
            int i4 = i3 + 1;
            int i5 = iArr[i3];
            int i6 = i4 + 1;
            int i7 = i2 - 1;
            iArr2[i7] = iArr[i4];
            int i8 = i7 - 1;
            iArr2[i8] = i5;
            int i9 = i6 + 1;
            int b2 = b(iArr[i6]);
            int i10 = i9 + 1;
            int a4 = a(iArr[i9]);
            int i11 = i10 + 1;
            int a5 = a(iArr[i10]);
            int i12 = i11 + 1;
            int i13 = i8 - 1;
            iArr2[i13] = b(iArr[i11]);
            int i14 = i13 - 1;
            iArr2[i14] = a4;
            int i15 = i14 - 1;
            iArr2[i15] = a5;
            i2 = i15 - 1;
            iArr2[i2] = b2;
            i++;
            i3 = i12;
        }
        int i16 = i3 + 1;
        int i17 = iArr[i3];
        int i18 = i16 + 1;
        int i19 = i2 - 1;
        iArr2[i19] = iArr[i16];
        int i20 = i19 - 1;
        iArr2[i20] = i17;
        int i21 = i18 + 1;
        int b3 = b(iArr[i18]);
        int i22 = i21 + 1;
        int a6 = a(iArr[i21]);
        int i23 = i22 + 1;
        int a7 = a(iArr[i22]);
        int i24 = i20 - 1;
        iArr2[i24] = b(iArr[i23]);
        int i25 = i24 - 1;
        iArr2[i25] = a7;
        int i26 = i25 - 1;
        iArr2[i26] = a6;
        iArr2[i26 - 1] = b3;
        return iArr2;
    }

    private int b(int i) {
        if (i < 2) {
            return i;
        }
        int i2 = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY % i;
        int i3 = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY / i;
        int i4 = 1;
        while (i2 != 1) {
            int i5 = i / i2;
            i %= i2;
            i4 = (i4 + (i5 * i3)) & 65535;
            if (i == 1) {
                return i4;
            }
            int i6 = i2 / i;
            i2 %= i;
            i3 = (i3 + (i6 * i4)) & 65535;
        }
        return (1 - i3) & 65535;
    }

    /* access modifiers changed from: 0000 */
    public int a(int i) {
        return (0 - i) & 65535;
    }

    public String getAlgorithmName() {
        return "IDEA";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.a = a(z, ((KeyParameter) cipherParameters).getKey());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to IDEA init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.a == null) {
            throw new IllegalStateException("IDEA engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            a(this.a, bArr, i, bArr2, i2);
            return 8;
        }
    }

    public void reset() {
    }
}
