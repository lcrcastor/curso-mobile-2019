package org.joda.time.field;

import org.joda.time.DurationFieldType;

public class PreciseDurationField extends BaseDurationField {
    private static final long serialVersionUID = -8346152187724495365L;
    private final long a;

    public final boolean isPrecise() {
        return true;
    }

    public PreciseDurationField(DurationFieldType durationFieldType, long j) {
        super(durationFieldType);
        this.a = j;
    }

    public final long getUnitMillis() {
        return this.a;
    }

    public long getValueAsLong(long j, long j2) {
        return j / this.a;
    }

    public long getMillis(int i, long j) {
        return ((long) i) * this.a;
    }

    public long getMillis(long j, long j2) {
        return FieldUtils.safeMultiply(j, this.a);
    }

    public long add(long j, int i) {
        return FieldUtils.safeAdd(j, ((long) i) * this.a);
    }

    public long add(long j, long j2) {
        return FieldUtils.safeAdd(j, FieldUtils.safeMultiply(j2, this.a));
    }

    public long getDifferenceAsLong(long j, long j2) {
        return FieldUtils.safeSubtract(j, j2) / this.a;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreciseDurationField)) {
            return false;
        }
        PreciseDurationField preciseDurationField = (PreciseDurationField) obj;
        if (!(getType() == preciseDurationField.getType() && this.a == preciseDurationField.a)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = this.a;
        return ((int) (j ^ (j >>> 32))) + getType().hashCode();
    }
}
