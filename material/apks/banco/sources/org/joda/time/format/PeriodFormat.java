package org.joda.time.format;

import java.io.Writer;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

public class PeriodFormat {
    private static final ConcurrentMap<Locale, PeriodFormatter> a = new ConcurrentHashMap();

    static class DynamicWordBased implements PeriodParser, PeriodPrinter {
        private final PeriodFormatter a;

        DynamicWordBased(PeriodFormatter periodFormatter) {
            this.a = periodFormatter;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            return a(locale).countFieldsToPrint(readablePeriod, i, locale);
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            return a(locale).calculatePrintedLength(readablePeriod, locale);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            a(locale).printTo(stringBuffer, readablePeriod, locale);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) {
            a(locale).printTo(writer, readablePeriod, locale);
        }

        private PeriodPrinter a(Locale locale) {
            if (locale == null || locale.equals(this.a.getLocale())) {
                return this.a.getPrinter();
            }
            return PeriodFormat.wordBased(locale).getPrinter();
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            return b(locale).parseInto(readWritablePeriod, str, i, locale);
        }

        private PeriodParser b(Locale locale) {
            if (locale == null || locale.equals(this.a.getLocale())) {
                return this.a.getParser();
            }
            return PeriodFormat.wordBased(locale).getParser();
        }
    }

    protected PeriodFormat() {
    }

    public static PeriodFormatter getDefault() {
        return wordBased(Locale.ENGLISH);
    }

    public static PeriodFormatter wordBased() {
        return wordBased(Locale.getDefault());
    }

    public static PeriodFormatter wordBased(Locale locale) {
        PeriodFormatter periodFormatter = (PeriodFormatter) a.get(locale);
        if (periodFormatter != null) {
            return periodFormatter;
        }
        DynamicWordBased dynamicWordBased = new DynamicWordBased(a(locale));
        PeriodFormatter periodFormatter2 = new PeriodFormatter(dynamicWordBased, dynamicWordBased, locale, null);
        PeriodFormatter periodFormatter3 = (PeriodFormatter) a.putIfAbsent(locale, periodFormatter2);
        return periodFormatter3 != null ? periodFormatter3 : periodFormatter2;
    }

    private static PeriodFormatter a(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("org.joda.time.format.messages", locale);
        if (a(bundle, "PeriodFormat.regex.separator")) {
            return a(bundle, locale);
        }
        return b(bundle, locale);
    }

    private static PeriodFormatter a(ResourceBundle resourceBundle, Locale locale) {
        String[] a2 = a(resourceBundle);
        String string = resourceBundle.getString("PeriodFormat.regex.separator");
        PeriodFormatterBuilder periodFormatterBuilder = new PeriodFormatterBuilder();
        periodFormatterBuilder.appendYears();
        if (a(resourceBundle, "PeriodFormat.years.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.years.regex").split(string), resourceBundle.getString("PeriodFormat.years.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.year"), resourceBundle.getString("PeriodFormat.years"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendMonths();
        if (a(resourceBundle, "PeriodFormat.months.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.months.regex").split(string), resourceBundle.getString("PeriodFormat.months.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.month"), resourceBundle.getString("PeriodFormat.months"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendWeeks();
        if (a(resourceBundle, "PeriodFormat.weeks.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.weeks.regex").split(string), resourceBundle.getString("PeriodFormat.weeks.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.week"), resourceBundle.getString("PeriodFormat.weeks"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendDays();
        if (a(resourceBundle, "PeriodFormat.days.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.days.regex").split(string), resourceBundle.getString("PeriodFormat.days.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.day"), resourceBundle.getString("PeriodFormat.days"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendHours();
        if (a(resourceBundle, "PeriodFormat.hours.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.hours.regex").split(string), resourceBundle.getString("PeriodFormat.hours.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.hour"), resourceBundle.getString("PeriodFormat.hours"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendMinutes();
        if (a(resourceBundle, "PeriodFormat.minutes.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.minutes.regex").split(string), resourceBundle.getString("PeriodFormat.minutes.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.minute"), resourceBundle.getString("PeriodFormat.minutes"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendSeconds();
        if (a(resourceBundle, "PeriodFormat.seconds.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.seconds.regex").split(string), resourceBundle.getString("PeriodFormat.seconds.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.second"), resourceBundle.getString("PeriodFormat.seconds"));
        }
        periodFormatterBuilder.appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2);
        periodFormatterBuilder.appendMillis();
        if (a(resourceBundle, "PeriodFormat.milliseconds.regex")) {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.milliseconds.regex").split(string), resourceBundle.getString("PeriodFormat.milliseconds.list").split(string));
        } else {
            periodFormatterBuilder.appendSuffix(resourceBundle.getString("PeriodFormat.millisecond"), resourceBundle.getString("PeriodFormat.milliseconds"));
        }
        return periodFormatterBuilder.toFormatter().withLocale(locale);
    }

    private static PeriodFormatter b(ResourceBundle resourceBundle, Locale locale) {
        String[] a2 = a(resourceBundle);
        return new PeriodFormatterBuilder().appendYears().appendSuffix(resourceBundle.getString("PeriodFormat.year"), resourceBundle.getString("PeriodFormat.years")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendMonths().appendSuffix(resourceBundle.getString("PeriodFormat.month"), resourceBundle.getString("PeriodFormat.months")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendWeeks().appendSuffix(resourceBundle.getString("PeriodFormat.week"), resourceBundle.getString("PeriodFormat.weeks")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendDays().appendSuffix(resourceBundle.getString("PeriodFormat.day"), resourceBundle.getString("PeriodFormat.days")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendHours().appendSuffix(resourceBundle.getString("PeriodFormat.hour"), resourceBundle.getString("PeriodFormat.hours")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendMinutes().appendSuffix(resourceBundle.getString("PeriodFormat.minute"), resourceBundle.getString("PeriodFormat.minutes")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendSeconds().appendSuffix(resourceBundle.getString("PeriodFormat.second"), resourceBundle.getString("PeriodFormat.seconds")).appendSeparator(resourceBundle.getString("PeriodFormat.commaspace"), resourceBundle.getString("PeriodFormat.spaceandspace"), a2).appendMillis().appendSuffix(resourceBundle.getString("PeriodFormat.millisecond"), resourceBundle.getString("PeriodFormat.milliseconds")).toFormatter().withLocale(locale);
    }

    private static String[] a(ResourceBundle resourceBundle) {
        return new String[]{resourceBundle.getString("PeriodFormat.space"), resourceBundle.getString("PeriodFormat.comma"), resourceBundle.getString("PeriodFormat.commandand"), resourceBundle.getString("PeriodFormat.commaspaceand")};
    }

    private static boolean a(ResourceBundle resourceBundle, String str) {
        Enumeration keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            if (((String) keys.nextElement()).equals(str)) {
                return true;
            }
        }
        return false;
    }
}
