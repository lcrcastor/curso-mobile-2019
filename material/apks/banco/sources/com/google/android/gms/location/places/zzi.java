package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi implements Creator<PlaceReport> {
    static void a(PlaceReport placeReport, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, placeReport.a);
        zzb.zza(parcel, 2, placeReport.getPlaceId(), false);
        zzb.zza(parcel, 3, placeReport.getTag(), false);
        zzb.zza(parcel, 4, placeReport.getSource(), false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzny */
    public PlaceReport createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        String str = null;
        int i = 0;
        String str2 = null;
        String str3 = null;
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
                    str2 = zza.zzq(parcel, zzcp);
                    break;
                case 4:
                    str3 = zza.zzq(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new PlaceReport(i, str, str2, str3);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzvb */
    public PlaceReport[] newArray(int i) {
        return new PlaceReport[i];
    }
}
