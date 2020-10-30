package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza.C0018zza;
import com.google.android.gms.flags.impl.zza.zzb;
import com.google.android.gms.flags.impl.zza.zzc;
import com.google.android.gms.flags.impl.zza.zzd;
import com.google.android.gms.internal.zzuz.zza;

@DynamiteApi
public class FlagProviderImpl extends zza {
    private boolean a = false;
    private SharedPreferences b;

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return !this.a ? z : C0018zza.zza(this.b, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return !this.a ? i : zzb.zza(this.b, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return !this.a ? j : zzc.zza(this.b, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return !this.a ? str2 : zzd.zza(this.b, str, str2);
    }

    public void init(com.google.android.gms.dynamic.zzd zzd) {
        Context context = (Context) zze.zzae(zzd);
        if (!this.a) {
            try {
                this.b = zzb.zzn(context.createPackageContext("com.google.android.gms", 0));
                this.a = true;
            } catch (NameNotFoundException unused) {
            }
        }
    }
}
