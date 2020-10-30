package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzm implements Creator<StreetViewPanoramaLocation> {
    static void a(StreetViewPanoramaLocation streetViewPanoramaLocation, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, streetViewPanoramaLocation.a());
        zzb.zza(parcel, 2, (T[]) streetViewPanoramaLocation.links, i, false);
        zzb.zza(parcel, 3, (Parcelable) streetViewPanoramaLocation.position, i, false);
        zzb.zza(parcel, 4, streetViewPanoramaLocation.panoId, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzoz */
    public StreetViewPanoramaLocation createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        StreetViewPanoramaLink[] streetViewPanoramaLinkArr = null;
        int i = 0;
        LatLng latLng = null;
        String str = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    streetViewPanoramaLinkArr = (StreetViewPanoramaLink[]) zza.zzb(parcel, zzcp, StreetViewPanoramaLink.CREATOR);
                    break;
                case 3:
                    latLng = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 4:
                    str = zza.zzq(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new StreetViewPanoramaLocation(i, streetViewPanoramaLinkArr, latLng, str);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzwe */
    public StreetViewPanoramaLocation[] newArray(int i) {
        return new StreetViewPanoramaLocation[i];
    }
}
