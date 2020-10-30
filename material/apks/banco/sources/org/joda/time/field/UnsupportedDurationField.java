package org.joda.time.field;

import java.io.Serializable;
import java.util.HashMap;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public final class UnsupportedDurationField extends DurationField implements Serializable {
    private static HashMap<DurationFieldType, UnsupportedDurationField> a = null;
    private static final long serialVersionUID = -6390301302770925357L;
    private final DurationFieldType b;

    public int compareTo(DurationField durationField) {
        return 0;
    }

    public long getUnitMillis() {
        return 0;
    }

    public boolean isPrecise() {
        return true;
    }

    public boolean isSupported() {
        return false;
    }

    public static synchronized UnsupportedDurationField getInstance(DurationFieldType durationFieldType) {
        UnsupportedDurationField unsupportedDurationField;
        synchronized (UnsupportedDurationField.class) {
            if (a == null) {
                a = new HashMap<>(7);
                unsupportedDurationField = null;
            } else {
                unsupportedDurationField = (UnsupportedDurationField) a.get(durationFieldType);
            }
            if (unsupportedDurationField == null) {
                unsupportedDurationField = new UnsupportedDurationField(durationFieldType);
                a.put(durationFieldType, unsupportedDurationField);
            }
        }
        return unsupportedDurationField;
    }

    private UnsupportedDurationField(DurationFieldType durationFieldType) {
        this.b = durationFieldType;
    }

    public final DurationFieldType getType() {
        return this.b;
    }

    public String getName() {
        return this.b.getName();
    }

    public int getValue(long j) {
        throw a();
    }

    public long getValueAsLong(long j) {
        throw a();
    }

    public int getValue(long j, long j2) {
        throw a();
    }

    public long getValueAsLong(long j, long j2) {
        throw a();
    }

    public long getMillis(int i) {
        throw a();
    }

    public long getMillis(long j) {
        throw a();
    }

    public long getMillis(int i, long j) {
        throw a();
    }

    public long getMillis(long j, long j2) {
        throw a();
    }

    public long add(long j, int i) {
        throw a();
    }

    public long add(long j, long j2) {
        throw a();
    }

    public int getDifference(long j, long j2) {
        throw a();
    }

    public long getDifferenceAsLong(long j, long j2) {
        throw a();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnsupportedDurationField)) {
            return false;
        }
        UnsupportedDurationField unsupportedDurationField = (UnsupportedDurationField) obj;
        if (unsupportedDurationField.getName() != null) {
            return unsupportedDurationField.getName().equals(getName());
        }
        if (getName() != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UnsupportedDurationField[");
        sb.append(getName());
        sb.append(']');
        return sb.toString();
    }

    private Object readResolve() {
        return getInstance(this.b);
    }

    private UnsupportedOperationException a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(" field is unsupported");
        return new UnsupportedOperationException(sb.toString());
    }
}
