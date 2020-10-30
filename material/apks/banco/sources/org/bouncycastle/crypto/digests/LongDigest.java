package org.bouncycastle.crypto.digests;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public abstract class LongDigest implements ExtendedDigest, EncodableDigest, Memoable {
    static final long[] a = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    protected long H1;
    protected long H2;
    protected long H3;
    protected long H4;
    protected long H5;
    protected long H6;
    protected long H7;
    protected long H8;
    private byte[] b;
    private int c;
    private long d;
    private long e;
    private long[] f;
    private int g;

    protected LongDigest() {
        this.b = new byte[8];
        this.f = new long[80];
        this.c = 0;
        reset();
    }

    protected LongDigest(LongDigest longDigest) {
        this.b = new byte[8];
        this.f = new long[80];
        copyIn(longDigest);
    }

    private long a(long j) {
        return (((j << 36) | (j >>> 28)) ^ ((j << 30) | (j >>> 34))) ^ ((j << 25) | (j >>> 39));
    }

    private long a(long j, long j2, long j3) {
        return (j & j2) ^ ((j ^ -1) & j3);
    }

    private void a() {
        if (this.d > 2305843009213693951L) {
            this.e += this.d >>> 61;
            this.d &= 2305843009213693951L;
        }
    }

    private long b(long j) {
        return (((j << 50) | (j >>> 14)) ^ ((j << 46) | (j >>> 18))) ^ ((j << 23) | (j >>> 41));
    }

    private long b(long j, long j2, long j3) {
        return ((j & j2) ^ (j & j3)) ^ (j2 & j3);
    }

    private long c(long j) {
        return (((j << 63) | (j >>> 1)) ^ ((j << 56) | (j >>> 8))) ^ (j >>> 7);
    }

    private long d(long j) {
        return (((j << 45) | (j >>> 19)) ^ ((j << 3) | (j >>> 61))) ^ (j >>> 6);
    }

    /* access modifiers changed from: protected */
    public void copyIn(LongDigest longDigest) {
        System.arraycopy(longDigest.b, 0, this.b, 0, longDigest.b.length);
        this.c = longDigest.c;
        this.d = longDigest.d;
        this.e = longDigest.e;
        this.H1 = longDigest.H1;
        this.H2 = longDigest.H2;
        this.H3 = longDigest.H3;
        this.H4 = longDigest.H4;
        this.H5 = longDigest.H5;
        this.H6 = longDigest.H6;
        this.H7 = longDigest.H7;
        this.H8 = longDigest.H8;
        System.arraycopy(longDigest.f, 0, this.f, 0, longDigest.f.length);
        this.g = longDigest.g;
    }

    public void finish() {
        a();
        long j = this.d << 3;
        long j2 = this.e;
        byte b2 = UnsignedBytes.MAX_POWER_OF_TWO;
        while (true) {
            update(b2);
            if (this.c != 0) {
                b2 = 0;
            } else {
                processLength(j, j2);
                processBlock();
                return;
            }
        }
    }

    public int getByteLength() {
        return 128;
    }

    /* access modifiers changed from: protected */
    public int getEncodedStateSize() {
        return (this.g * 8) + 96;
    }

    /* access modifiers changed from: protected */
    public void populateState(byte[] bArr) {
        System.arraycopy(this.b, 0, bArr, 0, this.c);
        Pack.intToBigEndian(this.c, bArr, 8);
        Pack.longToBigEndian(this.d, bArr, 12);
        Pack.longToBigEndian(this.e, bArr, 20);
        Pack.longToBigEndian(this.H1, bArr, 28);
        Pack.longToBigEndian(this.H2, bArr, 36);
        Pack.longToBigEndian(this.H3, bArr, 44);
        Pack.longToBigEndian(this.H4, bArr, 52);
        Pack.longToBigEndian(this.H5, bArr, 60);
        Pack.longToBigEndian(this.H6, bArr, 68);
        Pack.longToBigEndian(this.H7, bArr, 76);
        Pack.longToBigEndian(this.H8, bArr, 84);
        Pack.intToBigEndian(this.g, bArr, 92);
        for (int i = 0; i < this.g; i++) {
            Pack.longToBigEndian(this.f[i], bArr, (i * 8) + 96);
        }
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        a();
        for (int i = 16; i <= 79; i++) {
            this.f[i] = d(this.f[i - 2]) + this.f[i - 7] + c(this.f[i - 15]) + this.f[i - 16];
        }
        long j = this.H1;
        long j2 = this.H2;
        long j3 = this.H3;
        long j4 = this.H4;
        long j5 = this.H5;
        long j6 = this.H6;
        long j7 = j4;
        long j8 = j2;
        long j9 = j3;
        long j10 = j7;
        long j11 = j;
        long j12 = this.H7;
        long j13 = j6;
        int i2 = 0;
        int i3 = 0;
        long j14 = j5;
        long j15 = this.H8;
        while (i2 < 10) {
            int i4 = i3 + 1;
            long b2 = j15 + b(j14) + a(j14, j13, j12) + a[i3] + this.f[i3];
            long j16 = j10 + b2;
            long j17 = j11;
            long j18 = j17;
            long j19 = j16;
            long a2 = b2 + a(j17) + b(j17, j8, j9);
            int i5 = i4 + 1;
            long b3 = j12 + b(j16) + a(j16, j14, j13) + a[i4] + this.f[i4];
            long j20 = j9 + b3;
            long a3 = a(a2);
            long j21 = a2;
            long j22 = j20;
            long b4 = b3 + a3 + b(a2, j18, j8);
            long b5 = b(j22);
            long j23 = j22;
            long j24 = b4;
            int i6 = i5 + 1;
            long a4 = j13 + b5 + a(j22, j19, j14) + a[i5] + this.f[i5];
            int i7 = i2;
            long j25 = j8 + a4;
            long a5 = a4 + a(j24) + b(j24, j21, j18);
            long b6 = b(j25);
            long j26 = j25;
            long j27 = a5;
            int i8 = i6 + 1;
            long a6 = j14 + b6 + a(j25, j23, j19) + a[i6] + this.f[i6];
            long j28 = j18 + a6;
            long a7 = a6 + a(j27) + b(j27, j24, j21);
            int i9 = i8 + 1;
            long b7 = j19 + b(j28) + a(j28, j26, j23) + a[i8] + this.f[i8];
            long j29 = j27;
            long j30 = j27;
            long j31 = j21 + b7;
            long a8 = b7 + a(a7) + b(a7, j29, j24);
            long j32 = j28;
            long j33 = j28;
            long j34 = a8;
            int i10 = i9 + 1;
            long b8 = j23 + b(j31) + a(j31, j32, j26) + a[i9] + this.f[i9];
            long j35 = a7;
            long j36 = a7;
            long j37 = j24 + b8;
            long a9 = b8 + a(j34) + b(j34, j35, j30);
            int i11 = i10 + 1;
            long b9 = j26 + b(j37) + a(j37, j31, j33) + a[i10] + this.f[i10];
            long j38 = j34;
            long j39 = j34;
            long j40 = j30 + b9;
            long a10 = b9 + a(a9) + b(a9, j38, j36);
            long b10 = b(j40);
            long j41 = j40;
            long j42 = a10;
            int i12 = i11 + 1;
            long a11 = j33 + b10 + a(j40, j37, j31) + a[i11] + this.f[i11];
            j11 = a11 + a(j42) + b(j42, a9, j39);
            j9 = a9;
            j8 = j42;
            j15 = j31;
            i3 = i12;
            j13 = j41;
            i2 = i7 + 1;
            j10 = j39;
            j12 = j37;
            j14 = j36 + a11;
        }
        this.H1 += j11;
        this.H2 += j8;
        this.H3 += j9;
        this.H4 += j10;
        this.H5 += j14;
        this.H6 += j13;
        this.H7 += j12;
        this.H8 += j15;
        this.g = 0;
        for (int i13 = 0; i13 < 16; i13++) {
            this.f[i13] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j, long j2) {
        if (this.g > 14) {
            processBlock();
        }
        this.f[14] = j2;
        this.f[15] = j;
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        this.f[this.g] = Pack.bigEndianToLong(bArr, i);
        int i2 = this.g + 1;
        this.g = i2;
        if (i2 == 16) {
            processBlock();
        }
    }

    public void reset() {
        this.d = 0;
        this.e = 0;
        this.c = 0;
        for (int i = 0; i < this.b.length; i++) {
            this.b[i] = 0;
        }
        this.g = 0;
        for (int i2 = 0; i2 != this.f.length; i2++) {
            this.f[i2] = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void restoreState(byte[] bArr) {
        this.c = Pack.bigEndianToInt(bArr, 8);
        System.arraycopy(bArr, 0, this.b, 0, this.c);
        this.d = Pack.bigEndianToLong(bArr, 12);
        this.e = Pack.bigEndianToLong(bArr, 20);
        this.H1 = Pack.bigEndianToLong(bArr, 28);
        this.H2 = Pack.bigEndianToLong(bArr, 36);
        this.H3 = Pack.bigEndianToLong(bArr, 44);
        this.H4 = Pack.bigEndianToLong(bArr, 52);
        this.H5 = Pack.bigEndianToLong(bArr, 60);
        this.H6 = Pack.bigEndianToLong(bArr, 68);
        this.H7 = Pack.bigEndianToLong(bArr, 76);
        this.H8 = Pack.bigEndianToLong(bArr, 84);
        this.g = Pack.bigEndianToInt(bArr, 92);
        for (int i = 0; i < this.g; i++) {
            this.f[i] = Pack.bigEndianToLong(bArr, (i * 8) + 96);
        }
    }

    public void update(byte b2) {
        byte[] bArr = this.b;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = b2;
        if (this.c == this.b.length) {
            processWord(this.b, 0);
            this.c = 0;
        }
        this.d++;
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.c != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.b.length) {
            processWord(bArr, i);
            i += this.b.length;
            i2 -= this.b.length;
            this.d += (long) this.b.length;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
