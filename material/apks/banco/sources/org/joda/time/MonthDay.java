package org.joda.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BasePartial;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractPartialFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;

public final class MonthDay extends BasePartial implements Serializable, ReadablePartial {
    public static final int DAY_OF_MONTH = 1;
    public static final int MONTH_OF_YEAR = 0;
    private static final DateTimeFieldType[] a = {DateTimeFieldType.monthOfYear(), DateTimeFieldType.dayOfMonth()};
    private static final DateTimeFormatter b = new DateTimeFormatterBuilder().appendOptional(ISODateTimeFormat.localDateParser().getParser()).appendOptional(DateTimeFormat.forPattern("--MM-dd").getParser()).toFormatter();
    private static final long serialVersionUID = 2954560699050434609L;

    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 5727734012190224363L;
        private final MonthDay a;
        private final int b;

        Property(MonthDay monthDay, int i) {
            this.a = monthDay;
            this.b = i;
        }

        public DateTimeField getField() {
            return this.a.getField(this.b);
        }

        /* access modifiers changed from: protected */
        public ReadablePartial getReadablePartial() {
            return this.a;
        }

        public MonthDay getMonthDay() {
            return this.a;
        }

        public int get() {
            return this.a.getValue(this.b);
        }

        public MonthDay addToCopy(int i) {
            return new MonthDay(this.a, getField().add(this.a, this.b, this.a.getValues(), i));
        }

        public MonthDay addWrapFieldToCopy(int i) {
            return new MonthDay(this.a, getField().addWrapField(this.a, this.b, this.a.getValues(), i));
        }

        public MonthDay setCopy(int i) {
            return new MonthDay(this.a, getField().set(this.a, this.b, this.a.getValues(), i));
        }

        public MonthDay setCopy(String str, Locale locale) {
            return new MonthDay(this.a, getField().set(this.a, this.b, this.a.getValues(), str, locale));
        }

        public MonthDay setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public int size() {
        return 2;
    }

    public static MonthDay now() {
        return new MonthDay();
    }

    public static MonthDay now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new MonthDay(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static MonthDay now(Chronology chronology) {
        if (chronology != null) {
            return new MonthDay(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @FromString
    public static MonthDay parse(String str) {
        return parse(str, b);
    }

    public static MonthDay parse(String str, DateTimeFormatter dateTimeFormatter) {
        LocalDate parseLocalDate = dateTimeFormatter.parseLocalDate(str);
        return new MonthDay(parseLocalDate.getMonthOfYear(), parseLocalDate.getDayOfMonth());
    }

    public static MonthDay fromCalendarFields(Calendar calendar) {
        if (calendar != null) {
            return new MonthDay(calendar.get(2) + 1, calendar.get(5));
        }
        throw new IllegalArgumentException("The calendar must not be null");
    }

    public static MonthDay fromDateFields(Date date) {
        if (date != null) {
            return new MonthDay(date.getMonth() + 1, date.getDate());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public MonthDay() {
    }

    public MonthDay(DateTimeZone dateTimeZone) {
        super((Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public MonthDay(Chronology chronology) {
        super(chronology);
    }

    public MonthDay(long j) {
        super(j);
    }

    public MonthDay(long j, Chronology chronology) {
        super(j, chronology);
    }

    public MonthDay(Object obj) {
        super(obj, null, ISODateTimeFormat.localDateParser());
    }

    public MonthDay(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology), ISODateTimeFormat.localDateParser());
    }

    public MonthDay(int i, int i2) {
        this(i, i2, null);
    }

    public MonthDay(int i, int i2, Chronology chronology) {
        super(new int[]{i, i2}, chronology);
    }

    MonthDay(MonthDay monthDay, int[] iArr) {
        super((BasePartial) monthDay, iArr);
    }

    MonthDay(MonthDay monthDay, Chronology chronology) {
        super((BasePartial) monthDay, chronology);
    }

    private Object readResolve() {
        return !DateTimeZone.UTC.equals(getChronology().getZone()) ? new MonthDay(this, getChronology().withUTC()) : this;
    }

    /* access modifiers changed from: protected */
    public DateTimeField getField(int i, Chronology chronology) {
        switch (i) {
            case 0:
                return chronology.monthOfYear();
            case 1:
                return chronology.dayOfMonth();
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid index: ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public DateTimeFieldType getFieldType(int i) {
        return a[i];
    }

    public DateTimeFieldType[] getFieldTypes() {
        return (DateTimeFieldType[]) a.clone();
    }

    public MonthDay withChronologyRetainFields(Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (withUTC == getChronology()) {
            return this;
        }
        MonthDay monthDay = new MonthDay(this, withUTC);
        withUTC.validate(monthDay, getValues());
        return monthDay;
    }

    public MonthDay withField(DateTimeFieldType dateTimeFieldType, int i) {
        int indexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i == getValue(indexOfSupported)) {
            return this;
        }
        return new MonthDay(this, getField(indexOfSupported).set(this, indexOfSupported, getValues(), i));
    }

    public MonthDay withFieldAdded(DurationFieldType durationFieldType, int i) {
        int indexOfSupported = indexOfSupported(durationFieldType);
        if (i == 0) {
            return this;
        }
        return new MonthDay(this, getField(indexOfSupported).add(this, indexOfSupported, getValues(), i));
    }

    public MonthDay withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        if (readablePeriod == null || i == 0) {
            return this;
        }
        int[] values = getValues();
        for (int i2 = 0; i2 < readablePeriod.size(); i2++) {
            int indexOf = indexOf(readablePeriod.getFieldType(i2));
            if (indexOf >= 0) {
                values = getField(indexOf).add(this, indexOf, values, FieldUtils.safeMultiply(readablePeriod.getValue(i2), i));
            }
        }
        return new MonthDay(this, values);
    }

    public MonthDay plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public MonthDay plusMonths(int i) {
        return withFieldAdded(DurationFieldType.months(), i);
    }

    public MonthDay plusDays(int i) {
        return withFieldAdded(DurationFieldType.days(), i);
    }

    public MonthDay minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public MonthDay minusMonths(int i) {
        return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(i));
    }

    public MonthDay minusDays(int i) {
        return withFieldAdded(DurationFieldType.days(), FieldUtils.safeNegate(i));
    }

    public LocalDate toLocalDate(int i) {
        return new LocalDate(i, getMonthOfYear(), getDayOfMonth(), getChronology());
    }

    public int getMonthOfYear() {
        return getValue(0);
    }

    public int getDayOfMonth() {
        return getValue(1);
    }

    public MonthDay withMonthOfYear(int i) {
        return new MonthDay(this, getChronology().monthOfYear().set(this, 0, getValues(), i));
    }

    public MonthDay withDayOfMonth(int i) {
        return new MonthDay(this, getChronology().dayOfMonth().set(this, 1, getValues(), i));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    public Property monthOfYear() {
        return new Property(this, 0);
    }

    public Property dayOfMonth() {
        return new Property(this, 1);
    }

    @ToString
    public String toString() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(DateTimeFieldType.monthOfYear());
        arrayList.add(DateTimeFieldType.dayOfMonth());
        return ISODateTimeFormat.forFields(arrayList, true, true).print((ReadablePartial) this);
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
