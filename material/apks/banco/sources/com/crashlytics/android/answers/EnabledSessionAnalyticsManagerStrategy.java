package com.crashlytics.android.answers;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.events.TimeBasedFileRollOverRunnable;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class EnabledSessionAnalyticsManagerStrategy implements SessionAnalyticsManagerStrategy {
    final SessionEventMetadata a;
    FilesSender b;
    ApiKey c = new ApiKey();
    EventFilter d = new KeepAllEventFilter();
    boolean e = true;
    boolean f = true;
    volatile int g = -1;
    private final Kit h;
    private final HttpRequestFactory i;
    private final Context j;
    private final SessionAnalyticsFilesManager k;
    private final ScheduledExecutorService l;
    private final AtomicReference<ScheduledFuture<?>> m = new AtomicReference<>();

    public EnabledSessionAnalyticsManagerStrategy(Kit kit, Context context, ScheduledExecutorService scheduledExecutorService, SessionAnalyticsFilesManager sessionAnalyticsFilesManager, HttpRequestFactory httpRequestFactory, SessionEventMetadata sessionEventMetadata) {
        this.h = kit;
        this.j = context;
        this.l = scheduledExecutorService;
        this.k = sessionAnalyticsFilesManager;
        this.i = httpRequestFactory;
        this.a = sessionEventMetadata;
    }

    public void a(AnalyticsSettingsData analyticsSettingsData, String str) {
        SessionAnalyticsFilesSender sessionAnalyticsFilesSender = new SessionAnalyticsFilesSender(this.h, str, analyticsSettingsData.analyticsURL, this.i, this.c.getValue(this.j));
        this.b = AnswersRetryFilesSender.a(sessionAnalyticsFilesSender);
        this.k.a(analyticsSettingsData);
        this.e = analyticsSettingsData.trackCustomEvents;
        Logger logger = Fabric.getLogger();
        String str2 = Answers.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Custom event tracking ");
        sb.append(this.e ? "enabled" : "disabled");
        logger.d(str2, sb.toString());
        this.f = analyticsSettingsData.trackPredefinedEvents;
        Logger logger2 = Fabric.getLogger();
        String str3 = Answers.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Predefined event tracking ");
        sb2.append(this.f ? "enabled" : "disabled");
        logger2.d(str3, sb2.toString());
        if (analyticsSettingsData.samplingRate > 1) {
            Fabric.getLogger().d(Answers.TAG, "Event sampling enabled");
            this.d = new SamplingEventFilter(analyticsSettingsData.samplingRate);
        }
        this.g = analyticsSettingsData.flushIntervalSeconds;
        a(0, (long) this.g);
    }

    public void a(Builder builder) {
        SessionEvent a2 = builder.a(this.a);
        if (!this.e && Type.CUSTOM.equals(a2.c)) {
            Logger logger = Fabric.getLogger();
            String str = Answers.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Custom events tracking disabled - skipping event: ");
            sb.append(a2);
            logger.d(str, sb.toString());
        } else if (!this.f && Type.PREDEFINED.equals(a2.c)) {
            Logger logger2 = Fabric.getLogger();
            String str2 = Answers.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Predefined events tracking disabled - skipping event: ");
            sb2.append(a2);
            logger2.d(str2, sb2.toString());
        } else if (this.d.a(a2)) {
            Logger logger3 = Fabric.getLogger();
            String str3 = Answers.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Skipping filtered event: ");
            sb3.append(a2);
            logger3.d(str3, sb3.toString());
        } else {
            try {
                this.k.writeEvent(a2);
            } catch (IOException e2) {
                Logger logger4 = Fabric.getLogger();
                String str4 = Answers.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Failed to write event: ");
                sb4.append(a2);
                logger4.e(str4, sb4.toString(), e2);
            }
            scheduleTimeBasedRollOverIfNeeded();
        }
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
        if (this.g != -1) {
            a((long) this.g, (long) this.g);
        }
    }

    public void a() {
        if (this.b == null) {
            CommonUtils.logControlled(this.j, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.j, "Sending all files");
        List batchOfFilesToSend = this.k.getBatchOfFilesToSend();
        int i2 = 0;
        while (true) {
            try {
                if (batchOfFilesToSend.size() <= 0) {
                    break;
                }
                CommonUtils.logControlled(this.j, String.format(Locale.US, "attempt to send batch of %d files", new Object[]{Integer.valueOf(batchOfFilesToSend.size())}));
                boolean send = this.b.send(batchOfFilesToSend);
                if (send) {
                    i2 += batchOfFilesToSend.size();
                    this.k.deleteSentFiles(batchOfFilesToSend);
                }
                if (!send) {
                    break;
                }
                batchOfFilesToSend = this.k.getBatchOfFilesToSend();
            } catch (Exception e2) {
                Context context = this.j;
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to send batch of analytics files to server: ");
                sb.append(e2.getMessage());
                CommonUtils.logControlledError(context, sb.toString(), e2);
            }
        }
        if (i2 == 0) {
            this.k.deleteOldestInRollOverIfOverMax();
        }
    }

    public void cancelTimeBasedFileRollOver() {
        if (this.m.get() != null) {
            CommonUtils.logControlled(this.j, "Cancelling time-based rollover because no events are currently being generated.");
            ((ScheduledFuture) this.m.get()).cancel(false);
            this.m.set(null);
        }
    }

    public void b() {
        this.k.deleteAllEventsFiles();
    }

    public boolean rollFileOver() {
        try {
            return this.k.rollFileOver();
        } catch (IOException e2) {
            CommonUtils.logControlledError(this.j, "Failed to roll file over.", e2);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2, long j3) {
        if (this.m.get() == null) {
            TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.j, this);
            Context context = this.j;
            StringBuilder sb = new StringBuilder();
            sb.append("Scheduling time based file roll over every ");
            sb.append(j3);
            sb.append(" seconds");
            CommonUtils.logControlled(context, sb.toString());
            try {
                this.m.set(this.l.scheduleAtFixedRate(timeBasedFileRollOverRunnable, j2, j3, TimeUnit.SECONDS));
            } catch (RejectedExecutionException e2) {
                CommonUtils.logControlledError(this.j, "Failed to schedule time based file roll over", e2);
            }
        }
    }
}
