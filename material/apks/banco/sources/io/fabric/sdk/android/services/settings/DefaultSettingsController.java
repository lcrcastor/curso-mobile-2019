package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import org.json.JSONObject;

class DefaultSettingsController implements SettingsController {
    private final SettingsRequest a;
    private final SettingsJsonTransform b;
    private final CurrentTimeProvider c;
    private final CachedSettingsIo d;
    private final SettingsSpiCall e;
    private final Kit f;
    private final PreferenceStore g = new PreferenceStoreImpl(this.f);

    public DefaultSettingsController(Kit kit, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonTransform settingsJsonTransform, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall) {
        this.f = kit;
        this.a = settingsRequest;
        this.c = currentTimeProvider;
        this.b = settingsJsonTransform;
        this.d = cachedSettingsIo;
        this.e = settingsSpiCall;
    }

    public SettingsData loadSettingsData() {
        return loadSettingsData(SettingsCacheBehavior.USE_CACHE);
    }

    public SettingsData loadSettingsData(SettingsCacheBehavior settingsCacheBehavior) {
        SettingsData settingsData = null;
        try {
            if (!Fabric.isDebuggable() && !c()) {
                settingsData = a(settingsCacheBehavior);
            }
            if (settingsData == null) {
                JSONObject invoke = this.e.invoke(this.a);
                if (invoke != null) {
                    SettingsData buildFromJson = this.b.buildFromJson(this.c, invoke);
                    try {
                        this.d.writeCachedSettings(buildFromJson.expiresAtMillis, invoke);
                        a(invoke, "Loaded settings: ");
                        a(a());
                        settingsData = buildFromJson;
                    } catch (Exception e2) {
                        e = e2;
                        settingsData = buildFromJson;
                        Fabric.getLogger().e(Fabric.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
                        return settingsData;
                    }
                }
            }
            if (settingsData == null) {
                return a(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e(Fabric.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
            return settingsData;
        }
        return settingsData;
    }

    private SettingsData a(SettingsCacheBehavior settingsCacheBehavior) {
        SettingsData settingsData = null;
        try {
            if (SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(settingsCacheBehavior)) {
                return null;
            }
            JSONObject readCachedSettings = this.d.readCachedSettings();
            if (readCachedSettings != null) {
                SettingsData buildFromJson = this.b.buildFromJson(this.c, readCachedSettings);
                if (buildFromJson != null) {
                    a(readCachedSettings, "Loaded cached settings: ");
                    long currentTimeMillis = this.c.getCurrentTimeMillis();
                    if (!SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(settingsCacheBehavior)) {
                        if (buildFromJson.isExpired(currentTimeMillis)) {
                            Fabric.getLogger().d(Fabric.TAG, "Cached settings have expired.");
                            return null;
                        }
                    }
                    try {
                        Fabric.getLogger().d(Fabric.TAG, "Returning cached settings.");
                        return buildFromJson;
                    } catch (Exception e2) {
                        e = e2;
                        settingsData = buildFromJson;
                        Fabric.getLogger().e(Fabric.TAG, "Failed to get cached settings", e);
                        return settingsData;
                    }
                } else {
                    Fabric.getLogger().e(Fabric.TAG, "Failed to transform cached settings data.", null);
                    return null;
                }
            } else {
                Fabric.getLogger().d(Fabric.TAG, "No cached settings data found.");
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e(Fabric.TAG, "Failed to get cached settings", e);
            return settingsData;
        }
    }

    private void a(JSONObject jSONObject, String str) {
        Logger logger = Fabric.getLogger();
        String str2 = Fabric.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(jSONObject.toString());
        logger.d(str2, sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(this.f.getContext()));
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        return this.g.get().getString("existing_instance_identifier", "");
    }

    /* access modifiers changed from: 0000 */
    @SuppressLint({"CommitPrefEdits"})
    public boolean a(String str) {
        Editor edit = this.g.edit();
        edit.putString("existing_instance_identifier", str);
        return this.g.save(edit);
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return !b().equals(a());
    }
}
