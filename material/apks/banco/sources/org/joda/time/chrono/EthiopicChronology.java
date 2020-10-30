package org.joda.time.chrono;

import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.SkipDateTimeField;

public final class EthiopicChronology extends BasicFixedMonthChronology {
    public static final int EE = 1;
    private static final DateTimeField a = new BasicSingleEraDateTimeField("EE");
    private static final ConcurrentHashMap<DateTimeZone, EthiopicChronology[]> b = new ConcurrentHashMap<>();
    private static final EthiopicChronology c = getInstance(DateTimeZone.UTC);
    private static final long serialVersionUID = -5972804258688333942L;

    /* access modifiers changed from: 0000 */
    public int c() {
        return -292269337;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return 292272984;
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return 30962844000000L;
    }

    public static EthiopicChronology getInstanceUTC() {
        return c;
    }

    public static EthiopicChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone, int i) {
        EthiopicChronology ethiopicChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        EthiopicChronology[] ethiopicChronologyArr = (EthiopicChronology[]) b.get(dateTimeZone);
        if (ethiopicChronologyArr == null) {
            ethiopicChronologyArr = new EthiopicChronology[7];
            EthiopicChronology[] ethiopicChronologyArr2 = (EthiopicChronology[]) b.putIfAbsent(dateTimeZone, ethiopicChronologyArr);
            if (ethiopicChronologyArr2 != null) {
                ethiopicChronologyArr = ethiopicChronologyArr2;
            }
        }
        int i2 = i - 1;
        try {
            EthiopicChronology ethiopicChronology2 = ethiopicChronologyArr[i2];
            if (ethiopicChronology2 == null) {
                synchronized (ethiopicChronologyArr) {
                    ethiopicChronology2 = ethiopicChronologyArr[i2];
                    if (ethiopicChronology2 == null) {
                        if (dateTimeZone == DateTimeZone.UTC) {
                            EthiopicChronology ethiopicChronology3 = new EthiopicChronology(null, null, i);
                            DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, (Chronology) ethiopicChronology3);
                            ethiopicChronology = new EthiopicChronology(LimitChronology.getInstance(ethiopicChronology3, dateTime, null), null, i);
                        } else {
                            ethiopicChronology = new EthiopicChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                        }
                        ethiopicChronologyArr[i2] = ethiopicChronology;
                        ethiopicChronology2 = ethiopicChronology;
                    }
                }
            }
            return ethiopicChronology2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid min days in first week: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    EthiopicChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    private Object readResolve() {
        Chronology base = getBase();
        return getInstance(base == null ? DateTimeZone.UTC : base.getZone(), getMinimumDaysInFirstWeek());
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
        int i3 = i - 1963;
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
