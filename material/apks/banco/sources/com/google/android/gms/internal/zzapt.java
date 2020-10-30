package com.google.android.gms.internal;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class zzapt extends zzaot<Date> {
    public static final zzaou bmp = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            if (zzapx.by() == Date.class) {
                return new zzapt();
            }
            return null;
        }
    };
    private final DateFormat a = new SimpleDateFormat("MMM d, yyyy");

    public synchronized void zza(zzaqa zzaqa, Date date) {
        zzaqa.zzut(date == null ? null : this.a.format(date));
    }

    /* renamed from: zzm */
    public synchronized Date zzb(zzapy zzapy) {
        if (zzapy.bn() == zzapz.NULL) {
            zzapy.nextNull();
            return null;
        }
        try {
            return new Date(this.a.parse(zzapy.nextString()).getTime());
        } catch (ParseException e) {
            throw new zzaoq((Throwable) e);
        }
    }
}
