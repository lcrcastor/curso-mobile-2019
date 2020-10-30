package org.joda.time.chrono;

import android.support.media.ExifInterface;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.RemainderDateTimeField;

public final class ISOChronology extends AssembledChronology {
    private static final ISOChronology a = new ISOChronology(GregorianChronology.getInstanceUTC());
    private static final ConcurrentHashMap<DateTimeZone, ISOChronology> b = new ConcurrentHashMap<>();
    private static final long serialVersionUID = -6212696554273812441L;

    static final class Stub implements Serializable {
        private static final long serialVersionUID = -6212696554273812441L;
        private transient DateTimeZone a;

        Stub(DateTimeZone dateTimeZone) {
            this.a = dateTimeZone;
        }

        private Object readResolve() {
            return ISOChronology.getInstance(this.a);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeObject(this.a);
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = (DateTimeZone) objectInputStream.readObject();
        }
    }

    static {
        b.put(DateTimeZone.UTC, a);
    }

    public static ISOChronology getInstanceUTC() {
        return a;
    }

    public static ISOChronology getInstance() {
        return getInstance(DateTimeZone.getDefault());
    }

    public static ISOChronology getInstance(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        ISOChronology iSOChronology = (ISOChronology) b.get(dateTimeZone);
        if (iSOChronology != null) {
            return iSOChronology;
        }
        ISOChronology iSOChronology2 = new ISOChronology(ZonedChronology.getInstance(a, dateTimeZone));
        ISOChronology iSOChronology3 = (ISOChronology) b.putIfAbsent(dateTimeZone, iSOChronology2);
        return iSOChronology3 != null ? iSOChronology3 : iSOChronology2;
    }

    private ISOChronology(Chronology chronology) {
        super(chronology, null);
    }

    public Chronology withUTC() {
        return a;
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        if (dateTimeZone == getZone()) {
            return this;
        }
        return getInstance(dateTimeZone);
    }

    public String toString() {
        String str = "ISOChronology";
        DateTimeZone zone = getZone();
        if (zone == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append('[');
        sb.append(zone.getID());
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void assemble(Fields fields) {
        if (getBase().getZone() == DateTimeZone.UTC) {
            fields.centuryOfEra = new DividedDateTimeField(ISOYearOfEraDateTimeField.a, DateTimeFieldType.centuryOfEra(), 100);
            fields.centuries = fields.centuryOfEra.getDurationField();
            fields.yearOfCentury = new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra, DateTimeFieldType.yearOfCentury());
            fields.weekyearOfCentury = new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra, fields.weekyears, DateTimeFieldType.weekyearOfCentury());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ISOChronology)) {
            return false;
        }
        return getZone().equals(((ISOChronology) obj).getZone());
    }

    public int hashCode() {
        return (ExifInterface.TAG_RW2_ISO.hashCode() * 11) + getZone().hashCode();
    }

    private Object writeReplace() {
        return new Stub(getZone());
    }
}
