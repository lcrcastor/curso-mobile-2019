package com.google.common.cache;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.annotations.GwtIncompatible;
import java.util.Random;
import sun.misc.Unsafe;

@GwtIncompatible
abstract class Striped64 extends Number {
    static final ThreadLocal<int[]> a = new ThreadLocal<>();
    static final Random b = new Random();
    static final int c = Runtime.getRuntime().availableProcessors();
    private static final Unsafe g;
    private static final long h;
    private static final long i;
    volatile transient Cell[] d;
    volatile transient long e;
    volatile transient int f;

    static final class Cell {
        private static final Unsafe b;
        private static final long c;
        volatile long a;

        Cell(long j) {
            this.a = j;
        }

        /* access modifiers changed from: 0000 */
        public final boolean a(long j, long j2) {
            return b.compareAndSwapLong(this, c, j, j2);
        }

        static {
            try {
                b = Striped64.a();
                c = b.objectFieldOffset(Cell.class.getDeclaredField(TarjetasConstants.VALUE));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract long a(long j, long j2);

    static {
        try {
            g = a();
            Class<Striped64> cls = Striped64.class;
            h = g.objectFieldOffset(cls.getDeclaredField("base"));
            i = g.objectFieldOffset(cls.getDeclaredField("busy"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    Striped64() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean b(long j, long j2) {
        return g.compareAndSwapLong(this, h, j, j2);
    }

    /* access modifiers changed from: 0000 */
    public final boolean c() {
        return g.compareAndSwapInt(this, i, 0, 1);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0093, code lost:
        if (r1.d != r10) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        r5 = new com.google.common.cache.Striped64.Cell[(r11 << 1)];
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x009a, code lost:
        if (r6 >= r11) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x009c, code lost:
        r5[r6] = r10[r6];
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00a3, code lost:
        r1.d = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00a6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00a7, code lost:
        r2 = r0;
        r5 = 0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0027 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0107 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(long r18, int[] r20, boolean r21) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r5 = 1
            r6 = 0
            if (r20 != 0) goto L_0x0020
            java.lang.ThreadLocal<int[]> r4 = a
            int[] r7 = new int[r5]
            r4.set(r7)
            java.util.Random r4 = b
            int r4 = r4.nextInt()
            if (r4 != 0) goto L_0x0018
            r4 = 1
        L_0x0018:
            r7[r6] = r4
            r16 = r7
            r7 = r4
            r4 = r16
            goto L_0x0024
        L_0x0020:
            r7 = r20[r6]
            r4 = r20
        L_0x0024:
            r8 = r21
        L_0x0026:
            r9 = 0
        L_0x0027:
            com.google.common.cache.Striped64$Cell[] r10 = r1.d
            if (r10 == 0) goto L_0x00c6
            int r11 = r10.length
            if (r11 <= 0) goto L_0x00c6
            int r12 = r11 + -1
            r12 = r12 & r7
            r12 = r10[r12]
            if (r12 != 0) goto L_0x0068
            int r10 = r1.f
            if (r10 != 0) goto L_0x0066
            com.google.common.cache.Striped64$Cell r10 = new com.google.common.cache.Striped64$Cell
            r10.<init>(r2)
            int r11 = r1.f
            if (r11 != 0) goto L_0x0066
            boolean r11 = r17.c()
            if (r11 == 0) goto L_0x0066
            com.google.common.cache.Striped64$Cell[] r11 = r1.d     // Catch:{ all -> 0x0061 }
            if (r11 == 0) goto L_0x005a
            int r12 = r11.length     // Catch:{ all -> 0x0061 }
            if (r12 <= 0) goto L_0x005a
            int r12 = r12 + -1
            r12 = r12 & r7
            r13 = r11[r12]     // Catch:{ all -> 0x0061 }
            if (r13 != 0) goto L_0x005a
            r11[r12] = r10     // Catch:{ all -> 0x0061 }
            r10 = 1
            goto L_0x005b
        L_0x005a:
            r10 = 0
        L_0x005b:
            r1.f = r6
            if (r10 == 0) goto L_0x0027
            goto L_0x0107
        L_0x0061:
            r0 = move-exception
            r2 = r0
            r1.f = r6
            throw r2
        L_0x0066:
            r9 = 0
            goto L_0x00b7
        L_0x0068:
            if (r8 != 0) goto L_0x006c
            r8 = 1
            goto L_0x00b7
        L_0x006c:
            long r13 = r12.a
            long r5 = r1.a(r13, r2)
            boolean r5 = r12.a(r13, r5)
            if (r5 == 0) goto L_0x007a
            goto L_0x0107
        L_0x007a:
            int r5 = c
            if (r11 >= r5) goto L_0x0066
            com.google.common.cache.Striped64$Cell[] r5 = r1.d
            if (r5 == r10) goto L_0x0083
            goto L_0x0066
        L_0x0083:
            if (r9 != 0) goto L_0x0087
            r9 = 1
            goto L_0x00b7
        L_0x0087:
            int r5 = r1.f
            if (r5 != 0) goto L_0x00b7
            boolean r5 = r17.c()
            if (r5 == 0) goto L_0x00b7
            com.google.common.cache.Striped64$Cell[] r5 = r1.d     // Catch:{ all -> 0x00b1 }
            if (r5 != r10) goto L_0x00aa
            int r5 = r11 << 1
            com.google.common.cache.Striped64$Cell[] r5 = new com.google.common.cache.Striped64.Cell[r5]     // Catch:{ all -> 0x00a6 }
            r6 = 0
        L_0x009a:
            if (r6 >= r11) goto L_0x00a3
            r9 = r10[r6]     // Catch:{ all -> 0x00a6 }
            r5[r6] = r9     // Catch:{ all -> 0x00a6 }
            int r6 = r6 + 1
            goto L_0x009a
        L_0x00a3:
            r1.d = r5     // Catch:{ all -> 0x00a6 }
            goto L_0x00aa
        L_0x00a6:
            r0 = move-exception
            r2 = r0
            r5 = 0
            goto L_0x00b4
        L_0x00aa:
            r5 = 0
            r1.f = r5
            r5 = 1
            r6 = 0
            goto L_0x0026
        L_0x00b1:
            r0 = move-exception
            r5 = 0
            r2 = r0
        L_0x00b4:
            r1.f = r5
            throw r2
        L_0x00b7:
            int r5 = r7 << 13
            r5 = r5 ^ r7
            int r6 = r5 >>> 17
            r5 = r5 ^ r6
            int r6 = r5 << 5
            r5 = r5 ^ r6
            r6 = 0
            r4[r6] = r5
            r7 = r5
            r5 = 0
            goto L_0x0108
        L_0x00c6:
            int r5 = r1.f
            if (r5 != 0) goto L_0x00fa
            com.google.common.cache.Striped64$Cell[] r5 = r1.d
            if (r5 != r10) goto L_0x00fa
            boolean r5 = r17.c()
            if (r5 == 0) goto L_0x00fa
            com.google.common.cache.Striped64$Cell[] r5 = r1.d     // Catch:{ all -> 0x00f4 }
            if (r5 != r10) goto L_0x00ed
            r5 = 2
            com.google.common.cache.Striped64$Cell[] r5 = new com.google.common.cache.Striped64.Cell[r5]     // Catch:{ all -> 0x00e9 }
            r6 = r7 & 1
            com.google.common.cache.Striped64$Cell r10 = new com.google.common.cache.Striped64$Cell     // Catch:{ all -> 0x00e9 }
            r10.<init>(r2)     // Catch:{ all -> 0x00e9 }
            r5[r6] = r10     // Catch:{ all -> 0x00e9 }
            r1.d = r5     // Catch:{ all -> 0x00e9 }
            r5 = 0
            r15 = 1
            goto L_0x00ef
        L_0x00e9:
            r0 = move-exception
            r2 = r0
            r5 = 0
            goto L_0x00f7
        L_0x00ed:
            r5 = 0
            r15 = 0
        L_0x00ef:
            r1.f = r5
            if (r15 == 0) goto L_0x0108
            goto L_0x0107
        L_0x00f4:
            r0 = move-exception
            r5 = 0
            r2 = r0
        L_0x00f7:
            r1.f = r5
            throw r2
        L_0x00fa:
            r5 = 0
            long r10 = r1.e
            long r12 = r1.a(r10, r2)
            boolean r6 = r1.b(r10, r12)
            if (r6 == 0) goto L_0x0108
        L_0x0107:
            return
        L_0x0108:
            r5 = 1
            r6 = 0
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.Striped64.a(long, int[], boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0010, code lost:
        return (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.cache.Striped64.AnonymousClass1());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0011, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static sun.misc.Unsafe a() {
        /*
            sun.misc.Unsafe r0 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0005 }
            return r0
        L_0x0005:
            com.google.common.cache.Striped64$1 r0 = new com.google.common.cache.Striped64$1     // Catch:{ PrivilegedActionException -> 0x0011 }
            r0.<init>()     // Catch:{ PrivilegedActionException -> 0x0011 }
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ PrivilegedActionException -> 0x0011 }
            sun.misc.Unsafe r0 = (sun.misc.Unsafe) r0     // Catch:{ PrivilegedActionException -> 0x0011 }
            return r0
        L_0x0011:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Could not initialize intrinsics"
            java.lang.Throwable r0 = r0.getCause()
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.Striped64.a():sun.misc.Unsafe");
    }
}
