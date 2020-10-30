package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public class DividedDateTimeField extends DecoratedDateTimeField {
    final int a;
    final DurationField b;
    final DurationField c;
    private final int d;
    private final int e;

    public DividedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i) {
        this(dateTimeField, dateTimeField.getRangeDurationField(), dateTimeFieldType, i);
    }

    public DividedDateTimeField(DateTimeField dateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType, int i) {
        super(dateTimeField, dateTimeFieldType);
        if (i < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        DurationField durationField2 = dateTimeField.getDurationField();
        if (durationField2 == null) {
            this.b = null;
        } else {
            this.b = new ScaledDurationField(durationField2, dateTimeFieldType.getDurationType(), i);
        }
        this.c = durationField;
        this.a = i;
        int minimumValue = dateTimeField.getMinimumValue();
        int i2 = minimumValue >= 0 ? minimumValue / i : ((minimumValue + 1) / i) - 1;
        int maximumValue = dateTimeField.getMaximumValue();
        int i3 = maximumValue >= 0 ? maximumValue / i : ((maximumValue + 1) / i) - 1;
        this.d = i2;
        this.e = i3;
    }

    public DividedDateTimeField(RemainderDateTimeField remainderDateTimeField, DateTimeFieldType dateTimeFieldType) {
        this(remainderDateTimeField, (DurationField) null, dateTimeFieldType);
    }

    public DividedDateTimeField(RemainderDateTimeField remainderDateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType) {
        super(remainderDateTimeField.getWrappedField(), dateTimeFieldType);
        int i = remainderDateTimeField.a;
        this.a = i;
        this.b = remainderDateTimeField.c;
        this.c = durationField;
        DateTimeField wrappedField = getWrappedField();
        int minimumValue = wrappedField.getMinimumValue();
        int i2 = minimumValue >= 0 ? minimumValue / i : ((minimumValue + 1) / i) - 1;
        int maximumValue = wrappedField.getMaximumValue();
        int i3 = maximumValue >= 0 ? maximumValue / i : ((maximumValue + 1) / i) - 1;
        this.d = i2;
        this.e = i3;
    }

    public DurationField getRangeDurationField() {
        if (this.c != null) {
            return this.c;
        }
        return super.getRangeDurationField();
    }

    public int get(long j) {
        int i = getWrappedField().get(j);
        if (i >= 0) {
            return i / this.a;
        }
        return ((i + 1) / this.a) - 1;
    }

    public long add(long j, int i) {
        return getWrappedField().add(j, i * this.a);
    }

    public long add(long j, long j2) {
        return getWrappedField().add(j, j2 * ((long) this.a));
    }

    public long addWrapField(long j, int i) {
        return set(j, FieldUtils.getWrappedValue(get(j), i, this.d, this.e));
    }

    public int getDifference(long j, long j2) {
        return getWrappedField().getDifference(j, j2) / this.a;
    }

    public long getDifferenceAsLong(long j, long j2) {
        return getWrappedField().getDifferenceAsLong(j, j2) / ((long) this.a);
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.d, this.e);
        return getWrappedField().set(j, (i * this.a) + a(getWrappedField().get(j)));
    }

    public DurationField getDurationField() {
        return this.b;
    }

    public int getMinimumValue() {
        return this.d;
    }

    public int getMaximumValue() {
        return this.e;
    }

    public long roundFloor(long j) {
        DateTimeField wrappedField = getWrappedField();
        return wrappedField.roundFloor(wrappedField.set(j, get(j) * this.a));
    }

    public long remainder(long j) {
        return set(j, get(getWrappedField().remainder(j)));
    }

    public int getDivisor() {
        return this.a;
    }

    private int a(int i) {
        if (i >= 0) {
            return i % this.a;
        }
        return (this.a - 1) + ((i + 1) % this.a);
    }
}
