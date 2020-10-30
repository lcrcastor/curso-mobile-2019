package org.bouncycastle.crypto.engines;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.tls.CipherSuite;

public class SerpentEngine implements BlockCipher {
    private boolean a;
    private int[] b;
    private int c;
    private int d;
    private int e;
    private int f;

    private int a(int i, int i2) {
        return (i >>> (-i2)) | (i << i2);
    }

    private int a(byte[] bArr, int i) {
        return (bArr[i + 3] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << 8);
    }

    private void a() {
        int a2 = a(this.c, 13);
        int a3 = a(this.e, 3);
        int i = (this.f ^ a3) ^ (a2 << 3);
        this.d = a((this.d ^ a2) ^ a3, 1);
        this.f = a(i, 7);
        this.c = a((a2 ^ this.d) ^ this.f, 5);
        this.e = a((this.f ^ a3) ^ (this.d << 7), 22);
    }

    private void a(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i3 ^ i5;
        int i7 = i2 ^ i6;
        this.f = (i4 & i) ^ i7;
        int i8 = i ^ (i2 & i5);
        this.e = (i3 | i8) ^ i7;
        int i9 = this.f & (i6 ^ i8);
        this.d = (i6 ^ -1) ^ i9;
        this.c = (i8 ^ -1) ^ i9;
    }

