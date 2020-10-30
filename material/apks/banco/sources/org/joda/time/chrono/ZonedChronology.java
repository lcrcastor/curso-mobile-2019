package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;
import org.joda.time.ReadablePartial;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.BaseDurationField;

public final class ZonedChronology extends AssembledChronology {
    private static final long serialVersionUID = -1079258847191166848L;

    static final class ZonedDateTimeField extends BaseDateTimeField {
        final DateTimeField a;
        final DateTimeZone b;
        final DurationField c;
        final boolean d;
        final DurationField e;
        final DurationField f;

        ZonedDateTimeField(DateTimeField dateTimeField, DateTimeZone dateTimeZone, DurationField durationField, DurationField durationField2, DurationField durationField3) {
            super(dateTimeField.getType());
            if (!dateTimeField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.a = dateTimeField;
            this.b = dateTimeZone;
            this.c = durationField;
            this.d = ZonedChronology.a(durationField);
            this.e = durationField2;
            this.f = durationField3;
        }

        public boolean isLenient() {
            return this.a.isLenient();
        }

        public int get(long j) {
            return this.a.get(this.b.convertUTCToLocal(j));
        }

        public String getAsText(long j, Locale locale) {
            return this.a.getAsText(this.b.convertUTCToLocal(j), locale);
        }

        public String getAsShortText(long j, Locale locale) {
            return this.a.getAsShortText(this.b.convertUTCToLocal(j), locale);
        }

        public String getAsText(int i, Locale locale) {
            return this.a.getAsText(i, locale);
        }

        public String getAsShortText(int i, Locale locale) {
            return this.a.getAsShortText(i, locale);
        }

        public long add(long j, int i) {
            if (this.d) {
                long a2 = (long) a(j);
                return this.a.add(j + a2, i) - a2;
            }
            return this.b.convertLocalToUTC(this.a.add(this.b.convertUTCToLocal(j), i), false, j);
        }

        public long add(long j, long j2) {
            if (this.d) {
                long a2 = (long) a(j);
                return this.a.add(j + a2, j2) - a2;
            }
            return this.b.convertLocalToUTC(this.a.add(this.b.convertUTCToLocal(j), j2), false, j);
        }

        public long addWrapField(long j, int i) {
            if (this.d) {
                long a2 = (long) a(j);
                return this.a.addWrapField(j + a2, i) - a2;
            }
            return this.b.convertLocalToUTC(this.a.addWrapField(this.b.convertUTCToLocal(j), i), false, j);
        }

        public long set(long j, int i) {
            long j2 = this.a.set(this.b.convertUTCToLocal(j), i);
            long convertLocalToUTC = this.b.convertLocalToUTC(j2, false, j);
            if (get(convertLocalToUTC) == i) {
                return convertLocalToUTC;
            }
            IllegalInstantException illegalInstantException = new IllegalInstantException(j2, this.b.getID());
            IllegalFieldValueException illegalFieldValueException = new IllegalFieldValueException(this.a.getType(), Integer.valueOf(i), illegalInstantException.getMessage());
            illegalFieldValueException.initCause(illegalInstantException);
            throw illegalFieldValueException;
        }

        public long set(long j, String str, Locale locale) {
            return this.b.convertLocalToUTC(this.a.set(this.b.convertUTCToLocal(j), str, locale), false, j);
        }

        public int getDifference(long j, long j2) {
            int a2 = a(j2);
            return this.a.getDifference(j + ((long) (this.d ? a2 : a(j))), j2 + ((long) a2));
        }

        public long getDifferenceAsLong(long j, long j2) {
            int a2 = a(j2);
            return this.a.getDifferenceAsLong(j + ((long) (this.d ? a2 : a(j))), j2 + ((long) a2));
        }

        public final DurationField getDurationField() {
            return this.c;
        }

        public final DurationField getRangeDurationField() {
            return this.e;
        }

        public boolean isLeap(long j) {
            return this.a.isLeap(this.b.convertUTCToLocal(j));
        }

        public int getLeapAmount(long j) {
            return this.a.getLeapAmount(this.b.convertUTCToLocal(j));
        }

        public final DurationField getLeapDurationField() {
            return this.f;
        }

        public long roundFloor(long j) {
            if (this.d) {
                long a2 = (long) a(j);
                return this.a.roundFloor(j + a2) - a2;
            }
            return this.b.convertLocalToUTC(this.a.roundFloor(this.b.convertUTCToLocal(j)), false, j);
        }

        public long roundCeiling(long j) {
            if (this.d) {
                long a2 = (long) a(j);
                return this.a.roundCeiling(j + a2) - a2;
            }
            return this.b.convertLocalToUTC(this.a.roundCeiling(this.b.convertUTCToLocal(j)), false, j);
        }

        public long remainder(long j) {
            return this.a.remainder(this.b.convertUTCToLocal(j));
        }

        public int getMinimumValue() {
            return this.a.getMinimumValue();
        }

        public int getMinimumValue(long j) {
            return this.a.getMinimumValue(this.b.convertUTCToLocal(j));
        }

        public int getMinimumValue(ReadablePartial readablePartial) {
            return this.a.getMinimumValue(readablePartial);
        }

        public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.a.getMinimumValue(readablePartial, iArr);
        }

