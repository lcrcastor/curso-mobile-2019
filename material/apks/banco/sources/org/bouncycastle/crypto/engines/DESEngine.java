package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class DESEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private static final short[] b = {128, 64, 32, 16, 8, 4, 2, 1};
    private static final int[] c = {8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
    private static final byte[] d = {56, 48, 40, 32, Ascii.CAN, Ascii.DLE, 8, 0, 57, 49, 41, 33, Ascii.EM, 17, 9, 1, 58, 50, 42, 34, Ascii.SUB, Ascii.DC2, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, Ascii.RS, Ascii.SYN, Ascii.SO, 6, 61, 53, 45, 37, Ascii.GS, Ascii.NAK, Ascii.CR, 5, 60, 52, 44, 36, Ascii.FS, Ascii.DC4, Ascii.FF, 4, Ascii.ESC, 19, Ascii.VT, 3};
    private static final byte[] e = {1, 2, 4, 6, 8, 10, Ascii.FF, Ascii.SO, Ascii.SI, 17, 19, Ascii.NAK, Ascii.ETB, Ascii.EM, Ascii.ESC, Ascii.FS};
    private static final byte[] f = {Ascii.CR, Ascii.DLE, 10, Ascii.ETB, 0, 4, 2, Ascii.ESC, Ascii.SO, 5, Ascii.DC4, 9, Ascii.SYN, Ascii.DC2, Ascii.VT, 3, Ascii.EM, 7, Ascii.SI, 6, Ascii.SUB, 19, Ascii.FF, 1, 40, 51, Ascii.RS, 36, 46, 54, Ascii.GS, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, Ascii.FS, Ascii.US};
    private static final int[] g = {16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756};
    private static final int[] h = {-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
    private static final int[] i = {520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
    private static final int[] j = {8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928};
    private static final int[] k = {256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
    private static final int[] l = {536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, PKIFailureInfo.duplicateCertReq, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, PKIFailureInfo.duplicateCertReq, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, PKIFailureInfo.duplicateCertReq, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, PKIFailureInfo.duplicateCertReq, 4194320, 536887312};
    private static final int[] m = {2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154};
    private static final int[] n = {268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696};
    private int[] a = null;

    /* access modifiers changed from: protected */
    public void desFunc(int[] iArr, byte[] bArr, int i2, byte[] bArr2, int i3) {
        byte b2 = ((bArr[i2 + 0] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i2 + 2] & UnsignedBytes.MAX_VALUE) << 8) | (bArr[i2 + 3] & UnsignedBytes.MAX_VALUE);
        byte b3 = (bArr[i2 + 7] & UnsignedBytes.MAX_VALUE) | ((bArr[i2 + 4] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i2 + 5] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i2 + 6] & UnsignedBytes.MAX_VALUE) << 8);
        byte b4 = ((b2 >>> 4) ^ b3) & Ascii.SI;
        byte b5 = b3 ^ b4;
        byte b6 = (b4 << 4) ^ b2;
        byte b7 = ((b6 >>> Ascii.DLE) ^ b5) & UnsignedBytes.MAX_VALUE;
        byte b8 = b5 ^ b7;
        byte b9 = b6 ^ (b7 << Ascii.DLE);
        byte b10 = ((b8 >>> 2) ^ b9) & 858993459;
        byte b11 = b9 ^ b10;
        byte b12 = b8 ^ (b10 << 2);
        byte b13 = ((b12 >>> 8) ^ b11) & UnsignedBytes.MAX_VALUE;
        byte b14 = b11 ^ b13;
        byte b15 = b12 ^ (b13 << 8);
        byte b16 = (((b15 >>> Ascii.US) & 1) | (b15 << 1)) & -1;
        byte b17 = (b14 ^ b16) & -1431655766;
        byte b18 = b14 ^ b17;
        byte b19 = b16 ^ b17;
        int i4 = (((b18 >>> Ascii.US) & 1) | (b18 << 1)) & -1;
        for (int i5 = 0; i5 < 8; i5++) {
            int i6 = i5 * 4;
            int i7 = ((b19 << Ascii.FS) | (b19 >>> 4)) ^ iArr[i6 + 0];
            int i8 = g[(i7 >>> 24) & 63] | m[i7 & 63] | k[(i7 >>> 8) & 63] | i[(i7 >>> 16) & 63];
            byte b20 = iArr[i6 + 1] ^ b19;
            i4 ^= (((i8 | n[b20 & 63]) | l[(b20 >>> 8) & 63]) | j[(b20 >>> Ascii.DLE) & 63]) | h[(b20 >>> Ascii.CAN) & 63];
            int i9 = ((i4 << 28) | (i4 >>> 4)) ^ iArr[i6 + 2];
            int i10 = iArr[i6 + 3] ^ i4;
            b19 ^= ((((g[(i9 >>> 24) & 63] | ((m[i9 & 63] | k[(i9 >>> 8) & 63]) | i[(i9 >>> 16) & 63])) | n[i10 & 63]) | l[(i10 >>> 8) & 63]) | j[(i10 >>> 16) & 63]) | h[(i10 >>> 24) & 63];
        }
        int i11 = (b19 << Ascii.US) | (b19 >>> 1);
        int i12 = (i4 ^ i11) & -1431655766;
        int i13 = i4 ^ i12;
        int i14 = i11 ^ i12;
        int i15 = (i13 << 31) | (i13 >>> 1);
        int i16 = ((i15 >>> 8) ^ i14) & 16711935;
        int i17 = i14 ^ i16;
        int i18 = i15 ^ (i16 << 8);
        int i19 = ((i18 >>> 2) ^ i17) & 858993459;
        int i20 = i17 ^ i19;
        int i21 = i18 ^ (i19 << 2);
        int i22 = ((i20 >>> 16) ^ i21) & 65535;
        int i23 = i21 ^ i22;
        int i24 = i20 ^ (i22 << 16);
        int i25 = ((i24 >>> 4) ^ i23) & 252645135;
        int i26 = i23 ^ i25;
        int i27 = i24 ^ (i25 << 4);
        bArr2[i3 + 0] = (byte) ((i27 >>> 24) & 255);
        bArr2[i3 + 1] = (byte) ((i27 >>> 16) & 255);
        bArr2[i3 + 2] = (byte) ((i27 >>> 8) & 255);
        bArr2[i3 + 3] = (byte) (i27 & 255);
        bArr2[i3 + 4] = (byte) ((i26 >>> 24) & 255);
        bArr2[i3 + 5] = (byte) ((i26 >>> 16) & 255);
        bArr2[i3 + 6] = (byte) ((i26 >>> 8) & 255);
        bArr2[i3 + 7] = (byte) (i26 & 255);
    }

    /* access modifiers changed from: protected */
    public int[] generateWorkingKey(boolean z, byte[] bArr) {
        int i2;
        int[] iArr = new int[32];
        boolean[] zArr = new boolean[56];
        boolean[] zArr2 = new boolean[56];
        int i3 = 0;
        while (true) {
            boolean z2 = true;
            if (i3 >= 56) {
                break;
            }
            byte b2 = d[i3];
            if ((b[b2 & 7] & bArr[b2 >>> 3]) == 0) {
                z2 = false;
            }
            zArr[i3] = z2;
            i3++;
        }
        int i4 = 0;
        while (i4 < 16) {
            int i5 = z ? i4 << 1 : (15 - i4) << 1;
            int i6 = i5 + 1;
            iArr[i6] = 0;
            iArr[i5] = 0;
            int i7 = 0;
            while (true) {
                if (i7 >= 28) {
                    break;
                }
                int i8 = e[i4] + i7;
                if (i8 < 28) {
                    zArr2[i7] = zArr[i8];
                } else {
                    zArr2[i7] = zArr[i8 - 28];
                }
                i7++;
            }
            for (i2 = 28; i2 < 56; i2++) {
                int i9 = e[i4] + i2;
                if (i9 < 56) {
                    zArr2[i2] = zArr[i9];
                } else {
                    zArr2[i2] = zArr[i9 - 28];
                }
            }
            for (int i10 = 0; i10 < 24; i10++) {
                if (zArr2[f[i10]]) {
                    iArr[i5] = iArr[i5] | c[i10];
                }
                if (zArr2[f[i10 + 24]]) {
                    iArr[i6] = iArr[i6] | c[i10];
                }
            }
            i4++;
        }
        for (int i11 = 0; i11 != 32; i11 += 2) {
            int i12 = iArr[i11];
            int i13 = i11 + 1;
            int i14 = iArr[i13];
            iArr[i11] = ((16515072 & i14) >>> 10) | ((i12 & 16515072) << 6) | ((i12 & 4032) << 10) | ((i14 & 4032) >>> 6);
            iArr[i13] = ((i12 & 63) << 16) | ((i12 & 258048) << 12) | ((258048 & i14) >>> 4) | (i14 & 63);
        }
        return iArr;
    }

    public String getAlgorithmName() {
        return "DES";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            KeyParameter keyParameter = (KeyParameter) cipherParameters;
            if (keyParameter.getKey().length > 8) {
                throw new IllegalArgumentException("DES key too long - should be 8 bytes");
            }
            this.a = generateWorkingKey(z, keyParameter.getKey());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to DES init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.a == null) {
            throw new IllegalStateException("DES engine not initialised");
        } else if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            desFunc(this.a, bArr, i2, bArr2, i3);
            return 8;
        }
    }

    public void reset() {
    }
}
