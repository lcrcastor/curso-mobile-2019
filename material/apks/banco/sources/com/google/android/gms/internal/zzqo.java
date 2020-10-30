package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzqc.zza;
import java.util.Collections;

public class zzqo implements zzqq {
    private final zzqr a;

    public zzqo(zzqr zzqr) {
        this.a = zzqr;
    }

    public void begin() {
        this.a.c();
        this.a.g.d = Collections.emptySet();
    }

    public void connect() {
        this.a.a();
    }

    public boolean disconnect() {
        return true;
    }

    public void onConnected(Bundle bundle) {
    }

    public void onConnectionSuspended(int i) {
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(T t) {
        this.a.g.a.add(t);
        return t;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
