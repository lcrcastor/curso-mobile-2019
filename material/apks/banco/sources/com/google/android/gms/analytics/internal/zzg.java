package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;

public class zzg {
    private final Context a;
    private final Context b;

    public zzg(Context context) {
        zzac.zzy(context);
        Context applicationContext = context.getApplicationContext();
        zzac.zzb(applicationContext, (Object) "Application context can't be null");
        this.a = applicationContext;
        this.b = applicationContext;
    }

    /* access modifiers changed from: 0000 */
    public zzl a(zzf zzf) {
        return new zzl(zzf, this);
    }

    /* access modifiers changed from: 0000 */
    public zzag b(zzf zzf) {
        return new zzag(zzf);
    }

    public Context getApplicationContext() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public zzu zza(zzf zzf) {
        return new zzu(zzf);
    }

    public Context zzaaz() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public zzk zzb(zzf zzf) {
        return new zzk(zzf);
    }

    /* access modifiers changed from: protected */
    public zzi zzba(Context context) {
        return zzi.zzay(context);
    }

    /* access modifiers changed from: protected */
    public zza zzc(zzf zzf) {
        return new zza(zzf);
    }

    /* access modifiers changed from: protected */
    public zzn zzd(zzf zzf) {
        return new zzn(zzf);
    }

    /* access modifiers changed from: protected */
    public zzap zze(zzf zzf) {
        return new zzap(zzf);
    }

    /* access modifiers changed from: protected */
    public zzaf zzf(zzf zzf) {
        return new zzaf(zzf);
    }

    /* access modifiers changed from: protected */
    public zzr zzg(zzf zzf) {
        return new zzr(zzf);
    }

    /* access modifiers changed from: protected */
    public zze zzh(zzf zzf) {
        return zzh.zzaxj();
    }

    /* access modifiers changed from: protected */
    public GoogleAnalytics zzi(zzf zzf) {
        return new GoogleAnalytics(zzf);
    }

    /* access modifiers changed from: protected */
    public zzb zzl(zzf zzf) {
        return new zzb(zzf, this);
    }

    public zzj zzm(zzf zzf) {
        return new zzj(zzf);
    }

    public zzah zzn(zzf zzf) {
        return new zzah(zzf);
    }

    public zzi zzo(zzf zzf) {
        return new zzi(zzf);
    }

    public zzv zzp(zzf zzf) {
        return new zzv(zzf);
    }

    public zzai zzq(zzf zzf) {
        return new zzai(zzf);
    }
}
