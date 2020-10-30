package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class IoScheduler extends Scheduler {
    static final RxThreadFactory b;
    static final RxThreadFactory c;
    static final ThreadWorker d = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
    static final CachedWorkerPool g = new CachedWorkerPool(0, null, b);
    private static final TimeUnit h = TimeUnit.SECONDS;
    final ThreadFactory e;
    final AtomicReference<CachedWorkerPool> f;

    static final class CachedWorkerPool implements Runnable {
        final CompositeDisposable a;
        private final long b;
        private final ConcurrentLinkedQueue<ThreadWorker> c;
        private final ScheduledExecutorService d;
        private final Future<?> e;
        private final ThreadFactory f;

        CachedWorkerPool(long j, TimeUnit timeUnit, ThreadFactory threadFactory) {
            Future<?> future;
            this.b = timeUnit != null ? timeUnit.toNanos(j) : 0;
            this.c = new ConcurrentLinkedQueue<>();
            this.a = new CompositeDisposable();
            this.f = threadFactory;
            ScheduledExecutorService scheduledExecutorService = null;
            if (timeUnit != null) {
                scheduledExecutorService = Executors.newScheduledThreadPool(1, IoScheduler.c);
                future = scheduledExecutorService.scheduleWithFixedDelay(this, this.b, this.b, TimeUnit.NANOSECONDS);
            } else {
                future = null;
            }
            this.d = scheduledExecutorService;
            this.e = future;
        }

        public void run() {
            b();
        }

        /* access modifiers changed from: 0000 */
        public ThreadWorker a() {
            if (this.a.isDisposed()) {
                return IoScheduler.d;
            }
            while (!this.c.isEmpty()) {
                ThreadWorker threadWorker = (ThreadWorker) this.c.poll();
                if (threadWorker != null) {
                    return threadWorker;
                }
            }
            ThreadWorker threadWorker2 = new ThreadWorker(this.f);
            this.a.add(threadWorker2);
            return threadWorker2;
        }

        /* access modifiers changed from: 0000 */
        public void a(ThreadWorker threadWorker) {
            threadWorker.a(c() + this.b);
            this.c.offer(threadWorker);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (!this.c.isEmpty()) {
                long c2 = c();
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    ThreadWorker threadWorker = (ThreadWorker) it.next();
                    if (threadWorker.a() > c2) {
                        return;
                    }
                    if (this.c.remove(threadWorker)) {
                        this.a.remove(threadWorker);
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public long c() {
            return System.nanoTime();
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            this.a.dispose();
            if (this.e != null) {
                this.e.cancel(true);
            }
            if (this.d != null) {
                this.d.shutdownNow();
            }
        }
    }

    static final class EventLoopWorker extends Worker {
        final AtomicBoolean a = new AtomicBoolean();
        private final CompositeDisposable b;
        private final CachedWorkerPool c;
        private final ThreadWorker d;

        EventLoopWorker(CachedWorkerPool cachedWorkerPool) {
            this.c = cachedWorkerPool;
            this.b = new CompositeDisposable();
            this.d = cachedWorkerPool.a();
        }

        public void dispose() {
            if (this.a.compareAndSet(false, true)) {
                this.b.dispose();
                this.c.a(this.d);
            }
        }

        public boolean isDisposed() {
            return this.a.get();
        }

        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.b.isDisposed()) {
                return EmptyDisposable.INSTANCE;
            }
            return this.d.scheduleActual(runnable, j, timeUnit, this.b);
        }
    }

    static final class ThreadWorker extends NewThreadWorker {
        private long b = 0;

        ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }

        public long a() {
            return this.b;
        }

        public void a(long j) {
            this.b = j;
        }
    }

    static {
        d.dispose();
        int max = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
        b = new RxThreadFactory("RxCachedThreadScheduler", max);
        c = new RxThreadFactory("RxCachedWorkerPoolEvictor", max);
        g.d();
    }

    public IoScheduler() {
        this(b);
    }

    public IoScheduler(ThreadFactory threadFactory) {
        this.e = threadFactory;
        this.f = new AtomicReference<>(g);
        start();
    }

    public void start() {
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(60, h, this.e);
        if (!this.f.compareAndSet(g, cachedWorkerPool)) {
            cachedWorkerPool.d();
        }
    }

    public void shutdown() {
        CachedWorkerPool cachedWorkerPool;
        do {
            cachedWorkerPool = (CachedWorkerPool) this.f.get();
            if (cachedWorkerPool == g) {
                return;
            }
        } while (!this.f.compareAndSet(cachedWorkerPool, g));
        cachedWorkerPool.d();
    }

    @NonNull
    public Worker createWorker() {
        return new EventLoopWorker((CachedWorkerPool) this.f.get());
    }

    public int size() {
        return ((CachedWorkerPool) this.f.get()).a.size();
    }
}
