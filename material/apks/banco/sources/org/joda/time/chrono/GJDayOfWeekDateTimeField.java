package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.PreciseDurationDateTimeField;

final class GJDayOfWeekDateTimeField extends PreciseDurationDateTimeField {
    private final BasicChronology b;

    public int getMaximumValue() {
        return 7;
    }

    public int getMinimumValue() {
        return 1;
    }

    GJDayOfWeekDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfWeek(), durationField);
        this.b = basicChronology;
    }

    public int get(long j) {
        return this.b.g(j);
    }

    public String getAsText(int i, Locale locale) {
        return GJLocaleSymbols.a(locale).d(i);
    }

    public String getAsShortText(int i, Locale locale) {
        return GJLocaleSymbols.a(locale).e(i);
    }

    /* access modifiers changed from: protected */
    public int convertText(String str, Locale locale) {
        return GJLocaleSymbols.a(locale).c(str);
    }

    public DurationField getRangeDurationField() {
        return this.b.weeks();
    }

    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.a(locale).d();
    }

    public int getMaximumShortTextLength(Locale locale) {
        return GJLocaleSymbols.a(locale).e();
    }
}
