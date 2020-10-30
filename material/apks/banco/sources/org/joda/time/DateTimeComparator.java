package org.joda.time;

import java.io.Serializable;
import java.util.Comparator;
import org.bouncycastle.asn1.eac.EACTags;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.InstantConverter;

public class DateTimeComparator implements Serializable, Comparator<Object> {
    private static final DateTimeComparator a = new DateTimeComparator(null, null);
    private static final DateTimeComparator b = new DateTimeComparator(DateTimeFieldType.dayOfYear(), null);
    private static final DateTimeComparator c = new DateTimeComparator(null, DateTimeFieldType.dayOfYear());
    private static final long serialVersionUID = -6097339773320178364L;
    private final DateTimeFieldType d;
    private final DateTimeFieldType e;

    public static DateTimeComparator getInstance() {
        return a;
    }

    public static DateTimeComparator getInstance(DateTimeFieldType dateTimeFieldType) {
        return getInstance(dateTimeFieldType, null);
    }

    public static DateTimeComparator getInstance(DateTimeFieldType dateTimeFieldType, DateTimeFieldType dateTimeFieldType2) {
        if (dateTimeFieldType == null && dateTimeFieldType2 == null) {
            return a;
        }
        if (dateTimeFieldType == DateTimeFieldType.dayOfYear() && dateTimeFieldType2 == null) {
            return b;
        }
        if (dateTimeFieldType == null && dateTimeFieldType2 == DateTimeFieldType.dayOfYear()) {
            return c;
        }
        return new DateTimeComparator(dateTimeFieldType, dateTimeFieldType2);
    }

    public static DateTimeComparator getDateOnlyInstance() {
        return b;
    }

    public static DateTimeComparator getTimeOnlyInstance() {
        return c;
    }

    protected DateTimeComparator(DateTimeFieldType dateTimeFieldType, DateTimeFieldType dateTimeFieldType2) {
        this.d = dateTimeFieldType;
        this.e = dateTimeFieldType2;
    }

    public DateTimeFieldType getLowerLimit() {
        return this.d;
    }

    public DateTimeFieldType getUpperLimit() {
        return this.e;
    }

    public int compare(Object obj, Object obj2) {
        InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(obj);
        Chronology chronology = null;
        Chronology chronology2 = instantConverter.getChronology(obj, chronology);
        long instantMillis = instantConverter.getInstantMillis(obj, chronology2);
        InstantConverter instantConverter2 = ConverterManager.getInstance().getInstantConverter(obj2);
        Chronology chronology3 = instantConverter2.getChronology(obj2, chronology);
        long instantMillis2 = instantConverter2.getInstantMillis(obj2, chronology3);
        if (this.d != null) {
            instantMillis = this.d.getField(chronology2).roundFloor(instantMillis);
            instantMillis2 = this.d.getField(chronology3).roundFloor(instantMillis2);
        }
        if (this.e != null) {
            instantMillis = this.e.getField(chronology2).remainder(instantMillis);
            instantMillis2 = this.e.getField(chronology3).remainder(instantMillis2);
        }
        if (instantMillis < instantMillis2) {
            return -1;
        }
        return instantMillis > instantMillis2 ? 1 : 0;
    }

    private Object readResolve() {
        return getInstance(this.d, this.e);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DateTimeComparator)) {
            return false;
        }
        DateTimeComparator dateTimeComparator = (DateTimeComparator) obj;
        if ((this.d == dateTimeComparator.getLowerLimit() || (this.d != null && this.d.equals(dateTimeComparator.getLowerLimit()))) && (this.e == dateTimeComparator.getUpperLimit() || (this.e != null && this.e.equals(dateTimeComparator.getUpperLimit())))) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.d == null ? 0 : this.d.hashCode();
        if (this.e != null) {
            i = this.e.hashCode();
        }
        return hashCode + (i * EACTags.SECURITY_ENVIRONMENT_TEMPLATE);
    }

    public String toString() {
        if (this.d == this.e) {
            StringBuilder sb = new StringBuilder();
            sb.append("DateTimeComparator[");
            sb.append(this.d == null ? "" : this.d.getName());
            sb.append("]");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("DateTimeComparator[");
        sb2.append(this.d == null ? "" : this.d.getName());
        sb2.append("-");
        sb2.append(this.e == null ? "" : this.e.getName());
        sb2.append("]");
        return sb2.toString();
    }
}
