package org.bouncycastle.crypto.digests;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.HttpStatus;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;

public final class WhirlpoolDigest implements ExtendedDigest, Memoable {
    private static final int[] a = {24, 35, 198, 232, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, 1, 79, 54, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, 210, 245, EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY, 111, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA, 82, 96, 188, CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 12, EACTags.SECURITY_ENVIRONMENT_TEMPLATE, 53, 29, 224, 215, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, 46, 75, 254, 87, 21, 119, 55, 229, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, 240, 74, 218, 88, HttpStatus.SC_CREATED, 41, 10, CipherSuite.TLS_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 107, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, 93, 16, 244, HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, 62, 5, 103, 228, 39, 65, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, EACTags.SECURE_MESSAGING_TEMPLATE, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, 216, 251, 238, EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE, 102, 221, 23, 71, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, HttpStatus.SC_ACCEPTED, 45, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 7, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 90, 131, 51, 99, 2, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 113, 200, 25, 73, 217, 242, 227, 91, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 38, 50, CipherSuite.TLS_PSK_WITH_NULL_SHA256, 233, 15, 213, 128, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256, HttpStatus.SC_RESET_CONTENT, 52, 72, 255, EACTags.SECURITY_SUPPORT_TEMPLATE, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 95, 32, 104, 26, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256, 180, 84, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 34, 100, 241, 115, 18, 64, 8, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, 236, 219, CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, 61, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, 0, HttpStatus.SC_MULTI_STATUS, 43, 118, 130, 214, 27, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 106, 80, 69, 243, 48, 239, 63, 85, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, 234, 101, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, 47, 192, 222, 28, 253, 77, CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA, 117, 6, CipherSuite.TLS_PSK_WITH_RC4_128_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, 230, 14, 31, 98, 212, 168, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, 249, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 37, 89, CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA, 114, 57, 76, 94, EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY, 56, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 209, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 226, 97, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 33, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 30, 67, 199, 252, 4, 81, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 109, 13, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 223, EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE, 36, 59, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, HttpStatus.SC_PARTIAL_CONTENT, 17, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 78, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384, 235, 60, 129, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA, 247, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 19, 44, 211, 231, 110, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 3, 86, 68, CertificateBody.profileType, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 42, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 83, 220, 11, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, 108, 49, 116, 246, 70, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, 20, 225, 22, 58, 105, 9, 112, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, 208, 237, HttpStatus.SC_NO_CONTENT, 66, CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256, 40, 92, 248, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA};
    private static final long[] b = new long[256];
    private static final long[] c = new long[256];
    private static final long[] d = new long[256];
    private static final long[] e = new long[256];
    private static final long[] f = new long[256];
    private static final long[] g = new long[256];
    private static final long[] h = new long[256];
    private static final long[] i = new long[256];
    private static final short[] s = new short[32];
    private final long[] j = new long[11];
    private byte[] k = new byte[64];
    private int l = 0;
    private short[] m = new short[32];
    private long[] n = new long[8];
    private long[] o = new long[8];
    private long[] p = new long[8];
    private long[] q = new long[8];
    private long[] r = new long[8];

    static {
        s[31] = 8;
    }

