package org.joda.time.chrono;

import java.util.Locale;

final class GJMonthOfYearDateTimeField extends BasicMonthOfYearDateTimeField {
    GJMonthOfYearDateTimeField(BasicChronology basicChronology) {
        super(basicChronology, 2);
    }

    public String getAsText(int i, Locale locale) {
        return GJLocaleSymbols.a(locale).b(i);
    }

    public String getAsShortText(int i, Locale locale) {
        return GJLocaleSymbols.a(locale).c(i);
    }

    /* access modifiers changed from: protected */
    public int convertText(String str, Locale locale) {
        return GJLocaleSymbols.a(locale).b(str);
    }

    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.a(locale).b();
    }

    public int getMaximumShortTextLength(Locale locale) {
        return GJLocaleSymbols.a(locale).c();
    }
}
