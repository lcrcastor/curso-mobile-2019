package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionApi;

public class zza implements ActivityRecognitionApi {

    /* renamed from: com.google.android.gms.location.internal.zza$zza reason: collision with other inner class name */
    static abstract class C0033zza extends com.google.android.gms.location.ActivityRecognition.zza<Status> {
        public C0033zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: a */
        public Status zzc(Status status) {
            return status;
        }
    }

    public PendingResult<Status> removeActivityUpdates(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzd(new C0033zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zzb(pendingIntent);
                zzc(Status.vY);
            }
        });
    }

    public PendingResult<Status> requestActivityUpdates(GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
        final long j2 = j;
        final PendingIntent pendingIntent2 = pendingIntent;
        AnonymousClass1 r0 = new C0033zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(j2, pendingIntent2);
                zzc(Status.vY);
            }
        };
        return googleApiClient.zzd(r0);
    }
}
