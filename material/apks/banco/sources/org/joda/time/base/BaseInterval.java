package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.MutableInterval;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadableInterval;
import org.joda.time.ReadablePeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.IntervalConverter;
import org.joda.time.field.FieldUtils;

public abstract class BaseInterval extends AbstractInterval implements Serializable, ReadableInterval {
    private static final long serialVersionUID = 576586928732749278L;
    private volatile Chronology a;
    private volatile long b;
    private volatile long c;

    protected BaseInterval(long j, long j2, Chronology chronology) {
        this.a = DateTimeUtils.getChronology(chronology);
        checkInterval(j, j2);
        this.b = j;
        this.c = j2;
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        if (readableInstant == null && readableInstant2 == null) {
            long currentTimeMillis = DateTimeUtils.currentTimeMillis();
            this.c = currentTimeMillis;
            this.b = currentTimeMillis;
            this.a = ISOChronology.getInstance();
            return;
        }
        this.a = DateTimeUtils.getInstantChronology(readableInstant);
        this.b = DateTimeUtils.getInstantMillis(readableInstant);
        this.c = DateTimeUtils.getInstantMillis(readableInstant2);
        checkInterval(this.b, this.c);
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadableDuration readableDuration) {
        this.a = DateTimeUtils.getInstantChronology(readableInstant);
        this.b = DateTimeUtils.getInstantMillis(readableInstant);
        this.c = FieldUtils.safeAdd(this.b, DateTimeUtils.getDurationMillis(readableDuration));
        checkInterval(this.b, this.c);
    }

    protected BaseInterval(ReadableDuration readableDuration, ReadableInstant readableInstant) {
        this.a = DateTimeUtils.getInstantChronology(readableInstant);
        this.c = DateTimeUtils.getInstantMillis(readableInstant);
        this.b = FieldUtils.safeAdd(this.c, -DateTimeUtils.getDurationMillis(readableDuration));
        checkInterval(this.b, this.c);
    }

    protected BaseInterval(ReadableInstant readableInstant, ReadablePeriod readablePeriod) {
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.a = instantChronology;
        this.b = DateTimeUtils.getInstantMillis(readableInstant);
        if (readablePeriod == null) {
            this.c = this.b;
        } else {
            this.c = instantChronology.add(readablePeriod, this.b, 1);
        }
        checkInterval(this.b, this.c);
    }

    protected BaseInterval(ReadablePeriod readablePeriod, ReadableInstant readableInstant) {
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.a = instantChronology;
        this.c = DateTimeUtils.getInstantMillis(readableInstant);
        if (readablePeriod == null) {
            this.b = this.c;
        } else {
            this.b = instantChronology.add(readablePeriod, this.c, -1);
        }
        checkInterval(this.b, this.c);
    }

    protected BaseInterval(Object obj, Chronology chronology) {
        IntervalConverter intervalConverter = ConverterManager.getInstance().getIntervalConverter(obj);
        if (intervalConverter.isReadableInterval(obj, chronology)) {
            ReadableInterval readableInterval = (ReadableInterval) obj;
            if (chronology == null) {
                chronology = readableInterval.getChronology();
            }
            this.a = chronology;
            this.b = readableInterval.getStartMillis();
            this.c = readableInterval.getEndMillis();
        } else if (this instanceof ReadWritableInterval) {
            intervalConverter.setInto((ReadWritableInterval) this, obj, chronology);
        } else {
            MutableInterval mutableInterval = new MutableInterval();
            intervalConverter.setInto(mutableInterval, obj, chronology);
            this.a = mutableInterval.getChronology();
            this.b = mutableInterval.getStartMillis();
            this.c = mutableInterval.getEndMillis();
        }
        checkInterval(this.b, this.c);
    }

    public Chronology getChronology() {
        return this.a;
    }

    public long getStartMillis() {
        return this.b;
    }

    public long getEndMillis() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void setInterval(long j, long j2, Chronology chronology) {
        checkInterval(j, j2);
        this.b = j;
        this.c = j2;
        this.a = DateTimeUtils.getChronology(chronology);
    }
}
