package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

public final class zzi {
    private static Boolean a;
    private static Boolean b;
    private static Boolean c;
    private static Boolean d;

    @TargetApi(13)
    private static boolean a(Resources resources) {
        if (b == null) {
            Configuration configuration = resources.getConfiguration();
            b = Boolean.valueOf(zzs.zzaxm() && (configuration.screenLayout & 15) <= 3 && configuration.smallestScreenWidthDp >= 600);
        }
        return b.booleanValue();
    }

    public static boolean zzb(Resources resources) {
        boolean z = false;
        if (resources == null) {
            return false;
        }
        if (a == null) {
            boolean z2 = (resources.getConfiguration().screenLayout & 15) > 3;
            if ((zzs.zzaxk() && z2) || a(resources)) {
                z = true;
            }
            a = Boolean.valueOf(z);
        }
        return a.booleanValue();
    }

    @TargetApi(20)
    public static boolean zzcl(Context context) {
        if (c == null) {
            c = Boolean.valueOf(zzs.zzaxs() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return c.booleanValue();
    }

    @TargetApi(21)
    public static boolean zzcm(Context context) {
        if (d == null) {
            d = Boolean.valueOf(zzs.zzaxu() && context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return d.booleanValue();
    }
}
