package com.crashlytics.android.answers;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.crashlytics.android.answers.BackgroundManager.Listener;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class SessionAnalyticsManager implements Listener {
    final AnswersEventsHandler a;
    final ActivityLifecycleManager b;
    final BackgroundManager c;
    final AnswersPreferenceManager d;
    private final long e;

    public void a(String str) {
    }

    public static SessionAnalyticsManager a(Kit kit, Context context, IdManager idManager, String str, String str2, long j) {
        Context context2 = context;
        SessionMetadataCollector sessionMetadataCollector = new SessionMetadataCollector(context2, idManager, str, str2);
        Kit kit2 = kit;
        AnswersFilesManagerProvider answersFilesManagerProvider = new AnswersFilesManagerProvider(context2, new FileStoreImpl(kit2));
        DefaultHttpRequestFactory defaultHttpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
        ActivityLifecycleManager activityLifecycleManager = new ActivityLifecycleManager(context2);
        ScheduledExecutorService buildSingleThreadScheduledExecutorService = ExecutorUtils.buildSingleThreadScheduledExecutorService("Answers Events Handler");
        BackgroundManager backgroundManager = new BackgroundManager(buildSingleThreadScheduledExecutorService);
        AnswersEventsHandler answersEventsHandler = new AnswersEventsHandler(kit2, context2, answersFilesManagerProvider, sessionMetadataCollector, defaultHttpRequestFactory, buildSingleThreadScheduledExecutorService);
        SessionAnalyticsManager sessionAnalyticsManager = new SessionAnalyticsManager(answersEventsHandler, activityLifecycleManager, backgroundManager, AnswersPreferenceManager.a(context2), j);
        return sessionAnalyticsManager;
    }

    SessionAnalyticsManager(AnswersEventsHandler answersEventsHandler, ActivityLifecycleManager activityLifecycleManager, BackgroundManager backgroundManager, AnswersPreferenceManager answersPreferenceManager, long j) {
        this.a = answersEventsHandler;
        this.b = activityLifecycleManager;
        this.c = backgroundManager;
        this.d = answersPreferenceManager;
        this.e = j;
    }

    public void a() {
        this.a.b();
        this.b.registerCallbacks(new AnswersLifecycleCallbacks(this, this.c));
        this.c.a((Listener) this);
        if (c()) {
            a(this.e);
            this.d.a();
        }
    }

    public void b() {
        this.b.resetCallbacks();
        this.a.a();
    }

    public void a(CustomEvent customEvent) {
        Logger logger = Fabric.getLogger();
        String str = Answers.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Logged custom event: ");
        sb.append(customEvent);
        logger.d(str, sb.toString());
        this.a.a(SessionEvent.a(customEvent));
    }

    public void a(PredefinedEvent predefinedEvent) {
        Logger logger = Fabric.getLogger();
        String str = Answers.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Logged predefined event: ");
        sb.append(predefinedEvent);
        logger.d(str, sb.toString());
        this.a.a(SessionEvent.a(predefinedEvent));
    }

    public void a(String str, String str2) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("onCrash called from main thread!!!");
        }
        Fabric.getLogger().d(Answers.TAG, "Logged crash");
        this.a.c(SessionEvent.a(str, str2));
    }

    public void a(long j) {
        Fabric.getLogger().d(Answers.TAG, "Logged install");
        this.a.b(SessionEvent.a(j));
    }

    public void a(Activity activity, Type type) {
        Logger logger = Fabric.getLogger();
        String str = Answers.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Logged lifecycle event: ");
        sb.append(type.name());
        logger.d(str, sb.toString());
        this.a.a(SessionEvent.a(type, activity));
    }

    public void onBackground() {
        Fabric.getLogger().d(Answers.TAG, "Flush events when app is backgrounded");
        this.a.c();
    }

    public void a(AnalyticsSettingsData analyticsSettingsData, String str) {
        this.c.a(analyticsSettingsData.flushOnBackground);
        this.a.a(analyticsSettingsData, str);
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return !this.d.b();
    }
}
