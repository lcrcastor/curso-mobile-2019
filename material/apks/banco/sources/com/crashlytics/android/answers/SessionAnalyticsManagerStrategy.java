package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.events.FileRollOverManager;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

interface SessionAnalyticsManagerStrategy extends FileRollOverManager {
    void a();

    void a(Builder builder);

    void a(AnalyticsSettingsData analyticsSettingsData, String str);

    void b();
}
