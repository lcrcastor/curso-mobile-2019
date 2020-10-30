package org.joda.time;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseLocal;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.PartialConverter;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class LocalTime extends BaseLocal implements Serializable, ReadablePartial {
    public static final LocalTime MIDNIGHT = new LocalTime(0, 0, 0, 0);
    private static final Set<DurationFieldType> a = new HashSet();
    private static final long serialVersionUID = -12873158713873L;
    private final long b;
    private final Chronology c;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -325842547277223L;
        private transient LocalTime a;
        private transient DateTimeField b;

        Property(LocalTime localTime, DateTimeField dateTimeField) {
            this.a = localTime;
            this.b = dateTimeField;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(this.b.getType());
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = (LocalTime) objectInputStream.readObject();
            this.b = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.a.getChronology());
        }

        public DateTimeField getField() {
            return this.b;
        }

        /* access modifiers changed from: protected */
        public long getMillis() {
            return this.a.getLocalMillis();
        }

        /* access modifiers changed from: protected */
        public Chronology getChronology() {
            return this.a.getChronology();
        }

        public LocalTime getLocalTime() {
            return this.a;
        }

        public LocalTime addCopy(int i) {
            return this.a.a(this.b.add(this.a.getLocalMillis(), i));
        }

        public LocalTime addCopy(long j) {
            return this.a.a(this.b.add(this.a.getLocalMillis(), j));
        }

        public LocalTime addNoWrapToCopy(int i) {
            long add = this.b.add(this.a.getLocalMillis(), i);
            if (((long) this.a.getChronology().millisOfDay().get(add)) == add) {
                return this.a.a(add);
            }
            throw new IllegalArgumentException("The addition exceeded the boundaries of LocalTime");
        }

        public LocalTime addWrapFieldToCopy(int i) {
            return this.a.a(this.b.addWrapField(this.a.getLocalMillis(), i));
        }

        public LocalTime setCopy(int i) {
            return this.a.a(this.b.set(this.a.getLocalMillis(), i));
        }

        public LocalTime setCopy(String str, Locale locale) {
            return this.a.a(this.b.set(this.a.getLocalMillis(), str, locale));
        }

        public LocalTime setCopy(String str) {
            return setCopy(str, null);
        }

        public LocalTime withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public LocalTime withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public LocalTime roundFloorCopy() {
            return this.a.a(this.b.roundFloor(this.a.getLocalMillis()));
        }

        public LocalTime roundCeilingCopy() {
            return this.a.a(this.b.roundCeiling(this.a.getLocalMillis()));
        }

        public LocalTime roundHalfFloorCopy() {
            return this.a.a(this.b.roundHalfFloor(this.a.getLocalMillis()));
        }

        public LocalTime roundHalfCeilingCopy() {
            return this.a.a(this.b.roundHalfCeiling(this.a.getLocalMillis()));
        }

        public LocalTime roundHalfEvenCopy() {
            return this.a.a(this.b.roundHalfEven(this.a.getLocalMillis()));
        }
    }

    public int size() {
        return 4;
    }

    static {
        a.add(DurationFieldType.millis());
        a.add(DurationFieldType.seconds());
        a.add(DurationFieldType.minutes());
        a.add(DurationFieldType.hours());
    }

    public static LocalTime now() {
        return new LocalTime();
    }

    public static LocalTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new LocalTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static LocalTime now(Chronology chronology) {
        if (chronology != null) {
            return new LocalTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @FromString
    public static LocalTime parse(String str) {
        return parse(str, ISODateTimeFormat.localTimeParser());
    }

    public static LocalTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseLocalTime(str);
    }

    public static LocalTime fromMillisOfDay(long j) {
        return fromMillisOfDay(j, null);
    }

    public static LocalTime fromMillisOfDay(long j, Chronology chronology) {
        return new LocalTime(j, DateTimeUtils.getChronology(chronology).withUTC());
    }

    public static LocalTime fromCalendarFields(Calendar calendar) {
        if (calendar != null) {
            return new LocalTime(calendar.get(11), calendar.get(12), calendar.get(13), calendar.get(14));
        }
        throw new IllegalArgumentException("The calendar must not be null");
    }

    public static LocalTime fromDateFields(Date date) {
        if (date != null) {
            return new LocalTime(date.getHours(), date.getMinutes(), date.getSeconds(), (((int) (date.getTime() % 1000)) + 1000) % 1000);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public LocalTime() {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance());
    }

    public LocalTime(DateTimeZone dateTimeZone) {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public LocalTime(Chronology chronology) {
        this(DateTimeUtils.currentTimeMillis(), chronology);
    }

    public LocalTime(long j) {
        this(j, (Chronology) ISOChronology.getInstance());
    }

    public LocalTime(long j, DateTimeZone dateTimeZone) {
        this(j, (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public LocalTime(long j, Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        long millisKeepLocal = chronology2.getZone().getMillisKeepLocal(DateTimeZone.UTC, j);
        Chronology withUTC = chronology2.withUTC();
        this.b = (long) withUTC.millisOfDay().get(millisKeepLocal);
        this.c = withUTC;
    }

    public LocalTime(Object obj) {
        this(obj, (Chronology) null);
    }

    public LocalTime(Object obj, DateTimeZone dateTimeZone) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology = DateTimeUtils.getChronology(partialConverter.getChronology(obj, dateTimeZone));
        this.c = chronology.withUTC();
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology, ISODateTimeFormat.localTimeParser());
        this.b = this.c.getDateTimeMillis(0, partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalTime(Object obj, Chronology chronology) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology2 = DateTimeUtils.getChronology(partialConverter.getChronology(obj, chronology));
        this.c = chronology2.withUTC();
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology2, ISODateTimeFormat.localTimeParser());
        this.b = this.c.getDateTimeMillis(0, partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalTime(int i, int i2) {
        this(i, i2, 0, 0, ISOChronology.getInstanceUTC());
    }

    public LocalTime(int i, int i2, int i3) {
        this(i, i2, i3, 0, ISOChronology.getInstanceUTC());
    }

    public LocalTime(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, ISOChronology.getInstanceUTC());
    }

    public LocalTime(int i, int i2, int i3, int i4, Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        long dateTimeMillis = withUTC.getDateTimeMillis(0, i, i2, i3, i4);
        this.c = withUTC;
        this.b = dateTimeMillis;
    }

    private Object readResolve() {
        if (this.c == null) {
            return new LocalTime(this.b, (Chronology) ISOChronology.getInstanceUTC());
        }
        return !DateTimeZone.UTC.equals(this.c.getZone()) ? new LocalTime(this.b, this.c.withUTC()) : this;
    }

    /* access modifiers changed from: protected */
    public DateTimeField getField(int i, Chronology chronology) {
        switch (i) {
            case 0:
                return chronology.hourOfDay();
            case 1:
                return chronology.minuteOfHour();
            case 2:
                return chronology.secondOfMinute();
            case 3:
                return chronology.millisOfSecond();
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid index: ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public int getValue(int i) {
        switch (i) {
            case 0:
                return getChronology().hourOfDay().get(getLocalMillis());
            case 1:
                return getChronology().minuteOfHour().get(getLocalMillis());
            case 2:
                return getChronology().secondOfMinute().get(getLocalMillis());
            case 3:
                return getChronology().millisOfSecond().get(getLocalMillis());
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid index: ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public int get(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        } else if (isSupported(dateTimeFieldType)) {
            return dateTimeFieldType.getField(getChronology()).get(getLocalMillis());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Field '");
            sb.append(dateTimeFieldType);
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean isSupported(DateTimeFieldType dateTimeFieldType) {
        boolean z = false;
        if (dateTimeFieldType == null || !isSupported(dateTimeFieldType.getDurationType())) {
            return false;
        }
        DurationFieldType rangeDurationType = dateTimeFieldType.getRangeDurationType();
        if (isSupported(rangeDurationType) || rangeDurationType == DurationFieldType.days()) {
            z = true;
        }
        return z;
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            return false;
        }
        DurationField field = durationFieldType.getField(getChronology());
        if (a.contains(durationFieldType) || field.getUnitMillis() < getChronology().days().getUnitMillis()) {
            return field.isSupported();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public long getLocalMillis() {
        return this.b;
    }

    public Chronology getChronology() {
        return this.c;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalTime) {
            LocalTime localTime = (LocalTime) obj;
            if (this.c.equals(localTime.c)) {
                if (this.b != localTime.b) {
                    z = false;
                }
                return z;
            }
        }
        return super.equals(obj);
    }

    public int compareTo(ReadablePartial readablePartial) {
        int i = 0;
        if (this == readablePartial) {
            return 0;
        }
        if (readablePartial instanceof LocalTime) {
            LocalTime localTime = (LocalTime) readablePartial;
            if (this.c.equals(localTime.c)) {
                if (this.b < localTime.b) {
                    i = -1;
                } else if (this.b != localTime.b) {
                    i = 1;
                }
                return i;
            }
        }
        return super.compareTo(readablePartial);
    }

    /* access modifiers changed from: 0000 */
    public LocalTime a(long j) {
        return j == getLocalMillis() ? this : new LocalTime(j, getChronology());
    }

    public LocalTime withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : a(getChronology().set(readablePartial, getLocalMillis()));
    }

    public LocalTime withField(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        } else if (isSupported(dateTimeFieldType)) {
            return a(dateTimeFieldType.getField(getChronology()).set(getLocalMillis(), i));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Field '");
            sb.append(dateTimeFieldType);
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public LocalTime withFieldAdded(DurationFieldType durationFieldType, int i) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        } else if (!isSupported(durationFieldType)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Field '");
            sb.append(durationFieldType);
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        } else if (i == 0) {
            return this;
        } else {
            return a(durationFieldType.getField(getChronology()).add(getLocalMillis(), i));
        }
    }

    public LocalTime withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        return (readablePeriod == null || i == 0) ? this : a(getChronology().add(readablePeriod, getLocalMillis(), i));
    }

    public LocalTime plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public LocalTime plusHours(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().hours().add(getLocalMillis(), i));
    }

    public LocalTime plusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().minutes().add(getLocalMillis(), i));
    }

    public LocalTime plusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().seconds().add(getLocalMillis(), i));
    }

    public LocalTime plusMillis(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().millis().add(getLocalMillis(), i));
    }

    public LocalTime minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public LocalTime minusHours(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().hours().subtract(getLocalMillis(), i));
    }

    public LocalTime minusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().minutes().subtract(getLocalMillis(), i));
    }

    public LocalTime minusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().seconds().subtract(getLocalMillis(), i));
    }

    public LocalTime minusMillis(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().millis().subtract(getLocalMillis(), i));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        } else if (isSupported(dateTimeFieldType)) {
            return new Property(this, dateTimeFieldType.getField(getChronology()));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Field '");
            sb.append(dateTimeFieldType);
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public int getHourOfDay() {
        return getChronology().hourOfDay().get(getLocalMillis());
    }

    public int getMinuteOfHour() {
        return getChronology().minuteOfHour().get(getLocalMillis());
    }

    public int getSecondOfMinute() {
        return getChronology().secondOfMinute().get(getLocalMillis());
    }

    public int getMillisOfSecond() {
        return getChronology().millisOfSecond().get(getLocalMillis());
    }

    public int getMillisOfDay() {
        return getChronology().millisOfDay().get(getLocalMillis());
    }

    public LocalTime withHourOfDay(int i) {
        return a(getChronology().hourOfDay().set(getLocalMillis(), i));
    }

    public LocalTime withMinuteOfHour(int i) {
        return a(getChronology().minuteOfHour().set(getLocalMillis(), i));
    }

    public LocalTime withSecondOfMinute(int i) {
        return a(getChronology().secondOfMinute().set(getLocalMillis(), i));
    }

    public LocalTime withMillisOfSecond(int i) {
        return a(getChronology().millisOfSecond().set(getLocalMillis(), i));
    }

    public LocalTime withMillisOfDay(int i) {
        return a(getChronology().millisOfDay().set(getLocalMillis(), i));
    }

    public Property hourOfDay() {
        return new Property(this, getChronology().hourOfDay());
    }

    public Property minuteOfHour() {
        return new Property(this, getChronology().minuteOfHour());
    }

    public Property secondOfMinute() {
        return new Property(this, getChronology().secondOfMinute());
    }

    public Property millisOfSecond() {
        return new Property(this, getChronology().millisOfSecond());
    }

    public Property millisOfDay() {
        return new Property(this, getChronology().millisOfDay());
    }

    public DateTime toDateTimeToday() {
        return toDateTimeToday(null);
    }

    public DateTime toDateTimeToday(DateTimeZone dateTimeZone) {
        Chronology withZone = getChronology().withZone(dateTimeZone);
        return new DateTime(withZone.set(this, DateTimeUtils.currentTimeMillis()), withZone);
    }

    @ToString
    public String toString() {
        return ISODateTimeFormat.time().print((ReadablePartial) this);
    }

    public String toString(String str) {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).print((ReadablePartial) this);
    }

    public String toString(String str, Locale locale) {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print((ReadablePartial) this);
    }
}
