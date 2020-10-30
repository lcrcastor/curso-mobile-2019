package org.joda.time.field;

import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public class DecoratedDurationField extends BaseDurationField {
    private static final long serialVersionUID = 8019982251647420015L;
    private final DurationField a;

    public DecoratedDurationField(DurationField durationField, DurationFieldType durationFieldType) {
        super(durationFieldType);
        if (durationField == null) {
            throw new IllegalArgumentException("The field must not be null");
        } else if (!durationField.isSupported()) {
            throw new IllegalArgumentException("The field must be supported");
        } else {
            this.a = durationField;
        }
    }

    public final DurationField getWrappedField() {
        return this.a;
    }

    public boolean isPrecise() {
        return this.a.isPrecise();
    }

    public long getValueAsLong(long j, long j2) {
        return this.a.getValueAsLong(j, j2);
    }

    public long getMillis(int i, long j) {
        return this.a.getMillis(i, j);
    }

    public long getMillis(long j, long j2) {
        return this.a.getMillis(j, j2);
    }

    public long add(long j, int i) {
        return this.a.add(j, i);
    }

    public long add(long j, long j2) {
        return this.a.add(j, j2);
    }

    public long getDifferenceAsLong(long j, long j2) {
        return this.a.getDifferenceAsLong(j, j2);
    }

    public long getUnitMillis() {
        return this.a.getUnitMillis();
    }
}
