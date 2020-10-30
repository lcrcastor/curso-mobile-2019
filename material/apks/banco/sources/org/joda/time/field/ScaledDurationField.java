package org.joda.time.field;

import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public class ScaledDurationField extends DecoratedDurationField {
    private static final long serialVersionUID = -3205227092378684157L;
    private final int a;

    public ScaledDurationField(DurationField durationField, DurationFieldType durationFieldType, int i) {
        super(durationField, durationFieldType);
        if (i == 0 || i == 1) {
            throw new IllegalArgumentException("The scalar must not be 0 or 1");
        }
        this.a = i;
    }

    public int getValue(long j) {
        return getWrappedField().getValue(j) / this.a;
    }

    public long getValueAsLong(long j) {
        return getWrappedField().getValueAsLong(j) / ((long) this.a);
    }

    public int getValue(long j, long j2) {
        return getWrappedField().getValue(j, j2) / this.a;
    }

    public long getValueAsLong(long j, long j2) {
        return getWrappedField().getValueAsLong(j, j2) / ((long) this.a);
    }

    public long getMillis(int i) {
        return getWrappedField().getMillis(((long) i) * ((long) this.a));
    }

    public long getMillis(long j) {
        return getWrappedField().getMillis(FieldUtils.safeMultiply(j, this.a));
    }

    public long getMillis(int i, long j) {
        return getWrappedField().getMillis(((long) i) * ((long) this.a), j);
    }

    public long getMillis(long j, long j2) {
        return getWrappedField().getMillis(FieldUtils.safeMultiply(j, this.a), j2);
    }

    public long add(long j, int i) {
        return getWrappedField().add(j, ((long) i) * ((long) this.a));
    }

    public long add(long j, long j2) {
        return getWrappedField().add(j, FieldUtils.safeMultiply(j2, this.a));
    }

    public int getDifference(long j, long j2) {
        return getWrappedField().getDifference(j, j2) / this.a;
    }

    public long getDifferenceAsLong(long j, long j2) {
        return getWrappedField().getDifferenceAsLong(j, j2) / ((long) this.a);
    }

    public long getUnitMillis() {
        return getWrappedField().getUnitMillis() * ((long) this.a);
    }

    public int getScalar() {
        return this.a;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScaledDurationField)) {
            return false;
        }
        ScaledDurationField scaledDurationField = (ScaledDurationField) obj;
        if (!(getWrappedField().equals(scaledDurationField.getWrappedField()) && getType() == scaledDurationField.getType() && this.a == scaledDurationField.a)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) this.a;
        return ((int) (j ^ (j >>> 32))) + getType().hashCode() + getWrappedField().hashCode();
    }
}
