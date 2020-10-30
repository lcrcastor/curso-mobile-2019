package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class LocationRequestCreator implements Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(LocationRequest locationRequest, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, locationRequest.a);
        zzb.zza(parcel, 2, locationRequest.b);
        zzb.zza(parcel, 3, locationRequest.c);
        zzb.zza(parcel, 4, locationRequest.d);
        zzb.zza(parcel, 5, locationRequest.e);
        zzb.zzc(parcel, 6, locationRequest.f);
        zzb.zza(parcel, 7, locationRequest.g);
        zzb.zzc(parcel, 1000, locationRequest.a());
        zzb.zza(parcel, 8, locationRequest.h);
        zzb.zzaj(parcel, zzcr);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = zza.zzcq(parcel);
        long j = 3600000;
        long j2 = 600000;
        long j3 = Long.MAX_VALUE;
        long j4 = 0;
        int i = 0;
        int i2 = 102;
        boolean z = false;
        int i3 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        float f = BitmapDescriptorFactory.HUE_RED;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            int zzgv = zza.zzgv(zzcp);
            if (zzgv != 1000) {
                switch (zzgv) {
                    case 1:
                        i2 = zza.zzg(parcel2, zzcp);
                        break;
                    case 2:
                        j = zza.zzi(parcel2, zzcp);
                        break;
                    case 3:
                        j2 = zza.zzi(parcel2, zzcp);
                        break;
                    case 4:
                        z = zza.zzc(parcel2, zzcp);
                        break;
                    case 5:
                        j3 = zza.zzi(parcel2, zzcp);
                        break;
                    case 6:
                        i3 = zza.zzg(parcel2, zzcp);
                        break;
                    case 7:
                        f = zza.zzl(parcel2, zzcp);
                        break;
                    case 8:
                        j4 = zza.zzi(parcel2, zzcp);
                        break;
                    default:
                        zza.zzb(parcel2, zzcp);
                        break;
                }
            } else {
                i = zza.zzg(parcel2, zzcp);
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        LocationRequest locationRequest = new LocationRequest(i, i2, j, j2, z, j3, i3, f, j4);
        return locationRequest;
    }

    public LocationRequest[] newArray(int i) {
        return new LocationRequest[i];
    }
}
