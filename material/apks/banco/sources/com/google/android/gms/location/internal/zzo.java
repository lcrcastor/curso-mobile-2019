package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class zzo implements Creator<ParcelableGeofence> {
    static void a(ParcelableGeofence parcelableGeofence, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, parcelableGeofence.getRequestId(), false);
        zzb.zza(parcel, 2, parcelableGeofence.getExpirationTime());
        zzb.zza(parcel, 3, parcelableGeofence.zzbpq());
        zzb.zza(parcel, 4, parcelableGeofence.getLatitude());
        zzb.zza(parcel, 5, parcelableGeofence.getLongitude());
        zzb.zza(parcel, 6, parcelableGeofence.getRadius());
        zzb.zzc(parcel, 7, parcelableGeofence.zzbpr());
        zzb.zzc(parcel, 1000, parcelableGeofence.getVersionCode());
        zzb.zzc(parcel, 8, parcelableGeofence.zzbps());
        zzb.zzc(parcel, 9, parcelableGeofence.zzbpt());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznq */
    public ParcelableGeofence createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        double d = 0.0d;
        double d2 = 0.0d;
        String str = null;
        long j = 0;
        int i = 0;
        int i2 = 0;
        short s = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        int i3 = 0;
        int i4 = -1;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        str = zza.zzq(parcel2, zzcp);
                        break;
                    case 2:
                        j = zza.zzi(parcel2, zzcp);
                        break;
                    case 3:
                        s = zza.zzf(parcel2, zzcp);
                        break;
                    case 4:
                        d = zza.zzn(parcel2, zzcp);
                        break;
                    case 5:
                        d2 = zza.zzn(parcel2, zzcp);
                        break;
                    case 6:
                        f = zza.zzl(parcel2, zzcp);
                        break;
                    case 7:
                        i2 = zza.zzg(parcel2, zzcp);
                        break;
                    case 8:
                        i3 = zza.zzg(parcel2, zzcp);
                        break;
                    case 9:
                        i4 = zza.zzg(parcel2, zzcp);
                        break;
                    default:
                        zza.zzb(parcel2, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel2, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        ParcelableGeofence parcelableGeofence = new ParcelableGeofence(i, str, i2, s, d, d2, f, j, i3, i4);
        return parcelableGeofence;
    }

    /* renamed from: zzus */
    public ParcelableGeofence[] newArray(int i) {
        return new ParcelableGeofence[i];
    }
}
