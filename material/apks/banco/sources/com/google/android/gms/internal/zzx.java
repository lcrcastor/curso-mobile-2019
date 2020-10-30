package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb.zza;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class zzx {
    public static String zza(Map<String, String> map) {
        return zzb(map, "ISO-8859-1");
    }

    public static zza zzb(zzi zzi) {
        boolean z;
        long j;
        boolean z2;
        long j2;
        long j3;
        long j4;
        zzi zzi2 = zzi;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzi2.zzz;
        String str = (String) map.get("Date");
        long zzg = str != null ? zzg(str) : 0;
        String str2 = (String) map.get("Cache-Control");
        if (str2 != null) {
            String[] split = str2.split(",");
            j2 = 0;
            z2 = false;
            j = 0;
            for (String trim : split) {
                String trim2 = trim.trim();
                if (trim2.equals(HeaderConstants.CACHE_CONTROL_NO_CACHE) || trim2.equals(HeaderConstants.CACHE_CONTROL_NO_STORE)) {
                    return null;
                }
                if (trim2.startsWith("max-age=")) {
                    try {
                        j2 = Long.parseLong(trim2.substring(8));
                    } catch (Exception unused) {
                    }
                } else if (trim2.startsWith("stale-while-revalidate=")) {
                    j = Long.parseLong(trim2.substring(23));
                } else if (trim2.equals(HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE) || trim2.equals(HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE)) {
                    z2 = true;
                }
            }
            z = true;
        } else {
            j2 = 0;
            z2 = false;
            j = 0;
            z = false;
        }
        String str3 = (String) map.get("Expires");
        long zzg2 = str3 != null ? zzg(str3) : 0;
        String str4 = (String) map.get("Last-Modified");
        long zzg3 = str4 != null ? zzg(str4) : 0;
        String str5 = (String) map.get("ETag");
        if (z) {
            long j5 = currentTimeMillis + (j2 * 1000);
            j4 = z2 ? j5 : j5 + (j * 1000);
            j3 = j5;
        } else if (zzg <= 0 || zzg2 < zzg) {
            j4 = 0;
            j3 = 0;
        } else {
            j3 = currentTimeMillis + (zzg2 - zzg);
            j4 = j3;
        }
        zza zza = new zza();
        zza.data = zzi2.data;
        zza.zza = str5;
        zza.zze = j3;
        zza.zzd = j4;
        zza.zzb = zzg;
        zza.zzc = zzg3;
        zza.zzf = map;
        return zza;
    }

    public static String zzb(Map<String, String> map, String str) {
        String str2 = (String) map.get("Content-Type");
        if (str2 != null) {
            String[] split = str2.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals(HttpRequest.PARAM_CHARSET)) {
                    return split2[1];
                }
            }
        }
        return str;
    }

    public static long zzg(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException unused) {
            return 0;
        }
    }
}
