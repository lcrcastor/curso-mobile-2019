package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzh implements Creator<PointOfInterest> {
    static void a(PointOfInterest pointOfInterest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, pointOfInterest.a());
        zzb.zza(parcel, 2, (Parcelable) pointOfInterest.latLng, i, false);
        zzb.zza(parcel, 3, pointOfInterest.placeId, false);
        zzb.zza(parcel, 4, pointOfInterest.name, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzou */
    public PointOfInterest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        LatLng latLng = null;
        int i = 0;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    latLng = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 3:
                    str = zza.zzq(parcel, zzcp);
                    break;
                case 4:
                    str2 = zza.zzq(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new PointOfInterest(i, latLng, str, str2);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzvz */
    public PointOfInterest[] newArray(int i) {
        return new PointOfInterest[i];
    }
}
