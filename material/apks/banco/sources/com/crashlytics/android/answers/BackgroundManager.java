package com.crashlytics.android.answers;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class BackgroundManager {
    final AtomicReference<ScheduledFuture<?>> a = new AtomicReference<>();
    boolean b = true;
    private final ScheduledExecutorService c;
    private final List<Listener> d = new ArrayList();
    private volatile boolean e = true;

    public interface Listener {
        void onBackground();
    }

    public BackgroundManager(ScheduledExecutorService scheduledExecutorService) {
        this.c = scheduledExecutorService;
    }

    public void a(boolean z) {
        this.e = z;
    }

    /* access modifiers changed from: private */
    public void c() {
        for (Listener onBackground : this.d) {
            onBackground.onBackground();
        }
    }

    public void a(Listener listener) {
        this.d.add(listener);
    }

    public void a() {
        this.b = false;
        ScheduledFuture scheduledFuture = (ScheduledFuture) this.a.getAndSet(null);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    public void b() {
        if (this.e && !this.b) {
            this.b = true;
            try {
                this.a.compareAndSet(null, this.c.schedule(new Runnable() {
                    public void run() {
                        BackgroundManager.this.a.set(null);
                        BackgroundManager.this.c();
                    }
                }, LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, TimeUnit.MILLISECONDS));
            } catch (RejectedExecutionException e2) {
                Fabric.getLogger().d(Answers.TAG, "Failed to schedule background detector", e2);
            }
        }
    }
}
