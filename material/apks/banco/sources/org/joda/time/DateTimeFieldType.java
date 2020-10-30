package org.joda.time;

import com.google.common.base.Ascii;
import java.io.Serializable;

public abstract class DateTimeFieldType implements Serializable {
    /* access modifiers changed from: private */
    public static final DateTimeFieldType a = new StandardDateTimeFieldType("era", 1, DurationFieldType.eras(), null);
    /* access modifiers changed from: private */
    public static final DateTimeFieldType b = new StandardDateTimeFieldType("yearOfEra", 2, DurationFieldType.years(), DurationFieldType.eras());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType c = new StandardDateTimeFieldType("centuryOfEra", 3, DurationFieldType.centuries(), DurationFieldType.eras());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType d = new StandardDateTimeFieldType("yearOfCentury", 4, DurationFieldType.years(), DurationFieldType.centuries());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType e = new StandardDateTimeFieldType("year", 5, DurationFieldType.years(), null);
    /* access modifiers changed from: private */
    public static final DateTimeFieldType f = new StandardDateTimeFieldType("dayOfYear", 6, DurationFieldType.days(), DurationFieldType.years());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType g = new StandardDateTimeFieldType("monthOfYear", 7, DurationFieldType.months(), DurationFieldType.years());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType h = new StandardDateTimeFieldType("dayOfMonth", 8, DurationFieldType.days(), DurationFieldType.months());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType i = new StandardDateTimeFieldType("weekyearOfCentury", 9, DurationFieldType.weekyears(), DurationFieldType.centuries());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType j = new StandardDateTimeFieldType("weekyear", 10, DurationFieldType.weekyears(), null);
    /* access modifiers changed from: private */
    public static final DateTimeFieldType k = new StandardDateTimeFieldType("weekOfWeekyear", Ascii.VT, DurationFieldType.weeks(), DurationFieldType.weekyears());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType l = new StandardDateTimeFieldType("dayOfWeek", Ascii.FF, DurationFieldType.days(), DurationFieldType.weeks());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType m = new StandardDateTimeFieldType("halfdayOfDay", Ascii.CR, DurationFieldType.halfdays(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType n = new StandardDateTimeFieldType("hourOfHalfday", Ascii.SO, DurationFieldType.hours(), DurationFieldType.halfdays());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType o = new StandardDateTimeFieldType("clockhourOfHalfday", Ascii.SI, DurationFieldType.hours(), DurationFieldType.halfdays());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType p = new StandardDateTimeFieldType("clockhourOfDay", Ascii.DLE, DurationFieldType.hours(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType q = new StandardDateTimeFieldType("hourOfDay", 17, DurationFieldType.hours(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType r = new StandardDateTimeFieldType("minuteOfDay", Ascii.DC2, DurationFieldType.minutes(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType s = new StandardDateTimeFieldType("minuteOfHour", 19, DurationFieldType.minutes(), DurationFieldType.hours());
    private static final long serialVersionUID = -42615285973990L;
    /* access modifiers changed from: private */
    public static final DateTimeFieldType t = new StandardDateTimeFieldType("secondOfDay", Ascii.DC4, DurationFieldType.seconds(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType u = new StandardDateTimeFieldType("secondOfMinute", Ascii.NAK, DurationFieldType.seconds(), DurationFieldType.minutes());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType v = new StandardDateTimeFieldType("millisOfDay", Ascii.SYN, DurationFieldType.millis(), DurationFieldType.days());
    /* access modifiers changed from: private */
    public static final DateTimeFieldType w = new StandardDateTimeFieldType("millisOfSecond", Ascii.ETB, DurationFieldType.millis(), DurationFieldType.seconds());
    private final String x;

    static class StandardDateTimeFieldType extends DateTimeFieldType {
        private static final long serialVersionUID = -9937958251642L;
        private final byte a;
        private final transient DurationFieldType b;
        private final transient DurationFieldType c;

        StandardDateTimeFieldType(String str, byte b2, DurationFieldType durationFieldType, DurationFieldType durationFieldType2) {
            super(str);
            this.a = b2;
            this.b = durationFieldType;
            this.c = durationFieldType2;
        }

        public DurationFieldType getDurationType() {
            return this.b;
        }

        public DurationFieldType getRangeDurationType() {
            return this.c;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StandardDateTimeFieldType)) {
                return false;
            }
            if (this.a != ((StandardDateTimeFieldType) obj).a) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return 1 << this.a;
        }

        public DateTimeField getField(Chronology chronology) {
            Chronology chronology2 = DateTimeUtils.getChronology(chronology);
            switch (this.a) {
                case 1:
                    return chronology2.era();
                case 2:
                    return chronology2.yearOfEra();
                case 3:
                    return chronology2.centuryOfEra();
                case 4:
                    return chronology2.yearOfCentury();
                case 5:
                    return chronology2.year();
                case 6:
                    return chronology2.dayOfYear();
                case 7:
                    return chronology2.monthOfYear();
                case 8:
                    return chronology2.dayOfMonth();
                case 9:
                    return chronology2.weekyearOfCentury();
                case 10:
                    return chronology2.weekyear();
                case 11:
                    return chronology2.weekOfWeekyear();
                case 12:
                    return chronology2.dayOfWeek();
                case 13:
                    return chronology2.halfdayOfDay();
                case 14:
                    return chronology2.hourOfHalfday();
                case 15:
                    return chronology2.clockhourOfHalfday();
                case 16:
                    return chronology2.clockhourOfDay();
                case 17:
                    return chronology2.hourOfDay();
                case 18:
                    return chronology2.minuteOfDay();
                case 19:
                    return chronology2.minuteOfHour();
                case 20:
                    return chronology2.secondOfDay();
                case 21:
                    return chronology2.secondOfMinute();
                case 22:
                    return chronology2.millisOfDay();
                case 23:
                    return chronology2.millisOfSecond();
                default:
                    throw new InternalError();
            }
        }

        private Object readResolve() {
            switch (this.a) {
                case 1:
                    return DateTimeFieldType.a;
                case 2:
                    return DateTimeFieldType.b;
                case 3:
                    return DateTimeFieldType.c;
                case 4:
                    return DateTimeFieldType.d;
                case 5:
                    return DateTimeFieldType.e;
                case 6:
                    return DateTimeFieldType.f;
                case 7:
                    return DateTimeFieldType.g;
                case 8:
                    return DateTimeFieldType.h;
                case 9:
                    return DateTimeFieldType.i;
                case 10:
                    return DateTimeFieldType.j;
                case 11:
                    return DateTimeFieldType.k;
                case 12:
                    return DateTimeFieldType.l;
                case 13:
                    return DateTimeFieldType.m;
                case 14:
                    return DateTimeFieldType.n;
                case 15:
                    return DateTimeFieldType.o;
                case 16:
                    return DateTimeFieldType.p;
                case 17:
                    return DateTimeFieldType.q;
                case 18:
                    return DateTimeFieldType.r;
                case 19:
                    return DateTimeFieldType.s;
                case 20:
                    return DateTimeFieldType.t;
                case 21:
                    return DateTimeFieldType.u;
                case 22:
                    return DateTimeFieldType.v;
                case 23:
                    return DateTimeFieldType.w;
                default:
                    return this;
            }
        }
    }

    public abstract DurationFieldType getDurationType();

    public abstract DateTimeField getField(Chronology chronology);

    public abstract DurationFieldType getRangeDurationType();

    protected DateTimeFieldType(String str) {
        this.x = str;
    }

    public static DateTimeFieldType millisOfSecond() {
        return w;
    }

    public static DateTimeFieldType millisOfDay() {
        return v;
    }

    public static DateTimeFieldType secondOfMinute() {
        return u;
    }

    public static DateTimeFieldType secondOfDay() {
        return t;
    }

    public static DateTimeFieldType minuteOfHour() {
        return s;
    }

    public static DateTimeFieldType minuteOfDay() {
        return r;
    }

    public static DateTimeFieldType hourOfDay() {
        return q;
    }

    public static DateTimeFieldType clockhourOfDay() {
        return p;
    }

    public static DateTimeFieldType hourOfHalfday() {
        return n;
    }

    public static DateTimeFieldType clockhourOfHalfday() {
        return o;
    }

    public static DateTimeFieldType halfdayOfDay() {
        return m;
    }

    public static DateTimeFieldType dayOfWeek() {
        return l;
    }

    public static DateTimeFieldType dayOfMonth() {
        return h;
    }

    public static DateTimeFieldType dayOfYear() {
        return f;
    }

    public static DateTimeFieldType weekOfWeekyear() {
        return k;
    }

    public static DateTimeFieldType weekyear() {
        return j;
    }

    public static DateTimeFieldType weekyearOfCentury() {
        return i;
    }

    public static DateTimeFieldType monthOfYear() {
        return g;
    }

    public static DateTimeFieldType year() {
        return e;
    }

    public static DateTimeFieldType yearOfEra() {
        return b;
    }

    public static DateTimeFieldType yearOfCentury() {
        return d;
    }

    public static DateTimeFieldType centuryOfEra() {
        return c;
    }

    public static DateTimeFieldType era() {
        return a;
    }

    public String getName() {
        return this.x;
    }

    public boolean isSupported(Chronology chronology) {
        return getField(chronology).isSupported();
    }

    public String toString() {
        return getName();
    }
}
