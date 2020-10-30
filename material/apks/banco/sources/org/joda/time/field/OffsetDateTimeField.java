package org.joda.time.field;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public class OffsetDateTimeField extends DecoratedDateTimeField {
    private final int a;
    private final int b;
    private final int c;

    public OffsetDateTimeField(DateTimeField dateTimeField, int i) {
        this(dateTimeField, dateTimeField == null ? null : dateTimeField.getType(), i, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i) {
        this(dateTimeField, dateTimeFieldType, i, Integer.MIN_VALUE, SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int i, int i2, int i3) {
        super(dateTimeField, dateTimeFieldType);
        if (i == 0) {
            throw new IllegalArgumentException("The offset cannot be zero");
        }
        this.a = i;
        if (i2 < dateTimeField.getMinimumValue() + i) {
            this.b = dateTimeField.getMinimumValue() + i;
        } else {
            this.b = i2;
        }
        if (i3 > dateTimeField.getMaximumValue() + i) {
            this.c = dateTimeField.getMaximumValue() + i;
        } else {
            this.c = i3;
        }
    }

    public int get(long j) {
        return super.get(j) + this.a;
    }

    public long add(long j, int i) {
        long add = super.add(j, i);
        FieldUtils.verifyValueBounds((DateTimeField) this, get(add), this.b, this.c);
        return add;
    }

    public long add(long j, long j2) {
        long add = super.add(j, j2);
        FieldUtils.verifyValueBounds((DateTimeField) this, get(add), this.b, this.c);
        return add;
    }

    public long addWrapField(long j, int i) {
        return set(j, FieldUtils.getWrappedValue(get(j), i, this.b, this.c));
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.b, this.c);
        return super.set(j, i - this.a);
    }

    public boolean isLeap(long j) {
        return getWrappedField().isLeap(j);
    }

    public int getLeapAmount(long j) {
        return getWrappedField().getLeapAmount(j);
    }

    public DurationField getLeapDurationField() {
        return getWrappedField().getLeapDurationField();
    }

    public int getMinimumValue() {
        return this.b;
    }

    public int getMaximumValue() {
        return this.c;
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

    public int getOffset() {
        return this.a;
    }
}
