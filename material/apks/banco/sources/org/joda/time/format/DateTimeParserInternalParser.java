package org.joda.time.format;

class DateTimeParserInternalParser implements InternalParser {
    private final DateTimeParser a;

    static InternalParser a(DateTimeParser dateTimeParser) {
        if (dateTimeParser instanceof InternalParserDateTimeParser) {
            return (InternalParser) dateTimeParser;
        }
        if (dateTimeParser == null) {
            return null;
        }
        return new DateTimeParserInternalParser(dateTimeParser);
    }

    private DateTimeParserInternalParser(DateTimeParser dateTimeParser) {
        this.a = dateTimeParser;
    }

    /* access modifiers changed from: 0000 */
    public DateTimeParser a() {
        return this.a;
    }

    public int estimateParsedLength() {
        return this.a.estimateParsedLength();
    }

    public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
        return this.a.parseInto(dateTimeParserBucket, charSequence.toString(), i);
    }
}
