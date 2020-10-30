package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqc.zzb;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GeofencingRequest.Builder;
import java.util.List;

public class zzf implements GeofencingApi {

    static abstract class zza extends com.google.android.gms.location.LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: a */
        public Status zzc(Status status) {
            return status;
        }
    }

    public PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, final GeofencingRequest geofencingRequest, final PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(geofencingRequest, pendingIntent, (zzb<Status>) this);
            }
        });
    }

    @Deprecated
    public PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, List<Geofence> list, PendingIntent pendingIntent) {
        Builder builder = new Builder();
        builder.addGeofences(list);
        builder.setInitialTrigger(5);
        return addGeofences(googleApiClient, builder.build(), pendingIntent);
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(pendingIntent, (zzb<Status>) this);
            }
        });
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, final List<String> list) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(list, (zzb<Status>) this);
            }
        });
    }
}
