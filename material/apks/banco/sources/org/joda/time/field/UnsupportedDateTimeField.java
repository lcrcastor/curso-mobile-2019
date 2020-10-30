package org.joda.time.field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;

public final class UnsupportedDateTimeField extends DateTimeField implements Serializable {
    private static HashMap<DateTimeFieldType, UnsupportedDateTimeField> a = null;
    private static final long serialVersionUID = -1934618396111902255L;
    private final DateTimeFieldType b;
    private final DurationField c;

    public DurationField getLeapDurationField() {
        return null;
    }

    public DurationField getRangeDurationField() {
        return null;
    }

    public boolean isLenient() {
        return false;
    }

    public boolean isSupported() {
        return false;
    }

    public String toString() {
        return "UnsupportedDateTimeField";
    }

    public static synchronized UnsupportedDateTimeField getInstance(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        UnsupportedDateTimeField unsupportedDateTimeField;
        synchronized (UnsupportedDateTimeField.class) {
            unsupportedDateTimeField = null;
            if (a == null) {
                a = new HashMap<>(7);
            } else {
                UnsupportedDateTimeField unsupportedDateTimeField2 = (UnsupportedDateTimeField) a.get(dateTimeFieldType);
                if (unsupportedDateTimeField2 == null || unsupportedDateTimeField2.getDurationField() == durationField) {
                    unsupportedDateTimeField = unsupportedDateTimeField2;
                }
            }
            if (unsupportedDateTimeField == null) {
                unsupportedDateTimeField = new UnsupportedDateTimeField(dateTimeFieldType, durationField);
                a.put(dateTimeFieldType, unsupportedDateTimeField);
            }
        }
        return unsupportedDateTimeField;
    }

    private UnsupportedDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        if (dateTimeFieldType == null || durationField == null) {
            throw new IllegalArgumentException();
        }
        this.b = dateTimeFieldType;
        this.c = durationField;
    }

    public DateTimeFieldType getType() {
        return this.b;
    }

    public String getName() {
        return this.b.getName();
    }

    public int get(long j) {
        throw a();
    }

    public String getAsText(long j, Locale locale) {
        throw a();
    }

    public String getAsText(long j) {
        throw a();
    }

    public String getAsText(ReadablePartial readablePartial, int i, Locale locale) {
        throw a();
    }

    public String getAsText(ReadablePartial readablePartial, Locale locale) {
        throw a();
    }

    public String getAsText(int i, Locale locale) {
        throw a();
    }

    public String getAsShortText(long j, Locale locale) {
        throw a();
    }

    public String getAsShortText(long j) {
        throw a();
    }

    public String getAsShortText(ReadablePartial readablePartial, int i, Locale locale) {
        throw a();
    }

    public String getAsShortText(ReadablePartial readablePartial, Locale locale) {
        throw a();
    }

    public String getAsShortText(int i, Locale locale) {
        throw a();
    }

    public long add(long j, int i) {
        return getDurationField().add(j, i);
    }

    public long add(long j, long j2) {
        return getDurationField().add(j, j2);
    }

    public int[] add(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        throw a();
    }

    public int[] addWrapPartial(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        throw a();
    }

    public long addWrapField(long j, int i) {
        throw a();
    }

    public int[] addWrapField(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        throw a();
    }

    public int getDifference(long j, long j2) {
        return getDurationField().getDifference(j, j2);
    }

    public long getDifferenceAsLong(long j, long j2) {
        return getDurationField().getDifferenceAsLong(j, j2);
    }

    public long set(long j, int i) {
        throw a();
    }

    public int[] set(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        throw a();
    }

    public long set(long j, String str, Locale locale) {
        throw a();
    }

    public long set(long j, String str) {
        throw a();
    }

    public int[] set(ReadablePartial readablePartial, int i, int[] iArr, String str, Locale locale) {
        throw a();
    }

    public DurationField getDurationField() {
        return this.c;
    }

    public boolean isLeap(long j) {
        throw a();
    }

    public int getLeapAmount(long j) {
        throw a();
    }

    public int getMinimumValue() {
        throw a();
    }

    public int getMinimumValue(long j) {
        throw a();
    }

    public int getMinimumValue(ReadablePartial readablePartial) {
        throw a();
    }

    public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
        throw a();
    }

    public int getMaximumValue() {
        throw a();
    }

    public int getMaximumValue(long j) {
        throw a();
    }

    public int getMaximumValue(ReadablePartial readablePartial) {
        throw a();
    }

    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        throw a();
    }

    public int getMaximumTextLength(Locale locale) {
        throw a();
    }

    public int getMaximumShortTextLength(Locale locale) {
        throw a();
    }

    public long roundFloor(long j) {
        throw a();
    }

    public long roundCeiling(long j) {
        throw a();
    }

    public long roundHalfFloor(long j) {
        throw a();
    }

    public long roundHalfCeiling(long j) {
        throw a();
    }

    public long roundHalfEven(long j) {
        throw a();
    }

    public long remainder(long j) {
        throw a();
    }

    private Object readResolve() {
        return getInstance(this.b, this.c);
    }

    private UnsupportedOperationException a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(" field is unsupported");
        return new UnsupportedOperationException(sb.toString());
    }
}
