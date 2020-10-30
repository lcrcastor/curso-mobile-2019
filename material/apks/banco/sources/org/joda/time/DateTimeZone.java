package org.joda.time;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.chrono.BaseChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.FormatUtils;
import org.joda.time.tz.DefaultNameProvider;
import org.joda.time.tz.FixedDateTimeZone;
import org.joda.time.tz.NameProvider;
import org.joda.time.tz.Provider;

public abstract class DateTimeZone implements Serializable {
    public static final DateTimeZone UTC = UTCDateTimeZone.a;
    private static final AtomicReference<Provider> a = new AtomicReference<>();
    private static final AtomicReference<NameProvider> b = new AtomicReference<>();
    private static final AtomicReference<DateTimeZone> c = new AtomicReference<>();
    private static final long serialVersionUID = 5546345482340108586L;
    private final String d;

    static final class LazyInit {
        static final Map<String, String> a = b();
        static final DateTimeFormatter b = a();

        LazyInit() {
        }

        private static DateTimeFormatter a() {
            return new DateTimeFormatterBuilder().appendTimeZoneOffset(null, true, 2, 4).toFormatter().withChronology(new BaseChronology() {
                private static final long serialVersionUID = -3128740902654445468L;

                public DateTimeZone getZone() {
                    return null;
                }

                public Chronology withUTC() {
                    return this;
                }

                public Chronology withZone(DateTimeZone dateTimeZone) {
                    return this;
                }

                public String toString() {
                    return getClass().getName();
                }
            });
        }

        private static Map<String, String> b() {
            HashMap hashMap = new HashMap();
            hashMap.put("GMT", "UTC");
            hashMap.put("WET", "WET");
            hashMap.put("CET", "CET");
            hashMap.put("MET", "CET");
            hashMap.put("ECT", "CET");
            hashMap.put("EET", "EET");
            hashMap.put("MIT", "Pacific/Apia");
            hashMap.put("HST", "Pacific/Honolulu");
            hashMap.put("AST", "America/Anchorage");
            hashMap.put("PST", "America/Los_Angeles");
            hashMap.put("MST", "America/Denver");
            hashMap.put("PNT", "America/Phoenix");
            hashMap.put("CST", "America/Chicago");
            hashMap.put("EST", "America/New_York");
            hashMap.put("IET", "America/Indiana/Indianapolis");
            hashMap.put("PRT", "America/Puerto_Rico");
            hashMap.put("CNT", "America/St_Johns");
            hashMap.put("AGT", "America/Argentina/Buenos_Aires");
            hashMap.put("BET", "America/Sao_Paulo");
            hashMap.put("ART", "Africa/Cairo");
            hashMap.put("CAT", "Africa/Harare");
            hashMap.put("EAT", "Africa/Addis_Ababa");
            hashMap.put("NET", "Asia/Yerevan");
            hashMap.put("PLT", "Asia/Karachi");
            hashMap.put("IST", "Asia/Kolkata");
            hashMap.put("BST", "Asia/Dhaka");
            hashMap.put("VST", "Asia/Ho_Chi_Minh");
            hashMap.put("CTT", "Asia/Shanghai");
            hashMap.put("JST", "Asia/Tokyo");
            hashMap.put("ACT", "Australia/Darwin");
            hashMap.put("AET", "Australia/Sydney");
            hashMap.put("SST", "Pacific/Guadalcanal");
            hashMap.put("NST", "Pacific/Auckland");
            return Collections.unmodifiableMap(hashMap);
        }
    }

    static final class Stub implements Serializable {
        private static final long serialVersionUID = -6471952376487863581L;
        private transient String a;

        Stub(String str) {
            this.a = str;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeUTF(this.a);
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = objectInputStream.readUTF();
        }

        private Object readResolve() {
            return DateTimeZone.forID(this.a);
        }
    }

    public abstract boolean equals(Object obj);

    public abstract String getNameKey(long j);

    public abstract int getOffset(long j);

    public abstract int getStandardOffset(long j);

    public abstract boolean isFixed();

    public abstract long nextTransition(long j);

    public abstract long previousTransition(long j);

    public static DateTimeZone getDefault() {
        DateTimeZone dateTimeZone = (DateTimeZone) c.get();
        if (dateTimeZone != null) {
            return dateTimeZone;
        }
        try {
            String property = System.getProperty("user.timezone");
            if (property != null) {
                dateTimeZone = forID(property);
            }
        } catch (RuntimeException unused) {
        }
        if (dateTimeZone == null) {
            try {
                dateTimeZone = forTimeZone(TimeZone.getDefault());
            } catch (IllegalArgumentException unused2) {
            }
        }
        if (dateTimeZone == null) {
            dateTimeZone = UTC;
        }
        return !c.compareAndSet(null, dateTimeZone) ? (DateTimeZone) c.get() : dateTimeZone;
    }

