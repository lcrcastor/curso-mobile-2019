package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzmr;
import java.util.ListIterator;

public class zza extends zzh<zza> {
    private final zzf a;
    private boolean b;

    public zza(zzf zzf) {
        super(zzf.zzaaq(), zzf.zzaan());
        this.a = zzf;
    }

    /* access modifiers changed from: 0000 */
    public zzf c() {
        return this.a;
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.b = z;
    }

    /* access modifiers changed from: protected */
    public void zza(zze zze) {
        zzmr zzmr = (zzmr) zze.zzb(zzmr.class);
        if (TextUtils.isEmpty(zzmr.zzxs())) {
            zzmr.setClientId(this.a.zzabe().zzacm());
        }
        if (this.b && TextUtils.isEmpty(zzmr.zzzp())) {
            com.google.android.gms.analytics.internal.zza zzabd = this.a.zzabd();
            zzmr.zzef(zzabd.zzaab());
            zzmr.zzar(zzabd.zzzq());
        }
    }

    public void zzdo(String str) {
        zzac.zzhz(str);
        zzdp(str);
        zzyi().add(new zzb(this.a, str));
    }

    public void zzdp(String str) {
        Uri a2 = zzb.a(str);
        ListIterator listIterator = zzyi().listIterator();
        while (listIterator.hasNext()) {
            if (a2.equals(((zzk) listIterator.next()).zzxl())) {
                listIterator.remove();
            }
        }
    }

    public zze zzxi() {
        zze zzxw = zzyh().zzxw();
        zzxw.zza((zzg) this.a.zzaav().zzabu());
        zzxw.zza((zzg) this.a.zzaaw().zzaeb());
        zzd(zzxw);
        return zzxw;
    }
}
