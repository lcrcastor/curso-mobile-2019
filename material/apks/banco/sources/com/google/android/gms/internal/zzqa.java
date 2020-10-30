package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzac;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzqa extends zzqd {
    private final SparseArray<zza> a = new SparseArray<>();

    class zza implements OnConnectionFailedListener {
        public final int a;
        public final GoogleApiClient b;
        public final OnConnectionFailedListener c;

        public zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
            this.a = i;
            this.b = googleApiClient;
            this.c = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        public void a() {
            this.b.unregisterConnectionFailedListener(this);
            this.b.disconnect();
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.append(str).append("GoogleApiClient #").print(this.a);
            printWriter.println(":");
            this.b.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String valueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("beginFailureResolution for ");
            sb.append(valueOf);
            Log.d("AutoManageHelper", sb.toString());
            zzqa.this.zzb(connectionResult, this.a);
        }
    }

    private zzqa(zzrb zzrb) {
        super(zzrb);
        this.yY.zza("AutoManageHelper", (zzra) this);
    }

    public static zzqa zza(zzqz zzqz) {
        zzrb zzc = zzc(zzqz);
        zzqa zzqa = (zzqa) zzc.zza("AutoManageHelper", zzqa.class);
        return zzqa != null ? zzqa : new zzqa(zzc);
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.a.size(); i++) {
            ((zza) this.a.valueAt(i)).a(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onStart() {
        super.onStart();
        boolean z = this.mStarted;
        String valueOf = String.valueOf(this.a);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
        sb.append("onStart ");
        sb.append(z);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(valueOf);
        Log.d("AutoManageHelper", sb.toString());
        if (!this.wy) {
            for (int i = 0; i < this.a.size(); i++) {
                ((zza) this.a.valueAt(i)).b.connect();
            }
        }
    }

    public void onStop() {
        super.onStop();
        for (int i = 0; i < this.a.size(); i++) {
            ((zza) this.a.valueAt(i)).b.disconnect();
        }
    }

    public void zza(int i, GoogleApiClient googleApiClient, OnConnectionFailedListener onConnectionFailedListener) {
        zzac.zzb(googleApiClient, (Object) "GoogleApiClient instance cannot be null");
        boolean z = this.a.indexOfKey(i) < 0;
        StringBuilder sb = new StringBuilder(54);
        sb.append("Already managing a GoogleApiClient with id ");
        sb.append(i);
        zzac.zza(z, (Object) sb.toString());
        boolean z2 = this.mStarted;
        boolean z3 = this.wy;
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("starting AutoManage for client ");
        sb2.append(i);
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(z2);
        sb2.append(UtilsCuentas.SEPARAOR2);
        sb2.append(z3);
        Log.d("AutoManageHelper", sb2.toString());
        this.a.put(i, new zza(i, googleApiClient, onConnectionFailedListener));
        if (this.mStarted && !this.wy) {
            String valueOf = String.valueOf(googleApiClient);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 11);
            sb3.append("connecting ");
            sb3.append(valueOf);
            Log.d("AutoManageHelper", sb3.toString());
            googleApiClient.connect();
        }
    }

    /* access modifiers changed from: protected */
    public void zza(ConnectionResult connectionResult, int i) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zza zza2 = (zza) this.a.get(i);
        if (zza2 != null) {
            zzfq(i);
            OnConnectionFailedListener onConnectionFailedListener = zza2.c;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void zzaqk() {
        for (int i = 0; i < this.a.size(); i++) {
            ((zza) this.a.valueAt(i)).b.connect();
        }
    }

    public void zzfq(int i) {
        zza zza2 = (zza) this.a.get(i);
        this.a.remove(i);
        if (zza2 != null) {
            zza2.a();
        }
    }
}
