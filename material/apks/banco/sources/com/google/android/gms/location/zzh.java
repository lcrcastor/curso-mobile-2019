package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class zzh implements Creator<LocationResult> {
    static void a(LocationResult locationResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, locationResult.getLocations(), false);
        zzb.zzc(parcel, 1000, locationResult.a());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzni */
    public LocationResult createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        List<Location> list = LocationResult.a;
        int i = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv == 1) {
                list = zza.zzc(parcel, zzcp, Location.CREATOR);
            } else if (zzgv != 1000) {
                zza.zzb(parcel, zzcp);
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new LocationResult(i, list);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzuf */
    public LocationResult[] newArray(int i) {
        return new LocationResult[i];
    }
}
