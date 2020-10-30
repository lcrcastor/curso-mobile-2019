package org.joda.time.chrono;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;

public final class IslamicChronology extends BasicChronology {
    public static final int AH = 1;
    public static final LeapYearPatternType LEAP_YEAR_15_BASED = new LeapYearPatternType(0, 623158436);
    public static final LeapYearPatternType LEAP_YEAR_16_BASED = new LeapYearPatternType(1, 623191204);
    public static final LeapYearPatternType LEAP_YEAR_HABASH_AL_HASIB = new LeapYearPatternType(3, 153692453);
    public static final LeapYearPatternType LEAP_YEAR_INDIAN = new LeapYearPatternType(2, 690562340);
    private static final DateTimeField a = new BasicSingleEraDateTimeField("AH");
    private static final ConcurrentHashMap<DateTimeZone, IslamicChronology[]> b = new ConcurrentHashMap<>();
    private static final IslamicChronology c = getInstance(DateTimeZone.UTC);
    private static final long serialVersionUID = -3663823829888L;
    private final LeapYearPatternType d;

    public static class LeapYearPatternType implements Serializable {
        private static final long serialVersionUID = 26581275372698L;
        final byte a;
        final int b;

        LeapYearPatternType(int i, int i2) {
            this.a = (byte) i;
            this.b = i2;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i) {
            if (((1 << (i % 30)) & this.b) > 0) {
                return true;
            }
            return false;
        }

