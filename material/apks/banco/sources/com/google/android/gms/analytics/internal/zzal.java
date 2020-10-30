package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;

class zzal {
    private final zze a;
    private long b;

    public zzal(zze zze) {
        zzac.zzy(zze);
        this.a = zze;
    }

    public zzal(zze zze, long j) {
        zzac.zzy(zze);
        this.a = zze;
        this.b = j;
    }

    public void a() {
        this.b = this.a.elapsedRealtime();
    }

    public boolean a(long j) {
        return this.b == 0 || this.a.elapsedRealtime() - this.b > j;
    }

    public void b() {
        this.b = 0;
    }
}
