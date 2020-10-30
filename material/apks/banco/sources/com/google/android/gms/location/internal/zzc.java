package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc implements Creator<ClientIdentity> {
    static void a(ClientIdentity clientIdentity, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, clientIdentity.uid);
        zzb.zza(parcel, 2, clientIdentity.packageName, false);
        zzb.zzc(parcel, 1000, clientIdentity.a());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznm */
    public ClientIdentity createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        i2 = zza.zzg(parcel, zzcp);
                        break;
                    case 2:
                        str = zza.zzq(parcel, zzcp);
                        break;
                    default:
                        zza.zzb(parcel, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new ClientIdentity(i, i2, str);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzul */
    public ClientIdentity[] newArray(int i) {
        return new ClientIdentity[i];
    }
}
