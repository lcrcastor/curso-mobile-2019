package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.zzc.zza;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class zzai {
    private static Context a;
    private static zzc b;

    private static Class<?> a() {
        try {
            return Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T a(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException unused) {
            String str = "Unable to instantiate the dynamic class ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        } catch (IllegalAccessException unused2) {
            String str2 = "Unable to call the default constructor of ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
    }

    private static <T> T a(ClassLoader classLoader, String str) {
        try {
            return a(((ClassLoader) zzac.zzy(classLoader)).loadClass(str));
        } catch (ClassNotFoundException unused) {
            String str2 = "Unable to find dynamic class ";
            String valueOf = String.valueOf(str);
            throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }

    private static void a(Context context) {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static zzc b(Context context) {
        if (zzbse()) {
            Log.i(zzai.class.getSimpleName(), "Making Creator statically");
            return (zzc) a(a());
        }
        Log.i(zzai.class.getSimpleName(), "Making Creator dynamically");
        return zza.zzhn((IBinder) a(c(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
    }

    private static Context c(Context context) {
        if (a == null) {
            a = zzbse() ? context.getApplicationContext() : GooglePlayServicesUtil.getRemoteContext(context);
        }
        return a;
    }

    public static boolean zzbse() {
        return false;
    }

    public static zzc zzdp(Context context) {
        zzac.zzy(context);
        if (b != null) {
            return b;
        }
        a(context);
        b = b(context);
        try {
            b.zzh(zze.zzac(c(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return b;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
