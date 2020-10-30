package org.bouncycastle.apache.bzip2;

import android.support.v4.view.InputDeviceCompat;
import com.facebook.internal.NativeProtocol;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;

public class CBZip2OutputStream extends OutputStream implements BZip2Constants {
    protected static final int CLEARMASK = -2097153;
    protected static final int DEPTH_THRESH = 10;
    protected static final int GREATER_ICOST = 15;
    protected static final int LESSER_ICOST = 0;
    protected static final int QSORT_STACK_SIZE = 1000;
    protected static final int SETMASK = 2097152;
    protected static final int SMALL_THRESH = 20;
    private boolean A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private OutputStream H;
    private int[] I;
    int a;
    int b;
    int c;
    boolean d;
    int e;
    int f;
    int g;
    CRC h;
    boolean i;
    private boolean j;
    private boolean[] k;
    private int l;
    private char[] m;
    private char[] n;
    private char[] o;
    private char[] p;
    private char[] q;
    private int[] r;
    private int[] s;
    private short[] t;
    private int[] u;
    private int v;
    private int[] w;
    private int x;
    private int y;
    private int z;

    static class StackElem {
        int a;
        int b;
        int c;

        private StackElem() {
        }
    }

    public CBZip2OutputStream(OutputStream outputStream) {
        this(outputStream, 9);
    }

    public CBZip2OutputStream(OutputStream outputStream, int i2) {
        this.h = new CRC();
        this.k = new boolean[256];
        this.m = new char[256];
        this.n = new char[256];
        this.o = new char[BZip2Constants.MAX_SELECTORS];
        this.p = new char[BZip2Constants.MAX_SELECTORS];
        this.w = new int[BZip2Constants.MAX_ALPHA_SIZE];
        this.C = -1;
        this.D = 0;
        this.i = false;
        this.I = new int[]{1, 4, 13, 40, EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY, 364, 1093, 3280, 9841, 29524, 88573, 265720, 797161, 2391484};
        this.q = null;
        this.r = null;
        this.s = null;
        this.u = null;
        outputStream.write(66);
        outputStream.write(90);
        a(outputStream);
        this.x = 50;
        int i3 = 9;
        if (i2 <= 9) {
            i3 = i2;
        }
        if (i3 < 1) {
            i3 = 1;
        }
        this.c = i3;
        n();
        d();
        e();
    }

    private char a(char c2, char c3, char c4) {
        if (c2 <= c3) {
            char c5 = c3;
            c3 = c2;
            c2 = c5;
        }
        if (c2 > c4) {
            c2 = c4;
        }
        return c3 > c2 ? c3 : c2;
    }

    private static void a() {
        System.out.println("panic");
    }

    private void a(int i2) {
        a(8, i2);
    }

    private void a(int i2, int i3) {
        while (this.g >= 8) {
            try {
                this.H.write(this.f >> 24);
                this.f <<= 8;
                this.g -= 8;
                this.e++;
            } catch (IOException e2) {
                throw e2;
            }
        }
        this.f = (i3 << ((32 - this.g) - i2)) | this.f;
        this.g += i2;
    }

    private void a(int i2, int i3, int i4) {
        int i5 = (i3 - i2) + 1;
        if (i5 >= 2) {
            int i6 = 0;
            while (this.I[i6] < i5) {
                i6++;
            }
            loop1:
            while (true) {
                i6--;
                if (i6 < 0) {
                    break;
                }
                int i7 = this.I[i6];
                int i8 = i2 + i7;
                int i9 = i8;
                while (i9 <= i3) {
                    int i10 = this.s[i9];
                    int i11 = i9;
                    while (true) {
                        int i12 = i11 - i7;
                        if (!c(this.s[i12] + i4, i10 + i4)) {
                            break;
                        }
                        this.s[i11] = this.s[i12];
                        if (i12 <= i8 - 1) {
                            i11 = i12;
                            break;
                        }
                        i11 = i12;
                    }
                    this.s[i11] = i10;
                    int i13 = i9 + 1;
                    if (i13 <= i3) {
                        int i14 = this.s[i13];
                        int i15 = i13;
                        while (true) {
                            int i16 = i15 - i7;
                            if (!c(this.s[i16] + i4, i14 + i4)) {
                                break;
                            }
                            this.s[i15] = this.s[i16];
                            if (i16 <= i8 - 1) {
                                i15 = i16;
                                break;
                            }
                            i15 = i16;
                        }
                        this.s[i15] = i14;
                        int i17 = i13 + 1;
                        if (i17 <= i3) {
                            int i18 = this.s[i17];
                            int i19 = i17;
                            while (true) {
                                int i20 = i19 - i7;
                                if (!c(this.s[i20] + i4, i18 + i4)) {
                                    break;
                                }
                                this.s[i19] = this.s[i20];
                                if (i20 <= i8 - 1) {
                                    i19 = i20;
                                    break;
                                }
                                i19 = i20;
                            }
                            this.s[i19] = i18;
                            i9 = i17 + 1;
                            if (this.y > this.z && this.A) {
                                break loop1;
                            }
                        } else {
                            continue;
                            break;
                        }
                    } else {
                        continue;
                        break;
                    }
                }
            }
        }
    }

    private void a(OutputStream outputStream) {
        this.H = outputStream;
        this.g = 0;
        this.f = 0;
        this.e = 0;
    }

    private void a(int[] iArr, char[] cArr, int i2, int i3, int i4) {
        int i5 = 0;
        while (i2 <= i3) {
            int i6 = i5;
            for (int i7 = 0; i7 < i4; i7++) {
                if (cArr[i7] == i2) {
                    iArr[i7] = i6;
                    i6++;
                }
            }
            i5 = i6 << 1;
            i2++;
        }
    }

