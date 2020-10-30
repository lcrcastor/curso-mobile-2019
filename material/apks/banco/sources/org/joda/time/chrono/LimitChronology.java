package org.joda.time.chrono;

import cz.msebera.android.httpclient.message.TokenParser;
import java.util.HashMap;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.DecoratedDurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class LimitChronology extends AssembledChronology {
    private static final long serialVersionUID = 7670866536893052522L;
    final DateTime a;
    final DateTime b;
    private transient LimitChronology c;

    class LimitDateTimeField extends DecoratedDateTimeField {
        private final DurationField b;
        private final DurationField c;
        private final DurationField d;

        LimitDateTimeField(DateTimeField dateTimeField, DurationField durationField, DurationField durationField2, DurationField durationField3) {
            super(dateTimeField, dateTimeField.getType());
            this.b = durationField;
            this.c = durationField2;
            this.d = durationField3;
        }

        public int get(long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().get(j);
        }

        public String getAsText(long j, Locale locale) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getAsText(j, locale);
        }

        public String getAsShortText(long j, Locale locale) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getAsShortText(j, locale);
        }

        public long add(long j, int i) {
            LimitChronology.this.a(j, (String) null);
            long add = getWrappedField().add(j, i);
            LimitChronology.this.a(add, "resulting");
            return add;
        }

        public long add(long j, long j2) {
            LimitChronology.this.a(j, (String) null);
            long add = getWrappedField().add(j, j2);
            LimitChronology.this.a(add, "resulting");
            return add;
        }

        public long addWrapField(long j, int i) {
            LimitChronology.this.a(j, (String) null);
            long addWrapField = getWrappedField().addWrapField(j, i);
            LimitChronology.this.a(addWrapField, "resulting");
            return addWrapField;
        }

        public int getDifference(long j, long j2) {
            LimitChronology.this.a(j, "minuend");
            LimitChronology.this.a(j2, "subtrahend");
            return getWrappedField().getDifference(j, j2);
        }

        public long getDifferenceAsLong(long j, long j2) {
            LimitChronology.this.a(j, "minuend");
            LimitChronology.this.a(j2, "subtrahend");
            return getWrappedField().getDifferenceAsLong(j, j2);
        }

        public long set(long j, int i) {
            LimitChronology.this.a(j, (String) null);
            long j2 = getWrappedField().set(j, i);
            LimitChronology.this.a(j2, "resulting");
            return j2;
        }

        public long set(long j, String str, Locale locale) {
            LimitChronology.this.a(j, (String) null);
            long j2 = getWrappedField().set(j, str, locale);
            LimitChronology.this.a(j2, "resulting");
            return j2;
        }

        public final DurationField getDurationField() {
            return this.b;
        }

        public final DurationField getRangeDurationField() {
            return this.c;
        }

        public boolean isLeap(long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().isLeap(j);
        }

        public int getLeapAmount(long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getLeapAmount(j);
        }

        public final DurationField getLeapDurationField() {
            return this.d;
        }

        public long roundFloor(long j) {
            LimitChronology.this.a(j, (String) null);
            long roundFloor = getWrappedField().roundFloor(j);
            LimitChronology.this.a(roundFloor, "resulting");
            return roundFloor;
        }

        public long roundCeiling(long j) {
            LimitChronology.this.a(j, (String) null);
            long roundCeiling = getWrappedField().roundCeiling(j);
            LimitChronology.this.a(roundCeiling, "resulting");
            return roundCeiling;
        }

        public long roundHalfFloor(long j) {
            LimitChronology.this.a(j, (String) null);
            long roundHalfFloor = getWrappedField().roundHalfFloor(j);
            LimitChronology.this.a(roundHalfFloor, "resulting");
            return roundHalfFloor;
        }

        public long roundHalfCeiling(long j) {
            LimitChronology.this.a(j, (String) null);
            long roundHalfCeiling = getWrappedField().roundHalfCeiling(j);
            LimitChronology.this.a(roundHalfCeiling, "resulting");
            return roundHalfCeiling;
        }

        public long roundHalfEven(long j) {
            LimitChronology.this.a(j, (String) null);
            long roundHalfEven = getWrappedField().roundHalfEven(j);
            LimitChronology.this.a(roundHalfEven, "resulting");
            return roundHalfEven;
        }

        public long remainder(long j) {
            LimitChronology.this.a(j, (String) null);
            long remainder = getWrappedField().remainder(j);
            LimitChronology.this.a(remainder, "resulting");
            return remainder;
        }

        public int getMinimumValue(long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getMinimumValue(j);
        }

        public int getMaximumValue(long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getMaximumValue(j);
        }

        public int getMaximumTextLength(Locale locale) {
            return getWrappedField().getMaximumTextLength(locale);
        }

        public int getMaximumShortTextLength(Locale locale) {
            return getWrappedField().getMaximumShortTextLength(locale);
        }
    }

    class LimitDurationField extends DecoratedDurationField {
        private static final long serialVersionUID = 8049297699408782284L;

        LimitDurationField(DurationField durationField) {
            super(durationField, durationField.getType());
        }

        public int getValue(long j, long j2) {
            LimitChronology.this.a(j2, (String) null);
            return getWrappedField().getValue(j, j2);
        }

        public long getValueAsLong(long j, long j2) {
            LimitChronology.this.a(j2, (String) null);
            return getWrappedField().getValueAsLong(j, j2);
        }

        public long getMillis(int i, long j) {
            LimitChronology.this.a(j, (String) null);
            return getWrappedField().getMillis(i, j);
        }

        public long getMillis(long j, long j2) {
            LimitChronology.this.a(j2, (String) null);
            return getWrappedField().getMillis(j, j2);
        }

        public long add(long j, int i) {
            LimitChronology.this.a(j, (String) null);
            long add = getWrappedField().add(j, i);
            LimitChronology.this.a(add, "resulting");
            return add;
        }

        public long add(long j, long j2) {
            LimitChronology.this.a(j, (String) null);
            long add = getWrappedField().add(j, j2);
            LimitChronology.this.a(add, "resulting");
            return add;
        }

        public int getDifference(long j, long j2) {
            LimitChronology.this.a(j, "minuend");
            LimitChronology.this.a(j2, "subtrahend");
            return getWrappedField().getDifference(j, j2);
        }

        public long getDifferenceAsLong(long j, long j2) {
            LimitChronology.this.a(j, "minuend");
            LimitChronology.this.a(j2, "subtrahend");
            return getWrappedField().getDifferenceAsLong(j, j2);
        }
    }

    class LimitException extends IllegalArgumentException {
        private static final long serialVersionUID = -5924689995607498581L;
        private final boolean b;

        LimitException(String str, boolean z) {
            super(str);
            this.b = z;
        }

        public String getMessage() {
            StringBuffer stringBuffer = new StringBuffer(85);
            stringBuffer.append("The");
            String message = super.getMessage();
            if (message != null) {
                stringBuffer.append(TokenParser.SP);
                stringBuffer.append(message);
            }
            stringBuffer.append(" instant is ");
            DateTimeFormatter withChronology = ISODateTimeFormat.dateTime().withChronology(LimitChronology.this.getBase());
            if (this.b) {
                stringBuffer.append("below the supported minimum of ");
                withChronology.printTo(stringBuffer, LimitChronology.this.getLowerLimit().getMillis());
            } else {
                stringBuffer.append("above the supported maximum of ");
                withChronology.printTo(stringBuffer, LimitChronology.this.getUpperLimit().getMillis());
            }
            stringBuffer.append(" (");
            stringBuffer.append(LimitChronology.this.getBase());
            stringBuffer.append(')');
            return stringBuffer.toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("IllegalArgumentException: ");
            sb.append(getMessage());
            return sb.toString();
        }
    }

    public static LimitChronology getInstance(Chronology chronology, ReadableDateTime readableDateTime, ReadableDateTime readableDateTime2) {
        DateTime dateTime;
        if (chronology == null) {
            throw new IllegalArgumentException("Must supply a chronology");
        }
        DateTime dateTime2 = null;
        if (readableDateTime == null) {
            dateTime = null;
        } else {
            dateTime = readableDateTime.toDateTime();
        }
        if (readableDateTime2 != null) {
            dateTime2 = readableDateTime2.toDateTime();
        }
        if (dateTime == null || dateTime2 == null || dateTime.isBefore(dateTime2)) {
            return new LimitChronology(chronology, dateTime, dateTime2);
        }
        throw new IllegalArgumentException("The lower limit must be come before than the upper limit");
    }

    private LimitChronology(Chronology chronology, DateTime dateTime, DateTime dateTime2) {
        super(chronology, null);
        this.a = dateTime;
        this.b = dateTime2;
    }

    public DateTime getLowerLimit() {
        return this.a;
    }

    public DateTime getUpperLimit() {
        return this.b;
    }

    public Chronology withUTC() {
        return withZone(DateTimeZone.UTC);
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        if (dateTimeZone == DateTimeZone.UTC && this.c != null) {
            return this.c;
        }
        DateTime dateTime = this.a;
        if (dateTime != null) {
            MutableDateTime mutableDateTime = dateTime.toMutableDateTime();
            mutableDateTime.setZoneRetainFields(dateTimeZone);
            dateTime = mutableDateTime.toDateTime();
        }
        DateTime dateTime2 = this.b;
        if (dateTime2 != null) {
            MutableDateTime mutableDateTime2 = dateTime2.toMutableDateTime();
            mutableDateTime2.setZoneRetainFields(dateTimeZone);
            dateTime2 = mutableDateTime2.toDateTime();
        }
        LimitChronology instance = getInstance(getBase().withZone(dateTimeZone), dateTime, dateTime2);
        if (dateTimeZone == DateTimeZone.UTC) {
            this.c = instance;
        }
        return instance;
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4) {
        long dateTimeMillis = getBase().getDateTimeMillis(i, i2, i3, i4);
        a(dateTimeMillis, "resulting");
        return dateTimeMillis;
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        long dateTimeMillis = getBase().getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
        a(dateTimeMillis, "resulting");
        return dateTimeMillis;
    }

    public long getDateTimeMillis(long j, int i, int i2, int i3, int i4) {
        a(j, (String) null);
        long dateTimeMillis = getBase().getDateTimeMillis(j, i, i2, i3, i4);
        a(dateTimeMillis, "resulting");
        return dateTimeMillis;
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
        LimitDurationField limitDurationField = new LimitDurationField(durationField);
        hashMap.put(durationField, limitDurationField);
        return limitDurationField;
    }

    private DateTimeField a(DateTimeField dateTimeField, HashMap<Object, Object> hashMap) {
        if (dateTimeField == null || !dateTimeField.isSupported()) {
            return dateTimeField;
        }
        if (hashMap.containsKey(dateTimeField)) {
            return (DateTimeField) hashMap.get(dateTimeField);
        }
        LimitDateTimeField limitDateTimeField = new LimitDateTimeField(dateTimeField, a(dateTimeField.getDurationField(), hashMap), a(dateTimeField.getRangeDurationField(), hashMap), a(dateTimeField.getLeapDurationField(), hashMap));
        hashMap.put(dateTimeField, limitDateTimeField);
        return limitDateTimeField;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j, String str) {
        DateTime dateTime = this.a;
        if (dateTime == null || j >= dateTime.getMillis()) {
            DateTime dateTime2 = this.b;
            if (dateTime2 != null && j >= dateTime2.getMillis()) {
                throw new LimitException(str, false);
            }
            return;
        }
        throw new LimitException(str, true);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LimitChronology)) {
            return false;
        }
        LimitChronology limitChronology = (LimitChronology) obj;
        if (!getBase().equals(limitChronology.getBase()) || !FieldUtils.equals(getLowerLimit(), limitChronology.getLowerLimit()) || !FieldUtils.equals(getUpperLimit(), limitChronology.getUpperLimit())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (getLowerLimit() != null ? getLowerLimit().hashCode() : 0) + 317351877;
        if (getUpperLimit() != null) {
            i = getUpperLimit().hashCode();
        }
        return hashCode + i + (getBase().hashCode() * 7);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LimitChronology[");
        sb.append(getBase().toString());
        sb.append(", ");
        sb.append(getLowerLimit() == null ? "NoLimit" : getLowerLimit().toString());
        sb.append(", ");
        sb.append(getUpperLimit() == null ? "NoLimit" : getUpperLimit().toString());
        sb.append(']');
        return sb.toString();
    }
}
