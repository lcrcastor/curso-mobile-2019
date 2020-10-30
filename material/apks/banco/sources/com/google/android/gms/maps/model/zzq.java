package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzq implements Creator<VisibleRegion> {
    static void a(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, visibleRegion.a());
        zzb.zza(parcel, 2, (Parcelable) visibleRegion.nearLeft, i, false);
        zzb.zza(parcel, 3, (Parcelable) visibleRegion.nearRight, i, false);
        zzb.zza(parcel, 4, (Parcelable) visibleRegion.farLeft, i, false);
        zzb.zza(parcel, 5, (Parcelable) visibleRegion.farRight, i, false);
        zzb.zza(parcel, 6, (Parcelable) visibleRegion.latLngBounds, i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzpd */
    public VisibleRegion createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        LatLng latLng = null;
        LatLng latLng2 = null;
        LatLng latLng3 = null;
        LatLng latLng4 = null;
        LatLngBounds latLngBounds = null;
        int i = 0;
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
                case 4:
                    latLng3 = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 5:
                    latLng4 = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) zza.zza(parcel, zzcp, LatLngBounds.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        VisibleRegion visibleRegion = new VisibleRegion(i, latLng, latLng2, latLng3, latLng4, latLngBounds);
        return visibleRegion;
    }

    /* renamed from: zzwi */
    public VisibleRegion[] newArray(int i) {
        return new VisibleRegion[i];
    }
}
