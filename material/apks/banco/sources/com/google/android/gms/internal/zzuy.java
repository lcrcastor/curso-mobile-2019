package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;
import com.google.android.gms.internal.zzuz.zza;

public class zzuy {
    private boolean a = false;
    private zzuz b = null;

    public void initialize(Context context) {
        synchronized (this) {
            if (!this.a) {
                try {
                    this.b = zza.asInterface(zzsu.zza(context, zzsu.Oy, ModuleDescriptor.MODULE_ID).zzjd("com.google.android.gms.flags.impl.FlagProviderImpl"));
                    this.b.init(zze.zzac(context));
                    this.a = true;
                } catch (RemoteException | zzsu.zza e) {
                    Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
                }
            }
        }
    }

    public <T> T zzb(zzuw<T> zzuw) {
        synchronized (this) {
            if (this.a) {
                return zzuw.zza(this.b);
            }
            T zzkq = zzuw.zzkq();
            return zzkq;
        }
    }
}
