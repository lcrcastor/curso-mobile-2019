package org.joda.time.chrono;

import org.joda.time.Chronology;

abstract class BasicFixedMonthChronology extends BasicChronology {
    private static final long serialVersionUID = 261387371998L;

    /* access modifiers changed from: 0000 */
    public int b() {
        return 30;
    }

    /* access modifiers changed from: 0000 */
    public long c(int i, int i2) {
        return ((long) (i2 - 1)) * 2592000000L;
    }

    /* access modifiers changed from: 0000 */
    public int e() {
        return 13;
    }

    /* access modifiers changed from: 0000 */
    public boolean e(int i) {
        return (i & 3) == 3;
    }

    /* access modifiers changed from: 0000 */
    public int f(int i) {
        return i != 13 ? 30 : 6;
    }

    /* access modifiers changed from: 0000 */
    public long f() {
        return 31557600000L;
    }

    /* access modifiers changed from: 0000 */
    public long g() {
        return 15778800000L;
    }

    /* access modifiers changed from: 0000 */
    public long h() {
        return 2592000000L;
    }

    BasicFixedMonthChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    /* access modifiers changed from: 0000 */
    public long f(long j, int i) {
        int c = c(j, a(j));
        int h = h(j);
        if (c > 365 && !e(i)) {
            c--;
        }
        return a(i, 1, c) + ((long) h);
    }

    /* access modifiers changed from: 0000 */
    public long a(long j, long j2) {
        int a = a(j);
        int a2 = a(j2);
        int i = a - a2;
        if (j - d(a) < j2 - d(a2)) {
            i--;
        }
        return (long) i;
    }

    /* access modifiers changed from: 0000 */
    public int c(long j) {
        return ((d(j) - 1) % 30) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i, int i2) {
        if (i2 != 13) {
            return 30;
        }
        return e(i) ? 6 : 5;
    }

    /* access modifiers changed from: 0000 */
    public int b(long j) {
        return ((d(j) - 1) / 30) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int a(long j, int i) {
        return ((int) ((j - d(i)) / 2592000000L)) + 1;
    }
}
