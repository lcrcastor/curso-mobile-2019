package org.joda.time;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
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

public final class LocalDateTime extends BaseLocal implements Serializable, ReadablePartial {
    private static final long serialVersionUID = -268716875315837168L;
    private final long a;
    private final Chronology b;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -358138762846288L;
        private transient LocalDateTime a;
        private transient DateTimeField b;

        Property(LocalDateTime localDateTime, DateTimeField dateTimeField) {
            this.a = localDateTime;
            this.b = dateTimeField;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(this.b.getType());
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = (LocalDateTime) objectInputStream.readObject();
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

        public LocalDateTime getLocalDateTime() {
            return this.a;
        }

        public LocalDateTime addToCopy(int i) {
            return this.a.a(this.b.add(this.a.getLocalMillis(), i));
        }

        public LocalDateTime addToCopy(long j) {
            return this.a.a(this.b.add(this.a.getLocalMillis(), j));
        }

        public LocalDateTime addWrapFieldToCopy(int i) {
            return this.a.a(this.b.addWrapField(this.a.getLocalMillis(), i));
        }

        public LocalDateTime setCopy(int i) {
            return this.a.a(this.b.set(this.a.getLocalMillis(), i));
        }

        public LocalDateTime setCopy(String str, Locale locale) {
            return this.a.a(this.b.set(this.a.getLocalMillis(), str, locale));
        }

        public LocalDateTime setCopy(String str) {
            return setCopy(str, null);
        }

        public LocalDateTime withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public LocalDateTime withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public LocalDateTime roundFloorCopy() {
            return this.a.a(this.b.roundFloor(this.a.getLocalMillis()));
        }

        public LocalDateTime roundCeilingCopy() {
            return this.a.a(this.b.roundCeiling(this.a.getLocalMillis()));
        }

        public LocalDateTime roundHalfFloorCopy() {
            return this.a.a(this.b.roundHalfFloor(this.a.getLocalMillis()));
        }

        public LocalDateTime roundHalfCeilingCopy() {
            return this.a.a(this.b.roundHalfCeiling(this.a.getLocalMillis()));
        }

        public LocalDateTime roundHalfEvenCopy() {
            return this.a.a(this.b.roundHalfEven(this.a.getLocalMillis()));
        }
    }

    public int size() {
        return 4;
    }

    public static LocalDateTime now() {
        return new LocalDateTime();
    }

