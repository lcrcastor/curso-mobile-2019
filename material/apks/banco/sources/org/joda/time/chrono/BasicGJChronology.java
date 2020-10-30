package org.joda.time.chrono;

import org.joda.time.Chronology;

abstract class BasicGJChronology extends BasicChronology {
    private static final int[] a = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] b = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final long[] c = new long[12];
    private static final long[] d = new long[12];
    private static final long serialVersionUID = 538276888268L;

    static {
        long j = 0;
        int i = 0;
        long j2 = 0;
        while (i < 11) {
            long j3 = j + (((long) a[i]) * 86400000);
            int i2 = i + 1;
            c[i2] = j3;
            long j4 = j2 + (((long) b[i]) * 86400000);
            d[i2] = j4;
            i = i2;
            j2 = j4;
            j = j3;
        }
    }

    BasicGJChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    /* access modifiers changed from: 0000 */
    public boolean j(long j) {
        return dayOfMonth().get(j) == 29 && monthOfYear().isLeap(j);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        if (r14 < 12825000) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005f, code lost:
        if (r14 < 20587500) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0072, code lost:
        if (r14 < 28265625) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008a, code lost:
        if (r14 < 4978125) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0096, code lost:
        if (r14 < 12740625) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00a7, code lost:
        if (r14 < 20503125) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b3, code lost:
        if (r14 < 28181250) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        return 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        return 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0034, code lost:
        if (r14 < 5062500) goto L_0x0036;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(long r13, int r15) {
        /*
            r12 = this;
            long r0 = r12.d(r15)
            long r2 = r13 - r0
            r13 = 10
            long r0 = r2 >> r13
            int r14 = (int) r0
            boolean r15 = r12.e(r15)
            r0 = 12
            r1 = 11
            r2 = 9
            r3 = 8
            r4 = 7
            r5 = 6
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 1
            r11 = 2615625(0x27e949, float:3.665271E-39)
            if (r15 == 0) goto L_0x007a
            r15 = 15356250(0xea515a, float:2.151869E-38)
            if (r14 >= r15) goto L_0x004f
            r13 = 7678125(0x7528ad, float:1.0759345E-38)
            if (r14 >= r13) goto L_0x003c
            if (r14 >= r11) goto L_0x0031
            goto L_0x00b6
        L_0x0031:
            r13 = 5062500(0x4d3f64, float:7.094073E-39)
            if (r14 >= r13) goto L_0x0039
        L_0x0036:
            r10 = 2
            goto L_0x00b6
        L_0x0039:
            r10 = 3
            goto L_0x00b6
        L_0x003c:
            r13 = 10209375(0x9bc85f, float:1.4306382E-38)
            if (r14 >= r13) goto L_0x0044
        L_0x0041:
            r10 = 4
            goto L_0x00b6
        L_0x0044:
            r13 = 12825000(0xc3b1a8, float:1.7971653E-38)
            if (r14 >= r13) goto L_0x004c
        L_0x0049:
            r10 = 5
            goto L_0x00b6
        L_0x004c:
            r10 = 6
            goto L_0x00b6
        L_0x004f:
            r15 = 23118750(0x160c39e, float:4.128265E-38)
            if (r14 >= r15) goto L_0x0067
            r13 = 17971875(0x1123aa3, float:2.6858035E-38)
            if (r14 >= r13) goto L_0x005c
        L_0x0059:
            r10 = 7
            goto L_0x00b6
        L_0x005c:
            r13 = 20587500(0x13a23ec, float:3.4188577E-38)
            if (r14 >= r13) goto L_0x0064
        L_0x0061:
            r10 = 8
            goto L_0x00b6
        L_0x0064:
            r10 = 9
            goto L_0x00b6
        L_0x0067:
            r15 = 25734375(0x188ace7, float:5.020661E-38)
            if (r14 >= r15) goto L_0x006f
        L_0x006c:
            r10 = 10
            goto L_0x00b6
        L_0x006f:
            r13 = 28265625(0x1af4c99, float:6.439476E-38)
            if (r14 >= r13) goto L_0x0077
        L_0x0074:
            r10 = 11
            goto L_0x00b6
        L_0x0077:
            r10 = 12
            goto L_0x00b6
        L_0x007a:
            r15 = 15271875(0xe907c3, float:2.1400455E-38)
            if (r14 >= r15) goto L_0x0099
            r13 = 7593750(0x73df16, float:1.064111E-38)
            if (r14 >= r13) goto L_0x008d
            if (r14 >= r11) goto L_0x0087
            goto L_0x00b6
        L_0x0087:
            r13 = 4978125(0x4bf5cd, float:6.975839E-39)
            if (r14 >= r13) goto L_0x0039
            goto L_0x0036
        L_0x008d:
            r13 = 10125000(0x9a7ec8, float:1.4188147E-38)
            if (r14 >= r13) goto L_0x0093
            goto L_0x0041
        L_0x0093:
            r13 = 12740625(0xc26811, float:1.7853418E-38)
            if (r14 >= r13) goto L_0x004c
            goto L_0x0049
        L_0x0099:
            r15 = 23034375(0x15f7a07, float:4.1046182E-38)
            if (r14 >= r15) goto L_0x00aa
            r13 = 17887500(0x110f10c, float:2.6621566E-38)
            if (r14 >= r13) goto L_0x00a4
            goto L_0x0059
        L_0x00a4:
            r13 = 20503125(0x138da55, float:3.3952108E-38)
            if (r14 >= r13) goto L_0x0064
            goto L_0x0061
        L_0x00aa:
            r15 = 25650000(0x1876350, float:4.9733674E-38)
            if (r14 >= r15) goto L_0x00b0
            goto L_0x006c
        L_0x00b0:
            r13 = 28181250(0x1ae0302, float:6.392182E-38)
            if (r14 >= r13) goto L_0x0077
            goto L_0x0074
        L_0x00b6:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.chrono.BasicGJChronology.a(long, int):int");
    }

    /* access modifiers changed from: 0000 */
    public int b(int i, int i2) {
        if (e(i)) {
            return b[i2 - 1];
        }
        return a[i2 - 1];
    }

    /* access modifiers changed from: 0000 */
    public int f(int i) {
        return b[i - 1];
    }

    /* access modifiers changed from: 0000 */
    public int e(long j, int i) {
        if (i > 28 || i < 1) {
            return i(j);
        }
        return 28;
    }

    /* access modifiers changed from: 0000 */
    public long c(int i, int i2) {
        if (e(i)) {
            return d[i2 - 1];
        }
        return c[i2 - 1];
    }

    /* access modifiers changed from: 0000 */
    public long a(long j, long j2) {
        int a2 = a(j);
        int a3 = a(j2);
        long d2 = j - d(a2);
        long d3 = j2 - d(a3);
        if (d3 >= 5097600000L) {
            if (e(a3)) {
                if (!e(a2)) {
                    d3 -= 86400000;
                }
            } else if (d2 >= 5097600000L && e(a2)) {
                d2 -= 86400000;
            }
        }
        int i = a2 - a3;
        if (d2 < d3) {
            i--;
        }
        return (long) i;
    }

    /* access modifiers changed from: 0000 */
    public long f(long j, int i) {
        int a2 = a(j);
        int c2 = c(j, a2);
        int h = h(j);
        if (c2 > 59) {
            if (e(a2)) {
                if (!e(i)) {
                    c2--;
                }
            } else if (e(i)) {
                c2++;
            }
        }
        return a(i, 1, c2) + ((long) h);
    }
}
