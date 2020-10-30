package com.google.android.gms.tagmanager;

import android.content.Context;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafg.zze;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah.zzi;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzcx {
    private static final zzce<com.google.android.gms.internal.zzai.zza> a = new zzce<>(zzdm.zzchl(), true);
    private final com.google.android.gms.internal.zzafg.zzc b;
    private final zzaj c;
    private final Map<String, zzam> d;
    private final Map<String, zzam> e;
    private final Map<String, zzam> f;
    private final zzl<com.google.android.gms.internal.zzafg.zza, zzce<com.google.android.gms.internal.zzai.zza>> g;
    private final zzl<String, zzb> h;
    private final Set<zze> i;
    private final DataLayer j;
    private final Map<String, zzc> k;
    private volatile String l;
    private int m;

    interface zza {
        void a(zze zze, Set<com.google.android.gms.internal.zzafg.zza> set, Set<com.google.android.gms.internal.zzafg.zza> set2, zzcs zzcs);
    }

    static class zzb {
        private zzce<com.google.android.gms.internal.zzai.zza> a;
        private com.google.android.gms.internal.zzai.zza b;

        public zzb(zzce<com.google.android.gms.internal.zzai.zza> zzce, com.google.android.gms.internal.zzai.zza zza) {
            this.a = zzce;
            this.b = zza;
        }

        public zzce<com.google.android.gms.internal.zzai.zza> a() {
            return this.a;
        }

        public com.google.android.gms.internal.zzai.zza b() {
            return this.b;
        }

        public int c() {
            return ((com.google.android.gms.internal.zzai.zza) this.a.a()).da() + (this.b == null ? 0 : this.b.da());
        }
    }

    static class zzc {
        private final Set<zze> a = new HashSet();
        private final Map<zze, List<com.google.android.gms.internal.zzafg.zza>> b = new HashMap();
        private final Map<zze, List<com.google.android.gms.internal.zzafg.zza>> c = new HashMap();
        private final Map<zze, List<String>> d = new HashMap();
        private final Map<zze, List<String>> e = new HashMap();
        private com.google.android.gms.internal.zzafg.zza f;

        public Set<zze> a() {
            return this.a;
        }

        public void a(com.google.android.gms.internal.zzafg.zza zza) {
            this.f = zza;
        }

        public void a(zze zze) {
            this.a.add(zze);
        }

        public void a(zze zze, com.google.android.gms.internal.zzafg.zza zza) {
            List list = (List) this.b.get(zze);
            if (list == null) {
                list = new ArrayList();
                this.b.put(zze, list);
            }
            list.add(zza);
        }

        public void a(zze zze, String str) {
            List list = (List) this.d.get(zze);
            if (list == null) {
                list = new ArrayList();
                this.d.put(zze, list);
            }
            list.add(str);
        }

        public Map<zze, List<com.google.android.gms.internal.zzafg.zza>> b() {
            return this.b;
        }

        public void b(zze zze, com.google.android.gms.internal.zzafg.zza zza) {
            List list = (List) this.c.get(zze);
            if (list == null) {
                list = new ArrayList();
                this.c.put(zze, list);
            }
            list.add(zza);
        }

        public void b(zze zze, String str) {
            List list = (List) this.e.get(zze);
            if (list == null) {
                list = new ArrayList();
                this.e.put(zze, list);
            }
            list.add(str);
        }

        public Map<zze, List<String>> c() {
            return this.d;
        }

        public Map<zze, List<String>> d() {
            return this.e;
        }

        public Map<zze, List<com.google.android.gms.internal.zzafg.zza>> e() {
            return this.c;
        }

        public com.google.android.gms.internal.zzafg.zza f() {
            return this.f;
        }
    }

    public zzcx(Context context, com.google.android.gms.internal.zzafg.zzc zzc2, DataLayer dataLayer, com.google.android.gms.tagmanager.zzu.zza zza2, com.google.android.gms.tagmanager.zzu.zza zza3, zzaj zzaj) {
        if (zzc2 == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.b = zzc2;
        this.i = new HashSet(zzc2.zzcjq());
        this.j = dataLayer;
        this.c = zzaj;
        this.g = new zzm().a(1048576, new com.google.android.gms.tagmanager.zzm.zza<com.google.android.gms.internal.zzafg.zza, zzce<com.google.android.gms.internal.zzai.zza>>() {
            /* renamed from: a */
            public int sizeOf(com.google.android.gms.internal.zzafg.zza zza, zzce<com.google.android.gms.internal.zzai.zza> zzce) {
                return ((com.google.android.gms.internal.zzai.zza) zzce.a()).da();
            }
        });
        this.h = new zzm().a(1048576, new com.google.android.gms.tagmanager.zzm.zza<String, zzb>() {
            /* renamed from: a */
            public int sizeOf(String str, zzb zzb) {
                return str.length() + zzb.c();
            }
        });
        this.d = new HashMap();
        b((zzam) new zzj(context));
        b((zzam) new zzu(zza3));
        b((zzam) new zzy(dataLayer));
        b((zzam) new zzdn(context, dataLayer));
        this.e = new HashMap();
        c((zzam) new zzs());
        c((zzam) new zzag());
        c((zzam) new zzah());
        c((zzam) new zzao());
        c((zzam) new zzap());
        c((zzam) new zzbk());
        c((zzam) new zzbl());
        c((zzam) new zzcn());
        c((zzam) new zzdg());
        this.f = new HashMap();
        a((zzam) new zzb(context));
        a((zzam) new zzc(context));
        a((zzam) new zze(context));
        a((zzam) new zzf(context));
        a((zzam) new zzg(context));
        a((zzam) new zzh(context));
        a((zzam) new zzi(context));
        a((zzam) new zzn());
        a((zzam) new zzr(this.b.getVersion()));
        a((zzam) new zzu(zza2));
        a((zzam) new zzw(dataLayer));
        a((zzam) new zzab(context));
        a((zzam) new zzac());
        a((zzam) new zzaf());
        a((zzam) new zzak(this));
        a((zzam) new zzaq());
        a((zzam) new zzar());
        a((zzam) new zzbe(context));
        a((zzam) new zzbg());
        a((zzam) new zzbj());
        a((zzam) new zzbq());
        a((zzam) new zzbs(context));
        a((zzam) new zzcf());
        a((zzam) new zzch());
        a((zzam) new zzck());
        a((zzam) new zzcm());
        a((zzam) new zzco(context));
        a((zzam) new zzcy());
        a((zzam) new zzcz());
        a((zzam) new zzdi());
        a((zzam) new zzdo());
        this.k = new HashMap();
        for (zze zze : this.i) {
            if (zzaj.a()) {
                a(zze.zzckz(), zze.zzcla(), "add macro");
                a(zze.zzcle(), zze.zzclb(), "remove macro");
                a(zze.zzcjw(), zze.zzclc(), "add tag");
                a(zze.zzcjx(), zze.zzcld(), "remove tag");
            }
            for (int i2 = 0; i2 < zze.zzckz().size(); i2++) {
                com.google.android.gms.internal.zzafg.zza zza4 = (com.google.android.gms.internal.zzafg.zza) zze.zzckz().get(i2);
                String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                if (zzaj.a() && i2 < zze.zzcla().size()) {
                    str = (String) zze.zzcla().get(i2);
                }
                zzc a2 = a(this.k, a(zza4));
                a2.a(zze);
                a2.a(zze, zza4);
                a2.a(zze, str);
            }
            for (int i3 = 0; i3 < zze.zzcle().size(); i3++) {
                com.google.android.gms.internal.zzafg.zza zza5 = (com.google.android.gms.internal.zzafg.zza) zze.zzcle().get(i3);
                String str2 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                if (zzaj.a() && i3 < zze.zzclb().size()) {
                    str2 = (String) zze.zzclb().get(i3);
                }
                zzc a3 = a(this.k, a(zza5));
                a3.a(zze);
                a3.b(zze, zza5);
                a3.b(zze, str2);
            }
        }
        for (Entry entry : this.b.zzckw().entrySet()) {
            for (com.google.android.gms.internal.zzafg.zza zza6 : (List) entry.getValue()) {
                if (!zzdm.zzk((com.google.android.gms.internal.zzai.zza) zza6.zzcjs().get(zzag.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    a(this.k, (String) entry.getKey()).a(zza6);
                }
            }
        }
    }

    private zzce<com.google.android.gms.internal.zzai.zza> a(com.google.android.gms.internal.zzai.zza zza2, Set<String> set, zzdp zzdp) {
        if (!zza2.zzyd) {
            return new zzce<>(zza2, true);
        }
        int i2 = zza2.type;
        if (i2 != 7) {
            switch (i2) {
                case 2:
                    com.google.android.gms.internal.zzai.zza zzo = zzafg.zzo(zza2);
                    zzo.zzxu = new com.google.android.gms.internal.zzai.zza[zza2.zzxu.length];
                    for (int i3 = 0; i3 < zza2.zzxu.length; i3++) {
                        zzce<com.google.android.gms.internal.zzai.zza> a2 = a(zza2.zzxu[i3], set, zzdp.a(i3));
                        if (a2 == a) {
                            return a;
                        }
                        zzo.zzxu[i3] = (com.google.android.gms.internal.zzai.zza) a2.a();
                    }
                    return new zzce<>(zzo, false);
                case 3:
                    com.google.android.gms.internal.zzai.zza zzo2 = zzafg.zzo(zza2);
                    if (zza2.zzxv.length != zza2.zzxw.length) {
                        String str = "Invalid serving value: ";
                        String valueOf = String.valueOf(zza2.toString());
                        zzbo.e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                        return a;
                    }
                    zzo2.zzxv = new com.google.android.gms.internal.zzai.zza[zza2.zzxv.length];
                    zzo2.zzxw = new com.google.android.gms.internal.zzai.zza[zza2.zzxv.length];
                    for (int i4 = 0; i4 < zza2.zzxv.length; i4++) {
                        zzce<com.google.android.gms.internal.zzai.zza> a3 = a(zza2.zzxv[i4], set, zzdp.b(i4));
                        zzce<com.google.android.gms.internal.zzai.zza> a4 = a(zza2.zzxw[i4], set, zzdp.c(i4));
                        if (a3 == a || a4 == a) {
                            return a;
                        }
                        zzo2.zzxv[i4] = (com.google.android.gms.internal.zzai.zza) a3.a();
                        zzo2.zzxw[i4] = (com.google.android.gms.internal.zzai.zza) a4.a();
                    }
                    return new zzce<>(zzo2, false);
                case 4:
                    if (set.contains(zza2.zzxx)) {
                        String valueOf2 = String.valueOf(zza2.zzxx);
                        String valueOf3 = String.valueOf(set.toString());
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 79 + String.valueOf(valueOf3).length());
                        sb.append("Macro cycle detected.  Current macro reference: ");
                        sb.append(valueOf2);
                        sb.append(".  Previous macro references: ");
                        sb.append(valueOf3);
                        sb.append(".");
                        zzbo.e(sb.toString());
                        return a;
                    }
                    set.add(zza2.zzxx);
                    zzce<com.google.android.gms.internal.zzai.zza> a5 = zzdq.a(a(zza2.zzxx, set, zzdp.a()), zza2.zzyc);
                    set.remove(zza2.zzxx);
                    return a5;
                default:
                    int i5 = zza2.type;
                    StringBuilder sb2 = new StringBuilder(25);
                    sb2.append("Unknown type: ");
                    sb2.append(i5);
                    zzbo.e(sb2.toString());
                    return a;
            }
        } else {
            com.google.android.gms.internal.zzai.zza zzo3 = zzafg.zzo(zza2);
            zzo3.zzyb = new com.google.android.gms.internal.zzai.zza[zza2.zzyb.length];
            for (int i6 = 0; i6 < zza2.zzyb.length; i6++) {
                zzce<com.google.android.gms.internal.zzai.zza> a6 = a(zza2.zzyb[i6], set, zzdp.d(i6));
                if (a6 == a) {
                    return a;
                }
                zzo3.zzyb[i6] = (com.google.android.gms.internal.zzai.zza) a6.a();
            }
            return new zzce<>(zzo3, false);
        }
    }

    private zzce<com.google.android.gms.internal.zzai.zza> a(String str, Set<String> set, zzbr zzbr) {
        com.google.android.gms.internal.zzafg.zza zza2;
        this.m++;
        zzb zzb2 = (zzb) this.h.a(str);
        if (zzb2 == null || this.c.a()) {
            zzc zzc2 = (zzc) this.k.get(str);
            if (zzc2 == null) {
                String valueOf = String.valueOf(b());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 15 + String.valueOf(str).length());
                sb.append(valueOf);
                sb.append("Invalid macro: ");
                sb.append(str);
                zzbo.e(sb.toString());
                this.m--;
                return a;
            }
            zzce a2 = a(str, zzc2.a(), zzc2.b(), zzc2.c(), zzc2.e(), zzc2.d(), set, zzbr.b());
            if (((Set) a2.a()).isEmpty()) {
                zza2 = zzc2.f();
            } else {
                if (((Set) a2.a()).size() > 1) {
                    String valueOf2 = String.valueOf(b());
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 37 + String.valueOf(str).length());
                    sb2.append(valueOf2);
                    sb2.append("Multiple macros active for macroName ");
                    sb2.append(str);
                    zzbo.zzdf(sb2.toString());
                }
                zza2 = (com.google.android.gms.internal.zzafg.zza) ((Set) a2.a()).iterator().next();
            }
            if (zza2 == null) {
                this.m--;
                return a;
            }
            zzce<com.google.android.gms.internal.zzai.zza> a3 = a(this.f, zza2, set, zzbr.a());
            zzce<com.google.android.gms.internal.zzai.zza> zzce = a3 == a ? a : new zzce<>((com.google.android.gms.internal.zzai.zza) a3.a(), a2.b() && a3.b());
            com.google.android.gms.internal.zzai.zza zzcgl = zza2.zzcgl();
            if (zzce.b()) {
                this.h.a(str, new zzb(zzce, zzcgl));
            }
            a(zzcgl, set);
            this.m--;
            return zzce;
        }
        a(zzb2.b(), set);
        this.m--;
        return zzb2.a();
    }

    private zzce<com.google.android.gms.internal.zzai.zza> a(Map<String, zzam> map, com.google.android.gms.internal.zzafg.zza zza2, Set<String> set, zzcp zzcp) {
        String sb;
        com.google.android.gms.internal.zzai.zza zza3 = (com.google.android.gms.internal.zzai.zza) zza2.zzcjs().get(zzag.FUNCTION.toString());
        if (zza3 == null) {
            sb = "No function id in properties";
        } else {
            String str = zza3.zzxy;
            zzam zzam = (zzam) map.get(str);
            if (zzam == null) {
                sb = String.valueOf(str).concat(" has no backing implementation.");
            } else {
                zzce<com.google.android.gms.internal.zzai.zza> zzce = (zzce) this.g.a(zza2);
                if (zzce != null && !this.c.a()) {
                    return zzce;
                }
                HashMap hashMap = new HashMap();
                boolean z = true;
                boolean z2 = true;
                for (Entry entry : zza2.zzcjs().entrySet()) {
                    zzce<com.google.android.gms.internal.zzai.zza> a2 = a((com.google.android.gms.internal.zzai.zza) entry.getValue(), set, zzcp.a((String) entry.getKey()).a((com.google.android.gms.internal.zzai.zza) entry.getValue()));
                    if (a2 == a) {
                        return a;
                    }
                    if (a2.b()) {
                        zza2.zza((String) entry.getKey(), (com.google.android.gms.internal.zzai.zza) a2.a());
                    } else {
                        z2 = false;
                    }
                    hashMap.put((String) entry.getKey(), (com.google.android.gms.internal.zzai.zza) a2.a());
                }
                if (!zzam.a(hashMap.keySet())) {
                    String valueOf = String.valueOf(zzam.zzcfg());
                    String valueOf2 = String.valueOf(hashMap.keySet());
                    StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
                    sb2.append("Incorrect keys for function ");
                    sb2.append(str);
                    sb2.append(" required ");
                    sb2.append(valueOf);
                    sb2.append(" had ");
                    sb2.append(valueOf2);
                    sb = sb2.toString();
                } else {
                    if (!z2 || !zzam.zzcds()) {
                        z = false;
                    }
                    zzce<com.google.android.gms.internal.zzai.zza> zzce2 = new zzce<>(zzam.zzaw(hashMap), z);
                    if (z) {
                        this.g.a(zza2, zzce2);
                    }
                    zzcp.a((com.google.android.gms.internal.zzai.zza) zzce2.a());
                    return zzce2;
                }
            }
        }
        zzbo.e(sb);
        return a;
    }

    private zzce<Set<com.google.android.gms.internal.zzafg.zza>> a(Set<zze> set, Set<String> set2, zza zza2, zzcw zzcw) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Iterator it = set.iterator();
        while (true) {
            boolean z = true;
            while (true) {
                if (it.hasNext()) {
                    zze zze = (zze) it.next();
                    zzcs a2 = zzcw.a();
                    zzce a3 = a(zze, set2, a2);
                    if (((Boolean) a3.a()).booleanValue()) {
                        zza2.a(zze, hashSet, hashSet2, a2);
                    }
                    if (!z || !a3.b()) {
                        z = false;
                    }
                } else {
                    hashSet.removeAll(hashSet2);
                    zzcw.a(hashSet);
                    return new zzce<>(hashSet, z);
                }
            }
        }
    }

    private static zzc a(Map<String, zzc> map, String str) {
        zzc zzc2 = (zzc) map.get(str);
        if (zzc2 != null) {
            return zzc2;
        }
        zzc zzc3 = new zzc();
        map.put(str, zzc3);
        return zzc3;
    }

    private static String a(com.google.android.gms.internal.zzafg.zza zza2) {
        return zzdm.zzg((com.google.android.gms.internal.zzai.zza) zza2.zzcjs().get(zzag.INSTANCE_NAME.toString()));
    }

    private void a(com.google.android.gms.internal.zzai.zza zza2, Set<String> set) {
        if (zza2 != null) {
            zzce<com.google.android.gms.internal.zzai.zza> a2 = a(zza2, set, (zzdp) new zzcc());
            if (a2 != a) {
                Object zzl = zzdm.zzl((com.google.android.gms.internal.zzai.zza) a2.a());
                if (zzl instanceof Map) {
                    this.j.push((Map) zzl);
                    return;
                }
                if (zzl instanceof List) {
                    for (Object next : (List) zzl) {
                        if (next instanceof Map) {
                            this.j.push((Map) next);
                        } else {
                            zzbo.zzdf("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    zzbo.zzdf("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private static void a(List<com.google.android.gms.internal.zzafg.zza> list, List<String> list2, String str) {
        if (list.size() != list2.size()) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 102);
            sb.append("Invalid resource: imbalance of rule names of functions for ");
            sb.append(str);
            sb.append(" operation. Using default rule name instead");
            zzbo.zzde(sb.toString());
        }
    }

    private static void a(Map<String, zzam> map, zzam zzam) {
        if (map.containsKey(zzam.zzcff())) {
            String str = "Duplicate function type name: ";
            String valueOf = String.valueOf(zzam.zzcff());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        map.put(zzam.zzcff(), zzam);
    }

    private String b() {
        if (this.m <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.m));
        for (int i2 = 2; i2 < this.m; i2++) {
            sb.append(TokenParser.SP);
        }
        sb.append(": ");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public zzce<Boolean> a(com.google.android.gms.internal.zzafg.zza zza2, Set<String> set, zzcp zzcp) {
        zzce a2 = a(this.e, zza2, set, zzcp);
        Boolean zzk = zzdm.zzk((com.google.android.gms.internal.zzai.zza) a2.a());
        zzcp.a(zzdm.zzat(zzk));
        return new zzce<>(zzk, a2.b());
    }

    /* access modifiers changed from: 0000 */
    public zzce<Boolean> a(zze zze, Set<String> set, zzcs zzcs) {
        boolean z;
        Iterator it = zze.zzcjv().iterator();
        while (true) {
            boolean z2 = true;
            while (true) {
                if (it.hasNext()) {
                    zzce a2 = a((com.google.android.gms.internal.zzafg.zza) it.next(), set, zzcs.a());
                    if (((Boolean) a2.a()).booleanValue()) {
                        zzcs.a(zzdm.zzat(Boolean.valueOf(false)));
                        return new zzce<>(Boolean.valueOf(false), a2.b());
                    } else if (!z || !a2.b()) {
                        z2 = false;
                    }
                } else {
                    for (com.google.android.gms.internal.zzafg.zza a3 : zze.zzcju()) {
                        zzce a4 = a(a3, set, zzcs.b());
                        if (!((Boolean) a4.a()).booleanValue()) {
                            zzcs.a(zzdm.zzat(Boolean.valueOf(false)));
                            return new zzce<>(Boolean.valueOf(false), a4.b());
                        }
                        z = z && a4.b();
                    }
                    zzcs.a(zzdm.zzat(Boolean.valueOf(true)));
                    return new zzce<>(Boolean.valueOf(true), z);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public zzce<Set<com.google.android.gms.internal.zzafg.zza>> a(String str, Set<zze> set, Map<zze, List<com.google.android.gms.internal.zzafg.zza>> map, Map<zze, List<String>> map2, Map<zze, List<com.google.android.gms.internal.zzafg.zza>> map3, Map<zze, List<String>> map4, Set<String> set2, zzcw zzcw) {
        final Map<zze, List<com.google.android.gms.internal.zzafg.zza>> map5 = map;
        final Map<zze, List<String>> map6 = map2;
        final Map<zze, List<com.google.android.gms.internal.zzafg.zza>> map7 = map3;
        final Map<zze, List<String>> map8 = map4;
        AnonymousClass3 r0 = new zza() {
            public void a(zze zze, Set<com.google.android.gms.internal.zzafg.zza> set, Set<com.google.android.gms.internal.zzafg.zza> set2, zzcs zzcs) {
                List list = (List) map5.get(zze);
                List list2 = (List) map6.get(zze);
                if (list != null) {
                    set.addAll(list);
                    zzcs.c().zzc(list, list2);
                }
                List list3 = (List) map7.get(zze);
                List list4 = (List) map8.get(zze);
                if (list3 != null) {
                    set2.addAll(list3);
                    zzcs.d().zzc(list3, list4);
                }
            }
        };
        return a(set, set2, (zza) r0, zzcw);
    }

    /* access modifiers changed from: 0000 */
    public zzce<Set<com.google.android.gms.internal.zzafg.zza>> a(Set<zze> set, zzcw zzcw) {
        return a(set, (Set<String>) new HashSet<String>(), (zza) new zza() {
            public void a(zze zze, Set<com.google.android.gms.internal.zzafg.zza> set, Set<com.google.android.gms.internal.zzafg.zza> set2, zzcs zzcs) {
                set.addAll(zze.zzcjw());
                set2.addAll(zze.zzcjx());
                zzcs.e().zzc(zze.zzcjw(), zze.zzclc());
                zzcs.f().zzc(zze.zzcjx(), zze.zzcld());
            }
        }, zzcw);
    }

    /* access modifiers changed from: 0000 */
    public synchronized String a() {
        return this.l;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzam zzam) {
        a(this.f, zzam);
    }

    public synchronized void a(String str) {
        c(str);
        zzai b2 = this.c.b(str);
        zzv b3 = b2.b();
        for (com.google.android.gms.internal.zzafg.zza a2 : (Set) a(this.i, b3.b()).a()) {
            a(this.d, a2, (Set<String>) new HashSet<String>(), b3.a());
        }
        b2.c();
        c((String) null);
    }

    public synchronized void a(List<zzi> list) {
        for (zzi zzi : list) {
            if (zzi.name != null) {
                if (zzi.name.startsWith("gaExperiment:")) {
                    zzal.a(this.j, zzi);
                }
            }
            String valueOf = String.valueOf(zzi);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
            sb.append("Ignored supplemental: ");
            sb.append(valueOf);
            zzbo.v(sb.toString());
        }
    }

    public zzce<com.google.android.gms.internal.zzai.zza> b(String str) {
        this.m = 0;
        zzai a2 = this.c.a(str);
        zzce<com.google.android.gms.internal.zzai.zza> a3 = a(str, (Set<String>) new HashSet<String>(), a2.a());
        a2.c();
        return a3;
    }

    /* access modifiers changed from: 0000 */
    public void b(zzam zzam) {
        a(this.d, zzam);
    }

    /* access modifiers changed from: 0000 */
    public void c(zzam zzam) {
        a(this.e, zzam);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void c(String str) {
        this.l = str;
    }
}
