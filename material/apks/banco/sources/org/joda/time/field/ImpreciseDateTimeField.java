package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public abstract class ImpreciseDateTimeField extends BaseDateTimeField {
    private final DurationField a;
    final long b;

    final class LinkedDurationField extends BaseDurationField {
        private static final long serialVersionUID = -203813474600094134L;

        public boolean isPrecise() {
            return false;
        }

        LinkedDurationField(DurationFieldType durationFieldType) {
            super(durationFieldType);
        }

        public long getUnitMillis() {
            return ImpreciseDateTimeField.this.b;
        }

        public int getValue(long j, long j2) {
            return ImpreciseDateTimeField.this.getDifference(j2 + j, j2);
        }

        public long getValueAsLong(long j, long j2) {
            return ImpreciseDateTimeField.this.getDifferenceAsLong(j2 + j, j2);
        }

        public long getMillis(int i, long j) {
            return ImpreciseDateTimeField.this.add(j, i) - j;
        }

        public long getMillis(long j, long j2) {
            return ImpreciseDateTimeField.this.add(j2, j) - j2;
        }

        public long add(long j, int i) {
            return ImpreciseDateTimeField.this.add(j, i);
        }

        public long add(long j, long j2) {
            return ImpreciseDateTimeField.this.add(j, j2);
        }

        public int getDifference(long j, long j2) {
            return ImpreciseDateTimeField.this.getDifference(j, j2);
        }

        public long getDifferenceAsLong(long j, long j2) {
            return ImpreciseDateTimeField.this.getDifferenceAsLong(j, j2);
        }
    }

    public abstract long add(long j, int i);

    public abstract long add(long j, long j2);

    public abstract int get(long j);

    public abstract DurationField getRangeDurationField();

    public abstract long roundFloor(long j);

    public abstract long set(long j, int i);

    public ImpreciseDateTimeField(DateTimeFieldType dateTimeFieldType, long j) {
        super(dateTimeFieldType);
        this.b = j;
        this.a = new LinkedDurationField(dateTimeFieldType.getDurationType());
    }

    public int getDifference(long j, long j2) {
        return FieldUtils.safeToInt(getDifferenceAsLong(j, j2));
    }

    public long getDifferenceAsLong(long j, long j2) {
        long j3;
        long j4;
        if (j < j2) {
            return -getDifferenceAsLong(j2, j);
        }
        long j5 = (j - j2) / this.b;
        if (add(j2, j5) < j) {
            while (true) {
                j4 = j5 + 1;
                if (add(j2, j4) > j) {
                    break;
                }
                j5 = j4;
            }
            j5 = j4 - 1;
        } else if (add(j2, j5) > j) {
            while (true) {
                j3 = j5 - 1;
                if (add(j2, j3) <= j) {
                    break;
                }
                j5 = j3;
            }
            j5 = j3;
        }
        return j5;
    }

    public final DurationField getDurationField() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final long getDurationUnitMillis() {
        return this.b;
    }
}
