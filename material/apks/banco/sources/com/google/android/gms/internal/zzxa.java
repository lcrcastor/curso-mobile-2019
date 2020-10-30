package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;

public final class zzxa implements Optional {
    public static final zzxa aAa = new zza().zzcdf();
    private final boolean a;
    private final boolean b;
    private final String c;
    private final boolean d;
    private final String e;
    private final boolean f;
    private final Long g;
    private final Long h;

    public static final class zza {
        public zzxa zzcdf() {
            zzxa zzxa = new zzxa(false, false, null, false, null, false, null, null);
            return zzxa;
        }
    }

    private zzxa(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
        this.a = z;
        this.b = z2;
        this.c = str;
        this.d = z3;
        this.f = z4;
        this.e = str2;
        this.g = l;
        this.h = l2;
    }

    public boolean zzahk() {
        return this.b;
    }

    public boolean zzahm() {
        return this.d;
    }

    public String zzahn() {
        return this.c;
    }

    @Nullable
    public String zzaho() {
        return this.e;
    }

    public boolean zzcdb() {
        return this.a;
    }

    public boolean zzcdc() {
        return this.f;
    }

    @Nullable
    public Long zzcdd() {
        return this.g;
    }

    @Nullable
    public Long zzcde() {
        return this.h;
    }
}
