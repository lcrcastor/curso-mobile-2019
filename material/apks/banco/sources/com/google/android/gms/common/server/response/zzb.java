package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.server.response.FieldMappingDictionary.FieldMapPair;

public class zzb implements Creator<FieldMapPair> {
    static void a(FieldMapPair fieldMapPair, Parcel parcel, int i) {
        int zzcr = com.google.android.gms.common.internal.safeparcel.zzb.zzcr(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, fieldMapPair.a);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, fieldMapPair.b, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) fieldMapPair.c, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzcx */
    public FieldMapPair createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        String str = null;
        int i = 0;
        Field field = null;
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
                    field = (Field) zza.zza(parcel, zzcp, Field.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new FieldMapPair(i, str, field);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzhc */
    public FieldMapPair[] newArray(int i) {
        return new FieldMapPair[i];
    }
}
