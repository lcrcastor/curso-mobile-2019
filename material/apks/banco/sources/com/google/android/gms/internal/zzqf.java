package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzac;

public class zzqf implements ConnectionCallbacks, OnConnectionFailedListener {
    private final int a;
    private zzqg b;
    public final Api<?> tv;

    public zzqf(Api<?> api, int i) {
        this.tv = api;
        this.a = i;
    }

    private void a() {
        zzac.zzb(this.b, (Object) "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public void onConnected(@Nullable Bundle bundle) {
        a();
        this.b.onConnected(bundle);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        a();
        this.b.zza(connectionResult, this.tv, this.a);
    }

    public void onConnectionSuspended(int i) {
        a();
        this.b.onConnectionSuspended(i);
    }

    public void zza(zzqg zzqg) {
        this.b = zzqg;
    }
}
