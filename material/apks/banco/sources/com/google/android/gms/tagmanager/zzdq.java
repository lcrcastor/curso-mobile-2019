package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzai.zza;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class zzdq {
    private static zzce<zza> a(zzce<zza> zzce) {
        try {
            return new zzce<>(zzdm.zzat(a(zzdm.zzg((zza) zzce.a()))), zzce.b());
        } catch (UnsupportedEncodingException e) {
            zzbo.zzb("Escape URI: unsupported encoding", e);
            return zzce;
        }
    }

    private static zzce<zza> a(zzce<zza> zzce, int i) {
        String sb;
        if (!a((zza) zzce.a())) {
            sb = "Escaping can only be applied to strings.";
        } else if (i == 12) {
            return a(zzce);
        } else {
            StringBuilder sb2 = new StringBuilder(39);
            sb2.append("Unsupported Value Escaping: ");
            sb2.append(i);
            sb = sb2.toString();
        }
        zzbo.e(sb);
        return zzce;
    }

    static zzce<zza> a(zzce<zza> zzce, int... iArr) {
        for (int a : iArr) {
            zzce = a(zzce, a);
        }
        return zzce;
    }

    static String a(String str) {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static boolean a(zza zza) {
        return zzdm.zzl(zza) instanceof String;
    }
}
