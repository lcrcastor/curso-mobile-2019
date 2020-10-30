package org.joda.time.chrono;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.LenientDateTimeField;

public final class LenientChronology extends AssembledChronology {
    private static final long serialVersionUID = -3148237568046877177L;
    private transient Chronology a;

    public static LenientChronology getInstance(Chronology chronology) {
        if (chronology != null) {
            return new LenientChronology(chronology);
        }
        throw new IllegalArgumentException("Must supply a chronology");
    }

    private LenientChronology(Chronology chronology) {
        super(chronology, null);
    }

    public Chronology withUTC() {
        if (this.a == null) {
            if (getZone() == DateTimeZone.UTC) {
                this.a = this;
            } else {
                this.a = getInstance(getBase().withUTC());
            }
        }
        return this.a;
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == DateTimeZone.UTC) {
            return withUTC();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        return getInstance(getBase().withZone(dateTimeZone));
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        fields.year = a(fields.year);
        fields.yearOfEra = a(fields.yearOfEra);
        fields.yearOfCentury = a(fields.yearOfCentury);
        fields.centuryOfEra = a(fields.centuryOfEra);
        fields.era = a(fields.era);
        fields.dayOfWeek = a(fields.dayOfWeek);
        fields.dayOfMonth = a(fields.dayOfMonth);
        fields.dayOfYear = a(fields.dayOfYear);
        fields.monthOfYear = a(fields.monthOfYear);
        fields.weekOfWeekyear = a(fields.weekOfWeekyear);
        fields.weekyear = a(fields.weekyear);
        fields.weekyearOfCentury = a(fields.weekyearOfCentury);
        fields.millisOfSecond = a(fields.millisOfSecond);
        fields.millisOfDay = a(fields.millisOfDay);
        fields.secondOfMinute = a(fields.secondOfMinute);
        fields.secondOfDay = a(fields.secondOfDay);
        fields.minuteOfHour = a(fields.minuteOfHour);
        fields.minuteOfDay = a(fields.minuteOfDay);
        fields.hourOfDay = a(fields.hourOfDay);
        fields.hourOfHalfday = a(fields.hourOfHalfday);
        fields.clockhourOfDay = a(fields.clockhourOfDay);
        fields.clockhourOfHalfday = a(fields.clockhourOfHalfday);
        fields.halfdayOfDay = a(fields.halfdayOfDay);
    }

    private final DateTimeField a(DateTimeField dateTimeField) {
        return LenientDateTimeField.getInstance(dateTimeField, getBase());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LenientChronology)) {
            return false;
        }
        return getBase().equals(((LenientChronology) obj).getBase());
    }

    public int hashCode() {
        return (getBase().hashCode() * 7) + 236548278;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LenientChronology[");
        sb.append(getBase().toString());
        sb.append(']');
        return sb.toString();
    }
}
