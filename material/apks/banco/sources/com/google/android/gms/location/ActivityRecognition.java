package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.location.internal.zzl;

public class ActivityRecognition {
    public static final Api<NoOptions> API = new Api<>("ActivityRecognition.API", b, a);
    public static final ActivityRecognitionApi ActivityRecognitionApi = new com.google.android.gms.location.internal.zza();
    public static final String CLIENT_NAME = "activity_recognition";
    private static final zzf<zzl> a = new zzf<>();
    private static final com.google.android.gms.common.api.Api.zza<zzl, NoOptions> b = new com.google.android.gms.common.api.Api.zza<zzl, NoOptions>() {
        /* renamed from: a */
        public zzl zza(Context context, Looper looper, zzh zzh, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            zzl zzl = new zzl(context, looper, connectionCallbacks, onConnectionFailedListener, ActivityRecognition.CLIENT_NAME);
            return zzl;
        }
    };

    public static abstract class zza<R extends Result> extends com.google.android.gms.internal.zzqc.zza<R, zzl> {
        public zza(GoogleApiClient googleApiClient) {
            super(ActivityRecognition.API, googleApiClient);
        }
    }

    private ActivityRecognition() {
    }
}
