package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import cz.msebera.android.httpclient.entity.mime.MIME;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

class Onboarding extends Kit<Boolean> {
    private final HttpRequestFactory a = new DefaultHttpRequestFactory();
    private PackageManager h;
    private String i;
    private PackageInfo j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private final Future<Map<String, KitInfo>> p;
    private final Collection<Kit> q;

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public String getVersion() {
        return "1.3.14.143";
    }

    public Onboarding(Future<Map<String, KitInfo>> future, Collection<Kit> collection) {
        this.p = future;
        this.q = collection;
    }

    /* access modifiers changed from: protected */
    public boolean onPreExecute() {
        try {
            this.m = getIdManager().getInstallerPackageName();
            this.h = getContext().getPackageManager();
            this.i = getContext().getPackageName();
            this.j = this.h.getPackageInfo(this.i, 0);
            this.k = Integer.toString(this.j.versionCode);
            this.l = this.j.versionName == null ? IdManager.DEFAULT_VERSION_NAME : this.j.versionName;
            this.n = this.h.getApplicationLabel(getContext().getApplicationInfo()).toString();
            this.o = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
            return true;
        } catch (NameNotFoundException e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed init", e);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Boolean doInBackground() {
        boolean z;
        Map map;
        String appIconHashOrNull = CommonUtils.getAppIconHashOrNull(getContext());
        SettingsData c = c();
        if (c != null) {
            try {
                if (this.p != null) {
                    map = (Map) this.p.get();
                } else {
                    map = new HashMap();
                }
                z = a(appIconHashOrNull, c.appData, a(map, this.q).values());
            } catch (Exception e) {
                Fabric.getLogger().e(Fabric.TAG, "Error performing auto configuration.", e);
            }
            return Boolean.valueOf(z);
        }
        z = false;
        return Boolean.valueOf(z);
    }

    private SettingsData c() {
        try {
            Settings.getInstance().initialize(this, this.f, this.a, this.k, this.l, b()).loadSettingsData();
            return Settings.getInstance().awaitSettingsData();
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Error dealing with settings", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public Map<String, KitInfo> a(Map<String, KitInfo> map, Collection<Kit> collection) {
        for (Kit kit : collection) {
            if (!map.containsKey(kit.getIdentifier())) {
                map.put(kit.getIdentifier(), new KitInfo(kit.getIdentifier(), kit.getVersion(), MIME.ENC_BINARY));
            }
        }
        return map;
    }

    private boolean a(String str, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        if (AppSettingsData.STATUS_NEW.equals(appSettingsData.status)) {
            if (b(str, appSettingsData, collection)) {
                return Settings.getInstance().loadSettingsSkippingCache();
            }
            Fabric.getLogger().e(Fabric.TAG, "Failed to create app with Crashlytics service.", null);
            return false;
        } else if (AppSettingsData.STATUS_CONFIGURED.equals(appSettingsData.status)) {
            return Settings.getInstance().loadSettingsSkippingCache();
        } else {
            if (appSettingsData.updateRequired) {
                Fabric.getLogger().d(Fabric.TAG, "Server says an update is required - forcing a full App update.");
                c(str, appSettingsData, collection);
            }
            return true;
        }
    }

    private boolean b(String str, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        return new CreateAppSpiCall(this, b(), appSettingsData.url, this.a).invoke(a(IconRequest.build(getContext(), str), collection));
    }

    private boolean c(String str, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        return a(appSettingsData, IconRequest.build(getContext(), str), collection);
    }

    private boolean a(AppSettingsData appSettingsData, IconRequest iconRequest, Collection<KitInfo> collection) {
        return new UpdateAppSpiCall(this, b(), appSettingsData.url, this.a).invoke(a(iconRequest, collection));
    }

    private AppRequestData a(IconRequest iconRequest, Collection<KitInfo> collection) {
        Context context = getContext();
        AppRequestData appRequestData = new AppRequestData(new ApiKey().getValue(context), getIdManager().getAppIdentifier(), this.l, this.k, CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(context)), this.n, DeliveryMechanism.determineFrom(this.m).getId(), this.o, "0", iconRequest, collection);
        return appRequestData;
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
    }
}
