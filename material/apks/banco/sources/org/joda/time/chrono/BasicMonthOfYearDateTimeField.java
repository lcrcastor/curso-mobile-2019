package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

class BasicMonthOfYearDateTimeField extends ImpreciseDateTimeField {
    private final BasicChronology a;
    private final int c = this.a.e();
    private final int d;

    public int getMinimumValue() {
        return 1;
    }

    public boolean isLenient() {
        return false;
    }

    BasicMonthOfYearDateTimeField(BasicChronology basicChronology, int i) {
        super(DateTimeFieldType.monthOfYear(), basicChronology.h());
        this.a = basicChronology;
        this.d = i;
    }

    public int get(long j) {
        return this.a.b(j);
    }

    public long add(long j, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (i == 0) {
            return j;
        }
        long h = (long) this.a.h(j);
        int a2 = this.a.a(j);
        int a3 = this.a.a(j, a2);
        int i6 = a3 - 1;
        int i7 = i6 + i;
        if (a3 <= 0 || i7 >= 0) {
            i2 = i7;
            i3 = a2;
        } else {
            i3 = a2 + 1;
            i2 = (i - this.c) + i6;
        }
        if (i2 >= 0) {
            i5 = i3 + (i2 / this.c);
            i4 = (i2 % this.c) + 1;
        } else {
            i5 = (i3 + (i2 / this.c)) - 1;
            int abs = Math.abs(i2) % this.c;
            if (abs == 0) {
                abs = this.c;
            }
            i4 = (this.c - abs) + 1;
            if (i4 == 1) {
                i5++;
            }
        }
        int a4 = this.a.a(j, a2, a3);
        int b = this.a.b(i5, i4);
        if (a4 > b) {
            a4 = b;
        }
        return this.a.a(i5, i4, a4) + h;
    }

    public long add(long j, long j2) {
        long j3;
        long j4;
        long j5;
        long j6 = j;
        long j7 = j2;
        int i = (int) j7;
        if (((long) i) == j7) {
            return add(j6, i);
        }
        long h = (long) this.a.h(j6);
        int a2 = this.a.a(j6);
        int a3 = this.a.a(j6, a2);
        long j8 = ((long) (a3 - 1)) + j7;
        if (j8 >= 0) {
            j5 = ((long) a2) + (j8 / ((long) this.c));
            j3 = (j8 % ((long) this.c)) + 1;
        } else {
            j5 = (((long) a2) + (j8 / ((long) this.c))) - 1;
            int abs = (int) (Math.abs(j8) % ((long) this.c));
            if (abs == 0) {
                abs = this.c;
            }
            j3 = (long) ((this.c - abs) + 1);
            if (j3 == 1) {
                j4 = j5 + 1;
                if (j4 >= ((long) this.a.c()) || j4 > ((long) this.a.d())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Magnitude of add amount is too large: ");
                    sb.append(j7);
                    throw new IllegalArgumentException(sb.toString());
                }
                int i2 = (int) j4;
                int i3 = (int) j3;
                int a4 = this.a.a(j6, a2, a3);
                int b = this.a.b(i2, i3);
                if (a4 > b) {
                    a4 = b;
                }
                return this.a.a(i2, i3, a4) + h;
            }
        }
        j4 = j5;
        if (j4 >= ((long) this.a.c())) {
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Magnitude of add amount is too large: ");
        sb2.append(j7);
        throw new IllegalArgumentException(sb2.toString());
    }

    public int[] add(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        if (i2 == 0) {
            return iArr;
        }
        if (readablePartial.size() > 0 && readablePartial.getFieldType(0).equals(DateTimeFieldType.monthOfYear()) && i == 0) {
            return set(readablePartial, 0, iArr, ((((iArr[0] - 1) + (i2 % 12)) + 12) % 12) + 1);
        }
        if (!DateTimeUtils.isContiguous(readablePartial)) {
            return super.add(readablePartial, i, iArr, i2);
        }
        long j = 0;
        int size = readablePartial.size();
        for (int i3 = 0; i3 < size; i3++) {
            j = readablePartial.getFieldType(i3).getField(this.a).set(j, iArr[i3]);
        }
        return this.a.get(readablePartial, add(j, i2));
    }

    public long addWrapField(long j, int i) {
        return set(j, FieldUtils.getWrappedValue(get(j), i, 1, this.c));
    }

    public long getDifferenceAsLong(long j, long j2) {
        if (j < j2) {
            return (long) (-getDifference(j2, j));
        }
        int a2 = this.a.a(j);
        int a3 = this.a.a(j, a2);
        int a4 = this.a.a(j2);
        int a5 = this.a.a(j2, a4);
        long j3 = ((((long) (a2 - a4)) * ((long) this.c)) + ((long) a3)) - ((long) a5);
        int a6 = this.a.a(j, a2, a3);
        if (a6 == this.a.b(a2, a3) && this.a.a(j2, a4, a5) > a6) {
            j2 = this.a.dayOfMonth().set(j2, a6);
        }
        return j - this.a.a(a2, a3) < j2 - this.a.a(a4, a5) ? j3 - 1 : j3;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, 1, this.c);
        int a2 = this.a.a(j);
        int b = this.a.b(j, a2);
        int b2 = this.a.b(a2, i);
        if (b > b2) {
            b = b2;
        }
        return this.a.a(a2, i, b) + ((long) this.a.h(j));
    }

    public DurationField getRangeDurationField() {
        return this.a.years();
    }

    public boolean isLeap(long j) {
        int a2 = this.a.a(j);
        boolean z = false;
        if (!this.a.e(a2)) {
            return false;
        }
        if (this.a.a(j, a2) == this.d) {
            z = true;
        }
        return z;
    }

    public int getLeapAmount(long j) {
        return isLeap(j) ? 1 : 0;
    }

    public DurationField getLeapDurationField() {
        return this.a.days();
    }

    public int getMaximumValue() {
        return this.c;
    }

    public long roundFloor(long j) {
        int a2 = this.a.a(j);
        return this.a.a(a2, this.a.a(j, a2));
    }

    public long remainder(long j) {
        return j - roundFloor(j);
    }
}
