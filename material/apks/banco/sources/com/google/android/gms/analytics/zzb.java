package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzc;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzmi;
import com.google.android.gms.internal.zzmj;
import com.google.android.gms.internal.zzmk;
import com.google.android.gms.internal.zzml;
import com.google.android.gms.internal.zzmm;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzmp;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmr;
import com.google.android.gms.internal.zzms;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzmu;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzb extends zzc implements zzk {
    private static DecimalFormat a;
    private final zzf b;
    private final String c;
    private final Uri d;
    private final boolean e;
    private final boolean f;

    public zzb(zzf zzf, String str) {
        this(zzf, str, true, false);
    }

    public zzb(zzf zzf, String str, boolean z, boolean z2) {
        super(zzf);
        zzac.zzhz(str);
        this.b = zzf;
        this.c = str;
        this.e = z;
        this.f = z2;
        this.d = a(this.c);
    }

    static Uri a(String str) {
        zzac.zzhz(str);
        Builder builder = new Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    static String a(double d2) {
        if (a == null) {
            a = new DecimalFormat("0.######");
        }
        return a.format(d2);
    }

    private static String a(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            return null;
        } else if (obj instanceof Double) {
            Double d2 = (Double) obj;
            if (d2.doubleValue() != 0.0d) {
                return a(d2.doubleValue());
            }
            return null;
        } else if (!(obj instanceof Boolean)) {
            return String.valueOf(obj);
        } else {
            if (obj != Boolean.FALSE) {
                return "1";
            }
            return null;
        }
    }

    private static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append((String) entry.getValue());
        }
        return sb.toString();
    }

    private static void a(Map<String, String> map, String str, double d2) {
        if (d2 != 0.0d) {
            map.put(str, a(d2));
        }
    }

    private static void a(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            StringBuilder sb = new StringBuilder(23);
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            map.put(str, sb.toString());
        }
    }

    private static void a(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    private static void a(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    public static Map<String, String> zzc(zze zze) {
        HashMap hashMap = new HashMap();
        zzmm zzmm = (zzmm) zze.zza(zzmm.class);
        if (zzmm != null) {
            for (Entry entry : zzmm.zzzd().entrySet()) {
                String a2 = a(entry.getValue());
                if (a2 != null) {
                    hashMap.put((String) entry.getKey(), a2);
                }
            }
        }
        zzmr zzmr = (zzmr) zze.zza(zzmr.class);
        if (zzmr != null) {
            a((Map<String, String>) hashMap, "t", zzmr.zzzo());
            a((Map<String, String>) hashMap, "cid", zzmr.zzxs());
            a((Map<String, String>) hashMap, "uid", zzmr.getUserId());
            a((Map<String, String>) hashMap, "sc", zzmr.zzzr());
            a((Map<String, String>) hashMap, "sf", zzmr.zzzt());
            a((Map<String, String>) hashMap, "ni", zzmr.zzzs());
            a((Map<String, String>) hashMap, "adid", zzmr.zzzp());
            a((Map<String, String>) hashMap, "ate", zzmr.zzzq());
        }
        zzms zzms = (zzms) zze.zza(zzms.class);
        if (zzms != null) {
            a((Map<String, String>) hashMap, "cd", zzms.zzzv());
            a((Map<String, String>) hashMap, "a", (double) zzms.zzzw());
            a((Map<String, String>) hashMap, "dr", zzms.zzzx());
        }
        zzmp zzmp = (zzmp) zze.zza(zzmp.class);
        if (zzmp != null) {
            a((Map<String, String>) hashMap, "ec", zzmp.getCategory());
            a((Map<String, String>) hashMap, "ea", zzmp.getAction());
            a((Map<String, String>) hashMap, "el", zzmp.getLabel());
            a((Map<String, String>) hashMap, "ev", (double) zzmp.getValue());
        }
        zzmj zzmj = (zzmj) zze.zza(zzmj.class);
        if (zzmj != null) {
            a((Map<String, String>) hashMap, "cn", zzmj.getName());
            a((Map<String, String>) hashMap, "cs", zzmj.getSource());
            a((Map<String, String>) hashMap, "cm", zzmj.zzyv());
            a((Map<String, String>) hashMap, "ck", zzmj.zzyw());
            a((Map<String, String>) hashMap, "cc", zzmj.getContent());
            a((Map<String, String>) hashMap, "ci", zzmj.getId());
            a((Map<String, String>) hashMap, "anid", zzmj.zzyx());
            a((Map<String, String>) hashMap, "gclid", zzmj.zzyy());
            a((Map<String, String>) hashMap, "dclid", zzmj.zzyz());
            a((Map<String, String>) hashMap, "aclid", zzmj.zzza());
        }
        zzmq zzmq = (zzmq) zze.zza(zzmq.class);
        if (zzmq != null) {
            a((Map<String, String>) hashMap, "exd", zzmq.getDescription());
            a((Map<String, String>) hashMap, "exf", zzmq.zzzn());
        }
        zzmt zzmt = (zzmt) zze.zza(zzmt.class);
        if (zzmt != null) {
            a((Map<String, String>) hashMap, "sn", zzmt.zzzz());
            a((Map<String, String>) hashMap, "sa", zzmt.getAction());
            a((Map<String, String>) hashMap, "st", zzmt.getTarget());
        }
        zzmu zzmu = (zzmu) zze.zza(zzmu.class);
        if (zzmu != null) {
            a((Map<String, String>) hashMap, "utv", zzmu.zzaaa());
            a((Map<String, String>) hashMap, "utt", (double) zzmu.getTimeInMillis());
            a((Map<String, String>) hashMap, "utc", zzmu.getCategory());
            a((Map<String, String>) hashMap, "utl", zzmu.getLabel());
        }
        zzmk zzmk = (zzmk) zze.zza(zzmk.class);
        if (zzmk != null) {
            for (Entry entry2 : zzmk.zzzb().entrySet()) {
                String zzbj = zzc.zzbj(((Integer) entry2.getKey()).intValue());
                if (!TextUtils.isEmpty(zzbj)) {
                    hashMap.put(zzbj, (String) entry2.getValue());
                }
            }
        }
        zzml zzml = (zzml) zze.zza(zzml.class);
        if (zzml != null) {
            for (Entry entry3 : zzml.zzzc().entrySet()) {
                String zzbl = zzc.zzbl(((Integer) entry3.getKey()).intValue());
                if (!TextUtils.isEmpty(zzbl)) {
                    hashMap.put(zzbl, a(((Double) entry3.getValue()).doubleValue()));
                }
            }
        }
        zzmo zzmo = (zzmo) zze.zza(zzmo.class);
        if (zzmo != null) {
            ProductAction zzzj = zzmo.zzzj();
            if (zzzj != null) {
                for (Entry entry4 : zzzj.build().entrySet()) {
                    hashMap.put(((String) entry4.getKey()).startsWith("&") ? ((String) entry4.getKey()).substring(1) : (String) entry4.getKey(), (String) entry4.getValue());
                }
            }
            int i = 1;
            for (Promotion zzem : zzmo.zzzm()) {
                hashMap.putAll(zzem.zzem(zzc.zzbp(i)));
                i++;
            }
            int i2 = 1;
            for (Product zzem2 : zzmo.zzzk()) {
                hashMap.putAll(zzem2.zzem(zzc.zzbn(i2)));
                i2++;
            }
            int i3 = 1;
            for (Entry entry5 : zzmo.zzzl().entrySet()) {
                List<Product> list = (List) entry5.getValue();
                String zzbs = zzc.zzbs(i3);
                int i4 = 1;
                for (Product product : list) {
                    String valueOf = String.valueOf(zzbs);
                    String valueOf2 = String.valueOf(zzc.zzbq(i4));
                    hashMap.putAll(product.zzem(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry5.getKey())) {
                    String valueOf3 = String.valueOf(zzbs);
                    String valueOf4 = String.valueOf("nm");
                    hashMap.put(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), (String) entry5.getKey());
                }
                i3++;
            }
        }
        zzmn zzmn = (zzmn) zze.zza(zzmn.class);
        if (zzmn != null) {
            a((Map<String, String>) hashMap, "ul", zzmn.getLanguage());
            a((Map<String, String>) hashMap, "sd", (double) zzmn.zzze());
            a(hashMap, "sr", zzmn.zzzf(), zzmn.zzzg());
            a(hashMap, "vp", zzmn.zzzh(), zzmn.zzzi());
        }
        zzmi zzmi = (zzmi) zze.zza(zzmi.class);
        if (zzmi != null) {
            a((Map<String, String>) hashMap, "an", zzmi.zzys());
            a((Map<String, String>) hashMap, "aid", zzmi.zzti());
            a((Map<String, String>) hashMap, "aiid", zzmi.zzyu());
            a((Map<String, String>) hashMap, "av", zzmi.zzyt());
        }
        return hashMap;
    }

    public void zzb(zze zze) {
        zzac.zzy(zze);
        zzac.zzb(zze.zzyb(), (Object) "Can't deliver not submitted measurement");
        zzac.zzhr("deliver should be called on worker thread");
        zze zzxw = zze.zzxw();
        zzmr zzmr = (zzmr) zzxw.zzb(zzmr.class);
        if (TextUtils.isEmpty(zzmr.zzzo())) {
            zzaao().zzh(zzc(zzxw), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(zzmr.zzxs())) {
            zzaao().zzh(zzc(zzxw), "Ignoring measurement without client id");
        } else if (!this.b.zzabb().getAppOptOut()) {
            double zzzt = zzmr.zzzt();
            if (zzao.zza(zzzt, zzmr.zzxs())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzzt));
                return;
            }
            Map zzc = zzc(zzxw);
            zzc.put("v", "1");
            zzc.put("_v", zze.aK);
            zzc.put("tid", this.c);
            if (this.b.zzabb().isDryRunEnabled()) {
                zzc("Dry run is enabled. GoogleAnalytics would have sent", a(zzc));
                return;
            }
            HashMap hashMap = new HashMap();
            zzao.zzc(hashMap, "uid", zzmr.getUserId());
            zzmi zzmi = (zzmi) zze.zza(zzmi.class);
            if (zzmi != null) {
                zzao.zzc(hashMap, "an", zzmi.zzys());
                zzao.zzc(hashMap, "aid", zzmi.zzti());
                zzao.zzc(hashMap, "av", zzmi.zzyt());
                zzao.zzc(hashMap, "aiid", zzmi.zzyu());
            }
            zzh zzh = new zzh(0, zzmr.zzxs(), this.c, !TextUtils.isEmpty(zzmr.zzzp()), 0, hashMap);
            zzc.put("_s", String.valueOf(zzxu().zza(zzh)));
            zzab zzab = new zzab(zzaao(), zzc, zze.zzxz(), true);
            zzxu().zza(zzab);
        }
    }

    public Uri zzxl() {
        return this.d;
    }
}
