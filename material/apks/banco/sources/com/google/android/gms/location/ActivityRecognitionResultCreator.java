package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class ActivityRecognitionResultCreator implements Creator<ActivityRecognitionResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, activityRecognitionResult.a, false);
        zzb.zza(parcel, 2, activityRecognitionResult.b);
        zzb.zza(parcel, 3, activityRecognitionResult.c);
        zzb.zzc(parcel, 4, activityRecognitionResult.d);
        zzb.zza(parcel, 5, activityRecognitionResult.e, false);
        zzb.zzc(parcel, 1000, activityRecognitionResult.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        long j = 0;
        long j2 = 0;
        List list = null;
        Bundle bundle = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        list = zza.zzc(parcel, zzcp, DetectedActivity.CREATOR);
                        break;
                    case 2:
                        j = zza.zzi(parcel, zzcp);
                        break;
                    case 3:
                        j2 = zza.zzi(parcel, zzcp);
                        break;
                    case 4:
                        i2 = zza.zzg(parcel, zzcp);
                        break;
                    case 5:
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
        ActivityRecognitionResult activityRecognitionResult = new ActivityRecognitionResult(i, list, j, j2, i2, bundle);
        return activityRecognitionResult;
    }

    public ActivityRecognitionResult[] newArray(int i) {
        return new ActivityRecognitionResult[i];
    }
}