    private void b() {
        this.l = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (this.k[i2]) {
                this.m[this.l] = (char) i2;
                this.n[i2] = (char) this.l;
                this.l++;
            }
        }
    }

    private void b(int i2) {
        a(8, (i2 >> 24) & 255);
        a(8, (i2 >> 16) & 255);
        a(8, (i2 >> 8) & 255);
        a(8, i2 & 255);
    }

    private void b(int i2, int i3) {
        a(i2, i3);
    }

    private void b(int i2, int i3, int i4) {
        while (i4 > 0) {
            int i5 = this.s[i2];
            this.s[i2] = this.s[i3];
            this.s[i3] = i5;
            i2++;
            i3++;
            i4--;
        }
    }

    private void c() {
        if (this.a < this.G) {
            this.k[this.C] = true;
            for (int i2 = 0; i2 < this.D; i2++) {
                this.h.a((char) this.C);
            }
            switch (this.D) {
                case 1:
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    return;
                case 2:
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    return;
                case 3:
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    return;
                default:
                    this.k[this.D - 4] = true;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) this.C;
                    this.a++;
                    this.q[this.a + 1] = (char) (this.D - 4);
                    return;
            }
        } else {
            f();
            e();
            c();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0090, code lost:
        if (r9 > 0) goto L_0x0092;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(int r13, int r14, int r15) {
        /*
            r12 = this;
            r0 = 1000(0x3e8, float:1.401E-42)
            org.bouncycastle.apache.bzip2.CBZip2OutputStream$StackElem[] r1 = new org.bouncycastle.apache.bzip2.CBZip2OutputStream.StackElem[r0]
            r2 = 0
            r3 = 0
        L_0x0006:
            if (r3 >= r0) goto L_0x0013
            org.bouncycastle.apache.bzip2.CBZip2OutputStream$StackElem r4 = new org.bouncycastle.apache.bzip2.CBZip2OutputStream$StackElem
            r5 = 0
            r4.<init>()
            r1[r3] = r4
            int r3 = r3 + 1
            goto L_0x0006
        L_0x0013:
            r3 = r1[r2]
            r3.a = r13
            r13 = r1[r2]
            r13.b = r14
            r13 = r1[r2]
            r13.c = r15
            r13 = 1
            r14 = 1
        L_0x0021:
            if (r14 <= 0) goto L_0x0142
            if (r14 < r0) goto L_0x0028
            a()
        L_0x0028:
            int r14 = r14 + -1
            r15 = r1[r14]
            int r15 = r15.a
            r2 = r1[r14]
            int r2 = r2.b
            r3 = r1[r14]
            int r3 = r3.c
            int r4 = r2 - r15
            r5 = 20
            if (r4 < r5) goto L_0x0135
            r4 = 10
            if (r3 <= r4) goto L_0x0042
            goto L_0x0135
        L_0x0042:
            char[] r4 = r12.q
            int[] r5 = r12.s
            r5 = r5[r15]
            int r5 = r5 + r3
            int r5 = r5 + r13
            char r4 = r4[r5]
            char[] r5 = r12.q
            int[] r6 = r12.s
            r6 = r6[r2]
            int r6 = r6 + r3
            int r6 = r6 + r13
            char r5 = r5[r6]
            char[] r6 = r12.q
            int[] r7 = r12.s
            int r8 = r15 + r2
            int r8 = r8 >> r13
            r7 = r7[r8]
            int r7 = r7 + r3
            int r7 = r7 + r13
            char r6 = r6[r7]
            char r4 = r12.a(r4, r5, r6)
            r5 = r15
            r7 = r5
            r6 = r2
            r8 = r6
        L_0x006b:
            if (r5 <= r6) goto L_0x006e
            goto L_0x0092
        L_0x006e:
            char[] r9 = r12.q
            int[] r10 = r12.s
            r10 = r10[r5]
            int r10 = r10 + r3
            int r10 = r10 + r13
            char r9 = r9[r10]
            int r9 = r9 - r4
            if (r9 != 0) goto L_0x0090
            int[] r9 = r12.s
            r9 = r9[r5]
            int[] r10 = r12.s
            int[] r11 = r12.s
            r11 = r11[r7]
            r10[r5] = r11
            int[] r10 = r12.s
            r10[r7] = r9
            int r7 = r7 + 1
        L_0x008d:
            int r5 = r5 + 1
            goto L_0x006b
        L_0x0090:
            if (r9 <= 0) goto L_0x008d
        L_0x0092:
            if (r5 <= r6) goto L_0x0095
            goto L_0x00b9
        L_0x0095:
            char[] r9 = r12.q
            int[] r10 = r12.s
            r10 = r10[r6]
            int r10 = r10 + r3
            int r10 = r10 + r13
            char r9 = r9[r10]
            int r9 = r9 - r4
            if (r9 != 0) goto L_0x00b7
            int[] r9 = r12.s
            r9 = r9[r6]
            int[] r10 = r12.s
            int[] r11 = r12.s
            r11 = r11[r8]
            r10[r6] = r11
            int[] r10 = r12.s
            r10[r8] = r9
            int r8 = r8 + -1
        L_0x00b4:
            int r6 = r6 + -1
            goto L_0x0092
        L_0x00b7:
            if (r9 >= 0) goto L_0x00b4
        L_0x00b9:
            if (r5 <= r6) goto L_0x011f
            if (r8 >= r7) goto L_0x00cf
            r4 = r1[r14]
            r4.a = r15
            r15 = r1[r14]
            r15.b = r2
            r15 = r1[r14]
            int r3 = r3 + 1
            r15.c = r3
            int r14 = r14 + 1
            goto L_0x0021
        L_0x00cf:
            int r4 = r7 - r15
            int r9 = r5 - r7
            if (r4 >= r9) goto L_0x00d6
            goto L_0x00d7
        L_0x00d6:
            r4 = r9
        L_0x00d7:
            int r9 = r5 - r4
            r12.b(r15, r9, r4)
            int r4 = r2 - r8
            int r6 = r8 - r6
            if (r4 >= r6) goto L_0x00e3
            goto L_0x00e4
        L_0x00e3:
            r4 = r6
        L_0x00e4:
            int r8 = r2 - r4
            int r8 = r8 + r13
            r12.b(r5, r8, r4)
            int r5 = r5 + r15
            int r5 = r5 - r7
            int r5 = r5 - r13
            int r4 = r2 - r6
            int r4 = r4 + r13
            r6 = r1[r14]
            r6.a = r15
            r15 = r1[r14]
            r15.b = r5
            r15 = r1[r14]
            r15.c = r3
            int r14 = r14 + 1
            r15 = r1[r14]
            int r5 = r5 + r13
            r15.a = r5
            r15 = r1[r14]
            int r5 = r4 + -1
            r15.b = r5
            r15 = r1[r14]
            int r5 = r3 + 1
            r15.c = r5
            int r14 = r14 + r13
            r15 = r1[r14]
            r15.a = r4
            r15 = r1[r14]
            r15.b = r2
            r15 = r1[r14]
            r15.c = r3
            int r14 = r14 + r13
            goto L_0x0021
        L_0x011f:
            int[] r9 = r12.s
            r9 = r9[r5]
            int[] r10 = r12.s
            int[] r11 = r12.s
            r11 = r11[r6]
            r10[r5] = r11
            int[] r10 = r12.s
            r10[r6] = r9
            int r5 = r5 + 1
            int r6 = r6 + -1
            goto L_0x006b
        L_0x0135:
            r12.a(r15, r2, r3)
            int r15 = r12.y
            int r2 = r12.z
            if (r15 <= r2) goto L_0x0021
            boolean r15 = r12.A
            if (r15 == 0) goto L_0x0021
        L_0x0142:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2OutputStream.c(int, int, int):void");
    }

    private boolean c(int i2, int i3) {
        int i4 = i2 + 1;
        char c2 = this.q[i4];
        int i5 = i3 + 1;
        char c3 = this.q[i5];
        if (c2 != c3) {
            return c2 > c3;
        }
        int i6 = i4 + 1;
        char c4 = this.q[i6];
        int i7 = i5 + 1;
        char c5 = this.q[i7];
        if (c4 != c5) {
            return c4 > c5;
        }
        int i8 = i6 + 1;
        char c6 = this.q[i8];
        int i9 = i7 + 1;
        char c7 = this.q[i9];
        if (c6 != c7) {
            return c6 > c7;
        }
        int i10 = i8 + 1;
        char c8 = this.q[i10];
        int i11 = i9 + 1;
        char c9 = this.q[i11];
        if (c8 != c9) {
            return c8 > c9;
        }
        int i12 = i10 + 1;
        char c10 = this.q[i12];
        int i13 = i11 + 1;
        char c11 = this.q[i13];
        if (c10 != c11) {
            return c10 > c11;
        }
        int i14 = i12 + 1;
        char c12 = this.q[i14];
        int i15 = i13 + 1;
        char c13 = this.q[i15];
        if (c12 != c13) {
            return c12 > c13;
        }
        int i16 = this.a + 1;
        do {
            int i17 = i14 + 1;
            char c14 = this.q[i17];
            int i18 = i15 + 1;
            char c15 = this.q[i18];
            if (c14 != c15) {
                return c14 > c15;
            }
            int i19 = this.r[i14];
            int i20 = this.r[i15];
            if (i19 != i20) {
                return i19 > i20;
            }
            int i21 = i17 + 1;
            char c16 = this.q[i21];
            int i22 = i18 + 1;
            char c17 = this.q[i22];
            if (c16 != c17) {
                return c16 > c17;
            }
            int i23 = this.r[i17];
            int i24 = this.r[i18];
            if (i23 != i24) {
                return i23 > i24;
            }
            int i25 = i21 + 1;
            char c18 = this.q[i25];
            int i26 = i22 + 1;
            char c19 = this.q[i26];
            if (c18 != c19) {
                return c18 > c19;
            }
            int i27 = this.r[i21];
            int i28 = this.r[i22];
            if (i27 != i28) {
                return i27 > i28;
            }
            int i29 = i25 + 1;
            char c20 = this.q[i29];
            int i30 = i26 + 1;
            char c21 = this.q[i30];
            if (c20 != c21) {
                return c20 > c21;
            }
            int i31 = this.r[i25];
            int i32 = this.r[i26];
            if (i31 != i32) {
                return i31 > i32;
            }
            if (i29 > this.a) {
                i29 = (i29 - this.a) - 1;
            }
            i14 = i29;
            if (i30 > this.a) {
                i30 = (i30 - this.a) - 1;
            }
            i15 = i30;
            i16 -= 4;
            this.y++;
        } while (i16 >= 0);
        return false;
    }

    private void d() {
        this.e = 0;
        this.B = 0;
        a(104);
        a(this.c + 48);
        this.F = 0;
    }

    private void e() {
        this.h.a();
        this.a = -1;
        for (int i2 = 0; i2 < 256; i2++) {
            this.k[i2] = false;
        }
        this.G = (this.c * BZip2Constants.baseBlockSize) - 20;
    }

    private void f() {
        this.E = this.h.b();
        this.F = (this.F << 1) | (this.F >>> 31);
        this.F ^= this.E;
        m();
        a(49);
        a(65);
        a(89);
        a(38);
        a(83);
        a(89);
        b(this.E);
        if (this.d) {
            a(1, 1);
            this.B++;
        } else {
            a(1, 0);
        }
        j();
    }

    private void g() {
        a(23);
        a(114);
        a(69);
        a(56);
        a(80);
        a((int) CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA);
        b(this.F);
        h();
    }

    private void h() {
        while (this.g > 0) {
            try {
                this.H.write(this.f >> 24);
                this.f <<= 8;
                this.g -= 8;
                this.e++;
            } catch (IOException e2) {
                throw e2;
            }
        }
    }

    protected static void hbMakeCodeLengths(char[] cArr, int[] iArr, int i2, int i3) {
        int i4 = i2;
        int i5 = 260;
        int[] iArr2 = new int[260];
        int[] iArr3 = new int[516];
        int[] iArr4 = new int[516];
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = 1;
            if (i7 >= i4) {
                break;
            }
            int i9 = i7 + 1;
            if (iArr[i7] != 0) {
                i8 = iArr[i7];
            }
            iArr3[i9] = i8 << 8;
            i7 = i9;
        }
        while (true) {
            iArr2[i6] = i6;
            iArr3[i6] = i6;
            iArr4[i6] = -2;
            int i10 = 0;
            for (int i11 = 1; i11 <= i4; i11++) {
                iArr4[i11] = -1;
                i10++;
                iArr2[i10] = i11;
                int i12 = iArr2[i10];
                int i13 = i10;
                while (true) {
                    int i14 = i13 >> 1;
                    if (iArr3[i12] >= iArr3[iArr2[i14]]) {
                        break;
                    }
                    iArr2[i13] = iArr2[i14];
                    i13 = i14;
                }
                iArr2[i13] = i12;
            }
            if (i10 >= i5) {
                a();
            }
            int i15 = i4;
            while (i10 > 1) {
                int i16 = iArr2[1];
                iArr2[1] = iArr2[i10];
                int i17 = i10 - 1;
                int i18 = iArr2[1];
                int i19 = 1;
                while (true) {
                    int i20 = i19 << 1;
                    if (i20 > i17) {
                        break;
                    }
                    if (i20 < i17) {
                        int i21 = i20 + 1;
                        if (iArr3[iArr2[i21]] < iArr3[iArr2[i20]]) {
                            i20 = i21;
                        }
                    }
                    if (iArr3[i18] < iArr3[iArr2[i20]]) {
                        break;
                    }
                    iArr2[i19] = iArr2[i20];
                    i19 = i20;
                }
                iArr2[i19] = i18;
                int i22 = iArr2[1];
                iArr2[1] = iArr2[i17];
                int i23 = i17 - 1;
                int i24 = iArr2[1];
                int i25 = 1;
                while (true) {
                    int i26 = i25 << 1;
                    if (i26 > i23) {
                        break;
                    }
                    if (i26 < i23) {
                        int i27 = i26 + 1;
                        if (iArr3[iArr2[i27]] < iArr3[iArr2[i26]]) {
                            i26 = i27;
                        }
                    }
                    if (iArr3[i24] < iArr3[iArr2[i26]]) {
                        break;
                    }
                    iArr2[i25] = iArr2[i26];
                    i25 = i26;
                }
                iArr2[i25] = i24;
                i15++;
                iArr4[i22] = i15;
                iArr4[i16] = i15;
                iArr3[i15] = ((((iArr3[i16] & 255) > (iArr3[i22] & 255) ? iArr3[i16] : iArr3[i22]) & 255) + 1) | ((iArr3[i16] & InputDeviceCompat.SOURCE_ANY) + (iArr3[i22] & InputDeviceCompat.SOURCE_ANY));
                iArr4[i15] = -1;
                i10 = i23 + 1;
                iArr2[i10] = i15;
                int i28 = iArr2[i10];
                int i29 = i10;
                while (true) {
                    int i30 = i29 >> 1;
                    if (iArr3[i28] >= iArr3[iArr2[i30]]) {
                        break;
                    }
                    iArr2[i29] = iArr2[i30];
                    i29 = i30;
                }
                iArr2[i29] = i28;
            }
            if (i15 >= 516) {
                a();
            }
            boolean z2 = false;
            for (int i31 = 1; i31 <= i4; i31++) {
                int i32 = i31;
                int i33 = 0;
                while (iArr4[i32] >= 0) {
                    i32 = iArr4[i32];
                    i33++;
                }
                cArr[i31 - 1] = (char) i33;
                if (i33 > i3) {
                    z2 = true;
                }
            }
            int i34 = i3;
            if (z2) {
                for (int i35 = 1; i35 < i4; i35++) {
                    iArr3[i35] = (((iArr3[i35] >> 8) / 2) + 1) << 8;
                }
                i5 = 260;
                i6 = 0;
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r10v0 */
    /* JADX WARNING: type inference failed for: r3v4, types: [short[]] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r3v8, types: [char[]] */
    /* JADX WARNING: type inference failed for: r3v9, types: [int, char] */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12, types: [int] */
    /* JADX WARNING: type inference failed for: r3v13, types: [int] */
    /* JADX WARNING: type inference failed for: r3v14, types: [int] */
    /* JADX WARNING: type inference failed for: r3v15, types: [int] */
    /* JADX WARNING: type inference failed for: r10v6, types: [short] */
    /* JADX WARNING: type inference failed for: r11v16 */
    /* JADX WARNING: type inference failed for: r11v17 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r15v6, types: [short] */
    /* JADX WARNING: type inference failed for: r11v22 */
    /* JADX WARNING: type inference failed for: r11v23, types: [short] */
    /* JADX WARNING: type inference failed for: r15v7, types: [int, short] */
    /* JADX WARNING: type inference failed for: r10v19 */
    /* JADX WARNING: type inference failed for: r10v23 */
    /* JADX WARNING: type inference failed for: r10v24 */
    /* JADX WARNING: type inference failed for: r10v25 */
    /* JADX WARNING: type inference failed for: r3v42 */
    /* JADX WARNING: type inference failed for: r3v43 */
    /* JADX WARNING: type inference failed for: r3v44 */
    /* JADX WARNING: type inference failed for: r3v45 */
    /* JADX WARNING: type inference failed for: r10v26 */
    /* JADX WARNING: type inference failed for: r10v27 */
    /* JADX WARNING: type inference failed for: r11v30 */
    /* JADX WARNING: type inference failed for: r11v31 */
    /* JADX WARNING: type inference failed for: r10v28 */
    /* JADX WARNING: type inference failed for: r11v32 */
    /* JADX WARNING: type inference failed for: r11v33 */
    /* JADX WARNING: type inference failed for: r10v29 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r3v9, types: [int, char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=null, for r11v23, types: [short] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=null, for r15v6, types: [short] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=null, for r15v7, types: [int, short] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=short[], code=null, for r3v4, types: [short[]] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r11v22
      assigns: []
      uses: []
      mth insns count: 391
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 19 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
            r27 = this;
            r6 = r27
            r0 = 258(0x102, float:3.62E-43)
            r1 = 6
            int[] r2 = new int[]{r1, r0}
            java.lang.Class<char> r3 = char.class
            java.lang.Object r2 = java.lang.reflect.Array.newInstance(r3, r2)
            r7 = r2
            char[][] r7 = (char[][]) r7
            int r2 = r6.l
            r8 = 2
            int r9 = r2 + 2
            r10 = 0
            r2 = 0
        L_0x0019:
            r11 = 15
            if (r2 >= r1) goto L_0x002a
            r3 = 0
        L_0x001e:
            if (r3 >= r9) goto L_0x0027
            r4 = r7[r2]
            r4[r3] = r11
            int r3 = r3 + 1
            goto L_0x001e
        L_0x0027:
            int r2 = r2 + 1
            goto L_0x0019
        L_0x002a:
            int r2 = r6.v
            if (r2 > 0) goto L_0x0031
            a()
        L_0x0031:
            int r2 = r6.v
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 >= r3) goto L_0x0039
            r14 = 2
            goto L_0x0052
        L_0x0039:
            int r2 = r6.v
            r3 = 600(0x258, float:8.41E-43)
            if (r2 >= r3) goto L_0x0041
            r14 = 3
            goto L_0x0052
        L_0x0041:
            int r2 = r6.v
            r3 = 1200(0x4b0, float:1.682E-42)
            if (r2 >= r3) goto L_0x0049
            r14 = 4
            goto L_0x0052
        L_0x0049:
            int r2 = r6.v
            r3 = 2400(0x960, float:3.363E-42)
            if (r2 >= r3) goto L_0x0051
            r14 = 5
            goto L_0x0052
        L_0x0051:
            r14 = 6
        L_0x0052:
            int r2 = r6.v
            r3 = r2
            r2 = r14
            r5 = 0
        L_0x0057:
            r15 = 1
            if (r2 <= 0) goto L_0x00a2
            int r12 = r3 / r2
            int r17 = r5 + -1
            r4 = r17
            r13 = 0
        L_0x0061:
            if (r13 >= r12) goto L_0x0071
            int r0 = r9 + -1
            if (r4 >= r0) goto L_0x0071
            int r4 = r4 + 1
            int[] r0 = r6.w
            r0 = r0[r4]
            int r13 = r13 + r0
            r0 = 258(0x102, float:3.62E-43)
            goto L_0x0061
        L_0x0071:
            if (r4 <= r5) goto L_0x0083
            if (r2 == r14) goto L_0x0083
            if (r2 == r15) goto L_0x0083
            int r0 = r14 - r2
            int r0 = r0 % r8
            if (r0 != r15) goto L_0x0083
            int[] r0 = r6.w
            r0 = r0[r4]
            int r13 = r13 - r0
            int r4 = r4 + -1
        L_0x0083:
            r0 = 0
        L_0x0084:
            if (r0 >= r9) goto L_0x009a
            if (r0 < r5) goto L_0x0091
            if (r0 > r4) goto L_0x0091
            int r12 = r2 + -1
            r12 = r7[r12]
            r12[r0] = r10
            goto L_0x0097
        L_0x0091:
            int r12 = r2 + -1
            r12 = r7[r12]
            r12[r0] = r11
        L_0x0097:
            int r0 = r0 + 1
            goto L_0x0084
        L_0x009a:
            int r2 = r2 + -1
            int r5 = r4 + 1
            int r3 = r3 - r13
            r0 = 258(0x102, float:3.62E-43)
            goto L_0x0057
        L_0x00a2:
            int[] r2 = new int[]{r1, r0}
            java.lang.Class<int> r0 = int.class
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r0, r2)
            int[][] r0 = (int[][]) r0
            int[] r2 = new int[r1]
            short[] r3 = new short[r1]
            r4 = 0
            r12 = 0
        L_0x00b4:
            r13 = 20
            r5 = 4
            if (r4 >= r5) goto L_0x01ca
            r5 = 0
        L_0x00ba:
            if (r5 >= r14) goto L_0x00c1
            r2[r5] = r10
            int r5 = r5 + 1
            goto L_0x00ba
        L_0x00c1:
            r5 = 0
        L_0x00c2:
            if (r5 >= r14) goto L_0x00d1
            r12 = 0
        L_0x00c5:
            if (r12 >= r9) goto L_0x00ce
            r17 = r0[r5]
            r17[r12] = r10
            int r12 = r12 + 1
            goto L_0x00c5
        L_0x00ce:
            int r5 = r5 + 1
            goto L_0x00c2
        L_0x00d1:
            r5 = 0
            r12 = 0
        L_0x00d3:
            int r11 = r6.v
            if (r5 < r11) goto L_0x00eb
            r5 = 0
        L_0x00d8:
            if (r5 >= r14) goto L_0x00e5
            r11 = r7[r5]
            r8 = r0[r5]
            hbMakeCodeLengths(r11, r8, r9, r13)
            int r5 = r5 + 1
            r8 = 2
            goto L_0x00d8
        L_0x00e5:
            int r4 = r4 + 1
            r8 = 2
            r11 = 15
            goto L_0x00b4
        L_0x00eb:
            int r8 = r5 + 50
            int r8 = r8 - r15
            int r11 = r6.v
            if (r8 < r11) goto L_0x00f5
            int r8 = r6.v
            int r8 = r8 - r15
        L_0x00f5:
            r11 = 0
        L_0x00f6:
            if (r11 >= r14) goto L_0x00fd
            r3[r11] = r10
            int r11 = r11 + 1
            goto L_0x00f6
        L_0x00fd:
            if (r14 != r1) goto L_0x016f
            r11 = r5
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
        L_0x010c:
            if (r11 > r8) goto L_0x015c
            short[] r13 = r6.t
            short r13 = r13[r11]
            r23 = r7[r10]
            char r23 = r23[r13]
            int r1 = r17 + r23
            short r1 = (short) r1
            r17 = r7[r15]
            char r17 = r17[r13]
            int r15 = r18 + r17
            short r15 = (short) r15
            r17 = 2
            r18 = r7[r17]
            char r17 = r18[r13]
            int r10 = r19 + r17
            short r10 = (short) r10
            r17 = 3
            r18 = r7[r17]
            char r17 = r18[r13]
            r25 = r1
            int r1 = r20 + r17
            short r1 = (short) r1
            r17 = 4
            r18 = r7[r17]
            char r17 = r18[r13]
            r26 = r1
            int r1 = r21 + r17
            short r1 = (short) r1
            r16 = 5
            r17 = r7[r16]
            char r13 = r17[r13]
            int r13 = r22 + r13
            short r13 = (short) r13
            int r11 = r11 + 1
            r21 = r1
            r19 = r10
            r22 = r13
            r18 = r15
            r17 = r25
            r20 = r26
            r1 = 6
            r10 = 0
            r13 = 20
            r15 = 1
            goto L_0x010c
        L_0x015c:
            r1 = 0
            r3[r1] = r17
            r1 = 1
            r3[r1] = r18
            r1 = 2
            r3[r1] = r19
            r1 = 3
            r3[r1] = r20
            r1 = 4
            r3[r1] = r21
            r10 = 5
            r3[r10] = r22
            goto L_0x018b
        L_0x016f:
            r1 = 4
            r10 = r5
        L_0x0171:
            if (r10 > r8) goto L_0x018b
            short[] r11 = r6.t
            short r11 = r11[r10]
            r13 = 0
        L_0x0178:
            if (r13 >= r14) goto L_0x0188
            short r15 = r3[r13]
            r17 = r7[r13]
            char r17 = r17[r11]
            int r15 = r15 + r17
            short r15 = (short) r15
            r3[r13] = r15
            int r13 = r13 + 1
            goto L_0x0178
        L_0x0188:
            int r10 = r10 + 1
            goto L_0x0171
        L_0x018b:
            r10 = 999999999(0x3b9ac9ff, float:0.004723787)
            r11 = -1
            r10 = 0
            r11 = 999999999(0x3b9ac9ff, float:0.004723787)
            r13 = -1
        L_0x0194:
            if (r10 >= r14) goto L_0x01a0
            short r15 = r3[r10]
            if (r15 >= r11) goto L_0x019d
            short r11 = r3[r10]
            r13 = r10
        L_0x019d:
            int r10 = r10 + 1
            goto L_0x0194
        L_0x01a0:
            r10 = r2[r13]
            r11 = 1
            int r10 = r10 + r11
            r2[r13] = r10
            char[] r10 = r6.o
            char r11 = (char) r13
            r10[r12] = r11
            int r12 = r12 + 1
        L_0x01ad:
            if (r5 > r8) goto L_0x01c0
            r10 = r0[r13]
            short[] r11 = r6.t
            short r11 = r11[r5]
            r15 = r10[r11]
            r17 = 1
            int r15 = r15 + 1
            r10[r11] = r15
            int r5 = r5 + 1
            goto L_0x01ad
        L_0x01c0:
            int r5 = r8 + 1
            r1 = 6
            r8 = 2
            r10 = 0
            r13 = 20
            r15 = 1
            goto L_0x00d3
        L_0x01ca:
            r0 = 0
            int[][] r0 = (int[][]) r0
            r0 = 8
            if (r14 < r0) goto L_0x01d4
            a()
        L_0x01d4:
            r0 = 32768(0x8000, float:4.5918E-41)
            if (r12 >= r0) goto L_0x01e0
            r0 = 18002(0x4652, float:2.5226E-41)
            if (r12 <= r0) goto L_0x01de
            goto L_0x01e0
        L_0x01de:
            r0 = 6
            goto L_0x01e4
        L_0x01e0:
            a()
            goto L_0x01de
        L_0x01e4:
            char[] r1 = new char[r0]
            r0 = 0
        L_0x01e7:
            if (r0 >= r14) goto L_0x01ef
            char r2 = (char) r0
            r1[r0] = r2
            int r0 = r0 + 1
            goto L_0x01e7
        L_0x01ef:
            r0 = 0
        L_0x01f0:
            if (r0 >= r12) goto L_0x020f
            char[] r2 = r6.o
            char r2 = r2[r0]
            r24 = 0
            char r3 = r1[r24]
            r4 = 0
        L_0x01fb:
            if (r2 == r3) goto L_0x0205
            int r4 = r4 + 1
            char r5 = r1[r4]
            r1[r4] = r3
            r3 = r5
            goto L_0x01fb
        L_0x0205:
            r1[r24] = r3
            char[] r2 = r6.p
            char r3 = (char) r4
            r2[r0] = r3
            int r0 = r0 + 1
            goto L_0x01f0
        L_0x020f:
            r0 = 6
            r2 = 258(0x102, float:3.62E-43)
            int[] r0 = new int[]{r0, r2}
            java.lang.Class<int> r1 = int.class
            java.lang.Object r0 = java.lang.reflect.Array.newInstance(r1, r0)
            r8 = r0
            int[][] r8 = (int[][]) r8
            r10 = 0
        L_0x0220:
            if (r10 >= r14) goto L_0x025a
            r0 = 32
            r0 = 0
            r3 = 32
            r4 = 0
        L_0x0228:
            if (r0 >= r9) goto L_0x0241
            r1 = r7[r10]
            char r1 = r1[r0]
            if (r1 <= r4) goto L_0x0234
            r1 = r7[r10]
            char r4 = r1[r0]
        L_0x0234:
            r1 = r7[r10]
            char r1 = r1[r0]
            if (r1 >= r3) goto L_0x023e
            r1 = r7[r10]
            char r3 = r1[r0]
        L_0x023e:
            int r0 = r0 + 1
            goto L_0x0228
        L_0x0241:
            r11 = 20
            if (r4 <= r11) goto L_0x0248
            a()
        L_0x0248:
            r0 = 1
            if (r3 >= r0) goto L_0x024e
            a()
        L_0x024e:
            r1 = r8[r10]
            r2 = r7[r10]
            r0 = r6
            r5 = r9
            r0.a(r1, r2, r3, r4, r5)
            int r10 = r10 + 1
            goto L_0x0220
        L_0x025a:
            r0 = 16
            boolean[] r1 = new boolean[r0]
            r2 = 0
        L_0x025f:
            if (r2 >= r0) goto L_0x0279
            r3 = 0
            r1[r2] = r3
            r3 = 0
        L_0x0265:
            if (r3 >= r0) goto L_0x0276
            boolean[] r4 = r6.k
            int r5 = r2 * 16
            int r5 = r5 + r3
            boolean r4 = r4[r5]
            if (r4 == 0) goto L_0x0273
            r4 = 1
            r1[r2] = r4
        L_0x0273:
            int r3 = r3 + 1
            goto L_0x0265
        L_0x0276:
            int r2 = r2 + 1
            goto L_0x025f
        L_0x0279:
            r2 = 0
        L_0x027a:
            if (r2 >= r0) goto L_0x028d
            boolean r3 = r1[r2]
            if (r3 == 0) goto L_0x0285
            r3 = 1
            r6.a(r3, r3)
            goto L_0x028a
        L_0x0285:
            r3 = 1
            r4 = 0
            r6.a(r3, r4)
        L_0x028a:
            int r2 = r2 + 1
            goto L_0x027a
        L_0x028d:
            r2 = 0
        L_0x028e:
            if (r2 >= r0) goto L_0x02b0
            boolean r3 = r1[r2]
            if (r3 == 0) goto L_0x02ad
            r3 = 0
        L_0x0295:
            if (r3 >= r0) goto L_0x02ad
            boolean[] r4 = r6.k
            int r5 = r2 * 16
            int r5 = r5 + r3
            boolean r4 = r4[r5]
            if (r4 == 0) goto L_0x02a5
            r4 = 1
            r6.a(r4, r4)
            goto L_0x02aa
        L_0x02a5:
            r4 = 1
            r5 = 0
            r6.a(r4, r5)
        L_0x02aa:
            int r3 = r3 + 1
            goto L_0x0295
        L_0x02ad:
            int r2 = r2 + 1
            goto L_0x028e
        L_0x02b0:
            r2 = 3
            r6.a(r2, r14)
            r0 = 15
            r6.a(r0, r12)
            r0 = 0
        L_0x02ba:
            if (r0 >= r12) goto L_0x02d2
            r1 = 0
        L_0x02bd:
            char[] r2 = r6.p
            char r2 = r2[r0]
            if (r1 >= r2) goto L_0x02ca
            r2 = 1
            r6.a(r2, r2)
            int r1 = r1 + 1
            goto L_0x02bd
        L_0x02ca:
            r1 = 0
            r2 = 1
            r6.a(r2, r1)
            int r0 = r0 + 1
            goto L_0x02ba
        L_0x02d2:
            r1 = 0
            r0 = 0
        L_0x02d4:
            if (r0 >= r14) goto L_0x030d
            r2 = r7[r0]
            char r2 = r2[r1]
            r1 = 5
            r6.a(r1, r2)
            r3 = r2
            r2 = 0
        L_0x02e0:
            if (r2 >= r9) goto L_0x0306
        L_0x02e2:
            r4 = r7[r0]
            char r4 = r4[r2]
            if (r3 >= r4) goto L_0x02ef
            r4 = 2
            r6.a(r4, r4)
            int r3 = r3 + 1
            goto L_0x02e2
        L_0x02ef:
            r4 = 2
        L_0x02f0:
            r5 = r7[r0]
            char r5 = r5[r2]
            if (r3 <= r5) goto L_0x02fd
            r5 = 3
            r6.a(r4, r5)
            int r3 = r3 + -1
            goto L_0x02f0
        L_0x02fd:
            r5 = 3
            r10 = 1
            r11 = 0
            r6.a(r10, r11)
            int r2 = r2 + 1
            goto L_0x02e0
        L_0x0306:
            r4 = 2
            r5 = 3
            r11 = 0
            int r0 = r0 + 1
            r1 = 0
            goto L_0x02d4
        L_0x030d:
            r11 = 0
            r0 = 0
        L_0x030f:
            int r1 = r6.v
            if (r11 < r1) goto L_0x0319
            if (r0 == r12) goto L_0x0318
            a()
        L_0x0318:
            return
        L_0x0319:
            int r1 = r11 + 50
            r2 = 1
            int r1 = r1 - r2
            int r3 = r6.v
            if (r1 < r3) goto L_0x0324
            int r1 = r6.v
            int r1 = r1 - r2
        L_0x0324:
            if (r11 > r1) goto L_0x0344
            char[] r3 = r6.o
            char r3 = r3[r0]
            r3 = r7[r3]
            short[] r4 = r6.t
            short r4 = r4[r11]
            char r3 = r3[r4]
            char[] r4 = r6.o
            char r4 = r4[r0]
            r4 = r8[r4]
            short[] r5 = r6.t
            short r5 = r5[r11]
            r4 = r4[r5]
            r6.a(r3, r4)
            int r11 = r11 + 1
            goto L_0x0324
        L_0x0344:
            int r11 = r1 + 1
            int r0 = r0 + 1
            goto L_0x030f
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2OutputStream.i():void");
    }

    private void j() {
        b(24, this.b);
        o();
        i();
    }

    private void k() {
        int i2;
        int i3;
        int i4;
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        boolean[] zArr = new boolean[256];
        int i5 = 0;
        while (true) {
            i2 = 2;
            i3 = 20;
            if (i5 >= 20) {
                break;
            }
            this.q[this.a + i5 + 2] = this.q[(i5 % (this.a + 1)) + 1];
            i5++;
        }
        for (int i6 = 0; i6 <= this.a + 20; i6++) {
            this.r[i6] = 0;
        }
        this.q[0] = this.q[this.a + 1];
        if (this.a < 4000) {
            for (int i7 = 0; i7 <= this.a; i7++) {
                this.s[i7] = i7;
            }
            this.A = false;
            this.z = 0;
            this.y = 0;
            a(0, this.a, 0);
            return;
        }
        for (int i8 = 0; i8 <= 255; i8++) {
            zArr[i8] = false;
        }
        for (int i9 = 0; i9 <= 65536; i9++) {
            this.u[i9] = 0;
        }
        char c2 = this.q[0];
        int i10 = 0;
        while (i10 <= this.a) {
            i10++;
            char c3 = this.q[i10];
            int[] iArr3 = this.u;
            int i11 = (c2 << 8) + c3;
            iArr3[i11] = iArr3[i11] + 1;
            c2 = c3;
        }
        for (int i12 = 1; i12 <= 65536; i12++) {
            int[] iArr4 = this.u;
            iArr4[i12] = iArr4[i12] + this.u[i12 - 1];
        }
        char c4 = this.q[1];
        int i13 = 0;
        while (i13 < this.a) {
            char c5 = this.q[i13 + 2];
            int i14 = (c4 << 8) + c5;
            int[] iArr5 = this.u;
            iArr5[i14] = iArr5[i14] - 1;
            this.s[this.u[i14]] = i13;
            i13++;
            c4 = c5;
        }
        int i15 = (this.q[this.a + 1] << 8) + this.q[1];
        int[] iArr6 = this.u;
        iArr6[i15] = iArr6[i15] - 1;
        this.s[this.u[i15]] = this.a;
        for (int i16 = 0; i16 <= 255; i16++) {
            iArr[i16] = i16;
        }
        int i17 = 1;
        do {
            i17 = (i17 * 3) + 1;
        } while (i17 <= 256);
        while (true) {
            i17 /= 3;
            for (int i18 = i17; i18 <= 255; i18++) {
                int i19 = iArr[i18];
                int i20 = i18;
                while (true) {
                    i4 = i20 - i17;
                    if (this.u[(iArr[i4] + 1) << 8] - this.u[iArr[i4] << 8] <= this.u[(i19 + 1) << 8] - this.u[i19 << 8]) {
                        i4 = i20;
                        break;
                    }
                    iArr[i20] = iArr[i4];
                    if (i4 <= i17 - 1) {
                        break;
                    }
                    i20 = i4;
                }
                iArr[i4] = i19;
            }
            if (i17 == 1) {
                break;
            }
        }
        int i21 = 0;
        while (i21 <= 255) {
            int i22 = iArr[i21];
            for (int i23 = 0; i23 <= 255; i23++) {
                int i24 = (i22 << 8) + i23;
                if ((this.u[i24] & 2097152) != 2097152) {
                    int i25 = this.u[i24] & CLEARMASK;
                    int i26 = (CLEARMASK & this.u[i24 + 1]) - 1;
                    if (i26 > i25) {
                        c(i25, i26, i2);
                        if (this.y > this.z && this.A) {
                            return;
                        }
                    }
                    int[] iArr7 = this.u;
                    iArr7[i24] = 2097152 | iArr7[i24];
                }
            }
            zArr[i22] = true;
            if (i21 < 255) {
                int i27 = this.u[i22 << 8] & CLEARMASK;
                int i28 = (this.u[(i22 + 1) << 8] & CLEARMASK) - i27;
                int i29 = 0;
                while ((i28 >> i29) > 65534) {
                    i29++;
                }
                int i30 = 0;
                while (i30 < i28) {
                    int i31 = this.s[i27 + i30];
                    int i32 = i30 >> i29;
                    this.r[i31] = i32;
                    if (i31 < i3) {
                        this.r[i31 + this.a + 1] = i32;
                    }
                    i30++;
                    i3 = 20;
                }
                if (((i28 - 1) >> i29) > 65535) {
                    a();
                }
            }
            for (int i33 = 0; i33 <= 255; i33++) {
                iArr2[i33] = this.u[(i33 << 8) + i22] & CLEARMASK;
            }
            for (int i34 = this.u[i22 << 8] & CLEARMASK; i34 < (this.u[(i22 + 1) << 8] & CLEARMASK); i34++) {
                char c6 = this.q[this.s[i34]];
                if (!zArr[c6]) {
                    this.s[iArr2[c6]] = this.s[i34] == 0 ? this.a : this.s[i34] - 1;
                    iArr2[c6] = iArr2[c6] + 1;
                }
            }
            for (int i35 = 0; i35 <= 255; i35++) {
                int[] iArr8 = this.u;
                int i36 = (i35 << 8) + i22;
                iArr8[i36] = iArr8[i36] | 2097152;
            }
            i21++;
            i2 = 2;
            i3 = 20;
        }
    }

    private void l() {
        for (int i2 = 0; i2 < 256; i2++) {
            this.k[i2] = false;
        }
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 <= this.a) {
            if (i4 == 0) {
                i4 = (char) rNums[i5];
                i5++;
                if (i5 == 512) {
                    i5 = 0;
                }
            }
            i4--;
            char[] cArr = this.q;
            i3++;
            cArr[i3] = (char) (cArr[i3] ^ (i4 == 1 ? (char) 1 : 0));
            char[] cArr2 = this.q;
            cArr2[i3] = (char) (cArr2[i3] & 255);
            this.k[this.q[i3]] = true;
        }
    }

    private void m() {
        this.z = this.x * this.a;
        int i2 = 0;
        this.y = 0;
        this.d = false;
        this.A = true;
        k();
        if (this.y > this.z && this.A) {
            l();
            this.y = 0;
            this.z = 0;
            this.d = true;
            this.A = false;
            k();
        }
        this.b = -1;
        while (true) {
            if (i2 > this.a) {
                break;
            } else if (this.s[i2] == 0) {
                this.b = i2;
                break;
            } else {
                i2++;
            }
        }
        if (this.b == -1) {
            a();
        }
    }

    private void n() {
        int i2 = this.c * BZip2Constants.baseBlockSize;
        this.q = new char[(i2 + 1 + 20)];
        this.r = new int[(i2 + 20)];
        this.s = new int[i2];
        this.u = new int[NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY];
        if (!(this.q == null || this.r == null || this.s == null)) {
            int[] iArr = this.u;
        }
        this.t = new short[(i2 * 2)];
    }

    private void o() {
        char[] cArr = new char[256];
        b();
        int i2 = this.l + 1;
        for (int i3 = 0; i3 <= i2; i3++) {
            this.w[i3] = 0;
        }
        for (int i4 = 0; i4 < this.l; i4++) {
            cArr[i4] = (char) i4;
        }
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 <= this.a; i7++) {
            char c2 = this.n[this.q[this.s[i7]]];
            char c3 = cArr[0];
            int i8 = 0;
            while (c2 != c3) {
                i8++;
                char c4 = cArr[i8];
                cArr[i8] = c3;
                c3 = c4;
            }
            cArr[0] = c3;
            if (i8 == 0) {
                i5++;
            } else {
                if (i5 > 0) {
                    int i9 = i5 - 1;
                    while (true) {
                        switch (i9 % 2) {
                            case 0:
                                this.t[i6] = 0;
                                i6++;
                                int[] iArr = this.w;
                                iArr[0] = iArr[0] + 1;
                                break;
                            case 1:
                                this.t[i6] = 1;
                                i6++;
                                int[] iArr2 = this.w;
                                iArr2[1] = iArr2[1] + 1;
                                break;
                        }
                        if (i9 < 2) {
                            i5 = 0;
                        } else {
                            i9 = (i9 - 2) / 2;
                        }
                    }
                }
                int i10 = i8 + 1;
                this.t[i6] = (short) i10;
                i6++;
                int[] iArr3 = this.w;
                iArr3[i10] = iArr3[i10] + 1;
            }
        }
        if (i5 > 0) {
            int i11 = i5 - 1;
            while (true) {
                switch (i11 % 2) {
                    case 0:
                        this.t[i6] = 0;
                        i6++;
                        int[] iArr4 = this.w;
                        iArr4[0] = iArr4[0] + 1;
                        break;
                    case 1:
                        this.t[i6] = 1;
                        i6++;
                        int[] iArr5 = this.w;
                        iArr5[1] = iArr5[1] + 1;
                        break;
                }
                if (i11 >= 2) {
                    i11 = (i11 - 2) / 2;
                }
            }
        }
        this.t[i6] = (short) i2;
        int i12 = i6 + 1;
        int[] iArr6 = this.w;
        iArr6[i2] = iArr6[i2] + 1;
        this.v = i12;
    }

    public void close() {
        if (!this.i) {
            finish();
            this.i = true;
            super.close();
            this.H.close();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        close();
        super.finalize();
    }

    public void finish() {
        if (!this.j) {
            if (this.D > 0) {
                c();
            }
            this.C = -1;
            f();
            g();
            this.j = true;
            flush();
        }
    }

    public void flush() {
        super.flush();
        this.H.flush();
    }

    public void write(int i2) {
        int i3;
        int i4 = (i2 + 256) % 256;
        if (this.C == -1) {
            this.C = i4;
            i3 = this.D + 1;
        } else if (this.C == i4) {
            this.D++;
            if (this.D > 254) {
                c();
                this.C = -1;
                i3 = 0;
            }
        } else {
            c();
            this.D = 1;
            this.C = i4;
            return;
        }
        this.D = i3;
    }
}
