package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class DetectedActivityCreator implements Creator<DetectedActivity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(DetectedActivity detectedActivity, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, detectedActivity.a);
        zzb.zzc(parcel, 2, detectedActivity.b);
        zzb.zzc(parcel, 1000, detectedActivity.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    public DetectedActivity createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        i2 = zza.zzg(parcel, zzcp);
                        break;
                    case 2:
                        i3 = zza.zzg(parcel, zzcp);
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
            return new DetectedActivity(i, i2, i3);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    public DetectedActivity[] newArray(int i) {
        return new DetectedActivity[i];
    }
}
