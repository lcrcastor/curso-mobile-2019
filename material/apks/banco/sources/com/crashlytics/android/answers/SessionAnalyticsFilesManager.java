package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.UUID;

class SessionAnalyticsFilesManager extends EventsFilesManager<SessionEvent> {
    private AnalyticsSettingsData a;

    SessionAnalyticsFilesManager(Context context, SessionEventTransform sessionEventTransform, CurrentTimeProvider currentTimeProvider, EventsStorage eventsStorage) {
        super(context, sessionEventTransform, currentTimeProvider, eventsStorage, 100);
    }

    /* access modifiers changed from: protected */
    public String generateUniqueRollOverFileName() {
        UUID randomUUID = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append("sa");
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(randomUUID.toString());
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(this.currentTimeProvider.getCurrentTimeMillis());
        sb.append(".tap");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public int getMaxFilesToKeep() {
        return this.a == null ? super.getMaxFilesToKeep() : this.a.maxPendingSendFileCount;
    }

    /* access modifiers changed from: protected */
    public int getMaxByteSizePerFile() {
        return this.a == null ? super.getMaxByteSizePerFile() : this.a.maxByteSizePerFile;
    }

    /* access modifiers changed from: 0000 */
    public void a(AnalyticsSettingsData analyticsSettingsData) {
        this.a = analyticsSettingsData;
    }
}
