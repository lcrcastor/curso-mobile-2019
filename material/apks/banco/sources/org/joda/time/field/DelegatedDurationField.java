package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public class DelegatedDurationField extends DurationField implements Serializable {
    private static final long serialVersionUID = -5576443481242007829L;
    private final DurationField a;
    private final DurationFieldType b;

    protected DelegatedDurationField(DurationField durationField) {
        this(durationField, null);
    }

    protected DelegatedDurationField(DurationField durationField, DurationFieldType durationFieldType) {
        if (durationField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        this.a = durationField;
        if (durationFieldType == null) {
            durationFieldType = durationField.getType();
        }
        this.b = durationFieldType;
    }

    public final DurationField getWrappedField() {
        return this.a;
    }

    public DurationFieldType getType() {
        return this.b;
    }

    public String getName() {
        return this.b.getName();
    }

    public boolean isSupported() {
        return this.a.isSupported();
    }

    public boolean isPrecise() {
        return this.a.isPrecise();
    }

    public int getValue(long j) {
        return this.a.getValue(j);
    }

    public long getValueAsLong(long j) {
        return this.a.getValueAsLong(j);
    }

    public int getValue(long j, long j2) {
        return this.a.getValue(j, j2);
    }

    public long getValueAsLong(long j, long j2) {
        return this.a.getValueAsLong(j, j2);
    }

    public long getMillis(int i) {
        return this.a.getMillis(i);
    }

    public long getMillis(long j) {
        return this.a.getMillis(j);
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

    public int getDifference(long j, long j2) {
        return this.a.getDifference(j, j2);
    }

    public long getDifferenceAsLong(long j, long j2) {
        return this.a.getDifferenceAsLong(j, j2);
    }

    public long getUnitMillis() {
        return this.a.getUnitMillis();
    }

    public int compareTo(DurationField durationField) {
        return this.a.compareTo(durationField);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DelegatedDurationField) {
            return this.a.equals(((DelegatedDurationField) obj).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public String toString() {
        if (this.b == null) {
            return this.a.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DurationField[");
        sb.append(this.b);
        sb.append(']');
        return sb.toString();
    }
}
