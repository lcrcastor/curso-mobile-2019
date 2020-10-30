package org.joda.time.format;

import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

class DateTimePrinterInternalPrinter implements InternalPrinter {
    private final DateTimePrinter a;

    static InternalPrinter a(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter instanceof InternalPrinterDateTimePrinter) {
            return (InternalPrinter) dateTimePrinter;
        }
        if (dateTimePrinter == null) {
            return null;
        }
        return new DateTimePrinterInternalPrinter(dateTimePrinter);
    }

    private DateTimePrinterInternalPrinter(DateTimePrinter dateTimePrinter) {
        this.a = dateTimePrinter;
    }

    /* access modifiers changed from: 0000 */
    public DateTimePrinter a() {
        return this.a;
    }

    public int estimatePrintedLength() {
        return this.a.estimatePrintedLength();
    }

    public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
        Appendable appendable2 = appendable;
        if (appendable2 instanceof StringBuffer) {
            this.a.printTo((StringBuffer) appendable2, j, chronology, i, dateTimeZone, locale);
        }
        if (appendable2 instanceof Writer) {
            this.a.printTo((Writer) appendable2, j, chronology, i, dateTimeZone, locale);
        }
        StringBuffer stringBuffer = new StringBuffer(estimatePrintedLength());
        this.a.printTo(stringBuffer, j, chronology, i, dateTimeZone, locale);
        appendable2.append(stringBuffer);
    }

    public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
        if (appendable instanceof StringBuffer) {
            this.a.printTo((StringBuffer) appendable, readablePartial, locale);
        }
        if (appendable instanceof Writer) {
            this.a.printTo((Writer) appendable, readablePartial, locale);
        }
        StringBuffer stringBuffer = new StringBuffer(estimatePrintedLength());
        this.a.printTo(stringBuffer, readablePartial, locale);
        appendable.append(stringBuffer);
    }
}
