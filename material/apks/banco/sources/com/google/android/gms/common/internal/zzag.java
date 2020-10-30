package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

public final class zzag extends zzg<zzy> {
    private static final zzag a = new zzag();

    private zzag() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    private View a(Context context, int i, int i2) {
        try {
            SignInButtonConfig signInButtonConfig = new SignInButtonConfig(i, i2, null);
            return (View) zze.zzae(((zzy) zzcu(context)).zza(zze.zzac(context), signInButtonConfig));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("Could not get button with size ");
            sb.append(i);
            sb.append(" and color ");
            sb.append(i2);
            throw new zza(sb.toString(), e);
        }
    }

    public static View zzb(Context context, int i, int i2) {
        return a.a(context, i, i2);
    }

    /* renamed from: zzdz */
    public zzy zzc(IBinder iBinder) {
        return zzy.zza.zzdy(iBinder);
    }
}
