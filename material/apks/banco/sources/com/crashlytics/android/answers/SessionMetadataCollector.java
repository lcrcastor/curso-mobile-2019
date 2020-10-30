package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import java.util.Map;
import java.util.UUID;

class SessionMetadataCollector {
    private final Context a;
    private final IdManager b;
    private final String c;
    private final String d;

    public SessionMetadataCollector(Context context, IdManager idManager, String str, String str2) {
        this.a = context;
        this.b = idManager;
        this.c = str;
        this.d = str2;
    }

    public SessionEventMetadata a() {
        Map deviceIdentifiers = this.b.getDeviceIdentifiers();
        SessionEventMetadata sessionEventMetadata = new SessionEventMetadata(this.b.getAppIdentifier(), UUID.randomUUID().toString(), this.b.getAppInstallIdentifier(), (String) deviceIdentifiers.get(DeviceIdentifierType.ANDROID_ID), (String) deviceIdentifiers.get(DeviceIdentifierType.ANDROID_ADVERTISING_ID), this.b.isLimitAdTrackingEnabled(), (String) deviceIdentifiers.get(DeviceIdentifierType.FONT_TOKEN), CommonUtils.resolveBuildId(this.a), this.b.getOsVersionString(), this.b.getModelName(), this.c, this.d);
        return sessionEventMetadata;
    }
}
