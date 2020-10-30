package com.google.android.gms.location.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqc.zzb;
import com.google.android.gms.location.LocationServices.zza;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public class zzq implements SettingsApi {
    public PendingResult<LocationSettingsResult> checkLocationSettings(GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest) {
        return zza(googleApiClient, locationSettingsRequest, null);
    }

    public PendingResult<LocationSettingsResult> zza(GoogleApiClient googleApiClient, final LocationSettingsRequest locationSettingsRequest, final String str) {
        return googleApiClient.zzc(new zza<LocationSettingsResult>(googleApiClient) {
            /* renamed from: a */
            public LocationSettingsResult zzc(Status status) {
                return new LocationSettingsResult(status);
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationSettingsRequest, (zzb<LocationSettingsResult>) this, str);
            }
        });
    }
}
