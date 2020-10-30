package org.joda.time.field;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;

public final class SkipUndoDateTimeField extends DelegatedDateTimeField {
    private static final long serialVersionUID = -5875876968979L;
    private final Chronology a;
    private final int b;
    private transient int c;

    public SkipUndoDateTimeField(Chronology chronology, DateTimeField dateTimeField) {
        this(chronology, dateTimeField, 0);
    }

    public SkipUndoDateTimeField(Chronology chronology, DateTimeField dateTimeField, int i) {
        super(dateTimeField);
        this.a = chronology;
        int minimumValue = super.getMinimumValue();
        if (minimumValue < i) {
            this.c = minimumValue + 1;
        } else if (minimumValue == i + 1) {
            this.c = i;
        } else {
            this.c = minimumValue;
        }
        this.b = i;
    }

    public int get(long j) {
        int i = super.get(j);
        return i < this.b ? i + 1 : i;
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, this.c, getMaximumValue());
        if (i <= this.b) {
            i--;
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
