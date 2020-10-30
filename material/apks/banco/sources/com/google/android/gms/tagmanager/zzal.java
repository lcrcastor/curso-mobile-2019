package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzah.zzc;
import com.google.android.gms.internal.zzah.zzd;
import com.google.android.gms.internal.zzah.zzi;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzal {
    private static Map<String, Object> a(zza zza) {
        Object zzl = zzdm.zzl(zza);
        if (zzl instanceof Map) {
            return (Map) zzl;
        }
        String valueOf = String.valueOf(zzl);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
        sb.append("value: ");
        sb.append(valueOf);
        sb.append(" is not a map value, ignored.");
        zzbo.zzdf(sb.toString());
        return null;
    }

    private static void a(DataLayer dataLayer, zzd zzd) {
        for (zza zzg : zzd.zzwa) {
            dataLayer.a(zzdm.zzg(zzg));
        }
    }

    public static void a(DataLayer dataLayer, zzi zzi) {
        if (zzi.zzxp == null) {
            zzbo.zzdf("supplemental missing experimentSupplemental");
            return;
        }
        a(dataLayer, zzi.zzxp);
        b(dataLayer, zzi.zzxp);
        c(dataLayer, zzi.zzxp);
    }

    private static void b(DataLayer dataLayer, zzd zzd) {
        for (zza a : zzd.zzvz) {
            Map a2 = a(a);
            if (a2 != null) {
                dataLayer.push(a2);
            }
        }
    }

    private static void c(DataLayer dataLayer, zzd zzd) {
        zzc[] zzcArr;
        String str;
        for (zzc zzc : zzd.zzwb) {
            if (zzc.zzcb == null) {
                str = "GaExperimentRandom: No key";
            } else {
                Object obj = dataLayer.get(zzc.zzcb);
                Long valueOf = !(obj instanceof Number) ? null : Long.valueOf(((Number) obj).longValue());
                long j = zzc.zzvv;
                long j2 = zzc.zzvw;
                if (!zzc.zzvx || valueOf == null || valueOf.longValue() < j || valueOf.longValue() > j2) {
                    if (j <= j2) {
                        obj = Long.valueOf(Math.round((Math.random() * ((double) (j2 - j))) + ((double) j)));
                    } else {
                        str = "GaExperimentRandom: random range invalid";
                    }
                }
                dataLayer.a(zzc.zzcb);
                Map a = dataLayer.a(zzc.zzcb, obj);
                if (zzc.zzvy > 0) {
                    if (!a.containsKey("gtm")) {
                        a.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(zzc.zzvy)));
                    } else {
                        Object obj2 = a.get("gtm");
                        if (obj2 instanceof Map) {
                            ((Map) obj2).put("lifetime", Long.valueOf(zzc.zzvy));
                        } else {
                            zzbo.zzdf("GaExperimentRandom: gtm not a map");
                        }
                    }
                }
                dataLayer.push(a);
            }
            zzbo.zzdf(str);
        }
    }
}
