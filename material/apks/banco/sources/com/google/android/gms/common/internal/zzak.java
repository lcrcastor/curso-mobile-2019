package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzak implements Creator<ValidateAccountRequest> {
    static void a(ValidateAccountRequest validateAccountRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, validateAccountRequest.a);
        zzb.zzc(parcel, 2, validateAccountRequest.zzavl());
        zzb.zza(parcel, 3, validateAccountRequest.b, false);
        zzb.zza(parcel, 4, (T[]) validateAccountRequest.zzavj(), i, false);
        zzb.zza(parcel, 5, validateAccountRequest.zzavm(), false);
        zzb.zza(parcel, 6, validateAccountRequest.getCallingPackage(), false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzco */
    public ValidateAccountRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        IBinder iBinder = null;
        Scope[] scopeArr = null;
        Bundle bundle = null;
        String str = null;
        int i = 0;
        int i2 = 0;
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
                    iBinder = zza.zzr(parcel, zzcp);
                    break;
                case 4:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzcp, Scope.CREATOR);
                    break;
                case 5:
                    bundle = zza.zzs(parcel, zzcp);
                    break;
                case 6:
                    str = zza.zzq(parcel, zzcp);
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
        ValidateAccountRequest validateAccountRequest = new ValidateAccountRequest(i, i2, iBinder, scopeArr, bundle, str);
        return validateAccountRequest;
    }

    /* renamed from: zzgu */
    public ValidateAccountRequest[] newArray(int i) {
        return new ValidateAccountRequest[i];
    }
}
