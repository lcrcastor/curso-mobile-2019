package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza implements Creator<AuthAccountResult> {
    static void a(AuthAccountResult authAccountResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, authAccountResult.a);
        zzb.zzc(parcel, 2, authAccountResult.zzcdg());
        zzb.zza(parcel, 3, (Parcelable) authAccountResult.zzcdh(), i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzaad */
    public AuthAccountResult[] newArray(int i) {
        return new AuthAccountResult[i];
    }

    /* renamed from: zzsj */
    public AuthAccountResult createFromParcel(Parcel parcel) {
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        int i = 0;
        Intent intent = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, zzcp);
                    break;
                case 3:
                    intent = (Intent) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, zzcp, Intent.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new AuthAccountResult(i, i2, intent);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }
}
