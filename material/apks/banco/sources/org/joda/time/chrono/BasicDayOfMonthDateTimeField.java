package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicDayOfMonthDateTimeField extends PreciseDurationDateTimeField {
    private final BasicChronology b;

    public int getMinimumValue() {
        return 1;
    }

    BasicDayOfMonthDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfMonth(), durationField);
        this.b = basicChronology;
    }

    public int get(long j) {
        return this.b.c(j);
    }

    public DurationField getRangeDurationField() {
        return this.b.months();
    }

    public int getMaximumValue() {
        return this.b.b();
    }

    public int getMaximumValue(long j) {
        return this.b.i(j);
    }

    public int getMaximumValue(ReadablePartial readablePartial) {
        if (!readablePartial.isSupported(DateTimeFieldType.monthOfYear())) {
            return getMaximumValue();
        }
        int i = readablePartial.get(DateTimeFieldType.monthOfYear());
        if (!readablePartial.isSupported(DateTimeFieldType.year())) {
            return this.b.f(i);
        }
        return this.b.b(readablePartial.get(DateTimeFieldType.year()), i);
    }

    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        int size = readablePartial.size();
        for (int i = 0; i < size; i++) {
            if (readablePartial.getFieldType(i) == DateTimeFieldType.monthOfYear()) {
                int i2 = iArr[i];
                for (int i3 = 0; i3 < size; i3++) {
                    if (readablePartial.getFieldType(i3) == DateTimeFieldType.year()) {
                        return this.b.b(iArr[i3], i2);
                    }
                }
                return this.b.f(i2);
            }
        }
        return getMaximumValue();
    }

    /* access modifiers changed from: protected */
    public int getMaximumValueForSet(long j, int i) {
        return this.b.e(j, i);
    }

    public boolean isLeap(long j) {
        return this.b.j(j);
    }
}
