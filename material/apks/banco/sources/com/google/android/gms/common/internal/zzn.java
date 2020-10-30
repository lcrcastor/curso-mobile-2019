package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzn {
    private static final Object a = new Object();
    private static zzn b;

    public static zzn zzcf(Context context) {
        synchronized (a) {
            if (b == null) {
                b = new zzo(context.getApplicationContext());
            }
        }
        return b;
    }

    public abstract boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract boolean zza(String str, String str2, ServiceConnection serviceConnection, String str3);

    public abstract void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract void zzb(String str, String str2, ServiceConnection serviceConnection, String str3);
}
