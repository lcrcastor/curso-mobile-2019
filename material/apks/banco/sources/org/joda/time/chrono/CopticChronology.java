package org.joda.time.chrono;

import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.SkipDateTimeField;

public final class CopticChronology extends BasicFixedMonthChronology {
    public static final int AM = 1;
    private static final DateTimeField a = new BasicSingleEraDateTimeField("AM");
    private static final ConcurrentHashMap<DateTimeZone, CopticChronology[]> b = new ConcurrentHashMap<>();
    private static final CopticChronology c = getInstance(DateTimeZone.UTC);
    private static final long serialVersionUID = -5972804258688333942L;

    /* access modifiers changed from: 0000 */
    public int c() {
        return -292269337;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return 292272708;
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return 26607895200000L;
    }

    public static CopticChronology getInstanceUTC() {
        return c;
    }

    public static CopticChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static CopticChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static CopticChronology getInstance(DateTimeZone dateTimeZone, int i) {
        CopticChronology copticChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        CopticChronology[] copticChronologyArr = (CopticChronology[]) b.get(dateTimeZone);
        if (copticChronologyArr == null) {
            copticChronologyArr = new CopticChronology[7];
            CopticChronology[] copticChronologyArr2 = (CopticChronology[]) b.putIfAbsent(dateTimeZone, copticChronologyArr);
            if (copticChronologyArr2 != null) {
                copticChronologyArr = copticChronologyArr2;
            }
        }
        int i2 = i - 1;
        try {
            CopticChronology copticChronology2 = copticChronologyArr[i2];
            if (copticChronology2 == null) {
                synchronized (copticChronologyArr) {
                    copticChronology2 = copticChronologyArr[i2];
                    if (copticChronology2 == null) {
                        if (dateTimeZone == DateTimeZone.UTC) {
                            CopticChronology copticChronology3 = new CopticChronology(null, null, i);
                            DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, (Chronology) copticChronology3);
                            copticChronology = new CopticChronology(LimitChronology.getInstance(copticChronology3, dateTime, null), null, i);
                        } else {
                            copticChronology = new CopticChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                        }
                        copticChronologyArr[i2] = copticChronology;
                        copticChronology2 = copticChronology;
                    }
                }
            }
            return copticChronology2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid min days in first week: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    CopticChronology(Chronology chronology, Object obj, int i) {
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
        return c;
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
    public boolean j(long j) {
        return dayOfMonth().get(j) == 6 && monthOfYear().isLeap(j);
    }

    /* access modifiers changed from: 0000 */
    public long g(int i) {
        int i2;
        int i3 = i - 1687;
        if (i3 <= 0) {
            i2 = (i3 + 3) >> 2;
        } else {
            int i4 = i3 >> 2;
            i2 = !e(i) ? i4 + 1 : i4;
        }
        return (((((long) i3) * 365) + ((long) i2)) * 86400000) + 21859200000L;
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.year = new SkipDateTimeField(this, fields.year);
            fields.weekyear = new SkipDateTimeField(this, fields.weekyear);
            fields.era = a;
            fields.monthOfYear = new BasicMonthOfYearDateTimeField(this, 13);
            fields.months = fields.monthOfYear.getDurationField();
        }
    }
}
