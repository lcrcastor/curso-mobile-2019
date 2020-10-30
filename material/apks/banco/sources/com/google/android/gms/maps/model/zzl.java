package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzl implements Creator<StreetViewPanoramaLink> {
    static void a(StreetViewPanoramaLink streetViewPanoramaLink, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, streetViewPanoramaLink.a());
        zzb.zza(parcel, 2, streetViewPanoramaLink.panoId, false);
        zzb.zza(parcel, 3, streetViewPanoramaLink.bearing);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzoy */
    public StreetViewPanoramaLink createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        String str = null;
        float f = BitmapDescriptorFactory.HUE_RED;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    str = zza.zzq(parcel, zzcp);
                    break;
                case 3:
                    f = zza.zzl(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new StreetViewPanoramaLink(i, str, f);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzwd */
    public StreetViewPanoramaLink[] newArray(int i) {
        return new StreetViewPanoramaLink[i];
    }
}
