package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.zzqx;
import com.google.android.gms.location.ActivityRecognitionRequest;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GestureRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzg;
import java.util.List;

public interface zzi extends IInterface {

    public static abstract class zza extends Binder implements zzi {

        /* renamed from: com.google.android.gms.location.internal.zzi$zza$zza reason: collision with other inner class name */
        static class C0036zza implements zzi {
            private IBinder a;

            C0036zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void zza(long j, boolean z, PendingIntent pendingIntent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeLong(j);
                    obtain.writeInt(z ? 1 : 0);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PendingIntent pendingIntent, zzh zzh, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(Location location, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(ActivityRecognitionRequest activityRecognitionRequest, PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (activityRecognitionRequest != null) {
                        obtain.writeInt(1);
                        activityRecognitionRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (activityTransitionRequest != null) {
                        obtain.writeInt(1);
                        activityTransitionRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzh zzh) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (geofencingRequest != null) {
                        obtain.writeInt(1);
                        geofencingRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    this.a.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(GestureRequest gestureRequest, PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (gestureRequest != null) {
                        obtain.writeInt(1);
                        gestureRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, PendingIntent pendingIntent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, zzg zzg) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequest locationRequest, zzg zzg, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        obtain.writeInt(1);
                        locationRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationSettingsRequest locationSettingsRequest, zzj zzj, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationSettingsRequest != null) {
                        obtain.writeInt(1);
                        locationSettingsRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzj != null ? zzj.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequestInternal != null) {
                        obtain.writeInt(1);
                        locationRequestInternal.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequestInternal locationRequestInternal, zzg zzg) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequestInternal != null) {
                        obtain.writeInt(1);
                        locationRequestInternal.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    this.a.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LocationRequestUpdateData locationRequestUpdateData) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequestUpdateData != null) {
                        obtain.writeInt(1);
                        locationRequestUpdateData.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzg zzg) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    this.a.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzh zzh, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(zzg zzg) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStrongBinder(zzg != null ? zzg.asBinder() : null);
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(List<ParcelableGeofence> list, PendingIntent pendingIntent, zzh zzh, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeTypedList(list);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String[] strArr, zzh zzh, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(zzh != null ? zzh.asBinder() : null);
                    obtain.writeString(str);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(PendingIntent pendingIntent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location zzbpk() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzc(PendingIntent pendingIntent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzc(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzcc(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzd(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzd(Location location) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zze(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzf(PendingIntent pendingIntent, zzqx zzqx) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(zzqx != null ? zzqx.asBinder() : null);
                    this.a.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ActivityRecognitionResult zzkw(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.a.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (ActivityRecognitionResult) ActivityRecognitionResult.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location zzkx(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public LocationAvailability zzky(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    obtain.writeString(str);
                    this.a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (LocationAvailability) LocationAvailability.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzi zzhc(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzi)) ? new C0036zza(iBinder) : (zzi) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1, types: [android.location.Location] */
        /* JADX WARNING: type inference failed for: r1v3, types: [android.location.Location] */
        /* JADX WARNING: type inference failed for: r1v4, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v6, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v7, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v9, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v10, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v12, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v13, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v15, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v16, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v19, types: [com.google.android.gms.location.LocationRequest] */
        /* JADX WARNING: type inference failed for: r1v21, types: [com.google.android.gms.location.LocationRequest] */
        /* JADX WARNING: type inference failed for: r1v22, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v24, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v25, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v27, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v28, types: [android.location.Location] */
        /* JADX WARNING: type inference failed for: r1v30, types: [android.location.Location] */
        /* JADX WARNING: type inference failed for: r1v31, types: [com.google.android.gms.location.LocationRequest] */
        /* JADX WARNING: type inference failed for: r1v33, types: [com.google.android.gms.location.LocationRequest] */
        /* JADX WARNING: type inference failed for: r1v34, types: [com.google.android.gms.location.internal.LocationRequestInternal] */
        /* JADX WARNING: type inference failed for: r1v36, types: [com.google.android.gms.location.internal.LocationRequestInternal] */
        /* JADX WARNING: type inference failed for: r1v37, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v39, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v40, types: [com.google.android.gms.location.internal.LocationRequestUpdateData] */
        /* JADX WARNING: type inference failed for: r1v42, types: [com.google.android.gms.location.internal.LocationRequestUpdateData] */
        /* JADX WARNING: type inference failed for: r1v43, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v45, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v46, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v48, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v49, types: [com.google.android.gms.location.LocationSettingsRequest] */
        /* JADX WARNING: type inference failed for: r1v51, types: [com.google.android.gms.location.LocationSettingsRequest] */
        /* JADX WARNING: type inference failed for: r1v52, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v54, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v55, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v57, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v58, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v60, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v61, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v63, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v64, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v66, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v67, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v69, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v70, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v72, types: [android.app.PendingIntent] */
        /* JADX WARNING: type inference failed for: r1v73 */
        /* JADX WARNING: type inference failed for: r1v74 */
        /* JADX WARNING: type inference failed for: r1v75 */
        /* JADX WARNING: type inference failed for: r1v76 */
        /* JADX WARNING: type inference failed for: r1v77 */
        /* JADX WARNING: type inference failed for: r1v78 */
        /* JADX WARNING: type inference failed for: r1v79 */
        /* JADX WARNING: type inference failed for: r1v80 */
        /* JADX WARNING: type inference failed for: r1v81 */
        /* JADX WARNING: type inference failed for: r1v82 */
        /* JADX WARNING: type inference failed for: r1v83 */
        /* JADX WARNING: type inference failed for: r1v84 */
        /* JADX WARNING: type inference failed for: r1v85 */
        /* JADX WARNING: type inference failed for: r1v86 */
        /* JADX WARNING: type inference failed for: r1v87 */
        /* JADX WARNING: type inference failed for: r1v88 */
        /* JADX WARNING: type inference failed for: r1v89 */
        /* JADX WARNING: type inference failed for: r1v90 */
        /* JADX WARNING: type inference failed for: r1v91 */
        /* JADX WARNING: type inference failed for: r1v92 */
        /* JADX WARNING: type inference failed for: r1v93 */
        /* JADX WARNING: type inference failed for: r1v94 */
        /* JADX WARNING: type inference failed for: r1v95 */
        /* JADX WARNING: type inference failed for: r1v96 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.app.PendingIntent, android.location.Location, com.google.android.gms.location.LocationRequest, com.google.android.gms.location.internal.LocationRequestInternal, com.google.android.gms.location.internal.LocationRequestUpdateData, com.google.android.gms.location.LocationSettingsRequest]
          uses: [android.location.Location, android.app.PendingIntent, com.google.android.gms.location.LocationRequest, com.google.android.gms.location.internal.LocationRequestInternal, com.google.android.gms.location.internal.LocationRequestUpdateData, com.google.android.gms.location.LocationSettingsRequest]
          mth insns count: 472
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 25 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) {
            /*
                r6 = this;
                r0 = 26
                r1 = 0
                r2 = 1
                if (r7 == r0) goto L_0x048f
                r0 = 34
                r3 = 0
                if (r7 == r0) goto L_0x0472
                r0 = 57
                if (r7 == r0) goto L_0x043f
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                if (r7 == r0) goto L_0x0439
                switch(r7) {
                    case 1: goto L_0x040c;
                    case 2: goto L_0x03e5;
                    case 3: goto L_0x03c9;
                    case 4: goto L_0x03b1;
                    case 5: goto L_0x038b;
                    case 6: goto L_0x0370;
                    case 7: goto L_0x0357;
                    case 8: goto L_0x0334;
                    case 9: goto L_0x0309;
                    case 10: goto L_0x02f5;
                    case 11: goto L_0x02da;
                    case 12: goto L_0x02c7;
                    case 13: goto L_0x02ac;
                    default: goto L_0x0017;
                }
            L_0x0017:
                switch(r7) {
                    case 20: goto L_0x0285;
                    case 21: goto L_0x0268;
                    default: goto L_0x001a;
                }
            L_0x001a:
                switch(r7) {
                    case 52: goto L_0x0245;
                    case 53: goto L_0x021a;
                    default: goto L_0x001d;
                }
            L_0x001d:
                switch(r7) {
                    case 59: goto L_0x01ff;
                    case 60: goto L_0x01cc;
                    case 61: goto L_0x01a9;
                    default: goto L_0x0020;
                }
            L_0x0020:
                switch(r7) {
                    case 63: goto L_0x0182;
                    case 64: goto L_0x0165;
                    case 65: goto L_0x0142;
                    case 66: goto L_0x011f;
                    case 67: goto L_0x010b;
                    case 68: goto L_0x00e8;
                    case 69: goto L_0x00c5;
                    case 70: goto L_0x0092;
                    case 71: goto L_0x007e;
                    case 72: goto L_0x004b;
                    case 73: goto L_0x0028;
                    default: goto L_0x0023;
                }
            L_0x0023:
                boolean r7 = super.onTransact(r7, r8, r9, r10)
                return r7
            L_0x0028:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x003c
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x003c:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zza(r1, r7)
                r9.writeNoException()
                return r2
            L_0x004b:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x005f
                com.google.android.gms.location.zzc r7 = com.google.android.gms.location.ActivityTransitionRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.ActivityTransitionRequest r7 = (com.google.android.gms.location.ActivityTransitionRequest) r7
                goto L_0x0060
            L_0x005f:
                r7 = r1
            L_0x0060:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x006f
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r10 = r10.createFromParcel(r8)
                r1 = r10
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x006f:
                android.os.IBinder r8 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r8 = com.google.android.gms.internal.zzqx.zza.zzdp(r8)
                r6.zza(r7, r1, r8)
                r9.writeNoException()
                return r2
            L_0x007e:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zza(r7)
                r9.writeNoException()
                return r2
            L_0x0092:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x00a6
                android.os.Parcelable$Creator<com.google.android.gms.location.ActivityRecognitionRequest> r7 = com.google.android.gms.location.ActivityRecognitionRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.ActivityRecognitionRequest r7 = (com.google.android.gms.location.ActivityRecognitionRequest) r7
                goto L_0x00a7
            L_0x00a6:
                r7 = r1
            L_0x00a7:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x00b6
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r10 = r10.createFromParcel(r8)
                r1 = r10
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x00b6:
                android.os.IBinder r8 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r8 = com.google.android.gms.internal.zzqx.zza.zzdp(r8)
                r6.zza(r7, r1, r8)
                r9.writeNoException()
                return r2
            L_0x00c5:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x00d9
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x00d9:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zzf(r1, r7)
                r9.writeNoException()
                return r2
            L_0x00e8:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x00fc
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x00fc:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zze(r1, r7)
                r9.writeNoException()
                return r2
            L_0x010b:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzg r7 = com.google.android.gms.location.internal.zzg.zza.zzha(r7)
                r6.zza(r7)
                r9.writeNoException()
                return r2
            L_0x011f:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0133
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0133:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zzc(r1, r7)
                r9.writeNoException()
                return r2
            L_0x0142:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0156
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0156:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zzb(r1, r7)
                r9.writeNoException()
                return r2
            L_0x0165:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                java.lang.String r7 = r8.readString()
                com.google.android.gms.location.ActivityRecognitionResult r7 = r6.zzkw(r7)
                r9.writeNoException()
                if (r7 == 0) goto L_0x017e
                r9.writeInt(r2)
                r7.writeToParcel(r9, r2)
                return r2
            L_0x017e:
                r9.writeInt(r3)
                return r2
            L_0x0182:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0196
                android.os.Parcelable$Creator<com.google.android.gms.location.LocationSettingsRequest> r7 = com.google.android.gms.location.LocationSettingsRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                com.google.android.gms.location.LocationSettingsRequest r1 = (com.google.android.gms.location.LocationSettingsRequest) r1
            L_0x0196:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzj r7 = com.google.android.gms.location.internal.zzj.zza.zzhd(r7)
                java.lang.String r8 = r8.readString()
                r6.zza(r1, r7, r8)
                r9.writeNoException()
                return r2
            L_0x01a9:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x01bd
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x01bd:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r7 = com.google.android.gms.internal.zzqx.zza.zzdp(r7)
                r6.zzd(r1, r7)
                r9.writeNoException()
                return r2
            L_0x01cc:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x01e0
                com.google.android.gms.location.zze r7 = com.google.android.gms.location.GestureRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.GestureRequest r7 = (com.google.android.gms.location.GestureRequest) r7
                goto L_0x01e1
            L_0x01e0:
                r7 = r1
            L_0x01e1:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x01f0
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r10 = r10.createFromParcel(r8)
                r1 = r10
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x01f0:
                android.os.IBinder r8 = r8.readStrongBinder()
                com.google.android.gms.internal.zzqx r8 = com.google.android.gms.internal.zzqx.zza.zzdp(r8)
                r6.zza(r7, r1, r8)
                r9.writeNoException()
                return r2
            L_0x01ff:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0213
                com.google.android.gms.location.internal.zzn r7 = com.google.android.gms.location.internal.LocationRequestUpdateData.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                com.google.android.gms.location.internal.LocationRequestUpdateData r1 = (com.google.android.gms.location.internal.LocationRequestUpdateData) r1
            L_0x0213:
                r6.zza(r1)
                r9.writeNoException()
                return r2
            L_0x021a:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x022e
                com.google.android.gms.location.internal.zzm r7 = com.google.android.gms.location.internal.LocationRequestInternal.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.internal.LocationRequestInternal r7 = (com.google.android.gms.location.internal.LocationRequestInternal) r7
                goto L_0x022f
            L_0x022e:
                r7 = r1
            L_0x022f:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x023e
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r8 = r10.createFromParcel(r8)
                r1 = r8
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x023e:
                r6.zza(r7, r1)
                r9.writeNoException()
                return r2
            L_0x0245:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0259
                com.google.android.gms.location.internal.zzm r7 = com.google.android.gms.location.internal.LocationRequestInternal.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                com.google.android.gms.location.internal.LocationRequestInternal r1 = (com.google.android.gms.location.internal.LocationRequestInternal) r1
            L_0x0259:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.zzg r7 = com.google.android.gms.location.zzg.zza.zzgy(r7)
                r6.zza(r1, r7)
                r9.writeNoException()
                return r2
            L_0x0268:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                java.lang.String r7 = r8.readString()
                android.location.Location r7 = r6.zzkx(r7)
                r9.writeNoException()
                if (r7 == 0) goto L_0x0281
                r9.writeInt(r2)
                r7.writeToParcel(r9, r2)
                return r2
            L_0x0281:
                r9.writeInt(r3)
                return r2
            L_0x0285:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0299
                com.google.android.gms.location.LocationRequestCreator r7 = com.google.android.gms.location.LocationRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                com.google.android.gms.location.LocationRequest r1 = (com.google.android.gms.location.LocationRequest) r1
            L_0x0299:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.zzg r7 = com.google.android.gms.location.zzg.zza.zzgy(r7)
                java.lang.String r8 = r8.readString()
                r6.zza(r1, r7, r8)
                r9.writeNoException()
                return r2
            L_0x02ac:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x02c0
                android.os.Parcelable$Creator r7 = android.location.Location.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.location.Location r1 = (android.location.Location) r1
            L_0x02c0:
                r6.zzd(r1)
                r9.writeNoException()
                return r2
            L_0x02c7:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x02d3
                r3 = 1
            L_0x02d3:
                r6.zzcc(r3)
                r9.writeNoException()
                return r2
            L_0x02da:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x02ee
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x02ee:
                r6.zzc(r1)
                r9.writeNoException()
                return r2
            L_0x02f5:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.zzg r7 = com.google.android.gms.location.zzg.zza.zzgy(r7)
                r6.zza(r7)
                r9.writeNoException()
                return r2
            L_0x0309:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x031d
                com.google.android.gms.location.LocationRequestCreator r7 = com.google.android.gms.location.LocationRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.LocationRequest r7 = (com.google.android.gms.location.LocationRequest) r7
                goto L_0x031e
            L_0x031d:
                r7 = r1
            L_0x031e:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x032d
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r8 = r10.createFromParcel(r8)
                r1 = r8
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x032d:
                r6.zza(r7, r1)
                r9.writeNoException()
                return r2
            L_0x0334:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0348
                com.google.android.gms.location.LocationRequestCreator r7 = com.google.android.gms.location.LocationRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                com.google.android.gms.location.LocationRequest r1 = (com.google.android.gms.location.LocationRequest) r1
            L_0x0348:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.zzg r7 = com.google.android.gms.location.zzg.zza.zzgy(r7)
                r6.zza(r1, r7)
                r9.writeNoException()
                return r2
            L_0x0357:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                android.location.Location r7 = r6.zzbpk()
                r9.writeNoException()
                if (r7 == 0) goto L_0x036c
                r9.writeInt(r2)
                r7.writeToParcel(r9, r2)
                return r2
            L_0x036c:
                r9.writeInt(r3)
                return r2
            L_0x0370:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0384
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0384:
                r6.zzb(r1)
                r9.writeNoException()
                return r2
            L_0x038b:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                long r4 = r8.readLong()
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x039b
                r3 = 1
            L_0x039b:
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x03aa
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x03aa:
                r6.zza(r4, r3, r1)
                r9.writeNoException()
                return r2
            L_0x03b1:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzh r7 = com.google.android.gms.location.internal.zzh.zza.zzhb(r7)
                java.lang.String r8 = r8.readString()
                r6.zza(r7, r8)
                r9.writeNoException()
                return r2
            L_0x03c9:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                java.lang.String[] r7 = r8.createStringArray()
                android.os.IBinder r10 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzh r10 = com.google.android.gms.location.internal.zzh.zza.zzhb(r10)
                java.lang.String r8 = r8.readString()
                r6.zza(r7, r10, r8)
                r9.writeNoException()
                return r2
            L_0x03e5:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x03f9
                android.os.Parcelable$Creator r7 = android.app.PendingIntent.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x03f9:
                android.os.IBinder r7 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzh r7 = com.google.android.gms.location.internal.zzh.zza.zzhb(r7)
                java.lang.String r8 = r8.readString()
                r6.zza(r1, r7, r8)
                r9.writeNoException()
                return r2
            L_0x040c:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                com.google.android.gms.location.internal.zzo r7 = com.google.android.gms.location.internal.ParcelableGeofence.CREATOR
                java.util.ArrayList r7 = r8.createTypedArrayList(r7)
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x0426
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r10 = r10.createFromParcel(r8)
                r1 = r10
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0426:
                android.os.IBinder r10 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzh r10 = com.google.android.gms.location.internal.zzh.zza.zzhb(r10)
                java.lang.String r8 = r8.readString()
                r6.zza(r7, r1, r10, r8)
                r9.writeNoException()
                return r2
            L_0x0439:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r9.writeString(r7)
                return r2
            L_0x043f:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x0453
                android.os.Parcelable$Creator<com.google.android.gms.location.GeofencingRequest> r7 = com.google.android.gms.location.GeofencingRequest.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                com.google.android.gms.location.GeofencingRequest r7 = (com.google.android.gms.location.GeofencingRequest) r7
                goto L_0x0454
            L_0x0453:
                r7 = r1
            L_0x0454:
                int r10 = r8.readInt()
                if (r10 == 0) goto L_0x0463
                android.os.Parcelable$Creator r10 = android.app.PendingIntent.CREATOR
                java.lang.Object r10 = r10.createFromParcel(r8)
                r1 = r10
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0463:
                android.os.IBinder r8 = r8.readStrongBinder()
                com.google.android.gms.location.internal.zzh r8 = com.google.android.gms.location.internal.zzh.zza.zzhb(r8)
                r6.zza(r7, r1, r8)
                r9.writeNoException()
                return r2
            L_0x0472:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                java.lang.String r7 = r8.readString()
                com.google.android.gms.location.LocationAvailability r7 = r6.zzky(r7)
                r9.writeNoException()
                if (r7 == 0) goto L_0x048b
                r9.writeInt(r2)
                r7.writeToParcel(r9, r2)
                return r2
            L_0x048b:
                r9.writeInt(r3)
                return r2
            L_0x048f:
                java.lang.String r7 = "com.google.android.gms.location.internal.IGoogleLocationManagerService"
                r8.enforceInterface(r7)
                int r7 = r8.readInt()
                if (r7 == 0) goto L_0x04a3
                android.os.Parcelable$Creator r7 = android.location.Location.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r8)
                r1 = r7
                android.location.Location r1 = (android.location.Location) r1
            L_0x04a3:
                int r7 = r8.readInt()
                r6.zza(r1, r7)
                r9.writeNoException()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.internal.zzi.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void zza(long j, boolean z, PendingIntent pendingIntent);

    void zza(PendingIntent pendingIntent, zzqx zzqx);

    void zza(PendingIntent pendingIntent, zzh zzh, String str);

    void zza(Location location, int i);

    void zza(zzqx zzqx);

    void zza(ActivityRecognitionRequest activityRecognitionRequest, PendingIntent pendingIntent, zzqx zzqx);

    void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, zzqx zzqx);

    void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzh zzh);

    void zza(GestureRequest gestureRequest, PendingIntent pendingIntent, zzqx zzqx);

    void zza(LocationRequest locationRequest, PendingIntent pendingIntent);

    void zza(LocationRequest locationRequest, zzg zzg);

    void zza(LocationRequest locationRequest, zzg zzg, String str);

    void zza(LocationSettingsRequest locationSettingsRequest, zzj zzj, String str);

    void zza(LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent);

    void zza(LocationRequestInternal locationRequestInternal, zzg zzg);

    void zza(LocationRequestUpdateData locationRequestUpdateData);

    void zza(zzg zzg);

    void zza(zzh zzh, String str);

    void zza(zzg zzg);

    void zza(List<ParcelableGeofence> list, PendingIntent pendingIntent, zzh zzh, String str);

    void zza(String[] strArr, zzh zzh, String str);

    void zzb(PendingIntent pendingIntent);

    void zzb(PendingIntent pendingIntent, zzqx zzqx);

    Location zzbpk();

    void zzc(PendingIntent pendingIntent);

    void zzc(PendingIntent pendingIntent, zzqx zzqx);

    void zzcc(boolean z);

    void zzd(PendingIntent pendingIntent, zzqx zzqx);

    void zzd(Location location);

    void zze(PendingIntent pendingIntent, zzqx zzqx);

    void zzf(PendingIntent pendingIntent, zzqx zzqx);

    ActivityRecognitionResult zzkw(String str);

    Location zzkx(String str);

    LocationAvailability zzky(String str);
}
