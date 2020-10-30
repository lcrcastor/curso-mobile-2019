package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzmi;

public class zzk extends zzd {
    private final zzmi a = new zzmi();

    zzk(zzf zzf) {
        super(zzf);
    }

    public zzmi zzabu() {
        zzaax();
        return this.a;
    }

    public void zzxq() {
        zzap zzxv = zzxv();
        String zzys = zzxv.zzys();
        if (zzys != null) {
            this.a.setAppName(zzys);
        }
        String zzyt = zzxv.zzyt();
        if (zzyt != null) {
            this.a.setAppVersion(zzyt);
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        zzaaq().zzyj().zzb(this.a);
        zzxq();
    }
}
