package org.joda.time.format;

import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

interface InternalPrinter {
    void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale);

    void a(Appendable appendable, ReadablePartial readablePartial, Locale locale);

    int estimatePrintedLength();
}
