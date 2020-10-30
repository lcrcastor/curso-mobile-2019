package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.IllegalFieldValueException;

public final class SkipDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = -8869148464118507846L;
    private final Chronology a;
    private final int b;
    private transient int c;

    public SkipDateTimeField(Chronology chronology, DateTimeField dateTimeField) {
        this(chronology, dateTimeField, 0);
    }

    public SkipDateTimeField(Chronology chronology, DateTimeField dateTimeField, int i) {
        super(dateTimeField);
        this.a = chronology;
        int minimumValue = super.getMinimumValue();
        if (minimumValue < i) {
            this.c = minimumValue - 1;
        } else if (minimumValue == i) {
            this.c = i + 1;
        } else {
            this.c = minimumValue;
        }
        this.b = i;
    }

    public int get(long j) {
        int i = super.get(j);
        return i <= this.b ? i - 1 : i;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.c, getMaximumValue());
        if (i <= this.b) {
            if (i == this.b) {
                throw new IllegalFieldValueException(DateTimeFieldType.year(), (Number) Integer.valueOf(i), (Number) null, (Number) null);
            }
            i++;
        }
        return super.set(j, i);
    }

    public int getMinimumValue() {
        return this.c;
    }

    private Object readResolve() {
        return getType().getField(this.a);
    }
}
