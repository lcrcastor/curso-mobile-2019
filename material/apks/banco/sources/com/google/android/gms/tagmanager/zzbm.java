package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;

class zzbm implements zzcl {
    private final long a;
    private final long b;
    private final int c;
    private double d;
    private long e;
    private final Object f = new Object();
    private final String g;
    private final zze h;

    public zzbm(int i, int i2, long j, long j2, String str, zze zze) {
        this.c = i2;
        this.d = (double) Math.min(i, i2);
        this.a = j;
        this.b = j2;
        this.g = str;
        this.h = zze;
    }

    public boolean a() {
        synchronized (this.f) {
            long currentTimeMillis = this.h.currentTimeMillis();
            if (currentTimeMillis - this.e < this.b) {
                String str = this.g;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Excessive ");
                sb.append(str);
                sb.append(" detected; call ignored.");
                zzbo.zzdf(sb.toString());
                return false;
            }
            if (this.d < ((double) this.c)) {
                double d2 = ((double) (currentTimeMillis - this.e)) / ((double) this.a);
                if (d2 > 0.0d) {
                    this.d = Math.min((double) this.c, this.d + d2);
                }
            }
            this.e = currentTimeMillis;
            if (this.d >= 1.0d) {
                this.d -= 1.0d;
                return true;
            }
            String str2 = this.g;
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 34);
            sb2.append("Excessive ");
            sb2.append(str2);
            sb2.append(" detected; call ignored.");
            zzbo.zzdf(sb2.toString());
            return false;
        }
    }
}
