package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.events.EventsStorageListener;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.concurrent.ScheduledExecutorService;

class AnswersEventsHandler implements EventsStorageListener {
    final ScheduledExecutorService a;
    SessionAnalyticsManagerStrategy b = new DisabledSessionAnalyticsManagerStrategy();
    /* access modifiers changed from: private */
    public final Kit c;
    /* access modifiers changed from: private */
    public final Context d;
    /* access modifiers changed from: private */
    public final AnswersFilesManagerProvider e;
    /* access modifiers changed from: private */
    public final SessionMetadataCollector f;
    /* access modifiers changed from: private */
    public final HttpRequestFactory g;

    public AnswersEventsHandler(Kit kit, Context context, AnswersFilesManagerProvider answersFilesManagerProvider, SessionMetadataCollector sessionMetadataCollector, HttpRequestFactory httpRequestFactory, ScheduledExecutorService scheduledExecutorService) {
        this.c = kit;
        this.d = context;
        this.e = answersFilesManagerProvider;
        this.f = sessionMetadataCollector;
        this.g = httpRequestFactory;
        this.a = scheduledExecutorService;
    }

    public void a(Builder builder) {
        a(builder, false, false);
    }

    public void b(Builder builder) {
        a(builder, false, true);
    }

    public void c(Builder builder) {
        a(builder, true, false);
    }

    public void a(final AnalyticsSettingsData analyticsSettingsData, final String str) {
        b((Runnable) new Runnable() {
            public void run() {
                try {
                    AnswersEventsHandler.this.b.a(analyticsSettingsData, str);
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to set analytics settings data", e);
                }
            }
        });
    }

    public void a() {
        b((Runnable) new Runnable() {
            public void run() {
                try {
                    SessionAnalyticsManagerStrategy sessionAnalyticsManagerStrategy = AnswersEventsHandler.this.b;
                    AnswersEventsHandler.this.b = new DisabledSessionAnalyticsManagerStrategy();
                    sessionAnalyticsManagerStrategy.b();
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to disable events", e);
                }
            }
        });
    }

    public void onRollOver(String str) {
        b((Runnable) new Runnable() {
            public void run() {
                try {
                    AnswersEventsHandler.this.b.a();
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to send events files", e);
                }
            }
        });
    }

    public void b() {
        b((Runnable) new Runnable() {
            public void run() {
                try {
                    SessionEventMetadata a2 = AnswersEventsHandler.this.f.a();
                    SessionAnalyticsFilesManager a3 = AnswersEventsHandler.this.e.a();
                    a3.registerRollOverListener(AnswersEventsHandler.this);
                    AnswersEventsHandler answersEventsHandler = AnswersEventsHandler.this;
                    EnabledSessionAnalyticsManagerStrategy enabledSessionAnalyticsManagerStrategy = new EnabledSessionAnalyticsManagerStrategy(AnswersEventsHandler.this.c, AnswersEventsHandler.this.d, AnswersEventsHandler.this.a, a3, AnswersEventsHandler.this.g, a2);
                    answersEventsHandler.b = enabledSessionAnalyticsManagerStrategy;
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to enable events", e);
                }
            }
        });
    }

    public void c() {
        b((Runnable) new Runnable() {
            public void run() {
                try {
                    AnswersEventsHandler.this.b.rollFileOver();
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to flush events", e);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(final Builder builder, boolean z, final boolean z2) {
        AnonymousClass6 r0 = new Runnable() {
            public void run() {
                try {
                    AnswersEventsHandler.this.b.a(builder);
                    if (z2) {
                        AnswersEventsHandler.this.b.rollFileOver();
                    }
                } catch (Exception e) {
                    Fabric.getLogger().e(Answers.TAG, "Failed to process event", e);
                }
            }
        };
        if (z) {
            a((Runnable) r0);
        } else {
            b((Runnable) r0);
        }
    }

    private void a(Runnable runnable) {
        try {
            this.a.submit(runnable).get();
        } catch (Exception e2) {
            Fabric.getLogger().e(Answers.TAG, "Failed to run events task", e2);
        }
    }

    private void b(Runnable runnable) {
        try {
            this.a.submit(runnable);
        } catch (Exception e2) {
            Fabric.getLogger().e(Answers.TAG, "Failed to submit events task", e2);
        }
    }
}
