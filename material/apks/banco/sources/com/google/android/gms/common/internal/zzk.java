package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzk implements Creator<GetServiceRequest> {
    static void a(GetServiceRequest getServiceRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, getServiceRequest.a);
        zzb.zzc(parcel, 2, getServiceRequest.b);
        zzb.zzc(parcel, 3, getServiceRequest.c);
        zzb.zza(parcel, 4, getServiceRequest.d, false);
        zzb.zza(parcel, 5, getServiceRequest.e, false);
        zzb.zza(parcel, 6, (T[]) getServiceRequest.f, i, false);
        zzb.zza(parcel, 7, getServiceRequest.g, false);
        zzb.zza(parcel, 8, (Parcelable) getServiceRequest.h, i, false);
        zzb.zza(parcel, 9, getServiceRequest.i);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzck */
    public GetServiceRequest createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        String str = null;
        IBinder iBinder = null;
        Scope[] scopeArr = null;
        Bundle bundle = null;
        Account account = null;
        long j = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    i2 = zza.zzg(parcel2, zzcp);
                    break;
                case 3:
                    i3 = zza.zzg(parcel2, zzcp);
                    break;
                case 4:
                    str = zza.zzq(parcel2, zzcp);
                    break;
                case 5:
                    iBinder = zza.zzr(parcel2, zzcp);
                    break;
                case 6:
                    scopeArr = (Scope[]) zza.zzb(parcel2, zzcp, Scope.CREATOR);
                    break;
                case 7:
                    bundle = zza.zzs(parcel2, zzcp);
                    break;
                case 8:
                    account = (Account) zza.zza(parcel2, zzcp, Account.CREATOR);
                    break;
                case 9:
                    j = zza.zzi(parcel2, zzcp);
                    break;
                default:
                    zza.zzb(parcel2, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        GetServiceRequest getServiceRequest = new GetServiceRequest(i, i2, i3, str, iBinder, scopeArr, bundle, account, j);
        return getServiceRequest;
    }

    /* renamed from: zzgn */
    public GetServiceRequest[] newArray(int i) {
        return new GetServiceRequest[i];
    }
}
