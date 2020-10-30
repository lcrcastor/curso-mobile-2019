package org.joda.time.chrono;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.StrictDateTimeField;

public final class StrictChronology extends AssembledChronology {
    private static final long serialVersionUID = 6633006628097111960L;
    private transient Chronology a;

    public static StrictChronology getInstance(Chronology chronology) {
        if (chronology != null) {
            return new StrictChronology(chronology);
        }
        throw new IllegalArgumentException("Must supply a chronology");
    }

    private StrictChronology(Chronology chronology) {
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

    private static final DateTimeField a(DateTimeField dateTimeField) {
        return StrictDateTimeField.getInstance(dateTimeField);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StrictChronology)) {
            return false;
        }
        return getBase().equals(((StrictChronology) obj).getBase());
    }

    public int hashCode() {
        return (getBase().hashCode() * 7) + 352831696;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StrictChronology[");
        sb.append(getBase().toString());
        sb.append(']');
        return sb.toString();
    }
}
