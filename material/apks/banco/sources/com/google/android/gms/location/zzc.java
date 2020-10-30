package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.internal.ClientIdentity;
import java.util.ArrayList;
import java.util.List;

public class zzc implements Creator<ActivityTransitionRequest> {
    static void a(ActivityTransitionRequest activityTransitionRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, activityTransitionRequest.zzbpd(), false);
        zzb.zza(parcel, 2, activityTransitionRequest.getTag(), false);
        zzb.zzc(parcel, 3, activityTransitionRequest.zzbpe(), false);
        zzb.zzc(parcel, 1000, activityTransitionRequest.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznf */
    public ActivityTransitionRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        ArrayList arrayList = null;
        int i = 0;
        String str = null;
        List list = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        arrayList = zza.zzc(parcel, zzcp, ActivityTransition.CREATOR);
                        break;
                    case 2:
                        str = zza.zzq(parcel, zzcp);
                        break;
                    case 3:
                        list = zza.zzc(parcel, zzcp, ClientIdentity.CREATOR);
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
            return new ActivityTransitionRequest(i, arrayList, str, list);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zztx */
    public ActivityTransitionRequest[] newArray(int i) {
        return new ActivityTransitionRequest[i];
    }
}
