package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzad implements Creator<ResolveAccountRequest> {
    static void a(ResolveAccountRequest resolveAccountRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, resolveAccountRequest.a);
        zzb.zza(parcel, 2, (Parcelable) resolveAccountRequest.getAccount(), i, false);
        zzb.zzc(parcel, 3, resolveAccountRequest.getSessionId());
        zzb.zza(parcel, 4, (Parcelable) resolveAccountRequest.zzavc(), i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcl */
    public ResolveAccountRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        Account account = null;
        int i = 0;
        GoogleSignInAccount googleSignInAccount = null;
        int i2 = 0;
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
                    i2 = zza.zzg(parcel, zzcp);
                    break;
                case 4:
                    googleSignInAccount = (GoogleSignInAccount) zza.zza(parcel, zzcp, GoogleSignInAccount.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new ResolveAccountRequest(i, account, i2, googleSignInAccount);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzgr */
    public ResolveAccountRequest[] newArray(int i) {
        return new ResolveAccountRequest[i];
    }
}
