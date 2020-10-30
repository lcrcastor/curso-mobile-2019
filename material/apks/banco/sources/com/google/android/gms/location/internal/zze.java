package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze implements Creator<FusedLocationProviderResult> {
    static void a(FusedLocationProviderResult fusedLocationProviderResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, (Parcelable) fusedLocationProviderResult.getStatus(), i, false);
        zzb.zzc(parcel, 1000, fusedLocationProviderResult.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznn */
    public FusedLocationProviderResult createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        Status status = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv == 1) {
                status = (Status) zza.zza(parcel, zzcp, Status.CREATOR);
            } else if (zzgv != 1000) {
                zza.zzb(parcel, zzcp);
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new FusedLocationProviderResult(i, status);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzum */
    public FusedLocationProviderResult[] newArray(int i) {
        return new FusedLocationProviderResult[i];
    }
}
