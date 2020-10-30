package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzd;
import com.google.android.gms.internal.zzqc.zza;

public class zzqu<O extends ApiOptions> extends zzql {
    private final zzd<O> a;

    public zzqu(zzd<O> zzd) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.a = zzd;
    }

    public Looper getLooper() {
        return this.a.getLooper();
    }

    public void zza(zzrp zzrp) {
        this.a.zzapu();
    }

    public void zzb(zzrp zzrp) {
        this.a.zzapv();
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@NonNull T t) {
        return this.a.zza(t);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(@NonNull T t) {
        return this.a.zzb(t);
    }
}
