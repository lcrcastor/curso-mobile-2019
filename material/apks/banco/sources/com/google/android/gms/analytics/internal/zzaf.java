package com.google.android.gms.analytics.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import java.util.Map;
import java.util.Map.Entry;

public class zzaf extends zzd {
    private static String a = "3";
    private static String b = "01VDIWEA?";
    private static zzaf c;

    public zzaf(zzf zzf) {
        super(zzf);
    }

    public static zzaf zzaew() {
        return c;
    }

    public void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        String str2 = (String) zzy.cg.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
        if (i >= 5) {
            zzb(i, str, obj, obj2, obj3);
        }
    }

    public void zza(zzab zzab, String str) {
        if (str == null) {
            str = "no reason provided";
        }
        String zzab2 = zzab != null ? zzab.toString() : "no hit data";
        String str2 = "Discarding hit. ";
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), zzab2);
    }

    public synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        zzac.zzy(str);
        if (i < 0) {
            i = 0;
        }
        if (i >= b.length()) {
            i = b.length() - 1;
        }
        char c2 = zzaap().zzacu() ? zzaap().zzact() ? 'P' : 'C' : zzaap().zzact() ? 'p' : 'c';
        String str2 = a;
        char charAt = b.charAt(i);
        String str3 = zze.VERSION;
        String valueOf = String.valueOf(zzc(str, zzm(obj), zzm(obj2), zzm(obj3)));
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(str3).length() + String.valueOf(valueOf).length());
        sb.append(str2);
        sb.append(charAt);
        sb.append(c2);
        sb.append(str3);
        sb.append(":");
        sb.append(valueOf);
        String sb2 = sb.toString();
        if (sb2.length() > 1024) {
            sb2 = sb2.substring(0, 1024);
        }
        zzai zzabc = zzaal().zzabc();
        if (zzabc != null) {
            zzabc.zzafj().zzfd(sb2);
        }
    }

    public void zzh(Map<String, String> map, String str) {
        String str2;
        if (str == null) {
            str = "no reason provided";
        }
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append((String) entry.getKey());
                sb.append('=');
                sb.append((String) entry.getValue());
            }
            str2 = sb.toString();
        } else {
            str2 = "no hit data";
        }
        String str3 = "Discarding hit. ";
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3), str2);
    }

    /* access modifiers changed from: protected */
    public String zzm(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            obj = new Long((long) ((Integer) obj).intValue());
        }
        if (!(obj instanceof Long)) {
            return obj instanceof Boolean ? String.valueOf(obj) : obj instanceof Throwable ? obj.getClass().getCanonicalName() : "-";
        }
        Long l = (Long) obj;
        if (Math.abs(l.longValue()) < 100) {
            return String.valueOf(obj);
        }
        String str = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
        String valueOf = String.valueOf(Math.abs(l.longValue()));
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1))));
        sb.append("...");
        sb.append(str);
        sb.append(Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        synchronized (zzaf.class) {
            c = this;
        }
    }
}
