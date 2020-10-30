package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;

public class zzb implements Creator<CircleOptions> {
    static void a(CircleOptions circleOptions, Parcel parcel, int i) {
        int zzcr = com.google.android.gms.common.internal.safeparcel.zzb.zzcr(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, circleOptions.a());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) circleOptions.getCenter(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, circleOptions.getRadius());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, circleOptions.getStrokeWidth());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, circleOptions.getStrokeColor());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, circleOptions.getFillColor());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, circleOptions.getZIndex());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, circleOptions.isVisible());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, circleOptions.isClickable());
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzoo */
    public CircleOptions createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        LatLng latLng = null;
        double d = 0.0d;
        int i = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        int i2 = 0;
        int i3 = 0;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        boolean z = false;
        boolean z2 = false;
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
                    d = zza.zzn(parcel2, zzcp);
                    break;
                case 4:
                    f = zza.zzl(parcel2, zzcp);
                    break;
                case 5:
                    i2 = zza.zzg(parcel2, zzcp);
                    break;
                case 6:
                    i3 = zza.zzg(parcel2, zzcp);
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
        CircleOptions circleOptions = new CircleOptions(i, latLng, d, f, i2, i3, f2, z, z2);
        return circleOptions;
    }

    /* renamed from: zzvt */
    public CircleOptions[] newArray(int i) {
        return new CircleOptions[i];
    }
}
