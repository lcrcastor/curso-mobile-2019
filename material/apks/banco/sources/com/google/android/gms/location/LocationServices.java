package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.location.internal.zzd;
import com.google.android.gms.location.internal.zzf;
import com.google.android.gms.location.internal.zzl;
import com.google.android.gms.location.internal.zzq;

public class LocationServices {
    public static final Api<NoOptions> API = new Api<>("LocationServices.API", b, a);
    public static final FusedLocationProviderApi FusedLocationApi = new zzd();
    public static final GeofencingApi GeofencingApi = new zzf();
    public static final SettingsApi SettingsApi = new zzq();
    private static final Api.zzf<zzl> a = new Api.zzf<>();
    private static final com.google.android.gms.common.api.Api.zza<zzl, NoOptions> b = new com.google.android.gms.common.api.Api.zza<zzl, NoOptions>() {
        /* renamed from: a */
        public zzl zza(Context context, Looper looper, zzh zzh, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzl zzl = new zzl(context, looper, connectionCallbacks, onConnectionFailedListener, "locationServices", zzh);
            return zzl;
        }
    };

    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqc.zza<R, zzl> {
        public zza(GoogleApiClient googleApiClient) {
            super(LocationServices.API, googleApiClient);
        }
    }

    private LocationServices() {
    }

    public static zzl zzj(GoogleApiClient googleApiClient) {
        boolean z = false;
        zzac.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzl zzl = (zzl) googleApiClient.zza((zzc<C>) a);
        if (zzl != null) {
            z = true;
        }
        zzac.zza(z, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return zzl;
    }
}
