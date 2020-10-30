package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze implements Creator<LatLng> {
    static void a(LatLng latLng, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, latLng.a());
        zzb.zza(parcel, 2, latLng.latitude);
        zzb.zza(parcel, 3, latLng.longitude);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzor */
    public LatLng createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        double d = 0.0d;
        double d2 = 0.0d;
        int i = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    d = zza.zzn(parcel, zzcp);
                    break;
                case 3:
                    d2 = zza.zzn(parcel, zzcp);
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
        LatLng latLng = new LatLng(i, d, d2);
        return latLng;
    }

    /* renamed from: zzvw */
    public LatLng[] newArray(int i) {
        return new LatLng[i];
    }
}