    public static void setDefault(DateTimeZone dateTimeZone) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setDefault"));
        }
        if (dateTimeZone == null) {
            throw new IllegalArgumentException("The datetime zone must not be null");
        }
        c.set(dateTimeZone);
    }

    @FromString
    public static DateTimeZone forID(String str) {
        if (str == null) {
            return getDefault();
        }
        if (str.equals("UTC")) {
            return UTC;
        }
        DateTimeZone zone = getProvider().getZone(str);
        if (zone != null) {
            return zone;
        }
        if (str.startsWith(Constants.SYMBOL_POSITIVE) || str.startsWith("-")) {
            int b2 = b(str);
            if (((long) b2) == 0) {
                return UTC;
            }
            return a(a(b2), b2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The datetime zone id '");
        sb.append(str);
        sb.append("' is not recognised");
        throw new IllegalArgumentException(sb.toString());
    }

    public static DateTimeZone forOffsetHours(int i) {
        return forOffsetHoursMinutes(i, 0);
    }

    public static DateTimeZone forOffsetHoursMinutes(int i, int i2) {
        int i3;
        if (i == 0 && i2 == 0) {
            return UTC;
        }
        if (i < -23 || i > 23) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hours out of range: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < -59 || i2 > 59) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Minutes out of range: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i <= 0 || i2 >= 0) {
            int i4 = i * 60;
            if (i4 < 0) {
                try {
                    i3 = i4 - Math.abs(i2);
                } catch (ArithmeticException unused) {
                    throw new IllegalArgumentException("Offset is too large");
                }
            } else {
                i3 = i4 + i2;
            }
            return forOffsetMillis(FieldUtils.safeMultiply(i3, (int) DateTimeConstants.MILLIS_PER_MINUTE));
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Positive hours must not have negative minutes: ");
            sb3.append(i2);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public static DateTimeZone forOffsetMillis(int i) {
        if (i >= -86399999 && i <= 86399999) {
            return a(a(i), i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Millis out of range: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public static DateTimeZone forTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            return getDefault();
        }
        String id2 = timeZone.getID();
        if (id2 == null) {
            throw new IllegalArgumentException("The TimeZone id must not be null");
        } else if (id2.equals("UTC")) {
            return UTC;
        } else {
            DateTimeZone dateTimeZone = null;
            String a2 = a(id2);
            Provider provider = getProvider();
            if (a2 != null) {
                dateTimeZone = provider.getZone(a2);
            }
            if (dateTimeZone == null) {
                dateTimeZone = provider.getZone(id2);
            }
            if (dateTimeZone != null) {
                return dateTimeZone;
            }
            if (a2 != null || (!id2.startsWith("GMT+") && !id2.startsWith("GMT-"))) {
                StringBuilder sb = new StringBuilder();
                sb.append("The datetime zone id '");
                sb.append(id2);
                sb.append("' is not recognised");
                throw new IllegalArgumentException(sb.toString());
            }
            int b2 = b(id2.substring(3));
            if (((long) b2) == 0) {
                return UTC;
            }
            return a(a(b2), b2);
        }
    }

    private static DateTimeZone a(String str, int i) {
        if (i == 0) {
            return UTC;
        }
        return new FixedDateTimeZone(str, null, i, i);
    }

    public static Set<String> getAvailableIDs() {
        return getProvider().getAvailableIDs();
    }

    public static Provider getProvider() {
        Provider provider = (Provider) a.get();
        if (provider != null) {
            return provider;
        }
        Provider a2 = a();
        return !a.compareAndSet(null, a2) ? (Provider) a.get() : a2;
    }

    public static void setProvider(Provider provider) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setProvider"));
        }
        if (provider == null) {
            provider = a();
        } else {
            a(provider);
        }
        a.set(provider);
    }

    private static Provider a(Provider provider) {
        Set availableIDs = provider.getAvailableIDs();
        if (availableIDs == null || availableIDs.size() == 0) {
            throw new IllegalArgumentException("The provider doesn't have any available ids");
        } else if (!availableIDs.contains("UTC")) {
            throw new IllegalArgumentException("The provider doesn't support UTC");
        } else if (UTC.equals(provider.getZone("UTC"))) {
            return provider;
        } else {
            throw new IllegalArgumentException("Invalid UTC zone provided");
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x003c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.joda.time.tz.Provider a() {
        /*
            java.lang.String r0 = "org.joda.time.DateTimeZone.Provider"
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ SecurityException -> 0x001e }
            if (r0 == 0) goto L_0x001e
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Exception -> 0x0017 }
            java.lang.Object r0 = r0.newInstance()     // Catch:{ Exception -> 0x0017 }
            org.joda.time.tz.Provider r0 = (org.joda.time.tz.Provider) r0     // Catch:{ Exception -> 0x0017 }
            org.joda.time.tz.Provider r0 = a(r0)     // Catch:{ Exception -> 0x0017 }
            return r0
        L_0x0017:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ SecurityException -> 0x001e }
            r1.<init>(r0)     // Catch:{ SecurityException -> 0x001e }
            throw r1     // Catch:{ SecurityException -> 0x001e }
        L_0x001e:
            java.lang.String r0 = "org.joda.time.DateTimeZone.Folder"
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ SecurityException -> 0x003c }
            if (r0 == 0) goto L_0x003c
            org.joda.time.tz.ZoneInfoProvider r1 = new org.joda.time.tz.ZoneInfoProvider     // Catch:{ Exception -> 0x0035 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0035 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0035 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0035 }
            org.joda.time.tz.Provider r0 = a(r1)     // Catch:{ Exception -> 0x0035 }
            return r0
        L_0x0035:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ SecurityException -> 0x003c }
            r1.<init>(r0)     // Catch:{ SecurityException -> 0x003c }
            throw r1     // Catch:{ SecurityException -> 0x003c }
        L_0x003c:
            org.joda.time.tz.ZoneInfoProvider r0 = new org.joda.time.tz.ZoneInfoProvider     // Catch:{ Exception -> 0x0048 }
            java.lang.String r1 = "org/joda/time/tz/data"
            r0.<init>(r1)     // Catch:{ Exception -> 0x0048 }
            org.joda.time.tz.Provider r0 = a(r0)     // Catch:{ Exception -> 0x0048 }
            return r0
        L_0x0048:
            r0 = move-exception
            r0.printStackTrace()
            org.joda.time.tz.UTCProvider r0 = new org.joda.time.tz.UTCProvider
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.DateTimeZone.a():org.joda.time.tz.Provider");
    }

    public static NameProvider getNameProvider() {
        NameProvider nameProvider = (NameProvider) b.get();
        if (nameProvider != null) {
            return nameProvider;
        }
        NameProvider b2 = b();
        return !b.compareAndSet(null, b2) ? (NameProvider) b.get() : b2;
    }

    public static void setNameProvider(NameProvider nameProvider) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setNameProvider"));
        }
        if (nameProvider == null) {
            nameProvider = b();
        }
        b.set(nameProvider);
    }

    private static NameProvider b() {
        NameProvider nameProvider = null;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
            if (property != null) {
                nameProvider = (NameProvider) Class.forName(property).newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (SecurityException unused) {
        }
        return nameProvider == null ? new DefaultNameProvider() : nameProvider;
    }

    private static String a(String str) {
        return (String) LazyInit.a.get(str);
    }

    private static int b(String str) {
        return -((int) LazyInit.b.parseMillis(str));
    }

    private static String a(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        if (i >= 0) {
            stringBuffer.append('+');
        } else {
            stringBuffer.append('-');
            i = -i;
        }
        int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
        FormatUtils.appendPaddedInteger(stringBuffer, i2, 2);
        int i3 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
        int i4 = i3 / DateTimeConstants.MILLIS_PER_MINUTE;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i4, 2);
        int i5 = i3 - (i4 * DateTimeConstants.MILLIS_PER_MINUTE);
        if (i5 == 0) {
            return stringBuffer.toString();
        }
        int i6 = i5 / 1000;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i6, 2);
        int i7 = i5 - (i6 * 1000);
        if (i7 == 0) {
            return stringBuffer.toString();
        }
        stringBuffer.append('.');
        FormatUtils.appendPaddedInteger(stringBuffer, i7, 3);
        return stringBuffer.toString();
    }

    protected DateTimeZone(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.d = str;
    }

    @ToString
    public final String getID() {
        return this.d;
    }

    public final String getShortName(long j) {
        return getShortName(j, null);
    }

    public String getShortName(long j, Locale locale) {
        String str;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j);
        if (nameKey == null) {
            return this.d;
        }
        NameProvider nameProvider = getNameProvider();
        if (nameProvider instanceof DefaultNameProvider) {
            str = ((DefaultNameProvider) nameProvider).getShortName(locale, this.d, nameKey, isStandardOffset(j));
        } else {
            str = nameProvider.getShortName(locale, this.d, nameKey);
        }
        if (str != null) {
            return str;
        }
        return a(getOffset(j));
    }

    public final String getName(long j) {
        return getName(j, null);
    }

    public String getName(long j, Locale locale) {
        String str;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j);
        if (nameKey == null) {
            return this.d;
        }
        NameProvider nameProvider = getNameProvider();
        if (nameProvider instanceof DefaultNameProvider) {
            str = ((DefaultNameProvider) nameProvider).getName(locale, this.d, nameKey, isStandardOffset(j));
        } else {
            str = nameProvider.getName(locale, this.d, nameKey);
        }
        if (str != null) {
            return str;
        }
        return a(getOffset(j));
    }

    public final int getOffset(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return getOffset(DateTimeUtils.currentTimeMillis());
        }
        return getOffset(readableInstant.getMillis());
    }

    public boolean isStandardOffset(long j) {
        return getOffset(j) == getStandardOffset(j);
    }

    public int getOffsetFromLocal(long j) {
        int offset = getOffset(j);
        long j2 = j - ((long) offset);
        int offset2 = getOffset(j2);
        if (offset != offset2) {
            if (offset - offset2 < 0) {
                long nextTransition = nextTransition(j2);
                if (nextTransition == j2) {
                    nextTransition = Long.MAX_VALUE;
                }
                long j3 = j - ((long) offset2);
                long nextTransition2 = nextTransition(j3);
                if (nextTransition2 == j3) {
                    nextTransition2 = Long.MAX_VALUE;
                }
                if (nextTransition != nextTransition2) {
                    return offset;
                }
            }
        } else if (offset >= 0) {
            long previousTransition = previousTransition(j2);
            if (previousTransition < j2) {
                int offset3 = getOffset(previousTransition);
                if (j2 - previousTransition <= ((long) (offset3 - offset))) {
                    return offset3;
                }
            }
        }
        return offset2;
    }

    public long convertUTCToLocal(long j) {
        long offset = (long) getOffset(j);
        long j2 = j + offset;
        if ((j ^ j2) >= 0 || (j ^ offset) < 0) {
            return j2;
        }
        throw new ArithmeticException("Adding time zone offset caused overflow");
    }

    public long convertLocalToUTC(long j, boolean z, long j2) {
        int offset = getOffset(j2);
        long j3 = j - ((long) offset);
        if (getOffset(j3) == offset) {
            return j3;
        }
        return convertLocalToUTC(j, z);
    }

    public long convertLocalToUTC(long j, boolean z) {
        long j2;
        int offset = getOffset(j);
        long j3 = j - ((long) offset);
        int offset2 = getOffset(j3);
        if (offset != offset2 && (z || offset < 0)) {
            long nextTransition = nextTransition(j3);
            int i = (nextTransition > j3 ? 1 : (nextTransition == j3 ? 0 : -1));
            long j4 = Long.MAX_VALUE;
            if (i == 0) {
                nextTransition = Long.MAX_VALUE;
            }
            long j5 = j - ((long) offset2);
            long nextTransition2 = nextTransition(j5);
            if (nextTransition2 != j5) {
                j4 = nextTransition2;
            }
            if (nextTransition != j4) {
                if (z) {
                    throw new IllegalInstantException(j, getID());
                }
                long j6 = (long) offset;
                j2 = j - j6;
                if ((j ^ j2) < 0 || (j ^ j6) >= 0) {
                    return j2;
                }
                throw new ArithmeticException("Subtracting time zone offset caused overflow");
            }
        }
        offset = offset2;
        long j62 = (long) offset;
        j2 = j - j62;
        if ((j ^ j2) < 0) {
        }
        return j2;
    }

    public long getMillisKeepLocal(DateTimeZone dateTimeZone, long j) {
        if (dateTimeZone == null) {
            dateTimeZone = getDefault();
        }
        DateTimeZone dateTimeZone2 = dateTimeZone;
        if (dateTimeZone2 == this) {
            return j;
        }
        return dateTimeZone2.convertLocalToUTC(convertUTCToLocal(j), false, j);
    }

    public boolean isLocalDateTimeGap(LocalDateTime localDateTime) {
        if (isFixed()) {
            return false;
        }
        try {
            localDateTime.toDateTime(this);
            return false;
        } catch (IllegalInstantException unused) {
            return true;
        }
    }

    public long adjustOffset(long j, boolean z) {
        long j2 = j - 10800000;
        long offset = (long) getOffset(j2);
        long offset2 = (long) getOffset(j + 10800000);
        if (offset <= offset2) {
            return j;
        }
        long j3 = offset - offset2;
        long nextTransition = nextTransition(j2);
        long j4 = nextTransition - j3;
        long j5 = nextTransition + j3;
        if (j < j4 || j >= j5) {
            return j;
        }
        if (j - j4 >= j3) {
            if (!z) {
                j -= j3;
            }
            return j;
        }
        if (z) {
            j += j3;
        }
        return j;
    }

    public TimeZone toTimeZone() {
        return TimeZone.getTimeZone(this.d);
    }

    public int hashCode() {
        return getID().hashCode() + 57;
    }

    public String toString() {
        return getID();
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() {
        return new Stub(this.d);
    }
}
