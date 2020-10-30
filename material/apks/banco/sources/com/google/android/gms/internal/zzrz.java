package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqc.zzb;

public final class zzrz implements zzry {

    static class zza extends zzrw {
        private final zzb<Status> a;

        public zza(zzb<Status> zzb) {
            this.a = zzb;
        }

        public void zzgw(int i) {
            this.a.setResult(new Status(i));
        }
    }

    public PendingResult<Status> zzg(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzsb zzsb) {
                ((zzsd) zzsb.zzatx()).zza(new zza(this));
            }
        });
    }
}
