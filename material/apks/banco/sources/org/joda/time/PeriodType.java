package org.joda.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.field.FieldUtils;

public class PeriodType implements Serializable {
    static int a = 0;
    static int b = 1;
    static int c = 2;
    static int d = 3;
    static int e = 4;
    static int f = 5;
    static int g = 6;
    static int h = 7;
    private static final Map<PeriodType, Object> i = new HashMap(32);
    private static PeriodType j = null;
    private static PeriodType k = null;
    private static PeriodType l = null;
    private static PeriodType m = null;
    private static PeriodType n = null;
    private static PeriodType o = null;
    private static PeriodType p = null;
    private static PeriodType q = null;
    private static PeriodType r = null;
    private static PeriodType s = null;
    private static final long serialVersionUID = 2274324892792009998L;
    private static PeriodType t;
    private static PeriodType u;
    private static PeriodType v;
    private static PeriodType w;
    private static PeriodType x;
    private static PeriodType y;
    private static PeriodType z;
    private final String A;
    private final DurationFieldType[] B;
    private final int[] C;

    public static PeriodType standard() {
        PeriodType periodType = j;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Standard", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        j = periodType2;
        return periodType2;
    }

    public static PeriodType yearMonthDayTime() {
        PeriodType periodType = k;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearMonthDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, 1, -1, 2, 3, 4, 5, 6});
        k = periodType2;
        return periodType2;
    }

    public static PeriodType yearMonthDay() {
        PeriodType periodType = l;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearMonthDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days()}, new int[]{0, 1, -1, 2, -1, -1, -1, -1});
        l = periodType2;
        return periodType2;
    }

    public static PeriodType yearWeekDayTime() {
        PeriodType periodType = m;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearWeekDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, -1, 1, 2, 3, 4, 5, 6});
        m = periodType2;
        return periodType2;
    }

    public static PeriodType yearWeekDay() {
        PeriodType periodType = n;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearWeekDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days()}, new int[]{0, -1, 1, 2, -1, -1, -1, -1});
        n = periodType2;
        return periodType2;
    }

    public static PeriodType yearDayTime() {
        PeriodType periodType = o;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, -1, -1, 1, 2, 3, 4, 5});
        o = periodType2;
        return periodType2;
    }

    public static PeriodType yearDay() {
        PeriodType periodType = p;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("YearDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.days()}, new int[]{0, -1, -1, 1, -1, -1, -1, -1});
        p = periodType2;
        return periodType2;
    }

    public static PeriodType dayTime() {
        PeriodType periodType = q;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("DayTime", new DurationFieldType[]{DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{-1, -1, -1, 0, 1, 2, 3, 4});
        q = periodType2;
        return periodType2;
    }

    public static PeriodType time() {
        PeriodType periodType = r;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Time", new DurationFieldType[]{DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{-1, -1, -1, -1, 0, 1, 2, 3});
        r = periodType2;
        return periodType2;
    }

    public static PeriodType years() {
        PeriodType periodType = s;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Years", new DurationFieldType[]{DurationFieldType.years()}, new int[]{0, -1, -1, -1, -1, -1, -1, -1});
        s = periodType2;
        return periodType2;
    }

    public static PeriodType months() {
        PeriodType periodType = t;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Months", new DurationFieldType[]{DurationFieldType.months()}, new int[]{-1, 0, -1, -1, -1, -1, -1, -1});
        t = periodType2;
        return periodType2;
    }

    public static PeriodType weeks() {
        PeriodType periodType = u;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Weeks", new DurationFieldType[]{DurationFieldType.weeks()}, new int[]{-1, -1, 0, -1, -1, -1, -1, -1});
        u = periodType2;
        return periodType2;
    }

    public static PeriodType days() {
        PeriodType periodType = v;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Days", new DurationFieldType[]{DurationFieldType.days()}, new int[]{-1, -1, -1, 0, -1, -1, -1, -1});
        v = periodType2;
        return periodType2;
    }

    public static PeriodType hours() {
        PeriodType periodType = w;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Hours", new DurationFieldType[]{DurationFieldType.hours()}, new int[]{-1, -1, -1, -1, 0, -1, -1, -1});
        w = periodType2;
        return periodType2;
    }

    public static PeriodType minutes() {
        PeriodType periodType = x;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Minutes", new DurationFieldType[]{DurationFieldType.minutes()}, new int[]{-1, -1, -1, -1, -1, 0, -1, -1});
        x = periodType2;
        return periodType2;
    }

    public static PeriodType seconds() {
        PeriodType periodType = y;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Seconds", new DurationFieldType[]{DurationFieldType.seconds()}, new int[]{-1, -1, -1, -1, -1, -1, 0, -1});
        y = periodType2;
        return periodType2;
    }

    public static PeriodType millis() {
        PeriodType periodType = z;
        if (periodType != null) {
            return periodType;
        }
        PeriodType periodType2 = new PeriodType("Millis", new DurationFieldType[]{DurationFieldType.millis()}, new int[]{-1, -1, -1, -1, -1, -1, -1, 0});
        z = periodType2;
        return periodType2;
    }

    public static synchronized PeriodType forFields(DurationFieldType[] durationFieldTypeArr) {
        synchronized (PeriodType.class) {
            if (durationFieldTypeArr != null) {
                if (durationFieldTypeArr.length != 0) {
                    for (DurationFieldType durationFieldType : durationFieldTypeArr) {
                        if (durationFieldType == null) {
                            throw new IllegalArgumentException("Types array must not contain null");
                        }
                    }
                    Map<PeriodType, Object> map = i;
                    if (map.isEmpty()) {
                        map.put(standard(), standard());
                        map.put(yearMonthDayTime(), yearMonthDayTime());
                        map.put(yearMonthDay(), yearMonthDay());
                        map.put(yearWeekDayTime(), yearWeekDayTime());
                        map.put(yearWeekDay(), yearWeekDay());
                        map.put(yearDayTime(), yearDayTime());
                        map.put(yearDay(), yearDay());
                        map.put(dayTime(), dayTime());
                        map.put(time(), time());
                        map.put(years(), years());
                        map.put(months(), months());
                        map.put(weeks(), weeks());
                        map.put(days(), days());
                        map.put(hours(), hours());
                        map.put(minutes(), minutes());
                        map.put(seconds(), seconds());
                        map.put(millis(), millis());
                    }
                    PeriodType periodType = new PeriodType(null, durationFieldTypeArr, null);
                    Object obj = map.get(periodType);
                    if (obj instanceof PeriodType) {
                        PeriodType periodType2 = (PeriodType) obj;
                        return periodType2;
                    } else if (obj != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("PeriodType does not support fields: ");
                        sb.append(obj);
                        throw new IllegalArgumentException(sb.toString());
                    } else {
                        PeriodType standard = standard();
                        ArrayList arrayList = new ArrayList(Arrays.asList(durationFieldTypeArr));
                        if (!arrayList.remove(DurationFieldType.years())) {
                            standard = standard.withYearsRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.months())) {
                            standard = standard.withMonthsRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.weeks())) {
                            standard = standard.withWeeksRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.days())) {
                            standard = standard.withDaysRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.hours())) {
                            standard = standard.withHoursRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.minutes())) {
                            standard = standard.withMinutesRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.seconds())) {
                            standard = standard.withSecondsRemoved();
                        }
                        if (!arrayList.remove(DurationFieldType.millis())) {
                            standard = standard.withMillisRemoved();
                        }
                        if (arrayList.size() > 0) {
                            map.put(periodType, arrayList);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("PeriodType does not support fields: ");
                            sb2.append(arrayList);
                            throw new IllegalArgumentException(sb2.toString());
                        }
                        PeriodType periodType3 = new PeriodType(null, standard.B, null);
                        PeriodType periodType4 = (PeriodType) map.get(periodType3);
                        if (periodType4 != null) {
                            map.put(periodType3, periodType4);
                            return periodType4;
                        }
                        map.put(periodType3, standard);
                        return standard;
                    }
                }
            }
            throw new IllegalArgumentException("Types array must not be null or empty");
        }
    }

    protected PeriodType(String str, DurationFieldType[] durationFieldTypeArr, int[] iArr) {
        this.A = str;
        this.B = durationFieldTypeArr;
        this.C = iArr;
    }

    public String getName() {
        return this.A;
    }

    public int size() {
        return this.B.length;
    }

    public DurationFieldType getFieldType(int i2) {
        return this.B[i2];
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        return indexOf(durationFieldType) >= 0;
    }

    public int indexOf(DurationFieldType durationFieldType) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.B[i2] == durationFieldType) {
                return i2;
            }
        }
        return -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PeriodType[");
        sb.append(getName());
        sb.append("]");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public int a(ReadablePeriod readablePeriod, int i2) {
        int i3 = this.C[i2];
        if (i3 == -1) {
            return 0;
        }
        return readablePeriod.getValue(i3);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ReadablePeriod readablePeriod, int i2, int[] iArr, int i3) {
        int i4 = this.C[i2];
        if (i4 == -1) {
            throw new UnsupportedOperationException("Field is not supported");
        }
        iArr[i4] = i3;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(ReadablePeriod readablePeriod, int i2, int[] iArr, int i3) {
        if (i3 == 0) {
            return false;
        }
        int i4 = this.C[i2];
        if (i4 == -1) {
            throw new UnsupportedOperationException("Field is not supported");
        }
        iArr[i4] = FieldUtils.safeAdd(iArr[i4], i3);
        return true;
    }

    public PeriodType withYearsRemoved() {
        return a(0, "NoYears");
    }

    public PeriodType withMonthsRemoved() {
        return a(1, "NoMonths");
    }

    public PeriodType withWeeksRemoved() {
        return a(2, "NoWeeks");
    }

    public PeriodType withDaysRemoved() {
        return a(3, "NoDays");
    }

    public PeriodType withHoursRemoved() {
        return a(4, "NoHours");
    }

    public PeriodType withMinutesRemoved() {
        return a(5, "NoMinutes");
    }

    public PeriodType withSecondsRemoved() {
        return a(6, "NoSeconds");
    }

    public PeriodType withMillisRemoved() {
        return a(7, "NoMillis");
    }

    private PeriodType a(int i2, String str) {
        int i3 = this.C[i2];
        if (i3 == -1) {
            return this;
        }
        DurationFieldType[] durationFieldTypeArr = new DurationFieldType[(size() - 1)];
        for (int i4 = 0; i4 < this.B.length; i4++) {
            if (i4 < i3) {
                durationFieldTypeArr[i4] = this.B[i4];
            } else if (i4 > i3) {
                durationFieldTypeArr[i4 - 1] = this.B[i4];
            }
        }
        int[] iArr = new int[8];
        for (int i5 = 0; i5 < iArr.length; i5++) {
            if (i5 < i2) {
                iArr[i5] = this.C[i5];
            } else if (i5 > i2) {
                iArr[i5] = this.C[i5] == -1 ? -1 : this.C[i5] - 1;
            } else {
                iArr[i5] = -1;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(str);
        return new PeriodType(sb.toString(), durationFieldTypeArr, iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PeriodType)) {
            return false;
        }
        return Arrays.equals(this.B, ((PeriodType) obj).B);
    }

    public int hashCode() {
        int i2 = 0;
        for (DurationFieldType hashCode : this.B) {
            i2 += hashCode.hashCode();
        }
        return i2;
    }
}
