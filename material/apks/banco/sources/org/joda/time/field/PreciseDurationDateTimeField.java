package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public abstract class PreciseDurationDateTimeField extends BaseDateTimeField {
    final long a;
    private final DurationField b;

    public int getMinimumValue() {
        return 0;
    }

    public boolean isLenient() {
        return false;
    }

    public PreciseDurationDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        super(dateTimeFieldType);
        if (!durationField.isPrecise()) {
            throw new IllegalArgumentException("Unit duration field must be precise");
        }
        this.a = durationField.getUnitMillis();
        if (this.a < 1) {
            throw new IllegalArgumentException("The unit milliseconds must be at least 1");
        }
        this.b = durationField;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, getMinimumValue(), getMaximumValueForSet(j, i));
        return j + (((long) (i - get(j))) * this.a);
    }

    public long roundFloor(long j) {
        if (j >= 0) {
            return j - (j % this.a);
        }
        long j2 = j + 1;
        return (j2 - (j2 % this.a)) - this.a;
    }

    public long roundCeiling(long j) {
        if (j <= 0) {
            return j - (j % this.a);
        }
        long j2 = j - 1;
        return (j2 - (j2 % this.a)) + this.a;
    }

    public long remainder(long j) {
        if (j >= 0) {
            return j % this.a;
        }
        return (((j + 1) % this.a) + this.a) - 1;
    }

    public DurationField getDurationField() {
        return this.b;
    }

    public final long getUnitMillis() {
        return this.a;
    }

    public int getMaximumValueForSet(long j, int i) {
        return getMaximumValue(j);
    }
}
