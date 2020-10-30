package com.google.android.gms.internal;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class zzapu extends zzaot<Time> {
    public static final zzaou bmp = new zzaou() {
        public <T> zzaot<T> zza(zzaob zzaob, zzapx<T> zzapx) {
            if (zzapx.by() == Time.class) {
                return new zzapu();
            }
            return null;
        }
    };
    private final DateFormat a = new SimpleDateFormat("hh:mm:ss a");

    public synchronized void zza(zzaqa zzaqa, Time time) {
        zzaqa.zzut(time == null ? null : this.a.format(time));
    }

    /* renamed from: zzn */
    public synchronized Time zzb(zzapy zzapy) {
        if (zzapy.bn() == zzapz.NULL) {
            zzapy.nextNull();
            return null;
        }
        try {
            return new Time(this.a.parse(zzapy.nextString()).getTime());
        } catch (ParseException e) {
            throw new zzaoq((Throwable) e);
        }
    }
}
