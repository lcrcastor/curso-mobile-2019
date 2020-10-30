package org.joda.time.tz;

import org.joda.time.DateTimeZone;

public class CachedDateTimeZone extends DateTimeZone {
    private static final int a;
    private static final long serialVersionUID = 5472298452022250685L;
    private final DateTimeZone b;
    private final transient Info[] c = new Info[(a + 1)];

    static final class Info {
        public final long a;
        public final DateTimeZone b;
        Info c;
        private String d;
        private int e = Integer.MIN_VALUE;
        private int f = Integer.MIN_VALUE;

        Info(DateTimeZone dateTimeZone, long j) {
            this.a = j;
            this.b = dateTimeZone;
        }

        public String a(long j) {
            if (this.c != null && j >= this.c.a) {
                return this.c.a(j);
            }
            if (this.d == null) {
                this.d = this.b.getNameKey(this.a);
            }
            return this.d;
        }

        public int b(long j) {
            if (this.c != null && j >= this.c.a) {
                return this.c.b(j);
            }
            if (this.e == Integer.MIN_VALUE) {
                this.e = this.b.getOffset(this.a);
            }
            return this.e;
        }

        public int c(long j) {
            if (this.c != null && j >= this.c.a) {
                return this.c.c(j);
            }
            if (this.f == Integer.MIN_VALUE) {
                this.f = this.b.getStandardOffset(this.a);
            }
            return this.f;
        }
    }

    static {
        Integer num;
        int i;
        try {
            num = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
        } catch (SecurityException unused) {
            num = null;
        }
        if (num == null) {
            i = 512;
        } else {
            int i2 = 0;
            for (int intValue = num.intValue() - 1; intValue > 0; intValue >>= 1) {
                i2++;
            }
            i = 1 << i2;
        }
        a = i - 1;
    }

    public static CachedDateTimeZone forZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone instanceof CachedDateTimeZone) {
            return (CachedDateTimeZone) dateTimeZone;
        }
        return new CachedDateTimeZone(dateTimeZone);
    }

    private CachedDateTimeZone(DateTimeZone dateTimeZone) {
        super(dateTimeZone.getID());
        this.b = dateTimeZone;
    }

    public DateTimeZone getUncachedZone() {
        return this.b;
    }

    public String getNameKey(long j) {
        return a(j).a(j);
    }

    public int getOffset(long j) {
        return a(j).b(j);
    }

    public int getStandardOffset(long j) {
        return a(j).c(j);
    }

    public boolean isFixed() {
        return this.b.isFixed();
    }

    public long nextTransition(long j) {
        return this.b.nextTransition(j);
    }

    public long previousTransition(long j) {
        return this.b.previousTransition(j);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CachedDateTimeZone) {
            return this.b.equals(((CachedDateTimeZone) obj).b);
        }
        return false;
    }

    private Info a(long j) {
        int i = (int) (j >> 32);
        Info[] infoArr = this.c;
        int i2 = a & i;
        Info info = infoArr[i2];
        if (info != null && ((int) (info.a >> 32)) == i) {
            return info;
        }
        Info b2 = b(j);
        infoArr[i2] = b2;
        return b2;
    }

    private Info b(long j) {
        long j2 = j & -4294967296L;
        Info info = new Info(this.b, j2);
        long j3 = j2 | 4294967295L;
        Info info2 = info;
        while (true) {
            long nextTransition = this.b.nextTransition(j2);
            if (nextTransition == j2 || nextTransition > j3) {
                return info;
            }
            Info info3 = new Info(this.b, nextTransition);
            info2.c = info3;
            info2 = info3;
            j2 = nextTransition;
        }
        return info;
    }
}
