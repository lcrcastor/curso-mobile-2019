package org.joda.time.chrono;

import cz.msebera.android.httpclient.HttpStatus;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;

public final class GregorianChronology extends BasicGJChronology {
    private static final GregorianChronology a = getInstance(DateTimeZone.UTC);
    private static final ConcurrentHashMap<DateTimeZone, GregorianChronology[]> b = new ConcurrentHashMap<>();
    private static final long serialVersionUID = -861407383323710522L;

    /* access modifiers changed from: 0000 */
    public int c() {
        return -292275054;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        return 292278993;
    }

    /* access modifiers changed from: 0000 */
    public long f() {
        return 31556952000L;
    }

    /* access modifiers changed from: 0000 */
    public long g() {
        return 15778476000L;
    }

    /* access modifiers changed from: 0000 */
    public long h() {
        return 2629746000L;
    }

    /* access modifiers changed from: 0000 */
    public long i() {
        return 31083597720000L;
    }

    public static GregorianChronology getInstanceUTC() {
        return a;
    }

    public static GregorianChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static GregorianChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static GregorianChronology getInstance(DateTimeZone dateTimeZone, int i) {
        GregorianChronology gregorianChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        GregorianChronology[] gregorianChronologyArr = (GregorianChronology[]) b.get(dateTimeZone);
        if (gregorianChronologyArr == null) {
            gregorianChronologyArr = new GregorianChronology[7];
            GregorianChronology[] gregorianChronologyArr2 = (GregorianChronology[]) b.putIfAbsent(dateTimeZone, gregorianChronologyArr);
            if (gregorianChronologyArr2 != null) {
                gregorianChronologyArr = gregorianChronologyArr2;
            }
        }
        int i2 = i - 1;
        try {
            GregorianChronology gregorianChronology2 = gregorianChronologyArr[i2];
            if (gregorianChronology2 == null) {
                synchronized (gregorianChronologyArr) {
                    gregorianChronology2 = gregorianChronologyArr[i2];
                    if (gregorianChronology2 == null) {
                        if (dateTimeZone == DateTimeZone.UTC) {
                            gregorianChronology = new GregorianChronology(null, null, i);
                        } else {
                            gregorianChronology = new GregorianChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                        }
                        gregorianChronologyArr[i2] = gregorianChronology;
                        gregorianChronology2 = gregorianChronology;
                    }
                }
            }
            return gregorianChronology2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid min days in first week: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private GregorianChronology(Chronology chronology, Object obj, int i) {
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

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean e(int i) {
        return (i & 3) == 0 && (i % 100 != 0 || i % HttpStatus.SC_BAD_REQUEST == 0);
    }

    /* access modifiers changed from: 0000 */
    public long g(int i) {
        int i2;
        int i3 = i / 100;
        if (i < 0) {
            i2 = ((((i + 3) >> 2) - i3) + ((i3 + 3) >> 2)) - 1;
        } else {
            i2 = ((i >> 2) - i3) + (i3 >> 2);
            if (e(i)) {
                i2--;
            }
        }
        return ((((long) i) * 365) + ((long) (i2 - 719527))) * 86400000;
    }
}
