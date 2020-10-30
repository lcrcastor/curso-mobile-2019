package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class zzb implements Creator<StreetViewPanoramaOptions> {
    static void a(StreetViewPanoramaOptions streetViewPanoramaOptions, Parcel parcel, int i) {
        int zzcr = com.google.android.gms.common.internal.safeparcel.zzb.zzcr(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, streetViewPanoramaOptions.a());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) streetViewPanoramaOptions.getStreetViewPanoramaCamera(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, streetViewPanoramaOptions.getPanoramaId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) streetViewPanoramaOptions.getPosition(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, streetViewPanoramaOptions.getRadius(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, streetViewPanoramaOptions.b());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, streetViewPanoramaOptions.c());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, streetViewPanoramaOptions.d());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, streetViewPanoramaOptions.e());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, streetViewPanoramaOptions.f());
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzom */
    public StreetViewPanoramaOptions createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        String str = null;
        LatLng latLng = null;
        Integer num = null;
        int i = 0;
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) zza.zza(parcel, zzcp, StreetViewPanoramaCamera.CREATOR);
                    break;
                case 3:
                    str = zza.zzq(parcel, zzcp);
                    break;
                case 4:
                    latLng = (LatLng) zza.zza(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 5:
                    num = zza.zzh(parcel, zzcp);
                    break;
                case 6:
                    b = zza.zze(parcel, zzcp);
                    break;
                case 7:
                    b2 = zza.zze(parcel, zzcp);
                    break;
                case 8:
                    b3 = zza.zze(parcel, zzcp);
                    break;
                case 9:
                    b4 = zza.zze(parcel, zzcp);
                    break;
                case 10:
                    b5 = zza.zze(parcel, zzcp);
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
        StreetViewPanoramaOptions streetViewPanoramaOptions = new StreetViewPanoramaOptions(i, streetViewPanoramaCamera, str, latLng, num, b, b2, b3, b4, b5);
        return streetViewPanoramaOptions;
    }

    /* renamed from: zzvr */
    public StreetViewPanoramaOptions[] newArray(int i) {
        return new StreetViewPanoramaOptions[i];
    }
}
