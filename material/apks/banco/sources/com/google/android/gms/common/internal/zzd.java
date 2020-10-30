package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzd implements Creator<AuthAccountRequest> {
    static void a(AuthAccountRequest authAccountRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, authAccountRequest.a);
        zzb.zza(parcel, 2, authAccountRequest.b, false);
        zzb.zza(parcel, 3, (T[]) authAccountRequest.c, i, false);
        zzb.zza(parcel, 4, authAccountRequest.d, false);
        zzb.zza(parcel, 5, authAccountRequest.e, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzci */
    public AuthAccountRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        IBinder iBinder = null;
        Scope[] scopeArr = null;
        Integer num = null;
        Integer num2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    iBinder = zza.zzr(parcel, zzcp);
                    break;
                case 3:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzcp, Scope.CREATOR);
                    break;
                case 4:
                    num = zza.zzh(parcel, zzcp);
                    break;
                case 5:
                    num2 = zza.zzh(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        AuthAccountRequest authAccountRequest = new AuthAccountRequest(i, iBinder, scopeArr, num, num2);
        return authAccountRequest;
    }

    /* renamed from: zzgk */
    public AuthAccountRequest[] newArray(int i) {
        return new AuthAccountRequest[i];
    }
}
