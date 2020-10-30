package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzh implements Creator<Status> {
    static void a(Status status, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, status.getStatusCode());
        zzb.zza(parcel, 2, status.getStatusMessage(), false);
        zzb.zza(parcel, 3, (Parcelable) status.a(), i, false);
        zzb.zzc(parcel, 1000, status.b());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzce */
    public Status createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        String str = null;
        PendingIntent pendingIntent = null;
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
                    case 3:
                        pendingIntent = (PendingIntent) zza.zza(parcel, zzcp, PendingIntent.CREATOR);
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
            return new Status(i, i2, str, pendingIntent);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzfp */
    public Status[] newArray(int i) {
        return new Status[i];
    }
}
