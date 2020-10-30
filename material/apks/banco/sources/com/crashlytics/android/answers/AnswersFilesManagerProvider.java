package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Looper;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.events.GZIPQueueFileEventStorage;
import io.fabric.sdk.android.services.persistence.FileStore;

class AnswersFilesManagerProvider {
    final Context a;
    final FileStore b;

    public AnswersFilesManagerProvider(Context context, FileStore fileStore) {
        this.a = context;
        this.b = fileStore;
    }

    public SessionAnalyticsFilesManager a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("AnswersFilesManagerProvider cannot be called on the main thread");
        }
        return new SessionAnalyticsFilesManager(this.a, new SessionEventTransform(), new SystemCurrentTimeProvider(), new GZIPQueueFileEventStorage(this.a, this.b.getFilesDir(), "session_analytics.tap", "session_analytics_to_send"));
    }
}
