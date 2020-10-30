package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze implements Creator<DataHolder> {
    static void a(DataHolder dataHolder, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, dataHolder.b(), false);
        zzb.zza(parcel, 2, (T[]) dataHolder.c(), i, false);
        zzb.zzc(parcel, 3, dataHolder.getStatusCode());
        zzb.zza(parcel, 4, dataHolder.zzasz(), false);
        zzb.zzc(parcel, 1000, dataHolder.a());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcg */
    public DataHolder createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        String[] strArr = null;
        CursorWindow[] cursorWindowArr = null;
        Bundle bundle = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        strArr = zza.zzac(parcel, zzcp);
                        break;
                    case 2:
                        cursorWindowArr = (CursorWindow[]) zza.zzb(parcel, zzcp, CursorWindow.CREATOR);
                        break;
                    case 3:
                        i2 = zza.zzg(parcel, zzcp);
                        break;
                    case 4:
                        bundle = zza.zzs(parcel, zzcp);
                        break;
                    default:
                        zza.zzb(parcel, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        DataHolder dataHolder = new DataHolder(i, strArr, cursorWindowArr, i2, bundle);
        dataHolder.zzate();
        return dataHolder;
    }

    /* renamed from: zzge */
    public DataHolder[] newArray(int i) {
        return new DataHolder[i];
    }
}
