package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzab;

public final class zzpz<O extends ApiOptions> {
    private final boolean a = false;
    private final int b;
    private final Api<O> c;
    private final O d;

    private zzpz(Api<O> api, O o) {
        this.c = api;
        this.d = o;
        this.b = zzab.hashCode(this.c, this.d);
    }

    public static <O extends ApiOptions> zzpz<O> zza(Api<O> api, O o) {
        return new zzpz<>(api, o);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzpz)) {
            return false;
        }
        zzpz zzpz = (zzpz) obj;
        return zzab.equal(this.c, zzpz.c) && zzab.equal(this.d, zzpz.d);
    }

    public int hashCode() {
        return this.b;
    }

    public String zzaqj() {
        return this.c.getName();
    }
}
