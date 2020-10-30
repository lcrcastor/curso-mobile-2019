package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.api.zzc;
import java.util.Set;

public final class zzqb extends zzqe<zzc> {
    private int b;
    private boolean c;

    private void a(ConnectionResult connectionResult) {
        int i = 0;
        while (true) {
            ArrayMap arrayMap = null;
            if (i < arrayMap.size()) {
                zza((zzpz) arrayMap.keyAt(i), connectionResult);
                i++;
            } else {
                return;
            }
        }
    }

    public void zza(zzpz<?> zzpz, ConnectionResult connectionResult) {
        ArrayMap arrayMap = null;
        synchronized (arrayMap) {
            arrayMap.put(zzpz, connectionResult);
            this.b--;
            if (!connectionResult.isSuccess()) {
                this.c = true;
            }
            if (this.b == 0) {
                Status status = this.c ? new Status(13) : Status.vY;
                zzc(arrayMap.size() == 1 ? new zzb(status, arrayMap) : new zzc(status, arrayMap));
            }
        }
    }

    public Set<zzpz<?>> zzaqm() {
        ArrayMap arrayMap = null;
        return arrayMap.keySet();
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzy */
    public zzc zzc(Status status) {
        zzc zzb;
        ArrayMap arrayMap = null;
        synchronized (arrayMap) {
            a(new ConnectionResult(8));
            zzb = arrayMap.size() == 1 ? new zzb(status, arrayMap) : new zzc(status, arrayMap);
        }
        return zzb;
    }
}
