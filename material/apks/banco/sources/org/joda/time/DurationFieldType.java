package org.joda.time;

import com.google.common.base.Ascii;
import java.io.Serializable;

public abstract class DurationFieldType implements Serializable {
    static final DurationFieldType a = new StandardDurationFieldType("eras", 1);
    static final DurationFieldType b = new StandardDurationFieldType("centuries", 2);
    static final DurationFieldType c = new StandardDurationFieldType("weekyears", 3);
    static final DurationFieldType d = new StandardDurationFieldType("years", 4);
    static final DurationFieldType e = new StandardDurationFieldType("months", 5);
    static final DurationFieldType f = new StandardDurationFieldType("weeks", 6);
    static final DurationFieldType g = new StandardDurationFieldType("days", 7);
    static final DurationFieldType h = new StandardDurationFieldType("halfdays", 8);
    static final DurationFieldType i = new StandardDurationFieldType("hours", 9);
    static final DurationFieldType j = new StandardDurationFieldType("minutes", 10);
    static final DurationFieldType k = new StandardDurationFieldType("seconds", Ascii.VT);
    static final DurationFieldType l = new StandardDurationFieldType("millis", Ascii.FF);
    private static final long serialVersionUID = 8765135187319L;
    private final String m;

    static class StandardDurationFieldType extends DurationFieldType {
        private static final long serialVersionUID = 31156755687123L;
        private final byte m;

        StandardDurationFieldType(String str, byte b) {
            super(str);
            this.m = b;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StandardDurationFieldType)) {
                return false;
            }
            if (this.m != ((StandardDurationFieldType) obj).m) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return 1 << this.m;
        }

        public DurationField getField(Chronology chronology) {
            Chronology chronology2 = DateTimeUtils.getChronology(chronology);
            switch (this.m) {
                case 1:
                    return chronology2.eras();
                case 2:
                    return chronology2.centuries();
                case 3:
                    return chronology2.weekyears();
                case 4:
                    return chronology2.years();
                case 5:
                    return chronology2.months();
                case 6:
                    return chronology2.weeks();
                case 7:
                    return chronology2.days();
                case 8:
                    return chronology2.halfdays();
                case 9:
                    return chronology2.hours();
                case 10:
                    return chronology2.minutes();
                case 11:
                    return chronology2.seconds();
                case 12:
                    return chronology2.millis();
                default:
                    throw new InternalError();
            }
        }

        private Object readResolve() {
            switch (this.m) {
                case 1:
                    return a;
                case 2:
                    return b;
                case 3:
                    return c;
                case 4:
                    return d;
                case 5:
                    return e;
                case 6:
                    return f;
                case 7:
                    return g;
                case 8:
                    return h;
                case 9:
                    return i;
                case 10:
                    return j;
                case 11:
                    return k;
                case 12:
                    return l;
                default:
                    return this;
            }
        }
    }

    public abstract DurationField getField(Chronology chronology);

    protected DurationFieldType(String str) {
        this.m = str;
    }

    public static DurationFieldType millis() {
        return l;
    }

    public static DurationFieldType seconds() {
        return k;
    }

    public static DurationFieldType minutes() {
        return j;
    }

    public static DurationFieldType hours() {
        return i;
    }

    public static DurationFieldType halfdays() {
        return h;
    }

    public static DurationFieldType days() {
        return g;
    }

    public static DurationFieldType weeks() {
        return f;
    }

    public static DurationFieldType weekyears() {
        return c;
    }

    public static DurationFieldType months() {
        return e;
    }

    public static DurationFieldType years() {
        return d;
    }

    public static DurationFieldType centuries() {
        return b;
    }

    public static DurationFieldType eras() {
        return a;
    }

    public String getName() {
        return this.m;
    }

    public boolean isSupported(Chronology chronology) {
        return getField(chronology).isSupported();
    }

    public String toString() {
        return getName();
    }
}
