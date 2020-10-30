package org.joda.time.chrono;

import java.io.ObjectInputStream;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;

public abstract class AssembledChronology extends BaseChronology {
    private static final long serialVersionUID = -6728465968995518215L;
    private transient DateTimeField A;
    private transient DateTimeField B;
    private transient DateTimeField C;
    private transient DateTimeField D;
    private transient DateTimeField E;
    private transient DateTimeField F;
    private transient DateTimeField G;
    private transient DateTimeField H;
    private transient DateTimeField I;
    private transient DateTimeField J;
    private transient DateTimeField K;
    private transient int L;
    private final Chronology a;
    private final Object b;
    private transient DurationField c;
    private transient DurationField d;
    private transient DurationField e;
    private transient DurationField f;
    private transient DurationField g;
    private transient DurationField h;
    private transient DurationField i;
    private transient DurationField j;
    private transient DurationField k;
    private transient DurationField l;
    private transient DurationField m;
    private transient DurationField n;
    private transient DateTimeField o;
    private transient DateTimeField p;
    private transient DateTimeField q;
    private transient DateTimeField r;
    private transient DateTimeField s;
    private transient DateTimeField t;
    private transient DateTimeField u;
    private transient DateTimeField v;
    private transient DateTimeField w;
    private transient DateTimeField x;
    private transient DateTimeField y;
    private transient DateTimeField z;

    public static final class Fields {
        public DurationField centuries;
        public DateTimeField centuryOfEra;
        public DateTimeField clockhourOfDay;
        public DateTimeField clockhourOfHalfday;
        public DateTimeField dayOfMonth;
        public DateTimeField dayOfWeek;
        public DateTimeField dayOfYear;
        public DurationField days;
        public DateTimeField era;
        public DurationField eras;
        public DateTimeField halfdayOfDay;
        public DurationField halfdays;
        public DateTimeField hourOfDay;
        public DateTimeField hourOfHalfday;
        public DurationField hours;
        public DurationField millis;
        public DateTimeField millisOfDay;
        public DateTimeField millisOfSecond;
        public DateTimeField minuteOfDay;
        public DateTimeField minuteOfHour;
        public DurationField minutes;
        public DateTimeField monthOfYear;
        public DurationField months;
        public DateTimeField secondOfDay;
        public DateTimeField secondOfMinute;
        public DurationField seconds;
        public DateTimeField weekOfWeekyear;
        public DurationField weeks;
        public DateTimeField weekyear;
        public DateTimeField weekyearOfCentury;
        public DurationField weekyears;
        public DateTimeField year;
        public DateTimeField yearOfCentury;
        public DateTimeField yearOfEra;
        public DurationField years;

        Fields() {
        }

