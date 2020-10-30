package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T> implements EventsStrategy<T> {
    final ScheduledExecutorService a;
    final AtomicReference<ScheduledFuture<?>> b;
    volatile int c = -1;
    protected final Context context;
    protected final EventsFilesManager<T> filesManager;

    public EnabledEventsStrategy(Context context2, ScheduledExecutorService scheduledExecutorService, EventsFilesManager<T> eventsFilesManager) {
        this.context = context2;
        this.a = scheduledExecutorService;
        this.filesManager = eventsFilesManager;
        this.b = new AtomicReference<>();
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
        if (this.c != -1) {
            a((long) this.c, (long) this.c);
        }
    }

    public void sendEvents() {
        a();
    }

    public void cancelTimeBasedFileRollOver() {
        if (this.b.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            ((ScheduledFuture) this.b.get()).cancel(false);
            this.b.set(null);
        }
    }

    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    public void recordEvent(T t) {
        CommonUtils.logControlled(this.context, t.toString());
        try {
            this.filesManager.writeEvent(t);
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to write event.", e);
        }
        scheduleTimeBasedRollOverIfNeeded();
    }

    public boolean rollFileOver() {
        try {
            return this.filesManager.rollFileOver();
        } catch (IOException e) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", e);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void configureRollover(int i) {
        this.c = i;
        a(0, (long) this.c);
    }

    public int getRollover() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j, long j2) {
        if (this.b.get() == null) {
            TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            Context context2 = this.context;
            StringBuilder sb = new StringBuilder();
            sb.append("Scheduling time based file roll over every ");
            sb.append(j2);
            sb.append(" seconds");
            CommonUtils.logControlled(context2, sb.toString());
            try {
                this.b.set(this.a.scheduleAtFixedRate(timeBasedFileRollOverRunnable, j, j2, TimeUnit.SECONDS));
            } catch (RejectedExecutionException e) {
                CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        FilesSender filesSender = getFilesSender();
        if (filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        List batchOfFilesToSend = this.filesManager.getBatchOfFilesToSend();
        int i = 0;
        while (true) {
            try {
                if (batchOfFilesToSend.size() <= 0) {
                    break;
                }
                CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", new Object[]{Integer.valueOf(batchOfFilesToSend.size())}));
                boolean send = filesSender.send(batchOfFilesToSend);
                if (send) {
                    i += batchOfFilesToSend.size();
                    this.filesManager.deleteSentFiles(batchOfFilesToSend);
                }
                if (!send) {
                    break;
                }
                batchOfFilesToSend = this.filesManager.getBatchOfFilesToSend();
            } catch (Exception e) {
                Context context2 = this.context;
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to send batch of analytics files to server: ");
                sb.append(e.getMessage());
                CommonUtils.logControlledError(context2, sb.toString(), e);
            }
        }
        if (i == 0) {
            this.filesManager.deleteOldestInRollOverIfOverMax();
        }
    }
}
