package org.bouncycastle.apache.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class CBZip2InputStream extends InputStream implements BZip2Constants {
    private int[][] A = ((int[][]) Array.newInstance(int.class, new int[]{6, BZip2Constants.MAX_ALPHA_SIZE}));
    private int[][] B = ((int[][]) Array.newInstance(int.class, new int[]{6, BZip2Constants.MAX_ALPHA_SIZE}));
    private int[][] C = ((int[][]) Array.newInstance(int.class, new int[]{6, BZip2Constants.MAX_ALPHA_SIZE}));
    private int[] D = new int[6];
    private InputStream E;
    private boolean F = false;
    private int G = -1;
    private int H = 1;
    private int I;
    private int J;
    private int K;
    private int L;
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g = 0;
    int h = 0;
    int i;
    char j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private int o;
    private int p;
    private CRC q = new CRC();
    private boolean[] r = new boolean[256];
    private int s;
    private char[] t = new char[256];
    private char[] u = new char[256];
    private char[] v = new char[BZip2Constants.MAX_SELECTORS];
    private char[] w = new char[BZip2Constants.MAX_SELECTORS];
    private int[] x = null;
    private char[] y = null;
    private int[] z = new int[256];

    public CBZip2InputStream(InputStream inputStream) {
        a(inputStream);
        d();
        e();
        q();
    }

    private int a(int i2) {
        while (this.p < i2) {
            char c2 = 0;
            try {
                c2 = (char) this.E.read();
            } catch (IOException unused) {
                b();
            }
            if (c2 == 65535) {
                b();
            }
            this.o = (c2 & 255) | (this.o << 8);
            this.p += 8;
        }
        int i3 = (this.o >> (this.p - i2)) & ((1 << i2) - 1);
        this.p -= i2;
        return i3;
    }

    private static void a() {
        System.out.println("CRC Error");
    }

    private void a(InputStream inputStream) {
        this.E = inputStream;
        this.p = 0;
        this.o = 0;
    }

    private void a(int[] iArr, int[] iArr2, int[] iArr3, char[] cArr, int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = i2;
        int i7 = 0;
        while (i6 <= i3) {
            int i8 = i7;
            for (int i9 = 0; i9 < i4; i9++) {
                if (cArr[i9] == i6) {
                    iArr3[i8] = i9;
                    i8++;
                }
            }
            i6++;
            i7 = i8;
        }
        for (int i10 = 0; i10 < 23; i10++) {
            iArr2[i10] = 0;
        }
        for (int i11 = 0; i11 < i4; i11++) {
            int i12 = cArr[i11] + 1;
            iArr2[i12] = iArr2[i12] + 1;
        }
        for (int i13 = 1; i13 < 23; i13++) {
            iArr2[i13] = iArr2[i13] + iArr2[i13 - 1];
        }
        for (int i14 = 0; i14 < 23; i14++) {
            iArr[i14] = 0;
        }
        int i15 = i2;
        while (i15 <= i3) {
            int i16 = i15 + 1;
            int i17 = i5 + (iArr2[i16] - iArr2[i15]);
            iArr[i15] = i17 - 1;
            i5 = i17 << 1;
            i15 = i16;
        }
        for (int i18 = i2 + 1; i18 <= i3; i18++) {
            iArr2[i18] = ((iArr[i18 - 1] + 1) << 1) - iArr2[i18];
        }
    }

    private int b(int i2) {
        return a(i2);
    }

    private static void b() {
        a();
    }

    private void c() {
        this.s = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (this.r[i2]) {
                this.t[this.s] = (char) i2;
                this.u[i2] = (char) this.s;
                this.s++;
            }
        }
    }

    private void c(int i2) {
        if (i2 >= 0 && i2 <= 9 && this.m >= 0) {
            int i3 = this.m;
        }
        this.m = i2;
        if (i2 != 0) {
            int i4 = i2 * BZip2Constants.baseBlockSize;
            this.y = new char[i4];
            this.x = new int[i4];
        }
    }

    private void d() {
        char l2 = l();
        char l3 = l();
        if (l2 == 'B' || l3 == 'Z') {
            char l4 = l();
            char l5 = l();
            if (l4 != 'h' || l5 < '1' || l5 > '9') {
                k();
                this.F = true;
                return;
            }
            c(l5 - '0');
            this.L = 0;
            return;
        }
        throw new IOException("Not a BZIP2 marked stream");
    }

    private void e() {
        char l2 = l();
        char l3 = l();
        char l4 = l();
        char l5 = l();
        char l6 = l();
        char l7 = l();
        if (l2 == 23 && l3 == 'r' && l4 == 'E' && l5 == '8' && l6 == 'P' && l7 == 144) {
            g();
        } else if (l2 == '1' && l3 == 'A' && l4 == 'Y' && l5 == '&' && l6 == 'S' && l7 == 'Y') {
            this.I = n();
            if (a(1) == 1) {
                this.n = true;
            } else {
                this.n = false;
            }
            p();
            this.q.a();
            this.H = 1;
        } else {
            i();
            this.F = true;
        }
    }

    private void f() {
        this.K = this.q.b();
        if (this.I != this.K) {
            j();
        }
        this.L = (this.L << 1) | (this.L >>> 31);
        this.L ^= this.K;
    }

    private void g() {
        this.J = n();
        if (this.J != this.L) {
            j();
        }
        k();
        this.F = true;
    }

    private static void h() {
        a();
    }

    private static void i() {
        a();
    }

    private static void j() {
        a();
    }

    private void k() {
        try {
            if (this.E != null && this.E != System.in) {
                this.E.close();
                this.E = null;
            }
        } catch (IOException unused) {
        }
    }

    private char l() {
        return (char) a(8);
    }

    private int m() {
        return a(8) | ((((((a(8) | 0) << 8) | a(8)) << 8) | a(8)) << 8);
    }

    private int n() {
        return m();
    }

    /* JADX WARNING: type inference failed for: r7v10, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r6v5, types: [int, char] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() {
        /*
            r15 = this;
            r0 = 6
            r1 = 258(0x102, float:3.62E-43)
            int[] r1 = new int[]{r0, r1}
            java.lang.Class<char> r2 = char.class
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r2, r1)
            char[][] r1 = (char[][]) r1
            r2 = 16
            boolean[] r3 = new boolean[r2]
            r4 = 0
            r5 = 0
        L_0x0015:
            r6 = 1
            if (r5 >= r2) goto L_0x0026
            int r7 = r15.a(r6)
            if (r7 != r6) goto L_0x0021
            r3[r5] = r6
            goto L_0x0023
        L_0x0021:
            r3[r5] = r4
        L_0x0023:
            int r5 = r5 + 1
            goto L_0x0015
        L_0x0026:
            r5 = 0
        L_0x0027:
            r7 = 256(0x100, float:3.59E-43)
            if (r5 >= r7) goto L_0x0032
            boolean[] r7 = r15.r
            r7[r5] = r4
            int r5 = r5 + 1
            goto L_0x0027
        L_0x0032:
            r5 = 0
        L_0x0033:
            if (r5 >= r2) goto L_0x004f
            boolean r7 = r3[r5]
            if (r7 == 0) goto L_0x004c
            r7 = 0
        L_0x003a:
            if (r7 >= r2) goto L_0x004c
            int r8 = r15.a(r6)
            if (r8 != r6) goto L_0x0049
            boolean[] r8 = r15.r
            int r9 = r5 * 16
            int r9 = r9 + r7
            r8[r9] = r6
        L_0x0049:
            int r7 = r7 + 1
            goto L_0x003a
        L_0x004c:
            int r5 = r5 + 1
            goto L_0x0033
        L_0x004f:
            r15.c()
            int r2 = r15.s
            int r2 = r2 + 2
            r3 = 3
            int r3 = r15.a(r3)
            r5 = 15
            int r5 = r15.a(r5)
            r7 = 0
        L_0x0062:
            if (r7 >= r5) goto L_0x0076
            r8 = 0
        L_0x0065:
            int r9 = r15.a(r6)
            if (r9 != r6) goto L_0x006e
            int r8 = r8 + 1
            goto L_0x0065
        L_0x006e:
            char[] r9 = r15.w
            char r8 = (char) r8
            r9[r7] = r8
            int r7 = r7 + 1
            goto L_0x0062
        L_0x0076:
            char[] r0 = new char[r0]
            r7 = 0
        L_0x0079:
            if (r7 >= r3) goto L_0x0081
            r0[r7] = r7
            int r7 = r7 + 1
            char r7 = (char) r7
            goto L_0x0079
        L_0x0081:
            r7 = 0
        L_0x0082:
            if (r7 >= r5) goto L_0x009d
            char[] r8 = r15.w
            char r8 = r8[r7]
            char r9 = r0[r8]
        L_0x008a:
            if (r8 <= 0) goto L_0x0094
            int r10 = r8 + -1
            char r11 = r0[r10]
            r0[r8] = r11
            char r8 = (char) r10
            goto L_0x008a
        L_0x0094:
            r0[r4] = r9
            char[] r8 = r15.v
            r8[r7] = r9
            int r7 = r7 + 1
            goto L_0x0082
        L_0x009d:
            r0 = 0
        L_0x009e:
            if (r0 >= r3) goto L_0x00c6
            r5 = 5
            int r5 = r15.a(r5)
            r7 = r5
            r5 = 0
        L_0x00a7:
            if (r5 >= r2) goto L_0x00c3
        L_0x00a9:
            int r8 = r15.a(r6)
            if (r8 != r6) goto L_0x00bb
            int r8 = r15.a(r6)
            if (r8 != 0) goto L_0x00b8
            int r7 = r7 + 1
            goto L_0x00a9
        L_0x00b8:
            int r7 = r7 + -1
            goto L_0x00a9
        L_0x00bb:
            r8 = r1[r0]
            char r9 = (char) r7
            r8[r5] = r9
            int r5 = r5 + 1
            goto L_0x00a7
        L_0x00c3:
            int r0 = r0 + 1
            goto L_0x009e
        L_0x00c6:
            r0 = 0
        L_0x00c7:
            if (r0 >= r3) goto L_0x0104
            r5 = 32
            r5 = 0
            r6 = 32
            r13 = 0
        L_0x00cf:
            if (r5 >= r2) goto L_0x00e9
            r7 = r1[r0]
            char r7 = r7[r5]
            if (r7 <= r13) goto L_0x00dc
            r7 = r1[r0]
            char r7 = r7[r5]
            r13 = r7
        L_0x00dc:
            r7 = r1[r0]
            char r7 = r7[r5]
            if (r7 >= r6) goto L_0x00e6
            r6 = r1[r0]
            char r6 = r6[r5]
        L_0x00e6:
            int r5 = r5 + 1
            goto L_0x00cf
        L_0x00e9:
            int[][] r5 = r15.A
            r8 = r5[r0]
            int[][] r5 = r15.B
            r9 = r5[r0]
            int[][] r5 = r15.C
            r10 = r5[r0]
            r11 = r1[r0]
            r7 = r15
            r12 = r6
            r14 = r2
            r7.a(r8, r9, r10, r11, r12, r13, r14)
            int[] r5 = r15.D
            r5[r0] = r6
            int r0 = r0 + 1
            goto L_0x00c7
        L_0x0104:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2InputStream.o():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0159  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0172  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void p() {
        /*
            r17 = this;
            r0 = r17
            r1 = 256(0x100, float:3.59E-43)
            char[] r1 = new char[r1]
            int r2 = r0.m
            r3 = 100000(0x186a0, float:1.4013E-40)
            int r2 = r2 * r3
            r3 = 24
            int r3 = r0.b(r3)
            r0.l = r3
            r17.o()
            int r3 = r0.s
            r4 = 1
            int r3 = r3 + r4
            r5 = 0
            r6 = 0
        L_0x001e:
            r7 = 255(0xff, float:3.57E-43)
            if (r6 > r7) goto L_0x0029
            int[] r7 = r0.z
            r7[r6] = r5
            int r6 = r6 + 1
            goto L_0x001e
        L_0x0029:
            r6 = 0
        L_0x002a:
            if (r6 > r7) goto L_0x0032
            char r8 = (char) r6
            r1[r6] = r8
            int r6 = r6 + 1
            goto L_0x002a
        L_0x0032:
            r6 = -1
            r0.k = r6
            r8 = 49
            char[] r9 = r0.v
            char r9 = r9[r5]
            int[] r10 = r0.D
            r10 = r10[r9]
            int r11 = r0.a(r10)
        L_0x0043:
            int[][] r12 = r0.A
            r12 = r12[r9]
            r12 = r12[r10]
            if (r11 <= r12) goto L_0x0082
            int r10 = r10 + 1
        L_0x004d:
            int r12 = r0.p
            if (r12 >= r4) goto L_0x0072
            java.io.InputStream r12 = r0.E     // Catch:{ IOException -> 0x0059 }
            int r12 = r12.read()     // Catch:{ IOException -> 0x0059 }
            char r12 = (char) r12
            goto L_0x005d
        L_0x0059:
            b()
            r12 = 0
        L_0x005d:
            if (r12 != r6) goto L_0x0062
            b()
        L_0x0062:
            int r13 = r0.o
            int r13 = r13 << 8
            r12 = r12 & 255(0xff, float:3.57E-43)
            r12 = r12 | r13
            r0.o = r12
            int r12 = r0.p
            int r12 = r12 + 8
            r0.p = r12
            goto L_0x004d
        L_0x0072:
            int r12 = r0.o
            int r13 = r0.p
            int r13 = r13 - r4
            int r12 = r12 >> r13
            r12 = r12 & r4
            int r13 = r0.p
            int r13 = r13 - r4
            r0.p = r13
            int r11 = r11 << 1
            r11 = r11 | r12
            goto L_0x0043
        L_0x0082:
            int[][] r12 = r0.C
            r12 = r12[r9]
            int[][] r13 = r0.B
            r9 = r13[r9]
            r9 = r9[r10]
            int r11 = r11 - r9
            r9 = r12[r11]
            r10 = 0
        L_0x0090:
            if (r9 != r3) goto L_0x0093
            return
        L_0x0093:
            r11 = 50
            if (r9 == 0) goto L_0x0146
            if (r9 != r4) goto L_0x009b
            goto L_0x0146
        L_0x009b:
            int r12 = r0.k
            int r12 = r12 + r4
            r0.k = r12
            int r12 = r0.k
            if (r12 < r2) goto L_0x00a7
            h()
        L_0x00a7:
            int r9 = r9 + -1
            char r12 = r1[r9]
            int[] r13 = r0.z
            char[] r14 = r0.t
            char r14 = r14[r12]
            r15 = r13[r14]
            int r15 = r15 + r4
            r13[r14] = r15
            char[] r13 = r0.y
            int r14 = r0.k
            char[] r15 = r0.t
            char r15 = r15[r12]
            r13[r14] = r15
        L_0x00c0:
            r13 = 3
            if (r9 <= r13) goto L_0x00de
            int r13 = r9 + -1
            char r14 = r1[r13]
            r1[r9] = r14
            int r14 = r9 + -2
            char r15 = r1[r14]
            r1[r13] = r15
            int r13 = r9 + -3
            char r15 = r1[r13]
            r1[r14] = r15
            int r14 = r9 + -4
            char r14 = r1[r14]
            r1[r13] = r14
            int r9 = r9 + -4
            goto L_0x00c0
        L_0x00de:
            if (r9 <= 0) goto L_0x00e9
            int r13 = r9 + -1
            char r13 = r1[r13]
            r1[r9] = r13
            int r9 = r9 + -1
            goto L_0x00de
        L_0x00e9:
            r1[r5] = r12
            if (r8 != 0) goto L_0x00f1
            int r10 = r10 + 1
            r8 = 50
        L_0x00f1:
            int r8 = r8 + r6
            char[] r9 = r0.v
            char r9 = r9[r10]
            int[] r11 = r0.D
            r11 = r11[r9]
            int r12 = r0.a(r11)
        L_0x00fe:
            int[][] r13 = r0.A
            r13 = r13[r9]
            r13 = r13[r11]
            if (r12 <= r13) goto L_0x0137
            int r11 = r11 + 1
        L_0x0108:
            int r13 = r0.p
            if (r13 >= r4) goto L_0x0127
            java.io.InputStream r13 = r0.E     // Catch:{ IOException -> 0x0114 }
            int r13 = r13.read()     // Catch:{ IOException -> 0x0114 }
            char r13 = (char) r13
            goto L_0x0118
        L_0x0114:
            b()
            r13 = 0
        L_0x0118:
            int r14 = r0.o
            int r14 = r14 << 8
            r13 = r13 & r7
            r13 = r13 | r14
            r0.o = r13
            int r13 = r0.p
            int r13 = r13 + 8
            r0.p = r13
            goto L_0x0108
        L_0x0127:
            int r13 = r0.o
            int r14 = r0.p
            int r14 = r14 - r4
            int r13 = r13 >> r14
            r13 = r13 & r4
            int r14 = r0.p
            int r14 = r14 - r4
            r0.p = r14
            int r12 = r12 << 1
            r12 = r12 | r13
            goto L_0x00fe
        L_0x0137:
            int[][] r13 = r0.C
            r13 = r13[r9]
            int[][] r14 = r0.B
            r9 = r14[r9]
            r9 = r9[r11]
            int r12 = r12 - r9
            r9 = r13[r12]
            goto L_0x0090
        L_0x0146:
            r12 = r8
            r13 = r10
            r8 = -1
            r10 = 1
        L_0x014a:
            if (r9 != 0) goto L_0x0150
            int r9 = r10 * 1
        L_0x014e:
            int r8 = r8 + r9
            goto L_0x0155
        L_0x0150:
            if (r9 != r4) goto L_0x0155
            int r9 = r10 * 2
            goto L_0x014e
        L_0x0155:
            int r10 = r10 * 2
            if (r12 != 0) goto L_0x015d
            int r13 = r13 + 1
            r12 = 50
        L_0x015d:
            int r12 = r12 + r6
            char[] r9 = r0.v
            char r9 = r9[r13]
            int[] r14 = r0.D
            r14 = r14[r9]
            int r15 = r0.a(r14)
        L_0x016a:
            int[][] r7 = r0.A
            r7 = r7[r9]
            r7 = r7[r14]
            if (r15 <= r7) goto L_0x01ac
            int r14 = r14 + 1
        L_0x0174:
            int r7 = r0.p
            if (r7 >= r4) goto L_0x019a
            java.io.InputStream r7 = r0.E     // Catch:{ IOException -> 0x0180 }
            int r7 = r7.read()     // Catch:{ IOException -> 0x0180 }
            char r7 = (char) r7
            goto L_0x0184
        L_0x0180:
            b()
            r7 = 0
        L_0x0184:
            if (r7 != r6) goto L_0x0189
            b()
        L_0x0189:
            int r6 = r0.o
            int r6 = r6 << 8
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6 | r7
            r0.o = r6
            int r6 = r0.p
            int r6 = r6 + 8
            r0.p = r6
            r6 = -1
            goto L_0x0174
        L_0x019a:
            int r6 = r0.o
            int r7 = r0.p
            int r7 = r7 - r4
            int r6 = r6 >> r7
            r6 = r6 & r4
            int r7 = r0.p
            int r7 = r7 - r4
            r0.p = r7
            int r7 = r15 << 1
            r15 = r7 | r6
            r6 = -1
            goto L_0x016a
        L_0x01ac:
            int[][] r6 = r0.C
            r6 = r6[r9]
            int[][] r7 = r0.B
            r7 = r7[r9]
            r7 = r7[r14]
            int r15 = r15 - r7
            r9 = r6[r15]
            if (r9 == 0) goto L_0x01ea
            if (r9 == r4) goto L_0x01ea
            int r8 = r8 + 1
            char[] r6 = r0.t
            char r7 = r1[r5]
            char r6 = r6[r7]
            int[] r7 = r0.z
            r10 = r7[r6]
            int r10 = r10 + r8
            r7[r6] = r10
        L_0x01cc:
            if (r8 <= 0) goto L_0x01dc
            int r7 = r0.k
            int r7 = r7 + r4
            r0.k = r7
            char[] r7 = r0.y
            int r10 = r0.k
            r7[r10] = r6
            int r8 = r8 + -1
            goto L_0x01cc
        L_0x01dc:
            int r6 = r0.k
            if (r6 < r2) goto L_0x01e3
            h()
        L_0x01e3:
            r8 = r12
            r10 = r13
            r6 = -1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x0090
        L_0x01ea:
            r6 = -1
            r7 = 255(0xff, float:3.57E-43)
            goto L_0x014a
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.apache.bzip2.CBZip2InputStream.p():void");
    }

    private void q() {
        int[] iArr = new int[257];
        iArr[0] = 0;
        this.e = 1;
        while (this.e <= 256) {
            iArr[this.e] = this.z[this.e - 1];
            this.e++;
        }
        this.e = 1;
        while (this.e <= 256) {
            int i2 = this.e;
            iArr[i2] = iArr[i2] + iArr[this.e - 1];
            this.e++;
        }
        this.e = 0;
        while (this.e <= this.k) {
            char c2 = this.y[this.e];
            this.x[iArr[c2]] = this.e;
            iArr[c2] = iArr[c2] + 1;
            this.e++;
        }
        this.f = this.x[this.l];
        this.b = 0;
        this.a = 0;
        this.d = 256;
        if (this.n) {
            this.g = 0;
            this.h = 0;
            r();
            return;
        }
        s();
    }

    private void r() {
        if (this.a <= this.k) {
            this.c = this.d;
            this.d = this.y[this.f];
            this.f = this.x[this.f];
            int i2 = 0;
            if (this.g == 0) {
                this.g = rNums[this.h];
                this.h++;
                if (this.h == 512) {
                    this.h = 0;
                }
            }
            this.g--;
            int i3 = this.d;
            if (this.g == 1) {
                i2 = 1;
            }
            this.d = i3 ^ i2;
            this.a++;
            this.G = this.d;
            this.H = 3;
            this.q.a(this.d);
            return;
        }
        f();
        e();
        q();
    }

    private void s() {
        if (this.a <= this.k) {
            this.c = this.d;
            this.d = this.y[this.f];
            this.f = this.x[this.f];
            this.a++;
            this.G = this.d;
            this.H = 6;
            this.q.a(this.d);
            return;
        }
        f();
        e();
        q();
    }

    private void t() {
        char c2 = 1;
        if (this.d != this.c) {
            this.H = 2;
            this.b = 1;
        } else {
            this.b++;
            if (this.b >= 4) {
                this.j = this.y[this.f];
                this.f = this.x[this.f];
                if (this.g == 0) {
                    this.g = rNums[this.h];
                    this.h++;
                    if (this.h == 512) {
                        this.h = 0;
                    }
                }
                this.g--;
                char c3 = this.j;
                if (this.g != 1) {
                    c2 = 0;
                }
                this.j = (char) (c3 ^ c2);
                this.i = 0;
                this.H = 4;
                u();
                return;
            }
            this.H = 2;
        }
        r();
    }

    private void u() {
        if (this.i < this.j) {
            this.G = this.d;
            this.q.a(this.d);
            this.i++;
            return;
        }
        this.H = 2;
        this.a++;
        this.b = 0;
        r();
    }

    private void v() {
        if (this.d != this.c) {
            this.H = 5;
            this.b = 1;
        } else {
            this.b++;
            if (this.b >= 4) {
                this.j = this.y[this.f];
                this.f = this.x[this.f];
                this.H = 7;
                this.i = 0;
                w();
                return;
            }
            this.H = 5;
        }
        s();
    }

    private void w() {
        if (this.i < this.j) {
            this.G = this.d;
            this.q.a(this.d);
            this.i++;
            return;
        }
        this.H = 5;
        this.a++;
        this.b = 0;
        s();
    }

    public int read() {
        if (this.F) {
            return -1;
        }
        int i2 = this.G;
        switch (this.H) {
            case 1:
            case 2:
            case 5:
                break;
            case 3:
                t();
                break;
            case 4:
                u();
                return i2;
            case 6:
                v();
                return i2;
            case 7:
                w();
                return i2;
            default:
                return i2;
        }
        return i2;
    }
}