    public WhirlpoolDigest() {
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = a[i2];
            int a2 = a(i3 << 1);
            int a3 = a(a2 << 1);
            int i4 = a3 ^ i3;
            int a4 = a(a3 << 1);
            int i5 = a4 ^ i3;
            int i6 = i3;
            b[i2] = a(i3, i6, a3, i3, a4, i4, a2, i5);
            int i7 = i3;
            c[i2] = a(i5, i6, i7, a3, i3, a4, i4, a2);
            int i8 = i3;
            d[i2] = a(a2, i5, i7, i8, a3, i3, a4, i4);
            int i9 = i3;
            e[i2] = a(i4, a2, i5, i8, i9, a3, i3, a4);
            int i10 = i3;
            f[i2] = a(a4, i4, a2, i5, i9, i10, a3, i3);
            int i11 = i3;
            g[i2] = a(i3, a4, i4, a2, i5, i10, i11, a3);
            int i12 = i3;
            h[i2] = a(a3, i3, a4, i4, a2, i5, i11, i12);
            i[i2] = a(i3, a3, i3, a4, i4, a2, i5, i12);
        }
        this.j[0] = 0;
        for (int i13 = 1; i13 <= 10; i13++) {
            int i14 = (i13 - 1) * 8;
            this.j[i13] = (((((((b[i14] & -72057594037927936L) ^ (c[i14 + 1] & 71776119061217280L)) ^ (d[i14 + 2] & 280375465082880L)) ^ (e[i14 + 3] & 1095216660480L)) ^ (f[i14 + 4] & 4278190080L)) ^ (g[i14 + 5] & 16711680)) ^ (h[i14 + 6] & 65280)) ^ (i[i14 + 7] & 255);
        }
    }

    public WhirlpoolDigest(WhirlpoolDigest whirlpoolDigest) {
        reset(whirlpoolDigest);
    }

    private int a(int i2) {
        return ((long) i2) >= 256 ? i2 ^ 285 : i2;
    }

    private long a(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        return (((((((((long) i2) << 56) ^ (((long) i3) << 48)) ^ (((long) i4) << 40)) ^ (((long) i5) << 32)) ^ (((long) i6) << 24)) ^ (((long) i7) << 16)) ^ (((long) i8) << 8)) ^ ((long) i9);
    }

    private void a() {
        int i2 = 0;
        for (int length = this.m.length - 1; length >= 0; length--) {
            int i3 = (this.m[length] & 255) + s[length] + i2;
            i2 = i3 >>> 8;
            this.m[length] = (short) (i3 & 255);
        }
    }

    private void a(long j2, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr[i2 + i3] = (byte) ((int) ((j2 >> (56 - (i3 * 8))) & 255));
        }
    }

    private void a(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < this.r.length; i3++) {
            this.q[i3] = b(this.k, i3 * 8);
        }
        processBlock();
        this.l = 0;
        Arrays.fill(this.k, 0);
    }

    private long b(byte[] bArr, int i2) {
        return ((((long) bArr[i2 + 0]) & 255) << 56) | ((((long) bArr[i2 + 1]) & 255) << 48) | ((((long) bArr[i2 + 2]) & 255) << 40) | ((((long) bArr[i2 + 3]) & 255) << 32) | ((((long) bArr[i2 + 4]) & 255) << 24) | ((((long) bArr[i2 + 5]) & 255) << 16) | ((((long) bArr[i2 + 6]) & 255) << 8) | (((long) bArr[i2 + 7]) & 255);
    }

    private void b() {
        byte[] c2 = c();
        byte[] bArr = this.k;
        int i2 = this.l;
        this.l = i2 + 1;
        bArr[i2] = (byte) (bArr[i2] | UnsignedBytes.MAX_POWER_OF_TWO);
        if (this.l == this.k.length) {
            a(this.k, 0);
        }
        if (this.l > 32) {
            while (this.l != 0) {
                update(0);
            }
        }
        while (this.l <= 32) {
            update(0);
        }
        System.arraycopy(c2, 0, this.k, 32, c2.length);
        a(this.k, 0);
    }

    private byte[] c() {
        byte[] bArr = new byte[32];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (this.m[i2] & 255);
        }
        return bArr;
    }

    public Memoable copy() {
        return new WhirlpoolDigest(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        b();
        for (int i3 = 0; i3 < 8; i3++) {
            a(this.n[i3], bArr, (i3 * 8) + i2);
        }
        reset();
        return getDigestSize();
    }

    public String getAlgorithmName() {
        return "Whirlpool";
    }

    public int getByteLength() {
        return 64;
    }

    public int getDigestSize() {
        return 64;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        for (int i2 = 0; i2 < 8; i2++) {
            long[] jArr = this.r;
            long j2 = this.q[i2];
            long[] jArr2 = this.o;
            long j3 = this.n[i2];
            jArr2[i2] = j3;
            jArr[i2] = j2 ^ j3;
        }
        int i3 = 1;
        while (i3 <= 10) {
            int i4 = 0;
            while (i4 < 8) {
                this.p[i4] = 0;
                long[] jArr3 = this.p;
                int i5 = i3;
                jArr3[i4] = jArr3[i4] ^ b[((int) (this.o[(i4 + 0) & 7] >>> 56)) & 255];
                long[] jArr4 = this.p;
                jArr4[i4] = jArr4[i4] ^ c[((int) (this.o[(i4 - 1) & 7] >>> 48)) & 255];
                long[] jArr5 = this.p;
                jArr5[i4] = jArr5[i4] ^ d[((int) (this.o[(i4 - 2) & 7] >>> 40)) & 255];
                long[] jArr6 = this.p;
                jArr6[i4] = jArr6[i4] ^ e[((int) (this.o[(i4 - 3) & 7] >>> 32)) & 255];
                long[] jArr7 = this.p;
                jArr7[i4] = jArr7[i4] ^ f[((int) (this.o[(i4 - 4) & 7] >>> 24)) & 255];
                long[] jArr8 = this.p;
                jArr8[i4] = jArr8[i4] ^ g[((int) (this.o[(i4 - 5) & 7] >>> 16)) & 255];
                long[] jArr9 = this.p;
                jArr9[i4] = jArr9[i4] ^ h[((int) (this.o[(i4 - 6) & 7] >>> 8)) & 255];
                long[] jArr10 = this.p;
                jArr10[i4] = jArr10[i4] ^ i[((int) this.o[(i4 - 7) & 7]) & 255];
                i4++;
                i3 = i5;
            }
            int i6 = i3;
            System.arraycopy(this.p, 0, this.o, 0, this.o.length);
            long[] jArr11 = this.o;
            jArr11[0] = jArr11[0] ^ this.j[i6];
            for (int i7 = 0; i7 < 8; i7++) {
                this.p[i7] = this.o[i7];
                long[] jArr12 = this.p;
                jArr12[i7] = jArr12[i7] ^ b[((int) (this.r[(i7 + 0) & 7] >>> 56)) & 255];
                long[] jArr13 = this.p;
                jArr13[i7] = jArr13[i7] ^ c[((int) (this.r[(i7 - 1) & 7] >>> 48)) & 255];
                long[] jArr14 = this.p;
                jArr14[i7] = jArr14[i7] ^ d[((int) (this.r[(i7 - 2) & 7] >>> 40)) & 255];
                long[] jArr15 = this.p;
                jArr15[i7] = jArr15[i7] ^ e[((int) (this.r[(i7 - 3) & 7] >>> 32)) & 255];
                long[] jArr16 = this.p;
                jArr16[i7] = jArr16[i7] ^ f[((int) (this.r[(i7 - 4) & 7] >>> 24)) & 255];
                long[] jArr17 = this.p;
                jArr17[i7] = jArr17[i7] ^ g[((int) (this.r[(i7 - 5) & 7] >>> 16)) & 255];
                long[] jArr18 = this.p;
                jArr18[i7] = jArr18[i7] ^ h[((int) (this.r[(i7 - 6) & 7] >>> 8)) & 255];
                long[] jArr19 = this.p;
                jArr19[i7] = jArr19[i7] ^ i[((int) this.r[(i7 - 7) & 7]) & 255];
            }
            System.arraycopy(this.p, 0, this.r, 0, this.r.length);
            i3 = i6 + 1;
        }
        for (int i8 = 0; i8 < 8; i8++) {
            long[] jArr20 = this.n;
            jArr20[i8] = jArr20[i8] ^ (this.r[i8] ^ this.q[i8]);
        }
    }

    public void reset() {
        this.l = 0;
        Arrays.fill(this.m, 0);
        Arrays.fill(this.k, 0);
        Arrays.fill(this.n, 0);
        Arrays.fill(this.o, 0);
        Arrays.fill(this.p, 0);
        Arrays.fill(this.q, 0);
        Arrays.fill(this.r, 0);
    }

    public void reset(Memoable memoable) {
        WhirlpoolDigest whirlpoolDigest = (WhirlpoolDigest) memoable;
        System.arraycopy(whirlpoolDigest.j, 0, this.j, 0, this.j.length);
        System.arraycopy(whirlpoolDigest.k, 0, this.k, 0, this.k.length);
        this.l = whirlpoolDigest.l;
        System.arraycopy(whirlpoolDigest.m, 0, this.m, 0, this.m.length);
        System.arraycopy(whirlpoolDigest.n, 0, this.n, 0, this.n.length);
        System.arraycopy(whirlpoolDigest.o, 0, this.o, 0, this.o.length);
        System.arraycopy(whirlpoolDigest.p, 0, this.p, 0, this.p.length);
        System.arraycopy(whirlpoolDigest.q, 0, this.q, 0, this.q.length);
        System.arraycopy(whirlpoolDigest.r, 0, this.r, 0, this.r.length);
    }

    public void update(byte b2) {
        this.k[this.l] = b2;
        this.l++;
        if (this.l == this.k.length) {
            a(this.k, 0);
        }
        a();
    }

    public void update(byte[] bArr, int i2, int i3) {
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
