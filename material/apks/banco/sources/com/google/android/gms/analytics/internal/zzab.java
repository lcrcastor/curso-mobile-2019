package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzab {
    private final Map<String, String> a;
    private final List<Command> b;
    private final long c;
    private final long d;
    private final int e;
    private final boolean f;
    private final String g;

    public zzab(zzc zzc, Map<String, String> map, long j, boolean z) {
        this(zzc, map, j, z, 0, 0, null);
    }

    public zzab(zzc zzc, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzc, map, j, z, j2, i, null);
    }

    public zzab(zzc zzc, Map<String, String> map, long j, boolean z, long j2, int i, List<Command> list) {
        zzac.zzy(zzc);
        zzac.zzy(map);
        this.d = j;
        this.f = z;
        this.c = j2;
        this.e = i;
        this.b = list != null ? list : Collections.emptyList();
        this.g = a(list);
        HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            if (a(entry.getKey())) {
                String a2 = a(zzc, entry.getKey());
                if (a2 != null) {
                    hashMap.put(a2, b(zzc, entry.getValue()));
                }
            }
        }
        for (Entry entry2 : map.entrySet()) {
            if (!a(entry2.getKey())) {
                String a3 = a(zzc, entry2.getKey());
                if (a3 != null) {
                    hashMap.put(a3, b(zzc, entry2.getValue()));
                }
            }
        }
        if (!TextUtils.isEmpty(this.g)) {
            zzao.zzc(hashMap, "_v", this.g);
            if (this.g.equals("ma4.0.0") || this.g.equals("ma4.0.1")) {
                hashMap.remove("adid");
            }
        }
        this.a = Collections.unmodifiableMap(hashMap);
    }

    private static String a(zzc zzc, Object obj) {
        if (obj == null) {
            return null;
        }
        String obj2 = obj.toString();
        if (obj2.startsWith("&")) {
            obj2 = obj2.substring(1);
        }
        int length = obj2.length();
        if (length > 256) {
            obj2 = obj2.substring(0, 256);
            zzc.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), obj2);
        }
        if (TextUtils.isEmpty(obj2)) {
            return null;
        }
        return obj2;
    }

    private String a(String str, String str2) {
        zzac.zzhz(str);
        zzac.zzb(!str.startsWith("&"), (Object) "Short param name required");
        String str3 = (String) this.a.get(str);
        return str3 != null ? str3 : str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002c A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.util.List<com.google.android.gms.analytics.internal.Command> r4) {
        /*
            r0 = 0
            if (r4 == 0) goto L_0x0024
            java.util.Iterator r4 = r4.iterator()
        L_0x0007:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x0024
            java.lang.Object r1 = r4.next()
            com.google.android.gms.analytics.internal.Command r1 = (com.google.android.gms.analytics.internal.Command) r1
            java.lang.String r2 = "appendVersion"
            java.lang.String r3 = r1.getId()
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0007
            java.lang.String r4 = r1.getValue()
            goto L_0x0025
        L_0x0024:
            r4 = r0
        L_0x0025:
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x002c
            return r0
        L_0x002c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzab.a(java.util.List):java.lang.String");
    }

    private static boolean a(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith("&");
    }

    private static String b(zzc zzc, Object obj) {
        String obj2 = obj == null ? "" : obj.toString();
        int length = obj2.length();
        if (length <= 8192) {
            return obj2;
        }
        String substring = obj2.substring(0, 8192);
        zzc.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), substring);
        return substring;
    }

    public static zzab zza(zzc zzc, zzab zzab, Map<String, String> map) {
        zzab zzab2 = new zzab(zzc, map, zzab.zzaeq(), zzab.zzaes(), zzab.zzaep(), zzab.zzaeo(), zzab.zzaer());
        return zzab2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=");
        stringBuffer.append(this.d);
        if (this.c != 0) {
            stringBuffer.append(", dbId=");
            stringBuffer.append(this.c);
        }
        if (this.e != 0) {
            stringBuffer.append(", appUID=");
            stringBuffer.append(this.e);
        }
        ArrayList<String> arrayList = new ArrayList<>(this.a.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append((String) this.a.get(str));
        }
        return stringBuffer.toString();
    }

    public int zzaeo() {
        return this.e;
    }

    public long zzaep() {
        return this.c;
    }

    public long zzaeq() {
        return this.d;
    }

    public List<Command> zzaer() {
        return this.b;
    }

    public boolean zzaes() {
        return this.f;
    }

    public long zzaet() {
        return zzao.zzfg(a("_s", "0"));
    }

    public String zzaeu() {
        return a("_m", "");
    }

    public Map<String, String> zzm() {
        return this.a;
    }
}
