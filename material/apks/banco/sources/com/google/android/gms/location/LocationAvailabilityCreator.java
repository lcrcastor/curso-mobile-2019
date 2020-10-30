package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class LocationAvailabilityCreator implements Creator<LocationAvailability> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LocationAvailability locationAvailability, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, locationAvailability.a);
        zzb.zzc(parcel, 2, locationAvailability.b);
        zzb.zza(parcel, 3, locationAvailability.c);
        zzb.zzc(parcel, 4, locationAvailability.d);
        zzb.zzc(parcel, 1000, locationAvailability.a());
        zzb.zzaj(parcel, zzcr);
    }

    public LocationAvailability createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        long j = 0;
        int i = 0;
        int i2 = 1000;
        int i3 = 1;
        int i4 = 1;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        i3 = zza.zzg(parcel, zzcp);
                        break;
                    case 2:
                        i4 = zza.zzg(parcel, zzcp);
                        break;
                    case 3:
                        j = zza.zzi(parcel, zzcp);
                        break;
                    case 4:
                        i2 = zza.zzg(parcel, zzcp);
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
        LocationAvailability locationAvailability = new LocationAvailability(i, i2, i3, i4, j);
        return locationAvailability;
    }

    public LocationAvailability[] newArray(int i) {
        return new LocationAvailability[i];
    }
}
