package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.signin.internal.zzg;

public final class zzwy {
    public static final Api<zzxa> API = new Api<>("SignIn.API", fb, fa);
    public static final Api<zza> Hp = new Api<>("SignIn.INTERNAL_API", a, azY);
    static final com.google.android.gms.common.api.Api.zza<zzg, zza> a = new com.google.android.gms.common.api.Api.zza<zzg, zza>() {
        /* renamed from: a */
        public zzg zza(Context context, Looper looper, zzh zzh, zza zza, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzg zzg = new zzg(context, looper, false, zzh, zza.zzccz(), connectionCallbacks, onConnectionFailedListener);
            return zzg;
        }
    };
    public static final zzf<zzg> azY = new zzf<>();
    public static final zzf<zzg> fa = new zzf<>();
    public static final com.google.android.gms.common.api.Api.zza<zzg, zzxa> fb = new com.google.android.gms.common.api.Api.zza<zzg, zzxa>() {
        /* renamed from: a */
        public zzg zza(Context context, Looper looper, zzh zzh, zzxa zzxa, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            if (zzxa == null) {
                zzxa = zzxa.aAa;
            }
            zzg zzg = new zzg(context, looper, true, zzh, zzxa, connectionCallbacks, onConnectionFailedListener);
            return zzg;
        }
    };
    public static final Scope hd = new Scope(Scopes.PROFILE);
    public static final Scope he = new Scope("email");

    public static class zza implements HasOptions {
        public Bundle zzccz() {
            return null;
        }
    }
}
