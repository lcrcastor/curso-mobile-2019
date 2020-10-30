package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private final ValueLoader<String> a = new ValueLoader<String>() {
        /* renamed from: a */
        public String load(Context context) {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? "" : installerPackageName;
        }
    };
    private final MemoryValueCache<String> b = new MemoryValueCache<>();

    public String getInstallerPackageName(Context context) {
        try {
            String str = (String) this.b.get(context, this.a);
            if ("".equals(str)) {
                str = null;
            }
            return str;
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed to determine installer package name", e);
            return null;
        }
    }
}
