package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadWritableInstant;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;

public class DateTimeFormatter {
    private final InternalPrinter a;
    private final InternalParser b;
    private final Locale c;
    private final boolean d;
    private final Chronology e;
    private final DateTimeZone f;
    private final Integer g;
    private final int h;

    public DateTimeFormatter(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        this(DateTimePrinterInternalPrinter.a(dateTimePrinter), DateTimeParserInternalParser.a(dateTimeParser));
    }

    DateTimeFormatter(InternalPrinter internalPrinter, InternalParser internalParser) {
        this.a = internalPrinter;
        this.b = internalParser;
        this.c = null;
        this.d = false;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = 2000;
    }

    private DateTimeFormatter(InternalPrinter internalPrinter, InternalParser internalParser, Locale locale, boolean z, Chronology chronology, DateTimeZone dateTimeZone, Integer num, int i) {
        this.a = internalPrinter;
        this.b = internalParser;
        this.c = locale;
        this.d = z;
        this.e = chronology;
        this.f = dateTimeZone;
        this.g = num;
        this.h = i;
    }

    public boolean isPrinter() {
        return this.a != null;
    }

    public DateTimePrinter getPrinter() {
        return InternalPrinterDateTimePrinter.a(this.a);
    }

    /* access modifiers changed from: 0000 */
    public InternalPrinter a() {
        return this.a;
    }

    public boolean isParser() {
        return this.b != null;
    }

    public DateTimeParser getParser() {
        return InternalParserDateTimeParser.a(this.b);
    }

    /* access modifiers changed from: 0000 */
    public InternalParser b() {
        return this.b;
    }

    public DateTimeFormatter withLocale(Locale locale) {
        if (locale == getLocale() || (locale != null && locale.equals(getLocale()))) {
            return this;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, locale, this.d, this.e, this.f, this.g, this.h);
        return dateTimeFormatter;
    }

    public Locale getLocale() {
        return this.c;
    }

    public DateTimeFormatter withOffsetParsed() {
        if (this.d) {
            return this;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, this.c, true, this.e, null, this.g, this.h);
        return dateTimeFormatter;
    }

    public boolean isOffsetParsed() {
        return this.d;
    }

    public DateTimeFormatter withChronology(Chronology chronology) {
        if (this.e == chronology) {
            return this;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, this.c, this.d, chronology, this.f, this.g, this.h);
        return dateTimeFormatter;
    }

    public Chronology getChronology() {
        return this.e;
    }

    @Deprecated
    public Chronology getChronolgy() {
        return this.e;
    }

    public DateTimeFormatter withZoneUTC() {
        return withZone(DateTimeZone.UTC);
    }

    public DateTimeFormatter withZone(DateTimeZone dateTimeZone) {
        if (this.f == dateTimeZone) {
            return this;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, this.c, false, this.e, dateTimeZone, this.g, this.h);
        return dateTimeFormatter;
    }

    public DateTimeZone getZone() {
        return this.f;
    }

