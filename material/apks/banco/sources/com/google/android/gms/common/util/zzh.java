package com.google.android.gms.common.util;

import android.os.SystemClock;

public final class zzh implements zze {
    private static zzh a;

    public static synchronized zze zzaxj() {
        zzh zzh;
        synchronized (zzh.class) {
            if (a == null) {
                a = new zzh();
            }
            zzh = a;
        }
        return zzh;
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long nanoTime() {
        return System.nanoTime();
    }
}
