package com.google.android.gms.analytics;

import android.support.v4.app.NotificationCompat;
import com.google.android.gms.analytics.internal.zzae;

public final class zzc {
    private static String a(String str, int i) {
        if (i < 1) {
            zzae.zzf("index out of range for prefix", str);
            return "";
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11);
        sb.append(str);
        sb.append(i);
        return sb.toString();
    }

    public static String zzbi(int i) {
        return a("&cd", i);
    }

    public static String zzbj(int i) {
        return a("cd", i);
    }

    public static String zzbk(int i) {
        return a("&cm", i);
    }

    public static String zzbl(int i) {
        return a("cm", i);
    }

    public static String zzbm(int i) {
        return a("&pr", i);
    }

    public static String zzbn(int i) {
        return a("pr", i);
    }

    public static String zzbo(int i) {
        return a("&promo", i);
    }

    public static String zzbp(int i) {
        return a(NotificationCompat.CATEGORY_PROMO, i);
    }

    public static String zzbq(int i) {
        return a("pi", i);
    }

    public static String zzbr(int i) {
        return a("&il", i);
    }

    public static String zzbs(int i) {
        return a("il", i);
    }

    public static String zzbt(int i) {
        return a("cd", i);
    }

    public static String zzbu(int i) {
        return a("cm", i);
    }
}
