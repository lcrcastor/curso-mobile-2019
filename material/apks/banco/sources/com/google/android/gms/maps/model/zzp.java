package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzp implements Creator<TileOverlayOptions> {
    static void a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, tileOverlayOptions.a());
        zzb.zza(parcel, 2, tileOverlayOptions.b(), false);
        zzb.zza(parcel, 3, tileOverlayOptions.isVisible());
        zzb.zza(parcel, 4, tileOverlayOptions.getZIndex());
        zzb.zza(parcel, 5, tileOverlayOptions.getFadeIn());
        zzb.zza(parcel, 6, tileOverlayOptions.getTransparency());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzpc */
    public TileOverlayOptions createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        IBinder iBinder = null;
        int i = 0;
        boolean z = false;
        float f = BitmapDescriptorFactory.HUE_RED;
        boolean z2 = true;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    iBinder = zza.zzr(parcel, zzcp);
                    break;
                case 3:
                    z = zza.zzc(parcel, zzcp);
                    break;
                case 4:
                    f = zza.zzl(parcel, zzcp);
                    break;
                case 5:
                    z2 = zza.zzc(parcel, zzcp);
                    break;
                case 6:
                    f2 = zza.zzl(parcel, zzcp);
                    break;
                default:
                    zza.zzb(parcel, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel);
        }
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions(i, iBinder, z, f, z2, f2);
        return tileOverlayOptions;
    }

    /* renamed from: zzwh */
    public TileOverlayOptions[] newArray(int i) {
        return new TileOverlayOptions[i];
    }
}
