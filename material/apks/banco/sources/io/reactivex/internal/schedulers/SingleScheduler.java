package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleScheduler extends Scheduler {
    static final RxThreadFactory d = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5).intValue())), true);
    static final ScheduledExecutorService e = Executors.newScheduledThreadPool(0);
    final ThreadFactory b;
    final AtomicReference<ScheduledExecutorService> c;

    static final class ScheduledWorker extends Worker {
        final ScheduledExecutorService a;
        final CompositeDisposable b = new CompositeDisposable();
        volatile boolean c;

        ScheduledWorker(ScheduledExecutorService scheduledExecutorService) {
            this.a = scheduledExecutorService;
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            Future future;
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(RxJavaPlugins.onSchedule(runnable), this.b);
            this.b.add(scheduledRunnable);
            if (j <= 0) {
                try {
                    future = this.a.submit(scheduledRunnable);
                } catch (RejectedExecutionException e) {
                    dispose();
                    RxJavaPlugins.onError(e);
                    return EmptyDisposable.INSTANCE;
                }
            } else {
                future = this.a.schedule(scheduledRunnable, j, timeUnit);
            }
            scheduledRunnable.setFuture(future);
            return scheduledRunnable;
        }

        public void dispose() {
            if (!this.c) {
                this.c = true;
                this.b.dispose();
            }
        }

        public boolean isDisposed() {
            return this.c;
        }
    }

    static {
        e.shutdown();
    }

    public SingleScheduler() {
        this(d);
    }

    public SingleScheduler(ThreadFactory threadFactory) {
        this.c = new AtomicReference<>();
        this.b = threadFactory;
        this.c.lazySet(a(threadFactory));
    }

    static ScheduledExecutorService a(ThreadFactory threadFactory) {
        return SchedulerPoolFactory.create(threadFactory);
    }

    public void start() {
        ScheduledExecutorService scheduledExecutorService;
        ScheduledExecutorService scheduledExecutorService2 = null;
        do {
            scheduledExecutorService = (ScheduledExecutorService) this.c.get();
            if (scheduledExecutorService != e) {
                if (scheduledExecutorService2 != null) {
                    scheduledExecutorService2.shutdown();
                }
                return;
            } else if (scheduledExecutorService2 == null) {
                scheduledExecutorService2 = a(this.b);
            }
        } while (!this.c.compareAndSet(scheduledExecutorService, scheduledExecutorService2));
    }

    public void shutdown() {
        if (((ScheduledExecutorService) this.c.get()) != e) {
            ScheduledExecutorService scheduledExecutorService = (ScheduledExecutorService) this.c.getAndSet(e);
            if (scheduledExecutorService != e) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    @NonNull
    public Worker createWorker() {
        return new ScheduledWorker((ScheduledExecutorService) this.c.get());
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Future future;
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(RxJavaPlugins.onSchedule(runnable));
        if (j <= 0) {
            try {
                future = ((ScheduledExecutorService) this.c.get()).submit(scheduledDirectTask);
            } catch (RejectedExecutionException e2) {
                RxJavaPlugins.onError(e2);
                return EmptyDisposable.INSTANCE;
            }
        } else {
            future = ((ScheduledExecutorService) this.c.get()).schedule(scheduledDirectTask, j, timeUnit);
        }
        scheduledDirectTask.setFuture(future);
        return scheduledDirectTask;
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(RxJavaPlugins.onSchedule(runnable));
        try {
            scheduledDirectPeriodicTask.setFuture(((ScheduledExecutorService) this.c.get()).scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
            return scheduledDirectPeriodicTask;
        } catch (RejectedExecutionException e2) {
            RxJavaPlugins.onError(e2);
            return EmptyDisposable.INSTANCE;
        }
    }
}
