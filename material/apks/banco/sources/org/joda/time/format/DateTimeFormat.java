package org.joda.time.format;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.bouncycastle.asn1.eac.EACTags;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

public class DateTimeFormat {
    private static final ConcurrentHashMap<String, DateTimeFormatter> a = new ConcurrentHashMap<>();
    private static final AtomicReferenceArray<DateTimeFormatter> b = new AtomicReferenceArray<>(25);

    static class StyleFormatter implements InternalParser, InternalPrinter {
        private static final ConcurrentHashMap<StyleFormatterCacheKey, DateTimeFormatter> a = new ConcurrentHashMap<>();
        private final int b;
        private final int c;
        private final int d;

        public int estimateParsedLength() {
            return 40;
        }

        public int estimatePrintedLength() {
            return 40;
        }

        StyleFormatter(int i, int i2, int i3) {
            this.b = i;
            this.c = i2;
            this.d = i3;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            Locale locale2 = locale;
            b(locale2).a().a(appendable, j, chronology, i, dateTimeZone, locale2);
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            b(locale).a().a(appendable, readablePartial, locale);
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            return b(dateTimeParserBucket.getLocale()).b().a(dateTimeParserBucket, charSequence, i);
        }

        private DateTimeFormatter b(Locale locale) {
            if (locale == null) {
                locale = Locale.getDefault();
            }
            StyleFormatterCacheKey styleFormatterCacheKey = new StyleFormatterCacheKey(this.d, this.b, this.c, locale);
            DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) a.get(styleFormatterCacheKey);
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            DateTimeFormatter forPattern = DateTimeFormat.forPattern(a(locale));
            DateTimeFormatter dateTimeFormatter2 = (DateTimeFormatter) a.putIfAbsent(styleFormatterCacheKey, forPattern);
            return dateTimeFormatter2 != null ? dateTimeFormatter2 : forPattern;
        }

