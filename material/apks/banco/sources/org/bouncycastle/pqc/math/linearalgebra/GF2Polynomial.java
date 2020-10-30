package org.bouncycastle.pqc.math.linearalgebra;

import android.support.v4.app.FrameMetricsAggregator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;
import com.google.common.primitives.Shorts;
import com.google.common.primitives.UnsignedBytes;
import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.eac.CertificateBody;

public class GF2Polynomial {
    private static Random d = new Random();
    private static final boolean[] e = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private static final short[] f = {0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, Shorts.MAX_POWER_OF_TWO, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845};
    private static final int[] g = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, PKIFailureInfo.duplicateCertReq, 1073741824, Integer.MIN_VALUE, 0};
    private static final int[] h = {0, 1, 3, 7, 15, 31, 63, CertificateBody.profileType, 255, FrameMetricsAggregator.EVERY_DURATION, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, ViewCompat.MEASURED_SIZE_MASK, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, SubsamplingScaleImageView.TILE_SIZE_AUTO, -1};
    private int a;
    private int b;
    private int[] c;

    public GF2Polynomial(int i) {
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
    }

    public GF2Polynomial(int i, String str) {
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
        if (str.equalsIgnoreCase("ZERO")) {
            assignZero();
        } else if (str.equalsIgnoreCase("ONE")) {
            assignOne();
        } else if (str.equalsIgnoreCase("RANDOM")) {
            randomize();
        } else if (str.equalsIgnoreCase("X")) {
            assignX();
        } else if (str.equalsIgnoreCase("ALL")) {
            assignAll();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Error: GF2Polynomial was called using ");
            sb.append(str);
            sb.append(" as value!");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public GF2Polynomial(int i, BigInteger bigInteger) {
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] == 0) {
            byte[] bArr = new byte[(byteArray.length - 1)];
            System.arraycopy(byteArray, 1, bArr, 0, bArr.length);
            byteArray = bArr;
        }
        int length = byteArray.length & 3;
        int length2 = ((byteArray.length - 1) >> 2) + 1;
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr = this.c;
            int i3 = length2 - 1;
            iArr[i3] = iArr[i3] | ((byteArray[i2] & UnsignedBytes.MAX_VALUE) << (((length - 1) - i2) << 3));
        }
        for (int i4 = 0; i4 <= ((byteArray.length - 4) >> 2); i4++) {
            int length3 = (byteArray.length - 1) - (i4 << 2);
            this.c[i4] = byteArray[length3] & UnsignedBytes.MAX_VALUE;
            int[] iArr2 = this.c;
            iArr2[i4] = iArr2[i4] | ((byteArray[length3 - 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
            int[] iArr3 = this.c;
            iArr3[i4] = iArr3[i4] | ((byteArray[length3 - 2] << Ascii.DLE) & 16711680);
            int[] iArr4 = this.c;
            iArr4[i4] = ((byteArray[length3 - 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | iArr4[i4];
        }
        if ((this.a & 31) != 0) {
            int[] iArr5 = this.c;
            int i5 = this.b - 1;
            iArr5[i5] = iArr5[i5] & h[this.a & 31];
        }
        reduceN();
    }

    public GF2Polynomial(int i, Random random) {
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
        randomize(random);
    }

    public GF2Polynomial(int i, byte[] bArr) {
        int i2;
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
        int min = Math.min(((bArr.length - 1) >> 2) + 1, this.b);
        int i3 = 0;
        while (true) {
            i2 = min - 1;
            if (i3 >= i2) {
                break;
            }
            int length = (bArr.length - (i3 << 2)) - 1;
            this.c[i3] = bArr[length] & UnsignedBytes.MAX_VALUE;
            int[] iArr = this.c;
            iArr[i3] = (65280 & (bArr[length - 1] << 8)) | iArr[i3];
            int[] iArr2 = this.c;
            iArr2[i3] = (16711680 & (bArr[length - 2] << Ascii.DLE)) | iArr2[i3];
            int[] iArr3 = this.c;
            iArr3[i3] = ((bArr[length - 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | iArr3[i3];
            i3++;
        }
        int length2 = (bArr.length - (i2 << 2)) - 1;
        this.c[i2] = bArr[length2] & UnsignedBytes.MAX_VALUE;
        if (length2 > 0) {
            int[] iArr4 = this.c;
            iArr4[i2] = (65280 & (bArr[length2 - 1] << 8)) | iArr4[i2];
        }
        if (length2 > 1) {
            int[] iArr5 = this.c;
            iArr5[i2] = iArr5[i2] | (16711680 & (bArr[length2 - 2] << Ascii.DLE));
        }
        if (length2 > 2) {
            int[] iArr6 = this.c;
            iArr6[i2] = ((bArr[length2 - 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | iArr6[i2];
        }
        b();
        reduceN();
    }

    public GF2Polynomial(int i, int[] iArr) {
        if (i < 1) {
            i = 1;
        }
        this.b = ((i - 1) >> 5) + 1;
        this.c = new int[this.b];
        this.a = i;
        System.arraycopy(iArr, 0, this.c, 0, Math.min(this.b, iArr.length));
        b();
    }

    public GF2Polynomial(GF2Polynomial gF2Polynomial) {
        this.a = gF2Polynomial.a;
        this.b = gF2Polynomial.b;
        this.c = IntUtils.clone(gF2Polynomial.c);
    }

    private GF2Polynomial a(int i) {
        int min = Math.min(i, this.b - i);
        GF2Polynomial gF2Polynomial = new GF2Polynomial(min << 5);
        if (this.b >= i) {
            System.arraycopy(this.c, i, gF2Polynomial.c, 0, min);
        }
        return gF2Polynomial;
    }

    private GF2Polynomial a(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.a << 1);
        if (this.a <= 32) {
            gF2Polynomial2.c = b(this.c[0], gF2Polynomial.c[0]);
            return gF2Polynomial2;
        } else if (this.a <= 64) {
            gF2Polynomial2.c = d(this.c, gF2Polynomial.c);
            return gF2Polynomial2;
        } else if (this.a <= 128) {
            gF2Polynomial2.c = c(this.c, gF2Polynomial.c);
            return gF2Polynomial2;
        } else if (this.a <= 256) {
            gF2Polynomial2.c = b(this.c, gF2Polynomial.c);
            return gF2Polynomial2;
        } else if (this.a <= 512) {
            gF2Polynomial2.c = a(this.c, gF2Polynomial.c);
            return gF2Polynomial2;
        } else {
            int i = g[IntegerFunctions.floorLog(this.a - 1)];
            int i2 = ((i - 1) >> 5) + 1;
            GF2Polynomial b2 = b(i2);
            GF2Polynomial a2 = a(i2);
            GF2Polynomial b3 = gF2Polynomial.b(i2);
            GF2Polynomial a3 = gF2Polynomial.a(i2);
            GF2Polynomial a4 = a2.a(a3);
            GF2Polynomial a5 = b2.a(b3);
            b2.addToThis(a2);
            b3.addToThis(a3);
            GF2Polynomial a6 = b2.a(b3);
            gF2Polynomial2.shiftLeftAddThis(a4, i << 1);
            gF2Polynomial2.shiftLeftAddThis(a4, i);
            gF2Polynomial2.shiftLeftAddThis(a6, i);
            gF2Polynomial2.shiftLeftAddThis(a5, i);
            gF2Polynomial2.addToThis(a5);
            return gF2Polynomial2;
        }
    }

    private static int[] a(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        int[] iArr5 = new int[32];
        int[] iArr6 = new int[8];
        System.arraycopy(iArr3, 0, iArr6, 0, Math.min(8, iArr3.length));
        int[] iArr7 = new int[8];
        if (iArr3.length > 8) {
            System.arraycopy(iArr3, 8, iArr7, 0, Math.min(8, iArr3.length - 8));
        }
        int[] iArr8 = new int[8];
        System.arraycopy(iArr4, 0, iArr8, 0, Math.min(8, iArr4.length));
        int[] iArr9 = new int[8];
        if (iArr4.length > 8) {
            System.arraycopy(iArr4, 8, iArr9, 0, Math.min(8, iArr4.length - 8));
        }
        int[] b2 = b(iArr7, iArr9);
        iArr5[31] = iArr5[31] ^ b2[15];
        iArr5[30] = iArr5[30] ^ b2[14];
        iArr5[29] = iArr5[29] ^ b2[13];
        iArr5[28] = iArr5[28] ^ b2[12];
        iArr5[27] = iArr5[27] ^ b2[11];
        iArr5[26] = iArr5[26] ^ b2[10];
        iArr5[25] = iArr5[25] ^ b2[9];
        iArr5[24] = iArr5[24] ^ b2[8];
        iArr5[23] = iArr5[23] ^ (b2[7] ^ b2[15]);
        iArr5[22] = iArr5[22] ^ (b2[6] ^ b2[14]);
        iArr5[21] = iArr5[21] ^ (b2[5] ^ b2[13]);
        iArr5[20] = iArr5[20] ^ (b2[4] ^ b2[12]);
        iArr5[19] = iArr5[19] ^ (b2[3] ^ b2[11]);
        iArr5[18] = iArr5[18] ^ (b2[2] ^ b2[10]);
        iArr5[17] = iArr5[17] ^ (b2[1] ^ b2[9]);
        iArr5[16] = iArr5[16] ^ (b2[0] ^ b2[8]);
        iArr5[15] = iArr5[15] ^ b2[7];
        iArr5[14] = iArr5[14] ^ b2[6];
        iArr5[13] = iArr5[13] ^ b2[5];
        iArr5[12] = iArr5[12] ^ b2[4];
        iArr5[11] = iArr5[11] ^ b2[3];
        iArr5[10] = iArr5[10] ^ b2[2];
        iArr5[9] = iArr5[9] ^ b2[1];
        iArr5[8] = b2[0] ^ iArr5[8];
        iArr7[0] = iArr7[0] ^ iArr6[0];
        iArr7[1] = iArr7[1] ^ iArr6[1];
        iArr7[2] = iArr7[2] ^ iArr6[2];
        iArr7[3] = iArr7[3] ^ iArr6[3];
        iArr7[4] = iArr7[4] ^ iArr6[4];
        iArr7[5] = iArr7[5] ^ iArr6[5];
        iArr7[6] = iArr7[6] ^ iArr6[6];
        iArr7[7] = iArr7[7] ^ iArr6[7];
        iArr9[0] = iArr9[0] ^ iArr8[0];
        iArr9[1] = iArr9[1] ^ iArr8[1];
        iArr9[2] = iArr9[2] ^ iArr8[2];
        iArr9[3] = iArr9[3] ^ iArr8[3];
        iArr9[4] = iArr9[4] ^ iArr8[4];
        iArr9[5] = iArr9[5] ^ iArr8[5];
        iArr9[6] = iArr9[6] ^ iArr8[6];
        iArr9[7] = iArr9[7] ^ iArr8[7];
        int[] b3 = b(iArr7, iArr9);
        iArr5[23] = iArr5[23] ^ b3[15];
        iArr5[22] = iArr5[22] ^ b3[14];
        iArr5[21] = iArr5[21] ^ b3[13];
        iArr5[20] = iArr5[20] ^ b3[12];
        iArr5[19] = iArr5[19] ^ b3[11];
        iArr5[18] = iArr5[18] ^ b3[10];
        iArr5[17] = iArr5[17] ^ b3[9];
        iArr5[16] = iArr5[16] ^ b3[8];
        iArr5[15] = iArr5[15] ^ b3[7];
        iArr5[14] = iArr5[14] ^ b3[6];
        iArr5[13] = iArr5[13] ^ b3[5];
        iArr5[12] = iArr5[12] ^ b3[4];
        iArr5[11] = iArr5[11] ^ b3[3];
        iArr5[10] = iArr5[10] ^ b3[2];
        iArr5[9] = iArr5[9] ^ b3[1];
        iArr5[8] = b3[0] ^ iArr5[8];
        int[] b4 = b(iArr6, iArr8);
        iArr5[23] = iArr5[23] ^ b4[15];
        iArr5[22] = iArr5[22] ^ b4[14];
        iArr5[21] = iArr5[21] ^ b4[13];
        iArr5[20] = iArr5[20] ^ b4[12];
        iArr5[19] = iArr5[19] ^ b4[11];
        iArr5[18] = iArr5[18] ^ b4[10];
        iArr5[17] = iArr5[17] ^ b4[9];
        iArr5[16] = iArr5[16] ^ b4[8];
        iArr5[15] = iArr5[15] ^ (b4[7] ^ b4[15]);
        iArr5[14] = iArr5[14] ^ (b4[6] ^ b4[14]);
        iArr5[13] = iArr5[13] ^ (b4[5] ^ b4[13]);
        iArr5[12] = iArr5[12] ^ (b4[4] ^ b4[12]);
        iArr5[11] = iArr5[11] ^ (b4[3] ^ b4[11]);
        iArr5[10] = iArr5[10] ^ (b4[2] ^ b4[10]);
        iArr5[9] = iArr5[9] ^ (b4[1] ^ b4[9]);
        iArr5[8] = iArr5[8] ^ (b4[0] ^ b4[8]);
        iArr5[7] = iArr5[7] ^ b4[7];
        iArr5[6] = iArr5[6] ^ b4[6];
        iArr5[5] = iArr5[5] ^ b4[5];
        iArr5[4] = iArr5[4] ^ b4[4];
        iArr5[3] = iArr5[3] ^ b4[3];
        iArr5[2] = iArr5[2] ^ b4[2];
        iArr5[1] = iArr5[1] ^ b4[1];
        iArr5[0] = b4[0] ^ iArr5[0];
        return iArr5;
    }

    private GF2Polynomial b(int i) {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(i << 5);
        System.arraycopy(this.c, 0, gF2Polynomial.c, 0, Math.min(i, this.b));
        return gF2Polynomial;
    }

    private void b() {
        if ((this.a & 31) != 0) {
            int[] iArr = this.c;
            int i = this.b - 1;
            iArr[i] = iArr[i] & h[this.a & 31];
        }
    }

    private static int[] b(int i, int i2) {
        int[] iArr = new int[2];
        if (i == 0 || i2 == 0) {
            return iArr;
        }
        long j = ((long) i2) & 4294967295L;
        long j2 = 0;
        for (int i3 = 1; i3 <= 32; i3++) {
            if ((g[i3 - 1] & i) != 0) {
                j2 ^= j;
            }
            j <<= 1;
        }
        iArr[1] = (int) (j2 >>> 32);
        iArr[0] = (int) (j2 & 4294967295L);
        return iArr;
    }

    private static int[] b(int[] iArr, int[] iArr2) {
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        int[] iArr5 = new int[16];
        int[] iArr6 = new int[4];
        System.arraycopy(iArr3, 0, iArr6, 0, Math.min(4, iArr3.length));
        int[] iArr7 = new int[4];
        if (iArr3.length > 4) {
            System.arraycopy(iArr3, 4, iArr7, 0, Math.min(4, iArr3.length - 4));
        }
        int[] iArr8 = new int[4];
        System.arraycopy(iArr4, 0, iArr8, 0, Math.min(4, iArr4.length));
        int[] iArr9 = new int[4];
        if (iArr4.length > 4) {
            System.arraycopy(iArr4, 4, iArr9, 0, Math.min(4, iArr4.length - 4));
        }
        if (iArr7[3] != 0 || iArr7[2] != 0 || iArr9[3] != 0 || iArr9[2] != 0) {
            int[] c2 = c(iArr7, iArr9);
            iArr5[15] = iArr5[15] ^ c2[7];
            iArr5[14] = iArr5[14] ^ c2[6];
            iArr5[13] = iArr5[13] ^ c2[5];
            iArr5[12] = iArr5[12] ^ c2[4];
            iArr5[11] = iArr5[11] ^ (c2[3] ^ c2[7]);
            iArr5[10] = iArr5[10] ^ (c2[2] ^ c2[6]);
            iArr5[9] = iArr5[9] ^ (c2[1] ^ c2[5]);
            iArr5[8] = iArr5[8] ^ (c2[0] ^ c2[4]);
            iArr5[7] = iArr5[7] ^ c2[3];
            iArr5[6] = iArr5[6] ^ c2[2];
            iArr5[5] = iArr5[5] ^ c2[1];
            iArr5[4] = c2[0] ^ iArr5[4];
        } else if (iArr7[1] != 0 || iArr9[1] != 0) {
            int[] d2 = d(iArr7, iArr9);
            iArr5[11] = iArr5[11] ^ d2[3];
            iArr5[10] = iArr5[10] ^ d2[2];
            iArr5[9] = iArr5[9] ^ d2[1];
            iArr5[8] = iArr5[8] ^ d2[0];
            iArr5[7] = iArr5[7] ^ d2[3];
            iArr5[6] = iArr5[6] ^ d2[2];
            iArr5[5] = iArr5[5] ^ d2[1];
            iArr5[4] = d2[0] ^ iArr5[4];
        } else if (!(iArr7[0] == 0 && iArr9[0] == 0)) {
            int[] b2 = b(iArr7[0], iArr9[0]);
            iArr5[9] = iArr5[9] ^ b2[1];
            iArr5[8] = iArr5[8] ^ b2[0];
            iArr5[5] = iArr5[5] ^ b2[1];
            iArr5[4] = b2[0] ^ iArr5[4];
        }
        iArr7[0] = iArr7[0] ^ iArr6[0];
        iArr7[1] = iArr7[1] ^ iArr6[1];
        iArr7[2] = iArr7[2] ^ iArr6[2];
        iArr7[3] = iArr7[3] ^ iArr6[3];
        iArr9[0] = iArr9[0] ^ iArr8[0];
        iArr9[1] = iArr9[1] ^ iArr8[1];
        iArr9[2] = iArr9[2] ^ iArr8[2];
        iArr9[3] = iArr9[3] ^ iArr8[3];
        int[] c3 = c(iArr7, iArr9);
        iArr5[11] = iArr5[11] ^ c3[7];
        iArr5[10] = iArr5[10] ^ c3[6];
        iArr5[9] = iArr5[9] ^ c3[5];
        iArr5[8] = iArr5[8] ^ c3[4];
        iArr5[7] = iArr5[7] ^ c3[3];
        iArr5[6] = iArr5[6] ^ c3[2];
        iArr5[5] = iArr5[5] ^ c3[1];
        iArr5[4] = c3[0] ^ iArr5[4];
        int[] c4 = c(iArr6, iArr8);
        iArr5[11] = iArr5[11] ^ c4[7];
        iArr5[10] = iArr5[10] ^ c4[6];
        iArr5[9] = iArr5[9] ^ c4[5];
        iArr5[8] = iArr5[8] ^ c4[4];
        iArr5[7] = iArr5[7] ^ (c4[3] ^ c4[7]);
        iArr5[6] = iArr5[6] ^ (c4[2] ^ c4[6]);
        iArr5[5] = iArr5[5] ^ (c4[1] ^ c4[5]);
        iArr5[4] = iArr5[4] ^ (c4[0] ^ c4[4]);
        iArr5[3] = iArr5[3] ^ c4[3];
        iArr5[2] = iArr5[2] ^ c4[2];
        iArr5[1] = iArr5[1] ^ c4[1];
        iArr5[0] = c4[0] ^ iArr5[0];
        return iArr5;
    }

    private void c(int i) {
        if (this.b <= this.c.length) {
            for (int i2 = this.b - 1; i2 >= i; i2--) {
                this.c[i2] = this.c[i2 - i];
            }
            for (int i3 = 0; i3 < i; i3++) {
                this.c[i3] = 0;
            }
            return;
        }
        int[] iArr = new int[this.b];
        System.arraycopy(this.c, 0, iArr, i, this.b - i);
        this.c = null;
        this.c = iArr;
    }

    private static int[] c(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[8];
        int[] iArr4 = new int[2];
        System.arraycopy(iArr, 0, iArr4, 0, Math.min(2, iArr.length));
        int[] iArr5 = new int[2];
        if (iArr.length > 2) {
            System.arraycopy(iArr, 2, iArr5, 0, Math.min(2, iArr.length - 2));
        }
        int[] iArr6 = new int[2];
        System.arraycopy(iArr2, 0, iArr6, 0, Math.min(2, iArr2.length));
        int[] iArr7 = new int[2];
        if (iArr2.length > 2) {
            System.arraycopy(iArr2, 2, iArr7, 0, Math.min(2, iArr2.length - 2));
        }
        if (iArr5[1] != 0 || iArr7[1] != 0) {
            int[] d2 = d(iArr5, iArr7);
            iArr3[7] = iArr3[7] ^ d2[3];
            iArr3[6] = iArr3[6] ^ d2[2];
            iArr3[5] = iArr3[5] ^ (d2[1] ^ d2[3]);
            iArr3[4] = iArr3[4] ^ (d2[0] ^ d2[2]);
            iArr3[3] = iArr3[3] ^ d2[1];
            iArr3[2] = d2[0] ^ iArr3[2];
        } else if (!(iArr5[0] == 0 && iArr7[0] == 0)) {
            int[] b2 = b(iArr5[0], iArr7[0]);
            iArr3[5] = iArr3[5] ^ b2[1];
            iArr3[4] = iArr3[4] ^ b2[0];
            iArr3[3] = iArr3[3] ^ b2[1];
            iArr3[2] = b2[0] ^ iArr3[2];
        }
        iArr5[0] = iArr5[0] ^ iArr4[0];
        iArr5[1] = iArr5[1] ^ iArr4[1];
        iArr7[0] = iArr7[0] ^ iArr6[0];
        iArr7[1] = iArr7[1] ^ iArr6[1];
        if (iArr5[1] == 0 && iArr7[1] == 0) {
            int[] b3 = b(iArr5[0], iArr7[0]);
            iArr3[3] = iArr3[3] ^ b3[1];
            iArr3[2] = b3[0] ^ iArr3[2];
        } else {
            int[] d3 = d(iArr5, iArr7);
            iArr3[5] = iArr3[5] ^ d3[3];
            iArr3[4] = iArr3[4] ^ d3[2];
            iArr3[3] = iArr3[3] ^ d3[1];
            iArr3[2] = d3[0] ^ iArr3[2];
        }
        if (iArr4[1] == 0 && iArr6[1] == 0) {
            int[] b4 = b(iArr4[0], iArr6[0]);
            iArr3[3] = iArr3[3] ^ b4[1];
            iArr3[2] = iArr3[2] ^ b4[0];
            iArr3[1] = iArr3[1] ^ b4[1];
            iArr3[0] = b4[0] ^ iArr3[0];
            return iArr3;
        }
        int[] d4 = d(iArr4, iArr6);
        iArr3[5] = iArr3[5] ^ d4[3];
        iArr3[4] = iArr3[4] ^ d4[2];
        iArr3[3] = iArr3[3] ^ (d4[1] ^ d4[3]);
        iArr3[2] = iArr3[2] ^ (d4[0] ^ d4[2]);
        iArr3[1] = iArr3[1] ^ d4[1];
        iArr3[0] = d4[0] ^ iArr3[0];
        return iArr3;
    }

    private static int[] d(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[4];
        int i = iArr[0];
        int i2 = iArr.length > 1 ? iArr[1] : 0;
        int i3 = iArr2[0];
        int i4 = iArr2.length > 1 ? iArr2[1] : 0;
        if (!(i2 == 0 && i4 == 0)) {
            int[] b2 = b(i2, i4);
            iArr3[3] = iArr3[3] ^ b2[1];
            iArr3[2] = iArr3[2] ^ (b2[0] ^ b2[1]);
            iArr3[1] = b2[0] ^ iArr3[1];
        }
        int[] b3 = b(i2 ^ i, i4 ^ i3);
        iArr3[2] = iArr3[2] ^ b3[1];
        iArr3[1] = b3[0] ^ iArr3[1];
        int[] b4 = b(i, i3);
        iArr3[2] = iArr3[2] ^ b4[1];
        iArr3[1] = iArr3[1] ^ (b4[0] ^ b4[1]);
        iArr3[0] = b4[0] ^ iArr3[0];
        return iArr3;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.b++;
        this.a += 32;
        if (this.b <= this.c.length) {
            for (int i = this.b - 1; i >= 1; i--) {
                this.c[i] = this.c[i - 1];
            }
            this.c[0] = 0;
            return;
        }
        int[] iArr = new int[this.b];
        System.arraycopy(this.c, 0, iArr, 1, this.b - 1);
        this.c = null;
        this.c = iArr;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, int i2) {
        int i3 = i;
        int i4 = i3 >>> 5;
        int i5 = i3 & 31;
        int i6 = 32 - i5;
        int i7 = i3 - i2;
        int i8 = i7 >>> 5;
        int i9 = 32 - (i7 & 31);
        int i10 = ((i3 << 1) - 2) >>> 5;
        while (i10 > i4) {
            long j = ((long) this.c[i10]) & 4294967295L;
            int[] iArr = this.c;
            int i11 = i10 - i4;
            int i12 = i11 - 1;
            iArr[i12] = ((int) (j << i6)) ^ iArr[i12];
            int[] iArr2 = this.c;
            int i13 = i5;
            int i14 = i6;
            iArr2[i11] = (int) (((long) iArr2[i11]) ^ (j >>> (32 - i6)));
            int[] iArr3 = this.c;
            int i15 = i10 - i8;
            int i16 = i15 - 1;
            iArr3[i16] = iArr3[i16] ^ ((int) (j << i9));
            int[] iArr4 = this.c;
            iArr4[i15] = (int) (((long) iArr4[i15]) ^ (j >>> (32 - i9)));
            this.c[i10] = 0;
            i10--;
            i5 = i13;
            i6 = i14;
        }
        int i17 = i5;
        int i18 = i6;
        long j2 = ((long) this.c[i4]) & 4294967295L & (4294967295 << i17);
        int[] iArr5 = this.c;
        iArr5[0] = (int) (((long) iArr5[0]) ^ (j2 >>> (32 - i18)));
        int i19 = i4 - i8;
        int i20 = i19 - 1;
        if (i20 >= 0) {
            int[] iArr6 = this.c;
            iArr6[i20] = iArr6[i20] ^ ((int) (j2 << i9));
        }
        int[] iArr7 = this.c;
        iArr7[i19] = (int) (((long) iArr7[i19]) ^ (j2 >>> (32 - i9)));
        int[] iArr8 = this.c;
        iArr8[i4] = iArr8[i4] & h[i17];
        this.b = ((i3 - 1) >>> 5) + 1;
        this.a = i3;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, int[] iArr) {
        int i2 = i;
        int i3 = i2 >>> 5;
        int i4 = i2 & 31;
        int i5 = 32 - i4;
        int i6 = (i2 - iArr[0]) >>> 5;
        int i7 = 32 - ((i2 - iArr[0]) & 31);
        int i8 = (i2 - iArr[1]) >>> 5;
        int i9 = 32 - ((i2 - iArr[1]) & 31);
        int i10 = (i2 - iArr[2]) >>> 5;
        int i11 = 32 - ((i2 - iArr[2]) & 31);
        int i12 = ((i2 << 1) - 2) >>> 5;
        while (i12 > i3) {
            int i13 = i6;
            long j = ((long) this.c[i12]) & 4294967295L;
            int[] iArr2 = this.c;
            int i14 = i12 - i3;
            int i15 = i14 - 1;
            int i16 = i3;
            int i17 = i4;
            iArr2[i15] = ((int) (j << i5)) ^ iArr2[i15];
            int[] iArr3 = this.c;
            int i18 = i5;
            iArr3[i14] = (int) (((long) iArr3[i14]) ^ (j >>> (32 - i5)));
            int[] iArr4 = this.c;
            int i19 = i12 - i13;
            int i20 = i19 - 1;
            iArr4[i20] = iArr4[i20] ^ ((int) (j << i7));
            int[] iArr5 = this.c;
            int i21 = i7;
            iArr5[i19] = (int) (((long) iArr5[i19]) ^ (j >>> (32 - i7)));
            int[] iArr6 = this.c;
            int i22 = i12 - i8;
            int i23 = i22 - 1;
            iArr6[i23] = iArr6[i23] ^ ((int) (j << i9));
            int[] iArr7 = this.c;
            iArr7[i22] = (int) (((long) iArr7[i22]) ^ (j >>> (32 - i9)));
            int[] iArr8 = this.c;
            int i24 = i12 - i10;
            int i25 = i24 - 1;
            iArr8[i25] = iArr8[i25] ^ ((int) (j << i11));
            int[] iArr9 = this.c;
            iArr9[i24] = (int) (((long) iArr9[i24]) ^ (j >>> (32 - i11)));
            this.c[i12] = 0;
            i12--;
            i6 = i13;
            i3 = i16;
            i4 = i17;
            i5 = i18;
            i7 = i21;
        }
        int i26 = i3;
        int i27 = i4;
        int i28 = i5;
        int i29 = i6;
        int i30 = i7;
        long j2 = ((long) this.c[i26]) & 4294967295L & (4294967295 << i27);
        int[] iArr10 = this.c;
        int i31 = i11;
        iArr10[0] = (int) (((long) iArr10[0]) ^ (j2 >>> (32 - i28)));
        int i32 = i26 - i29;
        int i33 = i32 - 1;
        if (i33 >= 0) {
            int[] iArr11 = this.c;
            iArr11[i33] = iArr11[i33] ^ ((int) (j2 << i30));
        }
        int[] iArr12 = this.c;
        iArr12[i32] = (int) (((long) iArr12[i32]) ^ (j2 >>> (32 - i30)));
        int i34 = i26 - i8;
        int i35 = i34 - 1;
        if (i35 >= 0) {
            int[] iArr13 = this.c;
            iArr13[i35] = iArr13[i35] ^ ((int) (j2 << i9));
        }
        int[] iArr14 = this.c;
        iArr14[i34] = (int) (((long) iArr14[i34]) ^ (j2 >>> (32 - i9)));
        int i36 = i26 - i10;
        int i37 = i36 - 1;
        if (i37 >= 0) {
            int[] iArr15 = this.c;
            iArr15[i37] = iArr15[i37] ^ ((int) (j2 << i31));
        }
        int[] iArr16 = this.c;
        iArr16[i36] = (int) (((long) iArr16[i36]) ^ (j2 >>> (32 - i31)));
        int[] iArr17 = this.c;
        iArr17[i26] = iArr17[i26] & h[i27];
        int i38 = i;
        this.b = ((i38 - 1) >>> 5) + 1;
        this.a = i38;
    }

    public GF2Polynomial add(GF2Polynomial gF2Polynomial) {
        return xor(gF2Polynomial);
    }

    public void addToThis(GF2Polynomial gF2Polynomial) {
        expandN(gF2Polynomial.a);
        xorThisBy(gF2Polynomial);
    }

    public void assignAll() {
        for (int i = 0; i < this.b; i++) {
            this.c[i] = -1;
        }
        b();
    }

    public void assignOne() {
        for (int i = 1; i < this.b; i++) {
            this.c[i] = 0;
        }
        this.c[0] = 1;
    }

    public void assignX() {
        for (int i = 1; i < this.b; i++) {
            this.c[i] = 0;
        }
        this.c[0] = 2;
    }

    public void assignZero() {
        for (int i = 0; i < this.b; i++) {
            this.c[i] = 0;
        }
    }

    public Object clone() {
        return new GF2Polynomial(this);
    }

    public GF2Polynomial[] divide(GF2Polynomial gF2Polynomial) {
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[2];
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.a);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial4 = new GF2Polynomial(gF2Polynomial);
        if (gF2Polynomial4.isZero()) {
            throw new RuntimeException();
        }
        gF2Polynomial3.reduceN();
        gF2Polynomial4.reduceN();
        if (gF2Polynomial3.a < gF2Polynomial4.a) {
            gF2PolynomialArr[0] = new GF2Polynomial(0);
            gF2PolynomialArr[1] = gF2Polynomial3;
            return gF2PolynomialArr;
        }
        int i = gF2Polynomial3.a - gF2Polynomial4.a;
        gF2Polynomial2.expandN(i + 1);
        while (i >= 0) {
            gF2Polynomial3.subtractFromThis(gF2Polynomial4.shiftLeft(i));
            gF2Polynomial3.reduceN();
            gF2Polynomial2.xorBit(i);
            i = gF2Polynomial3.a - gF2Polynomial4.a;
        }
        gF2PolynomialArr[0] = gF2Polynomial2;
        gF2PolynomialArr[1] = gF2Polynomial3;
        return gF2PolynomialArr;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GF2Polynomial)) {
            return false;
        }
        GF2Polynomial gF2Polynomial = (GF2Polynomial) obj;
        if (this.a != gF2Polynomial.a) {
            return false;
        }
        for (int i = 0; i < this.b; i++) {
            if (this.c[i] != gF2Polynomial.c[i]) {
                return false;
            }
        }
        return true;
    }

    public void expandN(int i) {
        if (this.a < i) {
            this.a = i;
            int i2 = ((i - 1) >>> 5) + 1;
            if (this.b < i2) {
                if (this.c.length >= i2) {
                    for (int i3 = this.b; i3 < i2; i3++) {
                        this.c[i3] = 0;
                    }
                    this.b = i2;
                    return;
                }
                int[] iArr = new int[i2];
                System.arraycopy(this.c, 0, iArr, 0, this.b);
                this.b = i2;
                this.c = null;
                this.c = iArr;
            }
        }
    }

    public GF2Polynomial gcd(GF2Polynomial gF2Polynomial) {
        if (isZero() && gF2Polynomial.isZero()) {
            throw new ArithmeticException("Both operands of gcd equal zero.");
        } else if (isZero()) {
            return new GF2Polynomial(gF2Polynomial);
        } else {
            if (gF2Polynomial.isZero()) {
                return new GF2Polynomial(this);
            }
            GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this);
            GF2Polynomial gF2Polynomial3 = new GF2Polynomial(gF2Polynomial);
            GF2Polynomial gF2Polynomial4 = gF2Polynomial2;
            GF2Polynomial gF2Polynomial5 = gF2Polynomial3;
            while (!gF2Polynomial5.isZero()) {
                GF2Polynomial gF2Polynomial6 = gF2Polynomial5;
                gF2Polynomial5 = gF2Polynomial4.remainder(gF2Polynomial5);
                gF2Polynomial4 = gF2Polynomial6;
            }
            return gF2Polynomial4;
        }
    }

    public int getBit(int i) {
        int i2 = 0;
        if (i >= 0) {
            if (i > this.a - 1) {
                return 0;
            }
            if ((g[i & 31] & this.c[i >>> 5]) != 0) {
                i2 = 1;
            }
        }
        return i2;
    }

    public int getLength() {
        return this.a;
    }

    public int hashCode() {
        return this.a + this.c.hashCode();
    }

    public GF2Polynomial increase() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this);
        gF2Polynomial.increaseThis();
        return gF2Polynomial;
    }

    public void increaseThis() {
        xorBit(0);
    }

    public boolean isIrreducible() {
        if (isZero()) {
            return false;
        }
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this);
        gF2Polynomial.reduceN();
        int i = gF2Polynomial.a - 1;
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(gF2Polynomial.a, "X");
        for (int i2 = 1; i2 <= (i >> 1); i2++) {
            gF2Polynomial2.squareThisPreCalc();
            gF2Polynomial2 = gF2Polynomial2.remainder(gF2Polynomial);
            GF2Polynomial add = gF2Polynomial2.add(new GF2Polynomial(32, "X"));
            if (add.isZero() || !gF2Polynomial.gcd(add).isOne()) {
                return false;
            }
        }
        return true;
    }

    public boolean isOne() {
        for (int i = 1; i < this.b; i++) {
            if (this.c[i] != 0) {
                return false;
            }
        }
        return this.c[0] == 1;
    }

    public boolean isZero() {
        if (this.a == 0) {
            return true;
        }
        for (int i = 0; i < this.b; i++) {
            if (this.c[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public GF2Polynomial multiply(GF2Polynomial gF2Polynomial) {
        int max = Math.max(this.a, gF2Polynomial.a);
        expandN(max);
        gF2Polynomial.expandN(max);
        return a(gF2Polynomial);
    }

    public GF2Polynomial multiplyClassic(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(Math.max(this.a, gF2Polynomial.a) << 1);
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[32];
        gF2PolynomialArr[0] = new GF2Polynomial(this);
        for (int i = 1; i <= 31; i++) {
            gF2PolynomialArr[i] = gF2PolynomialArr[i - 1].shiftLeft();
        }
        for (int i2 = 0; i2 < gF2Polynomial.b; i2++) {
            for (int i3 = 0; i3 <= 31; i3++) {
                if ((gF2Polynomial.c[i2] & g[i3]) != 0) {
                    gF2Polynomial2.xorThisBy(gF2PolynomialArr[i3]);
                }
            }
            for (int i4 = 0; i4 <= 31; i4++) {
                gF2PolynomialArr[i4].a();
            }
        }
        return gF2Polynomial2;
    }

    public GF2Polynomial quotient(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.a);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial4 = new GF2Polynomial(gF2Polynomial);
        if (gF2Polynomial4.isZero()) {
            throw new RuntimeException();
        }
        gF2Polynomial3.reduceN();
        gF2Polynomial4.reduceN();
        if (gF2Polynomial3.a < gF2Polynomial4.a) {
            return new GF2Polynomial(0);
        }
        int i = gF2Polynomial3.a - gF2Polynomial4.a;
        gF2Polynomial2.expandN(i + 1);
        while (i >= 0) {
            gF2Polynomial3.subtractFromThis(gF2Polynomial4.shiftLeft(i));
            gF2Polynomial3.reduceN();
            gF2Polynomial2.xorBit(i);
            i = gF2Polynomial3.a - gF2Polynomial4.a;
        }
        return gF2Polynomial2;
    }

    public void randomize() {
        for (int i = 0; i < this.b; i++) {
            this.c[i] = d.nextInt();
        }
        b();
    }

    public void randomize(Random random) {
        for (int i = 0; i < this.b; i++) {
            this.c[i] = random.nextInt();
        }
        b();
    }

    public void reduceN() {
        int i = this.b;
        while (true) {
            i--;
            if (this.c[i] != 0 || i <= 0) {
                int i2 = this.c[i];
                int i3 = 0;
            }
        }
        int i22 = this.c[i];
        int i32 = 0;
        while (i22 != 0) {
            i22 >>>= 1;
            i32++;
        }
        this.a = (i << 5) + i32;
        this.b = i + 1;
    }

    public GF2Polynomial remainder(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this);
        GF2Polynomial gF2Polynomial3 = new GF2Polynomial(gF2Polynomial);
        if (gF2Polynomial3.isZero()) {
            throw new RuntimeException();
        }
        gF2Polynomial2.reduceN();
        gF2Polynomial3.reduceN();
        if (gF2Polynomial2.a < gF2Polynomial3.a) {
            return gF2Polynomial2;
        }
        while (true) {
            int i = gF2Polynomial2.a - gF2Polynomial3.a;
            if (i < 0) {
                return gF2Polynomial2;
            }
            gF2Polynomial2.subtractFromThis(gF2Polynomial3.shiftLeft(i));
            gF2Polynomial2.reduceN();
        }
    }

    public void resetBit(int i) {
        if (i < 0 || i > this.a - 1) {
            throw new RuntimeException();
        } else if (i <= this.a - 1) {
            int[] iArr = this.c;
            int i2 = i >>> 5;
            iArr[i2] = (g[i & 31] ^ -1) & iArr[i2];
        }
    }

    public void setBit(int i) {
        if (i < 0 || i > this.a - 1) {
            throw new RuntimeException();
        } else if (i <= this.a - 1) {
            int[] iArr = this.c;
            int i2 = i >>> 5;
            iArr[i2] = g[i & 31] | iArr[i2];
        }
    }

    public GF2Polynomial shiftLeft() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.a + 1, this.c);
        for (int i = gF2Polynomial.b - 1; i >= 1; i--) {
            int[] iArr = gF2Polynomial.c;
            iArr[i] = iArr[i] << 1;
            int[] iArr2 = gF2Polynomial.c;
            iArr2[i] = iArr2[i] | (gF2Polynomial.c[i - 1] >>> 31);
        }
        int[] iArr3 = gF2Polynomial.c;
        iArr3[0] = iArr3[0] << 1;
        return gF2Polynomial;
    }

    public GF2Polynomial shiftLeft(int i) {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.a + i, this.c);
        if (i >= 32) {
            gF2Polynomial.c(i >>> 5);
        }
        int i2 = i & 31;
        if (i2 != 0) {
            for (int i3 = gF2Polynomial.b - 1; i3 >= 1; i3--) {
                int[] iArr = gF2Polynomial.c;
                iArr[i3] = iArr[i3] << i2;
                int[] iArr2 = gF2Polynomial.c;
                iArr2[i3] = iArr2[i3] | (gF2Polynomial.c[i3 - 1] >>> (32 - i2));
            }
            int[] iArr3 = gF2Polynomial.c;
            iArr3[0] = iArr3[0] << i2;
        }
        return gF2Polynomial;
    }

    public void shiftLeftAddThis(GF2Polynomial gF2Polynomial, int i) {
        if (i == 0) {
            addToThis(gF2Polynomial);
            return;
        }
        expandN(gF2Polynomial.a + i);
        int i2 = i >>> 5;
        for (int i3 = gF2Polynomial.b - 1; i3 >= 0; i3--) {
            int i4 = i3 + i2;
            int i5 = i4 + 1;
            if (i5 < this.b) {
                int i6 = i & 31;
                if (i6 != 0) {
                    int[] iArr = this.c;
                    iArr[i5] = (gF2Polynomial.c[i3] >>> (32 - i6)) ^ iArr[i5];
                }
            }
            int[] iArr2 = this.c;
            iArr2[i4] = iArr2[i4] ^ (gF2Polynomial.c[i3] << (i & 31));
        }
    }

    public void shiftLeftThis() {
        if ((this.a & 31) == 0) {
            this.a++;
            this.b++;
            if (this.b > this.c.length) {
                int[] iArr = new int[this.b];
                System.arraycopy(this.c, 0, iArr, 0, this.c.length);
                this.c = null;
                this.c = iArr;
            }
            for (int i = this.b - 1; i >= 1; i--) {
                int[] iArr2 = this.c;
                int i2 = i - 1;
                iArr2[i] = iArr2[i] | (this.c[i2] >>> 31);
                int[] iArr3 = this.c;
                iArr3[i2] = iArr3[i2] << 1;
            }
            return;
        }
        this.a++;
        for (int i3 = this.b - 1; i3 >= 1; i3--) {
            int[] iArr4 = this.c;
            iArr4[i3] = iArr4[i3] << 1;
            int[] iArr5 = this.c;
            iArr5[i3] = iArr5[i3] | (this.c[i3 - 1] >>> 31);
        }
        int[] iArr6 = this.c;
        iArr6[0] = iArr6[0] << 1;
    }

    public GF2Polynomial shiftRight() {
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.a - 1);
        int i = 0;
        System.arraycopy(this.c, 0, gF2Polynomial.c, 0, gF2Polynomial.b);
        while (i <= gF2Polynomial.b - 2) {
            int[] iArr = gF2Polynomial.c;
            iArr[i] = iArr[i] >>> 1;
            int[] iArr2 = gF2Polynomial.c;
            int i2 = i + 1;
            iArr2[i] = iArr2[i] | (gF2Polynomial.c[i2] << 31);
            i = i2;
        }
        int[] iArr3 = gF2Polynomial.c;
        int i3 = gF2Polynomial.b - 1;
        iArr3[i3] = iArr3[i3] >>> 1;
        if (gF2Polynomial.b < this.b) {
            int[] iArr4 = gF2Polynomial.c;
            int i4 = gF2Polynomial.b - 1;
            iArr4[i4] = iArr4[i4] | (this.c[gF2Polynomial.b] << 31);
        }
        return gF2Polynomial;
    }

    public void shiftRightThis() {
        this.a--;
        this.b = ((this.a - 1) >>> 5) + 1;
        int i = 0;
        while (i <= this.b - 2) {
            int[] iArr = this.c;
            iArr[i] = iArr[i] >>> 1;
            int[] iArr2 = this.c;
            int i2 = i + 1;
            iArr2[i] = iArr2[i] | (this.c[i2] << 31);
            i = i2;
        }
        int[] iArr3 = this.c;
        int i3 = this.b - 1;
        iArr3[i3] = iArr3[i3] >>> 1;
        if ((this.a & 31) == 0) {
            int[] iArr4 = this.c;
            int i4 = this.b - 1;
            iArr4[i4] = iArr4[i4] | (this.c[this.b] << 31);
        }
    }

    public void squareThisBitwise() {
        if (!isZero()) {
            int[] iArr = new int[(this.b << 1)];
            for (int i = this.b - 1; i >= 0; i--) {
                int i2 = this.c[i];
                int i3 = 1;
                for (int i4 = 0; i4 < 16; i4++) {
                    if ((i2 & 1) != 0) {
                        int i5 = i << 1;
                        iArr[i5] = iArr[i5] | i3;
                    }
                    if ((65536 & i2) != 0) {
                        int i6 = (i << 1) + 1;
                        iArr[i6] = iArr[i6] | i3;
                    }
                    i3 <<= 2;
                    i2 >>>= 1;
                }
            }
            this.c = null;
            this.c = iArr;
            this.b = iArr.length;
            this.a = (this.a << 1) - 1;
        }
    }

    public void squareThisPreCalc() {
        if (!isZero()) {
            if (this.c.length >= (this.b << 1)) {
                for (int i = this.b - 1; i >= 0; i--) {
                    int i2 = i << 1;
                    this.c[i2 + 1] = f[(this.c[i] & 16711680) >>> 16] | (f[(this.c[i] & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16);
                    this.c[i2] = f[this.c[i] & 255] | (f[(this.c[i] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8] << 16);
                }
            } else {
                int[] iArr = new int[(this.b << 1)];
                for (int i3 = 0; i3 < this.b; i3++) {
                    int i4 = i3 << 1;
                    iArr[i4] = f[this.c[i3] & 255] | (f[(this.c[i3] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8] << 16);
                    iArr[i4 + 1] = f[(this.c[i3] & 16711680) >>> 16] | (f[(this.c[i3] & ViewCompat.MEASURED_STATE_MASK) >>> 24] << 16);
                }
                this.c = null;
                this.c = iArr;
            }
            this.b <<= 1;
            this.a = (this.a << 1) - 1;
        }
    }

    public GF2Polynomial subtract(GF2Polynomial gF2Polynomial) {
        return xor(gF2Polynomial);
    }

    public void subtractFromThis(GF2Polynomial gF2Polynomial) {
        expandN(gF2Polynomial.a);
        xorThisBy(gF2Polynomial);
    }

    public boolean testBit(int i) {
        boolean z = false;
        if (i >= 0) {
            if (i > this.a - 1) {
                return false;
            }
            if ((g[i & 31] & this.c[i >>> 5]) != 0) {
                z = true;
            }
        }
        return z;
    }

    public byte[] toByteArray() {
        int i = ((this.a - 1) >> 3) + 1;
        int i2 = i & 3;
        byte[] bArr = new byte[i];
        for (int i3 = 0; i3 < (i >> 2); i3++) {
            int i4 = (i - (i3 << 2)) - 1;
            bArr[i4] = (byte) (255 & this.c[i3]);
            bArr[i4 - 1] = (byte) ((this.c[i3] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8);
            bArr[i4 - 2] = (byte) ((this.c[i3] & 16711680) >>> 16);
            bArr[i4 - 3] = (byte) ((this.c[i3] & ViewCompat.MEASURED_STATE_MASK) >>> 24);
        }
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = ((i2 - i5) - 1) << 3;
            bArr[i5] = (byte) ((this.c[this.b - 1] & (255 << i6)) >>> i6);
        }
        return bArr;
    }

    public BigInteger toFlexiBigInt() {
        return (this.a == 0 || isZero()) ? new BigInteger(0, new byte[0]) : new BigInteger(1, toByteArray());
    }

    public int[] toIntegerArray() {
        int[] iArr = new int[this.b];
        System.arraycopy(this.c, 0, iArr, 0, this.b);
        return iArr;
    }

    public String toString(int i) {
        char[] cArr = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        String[] strArr = {Constants.FORMAT_ABREV_SUC_ACCOUNT, "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
        String str = new String();
        if (i == 16) {
            for (int i2 = this.b - 1; i2 >= 0; i2--) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(cArr[(this.c[i2] >>> 28) & 15]);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(cArr[(this.c[i2] >>> 24) & 15]);
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(sb4);
                sb5.append(cArr[(this.c[i2] >>> 20) & 15]);
                String sb6 = sb5.toString();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(sb6);
                sb7.append(cArr[(this.c[i2] >>> 16) & 15]);
                String sb8 = sb7.toString();
                StringBuilder sb9 = new StringBuilder();
                sb9.append(sb8);
                sb9.append(cArr[(this.c[i2] >>> 12) & 15]);
                String sb10 = sb9.toString();
                StringBuilder sb11 = new StringBuilder();
                sb11.append(sb10);
                sb11.append(cArr[(this.c[i2] >>> 8) & 15]);
                String sb12 = sb11.toString();
                StringBuilder sb13 = new StringBuilder();
                sb13.append(sb12);
                sb13.append(cArr[(this.c[i2] >>> 4) & 15]);
                String sb14 = sb13.toString();
                StringBuilder sb15 = new StringBuilder();
                sb15.append(sb14);
                sb15.append(cArr[this.c[i2] & 15]);
                String sb16 = sb15.toString();
                StringBuilder sb17 = new StringBuilder();
                sb17.append(sb16);
                sb17.append(UtilsCuentas.SEPARAOR2);
                str = sb17.toString();
            }
        } else {
            for (int i3 = this.b - 1; i3 >= 0; i3--) {
                StringBuilder sb18 = new StringBuilder();
                sb18.append(str);
                sb18.append(strArr[(this.c[i3] >>> 28) & 15]);
                String sb19 = sb18.toString();
                StringBuilder sb20 = new StringBuilder();
                sb20.append(sb19);
                sb20.append(strArr[(this.c[i3] >>> 24) & 15]);
                String sb21 = sb20.toString();
                StringBuilder sb22 = new StringBuilder();
                sb22.append(sb21);
                sb22.append(strArr[(this.c[i3] >>> 20) & 15]);
                String sb23 = sb22.toString();
                StringBuilder sb24 = new StringBuilder();
                sb24.append(sb23);
                sb24.append(strArr[(this.c[i3] >>> 16) & 15]);
                String sb25 = sb24.toString();
                StringBuilder sb26 = new StringBuilder();
                sb26.append(sb25);
                sb26.append(strArr[(this.c[i3] >>> 12) & 15]);
                String sb27 = sb26.toString();
                StringBuilder sb28 = new StringBuilder();
                sb28.append(sb27);
                sb28.append(strArr[(this.c[i3] >>> 8) & 15]);
                String sb29 = sb28.toString();
                StringBuilder sb30 = new StringBuilder();
                sb30.append(sb29);
                sb30.append(strArr[(this.c[i3] >>> 4) & 15]);
                String sb31 = sb30.toString();
                StringBuilder sb32 = new StringBuilder();
                sb32.append(sb31);
                sb32.append(strArr[this.c[i3] & 15]);
                String sb33 = sb32.toString();
                StringBuilder sb34 = new StringBuilder();
                sb34.append(sb33);
                sb34.append(UtilsCuentas.SEPARAOR2);
                str = sb34.toString();
            }
        }
        return str;
    }

    public boolean vectorMult(GF2Polynomial gF2Polynomial) {
        if (this.a != gF2Polynomial.a) {
            throw new RuntimeException();
        }
        boolean z = false;
        for (int i = 0; i < this.b; i++) {
            int i2 = this.c[i] & gF2Polynomial.c[i];
            z = (((z ^ e[i2 & 255]) ^ e[(i2 >>> 8) & 255]) ^ e[(i2 >>> 16) & 255]) ^ e[(i2 >>> 24) & 255];
        }
        return z;
    }

    public GF2Polynomial xor(GF2Polynomial gF2Polynomial) {
        GF2Polynomial gF2Polynomial2;
        int min = Math.min(this.b, gF2Polynomial.b);
        int i = 0;
        if (this.a >= gF2Polynomial.a) {
            gF2Polynomial2 = new GF2Polynomial(this);
            while (i < min) {
                int[] iArr = gF2Polynomial2.c;
                iArr[i] = iArr[i] ^ gF2Polynomial.c[i];
                i++;
            }
        } else {
            gF2Polynomial2 = new GF2Polynomial(gF2Polynomial);
            while (i < min) {
                int[] iArr2 = gF2Polynomial2.c;
                iArr2[i] = iArr2[i] ^ this.c[i];
                i++;
            }
        }
        gF2Polynomial2.b();
        return gF2Polynomial2;
    }

    public void xorBit(int i) {
        if (i < 0 || i > this.a - 1) {
            throw new RuntimeException();
        } else if (i <= this.a - 1) {
            int[] iArr = this.c;
            int i2 = i >>> 5;
            iArr[i2] = g[i & 31] ^ iArr[i2];
        }
    }

    public void xorThisBy(GF2Polynomial gF2Polynomial) {
        for (int i = 0; i < Math.min(this.b, gF2Polynomial.b); i++) {
            int[] iArr = this.c;
            iArr[i] = iArr[i] ^ gF2Polynomial.c[i];
        }
        b();
    }
}