    public static LocalDateTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new LocalDateTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static LocalDateTime now(Chronology chronology) {
        if (chronology != null) {
            return new LocalDateTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @FromString
    public static LocalDateTime parse(String str) {
        return parse(str, ISODateTimeFormat.localDateOptionalTimeParser());
    }

    public static LocalDateTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseLocalDateTime(str);
    }

    public static LocalDateTime fromCalendarFields(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("The calendar must not be null");
        }
        int i = calendar.get(0);
        int i2 = calendar.get(1);
        if (i != 1) {
            i2 = 1 - i2;
        }
        LocalDateTime localDateTime = new LocalDateTime(i2, calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13), calendar.get(14));
        return localDateTime;
    }

    public static LocalDateTime fromDateFields(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (date.getTime() < 0) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return fromCalendarFields(gregorianCalendar);
        } else {
            LocalDateTime localDateTime = new LocalDateTime(date.getYear() + 1900, date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), (((int) (date.getTime() % 1000)) + 1000) % 1000);
            return localDateTime;
        }
    }

    public LocalDateTime() {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance());
    }

    public LocalDateTime(DateTimeZone dateTimeZone) {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public LocalDateTime(Chronology chronology) {
        this(DateTimeUtils.currentTimeMillis(), chronology);
    }

    public LocalDateTime(long j) {
        this(j, (Chronology) ISOChronology.getInstance());
    }

    public LocalDateTime(long j, DateTimeZone dateTimeZone) {
        this(j, (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public LocalDateTime(long j, Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.a = chronology2.getZone().getMillisKeepLocal(DateTimeZone.UTC, j);
        this.b = chronology2.withUTC();
    }

    public LocalDateTime(Object obj) {
        this(obj, (Chronology) null);
    }

    public LocalDateTime(Object obj, DateTimeZone dateTimeZone) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology = DateTimeUtils.getChronology(partialConverter.getChronology(obj, dateTimeZone));
        this.b = chronology.withUTC();
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology, ISODateTimeFormat.localDateOptionalTimeParser());
        this.a = this.b.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalDateTime(Object obj, Chronology chronology) {
        PartialConverter partialConverter = ConverterManager.getInstance().getPartialConverter(obj);
        Chronology chronology2 = DateTimeUtils.getChronology(partialConverter.getChronology(obj, chronology));
        this.b = chronology2.withUTC();
        int[] partialValues = partialConverter.getPartialValues(this, obj, chronology2, ISODateTimeFormat.localDateOptionalTimeParser());
        this.a = this.b.getDateTimeMillis(partialValues[0], partialValues[1], partialValues[2], partialValues[3]);
    }

    public LocalDateTime(int i, int i2, int i3, int i4, int i5) {
        this(i, i2, i3, i4, i5, 0, 0, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, i4, i5, i6, 0, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(i, i2, i3, i4, i5, i6, i7, ISOChronology.getInstanceUTC());
    }

    public LocalDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        long dateTimeMillis = withUTC.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
        this.b = withUTC;
        this.a = dateTimeMillis;
    }

    private Object readResolve() {
        if (this.b == null) {
            return new LocalDateTime(this.a, (Chronology) ISOChronology.getInstanceUTC());
        }
        return !DateTimeZone.UTC.equals(this.b.getZone()) ? new LocalDateTime(this.a, this.b.withUTC()) : this;
    }

    /* access modifiers changed from: protected */
    public DateTimeField getField(int i, Chronology chronology) {
        switch (i) {
            case 0:
                return chronology.year();
            case 1:
                return chronology.monthOfYear();
            case 2:
                return chronology.dayOfMonth();
            case 3:
                return chronology.millisOfDay();
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
                return getChronology().year().get(getLocalMillis());
            case 1:
                return getChronology().monthOfYear().get(getLocalMillis());
            case 2:
                return getChronology().dayOfMonth().get(getLocalMillis());
            case 3:
                return getChronology().millisOfDay().get(getLocalMillis());
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid index: ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public int get(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return dateTimeFieldType.getField(getChronology()).get(getLocalMillis());
        }
        throw new IllegalArgumentException("The DateTimeFieldType must not be null");
    }

    public boolean isSupported(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            return false;
        }
        return dateTimeFieldType.getField(getChronology()).isSupported();
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            return false;
        }
        return durationFieldType.getField(getChronology()).isSupported();
    }

    /* access modifiers changed from: protected */
    public long getLocalMillis() {
        return this.a;
    }

    public Chronology getChronology() {
        return this.b;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) obj;
            if (this.b.equals(localDateTime.b)) {
                if (this.a != localDateTime.a) {
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
        if (readablePartial instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) readablePartial;
            if (this.b.equals(localDateTime.b)) {
                if (this.a < localDateTime.a) {
                    i = -1;
                } else if (this.a != localDateTime.a) {
                    i = 1;
                }
                return i;
            }
        }
        return super.compareTo(readablePartial);
    }

    public DateTime toDateTime() {
        return toDateTime(null);
    }

    public DateTime toDateTime(DateTimeZone dateTimeZone) {
        DateTime dateTime = new DateTime(getYear(), getMonthOfYear(), getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute(), getMillisOfSecond(), this.b.withZone(DateTimeUtils.getZone(dateTimeZone)));
        return dateTime;
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getLocalMillis(), getChronology());
    }

    public LocalTime toLocalTime() {
        return new LocalTime(getLocalMillis(), getChronology());
    }

    public Date toDate() {
        Date date = new Date(getYear() - 1900, getMonthOfYear() - 1, getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute());
        date.setTime(date.getTime() + ((long) getMillisOfSecond()));
        return a(date, TimeZone.getDefault());
    }

    public Date toDate(TimeZone timeZone) {
        Calendar instance = Calendar.getInstance(timeZone);
        instance.clear();
        instance.set(getYear(), getMonthOfYear() - 1, getDayOfMonth(), getHourOfDay(), getMinuteOfHour(), getSecondOfMinute());
        Date time = instance.getTime();
        time.setTime(time.getTime() + ((long) getMillisOfSecond()));
        return a(time, timeZone);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0068, code lost:
        if (fromCalendarFields(r8).equals(r7) != false) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Date a(java.util.Date r8, java.util.TimeZone r9) {
        /*
            r7 = this;
            java.util.Calendar r0 = java.util.Calendar.getInstance(r9)
            r0.setTime(r8)
            org.joda.time.LocalDateTime r8 = fromCalendarFields(r0)
            boolean r1 = r8.isBefore(r7)
            if (r1 == 0) goto L_0x0048
        L_0x0011:
            boolean r9 = r8.isBefore(r7)
            if (r9 == 0) goto L_0x0028
            long r8 = r0.getTimeInMillis()
            r1 = 60000(0xea60, double:2.9644E-319)
            long r3 = r8 + r1
            r0.setTimeInMillis(r3)
            org.joda.time.LocalDateTime r8 = fromCalendarFields(r0)
            goto L_0x0011
        L_0x0028:
            boolean r8 = r8.isBefore(r7)
            r1 = 1000(0x3e8, double:4.94E-321)
            if (r8 != 0) goto L_0x003e
            long r8 = r0.getTimeInMillis()
            long r3 = r8 - r1
            r0.setTimeInMillis(r3)
            org.joda.time.LocalDateTime r8 = fromCalendarFields(r0)
            goto L_0x0028
        L_0x003e:
            long r8 = r0.getTimeInMillis()
            long r3 = r8 + r1
            r0.setTimeInMillis(r3)
            goto L_0x006b
        L_0x0048:
            boolean r8 = r8.equals(r7)
            if (r8 == 0) goto L_0x006b
            java.util.Calendar r8 = java.util.Calendar.getInstance(r9)
            long r1 = r0.getTimeInMillis()
            int r9 = r9.getDSTSavings()
            long r3 = (long) r9
            long r5 = r1 - r3
            r8.setTimeInMillis(r5)
            org.joda.time.LocalDateTime r9 = fromCalendarFields(r8)
            boolean r9 = r9.equals(r7)
            if (r9 == 0) goto L_0x006b
            goto L_0x006c
        L_0x006b:
            r8 = r0
        L_0x006c:
            java.util.Date r8 = r8.getTime()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.LocalDateTime.a(java.util.Date, java.util.TimeZone):java.util.Date");
    }

    /* access modifiers changed from: 0000 */
    public LocalDateTime a(long j) {
        return j == getLocalMillis() ? this : new LocalDateTime(j, getChronology());
    }

    public LocalDateTime withDate(int i, int i2, int i3) {
        Chronology chronology = getChronology();
        return a(chronology.dayOfMonth().set(chronology.monthOfYear().set(chronology.year().set(getLocalMillis(), i), i2), i3));
    }

    public LocalDateTime withTime(int i, int i2, int i3, int i4) {
        Chronology chronology = getChronology();
        return a(chronology.millisOfSecond().set(chronology.secondOfMinute().set(chronology.minuteOfHour().set(chronology.hourOfDay().set(getLocalMillis(), i), i2), i3), i4));
    }

    public LocalDateTime withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : a(getChronology().set(readablePartial, getLocalMillis()));
    }

    public LocalDateTime withField(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType != null) {
            return a(dateTimeFieldType.getField(getChronology()).set(getLocalMillis(), i));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public LocalDateTime withFieldAdded(DurationFieldType durationFieldType, int i) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        } else if (i == 0) {
            return this;
        } else {
            return a(durationFieldType.getField(getChronology()).add(getLocalMillis(), i));
        }
    }

    public LocalDateTime withDurationAdded(ReadableDuration readableDuration, int i) {
        return (readableDuration == null || i == 0) ? this : a(getChronology().add(getLocalMillis(), readableDuration.getMillis(), i));
    }

    public LocalDateTime withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        return (readablePeriod == null || i == 0) ? this : a(getChronology().add(readablePeriod, getLocalMillis(), i));
    }

    public LocalDateTime plus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, 1);
    }

    public LocalDateTime plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public LocalDateTime plusYears(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().years().add(getLocalMillis(), i));
    }

    public LocalDateTime plusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().months().add(getLocalMillis(), i));
    }

    public LocalDateTime plusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().weeks().add(getLocalMillis(), i));
    }

    public LocalDateTime plusDays(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().days().add(getLocalMillis(), i));
    }

    public LocalDateTime plusHours(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().hours().add(getLocalMillis(), i));
    }

    public LocalDateTime plusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().minutes().add(getLocalMillis(), i));
    }

    public LocalDateTime plusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().seconds().add(getLocalMillis(), i));
    }

    public LocalDateTime plusMillis(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().millis().add(getLocalMillis(), i));
    }

    public LocalDateTime minus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, -1);
    }

    public LocalDateTime minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public LocalDateTime minusYears(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().years().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().months().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().weeks().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusDays(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().days().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusHours(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().hours().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().minutes().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return a(getChronology().seconds().subtract(getLocalMillis(), i));
    }

    public LocalDateTime minusMillis(int i) {
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

    public int getEra() {
        return getChronology().era().get(getLocalMillis());
    }

    public int getCenturyOfEra() {
        return getChronology().centuryOfEra().get(getLocalMillis());
    }

    public int getYearOfEra() {
        return getChronology().yearOfEra().get(getLocalMillis());
    }

    public int getYearOfCentury() {
        return getChronology().yearOfCentury().get(getLocalMillis());
    }

    public int getYear() {
        return getChronology().year().get(getLocalMillis());
    }

    public int getWeekyear() {
        return getChronology().weekyear().get(getLocalMillis());
    }

    public int getMonthOfYear() {
        return getChronology().monthOfYear().get(getLocalMillis());
    }

    public int getWeekOfWeekyear() {
        return getChronology().weekOfWeekyear().get(getLocalMillis());
    }

    public int getDayOfYear() {
        return getChronology().dayOfYear().get(getLocalMillis());
    }

    public int getDayOfMonth() {
        return getChronology().dayOfMonth().get(getLocalMillis());
    }

    public int getDayOfWeek() {
        return getChronology().dayOfWeek().get(getLocalMillis());
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

    public LocalDateTime withEra(int i) {
        return a(getChronology().era().set(getLocalMillis(), i));
    }

    public LocalDateTime withCenturyOfEra(int i) {
        return a(getChronology().centuryOfEra().set(getLocalMillis(), i));
    }

    public LocalDateTime withYearOfEra(int i) {
        return a(getChronology().yearOfEra().set(getLocalMillis(), i));
    }

    public LocalDateTime withYearOfCentury(int i) {
        return a(getChronology().yearOfCentury().set(getLocalMillis(), i));
    }

    public LocalDateTime withYear(int i) {
        return a(getChronology().year().set(getLocalMillis(), i));
    }

    public LocalDateTime withWeekyear(int i) {
        return a(getChronology().weekyear().set(getLocalMillis(), i));
    }

    public LocalDateTime withMonthOfYear(int i) {
        return a(getChronology().monthOfYear().set(getLocalMillis(), i));
    }

    public LocalDateTime withWeekOfWeekyear(int i) {
        return a(getChronology().weekOfWeekyear().set(getLocalMillis(), i));
    }

    public LocalDateTime withDayOfYear(int i) {
        return a(getChronology().dayOfYear().set(getLocalMillis(), i));
    }

    public LocalDateTime withDayOfMonth(int i) {
        return a(getChronology().dayOfMonth().set(getLocalMillis(), i));
    }

    public LocalDateTime withDayOfWeek(int i) {
        return a(getChronology().dayOfWeek().set(getLocalMillis(), i));
    }

    public LocalDateTime withHourOfDay(int i) {
        return a(getChronology().hourOfDay().set(getLocalMillis(), i));
    }

    public LocalDateTime withMinuteOfHour(int i) {
        return a(getChronology().minuteOfHour().set(getLocalMillis(), i));
    }

    public LocalDateTime withSecondOfMinute(int i) {
        return a(getChronology().secondOfMinute().set(getLocalMillis(), i));
    }

    public LocalDateTime withMillisOfSecond(int i) {
        return a(getChronology().millisOfSecond().set(getLocalMillis(), i));
    }

    public LocalDateTime withMillisOfDay(int i) {
        return a(getChronology().millisOfDay().set(getLocalMillis(), i));
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    public Property centuryOfEra() {
        return new Property(this, getChronology().centuryOfEra());
    }

    public Property yearOfCentury() {
        return new Property(this, getChronology().yearOfCentury());
    }

    public Property yearOfEra() {
        return new Property(this, getChronology().yearOfEra());
    }

    public Property year() {
        return new Property(this, getChronology().year());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property dayOfYear() {
        return new Property(this, getChronology().dayOfYear());
    }

    public Property dayOfMonth() {
        return new Property(this, getChronology().dayOfMonth());
    }

    public Property dayOfWeek() {
        return new Property(this, getChronology().dayOfWeek());
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

    @ToString
    public String toString() {
        return ISODateTimeFormat.dateTime().print((ReadablePartial) this);
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
