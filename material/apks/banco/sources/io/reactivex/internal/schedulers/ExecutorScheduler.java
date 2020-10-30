package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ExecutorScheduler extends Scheduler {
    static final Scheduler c = Schedulers.single();
    @NonNull
    final Executor b;

    final class DelayedDispose implements Runnable {
        private final DelayedRunnable b;

        DelayedDispose(DelayedRunnable delayedRunnable) {
            this.b = delayedRunnable;
        }

        public void run() {
            this.b.b.replace(ExecutorScheduler.this.scheduleDirect(this.b));
        }
    }

    static final class DelayedRunnable extends AtomicReference<Runnable> implements Disposable, Runnable {
        private static final long serialVersionUID = -4101336210206799084L;
        final SequentialDisposable a = new SequentialDisposable();
        final SequentialDisposable b = new SequentialDisposable();

        DelayedRunnable(Runnable runnable) {
            super(runnable);
        }

        public void run() {
            Runnable runnable = (Runnable) get();
            if (runnable != null) {
                try {
                    runnable.run();
                } finally {
                    lazySet(null);
                    this.a.lazySet(DisposableHelper.DISPOSED);
                    this.b.lazySet(DisposableHelper.DISPOSED);
                }
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }

        public void dispose() {
            if (getAndSet(null) != null) {
                this.a.dispose();
                this.b.dispose();
            }
        }
    }

    public static final class ExecutorWorker extends Worker implements Runnable {
        final Executor a;
        final MpscLinkedQueue<Runnable> b;
        volatile boolean c;
        final AtomicInteger d = new AtomicInteger();
        final CompositeDisposable e = new CompositeDisposable();

        static final class BooleanRunnable extends AtomicBoolean implements Disposable, Runnable {
            private static final long serialVersionUID = -2421395018820541164L;
            final Runnable a;

            BooleanRunnable(Runnable runnable) {
                this.a = runnable;
            }

            public void run() {
                if (!get()) {
                    try {
                        this.a.run();
                    } finally {
                        lazySet(true);
                    }
                }
            }

            public void dispose() {
                lazySet(true);
            }

            public boolean isDisposed() {
                return get();
            }
        }

        final class SequentialDispose implements Runnable {
            private final SequentialDisposable b;
            private final Runnable c;

            SequentialDispose(SequentialDisposable sequentialDisposable, Runnable runnable) {
                this.b = sequentialDisposable;
                this.c = runnable;
            }

            public void run() {
                this.b.replace(ExecutorWorker.this.schedule(this.c));
            }
        }

        public ExecutorWorker(Executor executor) {
            this.a = executor;
            this.b = new MpscLinkedQueue<>();
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            BooleanRunnable booleanRunnable = new BooleanRunnable(RxJavaPlugins.onSchedule(runnable));
            this.b.offer(booleanRunnable);
            if (this.d.getAndIncrement() == 0) {
                try {
                    this.a.execute(this);
                } catch (RejectedExecutionException e2) {
                    this.c = true;
                    this.b.clear();
                    RxJavaPlugins.onError(e2);
                    return EmptyDisposable.INSTANCE;
                }
            }
            return booleanRunnable;
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (j <= 0) {
                return schedule(runnable);
            }
            if (this.c) {
                return EmptyDisposable.INSTANCE;
            }
            SequentialDisposable sequentialDisposable = new SequentialDisposable();
            SequentialDisposable sequentialDisposable2 = new SequentialDisposable(sequentialDisposable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(new SequentialDispose(sequentialDisposable2, RxJavaPlugins.onSchedule(runnable)), this.e);
            this.e.add(scheduledRunnable);
            if (this.a instanceof ScheduledExecutorService) {
                try {
                    scheduledRunnable.setFuture(((ScheduledExecutorService) this.a).schedule(scheduledRunnable, j, timeUnit));
                } catch (RejectedExecutionException e2) {
                    this.c = true;
                    RxJavaPlugins.onError(e2);
                    return EmptyDisposable.INSTANCE;
                }
            } else {
                scheduledRunnable.setFuture(new DisposeOnCancel(ExecutorScheduler.c.scheduleDirect(scheduledRunnable, j, timeUnit)));
            }
            sequentialDisposable.replace(scheduledRunnable);
            return sequentialDisposable2;
        }

        public void dispose() {
            if (!this.c) {
                this.c = true;
                this.e.dispose();
                if (this.d.getAndIncrement() == 0) {
                    this.b.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.c;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
            r1 = r3.d.addAndGet(-r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
            if (r1 != 0) goto L_0x0003;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0015, code lost:
            if (r3.c == false) goto L_0x001b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
            r0.clear();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
                io.reactivex.internal.queue.MpscLinkedQueue<java.lang.Runnable> r0 = r3.b
                r1 = 1
            L_0x0003:
                boolean r2 = r3.c
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            L_0x000b:
                java.lang.Object r2 = r0.poll()
                java.lang.Runnable r2 = (java.lang.Runnable) r2
                if (r2 != 0) goto L_0x0025
                boolean r2 = r3.c
                if (r2 == 0) goto L_0x001b
                r0.clear()
                return
            L_0x001b:
                java.util.concurrent.atomic.AtomicInteger r2 = r3.d
                int r1 = -r1
                int r1 = r2.addAndGet(r1)
                if (r1 != 0) goto L_0x0003
                return
            L_0x0025:
                r2.run()
                boolean r2 = r3.c
                if (r2 == 0) goto L_0x000b
                r0.clear()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker.run():void");
        }
    }

    public ExecutorScheduler(@NonNull Executor executor) {
        this.b = executor;
    }

    @NonNull
    public Worker createWorker() {
        return new ExecutorWorker(this.b);
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        try {
            if (this.b instanceof ExecutorService) {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(onSchedule);
                scheduledDirectTask.setFuture(((ExecutorService) this.b).submit(scheduledDirectTask));
                return scheduledDirectTask;
            }
            BooleanRunnable booleanRunnable = new BooleanRunnable(onSchedule);
            this.b.execute(booleanRunnable);
            return booleanRunnable;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        Runnable onSchedule = RxJavaPlugins.onSchedule(runnable);
        if (this.b instanceof ScheduledExecutorService) {
            try {
                ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(onSchedule);
                scheduledDirectTask.setFuture(((ScheduledExecutorService) this.b).schedule(scheduledDirectTask, j, timeUnit));
                return scheduledDirectTask;
            } catch (RejectedExecutionException e) {
                RxJavaPlugins.onError(e);
                return EmptyDisposable.INSTANCE;
            }
        } else {
            DelayedRunnable delayedRunnable = new DelayedRunnable(onSchedule);
            delayedRunnable.a.replace(c.scheduleDirect(new DelayedDispose(delayedRunnable), j, timeUnit));
            return delayedRunnable;
        }
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        if (!(this.b instanceof ScheduledExecutorService)) {
            return super.schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
        }
        try {
            ScheduledDirectPeriodicTask scheduledDirectPeriodicTask = new ScheduledDirectPeriodicTask(RxJavaPlugins.onSchedule(runnable));
            scheduledDirectPeriodicTask.setFuture(((ScheduledExecutorService) this.b).scheduleAtFixedRate(scheduledDirectPeriodicTask, j, j2, timeUnit));
            return scheduledDirectPeriodicTask;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }
}
