package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.TweakableBlockCipherParameters;

public class ThreefishEngine implements BlockCipher {
    public static final int BLOCKSIZE_1024 = 1024;
    public static final int BLOCKSIZE_256 = 256;
    public static final int BLOCKSIZE_512 = 512;
    /* access modifiers changed from: private */
    public static int[] a = new int[80];
    /* access modifiers changed from: private */
    public static int[] b = new int[a.length];
    /* access modifiers changed from: private */
    public static int[] c = new int[a.length];
    /* access modifiers changed from: private */
    public static int[] d = new int[a.length];
    private int e;
    private int f;
    private long[] g;
    private long[] h = new long[5];
    private long[] i;
    private ThreefishCipher j;
    private boolean k;

    static final class Threefish1024Cipher extends ThreefishCipher {
        public Threefish1024Cipher(long[] jArr, long[] jArr2) {
            super(jArr, jArr2);
        }

        /* JADX WARNING: type inference failed for: r2v0 */
        /* JADX WARNING: type inference failed for: r3v1 */
        /* JADX WARNING: type inference failed for: r2v1 */
        /* JADX WARNING: type inference failed for: r2v39 */
        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(long[] r251, long[] r252) {
            /*
                r250 = this;
                r0 = r250
                r1 = r251
                r2 = r252
                long[] r3 = r0.b
                long[] r4 = r0.a
                int[] r5 = org.bouncycastle.crypto.engines.ThreefishEngine.b
                int[] r6 = org.bouncycastle.crypto.engines.ThreefishEngine.d
                int r7 = r3.length
                r8 = 33
                if (r7 == r8) goto L_0x001d
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                r1.<init>()
                throw r1
            L_0x001d:
                int r7 = r4.length
                r8 = 5
                if (r7 == r8) goto L_0x0027
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                r1.<init>()
                throw r1
            L_0x0027:
                r7 = 0
                r9 = r1[r7]
                r11 = 1
                r12 = r1[r11]
                r14 = 2
                r15 = r1[r14]
                r17 = 3
                r18 = r1[r17]
                r14 = 4
                r20 = r1[r14]
                r22 = r1[r8]
                r24 = 6
                r25 = r1[r24]
                r27 = 7
                r28 = r1[r27]
                r8 = 8
                r30 = r1[r8]
                r8 = 9
                r32 = r1[r8]
                r8 = 10
                r34 = r1[r8]
                r36 = 11
                r37 = r1[r36]
                r8 = 12
                r39 = r1[r8]
                r8 = 13
                r41 = r1[r8]
                r43 = 14
                r44 = r1[r43]
                r46 = 15
                r46 = r1[r46]
                r48 = r3[r7]
                long r50 = r9 + r48
                r9 = r3[r11]
                long r48 = r12 + r9
                r1 = 2
                r9 = r3[r1]
                long r12 = r15 + r9
                r9 = r3[r17]
                long r15 = r18 + r9
                r9 = r3[r14]
                long r18 = r20 + r9
                r1 = 5
                r9 = r3[r1]
                long r20 = r22 + r9
                r9 = r3[r24]
                long r22 = r25 + r9
                r9 = r3[r27]
                long r25 = r28 + r9
                r1 = 8
                r9 = r3[r1]
                long r28 = r30 + r9
                r1 = 9
                r9 = r3[r1]
                long r30 = r32 + r9
                r1 = 10
                r9 = r3[r1]
                long r32 = r34 + r9
                r9 = r3[r36]
                long r34 = r37 + r9
                r1 = 12
                r9 = r3[r1]
                long r37 = r39 + r9
                r9 = r3[r8]
                r39 = r4[r7]
                long r52 = r9 + r39
                long r9 = r41 + r52
                r39 = r3[r43]
                r41 = r4[r11]
                long r52 = r39 + r41
                long r39 = r44 + r52
                r1 = 15
                r41 = r3[r1]
                long r44 = r46 + r41
                r62 = r9
                r54 = r20
                r56 = r25
                r58 = r30
                r60 = r34
                r64 = r44
                r9 = r48
                r1 = 1
                r248 = r12
                r12 = r15
                r15 = r248
            L_0x00c9:
                r11 = 20
                if (r1 >= r11) goto L_0x04bd
                r11 = r5[r1]
                r20 = r6[r1]
                r7 = 24
                r66 = r5
                r67 = r6
                long r5 = r50 + r9
                long r9 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r9, r7, r5)
                r68 = r1
                long r0 = r15 + r12
                long r12 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r12, r8, r0)
                r69 = r9
                r14 = r54
                long r8 = r18 + r14
                r7 = 8
                long r14 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r7, r8)
                r10 = 47
                r73 = r3
                r71 = r8
                r7 = r56
                long r2 = r22 + r7
                long r7 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r7, r10, r2)
                r74 = r14
                r9 = r58
                long r14 = r28 + r9
                r76 = r4
                r4 = 8
                long r9 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r9, r4, r14)
                r4 = 17
                r79 = r11
                r80 = r12
                r77 = r14
                r14 = r60
                long r11 = r32 + r14
                long r13 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r4, r11)
                r4 = 22
                r82 = r7
                r84 = r11
                r7 = r62
                long r11 = r37 + r7
                long r7 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r7, r4, r11)
                r4 = 37
                r88 = r2
                r86 = r11
                r11 = r64
                long r2 = r39 + r11
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r11, r4, r2)
                r4 = 38
                r90 = r2
                long r2 = r5 + r9
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r9, r4, r2)
                r6 = 19
                long r9 = r0 + r7
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r7, r6, r9)
                long r6 = r88 + r13
                r8 = 10
                long r13 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r13, r8, r6)
                r8 = 55
                r92 = r4
                long r4 = r71 + r11
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r11, r8, r4)
                r8 = 49
                r94 = r13
                long r13 = r84 + r82
                r96 = r0
                r0 = r82
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r13)
                r8 = 18
                r98 = r13
                long r13 = r86 + r80
                r100 = r11
                r11 = r80
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r11, r8, r13)
                r8 = 23
                r102 = r13
                long r13 = r90 + r74
                r104 = r6
                r6 = r74
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r13)
                r8 = 52
                r106 = r13
                long r13 = r77 + r69
                r108 = r4
                r4 = r69
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r13)
                r8 = 33
                r110 = r13
                long r13 = r2 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r13)
                long r2 = r9 + r6
                r8 = 4
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r2)
                r8 = 51
                long r9 = r108 + r11
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r11, r8, r9)
                r112 = r0
                long r0 = r104 + r4
                r8 = 13
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r0)
                r8 = 34
                r114 = r11
                long r11 = r102 + r100
                r116 = r6
                r6 = r100
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r11)
                r8 = 41
                r118 = r11
                long r11 = r106 + r96
                r120 = r4
                r4 = r96
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r11)
                r8 = 59
                r122 = r11
                long r11 = r110 + r94
                r124 = r9
                r9 = r94
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r9, r8, r11)
                r10 = 17
                r126 = r11
                long r11 = r98 + r92
                r128 = r0
                r0 = r92
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r10, r11)
                r130 = r11
                long r10 = r13 + r6
                r12 = 5
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r12, r10)
                r12 = 20
                long r13 = r2 + r8
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r8, r12, r13)
                r8 = 48
                r132 = r6
                long r6 = r128 + r4
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r6)
                r8 = 41
                r134 = r4
                long r4 = r124 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r4)
                r8 = 47
                r136 = r2
                long r2 = r122 + r120
                r138 = r0
                r0 = r120
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r2)
                r8 = 28
                r140 = r2
                long r2 = r126 + r116
                r142 = r6
                r6 = r116
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r2)
                r8 = 16
                r144 = r2
                long r2 = r130 + r114
                r146 = r6
                r6 = r114
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r2)
                r8 = 25
                r148 = r2
                long r2 = r118 + r112
                r150 = r4
                r4 = r112
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r2)
                r8 = r73[r79]
                long r15 = r10 + r8
                int r11 = r79 + 1
                r8 = r73[r11]
                r152 = r11
                long r10 = r0 + r8
                int r0 = r79 + 2
                r8 = r73[r0]
                long r18 = r13 + r8
                int r1 = r79 + 3
                r8 = r73[r1]
                long r12 = r6 + r8
                int r6 = r79 + 4
                r7 = r73[r6]
                long r21 = r150 + r7
                int r7 = r79 + 5
                r8 = r73[r7]
                r153 = r6
                r154 = r7
                long r6 = r146 + r8
                int r8 = r79 + 6
                r25 = r73[r8]
                long r28 = r142 + r25
                int r9 = r79 + 7
                r25 = r73[r9]
                r155 = r8
                r156 = r9
                long r8 = r4 + r25
                int r4 = r79 + 8
                r25 = r73[r4]
                long r30 = r144 + r25
                int r5 = r79 + 9
                r25 = r73[r5]
                r157 = r4
                r158 = r5
                long r4 = r138 + r25
                int r14 = r79 + 10
                r25 = r73[r14]
                long r32 = r148 + r25
                int r23 = r79 + 11
                r25 = r73[r23]
                r159 = r0
                r160 = r1
                long r0 = r136 + r25
                int r25 = r79 + 12
                r34 = r73[r25]
                long r37 = r2 + r34
                int r2 = r79 + 13
                r34 = r73[r2]
                r39 = r76[r20]
                long r41 = r34 + r39
                r161 = r2
                long r2 = r134 + r41
                int r26 = r79 + 14
                r34 = r73[r26]
                int r39 = r20 + 1
                r41 = r76[r39]
                long r44 = r34 + r41
                long r34 = r140 + r44
                int r40 = r79 + 15
                r41 = r73[r40]
                r163 = r2
                r162 = r14
                r14 = r68
                long r2 = (long) r14
                long r44 = r41 + r2
                r165 = r2
                long r2 = r132 + r44
                r167 = r14
                r14 = 41
                r168 = r2
                long r2 = r15 + r10
                long r10 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r10, r14, r2)
                long r14 = r18 + r12
                r170 = r10
                r10 = 9
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r12, r10, r14)
                r10 = 37
                r172 = r11
                long r11 = r21 + r6
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r10, r11)
                r10 = 31
                r174 = r6
                long r6 = r28 + r8
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r8, r10, r6)
                r176 = r8
                long r8 = r30 + r4
                r10 = 12
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r10, r8)
                r10 = 47
                r178 = r8
                long r8 = r32 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r10, r8)
                r10 = 44
                r180 = r8
                long r8 = r37 + r163
                r182 = r11
                r11 = r163
                long r10 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r11, r10, r8)
                r12 = 30
                r184 = r8
                long r8 = r34 + r168
                r186 = r0
                r0 = r168
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r12, r8)
                r12 = 16
                r188 = r8
                long r8 = r2 + r4
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r12, r8)
                r4 = 34
                long r12 = r14 + r10
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r10, r4, r12)
                r10 = 56
                long r14 = r6 + r186
                r6 = r186
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r10, r14)
                r10 = 51
                r190 = r2
                long r2 = r182 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r10, r2)
                long r10 = r180 + r176
                r194 = r4
                r192 = r6
                r6 = r176
                r4 = 4
                long r5 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r4, r10)
                r4 = 53
                r196 = r10
                long r10 = r184 + r172
                r198 = r0
                r0 = r172
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r4, r10)
                r4 = 42
                r200 = r10
                long r10 = r188 + r174
                r202 = r14
                r14 = r174
                long r14 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r4, r10)
                r4 = 41
                r204 = r10
                long r10 = r178 + r170
                r206 = r0
                r0 = r170
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r4, r10)
                r4 = 31
                r208 = r10
                long r10 = r8 + r5
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r5, r4, r10)
                r6 = 44
                long r7 = r12 + r14
                long r12 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r6, r7)
                r6 = 47
                long r14 = r2 + r206
                r2 = r206
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r2, r6, r14)
                r6 = 46
                r210 = r4
                long r4 = r202 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r6, r4)
                r6 = 19
                r212 = r2
                long r2 = r200 + r198
                r214 = r12
                r12 = r198
                long r12 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r12, r6, r2)
                r6 = 42
                r216 = r2
                long r2 = r204 + r194
                r218 = r0
                r0 = r194
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r6, r2)
                r6 = 44
                r220 = r2
                long r2 = r208 + r192
                r222 = r14
                r14 = r192
                long r14 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r6, r2)
                r6 = 25
                r224 = r2
                long r2 = r196 + r190
                r226 = r0
                r0 = r190
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r6, r2)
                r228 = r2
                long r2 = r10 + r12
                r6 = 9
                long r9 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r12, r6, r2)
                r6 = 48
                long r11 = r7 + r14
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r14, r6, r11)
                r8 = 35
                long r13 = r4 + r226
                r4 = r226
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r4, r8, r13)
                r8 = 52
                r230 = r9
                long r9 = r222 + r0
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r9)
                r8 = 23
                r232 = r4
                long r4 = r220 + r218
                r234 = r6
                r6 = r218
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r6, r8, r4)
                r8 = 31
                r236 = r4
                long r4 = r224 + r214
                r238 = r0
                r0 = r214
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r4)
                r8 = 37
                r240 = r4
                long r4 = r228 + r212
                r242 = r13
                r13 = r212
                long r13 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r13, r8, r4)
                r8 = 20
                r244 = r4
                long r4 = r216 + r210
                r246 = r0
                r0 = r210
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.a(r0, r8, r4)
                r15 = r73[r152]
                long r50 = r2 + r15
                r2 = r73[r159]
                long r15 = r6 + r2
                r2 = r73[r160]
                long r6 = r11 + r2
                r2 = r73[r153]
                long r11 = r13 + r2
                r2 = r73[r154]
                long r18 = r9 + r2
                r2 = r73[r155]
                r8 = r246
                long r54 = r8 + r2
                r2 = r73[r156]
                r8 = r242
                long r13 = r8 + r2
                r2 = r73[r157]
                long r56 = r0 + r2
                r0 = r73[r158]
                r2 = r240
                long r28 = r2 + r0
                r0 = r73[r162]
                r2 = r238
                long r58 = r2 + r0
                r0 = r73[r23]
                r2 = r244
                long r32 = r2 + r0
                r0 = r73[r25]
                long r60 = r234 + r0
                r0 = r73[r161]
                long r37 = r4 + r0
                r0 = r73[r26]
                r2 = r76[r39]
                long r4 = r0 + r2
                long r62 = r232 + r4
                r0 = r73[r40]
                r2 = 2
                int r20 = r20 + 2
                r2 = r76[r20]
                long r4 = r0 + r2
                long r39 = r236 + r4
                int r0 = r79 + 16
                r0 = r73[r0]
                long r2 = r0 + r165
                r0 = 1
                long r4 = r2 + r0
                long r64 = r230 + r4
                int r1 = r167 + 2
                r22 = r13
                r9 = r15
                r5 = r66
                r3 = r73
                r4 = r76
                r0 = r250
                r2 = r252
                r8 = 13
                r14 = 4
                r15 = r6
                r12 = r11
                r6 = r67
                r7 = 0
                goto L_0x00c9
            L_0x04bd:
                r0 = r2
                r1 = r12
                r3 = r15
                r30 = r58
                r14 = r60
                r7 = r62
                r11 = r64
                r5 = 0
                r0[r5] = r50
                r5 = 1
                r0[r5] = r9
                r5 = 2
                r0[r5] = r3
                r0[r17] = r1
                r1 = 4
                r0[r1] = r18
                r1 = 5
                r0[r1] = r54
                r0[r24] = r22
                r0[r27] = r56
                r1 = 8
                r0[r1] = r28
                r1 = 9
                r0[r1] = r30
                r1 = 10
                r0[r1] = r32
                r0[r36] = r14
                r1 = 12
                r0[r1] = r37
                r1 = 13
                r0[r1] = r7
                r0[r43] = r39
                r1 = 15
                r0[r1] = r11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.ThreefishEngine.Threefish1024Cipher.a(long[], long[]):void");
        }

        /* JADX WARNING: type inference failed for: r244v0, types: [long[]] */
        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Incorrect type for immutable var: ssa=long[], code=null, for r244v0, types: [long[]] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b(long[] r243, long[] r244) {
            /*
                r242 = this;
                r0 = r242
                long[] r3 = r0.b
                long[] r4 = r0.a
                int[] r5 = org.bouncycastle.crypto.engines.ThreefishEngine.b
                int[] r6 = org.bouncycastle.crypto.engines.ThreefishEngine.d
                int r7 = r3.length
                r8 = 33
                if (r7 == r8) goto L_0x0019
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                r1.<init>()
                throw r1
            L_0x0019:
                int r7 = r4.length
                r8 = 5
                if (r7 == r8) goto L_0x0023
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                r1.<init>()
                throw r1
            L_0x0023:
                r7 = 0
                r9 = r243[r7]
                r11 = 1
                r12 = r243[r11]
                r14 = 2
                r15 = r243[r14]
                r17 = 3
                r18 = r243[r17]
                r14 = 4
                r20 = r243[r14]
                r22 = r243[r8]
                r24 = 6
                r25 = r243[r24]
                r27 = 7
                r28 = r243[r27]
                r7 = 8
                r31 = r243[r7]
                r7 = 9
                r33 = r243[r7]
                r8 = 10
                r35 = r243[r8]
                r37 = 11
                r38 = r243[r37]
                r8 = 12
                r40 = r243[r8]
                r8 = 13
                r42 = r243[r8]
                r44 = 14
                r45 = r243[r44]
                r47 = 15
                r47 = r243[r47]
                r1 = 19
            L_0x005f:
                if (r1 < r11) goto L_0x0495
                r49 = r5[r1]
                r50 = r6[r1]
                int r51 = r49 + 1
                r52 = r3[r51]
                r54 = r15
                long r14 = r9 - r52
                int r9 = r49 + 2
                r52 = r3[r9]
                r56 = r9
                long r8 = r12 - r52
                int r10 = r49 + 3
                r12 = r3[r10]
                r57 = r8
                long r7 = r54 - r12
                int r9 = r49 + 4
                r12 = r3[r9]
                r59 = r5
                r60 = r6
                long r5 = r18 - r12
                int r12 = r49 + 5
                r18 = r3[r12]
                r61 = r12
                long r11 = r20 - r18
                int r13 = r49 + 6
                r18 = r3[r13]
                r63 = r9
                r62 = r10
                long r9 = r22 - r18
                int r16 = r49 + 7
                r18 = r3[r16]
                r64 = r5
                long r5 = r25 - r18
                int r18 = r49 + 8
                r20 = r3[r18]
                r66 = r9
                long r9 = r28 - r20
                int r19 = r49 + 9
                r20 = r3[r19]
                r68 = r9
                long r9 = r31 - r20
                int r20 = r49 + 10
                r21 = r3[r20]
                r70 = r9
                long r9 = r33 - r21
                int r21 = r49 + 11
                r22 = r3[r21]
                r72 = r9
                long r9 = r35 - r22
                int r22 = r49 + 12
                r25 = r3[r22]
                r74 = r9
                long r9 = r38 - r25
                int r23 = r49 + 13
                r25 = r3[r23]
                r76 = r11
                long r11 = r40 - r25
                int r25 = r49 + 14
                r28 = r3[r25]
                int r26 = r50 + 1
                r31 = r4[r26]
                long r33 = r28 + r31
                r78 = r11
                long r11 = r42 - r33
                int r28 = r49 + 15
                r31 = r3[r28]
                int r29 = r50 + 2
                r33 = r4[r29]
                long r35 = r31 + r33
                r80 = r4
                r81 = r5
                long r4 = r45 - r35
                int r6 = r49 + 16
                r31 = r3[r6]
                r83 = r3
                long r2 = (long) r1
                long r33 = r31 + r2
                r31 = 1
                long r35 = r33 + r31
                r84 = r1
                long r0 = r47 - r35
                r6 = 9
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r6, r14)
                r85 = r2
                long r2 = r14 - r0
                r6 = 48
                long r9 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r9, r6, r7)
                long r14 = r7 - r9
                r6 = 35
                r7 = r81
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r6, r7)
                r87 = r9
                long r9 = r7 - r11
                r6 = 52
                r89 = r11
                r11 = r72
                r7 = r76
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r6, r7)
                r91 = r0
                long r0 = r7 - r11
                r6 = 23
                r7 = r57
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r7, r6, r4)
                r93 = r11
                long r11 = r4 - r6
                r4 = 31
                r97 = r6
                r95 = r11
                r11 = r66
                r5 = r70
                long r7 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r4, r5)
                long r11 = r5 - r7
                r4 = 37
                r99 = r11
                r5 = r64
                r11 = r74
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r5, r4, r11)
                r101 = r9
                long r9 = r11 - r4
                r6 = 20
                r103 = r9
                r11 = r68
                r9 = r78
                long r11 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r6, r9)
                r105 = r0
                long r0 = r9 - r11
                r6 = 31
                long r9 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r6, r2)
                long r11 = r2 - r9
                r2 = 44
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r7, r2, r14)
                long r6 = r14 - r2
                r8 = 47
                r14 = r105
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r8, r14)
                r107 = r2
                long r2 = r14 - r4
                r8 = 46
                r109 = r4
                r4 = r97
                r14 = r101
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r8, r14)
                r111 = r9
                long r8 = r14 - r4
                r10 = 19
                r14 = r91
                long r14 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r10, r0)
                r113 = r4
                long r4 = r0 - r14
                r0 = 42
                r117 = r2
                r115 = r4
                r4 = r89
                r1 = r95
                long r3 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r0, r1)
                r119 = r13
                r120 = r14
                long r13 = r1 - r3
                r0 = 44
                r122 = r13
                r1 = r87
                r13 = r99
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r1, r0, r13)
                r124 = r8
                long r8 = r13 - r0
                r2 = 25
                r126 = r8
                r13 = r93
                r8 = r103
                long r13 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r13, r2, r8)
                r128 = r0
                long r0 = r8 - r13
                r2 = 16
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r13, r2, r11)
                long r13 = r11 - r8
                r2 = 34
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r3, r2, r6)
                long r4 = r6 - r2
                r6 = 56
                r130 = r2
                r10 = r124
                r2 = r128
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r6, r10)
                long r6 = r10 - r2
                r10 = 51
                r132 = r2
                r11 = r117
                r2 = r120
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r10, r11)
                r134 = r8
                long r8 = r11 - r2
                r10 = r111
                r12 = 4
                long r10 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r10, r12, r0)
                r136 = r2
                long r2 = r0 - r10
                r0 = 53
                r138 = r2
                r140 = r6
                r1 = r109
                r6 = r115
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r1, r0, r6)
                long r2 = r6 - r0
                r6 = 42
                r142 = r2
                r144 = r10
                r2 = r107
                r10 = r122
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r6, r10)
                long r6 = r10 - r2
                r10 = 41
                r146 = r6
                r11 = r113
                r6 = r126
                long r10 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r11, r10, r6)
                r148 = r2
                long r2 = r6 - r10
                r6 = 41
                long r6 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r10, r6, r13)
                long r10 = r13 - r6
                r12 = 9
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r12, r4)
                long r12 = r4 - r0
                r4 = 37
                r14 = r148
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r4, r8)
                long r14 = r8 - r4
                r8 = 31
                r150 = r4
                r152 = r14
                r4 = r140
                r14 = r144
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r8, r4)
                long r14 = r4 - r8
                r154 = r8
                r4 = r134
                r8 = 12
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r8, r2)
                long r8 = r2 - r4
                r2 = 47
                r156 = r4
                r158 = r8
                r3 = r132
                r8 = r138
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r3, r2, r8)
                long r4 = r8 - r2
                r8 = 44
                r160 = r2
                r162 = r4
                r2 = r130
                r4 = r142
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r8, r4)
                long r8 = r4 - r2
                r4 = 30
                r164 = r2
                r166 = r8
                r2 = r136
                r8 = r146
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r4, r8)
                long r4 = r8 - r2
                r8 = r83[r49]
                r168 = r2
                long r2 = r10 - r8
                r8 = r83[r51]
                long r10 = r6 - r8
                r6 = r83[r56]
                long r8 = r12 - r6
                r6 = r83[r62]
                long r12 = r0 - r6
                r0 = r83[r63]
                long r6 = r152 - r0
                r0 = r83[r61]
                r170 = r12
                long r12 = r150 - r0
                r0 = r83[r119]
                r172 = r12
                long r12 = r14 - r0
                r0 = r83[r16]
                long r14 = r154 - r0
                r0 = r83[r18]
                r174 = r14
                long r14 = r158 - r0
                r0 = r83[r19]
                r176 = r14
                long r14 = r156 - r0
                r0 = r83[r20]
                r178 = r10
                long r10 = r162 - r0
                r0 = r83[r21]
                r180 = r10
                long r10 = r160 - r0
                r0 = r83[r22]
                r182 = r6
                long r6 = r166 - r0
                r0 = r83[r23]
                r18 = r80[r50]
                long r20 = r0 + r18
                long r0 = r164 - r20
                r18 = r83[r25]
                r20 = r80[r26]
                long r22 = r18 + r20
                r184 = r6
                long r6 = r4 - r22
                r4 = r83[r28]
                long r18 = r4 + r85
                long r4 = r168 - r18
                r186 = r6
                r6 = 5
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r6, r2)
                long r6 = r2 - r4
                r2 = 20
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r10, r2, r8)
                long r10 = r8 - r2
                r8 = 48
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r8, r12)
                long r8 = r12 - r0
                r12 = 41
                r188 = r2
                r2 = r182
                long r12 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r12, r2)
                long r14 = r2 - r12
                r2 = 47
                r192 = r0
                r190 = r12
                r12 = r178
                r0 = r186
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r12, r2, r0)
                long r12 = r0 - r2
                r0 = 28
                r196 = r4
                r194 = r12
                r12 = r172
                r4 = r176
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r12, r0, r4)
                long r12 = r4 - r0
                r4 = 16
                r200 = r2
                r198 = r12
                r12 = r170
                r2 = r180
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r12, r4, r2)
                long r12 = r2 - r4
                r2 = 25
                r204 = r8
                r202 = r12
                r12 = r174
                r8 = r184
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r12, r2, r8)
                long r12 = r8 - r2
                r8 = 33
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r8, r6)
                long r8 = r6 - r2
                r6 = 4
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r6, r10)
                long r6 = r10 - r0
                r10 = 51
                long r4 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r10, r14)
                long r10 = r14 - r4
                r206 = r0
                r208 = r4
                r0 = r200
                r14 = r204
                r4 = 13
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r4, r14)
                long r4 = r14 - r0
                r14 = 34
                r210 = r0
                r0 = r196
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r14, r12)
                long r14 = r12 - r0
                r12 = 41
                r214 = r2
                r212 = r14
                r13 = r192
                r2 = r194
                long r12 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r13, r12, r2)
                long r14 = r2 - r12
                r2 = 59
                r218 = r0
                r216 = r14
                r14 = r188
                r0 = r198
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r2, r0)
                long r14 = r0 - r2
                r0 = 17
                r222 = r10
                r220 = r14
                r14 = r190
                r10 = r202
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r0, r10)
                long r14 = r10 - r0
                r10 = 38
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r10, r8)
                long r10 = r8 - r0
                r8 = 19
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r12, r8, r6)
                long r12 = r6 - r8
                r6 = 10
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r6, r4)
                long r6 = r4 - r2
                r4 = 55
                r226 = r2
                r224 = r8
                r2 = r218
                r8 = r222
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r4, r8)
                long r4 = r8 - r2
                r8 = 49
                r228 = r2
                r2 = r214
                long r2 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r2, r8, r14)
                long r8 = r14 - r2
                r14 = 18
                r232 = r0
                r230 = r8
                r8 = r208
                r0 = r212
                long r8 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r8, r14, r0)
                long r14 = r0 - r8
                r0 = 23
                r236 = r2
                r234 = r14
                r14 = r206
                r1 = r216
                long r14 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r0, r1)
                r238 = r6
                long r6 = r1 - r14
                r0 = 52
                r240 = r6
                r1 = r210
                r6 = r220
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r1, r0, r6)
                long r2 = r6 - r0
                r6 = 24
                long r0 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r0, r6, r10)
                long r6 = r10 - r0
                r10 = 13
                long r18 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r8, r10, r12)
                long r8 = r12 - r18
                r10 = 8
                long r22 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r14, r10, r4)
                long r20 = r4 - r22
                r4 = 47
                r13 = r236
                r11 = r238
                long r28 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r13, r4, r11)
                long r25 = r11 - r28
                r4 = r232
                long r33 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r4, r10, r2)
                long r31 = r2 - r33
                r2 = 17
                r3 = r226
                r14 = r230
                long r38 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r3, r2, r14)
                long r35 = r14 - r38
                r2 = 22
                r3 = r224
                r14 = r234
                long r42 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r3, r2, r14)
                long r40 = r14 - r42
                r2 = 37
                r3 = r228
                r14 = r240
                long r47 = org.bouncycastle.crypto.engines.ThreefishEngine.b(r3, r2, r14)
                long r45 = r14 - r47
                int r2 = r84 + -2
                r12 = r0
                r1 = r2
                r15 = r8
                r5 = r59
                r4 = r80
                r3 = r83
                r0 = r242
                r8 = 13
                r11 = 1
                r14 = 4
                r9 = r6
                r6 = r60
                r7 = 9
                goto L_0x005f
            L_0x0495:
                r83 = r3
                r80 = r4
                r54 = r15
                r0 = 0
                r1 = r83[r0]
                long r3 = r9 - r1
                r0 = 1
                r1 = r83[r0]
                long r5 = r12 - r1
                r0 = 2
                r1 = r83[r0]
                long r15 = r54 - r1
                r0 = r83[r17]
                long r7 = r18 - r0
                r0 = 4
                r1 = r83[r0]
                long r9 = r20 - r1
                r0 = 5
                r1 = r83[r0]
                long r11 = r22 - r1
                r0 = r83[r24]
                long r13 = r25 - r0
                r0 = r83[r27]
                long r18 = r28 - r0
                r0 = 8
                r1 = r83[r0]
                long r20 = r31 - r1
                r0 = 9
                r1 = r83[r0]
                long r22 = r33 - r1
                r0 = 10
                r1 = r83[r0]
                long r25 = r35 - r1
                r0 = r83[r37]
                long r28 = r38 - r0
                r0 = 12
                r1 = r83[r0]
                long r31 = r40 - r1
                r0 = 13
                r1 = r83[r0]
                r0 = 0
                r33 = r80[r0]
                long r35 = r1 + r33
                long r1 = r42 - r35
                r33 = r83[r44]
                r30 = 1
                r35 = r80[r30]
                long r38 = r33 + r35
                long r33 = r45 - r38
                r35 = 15
                r35 = r83[r35]
                long r38 = r47 - r35
                r244[r0] = r3
                r244[r30] = r5
                r0 = 2
                r244[r0] = r15
                r244[r17] = r7
                r0 = 4
                r244[r0] = r9
                r0 = 5
                r244[r0] = r11
                r244[r24] = r13
                r244[r27] = r18
                r0 = 8
                r244[r0] = r20
                r0 = 9
                r244[r0] = r22
                r0 = 10
                r244[r0] = r25
                r244[r37] = r28
                r0 = 12
                r244[r0] = r31
                r0 = 13
                r244[r0] = r1
                r244[r44] = r33
                r0 = 15
                r244[r0] = r38
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.ThreefishEngine.Threefish1024Cipher.b(long[], long[]):void");
        }
    }

    static final class Threefish256Cipher extends ThreefishCipher {
        public Threefish256Cipher(long[] jArr, long[] jArr2) {
            super(jArr, jArr2);
        }

        /* access modifiers changed from: 0000 */
        public void a(long[] jArr, long[] jArr2) {
            long[] jArr3 = this.b;
            long[] jArr4 = this.a;
            int[] a = ThreefishEngine.c;
            int[] b = ThreefishEngine.d;
            if (jArr3.length != 9) {
                throw new IllegalArgumentException();
            } else if (jArr4.length != 5) {
                throw new IllegalArgumentException();
            } else {
                long j = jArr[0];
                long j2 = jArr[1];
                long j3 = jArr[2];
                long j4 = j + jArr3[0];
                long j5 = j2 + jArr3[1] + jArr4[0];
                long j6 = j3 + jArr3[2] + jArr4[1];
                long j7 = jArr[3] + jArr3[3];
                int i = 1;
                while (i < 18) {
                    int i2 = a[i];
                    int i3 = b[i];
                    long j8 = j6;
                    long j9 = j4 + j5;
                    long a2 = ThreefishEngine.a(j5, 14, j9);
                    long j10 = j8 + j7;
                    long a3 = ThreefishEngine.a(j7, 16, j10);
                    int[] iArr = a;
                    int[] iArr2 = b;
                    long j11 = j9 + a3;
                    long a4 = ThreefishEngine.a(a3, 52, j11);
                    long j12 = j10 + a2;
                    long a5 = ThreefishEngine.a(a2, 57, j12);
                    int i4 = i;
                    long j13 = j11 + a5;
                    long a6 = ThreefishEngine.a(a5, 23, j13);
                    long j14 = j12 + a4;
                    long a7 = ThreefishEngine.a(a4, 40, j14);
                    long j15 = j13 + a7;
                    long a8 = ThreefishEngine.a(a7, 5, j15);
                    long j16 = j14 + a6;
                    long j17 = j15 + jArr3[i2];
                    int i5 = i2 + 1;
                    long a9 = ThreefishEngine.a(a6, 37, j16) + jArr3[i5] + jArr4[i3];
                    int i6 = i2 + 2;
                    int i7 = i3 + 1;
                    long j18 = j16 + jArr3[i6] + jArr4[i7];
                    int i8 = i2 + 3;
                    int i9 = i5;
                    int i10 = i4;
                    long j19 = (long) i10;
                    long j20 = j19;
                    long j21 = a8 + jArr3[i8] + j19;
                    int i11 = i2;
                    int i12 = i8;
                    long j22 = j17 + a9;
                    long a10 = ThreefishEngine.a(a9, 25, j22);
                    long j23 = j18 + j21;
                    long a11 = ThreefishEngine.a(j21, 33, j23);
                    long j24 = j22 + a11;
                    long a12 = ThreefishEngine.a(a11, 46, j24);
                    long j25 = j23 + a10;
                    long a13 = ThreefishEngine.a(a10, 12, j25);
                    long j26 = j24 + a13;
                    long a14 = ThreefishEngine.a(a13, 58, j26);
                    long j27 = j25 + a12;
                    long a15 = ThreefishEngine.a(a12, 22, j27);
                    long j28 = j26 + a15;
                    long a16 = ThreefishEngine.a(a15, 32, j28);
                    long j29 = j27 + a14;
                    j4 = j28 + jArr3[i9];
                    long a17 = ThreefishEngine.a(a14, 32, j29) + jArr3[i6] + jArr4[i7];
                    i = i10 + 2;
                    j6 = j29 + jArr3[i12] + jArr4[i3 + 2];
                    j7 = a16 + jArr3[i11 + 4] + j20 + 1;
                    j5 = a17;
                    a = iArr;
                    b = iArr2;
                }
                long j30 = j6;
                jArr2[0] = j4;
                jArr2[1] = j5;
                jArr2[2] = j30;
                jArr2[3] = j7;
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(long[] jArr, long[] jArr2) {
            long[] jArr3 = this.b;
            long[] jArr4 = this.a;
            int[] a = ThreefishEngine.c;
            int[] b = ThreefishEngine.d;
            if (jArr3.length != 9) {
                throw new IllegalArgumentException();
            } else if (jArr4.length != 5) {
                throw new IllegalArgumentException();
            } else {
                long j = jArr[0];
                long j2 = jArr[1];
                long j3 = jArr[2];
                long j4 = jArr[3];
                int i = 17;
                for (int i2 = 1; i >= i2; i2 = 1) {
                    int i3 = a[i];
                    int i4 = b[i];
                    int i5 = i3 + 1;
                    long j5 = j - jArr3[i5];
                    int i6 = i3 + 2;
                    int i7 = i4 + 1;
                    long j6 = j2 - (jArr3[i6] + jArr4[i7]);
                    int i8 = i3 + 3;
                    int i9 = i8;
                    long j7 = j3 - (jArr3[i8] + jArr4[i4 + 2]);
                    int[] iArr = a;
                    int[] iArr2 = b;
                    long j8 = (long) i;
                    int i10 = i;
                    long b2 = ThreefishEngine.b(j4 - ((jArr3[i3 + 4] + j8) + 1), 32, j5);
                    long j9 = j8;
                    long j10 = j5 - b2;
                    long b3 = ThreefishEngine.b(j6, 32, j7);
                    long j11 = j7 - b3;
                    long b4 = ThreefishEngine.b(b3, 58, j10);
                    long j12 = j10 - b4;
                    long b5 = ThreefishEngine.b(b2, 22, j11);
                    long j13 = j11 - b5;
                    long b6 = ThreefishEngine.b(b5, 46, j12);
                    long j14 = j12 - b6;
                    long b7 = ThreefishEngine.b(b4, 12, j13);
                    long j15 = j13 - b7;
                    long b8 = ThreefishEngine.b(b7, 25, j14);
                    long j16 = j14 - b8;
                    long b9 = ThreefishEngine.b(b6, 33, j15);
                    long j17 = j15 - b9;
                    long j18 = b9;
                    long j19 = j16 - jArr3[i3];
                    long j20 = b8 - (jArr3[i5] + jArr4[i4]);
                    long j21 = j17 - (jArr3[i6] + jArr4[i7]);
                    long b10 = ThreefishEngine.b(j18 - (jArr3[i9] + j9), 5, j19);
                    long j22 = j19 - b10;
                    long b11 = ThreefishEngine.b(j20, 37, j21);
                    long j23 = j21 - b11;
                    long b12 = ThreefishEngine.b(b11, 23, j22);
                    long j24 = j22 - b12;
                    long b13 = ThreefishEngine.b(b10, 40, j23);
                    long j25 = j23 - b13;
                    long b14 = ThreefishEngine.b(b13, 52, j24);
                    long j26 = j24 - b14;
                    long b15 = ThreefishEngine.b(b12, 57, j25);
                    long j27 = j25 - b15;
                    j2 = ThreefishEngine.b(b15, 14, j26);
                    long j28 = j26 - j2;
                    j4 = ThreefishEngine.b(b14, 16, j27);
                    j3 = j27 - j4;
                    j = j28;
                    i = i10 - 2;
                    a = iArr;
                    b = iArr2;
                }
                long j29 = j - jArr3[0];
                long j30 = j2 - (jArr3[1] + jArr4[0]);
                long j31 = j3 - (jArr3[2] + jArr4[1]);
                long j32 = j4 - jArr3[3];
                jArr2[0] = j29;
                jArr2[1] = j30;
                jArr2[2] = j31;
                jArr2[3] = j32;
            }
        }
    }

    static final class Threefish512Cipher extends ThreefishCipher {
        protected Threefish512Cipher(long[] jArr, long[] jArr2) {
            super(jArr, jArr2);
        }

        public void a(long[] jArr, long[] jArr2) {
            long[] jArr3 = this.b;
            long[] jArr4 = this.a;
            int[] c = ThreefishEngine.a;
            int[] b = ThreefishEngine.d;
            if (jArr3.length != 17) {
                throw new IllegalArgumentException();
            } else if (jArr4.length != 5) {
                throw new IllegalArgumentException();
            } else {
                long j = jArr[0];
                long j2 = jArr[1];
                long j3 = jArr[2];
                long j4 = jArr[3];
                long j5 = jArr[4];
                long j6 = jArr[5];
                long j7 = jArr[6];
                long j8 = j + jArr3[0];
                long j9 = j2 + jArr3[1];
                long j10 = j3 + jArr3[2];
                long j11 = j4 + jArr3[3];
                long j12 = j5 + jArr3[4];
                long j13 = j7 + jArr3[6] + jArr4[1];
                long j14 = j6 + jArr3[5] + jArr4[0];
                long j15 = jArr[7] + jArr3[7];
                long j16 = j9;
                int i = 1;
                long j17 = j10;
                long j18 = j11;
                long j19 = j17;
                while (i < 18) {
                    int i2 = c[i];
                    int i3 = b[i];
                    int[] iArr = c;
                    int[] iArr2 = b;
                    long j20 = j8 + j16;
                    long a = ThreefishEngine.a(j16, 46, j20);
                    int i4 = i;
                    long j21 = j19 + j18;
                    long a2 = ThreefishEngine.a(j18, 36, j21);
                    long j22 = j12 + j14;
                    long a3 = ThreefishEngine.a(j14, 19, j22);
                    long[] jArr5 = jArr3;
                    long[] jArr6 = jArr4;
                    long j23 = j20;
                    long j24 = j15;
                    long j25 = j13 + j24;
                    long a4 = ThreefishEngine.a(j24, 37, j25);
                    long j26 = j21 + a;
                    long a5 = ThreefishEngine.a(a, 33, j26);
                    long j27 = j22 + a4;
                    long a6 = ThreefishEngine.a(a4, 27, j27);
                    long j28 = j25 + a3;
                    long a7 = ThreefishEngine.a(a3, 14, j28);
                    long j29 = j23 + a2;
                    long j30 = a6;
                    long a8 = ThreefishEngine.a(a2, 42, j29);
                    long j31 = j26;
                    long j32 = j27 + a5;
                    long a9 = ThreefishEngine.a(a5, 17, j32);
                    long j33 = j28 + a8;
                    long a10 = ThreefishEngine.a(a8, 49, j33);
                    long j34 = j29 + a7;
                    long a11 = ThreefishEngine.a(a7, 36, j34);
                    long j35 = j31 + j30;
                    long j36 = a10;
                    long a12 = ThreefishEngine.a(j30, 39, j35);
                    long j37 = j32;
                    long j38 = j33 + a9;
                    long a13 = ThreefishEngine.a(a9, 44, j38);
                    long j39 = j34 + a12;
                    long a14 = ThreefishEngine.a(a12, 9, j39);
                    long j40 = j35 + a11;
                    long a15 = ThreefishEngine.a(a11, 54, j40);
                    long j41 = j37 + j36;
                    long j42 = a14;
                    long j43 = j39 + jArr5[i2];
                    int i5 = i2 + 1;
                    int i6 = i5;
                    long j44 = a13 + jArr5[i5];
                    int i7 = i2 + 2;
                    long j45 = j40 + jArr5[i7];
                    int i8 = i2 + 3;
                    int i9 = i7;
                    int i10 = i8;
                    long a16 = ThreefishEngine.a(j36, 56, j41) + jArr5[i8];
                    int i11 = i2 + 4;
                    long j46 = j41 + jArr5[i11];
                    int i12 = i2 + 5;
                    long j47 = a15 + jArr5[i12] + jArr6[i3];
                    int i13 = i2 + 6;
                    int i14 = i3 + 1;
                    long j48 = j38 + jArr5[i13] + jArr6[i14];
                    int i15 = i2 + 7;
                    int i16 = i2;
                    int i17 = i4;
                    long j49 = (long) i17;
                    long j50 = j49;
                    long j51 = j42 + jArr5[i15] + j49;
                    int i18 = i15;
                    int i19 = i17;
                    long j52 = j43 + j44;
                    int i20 = i14;
                    long a17 = ThreefishEngine.a(j44, 39, j52);
                    int i21 = i12;
                    int i22 = i13;
                    long j53 = j45 + a16;
                    long a18 = ThreefishEngine.a(a16, 30, j53);
                    long j54 = j46 + j47;
                    long a19 = ThreefishEngine.a(j47, 34, j54);
                    long j55 = j52;
                    long j56 = j48 + j51;
                    long a20 = ThreefishEngine.a(j51, 24, j56);
                    long j57 = j53 + a17;
                    long a21 = ThreefishEngine.a(a17, 13, j57);
                    long j58 = j54 + a20;
                    long a22 = ThreefishEngine.a(a20, 50, j58);
                    long j59 = j56 + a19;
                    long a23 = ThreefishEngine.a(a19, 10, j59);
                    long j60 = j55 + a18;
                    long j61 = a22;
                    long a24 = ThreefishEngine.a(a18, 17, j60);
                    long j62 = j57;
                    long j63 = j58 + a21;
                    long a25 = ThreefishEngine.a(a21, 25, j63);
                    long j64 = j59 + a24;
                    long a26 = ThreefishEngine.a(a24, 29, j64);
                    long j65 = j60 + a23;
                    long a27 = ThreefishEngine.a(a23, 39, j65);
                    long j66 = j62 + j61;
                    long j67 = a26;
                    long a28 = ThreefishEngine.a(j61, 43, j66);
                    long j68 = j63;
                    long j69 = j64 + a25;
                    long a29 = ThreefishEngine.a(a25, 8, j69);
                    long j70 = j69;
                    long j71 = j65 + a28;
                    long a30 = ThreefishEngine.a(a28, 35, j71);
                    long j72 = j66 + a27;
                    long a31 = ThreefishEngine.a(a27, 56, j72);
                    long j73 = j68 + j67;
                    long j74 = a30;
                    j8 = j71 + jArr5[i6];
                    long j75 = a29 + jArr5[i9];
                    long j76 = j72 + jArr5[i10];
                    long a32 = ThreefishEngine.a(j67, 22, j73) + jArr5[i11];
                    j12 = j73 + jArr5[i21];
                    long j77 = a31 + jArr5[i22] + jArr6[i20];
                    j13 = j70 + jArr5[i18] + jArr6[i3 + 2];
                    j15 = j74 + jArr5[i16 + 8] + j50 + 1;
                    j18 = a32;
                    c = iArr;
                    b = iArr2;
                    jArr3 = jArr5;
                    jArr4 = jArr6;
                    j14 = j77;
                    i = i19 + 2;
                    j19 = j76;
                    j16 = j75;
                }
                long j78 = j15;
                jArr2[0] = j8;
                jArr2[1] = j16;
                jArr2[2] = j19;
                jArr2[3] = j18;
                jArr2[4] = j12;
                jArr2[5] = j14;
                jArr2[6] = j13;
                jArr2[7] = j78;
            }
        }

        public void b(long[] jArr, long[] jArr2) {
            long[] jArr3 = this.b;
            long[] jArr4 = this.a;
            int[] c = ThreefishEngine.a;
            int[] b = ThreefishEngine.d;
            if (jArr3.length != 17) {
                throw new IllegalArgumentException();
            } else if (jArr4.length != 5) {
                throw new IllegalArgumentException();
            } else {
                long j = jArr[0];
                long j2 = jArr[1];
                long j3 = jArr[2];
                long j4 = jArr[3];
                long j5 = jArr[4];
                long j6 = jArr[5];
                long j7 = jArr[6];
                long j8 = jArr[7];
                int i = 17;
                for (int i2 = 1; i >= i2; i2 = 1) {
                    int i3 = c[i];
                    int i4 = b[i];
                    int i5 = i3 + 1;
                    long j9 = j - jArr3[i5];
                    int i6 = i3 + 2;
                    int i7 = i6;
                    long j10 = j2 - jArr3[i6];
                    int i8 = i3 + 3;
                    int[] iArr = c;
                    int[] iArr2 = b;
                    long j11 = j3 - jArr3[i8];
                    int i9 = i3 + 4;
                    int i10 = i9;
                    int i11 = i3 + 5;
                    int i12 = i11;
                    int i13 = i3 + 6;
                    int i14 = i4 + 1;
                    int i15 = i8;
                    long j12 = j4 - jArr3[i9];
                    long j13 = j6 - (jArr3[i13] + jArr4[i14]);
                    int i16 = i3 + 7;
                    int i17 = i16;
                    long j14 = j5 - jArr3[i11];
                    long j15 = j7 - (jArr3[i16] + jArr4[i4 + 2]);
                    long[] jArr5 = jArr3;
                    long j16 = (long) i;
                    int i18 = i;
                    long j17 = j8 - ((jArr3[i3 + 8] + j16) + 1);
                    long b2 = ThreefishEngine.b(j10, 8, j15);
                    long j18 = j16;
                    long j19 = j15 - b2;
                    long b3 = ThreefishEngine.b(j17, 35, j9);
                    long j20 = j9 - b3;
                    long b4 = ThreefishEngine.b(j13, 56, j11);
                    long j21 = b3;
                    long j22 = j11 - b4;
                    long j23 = j14;
                    long b5 = ThreefishEngine.b(j12, 22, j23);
                    long j24 = b4;
                    long j25 = j23 - b5;
                    long b6 = ThreefishEngine.b(b2, 25, j25);
                    long j26 = j25 - b6;
                    long b7 = ThreefishEngine.b(b5, 29, j19);
                    long j27 = j19 - b7;
                    long b8 = ThreefishEngine.b(j24, 39, j20);
                    long j28 = j20 - b8;
                    long j29 = b7;
                    long j30 = j22;
                    long b9 = ThreefishEngine.b(j21, 43, j30);
                    long j31 = j28;
                    long j32 = j30 - b9;
                    long b10 = ThreefishEngine.b(b6, 13, j32);
                    long j33 = j32 - b10;
                    long b11 = ThreefishEngine.b(b9, 50, j26);
                    long j34 = j26 - b11;
                    long b12 = ThreefishEngine.b(b8, 10, j27);
                    long j35 = j27 - b12;
                    long j36 = b11;
                    long j37 = j31;
                    long b13 = ThreefishEngine.b(j29, 17, j37);
                    long j38 = j35;
                    long j39 = j37 - b13;
                    long b14 = ThreefishEngine.b(b10, 39, j39);
                    long j40 = j39 - b14;
                    long b15 = ThreefishEngine.b(b13, 30, j33);
                    long j41 = j33 - b15;
                    long b16 = ThreefishEngine.b(b12, 34, j34);
                    long j42 = j34 - b16;
                    long j43 = b16;
                    long j44 = j38;
                    long b17 = ThreefishEngine.b(j36, 24, j44);
                    long j45 = j44 - b17;
                    long j46 = b17;
                    long j47 = j40 - jArr5[i3];
                    long j48 = b14 - jArr5[i5];
                    long j49 = j41 - jArr5[i7];
                    long j50 = b15 - jArr5[i15];
                    long j51 = j42 - jArr5[i10];
                    long j52 = j43 - (jArr5[i12] + jArr4[i4]);
                    long j53 = j45 - (jArr5[i13] + jArr4[i14]);
                    long j54 = j51;
                    long j55 = j46 - (jArr5[i17] + j18);
                    long b18 = ThreefishEngine.b(j48, 44, j53);
                    long j56 = j50;
                    long j57 = j53 - b18;
                    long b19 = ThreefishEngine.b(j55, 9, j47);
                    long j58 = j47 - b19;
                    long b20 = ThreefishEngine.b(j52, 54, j49);
                    long j59 = j49 - b20;
                    long j60 = b19;
                    long j61 = j54;
                    long b21 = ThreefishEngine.b(j56, 56, j61);
                    long j62 = j59;
                    long j63 = j61 - b21;
                    long b22 = ThreefishEngine.b(b18, 17, j63);
                    long j64 = j63 - b22;
                    long b23 = ThreefishEngine.b(b21, 49, j57);
                    long j65 = j57 - b23;
                    long b24 = ThreefishEngine.b(b20, 36, j58);
                    long j66 = j58 - b24;
                    long j67 = b23;
                    long j68 = j62;
                    long b25 = ThreefishEngine.b(j60, 39, j68);
                    long j69 = j66;
                    long j70 = j68 - b25;
                    long b26 = ThreefishEngine.b(b22, 33, j70);
                    long j71 = j70 - b26;
                    long b27 = ThreefishEngine.b(b25, 27, j64);
                    long j72 = j64 - b27;
                    long b28 = ThreefishEngine.b(b24, 14, j65);
                    long j73 = j65 - b28;
                    long j74 = j69;
                    long b29 = ThreefishEngine.b(j67, 42, j74);
                    long j75 = b27;
                    long j76 = j74 - b29;
                    long b30 = ThreefishEngine.b(b26, 46, j76);
                    long j77 = j76 - b30;
                    j4 = ThreefishEngine.b(b29, 36, j71);
                    j3 = j71 - j4;
                    j6 = ThreefishEngine.b(b28, 19, j72);
                    j5 = j72 - j6;
                    long j78 = j73;
                    j8 = ThreefishEngine.b(j75, 37, j78);
                    j7 = j78 - j8;
                    i = i18 - 2;
                    j2 = b30;
                    j = j77;
                    c = iArr;
                    b = iArr2;
                    jArr3 = jArr5;
                }
                long[] jArr6 = jArr3;
                long j79 = j2 - jArr6[1];
                long j80 = j3 - jArr6[2];
                long j81 = j4 - jArr6[3];
                long j82 = j5 - jArr6[4];
                long j83 = j6 - (jArr6[5] + jArr4[0]);
                long j84 = j7 - (jArr6[6] + jArr4[1]);
                long j85 = j8 - jArr6[7];
                jArr2[0] = j - jArr6[0];
                jArr2[1] = j79;
                jArr2[2] = j80;
                jArr2[3] = j81;
                jArr2[4] = j82;
                jArr2[5] = j83;
                jArr2[6] = j84;
                jArr2[7] = j85;
            }
        }
    }

    static abstract class ThreefishCipher {
        protected final long[] a;
        protected final long[] b;

        protected ThreefishCipher(long[] jArr, long[] jArr2) {
            this.b = jArr;
            this.a = jArr2;
        }

        /* access modifiers changed from: 0000 */
        public abstract void a(long[] jArr, long[] jArr2);

        /* access modifiers changed from: 0000 */
        public abstract void b(long[] jArr, long[] jArr2);
    }

    static {
        for (int i2 = 0; i2 < a.length; i2++) {
            b[i2] = i2 % 17;
            a[i2] = i2 % 9;
            c[i2] = i2 % 5;
            d[i2] = i2 % 3;
        }
    }

    public ThreefishEngine(int i2) {
        ThreefishCipher threefishCipher;
        this.e = i2 / 8;
        this.f = this.e / 8;
        this.g = new long[this.f];
        this.i = new long[((this.f * 2) + 1)];
        if (i2 == 256) {
            threefishCipher = new Threefish256Cipher(this.i, this.h);
        } else if (i2 == 512) {
            threefishCipher = new Threefish512Cipher(this.i, this.h);
        } else if (i2 != 1024) {
            throw new IllegalArgumentException("Invalid blocksize - Threefish is defined with block size of 256, 512, or 1024 bits");
        } else {
            threefishCipher = new Threefish1024Cipher(this.i, this.h);
        }
        this.j = threefishCipher;
    }

    static long a(long j2, int i2, long j3) {
        return ((j2 << i2) | (j2 >>> (-i2))) ^ j3;
    }

    private void a(long[] jArr) {
        if (jArr.length != this.f) {
            StringBuilder sb = new StringBuilder();
            sb.append("Threefish key must be same size as block (");
            sb.append(this.f);
            sb.append(" words)");
            throw new IllegalArgumentException(sb.toString());
        }
        long j2 = 2004413935125273122L;
        int i2 = 0;
        while (i2 < this.f) {
            this.i[i2] = jArr[i2];
            i2++;
            j2 ^= this.i[i2];
        }
        this.i[this.f] = j2;
        System.arraycopy(this.i, 0, this.i, this.f + 1, this.f);
    }

    static long b(long j2, int i2, long j3) {
        long j4 = j2 ^ j3;
        return (j4 >>> i2) | (j4 << (-i2));
    }

    private void b(long[] jArr) {
        if (jArr.length != 2) {
            throw new IllegalArgumentException("Tweak must be 2 words.");
        }
        this.h[0] = jArr[0];
        this.h[1] = jArr[1];
        this.h[2] = this.h[0] ^ this.h[1];
        this.h[3] = this.h[0];
        this.h[4] = this.h[1];
    }

    public static long bytesToWord(byte[] bArr, int i2) {
        if (i2 + 8 > bArr.length) {
            throw new IllegalArgumentException();
        }
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        long j2 = (((long) bArr[i2]) & 255) | ((((long) bArr[i3]) & 255) << 8);
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        long j3 = j2 | ((((long) bArr[i4]) & 255) << 16) | ((((long) bArr[i5]) & 255) << 24);
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        return j3 | ((((long) bArr[i6]) & 255) << 32) | ((((long) bArr[i7]) & 255) << 40) | ((((long) bArr[i8]) & 255) << 48) | ((((long) bArr[i8 + 1]) & 255) << 56);
    }

    public static void wordToBytes(long j2, byte[] bArr, int i2) {
        if (i2 + 8 > bArr.length) {
            throw new IllegalArgumentException();
        }
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) j2);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) (j2 >> 8));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) (j2 >> 16));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) (j2 >> 24));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) (j2 >> 32));
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((int) (j2 >> 40));
        int i9 = i8 + 1;
        bArr[i8] = (byte) ((int) (j2 >> 48));
        bArr[i9] = (byte) ((int) (j2 >> 56));
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Threefish-");
        sb.append(this.e * 8);
        return sb.toString();
    }

    public int getBlockSize() {
        return this.e;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        byte[] bArr;
        byte[] bArr2;
        long[] jArr;
        long[] jArr2 = null;
        if (cipherParameters instanceof TweakableBlockCipherParameters) {
            TweakableBlockCipherParameters tweakableBlockCipherParameters = (TweakableBlockCipherParameters) cipherParameters;
            bArr2 = tweakableBlockCipherParameters.getKey().getKey();
            bArr = tweakableBlockCipherParameters.getTweak();
        } else if (cipherParameters instanceof KeyParameter) {
            bArr2 = ((KeyParameter) cipherParameters).getKey();
            bArr = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid parameter passed to Threefish init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        if (bArr2 == null) {
            jArr = null;
        } else if (bArr2.length != this.e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Threefish key must be same size as block (");
            sb2.append(this.e);
            sb2.append(" bytes)");
            throw new IllegalArgumentException(sb2.toString());
        } else {
            jArr = new long[this.f];
            for (int i2 = 0; i2 < jArr.length; i2++) {
                jArr[i2] = bytesToWord(bArr2, i2 * 8);
            }
        }
        if (bArr != null) {
            if (bArr.length != 16) {
                throw new IllegalArgumentException("Threefish tweak must be 16 bytes");
            }
            jArr2 = new long[]{bytesToWord(bArr, 0), bytesToWord(bArr, 8)};
        }
        init(z, jArr, jArr2);
    }

    public void init(boolean z, long[] jArr, long[] jArr2) {
        this.k = z;
        if (jArr != null) {
            a(jArr);
        }
        if (jArr2 != null) {
            b(jArr2);
        }
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.e + i3 > bArr2.length) {
            throw new DataLengthException("Output buffer too short");
        } else if (this.e + i2 > bArr.length) {
            throw new DataLengthException("Input buffer too short");
        } else {
            for (int i4 = 0; i4 < this.e; i4 += 8) {
                this.g[i4 >> 3] = bytesToWord(bArr, i2 + i4);
            }
            processBlock(this.g, this.g);
            for (int i5 = 0; i5 < this.e; i5 += 8) {
                wordToBytes(this.g[i5 >> 3], bArr2, i3 + i5);
            }
            return this.e;
        }
    }

    public int processBlock(long[] jArr, long[] jArr2) {
        if (this.i[this.f] == 0) {
            throw new IllegalStateException("Threefish engine not initialised");
        } else if (jArr.length != this.f) {
            throw new DataLengthException("Input buffer too short");
        } else if (jArr2.length != this.f) {
            throw new DataLengthException("Output buffer too short");
        } else {
            if (this.k) {
                this.j.a(jArr, jArr2);
            } else {
                this.j.b(jArr, jArr2);
            }
            return this.f;
        }
    }

    public void reset() {
    }
}
