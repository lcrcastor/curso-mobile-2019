package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc implements Creator<GroundOverlayOptions> {
    static void a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, groundOverlayOptions.b());
        zzb.zza(parcel, 2, groundOverlayOptions.a(), false);
        zzb.zza(parcel, 3, (Parcelable) groundOverlayOptions.getLocation(), i, false);
        zzb.zza(parcel, 4, groundOverlayOptions.getWidth());
        zzb.zza(parcel, 5, groundOverlayOptions.getHeight());
        zzb.zza(parcel, 6, (Parcelable) groundOverlayOptions.getBounds(), i, false);
        zzb.zza(parcel, 7, groundOverlayOptions.getBearing());
        zzb.zza(parcel, 8, groundOverlayOptions.getZIndex());
        zzb.zza(parcel, 9, groundOverlayOptions.isVisible());
        zzb.zza(parcel, 10, groundOverlayOptions.getTransparency());
        zzb.zza(parcel, 11, groundOverlayOptions.getAnchorU());
        zzb.zza(parcel, 12, groundOverlayOptions.getAnchorV());
        zzb.zza(parcel, 13, groundOverlayOptions.isClickable());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzop */
    public GroundOverlayOptions createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        IBinder iBinder = null;
        LatLng latLng = null;
        LatLngBounds latLngBounds = null;
        int i = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        float f3 = BitmapDescriptorFactory.HUE_RED;
        float f4 = BitmapDescriptorFactory.HUE_RED;
        boolean z = false;
        float f5 = BitmapDescriptorFactory.HUE_RED;
        float f6 = BitmapDescriptorFactory.HUE_RED;
        float f7 = BitmapDescriptorFactory.HUE_RED;
        boolean z2 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    iBinder = zza.zzr(parcel2, zzcp);
                    break;
                case 3:
                    latLng = (LatLng) zza.zza(parcel2, zzcp, LatLng.CREATOR);
                    break;
                case 4:
                    f = zza.zzl(parcel2, zzcp);
                    break;
                case 5:
                    f2 = zza.zzl(parcel2, zzcp);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) zza.zza(parcel2, zzcp, LatLngBounds.CREATOR);
                    break;
                case 7:
                    f3 = zza.zzl(parcel2, zzcp);
                    break;
                case 8:
                    f4 = zza.zzl(parcel2, zzcp);
                    break;
                case 9:
                    z = zza.zzc(parcel2, zzcp);
                    break;
                case 10:
                    f5 = zza.zzl(parcel2, zzcp);
                    break;
                case 11:
                    f6 = zza.zzl(parcel2, zzcp);
                    break;
                case 12:
                    f7 = zza.zzl(parcel2, zzcp);
                    break;
                case 13:
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
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions(i, iBinder, latLng, f, f2, latLngBounds, f3, f4, z, f5, f6, f7, z2);
        return groundOverlayOptions;
    }

    /* renamed from: zzvu */
    public GroundOverlayOptions[] newArray(int i) {
        return new GroundOverlayOptions[i];
    }
}
