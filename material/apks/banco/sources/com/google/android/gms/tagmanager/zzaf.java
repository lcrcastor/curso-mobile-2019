package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;

class zzaf extends zzam {
    private static final String a = com.google.android.gms.internal.zzaf.ENCODE.toString();
    private static final String b = zzag.ARG0.toString();
    private static final String c = zzag.NO_PADDING.toString();
    private static final String d = zzag.INPUT_FORMAT.toString();
    private static final String e = zzag.OUTPUT_FORMAT.toString();

    public zzaf() {
        super(a, b);
    }

    public zza zzaw(Map<String, zza> map) {
        String str;
        byte[] bArr;
        String str2;
        zza zza = (zza) map.get(b);
        if (zza == null || zza == zzdm.zzchl()) {
            return zzdm.zzchl();
        }
        String zzg = zzdm.zzg(zza);
        zza zza2 = (zza) map.get(d);
        String zzg2 = zza2 == null ? TextBundle.TEXT_ENTRY : zzdm.zzg(zza2);
        zza zza3 = (zza) map.get(e);
        String zzg3 = zza3 == null ? "base16" : zzdm.zzg(zza3);
        int i = 2;
        zza zza4 = (zza) map.get(c);
        if (zza4 != null && zzdm.zzk(zza4).booleanValue()) {
            i = 3;
        }
        try {
            if (TextBundle.TEXT_ENTRY.equals(zzg2)) {
                bArr = zzg.getBytes();
            } else if ("base16".equals(zzg2)) {
                bArr = zzk.zzot(zzg);
            } else if ("base64".equals(zzg2)) {
                bArr = Base64.decode(zzg, i);
            } else if ("base64url".equals(zzg2)) {
                bArr = Base64.decode(zzg, i | 8);
            } else {
                String str3 = "Encode: unknown input format: ";
                String valueOf = String.valueOf(zzg2);
                zzbo.e(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                return zzdm.zzchl();
            }
            if ("base16".equals(zzg3)) {
                str2 = zzk.zzp(bArr);
            } else if ("base64".equals(zzg3)) {
                str2 = Base64.encodeToString(bArr, i);
            } else if ("base64url".equals(zzg3)) {
                str2 = Base64.encodeToString(bArr, i | 8);
            } else {
                String str4 = "Encode: unknown output format: ";
                String valueOf2 = String.valueOf(zzg3);
                str = valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4);
                zzbo.e(str);
                return zzdm.zzchl();
            }
            return zzdm.zzat(str2);
        } catch (IllegalArgumentException unused) {
            str = "Encode: invalid input:";
        }
    }

    public boolean zzcds() {
        return true;
    }
}
