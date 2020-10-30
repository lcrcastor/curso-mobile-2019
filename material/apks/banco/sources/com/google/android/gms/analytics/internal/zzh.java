package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzh {
    private final long a;
    private final String b;
    private final String c;
    private final boolean d;
    private long e;
    private final Map<String, String> f;

    public zzh(long j, String str, String str2, boolean z, long j2, Map<String, String> map) {
        zzac.zzhz(str);
        zzac.zzhz(str2);
        this.a = j;
        this.b = str;
        this.c = str2;
        this.d = z;
        this.e = j2;
        this.f = map != null ? new HashMap<>(map) : Collections.emptyMap();
    }

    public long zzabf() {
        return this.a;
    }

    public String zzabg() {
        return this.c;
    }

    public boolean zzabh() {
        return this.d;
    }

    public long zzabi() {
        return this.e;
    }

    public Map<String, String> zzm() {
        return this.f;
    }

    public void zzr(long j) {
        this.e = j;
    }

    public String zzxs() {
        return this.b;
    }
}
