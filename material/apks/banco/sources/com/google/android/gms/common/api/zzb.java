package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzpz;

public class zzb extends zzc {
    private final ConnectionResult a;

    public zzb(Status status, ArrayMap<zzpz<?>, ConnectionResult> arrayMap) {
        super(status, arrayMap);
        this.a = (ConnectionResult) arrayMap.get(arrayMap.keyAt(0));
    }
}
