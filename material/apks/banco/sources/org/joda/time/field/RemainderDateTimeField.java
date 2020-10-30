package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public class RemainderDateTimeField extends DecoratedDateTimeField {
    final int a;
    final DurationField b;
    final DurationField c;

    public int getMinimumValue() {
        return 0;
    }

    public RemainderDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i) {
        super(dateTimeField, dateTimeFieldType);
        if (i < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        DurationField durationField = dateTimeField.getDurationField();
        if (durationField == null) {
            this.c = null;
        } else {
            this.c = new ScaledDurationField(durationField, dateTimeFieldType.getRangeDurationType(), i);
        }
        this.b = dateTimeField.getDurationField();
        this.a = i;
    }

    public RemainderDateTimeField(DateTimeField dateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType, int i) {
        super(dateTimeField, dateTimeFieldType);
        if (i < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        this.c = durationField;
        this.b = dateTimeField.getDurationField();
        this.a = i;
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField) {
        this(dividedDateTimeField, dividedDateTimeField.getType());
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField, DateTimeFieldType dateTimeFieldType) {
        this(dividedDateTimeField, dividedDateTimeField.getWrappedField().getDurationField(), dateTimeFieldType);
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType) {
        super(dividedDateTimeField.getWrappedField(), dateTimeFieldType);
        this.a = dividedDateTimeField.a;
        this.b = durationField;
        this.c = dividedDateTimeField.b;
    }

    public int get(long j) {
        int i = getWrappedField().get(j);
        if (i >= 0) {
            return i % this.a;
        }
        return (this.a - 1) + ((i + 1) % this.a);
    }

    public long addWrapField(long j, int i) {
        return set(j, FieldUtils.getWrappedValue(get(j), i, 0, this.a - 1));
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, 0, this.a - 1);
        return getWrappedField().set(j, (a(getWrappedField().get(j)) * this.a) + i);
    }

    public DurationField getDurationField() {
        return this.b;
    }

    public DurationField getRangeDurationField() {
        return this.c;
    }

    public int getMaximumValue() {
        return this.a - 1;
    }

    public long roundFloor(long j) {
        return getWrappedField().roundFloor(j);
    }

    public long roundCeiling(long j) {
        return getWrappedField().roundCeiling(j);
    }

    public long roundHalfFloor(long j) {
        return getWrappedField().roundHalfFloor(j);
    }

    public long roundHalfCeiling(long j) {
        return getWrappedField().roundHalfCeiling(j);
    }

    public long roundHalfEven(long j) {
        return getWrappedField().roundHalfEven(j);
    }

    public long remainder(long j) {
        return getWrappedField().remainder(j);
    }

    public int getDivisor() {
        return this.a;
    }

    private int a(int i) {
        if (i >= 0) {
            return i / this.a;
        }
        return ((i + 1) / this.a) - 1;
    }
}
