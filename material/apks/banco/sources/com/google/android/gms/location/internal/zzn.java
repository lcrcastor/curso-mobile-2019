package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzn implements Creator<LocationRequestUpdateData> {
    static void a(LocationRequestUpdateData locationRequestUpdateData, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, locationRequestUpdateData.a);
        zzb.zza(parcel, 2, (Parcelable) locationRequestUpdateData.b, i, false);
        zzb.zza(parcel, 3, locationRequestUpdateData.b(), false);
        zzb.zza(parcel, 4, (Parcelable) locationRequestUpdateData.d, i, false);
        zzb.zza(parcel, 5, locationRequestUpdateData.c(), false);
        zzb.zza(parcel, 6, locationRequestUpdateData.d(), false);
        zzb.zzc(parcel, 1000, locationRequestUpdateData.a());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznp */
    public LocationRequestUpdateData createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        LocationRequestInternal locationRequestInternal = null;
        IBinder iBinder = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        int i = 0;
        int i2 = 1;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        i2 = zza.zzg(parcel, zzcp);
                        break;
                    case 2:
                        locationRequestInternal = (LocationRequestInternal) zza.zza(parcel, zzcp, LocationRequestInternal.CREATOR);
                        break;
                    case 3:
                        iBinder = zza.zzr(parcel, zzcp);
                        break;
                    case 4:
                        pendingIntent = (PendingIntent) zza.zza(parcel, zzcp, PendingIntent.CREATOR);
                        break;
                    case 5:
                        iBinder2 = zza.zzr(parcel, zzcp);
                        break;
                    case 6:
                        iBinder3 = zza.zzr(parcel, zzcp);
                        break;
                    default:
                        zza.zzb(parcel, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        LocationRequestUpdateData locationRequestUpdateData = new LocationRequestUpdateData(i, i2, locationRequestInternal, iBinder, pendingIntent, iBinder2, iBinder3);
        return locationRequestUpdateData;
    }

    /* renamed from: zzup */
    public LocationRequestUpdateData[] newArray(int i) {
        return new LocationRequestUpdateData[i];
    }
}