        public void copyFieldsFrom(Chronology chronology) {
            DurationField millis2 = chronology.millis();
            if (a(millis2)) {
                this.millis = millis2;
            }
            DurationField seconds2 = chronology.seconds();
            if (a(seconds2)) {
                this.seconds = seconds2;
            }
            DurationField minutes2 = chronology.minutes();
            if (a(minutes2)) {
                this.minutes = minutes2;
            }
            DurationField hours2 = chronology.hours();
            if (a(hours2)) {
                this.hours = hours2;
            }
            DurationField halfdays2 = chronology.halfdays();
            if (a(halfdays2)) {
                this.halfdays = halfdays2;
            }
            DurationField days2 = chronology.days();
            if (a(days2)) {
                this.days = days2;
            }
            DurationField weeks2 = chronology.weeks();
            if (a(weeks2)) {
                this.weeks = weeks2;
            }
            DurationField weekyears2 = chronology.weekyears();
            if (a(weekyears2)) {
                this.weekyears = weekyears2;
            }
            DurationField months2 = chronology.months();
            if (a(months2)) {
                this.months = months2;
            }
            DurationField years2 = chronology.years();
            if (a(years2)) {
                this.years = years2;
            }
            DurationField centuries2 = chronology.centuries();
            if (a(centuries2)) {
                this.centuries = centuries2;
            }
            DurationField eras2 = chronology.eras();
            if (a(eras2)) {
                this.eras = eras2;
            }
            DateTimeField millisOfSecond2 = chronology.millisOfSecond();
            if (a(millisOfSecond2)) {
                this.millisOfSecond = millisOfSecond2;
            }
            DateTimeField millisOfDay2 = chronology.millisOfDay();
            if (a(millisOfDay2)) {
                this.millisOfDay = millisOfDay2;
            }
            DateTimeField secondOfMinute2 = chronology.secondOfMinute();
            if (a(secondOfMinute2)) {
                this.secondOfMinute = secondOfMinute2;
            }
            DateTimeField secondOfDay2 = chronology.secondOfDay();
            if (a(secondOfDay2)) {
                this.secondOfDay = secondOfDay2;
            }
            DateTimeField minuteOfHour2 = chronology.minuteOfHour();
            if (a(minuteOfHour2)) {
                this.minuteOfHour = minuteOfHour2;
            }
            DateTimeField minuteOfDay2 = chronology.minuteOfDay();
            if (a(minuteOfDay2)) {
                this.minuteOfDay = minuteOfDay2;
            }
            DateTimeField hourOfDay2 = chronology.hourOfDay();
            if (a(hourOfDay2)) {
                this.hourOfDay = hourOfDay2;
            }
            DateTimeField clockhourOfDay2 = chronology.clockhourOfDay();
            if (a(clockhourOfDay2)) {
                this.clockhourOfDay = clockhourOfDay2;
            }
            DateTimeField hourOfHalfday2 = chronology.hourOfHalfday();
            if (a(hourOfHalfday2)) {
                this.hourOfHalfday = hourOfHalfday2;
            }
            DateTimeField clockhourOfHalfday2 = chronology.clockhourOfHalfday();
            if (a(clockhourOfHalfday2)) {
                this.clockhourOfHalfday = clockhourOfHalfday2;
            }
            DateTimeField halfdayOfDay2 = chronology.halfdayOfDay();
            if (a(halfdayOfDay2)) {
                this.halfdayOfDay = halfdayOfDay2;
            }
            DateTimeField dayOfWeek2 = chronology.dayOfWeek();
            if (a(dayOfWeek2)) {
                this.dayOfWeek = dayOfWeek2;
            }
            DateTimeField dayOfMonth2 = chronology.dayOfMonth();
            if (a(dayOfMonth2)) {
                this.dayOfMonth = dayOfMonth2;
            }
            DateTimeField dayOfYear2 = chronology.dayOfYear();
            if (a(dayOfYear2)) {
                this.dayOfYear = dayOfYear2;
            }
            DateTimeField weekOfWeekyear2 = chronology.weekOfWeekyear();
            if (a(weekOfWeekyear2)) {
                this.weekOfWeekyear = weekOfWeekyear2;
            }
            DateTimeField weekyear2 = chronology.weekyear();
            if (a(weekyear2)) {
                this.weekyear = weekyear2;
            }
            DateTimeField weekyearOfCentury2 = chronology.weekyearOfCentury();
            if (a(weekyearOfCentury2)) {
                this.weekyearOfCentury = weekyearOfCentury2;
            }
            DateTimeField monthOfYear2 = chronology.monthOfYear();
            if (a(monthOfYear2)) {
                this.monthOfYear = monthOfYear2;
            }
            DateTimeField year2 = chronology.year();
            if (a(year2)) {
                this.year = year2;
            }
            DateTimeField yearOfEra2 = chronology.yearOfEra();
            if (a(yearOfEra2)) {
                this.yearOfEra = yearOfEra2;
            }
            DateTimeField yearOfCentury2 = chronology.yearOfCentury();
            if (a(yearOfCentury2)) {
                this.yearOfCentury = yearOfCentury2;
            }
            DateTimeField centuryOfEra2 = chronology.centuryOfEra();
            if (a(centuryOfEra2)) {
                this.centuryOfEra = centuryOfEra2;
            }
            DateTimeField era2 = chronology.era();
            if (a(era2)) {
                this.era = era2;
            }
        }

        private static boolean a(DurationField durationField) {
            if (durationField == null) {
                return false;
            }
            return durationField.isSupported();
        }

        private static boolean a(DateTimeField dateTimeField) {
            if (dateTimeField == null) {
                return false;
            }
            return dateTimeField.isSupported();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void assemble(Fields fields);

    protected AssembledChronology(Chronology chronology, Object obj) {
        this.a = chronology;
        this.b = obj;
        a();
    }

    public DateTimeZone getZone() {
        Chronology chronology = this.a;
        if (chronology != null) {
            return chronology.getZone();
        }
        return null;
    }

    public long getDateTimeMillis(int i2, int i3, int i4, int i5) {
        Chronology chronology = this.a;
        if (chronology == null || (this.L & 6) != 6) {
            return super.getDateTimeMillis(i2, i3, i4, i5);
        }
        return chronology.getDateTimeMillis(i2, i3, i4, i5);
    }

    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Chronology chronology = this.a;
        if (chronology == null || (this.L & 5) != 5) {
            return super.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        }
        return chronology.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
    }

    public long getDateTimeMillis(long j2, int i2, int i3, int i4, int i5) {
        Chronology chronology = this.a;
        if (chronology == null || (this.L & 1) != 1) {
            return super.getDateTimeMillis(j2, i2, i3, i4, i5);
        }
        return chronology.getDateTimeMillis(j2, i2, i3, i4, i5);
    }

    public final DurationField millis() {
        return this.c;
    }

