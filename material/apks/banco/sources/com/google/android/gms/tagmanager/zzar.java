package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.bouncycastle.i18n.TextBundle;

class zzar extends zzam {
    private static final String a = zzaf.HASH.toString();
    private static final String b = zzag.ARG0.toString();
    private static final String c = zzag.ALGORITHM.toString();
    private static final String d = zzag.INPUT_FORMAT.toString();

    public zzar() {
        super(a, b);
    }

    private byte[] a(String str, byte[] bArr) {
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(bArr);
        return instance.digest();
    }

    public zza zzaw(Map<String, zza> map) {
        String str;
        byte[] bArr;
        zza zza = (zza) map.get(b);
        if (zza == null || zza == zzdm.zzchl()) {
            return zzdm.zzchl();
        }
        String zzg = zzdm.zzg(zza);
        zza zza2 = (zza) map.get(c);
        String zzg2 = zza2 == null ? CommonUtils.MD5_INSTANCE : zzdm.zzg(zza2);
        zza zza3 = (zza) map.get(d);
        String zzg3 = zza3 == null ? TextBundle.TEXT_ENTRY : zzdm.zzg(zza3);
        if (TextBundle.TEXT_ENTRY.equals(zzg3)) {
            bArr = zzg.getBytes();
        } else if ("base16".equals(zzg3)) {
            bArr = zzk.zzot(zzg);
        } else {
            String str2 = "Hash: unknown input format: ";
            String valueOf = String.valueOf(zzg3);
            str = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
            zzbo.e(str);
            return zzdm.zzchl();
        }
        try {
            return zzdm.zzat(zzk.zzp(a(zzg2, bArr)));
        } catch (NoSuchAlgorithmException unused) {
            String str3 = "Hash: unknown algorithm: ";
            String valueOf2 = String.valueOf(zzg2);
            str = valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3);
        }
    }

    public boolean zzcds() {
        return true;
    }
}
