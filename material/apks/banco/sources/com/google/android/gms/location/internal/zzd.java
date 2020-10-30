package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class zzd implements FusedLocationProviderApi {

    static abstract class zza extends com.google.android.gms.location.LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        /* renamed from: a */
        public Status zzc(Status status) {
            return status;
        }
    }

    static class zzb extends com.google.android.gms.location.internal.zzg.zza {
        private final com.google.android.gms.internal.zzqc.zzb<Status> a;

        public zzb(com.google.android.gms.internal.zzqc.zzb<Status> zzb) {
            this.a = zzb;
        }

        public void zza(FusedLocationProviderResult fusedLocationProviderResult) {
            this.a.setResult(fusedLocationProviderResult.getStatus());
        }
    }

    public PendingResult<Status> flushLocations(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(new zzb(this));
            }
        });
    }

    public Location getLastLocation(GoogleApiClient googleApiClient) {
        try {
            return LocationServices.zzj(googleApiClient).getLastLocation();
        } catch (Exception unused) {
            return null;
        }
    }

    public LocationAvailability getLocationAvailability(GoogleApiClient googleApiClient) {
        try {
            return LocationServices.zzj(googleApiClient).zzbpl();
        } catch (Exception unused) {
            return null;
        }
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(pendingIntent, (zzg) new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final LocationCallback locationCallback) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationCallback, (zzg) new zzb(this));
            }
        });
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, final LocationListener locationListener) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationListener, (zzg) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, final LocationRequest locationRequest, final PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationRequest, pendingIntent, (zzg) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        final LocationRequest locationRequest2 = locationRequest;
        final LocationCallback locationCallback2 = locationCallback;
        final Looper looper2 = looper;
        AnonymousClass7 r0 = new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(LocationRequestInternal.zzb(locationRequest2), locationCallback2, looper2, (zzg) new zzb(this));
            }
        };
        return googleApiClient.zzd(r0);
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, final LocationRequest locationRequest, final LocationListener locationListener) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationRequest, locationListener, (Looper) null, (zzg) new zzb(this));
            }
        });
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        final LocationRequest locationRequest2 = locationRequest;
        final LocationListener locationListener2 = locationListener;
        final Looper looper2 = looper;
        AnonymousClass6 r0 = new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zza(locationRequest2, locationListener2, looper2, (zzg) new zzb(this));
            }
        };
        return googleApiClient.zzd(r0);
    }

    public PendingResult<Status> setMockLocation(GoogleApiClient googleApiClient, final Location location) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zzd(location);
                zzc(Status.vY);
            }
        });
    }

    public PendingResult<Status> setMockMode(GoogleApiClient googleApiClient, final boolean z) {
        return googleApiClient.zzd(new zza(googleApiClient) {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void zza(zzl zzl) {
                zzl.zzcc(z);
                zzc(Status.vY);
            }
        });
    }
}