        public int getMaximumValue() {
            return this.a.getMaximumValue();
        }

        public int getMaximumValue(long j) {
            return this.a.getMaximumValue(this.b.convertUTCToLocal(j));
        }

        public int getMaximumValue(ReadablePartial readablePartial) {
            return this.a.getMaximumValue(readablePartial);
        }

        public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.a.getMaximumValue(readablePartial, iArr);
        }

        public int getMaximumTextLength(Locale locale) {
            return this.a.getMaximumTextLength(locale);
        }

        public int getMaximumShortTextLength(Locale locale) {
            return this.a.getMaximumShortTextLength(locale);
        }

        private int a(long j) {
            int offset = this.b.getOffset(j);
            long j2 = (long) offset;
            if ((j ^ (j + j2)) >= 0 || (j ^ j2) < 0) {
                return offset;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ZonedDateTimeField)) {
                return false;
            }
            ZonedDateTimeField zonedDateTimeField = (ZonedDateTimeField) obj;
            if (!this.a.equals(zonedDateTimeField.a) || !this.b.equals(zonedDateTimeField.b) || !this.c.equals(zonedDateTimeField.c) || !this.e.equals(zonedDateTimeField.e)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }
    }

    static class ZonedDurationField extends BaseDurationField {
        private static final long serialVersionUID = -485345310999208286L;
        final DurationField a;
        final boolean b;
        final DateTimeZone c;

        ZonedDurationField(DurationField durationField, DateTimeZone dateTimeZone) {
            super(durationField.getType());
            if (!durationField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.a = durationField;
            this.b = ZonedChronology.a(durationField);
            this.c = dateTimeZone;
        }

        public boolean isPrecise() {
            if (this.b) {
                return this.a.isPrecise();
            }
            return this.a.isPrecise() && this.c.isFixed();
        }

        public long getUnitMillis() {
            return this.a.getUnitMillis();
        }

        public int getValue(long j, long j2) {
            return this.a.getValue(j, c(j2));
        }

        public long getValueAsLong(long j, long j2) {
            return this.a.getValueAsLong(j, c(j2));
        }

        public long getMillis(int i, long j) {
            return this.a.getMillis(i, c(j));
        }

        public long getMillis(long j, long j2) {
            return this.a.getMillis(j, c(j2));
        }

        public long add(long j, int i) {
            int a2 = a(j);
            long add = this.a.add(j + ((long) a2), i);
            if (!this.b) {
                a2 = b(add);
            }
            return add - ((long) a2);
        }

        public long add(long j, long j2) {
            int a2 = a(j);
            long add = this.a.add(j + ((long) a2), j2);
            if (!this.b) {
                a2 = b(add);
            }
            return add - ((long) a2);
        }

        public int getDifference(long j, long j2) {
            int a2 = a(j2);
            return this.a.getDifference(j + ((long) (this.b ? a2 : a(j))), j2 + ((long) a2));
        }

        public long getDifferenceAsLong(long j, long j2) {
            int a2 = a(j2);
            return this.a.getDifferenceAsLong(j + ((long) (this.b ? a2 : a(j))), j2 + ((long) a2));
        }

        private int a(long j) {
            int offset = this.c.getOffset(j);
            long j2 = (long) offset;
            if ((j ^ (j + j2)) >= 0 || (j ^ j2) < 0) {
                return offset;
            }
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }

        private int b(long j) {
            int offsetFromLocal = this.c.getOffsetFromLocal(j);
            long j2 = (long) offsetFromLocal;
            if ((j ^ (j - j2)) >= 0 || (j ^ j2) >= 0) {
                return offsetFromLocal;
            }
            throw new ArithmeticException("Subtracting time zone offset caused overflow");
        }

        private long c(long j) {
            return this.c.convertUTCToLocal(j);
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ZonedDurationField)) {
                return false;
            }
            ZonedDurationField zonedDurationField = (ZonedDurationField) obj;
            if (!this.a.equals(zonedDurationField.a) || !this.c.equals(zonedDurationField.c)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.c.hashCode();
        }
    }

    public static ZonedChronology getInstance(Chronology chronology, DateTimeZone dateTimeZone) {
        if (chronology == null) {
            throw new IllegalArgumentException("Must supply a chronology");
        }
        Chronology withUTC = chronology.withUTC();
        if (withUTC == null) {
            throw new IllegalArgumentException("UTC chronology must not be null");
        } else if (dateTimeZone != null) {
            return new ZonedChronology(withUTC, dateTimeZone);
        } else {
            throw new IllegalArgumentException("DateTimeZone must not be null");
        }
    }

    static boolean a(DurationField durationField) {
        return durationField != null && durationField.getUnitMillis() < 43200000;
    }

    private ZonedChronology(Chronology chronology, DateTimeZone dateTimeZone) {
        super(chronology, dateTimeZone);
    }

    public DateTimeZone getZone() {
        return (DateTimeZone) getParam();
    }

    public Chronology withUTC() {
        return getBase();
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getParam()) {
            return this;
        }
        if (dateTimeZone == DateTimeZone.UTC) {
            return getBase();
        }
        return new ZonedChronology(getBase(), dateTimeZone);
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4) {
        return a(getBase().getDateTimeMillis(i, i2, i3, i4));
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(getBase().getDateTimeMillis(i, i2, i3, i4, i5, i6, i7));
    }

    public long getDateTimeMillis(long j, int i, int i2, int i3, int i4) {
        return a(getBase().getDateTimeMillis(j + ((long) getZone().getOffset(j)), i, i2, i3, i4));
    }

    private long a(long j) {
        if (j == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        if (j == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        DateTimeZone zone = getZone();
        int offsetFromLocal = zone.getOffsetFromLocal(j);
        long j2 = j - ((long) offsetFromLocal);
        if (j > 604800000 && j2 < 0) {
            return Long.MAX_VALUE;
        }
        if (j < -604800000 && j2 > 0) {
            return Long.MIN_VALUE;
        }
        if (offsetFromLocal == zone.getOffset(j2)) {
            return j2;
        }
        throw new IllegalInstantException(j, zone.getID());
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        HashMap hashMap = new HashMap();
        fields.eras = a(fields.eras, hashMap);
        fields.centuries = a(fields.centuries, hashMap);
        fields.years = a(fields.years, hashMap);
        fields.months = a(fields.months, hashMap);
        fields.weekyears = a(fields.weekyears, hashMap);
        fields.weeks = a(fields.weeks, hashMap);
        fields.days = a(fields.days, hashMap);
        fields.halfdays = a(fields.halfdays, hashMap);
        fields.hours = a(fields.hours, hashMap);
        fields.minutes = a(fields.minutes, hashMap);
        fields.seconds = a(fields.seconds, hashMap);
        fields.millis = a(fields.millis, hashMap);
        fields.year = a(fields.year, hashMap);
        fields.yearOfEra = a(fields.yearOfEra, hashMap);
        fields.yearOfCentury = a(fields.yearOfCentury, hashMap);
        fields.centuryOfEra = a(fields.centuryOfEra, hashMap);
        fields.era = a(fields.era, hashMap);
        fields.dayOfWeek = a(fields.dayOfWeek, hashMap);
        fields.dayOfMonth = a(fields.dayOfMonth, hashMap);
        fields.dayOfYear = a(fields.dayOfYear, hashMap);
        fields.monthOfYear = a(fields.monthOfYear, hashMap);
        fields.weekOfWeekyear = a(fields.weekOfWeekyear, hashMap);
        fields.weekyear = a(fields.weekyear, hashMap);
        fields.weekyearOfCentury = a(fields.weekyearOfCentury, hashMap);
        fields.millisOfSecond = a(fields.millisOfSecond, hashMap);
        fields.millisOfDay = a(fields.millisOfDay, hashMap);
        fields.secondOfMinute = a(fields.secondOfMinute, hashMap);
        fields.secondOfDay = a(fields.secondOfDay, hashMap);
        fields.minuteOfHour = a(fields.minuteOfHour, hashMap);
        fields.minuteOfDay = a(fields.minuteOfDay, hashMap);
        fields.hourOfDay = a(fields.hourOfDay, hashMap);
        fields.hourOfHalfday = a(fields.hourOfHalfday, hashMap);
        fields.clockhourOfDay = a(fields.clockhourOfDay, hashMap);
        fields.clockhourOfHalfday = a(fields.clockhourOfHalfday, hashMap);
        fields.halfdayOfDay = a(fields.halfdayOfDay, hashMap);
    }

    private DurationField a(DurationField durationField, HashMap<Object, Object> hashMap) {
        if (durationField == null || !durationField.isSupported()) {
            return durationField;
        }
        if (hashMap.containsKey(durationField)) {
            return (DurationField) hashMap.get(durationField);
        }
        ZonedDurationField zonedDurationField = new ZonedDurationField(durationField, getZone());
        hashMap.put(durationField, zonedDurationField);
        return zonedDurationField;
    }

    private DateTimeField a(DateTimeField dateTimeField, HashMap<Object, Object> hashMap) {
        if (dateTimeField == null || !dateTimeField.isSupported()) {
            return dateTimeField;
        }
        if (hashMap.containsKey(dateTimeField)) {
            return (DateTimeField) hashMap.get(dateTimeField);
        }
        ZonedDateTimeField zonedDateTimeField = new ZonedDateTimeField(dateTimeField, getZone(), a(dateTimeField.getDurationField(), hashMap), a(dateTimeField.getRangeDurationField(), hashMap), a(dateTimeField.getLeapDurationField(), hashMap));
        hashMap.put(dateTimeField, zonedDateTimeField);
        return zonedDateTimeField;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonedChronology)) {
            return false;
        }
        ZonedChronology zonedChronology = (ZonedChronology) obj;
        if (!getBase().equals(zonedChronology.getBase()) || !getZone().equals(zonedChronology.getZone())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (getZone().hashCode() * 11) + 326565 + (getBase().hashCode() * 7);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ZonedChronology[");
        sb.append(getBase());
        sb.append(", ");
        sb.append(getZone().getID());
        sb.append(']');
        return sb.toString();
    }
}
