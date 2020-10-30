package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class zzbf {
    static Map<String, String> a = new HashMap();
    private static String b;

    static void a(Context context, String str) {
        zzdd.a(context, "gtm_install_referrer", "referrer", str);
        zzaf(context, str);
    }

    public static String zzae(Context context, String str) {
        if (b == null) {
            synchronized (zzbf.class) {
                if (b == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    b = sharedPreferences != null ? sharedPreferences.getString("referrer", "") : "";
                }
            }
        }
        return zzbg(b, str);
    }

    public static void zzaf(Context context, String str) {
        String zzbg = zzbg(str, "conv");
        if (zzbg != null && zzbg.length() > 0) {
            a.put(zzbg, str);
            zzdd.a(context, "gtm_click_referrers", zzbg, str);
        }
    }

    public static String zzbg(String str, String str2) {
        if (str2 != null) {
            String str3 = "http://hostname/?";
            String valueOf = String.valueOf(str);
            return Uri.parse(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3)).getQueryParameter(str2);
        } else if (str.length() > 0) {
            return str;
        } else {
            return null;
        }
    }

    public static String zzj(Context context, String str, String str2) {
        String str3 = (String) a.get(str);
        if (str3 == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str3 = sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
            a.put(str, str3);
        }
        return zzbg(str3, str2);
    }

    public static void zzpm(String str) {
        synchronized (zzbf.class) {
            b = str;
        }
    }
}
