package org.bouncycastle.pqc.math.linearalgebra;

import android.support.v4.media.session.PlaybackStateCompat;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.primitives.Longs;
import java.math.BigInteger;
import java.util.Random;

public class GF2nONBElement extends GF2nElement {
    private static final long[] a = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH, PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM, PlaybackStateCompat.ACTION_PLAY_FROM_URI, PlaybackStateCompat.ACTION_PREPARE, PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID, PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH, PlaybackStateCompat.ACTION_PREPARE_FROM_URI, PlaybackStateCompat.ACTION_SET_REPEAT_MODE, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, Longs.MAX_POWER_OF_TWO, Long.MIN_VALUE};
    private static final long[] b = {1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, 2147483647L, 4294967295L, 8589934591L, 17179869183L, 34359738367L, 68719476735L, 137438953471L, 274877906943L, 549755813887L, 1099511627775L, 2199023255551L, 4398046511103L, 8796093022207L, 17592186044415L, 35184372088831L, 70368744177663L, 140737488355327L, 281474976710655L, 562949953421311L, 1125899906842623L, 2251799813685247L, 4503599627370495L, 9007199254740991L, 18014398509481983L, 36028797018963967L, 72057594037927935L, 144115188075855871L, 288230376151711743L, 576460752303423487L, 1152921504606846975L, 2305843009213693951L, 4611686018427387903L, Long.MAX_VALUE, -1};
    private static final int[] c = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    private int d;
    private int e;
    private long[] f;

    public GF2nONBElement(GF2nONBElement gF2nONBElement) {
        this.mField = gF2nONBElement.mField;
        this.mDegree = this.mField.getDegree();
        this.d = ((GF2nONBField) this.mField).a();
        this.e = ((GF2nONBField) this.mField).b();
        this.f = new long[this.d];
        a(gF2nONBElement.c());
    }

    public GF2nONBElement(GF2nONBField gF2nONBField, BigInteger bigInteger) {
        this.mField = gF2nONBField;
        this.mDegree = this.mField.getDegree();
        this.d = gF2nONBField.a();
        this.e = gF2nONBField.b();
        this.f = new long[this.d];
        a(bigInteger);
    }

    public GF2nONBElement(GF2nONBField gF2nONBField, Random random) {
        this.mField = gF2nONBField;
        this.mDegree = this.mField.getDegree();
        this.d = gF2nONBField.a();
        this.e = gF2nONBField.b();
        this.f = new long[this.d];
        if (this.d > 1) {
            for (int i = 0; i < this.d - 1; i++) {
                this.f[i] = random.nextLong();
            }
            this.f[this.d - 1] = random.nextLong() >>> (64 - this.e);
            return;
        }
        this.f[0] = random.nextLong();
        this.f[0] = this.f[0] >>> (64 - this.e);
    }

    public GF2nONBElement(GF2nONBField gF2nONBField, byte[] bArr) {
        this.mField = gF2nONBField;
        this.mDegree = this.mField.getDegree();
        this.d = gF2nONBField.a();
        this.e = gF2nONBField.b();
        this.f = new long[this.d];
        a(bArr);
    }

    private GF2nONBElement(GF2nONBField gF2nONBField, long[] jArr) {
        this.mField = gF2nONBField;
        this.mDegree = this.mField.getDegree();
        this.d = gF2nONBField.a();
        this.e = gF2nONBField.b();
        this.f = jArr;
    }

    public static GF2nONBElement ONE(GF2nONBField gF2nONBField) {
        int a2 = gF2nONBField.a();
        long[] jArr = new long[a2];
        int i = 0;
        while (true) {
            int i2 = a2 - 1;
            if (i < i2) {
                jArr[i] = -1;
                i++;
            } else {
                jArr[i2] = b[gF2nONBField.b() - 1];
                return new GF2nONBElement(gF2nONBField, jArr);
            }
        }
    }

    public static GF2nONBElement ZERO(GF2nONBField gF2nONBField) {
        return new GF2nONBElement(gF2nONBField, new long[gF2nONBField.a()]);
    }

    private void a(BigInteger bigInteger) {
        a(bigInteger.toByteArray());
    }

    private void a(byte[] bArr) {
        this.f = new long[this.d];
        for (int i = 0; i < bArr.length; i++) {
            long[] jArr = this.f;
            int i2 = i >>> 3;
            jArr[i2] = jArr[i2] | ((((long) bArr[(bArr.length - 1) - i]) & 255) << ((i & 7) << 3));
        }
    }

    private void a(long[] jArr) {
        System.arraycopy(jArr, 0, this.f, 0, this.d);
    }

    private long[] c() {
        long[] jArr = new long[this.f.length];
        System.arraycopy(this.f, 0, jArr, 0, this.f.length);
        return jArr;
    }

    private long[] d() {
        long[] jArr = new long[this.f.length];
        for (int i = 0; i < this.mDegree; i++) {
            if (a((this.mDegree - i) - 1)) {
                int i2 = i >>> 6;
                jArr[i2] = jArr[i2] | a[i & 63];
            }
        }
        return jArr;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.f = new long[this.d];
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        boolean z = false;
        if (i >= 0) {
            if (i > this.mDegree) {
                return false;
            }
            if ((this.f[i >>> 6] & a[i & 63]) != 0) {
                z = true;
            }
        }
        return z;
    }

    public GFElement add(GFElement gFElement) {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.addToThis(gFElement);
        return gF2nONBElement;
    }

    public void addToThis(GFElement gFElement) {
        if (!(gFElement instanceof GF2nONBElement)) {
            throw new RuntimeException();
        }
        GF2nONBElement gF2nONBElement = (GF2nONBElement) gFElement;
        if (!this.mField.equals(gF2nONBElement.mField)) {
            throw new RuntimeException();
        }
        for (int i = 0; i < this.d; i++) {
            long[] jArr = this.f;
            jArr[i] = jArr[i] ^ gF2nONBElement.f[i];
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.f = d();
    }

    public Object clone() {
        return new GF2nONBElement(this);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GF2nONBElement)) {
            return false;
        }
        GF2nONBElement gF2nONBElement = (GF2nONBElement) obj;
        for (int i = 0; i < this.d; i++) {
            if (this.f[i] != gF2nONBElement.f[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.f.hashCode();
    }

    public GF2nElement increase() {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.increaseThis();
        return gF2nONBElement;
    }

    public void increaseThis() {
        addToThis(ONE((GF2nONBField) this.mField));
    }

    public GFElement invert() {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.invertThis();
        return gF2nONBElement;
    }

    public void invertThis() {
        if (isZero()) {
            throw new ArithmeticException();
        }
        int i = 31;
        boolean z = false;
        while (!z && i >= 0) {
            if ((((long) (this.mDegree - 1)) & a[i]) != 0) {
                z = true;
            }
            i--;
        }
        int i2 = i + 1;
        ZERO((GF2nONBField) this.mField);
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        int i3 = 1;
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            GF2nElement gF2nElement = (GF2nElement) gF2nONBElement.clone();
            for (int i5 = 1; i5 <= i3; i5++) {
                gF2nElement.squareThis();
            }
            gF2nONBElement.multiplyThisBy(gF2nElement);
            i3 <<= 1;
            if ((((long) (this.mDegree - 1)) & a[i4]) != 0) {
                gF2nONBElement.squareThis();
                gF2nONBElement.multiplyThisBy(this);
                i3++;
            }
        }
        gF2nONBElement.squareThis();
    }

    public boolean isOne() {
        boolean z = true;
        for (int i = 0; i < this.d - 1 && z; i++) {
            z = z && (this.f[i] & -1) == -1;
        }
        if (z) {
            if (z && (this.f[this.d - 1] & b[this.e - 1]) == b[this.e - 1]) {
                return true;
            }
            z = false;
        }
        return z;
    }

    public boolean isZero() {
        boolean z = true;
        for (int i = 0; i < this.d && z; i++) {
            z = z && (this.f[i] & -1) == 0;
        }
        return z;
    }

    public GFElement multiply(GFElement gFElement) {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.multiplyThisBy(gFElement);
        return gF2nONBElement;
    }

    public void multiplyThisBy(GFElement gFElement) {
        GFElement gFElement2 = gFElement;
        if (!(gFElement2 instanceof GF2nONBElement)) {
            throw new RuntimeException("The elements have different representation: not yet implemented");
        }
        GF2nONBElement gF2nONBElement = (GF2nONBElement) gFElement2;
        if (!this.mField.equals(gF2nONBElement.mField)) {
            throw new RuntimeException();
        } else if (equals(gFElement)) {
            squareThis();
        } else {
            long[] jArr = this.f;
            long[] jArr2 = gF2nONBElement.f;
            long[] jArr3 = new long[this.d];
            int[][] iArr = ((GF2nONBField) this.mField).a;
            int i = this.d - 1;
            int i2 = this.e - 1;
            long j = a[63];
            long j2 = a[i2];
            char c2 = 0;
            for (int i3 = 0; i3 < this.mDegree; i3++) {
                int i4 = 0;
                boolean z = false;
                while (i4 < this.mDegree) {
                    int i5 = c[i4];
                    int i6 = i4 & 63;
                    int i7 = c[iArr[i4][c2]];
                    int i8 = iArr[i4][c2] & 63;
                    if ((jArr[i5] & a[i6]) != 0) {
                        if ((jArr2[i7] & a[i8]) != 0) {
                            z = !z;
                        }
                        if (iArr[i4][1] != -1) {
                            if ((jArr2[c[iArr[i4][1]]] & a[iArr[i4][1] & 63]) != 0) {
                                z = !z;
                            }
                        }
                    }
                    i4++;
                    c2 = 0;
                }
                int i9 = c[i3];
                int i10 = i3 & 63;
                if (z) {
                    jArr3[i9] = jArr3[i9] ^ a[i10];
                }
                if (this.d > 1) {
                    int i11 = i - 1;
                    boolean z2 = (jArr[i] & 1) == 1;
                    int i12 = i11;
                    while (i12 >= 0) {
                        boolean z3 = (jArr[i12] & 1) != 0;
                        jArr[i12] = jArr[i12] >>> 1;
                        if (z2) {
                            jArr[i12] = jArr[i12] ^ j;
                        }
                        i12--;
                        z2 = z3;
                    }
                    jArr[i] = jArr[i] >>> 1;
                    if (z2) {
                        jArr[i] = jArr[i] ^ j2;
                    }
                    boolean z4 = (jArr2[i] & 1) == 1;
                    while (i11 >= 0) {
                        boolean z5 = (jArr2[i11] & 1) != 0;
                        jArr2[i11] = jArr2[i11] >>> 1;
                        if (z4) {
                            jArr2[i11] = jArr2[i11] ^ j;
                        }
                        i11--;
                        z4 = z5;
                    }
                    jArr2[i] = jArr2[i] >>> 1;
                    if (z4) {
                        jArr2[i] = jArr2[i] ^ j2;
                    }
                    c2 = 0;
                } else {
                    c2 = 0;
                    boolean z6 = (jArr[0] & 1) == 1;
                    jArr[0] = jArr[0] >>> 1;
                    if (z6) {
                        jArr[0] = jArr[0] ^ j2;
                    }
                    boolean z7 = (jArr2[0] & 1) == 1;
                    jArr2[0] = jArr2[0] >>> 1;
                    if (z7) {
                        jArr2[0] = jArr2[0] ^ j2;
                    }
                }
            }
            a(jArr3);
        }
    }

    public GF2nElement solveQuadraticEquation() {
        if (trace() == 1) {
            throw new RuntimeException();
        }
        long j = a[63];
        long[] jArr = new long[this.d];
        long j2 = 0;
        for (int i = 0; i < this.d - 1; i++) {
            long j3 = j2;
            for (int i2 = 1; i2 < 64; i2++) {
                if (((a[i2] & this.f[i]) == 0 || (j3 & a[i2 - 1]) == 0) && !((this.f[i] & a[i2]) == 0 && (j3 & a[i2 - 1]) == 0)) {
                    j3 ^= a[i2];
                }
            }
            jArr[i] = j3;
            long j4 = j & j3;
            j2 = ((j4 == 0 || (1 & this.f[i + 1]) != 1) && !(j4 == 0 && (1 & this.f[i + 1]) == 0)) ? 1 : 0;
        }
        int i3 = 63 & this.mDegree;
        long j5 = this.f[this.d - 1];
        for (int i4 = 1; i4 < i3; i4++) {
            if (((a[i4] & j5) == 0 || (a[i4 - 1] & j2) == 0) && !((a[i4] & j5) == 0 && (a[i4 - 1] & j2) == 0)) {
                j2 ^= a[i4];
            }
        }
        jArr[this.d - 1] = j2;
        return new GF2nONBElement((GF2nONBField) this.mField, jArr);
    }

    public GF2nElement square() {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.squareThis();
        return gF2nONBElement;
    }

    public GF2nElement squareRoot() {
        GF2nONBElement gF2nONBElement = new GF2nONBElement(this);
        gF2nONBElement.squareRootThis();
        return gF2nONBElement;
    }

    public void squareRootThis() {
        long[] c2 = c();
        int i = this.d - 1;
        int i2 = this.e - 1;
        long j = a[63];
        boolean z = (c2[0] & 1) != 0;
        int i3 = i;
        while (i3 >= 0) {
            boolean z2 = (c2[i3] & 1) != 0;
            c2[i3] = c2[i3] >>> 1;
            if (z) {
                if (i3 == i) {
                    c2[i3] = c2[i3] ^ a[i2];
                } else {
                    c2[i3] = c2[i3] ^ j;
                }
            }
            i3--;
            z = z2;
        }
        a(c2);
    }

    public void squareThis() {
        long[] c2 = c();
        int i = this.d - 1;
        int i2 = this.e - 1;
        long j = a[63];
        boolean z = false;
        boolean z2 = (c2[i] & a[i2]) != 0;
        int i3 = 0;
        while (i3 < i) {
            boolean z3 = (c2[i3] & j) != 0;
            c2[i3] = c2[i3] << 1;
            if (z2) {
                c2[i3] = c2[i3] ^ 1;
            }
            i3++;
            z2 = z3;
        }
        if ((c2[i] & a[i2]) != 0) {
            z = true;
        }
        c2[i] = c2[i] << 1;
        if (z2) {
            c2[i] = c2[i] ^ 1;
        }
        if (z) {
            c2[i] = c2[i] ^ a[i2 + 1];
        }
        a(c2);
    }

    public boolean testRightmostBit() {
        return (this.f[this.d - 1] & a[this.e - 1]) != 0;
    }

    public byte[] toByteArray() {
        int i = ((this.mDegree - 1) >> 3) + 1;
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = (i2 & 7) << 3;
            bArr[(i - i2) - 1] = (byte) ((int) ((this.f[i2 >>> 3] & (255 << i3)) >>> i3));
        }
        return bArr;
    }

    public BigInteger toFlexiBigInt() {
        return new BigInteger(1, toByteArray());
    }

    public String toString() {
        return toString(16);
    }

    public String toString(int i) {
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        String str2;
        String str3 = "";
        long[] c2 = c();
        int i2 = this.e;
        if (i == 2) {
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                if ((c2[c2.length - 1] & (1 << i2)) == 0) {
                    sb2 = new StringBuilder();
                    sb2.append(str3);
                    str2 = "0";
                } else {
                    sb2 = new StringBuilder();
                    sb2.append(str3);
                    str2 = "1";
                }
                sb2.append(str2);
                str3 = sb2.toString();
            }
            for (int length = c2.length - 2; length >= 0; length--) {
                for (int i3 = 63; i3 >= 0; i3--) {
                    if ((c2[length] & a[i3]) == 0) {
                        sb = new StringBuilder();
                        sb.append(str3);
                        str = "0";
                    } else {
                        sb = new StringBuilder();
                        sb.append(str3);
                        str = "1";
                    }
                    sb.append(str);
                    str3 = sb.toString();
                }
            }
        } else if (i == 16) {
            char[] cArr = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for (int length2 = c2.length - 1; length2 >= 0; length2--) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str3);
                sb3.append(cArr[((int) (c2[length2] >>> 60)) & 15]);
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(sb4);
                sb5.append(cArr[((int) (c2[length2] >>> 56)) & 15]);
                String sb6 = sb5.toString();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(sb6);
                sb7.append(cArr[((int) (c2[length2] >>> 52)) & 15]);
                String sb8 = sb7.toString();
                StringBuilder sb9 = new StringBuilder();
                sb9.append(sb8);
                sb9.append(cArr[((int) (c2[length2] >>> 48)) & 15]);
                String sb10 = sb9.toString();
                StringBuilder sb11 = new StringBuilder();
                sb11.append(sb10);
                sb11.append(cArr[((int) (c2[length2] >>> 44)) & 15]);
                String sb12 = sb11.toString();
                StringBuilder sb13 = new StringBuilder();
                sb13.append(sb12);
                sb13.append(cArr[((int) (c2[length2] >>> 40)) & 15]);
                String sb14 = sb13.toString();
                StringBuilder sb15 = new StringBuilder();
                sb15.append(sb14);
                sb15.append(cArr[((int) (c2[length2] >>> 36)) & 15]);
                String sb16 = sb15.toString();
                StringBuilder sb17 = new StringBuilder();
                sb17.append(sb16);
                sb17.append(cArr[((int) (c2[length2] >>> 32)) & 15]);
                String sb18 = sb17.toString();
                StringBuilder sb19 = new StringBuilder();
                sb19.append(sb18);
                sb19.append(cArr[((int) (c2[length2] >>> 28)) & 15]);
                String sb20 = sb19.toString();
                StringBuilder sb21 = new StringBuilder();
                sb21.append(sb20);
                sb21.append(cArr[((int) (c2[length2] >>> 24)) & 15]);
                String sb22 = sb21.toString();
                StringBuilder sb23 = new StringBuilder();
                sb23.append(sb22);
                sb23.append(cArr[((int) (c2[length2] >>> 20)) & 15]);
                String sb24 = sb23.toString();
                StringBuilder sb25 = new StringBuilder();
                sb25.append(sb24);
                sb25.append(cArr[((int) (c2[length2] >>> 16)) & 15]);
                String sb26 = sb25.toString();
                StringBuilder sb27 = new StringBuilder();
                sb27.append(sb26);
                sb27.append(cArr[((int) (c2[length2] >>> 12)) & 15]);
                String sb28 = sb27.toString();
                StringBuilder sb29 = new StringBuilder();
                sb29.append(sb28);
                sb29.append(cArr[((int) (c2[length2] >>> 8)) & 15]);
                String sb30 = sb29.toString();
                StringBuilder sb31 = new StringBuilder();
                sb31.append(sb30);
                sb31.append(cArr[((int) (c2[length2] >>> 4)) & 15]);
                String sb32 = sb31.toString();
                StringBuilder sb33 = new StringBuilder();
                sb33.append(sb32);
                sb33.append(cArr[((int) c2[length2]) & 15]);
                String sb34 = sb33.toString();
                StringBuilder sb35 = new StringBuilder();
                sb35.append(sb34);
                sb35.append(UtilsCuentas.SEPARAOR2);
                str3 = sb35.toString();
            }
        }
        return str3;
    }

    public int trace() {
        int i = this.d - 1;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i3;
            for (int i5 = 0; i5 < 64; i5++) {
                if ((this.f[i2] & a[i5]) != 0) {
                    i4 ^= 1;
                }
            }
            i2++;
            i3 = i4;
        }
        int i6 = this.e;
        for (int i7 = 0; i7 < i6; i7++) {
            if ((this.f[i] & a[i7]) != 0) {
                i3 ^= 1;
            }
        }
        return i3;
    }
}
