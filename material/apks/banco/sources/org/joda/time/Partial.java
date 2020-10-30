package org.joda.time;

import cz.msebera.android.httpclient.message.TokenParser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.joda.time.base.AbstractPartial;
import org.joda.time.field.AbstractPartialFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class Partial extends AbstractPartial implements Serializable, ReadablePartial {
    private static final long serialVersionUID = 12324121189002L;
    private final Chronology a;
    private final DateTimeFieldType[] b;
    private final int[] c;
    private transient DateTimeFormatter[] d;

    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 53278362873888L;
        private final Partial a;
        private final int b;

        Property(Partial partial, int i) {
            this.a = partial;
            this.b = i;
        }

        public DateTimeField getField() {
            return this.a.getField(this.b);
        }

        /* access modifiers changed from: protected */
        public ReadablePartial getReadablePartial() {
            return this.a;
        }

        public Partial getPartial() {
            return this.a;
        }

        public int get() {
            return this.a.getValue(this.b);
        }

        public Partial addToCopy(int i) {
            return new Partial(this.a, getField().add(this.a, this.b, this.a.getValues(), i));
        }

        public Partial addWrapFieldToCopy(int i) {
            return new Partial(this.a, getField().addWrapField(this.a, this.b, this.a.getValues(), i));
        }

        public Partial setCopy(int i) {
            return new Partial(this.a, getField().set(this.a, this.b, this.a.getValues(), i));
        }

        public Partial setCopy(String str, Locale locale) {
            return new Partial(this.a, getField().set(this.a, this.b, this.a.getValues(), str, locale));
        }

        public Partial setCopy(String str) {
            return setCopy(str, null);
        }

        public Partial withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public Partial withMinimumValue() {
            return setCopy(getMinimumValue());
        }
    }

    public Partial() {
        this((Chronology) null);
    }

    public Partial(Chronology chronology) {
        this.a = DateTimeUtils.getChronology(chronology).withUTC();
        this.b = new DateTimeFieldType[0];
        this.c = new int[0];
    }

    public Partial(DateTimeFieldType dateTimeFieldType, int i) {
        this(dateTimeFieldType, i, (Chronology) null);
    }

    public Partial(DateTimeFieldType dateTimeFieldType, int i, Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        this.a = withUTC;
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The field type must not be null");
        }
        this.b = new DateTimeFieldType[]{dateTimeFieldType};
        this.c = new int[]{i};
        withUTC.validate(this, this.c);
    }

    public Partial(DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr) {
        this(dateTimeFieldTypeArr, iArr, (Chronology) null);
    }

    public Partial(DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr, Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        this.a = withUTC;
        if (dateTimeFieldTypeArr == null) {
            throw new IllegalArgumentException("Types array must not be null");
        } else if (iArr == null) {
            throw new IllegalArgumentException("Values array must not be null");
        } else if (iArr.length != dateTimeFieldTypeArr.length) {
            throw new IllegalArgumentException("Values array must be the same length as the types array");
        } else if (dateTimeFieldTypeArr.length == 0) {
            this.b = dateTimeFieldTypeArr;
            this.c = iArr;
        } else {
            int i = 0;
            for (int i2 = 0; i2 < dateTimeFieldTypeArr.length; i2++) {
                if (dateTimeFieldTypeArr[i2] == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Types array must not contain null: index ");
                    sb.append(i2);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            DurationField durationField = null;
            while (i < dateTimeFieldTypeArr.length) {
                DateTimeFieldType dateTimeFieldType = dateTimeFieldTypeArr[i];
                DurationField field = dateTimeFieldType.getDurationType().getField(this.a);
                if (i > 0) {
                    if (field.isSupported()) {
                        int compareTo = durationField.compareTo(field);
                        if (compareTo < 0) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Types array must be in order largest-smallest: ");
                            sb2.append(dateTimeFieldTypeArr[i - 1].getName());
                            sb2.append(" < ");
                            sb2.append(dateTimeFieldType.getName());
                            throw new IllegalArgumentException(sb2.toString());
                        } else if (compareTo != 0) {
                            continue;
                        } else if (durationField.equals(field)) {
                            int i3 = i - 1;
                            DurationFieldType rangeDurationType = dateTimeFieldTypeArr[i3].getRangeDurationType();
                            DurationFieldType rangeDurationType2 = dateTimeFieldType.getRangeDurationType();
                            if (rangeDurationType == null) {
                                if (rangeDurationType2 == null) {
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append("Types array must not contain duplicate: ");
                                    sb3.append(dateTimeFieldTypeArr[i3].getName());
                                    sb3.append(" and ");
                                    sb3.append(dateTimeFieldType.getName());
                                    throw new IllegalArgumentException(sb3.toString());
                                }
                            } else if (rangeDurationType2 == null) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("Types array must be in order largest-smallest: ");
                                sb4.append(dateTimeFieldTypeArr[i3].getName());
                                sb4.append(" < ");
                                sb4.append(dateTimeFieldType.getName());
                                throw new IllegalArgumentException(sb4.toString());
                            } else {
                                DurationField field2 = rangeDurationType.getField(this.a);
                                DurationField field3 = rangeDurationType2.getField(this.a);
                                if (field2.compareTo(field3) < 0) {
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append("Types array must be in order largest-smallest: ");
                                    sb5.append(dateTimeFieldTypeArr[i3].getName());
                                    sb5.append(" < ");
                                    sb5.append(dateTimeFieldType.getName());
                                    throw new IllegalArgumentException(sb5.toString());
                                } else if (field2.compareTo(field3) == 0) {
                                    StringBuilder sb6 = new StringBuilder();
                                    sb6.append("Types array must not contain duplicate: ");
                                    sb6.append(dateTimeFieldTypeArr[i3].getName());
                                    sb6.append(" and ");
                                    sb6.append(dateTimeFieldType.getName());
                                    throw new IllegalArgumentException(sb6.toString());
                                }
                            }
                        } else if (durationField.isSupported() && durationField.getType() != DurationFieldType.d) {
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("Types array must be in order largest-smallest, for year-based fields, years is defined as being largest: ");
                            sb7.append(dateTimeFieldTypeArr[i - 1].getName());
                            sb7.append(" < ");
                            sb7.append(dateTimeFieldType.getName());
                            throw new IllegalArgumentException(sb7.toString());
                        }
                    } else if (durationField.isSupported()) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("Types array must be in order largest-smallest: ");
                        sb8.append(dateTimeFieldTypeArr[i - 1].getName());
                        sb8.append(" < ");
                        sb8.append(dateTimeFieldType.getName());
                        throw new IllegalArgumentException(sb8.toString());
                    } else {
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("Types array must not contain duplicate unsupported: ");
                        sb9.append(dateTimeFieldTypeArr[i - 1].getName());
                        sb9.append(" and ");
                        sb9.append(dateTimeFieldType.getName());
                        throw new IllegalArgumentException(sb9.toString());
                    }
                }
                i++;
                durationField = field;
            }
            this.b = (DateTimeFieldType[]) dateTimeFieldTypeArr.clone();
            withUTC.validate(this, iArr);
            this.c = (int[]) iArr.clone();
        }
    }

    public Partial(ReadablePartial readablePartial) {
        if (readablePartial == null) {
            throw new IllegalArgumentException("The partial must not be null");
        }
        this.a = DateTimeUtils.getChronology(readablePartial.getChronology()).withUTC();
        this.b = new DateTimeFieldType[readablePartial.size()];
        this.c = new int[readablePartial.size()];
        for (int i = 0; i < readablePartial.size(); i++) {
            this.b[i] = readablePartial.getFieldType(i);
            this.c[i] = readablePartial.getValue(i);
        }
    }

    Partial(Partial partial, int[] iArr) {
        this.a = partial.a;
        this.b = partial.b;
        this.c = iArr;
    }

    Partial(Chronology chronology, DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr) {
        this.a = chronology;
        this.b = dateTimeFieldTypeArr;
        this.c = iArr;
    }

    public int size() {
        return this.b.length;
    }

    public Chronology getChronology() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public DateTimeField getField(int i, Chronology chronology) {
        return this.b[i].getField(chronology);
    }

    public DateTimeFieldType getFieldType(int i) {
        return this.b[i];
    }

    public DateTimeFieldType[] getFieldTypes() {
        return (DateTimeFieldType[]) this.b.clone();
    }

    public int getValue(int i) {
        return this.c[i];
    }

    public int[] getValues() {
        return (int[]) this.c.clone();
    }

    public Partial withChronologyRetainFields(Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (withUTC == getChronology()) {
            return this;
        }
        Partial partial = new Partial(withUTC, this.b, this.c);
        withUTC.validate(partial, this.c);
        return partial;
    }

    public Partial with(DateTimeFieldType dateTimeFieldType, int i) {
        int i2;
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The field type must not be null");
        }
        int indexOf = indexOf(dateTimeFieldType);
        if (indexOf == -1) {
            DateTimeFieldType[] dateTimeFieldTypeArr = new DateTimeFieldType[(this.b.length + 1)];
            int[] iArr = new int[dateTimeFieldTypeArr.length];
            DurationField field = dateTimeFieldType.getDurationType().getField(this.a);
            if (field.isSupported()) {
                i2 = 0;
                while (i2 < this.b.length) {
                    DateTimeFieldType dateTimeFieldType2 = this.b[i2];
                    DurationField field2 = dateTimeFieldType2.getDurationType().getField(this.a);
                    if (field2.isSupported()) {
                        int compareTo = field.compareTo(field2);
                        if (compareTo <= 0) {
                            if (compareTo == 0) {
                                if (dateTimeFieldType.getRangeDurationType() != null) {
                                    if (dateTimeFieldType2.getRangeDurationType() != null && dateTimeFieldType.getRangeDurationType().getField(this.a).compareTo(dateTimeFieldType2.getRangeDurationType().getField(this.a)) > 0) {
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            break;
                        }
                    }
                    i2++;
                }
            } else {
                i2 = 0;
            }
            System.arraycopy(this.b, 0, dateTimeFieldTypeArr, 0, i2);
            System.arraycopy(this.c, 0, iArr, 0, i2);
            dateTimeFieldTypeArr[i2] = dateTimeFieldType;
            iArr[i2] = i;
            int i3 = i2 + 1;
            System.arraycopy(this.b, i2, dateTimeFieldTypeArr, i3, (dateTimeFieldTypeArr.length - i2) - 1);
            System.arraycopy(this.c, i2, iArr, i3, (iArr.length - i2) - 1);
            Partial partial = new Partial(dateTimeFieldTypeArr, iArr, this.a);
            this.a.validate(partial, iArr);
            return partial;
        } else if (i == getValue(indexOf)) {
            return this;
        } else {
            return new Partial(this, getField(indexOf).set(this, indexOf, getValues(), i));
        }
    }

    public Partial without(DateTimeFieldType dateTimeFieldType) {
        int indexOf = indexOf(dateTimeFieldType);
        if (indexOf == -1) {
            return this;
        }
        DateTimeFieldType[] dateTimeFieldTypeArr = new DateTimeFieldType[(size() - 1)];
        int[] iArr = new int[(size() - 1)];
        System.arraycopy(this.b, 0, dateTimeFieldTypeArr, 0, indexOf);
        int i = indexOf + 1;
        System.arraycopy(this.b, i, dateTimeFieldTypeArr, indexOf, dateTimeFieldTypeArr.length - indexOf);
        System.arraycopy(this.c, 0, iArr, 0, indexOf);
        System.arraycopy(this.c, i, iArr, indexOf, iArr.length - indexOf);
        Partial partial = new Partial(this.a, dateTimeFieldTypeArr, iArr);
        this.a.validate(partial, iArr);
        return partial;
    }

    public Partial withField(DateTimeFieldType dateTimeFieldType, int i) {
        int indexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i == getValue(indexOfSupported)) {
            return this;
        }
        return new Partial(this, getField(indexOfSupported).set(this, indexOfSupported, getValues(), i));
    }

    public Partial withFieldAdded(DurationFieldType durationFieldType, int i) {
        int indexOfSupported = indexOfSupported(durationFieldType);
        if (i == 0) {
            return this;
        }
        return new Partial(this, getField(indexOfSupported).add(this, indexOfSupported, getValues(), i));
    }

    public Partial withFieldAddWrapped(DurationFieldType durationFieldType, int i) {
        int indexOfSupported = indexOfSupported(durationFieldType);
        if (i == 0) {
            return this;
        }
        return new Partial(this, getField(indexOfSupported).addWrapPartial(this, indexOfSupported, getValues(), i));
    }

    public Partial withPeriodAdded(ReadablePeriod readablePeriod, int i) {
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
        return new Partial(this, values);
    }

    public Partial plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public Partial minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    public boolean isMatch(ReadableInstant readableInstant) {
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        for (int i = 0; i < this.b.length; i++) {
            if (this.b[i].getField(instantChronology).get(instantMillis) != this.c[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isMatch(ReadablePartial readablePartial) {
        if (readablePartial == null) {
            throw new IllegalArgumentException("The partial must not be null");
        }
        for (int i = 0; i < this.b.length; i++) {
            if (readablePartial.get(this.b[i]) != this.c[i]) {
                return false;
            }
        }
        return true;
    }

    public DateTimeFormatter getFormatter() {
        DateTimeFormatter[] dateTimeFormatterArr = this.d;
        if (dateTimeFormatterArr == null) {
            if (size() == 0) {
                return null;
            }
            dateTimeFormatterArr = new DateTimeFormatter[2];
            try {
                ArrayList arrayList = new ArrayList(Arrays.asList(this.b));
                dateTimeFormatterArr[0] = ISODateTimeFormat.forFields(arrayList, true, false);
                if (arrayList.size() == 0) {
                    dateTimeFormatterArr[1] = dateTimeFormatterArr[0];
                }
            } catch (IllegalArgumentException unused) {
            }
            this.d = dateTimeFormatterArr;
        }
        return dateTimeFormatterArr[0];
    }

    public String toString() {
        DateTimeFormatter[] dateTimeFormatterArr = this.d;
        if (dateTimeFormatterArr == null) {
            getFormatter();
            dateTimeFormatterArr = this.d;
            if (dateTimeFormatterArr == null) {
                return toStringList();
            }
        }
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterArr[1];
        if (dateTimeFormatter == null) {
            return toStringList();
        }
        return dateTimeFormatter.print((ReadablePartial) this);
    }

    public String toStringList() {
        int size = size();
        StringBuilder sb = new StringBuilder(size * 20);
        sb.append('[');
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(',');
                sb.append(TokenParser.SP);
            }
            sb.append(this.b[i].getName());
            sb.append('=');
            sb.append(this.c[i]);
        }
        sb.append(']');
        return sb.toString();
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
