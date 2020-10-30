package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class zzafz {
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    static HashMap<String, String> a;
    public static final Uri aRT = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern aRU = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern aRV = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    static HashSet<String> b = new HashSet<>();
    /* access modifiers changed from: private */
    public static Object c;

    private static void a(final ContentResolver contentResolver) {
        if (a == null) {
            a = new HashMap<>();
            c = new Object();
            new Thread("Gservices") {
                public void run() {
                    Looper.prepare();
                    contentResolver.registerContentObserver(zzafz.CONTENT_URI, true, new ContentObserver(new Handler(Looper.myLooper())) {
                        public void onChange(boolean z) {
                            synchronized (zzafz.class) {
                                zzafz.a.clear();
                                zzafz.c = new Object();
                                if (!zzafz.b.isEmpty()) {
                                    zzafz.zzb(contentResolver, (String[]) zzafz.b.toArray(new String[zzafz.b.size()]));
                                }
                            }
                        }
                    });
                    Looper.loop();
                }
            }.start();
        }
    }

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        String string = getString(contentResolver, str);
        if (string == null) {
            return j;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static String getString(ContentResolver contentResolver, String str) {
        return zza(contentResolver, str, null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r0 = b.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        if (r0.hasNext() == false) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        if (r10.startsWith((java.lang.String) r0.next()) == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        r9 = r9.query(CONTENT_URI, null, null, new java.lang.String[]{r10}, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        if (r9 == null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004e, code lost:
        if (r9.moveToFirst() != false) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0051, code lost:
        r0 = r9.getString(1);
        r2 = com.google.android.gms.internal.zzafz.class;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
        if (r1 != c) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005c, code lost:
        a.put(r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0062, code lost:
        if (r0 == null) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0064, code lost:
        r11 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0065, code lost:
        if (r9 == null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0067, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006a, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006e, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0070, code lost:
        a.put(r10, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0076, code lost:
        if (r9 == null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0078, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x007b, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x007c, code lost:
        if (r9 != null) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x007e, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0081, code lost:
        throw r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.Class<com.google.android.gms.internal.zzafz> r0 = com.google.android.gms.internal.zzafz.class
            monitor-enter(r0)
            a(r9)     // Catch:{ all -> 0x0082 }
            java.lang.Object r1 = c     // Catch:{ all -> 0x0082 }
            java.util.HashMap<java.lang.String, java.lang.String> r2 = a     // Catch:{ all -> 0x0082 }
            boolean r2 = r2.containsKey(r10)     // Catch:{ all -> 0x0082 }
            if (r2 == 0) goto L_0x001e
            java.util.HashMap<java.lang.String, java.lang.String> r9 = a     // Catch:{ all -> 0x0082 }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ all -> 0x0082 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x0082 }
            if (r9 == 0) goto L_0x001b
            goto L_0x001c
        L_0x001b:
            r9 = r11
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            return r9
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            java.util.HashSet<java.lang.String> r0 = b
            java.util.Iterator r0 = r0.iterator()
        L_0x0025:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0038
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            boolean r2 = r10.startsWith(r2)
            if (r2 == 0) goto L_0x0025
            return r11
        L_0x0038:
            android.net.Uri r4 = CONTENT_URI
            r5 = 0
            r6 = 0
            r0 = 1
            java.lang.String[] r7 = new java.lang.String[r0]
            r2 = 0
            r7[r2] = r10
            r8 = 0
            r3 = r9
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)
            if (r9 == 0) goto L_0x0070
            boolean r2 = r9.moveToFirst()     // Catch:{ all -> 0x006e }
            if (r2 != 0) goto L_0x0051
            goto L_0x0070
        L_0x0051:
            java.lang.String r0 = r9.getString(r0)     // Catch:{ all -> 0x006e }
            java.lang.Class<com.google.android.gms.internal.zzafz> r2 = com.google.android.gms.internal.zzafz.class
            monitor-enter(r2)     // Catch:{ all -> 0x006e }
            java.lang.Object r3 = c     // Catch:{ all -> 0x006b }
            if (r1 != r3) goto L_0x0061
            java.util.HashMap<java.lang.String, java.lang.String> r1 = a     // Catch:{ all -> 0x006b }
            r1.put(r10, r0)     // Catch:{ all -> 0x006b }
        L_0x0061:
            monitor-exit(r2)     // Catch:{ all -> 0x006b }
            if (r0 == 0) goto L_0x0065
            r11 = r0
        L_0x0065:
            if (r9 == 0) goto L_0x006a
            r9.close()
        L_0x006a:
            return r11
        L_0x006b:
            r10 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x006b }
            throw r10     // Catch:{ all -> 0x006e }
        L_0x006e:
            r10 = move-exception
            goto L_0x007c
        L_0x0070:
            java.util.HashMap<java.lang.String, java.lang.String> r0 = a     // Catch:{ all -> 0x006e }
            r1 = 0
            r0.put(r10, r1)     // Catch:{ all -> 0x006e }
            if (r9 == 0) goto L_0x007b
            r9.close()
        L_0x007b:
            return r11
        L_0x007c:
            if (r9 == 0) goto L_0x0081
            r9.close()
        L_0x0081:
            throw r10
        L_0x0082:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzafz.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    public static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(aRT, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (query == null) {
            return treeMap;
        }
        while (query.moveToNext()) {
            try {
                treeMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return treeMap;
    }

    public static void zzb(ContentResolver contentResolver, String... strArr) {
        Map zza = zza(contentResolver, strArr);
        synchronized (zzafz.class) {
            a(contentResolver);
            b.addAll(Arrays.asList(strArr));
            for (Entry entry : zza.entrySet()) {
                a.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }
}
