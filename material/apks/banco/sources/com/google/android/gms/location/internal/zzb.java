package com.google.android.gms.location.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.location.internal.zzi.zza;

public class zzb extends zzl<zzi> {
    protected final zzp<zzi> ahK = new zzp<zzi>() {
        /* renamed from: a */
        public zzi zzatx() {
            return (zzi) zzb.this.zzatx();
        }

        public void zzatw() {
            zzb.this.zzatw();
        }
    };
    private final String b;

    public zzb(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzh zzh) {
        super(context, looper, 23, zzh, connectionCallbacks, onConnectionFailedListener);
        this.b = str;
    }

    /* access modifiers changed from: protected */
    public Bundle zzagl() {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.b);
        return bundle;
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzgz */
    public zzi zzh(IBinder iBinder) {
        return zza.zzhc(iBinder);
    }

    /* access modifiers changed from: protected */
    public String zzix() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    /* access modifiers changed from: protected */
    public String zziy() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }
}
