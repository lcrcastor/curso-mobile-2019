package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder implements Callable<Map<String, KitInfo>> {
    final String a;

    FabricKitsFinder(String str) {
        this.a = str;
    }

    /* renamed from: a */
    public Map<String, KitInfo> call() {
        HashMap hashMap = new HashMap();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ZipFile b = b();
        Enumeration entries = b.entries();
        int i = 0;
        while (entries.hasMoreElements()) {
            i++;
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (zipEntry.getName().startsWith("fabric/") && zipEntry.getName().length() > "fabric/".length()) {
                KitInfo a2 = a(zipEntry, b);
                if (a2 != null) {
                    hashMap.put(a2.getIdentifier(), a2);
                    Fabric.getLogger().v(Fabric.TAG, String.format("Found kit:[%s] version:[%s]", new Object[]{a2.getIdentifier(), a2.getVersion()}));
                }
            }
        }
        if (b != null) {
            try {
                b.close();
            } catch (IOException unused) {
            }
        }
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("finish scanning in ");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb.append(" reading:");
        sb.append(i);
        logger.v(str, sb.toString());
        return hashMap;
    }

    private KitInfo a(ZipEntry zipEntry, ZipFile zipFile) {
        InputStream inputStream;
        try {
            inputStream = zipFile.getInputStream(zipEntry);
            try {
                Properties properties = new Properties();
                properties.load(inputStream);
                String property = properties.getProperty("fabric-identifier");
                String property2 = properties.getProperty("fabric-version");
                String property3 = properties.getProperty("fabric-build-type");
                if (!TextUtils.isEmpty(property)) {
                    if (!TextUtils.isEmpty(property2)) {
                        KitInfo kitInfo = new KitInfo(property, property2, property3);
                        CommonUtils.closeQuietly(inputStream);
                        return kitInfo;
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid format of fabric file,");
                sb.append(zipEntry.getName());
                throw new IllegalStateException(sb.toString());
            } catch (IOException e) {
                e = e;
                try {
                    Logger logger = Fabric.getLogger();
                    String str = Fabric.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Error when parsing fabric properties ");
                    sb2.append(zipEntry.getName());
                    logger.e(str, sb2.toString(), e);
                    CommonUtils.closeQuietly(inputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    CommonUtils.closeQuietly(inputStream);
                    throw th;
                }
            }
        } catch (IOException e2) {
            e = e2;
            inputStream = null;
            Logger logger2 = Fabric.getLogger();
            String str2 = Fabric.TAG;
            StringBuilder sb22 = new StringBuilder();
            sb22.append("Error when parsing fabric properties ");
            sb22.append(zipEntry.getName());
            logger2.e(str2, sb22.toString(), e);
            CommonUtils.closeQuietly(inputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
            CommonUtils.closeQuietly(inputStream);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public ZipFile b() {
        return new ZipFile(this.a);
    }
}
