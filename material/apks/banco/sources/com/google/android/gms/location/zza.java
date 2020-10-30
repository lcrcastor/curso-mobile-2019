package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.WorkSource;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza implements Creator<ActivityRecognitionRequest> {
    static void a(ActivityRecognitionRequest activityRecognitionRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, activityRecognitionRequest.getIntervalMillis());
        zzb.zza(parcel, 2, activityRecognitionRequest.zzbox());
        zzb.zza(parcel, 3, (Parcelable) activityRecognitionRequest.zzboy(), i, false);
        zzb.zza(parcel, 4, activityRecognitionRequest.getTag(), false);
        zzb.zza(parcel, 5, activityRecognitionRequest.zzboz(), false);
        zzb.zza(parcel, 6, activityRecognitionRequest.zzbpa());
        zzb.zza(parcel, 7, activityRecognitionRequest.getAccountName(), false);
        zzb.zzc(parcel, 1000, activityRecognitionRequest.a());
        zzb.zza(parcel, 8, activityRecognitionRequest.zzbpb());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznd */
    public ActivityRecognitionRequest createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        long j = 0;
        long j2 = 0;
        WorkSource workSource = null;
        String str = null;
        int[] iArr = null;
        String str2 = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            int zzgv = com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        j = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                        break;
                    case 2:
                        z = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel2, zzcp);
                        break;
                    case 3:
                        workSource = (WorkSource) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel2, zzcp, WorkSource.CREATOR);
                        break;
                    case 4:
                        str = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                        break;
                    case 5:
                        iArr = com.google.android.gms.common.internal.safeparcel.zza.zzw(parcel2, zzcp);
                        break;
                    case 6:
                        z2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel2, zzcp);
                        break;
                    case 7:
                        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                        break;
                    case 8:
                        j2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                        break;
                    default:
                        com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel2, zzcp);
                        break;
                }
            } else {
                i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        ActivityRecognitionRequest activityRecognitionRequest = new ActivityRecognitionRequest(i, j, z, workSource, str, iArr, z2, str2, j2);
        return activityRecognitionRequest;
    }

    /* renamed from: zztv */
    public ActivityRecognitionRequest[] newArray(int i) {
        return new ActivityRecognitionRequest[i];
    }
}
