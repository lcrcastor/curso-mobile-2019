package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class zzc implements Creator<CheckServerAuthResult> {
    static void a(CheckServerAuthResult checkServerAuthResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, checkServerAuthResult.a);
        zzb.zza(parcel, 2, checkServerAuthResult.b);
        zzb.zzc(parcel, 3, checkServerAuthResult.c, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzaae */
    public CheckServerAuthResult[] newArray(int i) {
        return new CheckServerAuthResult[i];
    }

    /* renamed from: zzsk */
    public CheckServerAuthResult createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        List list = null;
        boolean z = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    z = zza.zzc(parcel, zzcp);
                    break;
                case 3:
                    list = zza.zzc(parcel, zzcp, Scope.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new CheckServerAuthResult(i, z, list);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }
}
