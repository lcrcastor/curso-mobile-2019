package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzae implements Creator<ResolveAccountResponse> {
    static void a(ResolveAccountResponse resolveAccountResponse, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, resolveAccountResponse.a);
        zzb.zza(parcel, 2, resolveAccountResponse.b, false);
        zzb.zza(parcel, 3, (Parcelable) resolveAccountResponse.zzave(), i, false);
        zzb.zza(parcel, 4, resolveAccountResponse.zzavf());
        zzb.zza(parcel, 5, resolveAccountResponse.zzavg());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcm */
    public ResolveAccountResponse createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        IBinder iBinder = null;
        ConnectionResult connectionResult = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
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
                    connectionResult = (ConnectionResult) zza.zza(parcel, zzcp, ConnectionResult.CREATOR);
                    break;
                case 4:
                    z = zza.zzc(parcel, zzcp);
                    break;
                case 5:
                    z2 = zza.zzc(parcel, zzcp);
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
        ResolveAccountResponse resolveAccountResponse = new ResolveAccountResponse(i, iBinder, connectionResult, z, z2);
        return resolveAccountResponse;
    }

    /* renamed from: zzgs */
    public ResolveAccountResponse[] newArray(int i) {
        return new ResolveAccountResponse[i];
    }
}
