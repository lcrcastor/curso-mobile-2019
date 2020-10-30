package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class zzre {
    private final Set<zzrd<?>> a = Collections.newSetFromMap(new WeakHashMap());

    public void release() {
        for (zzrd clear : this.a) {
            clear.clear();
        }
        this.a.clear();
    }

    public <L> zzrd<L> zza(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzac.zzb(l, (Object) "Listener must not be null");
        zzac.zzb(looper, (Object) "Looper must not be null");
        zzac.zzb(str, (Object) "Listener type must not be null");
        zzrd<L> zzrd = new zzrd<>(looper, l, str);
        this.a.add(zzrd);
        return zzrd;
    }

    public <L> zzrd<L> zzb(@NonNull L l, Looper looper) {
        return zza(l, looper, "NO_TYPE");
    }
}
