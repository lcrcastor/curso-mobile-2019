package org.joda.time.format;

import java.io.Writer;
import java.util.Locale;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

public class PeriodFormatter {
    private final PeriodPrinter a;
    private final PeriodParser b;
    private final Locale c;
    private final PeriodType d;

    public PeriodFormatter(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        this.a = periodPrinter;
        this.b = periodParser;
        this.c = null;
        this.d = null;
    }

    PeriodFormatter(PeriodPrinter periodPrinter, PeriodParser periodParser, Locale locale, PeriodType periodType) {
        this.a = periodPrinter;
        this.b = periodParser;
        this.c = locale;
        this.d = periodType;
    }

    public boolean isPrinter() {
        return this.a != null;
    }

    public PeriodPrinter getPrinter() {
        return this.a;
    }

    public boolean isParser() {
        return this.b != null;
    }

    public PeriodParser getParser() {
        return this.b;
    }

    public PeriodFormatter withLocale(Locale locale) {
        return (locale == getLocale() || (locale != null && locale.equals(getLocale()))) ? this : new PeriodFormatter(this.a, this.b, locale, this.d);
    }

    public Locale getLocale() {
        return this.c;
    }

    public PeriodFormatter withParseType(PeriodType periodType) {
        if (periodType == this.d) {
            return this;
        }
        return new PeriodFormatter(this.a, this.b, this.c, periodType);
    }

    public PeriodType getParseType() {
        return this.d;
    }

    public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod) {
        a();
        a(readablePeriod);
        getPrinter().printTo(stringBuffer, readablePeriod, this.c);
    }

    public void printTo(Writer writer, ReadablePeriod readablePeriod) {
        a();
        a(readablePeriod);
        getPrinter().printTo(writer, readablePeriod, this.c);
    }

    public String print(ReadablePeriod readablePeriod) {
        a();
        a(readablePeriod);
        PeriodPrinter printer = getPrinter();
        StringBuffer stringBuffer = new StringBuffer(printer.calculatePrintedLength(readablePeriod, this.c));
        printer.printTo(stringBuffer, readablePeriod, this.c);
        return stringBuffer.toString();
    }

    private void a() {
        if (this.a == null) {
            throw new UnsupportedOperationException("Printing not supported");
        }
    }

    private void a(ReadablePeriod readablePeriod) {
        if (readablePeriod == null) {
            throw new IllegalArgumentException("Period must not be null");
        }
    }

    public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i) {
        b();
        a(readWritablePeriod);
        return getParser().parseInto(readWritablePeriod, str, i, this.c);
    }

    public Period parsePeriod(String str) {
        b();
        return parseMutablePeriod(str).toPeriod();
    }

    public MutablePeriod parseMutablePeriod(String str) {
        b();
        MutablePeriod mutablePeriod = new MutablePeriod(0, this.d);
        int parseInto = getParser().parseInto(mutablePeriod, str, 0, this.c);
        if (parseInto < 0) {
            parseInto ^= -1;
        } else if (parseInto >= str.length()) {
            return mutablePeriod;
        }
        throw new IllegalArgumentException(FormatUtils.a(str, parseInto));
    }

    private void b() {
        if (this.b == null) {
            throw new UnsupportedOperationException("Parsing not supported");
        }
    }
}
