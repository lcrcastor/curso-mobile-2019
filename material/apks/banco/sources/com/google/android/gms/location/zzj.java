package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzj implements Creator<LocationSettingsResult> {
    static void a(LocationSettingsResult locationSettingsResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, (Parcelable) locationSettingsResult.getStatus(), i, false);
        zzb.zza(parcel, 2, (Parcelable) locationSettingsResult.getLocationSettingsStates(), i, false);
        zzb.zzc(parcel, 1000, locationSettingsResult.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznk */
    public LocationSettingsResult createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        Status status = null;
        int i = 0;
        LocationSettingsStates locationSettingsStates = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        status = (Status) zza.zza(parcel, zzcp, Status.CREATOR);
                        break;
                    case 2:
                        locationSettingsStates = (LocationSettingsStates) zza.zza(parcel, zzcp, LocationSettingsStates.CREATOR);
                        break;
                    default:
                        zza.zzb(parcel, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new LocationSettingsResult(i, status, locationSettingsStates);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzuh */
    public LocationSettingsResult[] newArray(int i) {
        return new LocationSettingsResult[i];
    }
}
