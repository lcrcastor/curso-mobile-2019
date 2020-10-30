package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.stats.zzc.zzb;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzj;
import java.util.List;

public class zzh {
    private static String a = "WakeLockTracker";
    private static zzh b = new zzh();
    private static Boolean c;

    private static boolean a(Context context) {
        if (c == null) {
            c = Boolean.valueOf(b(context));
        }
        return c.booleanValue();
    }

    private static boolean b(Context context) {
        try {
            return zzd.zzact() && ((Integer) zzb.Eh.get()).intValue() != zzd.LOG_LEVEL_OFF;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public static zzh zzaxf() {
        return b;
    }

    public void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list) {
        zza(context, str, i, str2, str3, str4, i2, list, 0);
    }

    public void zza(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list, long j) {
        int i3 = i;
        if (a(context)) {
            if (TextUtils.isEmpty(str)) {
                String str5 = a;
                String str6 = "missing wakeLock key. ";
                String valueOf = String.valueOf(str);
                Log.e(str5, valueOf.length() != 0 ? str6.concat(valueOf) : new String(str6));
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (7 == i3 || 8 == i3 || 10 == i3 || 11 == i3) {
                WakeLockEvent wakeLockEvent = r1;
                WakeLockEvent wakeLockEvent2 = new WakeLockEvent(currentTimeMillis, i3, str2, i2, zzf.a(list), str, SystemClock.elapsedRealtime(), zzj.zzcn(context), str3, zzf.a(context.getPackageName()), zzj.zzco(context), j, str4);
                try {
                    context.startService(new Intent().setComponent(zzd.En).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", wakeLockEvent));
                } catch (Exception e) {
                    Log.wtf(a, e);
                }
            }
        }
    }
}
