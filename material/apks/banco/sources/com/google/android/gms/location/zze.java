package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zze implements Creator<GestureRequest> {
    static void a(GestureRequest gestureRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zza(parcel, 1, gestureRequest.zzbpg(), false);
        zzb.zzc(parcel, 1000, gestureRequest.getVersionCode());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zznh */
    public GestureRequest createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv == 1) {
                arrayList = zza.zzad(parcel, zzcp);
            } else if (zzgv != 1000) {
                zza.zzb(parcel, zzcp);
            } else {
                i = zza.zzg(parcel, zzcp);
            }
        }
        if (parcel.dataPosition() == zzcq) {
            return new GestureRequest(i, arrayList);
        }
        StringBuilder sb = new StringBuilder(37);
        sb.append("Overread allowed size end=");
        sb.append(zzcq);
        throw new C0004zza(sb.toString(), parcel);
    }

    /* renamed from: zzuc */
    public GestureRequest[] newArray(int i) {
        return new GestureRequest[i];
    }
}