        private Object readResolve() {
            switch (this.a) {
                case 0:
                    return IslamicChronology.LEAP_YEAR_15_BASED;
                case 1:
                    return IslamicChronology.LEAP_YEAR_16_BASED;
                case 2:
                    return IslamicChronology.LEAP_YEAR_INDIAN;
                case 3:
                    return IslamicChronology.LEAP_YEAR_HABASH_AL_HASIB;
                default:
                    return this;
            }
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof LeapYearPatternType)) {
                return false;
            }
            if (this.a == ((LeapYearPatternType) obj).a) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.a;
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return 355;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return 30;
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return 292271022;
    }

    /* access modifiers changed from: 0000 */
    public long f() {
        return 30617280288L;
    }

    /* access modifiers changed from: 0000 */
    public long g() {
        return 15308640144L;
    }

    /* access modifiers changed from: 0000 */
    public long h() {
        return 2551440384L;
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return 21260793600000L;
    }

    public static IslamicChronology getInstanceUTC() {
        return c;
    }

    public static IslamicChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), LEAP_YEAR_16_BASED);
    }

    public static IslamicChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, LEAP_YEAR_16_BASED);
    }

    public static IslamicChronology getInstance(DateTimeZone dateTimeZone, LeapYearPatternType leapYearPatternType) {
        IslamicChronology islamicChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        IslamicChronology[] islamicChronologyArr = (IslamicChronology[]) b.get(dateTimeZone);
        if (islamicChronologyArr == null) {
            islamicChronologyArr = new IslamicChronology[4];
            IslamicChronology[] islamicChronologyArr2 = (IslamicChronology[]) b.putIfAbsent(dateTimeZone, islamicChronologyArr);
            if (islamicChronologyArr2 != null) {
                islamicChronologyArr = islamicChronologyArr2;
            }
        }
        IslamicChronology islamicChronology2 = islamicChronologyArr[leapYearPatternType.a];
        if (islamicChronology2 == null) {
            synchronized (islamicChronologyArr) {
                islamicChronology2 = islamicChronologyArr[leapYearPatternType.a];
                if (islamicChronology2 == null) {
                    if (dateTimeZone == DateTimeZone.UTC) {
                        IslamicChronology islamicChronology3 = new IslamicChronology(null, null, leapYearPatternType);
                        DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, (Chronology) islamicChronology3);
                        islamicChronology = new IslamicChronology(LimitChronology.getInstance(islamicChronology3, dateTime, null), null, leapYearPatternType);
                    } else {
                        islamicChronology = new IslamicChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, leapYearPatternType), dateTimeZone), null, leapYearPatternType);
                    }
                    islamicChronologyArr[leapYearPatternType.a] = islamicChronology;
                    islamicChronology2 = islamicChronology;
                }
            }
        }
        return islamicChronology2;
    }

    IslamicChronology(Chronology chronology, Object obj, LeapYearPatternType leapYearPatternType) {
        super(chronology, obj, 4);
        this.d = leapYearPatternType;
    }

    private Object readResolve() {
        Chronology base = getBase();
        return base == null ? getInstanceUTC() : getInstance(base.getZone());
    }

    public LeapYearPatternType getLeapYearPatternType() {
        return this.d;
    }

    public Chronology withUTC() {
        return c;
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        return getInstance(dateTimeZone);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IslamicChronology)) {
            return false;
        }
        if (getLeapYearPatternType().a != ((IslamicChronology) obj).getLeapYearPatternType().a || !super.equals(obj)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (super.hashCode() * 13) + getLeapYearPatternType().hashCode();
    }

    /* access modifiers changed from: 0000 */
    public int a(long j) {
        long j2 = j - -42521587200000L;
        long j3 = j2 / 918518400000L;
        long j4 = j2 % 918518400000L;
        int i = (int) ((j3 * 30) + 1);
        long j5 = e(i) ? 30672000000L : 30585600000L;
        while (j4 >= j5) {
            long j6 = j4 - j5;
            i++;
            j5 = e(i) ? 30672000000L : 30585600000L;
            j4 = j6;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public long f(long j, int i) {
        int c2 = c(j, a(j));
        int h = h(j);
        if (c2 > 354 && !e(i)) {
            c2--;
        }
        return a(i, 1, c2) + ((long) h);
    }

    /* access modifiers changed from: 0000 */
    public long a(long j, long j2) {
        int a2 = a(j);
        int a3 = a(j2);
        int i = a2 - a3;
        if (j - d(a2) < j2 - d(a3)) {
            i--;
        }
        return (long) i;
    }

    /* access modifiers changed from: 0000 */
    public long c(int i, int i2) {
        int i3 = i2 - 1;
        if (i3 % 2 == 1) {
            return (((long) (i3 / 2)) * 5097600000L) + 2592000000L;
        }
        return ((long) (i3 / 2)) * 5097600000L;
    }

    /* access modifiers changed from: 0000 */
    public int c(long j) {
        int d2 = d(j) - 1;
        if (d2 == 354) {
            return 30;
        }
        return ((d2 % 59) % 30) + 1;
    }

    /* access modifiers changed from: 0000 */
    public boolean e(int i) {
        return this.d.a(i);
    }

    /* access modifiers changed from: 0000 */
    public int a(int i) {
        return e(i) ? 355 : 354;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i, int i2) {
        int i3 = 30;
        if (i2 == 12 && e(i)) {
            return 30;
        }
        if ((i2 - 1) % 2 != 0) {
            i3 = 29;
        }
        return i3;
    }

    /* access modifiers changed from: 0000 */
    public int f(int i) {
        int i2 = 30;
        if (i == 12) {
            return 30;
        }
        if ((i - 1) % 2 != 0) {
            i2 = 29;
        }
        return i2;
    }

    /* access modifiers changed from: 0000 */
    public int a(long j, int i) {
        int d2 = (int) ((j - d(i)) / 86400000);
        if (d2 == 354) {
            return 12;
        }
        return ((d2 * 2) / 59) + 1;
    }

    /* access modifiers changed from: 0000 */
    public long g(int i) {
        if (i > 292271022) {
            StringBuilder sb = new StringBuilder();
            sb.append("Year is too large: ");
            sb.append(i);
            sb.append(" > ");
            sb.append(292271022);
            throw new ArithmeticException(sb.toString());
        } else if (i < -292269337) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Year is too small: ");
            sb2.append(i);
            sb2.append(" < ");
            sb2.append(-292269337);
            throw new ArithmeticException(sb2.toString());
        } else {
            int i2 = i - 1;
            long j = (((long) (i2 / 30)) * 918518400000L) - 42521587200000L;
            int i3 = 1;
            int i4 = (i2 % 30) + 1;
            while (i3 < i4) {
                long j2 = e(i3) ? 30672000000L : 30585600000L;
                i3++;
                j += j2;
            }
            return j;
        }
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.era = a;
            fields.monthOfYear = new BasicMonthOfYearDateTimeField(this, 12);
            fields.months = fields.monthOfYear.getDurationField();
        }
    }
}
