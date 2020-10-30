package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public class PreciseDateTimeField extends PreciseDurationDateTimeField {
    private final int b;
    private final DurationField c;

    public PreciseDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField, DurationField durationField2) {
        super(dateTimeFieldType, durationField);
        if (!durationField2.isPrecise()) {
            throw new IllegalArgumentException("Range duration field must be precise");
        }
        this.b = (int) (durationField2.getUnitMillis() / getUnitMillis());
        if (this.b < 2) {
            throw new IllegalArgumentException("The effective range must be at least 2");
        }
        this.c = durationField2;
    }

    public int get(long j) {
        if (j >= 0) {
            return (int) ((j / getUnitMillis()) % ((long) this.b));
        }
        return (this.b - 1) + ((int) (((j + 1) / getUnitMillis()) % ((long) this.b)));
    }

    public long addWrapField(long j, int i) {
        int i2 = get(j);
        return j + (((long) (FieldUtils.getWrappedValue(i2, i, getMinimumValue(), getMaximumValue()) - i2)) * getUnitMillis());
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, getMinimumValue(), getMaximumValue());
        return j + (((long) (i - get(j))) * this.a);
    }

    public DurationField getRangeDurationField() {
        return this.c;
    }

    public int getMaximumValue() {
        return this.b - 1;
    }

    public int getRange() {
        return this.b;
    }
}
