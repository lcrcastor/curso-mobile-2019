package org.joda.time.format;

class InternalParserDateTimeParser implements DateTimeParser, InternalParser {
    private final InternalParser a;

    static DateTimeParser a(InternalParser internalParser) {
        if (internalParser instanceof DateTimeParserInternalParser) {
            return ((DateTimeParserInternalParser) internalParser).a();
        }
        if (internalParser instanceof DateTimeParser) {
            return (DateTimeParser) internalParser;
        }
        if (internalParser == null) {
            return null;
        }
        return new InternalParserDateTimeParser(internalParser);
    }

    private InternalParserDateTimeParser(InternalParser internalParser) {
        this.a = internalParser;
    }

    public int estimateParsedLength() {
        return this.a.estimateParsedLength();
    }

    public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
        return this.a.a(dateTimeParserBucket, charSequence, i);
    }

    public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
        return this.a.a(dateTimeParserBucket, str, i);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InternalParserDateTimeParser)) {
            return false;
        }
        return this.a.equals(((InternalParserDateTimeParser) obj).a);
    }
}
