package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzi implements Creator<PolygonOptions> {
    static void a(PolygonOptions polygonOptions, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, polygonOptions.a());
        zzb.zzc(parcel, 2, polygonOptions.getPoints(), false);
        zzb.zzd(parcel, 3, polygonOptions.b(), false);
        zzb.zza(parcel, 4, polygonOptions.getStrokeWidth());
        zzb.zzc(parcel, 5, polygonOptions.getStrokeColor());
        zzb.zzc(parcel, 6, polygonOptions.getFillColor());
        zzb.zza(parcel, 7, polygonOptions.getZIndex());
        zzb.zza(parcel, 8, polygonOptions.isVisible());
        zzb.zza(parcel, 9, polygonOptions.isGeodesic());
        zzb.zza(parcel, 10, polygonOptions.isClickable());
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzov */
    public PolygonOptions createFromParcel(Parcel parcel) {
        int zzcq = zza.zzcq(parcel);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        ArrayList arrayList2 = null;
        float f = BitmapDescriptorFactory.HUE_RED;
        int i2 = 0;
        int i3 = 0;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            switch (zza.zzgv(zzcp)) {
                case 1:
                    i = zza.zzg(parcel, zzcp);
                    break;
                case 2:
                    arrayList2 = zza.zzc(parcel, zzcp, LatLng.CREATOR);
                    break;
                case 3:
                    zza.zza(parcel, zzcp, arrayList, getClass().getClassLoader());
                    break;
                case 4:
                    f = zza.zzl(parcel, zzcp);
                    break;
                case 5:
                    i2 = zza.zzg(parcel, zzcp);
                    break;
                case 6:
                    i3 = zza.zzg(parcel, zzcp);
                    break;
                case 7:
                    f2 = zza.zzl(parcel, zzcp);
                    break;
                case 8:
                    z = zza.zzc(parcel, zzcp);
                    break;
                case 9:
                    z2 = zza.zzc(parcel, zzcp);
                    break;
                case 10:
                    z3 = zza.zzc(parcel, zzcp);
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
        PolygonOptions polygonOptions = new PolygonOptions(i, arrayList2, arrayList, f, i2, i3, f2, z, z2, z3);
        return polygonOptions;
    }

    /* renamed from: zzwa */
    public PolygonOptions[] newArray(int i) {
        return new PolygonOptions[i];
    }
}
