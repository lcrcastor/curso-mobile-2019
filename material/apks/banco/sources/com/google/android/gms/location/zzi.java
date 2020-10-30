package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public class zzi implements Creator<LocationSettingsRequest> {
    static void a(LocationSettingsRequest locationSettingsRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, locationSettingsRequest.zzbgq(), false);
        zzb.zza(parcel, 2, locationSettingsRequest.zzbph());
        zzb.zza(parcel, 3, locationSettingsRequest.zzbpi());
        zzb.zzc(parcel, 1000, locationSettingsRequest.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznj */
    public LocationSettingsRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        ArrayList arrayList = null;
        boolean z = false;
        boolean z2 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        arrayList = zza.zzc(parcel, zzcp, LocationRequest.CREATOR);
                        break;
                    case 2:
                        z = zza.zzc(parcel, zzcp);
                        break;
                    case 3:
                        z2 = zza.zzc(parcel, zzcp);
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
            return new LocationSettingsRequest(i, (List<LocationRequest>) arrayList, z, z2);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzug */
    public LocationSettingsRequest[] newArray(int i) {
        return new LocationSettingsRequest[i];
    }
}
