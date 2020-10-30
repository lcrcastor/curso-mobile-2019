package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.location.zzf;
import com.google.android.gms.location.zzg;
import com.google.android.gms.location.zzg.zza;

public class LocationRequestUpdateData extends AbstractSafeParcelable {
    public static final zzn CREATOR = new zzn();
    int a;
    LocationRequestInternal b;
    zzg c;
    PendingIntent d;
    zzf e;
    zzg f;
    private final int g;

    LocationRequestUpdateData(int i, int i2, LocationRequestInternal locationRequestInternal, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        this.g = i;
        this.a = i2;
        this.b = locationRequestInternal;
        zzg zzg = null;
        this.c = iBinder == null ? null : zza.zzgy(iBinder);
        this.d = pendingIntent;
        this.e = iBinder2 == null ? null : zzf.zza.zzgx(iBinder2);
        if (iBinder3 != null) {
            zzg = zzg.zza.zzha(iBinder3);
        }
        this.f = zzg;
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent, @Nullable zzg zzg) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 1, locationRequestInternal, null, pendingIntent, null, zzg != null ? zzg.asBinder() : null);
        return locationRequestUpdateData;
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal locationRequestInternal, zzf zzf, @Nullable zzg zzg) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 1, locationRequestInternal, null, null, zzf.asBinder(), zzg != null ? zzg.asBinder() : null);
        return locationRequestUpdateData;
    }

    public static LocationRequestUpdateData zza(LocationRequestInternal locationRequestInternal, zzg zzg, @Nullable zzg zzg2) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 1, locationRequestInternal, zzg.asBinder(), null, null, zzg2 != null ? zzg2.asBinder() : null);
        return locationRequestUpdateData;
    }

    public static LocationRequestUpdateData zza(zzf zzf, @Nullable zzg zzg) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 2, null, null, null, zzf.asBinder(), zzg != null ? zzg.asBinder() : null);
        return locationRequestUpdateData;
    }

    public static LocationRequestUpdateData zza(zzg zzg, @Nullable zzg zzg2) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 2, null, zzg.asBinder(), null, null, zzg2 != null ? zzg2.asBinder() : null);
        return locationRequestUpdateData;
    }

    public static LocationRequestUpdateData zzb(PendingIntent pendingIntent, @Nullable zzg zzg) {
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(1, 2, null, null, pendingIntent, null, zzg != null ? zzg.asBinder() : null);
        return locationRequestUpdateData;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public IBinder b() {
        if (this.c == null) {
            return null;
        }
        return this.c.asBinder();
    }

    /* access modifiers changed from: 0000 */
    public IBinder c() {
        if (this.e == null) {
            return null;
        }
        return this.e.asBinder();
    }

    /* access modifiers changed from: 0000 */
    public IBinder d() {
        if (this.f == null) {
            return null;
        }
        return this.f.asBinder();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzn.a(this, parcel, i);
    }
}
