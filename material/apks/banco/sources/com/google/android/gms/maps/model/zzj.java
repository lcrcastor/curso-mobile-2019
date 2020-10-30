package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class zzj implements Creator<PolylineOptions> {
    static void a(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, polylineOptions.a());
        zzb.zzc(parcel, 2, polylineOptions.getPoints(), false);
        zzb.zza(parcel, 3, polylineOptions.getWidth());
        zzb.zzc(parcel, 4, polylineOptions.getColor());
        zzb.zza(parcel, 5, polylineOptions.getZIndex());
        zzb.zza(parcel, 6, polylineOptions.isVisible());
        zzb.zza(parcel, 7, polylineOptions.isGeodesic());
        zzb.zza(parcel, 8, polylineOptions.isClickable());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzow */
    public PolylineOptions createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        List list = null;
        int i = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        int i2 = 0;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    list = zza.zzc(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 3:
                    f = zza.zzl(parcel, zzcp);
                    break;
                case 4:
                    i2 = zza.zzg(parcel, zzcp);
                    break;
                case 5:
                    f2 = zza.zzl(parcel, zzcp);
                    break;
                case 6:
                    z = zza.zzc(parcel, zzcp);
                    break;
                case 7:
                    z2 = zza.zzc(parcel, zzcp);
                    break;
                case 8:
                    z3 = zza.zzc(parcel, zzcp);
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
        PolylineOptions polylineOptions = new PolylineOptions(i, list, f, i2, f2, z, z2, z3);
        return polylineOptions;
    }

    /* renamed from: zzwb */
    public PolylineOptions[] newArray(int i) {
        return new PolylineOptions[i];
    }
}
