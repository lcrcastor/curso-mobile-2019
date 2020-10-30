package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzf;
import com.google.android.gms.location.zzg;
import java.util.HashMap;
import java.util.Map;

public class zzk {
    private final zzp<zzi> a;
    private final Context b;
    private ContentProviderClient c = null;
    private boolean d = false;
    private Map<LocationListener, zzc> e = new HashMap();
    private Map<LocationCallback, zza> f = new HashMap();

    static class zza extends com.google.android.gms.location.zzf.zza {
        private Handler a;

        zza(final LocationCallback locationCallback, Looper looper) {
            if (looper == null) {
                looper = Looper.myLooper();
                zzac.zza(looper != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.a = new Handler(looper) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            locationCallback.onLocationResult((LocationResult) message.obj);
                            return;
                        case 1:
                            locationCallback.onLocationAvailability((LocationAvailability) message.obj);
                            return;
                        default:
                            return;
                    }
                }
            };
        }

        private synchronized void a(int i, Object obj) {
            if (this.a == null) {
                Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.obj = obj;
            this.a.sendMessage(obtain);
        }

        public synchronized void a() {
            this.a = null;
        }

        public void onLocationAvailability(LocationAvailability locationAvailability) {
            a(1, locationAvailability);
        }

        public void onLocationResult(LocationResult locationResult) {
            a(0, locationResult);
        }
    }

    static class zzb extends Handler {
        private final LocationListener a;

        public zzb(LocationListener locationListener) {
            this.a = locationListener;
        }

        public zzb(LocationListener locationListener, Looper looper) {
            super(looper);
            this.a = locationListener;
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                return;
            }
            this.a.onLocationChanged(new Location((Location) message.obj));
        }
    }

    static class zzc extends com.google.android.gms.location.zzg.zza {
        private Handler a;

        zzc(LocationListener locationListener, Looper looper) {
            if (looper == null) {
                zzac.zza(Looper.myLooper() != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.a = looper == null ? new zzb(locationListener) : new zzb(locationListener, looper);
        }

        public synchronized void a() {
            this.a = null;
        }

        public synchronized void onLocationChanged(Location location) {
            if (this.a == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.a.sendMessage(obtain);
        }
    }

    public zzk(Context context, zzp<zzi> zzp) {
        this.b = context;
        this.a = zzp;
    }

    private zza a(LocationCallback locationCallback, Looper looper) {
        zza zza2;
        synchronized (this.f) {
            zza2 = (zza) this.f.get(locationCallback);
            if (zza2 == null) {
                zza2 = new zza(locationCallback, looper);
            }
            this.f.put(locationCallback, zza2);
        }
        return zza2;
    }

    private zzc a(LocationListener locationListener, Looper looper) {
        zzc zzc2;
        synchronized (this.e) {
            zzc2 = (zzc) this.e.get(locationListener);
            if (zzc2 == null) {
                zzc2 = new zzc(locationListener, looper);
            }
            this.e.put(locationListener, zzc2);
        }
        return zzc2;
    }

    public Location getLastLocation() {
        this.a.zzatw();
        try {
            return ((zzi) this.a.zzatx()).zzkx(this.b.getPackageName());
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.e) {
                for (zzc zzc2 : this.e.values()) {
                    if (zzc2 != null) {
                        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza((zzg) zzc2, (zzg) null));
                    }
                }
                this.e.clear();
            }
            synchronized (this.f) {
                for (zza zza2 : this.f.values()) {
                    if (zza2 != null) {
                        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza((zzf) zza2, (zzg) null));
                    }
                }
                this.f.clear();
            }
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void zza(PendingIntent pendingIntent, zzg zzg) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zzb(pendingIntent, zzg));
    }

    public void zza(LocationCallback locationCallback, zzg zzg) {
        this.a.zzatw();
        zzac.zzb(locationCallback, (Object) "Invalid null callback");
        synchronized (this.f) {
            zza zza2 = (zza) this.f.remove(locationCallback);
            if (zza2 != null) {
                zza2.a();
                ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza((zzf) zza2, zzg));
            }
        }
    }

    public void zza(LocationListener locationListener, zzg zzg) {
        this.a.zzatw();
        zzac.zzb(locationListener, (Object) "Invalid null listener");
        synchronized (this.e) {
            zzc zzc2 = (zzc) this.e.remove(locationListener);
            if (zzc2 != null) {
                zzc2.a();
                ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza((zzg) zzc2, zzg));
            }
        }
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzg zzg) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb(locationRequest), pendingIntent, zzg));
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper, zzg zzg) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb(locationRequest), (zzg) a(locationListener, looper), zzg));
    }

    public void zza(LocationRequestInternal locationRequestInternal, LocationCallback locationCallback, Looper looper, zzg zzg) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zza(LocationRequestUpdateData.zza(locationRequestInternal, (zzf) a(locationCallback, looper), zzg));
    }

    public void zza(zzg zzg) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zza(zzg);
    }

    public LocationAvailability zzbpl() {
        this.a.zzatw();
        try {
            return ((zzi) this.a.zzatx()).zzky(this.b.getPackageName());
        } catch (RemoteException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void zzbpm() {
        if (this.d) {
            try {
                zzcc(false);
            } catch (RemoteException e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    public void zzcc(boolean z) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zzcc(z);
        this.d = z;
    }

    public void zzd(Location location) {
        this.a.zzatw();
        ((zzi) this.a.zzatx()).zzd(location);
    }
}