    public final DateTimeField millisOfSecond() {
        return this.o;
    }

    public final DateTimeField millisOfDay() {
        return this.p;
    }

    public final DurationField seconds() {
        return this.d;
    }

    public final DateTimeField secondOfMinute() {
        return this.q;
    }

    public final DateTimeField secondOfDay() {
        return this.r;
    }

    public final DurationField minutes() {
        return this.e;
    }

    public final DateTimeField minuteOfHour() {
        return this.s;
    }

    public final DateTimeField minuteOfDay() {
        return this.t;
    }

    public final DurationField hours() {
        return this.f;
    }

    public final DateTimeField hourOfDay() {
        return this.u;
    }

    public final DateTimeField clockhourOfDay() {
        return this.v;
    }

    public final DurationField halfdays() {
        return this.g;
    }

    public final DateTimeField hourOfHalfday() {
        return this.w;
    }

    public final DateTimeField clockhourOfHalfday() {
        return this.x;
    }

    public final DateTimeField halfdayOfDay() {
        return this.y;
    }

    public final DurationField days() {
        return this.h;
    }

    public final DateTimeField dayOfWeek() {
        return this.z;
    }

    public final DateTimeField dayOfMonth() {
        return this.A;
    }

    public final DateTimeField dayOfYear() {
        return this.B;
    }

    public final DurationField weeks() {
        return this.i;
    }

    public final DateTimeField weekOfWeekyear() {
        return this.C;
    }

    public final DurationField weekyears() {
        return this.j;
    }

    public final DateTimeField weekyear() {
        return this.D;
    }

    public final DateTimeField weekyearOfCentury() {
        return this.E;
    }

    public final DurationField months() {
        return this.k;
    }

    public final DateTimeField monthOfYear() {
        return this.F;
    }

    public final DurationField years() {
        return this.l;
    }

    public final DateTimeField year() {
        return this.G;
    }

    public final DateTimeField yearOfEra() {
        return this.H;
    }

    public final DateTimeField yearOfCentury() {
        return this.I;
    }

    public final DurationField centuries() {
        return this.m;
    }

    public final DateTimeField centuryOfEra() {
        return this.J;
    }

    public final DurationField eras() {
        return this.n;
    }

    public final DateTimeField era() {
        return this.K;
    }

    /* access modifiers changed from: protected */
    public final Chronology getBase() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final Object getParam() {
        return this.b;
    }