    private void a(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) i;
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2] = (byte) (i >>> 24);
    }

    private void a(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.f = a(bArr, i);
        this.e = a(bArr, i + 4);
        this.d = a(bArr, i + 8);
        this.c = a(bArr, i + 12);
        a(this.b[0] ^ this.c, this.b[1] ^ this.d, this.b[2] ^ this.e, this.b[3] ^ this.f);
        a();
        c(this.b[4] ^ this.c, this.b[5] ^ this.d, this.b[6] ^ this.e, this.b[7] ^ this.f);
        a();
        e(this.b[8] ^ this.c, this.b[9] ^ this.d, this.b[10] ^ this.e, this.b[11] ^ this.f);
        a();
        g(this.b[12] ^ this.c, this.b[13] ^ this.d, this.b[14] ^ this.e, this.b[15] ^ this.f);
        a();
        i(this.b[16] ^ this.c, this.b[17] ^ this.d, this.b[18] ^ this.e, this.b[19] ^ this.f);
        a();
        k(this.b[20] ^ this.c, this.b[21] ^ this.d, this.b[22] ^ this.e, this.b[23] ^ this.f);
        a();
        m(this.b[24] ^ this.c, this.b[25] ^ this.d, this.b[26] ^ this.e, this.b[27] ^ this.f);
        a();
        o(this.b[28] ^ this.c, this.b[29] ^ this.d, this.b[30] ^ this.e, this.b[31] ^ this.f);
        a();
        a(this.b[32] ^ this.c, this.b[33] ^ this.d, this.b[34] ^ this.e, this.b[35] ^ this.f);
        a();
        c(this.b[36] ^ this.c, this.b[37] ^ this.d, this.b[38] ^ this.e, this.b[39] ^ this.f);
        a();
        e(this.b[40] ^ this.c, this.b[41] ^ this.d, this.b[42] ^ this.e, this.b[43] ^ this.f);
        a();
        g(this.b[44] ^ this.c, this.b[45] ^ this.d, this.b[46] ^ this.e, this.b[47] ^ this.f);
        a();
        i(this.b[48] ^ this.c, this.b[49] ^ this.d, this.b[50] ^ this.e, this.b[51] ^ this.f);
        a();
        k(this.b[52] ^ this.c, this.b[53] ^ this.d, this.b[54] ^ this.e, this.b[55] ^ this.f);
        a();
        m(this.b[56] ^ this.c, this.b[57] ^ this.d, this.b[58] ^ this.e, this.b[59] ^ this.f);
        a();
        o(this.b[60] ^ this.c, this.b[61] ^ this.d, this.b[62] ^ this.e, this.b[63] ^ this.f);
        a();
        a(this.b[64] ^ this.c, this.b[65] ^ this.d, this.b[66] ^ this.e, this.b[67] ^ this.f);
        a();
        c(this.b[68] ^ this.c, this.b[69] ^ this.d, this.b[70] ^ this.e, this.b[71] ^ this.f);
        a();
        e(this.b[72] ^ this.c, this.b[73] ^ this.d, this.b[74] ^ this.e, this.b[75] ^ this.f);
        a();
        g(this.b[76] ^ this.c, this.b[77] ^ this.d, this.b[78] ^ this.e, this.b[79] ^ this.f);
        a();
        i(this.b[80] ^ this.c, this.b[81] ^ this.d, this.b[82] ^ this.e, this.b[83] ^ this.f);
        a();
        k(this.b[84] ^ this.c, this.b[85] ^ this.d, this.b[86] ^ this.e, this.b[87] ^ this.f);
        a();
        m(this.b[88] ^ this.c, this.b[89] ^ this.d, this.b[90] ^ this.e, this.b[91] ^ this.f);
        a();
        o(this.b[92] ^ this.c, this.b[93] ^ this.d, this.b[94] ^ this.e, this.b[95] ^ this.f);
        a();
        a(this.b[96] ^ this.c, this.b[97] ^ this.d, this.b[98] ^ this.e, this.b[99] ^ this.f);
        a();
        c(this.b[100] ^ this.c, this.b[101] ^ this.d, this.b[102] ^ this.e, this.b[103] ^ this.f);
        a();
        e(this.b[104] ^ this.c, this.b[105] ^ this.d, this.b[106] ^ this.e, this.b[107] ^ this.f);
        a();
        g(this.b[108] ^ this.c, this.b[109] ^ this.d, this.b[110] ^ this.e, this.b[111] ^ this.f);
        a();
        i(this.b[112] ^ this.c, this.b[113] ^ this.d, this.b[114] ^ this.e, this.b[115] ^ this.f);
        a();
        k(this.b[116] ^ this.c, this.b[117] ^ this.d, this.b[118] ^ this.e, this.b[119] ^ this.f);
        a();
        m(this.b[120] ^ this.c, this.b[121] ^ this.d, this.b[122] ^ this.e, this.b[123] ^ this.f);
        a();
        o(this.b[124] ^ this.c, this.b[125] ^ this.d, this.b[126] ^ this.e, this.b[127] ^ this.f);
        a(this.b[131] ^ this.f, bArr2, i2);
        a(this.b[130] ^ this.e, bArr2, i2 + 4);
        a(this.b[129] ^ this.d, bArr2, i2 + 8);
        a(this.b[128] ^ this.c, bArr2, i2 + 12);
    }

    private int[] a(byte[] bArr) {
        int[] iArr = new int[16];
        int length = bArr.length - 4;
        int i = 0;
        while (length > 0) {
            int i2 = i + 1;
            iArr[i] = a(bArr, length);
            length -= 4;
            i = i2;
        }
        if (length == 0) {
            int i3 = i + 1;
            iArr[i] = a(bArr, 0);
            if (i3 < 8) {
                iArr[i3] = 1;
            }
            int[] iArr2 = new int[CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA];
            for (int i4 = 8; i4 < 16; i4++) {
                int i5 = i4 - 8;
                iArr[i4] = a((-1640531527 ^ (((iArr[i5] ^ iArr[i4 - 5]) ^ iArr[i4 - 3]) ^ iArr[i4 - 1])) ^ i5, 11);
            }
            System.arraycopy(iArr, 8, iArr2, 0, 8);
            for (int i6 = 8; i6 < 132; i6++) {
                iArr2[i6] = a(((((iArr2[i6 - 8] ^ iArr2[i6 - 5]) ^ iArr2[i6 - 3]) ^ iArr2[i6 - 1]) ^ -1640531527) ^ i6, 11);
            }
            g(iArr2[0], iArr2[1], iArr2[2], iArr2[3]);
            iArr2[0] = this.c;
            iArr2[1] = this.d;
            iArr2[2] = this.e;
            iArr2[3] = this.f;
            e(iArr2[4], iArr2[5], iArr2[6], iArr2[7]);
            iArr2[4] = this.c;
            iArr2[5] = this.d;
            iArr2[6] = this.e;
            iArr2[7] = this.f;
            c(iArr2[8], iArr2[9], iArr2[10], iArr2[11]);
            iArr2[8] = this.c;
            iArr2[9] = this.d;
            iArr2[10] = this.e;
            iArr2[11] = this.f;
            a(iArr2[12], iArr2[13], iArr2[14], iArr2[15]);
            iArr2[12] = this.c;
            iArr2[13] = this.d;
            iArr2[14] = this.e;
            iArr2[15] = this.f;
            o(iArr2[16], iArr2[17], iArr2[18], iArr2[19]);
            iArr2[16] = this.c;
            iArr2[17] = this.d;
            iArr2[18] = this.e;
            iArr2[19] = this.f;
            m(iArr2[20], iArr2[21], iArr2[22], iArr2[23]);
            iArr2[20] = this.c;
            iArr2[21] = this.d;
            iArr2[22] = this.e;
            iArr2[23] = this.f;
            k(iArr2[24], iArr2[25], iArr2[26], iArr2[27]);
            iArr2[24] = this.c;
            iArr2[25] = this.d;
            iArr2[26] = this.e;
            iArr2[27] = this.f;
            i(iArr2[28], iArr2[29], iArr2[30], iArr2[31]);
            iArr2[28] = this.c;
            iArr2[29] = this.d;
            iArr2[30] = this.e;
            iArr2[31] = this.f;
            g(iArr2[32], iArr2[33], iArr2[34], iArr2[35]);
            iArr2[32] = this.c;
            iArr2[33] = this.d;
            iArr2[34] = this.e;
            iArr2[35] = this.f;
            e(iArr2[36], iArr2[37], iArr2[38], iArr2[39]);
            iArr2[36] = this.c;
            iArr2[37] = this.d;
            iArr2[38] = this.e;
            iArr2[39] = this.f;
            c(iArr2[40], iArr2[41], iArr2[42], iArr2[43]);
            iArr2[40] = this.c;
            iArr2[41] = this.d;
            iArr2[42] = this.e;
            iArr2[43] = this.f;
            a(iArr2[44], iArr2[45], iArr2[46], iArr2[47]);
            iArr2[44] = this.c;
            iArr2[45] = this.d;
            iArr2[46] = this.e;
            iArr2[47] = this.f;
            o(iArr2[48], iArr2[49], iArr2[50], iArr2[51]);
            iArr2[48] = this.c;
            iArr2[49] = this.d;
            iArr2[50] = this.e;
            iArr2[51] = this.f;
            m(iArr2[52], iArr2[53], iArr2[54], iArr2[55]);
            iArr2[52] = this.c;
            iArr2[53] = this.d;
            iArr2[54] = this.e;
            iArr2[55] = this.f;
            k(iArr2[56], iArr2[57], iArr2[58], iArr2[59]);
            iArr2[56] = this.c;
            iArr2[57] = this.d;
            iArr2[58] = this.e;
            iArr2[59] = this.f;
            i(iArr2[60], iArr2[61], iArr2[62], iArr2[63]);
            iArr2[60] = this.c;
            iArr2[61] = this.d;
            iArr2[62] = this.e;
            iArr2[63] = this.f;
            g(iArr2[64], iArr2[65], iArr2[66], iArr2[67]);
            iArr2[64] = this.c;
            iArr2[65] = this.d;
            iArr2[66] = this.e;
            iArr2[67] = this.f;
            e(iArr2[68], iArr2[69], iArr2[70], iArr2[71]);
            iArr2[68] = this.c;
            iArr2[69] = this.d;
            iArr2[70] = this.e;
            iArr2[71] = this.f;
            c(iArr2[72], iArr2[73], iArr2[74], iArr2[75]);
            iArr2[72] = this.c;
            iArr2[73] = this.d;
            iArr2[74] = this.e;
            iArr2[75] = this.f;
            a(iArr2[76], iArr2[77], iArr2[78], iArr2[79]);
            iArr2[76] = this.c;
            iArr2[77] = this.d;
            iArr2[78] = this.e;
            iArr2[79] = this.f;
            o(iArr2[80], iArr2[81], iArr2[82], iArr2[83]);
            iArr2[80] = this.c;
            iArr2[81] = this.d;
            iArr2[82] = this.e;
            iArr2[83] = this.f;
            m(iArr2[84], iArr2[85], iArr2[86], iArr2[87]);
            iArr2[84] = this.c;
            iArr2[85] = this.d;
            iArr2[86] = this.e;
            iArr2[87] = this.f;
            k(iArr2[88], iArr2[89], iArr2[90], iArr2[91]);
            iArr2[88] = this.c;
            iArr2[89] = this.d;
            iArr2[90] = this.e;
            iArr2[91] = this.f;
            i(iArr2[92], iArr2[93], iArr2[94], iArr2[95]);
            iArr2[92] = this.c;
            iArr2[93] = this.d;
            iArr2[94] = this.e;
            iArr2[95] = this.f;
            g(iArr2[96], iArr2[97], iArr2[98], iArr2[99]);
            iArr2[96] = this.c;
            iArr2[97] = this.d;
            iArr2[98] = this.e;
            iArr2[99] = this.f;
            e(iArr2[100], iArr2[101], iArr2[102], iArr2[103]);
            iArr2[100] = this.c;
            iArr2[101] = this.d;
            iArr2[102] = this.e;
            iArr2[103] = this.f;
            c(iArr2[104], iArr2[105], iArr2[106], iArr2[107]);
            iArr2[104] = this.c;
            iArr2[105] = this.d;
            iArr2[106] = this.e;
            iArr2[107] = this.f;
            a(iArr2[108], iArr2[109], iArr2[110], iArr2[111]);
            iArr2[108] = this.c;
            iArr2[109] = this.d;
            iArr2[110] = this.e;
            iArr2[111] = this.f;
            o(iArr2[112], iArr2[113], iArr2[114], iArr2[115]);
            iArr2[112] = this.c;
            iArr2[113] = this.d;
            iArr2[114] = this.e;
            iArr2[115] = this.f;
            m(iArr2[116], iArr2[117], iArr2[118], iArr2[119]);
            iArr2[116] = this.c;
            iArr2[117] = this.d;
            iArr2[118] = this.e;
            iArr2[119] = this.f;
            k(iArr2[120], iArr2[121], iArr2[122], iArr2[123]);
            iArr2[120] = this.c;
            iArr2[121] = this.d;
            iArr2[122] = this.e;
            iArr2[123] = this.f;
            i(iArr2[124], iArr2[125], iArr2[126], iArr2[127]);
            iArr2[124] = this.c;
            iArr2[125] = this.d;
            iArr2[126] = this.e;
            iArr2[127] = this.f;
            g(iArr2[128], iArr2[129], iArr2[130], iArr2[131]);
            iArr2[128] = this.c;
            iArr2[129] = this.d;
            iArr2[130] = this.e;
            iArr2[131] = this.f;
            return iArr2;
        }
        throw new IllegalArgumentException("key must be a multiple of 4 bytes");
    }

    private int b(int i, int i2) {
        return (i << (-i2)) | (i >>> i2);
    }

    private void b() {
        int b2 = (b(this.e, 22) ^ this.f) ^ (this.d << 7);
        int b3 = (b(this.c, 5) ^ this.d) ^ this.f;
        int b4 = b(this.f, 7);
        int b5 = b(this.d, 1);
        this.f = (b4 ^ b2) ^ (b3 << 3);
        this.d = (b5 ^ b3) ^ b2;
        this.e = b(b2, 3);
        this.c = b(b3, 13);
    }

    private void b(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i2 ^ i;
        int i7 = (i5 | i6) ^ i4;
        int i8 = i3 ^ i7;
        this.e = i6 ^ i8;
        int i9 = (i6 & i4) ^ i5;
        this.d = (this.e & i9) ^ i7;
        this.f = (i & i7) ^ (this.d | i8);
        this.c = this.f ^ (i9 ^ i8);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [org.bouncycastle.crypto.engines.SerpentEngine] */
    private void b(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.f = this.b[131] ^ a(bArr, i);
        this.e = this.b[130] ^ a(bArr, i + 4);
        this.d = this.b[129] ^ a(bArr, i + 8);
        this.c = a(bArr, i + 12) ^ this.b[128];
        p(this.c, this.d, this.e, this.f);
        this.c ^= this.b[124];
        this.d ^= this.b[125];
        this.e ^= this.b[126];
        this.f ^= this.b[127];
        b();
        n(this.c, this.d, this.e, this.f);
        this.c ^= this.b[120];
        this.d ^= this.b[121];
        this.e ^= this.b[122];
        this.f ^= this.b[123];
        b();
        l(this.c, this.d, this.e, this.f);
        this.c ^= this.b[116];
        this.d ^= this.b[117];
        this.e ^= this.b[118];
        this.f ^= this.b[119];
        b();
        j(this.c, this.d, this.e, this.f);
        this.c ^= this.b[112];
        this.d ^= this.b[113];
        this.e ^= this.b[114];
        this.f ^= this.b[115];
        b();
        h(this.c, this.d, this.e, this.f);
        this.c ^= this.b[108];
        this.d ^= this.b[109];
        this.e ^= this.b[110];
        this.f ^= this.b[111];
        b();
        f(this.c, this.d, this.e, this.f);
        this.c ^= this.b[104];
        this.d ^= this.b[105];
        this.e ^= this.b[106];
        this.f ^= this.b[107];
        b();
        d(this.c, this.d, this.e, this.f);
        this.c ^= this.b[100];
        this.d ^= this.b[101];
        this.e ^= this.b[102];
        this.f ^= this.b[103];
        b();
        b(this.c, this.d, this.e, this.f);
        this.c ^= this.b[96];
        this.d ^= this.b[97];
        this.e ^= this.b[98];
        this.f ^= this.b[99];
        b();
        p(this.c, this.d, this.e, this.f);
        this.c ^= this.b[92];
        this.d ^= this.b[93];
        this.e ^= this.b[94];
        this.f ^= this.b[95];
        b();
        n(this.c, this.d, this.e, this.f);
        this.c ^= this.b[88];
        this.d ^= this.b[89];
        this.e ^= this.b[90];
        this.f ^= this.b[91];
        b();
        l(this.c, this.d, this.e, this.f);
        this.c ^= this.b[84];
        this.d ^= this.b[85];
        this.e ^= this.b[86];
        this.f ^= this.b[87];
        b();
        j(this.c, this.d, this.e, this.f);
        this.c ^= this.b[80];
        this.d ^= this.b[81];
        this.e ^= this.b[82];
        this.f ^= this.b[83];
        b();
        h(this.c, this.d, this.e, this.f);
        this.c ^= this.b[76];
        this.d ^= this.b[77];
        this.e ^= this.b[78];
        this.f ^= this.b[79];
        b();
        f(this.c, this.d, this.e, this.f);
        this.c ^= this.b[72];
        this.d ^= this.b[73];
        this.e ^= this.b[74];
        this.f ^= this.b[75];
        b();
        d(this.c, this.d, this.e, this.f);
        this.c ^= this.b[68];
        this.d ^= this.b[69];
        this.e ^= this.b[70];
        this.f ^= this.b[71];
        b();
        b(this.c, this.d, this.e, this.f);
        this.c ^= this.b[64];
        this.d ^= this.b[65];
        this.e ^= this.b[66];
        this.f ^= this.b[67];
        b();
        p(this.c, this.d, this.e, this.f);
        this.c ^= this.b[60];
        this.d ^= this.b[61];
        this.e ^= this.b[62];
        this.f ^= this.b[63];
        b();
        n(this.c, this.d, this.e, this.f);
        this.c ^= this.b[56];
        this.d ^= this.b[57];
        this.e ^= this.b[58];
        this.f ^= this.b[59];
        b();
        l(this.c, this.d, this.e, this.f);
        this.c ^= this.b[52];
        this.d ^= this.b[53];
        this.e ^= this.b[54];
        this.f ^= this.b[55];
        b();
        j(this.c, this.d, this.e, this.f);
        this.c ^= this.b[48];
        this.d ^= this.b[49];
        this.e ^= this.b[50];
        this.f ^= this.b[51];
        b();
        h(this.c, this.d, this.e, this.f);
        this.c ^= this.b[44];
        this.d ^= this.b[45];
        this.e ^= this.b[46];
        this.f ^= this.b[47];
        b();
        f(this.c, this.d, this.e, this.f);
        this.c ^= this.b[40];
        this.d ^= this.b[41];
        this.e ^= this.b[42];
        this.f ^= this.b[43];
        b();
        d(this.c, this.d, this.e, this.f);
        this.c ^= this.b[36];
        this.d ^= this.b[37];
        this.e ^= this.b[38];
        this.f ^= this.b[39];
        b();
        b(this.c, this.d, this.e, this.f);
        this.c ^= this.b[32];
        this.d ^= this.b[33];
        this.e ^= this.b[34];
        this.f ^= this.b[35];
        b();
        p(this.c, this.d, this.e, this.f);
        this.c ^= this.b[28];
        this.d ^= this.b[29];
        this.e ^= this.b[30];
        this.f ^= this.b[31];
        b();
        n(this.c, this.d, this.e, this.f);
        this.c ^= this.b[24];
        this.d ^= this.b[25];
        this.e ^= this.b[26];
        this.f ^= this.b[27];
        b();
        l(this.c, this.d, this.e, this.f);
        this.c ^= this.b[20];
        this.d ^= this.b[21];
        this.e ^= this.b[22];
        this.f ^= this.b[23];
        b();
        j(this.c, this.d, this.e, this.f);
        this.c ^= this.b[16];
        this.d ^= this.b[17];
        this.e ^= this.b[18];
        this.f ^= this.b[19];
        b();
        h(this.c, this.d, this.e, this.f);
        this.c ^= this.b[12];
        this.d ^= this.b[13];
        this.e ^= this.b[14];
        this.f ^= this.b[15];
        b();
        f(this.c, this.d, this.e, this.f);
        this.c ^= this.b[8];
        this.d ^= this.b[9];
        this.e ^= this.b[10];
        this.f ^= this.b[11];
        b();
        d(this.c, this.d, this.e, this.f);
        this.c ^= this.b[4];
        this.d ^= this.b[5];
        this.e ^= this.b[6];
        this.f ^= this.b[7];
        b();
        b(this.c, this.d, this.e, this.f);
        a(this.f ^ this.b[3], bArr2, i2);
        a(this.e ^ this.b[2], bArr2, i2 + 4);
        a(this.d ^ this.b[1], bArr2, i2 + 8);
        a(this.c ^ this.b[0], bArr2, i2 + 12);
    }

    private void c(int i, int i2, int i3, int i4) {
        int i5 = (i ^ -1) ^ i2;
        int i6 = (i | i5) ^ i3;
        this.e = i4 ^ i6;
        int i7 = i2 ^ (i4 | i5);
        int i8 = this.e ^ i5;
        this.f = (i6 & i7) ^ i8;
        int i9 = i7 ^ i6;
        this.d = this.f ^ i9;
        this.c = i6 ^ (i9 & i8);
    }

    private void d(int i, int i2, int i3, int i4) {
        int i5 = i4 ^ i2;
        int i6 = i ^ (i2 & i5);
        int i7 = i5 ^ i6;
        this.f = i3 ^ i7;
        int i8 = i2 ^ (i5 & i6);
        this.d = i6 ^ (this.f | i8);
        int i9 = this.d ^ -1;
        int i10 = i8 ^ this.f;
        this.c = i9 ^ i10;
        this.e = (i9 | i10) ^ i7;
    }

    private void e(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i2 ^ i4;
        this.c = (i3 & i5) ^ i6;
        int i7 = i3 ^ i5;
        int i8 = i2 & (i3 ^ this.c);
        this.f = i7 ^ i8;
        this.e = i ^ ((i8 | i4) & (this.c | i7));
        this.d = (this.f ^ i6) ^ (this.e ^ (i4 | i5));
    }

    private void f(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i4;
        int i6 = i5 ^ -1;
        int i7 = i ^ i3;
        int i8 = i3 ^ i5;
        this.c = (i2 & i8) ^ i7;
        this.f = (((i | i6) ^ i4) | i7) ^ i5;
        int i9 = i8 ^ -1;
        int i10 = this.c | this.f;
        this.d = i9 ^ i10;
        this.e = (i9 & i4) ^ (i10 ^ i7);
    }

    private void g(int i, int i2, int i3, int i4) {
        int i5 = i ^ i2;
        int i6 = i & i3;
        int i7 = i | i4;
        int i8 = i3 ^ i4;
        int i9 = i6 | (i5 & i7);
        this.e = i8 ^ i9;
        int i10 = (i7 ^ i2) ^ i9;
        this.c = i5 ^ (i8 & i10);
        int i11 = this.e & this.c;
        this.d = i10 ^ i11;
        this.f = (i2 | i4) ^ (i8 ^ i11);
    }

    private void h(int i, int i2, int i3, int i4) {
        int i5 = i | i2;
        int i6 = i2 ^ i3;
        int i7 = i ^ (i2 & i6);
        int i8 = i3 ^ i7;
        int i9 = i4 | i7;
        this.c = i6 ^ i9;
        int i10 = (i9 | i6) ^ i4;
        this.e = i8 ^ i10;
        int i11 = i5 ^ i10;
        this.f = i7 ^ (this.c & i11);
        this.d = this.f ^ (i11 ^ this.c);
    }

    private void i(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i3 ^ (i4 & i5);
        int i7 = i2 | i6;
        this.f = i5 ^ i7;
        int i8 = i2 ^ -1;
        this.c = (i5 | i8) ^ i6;
        int i9 = i8 ^ i5;
        this.e = (i7 & i9) ^ (this.c & i);
        this.d = (i ^ i6) ^ (i9 & this.e);
    }

    private void j(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ ((i3 | i4) & i);
        int i6 = i3 ^ (i & i5);
        this.d = i4 ^ i6;
        int i7 = i ^ -1;
        this.f = (i6 & this.d) ^ i5;
        int i8 = (this.d | i7) ^ i4;
        this.c = this.f ^ i8;
        this.e = (i7 ^ this.d) ^ (i5 & i8);
    }

    private void k(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i ^ i4;
        this.c = (i3 ^ i5) ^ (i6 | i7);
        int i8 = this.c & i4;
        this.d = (this.c ^ i6) ^ i8;
        int i9 = i7 ^ (this.c | i5);
        this.e = (i6 | i8) ^ i9;
        this.f = (i9 & this.d) ^ (i2 ^ i8);
    }

    private void l(int i, int i2, int i3, int i4) {
        int i5 = i3 ^ -1;
        int i6 = (i2 & i5) ^ i4;
        int i7 = i & i6;
        this.f = (i2 ^ i5) ^ i7;
        int i8 = this.f | i2;
        this.d = i6 ^ (i & i8);
        int i9 = i4 | i;
        this.c = (i5 ^ i8) ^ i9;
        this.e = ((i ^ i3) | i7) ^ (i2 & i9);
    }

    private void m(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i4;
        int i7 = i2 ^ i6;
        int i8 = i3 ^ (i5 | i6);
        this.d = i2 ^ i8;
        int i9 = (i6 | this.d) ^ i4;
        this.e = (i8 & i9) ^ i7;
        int i10 = i9 ^ i8;
        this.c = this.e ^ i10;
        this.f = (i10 & i7) ^ (i8 ^ -1);
    }

    private void n(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i3 ^ i6;
        int i8 = (i3 | i5) ^ i4;
        this.d = i7 ^ i8;
        int i9 = i6 ^ (i7 & i8);
        this.f = i8 ^ (i2 | i9);
        int i10 = i2 | this.f;
        this.c = i9 ^ i10;
        this.e = (i4 & i5) ^ (i10 ^ i7);
    }

    private void o(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i3;
        int i6 = (i3 & i5) ^ i4;
        int i7 = i ^ i6;
        this.d = i2 ^ ((i4 | i5) & i7);
        int i8 = this.d | i6;
        this.f = (i & i7) ^ i5;
        int i9 = i7 ^ i8;
        this.e = (this.f & i9) ^ i6;
        this.c = (i9 ^ -1) ^ (this.f & this.e);
    }

    private void p(int i, int i2, int i3, int i4) {
        int i5 = (i & i2) | i3;
        int i6 = (i | i2) & i4;
        this.f = i5 ^ i6;
        int i7 = i2 ^ i6;
        this.d = ((this.f ^ (i4 ^ -1)) | i7) ^ i;
        this.c = (i7 ^ i3) ^ (this.d | i4);
        this.e = ((i & this.f) ^ this.c) ^ (this.d ^ i5);
    }

    public String getAlgorithmName() {
        return "Serpent";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.a = z;
            this.b = a(((KeyParameter) cipherParameters).getKey());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("invalid parameter passed to Serpent init - ");
        sb.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.b == null) {
            throw new IllegalStateException("Serpent not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            if (this.a) {
                a(bArr, i, bArr2, i2);
            } else {
                b(bArr, i, bArr2, i2);
            }
            return 16;
        }
    }

    public void reset() {
    }
}
