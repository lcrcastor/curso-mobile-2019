package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Locale;

abstract class AbstractAppSpiCall extends AbstractSpiCall {
    public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
    public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
    public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
    public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
    public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
    public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
    public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
    public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
    public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
    public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
    public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
    public static final String APP_NAME_PARAM = "app[name]";
    public static final String APP_SDK_MODULES_PARAM_BUILD_TYPE = "app[build][libraries][%s][type]";
    public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries][%s]";
    public static final String APP_SDK_MODULES_PARAM_VERSION = "app[build][libraries][%s][version]";
    public static final String APP_SOURCE_PARAM = "app[source]";

    public AbstractAppSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    public boolean invoke(AppRequestData appRequestData) {
        HttpRequest b = b(a(getHttpRequest(), appRequestData), appRequestData);
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending app info to ");
        sb.append(getUrl());
        logger.d(str, sb.toString());
        if (appRequestData.icon != null) {
            Logger logger2 = Fabric.getLogger();
            String str2 = Fabric.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("App icon hash is ");
            sb2.append(appRequestData.icon.hash);
            logger2.d(str2, sb2.toString());
            Logger logger3 = Fabric.getLogger();
            String str3 = Fabric.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("App icon size is ");
            sb3.append(appRequestData.icon.width);
            sb3.append("x");
            sb3.append(appRequestData.icon.height);
            logger3.d(str3, sb3.toString());
        }
        int code = b.code();
        String str4 = "POST".equals(b.method()) ? "Create" : "Update";
        Logger logger4 = Fabric.getLogger();
        String str5 = Fabric.TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str4);
        sb4.append(" app request ID: ");
        sb4.append(b.header(AbstractSpiCall.HEADER_REQUEST_ID));
        logger4.d(str5, sb4.toString());
        Logger logger5 = Fabric.getLogger();
        String str6 = Fabric.TAG;
        StringBuilder sb5 = new StringBuilder();
        sb5.append("Result was ");
        sb5.append(code);
        logger5.d(str6, sb5.toString());
        return ResponseParser.parse(code) == 0;
    }

    private HttpRequest a(HttpRequest httpRequest, AppRequestData appRequestData) {
        return httpRequest.header(AbstractSpiCall.HEADER_API_KEY, appRequestData.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ca  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private io.fabric.sdk.android.services.network.HttpRequest b(io.fabric.sdk.android.services.network.HttpRequest r8, io.fabric.sdk.android.services.settings.AppRequestData r9) {
        /*
            r7 = this;
            java.lang.String r0 = "app[identifier]"
            java.lang.String r1 = r9.appId
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[name]"
            java.lang.String r1 = r9.name
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[display_version]"
            java.lang.String r1 = r9.displayVersion
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[build_version]"
            java.lang.String r1 = r9.buildVersion
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[source]"
            int r1 = r9.source
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[minimum_sdk_version]"
            java.lang.String r1 = r9.minSdkVersion
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = "app[built_sdk_version]"
            java.lang.String r1 = r9.builtSdkVersion
            io.fabric.sdk.android.services.network.HttpRequest r8 = r8.part(r0, r1)
            java.lang.String r0 = r9.instanceIdentifier
            boolean r0 = io.fabric.sdk.android.services.common.CommonUtils.isNullOrEmpty(r0)
            if (r0 != 0) goto L_0x004b
            java.lang.String r0 = "app[instance_identifier]"
            java.lang.String r1 = r9.instanceIdentifier
            r8.part(r0, r1)
        L_0x004b:
            io.fabric.sdk.android.services.settings.IconRequest r0 = r9.icon
            if (r0 == 0) goto L_0x00c6
            r0 = 0
            io.fabric.sdk.android.Kit r1 = r7.kit     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            android.content.Context r1 = r1.getContext()     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            io.fabric.sdk.android.services.settings.IconRequest r2 = r9.icon     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            int r2 = r2.iconResourceId     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            java.io.InputStream r1 = r1.openRawResource(r2)     // Catch:{ NotFoundException -> 0x0097, all -> 0x0094 }
            java.lang.String r0 = "app[icon][hash]"
            io.fabric.sdk.android.services.settings.IconRequest r2 = r9.icon     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.String r2 = r2.hash     // Catch:{ NotFoundException -> 0x0092 }
            io.fabric.sdk.android.services.network.HttpRequest r0 = r8.part(r0, r2)     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.String r2 = "app[icon][data]"
            java.lang.String r3 = "icon.png"
            java.lang.String r4 = "application/octet-stream"
            io.fabric.sdk.android.services.network.HttpRequest r0 = r0.part(r2, r3, r4, r1)     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.String r2 = "app[icon][width]"
            io.fabric.sdk.android.services.settings.IconRequest r3 = r9.icon     // Catch:{ NotFoundException -> 0x0092 }
            int r3 = r3.width     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ NotFoundException -> 0x0092 }
            io.fabric.sdk.android.services.network.HttpRequest r0 = r0.part(r2, r3)     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.String r2 = "app[icon][height]"
            io.fabric.sdk.android.services.settings.IconRequest r3 = r9.icon     // Catch:{ NotFoundException -> 0x0092 }
            int r3 = r3.height     // Catch:{ NotFoundException -> 0x0092 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ NotFoundException -> 0x0092 }
            r0.part(r2, r3)     // Catch:{ NotFoundException -> 0x0092 }
            goto L_0x00b9
        L_0x0092:
            r0 = move-exception
            goto L_0x009b
        L_0x0094:
            r8 = move-exception
            r1 = r0
            goto L_0x00c0
        L_0x0097:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x009b:
            io.fabric.sdk.android.Logger r2 = io.fabric.sdk.android.Fabric.getLogger()     // Catch:{ all -> 0x00bf }
            java.lang.String r3 = "Fabric"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r4.<init>()     // Catch:{ all -> 0x00bf }
            java.lang.String r5 = "Failed to find app icon with resource ID: "
            r4.append(r5)     // Catch:{ all -> 0x00bf }
            io.fabric.sdk.android.services.settings.IconRequest r5 = r9.icon     // Catch:{ all -> 0x00bf }
            int r5 = r5.iconResourceId     // Catch:{ all -> 0x00bf }
            r4.append(r5)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00bf }
            r2.e(r3, r4, r0)     // Catch:{ all -> 0x00bf }
        L_0x00b9:
            java.lang.String r0 = "Failed to close app icon InputStream."
            io.fabric.sdk.android.services.common.CommonUtils.closeOrLog(r1, r0)
            goto L_0x00c6
        L_0x00bf:
            r8 = move-exception
        L_0x00c0:
            java.lang.String r9 = "Failed to close app icon InputStream."
            io.fabric.sdk.android.services.common.CommonUtils.closeOrLog(r1, r9)
            throw r8
        L_0x00c6:
            java.util.Collection<io.fabric.sdk.android.KitInfo> r0 = r9.sdkKits
            if (r0 == 0) goto L_0x00f3
            java.util.Collection<io.fabric.sdk.android.KitInfo> r9 = r9.sdkKits
            java.util.Iterator r9 = r9.iterator()
        L_0x00d0:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L_0x00f3
            java.lang.Object r0 = r9.next()
            io.fabric.sdk.android.KitInfo r0 = (io.fabric.sdk.android.KitInfo) r0
            java.lang.String r1 = r7.a(r0)
            java.lang.String r2 = r0.getVersion()
            r8.part(r1, r2)
            java.lang.String r1 = r7.b(r0)
            java.lang.String r0 = r0.getBuildType()
            r8.part(r1, r0)
            goto L_0x00d0
        L_0x00f3:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.settings.AbstractAppSpiCall.b(io.fabric.sdk.android.services.network.HttpRequest, io.fabric.sdk.android.services.settings.AppRequestData):io.fabric.sdk.android.services.network.HttpRequest");
    }

    /* access modifiers changed from: 0000 */
    public String a(KitInfo kitInfo) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_VERSION, new Object[]{kitInfo.getIdentifier()});
    }

    /* access modifiers changed from: 0000 */
    public String b(KitInfo kitInfo) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_BUILD_TYPE, new Object[]{kitInfo.getIdentifier()});
    }
}
