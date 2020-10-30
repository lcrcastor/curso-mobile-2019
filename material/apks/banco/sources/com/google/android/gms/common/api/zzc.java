package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzpz;

public class zzc implements Result {
    private final Status a;
    private final ArrayMap<zzpz<?>, ConnectionResult> b;

    public zzc(Status status, ArrayMap<zzpz<?>, ConnectionResult> arrayMap) {
        this.a = status;
        this.b = arrayMap;
    }

    public Status getStatus() {
        return this.a;
    }
}
