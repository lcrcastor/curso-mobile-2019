package com.google.android.gms.common.internal;

import android.util.Log;

public final class zzq {
    public static final int CO = (23 - " PII_LOG".length());
    private static final String a = null;
    private final String b;
    private final String c;

    public zzq(String str) {
        this(str, null);
    }

    public zzq(String str, String str2) {
        zzac.zzb(str, (Object) "log tag cannot be null");
        zzac.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, Integer.valueOf(23));
        this.b = str;
        if (str2 == null || str2.length() <= 0) {
            this.c = null;
        } else {
            this.c = str2;
        }
    }

    private String a(String str) {
        return this.c == null ? str : this.c.concat(str);
    }

    public void zzae(String str, String str2) {
        if (zzgp(3)) {
            Log.d(str, a(str2));
        }
    }

    public void zzaf(String str, String str2) {
        if (zzgp(5)) {
            Log.w(str, a(str2));
        }
    }

    public void zzag(String str, String str2) {
        if (zzgp(6)) {
            Log.e(str, a(str2));
        }
    }

    public void zzb(String str, String str2, Throwable th) {
        if (zzgp(4)) {
            Log.i(str, a(str2), th);
        }
    }

    public void zzc(String str, String str2, Throwable th) {
        if (zzgp(5)) {
            Log.w(str, a(str2), th);
        }
    }

    public void zzd(String str, String str2, Throwable th) {
        if (zzgp(6)) {
            Log.e(str, a(str2), th);
        }
    }

    public void zze(String str, String str2, Throwable th) {
        if (zzgp(7)) {
            Log.e(str, a(str2), th);
            Log.wtf(str, a(str2), th);
        }
    }

    public boolean zzgp(int i) {
        return Log.isLoggable(this.b, i);
    }
}
