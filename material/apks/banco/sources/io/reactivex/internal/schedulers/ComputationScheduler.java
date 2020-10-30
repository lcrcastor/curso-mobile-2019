package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ComputationScheduler extends Scheduler {
    static final FixedSchedulerPool b = new FixedSchedulerPool(0, c);
    static final RxThreadFactory c = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5).intValue())), true);
    static final int d = a(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx2.computation-threads", 0).intValue());
    static final PoolWorker e = new PoolWorker(new RxThreadFactory("RxComputationShutdown"));
    final ThreadFactory f;
    final AtomicReference<FixedSchedulerPool> g;

    static final class EventLoopWorker extends Worker {
        volatile boolean a;
        private final ListCompositeDisposable b = new ListCompositeDisposable();
        private final CompositeDisposable c = new CompositeDisposable();
        private final ListCompositeDisposable d = new ListCompositeDisposable();
        private final PoolWorker e;

        EventLoopWorker(PoolWorker poolWorker) {
            this.e = poolWorker;
            this.d.add(this.b);
            this.d.add(this.c);
        }

        public void dispose() {
            if (!this.a) {
                this.a = true;
                this.d.dispose();
            }
        }

        public boolean isDisposed() {
            return this.a;
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            return this.e.scheduleActual(runnable, 0, TimeUnit.MILLISECONDS, this.b);
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            return this.e.scheduleActual(runnable, j, timeUnit, this.c);
        }
    }

    static final class FixedSchedulerPool {
        final int a;
        final PoolWorker[] b;
        long c;

        FixedSchedulerPool(int i, ThreadFactory threadFactory) {
            this.a = i;
            this.b = new PoolWorker[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.b[i2] = new PoolWorker(threadFactory);
            }
        }

        public PoolWorker a() {
            int i = this.a;
            if (i == 0) {
                return ComputationScheduler.e;
            }
            PoolWorker[] poolWorkerArr = this.b;
            long j = this.c;
            this.c = j + 1;
            return poolWorkerArr[(int) (j % ((long) i))];
        }

        public void b() {
            for (PoolWorker dispose : this.b) {
                dispose.dispose();
            }
        }
    }

    static final class PoolWorker extends NewThreadWorker {
        PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

    static int a(int i, int i2) {
        return (i2 <= 0 || i2 > i) ? i : i2;
    }

    static {
        e.dispose();
        b.b();
    }

    public ComputationScheduler() {
        this(c);
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        this.f = threadFactory;
        this.g = new AtomicReference<>(b);
        start();
    }

    @NonNull
    public Worker createWorker() {
        return new EventLoopWorker(((FixedSchedulerPool) this.g.get()).a());
    }

    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        return ((FixedSchedulerPool) this.g.get()).a().scheduleDirect(runnable, j, timeUnit);
    }

    @NonNull
    public Disposable schedulePeriodicallyDirect(@NonNull Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return ((FixedSchedulerPool) this.g.get()).a().schedulePeriodicallyDirect(runnable, j, j2, timeUnit);
    }

    public void start() {
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(d, this.f);
        if (!this.g.compareAndSet(b, fixedSchedulerPool)) {
            fixedSchedulerPool.b();
        }
    }

    public void shutdown() {
        FixedSchedulerPool fixedSchedulerPool;
        do {
            fixedSchedulerPool = (FixedSchedulerPool) this.g.get();
            if (fixedSchedulerPool == b) {
                return;
            }
        } while (!this.g.compareAndSet(fixedSchedulerPool, b));
        fixedSchedulerPool.b();
    }
}
