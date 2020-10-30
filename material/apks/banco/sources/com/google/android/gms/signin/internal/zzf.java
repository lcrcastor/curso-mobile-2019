package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzf implements Creator<RecordConsentRequest> {
    static void a(RecordConsentRequest recordConsentRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, recordConsentRequest.a);
        zzb.zza(parcel, 2, (Parcelable) recordConsentRequest.getAccount(), i, false);
        zzb.zza(parcel, 3, (T[]) recordConsentRequest.zzcdi(), i, false);
        zzb.zza(parcel, 4, recordConsentRequest.zzahn(), false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzaag */
    public RecordConsentRequest[] newArray(int i) {
        return new RecordConsentRequest[i];
    }

    /* renamed from: zzsl */
    public RecordConsentRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        Account account = null;
        int i = 0;
        Scope[] scopeArr = null;
        String str = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    account = (Account) zza.zza(parcel, zzcp, Account.CREATOR);
                    break;
                case 3:
                    scopeArr = (Scope[]) zza.zzb(parcel, zzcp, Scope.CREATOR);
                    break;
                case 4:
                    str = zza.zzq(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new RecordConsentRequest(i, account, scopeArr, str);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }
}
