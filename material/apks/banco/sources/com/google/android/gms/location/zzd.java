package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.internal.ParcelableGeofence;
import java.util.ArrayList;
import java.util.List;

public class zzd implements Creator<GeofencingRequest> {
    static void a(GeofencingRequest geofencingRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, geofencingRequest.zzbpf(), false);
        zzb.zzc(parcel, 2, geofencingRequest.getInitialTrigger());
        zzb.zzc(parcel, 1000, geofencingRequest.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzng */
    public GeofencingRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        ArrayList arrayList = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        arrayList = zza.zzc(parcel, zzcp, ParcelableGeofence.CREATOR);
                        break;
                    case 2:
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
        if (parcel.dataPosition() == zzcq) {
            return new GeofencingRequest(i, (List<ParcelableGeofence>) arrayList, i2);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzub */
    public GeofencingRequest[] newArray(int i) {
        return new GeofencingRequest[i];
    }
}
