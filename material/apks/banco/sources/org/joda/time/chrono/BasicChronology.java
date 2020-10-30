package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.ZeroIsMaxDateTimeField;

abstract class BasicChronology extends AssembledChronology {
    private static final DurationField a = MillisDurationField.INSTANCE;
    private static final DurationField b = new PreciseDurationField(DurationFieldType.seconds(), 1000);
    private static final DurationField c = new PreciseDurationField(DurationFieldType.minutes(), 60000);
    private static final DurationField d = new PreciseDurationField(DurationFieldType.hours(), 3600000);
    /* access modifiers changed from: private */
    public static final DurationField e = new PreciseDurationField(DurationFieldType.halfdays(), 43200000);
    /* access modifiers changed from: private */
    public static final DurationField f = new PreciseDurationField(DurationFieldType.days(), 86400000);
    private static final DurationField g = new PreciseDurationField(DurationFieldType.weeks(), 604800000);
    private static final DateTimeField h = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), a, b);
    private static final DateTimeField i = new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), a, f);
    private static final DateTimeField j = new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), b, c);
    private static final DateTimeField k = new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), b, f);
    private static final DateTimeField l = new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), c, d);
    private static final DateTimeField m = new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), c, f);
    private static final DateTimeField n = new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), d, f);
    private static final DateTimeField o = new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), d, e);
    private static final DateTimeField p = new ZeroIsMaxDateTimeField(n, DateTimeFieldType.clockhourOfDay());
    private static final DateTimeField q = new ZeroIsMaxDateTimeField(o, DateTimeFieldType.clockhourOfHalfday());
    private static final DateTimeField r = new HalfdayField();
    private static final long serialVersionUID = 8283225332206808863L;
    private final transient YearInfo[] s = new YearInfo[1024];
    private final int t;

    static class HalfdayField extends PreciseDateTimeField {
        HalfdayField() {
            super(DateTimeFieldType.halfdayOfDay(), BasicChronology.e, BasicChronology.f);
        }

        public String getAsText(int i, Locale locale) {
            return GJLocaleSymbols.a(locale).f(i);
        }

        public long set(long j, String str, Locale locale) {
            return set(j, GJLocaleSymbols.a(locale).d(str));
        }

        public int getMaximumTextLength(Locale locale) {
            return GJLocaleSymbols.a(locale).f();
        }
    }

    static class YearInfo {
        public final int a;
        public final long b;

        YearInfo(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return 366;
    }

    /* access modifiers changed from: 0000 */
    public abstract int a(long j2, int i2);

    /* access modifiers changed from: 0000 */
    public abstract long a(long j2, long j3);

    /* access modifiers changed from: 0000 */
    public int b() {
        return 31;
    }

    /* access modifiers changed from: 0000 */
    public abstract int b(int i2, int i3);

    /* access modifiers changed from: 0000 */
    public abstract int c();

    /* access modifiers changed from: 0000 */
    public abstract long c(int i2, int i3);

    /* access modifiers changed from: 0000 */
    public abstract int d();

    /* access modifiers changed from: 0000 */
    public int e() {
        return 12;
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean e(int i2);

    /* access modifiers changed from: 0000 */
    public abstract int f(int i2);

    /* access modifiers changed from: 0000 */
    public abstract long f();

    /* access modifiers changed from: 0000 */
    public abstract long f(long j2, int i2);

    /* access modifiers changed from: 0000 */
    public abstract long g();

    /* access modifiers changed from: 0000 */
    public abstract long g(int i2);

    /* access modifiers changed from: 0000 */
    public abstract long h();

    /* access modifiers changed from: 0000 */
    public abstract long i();

    /* access modifiers changed from: 0000 */
    public boolean j(long j2) {
        return false;
    }

    BasicChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj);
        if (i2 < 1 || i2 > 7) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid min days in first week: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        this.t = i2;
    }

    public DateTimeZone getZone() {
        Chronology base = getBase();
        if (base != null) {
            return base.getZone();
        }
        return DateTimeZone.UTC;
    }

    public long getDateTimeMillis(int i2, int i3, int i4, int i5) {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfDay(), i5, 0, 86399999);
        return a(i2, i3, i4, i5);
    }

    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.hourOfDay(), i5, 0, 23);
        FieldUtils.verifyValueBounds(DateTimeFieldType.minuteOfHour(), i6, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.secondOfMinute(), i7, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfSecond(), i8, 0, 999);
        return a(i2, i3, i4, (int) ((long) ((i5 * DateTimeConstants.MILLIS_PER_HOUR) + (i6 * DateTimeConstants.MILLIS_PER_MINUTE) + (i7 * 1000) + i8)));
    }

    private long a(int i2, int i3, int i4, int i5) {
        long b2 = b(i2, i3, i4);
        if (b2 == Long.MIN_VALUE) {
            b2 = b(i2, i3, i4 + 1);
            i5 -= DateTimeConstants.MILLIS_PER_DAY;
        }
        long j2 = b2 + ((long) i5);
        if (j2 < 0 && b2 > 0) {
            return Long.MAX_VALUE;
        }
        if (j2 <= 0 || b2 >= 0) {
            return j2;
        }
        return Long.MIN_VALUE;
    }

    public int getMinimumDaysInFirstWeek() {
        return this.t;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicChronology basicChronology = (BasicChronology) obj;
        if (getMinimumDaysInFirstWeek() != basicChronology.getMinimumDaysInFirstWeek() || !getZone().equals(basicChronology.getZone())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (getClass().getName().hashCode() * 11) + getZone().hashCode() + getMinimumDaysInFirstWeek();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        String name = getClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(lastIndexOf + 1);
        }
        sb.append(name);
        sb.append('[');
        DateTimeZone zone = getZone();
        if (zone != null) {
            sb.append(zone.getID());
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            sb.append(",mdfw=");
            sb.append(getMinimumDaysInFirstWeek());
        }
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        fields.millis = a;
        fields.seconds = b;
        fields.minutes = c;
        fields.hours = d;
        fields.halfdays = e;
        fields.days = f;
        fields.weeks = g;
        fields.millisOfSecond = h;
        fields.millisOfDay = i;
        fields.secondOfMinute = j;
        fields.secondOfDay = k;
        fields.minuteOfHour = l;
        fields.minuteOfDay = m;
        fields.hourOfDay = n;
        fields.hourOfHalfday = o;
        fields.clockhourOfDay = p;
        fields.clockhourOfHalfday = q;
        fields.halfdayOfDay = r;
        fields.year = new BasicYearDateTimeField(this);
        fields.yearOfEra = new GJYearOfEraDateTimeField(fields.year, this);
        fields.centuryOfEra = new DividedDateTimeField((DateTimeField) new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
        fields.centuries = fields.centuryOfEra.getDurationField();
        fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra), DateTimeFieldType.yearOfCentury(), 1);
        fields.era = new GJEraDateTimeField(this);
        fields.dayOfWeek = new GJDayOfWeekDateTimeField(this, fields.days);
        fields.dayOfMonth = new BasicDayOfMonthDateTimeField(this, fields.days);
        fields.dayOfYear = new BasicDayOfYearDateTimeField(this, fields.days);
        fields.monthOfYear = new GJMonthOfYearDateTimeField(this);
        fields.weekyear = new BasicWeekyearDateTimeField(this);
        fields.weekOfWeekyear = new BasicWeekOfWeekyearDateTimeField(this, fields.weeks);
        fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, fields.centuries, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
        fields.years = fields.year.getDurationField();
        fields.months = fields.monthOfYear.getDurationField();
        fields.weekyears = fields.weekyear.getDurationField();
    }

    /* access modifiers changed from: 0000 */
    public int a(int i2) {
        return e(i2) ? 366 : 365;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i2) {
        return (int) ((c(i2 + 1) - c(i2)) / 604800000);
    }

    /* access modifiers changed from: 0000 */
    public long c(int i2) {
        long d2 = d(i2);
        int g2 = g(d2);
        return g2 > 8 - this.t ? d2 + (((long) (8 - g2)) * 86400000) : d2 - (((long) (g2 - 1)) * 86400000);
    }

    /* access modifiers changed from: 0000 */
    public long d(int i2) {
        return i(i2).b;
    }

    /* access modifiers changed from: 0000 */
    public long a(int i2, int i3) {
        return d(i2) + c(i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public long a(int i2, int i3, int i4) {
        return d(i2) + c(i2, i3) + (((long) (i4 - 1)) * 86400000);
    }

    /* access modifiers changed from: 0000 */
    public int a(long j2) {
        long g2 = g();
        long i2 = (j2 >> 1) + i();
        if (i2 < 0) {
            i2 = (i2 - g2) + 1;
        }
        int i3 = (int) (i2 / g2);
        long d2 = d(i3);
        long j3 = j2 - d2;
        if (j3 < 0) {
            return i3 - 1;
        }
        long j4 = 31536000000L;
        if (j3 < 31536000000L) {
            return i3;
        }
        if (e(i3)) {
            j4 = 31622400000L;
        }
        return d2 + j4 <= j2 ? i3 + 1 : i3;
    }

    /* access modifiers changed from: 0000 */
    public int b(long j2) {
        return a(j2, a(j2));
    }

    /* access modifiers changed from: 0000 */
    public int c(long j2) {
        int a2 = a(j2);
        return a(j2, a2, a(j2, a2));
    }

    /* access modifiers changed from: 0000 */
    public int b(long j2, int i2) {
        return a(j2, i2, a(j2, i2));
    }

    /* access modifiers changed from: 0000 */
    public int a(long j2, int i2, int i3) {
        return ((int) ((j2 - (d(i2) + c(i2, i3))) / 86400000)) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int d(long j2) {
        return c(j2, a(j2));
    }

    /* access modifiers changed from: 0000 */
    public int c(long j2, int i2) {
        return ((int) ((j2 - d(i2)) / 86400000)) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int e(long j2) {
        int a2 = a(j2);
        int d2 = d(j2, a2);
        if (d2 == 1) {
            return a(j2 + 604800000);
        }
        return d2 > 51 ? a(j2 - 1209600000) : a2;
    }

    /* access modifiers changed from: 0000 */
    public int f(long j2) {
        return d(j2, a(j2));
    }

    /* access modifiers changed from: 0000 */
    public int d(long j2, int i2) {
        long c2 = c(i2);
        if (j2 < c2) {
            return b(i2 - 1);
        }
        if (j2 >= c(i2 + 1)) {
            return 1;
        }
        return ((int) ((j2 - c2) / 604800000)) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int g(long j2) {
        long j3;
        if (j2 >= 0) {
            j3 = j2 / 86400000;
        } else {
            j3 = (j2 - 86399999) / 86400000;
            if (j3 < -3) {
                return ((int) ((j3 + 4) % 7)) + 7;
            }
        }
        return ((int) ((j3 + 3) % 7)) + 1;
    }

    /* access modifiers changed from: 0000 */
    public int h(long j2) {
        if (j2 >= 0) {
            return (int) (j2 % 86400000);
        }
        return ((int) ((j2 + 1) % 86400000)) + 86399999;
    }

    /* access modifiers changed from: 0000 */
    public int i(long j2) {
        int a2 = a(j2);
        return b(a2, a(j2, a2));
    }

    /* access modifiers changed from: 0000 */
    public int e(long j2, int i2) {
        return i(j2);
    }

    /* access modifiers changed from: 0000 */
    public long b(int i2, int i3, int i4) {
        FieldUtils.verifyValueBounds(DateTimeFieldType.year(), i2, c() - 1, d() + 1);
        FieldUtils.verifyValueBounds(DateTimeFieldType.monthOfYear(), i3, 1, h(i2));
        FieldUtils.verifyValueBounds(DateTimeFieldType.dayOfMonth(), i4, 1, b(i2, i3));
        long a2 = a(i2, i3, i4);
        if (a2 < 0 && i2 == d() + 1) {
            return Long.MAX_VALUE;
        }
        if (a2 <= 0 || i2 != c() - 1) {
            return a2;
        }
        return Long.MIN_VALUE;
    }

    /* access modifiers changed from: 0000 */
    public int h(int i2) {
        return e();
    }

    private YearInfo i(int i2) {
        int i3 = i2 & 1023;
        YearInfo yearInfo = this.s[i3];
        if (yearInfo != null && yearInfo.a == i2) {
            return yearInfo;
        }
        YearInfo yearInfo2 = new YearInfo(i2, g(i2));
        this.s[i3] = yearInfo2;
        return yearInfo2;
    }
}
