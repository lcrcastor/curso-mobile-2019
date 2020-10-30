package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.zzsi;

public class zzaa {
    private static Object a = new Object();
    private static boolean b;
    private static String c;
    private static int d;

    private static void a(Context context) {
        synchronized (a) {
            if (!b) {
                b = true;
                try {
                    Bundle bundle = zzsi.zzcr(context).getApplicationInfo(context.getPackageName(), 128).metaData;
                    if (bundle != null) {
                        c = bundle.getString("com.google.app.id");
                        d = bundle.getInt("com.google.android.gms.version");
                    }
                } catch (NameNotFoundException e) {
                    Log.wtf("MetadataValueReader", "This should never happen.", e);
                }
            }
        }
    }

    public static String zzcg(Context context) {
        a(context);
        return c;
    }

    public static int zzch(Context context) {
        a(context);
        return d;
    }
}