    public DateTimeFormatter withPivotYear(Integer num) {
        if (this.g == num || (this.g != null && this.g.equals(num))) {
            return this;
        }
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, this.c, this.d, this.e, this.f, num, this.h);
        return dateTimeFormatter;
    }

    public DateTimeFormatter withPivotYear(int i) {
        return withPivotYear(Integer.valueOf(i));
    }

    public Integer getPivotYear() {
        return this.g;
    }

    public DateTimeFormatter withDefaultYear(int i) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatter(this.a, this.b, this.c, this.d, this.e, this.f, this.g, i);
        return dateTimeFormatter;
    }

    public int getDefaultYear() {
        return this.h;
    }

    public void printTo(StringBuffer stringBuffer, ReadableInstant readableInstant) {
        try {
            printTo((Appendable) stringBuffer, readableInstant);
        } catch (IOException unused) {
        }
    }

    public void printTo(StringBuilder sb, ReadableInstant readableInstant) {
        try {
            printTo((Appendable) sb, readableInstant);
        } catch (IOException unused) {
        }
    }

    public void printTo(Writer writer, ReadableInstant readableInstant) {
        printTo((Appendable) writer, readableInstant);
    }

    public void printTo(Appendable appendable, ReadableInstant readableInstant) {
        a(appendable, DateTimeUtils.getInstantMillis(readableInstant), DateTimeUtils.getInstantChronology(readableInstant));
    }

    public void printTo(StringBuffer stringBuffer, long j) {
        try {
            printTo((Appendable) stringBuffer, j);
        } catch (IOException unused) {
        }
    }

    public void printTo(StringBuilder sb, long j) {
        try {
            printTo((Appendable) sb, j);
        } catch (IOException unused) {
        }
    }

    public void printTo(Writer writer, long j) {
        printTo((Appendable) writer, j);
    }

    public void printTo(Appendable appendable, long j) {
        a(appendable, j, null);
    }

    public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial) {
        try {
            printTo((Appendable) stringBuffer, readablePartial);
        } catch (IOException unused) {
        }
    }

    public void printTo(StringBuilder sb, ReadablePartial readablePartial) {
        try {
            printTo((Appendable) sb, readablePartial);
        } catch (IOException unused) {
        }
    }

    public void printTo(Writer writer, ReadablePartial readablePartial) {
        printTo((Appendable) writer, readablePartial);
    }

    public void printTo(Appendable appendable, ReadablePartial readablePartial) {
        InternalPrinter c2 = c();
        if (readablePartial == null) {
            throw new IllegalArgumentException("The partial must not be null");
        }
        c2.a(appendable, readablePartial, this.c);
    }

    public String print(ReadableInstant readableInstant) {
        StringBuilder sb = new StringBuilder(c().estimatePrintedLength());
        try {
            printTo((Appendable) sb, readableInstant);
        } catch (IOException unused) {
        }
        return sb.toString();
    }

    public String print(long j) {
        StringBuilder sb = new StringBuilder(c().estimatePrintedLength());
        try {
            printTo((Appendable) sb, j);
        } catch (IOException unused) {
        }
        return sb.toString();
    }

    public String print(ReadablePartial readablePartial) {
        StringBuilder sb = new StringBuilder(c().estimatePrintedLength());
        try {
            printTo((Appendable) sb, readablePartial);
        } catch (IOException unused) {
        }
        return sb.toString();
    }

    private void a(Appendable appendable, long j, Chronology chronology) {
        long j2 = j;
        InternalPrinter c2 = c();
        Chronology a2 = a(chronology);
        DateTimeZone zone = a2.getZone();
        int offset = zone.getOffset(j2);
        long j3 = (long) offset;
        long j4 = j2 + j3;
        if ((j2 ^ j4) < 0 && (j2 ^ j3) >= 0) {
            zone = DateTimeZone.UTC;
            offset = 0;
            j4 = j2;
        }
        InternalPrinter internalPrinter = c2;
        Appendable appendable2 = appendable;
        long j5 = j4;
        internalPrinter.a(appendable2, j5, a2.withUTC(), offset, zone, this.c);
    }

    private InternalPrinter c() {
        InternalPrinter internalPrinter = this.a;
        if (internalPrinter != null) {
            return internalPrinter;
        }
        throw new UnsupportedOperationException("Printing not supported");
    }

    public int parseInto(ReadWritableInstant readWritableInstant, String str, int i) {
        InternalParser d2 = d();
        if (readWritableInstant == null) {
            throw new IllegalArgumentException("Instant must not be null");
        }
        long millis = readWritableInstant.getMillis();
        Chronology chronology = readWritableInstant.getChronology();
        int i2 = DateTimeUtils.getChronology(chronology).year().get(millis);
        long offset = millis + ((long) chronology.getZone().getOffset(millis));
        Chronology a2 = a(chronology);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(offset, a2, this.c, this.g, i2);
        int a3 = d2.a(dateTimeParserBucket, str, i);
        readWritableInstant.setMillis(dateTimeParserBucket.computeMillis(false, str));
        if (this.d && dateTimeParserBucket.getOffsetInteger() != null) {
            a2 = a2.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
        } else if (dateTimeParserBucket.getZone() != null) {
            a2 = a2.withZone(dateTimeParserBucket.getZone());
        }
        readWritableInstant.setChronology(a2);
        if (this.f != null) {
            readWritableInstant.setZone(this.f);
        }
        return a3;
    }

    public long parseMillis(String str) {
        InternalParser d2 = d();
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0, a(this.e), this.c, this.g, this.h);
        return dateTimeParserBucket.a(d2, (CharSequence) str);
    }

    public LocalDate parseLocalDate(String str) {
        return parseLocalDateTime(str).toLocalDate();
    }

    public LocalTime parseLocalTime(String str) {
        return parseLocalDateTime(str).toLocalTime();
    }

    public LocalDateTime parseLocalDateTime(String str) {
        InternalParser d2 = d();
        Chronology withUTC = a(null).withUTC();
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0, withUTC, this.c, this.g, this.h);
        int a2 = d2.a(dateTimeParserBucket, str, 0);
        if (a2 < 0) {
            a2 ^= -1;
        } else if (a2 >= str.length()) {
            long computeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (dateTimeParserBucket.getOffsetInteger() != null) {
                withUTC = withUTC.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                withUTC = withUTC.withZone(dateTimeParserBucket.getZone());
            }
            return new LocalDateTime(computeMillis, withUTC);
        }
        throw new IllegalArgumentException(FormatUtils.a(str, a2));
    }

    public DateTime parseDateTime(String str) {
        InternalParser d2 = d();
        Chronology a2 = a(null);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0, a2, this.c, this.g, this.h);
        int a3 = d2.a(dateTimeParserBucket, str, 0);
        if (a3 < 0) {
            a3 ^= -1;
        } else if (a3 >= str.length()) {
            long computeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (this.d && dateTimeParserBucket.getOffsetInteger() != null) {
                a2 = a2.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                a2 = a2.withZone(dateTimeParserBucket.getZone());
            }
            DateTime dateTime = new DateTime(computeMillis, a2);
            return this.f != null ? dateTime.withZone(this.f) : dateTime;
        }
        throw new IllegalArgumentException(FormatUtils.a(str, a3));
    }

    public MutableDateTime parseMutableDateTime(String str) {
        InternalParser d2 = d();
        Chronology a2 = a(null);
        DateTimeParserBucket dateTimeParserBucket = new DateTimeParserBucket(0, a2, this.c, this.g, this.h);
        int a3 = d2.a(dateTimeParserBucket, str, 0);
        if (a3 < 0) {
            a3 ^= -1;
        } else if (a3 >= str.length()) {
            long computeMillis = dateTimeParserBucket.computeMillis(true, str);
            if (this.d && dateTimeParserBucket.getOffsetInteger() != null) {
                a2 = a2.withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger().intValue()));
            } else if (dateTimeParserBucket.getZone() != null) {
                a2 = a2.withZone(dateTimeParserBucket.getZone());
            }
            MutableDateTime mutableDateTime = new MutableDateTime(computeMillis, a2);
            if (this.f != null) {
                mutableDateTime.setZone(this.f);
            }
            return mutableDateTime;
        }
        throw new IllegalArgumentException(FormatUtils.a(str, a3));
    }

    private InternalParser d() {
        InternalParser internalParser = this.b;
        if (internalParser != null) {
            return internalParser;
        }
        throw new UnsupportedOperationException("Parsing not supported");
    }

    private Chronology a(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        if (this.e != null) {
            chronology2 = this.e;
        }
        return this.f != null ? chronology2.withZone(this.f) : chronology2;
    }
}
