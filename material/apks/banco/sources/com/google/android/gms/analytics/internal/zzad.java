package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.util.zze;

public class zzad {
    private final long a;
    private final int b;
    private double c;
    private long d;
    private final Object e;
    private final String f;
    private final zze g;

    public zzad(int i, long j, String str, zze zze) {
        this.e = new Object();
        this.b = i;
        this.c = (double) this.b;
        this.a = j;
        this.f = str;
        this.g = zze;
    }

    public zzad(String str, zze zze) {
        this(60, 2000, str, zze);
    }

    public boolean zzaev() {
        synchronized (this.e) {
            long currentTimeMillis = this.g.currentTimeMillis();
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
            String str = this.f;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
            sb.append("Excessive ");
            sb.append(str);
            sb.append(" detected; call ignored.");
            zzae.zzdf(sb.toString());
            return false;
        }
    }
}
