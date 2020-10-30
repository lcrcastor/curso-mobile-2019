package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class zzanw implements zzaog<Date>, zzaop<Date> {
    private final DateFormat a;
    private final DateFormat b;
    private final DateFormat c;

    zzanw() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public zzanw(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    zzanw(String str) {
        this((DateFormat) new SimpleDateFormat(str, Locale.US), (DateFormat) new SimpleDateFormat(str));
    }

    zzanw(DateFormat dateFormat, DateFormat dateFormat2) {
        this.a = dateFormat;
        this.b = dateFormat2;
        this.c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.c.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:13|14|15|16|17) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:8|9|10|11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1 = r3.c.parse(r4.aR());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0028, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0029, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0033, code lost:
        throw new com.google.android.gms.internal.zzaoq(r4.aR(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r1 = r3.a.parse(r4.aR());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x001d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0011 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Date a(com.google.android.gms.internal.zzaoh r4) {
        /*
            r3 = this;
            java.text.DateFormat r0 = r3.b
            monitor-enter(r0)
            java.text.DateFormat r1 = r3.b     // Catch:{ ParseException -> 0x0011 }
            java.lang.String r2 = r4.aR()     // Catch:{ ParseException -> 0x0011 }
            java.util.Date r1 = r1.parse(r2)     // Catch:{ ParseException -> 0x0011 }
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            return r1
        L_0x000f:
            r4 = move-exception
            goto L_0x0034
        L_0x0011:
            java.text.DateFormat r1 = r3.a     // Catch:{ ParseException -> 0x001d }
            java.lang.String r2 = r4.aR()     // Catch:{ ParseException -> 0x001d }
            java.util.Date r1 = r1.parse(r2)     // Catch:{ ParseException -> 0x001d }
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            return r1
        L_0x001d:
            java.text.DateFormat r1 = r3.c     // Catch:{ ParseException -> 0x0029 }
            java.lang.String r2 = r4.aR()     // Catch:{ ParseException -> 0x0029 }
            java.util.Date r1 = r1.parse(r2)     // Catch:{ ParseException -> 0x0029 }
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            return r1
        L_0x0029:
            r1 = move-exception
            com.google.android.gms.internal.zzaoq r2 = new com.google.android.gms.internal.zzaoq     // Catch:{ all -> 0x000f }
            java.lang.String r4 = r4.aR()     // Catch:{ all -> 0x000f }
            r2.<init>(r4, r1)     // Catch:{ all -> 0x000f }
            throw r2     // Catch:{ all -> 0x000f }
        L_0x0034:
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzanw.a(com.google.android.gms.internal.zzaoh):java.util.Date");
    }

    /* renamed from: a */
    public zzaoh zza(Date date, Type type, zzaoo zzaoo) {
        zzaon zzaon;
        synchronized (this.b) {
            zzaon = new zzaon(this.a.format(date));
        }
        return zzaon;
    }

    /* renamed from: a */
    public Date zzb(zzaoh zzaoh, Type type, zzaof zzaof) {
        if (!(zzaoh instanceof zzaon)) {
            throw new zzaol("The date should be a string value");
        }
        Date a2 = a(zzaoh);
        if (type == Date.class) {
            return a2;
        }
        if (type == Timestamp.class) {
            return new Timestamp(a2.getTime());
        }
        if (type == java.sql.Date.class) {
            return new java.sql.Date(a2.getTime());
        }
        String valueOf = String.valueOf(getClass());
        String valueOf2 = String.valueOf(type);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23 + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append(" cannot deserialize to ");
        sb.append(valueOf2);
        throw new IllegalArgumentException(sb.toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(zzanw.class.getSimpleName());
        sb.append('(');
        sb.append(this.b.getClass().getSimpleName());
        sb.append(')');
        return sb.toString();
    }
}
