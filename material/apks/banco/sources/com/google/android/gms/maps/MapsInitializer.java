package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.maps.internal.zzai;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class MapsInitializer {
    private static boolean a = false;

    private MapsInitializer() {
    }

    public static synchronized int initialize(Context context) {
        synchronized (MapsInitializer.class) {
            zzac.zzb(context, (Object) "Context is null");
            if (a) {
                return 0;
            }
            try {
                zza(zzai.zzdp(context));
                a = true;
                return 0;
            } catch (GooglePlayServicesNotAvailableException e) {
                return e.errorCode;
            }
        }
    }

    public static void zza(zzc zzc) {
        try {
            CameraUpdateFactory.zza(zzc.zzbsc());
            BitmapDescriptorFactory.zza(zzc.zzbsd());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
