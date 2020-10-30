package com.appsflyer.cache;

import android.content.Context;
import android.util.Log;
import com.appsflyer.AppsFlyerLib;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    public static final String AF_CACHE_DIR = "AFRequestCache";
    public static final int CACHE_MAX_SIZE = 40;
    private static CacheManager a = new CacheManager();

    public static CacheManager getInstance() {
        return a;
    }

    private CacheManager() {
    }

    public List<RequestCacheData> getCachedRequests(Context context) {
        File[] listFiles;
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File(context.getFilesDir(), AF_CACHE_DIR);
            if (!file.exists()) {
                file.mkdir();
            } else {
                for (File file2 : file.listFiles()) {
                    String str = AppsFlyerLib.LOG_TAG;
                    StringBuilder sb = new StringBuilder("Found cached request");
                    sb.append(file2.getName());
                    Log.i(str, sb.toString());
                    arrayList.add(a(file2));
                }
            }
        } catch (Exception unused) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not cache request");
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0026 A[SYNTHETIC, Splitter:B:13:0x0026] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002d A[SYNTHETIC, Splitter:B:21:0x002d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.appsflyer.cache.RequestCacheData a(java.io.File r4) {
        /*
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Exception -> 0x002a, all -> 0x0022 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x002a, all -> 0x0022 }
            long r2 = r4.length()     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            int r2 = (int) r2     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            char[] r2 = new char[r2]     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r1.read(r2)     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            com.appsflyer.cache.RequestCacheData r3 = new com.appsflyer.cache.RequestCacheData     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            java.lang.String r4 = r4.getName()     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r3.setCacheKey(r4)     // Catch:{ Exception -> 0x002b, all -> 0x0020 }
            r1.close()     // Catch:{ IOException -> 0x001f }
        L_0x001f:
            return r3
        L_0x0020:
            r4 = move-exception
            goto L_0x0024
        L_0x0022:
            r4 = move-exception
            r1 = r0
        L_0x0024:
            if (r1 == 0) goto L_0x0029
            r1.close()     // Catch:{ IOException -> 0x0029 }
        L_0x0029:
            throw r4
        L_0x002a:
            r1 = r0
        L_0x002b:
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ IOException -> 0x0030 }
        L_0x0030:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.cache.CacheManager.a(java.io.File):com.appsflyer.cache.RequestCacheData");
    }

    public void init(Context context) {
        try {
            if (!new File(context.getFilesDir(), AF_CACHE_DIR).exists()) {
                new File(context.getFilesDir(), AF_CACHE_DIR).mkdir();
            }
        } catch (Exception unused) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not create cache directory");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a1 A[SYNTHETIC, Splitter:B:31:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a9 A[SYNTHETIC, Splitter:B:37:0x00a9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cacheRequest(com.appsflyer.cache.RequestCacheData r6, android.content.Context r7) {
        /*
            r5 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0098 }
            java.io.File r2 = r7.getFilesDir()     // Catch:{ Exception -> 0x0098 }
            java.lang.String r3 = "AFRequestCache"
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0098 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0098 }
            if (r2 != 0) goto L_0x0016
            r1.mkdir()     // Catch:{ Exception -> 0x0098 }
            return
        L_0x0016:
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Exception -> 0x0098 }
            if (r1 == 0) goto L_0x0029
            int r1 = r1.length     // Catch:{ Exception -> 0x0098 }
            r2 = 40
            if (r1 <= r2) goto L_0x0029
            java.lang.String r6 = "AppsFlyer_4.8.11"
            java.lang.String r7 = "reached cache limit, not caching request"
            android.util.Log.i(r6, r7)     // Catch:{ Exception -> 0x0098 }
            return
        L_0x0029:
            java.lang.String r1 = "AppsFlyer_4.8.11"
            java.lang.String r2 = "caching request..."
            android.util.Log.i(r1, r2)     // Catch:{ Exception -> 0x0098 }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0098 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0098 }
            java.io.File r7 = r7.getFilesDir()     // Catch:{ Exception -> 0x0098 }
            java.lang.String r3 = "AFRequestCache"
            r2.<init>(r7, r3)     // Catch:{ Exception -> 0x0098 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0098 }
            java.lang.String r7 = java.lang.Long.toString(r3)     // Catch:{ Exception -> 0x0098 }
            r1.<init>(r2, r7)     // Catch:{ Exception -> 0x0098 }
            r1.createNewFile()     // Catch:{ Exception -> 0x0098 }
            java.io.OutputStreamWriter r7 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x0098 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0098 }
            java.lang.String r1 = r1.getPath()     // Catch:{ Exception -> 0x0098 }
            r3 = 1
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0098 }
            r7.<init>(r2)     // Catch:{ Exception -> 0x0098 }
            java.lang.String r0 = "version="
            r7.write(r0)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            java.lang.String r0 = r6.getVersion()     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.write(r0)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r0 = 10
            r7.write(r0)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            java.lang.String r1 = "url="
            r7.write(r1)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            java.lang.String r1 = r6.getRequestURL()     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.write(r1)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.write(r0)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            java.lang.String r1 = "data="
            r7.write(r1)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            java.lang.String r6 = r6.getPostData()     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.write(r6)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.write(r0)     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.flush()     // Catch:{ Exception -> 0x0094, all -> 0x0091 }
            r7.close()     // Catch:{ IOException -> 0x0090 }
            return
        L_0x0090:
            return
        L_0x0091:
            r6 = move-exception
            r0 = r7
            goto L_0x00a7
        L_0x0094:
            r0 = r7
            goto L_0x0098
        L_0x0096:
            r6 = move-exception
            goto L_0x00a7
        L_0x0098:
            java.lang.String r6 = "AppsFlyer_4.8.11"
            java.lang.String r7 = "Could not cache request"
            android.util.Log.i(r6, r7)     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x00a6
            r0.close()     // Catch:{ IOException -> 0x00a5 }
            goto L_0x00a6
        L_0x00a5:
            return
        L_0x00a6:
            return
        L_0x00a7:
            if (r0 == 0) goto L_0x00ac
            r0.close()     // Catch:{ IOException -> 0x00ac }
        L_0x00ac:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.cache.CacheManager.cacheRequest(com.appsflyer.cache.RequestCacheData, android.content.Context):void");
    }

    public void deleteRequest(String str, Context context) {
        File file = new File(new File(context.getFilesDir(), AF_CACHE_DIR), str);
        String str2 = AppsFlyerLib.LOG_TAG;
        StringBuilder sb = new StringBuilder("Deleting ");
        sb.append(str);
        sb.append(" from cache");
        Log.i(str2, sb.toString());
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                String str3 = AppsFlyerLib.LOG_TAG;
                StringBuilder sb2 = new StringBuilder("Could not delete ");
                sb2.append(str);
                sb2.append(" from cache");
                Log.i(str3, sb2.toString(), e);
            }
        }
    }

    public void clearCache(Context context) {
        File[] listFiles;
        try {
            File file = new File(context.getFilesDir(), AF_CACHE_DIR);
            if (!file.exists()) {
                file.mkdir();
                return;
            }
            for (File file2 : file.listFiles()) {
                String str = AppsFlyerLib.LOG_TAG;
                StringBuilder sb = new StringBuilder("Found cached request");
                sb.append(file2.getName());
                Log.i(str, sb.toString());
                deleteRequest(a(file2).getCacheKey(), context);
            }
        } catch (Exception unused) {
            Log.i(AppsFlyerLib.LOG_TAG, "Could not cache request");
        }
    }
}
