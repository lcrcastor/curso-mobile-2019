package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class IdManager {
    public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
    public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
    public static final String DEFAULT_VERSION_NAME = "0.0";
    private static final Pattern d = Pattern.compile("[^\\p{Alnum}]");
    private static final String e = Pattern.quote("/");
    AdvertisingInfoProvider a;
    AdvertisingInfo b;
    boolean c;
    private final ReentrantLock f = new ReentrantLock();
    private final InstallerPackageNameProvider g;
    private final boolean h;
    private final boolean i;
    private final Context j;
    private final String k;
    private final String l;
    private final Collection<Kit> m;

    public enum DeviceIdentifierType {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(101),
        ANDROID_SERIAL(102),
        ANDROID_ADVERTISING_ID(103);
        
        public final int protobufIndex;

        private DeviceIdentifierType(int i) {
            this.protobufIndex = i;
        }
    }

    @Deprecated
    public String createIdHeaderValue(String str, String str2) {
        return "";
    }

    @Deprecated
    public String getBluetoothMacAddress() {
        return null;
    }

    @Deprecated
    public String getSerialNumber() {
        return null;
    }

    @Deprecated
    public String getTelephonyId() {
        return null;
    }

    @Deprecated
    public String getWifiMacAddress() {
        return null;
    }

    public IdManager(Context context, String str, String str2, Collection<Kit> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        } else if (str == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        } else if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        } else {
            this.j = context;
            this.k = str;
            this.l = str2;
            this.m = collection;
            this.g = new InstallerPackageNameProvider();
            this.a = new AdvertisingInfoProvider(context);
            this.h = CommonUtils.getBooleanResourceValue(context, COLLECT_DEVICE_IDENTIFIERS, true);
            if (!this.h) {
                Logger logger = Fabric.getLogger();
                String str3 = Fabric.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Device ID collection disabled for ");
                sb.append(context.getPackageName());
                logger.d(str3, sb.toString());
            }
            this.i = CommonUtils.getBooleanResourceValue(context, COLLECT_USER_IDENTIFIERS, true);
            if (!this.i) {
                Logger logger2 = Fabric.getLogger();
                String str4 = Fabric.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("User information collection disabled for ");
                sb2.append(context.getPackageName());
                logger2.d(str4, sb2.toString());
            }
        }
    }

    public boolean canCollectUserIds() {
        return this.i;
    }

    private String a(String str) {
        if (str == null) {
            return null;
        }
        return d.matcher(str).replaceAll("").toLowerCase(Locale.US);
    }

    public String getAppInstallIdentifier() {
        String str = this.l;
        if (str != null) {
            return str;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.j);
        String string = sharedPrefs.getString("crashlytics.installation.id", null);
        return string == null ? a(sharedPrefs) : string;
    }

    public String getAppIdentifier() {
        return this.k;
    }

    public String getOsVersionString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getOsDisplayVersionString());
        sb.append("/");
        sb.append(getOsBuildVersionString());
        return sb.toString();
    }

    public String getOsDisplayVersionString() {
        return b(VERSION.RELEASE);
    }

    public String getOsBuildVersionString() {
        return b(VERSION.INCREMENTAL);
    }

    public String getModelName() {
        return String.format(Locale.US, "%s/%s", new Object[]{b(Build.MANUFACTURER), b(Build.MODEL)});
    }

    private String b(String str) {
        return str.replaceAll(e, "");
    }

    public String getDeviceUUID() {
        String str = "";
        if (!this.h) {
            return str;
        }
        String androidId = getAndroidId();
        if (androidId != null) {
            return androidId;
        }
        SharedPreferences sharedPrefs = CommonUtils.getSharedPrefs(this.j);
        String string = sharedPrefs.getString("crashlytics.installation.id", null);
        return string == null ? a(sharedPrefs) : string;
    }

    private String a(SharedPreferences sharedPreferences) {
        this.f.lock();
        try {
            String string = sharedPreferences.getString("crashlytics.installation.id", null);
            if (string == null) {
                string = a(UUID.randomUUID().toString());
                sharedPreferences.edit().putString("crashlytics.installation.id", string).commit();
            }
            return string;
        } finally {
            this.f.unlock();
        }
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        HashMap hashMap = new HashMap();
        for (Kit kit : this.m) {
            if (kit instanceof DeviceIdentifierProvider) {
                for (Entry entry : ((DeviceIdentifierProvider) kit).getDeviceIdentifiers().entrySet()) {
                    a(hashMap, (DeviceIdentifierType) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        a(hashMap, DeviceIdentifierType.ANDROID_ID, getAndroidId());
        a(hashMap, DeviceIdentifierType.ANDROID_ADVERTISING_ID, getAdvertisingId());
        return Collections.unmodifiableMap(hashMap);
    }

    public String getInstallerPackageName() {
        return this.g.getInstallerPackageName(this.j);
    }

    /* access modifiers changed from: 0000 */
    public synchronized AdvertisingInfo a() {
        if (!this.c) {
            this.b = this.a.a();
            this.c = true;
        }
        return this.b;
    }

    public Boolean isLimitAdTrackingEnabled() {
        if (this.h) {
            AdvertisingInfo a2 = a();
            if (a2 != null) {
                return Boolean.valueOf(a2.b);
            }
        }
        return null;
    }

    public String getAdvertisingId() {
        if (this.h) {
            AdvertisingInfo a2 = a();
            if (a2 != null) {
                return a2.a;
            }
        }
        return null;
    }

    private void a(Map<DeviceIdentifierType, String> map, DeviceIdentifierType deviceIdentifierType, String str) {
        if (str != null) {
            map.put(deviceIdentifierType, str);
        }
    }

    public String getAndroidId() {
        if (this.h) {
            String string = Secure.getString(this.j.getContentResolver(), "android_id");
            if (!"9774d56d682e549c".equals(string)) {
                return a(string);
            }
        }
        return null;
    }
}
