package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzg;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class zzai<T extends IInterface> extends zzl<T> {
    private final zzg<T> b;

    public zzai(Context context, Looper looper, int i, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzh zzh, zzg<T> zzg) {
        super(context, looper, i, zzh, connectionCallbacks, onConnectionFailedListener);
        this.b = zzg;
    }

    public zzg<T> zzavk() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public void zzc(int i, T t) {
        this.b.zza(i, t);
    }

    /* access modifiers changed from: protected */
    public T zzh(IBinder iBinder) {
        return this.b.zzh(iBinder);
    }

    /* access modifiers changed from: protected */
    public String zzix() {
        return this.b.zzix();
    }

    /* access modifiers changed from: protected */
    public String zziy() {
        return this.b.zziy();
    }
}
