package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    static final String[] a = "gtm.lifetime".toString().split("\\.");
    private static final Pattern b = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> c;
    private final Map<String, Object> d;
    private final ReentrantLock e;
    private final LinkedList<Map<String, Object>> f;
    private final zzc g;
    /* access modifiers changed from: private */
    public final CountDownLatch h;

    static final class zza {
        public final String a;
        public final Object b;

        zza(String str, Object obj) {
            this.a = str;
            this.b = obj;
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (this.a.equals(zza.a) && this.b.equals(zza.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.a.hashCode()), Integer.valueOf(this.b.hashCode())});
        }

        public String toString() {
            String str = this.a;
            String valueOf = String.valueOf(this.b.toString());
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(valueOf).length());
            sb.append("Key: ");
            sb.append(str);
            sb.append(" value: ");
            sb.append(valueOf);
            return sb.toString();
        }
    }

    interface zzb {
        void a(Map<String, Object> map);
    }

    interface zzc {

        public interface zza {
            void zzai(List<zza> list);
        }

        void a(zza zza2);

        void a(String str);

        void a(List<zza> list, long j);
    }

    DataLayer() {
        this(new zzc() {
            public void a(zza zza) {
                zza.zzai(new ArrayList());
            }

            public void a(String str) {
            }

            public void a(List<zza> list, long j) {
            }
        });
    }

    DataLayer(zzc zzc2) {
        this.g = zzc2;
        this.c = new ConcurrentHashMap<>();
        this.d = new HashMap();
        this.e = new ReentrantLock();
        this.f = new LinkedList<>();
        this.h = new CountDownLatch(1);
        a();
    }

    private void a() {
        this.g.a((zza) new zza() {
            public void zzai(List<zza> list) {
                for (zza zza : list) {
                    DataLayer.this.a(DataLayer.this.a(zza.a, zza.b));
                }
                DataLayer.this.h.countDown();
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Map<String, Object> map) {
        this.e.lock();
        try {
            this.f.offer(map);
            if (this.e.getHoldCount() == 1) {
                b();
            }
            b(map);
        } finally {
            this.e.unlock();
        }
    }

    private void a(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : ".";
            String str3 = (String) entry.getKey();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 0 + String.valueOf(str2).length() + String.valueOf(str3).length());
            sb.append(str);
            sb.append(str2);
            sb.append(str3);
            String sb2 = sb.toString();
            if (entry.getValue() instanceof Map) {
                a((Map) entry.getValue(), sb2, collection);
            } else if (!sb2.equals("gtm.lifetime")) {
                collection.add(new zza(sb2, entry.getValue()));
            }
        }
    }

    static Long b(String str) {
        long j;
        long j2;
        long j3;
        Matcher matcher = b.matcher(str);
        if (!matcher.matches()) {
            String str2 = "unknown _lifetime: ";
            String valueOf = String.valueOf(str);
            zzbo.zzde(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return null;
        }
        try {
            j = Long.parseLong(matcher.group(1));
        } catch (NumberFormatException unused) {
            String str3 = "illegal number in _lifetime value: ";
            String valueOf2 = String.valueOf(str);
            zzbo.zzdf(valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
            j = 0;
        }
        if (j <= 0) {
            String str4 = "non-positive _lifetime: ";
            String valueOf3 = String.valueOf(str);
            zzbo.zzde(valueOf3.length() != 0 ? str4.concat(valueOf3) : new String(str4));
            return null;
        }
        String group = matcher.group(2);
        if (group.length() == 0) {
            return Long.valueOf(j);
        }
        char charAt = group.charAt(0);
        if (charAt != 'd') {
            if (charAt == 'h') {
                j3 = j * 1000 * 60;
            } else if (charAt == 'm') {
                j3 = j * 1000;
            } else if (charAt != 's') {
                String str5 = "unknown units in _lifetime: ";
                String valueOf4 = String.valueOf(str);
                zzbo.zzdf(valueOf4.length() != 0 ? str5.concat(valueOf4) : new String(str5));
                return null;
            } else {
                j2 = j * 1000;
            }
            j2 = j3 * 60;
        } else {
            j2 = j * 1000 * 60 * 60 * 24;
        }
        return Long.valueOf(j2);
    }

    private void b() {
        int i = 0;
        do {
            Map map = (Map) this.f.poll();
            if (map != null) {
                f(map);
                i++;
            } else {
                return;
            }
        } while (i <= 500);
        this.f.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    private void b(Map<String, Object> map) {
        Long c2 = c(map);
        if (c2 != null) {
            List e2 = e(map);
            e2.remove("gtm.lifetime");
            this.g.a(e2, c2.longValue());
        }
    }

    private Long c(Map<String, Object> map) {
        Object d2 = d(map);
        if (d2 == null) {
            return null;
        }
        return b(d2.toString());
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Map<java.lang.String, java.lang.Object>, code=java.lang.Object, for r6v0, types: [java.util.Map<java.lang.String, java.lang.Object>, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object d(java.lang.Object r6) {
        /*
            r5 = this;
            java.lang.String[] r0 = a
            int r1 = r0.length
            r2 = 0
        L_0x0004:
            if (r2 >= r1) goto L_0x0017
            r3 = r0[r2]
            boolean r4 = r6 instanceof java.util.Map
            if (r4 != 0) goto L_0x000e
            r6 = 0
            return r6
        L_0x000e:
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r6 = r6.get(r3)
            int r2 = r2 + 1
            goto L_0x0004
        L_0x0017:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.DataLayer.d(java.util.Map):java.lang.Object");
    }

    private List<zza> e(Map<String, Object> map) {
        ArrayList arrayList = new ArrayList();
        a(map, "", arrayList);
        return arrayList;
    }

    private void f(Map<String, Object> map) {
        synchronized (this.d) {
            for (String str : map.keySet()) {
                a(a(str, map.get(str)), this.d);
            }
        }
        g(map);
    }

    private void g(Map<String, Object> map) {
        for (zzb a2 : this.c.keySet()) {
            a2.a(map);
        }
    }

    public static List<Object> listOf(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object add : objArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap hashMap = new HashMap();
        for (int i = 0; i < objArr.length; i += 2) {
            if (!(objArr[i] instanceof String)) {
                String valueOf = String.valueOf(objArr[i]);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
                sb.append("key is not a string: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
            hashMap.put(objArr[i], objArr[i + 1]);
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public Map<String, Object> a(String str, Object obj) {
        HashMap hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        HashMap hashMap2 = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap3 = new HashMap();
            hashMap2.put(split[i], hashMap3);
            i++;
            hashMap2 = hashMap3;
        }
        hashMap2.put(split[split.length - 1], obj);
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzb zzb2) {
        this.c.put(zzb2, Integer.valueOf(0));
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        push(str, null);
        this.g.a(str);
    }

    /* access modifiers changed from: 0000 */
    public void a(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                a((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                a((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                a((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                a((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    public Object get(String str) {
        String[] split;
        synchronized (this.d) {
            Object obj = this.d;
            for (String str2 : str.split("\\.")) {
                if (!(obj instanceof Map)) {
                    return null;
                }
                obj = ((Map) obj).get(str2);
                if (obj == null) {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String str, Object obj) {
        push(a(str, obj));
    }

    public void push(Map<String, Object> map) {
        try {
            this.h.await();
        } catch (InterruptedException unused) {
            zzbo.zzdf("DataLayer.push: unexpected InterruptedException");
        }
        a(map);
    }

    public void pushEvent(String str, Map<String, Object> map) {
        HashMap hashMap = new HashMap(map);
        hashMap.put("event", str);
        push(hashMap);
    }

    public String toString() {
        String sb;
        synchronized (this.d) {
            StringBuilder sb2 = new StringBuilder();
            for (Entry entry : this.d.entrySet()) {
                sb2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{entry.getKey(), entry.getValue()}));
            }
            sb = sb2.toString();
        }
        return sb;
    }
}
