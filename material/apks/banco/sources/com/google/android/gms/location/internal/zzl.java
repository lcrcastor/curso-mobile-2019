package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.List;

public class zzl extends zzb {
    private final zzk b;

    static final class zza extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.internal.zzqc.zzb<Status> a;

        public zza(com.google.android.gms.internal.zzqc.zzb<Status> zzb) {
            this.a = zzb;
        }

        public void zza(int i, PendingIntent pendingIntent) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
        }

        public void zza(int i, String[] strArr) {
            if (this.a == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            this.a.setResult(LocationStatusCodes.zzuk(LocationStatusCodes.zzuj(i)));
            this.a = null;
        }

        public void zzb(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
        }
    }

    static final class zzb extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.internal.zzqc.zzb<Status> a;

        public zzb(com.google.android.gms.internal.zzqc.zzb<Status> zzb) {
            this.a = zzb;
        }

        private void a(int i) {
            if (this.a == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
                return;
            }
            this.a.setResult(LocationStatusCodes.zzuk(LocationStatusCodes.zzuj(i)));
            this.a = null;
        }

        public void zza(int i, PendingIntent pendingIntent) {
            a(i);
        }

        public void zza(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
        }

        public void zzb(int i, String[] strArr) {
            a(i);
        }
    }

    static final class zzc extends com.google.android.gms.location.internal.zzj.zza {
        private com.google.android.gms.internal.zzqc.zzb<LocationSettingsResult> a;

        public zzc(com.google.android.gms.internal.zzqc.zzb<LocationSettingsResult> zzb) {
            zzac.zzb(zzb != null, (Object) "listener can't be null.");
            this.a = zzb;
        }

        public void zza(LocationSettingsResult locationSettingsResult) {
            this.a.setResult(locationSettingsResult);
            this.a = null;
        }
    }

    public zzl(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzh.zzcd(context));
    }

    public zzl(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzh zzh) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzh);
        this.b = new zzk(context, this.ahK);
    }

    public void disconnect() {
        synchronized (this.b) {
            if (isConnected()) {
                try {
                    this.b.removeAllListeners();
                    this.b.zzbpm();
                } catch (Exception e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.b.getLastLocation();
    }

    public void zza(long j, PendingIntent pendingIntent) {
        zzatw();
        zzac.zzy(pendingIntent);
        zzac.zzb(j >= 0, (Object) "detectionIntervalMillis must be >= 0");
        ((zzi) zzatx()).zza(j, true, pendingIntent);
    }

    public void zza(PendingIntent pendingIntent, com.google.android.gms.internal.zzqc.zzb<Status> zzb2) {
        zzatw();
        zzac.zzb(pendingIntent, (Object) "PendingIntent must be specified.");
        zzac.zzb(zzb2, (Object) "ResultHolder not provided.");
        ((zzi) zzatx()).zza(pendingIntent, (zzh) new zzb(zzb2), getContext().getPackageName());
    }

    public void zza(PendingIntent pendingIntent, zzg zzg) {
        this.b.zza(pendingIntent, zzg);
    }

    public void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, com.google.android.gms.internal.zzqc.zzb<Status> zzb2) {
        zzatw();
        zzac.zzb(geofencingRequest, (Object) "geofencingRequest can't be null.");
        zzac.zzb(pendingIntent, (Object) "PendingIntent must be specified.");
        zzac.zzb(zzb2, (Object) "ResultHolder not provided.");
        ((zzi) zzatx()).zza(geofencingRequest, pendingIntent, (zzh) new zza(zzb2));
    }

    public void zza(LocationCallback locationCallback, zzg zzg) {
        this.b.zza(locationCallback, zzg);
    }

    public void zza(LocationListener locationListener, zzg zzg) {
        this.b.zza(locationListener, zzg);
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzg zzg) {
        this.b.zza(locationRequest, pendingIntent, zzg);
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper, zzg zzg) {
        synchronized (this.b) {
            this.b.zza(locationRequest, locationListener, looper, zzg);
        }
    }

    public void zza(LocationSettingsRequest locationSettingsRequest, com.google.android.gms.internal.zzqc.zzb<LocationSettingsResult> zzb2, String str) {
        zzatw();
        boolean z = false;
        zzac.zzb(locationSettingsRequest != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if (zzb2 != null) {
            z = true;
        }
        zzac.zzb(z, (Object) "listener can't be null.");
        ((zzi) zzatx()).zza(locationSettingsRequest, (zzj) new zzc(zzb2), str);
    }

    public void zza(LocationRequestInternal locationRequestInternal, LocationCallback locationCallback, Looper looper, zzg zzg) {
        synchronized (this.b) {
            this.b.zza(locationRequestInternal, locationCallback, looper, zzg);
        }
    }

    public void zza(zzg zzg) {
        this.b.zza(zzg);
    }

    public void zza(List<String> list, com.google.android.gms.internal.zzqc.zzb<Status> zzb2) {
        zzatw();
        zzac.zzb(list != null && list.size() > 0, (Object) "geofenceRequestIds can't be null nor empty.");
        zzac.zzb(zzb2, (Object) "ResultHolder not provided.");
        ((zzi) zzatx()).zza((String[]) list.toArray(new String[0]), (zzh) new zzb(zzb2), getContext().getPackageName());
    }

    public void zzb(PendingIntent pendingIntent) {
        zzatw();
        zzac.zzy(pendingIntent);
        ((zzi) zzatx()).zzb(pendingIntent);
    }

    public LocationAvailability zzbpl() {
        return this.b.zzbpl();
    }

    public void zzcc(boolean z) {
        this.b.zzcc(z);
    }

    public void zzd(Location location) {
        this.b.zzd(location);
    }
}
