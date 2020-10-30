package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzaf implements Creator<SignInButtonConfig> {
    static void a(SignInButtonConfig signInButtonConfig, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, signInButtonConfig.a);
        zzb.zzc(parcel, 2, signInButtonConfig.zzavh());
        zzb.zzc(parcel, 3, signInButtonConfig.zzavi());
        zzb.zza(parcel, 4, (T[]) signInButtonConfig.zzavj(), i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcn */
    public SignInButtonConfig createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        Scope[] scopeArr = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    i2 = zza.zzg(parcel, zzcp);
                    break;
                case 3:
                    i3 = zza.zzg(parcel, zzcp);
                    break;
                case 4:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzcp, Scope.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new SignInButtonConfig(i, i2, i3, scopeArr);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzgt */
    public SignInButtonConfig[] newArray(int i) {
        return new SignInButtonConfig[i];
    }
}
