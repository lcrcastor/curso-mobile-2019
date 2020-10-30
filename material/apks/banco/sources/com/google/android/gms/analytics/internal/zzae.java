package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

@Deprecated
public class zzae {
    private static volatile Logger a;

    static {
        setLogger(new zzs());
    }

    public static Logger getLogger() {
        return a;
    }

    public static void setLogger(Logger logger) {
        a = logger;
    }

    public static void v(String str) {
        zzaf zzaew = zzaf.zzaew();
        if (zzaew != null) {
            zzaew.zzep(str);
        } else if (zzbf(0)) {
            Log.v((String) zzy.cg.get(), str);
        }
        Logger logger = a;
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static boolean zzbf(int i) {
        return getLogger() != null && getLogger().getLogLevel() <= i;
    }

    public static void zzde(String str) {
        zzaf zzaew = zzaf.zzaew();
        if (zzaew != null) {
            zzaew.zzer(str);
        } else if (zzbf(1)) {
            Log.i((String) zzy.cg.get(), str);
        }
        Logger logger = a;
        if (logger != null) {
            logger.info(str);
        }
    }

    public static void zzdf(String str) {
        zzaf zzaew = zzaf.zzaew();
        if (zzaew != null) {
            zzaew.zzes(str);
        } else if (zzbf(2)) {
            Log.w((String) zzy.cg.get(), str);
        }
        Logger logger = a;
        if (logger != null) {
            logger.warn(str);
        }
    }

    public static void zzf(String str, Object obj) {
        String str2;
        zzaf zzaew = zzaf.zzaew();
        if (zzaew != null) {
            zzaew.zze(str, obj);
        } else if (zzbf(3)) {
            if (obj != null) {
                String valueOf = String.valueOf(obj);
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(valueOf).length());
                sb.append(str);
                sb.append(":");
                sb.append(valueOf);
                str2 = sb.toString();
            } else {
                str2 = str;
            }
            Log.e((String) zzy.cg.get(), str2);
        }
        Logger logger = a;
        if (logger != null) {
            logger.error(str);
        }
    }
}
