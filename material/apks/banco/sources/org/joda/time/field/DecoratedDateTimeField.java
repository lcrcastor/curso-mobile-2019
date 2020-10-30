package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public abstract class DecoratedDateTimeField extends BaseDateTimeField {
    private final DateTimeField a;

    protected DecoratedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType) {
        super(dateTimeFieldType);
        if (dateTimeField == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (!dateTimeField.isSupported()) {
            throw new IllegalArgumentException("The field must be supported");
        } else {
            this.a = dateTimeField;
        }
    }

    public final DateTimeField getWrappedField() {
        return this.a;
    }

    public boolean isLenient() {
        return this.a.isLenient();
    }

    public int get(long j) {
        return this.a.get(j);
    }

    public long set(long j, int i) {
        return this.a.set(j, i);
    }

    public DurationField getDurationField() {
        return this.a.getDurationField();
    }

    public DurationField getRangeDurationField() {
        return this.a.getRangeDurationField();
    }

    public int getMinimumValue() {
        return this.a.getMinimumValue();
    }

    public int getMaximumValue() {
        return this.a.getMaximumValue();
    }

    public long roundFloor(long j) {
        return this.a.roundFloor(j);
    }
}
