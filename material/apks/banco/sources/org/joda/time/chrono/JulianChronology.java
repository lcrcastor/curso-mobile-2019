package org.joda.time.chrono;

import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.SkipDateTimeField;

public final class JulianChronology extends BasicGJChronology {
    private static final JulianChronology a = getInstance(DateTimeZone.UTC);
    private static final ConcurrentHashMap<DateTimeZone, JulianChronology[]> b = new ConcurrentHashMap<>();
    private static final long serialVersionUID = -8731039522547897247L;

    /* access modifiers changed from: 0000 */
    public int c() {
        return -292269054;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return 292272992;
    }

    /* access modifiers changed from: 0000 */
    public boolean e(int i) {
        return (i & 3) == 0;
    }

    /* access modifiers changed from: 0000 */
    public long f() {
        return 31557600000L;
    }

    /* access modifiers changed from: 0000 */
    public long g() {
        return 15778800000L;
    }

    /* access modifiers changed from: 0000 */
    public long h() {
        return 2629800000L;
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return 31083663600000L;
    }

    static int i(int i) {
        if (i > 0) {
            return i;
        }
        if (i != 0) {
            return i + 1;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.year(), (Number) Integer.valueOf(i), (Number) null, (Number) null);
    }

    public static JulianChronology getInstanceUTC() {
        return a;
    }

    public static JulianChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static JulianChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static JulianChronology getInstance(DateTimeZone dateTimeZone, int i) {
        JulianChronology julianChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        JulianChronology[] julianChronologyArr = (JulianChronology[]) b.get(dateTimeZone);
        if (julianChronologyArr == null) {
            julianChronologyArr = new JulianChronology[7];
            JulianChronology[] julianChronologyArr2 = (JulianChronology[]) b.putIfAbsent(dateTimeZone, julianChronologyArr);
            if (julianChronologyArr2 != null) {
                julianChronologyArr = julianChronologyArr2;
            }
        }
        int i2 = i - 1;
        try {
            JulianChronology julianChronology2 = julianChronologyArr[i2];
            if (julianChronology2 == null) {
                synchronized (julianChronologyArr) {
                    julianChronology2 = julianChronologyArr[i2];
                    if (julianChronology2 == null) {
                        if (dateTimeZone == DateTimeZone.UTC) {
                            julianChronology = new JulianChronology(null, null, i);
                        } else {
                            julianChronology = new JulianChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                        }
                        julianChronologyArr[i2] = julianChronology;
                        julianChronology2 = julianChronology;
                    }
                }
            }
            return julianChronology2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid min days in first week: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    JulianChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    private Object readResolve() {
        Chronology base = getBase();
        int minimumDaysInFirstWeek = getMinimumDaysInFirstWeek();
        if (minimumDaysInFirstWeek == 0) {
            minimumDaysInFirstWeek = 4;
        }
        return getInstance(base == null ? DateTimeZone.UTC : base.getZone(), minimumDaysInFirstWeek);
    }

    public Chronology withUTC() {
        return a;
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        return getInstance(dateTimeZone);
    }

    /* access modifiers changed from: 0000 */
    public long b(int i, int i2, int i3) {
        return super.b(i(i), i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public long g(int i) {
        int i2;
        int i3 = i - 1968;
        if (i3 <= 0) {
            i2 = (i3 + 3) >> 2;
        } else {
            int i4 = i3 >> 2;
            i2 = !e(i) ? i4 + 1 : i4;
        }
        return (((((long) i3) * 365) + ((long) i2)) * 86400000) - 62035200000L;
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.year = new SkipDateTimeField(this, fields.year);
            fields.weekyear = new SkipDateTimeField(this, fields.weekyear);
        }
    }
}
