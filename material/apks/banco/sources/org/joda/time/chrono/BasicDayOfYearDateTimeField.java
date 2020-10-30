package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicDayOfYearDateTimeField extends PreciseDurationDateTimeField {
    private final BasicChronology b;

    public int getMinimumValue() {
        return 1;
    }

    BasicDayOfYearDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfYear(), durationField);
        this.b = basicChronology;
    }

    public int get(long j) {
        return this.b.d(j);
    }

    public DurationField getRangeDurationField() {
        return this.b.years();
    }

    public int getMaximumValue() {
        return this.b.a();
    }

    public int getMaximumValue(long j) {
        return this.b.a(this.b.a(j));
    }

    public int getMaximumValue(ReadablePartial readablePartial) {
        if (!readablePartial.isSupported(DateTimeFieldType.year())) {
            return this.b.a();
        }
        return this.b.a(readablePartial.get(DateTimeFieldType.year()));
    }

    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        int size = readablePartial.size();
        for (int i = 0; i < size; i++) {
            if (readablePartial.getFieldType(i) == DateTimeFieldType.year()) {
                return this.b.a(iArr[i]);
            }
        }
        return this.b.a();
    }

    /* access modifiers changed from: protected */
    public int getMaximumValueForSet(long j, int i) {
        int a = this.b.a() - 1;
        return (i > a || i < 1) ? getMaximumValue(j) : a;
    }

    public boolean isLeap(long j) {
        return this.b.j(j);
    }
}
