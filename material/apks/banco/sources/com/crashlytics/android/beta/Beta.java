package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.util.HashMap;
import java.util.Map;

public class Beta extends Kit<Boolean> implements DeviceIdentifierProvider {
    public static final String TAG = "Beta";
    private final MemoryValueCache<String> a = new MemoryValueCache<>();
    private final DeviceTokenLoader h = new DeviceTokenLoader();
    private UpdatesController i;

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    public String getVersion() {
        return "1.2.2.142";
    }

    public static Beta getInstance() {
        return (Beta) Fabric.getKit(Beta.class);
    }

    /* access modifiers changed from: protected */
    @TargetApi(14)
    public boolean onPreExecute() {
        this.i = a(VERSION.SDK_INT, (Application) getContext().getApplicationContext());
        return true;
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground() {
        Fabric.getLogger().d(TAG, "Beta kit initializing...");
        Context context = getContext();
        IdManager idManager = getIdManager();
        if (TextUtils.isEmpty(a(context, idManager.getInstallerPackageName()))) {
            Fabric.getLogger().d(TAG, "A Beta device token was not found for this app");
            return Boolean.valueOf(false);
        }
        Fabric.getLogger().d(TAG, "Beta device token is present, checking for app updates.");
        BetaSettingsData b = b();
        BuildProperties a2 = a(context);
        if (a(b, a2)) {
            this.i.a(context, this, idManager, b, a2, new PreferenceStoreImpl(this), new SystemCurrentTimeProvider(), new DefaultHttpRequestFactory(Fabric.getLogger()));
        }
        return Boolean.valueOf(true);
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(14)
    public UpdatesController a(int i2, Application application) {
        if (i2 >= 14) {
            return new ActivityLifecycleCheckForUpdatesController(getFabric().getActivityLifecycleManager(), getFabric().getExecutorService());
        }
        return new ImmediateCheckForUpdatesController();
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        String a2 = a(getContext(), getIdManager().getInstallerPackageName());
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(a2)) {
            hashMap.put(DeviceIdentifierType.FONT_TOKEN, a2);
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(BetaSettingsData betaSettingsData, BuildProperties buildProperties) {
        return (betaSettingsData == null || TextUtils.isEmpty(betaSettingsData.updateUrl) || buildProperties == null) ? false : true;
    }

    private String a(Context context, String str) {
        String str2 = null;
        try {
            String str3 = (String) this.a.get(context, this.h);
            if (!"".equals(str3)) {
                str2 = str3;
            }
        } catch (Exception e) {
            Fabric.getLogger().e(TAG, "Failed to load the Beta device token", e);
        }
        Logger logger = Fabric.getLogger();
        String str4 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Beta device token present: ");
        sb.append(!TextUtils.isEmpty(str2));
        logger.d(str4, sb.toString());
        return str2;
    }

    private BetaSettingsData b() {
        SettingsData awaitSettingsData = Settings.getInstance().awaitSettingsData();
        if (awaitSettingsData != null) {
            return awaitSettingsData.betaSettingsData;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x007d A[SYNTHETIC, Splitter:B:24:0x007d] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0092 A[SYNTHETIC, Splitter:B:31:0x0092] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.crashlytics.android.beta.BuildProperties a(android.content.Context r7) {
        /*
            r6 = this;
            r0 = 0
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ Exception -> 0x006c, all -> 0x0067 }
            java.lang.String r1 = "crashlytics-build.properties"
            java.io.InputStream r7 = r7.open(r1)     // Catch:{ Exception -> 0x006c, all -> 0x0067 }
            if (r7 == 0) goto L_0x0054
            com.crashlytics.android.beta.BuildProperties r1 = com.crashlytics.android.beta.BuildProperties.a(r7)     // Catch:{ Exception -> 0x004f }
            io.fabric.sdk.android.Logger r0 = io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ Exception -> 0x004d }
            java.lang.String r2 = "Beta"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004d }
            r3.<init>()     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = r1.d     // Catch:{ Exception -> 0x004d }
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = " build properties: "
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = r1.b     // Catch:{ Exception -> 0x004d }
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = " ("
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = r1.a     // Catch:{ Exception -> 0x004d }
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = " - "
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r4 = r1.c     // Catch:{ Exception -> 0x004d }
            r3.append(r4)     // Catch:{ Exception -> 0x004d }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x004d }
            r0.d(r2, r3)     // Catch:{ Exception -> 0x004d }
            r0 = r1
            goto L_0x0054
        L_0x004d:
            r0 = move-exception
            goto L_0x0070
        L_0x004f:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0070
        L_0x0054:
            if (r7 == 0) goto L_0x008e
            r7.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x008e
        L_0x005a:
            r7 = move-exception
            io.fabric.sdk.android.Logger r1 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Beta"
            java.lang.String r3 = "Error closing Beta build properties asset"
            r1.e(r2, r3, r7)
            goto L_0x008e
        L_0x0067:
            r7 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0090
        L_0x006c:
            r7 = move-exception
            r1 = r0
            r0 = r7
            r7 = r1
        L_0x0070:
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ all -> 0x008f }
            java.lang.String r3 = "Beta"
            java.lang.String r4 = "Error reading Beta build properties"
            r2.e(r3, r4, r0)     // Catch:{ all -> 0x008f }
            if (r7 == 0) goto L_0x008d
            r7.close()     // Catch:{ IOException -> 0x0081 }
            goto L_0x008d
        L_0x0081:
            r7 = move-exception
            io.fabric.sdk.android.Logger r0 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Beta"
            java.lang.String r3 = "Error closing Beta build properties asset"
            r0.e(r2, r3, r7)
        L_0x008d:
            r0 = r1
        L_0x008e:
            return r0
        L_0x008f:
            r0 = move-exception
        L_0x0090:
            if (r7 == 0) goto L_0x00a2
            r7.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x00a2
        L_0x0096:
            r7 = move-exception
            io.fabric.sdk.android.Logger r1 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r2 = "Beta"
            java.lang.String r3 = "Error closing Beta build properties asset"
            r1.e(r2, r3, r7)
        L_0x00a2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.beta.Beta.a(android.content.Context):com.crashlytics.android.beta.BuildProperties");
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
    }
}
