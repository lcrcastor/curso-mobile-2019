package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.UnsupportedDurationField;

final class BasicSingleEraDateTimeField extends BaseDateTimeField {
    private final String a;

    public int get(long j) {
        return 1;
    }

    public int getMaximumValue() {
        return 1;
    }

    public int getMinimumValue() {
        return 1;
    }

    public DurationField getRangeDurationField() {
        return null;
    }

    public boolean isLenient() {
        return false;
    }

    public long roundCeiling(long j) {
        return Long.MAX_VALUE;
    }

    public long roundFloor(long j) {
        return Long.MIN_VALUE;
    }

    public long roundHalfCeiling(long j) {
        return Long.MIN_VALUE;
    }

    public long roundHalfEven(long j) {
        return Long.MIN_VALUE;
    }

    public long roundHalfFloor(long j) {
        return Long.MIN_VALUE;
    }

    BasicSingleEraDateTimeField(String str) {
        super(DateTimeFieldType.era());
        this.a = str;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, 1, 1);
        return j;
    }

    public long set(long j, String str, Locale locale) {
        if (this.a.equals(str) || "1".equals(str)) {
            return j;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.era(), str);
    }

    public DurationField getDurationField() {
        return UnsupportedDurationField.getInstance(DurationFieldType.eras());
    }

    public String getAsText(int i, Locale locale) {
        return this.a;
    }

    public int getMaximumTextLength(Locale locale) {
        return this.a.length();
    }
}
