package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

public class zza implements Creator<GoogleMapOptions> {
    static void a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, googleMapOptions.a());
        zzb.zza(parcel, 2, googleMapOptions.b());
        zzb.zza(parcel, 3, googleMapOptions.c());
        zzb.zzc(parcel, 4, googleMapOptions.getMapType());
        zzb.zza(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
        zzb.zza(parcel, 6, googleMapOptions.d());
        zzb.zza(parcel, 7, googleMapOptions.e());
        zzb.zza(parcel, 8, googleMapOptions.f());
        zzb.zza(parcel, 9, googleMapOptions.g());
        zzb.zza(parcel, 10, googleMapOptions.h());
        zzb.zza(parcel, 11, googleMapOptions.i());
        zzb.zza(parcel, 12, googleMapOptions.j());
        zzb.zza(parcel, 14, googleMapOptions.k());
        zzb.zza(parcel, 15, googleMapOptions.l());
        zzb.zza(parcel, 16, googleMapOptions.getMinZoomPreference(), false);
        zzb.zza(parcel, 17, googleMapOptions.getMaxZoomPreference(), false);
        zzb.zza(parcel, 18, (Parcelable) googleMapOptions.getLatLngBoundsForCameraTarget(), i, false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzol */
    public GoogleMapOptions createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        CameraPosition cameraPosition = null;
        Float f = null;
        Float f2 = null;
        LatLngBounds latLngBounds = null;
        int i = 0;
        byte b = -1;
        byte b2 = -1;
        int i2 = 0;
        byte b3 = -1;
        byte b4 = -1;
        byte b5 = -1;
        byte b6 = -1;
        byte b7 = -1;
        byte b8 = -1;
        byte b9 = -1;
        byte b10 = -1;
        byte b11 = -1;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    b = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 3:
                    b2 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 4:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel2, zzcp, CameraPosition.CREATOR);
                    break;
                case 6:
                    b3 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 7:
                    b4 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 8:
                    b5 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 9:
                    b6 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 10:
                    b7 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 11:
                    b8 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 12:
                    b9 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 14:
                    b10 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 15:
                    b11 = com.google.android.gms.common.internal.safeparcel.zza.zze(parcel2, zzcp);
                    break;
                case 16:
                    f = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel2, zzcp);
                    break;
                case 17:
                    f2 = com.google.android.gms.common.internal.safeparcel.zza.zzm(parcel2, zzcp);
                    break;
                case 18:
                    latLngBounds = (LatLngBounds) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel2, zzcp, LatLngBounds.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel2, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        GoogleMapOptions googleMapOptions = new GoogleMapOptions(i, b, b2, i2, cameraPosition, b3, b4, b5, b6, b7, b8, b9, b10, b11, f, f2, latLngBounds);
        return googleMapOptions;
    }

    /* renamed from: zzvq */
    public GoogleMapOptions[] newArray(int i) {
        return new GoogleMapOptions[i];
    }
}
