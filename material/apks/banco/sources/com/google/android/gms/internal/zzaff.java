package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.util.HashMap;
import java.util.Map;

public class zzaff {
    Map<String, Object> a;
    private final Context b;
    private final zzafh c;
    private final zze d;
    private String e;
    private final Map<String, Object> f;

    public zzaff(Context context) {
        this(context, new HashMap(), new zzafh(context), zzh.zzaxj());
    }

    zzaff(Context context, Map<String, Object> map, zzafh zzafh, zze zze) {
        this.e = null;
        this.a = new HashMap();
        this.b = context;
        this.d = zze;
        this.c = zzafh;
        this.f = map;
    }

    public void zzqz(String str) {
        this.e = str;
    }
}
