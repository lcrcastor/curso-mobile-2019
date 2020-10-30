package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzh;

public final class zzrx {
    public static final Api<NoOptions> API = new Api<>("Common.API", a, fa);
    public static final zzry Dh = new zzrz();
    private static final zza<zzsb, NoOptions> a = new zza<zzsb, NoOptions>() {
        /* renamed from: a */
        public zzsb zza(Context context, Looper looper, zzh zzh, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzsb zzsb = new zzsb(context, looper, zzh, connectionCallbacks, onConnectionFailedListener);
            return zzsb;
        }
    };
    public static final zzf<zzsb> fa = new zzf<>();
}
