package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.StringToIntConverter.Entry;

public class zzc implements Creator<Entry> {
    static void a(Entry entry, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, entry.a);
        zzb.zza(parcel, 2, entry.b, false);
        zzb.zzc(parcel, 3, entry.c);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcv */
    public Entry createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    str = zza.zzq(parcel, zzcp);
                    break;
                case 3:
                    i2 = zza.zzg(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new Entry(i, str, i2);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzha */
    public Entry[] newArray(int i) {
        return new Entry[i];
    }
}
