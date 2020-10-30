package org.joda.time.field;

import java.io.Serializable;
import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;

public class DelegatedDateTimeField extends DateTimeField implements Serializable {
    private static final long serialVersionUID = -4730164440214502503L;
    private final DateTimeField a;
    private final DurationField b;
    private final DateTimeFieldType c;

    public DelegatedDateTimeField(DateTimeField dateTimeField) {
        this(dateTimeField, null);
    }

    public DelegatedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType) {
        this(dateTimeField, null, dateTimeFieldType);
    }

    public DelegatedDateTimeField(DateTimeField dateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType) {
        if (dateTimeField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        this.a = dateTimeField;
        this.b = durationField;
        if (dateTimeFieldType == null) {
            dateTimeFieldType = dateTimeField.getType();
        }
        this.c = dateTimeFieldType;
    }

    public final DateTimeField getWrappedField() {
        return this.a;
    }

    public DateTimeFieldType getType() {
        return this.c;
    }

    public String getName() {
        return this.c.getName();
    }

    public boolean isSupported() {
        return this.a.isSupported();
    }

    public boolean isLenient() {
        return this.a.isLenient();
    }

    public int get(long j) {
        return this.a.get(j);
    }

    public String getAsText(long j, Locale locale) {
        return this.a.getAsText(j, locale);
    }

    public String getAsText(long j) {
        return this.a.getAsText(j);
    }

    public String getAsText(ReadablePartial readablePartial, int i, Locale locale) {
        return this.a.getAsText(readablePartial, i, locale);
    }

    public String getAsText(ReadablePartial readablePartial, Locale locale) {
        return this.a.getAsText(readablePartial, locale);
    }

    public String getAsText(int i, Locale locale) {
        return this.a.getAsText(i, locale);
    }

    public String getAsShortText(long j, Locale locale) {
        return this.a.getAsShortText(j, locale);
    }

    public String getAsShortText(long j) {
        return this.a.getAsShortText(j);
    }

    public String getAsShortText(ReadablePartial readablePartial, int i, Locale locale) {
        return this.a.getAsShortText(readablePartial, i, locale);
    }

    public String getAsShortText(ReadablePartial readablePartial, Locale locale) {
        return this.a.getAsShortText(readablePartial, locale);
    }

    public String getAsShortText(int i, Locale locale) {
        return this.a.getAsShortText(i, locale);
    }

    public long add(long j, int i) {
        return this.a.add(j, i);
    }

    public long add(long j, long j2) {
        return this.a.add(j, j2);
    }

    public int[] add(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        return this.a.add(readablePartial, i, iArr, i2);
    }

    public int[] addWrapPartial(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        return this.a.addWrapPartial(readablePartial, i, iArr, i2);
    }

    public long addWrapField(long j, int i) {
        return this.a.addWrapField(j, i);
    }

    public int[] addWrapField(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        return this.a.addWrapField(readablePartial, i, iArr, i2);
    }

    public int getDifference(long j, long j2) {
        return this.a.getDifference(j, j2);
    }

    public long getDifferenceAsLong(long j, long j2) {
        return this.a.getDifferenceAsLong(j, j2);
    }

    public long set(long j, int i) {
        return this.a.set(j, i);
    }

    public long set(long j, String str, Locale locale) {
        return this.a.set(j, str, locale);
    }

    public long set(long j, String str) {
        return this.a.set(j, str);
    }

    public int[] set(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        return this.a.set(readablePartial, i, iArr, i2);
    }

    public int[] set(ReadablePartial readablePartial, int i, int[] iArr, String str, Locale locale) {
        return this.a.set(readablePartial, i, iArr, str, locale);
    }

    public DurationField getDurationField() {
        return this.a.getDurationField();
    }

    public DurationField getRangeDurationField() {
        if (this.b != null) {
            return this.b;
        }
        return this.a.getRangeDurationField();
    }

    public boolean isLeap(long j) {
        return this.a.isLeap(j);
    }

    public int getLeapAmount(long j) {
        return this.a.getLeapAmount(j);
    }

    public DurationField getLeapDurationField() {
        return this.a.getLeapDurationField();
    }

    public int getMinimumValue() {
        return this.a.getMinimumValue();
    }

    public int getMinimumValue(long j) {
        return this.a.getMinimumValue(j);
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
        return this.a.getMaximumValue(j);
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

    public long roundFloor(long j) {
        return this.a.roundFloor(j);
    }

    public long roundCeiling(long j) {
        return this.a.roundCeiling(j);
    }

    public long roundHalfFloor(long j) {
        return this.a.roundHalfFloor(j);
    }

    public long roundHalfCeiling(long j) {
        return this.a.roundHalfCeiling(j);
    }

    public long roundHalfEven(long j) {
        return this.a.roundHalfEven(j);
    }

    public long remainder(long j) {
        return this.a.remainder(j);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DateTimeField[");
        sb.append(getName());
        sb.append(']');
        return sb.toString();
    }
}
