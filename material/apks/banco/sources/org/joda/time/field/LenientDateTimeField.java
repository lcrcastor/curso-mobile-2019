package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;

public class LenientDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = 8714085824173290599L;
    private final Chronology a;

    public final boolean isLenient() {
        return true;
    }

    public static DateTimeField getInstance(DateTimeField dateTimeField, Chronology chronology) {
        if (dateTimeField == null) {
            return null;
        }
        if (dateTimeField instanceof StrictDateTimeField) {
            dateTimeField = ((StrictDateTimeField) dateTimeField).getWrappedField();
        }
        if (dateTimeField.isLenient()) {
            return dateTimeField;
        }
        return new LenientDateTimeField(dateTimeField, chronology);
    }

    protected LenientDateTimeField(DateTimeField dateTimeField, Chronology chronology) {
        super(dateTimeField);
        this.a = chronology;
    }

    public long set(long j, int i) {
        return this.a.getZone().convertLocalToUTC(getType().getField(this.a.withUTC()).add(this.a.getZone().convertUTCToLocal(j), FieldUtils.safeSubtract((long) i, (long) get(j))), false, j);
    }
}
