package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzcm extends zzam {
    private static final String a = zzaf.REGEX_GROUP.toString();
    private static final String b = zzag.ARG0.toString();
    private static final String c = zzag.ARG1.toString();
    private static final String d = zzag.IGNORE_CASE.toString();
    private static final String e = zzag.GROUP.toString();

    public zzcm() {
        super(a, b, c);
    }

    public zza zzaw(Map<String, zza> map) {
        zza zza = (zza) map.get(b);
        zza zza2 = (zza) map.get(c);
        if (zza == null || zza == zzdm.zzchl() || zza2 == null || zza2 == zzdm.zzchl()) {
            return zzdm.zzchl();
        }
        int i = 64;
        if (zzdm.zzk((zza) map.get(d)).booleanValue()) {
            i = 66;
        }
        int i2 = 1;
        zza zza3 = (zza) map.get(e);
        if (zza3 != null) {
            Long zzi = zzdm.zzi(zza3);
            if (zzi == zzdm.zzchg()) {
                return zzdm.zzchl();
            }
            i2 = zzi.intValue();
            if (i2 < 0) {
                return zzdm.zzchl();
            }
        }
        try {
            String zzg = zzdm.zzg(zza);
            String zzg2 = zzdm.zzg(zza2);
            String str = null;
            Matcher matcher = Pattern.compile(zzg2, i).matcher(zzg);
            if (matcher.find() && matcher.groupCount() >= i2) {
                str = matcher.group(i2);
            }
            return str == null ? zzdm.zzchl() : zzdm.zzat(str);
        } catch (PatternSyntaxException unused) {
            return zzdm.zzchl();
        }
    }

    public boolean zzcds() {
        return true;
    }
}