    private void a() {
        Fields fields = new Fields();
        if (this.a != null) {
            fields.copyFieldsFrom(this.a);
        }
        assemble(fields);
        DurationField durationField = fields.millis;
        if (durationField == null) {
            durationField = super.millis();
        }
        this.c = durationField;
        DurationField durationField2 = fields.seconds;
        if (durationField2 == null) {
            durationField2 = super.seconds();
        }
        this.d = durationField2;
        DurationField durationField3 = fields.minutes;
        if (durationField3 == null) {
            durationField3 = super.minutes();
        }
        this.e = durationField3;
        DurationField durationField4 = fields.hours;
        if (durationField4 == null) {
            durationField4 = super.hours();
        }
        this.f = durationField4;
        DurationField durationField5 = fields.halfdays;
        if (durationField5 == null) {
            durationField5 = super.halfdays();
        }
        this.g = durationField5;
        DurationField durationField6 = fields.days;
        if (durationField6 == null) {
            durationField6 = super.days();
        }
        this.h = durationField6;
        DurationField durationField7 = fields.weeks;
        if (durationField7 == null) {
            durationField7 = super.weeks();
        }
        this.i = durationField7;
        DurationField durationField8 = fields.weekyears;
        if (durationField8 == null) {
            durationField8 = super.weekyears();
        }
        this.j = durationField8;
        DurationField durationField9 = fields.months;
        if (durationField9 == null) {
            durationField9 = super.months();
        }
        this.k = durationField9;
        DurationField durationField10 = fields.years;
        if (durationField10 == null) {
            durationField10 = super.years();
        }
        this.l = durationField10;
        DurationField durationField11 = fields.centuries;
        if (durationField11 == null) {
            durationField11 = super.centuries();
        }
        this.m = durationField11;
        DurationField durationField12 = fields.eras;
        if (durationField12 == null) {
            durationField12 = super.eras();
        }
        this.n = durationField12;
        DateTimeField dateTimeField = fields.millisOfSecond;
        if (dateTimeField == null) {
            dateTimeField = super.millisOfSecond();
        }
        this.o = dateTimeField;
        DateTimeField dateTimeField2 = fields.millisOfDay;
        if (dateTimeField2 == null) {
            dateTimeField2 = super.millisOfDay();
        }
        this.p = dateTimeField2;
        DateTimeField dateTimeField3 = fields.secondOfMinute;
        if (dateTimeField3 == null) {
            dateTimeField3 = super.secondOfMinute();
        }
        this.q = dateTimeField3;
        DateTimeField dateTimeField4 = fields.secondOfDay;
        if (dateTimeField4 == null) {
            dateTimeField4 = super.secondOfDay();
        }
        this.r = dateTimeField4;
        DateTimeField dateTimeField5 = fields.minuteOfHour;
        if (dateTimeField5 == null) {
            dateTimeField5 = super.minuteOfHour();
        }
        this.s = dateTimeField5;
        DateTimeField dateTimeField6 = fields.minuteOfDay;
        if (dateTimeField6 == null) {
            dateTimeField6 = super.minuteOfDay();
        }
        this.t = dateTimeField6;
        DateTimeField dateTimeField7 = fields.hourOfDay;
        if (dateTimeField7 == null) {
            dateTimeField7 = super.hourOfDay();
        }
        this.u = dateTimeField7;
        DateTimeField dateTimeField8 = fields.clockhourOfDay;
        if (dateTimeField8 == null) {
            dateTimeField8 = super.clockhourOfDay();
        }
        this.v = dateTimeField8;
        DateTimeField dateTimeField9 = fields.hourOfHalfday;
        if (dateTimeField9 == null) {
            dateTimeField9 = super.hourOfHalfday();
        }
        this.w = dateTimeField9;
        DateTimeField dateTimeField10 = fields.clockhourOfHalfday;
        if (dateTimeField10 == null) {
            dateTimeField10 = super.clockhourOfHalfday();
        }
        this.x = dateTimeField10;
        DateTimeField dateTimeField11 = fields.halfdayOfDay;
        if (dateTimeField11 == null) {
            dateTimeField11 = super.halfdayOfDay();
        }
        this.y = dateTimeField11;
        DateTimeField dateTimeField12 = fields.dayOfWeek;
        if (dateTimeField12 == null) {
            dateTimeField12 = super.dayOfWeek();
        }
        this.z = dateTimeField12;
        DateTimeField dateTimeField13 = fields.dayOfMonth;
        if (dateTimeField13 == null) {
            dateTimeField13 = super.dayOfMonth();
        }
        this.A = dateTimeField13;
        DateTimeField dateTimeField14 = fields.dayOfYear;
        if (dateTimeField14 == null) {
            dateTimeField14 = super.dayOfYear();
        }
        this.B = dateTimeField14;
        DateTimeField dateTimeField15 = fields.weekOfWeekyear;
        if (dateTimeField15 == null) {
            dateTimeField15 = super.weekOfWeekyear();
        }
        this.C = dateTimeField15;
        DateTimeField dateTimeField16 = fields.weekyear;
        if (dateTimeField16 == null) {
            dateTimeField16 = super.weekyear();
        }
        this.D = dateTimeField16;
        DateTimeField dateTimeField17 = fields.weekyearOfCentury;
        if (dateTimeField17 == null) {
            dateTimeField17 = super.weekyearOfCentury();
        }
        this.E = dateTimeField17;
        DateTimeField dateTimeField18 = fields.monthOfYear;
        if (dateTimeField18 == null) {
            dateTimeField18 = super.monthOfYear();
        }
        this.F = dateTimeField18;
        DateTimeField dateTimeField19 = fields.year;
        if (dateTimeField19 == null) {
            dateTimeField19 = super.year();
        }
        this.G = dateTimeField19;
        DateTimeField dateTimeField20 = fields.yearOfEra;
        if (dateTimeField20 == null) {
            dateTimeField20 = super.yearOfEra();
        }
        this.H = dateTimeField20;
        DateTimeField dateTimeField21 = fields.yearOfCentury;
        if (dateTimeField21 == null) {
            dateTimeField21 = super.yearOfCentury();
        }
        this.I = dateTimeField21;
        DateTimeField dateTimeField22 = fields.centuryOfEra;
        if (dateTimeField22 == null) {
            dateTimeField22 = super.centuryOfEra();
        }
        this.J = dateTimeField22;
        DateTimeField dateTimeField23 = fields.era;
        if (dateTimeField23 == null) {
            dateTimeField23 = super.era();
        }
        this.K = dateTimeField23;
        int i2 = 0;
        if (this.a != null) {
            int i3 = ((this.u == this.a.hourOfDay() && this.s == this.a.minuteOfHour() && this.q == this.a.secondOfMinute() && this.o == this.a.millisOfSecond()) ? 1 : 0) | (this.p == this.a.millisOfDay() ? 2 : 0);
            if (this.G == this.a.year() && this.F == this.a.monthOfYear() && this.A == this.a.dayOfMonth()) {
                i2 = 4;
            }
            i2 |= i3;
        }
        this.L = i2;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        a();
    }
}
