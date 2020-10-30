package org.joda.time;

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.joda.time.chrono.ISOChronology;

public class DateTimeUtils {
    public static final MillisProvider SYSTEM_MILLIS_PROVIDER = new SystemMillisProvider();
    private static volatile MillisProvider a = SYSTEM_MILLIS_PROVIDER;
    private static final AtomicReference<Map<String, DateTimeZone>> b = new AtomicReference<>();

    static class FixedMillisProvider implements MillisProvider {
        private final long a;

        FixedMillisProvider(long j) {
            this.a = j;
        }

        public long getMillis() {
            return this.a;
        }
    }

    public interface MillisProvider {
        long getMillis();
    }

    static class OffsetMillisProvider implements MillisProvider {
        private final long a;

        OffsetMillisProvider(long j) {
            this.a = j;
        }

        public long getMillis() {
            return System.currentTimeMillis() + this.a;
        }
    }

    static class SystemMillisProvider implements MillisProvider {
        SystemMillisProvider() {
        }

        public long getMillis() {
            return System.currentTimeMillis();
        }
    }

    public static final long fromJulianDay(double d) {
        return (long) ((d - 2440587.5d) * 8.64E7d);
    }

    public static final double toJulianDay(long j) {
        return (((double) j) / 8.64E7d) + 2440587.5d;
    }

    protected DateTimeUtils() {
    }

    public static final long currentTimeMillis() {
        return a.getMillis();
    }

    public static final void setCurrentMillisSystem() {
        a();
        a = SYSTEM_MILLIS_PROVIDER;
    }

    public static final void setCurrentMillisFixed(long j) {
        a();
        a = new FixedMillisProvider(j);
    }

    public static final void setCurrentMillisOffset(long j) {
        a();
        if (j == 0) {
            a = SYSTEM_MILLIS_PROVIDER;
        } else {
            a = new OffsetMillisProvider(j);
        }
    }

    public static final void setCurrentMillisProvider(MillisProvider millisProvider) {
        if (millisProvider == null) {
            throw new IllegalArgumentException("The MillisProvider must not be null");
        }
        a();
        a = millisProvider;
    }

    private static void a() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("CurrentTime.setProvider"));
        }
    }

    public static final long getInstantMillis(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return currentTimeMillis();
        }
        return readableInstant.getMillis();
    }

    public static final Chronology getInstantChronology(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return ISOChronology.getInstance();
        }
        Chronology chronology = readableInstant.getChronology();
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final Chronology getIntervalChronology(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        Chronology chronology = readableInstant != null ? readableInstant.getChronology() : readableInstant2 != null ? readableInstant2.getChronology() : null;
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final Chronology getIntervalChronology(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ISOChronology.getInstance();
        }
        Chronology chronology = readableInterval.getChronology();
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final ReadableInterval getReadableInterval(ReadableInterval readableInterval) {
        if (readableInterval != null) {
            return readableInterval;
        }
        long currentTimeMillis = currentTimeMillis();
        return new Interval(currentTimeMillis, currentTimeMillis);
    }

    public static final Chronology getChronology(Chronology chronology) {
        return chronology == null ? ISOChronology.getInstance() : chronology;
    }

    public static final DateTimeZone getZone(DateTimeZone dateTimeZone) {
        return dateTimeZone == null ? DateTimeZone.getDefault() : dateTimeZone;
    }

    public static final PeriodType getPeriodType(PeriodType periodType) {
        return periodType == null ? PeriodType.standard() : periodType;
    }

    public static final long getDurationMillis(ReadableDuration readableDuration) {
        if (readableDuration == null) {
            return 0;
        }
        return readableDuration.getMillis();
    }

    public static final boolean isContiguous(ReadablePartial readablePartial) {
        if (readablePartial == null) {
            throw new IllegalArgumentException("Partial must not be null");
        }
        DurationFieldType durationFieldType = null;
        for (int i = 0; i < readablePartial.size(); i++) {
            DateTimeField field = readablePartial.getField(i);
            if (i > 0 && (field.getRangeDurationField() == null || field.getRangeDurationField().getType() != durationFieldType)) {
                return false;
            }
            durationFieldType = field.getDurationField().getType();
        }
        return true;
    }

    public static final DateFormatSymbols getDateFormatSymbols(Locale locale) {
        try {
            return (DateFormatSymbols) DateFormatSymbols.class.getMethod("getInstance", new Class[]{Locale.class}).invoke(null, new Object[]{locale});
        } catch (Exception unused) {
            return new DateFormatSymbols(locale);
        }
    }

    public static final Map<String, DateTimeZone> getDefaultTimeZoneNames() {
        Map<String, DateTimeZone> map = (Map) b.get();
        if (map != null) {
            return map;
        }
        Map<String, DateTimeZone> b2 = b();
        return !b.compareAndSet(null, b2) ? (Map) b.get() : b2;
    }

    public static final void setDefaultTimeZoneNames(Map<String, DateTimeZone> map) {
        b.set(Collections.unmodifiableMap(new HashMap(map)));
    }

    private static Map<String, DateTimeZone> b() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("UT", DateTimeZone.UTC);
        linkedHashMap.put("UTC", DateTimeZone.UTC);
        linkedHashMap.put("GMT", DateTimeZone.UTC);
        a(linkedHashMap, "EST", "America/New_York");
        a(linkedHashMap, "EDT", "America/New_York");
        a(linkedHashMap, "CST", "America/Chicago");
        a(linkedHashMap, "CDT", "America/Chicago");
        a(linkedHashMap, "MST", "America/Denver");
        a(linkedHashMap, "MDT", "America/Denver");
        a(linkedHashMap, "PST", "America/Los_Angeles");
        a(linkedHashMap, "PDT", "America/Los_Angeles");
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static void a(Map<String, DateTimeZone> map, String str, String str2) {
        try {
            map.put(str, DateTimeZone.forID(str2));
        } catch (RuntimeException unused) {
        }
    }

    public static final long toJulianDayNumber(long j) {
        return (long) Math.floor(toJulianDay(j) + 0.5d);
    }
}
