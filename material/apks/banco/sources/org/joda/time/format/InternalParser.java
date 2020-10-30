package org.joda.time.format;

interface InternalParser {
    int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i);

    int estimateParsedLength();
}
