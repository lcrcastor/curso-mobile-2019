package com.google.android.gms.internal;

import com.android.volley.DefaultRetryPolicy;

public class zzd implements zzo {
    private int a;
    private int b;
    private final int c;
    private final float d;

    public zzd() {
        this(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 1, 1.0f);
    }

    public zzd(int i, int i2, float f) {
        this.a = i;
        this.c = i2;
        this.d = f;
    }

    public void zza(zzr zzr) {
        this.b++;
        this.a = (int) (((float) this.a) + (((float) this.a) * this.d));
        if (!zze()) {
            throw zzr;
        }
    }

    public int zzc() {
        return this.a;
    }

    public int zzd() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public boolean zze() {
        return this.b <= this.c;
    }
}
