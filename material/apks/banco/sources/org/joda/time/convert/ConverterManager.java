package org.joda.time.convert;

import org.joda.time.JodaTimePermission;

public final class ConverterManager {
    private static ConverterManager a;
    private ConverterSet b = new ConverterSet(new Converter[]{ReadableInstantConverter.a, StringConverter.a, CalendarConverter.a, DateConverter.a, LongConverter.a, NullConverter.a});
    private ConverterSet c = new ConverterSet(new Converter[]{ReadablePartialConverter.a, ReadableInstantConverter.a, StringConverter.a, CalendarConverter.a, DateConverter.a, LongConverter.a, NullConverter.a});
    private ConverterSet d = new ConverterSet(new Converter[]{ReadableDurationConverter.a, ReadableIntervalConverter.a, StringConverter.a, LongConverter.a, NullConverter.a});
    private ConverterSet e = new ConverterSet(new Converter[]{ReadableDurationConverter.a, ReadablePeriodConverter.a, ReadableIntervalConverter.a, StringConverter.a, NullConverter.a});
    private ConverterSet f = new ConverterSet(new Converter[]{ReadableIntervalConverter.a, StringConverter.a, NullConverter.a});

    public static ConverterManager getInstance() {
        if (a == null) {
            a = new ConverterManager();
        }
        return a;
    }

    protected ConverterManager() {
    }

