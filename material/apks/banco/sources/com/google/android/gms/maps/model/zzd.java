package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzd implements Creator<LatLngBounds> {
    static void a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, latLngBounds.a());
        zzb.zza(parcel, 2, (Parcelable) latLngBounds.southwest, i, false);
        zzb.zza(parcel, 3, (Parcelable) latLngBounds.northeast, i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzoq */
    public LatLngBounds createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        LatLng latLng = null;
        int i = 0;
        LatLng latLng2 = null;
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
                    latLng2 = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new LatLngBounds(i, latLng, latLng2);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzvv */
    public LatLngBounds[] newArray(int i) {
        return new LatLngBounds[i];
    }
}
