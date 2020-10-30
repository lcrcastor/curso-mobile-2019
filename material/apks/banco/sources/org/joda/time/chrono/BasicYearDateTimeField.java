package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

class BasicYearDateTimeField extends ImpreciseDateTimeField {
    protected final BasicChronology a;

    public DurationField getRangeDurationField() {
        return null;
    }

    public boolean isLenient() {
        return false;
    }

    BasicYearDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.year(), basicChronology.f());
        this.a = basicChronology;
    }

    public int get(long j) {
        return this.a.a(j);
    }

    public long add(long j, int i) {
        if (i == 0) {
            return j;
        }
        return set(j, FieldUtils.safeAdd(get(j), i));
    }

    public long add(long j, long j2) {
        return add(j, FieldUtils.safeToInt(j2));
    }

    public long addWrapField(long j, int i) {
        if (i == 0) {
            return j;
        }
        return set(j, FieldUtils.getWrappedValue(this.a.a(j), i, this.a.c(), this.a.d()));
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.a.c(), this.a.d());
        return this.a.f(j, i);
    }

    public long setExtended(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.a.c() - 1, this.a.d() + 1);
        return this.a.f(j, i);
    }

    public long getDifferenceAsLong(long j, long j2) {
        if (j < j2) {
            return -this.a.a(j2, j);
        }
        return this.a.a(j, j2);
    }

    public boolean isLeap(long j) {
        return this.a.e(get(j));
    }

    public int getLeapAmount(long j) {
        return this.a.e(get(j)) ? 1 : 0;
    }

    public DurationField getLeapDurationField() {
        return this.a.days();
    }

    public int getMinimumValue() {
        return this.a.c();
    }

    public int getMaximumValue() {
        return this.a.d();
    }

    public long roundFloor(long j) {
        return this.a.d(get(j));
    }

    public long roundCeiling(long j) {
        int i = get(j);
        return j != this.a.d(i) ? this.a.d(i + 1) : j;
    }

    public long remainder(long j) {
        return j - roundFloor(j);
    }
}
