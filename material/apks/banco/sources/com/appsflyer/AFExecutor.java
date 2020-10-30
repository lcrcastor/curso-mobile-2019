package com.appsflyer;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AFExecutor {
    private static AFExecutor a;
    private Executor b;
    private ScheduledExecutorService c;
    private Executor d;

    private AFExecutor() {
    }

    public static AFExecutor getInstance() {
        if (a == null) {
            a = new AFExecutor();
        }
        return a;
    }

    public Executor getSerialExecutor() {
        if (this.d == null) {
            if (VERSION.SDK_INT < 11) {
                return Executors.newSingleThreadExecutor();
            }
            this.d = AsyncTask.SERIAL_EXECUTOR;
        }
        return this.d;
    }

    public Executor getThreadPoolExecutor() {
        if (this.b == null || ((this.b instanceof ThreadPoolExecutor) && (((ThreadPoolExecutor) this.b).isShutdown() || ((ThreadPoolExecutor) this.b).isTerminated() || ((ThreadPoolExecutor) this.b).isTerminating()))) {
            if (VERSION.SDK_INT < 11) {
                return Executors.newSingleThreadExecutor();
            }
            this.b = Executors.newFixedThreadPool(2);
        }
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final ScheduledThreadPoolExecutor a() {
        if (this.c == null || this.c.isShutdown() || this.c.isTerminated()) {
            this.c = Executors.newScheduledThreadPool(2);
        }
        return (ScheduledThreadPoolExecutor) this.c;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        try {
            a(this.c);
            if (this.b instanceof ThreadPoolExecutor) {
                a((ThreadPoolExecutor) this.b);
            }
        } catch (Throwable th) {
            AFLogger.afErrorLog("failed to stop Executors", th);
        }
    }

    private static void a(ExecutorService executorService) {
        try {
            AFLogger.afRDLog("shut downing executor ...");
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
        } catch (InterruptedException unused) {
            AFLogger.afRDLog("InterruptedException!!!");
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
        } catch (Throwable th) {
            if (!executorService.isTerminated()) {
                AFLogger.afRDLog("killing non-finished tasks");
            }
            executorService.shutdownNow();
            throw th;
        }
    }
}
