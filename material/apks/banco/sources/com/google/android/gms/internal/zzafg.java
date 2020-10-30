package com.google.android.gms.internal;

import com.google.android.gms.internal.zzah.zzh;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzafg {

    public static class zza {
        private final Map<String, com.google.android.gms.internal.zzai.zza> a;
        private final com.google.android.gms.internal.zzai.zza b;

        private zza(Map<String, com.google.android.gms.internal.zzai.zza> map, com.google.android.gms.internal.zzai.zza zza) {
            this.a = map;
            this.b = zza;
        }

        public static zzb zzckt() {
            return new zzb();
        }

        public String toString() {
            String valueOf = String.valueOf(zzcjs());
            String valueOf2 = String.valueOf(this.b);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32 + String.valueOf(valueOf2).length());
            sb.append("Properties: ");
            sb.append(valueOf);
            sb.append(" pushAfterEvaluate: ");
            sb.append(valueOf2);
            return sb.toString();
        }

        public void zza(String str, com.google.android.gms.internal.zzai.zza zza) {
            this.a.put(str, zza);
        }

        public com.google.android.gms.internal.zzai.zza zzcgl() {
            return this.b;
        }

        public Map<String, com.google.android.gms.internal.zzai.zza> zzcjs() {
            return Collections.unmodifiableMap(this.a);
        }
    }

    public static class zzb {
        private final Map<String, com.google.android.gms.internal.zzai.zza> a;
        private com.google.android.gms.internal.zzai.zza b;

        private zzb() {
            this.a = new HashMap();
        }

        public zzb zzb(String str, com.google.android.gms.internal.zzai.zza zza) {
            this.a.put(str, zza);
            return this;
        }

        public zza zzcku() {
            return new zza(this.a, this.b);
        }

        public zzb zzq(com.google.android.gms.internal.zzai.zza zza) {
            this.b = zza;
            return this;
        }
    }

    public static class zzc {
        private final List<zze> a;
        private final Map<String, List<zza>> b;
        private final String c;
        private final int d;

        private zzc(List<zze> list, Map<String, List<zza>> map, String str, int i) {
            this.a = Collections.unmodifiableList(list);
            this.b = Collections.unmodifiableMap(map);
            this.c = str;
            this.d = i;
        }

        public static zzd zzckv() {
            return new zzd();
        }

        public String getVersion() {
            return this.c;
        }

        public String toString() {
            String valueOf = String.valueOf(zzcjq());
            String valueOf2 = String.valueOf(this.b);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 17 + String.valueOf(valueOf2).length());
            sb.append("Rules: ");
            sb.append(valueOf);
            sb.append("  Macros: ");
            sb.append(valueOf2);
            return sb.toString();
        }

        public List<zze> zzcjq() {
            return this.a;
        }

        public Map<String, List<zza>> zzckw() {
            return this.b;
        }
    }

    public static class zzd {
        private final List<zze> a;
        private final Map<String, List<zza>> b;
        private String c;
        private int d;

        private zzd() {
            this.a = new ArrayList();
            this.b = new HashMap();
            this.c = "";
            this.d = 0;
        }

        public zzd zzaay(int i) {
            this.d = i;
            return this;
        }

        public zzd zzb(zze zze) {
            this.a.add(zze);
            return this;
        }

        public zzd zzc(zza zza) {
            String zzg = zzdm.zzg((com.google.android.gms.internal.zzai.zza) zza.zzcjs().get(zzag.INSTANCE_NAME.toString()));
            List list = (List) this.b.get(zzg);
            if (list == null) {
                list = new ArrayList();
                this.b.put(zzg, list);
            }
            list.add(zza);
            return this;
        }

        public zzc zzckx() {
            zzc zzc = new zzc(this.a, this.b, this.c, this.d);
            return zzc;
        }

        public zzd zzrj(String str) {
            this.c = str;
            return this;
        }
    }

    public static class zze {
        private final List<zza> a;
        private final List<zza> b;
        private final List<zza> c;
        private final List<zza> d;
        private final List<zza> e;
        private final List<zza> f;
        private final List<String> g;
        private final List<String> h;
        private final List<String> i;
        private final List<String> j;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.a = Collections.unmodifiableList(list);
            this.b = Collections.unmodifiableList(list2);
            this.c = Collections.unmodifiableList(list3);
            this.d = Collections.unmodifiableList(list4);
            this.e = Collections.unmodifiableList(list5);
            this.f = Collections.unmodifiableList(list6);
            this.g = Collections.unmodifiableList(list7);
            this.h = Collections.unmodifiableList(list8);
            this.i = Collections.unmodifiableList(list9);
            this.j = Collections.unmodifiableList(list10);
        }

        public static zzf zzcky() {
            return new zzf();
        }

        public String toString() {
            String valueOf = String.valueOf(zzcju());
            String valueOf2 = String.valueOf(zzcjv());
            String valueOf3 = String.valueOf(zzcjw());
            String valueOf4 = String.valueOf(zzcjx());
            String valueOf5 = String.valueOf(zzckz());
            String valueOf6 = String.valueOf(zzcle());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 102 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length());
            sb.append("Positive predicates: ");
            sb.append(valueOf);
            sb.append("  Negative predicates: ");
            sb.append(valueOf2);
            sb.append("  Add tags: ");
            sb.append(valueOf3);
            sb.append("  Remove tags: ");
            sb.append(valueOf4);
            sb.append("  Add macros: ");
            sb.append(valueOf5);
            sb.append("  Remove macros: ");
            sb.append(valueOf6);
            return sb.toString();
        }

        public List<zza> zzcju() {
            return this.a;
        }

        public List<zza> zzcjv() {
            return this.b;
        }

        public List<zza> zzcjw() {
            return this.c;
        }

        public List<zza> zzcjx() {
            return this.d;
        }

        public List<zza> zzckz() {
            return this.e;
        }

        public List<String> zzcla() {
            return this.g;
        }

        public List<String> zzclb() {
            return this.h;
        }

        public List<String> zzclc() {
            return this.i;
        }

        public List<String> zzcld() {
            return this.j;
        }

        public List<zza> zzcle() {
            return this.f;
        }
    }

    public static class zzf {
        private final List<zza> a;
        private final List<zza> b;
        private final List<zza> c;
        private final List<zza> d;
        private final List<zza> e;
        private final List<zza> f;
        private final List<String> g;
        private final List<String> h;
        private final List<String> i;
        private final List<String> j;

        private zzf() {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = new ArrayList();
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.g = new ArrayList();
            this.h = new ArrayList();
            this.i = new ArrayList();
            this.j = new ArrayList();
        }

        public zze zzclf() {
            zze zze = new zze(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
            return zze;
        }

        public zzf zzd(zza zza) {
            this.a.add(zza);
            return this;
        }

        public zzf zze(zza zza) {
            this.b.add(zza);
            return this;
        }

        public zzf zzf(zza zza) {
            this.c.add(zza);
            return this;
        }

        public zzf zzg(zza zza) {
            this.d.add(zza);
            return this;
        }

        public zzf zzh(zza zza) {
            this.e.add(zza);
            return this;
        }

        public zzf zzi(zza zza) {
            this.f.add(zza);
            return this;
        }

        public zzf zzrk(String str) {
            this.i.add(str);
            return this;
        }

        public zzf zzrl(String str) {
            this.j.add(str);
            return this;
        }

        public zzf zzrm(String str) {
            this.g.add(str);
            return this;
        }

        public zzf zzrn(String str) {
            this.h.add(str);
            return this;
        }
    }

    public static class zzg extends Exception {
        public zzg(String str) {
            super(str);
        }
    }

    private static zza a(com.google.android.gms.internal.zzah.zzb zzb2, com.google.android.gms.internal.zzah.zzf zzf2, com.google.android.gms.internal.zzai.zza[] zzaArr, int i) {
        zzb zzckt = zza.zzckt();
        for (int valueOf : zzb2.zzvq) {
            com.google.android.gms.internal.zzah.zze zze2 = (com.google.android.gms.internal.zzah.zze) a(zzf2.zzwg, Integer.valueOf(valueOf).intValue(), "properties");
            String str = (String) a(zzf2.zzwe, zze2.key, "keys");
            com.google.android.gms.internal.zzai.zza zza2 = (com.google.android.gms.internal.zzai.zza) a(zzaArr, zze2.value, "values");
            if (zzag.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzckt.zzq(zza2);
            } else {
                zzckt.zzb(str, zza2);
            }
        }
        return zzckt.zzcku();
    }

    private static zze a(com.google.android.gms.internal.zzah.zzg zzg2, List<zza> list, List<zza> list2, List<zza> list3, com.google.android.gms.internal.zzah.zzf zzf2) {
        zzf zzcky = zze.zzcky();
        for (int valueOf : zzg2.zzwu) {
            zzcky.zzd((zza) list3.get(Integer.valueOf(valueOf).intValue()));
        }
        for (int valueOf2 : zzg2.zzwv) {
            zzcky.zze((zza) list3.get(Integer.valueOf(valueOf2).intValue()));
        }
        for (int valueOf3 : zzg2.zzww) {
            zzcky.zzf((zza) list.get(Integer.valueOf(valueOf3).intValue()));
        }
        for (int valueOf4 : zzg2.zzwy) {
            zzcky.zzrk(zzf2.zzwf[Integer.valueOf(valueOf4).intValue()].string);
        }
        for (int valueOf5 : zzg2.zzwx) {
            zzcky.zzg((zza) list.get(Integer.valueOf(valueOf5).intValue()));
        }
        for (int valueOf6 : zzg2.zzwz) {
            zzcky.zzrl(zzf2.zzwf[Integer.valueOf(valueOf6).intValue()].string);
        }
        for (int valueOf7 : zzg2.zzxa) {
            zzcky.zzh((zza) list2.get(Integer.valueOf(valueOf7).intValue()));
        }
        for (int valueOf8 : zzg2.zzxc) {
            zzcky.zzrm(zzf2.zzwf[Integer.valueOf(valueOf8).intValue()].string);
        }
        for (int valueOf9 : zzg2.zzxb) {
            zzcky.zzi((zza) list2.get(Integer.valueOf(valueOf9).intValue()));
        }
        for (int valueOf10 : zzg2.zzxd) {
            zzcky.zzrn(zzf2.zzwf[Integer.valueOf(valueOf10).intValue()].string);
        }
        return zzcky.zzclf();
    }

    private static zzh a(com.google.android.gms.internal.zzai.zza zza2) {
        if (((zzh) zza2.zza(zzh.zzxe)) == null) {
            String valueOf = String.valueOf(zza2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 54);
            sb.append("Expected a ServingValue and didn't get one. Value is: ");
            sb.append(valueOf);
            a(sb.toString());
        }
        return (zzh) zza2.zza(zzh.zzxe);
    }

    private static com.google.android.gms.internal.zzai.zza a(int i, com.google.android.gms.internal.zzah.zzf zzf2, com.google.android.gms.internal.zzai.zza[] zzaArr, Set<Integer> set) {
        if (set.contains(Integer.valueOf(i))) {
            String valueOf = String.valueOf(set);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 90);
            sb.append("Value cycle detected.  Current value reference: ");
            sb.append(i);
            sb.append(".  Previous value references: ");
            sb.append(valueOf);
            sb.append(".");
            a(sb.toString());
        }
        com.google.android.gms.internal.zzai.zza zza2 = (com.google.android.gms.internal.zzai.zza) a(zzf2.zzwf, i, "values");
        if (zzaArr[i] != null) {
            return zzaArr[i];
        }
        com.google.android.gms.internal.zzai.zza zza3 = null;
        set.add(Integer.valueOf(i));
        int i2 = 0;
        switch (zza2.type) {
            case 1:
            case 5:
            case 6:
            case 8:
                zza3 = zza2;
                break;
            case 2:
                zzh a = a(zza2);
                com.google.android.gms.internal.zzai.zza zzo = zzo(zza2);
                zzo.zzxu = new com.google.android.gms.internal.zzai.zza[a.zzxg.length];
                int[] iArr = a.zzxg;
                int length = iArr.length;
                int i3 = 0;
                while (i2 < length) {
                    int i4 = i3 + 1;
                    zzo.zzxu[i3] = a(iArr[i2], zzf2, zzaArr, set);
                    i2++;
                    i3 = i4;
                }
                zza3 = zzo;
                break;
            case 3:
                zza3 = zzo(zza2);
                zzh a2 = a(zza2);
                if (a2.zzxh.length != a2.zzxi.length) {
                    int length2 = a2.zzxh.length;
                    int length3 = a2.zzxi.length;
                    StringBuilder sb2 = new StringBuilder(58);
                    sb2.append("Uneven map keys (");
                    sb2.append(length2);
                    sb2.append(") and map values (");
                    sb2.append(length3);
                    sb2.append(")");
                    a(sb2.toString());
                }
                zza3.zzxv = new com.google.android.gms.internal.zzai.zza[a2.zzxh.length];
                zza3.zzxw = new com.google.android.gms.internal.zzai.zza[a2.zzxh.length];
                int[] iArr2 = a2.zzxh;
                int length4 = iArr2.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < length4) {
                    int i7 = i6 + 1;
                    zza3.zzxv[i6] = a(iArr2[i5], zzf2, zzaArr, set);
                    i5++;
                    i6 = i7;
                }
                int[] iArr3 = a2.zzxi;
                int length5 = iArr3.length;
                int i8 = 0;
                while (i2 < length5) {
                    int i9 = i8 + 1;
                    zza3.zzxw[i8] = a(iArr3[i2], zzf2, zzaArr, set);
                    i2++;
                    i8 = i9;
                }
                break;
            case 4:
                zza3 = zzo(zza2);
                zza3.zzxx = zzdm.zzg(a(a(zza2).zzxl, zzf2, zzaArr, set));
                break;
            case 7:
                zza3 = zzo(zza2);
                zzh a3 = a(zza2);
                zza3.zzyb = new com.google.android.gms.internal.zzai.zza[a3.zzxk.length];
                int[] iArr4 = a3.zzxk;
                int length6 = iArr4.length;
                int i10 = 0;
                while (i2 < length6) {
                    int i11 = i10 + 1;
                    zza3.zzyb[i10] = a(iArr4[i2], zzf2, zzaArr, set);
                    i2++;
                    i10 = i11;
                }
                break;
        }
        if (zza3 == null) {
            String valueOf2 = String.valueOf(zza2);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 15);
            sb3.append("Invalid value: ");
            sb3.append(valueOf2);
            a(sb3.toString());
        }
        zzaArr[i] = zza3;
        set.remove(Integer.valueOf(i));
        return zza3;
    }

    private static <T> T a(T[] tArr, int i, String str) {
        if (i < 0 || i >= tArr.length) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 45);
            sb.append("Index out of bounds detected: ");
            sb.append(i);
            sb.append(" in ");
            sb.append(str);
            a(sb.toString());
        }
        return tArr[i];
    }

    private static void a(String str) {
        zzbo.e(str);
        throw new zzg(str);
    }

    public static zzc zzb(com.google.android.gms.internal.zzah.zzf zzf2) {
        com.google.android.gms.internal.zzai.zza[] zzaArr = new com.google.android.gms.internal.zzai.zza[zzf2.zzwf.length];
        for (int i = 0; i < zzf2.zzwf.length; i++) {
            a(i, zzf2, zzaArr, (Set<Integer>) new HashSet<Integer>(0));
        }
        zzd zzckv = zzc.zzckv();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzf2.zzwi.length; i2++) {
            arrayList.add(a(zzf2.zzwi[i2], zzf2, zzaArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzf2.zzwj.length; i3++) {
            arrayList2.add(a(zzf2.zzwj[i3], zzf2, zzaArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzf2.zzwh.length; i4++) {
            zza a = a(zzf2.zzwh[i4], zzf2, zzaArr, i4);
            zzckv.zzc(a);
            arrayList3.add(a);
        }
        for (com.google.android.gms.internal.zzah.zzg a2 : zzf2.zzwk) {
            zzckv.zzb(a(a2, arrayList, arrayList3, arrayList2, zzf2));
        }
        zzckv.zzrj(zzf2.version);
        zzckv.zzaay(zzf2.zzws);
        return zzckv.zzckx();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static com.google.android.gms.internal.zzai.zza zzo(com.google.android.gms.internal.zzai.zza zza2) {
        com.google.android.gms.internal.zzai.zza zza3 = new com.google.android.gms.internal.zzai.zza();
        zza3.type = zza2.type;
        zza3.zzyc = (int[]) zza2.zzyc.clone();
        if (zza2.zzyd) {
            zza3.zzyd = zza2.zzyd;
        }
        return zza3;
    }
}