        /* access modifiers changed from: 0000 */
        public String a(Locale locale) {
            DateFormat dateFormat;
            switch (this.d) {
                case 0:
                    dateFormat = DateFormat.getDateInstance(this.b, locale);
                    break;
                case 1:
                    dateFormat = DateFormat.getTimeInstance(this.c, locale);
                    break;
                case 2:
                    dateFormat = DateFormat.getDateTimeInstance(this.b, this.c, locale);
                    break;
                default:
                    dateFormat = null;
                    break;
            }
            if (dateFormat instanceof SimpleDateFormat) {
                return ((SimpleDateFormat) dateFormat).toPattern();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("No datetime pattern for locale: ");
            sb.append(locale);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static class StyleFormatterCacheKey {
        private final int a;
        private final Locale b;

        public StyleFormatterCacheKey(int i, int i2, int i3, Locale locale) {
            this.b = locale;
            this.a = i + (i2 << 4) + (i3 << 8);
        }

        public int hashCode() {
            return ((this.a + 31) * 31) + (this.b == null ? 0 : this.b.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof StyleFormatterCacheKey)) {
                return false;
            }
            StyleFormatterCacheKey styleFormatterCacheKey = (StyleFormatterCacheKey) obj;
            if (this.a != styleFormatterCacheKey.a) {
                return false;
            }
            if (this.b == null) {
                if (styleFormatterCacheKey.b != null) {
                    return false;
                }
            } else if (!this.b.equals(styleFormatterCacheKey.b)) {
                return false;
            }
            return true;
        }
    }

    public static DateTimeFormatter forPattern(String str) {
        return b(str);
    }

    public static DateTimeFormatter forStyle(String str) {
        return c(str);
    }

    public static String patternForStyle(String str, Locale locale) {
        DateTimeFormatter c = c(str);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return ((StyleFormatter) c.a()).a(locale);
    }

    public static DateTimeFormatter shortDate() {
        return a(3, 4);
    }

    public static DateTimeFormatter shortTime() {
        return a(4, 3);
    }

    public static DateTimeFormatter shortDateTime() {
        return a(3, 3);
    }

    public static DateTimeFormatter mediumDate() {
        return a(2, 4);
    }

    public static DateTimeFormatter mediumTime() {
        return a(4, 2);
    }

    public static DateTimeFormatter mediumDateTime() {
        return a(2, 2);
    }

    public static DateTimeFormatter longDate() {
        return a(1, 4);
    }

    public static DateTimeFormatter longTime() {
        return a(4, 1);
    }

    public static DateTimeFormatter longDateTime() {
        return a(1, 1);
    }

    public static DateTimeFormatter fullDate() {
        return a(0, 4);
    }

    public static DateTimeFormatter fullTime() {
        return a(4, 0);
    }

    public static DateTimeFormatter fullDateTime() {
        return a(0, 0);
    }

    static void a(DateTimeFormatterBuilder dateTimeFormatterBuilder, String str) {
        b(dateTimeFormatterBuilder, str);
    }

    protected DateTimeFormat() {
    }

    private static void b(DateTimeFormatterBuilder dateTimeFormatterBuilder, String str) {
        boolean z;
        int length = str.length();
        int[] iArr = new int[1];
        int i = 0;
        while (i < length) {
            iArr[0] = i;
            String a2 = a(str, iArr);
            int i2 = iArr[0];
            int length2 = a2.length();
            if (length2 != 0) {
                char charAt = a2.charAt(0);
                switch (charAt) {
                    case '\'':
                        String substring = a2.substring(1);
                        if (substring.length() != 1) {
                            dateTimeFormatterBuilder.appendLiteral(new String(substring));
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendLiteral(substring.charAt(0));
                            break;
                        }
                    case 'C':
                        dateTimeFormatterBuilder.appendCenturyOfEra(length2, length2);
                        break;
                    case 'D':
                        dateTimeFormatterBuilder.appendDayOfYear(length2);
                        break;
                    case 'E':
                        if (length2 < 4) {
                            dateTimeFormatterBuilder.appendDayOfWeekShortText();
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendDayOfWeekText();
                            break;
                        }
                    case 'G':
                        dateTimeFormatterBuilder.appendEraText();
                        break;
                    case 'H':
                        dateTimeFormatterBuilder.appendHourOfDay(length2);
                        break;
                    case 'K':
                        dateTimeFormatterBuilder.appendHourOfHalfday(length2);
                        break;
                    case 'M':
                        if (length2 >= 3) {
                            if (length2 < 4) {
                                dateTimeFormatterBuilder.appendMonthOfYearShortText();
                                break;
                            } else {
                                dateTimeFormatterBuilder.appendMonthOfYearText();
                                break;
                            }
                        } else {
                            dateTimeFormatterBuilder.appendMonthOfYear(length2);
                            break;
                        }
                    case 'S':
                        dateTimeFormatterBuilder.appendFractionOfSecond(length2, length2);
                        break;
                    case 'Y':
                    case EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY /*120*/:
                    case EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY /*121*/:
                        if (length2 != 2) {
                            int i3 = 9;
                            if (i2 + 1 < length) {
                                iArr[0] = iArr[0] + 1;
                                if (a(a(str, iArr))) {
                                    i3 = length2;
                                }
                                iArr[0] = iArr[0] - 1;
                            }
                            if (charAt == 'Y') {
                                dateTimeFormatterBuilder.appendYearOfEra(length2, i3);
                                break;
                            } else {
                                switch (charAt) {
                                    case EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY /*120*/:
                                        dateTimeFormatterBuilder.appendWeekyear(length2, i3);
                                        break;
                                    case EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY /*121*/:
                                        dateTimeFormatterBuilder.appendYear(length2, i3);
                                        break;
                                }
                            }
                        } else {
                            if (i2 + 1 < length) {
                                iArr[0] = iArr[0] + 1;
                                z = !a(a(str, iArr));
                                iArr[0] = iArr[0] - 1;
                            } else {
                                z = true;
                            }
                            if (charAt == 'x') {
                                dateTimeFormatterBuilder.appendTwoDigitWeekyear(new DateTime().getWeekyear() - 30, z);
                                break;
                            } else {
                                dateTimeFormatterBuilder.appendTwoDigitYear(new DateTime().getYear() - 30, z);
                                break;
                            }
                        }
                    case 'Z':
                        if (length2 != 1) {
                            if (length2 != 2) {
                                dateTimeFormatterBuilder.appendTimeZoneId();
                                break;
                            } else {
                                dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", true, 2, 2);
                                break;
                            }
                        } else {
                            dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", false, 2, 2);
                            break;
                        }
                    case 'a':
                        dateTimeFormatterBuilder.appendHalfdayOfDayText();
                        break;
                    case 'd':
                        dateTimeFormatterBuilder.appendDayOfMonth(length2);
                        break;
                    case 'e':
                        dateTimeFormatterBuilder.appendDayOfWeek(length2);
                        break;
                    case 'h':
                        dateTimeFormatterBuilder.appendClockhourOfHalfday(length2);
                        break;
                    case 'k':
                        dateTimeFormatterBuilder.appendClockhourOfDay(length2);
                        break;
                    case 'm':
                        dateTimeFormatterBuilder.appendMinuteOfHour(length2);
                        break;
                    case 's':
                        dateTimeFormatterBuilder.appendSecondOfMinute(length2);
                        break;
                    case 'w':
                        dateTimeFormatterBuilder.appendWeekOfWeekyear(length2);
                        break;
                    case EACTags.SECURITY_SUPPORT_TEMPLATE /*122*/:
                        if (length2 < 4) {
                            dateTimeFormatterBuilder.appendTimeZoneShortName(null);
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendTimeZoneName();
                            break;
                        }
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Illegal pattern component: ");
                        sb.append(a2);
                        throw new IllegalArgumentException(sb.toString());
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static String a(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i = iArr[0];
        int length = str.length();
        char charAt = str.charAt(i);
        if ((charAt >= 'A' && charAt <= 'Z') || (charAt >= 'a' && charAt <= 'z')) {
            sb.append(charAt);
            while (true) {
                int i2 = i + 1;
                if (i2 >= length || str.charAt(i2) != charAt) {
                    break;
                }
                sb.append(charAt);
                i = i2;
            }
        } else {
            sb.append('\'');
            boolean z = false;
            while (true) {
                if (i >= length) {
                    break;
                }
                char charAt2 = str.charAt(i);
                if (charAt2 == '\'') {
                    int i3 = i + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z = !z;
                    } else {
                        sb.append(charAt2);
                        i = i3;
                    }
                } else if (z || ((charAt2 < 'A' || charAt2 > 'Z') && (charAt2 < 'a' || charAt2 > 'z'))) {
                    sb.append(charAt2);
                }
                i++;
            }
            i--;
        }
        iArr[0] = i;
        return sb.toString();
    }

    private static boolean a(String str) {
        int length = str.length();
        if (length > 0) {
            switch (str.charAt(0)) {
                case 'C':
                case 'D':
                case 'F':
                case 'H':
                case 'K':
                case 'S':
                case 'W':
                case 'Y':
                case 'c':
                case 'd':
                case 'e':
                case 'h':
                case 'k':
                case 'm':
                case 's':
                case 'w':
                case EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY /*120*/:
                case EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY /*121*/:
                    return true;
                case 'M':
                    if (length <= 2) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    private static DateTimeFormatter b(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) a.get(str);
        if (dateTimeFormatter != null) {
            return dateTimeFormatter;
        }
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        b(dateTimeFormatterBuilder, str);
        DateTimeFormatter formatter = dateTimeFormatterBuilder.toFormatter();
        if (a.size() >= 500) {
            return formatter;
        }
        DateTimeFormatter dateTimeFormatter2 = (DateTimeFormatter) a.putIfAbsent(str, formatter);
        return dateTimeFormatter2 != null ? dateTimeFormatter2 : formatter;
    }

    private static DateTimeFormatter c(String str) {
        if (str == null || str.length() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid style specification: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
        int a2 = a(str.charAt(0));
        int a3 = a(str.charAt(1));
        if (a2 != 4 || a3 != 4) {
            return a(a2, a3);
        }
        throw new IllegalArgumentException("Style '--' is invalid");
    }

    private static DateTimeFormatter a(int i, int i2) {
        int i3 = (i << 2) + i + i2;
        if (i3 >= b.length()) {
            return b(i, i2);
        }
        DateTimeFormatter dateTimeFormatter = (DateTimeFormatter) b.get(i3);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = b(i, i2);
            if (!b.compareAndSet(i3, null, dateTimeFormatter)) {
                dateTimeFormatter = (DateTimeFormatter) b.get(i3);
            }
        }
        return dateTimeFormatter;
    }

    private static DateTimeFormatter b(int i, int i2) {
        int i3 = i == 4 ? 1 : i2 == 4 ? 0 : 2;
        StyleFormatter styleFormatter = new StyleFormatter(i, i2, i3);
        return new DateTimeFormatter((InternalPrinter) styleFormatter, (InternalParser) styleFormatter);
    }

    private static int a(char c) {
        if (c == '-') {
            return 4;
        }
        if (c == 'F') {
            return 0;
        }
        if (c == 'S') {
            return 3;
        }
        switch (c) {
            case 'L':
                return 1;
            case 'M':
                return 2;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid style character: ");
                sb.append(c);
                throw new IllegalArgumentException(sb.toString());
        }
    }
}
