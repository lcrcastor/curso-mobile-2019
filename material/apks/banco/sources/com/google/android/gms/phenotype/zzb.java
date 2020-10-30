package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;

public class zzb implements Creator<Flag> {
    static void a(Flag flag, Parcel parcel, int i) {
        int zzcr = com.google.android.gms.common.internal.safeparcel.zzb.zzcr(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, flag.a);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, flag.name, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, flag.b);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, flag.c);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, flag.d);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, flag.e, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, flag.f, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, flag.axr);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, flag.axs);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzrr */
    public Flag createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        String str = null;
        String str2 = null;
        byte[] bArr = null;
        long j = 0;
        double d = 0.0d;
        int i = 0;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    str = zza.zzq(parcel2, zzcp);
                    break;
                case 3:
                    j = zza.zzi(parcel2, zzcp);
                    break;
                case 4:
                    z = zza.zzc(parcel2, zzcp);
                    break;
                case 5:
                    d = zza.zzn(parcel2, zzcp);
                    break;
                case 6:
                    str2 = zza.zzq(parcel2, zzcp);
                    break;
                case 7:
                    bArr = zza.zzt(parcel2, zzcp);
                    break;
                case 8:
                    i2 = zza.zzg(parcel2, zzcp);
                    break;
                case 9:
                    i3 = zza.zzg(parcel2, zzcp);
                    break;
                default:
                    zza.zzb(parcel2, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        Flag flag = new Flag(i, str, j, z, d, str2, bArr, i2, i3);
        return flag;
    }

    /* renamed from: zzzj */
    public Flag[] newArray(int i) {
        return new Flag[i];
    }
}
