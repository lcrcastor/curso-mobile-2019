package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi implements Creator<SignInResponse> {
    static void a(SignInResponse signInResponse, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, signInResponse.a);
        zzb.zza(parcel, 2, (Parcelable) signInResponse.zzave(), i, false);
        zzb.zza(parcel, 3, (Parcelable) signInResponse.zzcdl(), i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzaai */
    public SignInResponse[] newArray(int i) {
        return new SignInResponse[i];
    }

    /* renamed from: zzsn */
    public SignInResponse createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        ConnectionResult connectionResult = null;
        int i = 0;
        ResolveAccountResponse resolveAccountResponse = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    connectionResult = (ConnectionResult) zza.zza(parcel, zzcp, ConnectionResult.CREATOR);
                    break;
                case 3:
                    resolveAccountResponse = (ResolveAccountResponse) zza.zza(parcel, zzcp, ResolveAccountResponse.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new SignInResponse(i, connectionResult, resolveAccountResponse);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }
}
