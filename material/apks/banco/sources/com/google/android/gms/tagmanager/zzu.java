package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class zzu extends zzam {
    private static final String a = zzaf.FUNCTION_CALL.toString();
    private static final String b = zzag.FUNCTION_CALL_NAME.toString();
    private static final String c = zzag.ADDITIONAL_PARAMS.toString();
    private final zza d;

    public interface zza {
        Object zzf(String str, Map<String, Object> map);
    }

    public zzu(zza zza2) {
        super(a, b);
        this.d = zza2;
    }

    public com.google.android.gms.internal.zzai.zza zzaw(Map<String, com.google.android.gms.internal.zzai.zza> map) {
        String sb;
        String zzg = zzdm.zzg((com.google.android.gms.internal.zzai.zza) map.get(b));
        HashMap hashMap = new HashMap();
        com.google.android.gms.internal.zzai.zza zza2 = (com.google.android.gms.internal.zzai.zza) map.get(c);
        if (zza2 != null) {
            Object zzl = zzdm.zzl(zza2);
            if (!(zzl instanceof Map)) {
                sb = "FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.";
                zzbo.zzdf(sb);
                return zzdm.zzchl();
            }
            for (Entry entry : ((Map) zzl).entrySet()) {
                hashMap.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return zzdm.zzat(this.d.zzf(zzg, hashMap));
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            StringBuilder sb2 = new StringBuilder(String.valueOf(zzg).length() + 34 + String.valueOf(valueOf).length());
            sb2.append("Custom macro/tag ");
            sb2.append(zzg);
            sb2.append(" threw exception ");
            sb2.append(valueOf);
            sb = sb2.toString();
        }
    }

    public boolean zzcds() {
        return false;
    }
}
