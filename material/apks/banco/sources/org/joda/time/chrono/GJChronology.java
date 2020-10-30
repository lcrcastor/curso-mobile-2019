package org.joda.time.chrono;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.DecoratedDurationField;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class GJChronology extends AssembledChronology {
    static final Instant a = new Instant(-12219292800000L);
    private static final ConcurrentHashMap<GJCacheKey, GJChronology> b = new ConcurrentHashMap<>();
    private static final long serialVersionUID = -2545574827706931671L;
    private JulianChronology c;
    /* access modifiers changed from: private */
    public GregorianChronology d;
    private Instant e;
    private long f;
    /* access modifiers changed from: private */
    public long g;

    class CutoverField extends BaseDateTimeField {
        final DateTimeField a;
        final DateTimeField b;
        final long c;
        final boolean d;
        protected DurationField e;
        protected DurationField f;

        public boolean isLenient() {
            return false;
        }

        CutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, long j) {
            this(gJChronology, dateTimeField, dateTimeField2, j, false);
        }

        CutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, long j, boolean z) {
            this(dateTimeField, dateTimeField2, null, j, z);
        }

        CutoverField(DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, long j, boolean z) {
            super(dateTimeField2.getType());
            this.a = dateTimeField;
            this.b = dateTimeField2;
            this.c = j;
            this.d = z;
            this.e = dateTimeField2.getDurationField();
            if (durationField == null) {
                durationField = dateTimeField2.getRangeDurationField();
                if (durationField == null) {
                    durationField = dateTimeField.getRangeDurationField();
                }
            }
            this.f = durationField;
        }

        public int get(long j) {
            if (j >= this.c) {
                return this.b.get(j);
            }
            return this.a.get(j);
        }

        public String getAsText(long j, Locale locale) {
            if (j >= this.c) {
                return this.b.getAsText(j, locale);
            }
            return this.a.getAsText(j, locale);
        }

        public String getAsText(int i, Locale locale) {
            return this.b.getAsText(i, locale);
        }

        public String getAsShortText(long j, Locale locale) {
            if (j >= this.c) {
                return this.b.getAsShortText(j, locale);
            }
            return this.a.getAsShortText(j, locale);
        }

        public String getAsShortText(int i, Locale locale) {
            return this.b.getAsShortText(i, locale);
        }

        public long add(long j, int i) {
            return this.b.add(j, i);
        }

        public long add(long j, long j2) {
            return this.b.add(j, j2);
        }

        public int[] add(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
            if (i2 == 0) {
                return iArr;
            }
            if (!DateTimeUtils.isContiguous(readablePartial)) {
                return super.add(readablePartial, i, iArr, i2);
            }
            long j = 0;
            int size = readablePartial.size();
            for (int i3 = 0; i3 < size; i3++) {
                j = readablePartial.getFieldType(i3).getField(GJChronology.this).set(j, iArr[i3]);
            }
            return GJChronology.this.get(readablePartial, add(j, i2));
        }

        public int getDifference(long j, long j2) {
            return this.b.getDifference(j, j2);
        }

        public long getDifferenceAsLong(long j, long j2) {
            return this.b.getDifferenceAsLong(j, j2);
        }

        public long set(long j, int i) {
            long j2;
            if (j >= this.c) {
                j2 = this.b.set(j, i);
                if (j2 < this.c) {
                    if (j2 + GJChronology.this.g < this.c) {
                        j2 = b(j2);
                    }
                    if (get(j2) != i) {
                        throw new IllegalFieldValueException(this.b.getType(), (Number) Integer.valueOf(i), (Number) null, (Number) null);
                    }
                }
            } else {
                j2 = this.a.set(j, i);
                if (j2 >= this.c) {
                    if (j2 - GJChronology.this.g >= this.c) {
                        j2 = a(j2);
                    }
                    if (get(j2) != i) {
                        throw new IllegalFieldValueException(this.a.getType(), (Number) Integer.valueOf(i), (Number) null, (Number) null);
                    }
                }
            }
            return j2;
        }

        public long set(long j, String str, Locale locale) {
            if (j >= this.c) {
                long j2 = this.b.set(j, str, locale);
                if (j2 >= this.c || j2 + GJChronology.this.g >= this.c) {
                    return j2;
                }
                return b(j2);
            }
            long j3 = this.a.set(j, str, locale);
            return (j3 < this.c || j3 - GJChronology.this.g < this.c) ? j3 : a(j3);
        }

        public DurationField getDurationField() {
            return this.e;
        }

        public DurationField getRangeDurationField() {
            return this.f;
        }

        public boolean isLeap(long j) {
            if (j >= this.c) {
                return this.b.isLeap(j);
            }
            return this.a.isLeap(j);
        }

        public int getLeapAmount(long j) {
            if (j >= this.c) {
                return this.b.getLeapAmount(j);
            }
            return this.a.getLeapAmount(j);
        }

        public DurationField getLeapDurationField() {
            return this.b.getLeapDurationField();
        }

        public int getMinimumValue() {
            return this.a.getMinimumValue();
        }

        public int getMinimumValue(ReadablePartial readablePartial) {
            return this.a.getMinimumValue(readablePartial);
        }

        public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.a.getMinimumValue(readablePartial, iArr);
        }

        public int getMinimumValue(long j) {
            if (j < this.c) {
                return this.a.getMinimumValue(j);
            }
            int minimumValue = this.b.getMinimumValue(j);
            if (this.b.set(j, minimumValue) < this.c) {
                minimumValue = this.b.get(this.c);
            }
            return minimumValue;
        }

        public int getMaximumValue() {
            return this.b.getMaximumValue();
        }

        public int getMaximumValue(long j) {
            if (j >= this.c) {
                return this.b.getMaximumValue(j);
            }
            int maximumValue = this.a.getMaximumValue(j);
            if (this.a.set(j, maximumValue) >= this.c) {
                maximumValue = this.a.get(this.a.add(this.c, -1));
            }
            return maximumValue;
        }

        public int getMaximumValue(ReadablePartial readablePartial) {
            return getMaximumValue(GJChronology.getInstanceUTC().set(readablePartial, 0));
        }

        public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
            GJChronology instanceUTC = GJChronology.getInstanceUTC();
            int size = readablePartial.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                DateTimeField field = readablePartial.getFieldType(i).getField(instanceUTC);
                if (iArr[i] <= field.getMaximumValue(j)) {
                    j = field.set(j, iArr[i]);
                }
            }
            return getMaximumValue(j);
        }

        public long roundFloor(long j) {
            if (j < this.c) {
                return this.a.roundFloor(j);
            }
            long roundFloor = this.b.roundFloor(j);
            if (roundFloor >= this.c || roundFloor + GJChronology.this.g >= this.c) {
                return roundFloor;
            }
            return b(roundFloor);
        }

        public long roundCeiling(long j) {
            if (j >= this.c) {
                return this.b.roundCeiling(j);
            }
            long roundCeiling = this.a.roundCeiling(j);
            return (roundCeiling < this.c || roundCeiling - GJChronology.this.g < this.c) ? roundCeiling : a(roundCeiling);
        }

        public int getMaximumTextLength(Locale locale) {
            return Math.max(this.a.getMaximumTextLength(locale), this.b.getMaximumTextLength(locale));
        }

        public int getMaximumShortTextLength(Locale locale) {
            return Math.max(this.a.getMaximumShortTextLength(locale), this.b.getMaximumShortTextLength(locale));
        }

        /* access modifiers changed from: protected */
        public long a(long j) {
            if (this.d) {
                return GJChronology.this.c(j);
            }
            return GJChronology.this.a(j);
        }

        /* access modifiers changed from: protected */
        public long b(long j) {
            if (this.d) {
                return GJChronology.this.d(j);
            }
            return GJChronology.this.b(j);
        }
    }

    final class ImpreciseCutoverField extends CutoverField {
        ImpreciseCutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, long j) {
            this(dateTimeField, dateTimeField2, (DurationField) null, j, false);
        }

        ImpreciseCutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, long j) {
            this(dateTimeField, dateTimeField2, durationField, j, false);
        }

        ImpreciseCutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, DurationField durationField2, long j) {
            this(dateTimeField, dateTimeField2, durationField, j, false);
            this.f = durationField2;
        }

        ImpreciseCutoverField(DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, long j, boolean z) {
            super(GJChronology.this, dateTimeField, dateTimeField2, j, z);
            if (durationField == null) {
                durationField = new LinkedDurationField(this.e, this);
            }
            this.e = durationField;
        }

        public long add(long j, int i) {
            if (j >= this.c) {
                long add = this.b.add(j, i);
                if (add >= this.c || add + GJChronology.this.g >= this.c) {
                    return add;
                }
                if (this.d) {
                    if (GJChronology.this.d.weekyear().get(add) <= 0) {
                        add = GJChronology.this.d.weekyear().add(add, -1);
                    }
                } else if (GJChronology.this.d.year().get(add) <= 0) {
                    add = GJChronology.this.d.year().add(add, -1);
                }
                return b(add);
            }
            long add2 = this.a.add(j, i);
            return (add2 < this.c || add2 - GJChronology.this.g < this.c) ? add2 : a(add2);
        }

        public long add(long j, long j2) {
            if (j >= this.c) {
                long add = this.b.add(j, j2);
                if (add >= this.c || add + GJChronology.this.g >= this.c) {
                    return add;
                }
                if (this.d) {
                    if (GJChronology.this.d.weekyear().get(add) <= 0) {
                        add = GJChronology.this.d.weekyear().add(add, -1);
                    }
                } else if (GJChronology.this.d.year().get(add) <= 0) {
                    add = GJChronology.this.d.year().add(add, -1);
                }
                return b(add);
            }
            long add2 = this.a.add(j, j2);
            return (add2 < this.c || add2 - GJChronology.this.g < this.c) ? add2 : a(add2);
        }

        public int getDifference(long j, long j2) {
            if (j >= this.c) {
                if (j2 >= this.c) {
                    return this.b.getDifference(j, j2);
                }
                return this.a.getDifference(b(j), j2);
            } else if (j2 < this.c) {
                return this.a.getDifference(j, j2);
            } else {
                return this.b.getDifference(a(j), j2);
            }
        }

        public long getDifferenceAsLong(long j, long j2) {
            if (j >= this.c) {
                if (j2 >= this.c) {
                    return this.b.getDifferenceAsLong(j, j2);
                }
                return this.a.getDifferenceAsLong(b(j), j2);
            } else if (j2 < this.c) {
                return this.a.getDifferenceAsLong(j, j2);
            } else {
                return this.b.getDifferenceAsLong(a(j), j2);
            }
        }

        public int getMinimumValue(long j) {
            if (j >= this.c) {
                return this.b.getMinimumValue(j);
            }
            return this.a.getMinimumValue(j);
        }

        public int getMaximumValue(long j) {
            if (j >= this.c) {
                return this.b.getMaximumValue(j);
            }
            return this.a.getMaximumValue(j);
        }
    }

    static class LinkedDurationField extends DecoratedDurationField {
        private static final long serialVersionUID = 4097975388007713084L;
        private final ImpreciseCutoverField a;

        LinkedDurationField(DurationField durationField, ImpreciseCutoverField impreciseCutoverField) {
            super(durationField, durationField.getType());
            this.a = impreciseCutoverField;
        }

        public long add(long j, int i) {
            return this.a.add(j, i);
        }

        public long add(long j, long j2) {
            return this.a.add(j, j2);
        }

        public int getDifference(long j, long j2) {
            return this.a.getDifference(j, j2);
        }

        public long getDifferenceAsLong(long j, long j2) {
            return this.a.getDifferenceAsLong(j, j2);
        }
    }

    private static long a(long j, Chronology chronology, Chronology chronology2) {
        return chronology2.getDateTimeMillis(chronology.year().get(j), chronology.monthOfYear().get(j), chronology.dayOfMonth().get(j), chronology.millisOfDay().get(j));
    }

    private static long b(long j, Chronology chronology, Chronology chronology2) {
        return chronology2.millisOfDay().set(chronology2.dayOfWeek().set(chronology2.weekOfWeekyear().set(chronology2.weekyear().set(0, chronology.weekyear().get(j)), chronology.weekOfWeekyear().get(j)), chronology.dayOfWeek().get(j)), chronology.millisOfDay().get(j));
    }

    public static GJChronology getInstanceUTC() {
        return getInstance(DateTimeZone.UTC, (ReadableInstant) a, 4);
    }

    public static GJChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), (ReadableInstant) a, 4);
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, (ReadableInstant) a, 4);
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone, ReadableInstant readableInstant) {
        return getInstance(dateTimeZone, readableInstant, 4);
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone, ReadableInstant readableInstant, int i) {
        Instant instant;
        GJChronology gJChronology;
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        if (readableInstant == null) {
            instant = a;
        } else {
            instant = readableInstant.toInstant();
            if (new LocalDate(instant.getMillis(), (Chronology) GregorianChronology.getInstance(zone)).getYear() <= 0) {
                throw new IllegalArgumentException("Cutover too early. Must be on or after 0001-01-01.");
            }
        }
        GJCacheKey gJCacheKey = new GJCacheKey(zone, instant, i);
        GJChronology gJChronology2 = (GJChronology) b.get(gJCacheKey);
        if (gJChronology2 != null) {
            return gJChronology2;
        }
        if (zone == DateTimeZone.UTC) {
            gJChronology = new GJChronology(JulianChronology.getInstance(zone, i), GregorianChronology.getInstance(zone, i), instant);
        } else {
            GJChronology instance = getInstance(DateTimeZone.UTC, (ReadableInstant) instant, i);
            gJChronology = new GJChronology(ZonedChronology.getInstance(instance, zone), instance.c, instance.d, instance.e);
        }
        GJChronology gJChronology3 = (GJChronology) b.putIfAbsent(gJCacheKey, gJChronology);
        return gJChronology3 != null ? gJChronology3 : gJChronology;
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone, long j, int i) {
        ReadableInstant readableInstant;
        if (j == a.getMillis()) {
            readableInstant = null;
        } else {
            readableInstant = new Instant(j);
        }
        return getInstance(dateTimeZone, readableInstant, i);
    }

    private GJChronology(JulianChronology julianChronology, GregorianChronology gregorianChronology, Instant instant) {
        super(null, new Object[]{julianChronology, gregorianChronology, instant});
    }

    private GJChronology(Chronology chronology, JulianChronology julianChronology, GregorianChronology gregorianChronology, Instant instant) {
        super(chronology, new Object[]{julianChronology, gregorianChronology, instant});
    }

    private Object readResolve() {
        return getInstance(getZone(), (ReadableInstant) this.e, getMinimumDaysInFirstWeek());
    }

    public DateTimeZone getZone() {
        Chronology base = getBase();
        if (base != null) {
            return base.getZone();
        }
        return DateTimeZone.UTC;
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
        return getInstance(dateTimeZone, (ReadableInstant) this.e, getMinimumDaysInFirstWeek());
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4) {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4);
        }
        long dateTimeMillis = this.d.getDateTimeMillis(i, i2, i3, i4);
        if (dateTimeMillis < this.f) {
            dateTimeMillis = this.c.getDateTimeMillis(i, i2, i3, i4);
            if (dateTimeMillis >= this.f) {
                throw new IllegalArgumentException("Specified date does not exist");
            }
        }
        return dateTimeMillis;
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8;
        int i9;
        long j;
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
        }
        try {
            j = this.d.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
            i9 = i2;
            i8 = i3;
        } catch (IllegalFieldValueException e2) {
            IllegalFieldValueException illegalFieldValueException = e2;
            i9 = i2;
            if (i9 == 2) {
                i8 = i3;
                if (i8 == 29) {
                    long dateTimeMillis = this.d.getDateTimeMillis(i, i9, 28, i4, i5, i6, i7);
                    if (dateTimeMillis >= this.f) {
                        throw illegalFieldValueException;
                    }
                    j = dateTimeMillis;
                }
            }
            throw illegalFieldValueException;
        }
        if (j < this.f) {
            j = this.c.getDateTimeMillis(i, i9, i8, i4, i5, i6, i7);
            if (j >= this.f) {
                throw new IllegalArgumentException("Specified date does not exist");
            }
        }
        return j;
    }

    public Instant getGregorianCutover() {
        return this.e;
    }

    public int getMinimumDaysInFirstWeek() {
        return this.d.getMinimumDaysInFirstWeek();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GJChronology)) {
            return false;
        }
        GJChronology gJChronology = (GJChronology) obj;
        if (!(this.f == gJChronology.f && getMinimumDaysInFirstWeek() == gJChronology.getMinimumDaysInFirstWeek() && getZone().equals(gJChronology.getZone()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ("GJ".hashCode() * 11) + getZone().hashCode() + getMinimumDaysInFirstWeek() + this.e.hashCode();
    }

    public String toString() {
        DateTimeFormatter dateTimeFormatter;
        StringBuffer stringBuffer = new StringBuffer(60);
        stringBuffer.append("GJChronology");
        stringBuffer.append('[');
        stringBuffer.append(getZone().getID());
        if (this.f != a.getMillis()) {
            stringBuffer.append(",cutover=");
            if (withUTC().dayOfYear().remainder(this.f) == 0) {
                dateTimeFormatter = ISODateTimeFormat.date();
            } else {
                dateTimeFormatter = ISODateTimeFormat.dateTime();
            }
            dateTimeFormatter.withChronology(withUTC()).printTo(stringBuffer, this.f);
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            stringBuffer.append(",mdfw=");
            stringBuffer.append(getMinimumDaysInFirstWeek());
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        Object[] objArr = (Object[]) getParam();
        JulianChronology julianChronology = (JulianChronology) objArr[0];
        GregorianChronology gregorianChronology = (GregorianChronology) objArr[1];
        Instant instant = (Instant) objArr[2];
        this.f = instant.getMillis();
        this.c = julianChronology;
        this.d = gregorianChronology;
        this.e = instant;
        if (getBase() == null) {
            if (julianChronology.getMinimumDaysInFirstWeek() != gregorianChronology.getMinimumDaysInFirstWeek()) {
                throw new IllegalArgumentException();
            }
            this.g = this.f - a(this.f);
            fields.copyFieldsFrom(gregorianChronology);
            if (gregorianChronology.millisOfDay().get(this.f) == 0) {
                CutoverField cutoverField = new CutoverField(this, julianChronology.millisOfSecond(), fields.millisOfSecond, this.f);
                fields.millisOfSecond = cutoverField;
                CutoverField cutoverField2 = new CutoverField(this, julianChronology.millisOfDay(), fields.millisOfDay, this.f);
                fields.millisOfDay = cutoverField2;
                CutoverField cutoverField3 = new CutoverField(this, julianChronology.secondOfMinute(), fields.secondOfMinute, this.f);
                fields.secondOfMinute = cutoverField3;
                CutoverField cutoverField4 = new CutoverField(this, julianChronology.secondOfDay(), fields.secondOfDay, this.f);
                fields.secondOfDay = cutoverField4;
                CutoverField cutoverField5 = new CutoverField(this, julianChronology.minuteOfHour(), fields.minuteOfHour, this.f);
                fields.minuteOfHour = cutoverField5;
                CutoverField cutoverField6 = new CutoverField(this, julianChronology.minuteOfDay(), fields.minuteOfDay, this.f);
                fields.minuteOfDay = cutoverField6;
                CutoverField cutoverField7 = new CutoverField(this, julianChronology.hourOfDay(), fields.hourOfDay, this.f);
                fields.hourOfDay = cutoverField7;
                CutoverField cutoverField8 = new CutoverField(this, julianChronology.hourOfHalfday(), fields.hourOfHalfday, this.f);
                fields.hourOfHalfday = cutoverField8;
                CutoverField cutoverField9 = new CutoverField(this, julianChronology.clockhourOfDay(), fields.clockhourOfDay, this.f);
                fields.clockhourOfDay = cutoverField9;
                CutoverField cutoverField10 = new CutoverField(this, julianChronology.clockhourOfHalfday(), fields.clockhourOfHalfday, this.f);
                fields.clockhourOfHalfday = cutoverField10;
                CutoverField cutoverField11 = new CutoverField(this, julianChronology.halfdayOfDay(), fields.halfdayOfDay, this.f);
                fields.halfdayOfDay = cutoverField11;
            }
            CutoverField cutoverField12 = new CutoverField(this, julianChronology.era(), fields.era, this.f);
            fields.era = cutoverField12;
            ImpreciseCutoverField impreciseCutoverField = new ImpreciseCutoverField(this, julianChronology.year(), fields.year, this.f);
            fields.year = impreciseCutoverField;
            fields.years = fields.year.getDurationField();
            ImpreciseCutoverField impreciseCutoverField2 = new ImpreciseCutoverField(this, julianChronology.yearOfEra(), fields.yearOfEra, fields.years, this.f);
            fields.yearOfEra = impreciseCutoverField2;
            ImpreciseCutoverField impreciseCutoverField3 = new ImpreciseCutoverField(this, julianChronology.centuryOfEra(), fields.centuryOfEra, this.f);
            fields.centuryOfEra = impreciseCutoverField3;
            fields.centuries = fields.centuryOfEra.getDurationField();
            ImpreciseCutoverField impreciseCutoverField4 = new ImpreciseCutoverField(this, julianChronology.yearOfCentury(), fields.yearOfCentury, fields.years, fields.centuries, this.f);
            fields.yearOfCentury = impreciseCutoverField4;
            ImpreciseCutoverField impreciseCutoverField5 = new ImpreciseCutoverField(this, julianChronology.monthOfYear(), fields.monthOfYear, (DurationField) null, fields.years, this.f);
            fields.monthOfYear = impreciseCutoverField5;
            fields.months = fields.monthOfYear.getDurationField();
            ImpreciseCutoverField impreciseCutoverField6 = new ImpreciseCutoverField(julianChronology.weekyear(), fields.weekyear, (DurationField) null, this.f, true);
            fields.weekyear = impreciseCutoverField6;
            fields.weekyears = fields.weekyear.getDurationField();
            ImpreciseCutoverField impreciseCutoverField7 = new ImpreciseCutoverField(this, julianChronology.weekyearOfCentury(), fields.weekyearOfCentury, fields.weekyears, fields.centuries, this.f);
            fields.weekyearOfCentury = impreciseCutoverField7;
            CutoverField cutoverField13 = new CutoverField(julianChronology.dayOfYear(), fields.dayOfYear, fields.years, gregorianChronology.year().roundCeiling(this.f), false);
            fields.dayOfYear = cutoverField13;
            CutoverField cutoverField14 = new CutoverField(julianChronology.weekOfWeekyear(), fields.weekOfWeekyear, fields.weekyears, gregorianChronology.weekyear().roundCeiling(this.f), true);
            fields.weekOfWeekyear = cutoverField14;
            CutoverField cutoverField15 = new CutoverField(this, julianChronology.dayOfMonth(), fields.dayOfMonth, this.f);
            cutoverField15.f = fields.months;
            fields.dayOfMonth = cutoverField15;
        }
    }

    /* access modifiers changed from: 0000 */
    public long a(long j) {
        return a(j, this.c, this.d);
    }

    /* access modifiers changed from: 0000 */
    public long b(long j) {
        return a(j, this.d, this.c);
    }

    /* access modifiers changed from: 0000 */
    public long c(long j) {
        return b(j, this.c, this.d);
    }

    /* access modifiers changed from: 0000 */
    public long d(long j) {
        return b(j, this.d, this.c);
    }
}
