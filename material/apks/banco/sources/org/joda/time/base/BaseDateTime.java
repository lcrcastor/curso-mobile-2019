package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.InstantConverter;

public abstract class BaseDateTime extends AbstractDateTime implements Serializable, ReadableDateTime {
    private static final long serialVersionUID = -6728882245981L;
    private volatile long a;
    private volatile Chronology b;

    public long checkInstant(long j, Chronology chronology) {
        return j;
    }

    public BaseDateTime() {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance());
    }

    public BaseDateTime(DateTimeZone dateTimeZone) {
        this(DateTimeUtils.currentTimeMillis(), (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public BaseDateTime(Chronology chronology) {
        this(DateTimeUtils.currentTimeMillis(), chronology);
    }

    public BaseDateTime(long j) {
        this(j, (Chronology) ISOChronology.getInstance());
    }

    public BaseDateTime(long j, DateTimeZone dateTimeZone) {
        this(j, (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public BaseDateTime(long j, Chronology chronology) {
        this.b = checkChronology(chronology);
        this.a = checkInstant(j, this.b);
        a();
    }

    public BaseDateTime(Object obj, DateTimeZone dateTimeZone) {
        InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(obj);
        Chronology checkChronology = checkChronology(instantConverter.getChronology(obj, dateTimeZone));
        this.b = checkChronology;
        this.a = checkInstant(instantConverter.getInstantMillis(obj, checkChronology), checkChronology);
        a();
    }

    public BaseDateTime(Object obj, Chronology chronology) {
        InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(obj);
        this.b = checkChronology(instantConverter.getChronology(obj, chronology));
        this.a = checkInstant(instantConverter.getInstantMillis(obj, chronology), this.b);
        a();
    }

    public BaseDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(i, i2, i3, i4, i5, i6, i7, (Chronology) ISOChronology.getInstance());
    }

    public BaseDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, DateTimeZone dateTimeZone) {
        this(i, i2, i3, i4, i5, i6, i7, (Chronology) ISOChronology.getInstance(dateTimeZone));
    }

    public BaseDateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, Chronology chronology) {
        this.b = checkChronology(chronology);
        this.a = checkInstant(this.b.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7), this.b);
        a();
    }

    private void a() {
        if (this.a == Long.MIN_VALUE || this.a == Long.MAX_VALUE) {
            this.b = this.b.withUTC();
        }
    }

    /* access modifiers changed from: protected */
    public Chronology checkChronology(Chronology chronology) {
        return DateTimeUtils.getChronology(chronology);
    }

    public long getMillis() {
        return this.a;
    }

    public Chronology getChronology() {
        return this.b;
    }

    public void setMillis(long j) {
        this.a = checkInstant(j, this.b);
    }

    public void setChronology(Chronology chronology) {
        this.b = checkChronology(chronology);
    }
}
