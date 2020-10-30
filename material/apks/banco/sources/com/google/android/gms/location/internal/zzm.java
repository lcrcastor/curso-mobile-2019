package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public class zzm implements Creator<LocationRequestInternal> {
    static void a(LocationRequestInternal locationRequestInternal, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, (Parcelable) locationRequestInternal.b, i, false);
        zzb.zza(parcel, 4, locationRequestInternal.c);
        zzb.zzc(parcel, 5, locationRequestInternal.d, false);
        zzb.zza(parcel, 6, locationRequestInternal.e, false);
        zzb.zza(parcel, 7, locationRequestInternal.f);
        zzb.zzc(parcel, 1000, locationRequestInternal.a());
        zzb.zza(parcel, 8, locationRequestInternal.g);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzno */
    public LocationRequestInternal createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        List<ClientIdentity> list = LocationRequestInternal.a;
        LocationRequest locationRequest = null;
        String str = null;
        int i = 0;
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv == 1) {
                locationRequest = (LocationRequest) zza.zza(parcel, zzcp, LocationRequest.CREATOR);
            } else if (zzgv != 1000) {
                switch (zzgv) {
                    case 4:
                        z = zza.zzc(parcel, zzcp);
                        break;
                    case 5:
                        list = zza.zzc(parcel, zzcp, ClientIdentity.CREATOR);
                        break;
                    case 6:
                        str = zza.zzq(parcel, zzcp);
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
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        LocationRequestInternal locationRequestInternal = new LocationRequestInternal(i, locationRequest, z, list, str, z2, z3);
        return locationRequestInternal;
    }

    /* renamed from: zzuo */
    public LocationRequestInternal[] newArray(int i) {
        return new LocationRequestInternal[i];
    }
}
