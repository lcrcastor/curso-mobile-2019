package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

final class BasicWeekyearDateTimeField extends ImpreciseDateTimeField {
    private final BasicChronology a;

    public DurationField getRangeDurationField() {
        return null;
    }

    public boolean isLenient() {
        return false;
    }

    BasicWeekyearDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.weekyear(), basicChronology.f());
        this.a = basicChronology;
    }

    public int get(long j) {
        return this.a.e(j);
    }

    public long add(long j, int i) {
        return i == 0 ? j : set(j, get(j) + i);
    }

    public long add(long j, long j2) {
        return add(j, FieldUtils.safeToInt(j2));
    }

    public long addWrapField(long j, int i) {
        return add(j, i);
    }

    public long getDifferenceAsLong(long j, long j2) {
        if (j < j2) {
            return (long) (-getDifference(j2, j));
        }
        int i = get(j);
        int i2 = get(j2);
        long remainder = remainder(j);
        long remainder2 = remainder(j2);
        if (remainder2 >= 31449600000L && this.a.b(i) <= 52) {
            remainder2 -= 604800000;
        }
        int i3 = i - i2;
        if (remainder < remainder2) {
            i3--;
        }
        return (long) i3;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, Math.abs(i), this.a.c(), this.a.d());
        int i2 = get(j);
        if (i2 == i) {
            return j;
        }
        int g = this.a.g(j);
        int b = this.a.b(i2);
        int b2 = this.a.b(i);
        if (b2 < b) {
            b = b2;
        }
        int f = this.a.f(j);
        if (f <= b) {
            b = f;
        }
        long f2 = this.a.f(j, i);
        int i3 = get(f2);
        long j2 = i3 < i ? f2 + 604800000 : i3 > i ? f2 - 604800000 : f2;
        return this.a.dayOfWeek().set(j2 + (((long) (b - this.a.f(j2))) * 604800000), g);
    }

    public boolean isLeap(long j) {
        return this.a.b(this.a.e(j)) > 52;
    }

    public int getLeapAmount(long j) {
        return this.a.b(this.a.e(j)) - 52;
    }

    public DurationField getLeapDurationField() {
        return this.a.weeks();
    }

    public int getMinimumValue() {
        return this.a.c();
    }

    public int getMaximumValue() {
        return this.a.d();
    }

    public long roundFloor(long j) {
        long roundFloor = this.a.weekOfWeekyear().roundFloor(j);
        int f = this.a.f(roundFloor);
        return f > 1 ? roundFloor - (((long) (f - 1)) * 604800000) : roundFloor;
    }

    public long remainder(long j) {
        return j - roundFloor(j);
    }
}
