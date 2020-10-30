package com.google.android.gms.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class zzapm extends zzaot<Date> {
    public static final zzaou bmp = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            if (zzapx.by() == Date.class) {
                return new zzapm();
            }
            return null;
        }
    };
    private final DateFormat a = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat b = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat c = a();

    private static DateFormat a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:6|7|8|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001a, code lost:
        return r2.c.parse(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0021, code lost:
        throw new com.google.android.gms.internal.zzaoq(r3, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        return r2.a.parse(r3);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0013 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.Date a(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.text.DateFormat r0 = r2.b     // Catch:{ ParseException -> 0x000b }
            java.util.Date r0 = r0.parse(r3)     // Catch:{ ParseException -> 0x000b }
            monitor-exit(r2)
            return r0
        L_0x0009:
            r3 = move-exception
            goto L_0x0022
        L_0x000b:
            java.text.DateFormat r0 = r2.a     // Catch:{ ParseException -> 0x0013 }
            java.util.Date r0 = r0.parse(r3)     // Catch:{ ParseException -> 0x0013 }
            monitor-exit(r2)
            return r0
        L_0x0013:
            java.text.DateFormat r0 = r2.c     // Catch:{ ParseException -> 0x001b }
            java.util.Date r0 = r0.parse(r3)     // Catch:{ ParseException -> 0x001b }
            monitor-exit(r2)
            return r0
        L_0x001b:
            r0 = move-exception
            com.google.android.gms.internal.zzaoq r1 = new com.google.android.gms.internal.zzaoq     // Catch:{ all -> 0x0009 }
            r1.<init>(r3, r0)     // Catch:{ all -> 0x0009 }
            throw r1     // Catch:{ all -> 0x0009 }
        L_0x0022:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapm.a(java.lang.String):java.util.Date");
    }

    public synchronized void zza(zzaqa zzaqa, Date date) {
        if (date == null) {
            zzaqa.bx();
        } else {
            zzaqa.zzut(this.a.format(date));
        }
    }

    /* renamed from: zzk */
    public Date zzb(zzapy zzapy) {
        if (zzapy.bn() != zzapz.NULL) {
            return a(zzapy.nextString());
        }
        zzapy.nextNull();
        return null;
    }
}
