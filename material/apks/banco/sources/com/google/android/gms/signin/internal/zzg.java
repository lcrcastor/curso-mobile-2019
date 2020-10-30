package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzk;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zze.zzf;
import com.google.android.gms.common.internal.zze.zzi;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzwz;
import com.google.android.gms.internal.zzxa;
import com.google.android.gms.signin.internal.zze.zza;

public class zzg extends zzl<zze> implements zzwz {
    private final boolean b;
    private final zzh c;
    private final Bundle d;
    private Integer e;

    public zzg(Context context, Looper looper, boolean z, zzh zzh, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzh, connectionCallbacks, onConnectionFailedListener);
        this.b = z;
        this.c = zzh;
        this.d = bundle;
        this.e = zzh.zzaun();
    }

    public zzg(Context context, Looper looper, boolean z, zzh zzh, zzxa zzxa, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, z, zzh, zza(zzh), connectionCallbacks, onConnectionFailedListener);
    }

    private ResolveAccountRequest a() {
        Account zzatv = this.c.zzatv();
        return new ResolveAccountRequest(zzatv, this.e.intValue(), "<<default account>>".equals(zzatv.name) ? zzk.zzbd(getContext()).zzaic() : null);
    }

    public static Bundle zza(zzh zzh) {
        zzxa zzaum = zzh.zzaum();
        Integer zzaun = zzh.zzaun();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", zzh.getAccount());
        if (zzaun != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzaun.intValue());
        }
        if (zzaum != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzaum.zzcdb());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzaum.zzahk());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzaum.zzahn());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzaum.zzahm());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzaum.zzaho());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzaum.zzcdc());
            if (zzaum.zzcdd() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzaum.zzcdd().longValue());
            }
            if (zzaum.zzcde() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzaum.zzcde().longValue());
            }
        }
        return bundle;
    }

    public void connect() {
        zza((zzf) new zzi());
    }

    public void zza(zzr zzr, boolean z) {
        try {
            ((zze) zzatx()).zza(zzr, this.e.intValue(), z);
        } catch (RemoteException unused) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public void zza(zzd zzd) {
        zzac.zzb(zzd, (Object) "Expecting a valid ISignInCallbacks");
        try {
            ((zze) zzatx()).zza(new SignInRequest(a()), zzd);
        } catch (RemoteException e2) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                zzd.zzb(new SignInResponse(8));
            } catch (RemoteException unused) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Bundle zzagl() {
        if (!getContext().getPackageName().equals(this.c.zzauj())) {
            this.d.putString("com.google.android.gms.signin.internal.realClientPackageName", this.c.zzauj());
        }
        return this.d;
    }

    public boolean zzahd() {
        return this.b;
    }

    public void zzcda() {
        try {
            ((zze) zzatx()).zzaaf(this.e.intValue());
        } catch (RemoteException unused) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    /* access modifiers changed from: protected */
    public String zzix() {
        return "com.google.android.gms.signin.service.START";
    }

    /* access modifiers changed from: protected */
    public String zziy() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzlc */
    public zze zzh(IBinder iBinder) {
        return zza.zzlb(iBinder);
    }
}
