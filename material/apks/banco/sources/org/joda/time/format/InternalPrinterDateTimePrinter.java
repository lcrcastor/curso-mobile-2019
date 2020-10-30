package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

class InternalPrinterDateTimePrinter implements DateTimePrinter, InternalPrinter {
    private final InternalPrinter a;

    static DateTimePrinter a(InternalPrinter internalPrinter) {
        if (internalPrinter instanceof DateTimePrinterInternalPrinter) {
            return ((DateTimePrinterInternalPrinter) internalPrinter).a();
        }
        if (internalPrinter instanceof DateTimePrinter) {
            return (DateTimePrinter) internalPrinter;
        }
        if (internalPrinter == null) {
            return null;
        }
        return new InternalPrinterDateTimePrinter(internalPrinter);
    }

    private InternalPrinterDateTimePrinter(InternalPrinter internalPrinter) {
        this.a = internalPrinter;
    }

    public int estimatePrintedLength() {
        return this.a.estimatePrintedLength();
    }

    public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
        try {
            this.a.a(stringBuffer, j, chronology, i, dateTimeZone, locale);
        } catch (IOException unused) {
        }
    }

    public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
        this.a.a(writer, j, chronology, i, dateTimeZone, locale);
    }

    public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
        this.a.a(appendable, j, chronology, i, dateTimeZone, locale);
    }

    public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        try {
            this.a.a(stringBuffer, readablePartial, locale);
        } catch (IOException unused) {
        }
    }

    public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) {
        this.a.a(writer, readablePartial, locale);
    }

    public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
        this.a.a(appendable, readablePartial, locale);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InternalPrinterDateTimePrinter)) {
            return false;
        }
        return this.a.equals(((InternalPrinterDateTimePrinter) obj).a);
    }
}