    public InstantConverter getInstantConverter(Object obj) {
        InstantConverter instantConverter = (InstantConverter) this.b.a(obj == null ? null : obj.getClass());
        if (instantConverter != null) {
            return instantConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No instant converter found for type: ");
        sb.append(obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public InstantConverter[] getInstantConverters() {
        ConverterSet converterSet = this.b;
        InstantConverter[] instantConverterArr = new InstantConverter[converterSet.a()];
        converterSet.a((Converter[]) instantConverterArr);
        return instantConverterArr;
    }

    public InstantConverter addInstantConverter(InstantConverter instantConverter) {
        a();
        if (instantConverter == null) {
            return null;
        }
        InstantConverter[] instantConverterArr = new InstantConverter[1];
        this.b = this.b.a((Converter) instantConverter, (Converter[]) instantConverterArr);
        return instantConverterArr[0];
    }

    public InstantConverter removeInstantConverter(InstantConverter instantConverter) {
        a();
        if (instantConverter == null) {
            return null;
        }
        InstantConverter[] instantConverterArr = new InstantConverter[1];
        this.b = this.b.b(instantConverter, instantConverterArr);
        return instantConverterArr[0];
    }

    private void a() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterInstantConverters"));
        }
    }

    public PartialConverter getPartialConverter(Object obj) {
        PartialConverter partialConverter = (PartialConverter) this.c.a(obj == null ? null : obj.getClass());
        if (partialConverter != null) {
            return partialConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No partial converter found for type: ");
        sb.append(obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public PartialConverter[] getPartialConverters() {
        ConverterSet converterSet = this.c;
        PartialConverter[] partialConverterArr = new PartialConverter[converterSet.a()];
        converterSet.a((Converter[]) partialConverterArr);
        return partialConverterArr;
    }

    public PartialConverter addPartialConverter(PartialConverter partialConverter) {
        b();
        if (partialConverter == null) {
            return null;
        }
        PartialConverter[] partialConverterArr = new PartialConverter[1];
        this.c = this.c.a((Converter) partialConverter, (Converter[]) partialConverterArr);
        return partialConverterArr[0];
    }

    public PartialConverter removePartialConverter(PartialConverter partialConverter) {
        b();
        if (partialConverter == null) {
            return null;
        }
        PartialConverter[] partialConverterArr = new PartialConverter[1];
        this.c = this.c.b(partialConverter, partialConverterArr);
        return partialConverterArr[0];
    }

    private void b() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterPartialConverters"));
        }
    }

    public DurationConverter getDurationConverter(Object obj) {
        DurationConverter durationConverter = (DurationConverter) this.d.a(obj == null ? null : obj.getClass());
        if (durationConverter != null) {
            return durationConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No duration converter found for type: ");
        sb.append(obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public DurationConverter[] getDurationConverters() {
        ConverterSet converterSet = this.d;
        DurationConverter[] durationConverterArr = new DurationConverter[converterSet.a()];
        converterSet.a((Converter[]) durationConverterArr);
        return durationConverterArr;
    }

    public DurationConverter addDurationConverter(DurationConverter durationConverter) {
        c();
        if (durationConverter == null) {
            return null;
        }
        DurationConverter[] durationConverterArr = new DurationConverter[1];
        this.d = this.d.a((Converter) durationConverter, (Converter[]) durationConverterArr);
        return durationConverterArr[0];
    }

    public DurationConverter removeDurationConverter(DurationConverter durationConverter) {
        c();
        if (durationConverter == null) {
            return null;
        }
        DurationConverter[] durationConverterArr = new DurationConverter[1];
        this.d = this.d.b(durationConverter, durationConverterArr);
        return durationConverterArr[0];
    }

    private void c() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterDurationConverters"));
        }
    }

    public PeriodConverter getPeriodConverter(Object obj) {
        PeriodConverter periodConverter = (PeriodConverter) this.e.a(obj == null ? null : obj.getClass());
        if (periodConverter != null) {
            return periodConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No period converter found for type: ");
        sb.append(obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public PeriodConverter[] getPeriodConverters() {
        ConverterSet converterSet = this.e;
        PeriodConverter[] periodConverterArr = new PeriodConverter[converterSet.a()];
        converterSet.a((Converter[]) periodConverterArr);
        return periodConverterArr;
    }

    public PeriodConverter addPeriodConverter(PeriodConverter periodConverter) {
        d();
        if (periodConverter == null) {
            return null;
        }
        PeriodConverter[] periodConverterArr = new PeriodConverter[1];
        this.e = this.e.a((Converter) periodConverter, (Converter[]) periodConverterArr);
        return periodConverterArr[0];
    }

    public PeriodConverter removePeriodConverter(PeriodConverter periodConverter) {
        d();
        if (periodConverter == null) {
            return null;
        }
        PeriodConverter[] periodConverterArr = new PeriodConverter[1];
        this.e = this.e.b(periodConverter, periodConverterArr);
        return periodConverterArr[0];
    }

    private void d() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterPeriodConverters"));
        }
    }

    public IntervalConverter getIntervalConverter(Object obj) {
        IntervalConverter intervalConverter = (IntervalConverter) this.f.a(obj == null ? null : obj.getClass());
        if (intervalConverter != null) {
            return intervalConverter;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No interval converter found for type: ");
        sb.append(obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public IntervalConverter[] getIntervalConverters() {
        ConverterSet converterSet = this.f;
        IntervalConverter[] intervalConverterArr = new IntervalConverter[converterSet.a()];
        converterSet.a((Converter[]) intervalConverterArr);
        return intervalConverterArr;
    }

    public IntervalConverter addIntervalConverter(IntervalConverter intervalConverter) {
        e();
        if (intervalConverter == null) {
            return null;
        }
        IntervalConverter[] intervalConverterArr = new IntervalConverter[1];
        this.f = this.f.a((Converter) intervalConverter, (Converter[]) intervalConverterArr);
        return intervalConverterArr[0];
    }

    public IntervalConverter removeIntervalConverter(IntervalConverter intervalConverter) {
        e();
        if (intervalConverter == null) {
            return null;
        }
        IntervalConverter[] intervalConverterArr = new IntervalConverter[1];
        this.f = this.f.b(intervalConverter, intervalConverterArr);
        return intervalConverterArr[0];
    }

    private void e() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("ConverterManager.alterIntervalConverters"));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConverterManager[");
        sb.append(this.b.a());
        sb.append(" instant,");
        sb.append(this.c.a());
        sb.append(" partial,");
        sb.append(this.d.a());
        sb.append(" duration,");
        sb.append(this.e.a());
        sb.append(" period,");
        sb.append(this.f.a());
        sb.append(" interval]");
        return sb.toString();
    }
}
