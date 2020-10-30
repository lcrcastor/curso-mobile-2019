package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzuw.zzd;
import java.util.ArrayList;
import java.util.Collection;

public class zzux {
    private final Collection<zzuw> a = new ArrayList();
    private final Collection<zzd> b = new ArrayList();
    private final Collection<zzd> c = new ArrayList();

    public static void initialize(Context context) {
        zzva.zzbhn().initialize(context);
    }

    public void zza(zzuw zzuw) {
        this.a.add(zzuw);
    }
}
