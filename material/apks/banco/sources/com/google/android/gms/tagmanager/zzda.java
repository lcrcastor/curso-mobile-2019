package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;

class zzda implements zzcl {
    private final long a;
    private final int b;
    private double c;
    private long d;
    private final Object e;
    private final zze f;

    public zzda() {
        this(60, 2000);
    }

    public zzda(int i, long j) {
        this.e = new Object();
        this.b = i;
        this.c = (double) this.b;
        this.a = j;
        this.f = zzh.zzaxj();
    }

    public boolean a() {
        synchronized (this.e) {
            long currentTimeMillis = this.f.currentTimeMillis();
            if (this.c < ((double) this.b)) {
                double d2 = ((double) (currentTimeMillis - this.d)) / ((double) this.a);
                if (d2 > 0.0d) {
                    this.c = Math.min((double) this.b, this.c + d2);
                }
            }
            this.d = currentTimeMillis;
            if (this.c >= 1.0d) {
                this.c -= 1.0d;
                return true;
            }
            zzbo.zzdf("No more tokens available.");
            return false;
        }
    }
}
