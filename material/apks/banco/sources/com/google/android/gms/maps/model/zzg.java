package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzg implements Creator<MarkerOptions> {
    static void a(MarkerOptions markerOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, markerOptions.a());
        zzb.zza(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
        zzb.zza(parcel, 3, markerOptions.getTitle(), false);
        zzb.zza(parcel, 4, markerOptions.getSnippet(), false);
        zzb.zza(parcel, 5, markerOptions.b(), false);
        zzb.zza(parcel, 6, markerOptions.getAnchorU());
        zzb.zza(parcel, 7, markerOptions.getAnchorV());
        zzb.zza(parcel, 8, markerOptions.isDraggable());
        zzb.zza(parcel, 9, markerOptions.isVisible());
        zzb.zza(parcel, 10, markerOptions.isFlat());
        zzb.zza(parcel, 11, markerOptions.getRotation());
        zzb.zza(parcel, 12, markerOptions.getInfoWindowAnchorU());
        zzb.zza(parcel, 13, markerOptions.getInfoWindowAnchorV());
        zzb.zza(parcel, 14, markerOptions.getAlpha());
        zzb.zza(parcel, 15, markerOptions.getZIndex());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzot */
    public MarkerOptions createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        LatLng latLng = null;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
        int i = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f3 = BitmapDescriptorFactory.HUE_RED;
        float f4 = 0.5f;
        float f5 = BitmapDescriptorFactory.HUE_RED;
        float f6 = 1.0f;
        float f7 = BitmapDescriptorFactory.HUE_RED;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    latLng = (LatLng) zza.zza(parcel2, zzcp, LatLng.CREATOR);
                    break;
                case 3:
                    str = zza.zzq(parcel2, zzcp);
                    break;
                case 4:
                    str2 = zza.zzq(parcel2, zzcp);
                    break;
                case 5:
                    iBinder = zza.zzr(parcel2, zzcp);
                    break;
                case 6:
                    f = zza.zzl(parcel2, zzcp);
                    break;
                case 7:
                    f2 = zza.zzl(parcel2, zzcp);
                    break;
                case 8:
                    z = zza.zzc(parcel2, zzcp);
                    break;
                case 9:
                    z2 = zza.zzc(parcel2, zzcp);
                    break;
                case 10:
                    z3 = zza.zzc(parcel2, zzcp);
                    break;
                case 11:
                    f3 = zza.zzl(parcel2, zzcp);
                    break;
                case 12:
                    f4 = zza.zzl(parcel2, zzcp);
                    break;
                case 13:
                    f5 = zza.zzl(parcel2, zzcp);
                    break;
                case 14:
                    f6 = zza.zzl(parcel2, zzcp);
                    break;
                case 15:
                    f7 = zza.zzl(parcel2, zzcp);
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
        MarkerOptions markerOptions = new MarkerOptions(i, latLng, str, str2, iBinder, f, f2, z, z2, z3, f3, f4, f5, f6, f7);
        return markerOptions;
    }

    /* renamed from: zzvy */
    public MarkerOptions[] newArray(int i) {
        return new MarkerOptions[i];
    }
}
