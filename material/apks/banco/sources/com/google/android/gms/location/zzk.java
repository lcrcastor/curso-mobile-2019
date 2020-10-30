package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzk implements Creator<LocationSettingsStates> {
    static void a(LocationSettingsStates locationSettingsStates, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, locationSettingsStates.isGpsUsable());
        zzb.zza(parcel, 2, locationSettingsStates.isNetworkLocationUsable());
        zzb.zza(parcel, 3, locationSettingsStates.isBleUsable());
        zzb.zza(parcel, 4, locationSettingsStates.isGpsPresent());
        zzb.zza(parcel, 5, locationSettingsStates.isNetworkLocationPresent());
        zzb.zza(parcel, 6, locationSettingsStates.isBlePresent());
        zzb.zzc(parcel, 1000, locationSettingsStates.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznl */
    public LocationSettingsStates createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        z = zza.zzc(parcel, zzcp);
                        break;
                    case 2:
                        z2 = zza.zzc(parcel, zzcp);
                        break;
                    case 3:
                        z3 = zza.zzc(parcel, zzcp);
                        break;
                    case 4:
                        z4 = zza.zzc(parcel, zzcp);
                        break;
                    case 5:
                        z5 = zza.zzc(parcel, zzcp);
                        break;
                    case 6:
                        z6 = zza.zzc(parcel, zzcp);
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
        LocationSettingsStates locationSettingsStates = new LocationSettingsStates(i, z, z2, z3, z4, z5, z6);
        return locationSettingsStates;
    }

    /* renamed from: zzui */
    public LocationSettingsStates[] newArray(int i) {
        return new LocationSettingsStates[i];
    }
}
