package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzqc.zza;

public class zzqm implements zzqq {
    /* access modifiers changed from: private */
    public final zzqr a;
    private boolean b = false;

    public zzqm(zzqr zzqr) {
        this.a = zzqr;
    }

    /* JADX WARNING: type inference failed for: r0v8, types: [com.google.android.gms.common.api.Api$zzg] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <A extends com.google.android.gms.common.api.Api.zzb> void a(com.google.android.gms.internal.zzqc.zza<? extends com.google.android.gms.common.api.Result, A> r4) {
        /*
            r3 = this;
            com.google.android.gms.internal.zzqr r0 = r3.a
            com.google.android.gms.internal.zzqp r0 = r0.g
            com.google.android.gms.internal.zzrq r0 = r0.i
            r0.a(r4)
            com.google.android.gms.internal.zzqr r0 = r3.a
            com.google.android.gms.internal.zzqp r0 = r0.g
            com.google.android.gms.common.api.Api$zzc r1 = r4.zzapp()
            com.google.android.gms.common.api.Api$zze r0 = r0.a(r1)
            boolean r1 = r0.isConnected()
            if (r1 != 0) goto L_0x0034
            com.google.android.gms.internal.zzqr r1 = r3.a
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.ConnectionResult> r1 = r1.b
            com.google.android.gms.common.api.Api$zzc r2 = r4.zzapp()
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x0034
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status
            r1 = 17
            r0.<init>(r1)
            r4.zzz(r0)
            return
        L_0x0034:
            boolean r1 = r0 instanceof com.google.android.gms.common.internal.zzai
            if (r1 == 0) goto L_0x003e
            com.google.android.gms.common.internal.zzai r0 = (com.google.android.gms.common.internal.zzai) r0
            com.google.android.gms.common.api.Api$zzg r0 = r0.zzavk()
        L_0x003e:
            r4.zzb(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqm.a(com.google.android.gms.internal.zzqc$zza):void");
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.b) {
            this.b = false;
            this.a.g.i.release();
            disconnect();
        }
    }

    public void begin() {
    }

    public void connect() {
        if (this.b) {
            this.b = false;
            this.a.a((zza) new zza(this) {
                public void a() {
                    zzqm.this.a.h.zzn(null);
                }
            });
        }
    }

    public boolean disconnect() {
        if (this.b) {
            return false;
        }
        if (this.a.g.e()) {
            this.b = true;
            for (zzrp a2 : this.a.g.h) {
                a2.a();
            }
            return false;
        }
        this.a.a((ConnectionResult) null);
        return true;
    }

    public void onConnected(Bundle bundle) {
    }

    public void onConnectionSuspended(int i) {
        this.a.a((ConnectionResult) null);
        this.a.h.zzc(i, this.b);
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i) {
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(T t) {
        return zzd(t);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(T t) {
        try {
            a((zza<? extends Result, A>) t);
            return t;
        } catch (DeadObjectException unused) {
            this.a.a((zza) new zza(this) {
                public void a() {
                    zzqm.this.onConnectionSuspended(1);
                }
            });
            return t;
        }
    }
}
