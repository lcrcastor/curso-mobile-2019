package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze.zzb;
import com.google.android.gms.common.internal.zze.zzc;
import com.google.android.gms.common.internal.zzm.zza;
import java.util.Set;

public abstract class zzl<T extends IInterface> extends zze<T> implements zze, zza {
    private final zzh b;
    private final Set<Scope> c;
    private final Account d;

    protected zzl(Context context, Looper looper, int i, zzh zzh, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzn.zzcf(context), GoogleApiAvailability.getInstance(), i, zzh, (ConnectionCallbacks) zzac.zzy(connectionCallbacks), (OnConnectionFailedListener) zzac.zzy(onConnectionFailedListener));
    }

    protected zzl(Context context, Looper looper, zzn zzn, GoogleApiAvailability googleApiAvailability, int i, zzh zzh, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, zzn, googleApiAvailability, i, a(connectionCallbacks), a(onConnectionFailedListener), zzh.zzauk());
        this.b = zzh;
        this.d = zzh.getAccount();
        this.c = a(zzh.zzauh());
    }

    @Nullable
    private static zzb a(final ConnectionCallbacks connectionCallbacks) {
        if (connectionCallbacks == null) {
            return null;
        }
        return new zzb() {
            public void onConnected(@Nullable Bundle bundle) {
                ConnectionCallbacks.this.onConnected(bundle);
            }

            public void onConnectionSuspended(int i) {
                ConnectionCallbacks.this.onConnectionSuspended(i);
            }
        };
    }

    @Nullable
    private static zzc a(final OnConnectionFailedListener onConnectionFailedListener) {
        if (onConnectionFailedListener == null) {
            return null;
        }
        return new zzc() {
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                OnConnectionFailedListener.this.onConnectionFailed(connectionResult);
            }
        };
    }

    private Set<Scope> a(@NonNull Set<Scope> set) {
        Set<Scope> zzc = zzc(set);
        for (Scope contains : zzc) {
            if (!set.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return zzc;
    }

    public final Account getAccount() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzatz() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public final zzh zzaus() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Set<Scope> zzc(@NonNull Set<Scope> set) {
        return set;
    }
}
